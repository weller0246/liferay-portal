/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.osgi.web.wab.generator.internal.processor;

import aQute.bnd.cdi.Discover;
import aQute.bnd.component.DSAnnotations;
import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.make.component.ServiceComponent;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.FileResource;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Packages;
import aQute.bnd.osgi.Resource;
import aQute.bnd.service.verifier.VerifierPlugin;
import aQute.bnd.version.Version;

import aQute.lib.filter.Filter;

import com.liferay.ant.bnd.jsp.JspAnalyzerPlugin;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.GlobalStartupAction;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.deploy.hot.DependencyManagementThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.servlet.PortalClassLoaderFilter;
import com.liferay.portal.kernel.servlet.PortalClassLoaderServlet;
import com.liferay.portal.kernel.util.ConcurrentHashMapBuilder;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.JS;
import com.liferay.whip.util.ReflectionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.Files;
import java.nio.file.Path;

import java.text.Format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WabProcessor {

	public WabProcessor(
		ClassLoader classLoader, File file, Map<String, String[]> parameters) {

		_file = file;
		_parameters = parameters;
	}

	public File getProcessedFile() throws IOException {
		_pluginDir = autoDeploy();

		if ((_pluginDir == null) || !_pluginDir.exists() ||
			!_pluginDir.isDirectory()) {

			return null;
		}

		File outputFile = null;

		try (Jar jar = new Jar(_pluginDir)) {
			if (jar.getBsn() == null) {
				outputFile = transformToOSGiBundle(jar);
			}
		}
		catch (Exception exception) {
			ReflectionUtil.throwException(exception);
		}

		if (PropsValues.MODULE_FRAMEWORK_WEB_GENERATOR_GENERATED_WABS_STORE) {
			writeGeneratedWab(outputFile);
		}

		return outputFile;
	}

	protected void addElement(Element element, String name, String text) {
		Element childElement = element.addElement(name);

		childElement.addText(GetterUtil.getString(text));
	}

	protected void appendProperty(
		Analyzer analyzer, String property, String string) {

		analyzer.setProperty(
			property, Analyzer.append(analyzer.getProperty(property), string));
	}

	protected File autoDeploy() {
		AutoDeploymentContext autoDeploymentContext =
			buildAutoDeploymentContext(getWebContextPath());

		executeAutoDeployers(autoDeploymentContext);

		_pluginPackage = autoDeploymentContext.getPluginPackage();

		if (_pluginPackage != null) {
			_context = _pluginPackage.getContext();
		}
		else {
			_context = autoDeploymentContext.getContext();
		}

		if (_file.isDirectory()) {
			return _file;
		}

		File deployDir = autoDeploymentContext.getDeployDir();

		if (!deployDir.exists()) {
			File parentFile = deployDir.getParentFile();

			File[] files = parentFile.listFiles(
				new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".war");
					}

				});

			if ((files == null) || (files.length == 0)) {
				_log.error("Unable to find any WARs in " + parentFile);

				return null;
			}

			File file = files[0];

			deployDir.mkdirs();

			if (file.isDirectory()) {
				FileUtil.move(file, deployDir);
			}
			else {
				try (Jar jar = new Jar(file)) {
					jar.expand(deployDir);
				}
				catch (Exception exception) {
					ReflectionUtil.throwException(exception);
				}
			}
		}

		if (_AUTODEPLOYED_WARS_STORE) {
			writeAutoDeployedWar(deployDir);
		}

		return deployDir;
	}

	protected AutoDeploymentContext buildAutoDeploymentContext(String context) {
		AutoDeploymentContext autoDeploymentContext =
			new AutoDeploymentContext();

		autoDeploymentContext.setContext(context);
		autoDeploymentContext.setFile(_file);

		if (_file.isDirectory()) {
			autoDeploymentContext.setDestDir(_file.getAbsolutePath());

			return autoDeploymentContext;
		}

		File file = new File(_file.getParentFile(), "deploy");

		file.mkdirs();

		autoDeploymentContext.setDestDir(file.getAbsolutePath());

		return autoDeploymentContext;
	}

	protected void executeAutoDeployers(
		AutoDeploymentContext autoDeploymentContext) {

		boolean enabled = DependencyManagementThreadLocal.isEnabled();

		try {
			DependencyManagementThreadLocal.setEnabled(false);

			AutoDeployListener autoDeployListener = getAutoDeployListener(
				autoDeploymentContext,
				GlobalStartupAction.getAutoDeployListeners(false));

			autoDeployListener.deploy(autoDeploymentContext);
		}
		catch (AutoDeployException autoDeployException) {
			throw new RuntimeException(autoDeployException);
		}
		finally {
			DependencyManagementThreadLocal.setEnabled(enabled);
		}
	}

	protected void formatDocument(File file, Document document)
		throws IOException {

		try {
			FileUtil.write(file, document.formattedString("  "));
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	protected AutoDeployListener getAutoDeployListener(
		AutoDeploymentContext autoDeploymentContext,
		List<AutoDeployListener> autoDeployListeners) {

		List<AutoDeployListener> deployableAutoDeployListeners =
			new ArrayList<>();

		for (AutoDeployListener autoDeployListener : autoDeployListeners) {
			try {
				if (autoDeployListener.isDeployable(autoDeploymentContext)) {
					deployableAutoDeployListeners.add(autoDeployListener);
				}
			}
			catch (AutoDeployException autoDeployException) {
				throw new RuntimeException(autoDeployException);
			}
		}

		if (deployableAutoDeployListeners.size() > 1) {
			StringBundler sb = new StringBundler(
				3 + (deployableAutoDeployListeners.size() * 2) - 1);

			sb.append("More than one auto deploy listener is available for ");
			sb.append(autoDeploymentContext.getFile());
			sb.append(": ");

			for (int i = 0; i < deployableAutoDeployListeners.size(); i++) {
				AutoDeployListener deployableAutoDeployListener =
					deployableAutoDeployListeners.get(i);

				Class<?> clazz = deployableAutoDeployListener.getClass();

				if (i != 0) {
					sb.append(StringPool.COMMA_AND_SPACE);
				}

				sb.append(clazz.getName());
			}

			throw new RuntimeException(new AutoDeployException(sb.toString()));
		}

		return deployableAutoDeployListeners.get(0);
	}

	protected Properties getPluginPackageProperties() {
		File file = new File(
			_pluginDir, "WEB-INF/liferay-plugin-package.properties");

		if (!file.exists()) {
			return new Properties();
		}

		try {
			return PropertiesUtil.load(FileUtil.read(file));
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException, ioException);
			}

			return new Properties();
		}
	}

	protected String getVersionedServicePackageName(String partialPackageName) {
		return StringBundler.concat(
			_servicePackageName, partialPackageName, ";version=",
			_bundleVersion);
	}

	protected String getWebContextPath() {
		String webContextpath = MapUtil.getString(
			_parameters, "Web-ContextPath");

		if (!webContextpath.startsWith(StringPool.SLASH)) {
			webContextpath = StringPool.SLASH.concat(webContextpath);
		}

		return webContextpath;
	}

	protected void processBeans(Builder analyzer) throws IOException {
		String beansXMLFile = "WEB-INF/beans.xml";

		File file = new File(_pluginDir, beansXMLFile);

		if (!file.exists()) {
			beansXMLFile = "WEB-INF/classes/META-INF/beans.xml";

			file = new File(_pluginDir, beansXMLFile);
		}

		if (!file.exists()) {
			return;
		}

		String finalBeansXMLFile = beansXMLFile;

		Set<Object> plugins = analyzer.getPlugins();

		plugins.add(
			new VerifierPlugin() {

				@Override
				public void verify(Analyzer analyzer) throws Exception {
					Parameters requireCapabilities = analyzer.parseHeader(
						analyzer.getProperty(Constants.REQUIRE_CAPABILITY));

					Map<String, Object> arguments =
						HashMapBuilder.<String, Object>put(
							"osgi.extender", "osgi.cdi"
						).put(
							"version", new Version(1)
						).build();

					for (Map.Entry<String, Attrs> entry :
							requireCapabilities.entrySet()) {

						String namespace = entry.getKey();

						Attrs attrs = entry.getValue();

						String filterString = attrs.get(
							Constants.FILTER_DIRECTIVE);

						Filter filter = new Filter(filterString);

						if (Objects.equals(namespace, "osgi.extender") &&
							filter.matchMap(arguments)) {

							attrs.putTyped(
								"descriptor", Arrays.asList(finalBeansXMLFile));
						}
					}

					analyzer.setProperty(
						Constants.REQUIRE_CAPABILITY,
						requireCapabilities.toString());
				}

			});

		String cdiInstruction = analyzer.getProperty(Constants.CDIANNOTATIONS);

		if (cdiInstruction != null) {
			return;
		}

		Document document = readDocument(file);

		Discover discover = _findDiscoveryMode(document);

		analyzer.setProperty(
			Constants.CDIANNOTATIONS, "*;discover=" + discover);

		appendProperty(
			analyzer, Constants.REQUIRE_CAPABILITY, _CDI_REQUIREMENTS);
	}

	protected void processBundleClasspath(
			Analyzer analyzer, Properties pluginPackageProperties)
		throws IOException {

		appendProperty(
			analyzer, Constants.BUNDLE_CLASSPATH, "ext/WEB-INF/classes");

		// Class path order is critical

		Map<String, File> classPath = LinkedHashMapBuilder.<String, File>put(
			"WEB-INF/classes", new File(_pluginDir, "WEB-INF/classes")
		).build();

		appendProperty(analyzer, Constants.BUNDLE_CLASSPATH, "WEB-INF/classes");

		processFiles(classPath, analyzer);

		Collection<File> files = classPath.values();

		analyzer.setClasspath(files.toArray(new File[classPath.size()]));
	}

	protected void processBundleManifestVersion(Analyzer analyzer) {
		String bundleManifestVersion = MapUtil.getString(
			_parameters, Constants.BUNDLE_MANIFESTVERSION);

		if (Validator.isNull(bundleManifestVersion)) {
			bundleManifestVersion = "2";
		}

		analyzer.setProperty(
			Constants.BUNDLE_MANIFESTVERSION, bundleManifestVersion);
	}

	protected void processBundleSymbolicName(Analyzer analyzer) {
		String bundleSymbolicName = MapUtil.getString(
			_parameters, Constants.BUNDLE_SYMBOLICNAME);

		if (Validator.isNull(bundleSymbolicName)) {
			bundleSymbolicName = _context.substring(1);
		}

		analyzer.setProperty(Constants.BUNDLE_SYMBOLICNAME, bundleSymbolicName);
	}

	protected void processBundleVersion(Analyzer analyzer) {
		_bundleVersion = MapUtil.getString(
			_parameters, Constants.BUNDLE_VERSION);

		if (Validator.isNull(_bundleVersion)) {
			if (_pluginPackage != null) {
				_bundleVersion = _pluginPackage.getVersion();
			}
			else {
				_bundleVersion = "1.0.0";
			}
		}

		if (!Version.isVersion(_bundleVersion)) {

			// Convert from the Maven format to the OSGi format

			Matcher matcher = _versionMavenPattern.matcher(_bundleVersion);

			if (matcher.matches()) {
				_bundleVersion = StringBundler.concat(
					matcher.group(1), ".", matcher.group(3), ".",
					matcher.group(5), ".", matcher.group(7));
			}
			else {
				_bundleVersion =
					"0.0.0." + StringUtil.replace(_bundleVersion, '.', '_');
			}
		}

		analyzer.setProperty(Constants.BUNDLE_VERSION, _bundleVersion);
	}

	protected void processClass(Analyzer analyzer, String value) {
		int index = value.lastIndexOf('.');

		if (index == -1) {
			return;
		}

		Packages packages = analyzer.getReferred();

		String packageName = value.substring(0, index);

		packages.put(analyzer.getPackageRef(packageName), new Attrs());
	}

	protected void processDeclarativeReferences(Analyzer analyzer)
		throws IOException {

		processDefaultServletPackages();
		processTLDDependencies(analyzer);

		processPortalListenerClassesDependencies(analyzer);

		Path pluginPath = _pluginDir.toPath();

		processXMLDependencies(
			analyzer, "WEB-INF/liferay-hook.xml", _XPATHS_HOOK);
		processXMLDependencies(
			analyzer, "WEB-INF/liferay-portlet.xml", _XPATHS_LIFERAY);
		processXMLDependencies(
			analyzer, "WEB-INF/portlet.xml", _XPATHS_PORTLET);
		processXMLDependencies(analyzer, "WEB-INF/web.xml", _XPATHS_JAVAEE);

		Path classes = pluginPath.resolve("WEB-INF/classes/");

		processPropertiesDependencies(
			analyzer, classes, ".properties", _KNOWN_PROPERTY_KEYS);
		processXMLDependencies(analyzer, classes, ".xml", _XPATHS_HBM);
		processXMLDependencies(analyzer, classes, ".xml", _XPATHS_SPRING);
	}

	protected void processDefaultServletPackages() {
		for (String value :
				PropsValues.
					MODULE_FRAMEWORK_WEB_GENERATOR_DEFAULT_SERVLET_PACKAGES) {

			Parameters defaultPackage = new Parameters(value);

			for (String packageName : defaultPackage.keySet()) {
				if (_importPackageParameters.containsKey(packageName)) {
					continue;
				}

				_importPackageParameters.add(packageName, _optionalAttrs);
			}
		}
	}

	protected void processExportPackageNames(Analyzer analyzer) {
		analyzer.setProperty(
			Constants.EXPORT_CONTENTS, _exportPackageParameters.toString());
	}

	protected void processExtraHeaders(Analyzer analyzer) {
		String bundleSymbolicName = analyzer.getProperty(
			Constants.BUNDLE_SYMBOLICNAME);

		Properties properties = PropsUtil.getProperties(
			PropsKeys.MODULE_FRAMEWORK_WEB_GENERATOR_HEADERS, true);

		Enumeration<Object> enumeration = properties.keys();

		while (enumeration.hasMoreElements()) {
			String key = (String)enumeration.nextElement();

			String value = properties.getProperty(key);

			String processedKey = key;

			if (processedKey.endsWith(StringPool.CLOSE_BRACKET)) {
				String filterString =
					StringPool.OPEN_BRACKET + bundleSymbolicName +
						StringPool.CLOSE_BRACKET;

				if (!processedKey.endsWith(filterString)) {
					continue;
				}

				processedKey = processedKey.substring(
					0, processedKey.indexOf(StringPool.OPEN_BRACKET));
			}

			if (Validator.isNotNull(value)) {
				Parameters parameters = new Parameters(value);

				if (processedKey.equals(Constants.EXPORT_PACKAGE)) {
					_exportPackageParameters.mergeWith(parameters, true);
				}
				else if (processedKey.equals(Constants.IMPORT_PACKAGE)) {
					_importPackageParameters.mergeWith(parameters, true);
				}

				analyzer.setProperty(processedKey, parameters.toString());
			}
		}
	}

	protected void processExtraRequirements() {
		Attrs attrs = new Attrs(_optionalAttrs);

		attrs.put("x-liferay-compatibility:", "spring");

		_importPackageParameters.add("org.eclipse.core.runtime", attrs);

		_importPackageParameters.add("!junit.*", new Attrs());
	}

	protected void processFiles(Map<String, File> classPath, Analyzer analyzer)
		throws IOException {

		Jar jar = analyzer.getJar();

		Map<String, Resource> resources = jar.getResources();

		Set<Map.Entry<String, Resource>> entrySet = resources.entrySet();

		Iterator<Map.Entry<String, Resource>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, Resource> entry = iterator.next();

			String path = entry.getKey();

			if (path.equals("WEB-INF/service.xml")) {
				processServicePackageName(entry.getValue());
			}
			else if (path.startsWith("WEB-INF/lib/")) {

				// Remove any other "-service.jar" or ignored jar so that real
				// imports are used

				if ((path.endsWith("-service.jar") &&
					 !path.endsWith(_context.concat("-service.jar"))) ||
					_ignoredResourcePaths.contains(path)) {

					iterator.remove();

					continue;
				}

				Resource resource = entry.getValue();

				if (resource instanceof FileResource) {
					FileResource fileResource = (FileResource)resource;

					classPath.put(path, fileResource.getFile());

					appendProperty(analyzer, Constants.BUNDLE_CLASSPATH, path);
				}
			}
			else if (_ignoredResourcePaths.contains(path)) {
				iterator.remove();
			}
		}
	}

	protected void processImportPackageNames(Analyzer analyzer) {
		String packageName = MapUtil.getString(
			_parameters, Constants.IMPORT_PACKAGE);

		if (Validator.isNotNull(packageName)) {
			analyzer.setProperty(Constants.IMPORT_PACKAGE, packageName);
		}
		else {
			StringBundler sb = new StringBundler(
				(_importPackageParameters.size() * 4) + 1);

			for (Map.Entry<String, Attrs> entry :
					_importPackageParameters.entrySet()) {

				String importPackageName = entry.getKey();

				boolean containedInClasspath = false;

				for (Jar jar : analyzer.getClasspath()) {
					List<String> packages = jar.getPackages();

					if (packages.contains(importPackageName)) {
						containedInClasspath = true;

						break;
					}
				}

				if (containedInClasspath) {
					continue;
				}

				sb.append(importPackageName);

				Attrs attrs = entry.getValue();

				if (!attrs.isEmpty()) {
					sb.append(";");
					sb.append(entry.getValue());
				}

				sb.append(StringPool.COMMA);
			}

			sb.append("*;resolution:=\"optional\"");

			analyzer.setProperty(Constants.IMPORT_PACKAGE, sb.toString());
		}
	}

	protected void processLiferayPortletXML() throws IOException {
		File file = new File(_pluginDir, "WEB-INF/liferay-portlet.xml");

		if (!file.exists()) {
			return;
		}

		Document document = readDocument(file);

		Element rootElement = document.getRootElement();

		for (Element element : rootElement.elements("portlet")) {
			Element strutsPathElement = element.element("struts-path");

			if (strutsPathElement == null) {
				continue;
			}

			String strutsPath = strutsPathElement.getTextTrim();

			if (!strutsPath.startsWith(StringPool.SLASH)) {
				strutsPath = StringPool.SLASH.concat(strutsPath);
			}

			strutsPathElement.setText(
				Portal.PATH_MODULE.substring(1) + _context + strutsPath);
		}

		formatDocument(file, document);
	}

	protected void processPackageNames(Analyzer analyzer) {
		processExportPackageNames(analyzer);
		processImportPackageNames(analyzer);
	}

	protected void processPluginPackagePropertiesExportImportPackages(
		Properties pluginPackageProperties) {

		if (pluginPackageProperties == null) {
			return;
		}

		String exportPackage = pluginPackageProperties.getProperty(
			Constants.EXPORT_PACKAGE);

		if (Validator.isNotNull(exportPackage)) {
			Parameters parameters = new Parameters(exportPackage);

			_exportPackageParameters.mergeWith(parameters, true);

			pluginPackageProperties.remove(Constants.EXPORT_PACKAGE);
		}

		String importPackage = pluginPackageProperties.getProperty(
			Constants.IMPORT_PACKAGE);

		if (Validator.isNotNull(importPackage)) {
			Parameters parameters = new Parameters(importPackage);

			_importPackageParameters.mergeWith(parameters, true);

			pluginPackageProperties.remove(Constants.IMPORT_PACKAGE);
		}
	}

	protected void processPortalListenerClassesDependencies(Analyzer analyzer) {
		File file = new File(_pluginDir, "WEB-INF/web.xml");

		if (!file.exists()) {
			return;
		}

		Document document = readDocument(file);

		Element rootElement = document.getRootElement();

		List<Element> contextParamElements = rootElement.elements(
			"context-param");

		for (Element contextParamElement : contextParamElements) {
			String paramName = contextParamElement.elementText("param-name");

			if (Validator.isNotNull(paramName) &&
				paramName.equals("portalListenerClasses")) {

				String paramValue = contextParamElement.elementText(
					"param-value");

				String[] portalListenerClassNames = StringUtil.split(
					paramValue, StringPool.COMMA);

				for (String portalListenerClassName :
						portalListenerClassNames) {

					processClass(analyzer, portalListenerClassName.trim());
				}
			}
		}
	}

	protected void processPropertiesDependencies(
		Analyzer analyzer, File file, String[] knownPropertyKeys) {

		if (!file.exists()) {
			return;
		}

		try (InputStream inputStream = new FileInputStream(file)) {
			Properties properties = new Properties();

			properties.load(inputStream);

			if (properties.isEmpty()) {
				return;
			}

			for (String key : knownPropertyKeys) {
				String value = properties.getProperty(key);

				if (value == null) {
					continue;
				}

				value = value.trim();

				processClass(analyzer, value);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			// Ignore this case

		}
	}

	protected void processPropertiesDependencies(
			Analyzer analyzer, Path path, String suffix,
			String[] knownPropertyKeys)
		throws IOException {

		File file = path.toFile();

		if (!file.isDirectory()) {
			return;
		}

		Stream<Path> pathStream = Files.walk(path);

		Stream<File> fileStream = pathStream.map(Path::toFile);

		fileStream.forEach(
			entry -> {
				String pathString = entry.getPath();

				if (pathString.endsWith(suffix)) {
					processPropertiesDependencies(
						analyzer, entry, knownPropertyKeys);
				}
			});
	}

	protected void processRequiredDeploymentContexts(Analyzer analyzer) {
		if (_pluginPackage == null) {
			return;
		}

		List<String> requiredDeploymentContexts =
			_pluginPackage.getRequiredDeploymentContexts();

		if (ListUtil.isEmpty(requiredDeploymentContexts)) {
			return;
		}

		StringBundler sb = new StringBundler(
			(6 * requiredDeploymentContexts.size()) - 1);

		for (int i = 0; i < requiredDeploymentContexts.size(); i++) {
			String requiredDeploymentContext = requiredDeploymentContexts.get(
				i);

			sb.append(requiredDeploymentContext);

			sb.append(StringPool.SEMICOLON);
			sb.append(Constants.BUNDLE_VERSION_ATTRIBUTE);
			sb.append(StringPool.EQUAL);
			sb.append(_bundleVersion);

			if ((i + 1) < requiredDeploymentContexts.size()) {
				sb.append(StringPool.COMMA);
			}
		}

		analyzer.setProperty(Constants.REQUIRE_BUNDLE, sb.toString());
	}

	protected void processResourceActionXML() throws IOException {
		File dir = new File(_pluginDir, "WEB-INF/classes");

		URI uri = dir.toURI();

		ClassLoader classLoader = new URLClassLoader(new URL[] {uri.toURL()});

		if (classLoader.getResource("portlet.properties") == null) {
			return;
		}

		Configuration configuration = ConfigurationFactoryUtil.getConfiguration(
			classLoader, "portlet");

		Properties properties = configuration.getProperties();

		for (String xmlFile :
				StringUtil.split(
					properties.getProperty(
						PropsKeys.RESOURCE_ACTIONS_CONFIGS))) {

			processResourceActionXML(dir, xmlFile);
		}
	}

	protected void processResourceActionXML(File dir, String xmlFile)
		throws IOException {

		File file = new File(dir, xmlFile);

		if (!file.exists()) {
			return;
		}

		Document document = readDocument(file);

		Element rootElement = document.getRootElement();

		String servletContextName = _context.substring(1);

		for (Element portletResourceElement :
				rootElement.elements("portlet-resource")) {

			Element portletNameElement = portletResourceElement.element(
				"portlet-name");

			portletNameElement.setText(
				JS.getSafeName(
					StringBundler.concat(
						portletNameElement.getTextTrim(),
						PortletConstants.WAR_SEPARATOR, servletContextName)));
		}

		for (Element modelResourceElement :
				rootElement.elements("model-resource")) {

			Element portletRefElement = modelResourceElement.element(
				"portlet-ref");

			for (Element portletNameElement :
					portletRefElement.elements("portlet-name")) {

				portletNameElement.setText(
					JS.getSafeName(
						StringBundler.concat(
							portletNameElement.getTextTrim(),
							PortletConstants.WAR_SEPARATOR,
							servletContextName)));
			}
		}

		formatDocument(file, document);

		if (!xmlFile.endsWith("-ext.xml")) {
			processResourceActionXML(
				dir, StringUtil.replace(xmlFile, ".xml", "-ext.xml"));
		}

		for (Element resourceFileElement : rootElement.elements("resource")) {
			processResourceActionXML(
				dir,
				StringUtil.trim(resourceFileElement.attributeValue("file")));
		}
	}

	protected void processServicePackageName(Resource resource) {
		try (InputStream inputStream = resource.openInputStream()) {
			Document document = UnsecureSAXReaderUtil.read(inputStream);

			Element rootElement = document.getRootElement();

			_servicePackageName = rootElement.attributeValue("package-path");

			String[] partialPackageNames = {
				"", ".exception", ".model", ".model.impl", ".service",
				".service.base", ".service.http", ".service.impl",
				".service.persistence", ".service.persistence.impl"
			};

			for (String partialPackageName : partialPackageNames) {
				Parameters parameters = new Parameters(
					getVersionedServicePackageName(partialPackageName));

				_exportPackageParameters.mergeWith(parameters, false);
				_importPackageParameters.mergeWith(parameters, false);
			}

			_importPackageParameters.add(
				"com.liferay.portal.osgi.web.wab.generator", _optionalAttrs);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	protected void processTLDDependencies(Analyzer analyzer)
		throws IOException {

		File dir = new File(_pluginDir, "WEB-INF/tld");

		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}

		File[] files = dir.listFiles(
			(File file) -> {
				if (!file.isFile()) {
					return false;
				}

				String fileName = file.getName();

				if (fileName.endsWith(".tld")) {
					return true;
				}

				return false;
			});

		for (File file : files) {
			String content = FileUtil.read(file);

			Matcher matcher = _tldPackagesPattern.matcher(content);

			while (matcher.find()) {
				String value = matcher.group(1);

				value = value.trim();

				processClass(analyzer, value);
			}
		}
	}

	protected void processWebContextPath(Manifest manifest) {
		Attributes attributes = manifest.getMainAttributes();

		attributes.putValue("Web-ContextPath", getWebContextPath());
	}

	protected void processWebXML(
		Element element, List<Element> initParamElements, Class<?> clazz) {

		if (element == null) {
			return;
		}

		String elementText = element.getTextTrim();

		if (!elementText.equals(clazz.getName())) {
			return;
		}

		for (Element initParamElement : initParamElements) {
			Element paramNameElement = initParamElement.element("param-name");

			String paramNameValue = paramNameElement.getTextTrim();

			if (!paramNameValue.equals(element.getName())) {
				continue;
			}

			Element paramValueElement = initParamElement.element("param-value");

			element.setText(paramValueElement.getTextTrim());

			initParamElement.detach();

			return;
		}
	}

	protected void processWebXML(String path) throws IOException {
		File file = new File(_pluginDir, path);

		if (!file.exists()) {
			return;
		}

		Document document = readDocument(file);

		Element rootElement = document.getRootElement();

		for (Element element : rootElement.elements("filter")) {
			Element filterClassElement = element.element("filter-class");

			processWebXML(
				filterClassElement, element.elements("init-param"),
				PortalClassLoaderFilter.class);
		}

		for (Element element : rootElement.elements("servlet")) {
			Element servletClassElement = element.element("servlet-class");

			processWebXML(
				servletClassElement, element.elements("init-param"),
				PortalClassLoaderServlet.class);
		}

		formatDocument(file, document);
	}

	protected void processXMLDependencies(
		Analyzer analyzer, File file, String xPathExpression) {

		if (!file.exists()) {
			return;
		}

		Document document = readDocument(file);

		if (!document.hasContent()) {
			return;
		}

		Element rootElement = document.getRootElement();

		XPath xPath = SAXReaderUtil.createXPath(xPathExpression, _xsds);

		List<Node> nodes = xPath.selectNodes(rootElement);

		for (Node node : nodes) {
			String text = node.getText();

			text = text.trim();

			processClass(analyzer, text);
		}
	}

	protected void processXMLDependencies(
			Analyzer analyzer, Path path, String suffix, String xPathExpression)
		throws IOException {

		File file = path.toFile();

		if (!file.isDirectory()) {
			return;
		}

		Stream<Path> pathStream = Files.walk(path);

		Stream<File> fileStream = pathStream.map(Path::toFile);

		fileStream.forEach(
			entry -> {
				String pathString = entry.getPath();

				if (pathString.endsWith(suffix)) {
					processXMLDependencies(analyzer, entry, _XPATHS_SPRING);
				}
			});
	}

	protected void processXMLDependencies(
		Analyzer analyzer, String fileName, String xPathExpression) {

		File file = new File(_pluginDir, fileName);

		processXMLDependencies(analyzer, file, xPathExpression);
	}

	protected Document readDocument(File file) {
		try {
			String content = FileUtil.read(file);

			return UnsecureSAXReaderUtil.read(content);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return SAXReaderUtil.createDocument();
		}
	}

	protected File transformToOSGiBundle(Jar jar) throws IOException {
		try (Builder analyzer = new Builder()) {
			analyzer.setBase(_pluginDir);
			analyzer.setJar(jar);
			analyzer.setProperty("-jsp", "*.jsp,*.jspf,*.jspx");
			analyzer.setProperty("Web-ContextPath", getWebContextPath());

			List<Object> disabledPlugins = new ArrayList<>();
			Properties properties = PropsUtil.getProperties(
				"module.framework.web.generator.bnd.plugin.enabled[", true);

			Set<Object> plugins = analyzer.getPlugins();

			for (Object plugin : plugins) {
				if (plugin instanceof DSAnnotations ||
					plugin instanceof ServiceComponent) {

					disabledPlugins.add(plugin);

					continue;
				}

				Class<?> clazz = plugin.getClass();

				String name = clazz.getName() + "]";

				if (!GetterUtil.getBoolean(
						properties.getProperty(name), true)) {

					disabledPlugins.add(plugin);
				}
			}

			plugins.removeAll(disabledPlugins);

			plugins.add(new JspAnalyzerPlugin());

			Properties pluginPackageProperties = getPluginPackageProperties();

			if (pluginPackageProperties.containsKey("portal-dependency-jars") &&
				_log.isWarnEnabled()) {

				_log.warn(
					"The property \"portal-dependency-jars\" is deprecated. " +
						"Specified JARs may not be included in the class " +
							"path.");
			}

			processBundleVersion(analyzer);
			processBundleClasspath(analyzer, pluginPackageProperties);
			processBundleSymbolicName(analyzer);
			processExtraHeaders(analyzer);
			processPluginPackagePropertiesExportImportPackages(
				pluginPackageProperties);

			processBundleManifestVersion(analyzer);

			processLiferayPortletXML();
			processWebXML("WEB-INF/web.xml");
			processWebXML("WEB-INF/liferay-web.xml");

			processResourceActionXML();

			processDeclarativeReferences(analyzer);

			processExtraRequirements();

			processPackageNames(analyzer);

			processRequiredDeploymentContexts(analyzer);

			_processExcludedJSPs(analyzer);

			analyzer.setProperties(pluginPackageProperties);

			processBeans(analyzer);

			try {
				jar = analyzer.build();

				File outputFile = analyzer.getOutputFile(null);

				jar.write(outputFile);

				return outputFile;
			}
			catch (Exception exception) {
				throw new IOException(
					"Unable to calculate the manifest", exception);
			}
		}
	}

	protected void writeAutoDeployedWar(File pluginDir) {
		File dir = new File(
			PropsValues.
				MODULE_FRAMEWORK_WEB_GENERATOR_GENERATED_WABS_STORE_DIR);

		dir.mkdirs();

		StringBundler sb = new StringBundler(5);

		String name = _file.getName();

		sb.append(name.substring(0, name.lastIndexOf(StringPool.PERIOD)));

		sb.append(StringPool.DASH);

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			PropsValues.INDEX_DATE_FORMAT_PATTERN);

		sb.append(format.format(new Date()));

		sb.append(".autodeployed.");

		sb.append(FileUtil.getExtension(name));

		try (Jar jar = new Jar(pluginDir)) {
			jar.write(new File(dir, sb.toString()));
		}
		catch (Exception exception) {
			_log.error("Unable to write JAR file for " + pluginDir, exception);
		}
	}

	protected void writeGeneratedWab(File file) throws IOException {
		File dir = new File(
			PropsValues.
				MODULE_FRAMEWORK_WEB_GENERATOR_GENERATED_WABS_STORE_DIR);

		dir.mkdirs();

		StringBundler sb = new StringBundler(5);

		String name = _file.getName();

		sb.append(name.substring(0, name.lastIndexOf(StringPool.PERIOD)));

		sb.append(StringPool.DASH);

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			PropsValues.INDEX_DATE_FORMAT_PATTERN);

		sb.append(format.format(new Date()));

		sb.append(".wab.");

		sb.append(FileUtil.getExtension(name));

		FileUtil.copyFile(file, new File(dir, sb.toString()));
	}

	private Discover _findDiscoveryMode(Document document) {
		if (!document.hasContent()) {
			return Discover.all;
		}

		Element rootElement = document.getRootElement();

		// bean-discovery-mode="all" version="1.1"

		XPath xPath = SAXReaderUtil.createXPath(
			"/cdi-beans:beans/@version", _xsds);

		Node versionNode = xPath.selectSingleNode(rootElement);

		if (versionNode == null) {
			return Discover.all;
		}

		Version version = Version.valueOf(versionNode.getStringValue());

		if (_CDI_ARCHIVE_VERSION.compareTo(version) <= 0) {
			xPath = SAXReaderUtil.createXPath(
				"/cdi-beans:beans/@bean-discovery-mode", _xsds);

			Node beanDiscoveryModeNode = xPath.selectSingleNode(rootElement);

			if (beanDiscoveryModeNode == null) {
				return Discover.annotated;
			}

			return Discover.valueOf(beanDiscoveryModeNode.getStringValue());
		}

		return Discover.all;
	}

	private void _processExcludedJSPs(Analyzer analyzer) {
		File file = new File(_pluginDir, "/WEB-INF/liferay-hook.xml");

		if (!file.exists()) {
			return;
		}

		Document document = readDocument(file);

		if (!document.hasContent()) {
			return;
		}

		Element rootElement = document.getRootElement();

		List<Node> nodes = rootElement.selectNodes("//custom-jsp-dir");

		String value = analyzer.getProperty("-jsp");

		for (Node node : nodes) {
			String text = node.getText();

			if (text.startsWith("/")) {
				text = text.substring(1);
			}

			value = StringBundler.concat("!", text, "/*,", value);
		}

		analyzer.setProperty("-jsp", value);
	}

	/**
	 * Used diagnostic testing.
	 */
	private static final boolean _AUTODEPLOYED_WARS_STORE =
		GetterUtil.getBoolean(
			PropsUtil.get(
				"module.framework.web.generator.autodeployed.wars.store"));

	private static final Version _CDI_ARCHIVE_VERSION = new Version(1, 1, 0);

	private static final String _CDI_REQUIREMENTS = StringBundler.concat(
		"osgi.cdi.extension;filter:='(osgi.cdi.extension=aries.cdi.http)',",
		"osgi.cdi.extension;filter:='(osgi.cdi.extension=aries.cdi.el.jsp)',",
		"osgi.cdi.extension;filter:='(osgi.cdi.extension=",
		"com.liferay.bean.portlet.cdi.extension)'");

	private static final String[] _KNOWN_PROPERTY_KEYS = {
		"jdbc.driverClassName"
	};

	private static final String _XPATHS_HBM = StringUtil.merge(
		new String[] {
			"//class/@name", "//id/@access", "//import/@class",
			"//property/@type"
		},
		"|");

	private static final String _XPATHS_HOOK = StringUtil.merge(
		new String[] {
			"//indexer-post-processor-impl", "//service-impl",
			"//servlet-filter-impl", "//struts-action-impl"
		},
		"|");

	private static final String _XPATHS_JAVAEE = StringUtil.merge(
		new String[] {
			"//j2ee:filter-class", "//j2ee:listener-class",
			"//j2ee:servlet-class", "//javaee:filter-class",
			"//javaee:listener-class", "//javaee:servlet-class"
		},
		"|");

	private static final String _XPATHS_LIFERAY = StringUtil.merge(
		new String[] {
			"//asset-renderer-factory", "//atom-collection-adapter",
			"//configuration-action-class", "//control-panel-entry-class",
			"//custom-attributes-display", "//friendly-url-mapper-class",
			"//indexer-class", "//open-search-class", "//permission-propagator",
			"//poller-processor-class", "//pop-message-listener-class",
			"//portlet-data-handler-class", "//portlet-layout-listener-class",
			"//portlet-url-class", "//social-activity-interpreter-class",
			"//social-request-interpreter-class", "//url-encoder-class",
			"//webdav-storage-class", "//workflow-handler",
			"//xml-rpc-method-class"
		},
		"|");

	private static final String _XPATHS_PORTLET = StringUtil.merge(
		new String[] {
			"//portlet2:filter-class", "//portlet2:listener-class",
			"//portlet2:portlet-class", "//portlet2:resource-bundle"
		},
		"|");

	private static final String _XPATHS_SPRING = StringUtil.merge(
		new String[] {
			"//beans:bean/@class", "//beans:*/@value-type",
			"//aop:*/@implement-interface", "//aop:*/@default-impl",
			"//context:load-time-weaver/@weaver-class",
			"//jee:jndi-lookup/@expected-type",
			"//jee:jndi-lookup/@proxy-interface", "//jee:remote-slsb/@ejbType",
			"//jee:*/@business-interface", "//lang:*/@script-interfaces",
			"//osgi:*/@interface", "//gemini-blueprint:*/@interface",
			"//blueprint:*/@interface", "//blueprint:*/@class",
			"//util:list/@list-class", "//util:set/@set-class",
			"//util:map/@map-class", "//webflow-config:*/@class"
		},
		"|");

	private static final Log _log = LogFactoryUtil.getLog(WabProcessor.class);

	private static final Attrs _optionalAttrs = new Attrs() {
		{
			put("resolution:", "optional");
		}
	};
	private static final Pattern _tldPackagesPattern = Pattern.compile(
		"<[^>]+?-class>\\p{Space}*?(.*?)\\p{Space}*?</[^>]+?-class>");
	private static final Pattern _versionMavenPattern = Pattern.compile(
		"(\\d{1,9})(\\.(\\d{1,9})(\\.(\\d{1,9})(-([-_\\da-zA-Z]+))?)?)?");
	private static final Map<String, String> _xsds =
		ConcurrentHashMapBuilder.put(
			"aop", "http://www.springframework.org/schema/aop"
		).put(
			"beans", "http://www.springframework.org/schema/beans"
		).put(
			"blueprint", "http://www.osgi.org/xmlns/blueprint/v1.0.0"
		).put(
			"cdi-beans", "http://xmlns.jcp.org/xml/ns/javaee"
		).put(
			"context", "http://www.springframework.org/schema/context"
		).put(
			"gemini-blueprint",
			"http://www.eclipse.org/gemini/blueprint/schema/blueprint"
		).put(
			"j2ee", "http://java.sun.com/xml/ns/j2ee"
		).put(
			"javaee", "http://java.sun.com/xml/ns/javaee"
		).put(
			"jee", "http://www.springframework.org/schema/jee"
		).put(
			"jms", "http://www.springframework.org/schema/jms"
		).put(
			"lang", "http://www.springframework.org/schema/lang"
		).put(
			"osgi", "http://www.springframework.org/schema/osgi"
		).put(
			"osgi-compendium",
			"http://www.springframework.org/schema/osgi-compendium"
		).put(
			"portlet2", "http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd"
		).put(
			"tool", "http://www.springframework.org/schema/tool"
		).put(
			"tx", "http://www.springframework.org/schema/tx"
		).put(
			"util", "http://www.springframework.org/schema/util"
		).put(
			"webflow-config",
			"http://www.springframework.org/schema/webflow-config"
		).put(
			"xsl", "http://www.w3.org/1999/XSL/Transform"
		).build();

	private String _bundleVersion;
	private String _context;
	private final Parameters _exportPackageParameters = new Parameters();
	private final File _file;
	private final Set<String> _ignoredResourcePaths = SetUtil.fromArray(
		PropsValues.MODULE_FRAMEWORK_WEB_GENERATOR_EXCLUDED_PATHS);
	private final Parameters _importPackageParameters = new Parameters();
	private final Map<String, String[]> _parameters;
	private File _pluginDir;
	private PluginPackage _pluginPackage;
	private String _servicePackageName;

}
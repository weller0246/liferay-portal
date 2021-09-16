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

package com.liferay.gradle.plugins.workspace.configurators;

import com.liferay.ant.bnd.metatype.MetatypePlugin;
import com.liferay.gradle.plugins.JspCDefaultsPlugin;
import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.extensions.LiferayOSGiExtension;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorPlugin;
import com.liferay.gradle.plugins.js.transpiler.JSTranspilerBasePlugin;
import com.liferay.gradle.plugins.js.transpiler.JSTranspilerPlugin;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.rest.builder.RESTBuilderPlugin;
import com.liferay.gradle.plugins.service.builder.ServiceBuilderPlugin;
import com.liferay.gradle.plugins.soy.SoyPlugin;
import com.liferay.gradle.plugins.soy.SoyTranslationPlugin;
import com.liferay.gradle.plugins.test.integration.TestIntegrationBasePlugin;
import com.liferay.gradle.plugins.test.integration.TestIntegrationPlugin;
import com.liferay.gradle.plugins.upgrade.table.builder.UpgradeTableBuilderPlugin;
import com.liferay.gradle.plugins.util.BndUtil;
import com.liferay.gradle.plugins.workspace.FrontendPlugin;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.JSModuleConfigGeneratorDefaultsPlugin;
import com.liferay.gradle.plugins.workspace.internal.util.FileUtil;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;

import groovy.json.JsonSlurper;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.CopySourceSpec;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DeleteSpec;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.jvm.tasks.Jar;
import org.gradle.language.base.plugins.LifecycleBasePlugin;

import org.osgi.framework.Constants;

/**
 * @author Andrea Di Giorgi
 * @author David Truong
 * @author Gregory Amerson
 */
public class ModulesProjectConfigurator extends BaseProjectConfigurator {

	public ModulesProjectConfigurator(Settings settings) {
		super(settings);

		_defaultRepositoryEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + NAME +
				".default.repository.enabled",
			_DEFAULT_REPOSITORY_ENABLED);
		_jspPrecompileEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + NAME + ".jsp.precompile.enabled",
			_DEFAULT_JSP_PRECOMPILE_ENABLED);
	}

	@Override
	public void apply(Project project) {
		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		Object jarSourcePath = null;

		File bndBndFile = project.file("bnd.bnd");
		File buildGradleFile = project.file("build.gradle");
		File pomXmlFile = project.file("pom.xml");

		if (bndBndFile.exists() &&
			(buildGradleFile.exists() || pomXmlFile.exists())) {

			if (!project.hasProperty(
					JspCDefaultsPlugin.COMPILE_JSP_INCLUDE_PROPERTY_NAME)) {

				ExtensionContainer extensionContainer = project.getExtensions();

				ExtraPropertiesExtension extraPropertiesExtension =
					extensionContainer.getExtraProperties();

				extraPropertiesExtension.set(
					JspCDefaultsPlugin.COMPILE_JSP_INCLUDE_PROPERTY_NAME,
					isJspPrecompileEnabled());
			}

			GradleUtil.applyPlugin(project, LiferayOSGiPlugin.class);
			GradleUtil.applyPlugin(project, RESTBuilderPlugin.class);
			GradleUtil.applyPlugin(project, ServiceBuilderPlugin.class);
			GradleUtil.applyPlugin(project, SoyPlugin.class);
			GradleUtil.applyPlugin(project, SoyTranslationPlugin.class);
			GradleUtil.applyPlugin(project, UpgradeTableBuilderPlugin.class);
			GradleUtil.applyPlugin(project, WSDDBuilderPlugin.class);

			if (GradleUtil.hasTask(
					project, NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME)) {

				GradleUtil.applyPlugin(project, JSTranspilerBasePlugin.class);
			}
			else {
				GradleUtil.applyPlugin(
					project, JSModuleConfigGeneratorPlugin.class);
				GradleUtil.applyPlugin(project, JSTranspilerPlugin.class);
			}

			JSModuleConfigGeneratorDefaultsPlugin.INSTANCE.apply(project);

			Jar jar = (Jar)GradleUtil.getTask(
				project, JavaPlugin.JAR_TASK_NAME);

			_configureLiferayOSGi(project);

			_configureRootTaskDistBundle(jar);

			project.afterEvaluate(
				new Action<Project>() {

					@Override
					public void execute(Project project) {
						_configureTaskTestIntegration(project);
					}

				});

			jarSourcePath = jar;
		}
		else {
			File packageJsonFile = project.file("package.json");

			if (packageJsonFile.exists() &&
				_hasJsPortletBuildScript(packageJsonFile.toPath())) {

				GradleUtil.applyPlugin(project, FrontendPlugin.class);

				final Task buildTask = GradleUtil.getTask(
					project, LifecycleBasePlugin.BUILD_TASK_NAME);

				_configureRootTaskDistBundle(buildTask);

				jarSourcePath = new Callable<ConfigurableFileCollection>() {

					@Override
					public ConfigurableFileCollection call() throws Exception {
						Project project = buildTask.getProject();

						ConfigurableFileCollection configurableFileCollection =
							project.files(_getJarFile(project));

						return configurableFileCollection.builtBy(buildTask);
					}

				};
			}
		}

		final BundleExtension bundleExtension = BndUtil.getBundleExtension(
			project.getExtensions());

		final WorkspaceExtension workspaceExtension = _getWorkspaceExtension(
			project);

		configureLiferay(project, workspaceExtension);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					TaskContainer taskContainer = project.getTasks();

					Task deployFastTask = taskContainer.findByName(
						LiferayOSGiPlugin.DEPLOY_FAST_TASK_NAME);

					if (deployFastTask != null) {
						_configureTaskDeployFast(
							(Copy)deployFastTask, bundleExtension,
							workspaceExtension);
					}

					Task setUpTestableTomcatTask = taskContainer.findByName(
						TestIntegrationPlugin.SET_UP_TESTABLE_TOMCAT_TASK_NAME);

					if (setUpTestableTomcatTask != null) {
						_configureTaskSetUpTestableTomcat(
							setUpTestableTomcatTask, workspaceExtension);
					}
				}

			});

		addTaskDockerDeploy(project, jarSourcePath, workspaceExtension);
	}

	@Override
	public String getName() {
		return NAME;
	}

	public boolean isDefaultRepositoryEnabled() {
		return _defaultRepositoryEnabled;
	}

	public boolean isJspPrecompileEnabled() {
		return _jspPrecompileEnabled;
	}

	public void setDefaultRepositoryEnabled(boolean defaultRepositoryEnabled) {
		_defaultRepositoryEnabled = defaultRepositoryEnabled;
	}

	public void setJspPrecompileEnabled(boolean jspPrecompileEnabled) {
		_jspPrecompileEnabled = jspPrecompileEnabled;
	}

	@Override
	protected Iterable<File> doGetProjectDirs(File rootDir) throws Exception {
		final Set<File> projectDirs = new HashSet<>();

		Files.walkFileTree(
			rootDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (Files.exists(dirPath.resolve("bnd.bnd"))) {
						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					String dirName = String.valueOf(dirPath.getFileName());

					if (isExcludedDirName(dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path packageJsonPath = dirPath.resolve("package.json");

					if (Files.exists(packageJsonPath) &&
						_hasJsPortletBuildScript(packageJsonPath)) {

						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

	protected static final String NAME = "modules";

	private void _configureLiferayOSGi(Project project) {
		LiferayOSGiExtension liferayOSGiExtension = GradleUtil.getExtension(
			project, LiferayOSGiExtension.class);

		Map<String, String> bundleDefaultInstructions = new HashMap<>();

		bundleDefaultInstructions.put(
			"-plugin.metatype", MetatypePlugin.class.getName());

		liferayOSGiExtension.bundleDefaultInstructions(
			bundleDefaultInstructions);
	}

	@SuppressWarnings("serial")
	private void _configureRootTaskDistBundle(final Jar jar) {
		Project project = jar.getProject();

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		copy.into(
			"osgi/modules",
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySourceSpec copySourceSpec) {
					copySourceSpec.from(jar);
				}

			});
	}

	private void _configureRootTaskDistBundle(Task buildTask) {
		Project project = buildTask.getProject();

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		copy.dependsOn(buildTask);

		copy.into("osgi/modules", _copyJarClosure(project, buildTask));
	}

	private void _configureTaskDeployFast(
		Copy deployFastTask, BundleExtension bundleExtension,
		WorkspaceExtension workspaceExtension) {

		Project project = deployFastTask.getProject();

		String bundleSymbolicName = bundleExtension.getInstruction(
			Constants.BUNDLE_SYMBOLICNAME);
		String bundleVersion = bundleExtension.getInstruction(
			Constants.BUNDLE_VERSION);

		StringBuilder sb = new StringBuilder();

		sb.append("work/");
		sb.append(bundleSymbolicName);
		sb.append("-");
		sb.append(bundleVersion);

		final String pathName = sb.toString();

		File dockerWorkDir = new File(
			workspaceExtension.getDockerDir(), pathName);

		deployFastTask.setDestinationDir(workspaceExtension.getHomeDir());

		deployFastTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					project.sync(
						new Action<CopySpec>() {

							@Override
							public void execute(CopySpec copySpec) {
								copySpec.from(
									new File(
										deployFastTask.getDestinationDir(),
										pathName));
								copySpec.into(dockerWorkDir);
							}

						});
				}

			});

		Task cleanTask = GradleUtil.getTask(
			project, LifecycleBasePlugin.CLEAN_TASK_NAME);

		cleanTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					project.delete(
						new Action<DeleteSpec>() {

							@Override
							public void execute(DeleteSpec deleteSpec) {
								deleteSpec.delete(dockerWorkDir);
							}

						});
				}

			});
	}

	private void _configureTaskSetUpTestableTomcat(
		Task setUpTestableTomcatTask, WorkspaceExtension workspaceExtension) {

		File homeDir = workspaceExtension.getHomeDir();

		if (!homeDir.exists()) {
			Project project = setUpTestableTomcatTask.getProject();

			Task initBundleTask = GradleUtil.getTask(
				project.getRootProject(),
				RootProjectConfigurator.INIT_BUNDLE_TASK_NAME);

			setUpTestableTomcatTask.dependsOn(initBundleTask);
		}
	}

	private void _configureTaskTestIntegration(Project project) {
		final File testClassesIntegrationDir = project.file(
			"test-classes/integration");
		Task testIntegrationClassesTask = GradleUtil.getTask(
			project,
			TestIntegrationBasePlugin.TEST_INTEGRATION_TASK_NAME + "Classes");

		testIntegrationClassesTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					project.sync(
						new Action<CopySpec>() {

							@Override
							public void execute(CopySpec copySpec) {
								SourceSet sourceSet = GradleUtil.getSourceSet(
									project,
									TestIntegrationBasePlugin.
										TEST_INTEGRATION_SOURCE_SET_NAME);

								copySpec.from(
									FileUtil.getJavaClassesDir(sourceSet));
								copySpec.from(_getResourcesDir(sourceSet));

								copySpec.into(testClassesIntegrationDir);
							}

						});
				}

			});

		Delete deleteCleanTask = (Delete)GradleUtil.getTask(
			project, LifecycleBasePlugin.CLEAN_TASK_NAME);

		deleteCleanTask.delete(testClassesIntegrationDir.getParentFile());
	}

	@SuppressWarnings({"rawtypes", "serial", "unused"})
	private Closure _copyJarClosure(Project project, final Task assembleTask) {
		return new Closure<Void>(project) {

			public void doCall(CopySpec copySpec) {
				Project project = assembleTask.getProject();

				File jarFile = _getJarFile(project);

				ConfigurableFileCollection configurableFileCollection =
					project.files(jarFile);

				configurableFileCollection.builtBy(assembleTask);

				copySpec.from(jarFile);
			}

		};
	}

	private File _getJarFile(Project project) {
		return project.file(
			"dist/" + GradleUtil.getArchivesBaseName(project) + "-" +
				project.getVersion() + ".jar");
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _getPackageJsonMap(File packageJsonFile) {
		if (!packageJsonFile.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(packageJsonFile);
	}

	private File _getResourcesDir(SourceSet sourceSet) {
		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		return sourceSetOutput.getResourcesDir();
	}

	private WorkspaceExtension _getWorkspaceExtension(Project project) {
		return GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);
	}

	@SuppressWarnings("unchecked")
	private boolean _hasJsPortletBuildScript(Path packageJsonPath) {
		Map<String, Object> packageJsonMap = _getPackageJsonMap(
			packageJsonPath.toFile());

		Map<String, Object> liferayTheme =
			(Map<String, Object>)packageJsonMap.get("liferayTheme");
		Map<String, Object> scripts = (Map<String, Object>)packageJsonMap.get(
			"scripts");

		if ((liferayTheme == null) && (scripts != null) &&
			(scripts.get("build") != null)) {

			return true;
		}

		return false;
	}

	private static final boolean _DEFAULT_JSP_PRECOMPILE_ENABLED = false;

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private boolean _defaultRepositoryEnabled;
	private boolean _jspPrecompileEnabled;

}
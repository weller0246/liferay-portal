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

package com.liferay.portal.k8s.agent.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.k8s.agent.PortalK8sConfigMapModifier;
import com.liferay.portal.k8s.agent.configuration.v1.PortalK8sAgentConfiguration;
import com.liferay.portal.k8s.agent.mutator.PortalK8sConfigurationPropertiesMutator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Http;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerEventListener;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;

import java.net.URL;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.felix.configurator.impl.json.BinUtil;
import org.apache.felix.configurator.impl.json.BinaryManager;
import org.apache.felix.configurator.impl.json.JSONUtil;
import org.apache.felix.configurator.impl.model.ConfigurationFile;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.k8s.agent.configuration.v1.PortalK8sAgentConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "portalK8sConfigurationPropertiesMutators.cardinality.minimum:Integer=3",
	service = PortalK8sConfigMapModifier.class
)
public class PortalK8sAgentImpl implements PortalK8sConfigMapModifier {

	@Activate
	public PortalK8sAgentImpl(
			BundleContext bundleContext,
			@Reference ConfigurationAdmin configurationAdmin,
			@Reference(
				name = "portalK8sConfigurationPropertiesMutators",
				policyOption = ReferencePolicyOption.GREEDY
			)
			List
				<PortalK8sConfigurationPropertiesMutator>
					portalK8sConfigurationPropertiesMutators,
			Map<String, Object> properties)
		throws Exception {

		_configurationAdmin = configurationAdmin;
		_portalK8sConfigurationPropertiesMutators =
			portalK8sConfigurationPropertiesMutators;

		_portalK8sAgentConfiguration = ConfigurableUtil.createConfigurable(
			PortalK8sAgentConfiguration.class, properties);

		_bundle = bundleContext.getBundle();

		if (_log.isInfoEnabled()) {
			_log.info("Initializing K8s agent with: " + properties);
		}

		Config config = Config.empty();

		String protocol = Http.HTTP;

		if (_portalK8sAgentConfiguration.apiServerSSL()) {
			protocol = Http.HTTPS;
		}

		String apiServerHost = _portalK8sAgentConfiguration.apiServerHost();
		int apiServerPort = _portalK8sAgentConfiguration.apiServerPort();

		String apiServerAddress = StringBundler.concat(
			protocol, Http.PROTOCOL_DELIMITER, apiServerHost, StringPool.COLON,
			apiServerPort, StringPool.SLASH);

		String caCertData = _portalK8sAgentConfiguration.caCertData();
		String namespace = _portalK8sAgentConfiguration.namespace();
		String saToken = _portalK8sAgentConfiguration.saToken();

		config.setCaCertData(caCertData);
		config.setMasterUrl(apiServerAddress);
		config.setNamespace(namespace);
		config.setOauthToken(saToken);

		Map<Integer, String> errorMessages = config.getErrorMessages();

		errorMessages.put(401, _ERROR_MESSAGE);
		errorMessages.put(403, _ERROR_MESSAGE);

		Config.configFromSysPropsOrEnvVars(config);

		_kubernetesClient = new DefaultKubernetesClient(config);

		SharedInformerFactory sharedInformerFactory =
			_kubernetesClient.informers();

		sharedInformerFactory.addSharedInformerEventListener(
			new SharedInformerEventListener() {

				@Override
				public void onException(Exception exception) {
					_log.error(exception);
				}

			});

		_sharedIndexInformer = _kubernetesClient.configMaps(
		).inNamespace(
			_portalK8sAgentConfiguration.namespace()
		).withLabel(
			_portalK8sAgentConfiguration.labelSelector()
		).inform(
			new ResourceEventHandler<ConfigMap>() {

				@Override
				public void onAdd(ConfigMap configMap) {
					_add(configMap);
				}

				@Override
				public void onDelete(
					ConfigMap configMap, boolean deletedFinalStateUnknown) {

					_delete(configMap);
				}

				@Override
				public void onUpdate(
					ConfigMap oldConfigMap, ConfigMap newConfigMap) {

					_update(oldConfigMap, newConfigMap);
				}

			}
		);

		if (_log.isDebugEnabled()) {
			_log.debug("Initialized K8s agent");
		}
	}

	@Override
	public Result modifyConfigMap(
		Consumer<Map<String, String>> configMapDataConsumer, String serviceId) {

		Objects.requireNonNull(configMapDataConsumer, "must not be null");
		Objects.requireNonNull(serviceId, "must not be null");

		String configMapName = serviceId.concat("-lxc-ext-init-metadata");

		ConfigMap configMap = _kubernetesClient.configMaps(
		).inNamespace(
			_portalK8sAgentConfiguration.namespace()
		).withName(
			configMapName
		).get();

		if (configMap != null) {
			Map<String, String> data = configMap.getData();

			Map<String, String> originalCopy = new TreeMap<>(data);

			configMapDataConsumer.accept(data);

			if (data.isEmpty()) {
				_kubernetesClient.configMaps(
				).delete(
					configMap
				);

				if (_log.isDebugEnabled()) {
					_log.debug("Deleted ".concat(configMap.toString()));
				}

				return Result.DELETED;
			}
			else if (!Objects.equals(data, originalCopy)) {
				configMap = _kubernetesClient.configMaps(
				).withName(
					configMapName
				).createOrReplace(
					configMap
				);

				if (_log.isDebugEnabled()) {
					_log.debug("Updated ".concat(configMap.toString()));
				}

				return Result.UPDATED;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Unchanged ".concat(configMap.toString()));
				}

				return Result.UNCHANGED;
			}
		}
		else {
			Map<String, String> data = new TreeMap<>();

			configMapDataConsumer.accept(data);

			configMap = new ConfigMapBuilder().withNewMetadata(
			).withNamespace(
				_portalK8sAgentConfiguration.namespace()
			).withName(
				configMapName
			).endMetadata(
			).addToData(
				data
			).build();

			configMap = _kubernetesClient.configMaps(
			).withName(
				configMapName
			).createOrReplace(
				configMap
			);

			if (_log.isDebugEnabled()) {
				_log.debug("Created ".concat(configMap.toString()));
			}

			return Result.CREATED;
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isDebugEnabled()) {
			_log.debug("Deactivating K8s agent");
		}

		_sharedIndexInformer.close();

		_kubernetesClient.close();

		if (_log.isDebugEnabled()) {
			_log.debug("Deactivated K8s agent");
		}
	}

	private void _add(ConfigMap configMap) {
		if (_log.isDebugEnabled()) {
			_log.debug("Adding config map " + configMap.toString());
		}

		Map<String, String> data = configMap.getData();

		if (data == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Data is null for config map " + configMap);
			}

			return;
		}

		for (Map.Entry<String, String> entry : data.entrySet()) {
			try {
				_processConfigurations(
					configMap, entry.getKey(), entry.getValue());
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private void _delete(ConfigMap configMap) {
		if (_log.isDebugEnabled()) {
			_log.debug("Deleting config map " + configMap);
		}

		Map<String, String> data = configMap.getData();

		if (data == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Data is null for config map " + configMap);
			}

			return;
		}

		ObjectMeta metadata = configMap.getMetadata();

		String configurationFilter = StringBundler.concat(
			"(.k8s.config.uid=", metadata.getUid(), ")");

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(configurationFilter);

			if (configurations != null) {
				for (Configuration configuration : configurations) {
					try {
						configuration.delete();
					}
					catch (Exception exception) {
						_log.error(exception);
					}
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private Configuration _getConfiguration(
			org.apache.felix.configurator.impl.model.Config config)
		throws Exception {

		String pid = config.getPid();

		if (pid.endsWith(_FILE_JSON_EXT)) {
			pid = pid.substring(0, pid.length() - _FILE_JSON_EXT.length());
		}

		int index = pid.indexOf(CharPool.TILDE);

		if (index <= 0) {
			index = pid.indexOf(CharPool.UNDERLINE);

			if (index <= 0) {
				index = pid.indexOf(CharPool.DASH);
			}
		}

		if (index > 0) {
			String name = pid.substring(index + 1);

			pid = pid.substring(0, index);

			return _configurationAdmin.getFactoryConfiguration(
				pid, name, StringPool.QUESTION);
		}

		return _configurationAdmin.getConfiguration(pid, StringPool.QUESTION);
	}

	private void _processConfiguration(
			org.apache.felix.configurator.impl.model.Config config,
			ObjectMeta objectMeta)
		throws Exception {

		Configuration configuration = null;

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat("(.k8s.config.key=", config.getPid(), ")"));

		if (ArrayUtil.isNotEmpty(configurations)) {
			configuration = configurations[0];

			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (Objects.equals(
					properties.get(".k8s.config.resource.version"),
					objectMeta.getResourceVersion())) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Configuration and Kubernetes resource versions are " +
							"identical");
				}

				return;
			}
		}
		else {
			configuration = _getConfiguration(config);
		}

		Set<Configuration.ConfigurationAttribute> configurationAttributes =
			configuration.getAttributes();

		if (configurationAttributes.contains(
				Configuration.ConfigurationAttribute.READ_ONLY)) {

			configuration.removeAttributes(
				Configuration.ConfigurationAttribute.READ_ONLY);
		}

		Dictionary<String, Object> properties = config.getProperties();

		for (PortalK8sConfigurationPropertiesMutator
				portalK8sConfigurationPropertiesMutator :
					_portalK8sConfigurationPropertiesMutators) {

			portalK8sConfigurationPropertiesMutator.
				mutateConfigurationProperties(
					objectMeta.getAnnotations(), objectMeta.getLabels(),
					properties);
		}

		properties.put(".k8s.config.key", config.getPid());
		properties.put(
			".k8s.config.resource.version", objectMeta.getResourceVersion());
		properties.put(".k8s.config.uid", objectMeta.getUid());

		if (_log.isDebugEnabled()) {
			_log.debug("Processed configuration " + properties);
		}

		configuration.updateIfDifferent(properties);

		configuration.addAttributes(
			Configuration.ConfigurationAttribute.READ_ONLY);
	}

	private void _processConfigurations(
			ConfigMap configMap, String fileName, String json)
		throws Exception {

		if (!fileName.endsWith(_FILE_JSON_EXT)) {
			throw new IllegalArgumentException("Invalid file " + fileName);
		}

		JSONUtil.Report report = new JSONUtil.Report();

		BinaryManager binaryManager = new BinaryManager(
			new BinUtil.ResourceProvider() {

				@Override
				public Enumeration<URL> findEntries(
					String path, String pattern) {

					return Collections.emptyEnumeration();
				}

				@Override
				public long getBundleId() {
					return _bundle.getBundleId();
				}

				@Override
				public URL getEntry(String path) {
					return null;
				}

				@Override
				public String getIdentifier() {
					return fileName;
				}

			},
			report);

		ConfigurationFile configurationFile = JSONUtil.readJSON(
			binaryManager, fileName, new URL("file", null, fileName),
			_bundle.getBundleId(), json, report);

		for (String error : report.errors) {
			_log.error(error);
		}

		for (String warning : report.warnings) {
			if (_log.isWarnEnabled()) {
				_log.warn(warning);
			}
		}

		if (configurationFile == null) {
			return;
		}

		for (org.apache.felix.configurator.impl.model.Config config :
				configurationFile.getConfigurations()) {

			try {
				_processConfiguration(config, configMap.getMetadata());
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private void _update(ConfigMap oldConfigMap, ConfigMap newConfigMap) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Updating config map ", oldConfigMap, " to ",
					newConfigMap));
		}

		Map<String, String> data = newConfigMap.getData();
		ObjectMeta objectMeta = newConfigMap.getMetadata();

		if (data != null) {
			for (Map.Entry<String, String> entry : data.entrySet()) {
				try {
					_processConfigurations(
						newConfigMap, entry.getKey(), entry.getValue());
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}
		}

		Configuration[] configurations = null;

		try {
			ObjectMeta oldObjectMeta = oldConfigMap.getMetadata();

			configurations = _configurationAdmin.listConfigurations(
				StringBundler.concat(
					"(&(.k8s.config.resource.version=",
					oldObjectMeta.getResourceVersion(), ")(.k8s.config.uid=",
					objectMeta.getUid(), "))"));
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			try {
				configuration.delete();
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private static final String _ERROR_MESSAGE =
		"Configured service account does not have access. Service account " +
			"may have been revoked.";

	private static final String _FILE_JSON_EXT = ".config.json";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalK8sAgentImpl.class);

	private final Bundle _bundle;
	private final ConfigurationAdmin _configurationAdmin;
	private final KubernetesClient _kubernetesClient;
	private final PortalK8sAgentConfiguration _portalK8sAgentConfiguration;
	private final List<PortalK8sConfigurationPropertiesMutator>
		_portalK8sConfigurationPropertiesMutators;
	private final SharedIndexInformer<ConfigMap> _sharedIndexInformer;

}
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

package com.liferay.portal.k8s.agent.internal.mutator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.k8s.agent.mutator.PortalK8sConfigurationPropertiesMutator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true, service = PortalK8sConfigurationPropertiesMutator.class
)
@ServiceRanking(1900)
public class AnnotationsPortalK8sConfigurationPropertiesMutator
	implements PortalK8sConfigurationPropertiesMutator {

	@Override
	public void mutateConfigurationProperties(
		Map<String, String> annotations, Map<String, String> labels,
		Dictionary<String, Object> properties) {

		try {
			ObjectMapper objectMapper = new ObjectMapper();

			JsonFactory jsonFactory = objectMapper.getFactory();

			JsonNode jsonNode = objectMapper.readTree(
				jsonFactory.createParser(
					annotations.get("cloud.liferay.com/context-data")));

			JsonNode environmentJsonNode = jsonNode.get("environment");

			String environment = "default";

			if (environmentJsonNode != null) {
				environment = environmentJsonNode.textValue();
			}

			properties.put("k8s.lxc.environment", environment);

			List<String> domains = new ArrayList<>();

			JsonNode domainsJsonNode = jsonNode.get("domains");

			if ((domainsJsonNode != null) && domainsJsonNode.isArray()) {
				for (int i = 0; i < domainsJsonNode.size(); i++) {
					JsonNode entryJsonNode = domainsJsonNode.get(i);

					domains.add(entryJsonNode.textValue());

					if (i == 0) {
						properties.put(
							"com.liferay.lxc.ext.mainDomain",
							"https://".concat(entryJsonNode.textValue()));
					}
				}
			}

			properties.put(
				"com.liferay.lxc.ext.domains", domains.toArray(new String[0]));
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnnotationsPortalK8sConfigurationPropertiesMutator.class);

}
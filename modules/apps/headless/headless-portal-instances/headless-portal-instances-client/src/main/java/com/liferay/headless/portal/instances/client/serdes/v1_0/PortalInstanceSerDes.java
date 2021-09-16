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

package com.liferay.headless.portal.instances.client.serdes.v1_0;

import com.liferay.headless.portal.instances.client.dto.v1_0.PortalInstance;
import com.liferay.headless.portal.instances.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Alberto Chaparro
 * @generated
 */
@Generated("")
public class PortalInstanceSerDes {

	public static PortalInstance toDTO(String json) {
		PortalInstanceJSONParser portalInstanceJSONParser =
			new PortalInstanceJSONParser();

		return portalInstanceJSONParser.parseToDTO(json);
	}

	public static PortalInstance[] toDTOs(String json) {
		PortalInstanceJSONParser portalInstanceJSONParser =
			new PortalInstanceJSONParser();

		return portalInstanceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PortalInstance portalInstance) {
		if (portalInstance == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (portalInstance.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(portalInstance.getActive());
		}

		if (portalInstance.getCompanyId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"companyId\": ");

			sb.append(portalInstance.getCompanyId());
		}

		if (portalInstance.getDomain() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"domain\": ");

			sb.append("\"");

			sb.append(_escape(portalInstance.getDomain()));

			sb.append("\"");
		}

		if (portalInstance.getPortalInstanceId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"portalInstanceId\": ");

			sb.append("\"");

			sb.append(_escape(portalInstance.getPortalInstanceId()));

			sb.append("\"");
		}

		if (portalInstance.getSiteInitializerKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteInitializerKey\": ");

			sb.append("\"");

			sb.append(_escape(portalInstance.getSiteInitializerKey()));

			sb.append("\"");
		}

		if (portalInstance.getVirtualHost() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"virtualHost\": ");

			sb.append("\"");

			sb.append(_escape(portalInstance.getVirtualHost()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PortalInstanceJSONParser portalInstanceJSONParser =
			new PortalInstanceJSONParser();

		return portalInstanceJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PortalInstance portalInstance) {
		if (portalInstance == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (portalInstance.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(portalInstance.getActive()));
		}

		if (portalInstance.getCompanyId() == null) {
			map.put("companyId", null);
		}
		else {
			map.put("companyId", String.valueOf(portalInstance.getCompanyId()));
		}

		if (portalInstance.getDomain() == null) {
			map.put("domain", null);
		}
		else {
			map.put("domain", String.valueOf(portalInstance.getDomain()));
		}

		if (portalInstance.getPortalInstanceId() == null) {
			map.put("portalInstanceId", null);
		}
		else {
			map.put(
				"portalInstanceId",
				String.valueOf(portalInstance.getPortalInstanceId()));
		}

		if (portalInstance.getSiteInitializerKey() == null) {
			map.put("siteInitializerKey", null);
		}
		else {
			map.put(
				"siteInitializerKey",
				String.valueOf(portalInstance.getSiteInitializerKey()));
		}

		if (portalInstance.getVirtualHost() == null) {
			map.put("virtualHost", null);
		}
		else {
			map.put(
				"virtualHost", String.valueOf(portalInstance.getVirtualHost()));
		}

		return map;
	}

	public static class PortalInstanceJSONParser
		extends BaseJSONParser<PortalInstance> {

		@Override
		protected PortalInstance createDTO() {
			return new PortalInstance();
		}

		@Override
		protected PortalInstance[] createDTOArray(int size) {
			return new PortalInstance[size];
		}

		@Override
		protected void setField(
			PortalInstance portalInstance, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					portalInstance.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "companyId")) {
				if (jsonParserFieldValue != null) {
					portalInstance.setCompanyId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "domain")) {
				if (jsonParserFieldValue != null) {
					portalInstance.setDomain((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "portalInstanceId")) {
				if (jsonParserFieldValue != null) {
					portalInstance.setPortalInstanceId(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "siteInitializerKey")) {

				if (jsonParserFieldValue != null) {
					portalInstance.setSiteInitializerKey(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "virtualHost")) {
				if (jsonParserFieldValue != null) {
					portalInstance.setVirtualHost((String)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}
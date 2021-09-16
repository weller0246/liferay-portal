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

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.OrganizationBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.RoleBrief;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class OrganizationBriefSerDes {

	public static OrganizationBrief toDTO(String json) {
		OrganizationBriefJSONParser organizationBriefJSONParser =
			new OrganizationBriefJSONParser();

		return organizationBriefJSONParser.parseToDTO(json);
	}

	public static OrganizationBrief[] toDTOs(String json) {
		OrganizationBriefJSONParser organizationBriefJSONParser =
			new OrganizationBriefJSONParser();

		return organizationBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(OrganizationBrief organizationBrief) {
		if (organizationBrief == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (organizationBrief.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(organizationBrief.getId());
		}

		if (organizationBrief.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(organizationBrief.getName()));

			sb.append("\"");
		}

		if (organizationBrief.getRoleBriefs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleBriefs\": ");

			sb.append("[");

			for (int i = 0; i < organizationBrief.getRoleBriefs().length; i++) {
				sb.append(String.valueOf(organizationBrief.getRoleBriefs()[i]));

				if ((i + 1) < organizationBrief.getRoleBriefs().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		OrganizationBriefJSONParser organizationBriefJSONParser =
			new OrganizationBriefJSONParser();

		return organizationBriefJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		OrganizationBrief organizationBrief) {

		if (organizationBrief == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (organizationBrief.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(organizationBrief.getId()));
		}

		if (organizationBrief.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(organizationBrief.getName()));
		}

		if (organizationBrief.getRoleBriefs() == null) {
			map.put("roleBriefs", null);
		}
		else {
			map.put(
				"roleBriefs",
				String.valueOf(organizationBrief.getRoleBriefs()));
		}

		return map;
	}

	public static class OrganizationBriefJSONParser
		extends BaseJSONParser<OrganizationBrief> {

		@Override
		protected OrganizationBrief createDTO() {
			return new OrganizationBrief();
		}

		@Override
		protected OrganizationBrief[] createDTOArray(int size) {
			return new OrganizationBrief[size];
		}

		@Override
		protected void setField(
			OrganizationBrief organizationBrief, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					organizationBrief.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					organizationBrief.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleBriefs")) {
				if (jsonParserFieldValue != null) {
					organizationBrief.setRoleBriefs(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RoleBriefSerDes.toDTO((String)object)
						).toArray(
							size -> new RoleBrief[size]
						));
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
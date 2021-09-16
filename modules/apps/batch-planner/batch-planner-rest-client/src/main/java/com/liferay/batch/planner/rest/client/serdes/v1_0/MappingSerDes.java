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

package com.liferay.batch.planner.rest.client.serdes.v1_0;

import com.liferay.batch.planner.rest.client.dto.v1_0.Mapping;
import com.liferay.batch.planner.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
public class MappingSerDes {

	public static Mapping toDTO(String json) {
		MappingJSONParser mappingJSONParser = new MappingJSONParser();

		return mappingJSONParser.parseToDTO(json);
	}

	public static Mapping[] toDTOs(String json) {
		MappingJSONParser mappingJSONParser = new MappingJSONParser();

		return mappingJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Mapping mapping) {
		if (mapping == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (mapping.getExternalFieldName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalFieldName\": ");

			sb.append("\"");

			sb.append(_escape(mapping.getExternalFieldName()));

			sb.append("\"");
		}

		if (mapping.getExternalFieldType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalFieldType\": ");

			sb.append("\"");

			sb.append(_escape(mapping.getExternalFieldType()));

			sb.append("\"");
		}

		if (mapping.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(mapping.getId());
		}

		if (mapping.getInternalFieldName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"internalFieldName\": ");

			sb.append("\"");

			sb.append(_escape(mapping.getInternalFieldName()));

			sb.append("\"");
		}

		if (mapping.getInternalFieldType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"internalFieldType\": ");

			sb.append("\"");

			sb.append(_escape(mapping.getInternalFieldType()));

			sb.append("\"");
		}

		if (mapping.getPlanId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"planId\": ");

			sb.append(mapping.getPlanId());
		}

		if (mapping.getScript() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"script\": ");

			sb.append("\"");

			sb.append(_escape(mapping.getScript()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		MappingJSONParser mappingJSONParser = new MappingJSONParser();

		return mappingJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Mapping mapping) {
		if (mapping == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (mapping.getExternalFieldName() == null) {
			map.put("externalFieldName", null);
		}
		else {
			map.put(
				"externalFieldName",
				String.valueOf(mapping.getExternalFieldName()));
		}

		if (mapping.getExternalFieldType() == null) {
			map.put("externalFieldType", null);
		}
		else {
			map.put(
				"externalFieldType",
				String.valueOf(mapping.getExternalFieldType()));
		}

		if (mapping.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(mapping.getId()));
		}

		if (mapping.getInternalFieldName() == null) {
			map.put("internalFieldName", null);
		}
		else {
			map.put(
				"internalFieldName",
				String.valueOf(mapping.getInternalFieldName()));
		}

		if (mapping.getInternalFieldType() == null) {
			map.put("internalFieldType", null);
		}
		else {
			map.put(
				"internalFieldType",
				String.valueOf(mapping.getInternalFieldType()));
		}

		if (mapping.getPlanId() == null) {
			map.put("planId", null);
		}
		else {
			map.put("planId", String.valueOf(mapping.getPlanId()));
		}

		if (mapping.getScript() == null) {
			map.put("script", null);
		}
		else {
			map.put("script", String.valueOf(mapping.getScript()));
		}

		return map;
	}

	public static class MappingJSONParser extends BaseJSONParser<Mapping> {

		@Override
		protected Mapping createDTO() {
			return new Mapping();
		}

		@Override
		protected Mapping[] createDTOArray(int size) {
			return new Mapping[size];
		}

		@Override
		protected void setField(
			Mapping mapping, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "externalFieldName")) {
				if (jsonParserFieldValue != null) {
					mapping.setExternalFieldName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "externalFieldType")) {
				if (jsonParserFieldValue != null) {
					mapping.setExternalFieldType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					mapping.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "internalFieldName")) {
				if (jsonParserFieldValue != null) {
					mapping.setInternalFieldName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "internalFieldType")) {
				if (jsonParserFieldValue != null) {
					mapping.setInternalFieldType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "planId")) {
				if (jsonParserFieldValue != null) {
					mapping.setPlanId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "script")) {
				if (jsonParserFieldValue != null) {
					mapping.setScript((String)jsonParserFieldValue);
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
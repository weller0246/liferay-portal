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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.ContentSubtype;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentSubtypeSerDes {

	public static ContentSubtype toDTO(String json) {
		ContentSubtypeJSONParser contentSubtypeJSONParser =
			new ContentSubtypeJSONParser();

		return contentSubtypeJSONParser.parseToDTO(json);
	}

	public static ContentSubtype[] toDTOs(String json) {
		ContentSubtypeJSONParser contentSubtypeJSONParser =
			new ContentSubtypeJSONParser();

		return contentSubtypeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentSubtype contentSubtype) {
		if (contentSubtype == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (contentSubtype.getSubtypeId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtypeId\": ");

			sb.append(contentSubtype.getSubtypeId());
		}

		if (contentSubtype.getSubtypeKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtypeKey\": ");

			sb.append("\"");

			sb.append(_escape(contentSubtype.getSubtypeKey()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ContentSubtypeJSONParser contentSubtypeJSONParser =
			new ContentSubtypeJSONParser();

		return contentSubtypeJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ContentSubtype contentSubtype) {
		if (contentSubtype == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (contentSubtype.getSubtypeId() == null) {
			map.put("subtypeId", null);
		}
		else {
			map.put("subtypeId", String.valueOf(contentSubtype.getSubtypeId()));
		}

		if (contentSubtype.getSubtypeKey() == null) {
			map.put("subtypeKey", null);
		}
		else {
			map.put(
				"subtypeKey", String.valueOf(contentSubtype.getSubtypeKey()));
		}

		return map;
	}

	public static class ContentSubtypeJSONParser
		extends BaseJSONParser<ContentSubtype> {

		@Override
		protected ContentSubtype createDTO() {
			return new ContentSubtype();
		}

		@Override
		protected ContentSubtype[] createDTOArray(int size) {
			return new ContentSubtype[size];
		}

		@Override
		protected void setField(
			ContentSubtype contentSubtype, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "subtypeId")) {
				if (jsonParserFieldValue != null) {
					contentSubtype.setSubtypeId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subtypeKey")) {
				if (jsonParserFieldValue != null) {
					contentSubtype.setSubtypeKey((String)jsonParserFieldValue);
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
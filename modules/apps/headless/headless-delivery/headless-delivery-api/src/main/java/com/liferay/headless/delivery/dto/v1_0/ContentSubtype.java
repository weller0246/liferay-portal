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

package com.liferay.headless.delivery.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "The display page template's content subtype.",
	value = "ContentSubtype"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ContentSubtype")
public class ContentSubtype implements Serializable {

	public static ContentSubtype toDTO(String json) {
		return ObjectMapperUtil.readValue(ContentSubtype.class, json);
	}

	@Schema(description = "The content subtype's ID.")
	public Long getSubtypeId() {
		return subtypeId;
	}

	public void setSubtypeId(Long subtypeId) {
		this.subtypeId = subtypeId;
	}

	@JsonIgnore
	public void setSubtypeId(
		UnsafeSupplier<Long, Exception> subtypeIdUnsafeSupplier) {

		try {
			subtypeId = subtypeIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The content subtype's ID.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long subtypeId;

	@Schema(description = "The content subtype's Key.")
	public String getSubtypeKey() {
		return subtypeKey;
	}

	public void setSubtypeKey(String subtypeKey) {
		this.subtypeKey = subtypeKey;
	}

	@JsonIgnore
	public void setSubtypeKey(
		UnsafeSupplier<String, Exception> subtypeKeyUnsafeSupplier) {

		try {
			subtypeKey = subtypeKeyUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The content subtype's Key.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String subtypeKey;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ContentSubtype)) {
			return false;
		}

		ContentSubtype contentSubtype = (ContentSubtype)object;

		return Objects.equals(toString(), contentSubtype.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (subtypeId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtypeId\": ");

			sb.append(subtypeId);
		}

		if (subtypeKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtypeKey\": ");

			sb.append("\"");

			sb.append(_escape(subtypeKey));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.ContentSubtype",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
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

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(value);
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}
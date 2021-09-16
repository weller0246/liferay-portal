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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Represents a fragment link value.",
	value = "FragmentLinkValue"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "FragmentLinkValue")
public class FragmentLinkValue implements Serializable {

	public static FragmentLinkValue toDTO(String json) {
		return ObjectMapperUtil.readValue(FragmentLinkValue.class, json);
	}

	@Schema(
		description = "The fragment link value's hypertext reference. Can be inline or mapped to an external value."
	)
	@Valid
	public Object getHref() {
		return href;
	}

	public void setHref(Object href) {
		this.href = href;
	}

	@JsonIgnore
	public void setHref(UnsafeSupplier<Object, Exception> hrefUnsafeSupplier) {
		try {
			href = hrefUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The fragment link value's hypertext reference. Can be inline or mapped to an external value."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Object href;

	@Schema(
		description = "The fragment link value's target (blank, parent, self, top)."
	)
	@Valid
	public Target getTarget() {
		return target;
	}

	@JsonIgnore
	public String getTargetAsString() {
		if (target == null) {
			return null;
		}

		return target.toString();
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	@JsonIgnore
	public void setTarget(
		UnsafeSupplier<Target, Exception> targetUnsafeSupplier) {

		try {
			target = targetUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The fragment link value's target (blank, parent, self, top)."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Target target;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentLinkValue)) {
			return false;
		}

		FragmentLinkValue fragmentLinkValue = (FragmentLinkValue)object;

		return Objects.equals(toString(), fragmentLinkValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (href != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"href\": ");

			sb.append(String.valueOf(href));
		}

		if (target != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"target\": ");

			sb.append("\"");

			sb.append(target);

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.FragmentLinkValue",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("Target")
	public static enum Target {

		BLANK("Blank"), PARENT("Parent"), SELF("Self"), TOP("Top");

		@JsonCreator
		public static Target create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Target target : values()) {
				if (Objects.equals(target.getValue(), value)) {
					return target;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Target(String value) {
			_value = value;
		}

		private final String _value;

	}

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
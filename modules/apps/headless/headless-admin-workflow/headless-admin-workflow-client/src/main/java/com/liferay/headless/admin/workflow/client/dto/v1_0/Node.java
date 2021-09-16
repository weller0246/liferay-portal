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

package com.liferay.headless.admin.workflow.client.dto.v1_0;

import com.liferay.headless.admin.workflow.client.function.UnsafeSupplier;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.NodeSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Node implements Cloneable, Serializable {

	public static Node toDTO(String json) {
		return NodeSerDes.toDTO(json);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setLabel(
		UnsafeSupplier<String, Exception> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String label;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public Type getType() {
		return type;
	}

	public String getTypeAsString() {
		if (type == null) {
			return null;
		}

		return type.toString();
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setType(UnsafeSupplier<Type, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Type type;

	@Override
	public Node clone() throws CloneNotSupportedException {
		return (Node)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Node)) {
			return false;
		}

		Node node = (Node)object;

		return Objects.equals(toString(), node.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return NodeSerDes.toJSON(this);
	}

	public static enum Type {

		CONDITION("CONDITION"), FORK("FORK"), INITIAL_STATE("INITIAL_STATE"),
		JOIN("JOIN"), JOIN_XOR("JOIN_XOR"), STATE("STATE"), TASK("TASK"),
		TERMINAL_STATE("TERMINAL_STATE");

		public static Type create(String value) {
			for (Type type : values()) {
				if (Objects.equals(type.getValue(), value) ||
					Objects.equals(type.name(), value)) {

					return type;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

}
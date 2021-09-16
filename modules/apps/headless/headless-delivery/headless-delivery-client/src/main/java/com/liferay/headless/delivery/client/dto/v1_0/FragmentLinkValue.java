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

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentLinkValueSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentLinkValue implements Cloneable, Serializable {

	public static FragmentLinkValue toDTO(String json) {
		return FragmentLinkValueSerDes.toDTO(json);
	}

	public Object getHref() {
		return href;
	}

	public void setHref(Object href) {
		this.href = href;
	}

	public void setHref(UnsafeSupplier<Object, Exception> hrefUnsafeSupplier) {
		try {
			href = hrefUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object href;

	public Target getTarget() {
		return target;
	}

	public String getTargetAsString() {
		if (target == null) {
			return null;
		}

		return target.toString();
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public void setTarget(
		UnsafeSupplier<Target, Exception> targetUnsafeSupplier) {

		try {
			target = targetUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Target target;

	@Override
	public FragmentLinkValue clone() throws CloneNotSupportedException {
		return (FragmentLinkValue)super.clone();
	}

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
		return FragmentLinkValueSerDes.toJSON(this);
	}

	public static enum Target {

		BLANK("Blank"), PARENT("Parent"), SELF("Self"), TOP("Top");

		public static Target create(String value) {
			for (Target target : values()) {
				if (Objects.equals(target.getValue(), value) ||
					Objects.equals(target.name(), value)) {

					return target;
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

		private Target(String value) {
			_value = value;
		}

		private final String _value;

	}

}
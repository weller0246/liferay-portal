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
import com.liferay.headless.delivery.client.serdes.v1_0.ContentSubtypeSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentSubtype implements Cloneable, Serializable {

	public static ContentSubtype toDTO(String json) {
		return ContentSubtypeSerDes.toDTO(json);
	}

	public Long getSubtypeId() {
		return subtypeId;
	}

	public void setSubtypeId(Long subtypeId) {
		this.subtypeId = subtypeId;
	}

	public void setSubtypeId(
		UnsafeSupplier<Long, Exception> subtypeIdUnsafeSupplier) {

		try {
			subtypeId = subtypeIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long subtypeId;

	public String getSubtypeKey() {
		return subtypeKey;
	}

	public void setSubtypeKey(String subtypeKey) {
		this.subtypeKey = subtypeKey;
	}

	public void setSubtypeKey(
		UnsafeSupplier<String, Exception> subtypeKeyUnsafeSupplier) {

		try {
			subtypeKey = subtypeKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String subtypeKey;

	@Override
	public ContentSubtype clone() throws CloneNotSupportedException {
		return (ContentSubtype)super.clone();
	}

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
		return ContentSubtypeSerDes.toJSON(this);
	}

}
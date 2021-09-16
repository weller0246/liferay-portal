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

package com.liferay.portal.internal.increment;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.util.Objects;

/**
 * @author Shuyang Zhou
 */
public abstract class IncreasableEntry<K, V> {

	public IncreasableEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof IncreasableEntry<?, ?>)) {
			return false;
		}

		IncreasableEntry<K, V> increasableEntry =
			(IncreasableEntry<K, V>)object;

		if (Objects.equals(key, increasableEntry.key) &&
			Objects.equals(value, increasableEntry.value)) {

			return true;
		}

		return false;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, key);

		return HashUtil.hash(hash, value);
	}

	public abstract IncreasableEntry<K, V> increase(V deltaValue);

	@Override
	public String toString() {
		return StringBundler.concat("{key=", key, ", value=", value, "}");
	}

	protected final K key;
	protected final V value;

}
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

package com.liferay.portal.kernel.mobile.device;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Milen Dyankov
 * @author Michael C. Han
 */
public class VersionableName
	implements Comparable<VersionableName>, Serializable {

	public static final VersionableName UNKNOWN = new VersionableName(
		"unknown", "unknown");

	public VersionableName(String name) {
		this(name, (Set<String>)null);
	}

	public VersionableName(String name, Set<String> versions) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException("Name is null");
		}

		_name = name;
		_versions = versions;
	}

	public VersionableName(String name, String version) {
		this(name, new HashSet<String>());

		addVersion(version);
	}

	public void addVersion(String version) {
		if (version == null) {
			return;
		}

		if (_versions == null) {
			_versions = new TreeSet<>();
		}

		_versions.add(version);
	}

	@Override
	public int compareTo(VersionableName versionableName) {
		String upperCaseName = StringUtil.toUpperCase(_name);

		return upperCaseName.compareTo(
			StringUtil.toUpperCase(versionableName.getName()));
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof VersionableName)) {
			return false;
		}

		VersionableName versionableName = (VersionableName)object;

		if (Objects.equals(_name, versionableName._name)) {
			return true;
		}

		return false;
	}

	public String getName() {
		return _name;
	}

	public Set<String> getVersions() {
		if (_versions == null) {
			return Collections.emptySet();
		}

		return Collections.unmodifiableSet(_versions);
	}

	@Override
	public int hashCode() {
		if (_name != null) {
			return _name.hashCode();
		}

		return 0;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{name=", _name, ", versions=", _versions, "}");
	}

	private final String _name;
	private Set<String> _versions;

}
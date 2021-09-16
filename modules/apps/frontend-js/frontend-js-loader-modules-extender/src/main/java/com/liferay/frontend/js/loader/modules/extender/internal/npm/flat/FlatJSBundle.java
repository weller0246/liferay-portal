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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.flat;

import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;

import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * Provides a complete implementation of {@link JSBundle}.
 *
 * @author Iván Zaera
 */
public class FlatJSBundle implements JSBundle {

	/**
	 * Constructs a <code>FlatJSBundle</code> with the OSGi bundle.
	 *
	 * @param bundle the OSGi bundle to which this object refers
	 */
	public FlatJSBundle(Bundle bundle) {
		_bundle = bundle;
	}

	/**
	 * Adds the NPM package description to the bundle.
	 *
	 * @param jsPackage the NPM package
	 */
	public void addJSPackage(JSPackage jsPackage) {
		_jsPackages.add(jsPackage);
	}

	@Override
	public String getId() {
		return String.valueOf(_bundle.getBundleId());
	}

	@Override
	public Collection<JSPackage> getJSPackages() {
		return _jsPackages;
	}

	@Override
	public String getName() {
		return _bundle.getSymbolicName();
	}

	@Override
	public URL getResourceURL(String location) {
		return _bundle.getResource(location);
	}

	@Override
	public String getVersion() {
		Version version = _bundle.getVersion();

		return version.toString();
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			getId(), StringPool.COLON, getName(), StringPool.AT, getVersion());
	}

	private final Bundle _bundle;
	private final Collection<JSPackage> _jsPackages = new ArrayList<>();

}
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

package com.liferay.portal.xuggler;

import com.liferay.petra.log4j.Log4JUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xuggler.Xuggler;
import com.liferay.portal.kernel.xuggler.XugglerInstallException;
import com.liferay.portal.util.JarUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import com.xuggle.ferry.JNILibraryLoader;
import com.xuggle.xuggler.IContainer;

import java.net.URL;

import java.nio.file.Paths;

import java.util.Map;

/**
 * @author Alexander Chow
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
public class XugglerImpl implements Xuggler {

	@Override
	public void installNativeLibraries(String name) throws Exception {
		try {
			JarUtil.downloadAndInstallJar(
				new URL(PropsValues.XUGGLER_JAR_URL + name),
				Paths.get(PropsValues.LIFERAY_LIB_PORTAL_DIR, name));

			_nativeLibraryCopied = true;
		}
		catch (Exception exception) {
			throw new XugglerInstallException.MustInstallJar(name, exception);
		}
	}

	@Override
	public boolean isEnabled() {
		return isEnabled(true);
	}

	@Override
	public boolean isEnabled(boolean checkNativeLibraries) {
		boolean enabled = false;

		try {
			enabled = PrefsPropsUtil.getBoolean(
				PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		if (!checkNativeLibraries) {
			return enabled;
		}

		if (enabled) {
			return isNativeLibraryInstalled();
		}

		return false;
	}

	@Override
	public boolean isNativeLibraryCopied() {
		return _nativeLibraryCopied;
	}

	@Override
	public boolean isNativeLibraryInstalled() {
		if (_nativeLibraryInstalled) {
			return _nativeLibraryInstalled;
		}

		Map<String, String> priorities = Log4JUtil.getPriorities();

		String priority = priorities.get(JNILibraryLoader.class.getName());

		if (Validator.isNull(priority)) {
			priority = "ALL";
		}

		try {
			Log4JUtil.setLevel(JNILibraryLoader.class.getName(), "OFF", false);

			IContainer.make();

			_nativeLibraryInstalled = true;
		}
		catch (NoClassDefFoundError ncdfe) {
			informAdministrator(ncdfe.getMessage());
		}
		catch (UnsatisfiedLinkError ule) {
			informAdministrator(ule.getMessage());
		}
		finally {
			Log4JUtil.setLevel(
				JNILibraryLoader.class.getName(), priority, false);
		}

		return _nativeLibraryInstalled;
	}

	protected void informAdministrator(String errorMessage) {
		if (!_informAdministrator || !_log.isWarnEnabled()) {
			return;
		}

		_informAdministrator = false;

		_log.warn(
			StringBundler.concat(
				"Liferay does not have the Xuggler native libraries ",
				"installed. In order to generate video and audio previews, ",
				"please follow the instructions for Xuggler in the Server ",
				"Administration section of the Control Panel at: ",
				"http://<server>/group/control_panel/manage/-/server",
				"/external-services. Warning: ", errorMessage));
	}

	private static final Log _log = LogFactoryUtil.getLog(XugglerImpl.class);

	private static boolean _informAdministrator = true;
	private static boolean _nativeLibraryCopied;
	private static boolean _nativeLibraryInstalled;

}
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

import org.osgi.annotation.versioning.ProviderType;

/**
 * Abstract class containing common methods for all devices
 *
 * @author Milen Dyankov
 * @author Michael C. Han
 */
@ProviderType
public abstract class BaseDevice implements Device {

	@Override
	public String toString() {
		return StringBundler.concat(
			"{brand=", getBrand(), ", browser=", getBrowser(),
			", browserVersion=", getBrowserVersion(), ", model=", getModel(),
			", os=", getOS(), ", osVersion=", getOSVersion(),
			", pointingMethod=", getPointingMethod(), ", qwertyKeyboard=",
			hasQwertyKeyboard(), ", screenPhysicalSize=",
			getScreenPhysicalSize(), ", screenResolution=",
			getScreenResolution(), ", tablet=", isTablet(), "}");
	}

}
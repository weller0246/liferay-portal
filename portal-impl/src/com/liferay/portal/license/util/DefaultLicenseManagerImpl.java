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

package com.liferay.portal.license.util;

import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.license.LicenseInfo;
import com.liferay.portal.kernel.license.util.LicenseManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.LicenseUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author Amos Fong
 */
public class DefaultLicenseManagerImpl implements LicenseManager {

	@Override
	public void checkLicense(String productId) {
	}

	@Override
	public List<Map<String, String>> getClusterLicenseProperties(
		String clusterNodeId) {

		return null;
	}

	@Override
	public String getHostName() {
		return PortalUtil.getComputerName();
	}

	@Override
	public Set<String> getIpAddresses() {
		return LicenseUtil.getIpAddresses();
	}

	@Override
	public LicenseInfo getLicenseInfo(String productId) {
		return null;
	}

	@Override
	public List<Map<String, String>> getLicenseProperties() {
		return null;
	}

	@Override
	public Map<String, String> getLicenseProperties(String productId) {
		return null;
	}

	@Override
	public int getLicenseState(Map<String, String> licenseProperties) {
		String productId = licenseProperties.get("productId");

		if (Validator.isNull(productId)) {
			return 0;
		}

		try {
			JSONObject jsonObject = new JSONObjectImpl();

			jsonObject.put(
				Constants.CMD, "GET_LICENSE_STATE"
			).put(
				"hostName", getHostName()
			).put(
				"ipAddresses", StringUtil.merge(getIpAddresses())
			).put(
				"macAddresses", StringUtil.merge(getMacAddresses())
			).put(
				"productId", productId
			);

			String productVersion = licenseProperties.get("productVersion");

			jsonObject.put("productVersion", productVersion);

			String randomUuid = String.valueOf(
				new UUID(
					SecureRandomUtil.nextLong(), SecureRandomUtil.nextLong()));

			jsonObject.put(
				"randomUuid", randomUuid
			).put(
				"serverId", Arrays.toString(LicenseUtil.getServerIdBytes())
			);

			String userCount = licenseProperties.get("userCount");

			jsonObject.put(
				"userCount", userCount
			).put(
				"version", 2
			);

			String response = LicenseUtil.sendRequest(jsonObject.toString());

			JSONObject responseJSONObject = new JSONObjectImpl(response);

			String errorMessage = responseJSONObject.getString("errorMessage");

			if (Validator.isNotNull(errorMessage)) {
				throw new Exception(errorMessage);
			}

			String responseRandomUuid = responseJSONObject.getString(
				"randomUuid");

			if (responseRandomUuid.equals(randomUuid)) {
				return responseJSONObject.getInt("licenseState");
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return 0;
	}

	@Override
	public int getLicenseState(String productId) {
		return getLicenseState(
			HashMapBuilder.put(
				"productId", productId
			).build());
	}

	@Override
	public Set<String> getMacAddresses() {
		return LicenseUtil.getMacAddresses();
	}

	@Override
	public void registerLicense(JSONObject jsonObject) throws Exception {
		String serverId = jsonObject.getString("serverId");

		if (Validator.isNotNull(serverId) && (serverId.length() <= 2)) {
			return;
		}

		serverId = serverId.substring(1, serverId.length() - 1);

		String[] serverIdArray = StringUtil.split(serverId);

		byte[] bytes = new byte[serverIdArray.length];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = Byte.valueOf(serverIdArray[i].trim());
		}

		LicenseUtil.writeServerProperties(bytes);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultLicenseManagerImpl.class);

}
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

package com.liferay.portal.workflow.kaleo.internal.model.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.upgrade.util.BaseUpgradeTableListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class BaseKaleoUpgradeTableListener extends BaseUpgradeTableListener {

	protected Map<Long, Long> getKeyValueMap(
		String tableName, String keyColumnName, String valueColumnName) {

		Map<Long, Long> keyValueMap = new HashMap<>();

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select ", keyColumnName, ", ", valueColumnName, " from ",
					tableName));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long key = resultSet.getLong(keyColumnName);
				long value = resultSet.getLong(valueColumnName);

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"{", keyColumnName, "=", key, ", ", valueColumnName,
							"=", value, "}"));
				}

				keyValueMap.put(key, value);
			}
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}

		return keyValueMap;
	}

	protected boolean isFixAutoUpgrade(
		ServiceComponent previousServiceComponent) {

		if (previousServiceComponent.getBuildNumber() >= 4) {
			return false;
		}

		return true;
	}

	protected void updateKeyValueMap(
			Map<Long, Long> keyValueMap, String kaleoClassName,
			String tableName, String keyColumnName)
		throws Exception {

		for (Map.Entry<Long, Long> entry : keyValueMap.entrySet()) {
			runSQL(
				StringBundler.concat(
					"update ", tableName, " set kaleoClassName = '",
					kaleoClassName, "', kaleoClassPK = ", entry.getValue(),
					" where ", keyColumnName, " = ", entry.getKey()));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseKaleoUpgradeTableListener.class);

}
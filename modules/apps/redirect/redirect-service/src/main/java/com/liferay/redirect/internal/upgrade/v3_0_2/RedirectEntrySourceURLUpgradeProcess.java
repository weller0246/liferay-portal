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

package com.liferay.redirect.internal.upgrade.v3_0_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Attila Bakay
 */
public class RedirectEntrySourceURLUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select redirectEntryId, groupId, sourceURL from " +
					"RedirectEntry order by BY redirectEntryId ASC");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update RedirectEntry set sourceURL = ? where " +
						"redirectEntryId = ?")) {

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				long redirectEntryId = resultSet.getLong(1);
				long groupId = resultSet.getLong(2);
				String sourceURL = resultSet.getString(3);

				Set<String> sourceURLs;
				Map<String, Long> redirectEntryIds;

				if (!_sourceURLs.containsKey(groupId)) {
					sourceURLs = new HashSet<>();
					redirectEntryIds = new LinkedHashMap<>();
				}
				else {
					sourceURLs = _sourceURLs.get(groupId);
					redirectEntryIds = _redirectEntryIds.get(
						groupId);
				}

				String lowerCaseSourceURL = StringUtil.toLowerCase(sourceURL);

				if (!sourceURLs.contains(lowerCaseSourceURL)) {
					sourceURLs.add(lowerCaseSourceURL);

					if (!sourceURL.equals(lowerCaseSourceURL)) {
						redirectEntryIds.put(
							lowerCaseSourceURL, redirectEntryId);
					}

					_redirectEntryIds.put(groupId, redirectEntryIds);
					_sourceURLs.put(groupId, sourceURLs);
				}
				else {
					if (sourceURL.equals(lowerCaseSourceURL)) {
						redirectEntryIds.remove(lowerCaseSourceURL);
					}
					else if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Can not modify '", sourceURL, "' to '",
								lowerCaseSourceURL,
								"' because it is already in use in group: ",
								groupId, " at id: ", redirectEntryId));
					}
				}
			}

			for (Map<String, Long> redirectEntryIds :
					_redirectEntryIds.values()) {

				for (Map.Entry<String, Long> upgradeRedirectEntry :
						redirectEntryIds.entrySet()) {

					String lowerCaseSourceURL = upgradeRedirectEntry.getKey();
					long redirectEntryId = upgradeRedirectEntry.getValue();

					preparedStatement2.setString(1, lowerCaseSourceURL);
					preparedStatement2.setLong(2, redirectEntryId);

					preparedStatement2.addBatch();
				}
			}

			preparedStatement2.executeBatch();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RedirectEntrySourceURLUpgradeProcess.class);

	private final Map<Long, Map<String, Long>> _redirectEntryIds =
		new LinkedHashMap<>();
	private final Map<Long, Set<String>> _sourceURLs = new LinkedHashMap<>();

}
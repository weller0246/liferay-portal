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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sergio González
 */
public class UpgradeRatings extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeRatingsEntry();
		upgradeRatingsStats();
	}

	protected void upgradeRatingsEntry() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select distinct classNameId from RatingsEntry");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				upgradeRatingsEntry(resultSet.getLong("classNameId"));
			}
		}
	}

	protected void upgradeRatingsEntry(long classNameId) throws Exception {
		String className = PortalUtil.getClassName(classNameId);

		if (ArrayUtil.contains(
				PropsValues.RATINGS_UPGRADE_THUMBS_CLASS_NAMES, className)) {

			upgradeRatingsEntryThumbs(classNameId);
		}
		else {
			int defaultRatingsStarsNormalizationFactor = GetterUtil.getInteger(
				PropsUtil.get(
					PropsKeys.RATINGS_UPGRADE_STARS_NORMALIZATION_FACTOR,
					new Filter("default")),
				5);

			int ratingsStarsNormalizationFactor = GetterUtil.getInteger(
				PropsUtil.get(
					PropsKeys.RATINGS_UPGRADE_STARS_NORMALIZATION_FACTOR,
					new Filter(className)),
				defaultRatingsStarsNormalizationFactor);

			upgradeRatingsEntryStars(
				classNameId, ratingsStarsNormalizationFactor);
		}
	}

	protected void upgradeRatingsEntryStars(
			long classNameId, int normalizationFactor)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update RatingsEntry set score = score / ? where classNameId " +
					"= ?")) {

			preparedStatement.setInt(1, normalizationFactor);
			preparedStatement.setLong(2, classNameId);

			preparedStatement.executeUpdate();
		}
	}

	protected void upgradeRatingsEntryThumbs(long classNameId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update RatingsEntry set score = ? where score = ? and " +
					"classNameId = ?")) {

			preparedStatement.setDouble(1, 0);
			preparedStatement.setDouble(2, -1);
			preparedStatement.setLong(3, classNameId);

			preparedStatement.executeUpdate();
		}
	}

	protected void upgradeRatingsStats() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String selectSQL = StringBundler.concat(
				"select classNameId, classPK, count(1) as totalEntries, ",
				"sum(RatingsEntry.score) as totalScore, ",
				"sum(RatingsEntry.score) / count(1) as averageScore from ",
				"RatingsEntry group by classNameId, classPK");

			String updateSQL =
				"update RatingsStats set totalEntries = ?, totalScore = ?, " +
					"averageScore = ? where classNameId = ? and classPK = ?";

			try (PreparedStatement preparedStatement1 =
					connection.prepareStatement(selectSQL);
				ResultSet resultSet = preparedStatement1.executeQuery();
				PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(updateSQL))) {

				while (resultSet.next()) {
					preparedStatement2.setInt(
						1, resultSet.getInt("totalEntries"));
					preparedStatement2.setDouble(
						2, resultSet.getDouble("totalScore"));
					preparedStatement2.setDouble(
						3, resultSet.getDouble("averageScore"));
					preparedStatement2.setLong(
						4, resultSet.getLong("classNameId"));
					preparedStatement2.setLong(5, resultSet.getLong("classPK"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

}
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

package com.liferay.portal.upgrade.v6_1_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class UpgradeLayoutSetBranch extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateLayoutSetBranches();
	}

	protected void updateLayoutSetBranch(
			long layoutSetBranchId, String themeId, String colorSchemeId,
			String wapThemeId, String wapColorSchemeId, String css,
			String settings, String layoutSetPrototypeUuid,
			boolean layoutSetPrototypeLinkEnabled)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("update LayoutSetBranch set themeId = ?, colorSchemeId = ");
		sb.append("?, wapThemeId = ?, wapColorSchemeId = ?, css = ?, ");
		sb.append("settings_ = ?, layoutSetPrototypeUuid = ?, ");
		sb.append("layoutSetPrototypeLinkEnabled = ? where layoutSetBranchId ");
		sb.append("= ?");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.setString(1, themeId);
			preparedStatement.setString(2, colorSchemeId);
			preparedStatement.setString(3, wapThemeId);
			preparedStatement.setString(4, wapColorSchemeId);
			preparedStatement.setString(5, css);
			preparedStatement.setString(6, settings);
			preparedStatement.setString(7, layoutSetPrototypeUuid);
			preparedStatement.setBoolean(8, layoutSetPrototypeLinkEnabled);
			preparedStatement.setLong(9, layoutSetBranchId);

			preparedStatement.executeUpdate();
		}
	}

	protected void updateLayoutSetBranches() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select groupId, layoutSetBranchId, privateLayout from " +
					"LayoutSetBranch");
			ResultSet rs = preparedStatement.executeQuery()) {

			while (rs.next()) {
				long layoutSetBranchId = rs.getLong("layoutSetBranchId");
				long groupId = rs.getLong("groupId");
				boolean privateLayout = rs.getBoolean("privateLayout");

				upgradeLayoutSetBranch(
					layoutSetBranchId, groupId, privateLayout);
			}
		}
	}

	protected void upgradeLayoutSetBranch(
			long layoutSetBranchId, long groupId, boolean privateLayout)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("select themeId, colorSchemeId, wapThemeId, ");
		sb.append("wapColorSchemeId, css, settings_, layoutSetPrototypeUuid, ");
		sb.append("layoutSetPrototypeLinkEnabled from LayoutSet where ");
		sb.append("groupId = ? and privateLayout = ?");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.setLong(1, groupId);
			preparedStatement.setBoolean(2, privateLayout);

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					String themeId = rs.getString("themeId");
					String colorSchemeId = rs.getString("colorSchemeId");
					String wapThemeId = rs.getString("wapThemeId");
					String wapColorSchemeId = rs.getString("wapColorSchemeId");
					String css = rs.getString("css");
					String settings = rs.getString("settings_");
					String layoutSetPrototypeUuid = rs.getString(
						"layoutSetPrototypeUuid");
					boolean layoutSetPrototypeLinkEnabled = rs.getBoolean(
						"layoutSetPrototypeLinkEnabled");

					updateLayoutSetBranch(
						layoutSetBranchId, themeId, colorSchemeId, wapThemeId,
						wapColorSchemeId, css, settings, layoutSetPrototypeUuid,
						layoutSetPrototypeLinkEnabled);
				}
			}
		}
	}

}
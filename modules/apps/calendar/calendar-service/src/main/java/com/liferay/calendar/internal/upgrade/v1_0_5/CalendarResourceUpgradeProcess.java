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

package com.liferay.calendar.internal.upgrade.v1_0_5;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

/**
 * @author Adam Brandizzi
 */
public class CalendarResourceUpgradeProcess extends UpgradeProcess {

	public CalendarResourceUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		CompanyLocalService companyLocalService,
		UserLocalService userLocalService) {

		_classNameLocalService = classNameLocalService;
		_companyLocalService = companyLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeCalendarResourceUserIds();
	}

	protected long getCompanyAdminUserId(Company company)
		throws PortalException {

		Role role = RoleLocalServiceUtil.getRole(
			company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		long[] userIds = UserLocalServiceUtil.getRoleUserIds(role.getRoleId());

		if (!ArrayUtil.isEmpty(userIds)) {
			return userIds[0];
		}

		List<Group> groups = GroupLocalServiceUtil.getRoleGroups(
			role.getRoleId());

		for (Group group : groups) {
			if (group.isOrganization()) {
				userIds = OrganizationLocalServiceUtil.getUserPrimaryKeys(
					group.getClassPK());

				if (!ArrayUtil.isEmpty(userIds)) {
					return userIds[0];
				}
			}
			else if (group.isRegularSite()) {
				userIds = GroupLocalServiceUtil.getUserPrimaryKeys(
					group.getGroupId());

				if (!ArrayUtil.isEmpty(userIds)) {
					return userIds[0];
				}
			}
			else if (group.isUserGroup()) {
				userIds = UserGroupLocalServiceUtil.getUserPrimaryKeys(
					group.getClassPK());

				if (!ArrayUtil.isEmpty(userIds)) {
					return userIds[0];
				}
			}
		}

		throw new UpgradeException(
			"Unable to find an administrator user in company " +
				company.getCompanyId());
	}

	protected void updateCalendarUserId(long calendarId, long userId)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update Calendar set userId = ? where calendarId = ?")) {

			preparedStatement.setLong(1, userId);
			preparedStatement.setLong(2, calendarId);

			preparedStatement.execute();
		}
	}

	protected void updateCalendarUserIds(
			long groupClassNameId, long defaultUserId, long adminUserId)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select Calendar.calendarId from Calendar join ",
					"CalendarResource on Calendar.calendarResourceId = ",
					"CalendarResource.calendarResourceId where ",
					"CalendarResource.classNameId = ? and ",
					"CalendarResource.userId = ?"))) {

			preparedStatement.setLong(1, groupClassNameId);
			preparedStatement.setLong(2, defaultUserId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long calendarId = resultSet.getLong(1);

					updateCalendarUserId(calendarId, adminUserId);
				}
			}
		}
	}

	protected void upgradeCalendarResourceUserId(
			long groupClassNameId, long defaultUserId, long companyAdminUserId)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update CalendarResource set userId = ? where userId = ? and " +
					"classNameId = ?")) {

			preparedStatement.setLong(1, companyAdminUserId);
			preparedStatement.setLong(2, defaultUserId);
			preparedStatement.setLong(3, groupClassNameId);

			preparedStatement.execute();
		}
	}

	protected void upgradeCalendarResourceUserIds() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_companyLocalService.forEachCompany(
				company -> {
					long classNameId = _classNameLocalService.getClassNameId(
						Group.class);
					long defaultUserId = _userLocalService.getDefaultUserId(
						company.getCompanyId());
					long companyAdminUserId = getCompanyAdminUserId(company);

					updateCalendarUserIds(
						classNameId, defaultUserId, companyAdminUserId);

					upgradeCalendarResourceUserId(
						classNameId, defaultUserId, companyAdminUserId);
				});
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final CompanyLocalService _companyLocalService;
	private final UserLocalService _userLocalService;

}
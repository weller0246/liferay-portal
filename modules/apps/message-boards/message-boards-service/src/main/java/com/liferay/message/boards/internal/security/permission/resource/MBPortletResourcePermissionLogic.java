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

package com.liferay.message.boards.internal.security.permission.resource;

import com.liferay.message.boards.service.MBBanLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionLogic;

/**
 * @author Sergio González
 */
public class MBPortletResourcePermissionLogic
	implements PortletResourcePermissionLogic {

	public MBPortletResourcePermissionLogic(
		MBBanLocalService mbBanLocalService) {

		_mbBanLocalService = mbBanLocalService;
	}

	@Override
	public Boolean contains(
		PermissionChecker permissionChecker, String name, Group group,
		String actionId) {

		if (_mbBanLocalService.hasBan(
				group.getGroupId(), permissionChecker.getUserId())) {

			return false;
		}

		return null;
	}

	private final MBBanLocalService _mbBanLocalService;

}
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

package com.liferay.item.selector.internal.provider;

import com.liferay.item.selector.provider.GroupItemSelectorProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portlet.usersadmin.search.GroupSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = GroupItemSelectorProvider.class)
public class GroupItemSelectorProviderImpl
	implements GroupItemSelectorProvider {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public String getEmptyResultsMessage() {
		return GroupSearch.EMPTY_RESULTS_MESSAGE;
	}

	@Override
	public String getEmptyResultsMessage(Locale locale) {
		return _language.get(locale, GroupSearch.EMPTY_RESULTS_MESSAGE);
	}

	@Override
	public List<Group> getGroups(
		long companyId, long groupId, String keywords, int start, int end) {

		LinkedHashMap<String, Object> groupParams =
			LinkedHashMapBuilder.<String, Object>put(
				"site", Boolean.TRUE
			).build();

		try {
			return _filterGroups(
				_groupLocalService.search(
					companyId, _classNameIds, keywords, groupParams, start, end,
					null));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return Collections.emptyList();
		}
	}

	@Override
	public int getGroupsCount(long companyId, long groupId, String keywords) {
		return _groupService.searchCount(
			companyId, _classNameIds, keywords,
			LinkedHashMapBuilder.<String, Object>put(
				"actionId", ActionKeys.VIEW
			).put(
				"site", Boolean.TRUE
			).build());
	}

	@Override
	public String getGroupType() {
		return "site";
	}

	@Override
	public String getIcon() {
		return "sites";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "site");
	}

	@Activate
	protected void activate() {
		_classNameIds = new long[] {
			_classNameLocalService.getClassNameId(Company.class),
			_classNameLocalService.getClassNameId(Group.class),
			_classNameLocalService.getClassNameId(Organization.class)
		};
	}

	private List<Group> _filterGroups(List<Group> groups)
		throws PortalException {

		List<Group> filteredGroups = new ArrayList<>();

		PermissionChecker permissionChecker =
			GuestOrUserUtil.getPermissionChecker();

		for (Group group : groups) {
			if (group.isCompany() ||
				GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.VIEW)) {

				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupItemSelectorProviderImpl.class);

	private long[] _classNameIds;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private GroupService _groupService;

	@Reference
	private Language _language;

}
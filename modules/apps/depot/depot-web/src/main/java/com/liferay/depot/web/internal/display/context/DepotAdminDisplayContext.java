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

package com.liferay.depot.web.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelServiceUtil;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.depot.web.internal.search.DepotEntrySearch;
import com.liferay.depot.web.internal.servlet.taglib.clay.DepotEntryVerticalCard;
import com.liferay.depot.web.internal.servlet.taglib.util.DepotActionDropdownItemsProvider;
import com.liferay.depot.web.internal.util.DepotAdminGroupSearchProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.usersadmin.search.GroupSearch;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DepotAdminDisplayContext {

	public DepotAdminDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_depotAdminGroupSearchProvider =
			(DepotAdminGroupSearchProvider)httpServletRequest.getAttribute(
				DepotAdminGroupSearchProvider.class.getName());
		_depotEntryLocalService =
			(DepotEntryLocalService)httpServletRequest.getAttribute(
				DepotEntryLocalService.class.getName());
	}

	public List<DropdownItem> getActionDropdownItems(DepotEntry depotEntry) {
		DepotActionDropdownItemsProvider depotActionDropdownItemsProvider =
			new DepotActionDropdownItemsProvider(
				depotEntry, _liferayPortletRequest, _liferayPortletResponse);

		return depotActionDropdownItemsProvider.getActionDropdownItems();
	}

	public String getDefaultDisplayStyle() {
		return "icon";
	}

	public int getDepotEntryConnectedGroupsCount(DepotEntry depotEntry)
		throws PortalException {

		return DepotEntryGroupRelServiceUtil.getDepotEntryGroupRelsCount(
			depotEntry);
	}

	public DepotEntryVerticalCard getDepotEntryVerticalCard(
			DepotEntry depotEntry)
		throws PortalException {

		SearchContainer<DepotEntry> searchContainer = searchContainer();

		return new DepotEntryVerticalCard(
			depotEntry, _liferayPortletRequest, _liferayPortletResponse,
			searchContainer.getRowChecker());
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest),
			DepotPortletKeys.DEPOT_ADMIN, getDefaultDisplayStyle());

		return _displayStyle;
	}

	public PortletURL getIteratorURL() throws PortalException {
		SearchContainer<DepotEntry> searchContainer = searchContainer();

		return searchContainer.getIteratorURL();
	}

	public String getSearchContainerId() {
		return "depotEntries";
	}

	public String getViewDepotURL(DepotEntry depotEntry)
		throws PortalException {

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_liferayPortletRequest, depotEntry.getGroup(),
				DepotPortletKeys.DEPOT_ADMIN, 0, 0, PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/depot/view_depot_dashboard"
		).setParameter(
			"depotEntryId", depotEntry.getDepotEntryId()
		).buildString();
	}

	public boolean isDisplayStyleDescriptive() {
		return Objects.equals(getDisplayStyle(), "descriptive");
	}

	public boolean isDisplayStyleIcon() {
		return Objects.equals(getDisplayStyle(), "icon");
	}

	public SearchContainer<DepotEntry> searchContainer()
		throws PortalException {

		if (_depotEntrySearch != null) {
			return _depotEntrySearch;
		}

		_depotEntrySearch = new DepotEntrySearch(
			_liferayPortletRequest, _liferayPortletResponse, _getPortletURL(),
			getSearchContainerId());

		GroupSearch groupSearch = _depotAdminGroupSearchProvider.getGroupSearch(
			_liferayPortletRequest, _getPortletURL());

		List<Group> searchResults = groupSearch.getResults();

		Stream<Group> stream = searchResults.stream();

		_depotEntrySearch.setResults(
			stream.map(
				this::_getGroup
			).map(
				Group::getGroupId
			).map(
				_depotEntryLocalService::fetchGroupDepotEntry
			).collect(
				Collectors.toList()
			));

		_depotEntrySearch.setTotal(groupSearch.getTotal());

		return _depotEntrySearch;
	}

	private Group _getGroup(Group group) {
		Group stagingGroup = group.getStagingGroup();

		if (stagingGroup != null) {
			return stagingGroup;
		}

		return group;
	}

	private PortletURL _getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setParameter(
			"displayStyle", getDisplayStyle()
		).buildPortletURL();
	}

	private final DepotAdminGroupSearchProvider _depotAdminGroupSearchProvider;
	private final DepotEntryLocalService _depotEntryLocalService;
	private DepotEntrySearch _depotEntrySearch;
	private String _displayStyle;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}
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

import com.liferay.depot.constants.DepotActionKeys;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.web.internal.roles.admin.group.type.contributor.DepotEntryPermission;
import com.liferay.depot.web.internal.security.permission.resource.DepotPermission;
import com.liferay.depot.web.internal.util.DepotEntryURLUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DepotAdminManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public DepotAdminManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			DepotAdminDisplayContext depotAdminDisplayContext)
		throws PortalException {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			depotAdminDisplayContext.searchContainer());

		_depotAdminDisplayContext = depotAdminDisplayContext;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteSelectedDepotEntries");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"deleteDepotEntriesURL",
			() -> PortletURLBuilder.createActionURL(
				liferayPortletResponse
			).setActionName(
				"/depot/delete_depot_entry"
			).buildString()
		).build();
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType", getOrderByType()
		).buildString();
	}

	@Override
	public String getComponentId() {
		return "depotAdminManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		try {
			return CreationMenuBuilder.addPrimaryDropdownItem(
				dropdownItem -> {
					dropdownItem.putData("action", "addDepotEntry");

					PortletURL addDepotEntryURL =
						DepotEntryURLUtil.getAddDepotEntryActionURL(
							_themeDisplay.getURLCurrent(),
							liferayPortletResponse);

					dropdownItem.putData(
						"addDepotEntryURL", addDepotEntryURL.toString());

					dropdownItem.setLabel(
						LanguageUtil.get(httpServletRequest, "add"));
				}
			).build();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	public Map<String, Object> getRowData(DepotEntry depotEntry)
		throws PortalException {

		return HashMapBuilder.<String, Object>put(
			"actions", StringUtil.merge(_getAvailableActions(depotEntry))
		).build();
	}

	@Override
	public String getSearchActionURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType", getOrderByType()
		).buildString();
	}

	@Override
	public Boolean isShowCreationMenu() {
		return DepotPermission.contains(
			_themeDisplay.getPermissionChecker(),
			_themeDisplay.getScopeGroupId(), DepotActionKeys.ADD_DEPOT_ENTRY);
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return _depotAdminDisplayContext.getDefaultDisplayStyle();
	}

	@Override
	protected String getDisplayStyle() {
		return _depotAdminDisplayContext.getDisplayStyle();
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list", "descriptive", "icon"};
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"descriptive-name"};
	}

	private List<String> _getAvailableActions(DepotEntry depotEntry)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (_hasDeleteDepotEntryPermission(depotEntry)) {
			availableActions.add("deleteSelectedDepotEntries");
		}

		return availableActions;
	}

	private boolean _hasDeleteDepotEntryPermission(DepotEntry depotEntry)
		throws PortalException {

		if (!DepotEntryPermission.contains(
				_themeDisplay.getPermissionChecker(),
				depotEntry.getDepotEntryId(), ActionKeys.DELETE)) {

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotAdminManagementToolbarDisplayContext.class);

	private final DepotAdminDisplayContext _depotAdminDisplayContext;
	private final ThemeDisplay _themeDisplay;

}
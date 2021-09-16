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

package com.liferay.site.navigation.item.selector.web.internal.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.item.selector.SiteNavigationMenuItemSelectorReturnType;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuItemServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuServiceUtil;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class SelectSiteNavigationMenuDisplayContext {

	public SelectSiteNavigationMenuDisplayContext(
		HttpServletRequest httpServletRequest, String itemSelectedEventName,
		PortletURL portletURL,
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry) {

		_httpServletRequest = httpServletRequest;
		_itemSelectedEventName = itemSelectedEventName;
		_portletURL = portletURL;
		_siteNavigationMenuItemTypeRegistry =
			siteNavigationMenuItemTypeRegistry;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<BreadcrumbEntry> getBreadcrumbEntries() throws Exception {
		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		breadcrumbEntries.add(_getMenusBreadcrumbEntry());

		long siteNavigationMenuId = getSiteNavigationMenuId();

		if (siteNavigationMenuId == 0) {
			breadcrumbEntries.addAll(_getLayoutBreadcrumbEntries());
		}
		else if (siteNavigationMenuId > 0) {
			breadcrumbEntries.addAll(_getSiteNavigationMenuBreadcrumbEntries());
		}

		return breadcrumbEntries;
	}

	public Map<String, Object> getContext(
		LiferayPortletResponse liferayPortletResponse) {

		return HashMapBuilder.<String, Object>put(
			"buttonClass", ".site-navigation-menu-selector"
		).put(
			"containerId",
			liferayPortletResponse.getNamespace() +
				"siteNavigationMenuLevelSelector"
		).put(
			"eventName", getItemSelectedEventName()
		).put(
			"returnType",
			SiteNavigationMenuItemSelectorReturnType.class.toString()
		).build();
	}

	public String getCurrentLevelTitle() {
		long siteNavigationMenuId = getSiteNavigationMenuId();
		long parentSiteNavigationMenuItemId =
			getParentSiteNavigationMenuItemId();

		if (siteNavigationMenuId == 0) {
			if (parentSiteNavigationMenuItemId == 0) {
				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					_themeDisplay.getLocale(), getClass());

				String key = "public-pages-hierarchy";

				if (isPrivateLayout()) {
					key = "private-pages-hierarchy";
				}

				return LanguageUtil.get(resourceBundle, key);
			}

			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				getParentSiteNavigationMenuItemId());

			return layout.getName(_themeDisplay.getLocale());
		}

		if (parentSiteNavigationMenuItemId == 0) {
			SiteNavigationMenu siteNavigationMenu =
				SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(
					siteNavigationMenuId);

			return siteNavigationMenu.getName();
		}

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemLocalServiceUtil.fetchSiteNavigationMenuItem(
				getParentSiteNavigationMenuItemId());

		return _getSiteNavigationMenuItemName(siteNavigationMenuItem);
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public long getParentSiteNavigationMenuItemId() {
		if (_parentSiteNavigationMenuItemId != null) {
			return _parentSiteNavigationMenuItemId;
		}

		_parentSiteNavigationMenuItemId = ParamUtil.getLong(
			_httpServletRequest, "parentSiteNavigationMenuItemId");

		return _parentSiteNavigationMenuItemId;
	}

	public String getSelectSiteNavigationMenuLevelURL(
			long siteNavigationMenuId, int type)
		throws PortletException {

		PortletURL portletURL = _getBasePortletURL(siteNavigationMenuId);

		if (type == SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY) {
			portletURL.setParameter("privateLayout", Boolean.TRUE.toString());
		}

		return portletURL.toString();
	}

	public long getSiteNavigationMenuId() {
		if (_siteNavigationMenuId != null) {
			return _siteNavigationMenuId;
		}

		_siteNavigationMenuId = ParamUtil.getLong(
			_httpServletRequest, "siteNavigationMenuId", -1);

		return _siteNavigationMenuId;
	}

	public SearchContainer<SiteNavigationMenuEntry>
			getSiteNavigationMenuItemSearchContainer()
		throws PortalException, PortletException {

		SearchContainer<SiteNavigationMenuEntry> searchContainer =
			new SearchContainer<>(
				_getPortletRequest(), _portletURL, null,
				"there-are-no-items-to-display");

		List<SiteNavigationMenuEntry> siteNavigationMenuItems =
			_getSiteNavigationMenuItems();

		searchContainer.setResults(siteNavigationMenuItems);
		searchContainer.setTotal(siteNavigationMenuItems.size());

		return searchContainer;
	}

	public SearchContainer<SiteNavigationMenu>
		getSiteNavigationMenuSearchContainer() {

		SearchContainer<SiteNavigationMenu> searchContainer =
			new SearchContainer<>(
				_getPortletRequest(), _portletURL, null, null);

		long[] groupIds = {_themeDisplay.getScopeGroupId()};

		Group scopeGroup = _themeDisplay.getScopeGroup();

		if (!scopeGroup.isCompany()) {
			groupIds = ArrayUtil.append(
				groupIds, _themeDisplay.getCompanyGroupId());
		}

		List<SiteNavigationMenu> staticSiteNavigationMenus =
			_getStaticSiteNavigationMenus();

		int start = searchContainer.getStart();

		int staticSiteNavigationMenusCount = staticSiteNavigationMenus.size();

		if (start != 0) {
			start -= staticSiteNavigationMenusCount;
		}

		List<SiteNavigationMenu> siteNavigationMenus =
			SiteNavigationMenuServiceUtil.getSiteNavigationMenus(
				groupIds, start, searchContainer.getEnd(), null);

		int siteNavigationMenusCount =
			SiteNavigationMenuServiceUtil.getSiteNavigationMenusCount(groupIds);

		if (start == 0) {
			siteNavigationMenus = ListUtil.concat(
				staticSiteNavigationMenus, siteNavigationMenus);
		}

		searchContainer.setResults(siteNavigationMenus);
		searchContainer.setTotal(
			siteNavigationMenusCount + staticSiteNavigationMenusCount);

		return searchContainer;
	}

	public boolean isPrivateLayout() {
		if (_privateLayout != null) {
			return _privateLayout;
		}

		_privateLayout = ParamUtil.getBoolean(
			_httpServletRequest, "privateLayout");

		return _privateLayout;
	}

	private BreadcrumbEntry _createBreadcrumbEntry(String title, String url) {
		return new BreadcrumbEntry() {
			{
				setBrowsable(url != null);
				setTitle(title);
				setURL(url);
			}
		};
	}

	private List<BreadcrumbEntry> _getAncestorsBreadcrumbEntries()
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemLocalServiceUtil.fetchSiteNavigationMenuItem(
				getParentSiteNavigationMenuItemId());

		breadcrumbEntries.add(
			_createBreadcrumbEntry(
				_getSiteNavigationMenuItemName(siteNavigationMenuItem),
				getSelectSiteNavigationMenuLevelURL(
					getSiteNavigationMenuId(),
					SiteNavigationConstants.TYPE_DEFAULT)));

		while (siteNavigationMenuItem.getParentSiteNavigationMenuItemId() !=
					0) {

			siteNavigationMenuItem =
				SiteNavigationMenuItemLocalServiceUtil.
					fetchSiteNavigationMenuItem(
						siteNavigationMenuItem.
							getParentSiteNavigationMenuItemId());

			breadcrumbEntries.add(
				0,
				_createBreadcrumbEntry(
					_getSiteNavigationMenuItemName(siteNavigationMenuItem),
					_getSelectSiteNavigationMenuLevelURL(
						getSiteNavigationMenuId(),
						siteNavigationMenuItem.getSiteNavigationMenuItemId())));
		}

		return breadcrumbEntries;
	}

	private PortletURL _getBasePortletURL(long siteNavigationMenuId)
		throws PortletException {

		PortletResponse portletResponse =
			(PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortletURLBuilder.create(
			PortletURLUtil.clone(
				_portletURL,
				PortalUtil.getLiferayPortletResponse(portletResponse))
		).setBackURL(
			ParamUtil.getString(
				_httpServletRequest, "backURL",
				PortalUtil.getCurrentURL(_httpServletRequest))
		).setParameter(
			"siteNavigationMenuId", siteNavigationMenuId
		).buildPortletURL();
	}

	private List<BreadcrumbEntry> _getLayoutBreadcrumbEntries()
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		String key = "public-pages-hierarchy";

		if (isPrivateLayout()) {
			key = "private-pages-hierarchy";
		}

		breadcrumbEntries.add(
			_createBreadcrumbEntry(
				LanguageUtil.get(resourceBundle, key),
				_getSelectSiteNavigationMenuLevelURL(
					getSiteNavigationMenuId(), 0)));

		if (getParentSiteNavigationMenuItemId() != 0) {
			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				getParentSiteNavigationMenuItemId());

			List<Layout> ancestors = layout.getAncestors();

			Collections.reverse(ancestors);

			for (Layout ancestor : ancestors) {
				breadcrumbEntries.add(
					_createBreadcrumbEntry(
						ancestor.getName(_themeDisplay.getLocale()),
						_getSelectSiteNavigationMenuLevelURL(
							getSiteNavigationMenuId(), ancestor.getPlid())));
			}

			breadcrumbEntries.add(
				_createBreadcrumbEntry(
					layout.getName(_themeDisplay.getLocale()),
					_getSelectSiteNavigationMenuLevelURL(
						getSiteNavigationMenuId(), layout.getPlid())));
		}

		return breadcrumbEntries;
	}

	private List<Layout> _getLayouts() {
		long parentSiteNavigationMenuItemId =
			getParentSiteNavigationMenuItemId();

		if (parentSiteNavigationMenuItemId > 0) {
			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				getParentSiteNavigationMenuItemId());

			return layout.getChildren();
		}

		return LayoutLocalServiceUtil.getLayouts(
			_themeDisplay.getScopeGroupId(), isPrivateLayout(),
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
	}

	private BreadcrumbEntry _getMenusBreadcrumbEntry() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		String backURL = ParamUtil.getString(
			_httpServletRequest, "backURL",
			PortalUtil.getCurrentURL(_httpServletRequest));

		return _createBreadcrumbEntry(
			LanguageUtil.get(resourceBundle, "menus"), backURL);
	}

	private PortletRequest _getPortletRequest() {
		return (PortletRequest)_httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	private SiteNavigationMenu _getPrivatePagesHierarchySiteNavigationMenu() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.createSiteNavigationMenu(0);

		siteNavigationMenu.setGroupId(_themeDisplay.getScopeGroupId());
		siteNavigationMenu.setName(
			LanguageUtil.get(resourceBundle, "private-pages-hierarchy"));
		siteNavigationMenu.setType(
			SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY);

		return siteNavigationMenu;
	}

	private SiteNavigationMenu _getPublicPagesHierarchySiteNavigationMenu() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.createSiteNavigationMenu(0);

		siteNavigationMenu.setGroupId(_themeDisplay.getScopeGroupId());
		siteNavigationMenu.setName(
			LanguageUtil.get(resourceBundle, "public-pages-hierarchy"));
		siteNavigationMenu.setType(
			SiteNavigationConstants.TYPE_PUBLIC_PAGES_HIERARCHY);

		return siteNavigationMenu;
	}

	private String _getSelectSiteNavigationMenuLevelURL(
			long siteNavigationMenuId, long parentSiteNavigationMenuItemId)
		throws PortletException {

		PortletURL portletURL = _getBasePortletURL(siteNavigationMenuId);

		if (parentSiteNavigationMenuItemId >= 0) {
			portletURL.setParameter(
				"parentSiteNavigationMenuItemId",
				String.valueOf(parentSiteNavigationMenuItemId));
		}

		if (isPrivateLayout()) {
			portletURL.setParameter("privateLayout", Boolean.TRUE.toString());
		}

		return portletURL.toString();
	}

	private List<BreadcrumbEntry> _getSiteNavigationMenuBreadcrumbEntries()
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuServiceUtil.fetchSiteNavigationMenu(
				getSiteNavigationMenuId());

		breadcrumbEntries.add(
			_createBreadcrumbEntry(
				siteNavigationMenu.getName(),
				_getSelectSiteNavigationMenuLevelURL(
					getSiteNavigationMenuId(), 0)));

		if (getParentSiteNavigationMenuItemId() != 0) {
			breadcrumbEntries.addAll(_getAncestorsBreadcrumbEntries());
		}

		return breadcrumbEntries;
	}

	private String _getSiteNavigationMenuItemName(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				siteNavigationMenuItem);

		return siteNavigationMenuItemType.getTitle(
			siteNavigationMenuItem, _themeDisplay.getLocale());
	}

	private List<SiteNavigationMenuEntry> _getSiteNavigationMenuItems()
		throws PortalException, PortletException {

		List<SiteNavigationMenuEntry> siteNavigationItems = new ArrayList<>();

		if (getSiteNavigationMenuId() > 0) {
			List<SiteNavigationMenuItem> siteNavigationMenuItems =
				SiteNavigationMenuItemServiceUtil.getSiteNavigationMenuItems(
					getSiteNavigationMenuId(),
					getParentSiteNavigationMenuItemId());

			for (SiteNavigationMenuItem siteNavigationMenuItem :
					siteNavigationMenuItems) {

				siteNavigationItems.add(
					SiteNavigationMenuEntry.of(
						_getSiteNavigationMenuItemName(siteNavigationMenuItem),
						_getSelectSiteNavigationMenuLevelURL(
							getSiteNavigationMenuId(),
							siteNavigationMenuItem.
								getSiteNavigationMenuItemId())));
			}

			return siteNavigationItems;
		}

		for (Layout layout : _getLayouts()) {
			siteNavigationItems.add(
				SiteNavigationMenuEntry.of(
					layout.getName(_themeDisplay.getLocale()),
					_getSelectSiteNavigationMenuLevelURL(
						getSiteNavigationMenuId(), layout.getPlid())));
		}

		return siteNavigationItems;
	}

	private List<SiteNavigationMenu> _getStaticSiteNavigationMenus() {
		return Arrays.asList(
			_getPublicPagesHierarchySiteNavigationMenu(),
			_getPrivatePagesHierarchySiteNavigationMenu());
	}

	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private Long _parentSiteNavigationMenuItemId;
	private final PortletURL _portletURL;
	private Boolean _privateLayout;
	private Long _siteNavigationMenuId;
	private final SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;
	private final ThemeDisplay _themeDisplay;

}
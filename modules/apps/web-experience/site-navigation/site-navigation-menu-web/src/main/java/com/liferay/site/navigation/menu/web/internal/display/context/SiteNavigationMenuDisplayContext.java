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

package com.liferay.site.navigation.menu.web.internal.display.context;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.site.navigation.item.selector.criterion.SiteNavigationMenuItemSelectorCriterion;
import com.liferay.site.navigation.menu.web.configuration.SiteNavigationMenuPortletInstanceConfiguration;
import com.liferay.site.navigation.menu.web.internal.constants.SiteNavigationMenuWebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class SiteNavigationMenuDisplayContext {

	public SiteNavigationMenuDisplayContext(HttpServletRequest request)
		throws ConfigurationException {

		_request = request;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_siteNavigationMenuPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SiteNavigationMenuPortletInstanceConfiguration.class);
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		String displayStyle = getDisplayStyle();

		if (displayStyle != null) {
			PortletDisplayTemplate portletDisplayTemplate =
				(PortletDisplayTemplate)_request.getAttribute(
					WebKeys.PORTLET_DISPLAY_TEMPLATE);

			_ddmTemplateKey = portletDisplayTemplate.getDDMTemplateKey(
				displayStyle);
		}

		return _ddmTemplateKey;
	}

	public int getDisplayDepth() {
		if (_displayDepth != -1) {
			return _displayDepth;
		}

		_displayDepth = ParamUtil.getInteger(
			_request, "displayDepth",
			_siteNavigationMenuPortletInstanceConfiguration.displayDepth());

		return _displayDepth;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_request, "displayStyle",
			_siteNavigationMenuPortletInstanceConfiguration.displayStyle());

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId = ParamUtil.getLong(
			_request, "displayStyleGroupId",
			_siteNavigationMenuPortletInstanceConfiguration.
				displayStyleGroupId());

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getSiteGroupId();
		}

		return _displayStyleGroupId;
	}

	public String getEventName() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getNamespace() + "selectLayout";
	}

	public String getExpandedLevels() {
		if (_expandedLevels != null) {
			return _expandedLevels;
		}

		String defaultExpandedLevels =
			_siteNavigationMenuPortletInstanceConfiguration.includedLayouts();

		if (Validator.isNull(defaultExpandedLevels)) {
			defaultExpandedLevels =
				_siteNavigationMenuPortletInstanceConfiguration.
					expandedLevels();
		}

		_expandedLevels = ParamUtil.getString(
			_request, "expandedLevels", defaultExpandedLevels);

		return _expandedLevels;
	}

	public String getItemSelectorURL() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		ItemSelector itemSelector = (ItemSelector)_request.getAttribute(
			SiteNavigationMenuWebKeys.ITEM_SELECTOR);

		LayoutItemSelectorCriterion layoutItemSelectorCriterion =
			new LayoutItemSelectorCriterion();

		Layout layout = themeDisplay.getLayout();

		layoutItemSelectorCriterion.setCheckDisplayPage(false);
		layoutItemSelectorCriterion.setEnableCurrentPage(true);
		layoutItemSelectorCriterion.setShowPrivatePages(
			layout.isPrivateLayout());
		layoutItemSelectorCriterion.setShowPublicPages(layout.isPublicLayout());

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(new UUIDItemSelectorReturnType());

		layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_request), getEventName(),
			layoutItemSelectorCriterion);

		itemSelectorURL.setParameter("layoutUuid", getRootMenuItemId());

		return itemSelectorURL.toString();
	}

	public String getLayoutBreadcrumb(Layout layout) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(_request, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(_request, "public-pages"));
		}

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(ancestors);

		for (Layout ancestor : ancestors) {
			sb.append(HtmlUtil.escape(ancestor.getName(locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(layout.getName(locale)));

		return sb.toString();
	}

	public String getRootLayoutName() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		Layout rootLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			getRootMenuItemId(), themeDisplay.getScopeGroupId(),
			layout.isPrivateLayout());

		if (rootLayout == null) {
			return StringPool.BLANK;
		}

		return getLayoutBreadcrumb(rootLayout);
	}

	public String getRootMenuItemId() {
		if (_rootMenuItemId != null) {
			return _rootMenuItemId;
		}

		String defaultRootMenuItemId =
			_siteNavigationMenuPortletInstanceConfiguration.rootLayoutUuid();

		if (Validator.isNull(defaultRootMenuItemId)) {
			defaultRootMenuItemId =
				_siteNavigationMenuPortletInstanceConfiguration.
					rootMenuItemId();
		}

		_rootMenuItemId = ParamUtil.getString(
			_request, "rootMenuItemId", defaultRootMenuItemId);

		return _rootMenuItemId;
	}

	public int getRootMenuItemLevel() {
		if (_rootMenuItemLevel != null) {
			return _rootMenuItemLevel;
		}

		int defaultRootMenuItemLevel =
			_siteNavigationMenuPortletInstanceConfiguration.rootLayoutLevel();

		if (defaultRootMenuItemLevel == 0) {
			defaultRootMenuItemLevel =
				_siteNavigationMenuPortletInstanceConfiguration.
					rootMenuItemLevel();
		}

		_rootMenuItemLevel = ParamUtil.getInteger(
			_request, "rootMenuItemLevel", defaultRootMenuItemLevel);

		return _rootMenuItemLevel;
	}

	public String getRootMenuItemType() {
		if (_rootMenuItemType != null) {
			return _rootMenuItemType;
		}

		String defaultRootMenuItemType =
			_siteNavigationMenuPortletInstanceConfiguration.rootLayoutType();

		if (Validator.isNull(defaultRootMenuItemType)) {
			defaultRootMenuItemType =
				_siteNavigationMenuPortletInstanceConfiguration.
					rootMenuItemType();
		}

		_rootMenuItemType = ParamUtil.getString(
			_request, "rootMenuItemType", defaultRootMenuItemType);

		return _rootMenuItemType;
	}

	public SiteNavigationMenu getSiteNavigationMenu() throws PortalException {
		if (_siteNavigationMenu != null) {
			return _siteNavigationMenu;
		}

		_siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(
				getSiteNavigationMenuId());

		return _siteNavigationMenu;
	}

	public String getSiteNavigationMenuEventName() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getNamespace() + "selectSiteNavigationMenu";
	}

	public long getSiteNavigationMenuId() {
		if (_siteNavigationMenuId != null) {
			return _siteNavigationMenuId;
		}

		_siteNavigationMenuId = ParamUtil.getLong(
			_request, "siteNavigationMenuId",
			_siteNavigationMenuPortletInstanceConfiguration.
				siteNavigationMenuId());

		return _siteNavigationMenuId;
	}

	public String getSiteNavigationMenuItemSelectorURL() {
		String eventName = getSiteNavigationMenuEventName();

		ItemSelector itemSelector = (ItemSelector)_request.getAttribute(
			SiteNavigationMenuWebKeys.ITEM_SELECTOR);

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(new UUIDItemSelectorReturnType());

		SiteNavigationMenuItemSelectorCriterion
			siteNavigationMenuItemSelectorCriterion =
				new SiteNavigationMenuItemSelectorCriterion();

		siteNavigationMenuItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(desiredItemSelectorReturnTypes);

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_request), eventName,
			siteNavigationMenuItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public boolean isPreview() {
		if (_preview != null) {
			return _preview;
		}

		_preview = ParamUtil.getBoolean(
			_request, "preview",
			_siteNavigationMenuPortletInstanceConfiguration.preview());

		return _preview;
	}

	private String _ddmTemplateKey;
	private int _displayDepth = -1;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private String _expandedLevels;
	private Boolean _preview;
	private final HttpServletRequest _request;
	private String _rootMenuItemId;
	private Integer _rootMenuItemLevel;
	private String _rootMenuItemType;
	private SiteNavigationMenu _siteNavigationMenu;
	private Long _siteNavigationMenuId;
	private final SiteNavigationMenuPortletInstanceConfiguration
		_siteNavigationMenuPortletInstanceConfiguration;

}
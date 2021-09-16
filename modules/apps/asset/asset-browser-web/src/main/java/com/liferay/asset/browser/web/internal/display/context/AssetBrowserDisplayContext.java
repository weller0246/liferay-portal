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

package com.liferay.asset.browser.web.internal.display.context;

import com.liferay.asset.browser.web.internal.configuration.AssetBrowserWebConfigurationValues;
import com.liferay.asset.browser.web.internal.constants.AssetBrowserPortletKeys;
import com.liferay.asset.browser.web.internal.search.AddAssetEntryChecker;
import com.liferay.asset.browser.web.internal.search.AssetBrowserSearch;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.util.AssetHelper;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryServiceUtil;
import com.liferay.item.selector.constants.ItemSelectorPortletKeys;
import com.liferay.item.selector.criteria.constants.ItemSelectorCriteriaConstants;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetBrowserDisplayContext {

	public AssetBrowserDisplayContext(
		AssetHelper assetHelper, HttpServletRequest httpServletRequest,
		PortletURL portletURL, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_assetHelper = assetHelper;
		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);
	}

	public AssetBrowserSearch getAssetBrowserSearch()
		throws PortalException, PortletException {

		if (_assetBrowserSearch != null) {
			return _assetBrowserSearch;
		}

		AssetBrowserSearch assetBrowserSearch = new AssetBrowserSearch(
			_renderRequest, getPortletURL());

		if (isMultipleSelection()) {
			assetBrowserSearch.setRowChecker(
				new AddAssetEntryChecker(
					_renderResponse, getRefererAssetEntryId()));
		}

		assetBrowserSearch.setOrderByCol(getOrderByCol());
		assetBrowserSearch.setOrderByType(getOrderByType());

		if (AssetBrowserWebConfigurationValues.SEARCH_WITH_DATABASE) {
			long[] subtypeSelectionIds = null;

			if (getSubtypeSelectionId() > 0) {
				subtypeSelectionIds = new long[] {getSubtypeSelectionId()};
			}

			int total = AssetEntryLocalServiceUtil.getEntriesCount(
				_getFilterGroupIds(), _getClassNameIds(), subtypeSelectionIds,
				_getKeywords(), _getKeywords(), _getKeywords(), _getKeywords(),
				_getListable(), false, false);

			assetBrowserSearch.setTotal(total);

			List<AssetEntry> assetEntries =
				AssetEntryLocalServiceUtil.getEntries(
					_getFilterGroupIds(), _getClassNameIds(),
					subtypeSelectionIds, _getKeywords(), _getKeywords(),
					_getKeywords(), _getKeywords(), _getListable(), false,
					false, assetBrowserSearch.getStart(),
					assetBrowserSearch.getEnd(), "modifiedDate",
					StringPool.BLANK, getOrderByType(), StringPool.BLANK);

			assetBrowserSearch.setResults(assetEntries);

			return assetBrowserSearch;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Sort sort = null;

		boolean orderByAsc = false;

		if (Objects.equals(getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		if (Objects.equals(getOrderByCol(), "modified-date")) {
			sort = new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
		}
		else if (Objects.equals(getOrderByCol(), "title")) {
			String sortFieldName = Field.getSortableFieldName(
				"localized_title_".concat(themeDisplay.getLanguageId()));

			sort = new Sort(sortFieldName, Sort.STRING_TYPE, !orderByAsc);
		}

		Hits hits = AssetEntryLocalServiceUtil.search(
			themeDisplay.getCompanyId(), _getFilterGroupIds(),
			themeDisplay.getUserId(), _getClassNameIds(),
			getSubtypeSelectionId(), _getKeywords(), _isShowNonindexable(),
			_getStatuses(), assetBrowserSearch.getStart(),
			assetBrowserSearch.getEnd(), sort);

		assetBrowserSearch.setResults(_assetHelper.getAssetEntries(hits));

		assetBrowserSearch.setTotal(hits.getLength());

		_assetBrowserSearch = assetBrowserSearch;

		return _assetBrowserSearch;
	}

	public AssetRendererFactory<?> getAssetRendererFactory() {
		if (_assetRendererFactory != null) {
			return _assetRendererFactory;
		}

		_assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getTypeSelection());

		return _assetRendererFactory;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			_httpServletRequest, AssetBrowserPortletKeys.ASSET_BROWSER, "list");

		return _displayStyle;
	}

	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_httpServletRequest, "itemSelectedEventName");

		if (Validator.isNull(_eventName)) {
			_eventName = ParamUtil.getString(
				_httpServletRequest, "eventName",
				_renderResponse.getNamespace() + "selectAsset");
		}

		return _eventName;
	}

	public String getGroupCssIcon(long groupId) throws PortalException {
		Group group = GroupServiceUtil.getGroup(groupId);

		return group.getIconCssClass();
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(_renderRequest, "groupId");

		if (_groupId == 0) {
			_groupId = ParamUtil.getLong(_httpServletRequest, "groupId");
		}

		return _groupId;
	}

	public String getGroupLabel(long groupId, Locale locale)
		throws PortalException {

		Group group = GroupServiceUtil.getGroup(groupId);

		return group.getDescriptiveName(locale);
	}

	public List<BreadcrumbEntry> getPortletBreadcrumbEntries()
		throws PortalException, PortletException {

		return Arrays.asList(
			_getSitesAndLibrariesBreadcrumb(), _getHomeBreadcrumb());
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(
				_portletURL,
				PortalUtil.getLiferayPortletResponse(_renderResponse))
		).setParameter(
			"eventName", getEventName()
		).setParameter(
			"groupId", getGroupId()
		).setParameter(
			"listable",
			() -> {
				if (_getListable() != null) {
					return _getListable();
				}

				return null;
			}
		).setParameter(
			"multipleSelection",
			() -> {
				if (isMultipleSelection()) {
					return Boolean.TRUE.toString();
				}

				return null;
			}
		).setParameter(
			"refererAssetEntryId", getRefererAssetEntryId()
		).setParameter(
			"selectedGroupId",
			() -> {
				long selectedGroupId = ParamUtil.getLong(
					_httpServletRequest, "selectedGroupId");

				if (selectedGroupId > 0) {
					return selectedGroupId;
				}

				return null;
			}
		).setParameter(
			"selectedGroupIds",
			() -> {
				long[] selectedGroupIds = getSelectedGroupIds();

				if (selectedGroupIds.length > 0) {
					return StringUtil.merge(selectedGroupIds);
				}

				return null;
			}
		).setParameter(
			"showAddButton",
			() -> {
				if (isShowAddButton()) {
					return Boolean.TRUE.toString();
				}

				return null;
			}
		).setParameter(
			"showNonindexable", _isShowNonindexable()
		).setParameter(
			"showScheduled", _isShowScheduled()
		).setParameter(
			"subtypeSelectionId", getSubtypeSelectionId()
		).setParameter(
			"typeSelection", getTypeSelection()
		).buildPortletURL();
	}

	public long getRefererAssetEntryId() {
		if (_refererAssetEntryId != null) {
			return _refererAssetEntryId;
		}

		_refererAssetEntryId = ParamUtil.getLong(
			_httpServletRequest, "refererAssetEntryId");

		return _refererAssetEntryId;
	}

	public long[] getSelectedGroupIds() {
		long[] selectedGroupIds = StringUtil.split(
			ParamUtil.getString(_httpServletRequest, "selectedGroupIds"), 0L);

		if (selectedGroupIds.length > 0) {
			return selectedGroupIds;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long selectedGroupId = ParamUtil.getLong(
			_httpServletRequest, "selectedGroupId");

		try {
			return PortalUtil.getSharedContentSiteGroupIds(
				themeDisplay.getCompanyId(), selectedGroupId,
				themeDisplay.getUserId());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return new long[0];
	}

	public long getSubtypeSelectionId() {
		if (_subtypeSelectionId != null) {
			return _subtypeSelectionId;
		}

		_subtypeSelectionId = ParamUtil.getLong(
			_httpServletRequest, "subtypeSelectionId");

		return _subtypeSelectionId;
	}

	public String getTypeSelection() {
		if (_typeSelection != null) {
			return _typeSelection;
		}

		_typeSelection = ParamUtil.getString(
			_httpServletRequest, "typeSelection");

		return _typeSelection;
	}

	public boolean isMultipleSelection() {
		if (_multipleSelection != null) {
			return _multipleSelection;
		}

		_multipleSelection = ParamUtil.getBoolean(
			_httpServletRequest, "multipleSelection");

		return _multipleSelection;
	}

	public boolean isSearchEverywhere() {
		if (_searchEverywhere != null) {
			return _searchEverywhere;
		}

		_searchEverywhere = Objects.equals(
			ParamUtil.getString(_httpServletRequest, "scope"), "everywhere");

		return _searchEverywhere;
	}

	public boolean isShowAddButton() {
		if (_showAddButton != null) {
			return _showAddButton;
		}

		_showAddButton = ParamUtil.getBoolean(
			_httpServletRequest, "showAddButton");

		return _showAddButton;
	}

	public boolean isShowAssetEntryStatus() {
		if (_isShowNonindexable() || _isShowScheduled()) {
			return true;
		}

		return false;
	}

	public boolean isShowBreadcrumb() {
		String scopeGroupType = ParamUtil.getString(
			_httpServletRequest, "scopeGroupType");

		if (Validator.isNotNull(scopeGroupType) &&
			scopeGroupType.equals(
				ItemSelectorCriteriaConstants.SCOPE_GROUP_TYPE_PAGE)) {

			return false;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		boolean showBreadcrumb = ParamUtil.getBoolean(
			_httpServletRequest, "showBreadcrumb");

		if (Objects.equals(
				ItemSelectorPortletKeys.ITEM_SELECTOR,
				portletDisplay.getPortletName()) ||
			showBreadcrumb) {

			return true;
		}

		return false;
	}

	protected String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol");

		if (Validator.isNotNull(orderByCol)) {
			_portalPreferences.setValue(
				AssetBrowserPortletKeys.ASSET_BROWSER, "order-by-col",
				orderByCol);
		}
		else {
			orderByCol = _portalPreferences.getValue(
				AssetBrowserPortletKeys.ASSET_BROWSER, "order-by-col",
				"modified-date");
		}

		_orderByCol = orderByCol;

		return _orderByCol;
	}

	protected String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType");

		if (Validator.isNotNull(orderByType)) {
			_portalPreferences.setValue(
				AssetBrowserPortletKeys.ASSET_BROWSER, "order-by-type",
				orderByType);
		}
		else {
			orderByType = _portalPreferences.getValue(
				AssetBrowserPortletKeys.ASSET_BROWSER, "order-by-type", "asc");
		}

		_orderByType = orderByType;

		return _orderByType;
	}

	private long[] _getClassNameIds() {
		if (_classNameIds != null) {
			return _classNameIds;
		}

		AssetRendererFactory<?> assetRendererFactory =
			getAssetRendererFactory();

		if (assetRendererFactory != null) {
			_classNameIds = new long[] {assetRendererFactory.getClassNameId()};
		}

		return _classNameIds;
	}

	private long[] _getFilterGroupIds() throws PortalException {
		if (_filterGroupIds != null) {
			return _filterGroupIds;
		}

		if (getGroupId() == 0) {
			_filterGroupIds = getSelectedGroupIds();
		}
		else if (!isSearchEverywhere()) {
			_filterGroupIds = new long[] {getGroupId()};
		}
		else {
			_filterGroupIds = ArrayUtil.append(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(getGroupId()),
				ListUtil.toLongArray(
					DepotEntryServiceUtil.getGroupConnectedDepotEntries(
						getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					DepotEntry::getGroupId));
		}

		return _filterGroupIds;
	}

	private BreadcrumbEntry _getHomeBreadcrumb() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(themeDisplay.getSiteGroupName());

		return breadcrumbEntry;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private Boolean _getListable() {
		Boolean listable = null;

		String listableValue = ParamUtil.getString(
			_httpServletRequest, "listable", null);

		if (Validator.isNotNull(listableValue)) {
			listable = ParamUtil.getBoolean(
				_httpServletRequest, "listable", true);
		}

		return listable;
	}

	private BreadcrumbEntry _getSitesAndLibrariesBreadcrumb()
		throws PortletException {

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			LanguageUtil.get(_httpServletRequest, "sites-and-libraries"));

		breadcrumbEntry.setURL(
			PortletURLBuilder.create(
				PortletURLUtil.clone(
					_portletURL,
					PortalUtil.getLiferayPortletResponse(_renderResponse))
			).setParameter(
				"groupType", "site"
			).setParameter(
				"scopeGroupType",
				ParamUtil.getString(_httpServletRequest, "scopeGroupType")
			).setParameter(
				"showGroupSelector", true
			).buildString());

		return breadcrumbEntry;
	}

	private int[] _getStatuses() {
		int[] statuses = {WorkflowConstants.STATUS_APPROVED};

		if (_isShowScheduled()) {
			statuses = new int[] {
				WorkflowConstants.STATUS_APPROVED,
				WorkflowConstants.STATUS_SCHEDULED
			};
		}

		return statuses;
	}

	private boolean _isShowNonindexable() {
		if (_showNonindexable != null) {
			return _showNonindexable;
		}

		_showNonindexable = ParamUtil.getBoolean(
			_httpServletRequest, "showNonindexable");

		return _showNonindexable;
	}

	private boolean _isShowScheduled() {
		if (_showScheduled != null) {
			return _showScheduled;
		}

		_showScheduled = ParamUtil.getBoolean(
			_httpServletRequest, "showScheduled");

		return _showScheduled;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetBrowserDisplayContext.class);

	private AssetBrowserSearch _assetBrowserSearch;
	private final AssetHelper _assetHelper;
	private AssetRendererFactory<?> _assetRendererFactory;
	private long[] _classNameIds;
	private String _displayStyle;
	private String _eventName;
	private long[] _filterGroupIds;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private Boolean _multipleSelection;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private final PortletURL _portletURL;
	private Long _refererAssetEntryId;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Boolean _searchEverywhere;
	private Boolean _showAddButton;
	private Boolean _showNonindexable;
	private Boolean _showScheduled;
	private Long _subtypeSelectionId;
	private String _typeSelection;

}
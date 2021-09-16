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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.model.KBTemplateSearchDisplay;
import com.liferay.knowledge.base.service.KBTemplateServiceUtil;
import com.liferay.knowledge.base.web.internal.search.KBTemplateSearch;
import com.liferay.knowledge.base.web.internal.security.permission.resource.AdminPermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBTemplatePermission;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class KBTemplatesManagementToolbarDisplayContext {

	public KBTemplatesManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, String templatePath)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_templatePath = templatePath;

		_currentURLObj = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_createSearchContainer();
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteKBTemplates");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public List<String> getAvailableActions(KBTemplate kbTemplate)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (KBTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(), kbTemplate,
				ActionKeys.DELETE)) {

			availableActions.add("deleteKBTemplates");
		}

		return availableActions;
	}

	public CreationMenu getCreationMenu() {
		if (Validator.isNotNull(_getKeywords()) ||
			!AdminPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				KBActionKeys.ADD_KB_TEMPLATE)) {

			return null;
		}

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						_templatePath + "edit_template.jsp"
					).setRedirect(
						PortalUtil.getCurrentURL(_httpServletRequest)
					).buildPortletURL());

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "add-template"));
			}
		).build();
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	public String getOrderByType() {
		return _searchContainer.getOrderByType();
	}

	public SearchContainer<KBTemplate> getSearchContainer() {
		return _searchContainer;
	}

	public PortletURL getSearchURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/admin/view_templates.jsp"
		).buildPortletURL();
	}

	public PortletURL getSortingURL() throws PortletException {
		return PortletURLBuilder.create(
			_getCurrentSortingURL()
		).setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc"
		).buildPortletURL();
	}

	public int getTotal() {
		return _searchContainer.getTotal();
	}

	public boolean isDisabled() {
		return !_searchContainer.hasResults();
	}

	private void _createSearchContainer() throws PortalException {
		PortletURL iteratorURL = PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/admin/view_templates.jsp"
		).buildPortletURL();

		_searchContainer = new KBTemplateSearch(
			_liferayPortletRequest, iteratorURL);

		String keywords = _getKeywords();

		if (Validator.isNull(keywords)) {
			_searchContainer.setTotal(
				KBTemplateServiceUtil.getGroupKBTemplatesCount(
					_themeDisplay.getScopeGroupId()));
			_searchContainer.setResults(
				KBTemplateServiceUtil.getGroupKBTemplates(
					_themeDisplay.getScopeGroupId(),
					_searchContainer.getStart(), _searchContainer.getEnd(),
					_searchContainer.getOrderByComparator()));
		}
		else {
			KBTemplateSearchDisplay kbTemplateSearchDisplay =
				KBTemplateServiceUtil.getKBTemplateSearchDisplay(
					_themeDisplay.getScopeGroupId(), keywords, keywords, null,
					null, false, new int[0], _searchContainer.getCur(),
					_searchContainer.getDelta(),
					_searchContainer.getOrderByComparator());

			_searchContainer.setResults(kbTemplateSearchDisplay.getResults());
			_searchContainer.setTotal(kbTemplateSearchDisplay.getTotal());
		}
	}

	private PortletURL _getCurrentSortingURL() throws PortletException {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(_currentURLObj, _liferayPortletResponse)
		).setMVCPath(
			"/admin/view_templates.jsp"
		).buildPortletURL();
	}

	private String _getKeywords() {
		return ParamUtil.getString(_httpServletRequest, "keywords");
	}

	private String _getOrderByCol() {
		return _searchContainer.getOrderByCol();
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				final Map<String, String> orderColumnsMap = HashMapBuilder.put(
					"create-date", "create-date"
				).put(
					"modified-date", "modified-date"
				).put(
					"title", "title"
				).put(
					"user-name", "user-name"
				).build();

				for (Map.Entry<String, String> orderByColEntry :
						orderColumnsMap.entrySet()) {

					add(
						dropdownItem -> {
							String orderByCol = orderByColEntry.getKey();

							dropdownItem.setActive(
								orderByCol.equals(_getOrderByCol()));

							dropdownItem.setHref(
								_getCurrentSortingURL(), "orderByCol",
								orderByColEntry.getValue());
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, orderByCol));
						});
				}
			}
		};
	}

	private final PortletURL _currentURLObj;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private SearchContainer<KBTemplate> _searchContainer;
	private final String _templatePath;
	private final ThemeDisplay _themeDisplay;

}
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

package com.liferay.wiki.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.web.internal.security.permission.resource.WikiNodePermission;
import com.liferay.wiki.web.internal.security.permission.resource.WikiResourcePermission;

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
public class WikiNodesManagementToolbarDisplayContext {

	public WikiNodesManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, String displayStyle,
		SearchContainer<WikiNode> searchContainer, TrashHelper trashHelper) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_displayStyle = displayStyle;
		_searchContainer = searchContainer;
		_trashHelper = trashHelper;

		_currentURLObj = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		_httpServletRequest = liferayPortletRequest.getHttpServletRequest();

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteNodes");

				if (_trashHelper.isTrashEnabled(
						_themeDisplay.getScopeGroupId())) {

					dropdownItem.setIcon("trash");
					dropdownItem.setLabel(
						LanguageUtil.get(
							_httpServletRequest, "move-to-recycle-bin"));
				}
				else {
					dropdownItem.setIcon("times-circle");
					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "delete"));
				}

				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"deleteNodesCmd",
			() -> {
				if (_isTrashEnabled()) {
					return Constants.MOVE_TO_TRASH;
				}

				return Constants.DELETE;
			}
		).put(
			"deleteNodesURL",
			() -> PortletURLBuilder.createActionURL(
				_liferayPortletResponse
			).setActionName(
				"/wiki/edit_node"
			).buildString()
		).put(
			"trashEnabled", _isTrashEnabled()
		).build();
	}

	public List<String> getAvailableActions(WikiNode wikiNode)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (WikiNodePermission.contains(
				_themeDisplay.getPermissionChecker(), wikiNode,
				ActionKeys.DELETE)) {

			availableActions.add("deleteNodes");
		}

		return availableActions;
	}

	public CreationMenu getCreationMenu() {
		if (!WikiResourcePermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), ActionKeys.ADD_NODE)) {

			return null;
		}

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					_liferayPortletResponse.createRenderURL(),
					"mvcRenderCommandName", "/wiki/edit_node", "redirect",
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCRenderCommandName(
						"/wiki_admin/view"
					).buildString());

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "add-wiki"));
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

	public String getSortingOrder() {
		return _getOrderByType();
	}

	public PortletURL getSortingURL() throws PortletException {
		return PortletURLBuilder.create(
			_getPortletURL()
		).setParameter(
			"orderByCol", _getOrderByCol()
		).setParameter(
			"orderByType",
			Objects.equals(_getOrderByType(), "asc") ? "desc" : "asc"
		).buildPortletURL();
	}

	public int getTotalItems() {
		return _searchContainer.getTotal();
	}

	public ViewTypeItemList getViewTypes() throws PortletException {
		return new ViewTypeItemList(_getPortletURL(), _displayStyle) {
			{
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public boolean isDisabled() {
		return !_searchContainer.hasResults();
	}

	public boolean isSelectable() {
		return true;
	}

	public boolean isShowSearch() {
		return false;
	}

	private String _getOrderByCol() {
		return _searchContainer.getOrderByCol();
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				final Map<String, String> orderColumns = HashMapBuilder.put(
					"lastPostDate", "last-post-date"
				).put(
					"name", "name"
				).build();

				for (Map.Entry<String, String> orderByColEntry :
						orderColumns.entrySet()) {

					String orderByCol = orderByColEntry.getKey();

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								orderByCol.equals(_getOrderByCol()));
							dropdownItem.setHref(
								_getPortletURL(), "orderByCol", orderByCol);
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									orderByColEntry.getValue()));
						});
				}
			}
		};
	}

	private String _getOrderByType() {
		return _searchContainer.getOrderByType();
	}

	private PortletURL _getPortletURL() throws PortletException {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(_currentURLObj, _liferayPortletResponse)
		).setMVCRenderCommandName(
			"/wiki_admin/view"
		).buildPortletURL();
	}

	private boolean _isTrashEnabled() {
		try {
			return _trashHelper.isTrashEnabled(
				PortalUtil.getScopeGroupId(_httpServletRequest));
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private final PortletURL _currentURLObj;
	private final String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final SearchContainer<WikiNode> _searchContainer;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}
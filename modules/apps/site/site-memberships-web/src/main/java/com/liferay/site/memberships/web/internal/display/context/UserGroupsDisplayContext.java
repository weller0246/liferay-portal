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

package com.liferay.site.memberships.web.internal.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.persistence.constants.UserGroupFinderConstants;
import com.liferay.portlet.usergroupsadmin.search.UserGroupDisplayTerms;
import com.liferay.portlet.usergroupsadmin.search.UserGroupSearch;
import com.liferay.site.memberships.constants.SiteMembershipsPortletKeys;
import com.liferay.site.memberships.web.internal.util.GroupUtil;

import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class UserGroupsDisplayContext {

	public UserGroupsDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			_httpServletRequest,
			SiteMembershipsPortletKeys.SITE_MEMBERSHIPS_ADMIN,
			"display-style-usergroups", "list");

		return _displayStyle;
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_groupId = ParamUtil.getLong(
			_httpServletRequest, "groupId",
			themeDisplay.getSiteGroupIdOrLiveGroupId());

		return _groupId;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_renderRequest, "keywords");

		return _keywords;
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "all");

		return _navigation;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_renderRequest, "orderByCol", "name");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view.jsp"
		).setRedirect(
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return themeDisplay.getURLCurrent();
			}
		).setKeywords(
			() -> {
				String keywords = getKeywords();

				if (Validator.isNotNull(keywords)) {
					return keywords;
				}

				return null;
			}
		).setNavigation(
			() -> {
				String navigation = getNavigation();

				if (Validator.isNotNull(navigation)) {
					return navigation;
				}

				return null;
			}
		).setTabs1(
			"user-groups"
		).setParameter(
			"displayStyle",
			() -> {
				String displayStyle = getDisplayStyle();

				if (Validator.isNotNull(displayStyle)) {
					return displayStyle;
				}

				return null;
			}
		).setParameter(
			"groupId", getGroupId()
		).setParameter(
			"orderByCol",
			() -> {
				String orderByCol = getOrderByCol();

				if (Validator.isNotNull(orderByCol)) {
					return orderByCol;
				}

				return null;
			}
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = getOrderByType();

				if (Validator.isNotNull(orderByType)) {
					return orderByType;
				}

				return null;
			}
		).setParameter(
			"roleId",
			() -> {
				Role role = getRole();

				if (role != null) {
					return role.getRoleId();
				}

				return null;
			}
		).buildPortletURL();
	}

	public Role getRole() {
		if (_role != null) {
			return _role;
		}

		long roleId = ParamUtil.getLong(_httpServletRequest, "roleId");

		if (roleId > 0) {
			_role = RoleLocalServiceUtil.fetchRole(roleId);
		}

		return _role;
	}

	public SearchContainer<UserGroup> getUserGroupSearchContainer() {
		if (_userGroupSearch != null) {
			return _userGroupSearch;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		UserGroupSearch userGroupSearch = new UserGroupSearch(
			_renderRequest, getPortletURL());

		userGroupSearch.setEmptyResultsMessage(
			LanguageUtil.format(
				ResourceBundleUtil.getBundle(
					themeDisplay.getLocale(), getClass()),
				"no-user-group-was-found-that-is-a-member-of-this-x",
				StringUtil.toLowerCase(
					GroupUtil.getGroupTypeLabel(
						_groupId, themeDisplay.getLocale())),
				false));

		userGroupSearch.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		UserGroupDisplayTerms searchTerms =
			(UserGroupDisplayTerms)userGroupSearch.getSearchTerms();

		LinkedHashMap<String, Object> userGroupParams =
			LinkedHashMapBuilder.<String, Object>put(
				UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_GROUPS,
				Long.valueOf(getGroupId())
			).put(
				UserGroupFinderConstants.PARAM_KEY_USER_GROUP_GROUP_ROLE,
				() -> {
					Role role = getRole();

					if (role != null) {
						return new Long[] {
							Long.valueOf(role.getRoleId()),
							Long.valueOf(getGroupId())
						};
					}

					return null;
				}
			).build();

		int userGroupsCount = UserGroupServiceUtil.searchCount(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			userGroupParams);

		userGroupSearch.setTotal(userGroupsCount);

		List<UserGroup> userGroups = UserGroupServiceUtil.search(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			userGroupParams, userGroupSearch.getStart(),
			userGroupSearch.getEnd(), userGroupSearch.getOrderByComparator());

		userGroupSearch.setResults(userGroups);

		_userGroupSearch = userGroupSearch;

		return _userGroupSearch;
	}

	private String _displayStyle;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Role _role;
	private UserGroupSearch _userGroupSearch;

}
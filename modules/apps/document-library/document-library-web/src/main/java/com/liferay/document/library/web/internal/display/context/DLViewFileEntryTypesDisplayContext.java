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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeServiceUtil;
import com.liferay.document.library.web.internal.security.permission.resource.DLPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class DLViewFileEntryTypesDisplayContext {

	public DLViewFileEntryTypesDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		this.renderResponse = renderResponse;
	}

	public String getClearResultsURL() {
		return getSearchActionURL();
	}

	public CreationMenu getCreationMenu() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (DLPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_DOCUMENT_TYPE)) {

			return CreationMenuBuilder.addPrimaryDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						renderResponse.createRenderURL(),
						"mvcRenderCommandName",
						"/document_library/edit_file_entry_type", "redirect",
						PortalUtil.getCurrentURL(_httpServletRequest));
					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "new"));
				}
			).build();
		}

		return null;
	}

	public String getSearchActionURL() {
		return String.valueOf(getPortletURL());
	}

	public SearchContainer<DLFileEntryType> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<DLFileEntryType> searchContainer = new SearchContainer(
			_renderRequest, new DisplayTerms(_httpServletRequest),
			new DisplayTerms(_httpServletRequest),
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			getPortletURL(), null,
			LanguageUtil.get(_httpServletRequest, "there-are-no-results"));

		DisplayTerms displayTerms = searchContainer.getSearchTerms();

		boolean includeBasicFileEntryType = ParamUtil.getBoolean(
			_renderRequest, "includeBasicFileEntryType");

		int total = DLFileEntryTypeServiceUtil.searchCount(
			themeDisplay.getCompanyId(),
			PortalUtil.getCurrentAndAncestorSiteGroupIds(
				themeDisplay.getScopeGroupId()),
			displayTerms.getKeywords(), includeBasicFileEntryType,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT);

		searchContainer.setTotal(total);

		searchContainer.setResults(
			DLFileEntryTypeServiceUtil.search(
				themeDisplay.getCompanyId(),
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					themeDisplay.getScopeGroupId()),
				displayTerms.getKeywords(), includeBasicFileEntryType,
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT,
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator()));

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public int getTotalItems() throws PortalException {
		SearchContainer<DLFileEntryType> searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	public boolean isSearchDisabled() throws PortalException {
		SearchContainer<DLFileEntryType> searchContainer = getSearchContainer();

		DisplayTerms displayTerms = searchContainer.getSearchTerms();

		if ((searchContainer.getTotal() == 0) &&
			Validator.isNull(displayTerms.getKeywords())) {

			return true;
		}

		return false;
	}

	protected PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			renderResponse
		).setNavigation(
			"file_entry_types"
		).buildPortletURL();
	}

	protected final RenderResponse renderResponse;

	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private SearchContainer<DLFileEntryType> _searchContainer;

}
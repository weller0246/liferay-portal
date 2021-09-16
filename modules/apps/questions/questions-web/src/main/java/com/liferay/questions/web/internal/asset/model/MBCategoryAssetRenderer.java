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

package com.liferay.questions.web.internal.asset.model;

import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Javier Gamarra
 */
public class MBCategoryAssetRenderer extends BaseJSPAssetRenderer<MBCategory> {

	public MBCategoryAssetRenderer(
		Company company, String historyRouterBasePath, MBCategory mbCategory,
		ModelResourcePermission<MBCategory> mbCategoryModelResourcePermission) {

		_company = company;
		_historyRouterBasePath = historyRouterBasePath;
		_mbCategory = mbCategory;
		_mbCategoryModelResourcePermission = mbCategoryModelResourcePermission;
	}

	@Override
	public MBCategory getAssetObject() {
		return _mbCategory;
	}

	@Override
	public String getClassName() {
		return MBCategory.class.getName();
	}

	@Override
	public long getClassPK() {
		return _mbCategory.getCategoryId();
	}

	@Override
	public long getGroupId() {
		return _mbCategory.getGroupId();
	}

	@Override
	public String getJspPath(
		HttpServletRequest httpServletRequest, String template) {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/message_boards/asset/" + template + ".jsp";
		}

		return null;
	}

	@Override
	public int getStatus() {
		return _mbCategory.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return _mbCategory.getDescription();
	}

	@Override
	public String getTitle(Locale locale) {
		return _mbCategory.getName();
	}

	@Override
	public PortletURL getURLEdit(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		return null;
	}

	@Override
	public String getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws PortalException {

		return StringBundler.concat(
			_company.getPortalURL(_mbCategory.getGroupId()),
			_historyRouterBasePath, "/questions/", _mbCategory.getCategoryId());
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return null;
	}

	@Override
	public long getUserId() {
		return _mbCategory.getUserId();
	}

	@Override
	public String getUserName() {
		return _mbCategory.getUserName();
	}

	@Override
	public String getUuid() {
		return _mbCategory.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return _mbCategoryModelResourcePermission.contains(
			permissionChecker, _mbCategory, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return _mbCategoryModelResourcePermission.contains(
			permissionChecker, _mbCategory, ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		httpServletRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_CATEGORY, _mbCategory);

		return super.include(httpServletRequest, httpServletResponse, template);
	}

	private final Company _company;
	private final String _historyRouterBasePath;
	private final MBCategory _mbCategory;
	private final ModelResourcePermission<MBCategory>
		_mbCategoryModelResourcePermission;

}
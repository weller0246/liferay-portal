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

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.permission.MBDiscussionPermission;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
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
public class MBMessageAssetRenderer
	extends BaseJSPAssetRenderer<MBMessage> implements TrashRenderer {

	public MBMessageAssetRenderer(
		Company company, String historyRouterPath, MBMessage mbMessage,
		ModelResourcePermission<MBMessage> mbMessageModelResourcePermission) {

		_company = company;
		_historyRouterPath = historyRouterPath;
		_mbMessage = mbMessage;
		_mbMessageModelResourcePermission = mbMessageModelResourcePermission;
	}

	@Override
	public MBMessage getAssetObject() {
		return _mbMessage;
	}

	@Override
	public String getClassName() {
		return MBMessage.class.getName();
	}

	@Override
	public long getClassPK() {
		return _mbMessage.getMessageId();
	}

	@Override
	public long getGroupId() {
		return _mbMessage.getGroupId();
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
	public String getPortletId() {
		AssetRendererFactory<MBMessage> assetRendererFactory =
			getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public String getSearchSummary(Locale locale) {
		return getSummary(null, null);
	}

	@Override
	public int getStatus() {
		return _mbMessage.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return _mbMessage.getBody();
	}

	@Override
	public String getTitle(Locale locale) {
		return _mbMessage.getSubject();
	}

	@Override
	public String getType() {
		return MBMessageAssetRendererFactory.TYPE;
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

		return _getQuestionsURL(_company.getPortalURL(_mbMessage.getGroupId()));
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _getQuestionsURL(themeDisplay.getPortalURL());
	}

	@Override
	public long getUserId() {
		if (_mbMessage.isAnonymous()) {
			return 0;
		}

		return _mbMessage.getUserId();
	}

	@Override
	public String getUserName() {
		if (_mbMessage.isAnonymous()) {
			return LanguageUtil.get(
				LocaleThreadLocal.getDefaultLocale(), "anonymous");
		}

		return _mbMessage.getUserName();
	}

	@Override
	public String getUuid() {
		return _mbMessage.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		if (_mbMessage.isDiscussion()) {
			return MBDiscussionPermission.contains(
				permissionChecker, _mbMessage, ActionKeys.UPDATE);
		}

		return _mbMessageModelResourcePermission.contains(
			permissionChecker, _mbMessage, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		if (_mbMessage.isDiscussion()) {
			return MBDiscussionPermission.contains(
				permissionChecker, _mbMessage, ActionKeys.VIEW);
		}

		return _mbMessageModelResourcePermission.contains(
			permissionChecker, _mbMessage, ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		httpServletRequest.setAttribute(
			WebKeys.MESSAGE_BOARDS_MESSAGE, _mbMessage);

		return super.include(httpServletRequest, httpServletResponse, template);
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	private String _getQuestionsURL(String portalURL) {
		return StringBundler.concat(
			portalURL, _historyRouterPath, "/questions/question/",
			_mbMessage.getMessageId());
	}

	private final Company _company;
	private final String _historyRouterPath;
	private final MBMessage _mbMessage;
	private final ModelResourcePermission<MBMessage>
		_mbMessageModelResourcePermission;

}
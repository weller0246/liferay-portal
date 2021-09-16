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

package com.liferay.account.admin.web.internal.portlet.action;

import com.liferay.account.admin.web.internal.constants.AccountWebKeys;
import com.liferay.account.admin.web.internal.display.AccountEntryDisplay;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Albert Lee
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT,
		"mvc.command.name=/account_admin/edit_account_entry"
	},
	service = MVCRenderCommand.class
)
public class EditAccountEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long accountEntryId = ParamUtil.getLong(
			renderRequest, "accountEntryId");

		renderRequest.setAttribute(
			AccountWebKeys.ACCOUNT_ENTRY_DISPLAY,
			AccountEntryDisplay.of(accountEntryId));

		return "/account_entries_admin/edit_account_entry.jsp";
	}

}
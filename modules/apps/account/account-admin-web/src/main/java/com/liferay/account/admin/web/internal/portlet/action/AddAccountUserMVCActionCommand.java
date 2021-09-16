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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT,
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_USERS_ADMIN,
		"mvc.command.name=/account_admin/add_account_user"
	},
	service = MVCActionCommand.class
)
public class AddAccountUserMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long accountEntryId = ParamUtil.getLong(
			actionRequest, "accountEntryId");
		String screenName = ParamUtil.getString(actionRequest, "screenName");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		String languageId = ParamUtil.getString(actionRequest, "languageId");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String middleName = ParamUtil.getString(actionRequest, "middleName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		long prefixId = ParamUtil.getLong(actionRequest, "prefixId");
		long suffixId = ParamUtil.getLong(actionRequest, "suffixId");

		try {
			AccountEntryUserRel accountEntryUserRel;

			AccountEntry accountEntry =
				_accountEntryLocalService.fetchAccountEntry(accountEntryId);

			if ((accountEntry != null) &&
				Objects.equals(
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON,
					accountEntry.getType())) {

				accountEntryUserRel =
					_accountEntryUserRelLocalService.
						addPersonTypeAccountEntryUserRel(
							accountEntryId, themeDisplay.getUserId(),
							screenName, emailAddress,
							LocaleUtil.fromLanguageId(languageId), firstName,
							middleName, lastName, prefixId, suffixId);
			}
			else {
				accountEntryUserRel =
					_accountEntryUserRelLocalService.addAccountEntryUserRel(
						accountEntryId, themeDisplay.getUserId(), screenName,
						emailAddress, LocaleUtil.fromLanguageId(languageId),
						firstName, middleName, lastName, prefixId, suffixId);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				redirect = _http.setParameter(
					redirect, actionResponse.getNamespace() + "p_u_i_d",
					accountEntryUserRel.getAccountUserId());

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (PortalException portalException) {
			if (portalException instanceof UserEmailAddressException ||
				portalException instanceof UserScreenNameException) {

				SessionErrors.add(
					actionRequest, portalException.getClass(), portalException);

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "/account_admin/add_account_user");
			}
			else {
				throw portalException;
			}
		}
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private Http _http;

}
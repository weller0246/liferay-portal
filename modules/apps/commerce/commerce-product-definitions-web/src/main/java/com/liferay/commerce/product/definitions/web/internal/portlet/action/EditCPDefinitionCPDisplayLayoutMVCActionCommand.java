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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPDisplayLayoutEntryException;
import com.liferay.commerce.product.exception.CPDisplayLayoutLayoutUuidException;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDisplayLayoutService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_CHANNELS,
		"mvc.command.name=/commerce_channels/edit_cp_definition_cp_display_layout"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionCPDisplayLayoutMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCPDisplayLayouts(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPDisplayLayoutIds = null;

		long cpDisplayLayoutId = ParamUtil.getLong(
			actionRequest, "cpDisplayLayoutId");

		if (cpDisplayLayoutId > 0) {
			deleteCPDisplayLayoutIds = new long[] {cpDisplayLayoutId};
		}
		else {
			deleteCPDisplayLayoutIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPDisplayLayoutIds"),
				0L);
		}

		for (long deleteCPDisplayLayoutId : deleteCPDisplayLayoutIds) {
			_cpDisplayLayoutService.deleteCPDisplayLayout(
				deleteCPDisplayLayoutId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCPDisplayLayout(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPDisplayLayouts(actionRequest);
			}
			else if (cmd.equals("setDefaultLayout")) {
				setDefaultLayout(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchCPDisplayLayoutException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof CPDisplayLayoutEntryException ||
					 exception instanceof CPDisplayLayoutLayoutUuidException ||
					 exception instanceof NoSuchCPDefinitionException) {

				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/commerce_channels/edit_cp_definition_cp_display_layout");
			}
			else {
				_log.error(exception, exception);

				throw exception;
			}
		}
	}

	protected void setDefaultLayout(ActionRequest actionRequest)
		throws Exception {

		long commerceChannelId = ParamUtil.getLong(
			actionRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				commerceChannel.getGroupId(),
				CPConstants.RESOURCE_NAME_CP_DISPLAY_LAYOUT));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		String layoutUuid = ParamUtil.getString(actionRequest, "layoutUuid");

		modifiableSettings.setValue("productLayoutUuid", layoutUuid);

		modifiableSettings.store();
	}

	protected void updateCPDisplayLayout(ActionRequest actionRequest)
		throws PortalException {

		long cpDisplayLayoutId = ParamUtil.getLong(
			actionRequest, "cpDisplayLayoutId");

		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		String layoutUuid = ParamUtil.getString(actionRequest, "layoutUuid");

		if (cpDisplayLayoutId > 0) {
			_cpDisplayLayoutService.updateCPDisplayLayout(
				cpDisplayLayoutId, classPK, layoutUuid);
		}
		else {
			long commerceChannelId = ParamUtil.getLong(
				actionRequest, "commerceChannelId");

			CommerceChannel commerceChannel =
				_commerceChannelService.getCommerceChannel(commerceChannelId);

			_cpDisplayLayoutService.addCPDisplayLayout(
				commerceChannel.getSiteGroupId(), CPDefinition.class, classPK,
				layoutUuid);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCPDefinitionCPDisplayLayoutMVCActionCommand.class);

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CPDisplayLayoutService _cpDisplayLayoutService;

	@Reference
	private Portal _portal;

	@Reference
	private SettingsFactory _settingsFactory;

}
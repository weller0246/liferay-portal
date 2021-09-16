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

package com.liferay.object.web.internal.object.entries.portlet.action;

import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Marco Leo
 */
public class EditObjectEntryMVCActionCommand extends BaseMVCActionCommand {

	public EditObjectEntryMVCActionCommand(
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryService objectEntryService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		Portal portal) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryService = objectEntryService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_portal = portal;
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
			_addOrUpdateObjectEntry(actionRequest, actionResponse);
		}
	}

	private void _addOrUpdateObjectEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long objectEntryId = ParamUtil.getLong(
				actionRequest, "objectEntryId");

			long objectDefinitionId = ParamUtil.getLong(
				actionRequest, "objectDefinitionId");

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectDefinitionId);

			if (objectEntryId == 0) {
				_objectEntryService.addObjectEntry(
					_getGroupId(actionRequest, objectDefinition),
					objectDefinition.getObjectDefinitionId(),
					_getValues(actionRequest),
					ServiceContextFactory.getInstance(
						objectDefinition.getClassName(), actionRequest));
			}
			else {
				_objectEntryService.updateObjectEntry(
					objectEntryId, _getValues(actionRequest),
					ServiceContextFactory.getInstance(
						objectDefinition.getClassName(), actionRequest));
			}
		}
		catch (Exception exception) {
			if (exception instanceof ObjectDefinitionScopeException ||
				exception instanceof ObjectEntryValuesException) {

				SessionErrors.add(actionRequest, exception.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw exception;
			}
		}
	}

	private long _getGroupId(
			ActionRequest actionRequest, ObjectDefinition objectDefinition)
		throws Exception {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		return objectScopeProvider.getGroupId(
			_portal.getHttpServletRequest(actionRequest));
	}

	private Map<String, Serializable> _getValues(ActionRequest actionRequest) {
		String ddmFormValues = ParamUtil.getString(
			actionRequest, "ddmFormValues");

		return (Map<String, Serializable>)JSONFactoryUtil.looseDeserialize(
			ddmFormValues);
	}

	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryService _objectEntryService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final Portal _portal;

}
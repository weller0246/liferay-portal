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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADAnonymousUserProvider;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.display.UADHierarchyDisplay;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/user_associated_data/anonymize_uad_entities"
	},
	service = MVCActionCommand.class
)
public class AnonymizeUADEntitiesMVCActionCommand
	extends BaseUADMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		User selectedUser = getSelectedUser(actionRequest);

		User anonymousUser = _uadAnonymousUserProvider.getAnonymousUser(
			selectedUser.getCompanyId());

		String applicationKey = ParamUtil.getString(
			actionRequest, "applicationKey");

		UADHierarchyDisplay uadHierarchyDisplay =
			uadRegistry.getUADHierarchyDisplay(applicationKey);

		for (String entityType : getEntityTypes(actionRequest)) {
			String[] primaryKeys = getPrimaryKeys(actionRequest, entityType);

			UADAnonymizer<Object> entityUADAnonymizer =
				(UADAnonymizer<Object>)getUADAnonymizer(
					actionRequest, entityType);
			UADDisplay<Object> entityUADDisplay =
				(UADDisplay<Object>)getUADDisplay(actionRequest, entityType);

			for (String primaryKey : primaryKeys) {
				_anonymize(
					anonymousUser, entityUADAnonymizer, entityUADDisplay,
					primaryKey, selectedUser.getUserId(), uadHierarchyDisplay);
			}
		}

		doReviewableRedirect(actionRequest, actionResponse);
	}

	private void _anonymize(
			User anonymousUser, UADAnonymizer<Object> entityUADAnonymizer,
			UADDisplay<Object> entityUADDisplay, String primaryKey,
			long selectedUserId, UADHierarchyDisplay uadHierarchyDisplay)
		throws Exception {

		Object entity = entityUADDisplay.get(primaryKey);

		if (uadHierarchyDisplay != null) {
			if (uadHierarchyDisplay.isUserOwned(entity, selectedUserId)) {
				entityUADAnonymizer.autoAnonymize(
					entity, selectedUserId, anonymousUser);
			}

			Map<Class<?>, List<Serializable>> containerItemPKsMap =
				uadHierarchyDisplay.getContainerItemPKsMap(
					entityUADDisplay.getTypeClass(),
					uadHierarchyDisplay.getPrimaryKey(entity), selectedUserId);

			for (Map.Entry<Class<?>, List<Serializable>> entry :
					containerItemPKsMap.entrySet()) {

				Class<?> containerItemClass = entry.getKey();

				UADAnonymizer<Object> containerItemUADAnonymizer =
					(UADAnonymizer<Object>)uadRegistry.getUADAnonymizer(
						containerItemClass.getName());
				UADDisplay<Object> containerItemUADDisplay =
					(UADDisplay<Object>)uadRegistry.getUADDisplay(
						containerItemClass.getName());

				doMultipleAction(
					entry.getValue(),
					containerItemPK -> {
						Object containerItem = containerItemUADDisplay.get(
							containerItemPK);

						containerItemUADAnonymizer.autoAnonymize(
							containerItem, selectedUserId, anonymousUser);
					});
			}
		}
		else {
			entityUADAnonymizer.autoAnonymize(
				entity, selectedUserId, anonymousUser);
		}
	}

	@Reference
	private UADAnonymousUserProvider _uadAnonymousUserProvider;

}
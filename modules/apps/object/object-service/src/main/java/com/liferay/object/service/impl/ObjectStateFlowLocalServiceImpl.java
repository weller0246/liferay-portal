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

package com.liferay.object.service.impl;

import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.service.base.ObjectStateFlowLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectStateFlow",
	service = AopService.class
)
public class ObjectStateFlowLocalServiceImpl
	extends ObjectStateFlowLocalServiceBaseImpl {

	@Override
	public ObjectStateFlow addObjectStateFlow(long userId, long objectFieldId)
		throws PortalException {

		ObjectStateFlow objectStateFlow = createObjectStateFlow(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectStateFlow.setCompanyId(user.getCompanyId());
		objectStateFlow.setUserId(user.getUserId());
		objectStateFlow.setUserName(user.getFullName());

		objectStateFlow.setObjectFieldId(objectFieldId);

		return addObjectStateFlow(objectStateFlow);
	}

	@Reference
	private UserLocalService _userLocalService;

}
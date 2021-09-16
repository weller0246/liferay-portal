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

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectEntryService}.
 *
 * @author Marco Leo
 * @see ObjectEntryService
 * @generated
 */
public class ObjectEntryServiceWrapper
	implements ObjectEntryService, ServiceWrapper<ObjectEntryService> {

	public ObjectEntryServiceWrapper(ObjectEntryService objectEntryService) {
		_objectEntryService = objectEntryService;
	}

	@Override
	public com.liferay.object.model.ObjectEntry addObjectEntry(
			long groupId, long objectDefinitionId,
			java.util.Map<String, java.io.Serializable> values,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectEntryService.addObjectEntry(
			groupId, objectDefinitionId, values, serviceContext);
	}

	@Override
	public com.liferay.object.model.ObjectEntry addOrUpdateObjectEntry(
			String externalReferenceCode, long groupId, long objectDefinitionId,
			java.util.Map<String, java.io.Serializable> values,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectEntryService.addOrUpdateObjectEntry(
			externalReferenceCode, groupId, objectDefinitionId, values,
			serviceContext);
	}

	@Override
	public com.liferay.object.model.ObjectEntry deleteObjectEntry(
			long objectEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectEntryService.deleteObjectEntry(objectEntryId);
	}

	@Override
	public com.liferay.object.model.ObjectEntry fetchObjectEntry(
			long objectEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectEntryService.fetchObjectEntry(objectEntryId);
	}

	@Override
	public com.liferay.object.model.ObjectEntry getObjectEntry(
			long objectEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectEntryService.getObjectEntry(objectEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.object.model.ObjectEntry updateObjectEntry(
			long objectEntryId,
			java.util.Map<String, java.io.Serializable> values,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectEntryService.updateObjectEntry(
			objectEntryId, values, serviceContext);
	}

	@Override
	public ObjectEntryService getWrappedService() {
		return _objectEntryService;
	}

	@Override
	public void setWrappedService(ObjectEntryService objectEntryService) {
		_objectEntryService = objectEntryService;
	}

	private ObjectEntryService _objectEntryService;

}
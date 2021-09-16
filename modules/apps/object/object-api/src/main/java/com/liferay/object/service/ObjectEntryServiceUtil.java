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

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.Serializable;

import java.util.Map;

/**
 * Provides the remote service utility for ObjectEntry. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see ObjectEntryService
 * @generated
 */
public class ObjectEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ObjectEntry addObjectEntry(
			long groupId, long objectDefinitionId,
			Map<String, Serializable> values,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addObjectEntry(
			groupId, objectDefinitionId, values, serviceContext);
	}

	public static ObjectEntry addOrUpdateObjectEntry(
			String externalReferenceCode, long groupId, long objectDefinitionId,
			Map<String, Serializable> values,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateObjectEntry(
			externalReferenceCode, groupId, objectDefinitionId, values,
			serviceContext);
	}

	public static ObjectEntry deleteObjectEntry(long objectEntryId)
		throws PortalException {

		return getService().deleteObjectEntry(objectEntryId);
	}

	public static ObjectEntry fetchObjectEntry(long objectEntryId)
		throws PortalException {

		return getService().fetchObjectEntry(objectEntryId);
	}

	public static ObjectEntry getObjectEntry(long objectEntryId)
		throws PortalException {

		return getService().getObjectEntry(objectEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ObjectEntry updateObjectEntry(
			long objectEntryId, Map<String, Serializable> values,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateObjectEntry(
			objectEntryId, values, serviceContext);
	}

	public static ObjectEntryService getService() {
		return _service;
	}

	private static volatile ObjectEntryService _service;

}
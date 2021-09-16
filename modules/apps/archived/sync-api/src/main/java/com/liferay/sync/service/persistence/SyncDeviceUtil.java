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

package com.liferay.sync.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.sync.model.SyncDevice;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the sync device service. This utility wraps <code>com.liferay.sync.service.persistence.impl.SyncDevicePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SyncDevicePersistence
 * @generated
 */
public class SyncDeviceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(SyncDevice syncDevice) {
		getPersistence().clearCache(syncDevice);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, SyncDevice> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SyncDevice> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SyncDevice> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SyncDevice> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SyncDevice update(SyncDevice syncDevice) {
		return getPersistence().update(syncDevice);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SyncDevice update(
		SyncDevice syncDevice, ServiceContext serviceContext) {

		return getPersistence().update(syncDevice, serviceContext);
	}

	/**
	 * Returns all the sync devices where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sync devices
	 */
	public static List<SyncDevice> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the sync devices where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @return the range of matching sync devices
	 */
	public static List<SyncDevice> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the sync devices where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync devices
	 */
	public static List<SyncDevice> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sync devices where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync devices
	 */
	public static List<SyncDevice> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SyncDevice> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sync device in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync device
	 * @throws NoSuchDeviceException if a matching sync device could not be found
	 */
	public static SyncDevice findByUuid_First(
			String uuid, OrderByComparator<SyncDevice> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first sync device in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync device, or <code>null</code> if a matching sync device could not be found
	 */
	public static SyncDevice fetchByUuid_First(
		String uuid, OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last sync device in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync device
	 * @throws NoSuchDeviceException if a matching sync device could not be found
	 */
	public static SyncDevice findByUuid_Last(
			String uuid, OrderByComparator<SyncDevice> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last sync device in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync device, or <code>null</code> if a matching sync device could not be found
	 */
	public static SyncDevice fetchByUuid_Last(
		String uuid, OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the sync devices before and after the current sync device in the ordered set where uuid = &#63;.
	 *
	 * @param syncDeviceId the primary key of the current sync device
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync device
	 * @throws NoSuchDeviceException if a sync device with the primary key could not be found
	 */
	public static SyncDevice[] findByUuid_PrevAndNext(
			long syncDeviceId, String uuid,
			OrderByComparator<SyncDevice> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByUuid_PrevAndNext(
			syncDeviceId, uuid, orderByComparator);
	}

	/**
	 * Removes all the sync devices where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of sync devices where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sync devices
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the sync devices where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sync devices
	 */
	public static List<SyncDevice> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the sync devices where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @return the range of matching sync devices
	 */
	public static List<SyncDevice> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the sync devices where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync devices
	 */
	public static List<SyncDevice> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sync devices where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync devices
	 */
	public static List<SyncDevice> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SyncDevice> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sync device in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync device
	 * @throws NoSuchDeviceException if a matching sync device could not be found
	 */
	public static SyncDevice findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SyncDevice> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first sync device in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync device, or <code>null</code> if a matching sync device could not be found
	 */
	public static SyncDevice fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last sync device in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync device
	 * @throws NoSuchDeviceException if a matching sync device could not be found
	 */
	public static SyncDevice findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SyncDevice> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last sync device in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync device, or <code>null</code> if a matching sync device could not be found
	 */
	public static SyncDevice fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the sync devices before and after the current sync device in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param syncDeviceId the primary key of the current sync device
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync device
	 * @throws NoSuchDeviceException if a sync device with the primary key could not be found
	 */
	public static SyncDevice[] findByUuid_C_PrevAndNext(
			long syncDeviceId, String uuid, long companyId,
			OrderByComparator<SyncDevice> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByUuid_C_PrevAndNext(
			syncDeviceId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the sync devices where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of sync devices where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sync devices
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the sync devices where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching sync devices
	 */
	public static List<SyncDevice> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the sync devices where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @return the range of matching sync devices
	 */
	public static List<SyncDevice> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the sync devices where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync devices
	 */
	public static List<SyncDevice> findByUserId(
		long userId, int start, int end,
		OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sync devices where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync devices
	 */
	public static List<SyncDevice> findByUserId(
		long userId, int start, int end,
		OrderByComparator<SyncDevice> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sync device in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync device
	 * @throws NoSuchDeviceException if a matching sync device could not be found
	 */
	public static SyncDevice findByUserId_First(
			long userId, OrderByComparator<SyncDevice> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first sync device in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync device, or <code>null</code> if a matching sync device could not be found
	 */
	public static SyncDevice fetchByUserId_First(
		long userId, OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last sync device in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync device
	 * @throws NoSuchDeviceException if a matching sync device could not be found
	 */
	public static SyncDevice findByUserId_Last(
			long userId, OrderByComparator<SyncDevice> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last sync device in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync device, or <code>null</code> if a matching sync device could not be found
	 */
	public static SyncDevice fetchByUserId_Last(
		long userId, OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the sync devices before and after the current sync device in the ordered set where userId = &#63;.
	 *
	 * @param syncDeviceId the primary key of the current sync device
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync device
	 * @throws NoSuchDeviceException if a sync device with the primary key could not be found
	 */
	public static SyncDevice[] findByUserId_PrevAndNext(
			long syncDeviceId, long userId,
			OrderByComparator<SyncDevice> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByUserId_PrevAndNext(
			syncDeviceId, userId, orderByComparator);
	}

	/**
	 * Removes all the sync devices where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of sync devices where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching sync devices
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns all the sync devices where companyId = &#63; and userName LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param userName the user name
	 * @return the matching sync devices
	 */
	public static List<SyncDevice> findByC_LikeU(
		long companyId, String userName) {

		return getPersistence().findByC_LikeU(companyId, userName);
	}

	/**
	 * Returns a range of all the sync devices where companyId = &#63; and userName LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userName the user name
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @return the range of matching sync devices
	 */
	public static List<SyncDevice> findByC_LikeU(
		long companyId, String userName, int start, int end) {

		return getPersistence().findByC_LikeU(companyId, userName, start, end);
	}

	/**
	 * Returns an ordered range of all the sync devices where companyId = &#63; and userName LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userName the user name
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync devices
	 */
	public static List<SyncDevice> findByC_LikeU(
		long companyId, String userName, int start, int end,
		OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().findByC_LikeU(
			companyId, userName, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sync devices where companyId = &#63; and userName LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userName the user name
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync devices
	 */
	public static List<SyncDevice> findByC_LikeU(
		long companyId, String userName, int start, int end,
		OrderByComparator<SyncDevice> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_LikeU(
			companyId, userName, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sync device in the ordered set where companyId = &#63; and userName LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param userName the user name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync device
	 * @throws NoSuchDeviceException if a matching sync device could not be found
	 */
	public static SyncDevice findByC_LikeU_First(
			long companyId, String userName,
			OrderByComparator<SyncDevice> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByC_LikeU_First(
			companyId, userName, orderByComparator);
	}

	/**
	 * Returns the first sync device in the ordered set where companyId = &#63; and userName LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param userName the user name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync device, or <code>null</code> if a matching sync device could not be found
	 */
	public static SyncDevice fetchByC_LikeU_First(
		long companyId, String userName,
		OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().fetchByC_LikeU_First(
			companyId, userName, orderByComparator);
	}

	/**
	 * Returns the last sync device in the ordered set where companyId = &#63; and userName LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param userName the user name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync device
	 * @throws NoSuchDeviceException if a matching sync device could not be found
	 */
	public static SyncDevice findByC_LikeU_Last(
			long companyId, String userName,
			OrderByComparator<SyncDevice> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByC_LikeU_Last(
			companyId, userName, orderByComparator);
	}

	/**
	 * Returns the last sync device in the ordered set where companyId = &#63; and userName LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param userName the user name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync device, or <code>null</code> if a matching sync device could not be found
	 */
	public static SyncDevice fetchByC_LikeU_Last(
		long companyId, String userName,
		OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().fetchByC_LikeU_Last(
			companyId, userName, orderByComparator);
	}

	/**
	 * Returns the sync devices before and after the current sync device in the ordered set where companyId = &#63; and userName LIKE &#63;.
	 *
	 * @param syncDeviceId the primary key of the current sync device
	 * @param companyId the company ID
	 * @param userName the user name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync device
	 * @throws NoSuchDeviceException if a sync device with the primary key could not be found
	 */
	public static SyncDevice[] findByC_LikeU_PrevAndNext(
			long syncDeviceId, long companyId, String userName,
			OrderByComparator<SyncDevice> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByC_LikeU_PrevAndNext(
			syncDeviceId, companyId, userName, orderByComparator);
	}

	/**
	 * Removes all the sync devices where companyId = &#63; and userName LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userName the user name
	 */
	public static void removeByC_LikeU(long companyId, String userName) {
		getPersistence().removeByC_LikeU(companyId, userName);
	}

	/**
	 * Returns the number of sync devices where companyId = &#63; and userName LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param userName the user name
	 * @return the number of matching sync devices
	 */
	public static int countByC_LikeU(long companyId, String userName) {
		return getPersistence().countByC_LikeU(companyId, userName);
	}

	/**
	 * Caches the sync device in the entity cache if it is enabled.
	 *
	 * @param syncDevice the sync device
	 */
	public static void cacheResult(SyncDevice syncDevice) {
		getPersistence().cacheResult(syncDevice);
	}

	/**
	 * Caches the sync devices in the entity cache if it is enabled.
	 *
	 * @param syncDevices the sync devices
	 */
	public static void cacheResult(List<SyncDevice> syncDevices) {
		getPersistence().cacheResult(syncDevices);
	}

	/**
	 * Creates a new sync device with the primary key. Does not add the sync device to the database.
	 *
	 * @param syncDeviceId the primary key for the new sync device
	 * @return the new sync device
	 */
	public static SyncDevice create(long syncDeviceId) {
		return getPersistence().create(syncDeviceId);
	}

	/**
	 * Removes the sync device with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDeviceId the primary key of the sync device
	 * @return the sync device that was removed
	 * @throws NoSuchDeviceException if a sync device with the primary key could not be found
	 */
	public static SyncDevice remove(long syncDeviceId)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().remove(syncDeviceId);
	}

	public static SyncDevice updateImpl(SyncDevice syncDevice) {
		return getPersistence().updateImpl(syncDevice);
	}

	/**
	 * Returns the sync device with the primary key or throws a <code>NoSuchDeviceException</code> if it could not be found.
	 *
	 * @param syncDeviceId the primary key of the sync device
	 * @return the sync device
	 * @throws NoSuchDeviceException if a sync device with the primary key could not be found
	 */
	public static SyncDevice findByPrimaryKey(long syncDeviceId)
		throws com.liferay.sync.exception.NoSuchDeviceException {

		return getPersistence().findByPrimaryKey(syncDeviceId);
	}

	/**
	 * Returns the sync device with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param syncDeviceId the primary key of the sync device
	 * @return the sync device, or <code>null</code> if a sync device with the primary key could not be found
	 */
	public static SyncDevice fetchByPrimaryKey(long syncDeviceId) {
		return getPersistence().fetchByPrimaryKey(syncDeviceId);
	}

	/**
	 * Returns all the sync devices.
	 *
	 * @return the sync devices
	 */
	public static List<SyncDevice> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the sync devices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @return the range of sync devices
	 */
	public static List<SyncDevice> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the sync devices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sync devices
	 */
	public static List<SyncDevice> findAll(
		int start, int end, OrderByComparator<SyncDevice> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sync devices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDeviceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync devices
	 * @param end the upper bound of the range of sync devices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sync devices
	 */
	public static List<SyncDevice> findAll(
		int start, int end, OrderByComparator<SyncDevice> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the sync devices from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of sync devices.
	 *
	 * @return the number of sync devices
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SyncDevicePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SyncDevicePersistence, SyncDevicePersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SyncDevicePersistence.class);

		ServiceTracker<SyncDevicePersistence, SyncDevicePersistence>
			serviceTracker =
				new ServiceTracker
					<SyncDevicePersistence, SyncDevicePersistence>(
						bundle.getBundleContext(), SyncDevicePersistence.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
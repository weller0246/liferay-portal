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

package com.liferay.commerce.price.list.service.persistence;

import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the commerce price list channel rel service. This utility wraps <code>com.liferay.commerce.price.list.service.persistence.impl.CommercePriceListChannelRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListChannelRelPersistence
 * @generated
 */
public class CommercePriceListChannelRelUtil {

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
	public static void clearCache(
		CommercePriceListChannelRel commercePriceListChannelRel) {

		getPersistence().clearCache(commercePriceListChannelRel);
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
	public static Map<Serializable, CommercePriceListChannelRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommercePriceListChannelRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePriceListChannelRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePriceListChannelRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePriceListChannelRel update(
		CommercePriceListChannelRel commercePriceListChannelRel) {

		return getPersistence().update(commercePriceListChannelRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePriceListChannelRel update(
		CommercePriceListChannelRel commercePriceListChannelRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commercePriceListChannelRel, serviceContext);
	}

	/**
	 * Returns all the commerce price list channel rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce price list channel rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @return the range of matching commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel findByUuid_First(
			String uuid,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel findByUuid_Last(
			String uuid,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce price list channel rels before and after the current commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the current commerce price list channel rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	public static CommercePriceListChannelRel[] findByUuid_PrevAndNext(
			long CommercePriceListChannelRelId, String uuid,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().findByUuid_PrevAndNext(
			CommercePriceListChannelRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce price list channel rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce price list channel rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list channel rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @return the range of matching commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce price list channel rels before and after the current commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the current commerce price list channel rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	public static CommercePriceListChannelRel[] findByUuid_C_PrevAndNext(
			long CommercePriceListChannelRelId, String uuid, long companyId,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			CommercePriceListChannelRelId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce price list channel rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list channel rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findByCommercePriceListId(
		long commercePriceListId) {

		return getPersistence().findByCommercePriceListId(commercePriceListId);
	}

	/**
	 * Returns a range of all the commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @return the range of matching commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {

		return getPersistence().findByCommercePriceListId(
			commercePriceListId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return getPersistence().findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel findByCommercePriceListId_First(
			long commercePriceListId,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().findByCommercePriceListId_First(
			commercePriceListId, orderByComparator);
	}

	/**
	 * Returns the first commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return getPersistence().fetchByCommercePriceListId_First(
			commercePriceListId, orderByComparator);
	}

	/**
	 * Returns the last commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel findByCommercePriceListId_Last(
			long commercePriceListId,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().findByCommercePriceListId_Last(
			commercePriceListId, orderByComparator);
	}

	/**
	 * Returns the last commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return getPersistence().fetchByCommercePriceListId_Last(
			commercePriceListId, orderByComparator);
	}

	/**
	 * Returns the commerce price list channel rels before and after the current commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the current commerce price list channel rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	public static CommercePriceListChannelRel[]
			findByCommercePriceListId_PrevAndNext(
				long CommercePriceListChannelRelId, long commercePriceListId,
				OrderByComparator<CommercePriceListChannelRel>
					orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().findByCommercePriceListId_PrevAndNext(
			CommercePriceListChannelRelId, commercePriceListId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce price list channel rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	public static void removeByCommercePriceListId(long commercePriceListId) {
		getPersistence().removeByCommercePriceListId(commercePriceListId);
	}

	/**
	 * Returns the number of commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list channel rels
	 */
	public static int countByCommercePriceListId(long commercePriceListId) {
		return getPersistence().countByCommercePriceListId(commercePriceListId);
	}

	/**
	 * Returns the commerce price list channel rel where commerceChannelId = &#63; and commercePriceListId = &#63; or throws a <code>NoSuchPriceListChannelRelException</code> if it could not be found.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel findByCCI_CPI(
			long commerceChannelId, long commercePriceListId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().findByCCI_CPI(
			commerceChannelId, commercePriceListId);
	}

	/**
	 * Returns the commerce price list channel rel where commerceChannelId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel fetchByCCI_CPI(
		long commerceChannelId, long commercePriceListId) {

		return getPersistence().fetchByCCI_CPI(
			commerceChannelId, commercePriceListId);
	}

	/**
	 * Returns the commerce price list channel rel where commerceChannelId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public static CommercePriceListChannelRel fetchByCCI_CPI(
		long commerceChannelId, long commercePriceListId,
		boolean useFinderCache) {

		return getPersistence().fetchByCCI_CPI(
			commerceChannelId, commercePriceListId, useFinderCache);
	}

	/**
	 * Removes the commerce price list channel rel where commerceChannelId = &#63; and commercePriceListId = &#63; from the database.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the commerce price list channel rel that was removed
	 */
	public static CommercePriceListChannelRel removeByCCI_CPI(
			long commerceChannelId, long commercePriceListId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().removeByCCI_CPI(
			commerceChannelId, commercePriceListId);
	}

	/**
	 * Returns the number of commerce price list channel rels where commerceChannelId = &#63; and commercePriceListId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list channel rels
	 */
	public static int countByCCI_CPI(
		long commerceChannelId, long commercePriceListId) {

		return getPersistence().countByCCI_CPI(
			commerceChannelId, commercePriceListId);
	}

	/**
	 * Caches the commerce price list channel rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListChannelRel the commerce price list channel rel
	 */
	public static void cacheResult(
		CommercePriceListChannelRel commercePriceListChannelRel) {

		getPersistence().cacheResult(commercePriceListChannelRel);
	}

	/**
	 * Caches the commerce price list channel rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListChannelRels the commerce price list channel rels
	 */
	public static void cacheResult(
		List<CommercePriceListChannelRel> commercePriceListChannelRels) {

		getPersistence().cacheResult(commercePriceListChannelRels);
	}

	/**
	 * Creates a new commerce price list channel rel with the primary key. Does not add the commerce price list channel rel to the database.
	 *
	 * @param CommercePriceListChannelRelId the primary key for the new commerce price list channel rel
	 * @return the new commerce price list channel rel
	 */
	public static CommercePriceListChannelRel create(
		long CommercePriceListChannelRelId) {

		return getPersistence().create(CommercePriceListChannelRelId);
	}

	/**
	 * Removes the commerce price list channel rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the commerce price list channel rel
	 * @return the commerce price list channel rel that was removed
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	public static CommercePriceListChannelRel remove(
			long CommercePriceListChannelRelId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().remove(CommercePriceListChannelRelId);
	}

	public static CommercePriceListChannelRel updateImpl(
		CommercePriceListChannelRel commercePriceListChannelRel) {

		return getPersistence().updateImpl(commercePriceListChannelRel);
	}

	/**
	 * Returns the commerce price list channel rel with the primary key or throws a <code>NoSuchPriceListChannelRelException</code> if it could not be found.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the commerce price list channel rel
	 * @return the commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	public static CommercePriceListChannelRel findByPrimaryKey(
			long CommercePriceListChannelRelId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListChannelRelException {

		return getPersistence().findByPrimaryKey(CommercePriceListChannelRelId);
	}

	/**
	 * Returns the commerce price list channel rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the commerce price list channel rel
	 * @return the commerce price list channel rel, or <code>null</code> if a commerce price list channel rel with the primary key could not be found
	 */
	public static CommercePriceListChannelRel fetchByPrimaryKey(
		long CommercePriceListChannelRelId) {

		return getPersistence().fetchByPrimaryKey(
			CommercePriceListChannelRelId);
	}

	/**
	 * Returns all the commerce price list channel rels.
	 *
	 * @return the commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce price list channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @return the range of commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list channel rels
	 */
	public static List<CommercePriceListChannelRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce price list channel rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce price list channel rels.
	 *
	 * @return the number of commerce price list channel rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommercePriceListChannelRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePriceListChannelRelPersistence,
		 CommercePriceListChannelRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePriceListChannelRelPersistence.class);

		ServiceTracker
			<CommercePriceListChannelRelPersistence,
			 CommercePriceListChannelRelPersistence> serviceTracker =
				new ServiceTracker
					<CommercePriceListChannelRelPersistence,
					 CommercePriceListChannelRelPersistence>(
						 bundle.getBundleContext(),
						 CommercePriceListChannelRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
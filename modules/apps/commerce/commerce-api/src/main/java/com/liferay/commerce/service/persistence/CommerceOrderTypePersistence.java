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

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.exception.NoSuchOrderTypeException;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce order type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypeUtil
 * @generated
 */
@ProviderType
public interface CommerceOrderTypePersistence
	extends BasePersistence<CommerceOrderType> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrderTypeUtil} to access the commerce order type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce order types where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce order types where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order types where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the first commerce order type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the last commerce order type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the last commerce order type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set where uuid = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType[] findByUuid_PrevAndNext(
			long commerceOrderTypeId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns all the commerce order types that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByUuid(String uuid);

	/**
	 * Returns a range of all the commerce order types that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set of commerce order types that the user has permission to view where uuid = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType[] filterFindByUuid_PrevAndNext(
			long commerceOrderTypeId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Removes all the commerce order types where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce order types where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce order types
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of commerce order types that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce order types that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the commerce order types where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce order types where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order types where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the first commerce order type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the last commerce order type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the last commerce order type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType[] findByUuid_C_PrevAndNext(
			long commerceOrderTypeId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns all the commerce order types that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce order types that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set of commerce order types that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType[] filterFindByUuid_C_PrevAndNext(
			long commerceOrderTypeId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Removes all the commerce order types where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce order types where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce order types
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce order types that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce order types that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce order types where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the commerce order types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order types where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the first commerce order type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the last commerce order type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the last commerce order type in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set where companyId = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType[] findByCompanyId_PrevAndNext(
			long commerceOrderTypeId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns all the commerce order types that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce order types that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set of commerce order types that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType[] filterFindByCompanyId_PrevAndNext(
			long commerceOrderTypeId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Removes all the commerce order types where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce order types where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce order types
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of commerce order types that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce order types that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the commerce order types where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByC_A(
		long companyId, boolean active);

	/**
	 * Returns a range of all the commerce order types where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByC_A(
		long companyId, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order types where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByC_A_First(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the first commerce order type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the last commerce order type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByC_A_Last(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the last commerce order type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType[] findByC_A_PrevAndNext(
			long commerceOrderTypeId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns all the commerce order types that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByC_A(
		long companyId, boolean active);

	/**
	 * Returns a range of all the commerce order types that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByC_A(
		long companyId, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set of commerce order types that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType[] filterFindByC_A_PrevAndNext(
			long commerceOrderTypeId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Removes all the commerce order types where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public void removeByC_A(long companyId, boolean active);

	/**
	 * Returns the number of commerce order types where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce order types
	 */
	public int countByC_A(long companyId, boolean active);

	/**
	 * Returns the number of commerce order types that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce order types that the user has permission to view
	 */
	public int filterCountByC_A(long companyId, boolean active);

	/**
	 * Returns all the commerce order types where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByLtD_S(
		Date displayDate, int status);

	/**
	 * Returns a range of all the commerce order types where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order types where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order type in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByLtD_S_First(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the first commerce order type in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByLtD_S_First(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the last commerce order type in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByLtD_S_Last(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the last commerce order type in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByLtD_S_Last(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType[] findByLtD_S_PrevAndNext(
			long commerceOrderTypeId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns all the commerce order types that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByLtD_S(
		Date displayDate, int status);

	/**
	 * Returns a range of all the commerce order types that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types that the user has permissions to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set of commerce order types that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType[] filterFindByLtD_S_PrevAndNext(
			long commerceOrderTypeId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Removes all the commerce order types where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public void removeByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of commerce order types where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce order types
	 */
	public int countByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of commerce order types that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce order types that the user has permission to view
	 */
	public int filterCountByLtD_S(Date displayDate, int status);

	/**
	 * Returns all the commerce order types where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByLtE_S(
		Date expirationDate, int status);

	/**
	 * Returns a range of all the commerce order types where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByLtE_S(
		Date expirationDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order types where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order types
	 */
	public java.util.List<CommerceOrderType> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order type in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByLtE_S_First(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the first commerce order type in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByLtE_S_First(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the last commerce order type in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByLtE_S_Last(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the last commerce order type in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByLtE_S_Last(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType[] findByLtE_S_PrevAndNext(
			long commerceOrderTypeId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Returns all the commerce order types that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByLtE_S(
		Date expirationDate, int status);

	/**
	 * Returns a range of all the commerce order types that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types that the user has permissions to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order types that the user has permission to view
	 */
	public java.util.List<CommerceOrderType> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns the commerce order types before and after the current commerce order type in the ordered set of commerce order types that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceOrderTypeId the primary key of the current commerce order type
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType[] filterFindByLtE_S_PrevAndNext(
			long commerceOrderTypeId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
				orderByComparator)
		throws NoSuchOrderTypeException;

	/**
	 * Removes all the commerce order types where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public void removeByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of commerce order types where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce order types
	 */
	public int countByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of commerce order types that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce order types that the user has permission to view
	 */
	public int filterCountByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the commerce order type where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchOrderTypeException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce order type
	 * @throws NoSuchOrderTypeException if a matching commerce order type could not be found
	 */
	public CommerceOrderType findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the commerce order type where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the commerce order type where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	public CommerceOrderType fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the commerce order type where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce order type that was removed
	 */
	public CommerceOrderType removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the number of commerce order types where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce order types
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the commerce order type in the entity cache if it is enabled.
	 *
	 * @param commerceOrderType the commerce order type
	 */
	public void cacheResult(CommerceOrderType commerceOrderType);

	/**
	 * Caches the commerce order types in the entity cache if it is enabled.
	 *
	 * @param commerceOrderTypes the commerce order types
	 */
	public void cacheResult(
		java.util.List<CommerceOrderType> commerceOrderTypes);

	/**
	 * Creates a new commerce order type with the primary key. Does not add the commerce order type to the database.
	 *
	 * @param commerceOrderTypeId the primary key for the new commerce order type
	 * @return the new commerce order type
	 */
	public CommerceOrderType create(long commerceOrderTypeId);

	/**
	 * Removes the commerce order type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderTypeId the primary key of the commerce order type
	 * @return the commerce order type that was removed
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType remove(long commerceOrderTypeId)
		throws NoSuchOrderTypeException;

	public CommerceOrderType updateImpl(CommerceOrderType commerceOrderType);

	/**
	 * Returns the commerce order type with the primary key or throws a <code>NoSuchOrderTypeException</code> if it could not be found.
	 *
	 * @param commerceOrderTypeId the primary key of the commerce order type
	 * @return the commerce order type
	 * @throws NoSuchOrderTypeException if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType findByPrimaryKey(long commerceOrderTypeId)
		throws NoSuchOrderTypeException;

	/**
	 * Returns the commerce order type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderTypeId the primary key of the commerce order type
	 * @return the commerce order type, or <code>null</code> if a commerce order type with the primary key could not be found
	 */
	public CommerceOrderType fetchByPrimaryKey(long commerceOrderTypeId);

	/**
	 * Returns all the commerce order types.
	 *
	 * @return the commerce order types
	 */
	public java.util.List<CommerceOrderType> findAll();

	/**
	 * Returns a range of all the commerce order types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of commerce order types
	 */
	public java.util.List<CommerceOrderType> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce order types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order types
	 */
	public java.util.List<CommerceOrderType> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order types
	 */
	public java.util.List<CommerceOrderType> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderType>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce order types from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce order types.
	 *
	 * @return the number of commerce order types
	 */
	public int countAll();

}
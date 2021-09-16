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

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectFieldException;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object field service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectFieldUtil
 * @generated
 */
@ProviderType
public interface ObjectFieldPersistence extends BasePersistence<ObjectField> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectFieldUtil} to access the object field persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object fields where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object fields
	 */
	public java.util.List<ObjectField> findByUuid(String uuid);

	/**
	 * Returns a range of all the object fields where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @return the range of matching object fields
	 */
	public java.util.List<ObjectField> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object fields where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object fields
	 */
	public java.util.List<ObjectField> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object fields where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object fields
	 */
	public java.util.List<ObjectField> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object field in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field
	 * @throws NoSuchObjectFieldException if a matching object field could not be found
	 */
	public ObjectField findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the first object field in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field, or <code>null</code> if a matching object field could not be found
	 */
	public ObjectField fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns the last object field in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field
	 * @throws NoSuchObjectFieldException if a matching object field could not be found
	 */
	public ObjectField findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the last object field in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field, or <code>null</code> if a matching object field could not be found
	 */
	public ObjectField fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns the object fields before and after the current object field in the ordered set where uuid = &#63;.
	 *
	 * @param objectFieldId the primary key of the current object field
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field
	 * @throws NoSuchObjectFieldException if a object field with the primary key could not be found
	 */
	public ObjectField[] findByUuid_PrevAndNext(
			long objectFieldId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Removes all the object fields where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object fields where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object fields
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object fields where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object fields
	 */
	public java.util.List<ObjectField> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object fields where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @return the range of matching object fields
	 */
	public java.util.List<ObjectField> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object fields where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object fields
	 */
	public java.util.List<ObjectField> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object fields where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object fields
	 */
	public java.util.List<ObjectField> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object field in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field
	 * @throws NoSuchObjectFieldException if a matching object field could not be found
	 */
	public ObjectField findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the first object field in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field, or <code>null</code> if a matching object field could not be found
	 */
	public ObjectField fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns the last object field in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field
	 * @throws NoSuchObjectFieldException if a matching object field could not be found
	 */
	public ObjectField findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the last object field in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field, or <code>null</code> if a matching object field could not be found
	 */
	public ObjectField fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns the object fields before and after the current object field in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectFieldId the primary key of the current object field
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field
	 * @throws NoSuchObjectFieldException if a object field with the primary key could not be found
	 */
	public ObjectField[] findByUuid_C_PrevAndNext(
			long objectFieldId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Removes all the object fields where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object fields where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object fields
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object fields where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching object fields
	 */
	public java.util.List<ObjectField> findByListTypeDefinitionId(
		long listTypeDefinitionId);

	/**
	 * Returns a range of all the object fields where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @return the range of matching object fields
	 */
	public java.util.List<ObjectField> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end);

	/**
	 * Returns an ordered range of all the object fields where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object fields
	 */
	public java.util.List<ObjectField> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object fields where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object fields
	 */
	public java.util.List<ObjectField> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object field in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field
	 * @throws NoSuchObjectFieldException if a matching object field could not be found
	 */
	public ObjectField findByListTypeDefinitionId_First(
			long listTypeDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the first object field in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field, or <code>null</code> if a matching object field could not be found
	 */
	public ObjectField fetchByListTypeDefinitionId_First(
		long listTypeDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns the last object field in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field
	 * @throws NoSuchObjectFieldException if a matching object field could not be found
	 */
	public ObjectField findByListTypeDefinitionId_Last(
			long listTypeDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the last object field in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field, or <code>null</code> if a matching object field could not be found
	 */
	public ObjectField fetchByListTypeDefinitionId_Last(
		long listTypeDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns the object fields before and after the current object field in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param objectFieldId the primary key of the current object field
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field
	 * @throws NoSuchObjectFieldException if a object field with the primary key could not be found
	 */
	public ObjectField[] findByListTypeDefinitionId_PrevAndNext(
			long objectFieldId, long listTypeDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Removes all the object fields where listTypeDefinitionId = &#63; from the database.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 */
	public void removeByListTypeDefinitionId(long listTypeDefinitionId);

	/**
	 * Returns the number of object fields where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching object fields
	 */
	public int countByListTypeDefinitionId(long listTypeDefinitionId);

	/**
	 * Returns all the object fields where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the matching object fields
	 */
	public java.util.List<ObjectField> findByObjectDefinitionId(
		long objectDefinitionId);

	/**
	 * Returns a range of all the object fields where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @return the range of matching object fields
	 */
	public java.util.List<ObjectField> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end);

	/**
	 * Returns an ordered range of all the object fields where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object fields
	 */
	public java.util.List<ObjectField> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object fields where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object fields
	 */
	public java.util.List<ObjectField> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object field in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field
	 * @throws NoSuchObjectFieldException if a matching object field could not be found
	 */
	public ObjectField findByObjectDefinitionId_First(
			long objectDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the first object field in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field, or <code>null</code> if a matching object field could not be found
	 */
	public ObjectField fetchByObjectDefinitionId_First(
		long objectDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns the last object field in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field
	 * @throws NoSuchObjectFieldException if a matching object field could not be found
	 */
	public ObjectField findByObjectDefinitionId_Last(
			long objectDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the last object field in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field, or <code>null</code> if a matching object field could not be found
	 */
	public ObjectField fetchByObjectDefinitionId_Last(
		long objectDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns the object fields before and after the current object field in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectFieldId the primary key of the current object field
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field
	 * @throws NoSuchObjectFieldException if a object field with the primary key could not be found
	 */
	public ObjectField[] findByObjectDefinitionId_PrevAndNext(
			long objectFieldId, long objectDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Removes all the object fields where objectDefinitionId = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 */
	public void removeByObjectDefinitionId(long objectDefinitionId);

	/**
	 * Returns the number of object fields where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the number of matching object fields
	 */
	public int countByObjectDefinitionId(long objectDefinitionId);

	/**
	 * Returns all the object fields where objectDefinitionId = &#63; and dbTableName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param dbTableName the db table name
	 * @return the matching object fields
	 */
	public java.util.List<ObjectField> findByODI_DTN(
		long objectDefinitionId, String dbTableName);

	/**
	 * Returns a range of all the object fields where objectDefinitionId = &#63; and dbTableName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param dbTableName the db table name
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @return the range of matching object fields
	 */
	public java.util.List<ObjectField> findByODI_DTN(
		long objectDefinitionId, String dbTableName, int start, int end);

	/**
	 * Returns an ordered range of all the object fields where objectDefinitionId = &#63; and dbTableName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param dbTableName the db table name
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object fields
	 */
	public java.util.List<ObjectField> findByODI_DTN(
		long objectDefinitionId, String dbTableName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object fields where objectDefinitionId = &#63; and dbTableName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param dbTableName the db table name
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object fields
	 */
	public java.util.List<ObjectField> findByODI_DTN(
		long objectDefinitionId, String dbTableName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object field in the ordered set where objectDefinitionId = &#63; and dbTableName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param dbTableName the db table name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field
	 * @throws NoSuchObjectFieldException if a matching object field could not be found
	 */
	public ObjectField findByODI_DTN_First(
			long objectDefinitionId, String dbTableName,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the first object field in the ordered set where objectDefinitionId = &#63; and dbTableName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param dbTableName the db table name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field, or <code>null</code> if a matching object field could not be found
	 */
	public ObjectField fetchByODI_DTN_First(
		long objectDefinitionId, String dbTableName,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns the last object field in the ordered set where objectDefinitionId = &#63; and dbTableName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param dbTableName the db table name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field
	 * @throws NoSuchObjectFieldException if a matching object field could not be found
	 */
	public ObjectField findByODI_DTN_Last(
			long objectDefinitionId, String dbTableName,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the last object field in the ordered set where objectDefinitionId = &#63; and dbTableName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param dbTableName the db table name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field, or <code>null</code> if a matching object field could not be found
	 */
	public ObjectField fetchByODI_DTN_Last(
		long objectDefinitionId, String dbTableName,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns the object fields before and after the current object field in the ordered set where objectDefinitionId = &#63; and dbTableName = &#63;.
	 *
	 * @param objectFieldId the primary key of the current object field
	 * @param objectDefinitionId the object definition ID
	 * @param dbTableName the db table name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field
	 * @throws NoSuchObjectFieldException if a object field with the primary key could not be found
	 */
	public ObjectField[] findByODI_DTN_PrevAndNext(
			long objectFieldId, long objectDefinitionId, String dbTableName,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
				orderByComparator)
		throws NoSuchObjectFieldException;

	/**
	 * Removes all the object fields where objectDefinitionId = &#63; and dbTableName = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param dbTableName the db table name
	 */
	public void removeByODI_DTN(long objectDefinitionId, String dbTableName);

	/**
	 * Returns the number of object fields where objectDefinitionId = &#63; and dbTableName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param dbTableName the db table name
	 * @return the number of matching object fields
	 */
	public int countByODI_DTN(long objectDefinitionId, String dbTableName);

	/**
	 * Returns the object field where objectDefinitionId = &#63; and name = &#63; or throws a <code>NoSuchObjectFieldException</code> if it could not be found.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param name the name
	 * @return the matching object field
	 * @throws NoSuchObjectFieldException if a matching object field could not be found
	 */
	public ObjectField findByODI_N(long objectDefinitionId, String name)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the object field where objectDefinitionId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param name the name
	 * @return the matching object field, or <code>null</code> if a matching object field could not be found
	 */
	public ObjectField fetchByODI_N(long objectDefinitionId, String name);

	/**
	 * Returns the object field where objectDefinitionId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object field, or <code>null</code> if a matching object field could not be found
	 */
	public ObjectField fetchByODI_N(
		long objectDefinitionId, String name, boolean useFinderCache);

	/**
	 * Removes the object field where objectDefinitionId = &#63; and name = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param name the name
	 * @return the object field that was removed
	 */
	public ObjectField removeByODI_N(long objectDefinitionId, String name)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the number of object fields where objectDefinitionId = &#63; and name = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param name the name
	 * @return the number of matching object fields
	 */
	public int countByODI_N(long objectDefinitionId, String name);

	/**
	 * Caches the object field in the entity cache if it is enabled.
	 *
	 * @param objectField the object field
	 */
	public void cacheResult(ObjectField objectField);

	/**
	 * Caches the object fields in the entity cache if it is enabled.
	 *
	 * @param objectFields the object fields
	 */
	public void cacheResult(java.util.List<ObjectField> objectFields);

	/**
	 * Creates a new object field with the primary key. Does not add the object field to the database.
	 *
	 * @param objectFieldId the primary key for the new object field
	 * @return the new object field
	 */
	public ObjectField create(long objectFieldId);

	/**
	 * Removes the object field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectFieldId the primary key of the object field
	 * @return the object field that was removed
	 * @throws NoSuchObjectFieldException if a object field with the primary key could not be found
	 */
	public ObjectField remove(long objectFieldId)
		throws NoSuchObjectFieldException;

	public ObjectField updateImpl(ObjectField objectField);

	/**
	 * Returns the object field with the primary key or throws a <code>NoSuchObjectFieldException</code> if it could not be found.
	 *
	 * @param objectFieldId the primary key of the object field
	 * @return the object field
	 * @throws NoSuchObjectFieldException if a object field with the primary key could not be found
	 */
	public ObjectField findByPrimaryKey(long objectFieldId)
		throws NoSuchObjectFieldException;

	/**
	 * Returns the object field with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectFieldId the primary key of the object field
	 * @return the object field, or <code>null</code> if a object field with the primary key could not be found
	 */
	public ObjectField fetchByPrimaryKey(long objectFieldId);

	/**
	 * Returns all the object fields.
	 *
	 * @return the object fields
	 */
	public java.util.List<ObjectField> findAll();

	/**
	 * Returns a range of all the object fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @return the range of object fields
	 */
	public java.util.List<ObjectField> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object fields
	 */
	public java.util.List<ObjectField> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object fields
	 */
	public java.util.List<ObjectField> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectField>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object fields from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object fields.
	 *
	 * @return the number of object fields
	 */
	public int countAll();

}
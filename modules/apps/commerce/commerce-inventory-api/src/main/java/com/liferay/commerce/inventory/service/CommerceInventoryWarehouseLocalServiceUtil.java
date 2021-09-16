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

package com.liferay.commerce.inventory.service;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CommerceInventoryWarehouse. This utility wraps
 * <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryWarehouseLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseLocalService
 * @generated
 */
public class CommerceInventoryWarehouseLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryWarehouseLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce inventory warehouse to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceInventoryWarehouseLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceInventoryWarehouse the commerce inventory warehouse
	 * @return the commerce inventory warehouse that was added
	 */
	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		return getService().addCommerceInventoryWarehouse(
			commerceInventoryWarehouse);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCommerceInventoryWarehouse(String,
	 String, String, String, boolean, String, String, String,
	 String, String, String, String, double, double,
	 ServiceContext)}
	 */
	@Deprecated
	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String name, String description, boolean active, String street1,
			String street2, String street3, String city, String zip,
			String commerceRegionCode, String commerceCountryCode,
			double latitude, double longitude, String externalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceInventoryWarehouse(
			name, description, active, street1, street2, street3, city, zip,
			commerceRegionCode, commerceCountryCode, latitude, longitude,
			externalReferenceCode, serviceContext);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String externalReferenceCode, String name, String description,
			boolean active, String street1, String street2, String street3,
			String city, String zip, String commerceRegionCode,
			String commerceCountryCode, double latitude, double longitude,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceInventoryWarehouse(
			externalReferenceCode, name, description, active, street1, street2,
			street3, city, zip, commerceRegionCode, commerceCountryCode,
			latitude, longitude, serviceContext);
	}

	/**
	 * Creates a new commerce inventory warehouse with the primary key. Does not add the commerce inventory warehouse to the database.
	 *
	 * @param commerceInventoryWarehouseId the primary key for the new commerce inventory warehouse
	 * @return the new commerce inventory warehouse
	 */
	public static CommerceInventoryWarehouse createCommerceInventoryWarehouse(
		long commerceInventoryWarehouseId) {

		return getService().createCommerceInventoryWarehouse(
			commerceInventoryWarehouseId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce inventory warehouse from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceInventoryWarehouseLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceInventoryWarehouse the commerce inventory warehouse
	 * @return the commerce inventory warehouse that was removed
	 * @throws PortalException
	 */
	public static CommerceInventoryWarehouse deleteCommerceInventoryWarehouse(
			CommerceInventoryWarehouse commerceInventoryWarehouse)
		throws PortalException {

		return getService().deleteCommerceInventoryWarehouse(
			commerceInventoryWarehouse);
	}

	/**
	 * Deletes the commerce inventory warehouse with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceInventoryWarehouseLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseId the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse that was removed
	 * @throws PortalException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse deleteCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().deleteCommerceInventoryWarehouse(
			commerceInventoryWarehouseId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static CommerceInventoryWarehouse fetchCommerceInventoryWarehouse(
		long commerceInventoryWarehouseId) {

		return getService().fetchCommerceInventoryWarehouse(
			commerceInventoryWarehouseId);
	}

	/**
	 * Returns the commerce inventory warehouse with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce inventory warehouse's external reference code
	 * @return the matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse
		fetchCommerceInventoryWarehouseByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().
			fetchCommerceInventoryWarehouseByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceInventoryWarehouseByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	public static CommerceInventoryWarehouse
		fetchCommerceInventoryWarehouseByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchCommerceInventoryWarehouseByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static CommerceInventoryWarehouse
		fetchCommerceInventoryWarehouseByReferenceCode(
			String externalReferenceCode, long companyId) {

		return getService().fetchCommerceInventoryWarehouseByReferenceCode(
			externalReferenceCode, companyId);
	}

	public static CommerceInventoryWarehouse
			geolocateCommerceInventoryWarehouse(
				long commerceInventoryWarehouseId, double latitude,
				double longitude)
		throws PortalException {

		return getService().geolocateCommerceInventoryWarehouse(
			commerceInventoryWarehouseId, latitude, longitude);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce inventory warehouse with the primary key.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse
	 * @throws PortalException if a commerce inventory warehouse with the primary key could not be found
	 */
	public static CommerceInventoryWarehouse getCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().getCommerceInventoryWarehouse(
			commerceInventoryWarehouseId);
	}

	/**
	 * Returns the commerce inventory warehouse with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce inventory warehouse's external reference code
	 * @return the matching commerce inventory warehouse
	 * @throws PortalException if a matching commerce inventory warehouse could not be found
	 */
	public static CommerceInventoryWarehouse
			getCommerceInventoryWarehouseByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().
			getCommerceInventoryWarehouseByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * Returns a range of all the commerce inventory warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of commerce inventory warehouses
	 */
	public static List<CommerceInventoryWarehouse>
		getCommerceInventoryWarehouses(int start, int end) {

		return getService().getCommerceInventoryWarehouses(start, end);
	}

	public static List<CommerceInventoryWarehouse>
		getCommerceInventoryWarehouses(long companyId) {

		return getService().getCommerceInventoryWarehouses(companyId);
	}

	public static List<CommerceInventoryWarehouse>
		getCommerceInventoryWarehouses(
			long companyId, boolean active, int start, int end,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getService().getCommerceInventoryWarehouses(
			companyId, active, start, end, orderByComparator);
	}

	public static List<CommerceInventoryWarehouse>
		getCommerceInventoryWarehouses(
			long companyId, boolean active, String commerceCountryCode,
			int start, int end,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getService().getCommerceInventoryWarehouses(
			companyId, active, commerceCountryCode, start, end,
			orderByComparator);
	}

	public static List<CommerceInventoryWarehouse>
		getCommerceInventoryWarehouses(
			long companyId, int start, int end,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return getService().getCommerceInventoryWarehouses(
			companyId, start, end, orderByComparator);
	}

	public static List<CommerceInventoryWarehouse>
		getCommerceInventoryWarehouses(
			long companyId, long groupId, boolean active) {

		return getService().getCommerceInventoryWarehouses(
			companyId, groupId, active);
	}

	public static List<CommerceInventoryWarehouse>
		getCommerceInventoryWarehouses(long groupId, String sku) {

		return getService().getCommerceInventoryWarehouses(groupId, sku);
	}

	/**
	 * Returns the number of commerce inventory warehouses.
	 *
	 * @return the number of commerce inventory warehouses
	 */
	public static int getCommerceInventoryWarehousesCount() {
		return getService().getCommerceInventoryWarehousesCount();
	}

	public static int getCommerceInventoryWarehousesCount(long companyId) {
		return getService().getCommerceInventoryWarehousesCount(companyId);
	}

	public static int getCommerceInventoryWarehousesCount(
		long companyId, boolean active) {

		return getService().getCommerceInventoryWarehousesCount(
			companyId, active);
	}

	public static int getCommerceInventoryWarehousesCount(
		long companyId, boolean active, String commerceCountryCode) {

		return getService().getCommerceInventoryWarehousesCount(
			companyId, active, commerceCountryCode);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static List<CommerceInventoryWarehouse> search(
			long companyId, Boolean active, String commerceCountryCode,
			String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().search(
			companyId, active, commerceCountryCode, keywords, start, end, sort);
	}

	public static int searchCommerceInventoryWarehousesCount(
			long companyId, Boolean active, String commerceCountryCode,
			String keywords)
		throws PortalException {

		return getService().searchCommerceInventoryWarehousesCount(
			companyId, active, commerceCountryCode, keywords);
	}

	public static CommerceInventoryWarehouse setActive(
			long commerceInventoryWarehouseId, boolean active)
		throws PortalException {

		return getService().setActive(commerceInventoryWarehouseId, active);
	}

	/**
	 * Updates the commerce inventory warehouse in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceInventoryWarehouseLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceInventoryWarehouse the commerce inventory warehouse
	 * @return the commerce inventory warehouse that was updated
	 */
	public static CommerceInventoryWarehouse updateCommerceInventoryWarehouse(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		return getService().updateCommerceInventoryWarehouse(
			commerceInventoryWarehouse);
	}

	public static CommerceInventoryWarehouse updateCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId, String name, String description,
			boolean active, String street1, String street2, String street3,
			String city, String zip, String commerceRegionCode,
			String commerceCountryCode, double latitude, double longitude,
			long mvccVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceInventoryWarehouse(
			commerceInventoryWarehouseId, name, description, active, street1,
			street2, street3, city, zip, commerceRegionCode,
			commerceCountryCode, latitude, longitude, mvccVersion,
			serviceContext);
	}

	public static CommerceInventoryWarehouseLocalService getService() {
		return _service;
	}

	private static volatile CommerceInventoryWarehouseLocalService _service;

}
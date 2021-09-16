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
 * Provides a wrapper for {@link ObjectRelationshipLocalService}.
 *
 * @author Marco Leo
 * @see ObjectRelationshipLocalService
 * @generated
 */
public class ObjectRelationshipLocalServiceWrapper
	implements ObjectRelationshipLocalService,
			   ServiceWrapper<ObjectRelationshipLocalService> {

	public ObjectRelationshipLocalServiceWrapper(
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	@Override
	public com.liferay.object.model.ObjectRelationship addObjectRelationship(
			long userId, long objectDefinitionId1, long objectDefinitionId2,
			java.util.Map<java.util.Locale, String> labelMap, String name,
			String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipLocalService.addObjectRelationship(
			userId, objectDefinitionId1, objectDefinitionId2, labelMap, name,
			type);
	}

	/**
	 * Adds the object relationship to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectRelationshipLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectRelationship the object relationship
	 * @return the object relationship that was added
	 */
	@Override
	public com.liferay.object.model.ObjectRelationship addObjectRelationship(
		com.liferay.object.model.ObjectRelationship objectRelationship) {

		return _objectRelationshipLocalService.addObjectRelationship(
			objectRelationship);
	}

	@Override
	public void addObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1, long primaryKey2)
		throws com.liferay.portal.kernel.exception.PortalException {

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			objectRelationshipId, primaryKey1, primaryKey2);
	}

	/**
	 * Creates a new object relationship with the primary key. Does not add the object relationship to the database.
	 *
	 * @param objectRelationshipId the primary key for the new object relationship
	 * @return the new object relationship
	 */
	@Override
	public com.liferay.object.model.ObjectRelationship createObjectRelationship(
		long objectRelationshipId) {

		return _objectRelationshipLocalService.createObjectRelationship(
			objectRelationshipId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the object relationship with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectRelationshipLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectRelationshipId the primary key of the object relationship
	 * @return the object relationship that was removed
	 * @throws PortalException if a object relationship with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectRelationship deleteObjectRelationship(
			long objectRelationshipId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationshipId);
	}

	/**
	 * Deletes the object relationship from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectRelationshipLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectRelationship the object relationship
	 * @return the object relationship that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.object.model.ObjectRelationship deleteObjectRelationship(
			com.liferay.object.model.ObjectRelationship objectRelationship)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _objectRelationshipLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _objectRelationshipLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _objectRelationshipLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _objectRelationshipLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _objectRelationshipLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _objectRelationshipLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _objectRelationshipLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _objectRelationshipLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.object.model.ObjectRelationship fetchObjectRelationship(
		long objectRelationshipId) {

		return _objectRelationshipLocalService.fetchObjectRelationship(
			objectRelationshipId);
	}

	@Override
	public com.liferay.object.model.ObjectRelationship
		fetchObjectRelationshipByObjectFieldId2(long objectFieldId2) {

		return _objectRelationshipLocalService.
			fetchObjectRelationshipByObjectFieldId2(objectFieldId2);
	}

	/**
	 * Returns the object relationship with the matching UUID and company.
	 *
	 * @param uuid the object relationship's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object relationship, or <code>null</code> if a matching object relationship could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectRelationship
		fetchObjectRelationshipByUuidAndCompanyId(String uuid, long companyId) {

		return _objectRelationshipLocalService.
			fetchObjectRelationshipByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _objectRelationshipLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _objectRelationshipLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectRelationshipLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the object relationship with the primary key.
	 *
	 * @param objectRelationshipId the primary key of the object relationship
	 * @return the object relationship
	 * @throws PortalException if a object relationship with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectRelationship getObjectRelationship(
			long objectRelationshipId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipLocalService.getObjectRelationship(
			objectRelationshipId);
	}

	/**
	 * Returns the object relationship with the matching UUID and company.
	 *
	 * @param uuid the object relationship's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object relationship
	 * @throws PortalException if a matching object relationship could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectRelationship
			getObjectRelationshipByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipLocalService.
			getObjectRelationshipByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the object relationships.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @return the range of object relationships
	 */
	@Override
	public java.util.List<com.liferay.object.model.ObjectRelationship>
		getObjectRelationships(int start, int end) {

		return _objectRelationshipLocalService.getObjectRelationships(
			start, end);
	}

	@Override
	public java.util.List<com.liferay.object.model.ObjectRelationship>
		getObjectRelationships(long objectDefinitionId1, int start, int end) {

		return _objectRelationshipLocalService.getObjectRelationships(
			objectDefinitionId1, start, end);
	}

	/**
	 * Returns the number of object relationships.
	 *
	 * @return the number of object relationships
	 */
	@Override
	public int getObjectRelationshipsCount() {
		return _objectRelationshipLocalService.getObjectRelationshipsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectRelationshipLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the object relationship in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectRelationshipLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectRelationship the object relationship
	 * @return the object relationship that was updated
	 */
	@Override
	public com.liferay.object.model.ObjectRelationship updateObjectRelationship(
		com.liferay.object.model.ObjectRelationship objectRelationship) {

		return _objectRelationshipLocalService.updateObjectRelationship(
			objectRelationship);
	}

	@Override
	public ObjectRelationshipLocalService getWrappedService() {
		return _objectRelationshipLocalService;
	}

	@Override
	public void setWrappedService(
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}
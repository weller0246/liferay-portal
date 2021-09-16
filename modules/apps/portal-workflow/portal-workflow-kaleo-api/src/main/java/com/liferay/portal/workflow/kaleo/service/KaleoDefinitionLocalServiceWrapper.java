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

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link KaleoDefinitionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoDefinitionLocalService
 * @generated
 */
public class KaleoDefinitionLocalServiceWrapper
	implements KaleoDefinitionLocalService,
			   ServiceWrapper<KaleoDefinitionLocalService> {

	public KaleoDefinitionLocalServiceWrapper(
		KaleoDefinitionLocalService kaleoDefinitionLocalService) {

		_kaleoDefinitionLocalService = kaleoDefinitionLocalService;
	}

	@Override
	public void activateKaleoDefinition(
			long kaleoDefinitionId, long kaleoDefinitionVersionId,
			long startKaleoNodeId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kaleoDefinitionLocalService.activateKaleoDefinition(
			kaleoDefinitionId, kaleoDefinitionVersionId, startKaleoNodeId,
			serviceContext);
	}

	@Override
	public void activateKaleoDefinition(
			long kaleoDefinitionId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kaleoDefinitionLocalService.activateKaleoDefinition(
			kaleoDefinitionId, serviceContext);
	}

	@Override
	public void activateKaleoDefinition(
			String name, int version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kaleoDefinitionLocalService.activateKaleoDefinition(
			name, version, serviceContext);
	}

	/**
	 * Adds the kaleo definition to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoDefinitionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoDefinition the kaleo definition
	 * @return the kaleo definition that was added
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoDefinition
		addKaleoDefinition(
			com.liferay.portal.workflow.kaleo.model.KaleoDefinition
				kaleoDefinition) {

		return _kaleoDefinitionLocalService.addKaleoDefinition(kaleoDefinition);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoDefinition
			addKaleoDefinition(
				String name, String title, String description, String content,
				String scope, int version,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoDefinitionLocalService.addKaleoDefinition(
			name, title, description, content, scope, version, serviceContext);
	}

	/**
	 * Creates a new kaleo definition with the primary key. Does not add the kaleo definition to the database.
	 *
	 * @param kaleoDefinitionId the primary key for the new kaleo definition
	 * @return the new kaleo definition
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoDefinition
		createKaleoDefinition(long kaleoDefinitionId) {

		return _kaleoDefinitionLocalService.createKaleoDefinition(
			kaleoDefinitionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoDefinitionLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deactivateKaleoDefinition(
			String name, int version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kaleoDefinitionLocalService.deactivateKaleoDefinition(
			name, version, serviceContext);
	}

	@Override
	public void deleteCompanyKaleoDefinitions(long companyId) {
		_kaleoDefinitionLocalService.deleteCompanyKaleoDefinitions(companyId);
	}

	/**
	 * Deletes the kaleo definition from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoDefinitionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoDefinition the kaleo definition
	 * @return the kaleo definition that was removed
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoDefinition
		deleteKaleoDefinition(
			com.liferay.portal.workflow.kaleo.model.KaleoDefinition
				kaleoDefinition) {

		return _kaleoDefinitionLocalService.deleteKaleoDefinition(
			kaleoDefinition);
	}

	/**
	 * Deletes the kaleo definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoDefinitionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoDefinitionId the primary key of the kaleo definition
	 * @return the kaleo definition that was removed
	 * @throws PortalException if a kaleo definition with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoDefinition
			deleteKaleoDefinition(long kaleoDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoDefinitionLocalService.deleteKaleoDefinition(
			kaleoDefinitionId);
	}

	@Override
	public void deleteKaleoDefinition(
			String name,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kaleoDefinitionLocalService.deleteKaleoDefinition(
			name, serviceContext);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoDefinitionLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _kaleoDefinitionLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _kaleoDefinitionLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoDefinitionLocalService.dynamicQuery();
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

		return _kaleoDefinitionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionModelImpl</code>.
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

		return _kaleoDefinitionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionModelImpl</code>.
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

		return _kaleoDefinitionLocalService.dynamicQuery(
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

		return _kaleoDefinitionLocalService.dynamicQueryCount(dynamicQuery);
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

		return _kaleoDefinitionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoDefinition
		fetchKaleoDefinition(long kaleoDefinitionId) {

		return _kaleoDefinitionLocalService.fetchKaleoDefinition(
			kaleoDefinitionId);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoDefinition
		fetchKaleoDefinition(
			String name,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoDefinitionLocalService.fetchKaleoDefinition(
			name, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoDefinitionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoDefinitionLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo definition with the primary key.
	 *
	 * @param kaleoDefinitionId the primary key of the kaleo definition
	 * @return the kaleo definition
	 * @throws PortalException if a kaleo definition with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoDefinition
			getKaleoDefinition(long kaleoDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoDefinitionLocalService.getKaleoDefinition(
			kaleoDefinitionId);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoDefinition
			getKaleoDefinition(
				String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoDefinitionLocalService.getKaleoDefinition(
			name, serviceContext);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>
			getKaleoDefinitions(
				boolean active, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>
						orderByComparator,
				com.liferay.portal.kernel.service.ServiceContext
					serviceContext) {

		return _kaleoDefinitionLocalService.getKaleoDefinitions(
			active, start, end, orderByComparator, serviceContext);
	}

	/**
	 * Returns a range of all the kaleo definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @return the range of kaleo definitions
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>
			getKaleoDefinitions(int start, int end) {

		return _kaleoDefinitionLocalService.getKaleoDefinitions(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>
			getKaleoDefinitions(
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>
						orderByComparator,
				com.liferay.portal.kernel.service.ServiceContext
					serviceContext) {

		return _kaleoDefinitionLocalService.getKaleoDefinitions(
			start, end, orderByComparator, serviceContext);
	}

	/**
	 * Returns the number of kaleo definitions.
	 *
	 * @return the number of kaleo definitions
	 */
	@Override
	public int getKaleoDefinitionsCount() {
		return _kaleoDefinitionLocalService.getKaleoDefinitionsCount();
	}

	@Override
	public int getKaleoDefinitionsCount(
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoDefinitionLocalService.getKaleoDefinitionsCount(
			active, serviceContext);
	}

	@Override
	public int getKaleoDefinitionsCount(
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoDefinitionLocalService.getKaleoDefinitionsCount(
			serviceContext);
	}

	@Override
	public int getKaleoDefinitionsCount(
		String name, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoDefinitionLocalService.getKaleoDefinitionsCount(
			name, active, serviceContext);
	}

	@Override
	public int getKaleoDefinitionsCount(
		String name,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoDefinitionLocalService.getKaleoDefinitionsCount(
			name, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoDefinitionLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoDefinitionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>
			getScopeKaleoDefinitions(
				String scope, boolean active, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>
						orderByComparator,
				com.liferay.portal.kernel.service.ServiceContext
					serviceContext) {

		return _kaleoDefinitionLocalService.getScopeKaleoDefinitions(
			scope, active, start, end, orderByComparator, serviceContext);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>
			getScopeKaleoDefinitions(
				String scope, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.KaleoDefinition>
						orderByComparator,
				com.liferay.portal.kernel.service.ServiceContext
					serviceContext) {

		return _kaleoDefinitionLocalService.getScopeKaleoDefinitions(
			scope, start, end, orderByComparator, serviceContext);
	}

	@Override
	public int getScopeKaleoDefinitionsCount(
		String scope, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoDefinitionLocalService.getScopeKaleoDefinitionsCount(
			scope, active, serviceContext);
	}

	@Override
	public int getScopeKaleoDefinitionsCount(
		String scope,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _kaleoDefinitionLocalService.getScopeKaleoDefinitionsCount(
			scope, serviceContext);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoDefinition
			updatedKaleoDefinition(
				long kaleoDefinitionId, String title, String description,
				String content,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoDefinitionLocalService.updatedKaleoDefinition(
			kaleoDefinitionId, title, description, content, serviceContext);
	}

	/**
	 * Updates the kaleo definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoDefinitionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoDefinition the kaleo definition
	 * @return the kaleo definition that was updated
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoDefinition
		updateKaleoDefinition(
			com.liferay.portal.workflow.kaleo.model.KaleoDefinition
				kaleoDefinition) {

		return _kaleoDefinitionLocalService.updateKaleoDefinition(
			kaleoDefinition);
	}

	@Override
	public KaleoDefinitionLocalService getWrappedService() {
		return _kaleoDefinitionLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoDefinitionLocalService kaleoDefinitionLocalService) {

		_kaleoDefinitionLocalService = kaleoDefinitionLocalService;
	}

	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

}
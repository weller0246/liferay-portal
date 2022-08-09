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

package com.liferay.commerce.discount.service.base;

import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel;
import com.liferay.commerce.discount.service.CommerceDiscountOrderTypeRelLocalService;
import com.liferay.commerce.discount.service.CommerceDiscountOrderTypeRelLocalServiceUtil;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountOrderTypeRelPersistence;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the commerce discount order type rel local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.discount.service.impl.CommerceDiscountOrderTypeRelLocalServiceImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.discount.service.impl.CommerceDiscountOrderTypeRelLocalServiceImpl
 * @generated
 */
public abstract class CommerceDiscountOrderTypeRelLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements CommerceDiscountOrderTypeRelLocalService,
			   IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CommerceDiscountOrderTypeRelLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CommerceDiscountOrderTypeRelLocalServiceUtil</code>.
	 */

	/**
	 * Adds the commerce discount order type rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountOrderTypeRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountOrderTypeRel the commerce discount order type rel
	 * @return the commerce discount order type rel that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscountOrderTypeRel addCommerceDiscountOrderTypeRel(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		commerceDiscountOrderTypeRel.setNew(true);

		return commerceDiscountOrderTypeRelPersistence.update(
			commerceDiscountOrderTypeRel);
	}

	/**
	 * Creates a new commerce discount order type rel with the primary key. Does not add the commerce discount order type rel to the database.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key for the new commerce discount order type rel
	 * @return the new commerce discount order type rel
	 */
	@Override
	@Transactional(enabled = false)
	public CommerceDiscountOrderTypeRel createCommerceDiscountOrderTypeRel(
		long commerceDiscountOrderTypeRelId) {

		return commerceDiscountOrderTypeRelPersistence.create(
			commerceDiscountOrderTypeRelId);
	}

	/**
	 * Deletes the commerce discount order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountOrderTypeRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel that was removed
	 * @throws PortalException if a commerce discount order type rel with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceDiscountOrderTypeRel deleteCommerceDiscountOrderTypeRel(
			long commerceDiscountOrderTypeRelId)
		throws PortalException {

		return commerceDiscountOrderTypeRelPersistence.remove(
			commerceDiscountOrderTypeRelId);
	}

	/**
	 * Deletes the commerce discount order type rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountOrderTypeRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountOrderTypeRel the commerce discount order type rel
	 * @return the commerce discount order type rel that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceDiscountOrderTypeRel deleteCommerceDiscountOrderTypeRel(
			CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel)
		throws PortalException {

		return commerceDiscountOrderTypeRelPersistence.remove(
			commerceDiscountOrderTypeRel);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return commerceDiscountOrderTypeRelPersistence.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(DSLQuery dslQuery) {
		Long count = dslQuery(dslQuery);

		return count.intValue();
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			CommerceDiscountOrderTypeRel.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return commerceDiscountOrderTypeRelPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return commerceDiscountOrderTypeRelPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return commerceDiscountOrderTypeRelPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return commerceDiscountOrderTypeRelPersistence.countWithDynamicQuery(
			dynamicQuery);
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
		DynamicQuery dynamicQuery, Projection projection) {

		return commerceDiscountOrderTypeRelPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public CommerceDiscountOrderTypeRel fetchCommerceDiscountOrderTypeRel(
		long commerceDiscountOrderTypeRelId) {

		return commerceDiscountOrderTypeRelPersistence.fetchByPrimaryKey(
			commerceDiscountOrderTypeRelId);
	}

	/**
	 * Returns the commerce discount order type rel with the matching UUID and company.
	 *
	 * @param uuid the commerce discount order type rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel
		fetchCommerceDiscountOrderTypeRelByUuidAndCompanyId(
			String uuid, long companyId) {

		return commerceDiscountOrderTypeRelPersistence.fetchByUuid_C_First(
			uuid, companyId, null);
	}

	/**
	 * Returns the commerce discount order type rel with the primary key.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel
	 * @throws PortalException if a commerce discount order type rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel getCommerceDiscountOrderTypeRel(
			long commerceDiscountOrderTypeRelId)
		throws PortalException {

		return commerceDiscountOrderTypeRelPersistence.findByPrimaryKey(
			commerceDiscountOrderTypeRelId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			commerceDiscountOrderTypeRelLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(
			CommerceDiscountOrderTypeRel.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceDiscountOrderTypeRelId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			commerceDiscountOrderTypeRelLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			CommerceDiscountOrderTypeRel.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceDiscountOrderTypeRelId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			commerceDiscountOrderTypeRelLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(
			CommerceDiscountOrderTypeRel.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceDiscountOrderTypeRelId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceDiscountOrderTypeRel>() {

				@Override
				public void performAction(
						CommerceDiscountOrderTypeRel
							commerceDiscountOrderTypeRel)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, commerceDiscountOrderTypeRel);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(
					CommerceDiscountOrderTypeRel.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceDiscountOrderTypeRelPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return commerceDiscountOrderTypeRelLocalService.
			deleteCommerceDiscountOrderTypeRel(
				(CommerceDiscountOrderTypeRel)persistedModel);
	}

	@Override
	public BasePersistence<CommerceDiscountOrderTypeRel> getBasePersistence() {
		return commerceDiscountOrderTypeRelPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceDiscountOrderTypeRelPersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns the commerce discount order type rel with the matching UUID and company.
	 *
	 * @param uuid the commerce discount order type rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce discount order type rel
	 * @throws PortalException if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel
			getCommerceDiscountOrderTypeRelByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException {

		return commerceDiscountOrderTypeRelPersistence.findByUuid_C_First(
			uuid, companyId, null);
	}

	/**
	 * Returns a range of all the commerce discount order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> getCommerceDiscountOrderTypeRels(
		int start, int end) {

		return commerceDiscountOrderTypeRelPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of commerce discount order type rels.
	 *
	 * @return the number of commerce discount order type rels
	 */
	@Override
	public int getCommerceDiscountOrderTypeRelsCount() {
		return commerceDiscountOrderTypeRelPersistence.countAll();
	}

	/**
	 * Updates the commerce discount order type rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountOrderTypeRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountOrderTypeRel the commerce discount order type rel
	 * @return the commerce discount order type rel that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscountOrderTypeRel updateCommerceDiscountOrderTypeRel(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		return commerceDiscountOrderTypeRelPersistence.update(
			commerceDiscountOrderTypeRel);
	}

	/**
	 * Returns the commerce discount order type rel local service.
	 *
	 * @return the commerce discount order type rel local service
	 */
	public CommerceDiscountOrderTypeRelLocalService
		getCommerceDiscountOrderTypeRelLocalService() {

		return commerceDiscountOrderTypeRelLocalService;
	}

	/**
	 * Sets the commerce discount order type rel local service.
	 *
	 * @param commerceDiscountOrderTypeRelLocalService the commerce discount order type rel local service
	 */
	public void setCommerceDiscountOrderTypeRelLocalService(
		CommerceDiscountOrderTypeRelLocalService
			commerceDiscountOrderTypeRelLocalService) {

		this.commerceDiscountOrderTypeRelLocalService =
			commerceDiscountOrderTypeRelLocalService;
	}

	/**
	 * Returns the commerce discount order type rel persistence.
	 *
	 * @return the commerce discount order type rel persistence
	 */
	public CommerceDiscountOrderTypeRelPersistence
		getCommerceDiscountOrderTypeRelPersistence() {

		return commerceDiscountOrderTypeRelPersistence;
	}

	/**
	 * Sets the commerce discount order type rel persistence.
	 *
	 * @param commerceDiscountOrderTypeRelPersistence the commerce discount order type rel persistence
	 */
	public void setCommerceDiscountOrderTypeRelPersistence(
		CommerceDiscountOrderTypeRelPersistence
			commerceDiscountOrderTypeRelPersistence) {

		this.commerceDiscountOrderTypeRelPersistence =
			commerceDiscountOrderTypeRelPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register(
			"com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel",
			commerceDiscountOrderTypeRelLocalService);

		_setLocalServiceUtilService(commerceDiscountOrderTypeRelLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel");

		_setLocalServiceUtilService(null);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommerceDiscountOrderTypeRelLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceDiscountOrderTypeRel.class;
	}

	protected String getModelClassName() {
		return CommerceDiscountOrderTypeRel.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				commerceDiscountOrderTypeRelPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _setLocalServiceUtilService(
		CommerceDiscountOrderTypeRelLocalService
			commerceDiscountOrderTypeRelLocalService) {

		try {
			Field field =
				CommerceDiscountOrderTypeRelLocalServiceUtil.class.
					getDeclaredField("_service");

			field.setAccessible(true);

			field.set(null, commerceDiscountOrderTypeRelLocalService);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@BeanReference(type = CommerceDiscountOrderTypeRelLocalService.class)
	protected CommerceDiscountOrderTypeRelLocalService
		commerceDiscountOrderTypeRelLocalService;

	@BeanReference(type = CommerceDiscountOrderTypeRelPersistence.class)
	protected CommerceDiscountOrderTypeRelPersistence
		commerceDiscountOrderTypeRelPersistence;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}
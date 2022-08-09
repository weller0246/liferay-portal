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

import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
import com.liferay.commerce.discount.service.CommerceDiscountAccountRelLocalService;
import com.liferay.commerce.discount.service.CommerceDiscountAccountRelLocalServiceUtil;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountAccountRelFinder;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountAccountRelPersistence;
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
 * Provides the base implementation for the commerce discount account rel local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.discount.service.impl.CommerceDiscountAccountRelLocalServiceImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.discount.service.impl.CommerceDiscountAccountRelLocalServiceImpl
 * @generated
 */
public abstract class CommerceDiscountAccountRelLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements CommerceDiscountAccountRelLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CommerceDiscountAccountRelLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CommerceDiscountAccountRelLocalServiceUtil</code>.
	 */

	/**
	 * Adds the commerce discount account rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountAccountRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountAccountRel the commerce discount account rel
	 * @return the commerce discount account rel that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscountAccountRel addCommerceDiscountAccountRel(
		CommerceDiscountAccountRel commerceDiscountAccountRel) {

		commerceDiscountAccountRel.setNew(true);

		return commerceDiscountAccountRelPersistence.update(
			commerceDiscountAccountRel);
	}

	/**
	 * Creates a new commerce discount account rel with the primary key. Does not add the commerce discount account rel to the database.
	 *
	 * @param commerceDiscountAccountRelId the primary key for the new commerce discount account rel
	 * @return the new commerce discount account rel
	 */
	@Override
	@Transactional(enabled = false)
	public CommerceDiscountAccountRel createCommerceDiscountAccountRel(
		long commerceDiscountAccountRelId) {

		return commerceDiscountAccountRelPersistence.create(
			commerceDiscountAccountRelId);
	}

	/**
	 * Deletes the commerce discount account rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountAccountRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountAccountRelId the primary key of the commerce discount account rel
	 * @return the commerce discount account rel that was removed
	 * @throws PortalException if a commerce discount account rel with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceDiscountAccountRel deleteCommerceDiscountAccountRel(
			long commerceDiscountAccountRelId)
		throws PortalException {

		return commerceDiscountAccountRelPersistence.remove(
			commerceDiscountAccountRelId);
	}

	/**
	 * Deletes the commerce discount account rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountAccountRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountAccountRel the commerce discount account rel
	 * @return the commerce discount account rel that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceDiscountAccountRel deleteCommerceDiscountAccountRel(
			CommerceDiscountAccountRel commerceDiscountAccountRel)
		throws PortalException {

		return commerceDiscountAccountRelPersistence.remove(
			commerceDiscountAccountRel);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return commerceDiscountAccountRelPersistence.dslQuery(dslQuery);
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
			CommerceDiscountAccountRel.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return commerceDiscountAccountRelPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountAccountRelModelImpl</code>.
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

		return commerceDiscountAccountRelPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountAccountRelModelImpl</code>.
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

		return commerceDiscountAccountRelPersistence.findWithDynamicQuery(
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
		return commerceDiscountAccountRelPersistence.countWithDynamicQuery(
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

		return commerceDiscountAccountRelPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public CommerceDiscountAccountRel fetchCommerceDiscountAccountRel(
		long commerceDiscountAccountRelId) {

		return commerceDiscountAccountRelPersistence.fetchByPrimaryKey(
			commerceDiscountAccountRelId);
	}

	/**
	 * Returns the commerce discount account rel with the matching UUID and company.
	 *
	 * @param uuid the commerce discount account rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel
		fetchCommerceDiscountAccountRelByUuidAndCompanyId(
			String uuid, long companyId) {

		return commerceDiscountAccountRelPersistence.fetchByUuid_C_First(
			uuid, companyId, null);
	}

	/**
	 * Returns the commerce discount account rel with the primary key.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the commerce discount account rel
	 * @return the commerce discount account rel
	 * @throws PortalException if a commerce discount account rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountAccountRel getCommerceDiscountAccountRel(
			long commerceDiscountAccountRelId)
		throws PortalException {

		return commerceDiscountAccountRelPersistence.findByPrimaryKey(
			commerceDiscountAccountRelId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			commerceDiscountAccountRelLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceDiscountAccountRel.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceDiscountAccountRelId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			commerceDiscountAccountRelLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			CommerceDiscountAccountRel.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceDiscountAccountRelId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			commerceDiscountAccountRelLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceDiscountAccountRel.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceDiscountAccountRelId");
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
				<CommerceDiscountAccountRel>() {

				@Override
				public void performAction(
						CommerceDiscountAccountRel commerceDiscountAccountRel)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, commerceDiscountAccountRel);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(
					CommerceDiscountAccountRel.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceDiscountAccountRelPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return commerceDiscountAccountRelLocalService.
			deleteCommerceDiscountAccountRel(
				(CommerceDiscountAccountRel)persistedModel);
	}

	@Override
	public BasePersistence<CommerceDiscountAccountRel> getBasePersistence() {
		return commerceDiscountAccountRelPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceDiscountAccountRelPersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns the commerce discount account rel with the matching UUID and company.
	 *
	 * @param uuid the commerce discount account rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce discount account rel
	 * @throws PortalException if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel
			getCommerceDiscountAccountRelByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException {

		return commerceDiscountAccountRelPersistence.findByUuid_C_First(
			uuid, companyId, null);
	}

	/**
	 * Returns a range of all the commerce discount account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> getCommerceDiscountAccountRels(
		int start, int end) {

		return commerceDiscountAccountRelPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of commerce discount account rels.
	 *
	 * @return the number of commerce discount account rels
	 */
	@Override
	public int getCommerceDiscountAccountRelsCount() {
		return commerceDiscountAccountRelPersistence.countAll();
	}

	/**
	 * Updates the commerce discount account rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountAccountRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountAccountRel the commerce discount account rel
	 * @return the commerce discount account rel that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscountAccountRel updateCommerceDiscountAccountRel(
		CommerceDiscountAccountRel commerceDiscountAccountRel) {

		return commerceDiscountAccountRelPersistence.update(
			commerceDiscountAccountRel);
	}

	/**
	 * Returns the commerce discount account rel local service.
	 *
	 * @return the commerce discount account rel local service
	 */
	public CommerceDiscountAccountRelLocalService
		getCommerceDiscountAccountRelLocalService() {

		return commerceDiscountAccountRelLocalService;
	}

	/**
	 * Sets the commerce discount account rel local service.
	 *
	 * @param commerceDiscountAccountRelLocalService the commerce discount account rel local service
	 */
	public void setCommerceDiscountAccountRelLocalService(
		CommerceDiscountAccountRelLocalService
			commerceDiscountAccountRelLocalService) {

		this.commerceDiscountAccountRelLocalService =
			commerceDiscountAccountRelLocalService;
	}

	/**
	 * Returns the commerce discount account rel persistence.
	 *
	 * @return the commerce discount account rel persistence
	 */
	public CommerceDiscountAccountRelPersistence
		getCommerceDiscountAccountRelPersistence() {

		return commerceDiscountAccountRelPersistence;
	}

	/**
	 * Sets the commerce discount account rel persistence.
	 *
	 * @param commerceDiscountAccountRelPersistence the commerce discount account rel persistence
	 */
	public void setCommerceDiscountAccountRelPersistence(
		CommerceDiscountAccountRelPersistence
			commerceDiscountAccountRelPersistence) {

		this.commerceDiscountAccountRelPersistence =
			commerceDiscountAccountRelPersistence;
	}

	/**
	 * Returns the commerce discount account rel finder.
	 *
	 * @return the commerce discount account rel finder
	 */
	public CommerceDiscountAccountRelFinder
		getCommerceDiscountAccountRelFinder() {

		return commerceDiscountAccountRelFinder;
	}

	/**
	 * Sets the commerce discount account rel finder.
	 *
	 * @param commerceDiscountAccountRelFinder the commerce discount account rel finder
	 */
	public void setCommerceDiscountAccountRelFinder(
		CommerceDiscountAccountRelFinder commerceDiscountAccountRelFinder) {

		this.commerceDiscountAccountRelFinder =
			commerceDiscountAccountRelFinder;
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
			"com.liferay.commerce.discount.model.CommerceDiscountAccountRel",
			commerceDiscountAccountRelLocalService);

		_setLocalServiceUtilService(commerceDiscountAccountRelLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.commerce.discount.model.CommerceDiscountAccountRel");

		_setLocalServiceUtilService(null);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommerceDiscountAccountRelLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceDiscountAccountRel.class;
	}

	protected String getModelClassName() {
		return CommerceDiscountAccountRel.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				commerceDiscountAccountRelPersistence.getDataSource();

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
		CommerceDiscountAccountRelLocalService
			commerceDiscountAccountRelLocalService) {

		try {
			Field field =
				CommerceDiscountAccountRelLocalServiceUtil.class.
					getDeclaredField("_service");

			field.setAccessible(true);

			field.set(null, commerceDiscountAccountRelLocalService);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@BeanReference(type = CommerceDiscountAccountRelLocalService.class)
	protected CommerceDiscountAccountRelLocalService
		commerceDiscountAccountRelLocalService;

	@BeanReference(type = CommerceDiscountAccountRelPersistence.class)
	protected CommerceDiscountAccountRelPersistence
		commerceDiscountAccountRelPersistence;

	@BeanReference(type = CommerceDiscountAccountRelFinder.class)
	protected CommerceDiscountAccountRelFinder commerceDiscountAccountRelFinder;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}
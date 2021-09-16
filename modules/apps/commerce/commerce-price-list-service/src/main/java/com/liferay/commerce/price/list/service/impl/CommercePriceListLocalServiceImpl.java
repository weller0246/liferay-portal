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

package com.liferay.commerce.price.list.service.impl;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.exception.CommerceBasePriceListCannotDeleteException;
import com.liferay.commerce.price.list.exception.CommercePriceListCurrencyException;
import com.liferay.commerce.price.list.exception.CommercePriceListDisplayDateException;
import com.liferay.commerce.price.list.exception.CommercePriceListExpirationDateException;
import com.liferay.commerce.price.list.exception.CommercePriceListParentPriceListGroupIdException;
import com.liferay.commerce.price.list.exception.DuplicateCommerceBasePriceListException;
import com.liferay.commerce.price.list.exception.DuplicateCommercePriceListException;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceEntryTable;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRelTable;
import com.liferay.commerce.price.list.model.CommercePriceListChannelRelTable;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRelTable;
import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRelTable;
import com.liferay.commerce.price.list.model.CommercePriceListTable;
import com.liferay.commerce.price.list.service.base.CommercePriceListLocalServiceBaseImpl;
import com.liferay.commerce.pricing.exception.CommerceUndefinedBasePriceListException;
import com.liferay.commerce.pricing.service.CommercePriceModifierLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.LongStream;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Zoltán Takács
 */
public class CommercePriceListLocalServiceImpl
	extends CommercePriceListLocalServiceBaseImpl {

	@Override
	public CommercePriceList addCatalogBaseCommercePriceList(
			long groupId, long userId, long commerceCurrencyId, String type,
			String name, ServiceContext serviceContext)
		throws PortalException {

		Date date = new Date();

		Calendar calendar = CalendarFactoryUtil.getCalendar(date.getTime());

		int displayDateHour = calendar.get(Calendar.HOUR);

		if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
			displayDateHour += 12;
		}

		return commercePriceListLocalService.addCommercePriceList(
			null, groupId, userId, commerceCurrencyId, true, type, 0L, true,
			name, 0D, calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			displayDateHour, calendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true,
			serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommercePriceList addCommerceCatalogBasePriceList(
			long groupId, long userId, long commerceCurrencyId, String type,
			String name, ServiceContext serviceContext)
		throws PortalException {

		return commercePriceListLocalService.addCatalogBaseCommercePriceList(
			groupId, userId, commerceCurrencyId, type, name, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceList addCommercePriceList(
			String externalReferenceCode, long groupId, long userId,
			long commerceCurrencyId, boolean netPrice, String type,
			long parentCommercePriceListId, boolean catalogBasePriceList,
			String name, double priority, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Commerce price list

		User user = userLocalService.getUser(userId);

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		validate(
			groupId, commerceCurrencyId, parentCommercePriceListId,
			catalogBasePriceList, 0, type);

		validateExternalReferenceCode(
			externalReferenceCode, serviceContext.getCompanyId());

		Date expirationDate = null;
		Date date = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CommercePriceListDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommercePriceListExpirationDateException.class);
		}

		long commercePriceListId = counterLocalService.increment();

		CommercePriceList commercePriceList =
			commercePriceListPersistence.create(commercePriceListId);

		commercePriceList.setExternalReferenceCode(externalReferenceCode);
		commercePriceList.setGroupId(groupId);
		commercePriceList.setCompanyId(user.getCompanyId());
		commercePriceList.setUserId(user.getUserId());
		commercePriceList.setUserName(user.getFullName());
		commercePriceList.setCommerceCurrencyId(commerceCurrencyId);
		commercePriceList.setParentCommercePriceListId(
			parentCommercePriceListId);
		commercePriceList.setCatalogBasePriceList(catalogBasePriceList);
		commercePriceList.setNetPrice(netPrice);
		commercePriceList.setType(type);
		commercePriceList.setName(name);
		commercePriceList.setPriority(priority);
		commercePriceList.setDisplayDate(displayDate);
		commercePriceList.setExpirationDate(expirationDate);

		if ((expirationDate == null) || expirationDate.after(date)) {
			commercePriceList.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commercePriceList.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		if (catalogBasePriceList) {
			commercePriceList.setStatus(WorkflowConstants.STATUS_APPROVED);
		}

		commercePriceList.setStatusByUserId(user.getUserId());
		commercePriceList.setStatusDate(serviceContext.getModifiedDate(date));
		commercePriceList.setExpandoBridgeAttributes(serviceContext);

		commercePriceList = commercePriceListPersistence.update(
			commercePriceList);

		// Workflow

		commercePriceList = startWorkflowInstance(
			user.getUserId(), commercePriceList, serviceContext);

		// Cache

		cleanPriceListCache(user.getCompanyId());

		// Resources

		resourceLocalService.addModelResources(
			commercePriceList, serviceContext);

		return commercePriceList;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceList addOrUpdateCommercePriceList(
			String externalReferenceCode, long groupId, long userId,
			long commercePriceListId, long commerceCurrencyId, boolean netPrice,
			String type, long parentCommercePriceListId,
			boolean catalogBasePriceList, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Update

		if (commercePriceListId > 0) {
			try {
				return updateCommercePriceList(
					commercePriceListId, commerceCurrencyId, netPrice, type,
					parentCommercePriceListId, catalogBasePriceList, name,
					priority, displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);
			}
			catch (NoSuchPriceListException noSuchPriceListException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find price list with ID: " +
							commercePriceListId,
						noSuchPriceListException);
				}
			}
		}

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		if (Validator.isNotNull(externalReferenceCode)) {
			CommercePriceList commercePriceList =
				commercePriceListPersistence.fetchByC_ERC(
					serviceContext.getCompanyId(), externalReferenceCode);

			if (commercePriceList != null) {
				return commercePriceListLocalService.updateCommercePriceList(
					commercePriceList.getCommercePriceListId(),
					commerceCurrencyId, netPrice, type,
					parentCommercePriceListId, catalogBasePriceList, name,
					priority, displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);
			}
		}

		// Add

		return commercePriceListLocalService.addCommercePriceList(
			externalReferenceCode, groupId, userId, commerceCurrencyId,
			netPrice, type, parentCommercePriceListId, catalogBasePriceList,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public void checkCommercePriceLists() throws PortalException {
		checkCommercePriceListsByDisplayDate();
		checkCommercePriceListsByExpirationDate();
	}

	@Override
	public void cleanPriceListCache(long companyId) {
		PortalCache<String, Serializable> portalCache =
			(PortalCache<String, Serializable>)_multiVMPool.getPortalCache(
				"PRICE_LISTS_" + companyId);

		portalCache.removeAll();
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommercePriceList deleteCommercePriceList(
			CommercePriceList commercePriceList)
		throws PortalException {

		if (commercePriceList.isCatalogBasePriceList()) {
			throw new CommerceBasePriceListCannotDeleteException();
		}

		return commercePriceListLocalService.forceDeleteCommercePriceList(
			commercePriceList);
	}

	@Override
	public CommercePriceList deleteCommercePriceList(long commercePriceListId)
		throws PortalException {

		CommercePriceList commercePriceList =
			commercePriceListPersistence.findByPrimaryKey(commercePriceListId);

		return commercePriceListLocalService.deleteCommercePriceList(
			commercePriceList);
	}

	@Override
	public void deleteCommercePriceLists(long companyId)
		throws PortalException {

		List<CommercePriceList> commercePriceLists =
			commercePriceListLocalService.getCommercePriceLists(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommercePriceList commercePriceList : commercePriceLists) {
			commercePriceListLocalService.forceDeleteCommercePriceList(
				commercePriceList);
		}
	}

	@Override
	public CommercePriceList fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commercePriceListPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public CommercePriceList fetchCatalogBaseCommercePriceList(long groupId)
		throws PortalException {

		return commercePriceListPersistence.fetchByG_C_T(
			groupId, true, CommercePriceListConstants.TYPE_PRICE_LIST);
	}

	@Override
	public CommercePriceList fetchCatalogBaseCommercePriceListByType(
			long groupId, String type)
		throws PortalException {

		return commercePriceListPersistence.fetchByG_C_T(groupId, true, type);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommercePriceList fetchCommerceCatalogBasePriceList(long groupId)
		throws PortalException {

		return commercePriceListLocalService.fetchCatalogBaseCommercePriceList(
			groupId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommercePriceList fetchCommerceCatalogBasePriceListByType(
			long groupId, String type)
		throws PortalException {

		return commercePriceListLocalService.
			fetchCatalogBaseCommercePriceListByType(groupId, type);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommercePriceList forceDeleteCommercePriceList(
			CommercePriceList commercePriceList)
		throws PortalException {

		// Commerce price entries

		commercePriceEntryLocalService.deleteCommercePriceEntries(
			commercePriceList.getCommercePriceListId());

		// Commerce price list account rels

		commercePriceListAccountRelLocalService.
			deleteCommercePriceListAccountRels(
				commercePriceList.getCommercePriceListId());

		// Commerce price list channel rels

		commercePriceListChannelRelLocalService.
			deleteCommercePriceListChannelRels(
				commercePriceList.getCommercePriceListId());

		// Commerce price list commerce account group rels

		commercePriceListCommerceAccountGroupRelLocalService.
			deleteCommercePriceListCommerceAccountGroupRels(
				commercePriceList.getCommercePriceListId());

		// Commerce price list commerce discount rels

		commercePriceListDiscountRelLocalService.
			deleteCommercePriceListDiscountRels(
				commercePriceList.getCommercePriceListId());

		// Commerce price list commerce order type rels

		commercePriceListOrderTypeRelLocalService.
			deleteCommercePriceListOrderTypeRels(
				commercePriceList.getCommercePriceListId());

		// Commerce price list commerce price modifier

		_commercePriceModifierLocalService.
			deleteCommercePriceModifiersByCommercePriceListId(
				commercePriceList.getCommercePriceListId());

		// Resources

		resourceLocalService.deleteResource(
			commercePriceList, ResourceConstants.SCOPE_INDIVIDUAL);

		// Commerce price list

		commercePriceListPersistence.remove(commercePriceList);

		// Expando

		expandoRowLocalService.deleteRows(
			commercePriceList.getCommercePriceListId());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			commercePriceList.getCompanyId(), commercePriceList.getGroupId(),
			CommercePriceList.class.getName(),
			commercePriceList.getCommercePriceListId());

		// Cache

		cleanPriceListCache(commercePriceList.getCompanyId());

		return commercePriceList;
	}

	@Override
	public CommercePriceList getCatalogBaseCommercePriceList(long groupId)
		throws PortalException {

		CommercePriceList commercePriceList =
			commercePriceListPersistence.fetchByG_C_T(
				groupId, true, CommercePriceListConstants.TYPE_PRICE_LIST);

		if (commercePriceList == null) {
			throw new CommerceUndefinedBasePriceListException();
		}

		return commercePriceList;
	}

	@Override
	public CommercePriceList getCatalogBaseCommercePriceListByType(
			long groupId, String type)
		throws PortalException {

		CommercePriceList commercePriceList =
			commercePriceListPersistence.fetchByG_C_T(groupId, true, type);

		if (commercePriceList == null) {
			throw new CommerceUndefinedBasePriceListException();
		}

		return commercePriceList;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommercePriceList getCommerceCatalogBasePriceList(long groupId)
		throws PortalException {

		return commercePriceListLocalService.getCatalogBaseCommercePriceList(
			groupId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommercePriceList getCommerceCatalogBasePriceListByType(
			long groupId, String type)
		throws PortalException {

		return commercePriceListLocalService.
			getCatalogBaseCommercePriceListByType(groupId, type);
	}

	@Override
	public Optional<CommercePriceList> getCommercePriceList(
			long companyId, long groupId, long commerceAccountId,
			long[] commerceAccountGroupIds)
		throws PortalException {

		Company company = companyLocalService.getCompany(companyId);

		if (commerceAccountGroupIds == null) {
			commerceAccountGroupIds = new long[0];
		}
		else if (commerceAccountGroupIds.length > 1) {
			commerceAccountGroupIds = ArrayUtil.unique(commerceAccountGroupIds);

			Arrays.sort(commerceAccountGroupIds);
		}

		String cacheKey = StringBundler.concat(
			groupId, StringPool.POUND, commerceAccountId, StringPool.POUND,
			StringUtil.merge(commerceAccountGroupIds));

		PortalCache<String, Serializable> portalCache =
			(PortalCache<String, Serializable>)_multiVMPool.getPortalCache(
				"PRICE_LISTS_" + company.getCompanyId());

		boolean priceListCalculated = GetterUtil.getBoolean(
			portalCache.get(cacheKey + "_calculated"));

		CommercePriceList commercePriceList =
			(CommercePriceList)portalCache.get(cacheKey);

		if (priceListCalculated) {
			return Optional.ofNullable(commercePriceList);
		}

		SearchContext searchContext = buildSearchContext(
			company.getCompanyId(), groupId, commerceAccountId,
			commerceAccountGroupIds);

		Indexer<CommercePriceList> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceList.class);

		Hits hits = indexer.search(searchContext, Field.ENTRY_CLASS_PK);

		List<Document> documents = hits.toList();

		if (documents.isEmpty()) {
			portalCache.put(cacheKey + "_calculated", true);

			return Optional.empty();
		}

		Document document = documents.get(0);

		long commercePriceListId = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		commercePriceList = fetchCommercePriceList(commercePriceListId);

		portalCache.put(cacheKey, commercePriceList);

		portalCache.put(cacheKey + "_calculated", true);

		return Optional.ofNullable(commercePriceList);
	}

	@Override
	public CommercePriceList
		getCommercePriceListByAccountAndChannelAndOrderTypeId(
			long groupId, long commerceAccountId, long commerceChannelId,
			long commerceOrderTypeId, String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, commerceAccountId, null, commerceChannelId,
				commerceOrderTypeId, type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public CommercePriceList getCommercePriceListByAccountAndChannelId(
		long groupId, long commerceAccountId, long commerceChannelId,
		String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, commerceAccountId, null, commerceChannelId, null, type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public CommercePriceList getCommercePriceListByAccountAndOrderTypeId(
		long groupId, long commerceAccountId, long commerceOrderTypeId,
		String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, commerceAccountId, null, null, commerceOrderTypeId,
				type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public CommercePriceList getCommercePriceListByAccountGroupAndOrderTypeId(
		long groupId, long[] commerceAccountGroupIds, long commerceOrderTypeId,
		String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, null, commerceAccountGroupIds, null,
				commerceOrderTypeId, type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public CommercePriceList getCommercePriceListByAccountGroupIds(
		long groupId, long[] commerceAccountGroupIds, String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, null, commerceAccountGroupIds, null, null, type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public CommercePriceList
		getCommercePriceListByAccountGroupsAndChannelAndOrderTypeId(
			long groupId, long[] commerceAccountGroupIds,
			long commerceChannelId, long commerceOrderTypeId, String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, null, commerceAccountGroupIds, commerceChannelId,
				commerceOrderTypeId, type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public CommercePriceList getCommercePriceListByAccountGroupsAndChannelId(
		long groupId, long[] commerceAccountGroupIds, long commerceChannelId,
		String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, null, commerceAccountGroupIds, commerceChannelId, null,
				type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public CommercePriceList getCommercePriceListByAccountGroupsAndOrderTypeId(
		long groupId, long[] commerceAccountGroupIds, long commerceOrderTypeId,
		String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, null, commerceAccountGroupIds, null,
				commerceOrderTypeId, type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public CommercePriceList getCommercePriceListByAccountId(
		long groupId, long commerceAccountId, String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, commerceAccountId, null, null, null, type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public CommercePriceList getCommercePriceListByChannelAndOrderTypeId(
		long groupId, long commerceChannelId, long commerceOrderTypeId,
		String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, null, null, commerceChannelId, commerceOrderTypeId,
				type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public CommercePriceList getCommercePriceListByChannelId(
		long groupId, long commerceChannelId, String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, null, null, commerceChannelId, null, type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public CommercePriceList getCommercePriceListByLowestPrice(
			long groupId, long commerceAccountId,
			long[] commerceAccountGroupIds, long commerceChannelId,
			long commerceOrderTypeId, String cPInstanceUuid, String type)
		throws PortalException {

		List<CommercePriceEntry> commercePriceEntries =
			commercePriceEntryPersistence.dslQuery(
				_getGroupByStep(
					DSLQueryFactoryUtil.selectDistinct(
						CommercePriceEntryTable.INSTANCE),
					groupId, commerceAccountId, commerceAccountGroupIds,
					commerceChannelId, commerceOrderTypeId, cPInstanceUuid, type
				).orderBy(
					CommercePriceEntryTable.INSTANCE.price.ascending()
				).limit(
					0, 1
				));

		if (commercePriceEntries.isEmpty()) {
			return null;
		}

		CommercePriceEntry commercePriceEntry = commercePriceEntries.get(0);

		return commercePriceEntry.getCommercePriceList();
	}

	@Override
	public CommercePriceList getCommercePriceListByOrderTypeId(
		long groupId, long commerceOrderTypeId, String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, null, null, null, commerceOrderTypeId, type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public CommercePriceList getCommercePriceListByUnqualified(
		long groupId, String type) {

		List<CommercePriceList> commercePriceLists = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListTable.INSTANCE),
				groupId, null, null, null, null, type
			).orderBy(
				CommercePriceListTable.INSTANCE.priority.descending(),
				CommercePriceListTable.INSTANCE.catalogBasePriceList.ascending()
			).limit(
				0, 1
			));

		if (commercePriceLists.isEmpty()) {
			return null;
		}

		return commercePriceLists.get(0);
	}

	@Override
	public List<CommercePriceList> getCommercePriceLists(
		long companyId, int start, int end) {

		return commercePriceListPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<CommercePriceList> getCommercePriceLists(
		long[] groupIds, long companyId, int start, int end) {

		return commercePriceListPersistence.findByG_C(
			groupIds, companyId, start, end);
	}

	@Override
	public List<CommercePriceList> getCommercePriceLists(
		long[] groupIds, long companyId, int status, int start, int end,
		OrderByComparator<CommercePriceList> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return commercePriceListPersistence.findByG_C_NotS(
				groupIds, companyId, WorkflowConstants.STATUS_IN_TRASH, start,
				end, orderByComparator);
		}

		return commercePriceListPersistence.findByG_C_S(
			groupIds, companyId, status, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListsCount(
		long commercePricingClassId, String name) {

		return commercePriceListFinder.countByCommercePricingClassId(
			commercePricingClassId, name);
	}

	@Override
	public int getCommercePriceListsCount(
		long[] groupIds, long companyId, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return commercePriceListPersistence.countByG_C_NotS(
				groupIds, companyId, WorkflowConstants.STATUS_IN_TRASH);
		}

		return commercePriceListPersistence.countByG_C_S(
			groupIds, companyId, status);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CommercePriceList> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceList.class);

			return indexer.search(searchContext);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Override
	public List<CommercePriceList> searchByCommercePricingClassId(
		long commercePricingClassId, String name, int start, int end) {

		return commercePriceListFinder.findByCommercePricingClassId(
			commercePricingClassId, name, start, end);
	}

	@Override
	public BaseModelSearchResult<CommercePriceList> searchCommercePriceLists(
			long companyId, long[] groupIds, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, keywords, status, start, end, sort);

		return searchCommercePriceLists(searchContext);
	}

	@Override
	public int searchCommercePriceListsCount(
			long companyId, long[] groupIds, String keywords, int status)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, keywords, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		return searchCommercePriceListsCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceList setCatalogBasePriceList(
			long commercePriceListId, boolean catalogBasePriceList)
		throws PortalException {

		CommercePriceList commercePriceList =
			commercePriceListPersistence.findByPrimaryKey(commercePriceListId);

		commercePriceList.setCatalogBasePriceList(catalogBasePriceList);

		return commercePriceListPersistence.update(commercePriceList);
	}

	@Override
	public void setCatalogBasePriceList(
			long groupId, long commercePriceListId, String type)
		throws PortalException {

		CommercePriceList baseCommercePriceList =
			commercePriceListPersistence.fetchByG_C_T(groupId, true, type);

		if (baseCommercePriceList != null) {
			commercePriceListLocalService.setCatalogBasePriceList(
				baseCommercePriceList.getCommercePriceListId(), false);
		}

		commercePriceListLocalService.setCatalogBasePriceList(
			commercePriceListId, true);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceList updateCommercePriceList(
			long commercePriceListId, long commerceCurrencyId, boolean netPrice,
			long parentCommercePriceListId, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CommercePriceList commercePriceList =
			commercePriceListPersistence.findByPrimaryKey(commercePriceListId);

		return commercePriceListLocalService.updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, netPrice,
			commercePriceList.getType(), parentCommercePriceListId,
			commercePriceList.isCatalogBasePriceList(), name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceList updateCommercePriceList(
			long commercePriceListId, long commerceCurrencyId, boolean netPrice,
			String type, long parentCommercePriceListId,
			boolean catalogBasePriceList, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Commerce price list

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommercePriceList commercePriceList =
			commercePriceListPersistence.findByPrimaryKey(commercePriceListId);

		validate(
			commercePriceList.getGroupId(), commerceCurrencyId,
			parentCommercePriceListId, catalogBasePriceList,
			commercePriceListId, type);

		Date expirationDate = null;
		Date date = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CommercePriceListDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommercePriceListExpirationDateException.class);
		}

		commercePriceList.setCommerceCurrencyId(commerceCurrencyId);
		commercePriceList.setParentCommercePriceListId(
			parentCommercePriceListId);
		commercePriceList.setCatalogBasePriceList(catalogBasePriceList);
		commercePriceList.setNetPrice(netPrice);
		commercePriceList.setType(type);
		commercePriceList.setName(name);
		commercePriceList.setPriority(priority);
		commercePriceList.setDisplayDate(displayDate);
		commercePriceList.setExpirationDate(expirationDate);

		if ((expirationDate == null) || expirationDate.after(date)) {
			commercePriceList.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commercePriceList.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commercePriceList.setStatusByUserId(user.getUserId());
		commercePriceList.setStatusDate(serviceContext.getModifiedDate(date));
		commercePriceList.setExpandoBridgeAttributes(serviceContext);

		commercePriceList = commercePriceListPersistence.update(
			commercePriceList);

		// Workflow

		commercePriceList = startWorkflowInstance(
			user.getUserId(), commercePriceList, serviceContext);

		cleanPriceListCache(commercePriceList.getCompanyId());

		return commercePriceList;
	}

	@Override
	public void updateCommercePriceListCurrencies(long commerceCurrencyId)
		throws PortalException {

		List<CommercePriceList> commercePriceLists =
			commercePriceListPersistence.findByCommerceCurrencyId(
				commerceCurrencyId);

		for (CommercePriceList commercePriceList : commercePriceLists) {
			commercePriceList.setCommerceCurrencyId(0);

			commercePriceList = commercePriceListPersistence.update(
				commercePriceList);

			cleanPriceListCache(commercePriceList.getCompanyId());

			doReindex(commercePriceList.getCommercePriceListId());
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceList updateExternalReferenceCode(
			CommercePriceList commercePriceList, String externalReferenceCode)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		commercePriceList.setExternalReferenceCode(externalReferenceCode);

		commercePriceList = commercePriceListPersistence.update(
			commercePriceList);

		cleanPriceListCache(commercePriceList.getCompanyId());

		return commercePriceList;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceList updateStatus(
			long userId, long commercePriceListId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		Date date = new Date();

		CommercePriceList commercePriceList =
			commercePriceListPersistence.findByPrimaryKey(commercePriceListId);

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(commercePriceList.getDisplayDate() != null) &&
			date.before(commercePriceList.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		Date modifiedDate = serviceContext.getModifiedDate(date);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = commercePriceList.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(date)) {
				commercePriceList.setExpirationDate(null);
			}
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			commercePriceList.setExpirationDate(date);
		}

		commercePriceList.setStatus(status);
		commercePriceList.setStatusByUserId(user.getUserId());
		commercePriceList.setStatusByUserName(user.getFullName());
		commercePriceList.setStatusDate(modifiedDate);

		commercePriceList = commercePriceListPersistence.update(
			commercePriceList);

		cleanPriceListCache(commercePriceList.getCompanyId());

		return commercePriceList;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long commerceAccountId,
		long[] commerceAccountGroupIds) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.STATUS, WorkflowConstants.STATUS_APPROVED
			).put(
				"commerceAccountGroupIds", commerceAccountGroupIds
			).put(
				"commerceAccountId", commerceAccountId
			).build());

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(1);
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setStart(0);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Sort sort = SortFactoryUtil.create(
			Field.PRIORITY + "_Number_sortable", true);

		searchContext.setSorts(sort);

		return searchContext;
	}

	protected SearchContext buildSearchContext(
		long companyId, long[] groupIds, String keywords, int status, int start,
		int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				Field.NAME, keywords
			).put(
				Field.STATUS, status
			).put(
				Field.USER_NAME, keywords
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).build());

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(groupIds);

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected void checkCommercePriceListsByDisplayDate()
		throws PortalException {

		List<CommercePriceList> commercePriceLists =
			commercePriceListPersistence.findByLtD_S(
				new Date(), WorkflowConstants.STATUS_SCHEDULED);

		for (CommercePriceList commercePriceList : commercePriceLists) {
			long userId = PortalUtil.getValidUserId(
				commercePriceList.getCompanyId(),
				commercePriceList.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCommand(Constants.UPDATE);
			serviceContext.setScopeGroupId(commercePriceList.getGroupId());

			commercePriceListLocalService.updateStatus(
				userId, commercePriceList.getCommercePriceListId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext,
				new HashMap<String, Serializable>());

			cleanPriceListCache(commercePriceList.getCompanyId());
		}
	}

	protected void checkCommercePriceListsByExpirationDate()
		throws PortalException {

		List<CommercePriceList> commercePriceLists =
			commercePriceListFinder.findByExpirationDate(
				new Date(),
				new QueryDefinition<CommercePriceList>(
					WorkflowConstants.STATUS_APPROVED));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Expiring " + commercePriceLists.size() +
					" commerce price lists");
		}

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			for (CommercePriceList commercePriceList : commercePriceLists) {
				long userId = PortalUtil.getValidUserId(
					commercePriceList.getCompanyId(),
					commercePriceList.getUserId());

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCommand(Constants.UPDATE);
				serviceContext.setScopeGroupId(commercePriceList.getGroupId());

				commercePriceListLocalService.updateStatus(
					userId, commercePriceList.getCommercePriceListId(),
					WorkflowConstants.STATUS_EXPIRED, serviceContext,
					new HashMap<String, Serializable>());

				cleanPriceListCache(commercePriceList.getCompanyId());
			}
		}
	}

	protected void doReindex(long commercePriceListId) throws PortalException {
		Indexer<CommercePriceList> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceList.class);

		indexer.reindex(CommercePriceList.class.getName(), commercePriceListId);
	}

	protected List<CommercePriceList> getCommercePriceLists(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommercePriceList> commercePriceLists = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long commercePriceListId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommercePriceList commercePriceList = fetchCommercePriceList(
				commercePriceListId);

			if (commercePriceList == null) {
				commercePriceLists = null;

				Indexer<CommercePriceList> indexer =
					IndexerRegistryUtil.getIndexer(CommercePriceList.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commercePriceLists != null) {
				commercePriceLists.add(commercePriceList);
			}
		}

		return commercePriceLists;
	}

	protected BaseModelSearchResult<CommercePriceList> searchCommercePriceLists(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CommercePriceList> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceList.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommercePriceList> commercePriceLists = getCommercePriceLists(
				hits);

			if (commercePriceLists != null) {
				return new BaseModelSearchResult<>(
					commercePriceLists, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected int searchCommercePriceListsCount(SearchContext searchContext)
		throws PortalException {

		Indexer<CommercePriceList> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceList.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	protected CommercePriceList startWorkflowInstance(
			long userId, CommercePriceList commercePriceList,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			commercePriceList.getCompanyId(), commercePriceList.getGroupId(),
			userId, CommercePriceList.class.getName(),
			commercePriceList.getCommercePriceListId(), commercePriceList,
			serviceContext, workflowContext);
	}

	protected void validate(
			long groupId, long commerceCurrencyId,
			long parentCommercePriceListId, boolean catalogBasePriceList,
			long commercePriceListId, String type)
		throws PortalException {

		if (catalogBasePriceList) {
			CommercePriceList basePriceList =
				commercePriceListPersistence.fetchByG_C_T(groupId, true, type);

			if ((basePriceList != null) &&
				(basePriceList.getCommercePriceListId() !=
					commercePriceListId)) {

				throw new DuplicateCommerceBasePriceListException();
			}
		}

		if (parentCommercePriceListId > 0) {
			if (parentCommercePriceListId == commercePriceListId) {
				throw new CommercePriceListParentPriceListGroupIdException();
			}

			CommercePriceList commercePriceList =
				commercePriceListLocalService.fetchCommercePriceList(
					parentCommercePriceListId);

			if ((commercePriceList != null) &&
				(commercePriceList.getGroupId() != groupId)) {

				throw new CommercePriceListParentPriceListGroupIdException();
			}
		}

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchCommerceCurrency(
				commerceCurrencyId);

		if (commerceCurrency == null) {
			throw new CommercePriceListCurrencyException();
		}
	}

	protected void validateExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		CommercePriceList commercePriceList =
			commercePriceListPersistence.fetchByC_ERC(
				companyId, externalReferenceCode);

		if (commercePriceList != null) {
			throw new DuplicateCommercePriceListException(
				"There is another commerce price list with external " +
					"reference code " + externalReferenceCode);
		}
	}

	private GroupByStep _getGroupByStep(
		FromStep fromStep, Long groupId, Long commerceAccountId,
		long[] commerceAccountGroupIds, Long commerceChannelId,
		Long commerceOrderTypeId, String type) {

		JoinStep joinStep = fromStep.from(CommercePriceListTable.INSTANCE);
		Predicate predicate = CommercePriceListTable.INSTANCE.status.eq(
			WorkflowConstants.STATUS_APPROVED
		).and(
			CommercePriceListTable.INSTANCE.groupId.eq(groupId)
		).and(
			CommercePriceListTable.INSTANCE.type.eq(type)
		);

		if (commerceAccountId != null) {
			joinStep = joinStep.innerJoinON(
				CommercePriceListAccountRelTable.INSTANCE,
				CommercePriceListAccountRelTable.INSTANCE.commercePriceListId.
					eq(CommercePriceListTable.INSTANCE.commercePriceListId));
			predicate = predicate.and(
				CommercePriceListAccountRelTable.INSTANCE.commerceAccountId.eq(
					commerceAccountId));
		}
		else {
			joinStep = joinStep.leftJoinOn(
				CommercePriceListAccountRelTable.INSTANCE,
				CommercePriceListAccountRelTable.INSTANCE.commercePriceListId.
					eq(CommercePriceListTable.INSTANCE.commercePriceListId));
			predicate = predicate.and(
				CommercePriceListAccountRelTable.INSTANCE.
					commercePriceListAccountRelId.isNull());
		}

		if (commerceAccountGroupIds != null) {
			if (commerceAccountGroupIds.length == 0) {
				commerceAccountGroupIds = new long[] {0};
			}

			joinStep = joinStep.innerJoinON(
				CommercePriceListCommerceAccountGroupRelTable.INSTANCE,
				CommercePriceListCommerceAccountGroupRelTable.INSTANCE.
					commercePriceListId.eq(
						CommercePriceListTable.INSTANCE.commercePriceListId));

			LongStream longStream = Arrays.stream(commerceAccountGroupIds);

			predicate = predicate.and(
				CommercePriceListCommerceAccountGroupRelTable.INSTANCE.
					commerceAccountGroupId.in(
						longStream.boxed(
						).toArray(
							Long[]::new
						)));
		}
		else {
			joinStep = joinStep.leftJoinOn(
				CommercePriceListCommerceAccountGroupRelTable.INSTANCE,
				CommercePriceListCommerceAccountGroupRelTable.INSTANCE.
					commercePriceListId.eq(
						CommercePriceListTable.INSTANCE.commercePriceListId));
			predicate = predicate.and(
				CommercePriceListCommerceAccountGroupRelTable.INSTANCE.
					commercePriceListCommerceAccountGroupRelId.isNull());
		}

		if (commerceChannelId != null) {
			joinStep = joinStep.innerJoinON(
				CommercePriceListChannelRelTable.INSTANCE,
				CommercePriceListChannelRelTable.INSTANCE.commercePriceListId.
					eq(CommercePriceListTable.INSTANCE.commercePriceListId));
			predicate = predicate.and(
				CommercePriceListChannelRelTable.INSTANCE.commerceChannelId.eq(
					commerceChannelId));
		}
		else {
			joinStep = joinStep.leftJoinOn(
				CommercePriceListChannelRelTable.INSTANCE,
				CommercePriceListChannelRelTable.INSTANCE.commercePriceListId.
					eq(CommercePriceListTable.INSTANCE.commercePriceListId));
			predicate = predicate.and(
				CommercePriceListChannelRelTable.INSTANCE.
					CommercePriceListChannelRelId.isNull());
		}

		if (commerceOrderTypeId != null) {
			joinStep = joinStep.innerJoinON(
				CommercePriceListOrderTypeRelTable.INSTANCE,
				CommercePriceListOrderTypeRelTable.INSTANCE.commercePriceListId.
					eq(CommercePriceListTable.INSTANCE.commercePriceListId));
			predicate = predicate.and(
				CommercePriceListOrderTypeRelTable.INSTANCE.commerceOrderTypeId.
					eq(commerceOrderTypeId));
		}
		else {
			joinStep = joinStep.leftJoinOn(
				CommercePriceListOrderTypeRelTable.INSTANCE,
				CommercePriceListOrderTypeRelTable.INSTANCE.commercePriceListId.
					eq(CommercePriceListTable.INSTANCE.commercePriceListId));
			predicate = predicate.and(
				CommercePriceListOrderTypeRelTable.INSTANCE.
					commercePriceListOrderTypeRelId.isNull());
		}

		return joinStep.where(predicate);
	}

	private GroupByStep _getGroupByStep(
		FromStep fromStep, Long groupId, Long commerceAccountId,
		long[] commerceAccountGroupIds, Long commerceChannelId,
		Long commerceOrderTypeId, String cPInstanceUuid, String type) {

		JoinStep joinStep = fromStep.from(
			CommercePriceEntryTable.INSTANCE
		).innerJoinON(
			CommercePriceListTable.INSTANCE,
			CommercePriceListTable.INSTANCE.commercePriceListId.eq(
				CommercePriceEntryTable.INSTANCE.commercePriceListId)
		).leftJoinOn(
			CommercePriceListAccountRelTable.INSTANCE,
			CommercePriceListAccountRelTable.INSTANCE.commercePriceListId.eq(
				CommercePriceListTable.INSTANCE.commercePriceListId)
		).leftJoinOn(
			CommercePriceListCommerceAccountGroupRelTable.INSTANCE,
			CommercePriceListCommerceAccountGroupRelTable.INSTANCE.
				commercePriceListId.eq(
					CommercePriceListTable.INSTANCE.commercePriceListId)
		).leftJoinOn(
			CommercePriceListChannelRelTable.INSTANCE,
			CommercePriceListChannelRelTable.INSTANCE.commercePriceListId.eq(
				CommercePriceListTable.INSTANCE.commercePriceListId)
		).leftJoinOn(
			CommercePriceListOrderTypeRelTable.INSTANCE,
			CommercePriceListOrderTypeRelTable.INSTANCE.commercePriceListId.eq(
				CommercePriceListTable.INSTANCE.commercePriceListId)
		);

		Long[] commerceAccountGroupObjs = {0L};

		if ((commerceAccountGroupIds != null) &&
			(commerceAccountGroupIds.length > 0)) {

			LongStream longStream = Arrays.stream(commerceAccountGroupIds);

			commerceAccountGroupObjs = longStream.boxed(
			).toArray(
				Long[]::new
			);
		}

		Predicate predicate = CommercePriceListTable.INSTANCE.status.eq(
			WorkflowConstants.STATUS_APPROVED
		).and(
			CommercePriceListTable.INSTANCE.groupId.eq(groupId)
		).and(
			CommercePriceListTable.INSTANCE.type.eq(type)
		).and(
			CommercePriceListAccountRelTable.INSTANCE.commerceAccountId.eq(
				commerceAccountId
			).or(
				CommercePriceListAccountRelTable.INSTANCE.
					commercePriceListAccountRelId.isNull()
			).withParentheses()
		).and(
			CommercePriceListCommerceAccountGroupRelTable.INSTANCE.
				commerceAccountGroupId.in(
					commerceAccountGroupObjs
				).or(
					CommercePriceListCommerceAccountGroupRelTable.INSTANCE.
						commercePriceListCommerceAccountGroupRelId.isNull()
				).withParentheses()
		).and(
			CommercePriceListChannelRelTable.INSTANCE.commerceChannelId.eq(
				commerceChannelId
			).or(
				CommercePriceListChannelRelTable.INSTANCE.
					CommercePriceListChannelRelId.isNull()
			).withParentheses()
		).and(
			CommercePriceListOrderTypeRelTable.INSTANCE.commerceOrderTypeId.eq(
				commerceOrderTypeId
			).or(
				CommercePriceListOrderTypeRelTable.INSTANCE.
					commercePriceListOrderTypeRelId.isNull()
			).withParentheses()
		);

		if (!Validator.isBlank(cPInstanceUuid)) {
			predicate = predicate.and(
				CommercePriceEntryTable.INSTANCE.CPInstanceUuid.eq(
					cPInstanceUuid)
			).and(
				CommercePriceEntryTable.INSTANCE.status.eq(
					WorkflowConstants.STATUS_APPROVED)
			);
		}

		return joinStep.where(predicate);
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListLocalServiceImpl.class);

	@ServiceReference(type = CommerceCurrencyLocalService.class)
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@ServiceReference(type = CommercePriceModifierLocalService.class)
	private CommercePriceModifierLocalService
		_commercePriceModifierLocalService;

	@ServiceReference(type = MultiVMPool.class)
	private MultiVMPool _multiVMPool;

}
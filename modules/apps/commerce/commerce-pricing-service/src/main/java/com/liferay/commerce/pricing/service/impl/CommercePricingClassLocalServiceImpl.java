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

package com.liferay.commerce.pricing.service.impl;

import com.liferay.commerce.pricing.exception.CommercePricingClassTitleException;
import com.liferay.commerce.pricing.exception.NoSuchPricingClassException;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.service.base.CommercePricingClassLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingClassLocalServiceImpl
	extends CommercePricingClassLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePricingClass addCommercePricingClass(
			long userId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		return addCommercePricingClass(
			null, userId, titleMap, descriptionMap, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePricingClass addCommercePricingClass(
			String externalReferenceCode, long userId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(titleMap);

		long commercePricingClassId = counterLocalService.increment();

		CommercePricingClass commercePricingClass =
			commercePricingClassPersistence.create(commercePricingClassId);

		commercePricingClass.setExternalReferenceCode(externalReferenceCode);
		commercePricingClass.setCompanyId(serviceContext.getCompanyId());
		commercePricingClass.setUserId(user.getUserId());
		commercePricingClass.setUserName(user.getFullName());
		commercePricingClass.setTitleMap(titleMap);
		commercePricingClass.setDescriptionMap(descriptionMap);
		commercePricingClass.setExpandoBridgeAttributes(serviceContext);

		Date date = new Date();

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			date.getTime(), user.getTimeZone());

		commercePricingClass.setLastPublishDate(calendar.getTime());

		commercePricingClass = commercePricingClassPersistence.update(
			commercePricingClass);

		// Resources

		resourceLocalService.addModelResources(
			commercePricingClass, serviceContext);

		return commercePricingClass;
	}

	@Override
	public CommercePricingClass addOrUpdateCommercePricingClass(
			String externalReferenceCode, long commercePricingClassId,
			long userId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		if (commercePricingClassId > 0) {
			try {
				return commercePricingClassLocalService.
					updateCommercePricingClass(
						commercePricingClassId, userId, titleMap,
						descriptionMap, serviceContext);
			}
			catch (NoSuchPricingClassException noSuchPricingClassException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find pricing class with ID: " +
							commercePricingClassId,
						noSuchPricingClassException);
				}
			}
		}

		if (!Validator.isBlank(externalReferenceCode)) {
			CommercePricingClass commercePricingClass =
				commercePricingClassPersistence.fetchByC_ERC(
					serviceContext.getCompanyId(), externalReferenceCode);

			if (commercePricingClass != null) {
				return commercePricingClassLocalService.
					updateCommercePricingClass(
						commercePricingClassId, userId, titleMap,
						descriptionMap, serviceContext);
			}
		}

		return commercePricingClassLocalService.addCommercePricingClass(
			externalReferenceCode, userId, titleMap, descriptionMap,
			serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommercePricingClass deleteCommercePricingClass(
			CommercePricingClass commercePricingClass)
		throws PortalException {

		long commercePricingClassId =
			commercePricingClass.getCommercePricingClassId();

		commercePricingClassCPDefinitionRelLocalService.
			deleteCommercePricingClassCPDefinitionRels(commercePricingClassId);

		commercePricingClassPersistence.remove(commercePricingClass);

		// Resources

		resourceLocalService.deleteResource(
			commercePricingClass, ResourceConstants.SCOPE_INDIVIDUAL);

		// Expando

		expandoRowLocalService.deleteRows(commercePricingClassId);

		return commercePricingClass;
	}

	@Override
	public CommercePricingClass deleteCommercePricingClass(
			long commercePricingClassId)
		throws PortalException {

		CommercePricingClass commercePricingClass =
			commercePricingClassPersistence.findByPrimaryKey(
				commercePricingClassId);

		return commercePricingClassLocalService.deleteCommercePricingClass(
			commercePricingClass);
	}

	@Override
	public void deleteCommercePricingClasses(long companyId)
		throws PortalException {

		List<CommercePricingClass> commercePricingClasses =
			commercePricingClassPersistence.findByCompanyId(companyId);

		for (CommercePricingClass commercePricingClass :
				commercePricingClasses) {

			commercePricingClassLocalService.deleteCommercePricingClass(
				commercePricingClass);
		}
	}

	@Override
	public CommercePricingClass fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commercePricingClassPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public long[] getCommercePricingClassByCPDefinition(long cpDefinitionId) {
		List<CommercePricingClassCPDefinitionRel>
			commercePricingClassCPDefinitionRels =
				commercePricingClassCPDefinitionRelLocalService.
					getCommercePricingClassByCPDefinitionId(cpDefinitionId);

		Stream<CommercePricingClassCPDefinitionRel> stream =
			commercePricingClassCPDefinitionRels.stream();

		LongStream longStream = stream.mapToLong(
			CommercePricingClassCPDefinitionRel::getCommercePricingClassId);

		return longStream.toArray();
	}

	@Override
	public int getCommercePricingClassCountByCPDefinitionId(
		long cpDefinitionId, String title) {

		return commercePricingClassFinder.countByCPDefinitionId(
			cpDefinitionId, title);
	}

	@Override
	public List<CommercePricingClass> getCommercePricingClasses(
		long companyId, int start, int end,
		OrderByComparator<CommercePricingClass> orderByComparator) {

		return commercePricingClassPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePricingClassesCount(long companyId) {
		return commercePricingClassPersistence.countByCompanyId(companyId);
	}

	@Override
	public int getCommercePricingClassesCount(
		long cpDefinitionId, String title) {

		return commercePricingClassFinder.countByCPDefinitionId(
			cpDefinitionId, title);
	}

	@Override
	public List<CommercePricingClass> searchByCPDefinitionId(
		long cpDefinitionId, String title, int start, int end) {

		return commercePricingClassFinder.findByCPDefinitionId(
			cpDefinitionId, title, start, end);
	}

	@Override
	public BaseModelSearchResult<CommercePricingClass>
			searchCommercePricingClasses(
				long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, start, end, sort);

		return searchCommercePricingClasses(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePricingClass updateCommercePricingClass(
			long commercePricingClassId, long userId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommercePricingClass commercePricingClass =
			commercePricingClassPersistence.findByPrimaryKey(
				commercePricingClassId);

		validate(titleMap);

		commercePricingClass.setCompanyId(serviceContext.getCompanyId());
		commercePricingClass.setUserId(user.getUserId());
		commercePricingClass.setUserName(user.getFullName());
		commercePricingClass.setTitleMap(titleMap);
		commercePricingClass.setDescriptionMap(descriptionMap);

		Date date = new Date();

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			date.getTime(), user.getTimeZone());

		commercePricingClass.setLastPublishDate(calendar.getTime());

		commercePricingClass.setExpandoBridgeAttributes(serviceContext);

		return commercePricingClassPersistence.update(commercePricingClass);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePricingClass updateCommercePricingClassExternalReferenceCode(
			String externalReferenceCode, long commercePricingClassId)
		throws PortalException {

		CommercePricingClass commercePricingClass =
			commercePricingClassLocalService.getCommercePricingClass(
				commercePricingClassId);

		commercePricingClass.setExternalReferenceCode(externalReferenceCode);

		return commercePricingClassLocalService.updateCommercePricingClass(
			commercePricingClass);
	}

	protected SearchContext buildSearchContext(
		long companyId, String keywords, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.CONTENT, keywords
			).put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				Field.NAME, keywords
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).build());

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

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

	protected List<CommercePricingClass> getCommercePricingClasses(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommercePricingClass> commercePricingClasses = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long commercePricingClassId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommercePricingClass commercePricingClass =
				fetchCommercePricingClass(commercePricingClassId);

			if (commercePricingClass == null) {
				commercePricingClasses = null;

				Indexer<CommercePricingClass> indexer =
					IndexerRegistryUtil.getIndexer(CommercePricingClass.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commercePricingClasses != null) {
				commercePricingClasses.add(commercePricingClass);
			}
		}

		return commercePricingClasses;
	}

	protected BaseModelSearchResult<CommercePricingClass>
			searchCommercePricingClasses(SearchContext searchContext)
		throws PortalException {

		Indexer<CommercePricingClass> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommercePricingClass.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommercePricingClass> commercePricingClasses =
				getCommercePricingClasses(hits);

			if (commercePricingClasses != null) {
				return new BaseModelSearchResult<>(
					commercePricingClasses, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void validate(Map<Locale, String> titleMap)
		throws PortalException {

		if ((titleMap == null) || titleMap.isEmpty()) {
			throw new CommercePricingClassTitleException();
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
		Field.TITLE
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePricingClassLocalServiceImpl.class);

}
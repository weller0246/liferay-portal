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

package com.liferay.object.rest.internal.manager.v1_0;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.internal.dto.v1_0.converter.ObjectEntryDTOConverter;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.GroupUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

import java.text.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(immediate = true, service = ObjectEntryManager.class)
public class ObjectEntryManagerImpl implements ObjectEntryManager {

	@Override
	public ObjectEntry addObjectEntry(
			DTOConverterContext dtoConverterContext, long userId,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			String scopeKey)
		throws Exception {

		return _objectEntryDTOConverter.toDTO(
			dtoConverterContext,
			_objectEntryLocalService.addObjectEntry(
				userId, _getGroupId(objectDefinition, scopeKey),
				objectDefinition.getObjectDefinitionId(),
				_toObjectValues(
					objectDefinition.getObjectDefinitionId(),
					objectEntry.getProperties(),
					dtoConverterContext.getLocale()),
				new ServiceContext()));
	}

	@Override
	public ObjectEntry addOrUpdateObjectEntry(
			DTOConverterContext dtoConverterContext,
			String externalReferenceCode, long userId,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			String scopeKey)
		throws Exception {

		return _objectEntryDTOConverter.toDTO(
			dtoConverterContext,
			_objectEntryLocalService.addOrUpdateObjectEntry(
				externalReferenceCode, userId,
				_getGroupId(objectDefinition, scopeKey),
				objectDefinition.getObjectDefinitionId(),
				_toObjectValues(
					objectDefinition.getObjectDefinitionId(),
					objectEntry.getProperties(),
					dtoConverterContext.getLocale()),
				new ServiceContext()));
	}

	@Override
	public void deleteObjectEntry(long objectEntryId) throws Exception {
		_objectEntryLocalService.deleteObjectEntry(objectEntryId);
	}

	@Override
	public void deleteObjectEntry(
			String externalReferenceCode, long companyId,
			ObjectDefinition objectDefinition, String scopeKey)
		throws Exception {

		_objectEntryLocalService.deleteObjectEntry(
			externalReferenceCode, companyId,
			_getGroupId(objectDefinition, scopeKey));
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			Filter filter, Pagination pagination, String search, Sort[] sorts)
		throws Exception {

		long objectDefinitionId = objectDefinition.getObjectDefinitionId();

		return SearchUtil.search(
			new HashMap<>(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"objectDefinitionId",
						String.valueOf(objectDefinitionId)),
					BooleanClauseOccur.MUST);
			},
			filter, objectDefinition.getClassName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_ANY);
				searchContext.setAttribute(
					"objectDefinitionId", objectDefinitionId);
				searchContext.setCompanyId(companyId);
				searchContext.setGroupIds(
					new long[] {_getGroupId(objectDefinition, scopeKey)});
			},
			sorts,
			document -> getObjectEntry(
				dtoConverterContext,
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public ObjectEntry getObjectEntry(
			DTOConverterContext dtoConverterContext, long objectEntryId)
		throws Exception {

		return _objectEntryDTOConverter.toDTO(
			dtoConverterContext,
			_objectEntryLocalService.getObjectEntry(objectEntryId));
	}

	@Override
	public ObjectEntry getObjectEntry(
			DTOConverterContext dtoConverterContext,
			String externalReferenceCode, long companyId,
			ObjectDefinition objectDefinition, String scopeKey)
		throws Exception {

		return _objectEntryDTOConverter.toDTO(
			dtoConverterContext,
			_objectEntryLocalService.getObjectEntry(
				externalReferenceCode, companyId,
				_getGroupId(objectDefinition, scopeKey)));
	}

	@Override
	public ObjectEntry updateObjectEntry(
			DTOConverterContext dtoConverterContext, long userId,
			long objectEntryId, ObjectEntry objectEntry)
		throws Exception {

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryLocalService.getObjectEntry(objectEntryId);

		return _objectEntryDTOConverter.toDTO(
			dtoConverterContext,
			_objectEntryLocalService.updateObjectEntry(
				userId, objectEntryId,
				_toObjectValues(
					serviceBuilderObjectEntry.getObjectDefinitionId(),
					objectEntry.getProperties(),
					dtoConverterContext.getLocale()),
				new ServiceContext()));
	}

	private long _getGroupId(
		ObjectDefinition objectDefinition, String scopeKey) {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (objectScopeProvider.isGroupAware()) {
			if (Objects.equals("site", objectDefinition.getScope())) {
				return GroupUtil.getGroupId(
					objectDefinition.getCompanyId(), scopeKey,
					_groupLocalService);
			}

			return GroupUtil.getDepotGroupId(
				scopeKey, objectDefinition.getCompanyId(),
				_depotEntryLocalService, _groupLocalService);
		}

		return 0;
	}

	private Date _toDate(Locale locale, String valueString) {
		if (Validator.isNull(valueString)) {
			return null;
		}

		try {
			return DateUtil.parseDate(
				"yyyy-MM-dd'T'HH:mm:ss'Z'", valueString, locale);
		}
		catch (ParseException parseException1) {
			try {
				return DateUtil.parseDate("yyyy-MM-dd", valueString, locale);
			}
			catch (ParseException parseException2) {
				throw new BadRequestException(
					"Unable to parse date that does not conform to ISO-8601",
					parseException2);
			}
		}
	}

	private Map<String, Serializable> _toObjectValues(
		long objectDefinitionId, Map<String, Object> properties,
		Locale locale) {

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(objectDefinitionId);

		Map<String, Serializable> values = new HashMap<>();

		for (ObjectField objectField : objectFields) {
			String name = objectField.getName();

			Object object = properties.get(name);

			if (object == null) {
				continue;
			}

			if (Objects.equals(objectField.getType(), "Date")) {
				values.put(name, _toDate(locale, String.valueOf(object)));
			}
			else {
				values.put(name, (Serializable)object);
			}
		}

		return values;
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectEntryDTOConverter _objectEntryDTOConverter;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}
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

package com.liferay.content.dashboard.web.internal.search.request;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class ContentDashboardSearchContextBuilder {

	public ContentDashboardSearchContextBuilder(
		HttpServletRequest httpServletRequest,
		AssetCategoryLocalService assetCategoryLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService) {

		_httpServletRequest = httpServletRequest;
		_assetCategoryLocalService = assetCategoryLocalService;
		_assetVocabularyLocalService = assetVocabularyLocalService;
	}

	public SearchContext build() {
		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		Integer status = GetterUtil.getInteger(
			ParamUtil.getInteger(
				_httpServletRequest, "status", WorkflowConstants.STATUS_ANY));

		if (status == WorkflowConstants.STATUS_APPROVED) {
			searchContext.setAttribute("head", Boolean.TRUE);
		}
		else {
			searchContext.setAttribute("latest", Boolean.TRUE);
		}

		searchContext.setAttribute("status", status);

		searchContext.setBooleanClauses(
			_getBooleanClauses(
				new AssetCategoryIds(
					ParamUtil.getLongValues(
						_httpServletRequest, "assetCategoryId"),
					_assetCategoryLocalService, _assetVocabularyLocalService),
				ParamUtil.getStringValues(_httpServletRequest, "assetTagId"),
				ParamUtil.getLongValues(_httpServletRequest, "authorIds"),
				ParamUtil.getStringValues(
					_httpServletRequest, "fileExtension")));

		String[] contentDashboardItemSubtypePayloads =
			ParamUtil.getParameterValues(
				_httpServletRequest, "contentDashboardItemSubtypePayload",
				new String[0], false);

		if (!ArrayUtil.isEmpty(contentDashboardItemSubtypePayloads)) {
			searchContext.setClassTypeIds(
				Stream.of(
					contentDashboardItemSubtypePayloads
				).map(
					contentDashboardItemSubtypePayload -> {
						try {
							return Optional.of(
								JSONFactoryUtil.createJSONObject(
									contentDashboardItemSubtypePayload));
						}
						catch (JSONException jsonException) {
							_log.error(jsonException, jsonException);

							return Optional.<JSONObject>empty();
						}
					}
				).filter(
					Optional::isPresent
				).map(
					Optional::get
				).mapToLong(
					jsonObject -> jsonObject.getLong("classPK")
				).toArray());
		}

		if (_end != null) {
			searchContext.setEnd(_end);
		}

		long groupId = ParamUtil.getLong(_httpServletRequest, "scopeId");

		if (groupId > 0) {
			searchContext.setGroupIds(new long[] {groupId});
		}
		else {
			searchContext.setGroupIds(null);
		}

		searchContext.setIncludeInternalAssetCategories(true);

		if (_sort != null) {
			searchContext.setSorts(_sort);
		}

		if (_start != null) {
			searchContext.setStart(_start);
		}

		return searchContext;
	}

	public ContentDashboardSearchContextBuilder withEnd(int end) {
		_end = end;

		return this;
	}

	public ContentDashboardSearchContextBuilder withSort(Sort sort) {
		_sort = sort;

		return this;
	}

	public ContentDashboardSearchContextBuilder withStart(int start) {
		_start = start;

		return this;
	}

	private Optional<Filter> _getAssetCategoryIdsFilterOptional(
		AssetCategoryIds assetCategoryIds) {

		if ((assetCategoryIds == null) ||
			(ArrayUtil.isEmpty(
				assetCategoryIds.getExternalAssetCategoryIds()) &&
			 ArrayUtil.isEmpty(
				 assetCategoryIds.getInternalAssetCategoryIds()))) {

			return Optional.empty();
		}

		BooleanFilter booleanFilter = new BooleanFilter();

		if (!ArrayUtil.isEmpty(
				assetCategoryIds.getExternalAssetCategoryIds())) {

			booleanFilter.add(
				_getTermsFilter(
					Field.ASSET_CATEGORY_IDS,
					assetCategoryIds.getExternalAssetCategoryIds()),
				BooleanClauseOccur.MUST);
		}

		if (!ArrayUtil.isEmpty(
				assetCategoryIds.getInternalAssetCategoryIds())) {

			booleanFilter.add(
				_getTermsFilter(
					Field.ASSET_INTERNAL_CATEGORY_IDS,
					assetCategoryIds.getInternalAssetCategoryIds()),
				BooleanClauseOccur.MUST);
		}

		return Optional.of(booleanFilter);
	}

	private Optional<Filter> _getAssetTagNamesFilterOptional(
		String[] assetTagNames) {

		if (ArrayUtil.isEmpty(assetTagNames)) {
			return Optional.empty();
		}

		BooleanFilter booleanFilter = new BooleanFilter();

		for (String assetTagName : assetTagNames) {
			booleanFilter.addTerm(
				Field.ASSET_TAG_NAMES + ".raw", assetTagName,
				BooleanClauseOccur.MUST);
		}

		return Optional.of(booleanFilter);
	}

	private Optional<Filter> _getAuthorIdsFilterOptional(long[] authorIds) {
		if (ArrayUtil.isEmpty(authorIds)) {
			return Optional.empty();
		}

		TermsFilter termsFilter = new TermsFilter(Field.USER_ID);

		for (long authorId : authorIds) {
			termsFilter.addValue(String.valueOf(authorId));
		}

		return Optional.of(termsFilter);
	}

	private BooleanClause[] _getBooleanClauses(
		AssetCategoryIds assetCategoryIds, String[] assetTagNames,
		long[] authorIds, String[] fileExtensions) {

		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		BooleanFilter booleanFilter = new BooleanFilter();

		Optional<Filter> assetCategoryIdsFilterOptional =
			_getAssetCategoryIdsFilterOptional(assetCategoryIds);

		if (assetCategoryIdsFilterOptional.isPresent()) {
			booleanFilter.add(
				assetCategoryIdsFilterOptional.get(), BooleanClauseOccur.MUST);
		}

		Optional<Filter> assetTagNamesFilterOptional =
			_getAssetTagNamesFilterOptional(assetTagNames);

		if (assetTagNamesFilterOptional.isPresent()) {
			booleanFilter.add(
				assetTagNamesFilterOptional.get(), BooleanClauseOccur.MUST);
		}

		Optional<Filter> authorIdsFilterOptional = _getAuthorIdsFilterOptional(
			authorIds);

		if (authorIdsFilterOptional.isPresent()) {
			booleanFilter.add(
				authorIdsFilterOptional.get(), BooleanClauseOccur.MUST);
		}

		Optional<Filter> fileExtensionsFilterOptional =
			_getFileExtensionsFilterOptional(fileExtensions);

		fileExtensionsFilterOptional.map(
			fileExtensionsFilter -> booleanFilter.add(
				fileExtensionsFilterOptional.get(), BooleanClauseOccur.MUST));

		booleanQueryImpl.setPreBooleanFilter(booleanFilter);

		return new BooleanClause[] {
			BooleanClauseFactoryUtil.create(
				booleanQueryImpl, BooleanClauseOccur.MUST.getName())
		};
	}

	private Optional<Filter> _getFileExtensionsFilterOptional(
		String[] fileExtensions) {

		if (ArrayUtil.isEmpty(fileExtensions)) {
			return Optional.empty();
		}

		TermsFilter termsFilter = new TermsFilter("fileExtension");

		for (String fileExtension : fileExtensions) {
			termsFilter.addValue(fileExtension);
		}

		return Optional.of(termsFilter);
	}

	private BooleanFilter _getTermsFilter(String field, long[] values) {
		BooleanFilter booleanFilter = new BooleanFilter();

		for (Long value : values) {
			booleanFilter.addTerm(
				field, String.valueOf(value), BooleanClauseOccur.MUST);
		}

		return booleanFilter;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardSearchContextBuilder.class);

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private Integer _end;
	private final HttpServletRequest _httpServletRequest;
	private Sort _sort;
	private Integer _start;

	private static class AssetCategoryIds {

		public AssetCategoryIds(
			long[] assetCategoryIds,
			AssetCategoryLocalService assetCategoryLocalService,
			AssetVocabularyLocalService assetVocabularyLocalService) {

			List<Long> externalAssetCategoryIds = new ArrayList<>();
			List<Long> internalAssetCategoryIds = new ArrayList<>();

			for (long assetCategoryId : assetCategoryIds) {
				AssetCategory assetCategory =
					assetCategoryLocalService.fetchAssetCategory(
						assetCategoryId);

				AssetVocabulary assetVocabulary =
					assetVocabularyLocalService.fetchAssetVocabulary(
						assetCategory.getVocabularyId());

				if (assetVocabulary.getVisibilityType() ==
						AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL) {

					internalAssetCategoryIds.add(assetCategoryId);
				}
				else {
					externalAssetCategoryIds.add(assetCategoryId);
				}
			}

			_externalAssetCategoryIds = ArrayUtil.toLongArray(
				externalAssetCategoryIds);
			_internalAssetCategoryIds = ArrayUtil.toLongArray(
				internalAssetCategoryIds);
		}

		public long[] getExternalAssetCategoryIds() {
			return _externalAssetCategoryIds;
		}

		public long[] getInternalAssetCategoryIds() {
			return _internalAssetCategoryIds;
		}

		private final long[] _externalAssetCategoryIds;
		private final long[] _internalAssetCategoryIds;

	}

}
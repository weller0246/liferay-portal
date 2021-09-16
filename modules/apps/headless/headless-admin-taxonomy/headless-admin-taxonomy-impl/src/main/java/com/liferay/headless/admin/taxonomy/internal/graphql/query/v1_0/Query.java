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

package com.liferay.headless.admin.taxonomy.internal.graphql.query.v1_0;

import com.liferay.headless.admin.taxonomy.dto.v1_0.Keyword;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.resource.v1_0.KeywordResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotEmpty;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	public static void setKeywordResourceComponentServiceObjects(
		ComponentServiceObjects<KeywordResource>
			keywordResourceComponentServiceObjects) {

		_keywordResourceComponentServiceObjects =
			keywordResourceComponentServiceObjects;
	}

	public static void setTaxonomyCategoryResourceComponentServiceObjects(
		ComponentServiceObjects<TaxonomyCategoryResource>
			taxonomyCategoryResourceComponentServiceObjects) {

		_taxonomyCategoryResourceComponentServiceObjects =
			taxonomyCategoryResourceComponentServiceObjects;
	}

	public static void setTaxonomyVocabularyResourceComponentServiceObjects(
		ComponentServiceObjects<TaxonomyVocabularyResource>
			taxonomyVocabularyResourceComponentServiceObjects) {

		_taxonomyVocabularyResourceComponentServiceObjects =
			taxonomyVocabularyResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {assetLibraryKeywords(assetLibraryId: ___, filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public KeywordPage assetLibraryKeywords(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> new KeywordPage(
				keywordResource.getAssetLibraryKeywordsPage(
					Long.valueOf(assetLibraryId), search,
					_filterBiFunction.apply(keywordResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(keywordResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {assetLibraryKeywordPermissions(assetLibraryId: ___, roleNames: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public KeywordPage assetLibraryKeywordPermissions(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> new KeywordPage(
				keywordResource.getAssetLibraryKeywordPermissionsPage(
					Long.valueOf(assetLibraryId), roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {keywordsRanked(page: ___, pageSize: ___, search: ___, siteKey: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public KeywordPage keywordsRanked(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> new KeywordPage(
				keywordResource.getKeywordsRankedPage(
					Long.valueOf(siteKey), search,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {keyword(keywordId: ___){actions, assetLibraryKey, creator, dateCreated, dateModified, id, keywordUsageCount, name, siteId, subscribed}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a keyword.")
	public Keyword keyword(@GraphQLName("keywordId") Long keywordId)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.getKeyword(keywordId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {keywords(filter: ___, page: ___, pageSize: ___, search: ___, siteKey: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a Site's keywords. Results can be paginated, filtered, searched, and sorted."
	)
	public KeywordPage keywords(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> new KeywordPage(
				keywordResource.getSiteKeywordsPage(
					Long.valueOf(siteKey), search,
					_filterBiFunction.apply(keywordResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(keywordResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {keywordPermissions(roleNames: ___, siteKey: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public KeywordPage keywordPermissions(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> new KeywordPage(
				keywordResource.getSiteKeywordPermissionsPage(
					Long.valueOf(siteKey), roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyCategoryRanked(page: ___, pageSize: ___, siteKey: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TaxonomyCategoryPage taxonomyCategoryRanked(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource -> new TaxonomyCategoryPage(
				taxonomyCategoryResource.getTaxonomyCategoryRankedPage(
					Long.valueOf(siteKey), Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyCategoryTaxonomyCategories(filter: ___, page: ___, pageSize: ___, parentTaxonomyCategoryId: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a taxonomy category's child taxonomy categories. Results can be paginated, filtered, searched, and sorted."
	)
	public TaxonomyCategoryPage taxonomyCategoryTaxonomyCategories(
			@GraphQLName("parentTaxonomyCategoryId") String
				parentTaxonomyCategoryId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource -> new TaxonomyCategoryPage(
				taxonomyCategoryResource.
					getTaxonomyCategoryTaxonomyCategoriesPage(
						parentTaxonomyCategoryId, search,
						_filterBiFunction.apply(
							taxonomyCategoryResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							taxonomyCategoryResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyCategory(taxonomyCategoryId: ___){actions, availableLanguages, creator, dateCreated, dateModified, description, description_i18n, externalReferenceCode, id, name, name_i18n, numberOfTaxonomyCategories, parentTaxonomyCategory, parentTaxonomyVocabulary, taxonomyCategoryProperties, taxonomyCategoryUsageCount, viewableBy}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a taxonomy category.")
	public TaxonomyCategory taxonomyCategory(
			@GraphQLName("taxonomyCategoryId") String taxonomyCategoryId)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource ->
				taxonomyCategoryResource.getTaxonomyCategory(
					taxonomyCategoryId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyCategoryPermissions(roleNames: ___, taxonomyCategoryId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TaxonomyCategoryPage taxonomyCategoryPermissions(
			@GraphQLName("taxonomyCategoryId") String taxonomyCategoryId,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource -> new TaxonomyCategoryPage(
				taxonomyCategoryResource.getTaxonomyCategoryPermissionsPage(
					taxonomyCategoryId, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyVocabularyTaxonomyCategories(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___, taxonomyVocabularyId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a vocabulary's taxonomy categories. Results can be paginated, filtered, searched, and sorted."
	)
	public TaxonomyCategoryPage taxonomyVocabularyTaxonomyCategories(
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource -> new TaxonomyCategoryPage(
				taxonomyCategoryResource.
					getTaxonomyVocabularyTaxonomyCategoriesPage(
						taxonomyVocabularyId, search,
						_filterBiFunction.apply(
							taxonomyCategoryResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							taxonomyCategoryResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {assetLibraryTaxonomyVocabularies(assetLibraryId: ___, filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TaxonomyVocabularyPage assetLibraryTaxonomyVocabularies(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource -> new TaxonomyVocabularyPage(
				taxonomyVocabularyResource.
					getAssetLibraryTaxonomyVocabulariesPage(
						Long.valueOf(assetLibraryId), search,
						_filterBiFunction.apply(
							taxonomyVocabularyResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							taxonomyVocabularyResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {assetLibraryTaxonomyVocabularyPermissions(assetLibraryId: ___, roleNames: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TaxonomyVocabularyPage assetLibraryTaxonomyVocabularyPermissions(
			@GraphQLName("assetLibraryId") @NotEmpty String assetLibraryId,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource -> new TaxonomyVocabularyPage(
				taxonomyVocabularyResource.
					getAssetLibraryTaxonomyVocabularyPermissionsPage(
						Long.valueOf(assetLibraryId), roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyVocabularies(filter: ___, page: ___, pageSize: ___, search: ___, siteKey: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a Site's taxonomy vocabularies. Results can be paginated, filtered, searched, and sorted."
	)
	public TaxonomyVocabularyPage taxonomyVocabularies(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource -> new TaxonomyVocabularyPage(
				taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
					Long.valueOf(siteKey), search,
					_filterBiFunction.apply(
						taxonomyVocabularyResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						taxonomyVocabularyResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {siteTaxonomyVocabularyPermissions(roleNames: ___, siteKey: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TaxonomyVocabularyPage siteTaxonomyVocabularyPermissions(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource -> new TaxonomyVocabularyPage(
				taxonomyVocabularyResource.
					getSiteTaxonomyVocabularyPermissionsPage(
						Long.valueOf(siteKey), roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyVocabulary(taxonomyVocabularyId: ___){actions, assetLibraryKey, assetTypes, availableLanguages, creator, dateCreated, dateModified, description, description_i18n, id, name, name_i18n, numberOfTaxonomyCategories, siteId, viewableBy}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a taxonomy vocabulary.")
	public TaxonomyVocabulary taxonomyVocabulary(
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource ->
				taxonomyVocabularyResource.getTaxonomyVocabulary(
					taxonomyVocabularyId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyVocabularyPermissions(roleNames: ___, taxonomyVocabularyId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TaxonomyVocabularyPage taxonomyVocabularyPermissions(
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource -> new TaxonomyVocabularyPage(
				taxonomyVocabularyResource.getTaxonomyVocabularyPermissionsPage(
					taxonomyVocabularyId, roleNames)));
	}

	@GraphQLTypeExtension(TaxonomyCategory.class)
	public class GetTaxonomyCategoryPermissionsPageTypeExtension {

		public GetTaxonomyCategoryPermissionsPageTypeExtension(
			TaxonomyCategory taxonomyCategory) {

			_taxonomyCategory = taxonomyCategory;
		}

		@GraphQLField
		public TaxonomyCategoryPage permissions(
				@GraphQLName("roleNames") String roleNames)
			throws Exception {

			return _applyComponentServiceObjects(
				_taxonomyCategoryResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				taxonomyCategoryResource -> new TaxonomyCategoryPage(
					taxonomyCategoryResource.getTaxonomyCategoryPermissionsPage(
						_taxonomyCategory.getId(), roleNames)));
		}

		private TaxonomyCategory _taxonomyCategory;

	}

	@GraphQLTypeExtension(TaxonomyVocabulary.class)
	public class GetTaxonomyVocabularyPermissionsPageTypeExtension {

		public GetTaxonomyVocabularyPermissionsPageTypeExtension(
			TaxonomyVocabulary taxonomyVocabulary) {

			_taxonomyVocabulary = taxonomyVocabulary;
		}

		@GraphQLField
		public TaxonomyVocabularyPage permissions(
				@GraphQLName("roleNames") String roleNames)
			throws Exception {

			return _applyComponentServiceObjects(
				_taxonomyVocabularyResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				taxonomyVocabularyResource -> new TaxonomyVocabularyPage(
					taxonomyVocabularyResource.
						getTaxonomyVocabularyPermissionsPage(
							_taxonomyVocabulary.getId(), roleNames)));
		}

		private TaxonomyVocabulary _taxonomyVocabulary;

	}

	@GraphQLTypeExtension(TaxonomyVocabulary.class)
	public class GetTaxonomyVocabularyTaxonomyCategoriesPageTypeExtension {

		public GetTaxonomyVocabularyTaxonomyCategoriesPageTypeExtension(
			TaxonomyVocabulary taxonomyVocabulary) {

			_taxonomyVocabulary = taxonomyVocabulary;
		}

		@GraphQLField(
			description = "Retrieves a vocabulary's taxonomy categories. Results can be paginated, filtered, searched, and sorted."
		)
		public TaxonomyCategoryPage taxonomyCategories(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_taxonomyCategoryResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				taxonomyCategoryResource -> new TaxonomyCategoryPage(
					taxonomyCategoryResource.
						getTaxonomyVocabularyTaxonomyCategoriesPage(
							_taxonomyVocabulary.getId(), search,
							_filterBiFunction.apply(
								taxonomyCategoryResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								taxonomyCategoryResource, sortsString))));
		}

		private TaxonomyVocabulary _taxonomyVocabulary;

	}

	@GraphQLTypeExtension(TaxonomyCategory.class)
	public class GetTaxonomyCategoryTaxonomyCategoriesPageTypeExtension {

		public GetTaxonomyCategoryTaxonomyCategoriesPageTypeExtension(
			TaxonomyCategory taxonomyCategory) {

			_taxonomyCategory = taxonomyCategory;
		}

		@GraphQLField(
			description = "Retrieves a taxonomy category's child taxonomy categories. Results can be paginated, filtered, searched, and sorted."
		)
		public TaxonomyCategoryPage taxonomyCategories(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_taxonomyCategoryResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				taxonomyCategoryResource -> new TaxonomyCategoryPage(
					taxonomyCategoryResource.
						getTaxonomyCategoryTaxonomyCategoriesPage(
							_taxonomyCategory.getId(), search,
							_filterBiFunction.apply(
								taxonomyCategoryResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								taxonomyCategoryResource, sortsString))));
		}

		private TaxonomyCategory _taxonomyCategory;

	}

	@GraphQLName("KeywordPage")
	public class KeywordPage {

		public KeywordPage(Page keywordPage) {
			actions = keywordPage.getActions();

			items = keywordPage.getItems();
			lastPage = keywordPage.getLastPage();
			page = keywordPage.getPage();
			pageSize = keywordPage.getPageSize();
			totalCount = keywordPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Keyword> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("TaxonomyCategoryPage")
	public class TaxonomyCategoryPage {

		public TaxonomyCategoryPage(Page taxonomyCategoryPage) {
			actions = taxonomyCategoryPage.getActions();

			items = taxonomyCategoryPage.getItems();
			lastPage = taxonomyCategoryPage.getLastPage();
			page = taxonomyCategoryPage.getPage();
			pageSize = taxonomyCategoryPage.getPageSize();
			totalCount = taxonomyCategoryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<TaxonomyCategory> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("TaxonomyVocabularyPage")
	public class TaxonomyVocabularyPage {

		public TaxonomyVocabularyPage(Page taxonomyVocabularyPage) {
			actions = taxonomyVocabularyPage.getActions();

			items = taxonomyVocabularyPage.getItems();
			lastPage = taxonomyVocabularyPage.getLastPage();
			page = taxonomyVocabularyPage.getPage();
			pageSize = taxonomyVocabularyPage.getPageSize();
			totalCount = taxonomyVocabularyPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<TaxonomyVocabulary> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(KeywordResource keywordResource)
		throws Exception {

		keywordResource.setContextAcceptLanguage(_acceptLanguage);
		keywordResource.setContextCompany(_company);
		keywordResource.setContextHttpServletRequest(_httpServletRequest);
		keywordResource.setContextHttpServletResponse(_httpServletResponse);
		keywordResource.setContextUriInfo(_uriInfo);
		keywordResource.setContextUser(_user);
		keywordResource.setGroupLocalService(_groupLocalService);
		keywordResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			TaxonomyCategoryResource taxonomyCategoryResource)
		throws Exception {

		taxonomyCategoryResource.setContextAcceptLanguage(_acceptLanguage);
		taxonomyCategoryResource.setContextCompany(_company);
		taxonomyCategoryResource.setContextHttpServletRequest(
			_httpServletRequest);
		taxonomyCategoryResource.setContextHttpServletResponse(
			_httpServletResponse);
		taxonomyCategoryResource.setContextUriInfo(_uriInfo);
		taxonomyCategoryResource.setContextUser(_user);
		taxonomyCategoryResource.setGroupLocalService(_groupLocalService);
		taxonomyCategoryResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			TaxonomyVocabularyResource taxonomyVocabularyResource)
		throws Exception {

		taxonomyVocabularyResource.setContextAcceptLanguage(_acceptLanguage);
		taxonomyVocabularyResource.setContextCompany(_company);
		taxonomyVocabularyResource.setContextHttpServletRequest(
			_httpServletRequest);
		taxonomyVocabularyResource.setContextHttpServletResponse(
			_httpServletResponse);
		taxonomyVocabularyResource.setContextUriInfo(_uriInfo);
		taxonomyVocabularyResource.setContextUser(_user);
		taxonomyVocabularyResource.setGroupLocalService(_groupLocalService);
		taxonomyVocabularyResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<KeywordResource>
		_keywordResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaxonomyCategoryResource>
		_taxonomyCategoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaxonomyVocabularyResource>
		_taxonomyVocabularyResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}
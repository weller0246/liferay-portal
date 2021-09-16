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

package com.liferay.commerce.product.internal.search;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class CPDisplayLayoutIndexer extends BaseIndexer<CPDisplayLayout> {

	public static final String CLASS_NAME = CPDisplayLayout.class.getName();

	public static final String FIELD_ENTRY_MODEL_CLASS_NAME =
		"entryModelClassName";

	public CPDisplayLayoutIndexer() {
		setDefaultSelectedFieldNames(
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.UID);
		setFilterSearch(false);
		setPermissionAware(false);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		Map<String, Serializable> attributes = searchContext.getAttributes();

		String entryModelClassName = (String)attributes.get(
			FIELD_ENTRY_MODEL_CLASS_NAME);

		if (Validator.isNotNull(entryModelClassName)) {
			contextBooleanFilter.addTerm(
				FIELD_ENTRY_MODEL_CLASS_NAME, entryModelClassName,
				BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
	}

	@Override
	protected void doDelete(CPDisplayLayout cpDisplayLayout) throws Exception {
		deleteDocument(
			cpDisplayLayout.getCompanyId(),
			cpDisplayLayout.getCPDisplayLayoutId());
	}

	@Override
	protected Document doGetDocument(CPDisplayLayout cpDisplayLayout)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing commerce product display layout " + cpDisplayLayout);
		}

		Document document = getBaseModelDocument(CLASS_NAME, cpDisplayLayout);

		String className = cpDisplayLayout.getClassName();

		document.addKeyword(FIELD_ENTRY_MODEL_CLASS_NAME, className);

		if (className.equals(CPDefinition.class.getName())) {
			CPDefinition cpDefinition =
				_cpDefinitionLocalService.getCPDefinition(
					cpDisplayLayout.getClassPK());

			CommerceCatalog commerceCatalog = cpDefinition.getCommerceCatalog();

			document.addKeyword(
				CPField.COMMERCE_CATALOG_GROUP_ID, cpDefinition.getGroupId());

			Locale locale = LocaleUtil.fromLanguageId(
				commerceCatalog.getCatalogDefaultLanguageId());

			addLocalizedField(
				document, Field.TITLE, locale, cpDefinition.getNameMap());

			document.addText(
				Field.NAME,
				cpDefinition.getName(
					commerceCatalog.getCatalogDefaultLanguageId()));
		}

		if (className.equals(AssetCategory.class.getName())) {
			AssetCategory assetCategory =
				_assetCategoryLocalService.getAssetCategory(
					cpDisplayLayout.getClassPK());

			Locale siteDefaultLocale = _portal.getSiteDefaultLocale(
				assetCategory.getGroupId());

			addLocalizedField(
				document, Field.DESCRIPTION, siteDefaultLocale,
				assetCategory.getDescriptionMap());

			document.addText(Field.NAME, assetCategory.getName());

			addLocalizedField(
				document, Field.TITLE, siteDefaultLocale,
				assetCategory.getTitleMap());
		}

		document.addKeyword(Field.GROUP_ID, cpDisplayLayout.getGroupId());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + cpDisplayLayout + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.ENTRY_CLASS_PK, Field.ENTRY_CLASS_PK);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CPDisplayLayout cpDisplayLayout) throws Exception {
		_indexWriterHelper.updateDocument(
			getSearchEngineId(), cpDisplayLayout.getCompanyId(),
			getDocument(cpDisplayLayout), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_cpDisplayLayoutLocalService.getCPDisplayLayout(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCPDisplayLayouts(companyId);
	}

	protected void reindexCPDisplayLayouts(long companyId)
		throws PortalException {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_cpDisplayLayoutLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CPDisplayLayout cpDisplayLayout) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(cpDisplayLayout));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index commerce product display layout " +
								cpDisplayLayout.getCPDisplayLayoutId(),
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDisplayLayoutIndexer.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private Portal _portal;

}
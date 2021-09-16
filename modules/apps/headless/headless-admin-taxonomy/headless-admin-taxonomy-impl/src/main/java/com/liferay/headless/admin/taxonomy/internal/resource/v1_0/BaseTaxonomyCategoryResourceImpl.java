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

package com.liferay.headless.admin.taxonomy.internal.resource.v1_0;

import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.permission.ModelPermissionsUtil;
import com.liferay.portal.vulcan.permission.PermissionUtil;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseTaxonomyCategoryResourceImpl
	implements EntityModelResource, TaxonomyCategoryResource,
			   VulcanBatchEngineTaskItemDelegate<TaxonomyCategory> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/ranked'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/taxonomy-categories/ranked")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Page<TaxonomyCategory> getTaxonomyCategoryRankedPage(
			@Parameter(hidden = true) @QueryParam("siteId") Long siteId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{parentTaxonomyCategoryId}/taxonomy-categories'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves a taxonomy category's child taxonomy categories. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentTaxonomyCategoryId"
			),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/taxonomy-categories/{parentTaxonomyCategoryId}/taxonomy-categories")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Page<TaxonomyCategory> getTaxonomyCategoryTaxonomyCategoriesPage(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentTaxonomyCategoryId")
			String parentTaxonomyCategoryId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{parentTaxonomyCategoryId}/taxonomy-categories' -d $'{"description": ___, "description_i18n": ___, "externalReferenceCode": ___, "name": ___, "name_i18n": ___, "taxonomyCategoryProperties": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Inserts a new child taxonomy category.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "parentTaxonomyCategoryId")
		}
	)
	@Path("/taxonomy-categories/{parentTaxonomyCategoryId}/taxonomy-categories")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory postTaxonomyCategoryTaxonomyCategory(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentTaxonomyCategoryId")
			String parentTaxonomyCategoryId,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		return new TaxonomyCategory();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the taxonomy category and returns a 204 if the operation succeeds."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "taxonomyCategoryId")}
	)
	@Path("/taxonomy-categories/{taxonomyCategoryId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public void deleteTaxonomyCategory(
			@NotNull @Parameter(hidden = true) @PathParam("taxonomyCategoryId")
				String taxonomyCategoryId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/taxonomy-categories/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Response deleteTaxonomyCategoryBatch(
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			Object object)
		throws Exception {

		vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineImportTaskResource.deleteImportTask(
				TaxonomyCategory.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves a taxonomy category.")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "taxonomyCategoryId")}
	)
	@Path("/taxonomy-categories/{taxonomyCategoryId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory getTaxonomyCategory(
			@NotNull @Parameter(hidden = true) @PathParam("taxonomyCategoryId")
				String taxonomyCategoryId)
		throws Exception {

		return new TaxonomyCategory();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}' -d $'{"description": ___, "description_i18n": ___, "externalReferenceCode": ___, "name": ___, "name_i18n": ___, "taxonomyCategoryProperties": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates only the fields received in the request body. Other fields are left untouched."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "taxonomyCategoryId")}
	)
	@PATCH
	@Path("/taxonomy-categories/{taxonomyCategoryId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory patchTaxonomyCategory(
			@NotNull @Parameter(hidden = true) @PathParam("taxonomyCategoryId")
				String taxonomyCategoryId,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		TaxonomyCategory existingTaxonomyCategory = getTaxonomyCategory(
			taxonomyCategoryId);

		if (taxonomyCategory.getActions() != null) {
			existingTaxonomyCategory.setActions(taxonomyCategory.getActions());
		}

		if (taxonomyCategory.getAvailableLanguages() != null) {
			existingTaxonomyCategory.setAvailableLanguages(
				taxonomyCategory.getAvailableLanguages());
		}

		if (taxonomyCategory.getDateCreated() != null) {
			existingTaxonomyCategory.setDateCreated(
				taxonomyCategory.getDateCreated());
		}

		if (taxonomyCategory.getDateModified() != null) {
			existingTaxonomyCategory.setDateModified(
				taxonomyCategory.getDateModified());
		}

		if (taxonomyCategory.getDescription() != null) {
			existingTaxonomyCategory.setDescription(
				taxonomyCategory.getDescription());
		}

		if (taxonomyCategory.getDescription_i18n() != null) {
			existingTaxonomyCategory.setDescription_i18n(
				taxonomyCategory.getDescription_i18n());
		}

		if (taxonomyCategory.getExternalReferenceCode() != null) {
			existingTaxonomyCategory.setExternalReferenceCode(
				taxonomyCategory.getExternalReferenceCode());
		}

		if (taxonomyCategory.getName() != null) {
			existingTaxonomyCategory.setName(taxonomyCategory.getName());
		}

		if (taxonomyCategory.getName_i18n() != null) {
			existingTaxonomyCategory.setName_i18n(
				taxonomyCategory.getName_i18n());
		}

		if (taxonomyCategory.getNumberOfTaxonomyCategories() != null) {
			existingTaxonomyCategory.setNumberOfTaxonomyCategories(
				taxonomyCategory.getNumberOfTaxonomyCategories());
		}

		if (taxonomyCategory.getTaxonomyCategoryUsageCount() != null) {
			existingTaxonomyCategory.setTaxonomyCategoryUsageCount(
				taxonomyCategory.getTaxonomyCategoryUsageCount());
		}

		if (taxonomyCategory.getViewableBy() != null) {
			existingTaxonomyCategory.setViewableBy(
				taxonomyCategory.getViewableBy());
		}

		preparePatch(taxonomyCategory, existingTaxonomyCategory);

		return putTaxonomyCategory(
			taxonomyCategoryId, existingTaxonomyCategory);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}' -d $'{"description": ___, "description_i18n": ___, "externalReferenceCode": ___, "name": ___, "name_i18n": ___, "taxonomyCategoryProperties": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the taxonomy category with the information sent in the request body. Any missing fields are deleted unless they are required."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "taxonomyCategoryId")}
	)
	@Path("/taxonomy-categories/{taxonomyCategoryId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory putTaxonomyCategory(
			@NotNull @Parameter(hidden = true) @PathParam("taxonomyCategoryId")
				String taxonomyCategoryId,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		return new TaxonomyCategory();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/taxonomy-categories/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Response putTaxonomyCategoryBatch(
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			Object object)
		throws Exception {

		vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineImportTaskResource.putImportTask(
				TaxonomyCategory.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "taxonomyCategoryId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/taxonomy-categories/{taxonomyCategoryId}/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getTaxonomyCategoryPermissionsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("taxonomyCategoryId")
				String taxonomyCategoryId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			taxonomyCategoryId);
		Long resourceId = getPermissionCheckerResourceId(taxonomyCategoryId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(taxonomyCategoryId));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getTaxonomyCategoryPermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putTaxonomyCategoryPermission",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "taxonomyCategoryId")}
	)
	@Path("/taxonomy-categories/{taxonomyCategoryId}/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putTaxonomyCategoryPermission(
				@NotNull @Parameter(hidden = true)
				@PathParam("taxonomyCategoryId")
				String taxonomyCategoryId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			taxonomyCategoryId);
		Long resourceId = getPermissionCheckerResourceId(taxonomyCategoryId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(taxonomyCategoryId));

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(),
			getPermissionCheckerGroupId(taxonomyCategoryId), resourceName,
			String.valueOf(resourceId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions, resourceId,
				resourceName, resourceActionLocalService,
				resourcePermissionLocalService, roleLocalService));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getTaxonomyCategoryPermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putTaxonomyCategoryPermission",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves a vocabulary's taxonomy categories. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "taxonomyVocabularyId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Page<TaxonomyCategory> getTaxonomyVocabularyTaxonomyCategoriesPage(
			@NotNull @Parameter(hidden = true)
			@PathParam("taxonomyVocabularyId")
			Long taxonomyVocabularyId,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories' -d $'{"description": ___, "description_i18n": ___, "externalReferenceCode": ___, "name": ___, "name_i18n": ___, "taxonomyCategoryProperties": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Inserts a new taxonomy category in a taxonomy vocabulary."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "taxonomyVocabularyId")
		}
	)
	@Path("/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory postTaxonomyVocabularyTaxonomyCategory(
			@NotNull @Parameter(hidden = true)
			@PathParam("taxonomyVocabularyId")
			Long taxonomyVocabularyId,
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		return new TaxonomyCategory();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "taxonomyVocabularyId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path(
		"/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories/batch"
	)
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Response postTaxonomyVocabularyTaxonomyCategoryBatch(
			@NotNull @Parameter(hidden = true)
			@PathParam("taxonomyVocabularyId")
			Long taxonomyVocabularyId,
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			Object object)
		throws Exception {

		vulcanBatchEngineImportTaskResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		vulcanBatchEngineImportTaskResource.setContextCompany(contextCompany);
		vulcanBatchEngineImportTaskResource.setContextHttpServletRequest(
			contextHttpServletRequest);
		vulcanBatchEngineImportTaskResource.setContextUriInfo(contextUriInfo);
		vulcanBatchEngineImportTaskResource.setContextUser(contextUser);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.entity(
			vulcanBatchEngineImportTaskResource.postImportTask(
				TaxonomyCategory.class.getName(), callbackURL, null, object)
		).build();
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<TaxonomyCategory> taxonomyCategories,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeConsumer<TaxonomyCategory, Exception>
			taxonomyCategoryUnsafeConsumer =
				taxonomyCategory -> postTaxonomyVocabularyTaxonomyCategory(
					Long.parseLong(
						(String)parameters.get("taxonomyVocabularyId")),
					taxonomyCategory);

		for (TaxonomyCategory taxonomyCategory : taxonomyCategories) {
			taxonomyCategoryUnsafeConsumer.accept(taxonomyCategory);
		}
	}

	@Override
	public void delete(
			java.util.Collection<TaxonomyCategory> taxonomyCategories,
			Map<String, Serializable> parameters)
		throws Exception {

		for (TaxonomyCategory taxonomyCategory : taxonomyCategories) {
			deleteTaxonomyCategory(taxonomyCategory.getId());
		}
	}

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return getEntityModel(
			new MultivaluedHashMap<String, Object>(multivaluedMap));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return null;
	}

	@Override
	public Page<TaxonomyCategory> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return null;
	}

	@Override
	public void setLanguageId(String languageId) {
		this.contextAcceptLanguage = new AcceptLanguage() {

			@Override
			public List<Locale> getLocales() {
				return null;
			}

			@Override
			public String getPreferredLanguageId() {
				return languageId;
			}

			@Override
			public Locale getPreferredLocale() {
				return LocaleUtil.fromLanguageId(languageId);
			}

		};
	}

	@Override
	public void update(
			java.util.Collection<TaxonomyCategory> taxonomyCategories,
			Map<String, Serializable> parameters)
		throws Exception {

		for (TaxonomyCategory taxonomyCategory : taxonomyCategories) {
			putTaxonomyCategory(
				taxonomyCategory.getId() != null ? taxonomyCategory.getId() :
					(String)parameters.get("taxonomyCategoryId"),
				taxonomyCategory);
		}
	}

	protected String getPermissionCheckerActionsResourceName(Object id)
		throws Exception {

		return getPermissionCheckerResourceName(id);
	}

	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String getPermissionCheckerPortletName(Object id)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long getPermissionCheckerResourceId(Object id) throws Exception {
		return GetterUtil.getLong(id);
	}

	protected String getPermissionCheckerResourceName(Object id)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<com.liferay.portal.vulcan.permission.Permission>
			toPermissionPage(
				Map<String, Map<String, String>> actions, long id,
				String resourceName, String roleNames)
		throws Exception {

		List<ResourceAction> resourceActions =
			resourceActionLocalService.getResourceActions(resourceName);

		if (Validator.isNotNull(roleNames)) {
			return Page.of(
				actions,
				transform(
					PermissionUtil.getRoles(
						contextCompany, roleLocalService,
						StringUtil.split(roleNames)),
					role -> PermissionUtil.toPermission(
						contextCompany.getCompanyId(), id, resourceActions,
						resourceName, resourcePermissionLocalService, role)));
		}

		return Page.of(
			actions,
			transform(
				PermissionUtil.getResourcePermissions(
					contextCompany.getCompanyId(), id, resourceName,
					resourcePermissionLocalService),
				resourcePermission -> PermissionUtil.toPermission(
					resourceActions, resourcePermission,
					roleLocalService.getRole(resourcePermission.getRoleId()))));
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany) {

		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser) {

		this.contextUser = contextUser;
	}

	public void setExpressionConvert(
		ExpressionConvert<Filter> expressionConvert) {

		this.expressionConvert = expressionConvert;
	}

	public void setFilterParserProvider(
		FilterParserProvider filterParserProvider) {

		this.filterParserProvider = filterParserProvider;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		this.resourceActionLocalService = resourceActionLocalService;
	}

	public void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		this.resourcePermissionLocalService = resourcePermissionLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	@Override
	public Filter toFilter(
		String filterString, Map<String, List<String>> multivaluedMap) {

		try {
			EntityModel entityModel = getEntityModel(multivaluedMap);

			FilterParser filterParser = filterParserProvider.provide(
				entityModel);

			com.liferay.portal.odata.filter.Filter oDataFilter =
				new com.liferay.portal.odata.filter.Filter(
					filterParser.parse(filterString));

			return expressionConvert.convert(
				oDataFilter.getExpression(),
				contextAcceptLanguage.getPreferredLocale(), entityModel);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid filter " + filterString, exception);
			}
		}

		return null;
	}

	protected Map<String, String> addAction(
		String actionName, GroupedModel groupedModel, String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, Long ownerId,
		String permissionName, Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			ownerId, permissionName, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName,
		ModelResourcePermission modelResourcePermission) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			modelResourcePermission, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, null, permissionName, siteId);
	}

	protected void preparePatch(
		TaxonomyCategory taxonomyCategory,
		TaxonomyCategory existingTaxonomyCategory) {
	}

	protected <T, R> List<R> transform(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected ExpressionConvert<Filter> expressionConvert;
	protected FilterParserProvider filterParserProvider;
	protected GroupLocalService groupLocalService;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;
	protected VulcanBatchEngineImportTaskResource
		vulcanBatchEngineImportTaskResource;

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseTaxonomyCategoryResourceImpl.class);

}
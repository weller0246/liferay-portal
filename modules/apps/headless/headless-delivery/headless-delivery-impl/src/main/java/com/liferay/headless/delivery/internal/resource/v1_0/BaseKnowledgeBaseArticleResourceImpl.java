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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource;
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
public abstract class BaseKnowledgeBaseArticleResourceImpl
	implements EntityModelResource, KnowledgeBaseArticleResource,
			   VulcanBatchEngineTaskItemDelegate<KnowledgeBaseArticle> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the knowledge base article and returns a 204 if the operation succeeds."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void deleteKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId")
			Long knowledgeBaseArticleId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/knowledge-base-articles/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Response deleteKnowledgeBaseArticleBatch(
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
				KnowledgeBaseArticle.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the knowledge base article.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId")
			Long knowledgeBaseArticleId)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}' -d $'{"articleBody": ___, "customFields": ___, "description": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "keywords": ___, "parentKnowledgeBaseArticleId": ___, "parentKnowledgeBaseFolderId": ___, "taxonomyCategoryIds": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@PATCH
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle patchKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId")
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		KnowledgeBaseArticle existingKnowledgeBaseArticle =
			getKnowledgeBaseArticle(knowledgeBaseArticleId);

		if (knowledgeBaseArticle.getActions() != null) {
			existingKnowledgeBaseArticle.setActions(
				knowledgeBaseArticle.getActions());
		}

		if (knowledgeBaseArticle.getArticleBody() != null) {
			existingKnowledgeBaseArticle.setArticleBody(
				knowledgeBaseArticle.getArticleBody());
		}

		if (knowledgeBaseArticle.getDateCreated() != null) {
			existingKnowledgeBaseArticle.setDateCreated(
				knowledgeBaseArticle.getDateCreated());
		}

		if (knowledgeBaseArticle.getDateModified() != null) {
			existingKnowledgeBaseArticle.setDateModified(
				knowledgeBaseArticle.getDateModified());
		}

		if (knowledgeBaseArticle.getDescription() != null) {
			existingKnowledgeBaseArticle.setDescription(
				knowledgeBaseArticle.getDescription());
		}

		if (knowledgeBaseArticle.getEncodingFormat() != null) {
			existingKnowledgeBaseArticle.setEncodingFormat(
				knowledgeBaseArticle.getEncodingFormat());
		}

		if (knowledgeBaseArticle.getExternalReferenceCode() != null) {
			existingKnowledgeBaseArticle.setExternalReferenceCode(
				knowledgeBaseArticle.getExternalReferenceCode());
		}

		if (knowledgeBaseArticle.getFriendlyUrlPath() != null) {
			existingKnowledgeBaseArticle.setFriendlyUrlPath(
				knowledgeBaseArticle.getFriendlyUrlPath());
		}

		if (knowledgeBaseArticle.getKeywords() != null) {
			existingKnowledgeBaseArticle.setKeywords(
				knowledgeBaseArticle.getKeywords());
		}

		if (knowledgeBaseArticle.getNumberOfAttachments() != null) {
			existingKnowledgeBaseArticle.setNumberOfAttachments(
				knowledgeBaseArticle.getNumberOfAttachments());
		}

		if (knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles() != null) {
			existingKnowledgeBaseArticle.setNumberOfKnowledgeBaseArticles(
				knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles());
		}

		if (knowledgeBaseArticle.getParentKnowledgeBaseArticleId() != null) {
			existingKnowledgeBaseArticle.setParentKnowledgeBaseArticleId(
				knowledgeBaseArticle.getParentKnowledgeBaseArticleId());
		}

		if (knowledgeBaseArticle.getParentKnowledgeBaseFolderId() != null) {
			existingKnowledgeBaseArticle.setParentKnowledgeBaseFolderId(
				knowledgeBaseArticle.getParentKnowledgeBaseFolderId());
		}

		if (knowledgeBaseArticle.getSiteId() != null) {
			existingKnowledgeBaseArticle.setSiteId(
				knowledgeBaseArticle.getSiteId());
		}

		if (knowledgeBaseArticle.getSubscribed() != null) {
			existingKnowledgeBaseArticle.setSubscribed(
				knowledgeBaseArticle.getSubscribed());
		}

		if (knowledgeBaseArticle.getTaxonomyCategoryIds() != null) {
			existingKnowledgeBaseArticle.setTaxonomyCategoryIds(
				knowledgeBaseArticle.getTaxonomyCategoryIds());
		}

		if (knowledgeBaseArticle.getTitle() != null) {
			existingKnowledgeBaseArticle.setTitle(
				knowledgeBaseArticle.getTitle());
		}

		if (knowledgeBaseArticle.getViewableBy() != null) {
			existingKnowledgeBaseArticle.setViewableBy(
				knowledgeBaseArticle.getViewableBy());
		}

		preparePatch(knowledgeBaseArticle, existingKnowledgeBaseArticle);

		return putKnowledgeBaseArticle(
			knowledgeBaseArticleId, existingKnowledgeBaseArticle);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}' -d $'{"articleBody": ___, "customFields": ___, "description": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "keywords": ___, "parentKnowledgeBaseArticleId": ___, "parentKnowledgeBaseFolderId": ___, "taxonomyCategoryIds": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the knowledge base article with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle putKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId")
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/knowledge-base-articles/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Response putKnowledgeBaseArticleBatch(
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
				KnowledgeBaseArticle.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the knowledge base article's rating and returns a 204 if the operation succeeds."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void deleteKnowledgeBaseArticleMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId")
			Long knowledgeBaseArticleId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the knowledge base article's rating.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Rating getKnowledgeBaseArticleMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId")
			Long knowledgeBaseArticleId)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a rating for the knowledge base article.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Rating postKnowledgeBaseArticleMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId")
			Long knowledgeBaseArticleId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Rating putKnowledgeBaseArticleMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId")
			Long knowledgeBaseArticleId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getKnowledgeBaseArticlePermissionsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("knowledgeBaseArticleId")
				Long knowledgeBaseArticleId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			knowledgeBaseArticleId);
		Long resourceId = getPermissionCheckerResourceId(
			knowledgeBaseArticleId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(knowledgeBaseArticleId));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getKnowledgeBaseArticlePermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putKnowledgeBaseArticlePermission",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putKnowledgeBaseArticlePermission(
				@NotNull @Parameter(hidden = true)
				@PathParam("knowledgeBaseArticleId")
				Long knowledgeBaseArticleId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			knowledgeBaseArticleId);
		Long resourceId = getPermissionCheckerResourceId(
			knowledgeBaseArticleId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(knowledgeBaseArticleId));

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(),
			getPermissionCheckerGroupId(knowledgeBaseArticleId), resourceName,
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
					"getKnowledgeBaseArticlePermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putKnowledgeBaseArticlePermission",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/subscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/subscribe")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void putKnowledgeBaseArticleSubscribe(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId")
			Long knowledgeBaseArticleId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{knowledgeBaseArticleId}/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseArticleId")
		}
	)
	@Path("/knowledge-base-articles/{knowledgeBaseArticleId}/unsubscribe")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void putKnowledgeBaseArticleUnsubscribe(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseArticleId")
			Long knowledgeBaseArticleId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the parent knowledge base article's child knowledge base articles. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentKnowledgeBaseArticleId"
			),
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("parentKnowledgeBaseArticleId")
				Long parentKnowledgeBaseArticleId,
				@Parameter(hidden = true) @QueryParam("flatten") Boolean
					flatten,
				@Parameter(hidden = true) @QueryParam("search") String search,
				@Context com.liferay.portal.vulcan.aggregation.Aggregation
					aggregation,
				@Context Filter filter, @Context Pagination pagination,
				@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles' -d $'{"articleBody": ___, "customFields": ___, "description": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "keywords": ___, "parentKnowledgeBaseArticleId": ___, "parentKnowledgeBaseFolderId": ___, "taxonomyCategoryIds": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Creates a child knowledge base article of the knowledge base article identified by `parentKnowledgeBaseArticleId`."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentKnowledgeBaseArticleId"
			)
		}
	)
	@Path(
		"/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle postKnowledgeBaseArticleKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentKnowledgeBaseArticleId")
			Long parentKnowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the folder's knowledge base articles. Results can be paginated, filtered, searched, flattened, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId"),
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("knowledgeBaseFolderId")
				Long knowledgeBaseFolderId,
				@Parameter(hidden = true) @QueryParam("flatten") Boolean
					flatten,
				@Parameter(hidden = true) @QueryParam("search") String search,
				@Context com.liferay.portal.vulcan.aggregation.Aggregation
					aggregation,
				@Context Filter filter, @Context Pagination pagination,
				@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles' -d $'{"articleBody": ___, "customFields": ___, "description": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "keywords": ___, "parentKnowledgeBaseArticleId": ___, "parentKnowledgeBaseFolderId": ___, "taxonomyCategoryIds": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Creates a new knowledge base article in the folder."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId")
		}
	)
	@Path(
		"/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle postKnowledgeBaseFolderKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseFolderId")
			Long knowledgeBaseFolderId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path(
		"/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles/batch"
	)
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Response postKnowledgeBaseFolderKnowledgeBaseArticleBatch(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseFolderId")
			Long knowledgeBaseFolderId,
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
				KnowledgeBaseArticle.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the site's knowledge base articles. Results can be paginated, filtered, searched, flattened, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/sites/{siteId}/knowledge-base-articles")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<KnowledgeBaseArticle> getSiteKnowledgeBaseArticlesPage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@Parameter(hidden = true) @QueryParam("flatten") Boolean flatten,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context com.liferay.portal.vulcan.aggregation.Aggregation
				aggregation,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles' -d $'{"articleBody": ___, "customFields": ___, "description": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "keywords": ___, "parentKnowledgeBaseArticleId": ___, "parentKnowledgeBaseFolderId": ___, "taxonomyCategoryIds": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new knowledge base article.")
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/knowledge-base-articles")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle postSiteKnowledgeBaseArticle(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/sites/{siteId}/knowledge-base-articles/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Response postSiteKnowledgeBaseArticleBatch(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
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
				KnowledgeBaseArticle.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the knowledge base article by external reference code."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void deleteSiteKnowledgeBaseArticleByExternalReferenceCode(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the site's knowledge base article by external reference code."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle
			getSiteKnowledgeBaseArticleByExternalReferenceCode(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				@NotNull @Parameter(hidden = true)
				@PathParam("externalReferenceCode")
				String externalReferenceCode)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{externalReferenceCode}' -d $'{"articleBody": ___, "customFields": ___, "description": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "keywords": ___, "parentKnowledgeBaseArticleId": ___, "parentKnowledgeBaseFolderId": ___, "taxonomyCategoryIds": ___, "title": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates the site's knowledge base article with the given external reference code, or creates it if it not exists."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/sites/{siteId}/knowledge-base-articles/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public KnowledgeBaseArticle
			putSiteKnowledgeBaseArticleByExternalReferenceCode(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				@NotNull @Parameter(hidden = true)
				@PathParam("externalReferenceCode")
				String externalReferenceCode,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return new KnowledgeBaseArticle();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/sites/{siteId}/knowledge-base-articles/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteKnowledgeBaseArticlePermissionsPage(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String portletName = getPermissionCheckerPortletName(siteId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, portletName, siteId,
			siteId);

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getSiteKnowledgeBaseArticlePermissionsPage", portletName,
					siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putSiteKnowledgeBaseArticlePermission", portletName,
					siteId)
			).build(),
			siteId, portletName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/knowledge-base-articles/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteKnowledgeBaseArticlePermission(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String portletName = getPermissionCheckerPortletName(siteId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, portletName, siteId,
			siteId);

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(), siteId, portletName,
			String.valueOf(siteId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions, siteId, portletName,
				resourceActionLocalService, resourcePermissionLocalService,
				roleLocalService));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getSiteKnowledgeBaseArticlePermissionsPage", portletName,
					siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putSiteKnowledgeBaseArticlePermission", portletName,
					siteId)
			).build(),
			siteId, portletName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/subscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/knowledge-base-articles/subscribe")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void putSiteKnowledgeBaseArticleSubscribe(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-articles/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/knowledge-base-articles/unsubscribe")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseArticle")})
	public void putSiteKnowledgeBaseArticleUnsubscribe(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId)
		throws Exception {
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<KnowledgeBaseArticle> knowledgeBaseArticles,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeConsumer<KnowledgeBaseArticle, Exception>
			knowledgeBaseArticleUnsafeConsumer =
				knowledgeBaseArticle ->
					postKnowledgeBaseFolderKnowledgeBaseArticle(
						Long.parseLong(
							(String)parameters.get("knowledgeBaseFolderId")),
						knowledgeBaseArticle);

		if (parameters.containsKey("siteId")) {
			knowledgeBaseArticleUnsafeConsumer =
				knowledgeBaseArticle -> postSiteKnowledgeBaseArticle(
					(Long)parameters.get("siteId"), knowledgeBaseArticle);
		}

		for (KnowledgeBaseArticle knowledgeBaseArticle :
				knowledgeBaseArticles) {

			knowledgeBaseArticleUnsafeConsumer.accept(knowledgeBaseArticle);
		}
	}

	@Override
	public void delete(
			java.util.Collection<KnowledgeBaseArticle> knowledgeBaseArticles,
			Map<String, Serializable> parameters)
		throws Exception {

		for (KnowledgeBaseArticle knowledgeBaseArticle :
				knowledgeBaseArticles) {

			deleteKnowledgeBaseArticle(knowledgeBaseArticle.getId());
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
	public Page<KnowledgeBaseArticle> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		if (parameters.containsKey("siteId")) {
			return getSiteKnowledgeBaseArticlesPage(
				(Long)parameters.get("siteId"),
				Boolean.parseBoolean((String)parameters.get("flatten")), search,
				null, filter, pagination, sorts);
		}
		else {
			return getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				Long.parseLong((String)parameters.get("knowledgeBaseFolderId")),
				Boolean.parseBoolean((String)parameters.get("flatten")), search,
				null, filter, pagination, sorts);
		}
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
			java.util.Collection<KnowledgeBaseArticle> knowledgeBaseArticles,
			Map<String, Serializable> parameters)
		throws Exception {

		for (KnowledgeBaseArticle knowledgeBaseArticle :
				knowledgeBaseArticles) {

			putKnowledgeBaseArticle(
				knowledgeBaseArticle.getId() != null ?
					knowledgeBaseArticle.getId() :
						Long.parseLong(
							(String)parameters.get("knowledgeBaseArticleId")),
				knowledgeBaseArticle);
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
		KnowledgeBaseArticle knowledgeBaseArticle,
		KnowledgeBaseArticle existingKnowledgeBaseArticle) {
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
		LogFactoryUtil.getLog(BaseKnowledgeBaseArticleResourceImpl.class);

}
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

import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.resource.v1_0.StructuredContentResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
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
public abstract class BaseStructuredContentResourceImpl
	implements EntityModelResource, StructuredContentResource,
			   VulcanBatchEngineTaskItemDelegate<StructuredContent> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "assetLibraryId"),
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/asset-libraries/{assetLibraryId}/structured-contents")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<StructuredContent> getAssetLibraryStructuredContentsPage(
			@NotNull @Parameter(hidden = true) @PathParam("assetLibraryId") Long
				assetLibraryId,
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents' -d $'{"contentFields": ___, "contentStructureId": ___, "customFields": ___, "datePublished": ___, "description": ___, "description_i18n": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "friendlyUrlPath_i18n": ___, "keywords": ___, "taxonomyCategoryIds": ___, "title": ___, "title_i18n": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "assetLibraryId")}
	)
	@Path("/asset-libraries/{assetLibraryId}/structured-contents")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent postAssetLibraryStructuredContent(
			@NotNull @Parameter(hidden = true) @PathParam("assetLibraryId") Long
				assetLibraryId,
			StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "assetLibraryId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/asset-libraries/{assetLibraryId}/structured-contents/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Response postAssetLibraryStructuredContentBatch(
			@NotNull @Parameter(hidden = true) @PathParam("assetLibraryId") Long
				assetLibraryId,
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
				StructuredContent.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "assetLibraryId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/asset-libraries/{assetLibraryId}/structured-contents/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getAssetLibraryStructuredContentPermissionsPage(
				@NotNull @Parameter(hidden = true) @PathParam("assetLibraryId")
					Long assetLibraryId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String portletName = getPermissionCheckerPortletName(assetLibraryId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, portletName,
			assetLibraryId, assetLibraryId);

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getAssetLibraryStructuredContentPermissionsPage",
					portletName, assetLibraryId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putAssetLibraryStructuredContentPermission", portletName,
					assetLibraryId)
			).build(),
			assetLibraryId, portletName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/asset-libraries/{assetLibraryId}/structured-contents/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "assetLibraryId")}
	)
	@Path("/asset-libraries/{assetLibraryId}/structured-contents/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putAssetLibraryStructuredContentPermission(
				@NotNull @Parameter(hidden = true) @PathParam("assetLibraryId")
					Long assetLibraryId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String portletName = getPermissionCheckerPortletName(assetLibraryId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, portletName,
			assetLibraryId, assetLibraryId);

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(), assetLibraryId, portletName,
			String.valueOf(assetLibraryId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions, assetLibraryId,
				portletName, resourceActionLocalService,
				resourcePermissionLocalService, roleLocalService));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getAssetLibraryStructuredContentPermissionsPage",
					portletName, assetLibraryId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putAssetLibraryStructuredContentPermission", portletName,
					assetLibraryId)
			).build(),
			assetLibraryId, portletName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/content-structures/{contentStructureId}/structured-contents'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves a list of the content structure's structured content. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "contentStructureId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/content-structures/{contentStructureId}/structured-contents")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<StructuredContent> getContentStructureStructuredContentsPage(
			@NotNull @Parameter(hidden = true) @PathParam("contentStructureId")
				Long contentStructureId,
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
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the site's structured content. Results can be paginated, filtered, searched, flattened, and sorted."
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
	@Path("/sites/{siteId}/structured-contents")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<StructuredContent> getSiteStructuredContentsPage(
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents' -d $'{"contentFields": ___, "contentStructureId": ___, "customFields": ___, "datePublished": ___, "description": ___, "description_i18n": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "friendlyUrlPath_i18n": ___, "keywords": ___, "taxonomyCategoryIds": ___, "title": ___, "title_i18n": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new structured content.")
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/structured-contents")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent postSiteStructuredContent(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/sites/{siteId}/structured-contents/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Response postSiteStructuredContentBatch(
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
				StructuredContent.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the site's structured content by external reference code."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/sites/{siteId}/structured-contents/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public void deleteSiteStructuredContentByExternalReferenceCode(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the site's structured content by external reference code."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/sites/{siteId}/structured-contents/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getSiteStructuredContentByExternalReferenceCode(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode)
		throws Exception {

		return new StructuredContent();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-external-reference-code/{externalReferenceCode}' -d $'{"contentFields": ___, "contentStructureId": ___, "customFields": ___, "datePublished": ___, "description": ___, "description_i18n": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "friendlyUrlPath_i18n": ___, "keywords": ___, "taxonomyCategoryIds": ___, "title": ___, "title_i18n": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates the site's structured content with the given external reference code, or creates it if it not exists."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/sites/{siteId}/structured-contents/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent putSiteStructuredContentByExternalReferenceCode(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-key/{key}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves a structured content by its key (`articleKey`)."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "key")
		}
	)
	@Path("/sites/{siteId}/structured-contents/by-key/{key}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getSiteStructuredContentByKey(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true) @PathParam("key") String key)
		throws Exception {

		return new StructuredContent();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-uuid/{uuid}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves a structured content by its UUID.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "uuid")
		}
	)
	@Path("/sites/{siteId}/structured-contents/by-uuid/{uuid}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getSiteStructuredContentByUuid(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true) @PathParam("uuid") String uuid)
		throws Exception {

		return new StructuredContent();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/sites/{siteId}/structured-contents/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteStructuredContentPermissionsPage(
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
					"getSiteStructuredContentPermissionsPage", portletName,
					siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putSiteStructuredContentPermission", portletName, siteId)
			).build(),
			siteId, portletName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/structured-contents/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteStructuredContentPermission(
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
					"getSiteStructuredContentPermissionsPage", portletName,
					siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putSiteStructuredContentPermission", portletName, siteId)
			).build(),
			siteId, portletName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the folder's structured content. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "structuredContentFolderId"
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
		"/structured-content-folders/{structuredContentFolderId}/structured-contents"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<StructuredContent>
			getStructuredContentFolderStructuredContentsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("structuredContentFolderId")
				Long structuredContentFolderId,
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents' -d $'{"contentFields": ___, "contentStructureId": ___, "customFields": ___, "datePublished": ___, "description": ___, "description_i18n": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "friendlyUrlPath_i18n": ___, "keywords": ___, "taxonomyCategoryIds": ___, "title": ___, "title_i18n": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new structured content in the folder.")
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "structuredContentFolderId"
			)
		}
	)
	@Path(
		"/structured-content-folders/{structuredContentFolderId}/structured-contents"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent postStructuredContentFolderStructuredContent(
			@NotNull @Parameter(hidden = true)
			@PathParam("structuredContentFolderId")
			Long structuredContentFolderId,
			StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "structuredContentFolderId"
			),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path(
		"/structured-content-folders/{structuredContentFolderId}/structured-contents/batch"
	)
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Response postStructuredContentFolderStructuredContentBatch(
			@NotNull @Parameter(hidden = true)
			@PathParam("structuredContentFolderId")
			Long structuredContentFolderId,
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
				StructuredContent.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the structured content and returns a 204 if the operation succeeds."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public void deleteStructuredContent(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/structured-contents/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Response deleteStructuredContentBatch(
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
				StructuredContent.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the structured content via its ID.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent getStructuredContent(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId)
		throws Exception {

		return new StructuredContent();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}' -d $'{"contentFields": ___, "contentStructureId": ___, "customFields": ___, "datePublished": ___, "description": ___, "description_i18n": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "friendlyUrlPath_i18n": ___, "keywords": ___, "taxonomyCategoryIds": ___, "title": ___, "title_i18n": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@PATCH
	@Path("/structured-contents/{structuredContentId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent patchStructuredContent(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId,
			StructuredContent structuredContent)
		throws Exception {

		StructuredContent existingStructuredContent = getStructuredContent(
			structuredContentId);

		if (structuredContent.getActions() != null) {
			existingStructuredContent.setActions(
				structuredContent.getActions());
		}

		if (structuredContent.getAssetLibraryKey() != null) {
			existingStructuredContent.setAssetLibraryKey(
				structuredContent.getAssetLibraryKey());
		}

		if (structuredContent.getAvailableLanguages() != null) {
			existingStructuredContent.setAvailableLanguages(
				structuredContent.getAvailableLanguages());
		}

		if (structuredContent.getContentStructureId() != null) {
			existingStructuredContent.setContentStructureId(
				structuredContent.getContentStructureId());
		}

		if (structuredContent.getDateCreated() != null) {
			existingStructuredContent.setDateCreated(
				structuredContent.getDateCreated());
		}

		if (structuredContent.getDateModified() != null) {
			existingStructuredContent.setDateModified(
				structuredContent.getDateModified());
		}

		if (structuredContent.getDatePublished() != null) {
			existingStructuredContent.setDatePublished(
				structuredContent.getDatePublished());
		}

		if (structuredContent.getDescription() != null) {
			existingStructuredContent.setDescription(
				structuredContent.getDescription());
		}

		if (structuredContent.getDescription_i18n() != null) {
			existingStructuredContent.setDescription_i18n(
				structuredContent.getDescription_i18n());
		}

		if (structuredContent.getExternalReferenceCode() != null) {
			existingStructuredContent.setExternalReferenceCode(
				structuredContent.getExternalReferenceCode());
		}

		if (structuredContent.getFriendlyUrlPath() != null) {
			existingStructuredContent.setFriendlyUrlPath(
				structuredContent.getFriendlyUrlPath());
		}

		if (structuredContent.getFriendlyUrlPath_i18n() != null) {
			existingStructuredContent.setFriendlyUrlPath_i18n(
				structuredContent.getFriendlyUrlPath_i18n());
		}

		if (structuredContent.getKey() != null) {
			existingStructuredContent.setKey(structuredContent.getKey());
		}

		if (structuredContent.getKeywords() != null) {
			existingStructuredContent.setKeywords(
				structuredContent.getKeywords());
		}

		if (structuredContent.getNumberOfComments() != null) {
			existingStructuredContent.setNumberOfComments(
				structuredContent.getNumberOfComments());
		}

		if (structuredContent.getSiteId() != null) {
			existingStructuredContent.setSiteId(structuredContent.getSiteId());
		}

		if (structuredContent.getSubscribed() != null) {
			existingStructuredContent.setSubscribed(
				structuredContent.getSubscribed());
		}

		if (structuredContent.getTaxonomyCategoryIds() != null) {
			existingStructuredContent.setTaxonomyCategoryIds(
				structuredContent.getTaxonomyCategoryIds());
		}

		if (structuredContent.getTitle() != null) {
			existingStructuredContent.setTitle(structuredContent.getTitle());
		}

		if (structuredContent.getTitle_i18n() != null) {
			existingStructuredContent.setTitle_i18n(
				structuredContent.getTitle_i18n());
		}

		if (structuredContent.getUuid() != null) {
			existingStructuredContent.setUuid(structuredContent.getUuid());
		}

		if (structuredContent.getViewableBy() != null) {
			existingStructuredContent.setViewableBy(
				structuredContent.getViewableBy());
		}

		preparePatch(structuredContent, existingStructuredContent);

		return putStructuredContent(
			structuredContentId, existingStructuredContent);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}' -d $'{"contentFields": ___, "contentStructureId": ___, "customFields": ___, "datePublished": ___, "description": ___, "description_i18n": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "friendlyUrlPath_i18n": ___, "keywords": ___, "taxonomyCategoryIds": ___, "title": ___, "title_i18n": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the structured content with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "StructuredContent")})
	public StructuredContent putStructuredContent(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId,
			StructuredContent structuredContent)
		throws Exception {

		return new StructuredContent();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/structured-contents/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Response putStructuredContentBatch(
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
				StructuredContent.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the structured content's rating and returns a 204 if the operation succeeds."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public void deleteStructuredContentMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the structured content's rating.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Rating getStructuredContentMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Create a rating for the structured content.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}/my-rating")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Rating postStructuredContentMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Rating putStructuredContentMyRating(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/structured-contents/{structuredContentId}/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getStructuredContentPermissionsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("structuredContentId")
				Long structuredContentId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			structuredContentId);
		Long resourceId = getPermissionCheckerResourceId(structuredContentId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(structuredContentId));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getStructuredContentPermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putStructuredContentPermission",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "StructuredContent")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putStructuredContentPermission(
				@NotNull @Parameter(hidden = true)
				@PathParam("structuredContentId")
				Long structuredContentId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			structuredContentId);
		Long resourceId = getPermissionCheckerResourceId(structuredContentId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(structuredContentId));

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(),
			getPermissionCheckerGroupId(structuredContentId), resourceName,
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
					"getStructuredContentPermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putStructuredContentPermission",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/rendered-content-by-display-page/{displayPageKey}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the structured content's rendered display page"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId"),
			@Parameter(in = ParameterIn.PATH, name = "displayPageKey")
		}
	)
	@Path(
		"/structured-contents/{structuredContentId}/rendered-content-by-display-page/{displayPageKey}"
	)
	@Produces("text/html")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public String
			getStructuredContentRenderedContentByDisplayPageDisplayPageKey(
				@NotNull @Parameter(hidden = true)
				@PathParam("structuredContentId")
				Long structuredContentId,
				@NotNull @Parameter(hidden = true) @PathParam("displayPageKey")
					String displayPageKey)
		throws Exception {

		return StringPool.BLANK;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/rendered-content/{contentTemplateId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the structured content's rendered template (the result of applying the structure's values to a template)."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId"),
			@Parameter(in = ParameterIn.PATH, name = "contentTemplateId")
		}
	)
	@Path(
		"/structured-contents/{structuredContentId}/rendered-content/{contentTemplateId}"
	)
	@Produces("text/html")
	@Tags(value = {@Tag(name = "StructuredContent")})
	public String getStructuredContentRenderedContentTemplate(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId,
			@NotNull @Parameter(hidden = true) @PathParam("contentTemplateId")
				String contentTemplateId)
		throws Exception {

		return StringPool.BLANK;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/subscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}/subscribe")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "StructuredContent")})
	public void putStructuredContentSubscribe(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "structuredContentId")
		}
	)
	@Path("/structured-contents/{structuredContentId}/unsubscribe")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "StructuredContent")})
	public void putStructuredContentUnsubscribe(
			@NotNull @Parameter(hidden = true) @PathParam("structuredContentId")
				Long structuredContentId)
		throws Exception {
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<StructuredContent> structuredContents,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeConsumer<StructuredContent, Exception>
			structuredContentUnsafeConsumer =
				structuredContent ->
					postStructuredContentFolderStructuredContent(
						Long.parseLong(
							(String)parameters.get(
								"structuredContentFolderId")),
						structuredContent);

		if (parameters.containsKey("assetLibraryId")) {
			structuredContentUnsafeConsumer =
				structuredContent -> postAssetLibraryStructuredContent(
					(Long)parameters.get("assetLibraryId"), structuredContent);
		}
		else if (parameters.containsKey("siteId")) {
			structuredContentUnsafeConsumer =
				structuredContent -> postSiteStructuredContent(
					(Long)parameters.get("siteId"), structuredContent);
		}

		for (StructuredContent structuredContent : structuredContents) {
			structuredContentUnsafeConsumer.accept(structuredContent);
		}
	}

	@Override
	public void delete(
			java.util.Collection<StructuredContent> structuredContents,
			Map<String, Serializable> parameters)
		throws Exception {

		for (StructuredContent structuredContent : structuredContents) {
			deleteStructuredContent(structuredContent.getId());
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
	public Page<StructuredContent> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		if (parameters.containsKey("assetLibraryId")) {
			return getAssetLibraryStructuredContentsPage(
				(Long)parameters.get("assetLibraryId"),
				Boolean.parseBoolean((String)parameters.get("flatten")), search,
				null, filter, pagination, sorts);
		}
		else if (parameters.containsKey("siteId")) {
			return getSiteStructuredContentsPage(
				(Long)parameters.get("siteId"),
				Boolean.parseBoolean((String)parameters.get("flatten")), search,
				null, filter, pagination, sorts);
		}
		else {
			return getContentStructureStructuredContentsPage(
				Long.parseLong((String)parameters.get("contentStructureId")),
				search, null, filter, pagination, sorts);
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
			java.util.Collection<StructuredContent> structuredContents,
			Map<String, Serializable> parameters)
		throws Exception {

		for (StructuredContent structuredContent : structuredContents) {
			putStructuredContent(
				structuredContent.getId() != null ? structuredContent.getId() :
					Long.parseLong(
						(String)parameters.get("structuredContentId")),
				structuredContent);
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
		StructuredContent structuredContent,
		StructuredContent existingStructuredContent) {
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
		LogFactoryUtil.getLog(BaseStructuredContentResourceImpl.class);

}
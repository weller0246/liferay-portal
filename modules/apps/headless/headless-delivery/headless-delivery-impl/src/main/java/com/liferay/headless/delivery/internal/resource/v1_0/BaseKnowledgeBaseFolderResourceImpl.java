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

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
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
public abstract class BaseKnowledgeBaseFolderResourceImpl
	implements EntityModelResource, KnowledgeBaseFolderResource,
			   VulcanBatchEngineTaskItemDelegate<KnowledgeBaseFolder> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the knowledge base folder and returns a 204 if the operation succeeds."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId")
		}
	)
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public void deleteKnowledgeBaseFolder(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseFolderId")
			Long knowledgeBaseFolderId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/knowledge-base-folders/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Response deleteKnowledgeBaseFolderBatch(
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
				KnowledgeBaseFolder.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the knowledge base folder.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId")
		}
	)
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseFolderId")
			Long knowledgeBaseFolderId)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}' -d $'{"customFields": ___, "description": ___, "externalReferenceCode": ___, "name": ___, "parentKnowledgeBaseFolderId": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId")
		}
	)
	@PATCH
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder patchKnowledgeBaseFolder(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseFolderId")
			Long knowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		KnowledgeBaseFolder existingKnowledgeBaseFolder =
			getKnowledgeBaseFolder(knowledgeBaseFolderId);

		if (knowledgeBaseFolder.getActions() != null) {
			existingKnowledgeBaseFolder.setActions(
				knowledgeBaseFolder.getActions());
		}

		if (knowledgeBaseFolder.getDateCreated() != null) {
			existingKnowledgeBaseFolder.setDateCreated(
				knowledgeBaseFolder.getDateCreated());
		}

		if (knowledgeBaseFolder.getDateModified() != null) {
			existingKnowledgeBaseFolder.setDateModified(
				knowledgeBaseFolder.getDateModified());
		}

		if (knowledgeBaseFolder.getDescription() != null) {
			existingKnowledgeBaseFolder.setDescription(
				knowledgeBaseFolder.getDescription());
		}

		if (knowledgeBaseFolder.getExternalReferenceCode() != null) {
			existingKnowledgeBaseFolder.setExternalReferenceCode(
				knowledgeBaseFolder.getExternalReferenceCode());
		}

		if (knowledgeBaseFolder.getName() != null) {
			existingKnowledgeBaseFolder.setName(knowledgeBaseFolder.getName());
		}

		if (knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles() != null) {
			existingKnowledgeBaseFolder.setNumberOfKnowledgeBaseArticles(
				knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles());
		}

		if (knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders() != null) {
			existingKnowledgeBaseFolder.setNumberOfKnowledgeBaseFolders(
				knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders());
		}

		if (knowledgeBaseFolder.getParentKnowledgeBaseFolderId() != null) {
			existingKnowledgeBaseFolder.setParentKnowledgeBaseFolderId(
				knowledgeBaseFolder.getParentKnowledgeBaseFolderId());
		}

		if (knowledgeBaseFolder.getSiteId() != null) {
			existingKnowledgeBaseFolder.setSiteId(
				knowledgeBaseFolder.getSiteId());
		}

		if (knowledgeBaseFolder.getViewableBy() != null) {
			existingKnowledgeBaseFolder.setViewableBy(
				knowledgeBaseFolder.getViewableBy());
		}

		preparePatch(knowledgeBaseFolder, existingKnowledgeBaseFolder);

		return putKnowledgeBaseFolder(
			knowledgeBaseFolderId, existingKnowledgeBaseFolder);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}' -d $'{"customFields": ___, "description": ___, "externalReferenceCode": ___, "name": ___, "parentKnowledgeBaseFolderId": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the knowledge base folder with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId")
		}
	)
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder putKnowledgeBaseFolder(
			@NotNull @Parameter(hidden = true)
			@PathParam("knowledgeBaseFolderId")
			Long knowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/knowledge-base-folders/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Response putKnowledgeBaseFolderBatch(
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
				KnowledgeBaseFolder.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getKnowledgeBaseFolderPermissionsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("knowledgeBaseFolderId")
				Long knowledgeBaseFolderId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			knowledgeBaseFolderId);
		Long resourceId = getPermissionCheckerResourceId(knowledgeBaseFolderId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(knowledgeBaseFolderId));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getKnowledgeBaseFolderPermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putKnowledgeBaseFolderPermission",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "knowledgeBaseFolderId")
		}
	)
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putKnowledgeBaseFolderPermission(
				@NotNull @Parameter(hidden = true)
				@PathParam("knowledgeBaseFolderId")
				Long knowledgeBaseFolderId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			knowledgeBaseFolderId);
		Long resourceId = getPermissionCheckerResourceId(knowledgeBaseFolderId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(knowledgeBaseFolderId));

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(),
			getPermissionCheckerGroupId(knowledgeBaseFolderId), resourceName,
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
					"getKnowledgeBaseFolderPermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putKnowledgeBaseFolderPermission",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the knowledge base folder's subfolders."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentKnowledgeBaseFolderId"
			),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path(
		"/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Page<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("parentKnowledgeBaseFolderId")
				Long parentKnowledgeBaseFolderId,
				@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders' -d $'{"customFields": ___, "description": ___, "externalReferenceCode": ___, "name": ___, "parentKnowledgeBaseFolderId": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Creates a knowledge base folder inside the parent folder."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentKnowledgeBaseFolderId"
			)
		}
	)
	@Path(
		"/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentKnowledgeBaseFolderId")
			Long parentKnowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the site's knowledge base folders. Results can be paginated."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/sites/{siteId}/knowledge-base-folders")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Page<KnowledgeBaseFolder> getSiteKnowledgeBaseFoldersPage(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders' -d $'{"customFields": ___, "description": ___, "externalReferenceCode": ___, "name": ___, "parentKnowledgeBaseFolderId": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new knowledge base folder.")
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/knowledge-base-folders")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder postSiteKnowledgeBaseFolder(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/sites/{siteId}/knowledge-base-folders/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Response postSiteKnowledgeBaseFolderBatch(
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
				KnowledgeBaseFolder.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the knowledge base folder by external reference code."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/sites/{siteId}/knowledge-base-folders/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public void deleteSiteKnowledgeBaseFolderByExternalReferenceCode(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the site's knowledge base folder by external reference code."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/sites/{siteId}/knowledge-base-folders/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder
			getSiteKnowledgeBaseFolderByExternalReferenceCode(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				@NotNull @Parameter(hidden = true)
				@PathParam("externalReferenceCode")
				String externalReferenceCode)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/by-external-reference-code/{externalReferenceCode}' -d $'{"customFields": ___, "description": ___, "externalReferenceCode": ___, "name": ___, "parentKnowledgeBaseFolderId": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates the site's knowledge base folder with the given external reference code, or creates it if it not exists."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/sites/{siteId}/knowledge-base-folders/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder
			putSiteKnowledgeBaseFolderByExternalReferenceCode(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				@NotNull @Parameter(hidden = true)
				@PathParam("externalReferenceCode")
				String externalReferenceCode,
				KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/sites/{siteId}/knowledge-base-folders/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteKnowledgeBaseFolderPermissionsPage(
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
					"getSiteKnowledgeBaseFolderPermissionsPage", portletName,
					siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putSiteKnowledgeBaseFolderPermission", portletName, siteId)
			).build(),
			siteId, portletName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/knowledge-base-folders/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteKnowledgeBaseFolderPermission(
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
					"getSiteKnowledgeBaseFolderPermissionsPage", portletName,
					siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putSiteKnowledgeBaseFolderPermission", portletName, siteId)
			).build(),
			siteId, portletName, null);
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<KnowledgeBaseFolder> knowledgeBaseFolders,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeConsumer<KnowledgeBaseFolder, Exception>
			knowledgeBaseFolderUnsafeConsumer = knowledgeBaseFolder -> {
			};

		if (parameters.containsKey("siteId")) {
			knowledgeBaseFolderUnsafeConsumer =
				knowledgeBaseFolder -> postSiteKnowledgeBaseFolder(
					(Long)parameters.get("siteId"), knowledgeBaseFolder);
		}

		for (KnowledgeBaseFolder knowledgeBaseFolder : knowledgeBaseFolders) {
			knowledgeBaseFolderUnsafeConsumer.accept(knowledgeBaseFolder);
		}
	}

	@Override
	public void delete(
			java.util.Collection<KnowledgeBaseFolder> knowledgeBaseFolders,
			Map<String, Serializable> parameters)
		throws Exception {

		for (KnowledgeBaseFolder knowledgeBaseFolder : knowledgeBaseFolders) {
			deleteKnowledgeBaseFolder(knowledgeBaseFolder.getId());
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
	public Page<KnowledgeBaseFolder> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		if (parameters.containsKey("siteId")) {
			return getSiteKnowledgeBaseFoldersPage(
				(Long)parameters.get("siteId"), pagination);
		}
		else {
			return null;
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
			java.util.Collection<KnowledgeBaseFolder> knowledgeBaseFolders,
			Map<String, Serializable> parameters)
		throws Exception {

		for (KnowledgeBaseFolder knowledgeBaseFolder : knowledgeBaseFolders) {
			putKnowledgeBaseFolder(
				knowledgeBaseFolder.getId() != null ?
					knowledgeBaseFolder.getId() :
						Long.parseLong(
							(String)parameters.get("knowledgeBaseFolderId")),
				knowledgeBaseFolder);
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
		KnowledgeBaseFolder knowledgeBaseFolder,
		KnowledgeBaseFolder existingKnowledgeBaseFolder) {
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
		LogFactoryUtil.getLog(BaseKnowledgeBaseFolderResourceImpl.class);

}
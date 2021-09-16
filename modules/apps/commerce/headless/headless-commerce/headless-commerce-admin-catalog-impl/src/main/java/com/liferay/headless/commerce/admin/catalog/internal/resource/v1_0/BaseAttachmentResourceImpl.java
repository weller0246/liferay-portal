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

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Attachment;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.AttachmentBase64;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.AttachmentUrl;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.AttachmentResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseAttachmentResourceImpl
	implements AttachmentResource, EntityModelResource,
			   VulcanBatchEngineTaskItemDelegate<Attachment> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/attachments'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path(
		"/products/by-externalReferenceCode/{externalReferenceCode}/attachments"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Page<Attachment> getProductByExternalReferenceCodeAttachmentsPage(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/attachments' -d $'{"attachment": ___, "cdnEnabled": ___, "cdnURL": ___, "customFields": ___, "displayDate": ___, "expirationDate": ___, "externalReferenceCode": ___, "id": ___, "neverExpire": ___, "options": ___, "priority": ___, "src": ___, "title": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/products/by-externalReferenceCode/{externalReferenceCode}/attachments"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Attachment postProductByExternalReferenceCodeAttachment(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			Attachment attachment)
		throws Exception {

		return new Attachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/attachments/by-base64' -d $'{"attachment": ___, "customFields": ___, "displayDate": ___, "expirationDate": ___, "externalReferenceCode": ___, "neverExpire": ___, "options": ___, "priority": ___, "src": ___, "title": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/products/by-externalReferenceCode/{externalReferenceCode}/attachments/by-base64"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Attachment postProductByExternalReferenceCodeAttachmentByBase64(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			AttachmentBase64 attachmentBase64)
		throws Exception {

		return new Attachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/attachments/by-url' -d $'{"customFields": ___, "displayDate": ___, "expirationDate": ___, "externalReferenceCode": ___, "neverExpire": ___, "options": ___, "priority": ___, "src": ___, "title": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/products/by-externalReferenceCode/{externalReferenceCode}/attachments/by-url"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Attachment postProductByExternalReferenceCodeAttachmentByUrl(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			AttachmentUrl attachmentUrl)
		throws Exception {

		return new Attachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/images'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/products/by-externalReferenceCode/{externalReferenceCode}/images")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Page<Attachment> getProductByExternalReferenceCodeImagesPage(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/images' -d $'{"attachment": ___, "cdnEnabled": ___, "cdnURL": ___, "customFields": ___, "displayDate": ___, "expirationDate": ___, "externalReferenceCode": ___, "id": ___, "neverExpire": ___, "options": ___, "priority": ___, "src": ___, "title": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path("/products/by-externalReferenceCode/{externalReferenceCode}/images")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Attachment postProductByExternalReferenceCodeImage(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			Attachment attachment)
		throws Exception {

		return new Attachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/images/by-base64' -d $'{"attachment": ___, "customFields": ___, "displayDate": ___, "expirationDate": ___, "externalReferenceCode": ___, "neverExpire": ___, "options": ___, "priority": ___, "src": ___, "title": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/products/by-externalReferenceCode/{externalReferenceCode}/images/by-base64"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Attachment postProductByExternalReferenceCodeImageByBase64(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			AttachmentBase64 attachmentBase64)
		throws Exception {

		return new Attachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/images/by-url' -d $'{"customFields": ___, "displayDate": ___, "expirationDate": ___, "externalReferenceCode": ___, "neverExpire": ___, "options": ___, "priority": ___, "src": ___, "title": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/products/by-externalReferenceCode/{externalReferenceCode}/images/by-url"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Attachment postProductByExternalReferenceCodeImageByUrl(
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode,
			AttachmentUrl attachmentUrl)
		throws Exception {

		return new Attachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/{id}/attachments'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "id"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/products/{id}/attachments")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Page<Attachment> getProductIdAttachmentsPage(
			@NotNull @Parameter(hidden = true) @PathParam("id") Long id,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/{id}/attachments' -d $'{"attachment": ___, "cdnEnabled": ___, "cdnURL": ___, "customFields": ___, "displayDate": ___, "expirationDate": ___, "externalReferenceCode": ___, "id": ___, "neverExpire": ___, "options": ___, "priority": ___, "src": ___, "title": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "id")})
	@Path("/products/{id}/attachments")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Attachment postProductIdAttachment(
			@NotNull @Parameter(hidden = true) @PathParam("id") Long id,
			Attachment attachment)
		throws Exception {

		return new Attachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/{id}/attachments/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "id"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/products/{id}/attachments/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Attachment")})
	public Response postProductIdAttachmentBatch(
			@NotNull @Parameter(hidden = true) @PathParam("id") Long id,
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
				Attachment.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/{id}/attachments/by-base64' -d $'{"attachment": ___, "customFields": ___, "displayDate": ___, "expirationDate": ___, "externalReferenceCode": ___, "neverExpire": ___, "options": ___, "priority": ___, "src": ___, "title": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "id")})
	@Path("/products/{id}/attachments/by-base64")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Attachment postProductIdAttachmentByBase64(
			@NotNull @Parameter(hidden = true) @PathParam("id") Long id,
			AttachmentBase64 attachmentBase64)
		throws Exception {

		return new Attachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/{id}/attachments/by-url' -d $'{"customFields": ___, "displayDate": ___, "expirationDate": ___, "externalReferenceCode": ___, "neverExpire": ___, "options": ___, "priority": ___, "src": ___, "title": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "id")})
	@Path("/products/{id}/attachments/by-url")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Attachment postProductIdAttachmentByUrl(
			@NotNull @Parameter(hidden = true) @PathParam("id") Long id,
			AttachmentUrl attachmentUrl)
		throws Exception {

		return new Attachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/{id}/images'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "id"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/products/{id}/images")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Page<Attachment> getProductIdImagesPage(
			@NotNull @Parameter(hidden = true) @PathParam("id") Long id,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/{id}/images' -d $'{"attachment": ___, "cdnEnabled": ___, "cdnURL": ___, "customFields": ___, "displayDate": ___, "expirationDate": ___, "externalReferenceCode": ___, "id": ___, "neverExpire": ___, "options": ___, "priority": ___, "src": ___, "title": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "id")})
	@Path("/products/{id}/images")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Attachment postProductIdImage(
			@NotNull @Parameter(hidden = true) @PathParam("id") Long id,
			Attachment attachment)
		throws Exception {

		return new Attachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/{id}/images/by-base64' -d $'{"attachment": ___, "customFields": ___, "displayDate": ___, "expirationDate": ___, "externalReferenceCode": ___, "neverExpire": ___, "options": ___, "priority": ___, "src": ___, "title": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "id")})
	@Path("/products/{id}/images/by-base64")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Attachment postProductIdImageByBase64(
			@NotNull @Parameter(hidden = true) @PathParam("id") Long id,
			AttachmentBase64 attachmentBase64)
		throws Exception {

		return new Attachment();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-commerce-admin-catalog/v1.0/products/{id}/images/by-url' -d $'{"customFields": ___, "displayDate": ___, "expirationDate": ___, "externalReferenceCode": ___, "neverExpire": ___, "options": ___, "priority": ___, "src": ___, "title": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "id")})
	@Path("/products/{id}/images/by-url")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Attachment")})
	public Attachment postProductIdImageByUrl(
			@NotNull @Parameter(hidden = true) @PathParam("id") Long id,
			AttachmentUrl attachmentUrl)
		throws Exception {

		return new Attachment();
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<Attachment> attachments,
			Map<String, Serializable> parameters)
		throws Exception {
	}

	@Override
	public void delete(
			java.util.Collection<Attachment> attachments,
			Map<String, Serializable> parameters)
		throws Exception {
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
	public Page<Attachment> read(
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
			java.util.Collection<Attachment> attachments,
			Map<String, Serializable> parameters)
		throws Exception {
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
		LogFactoryUtil.getLog(BaseAttachmentResourceImpl.class);

}
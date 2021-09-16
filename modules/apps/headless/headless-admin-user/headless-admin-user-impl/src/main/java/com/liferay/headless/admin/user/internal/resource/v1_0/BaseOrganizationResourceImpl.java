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

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.Organization;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.petra.function.UnsafeConsumer;
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
public abstract class BaseOrganizationResourceImpl
	implements EntityModelResource, OrganizationResource,
			   VulcanBatchEngineTaskItemDelegate<Organization> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the organizations. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/organizations")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Page<Organization> getOrganizationsPage(
			@Parameter(hidden = true) @QueryParam("flatten") Boolean flatten,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations' -d $'{"childOrganizations": ___, "comment": ___, "customFields": ___, "id": ___, "location": ___, "name": ___, "organizationAccounts": ___, "organizationContactInformation": ___, "parentOrganization": ___, "services": ___, "userAccounts": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new organization")
	@Override
	@Path("/organizations")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Organization postOrganization(Organization organization)
		throws Exception {

		return new Organization();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/organizations/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Organization")})
	public Response postOrganizationBatch(
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
				Organization.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(description = "Deletes an organization.")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "organizationId")}
	)
	@Path("/organizations/{organizationId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public void deleteOrganization(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/organizations/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Organization")})
	public Response deleteOrganizationBatch(
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
				Organization.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the organization.")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "organizationId")}
	)
	@Path("/organizations/{organizationId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Organization getOrganization(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId)
		throws Exception {

		return new Organization();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}' -d $'{"childOrganizations": ___, "comment": ___, "customFields": ___, "id": ___, "location": ___, "name": ___, "organizationAccounts": ___, "organizationContactInformation": ___, "parentOrganization": ___, "services": ___, "userAccounts": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates the organization with the information sent in the request body. Fields not present in the request body are left unchanged."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "organizationId")}
	)
	@PATCH
	@Path("/organizations/{organizationId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Organization patchOrganization(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId,
			Organization organization)
		throws Exception {

		Organization existingOrganization = getOrganization(organizationId);

		if (organization.getActions() != null) {
			existingOrganization.setActions(organization.getActions());
		}

		if (organization.getComment() != null) {
			existingOrganization.setComment(organization.getComment());
		}

		if (organization.getDateCreated() != null) {
			existingOrganization.setDateCreated(organization.getDateCreated());
		}

		if (organization.getDateModified() != null) {
			existingOrganization.setDateModified(
				organization.getDateModified());
		}

		if (organization.getImage() != null) {
			existingOrganization.setImage(organization.getImage());
		}

		if (organization.getKeywords() != null) {
			existingOrganization.setKeywords(organization.getKeywords());
		}

		if (organization.getName() != null) {
			existingOrganization.setName(organization.getName());
		}

		if (organization.getNumberOfAccounts() != null) {
			existingOrganization.setNumberOfAccounts(
				organization.getNumberOfAccounts());
		}

		if (organization.getNumberOfOrganizations() != null) {
			existingOrganization.setNumberOfOrganizations(
				organization.getNumberOfOrganizations());
		}

		if (organization.getNumberOfUsers() != null) {
			existingOrganization.setNumberOfUsers(
				organization.getNumberOfUsers());
		}

		preparePatch(organization, existingOrganization);

		return putOrganization(organizationId, existingOrganization);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}' -d $'{"childOrganizations": ___, "comment": ___, "customFields": ___, "id": ___, "location": ___, "name": ___, "organizationAccounts": ___, "organizationContactInformation": ___, "parentOrganization": ___, "services": ___, "userAccounts": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the organization with information sent in the request body. Any missing fields are deleted unless they are required."
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "organizationId")}
	)
	@Path("/organizations/{organizationId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "Organization")})
	public Organization putOrganization(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId,
			Organization organization)
		throws Exception {

		return new Organization();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/organizations/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {@Tag(name = "Organization")})
	public Response putOrganizationBatch(
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
				Organization.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}/child-organizations'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the parent organization's child organizations. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "organizationId"),
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/organizations/{organizationId}/child-organizations")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Page<Organization> getOrganizationChildOrganizationsPage(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId,
			@Parameter(hidden = true) @QueryParam("flatten") Boolean flatten,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/by-email-address'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@DELETE
	@Operation(
		description = "Removes users from an organization by their email addresses"
	)
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "organizationId")}
	)
	@Path("/organizations/{organizationId}/user-accounts/by-email-address")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public void deleteUserAccountsByEmailAddress(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId,
			String[] strings)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/by-email-address'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Assigns users to an organization by their email addresses"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "organizationId"),
			@Parameter(in = ParameterIn.QUERY, name = "organizationRoleIds")
		}
	)
	@Path("/organizations/{organizationId}/user-accounts/by-email-address")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Page<UserAccount> postUserAccountsByEmailAddress(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId,
			@Parameter(hidden = true) @QueryParam("organizationRoleIds") String
				organizationRoleIds,
			String[] strings)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/by-email-address/{emailAddress}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Removes a user from an organization by their email address"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "organizationId"),
			@Parameter(in = ParameterIn.PATH, name = "emailAddress")
		}
	)
	@Path(
		"/organizations/{organizationId}/user-accounts/by-email-address/{emailAddress}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public void deleteUserAccountByEmailAddress(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId,
			@NotNull @Parameter(hidden = true) @PathParam("emailAddress") String
				emailAddress)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/by-email-address/{emailAddress}'  -u 'test@liferay.com:test'
	 */
	@Operation(
		description = "Assigns a user to an organization by their email address"
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "organizationId"),
			@Parameter(in = ParameterIn.PATH, name = "emailAddress")
		}
	)
	@Path(
		"/organizations/{organizationId}/user-accounts/by-email-address/{emailAddress}"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public UserAccount postUserAccountByEmailAddress(
			@NotNull @Parameter(hidden = true) @PathParam("organizationId")
				String organizationId,
			@NotNull @Parameter(hidden = true) @PathParam("emailAddress") String
				emailAddress)
		throws Exception {

		return new UserAccount();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-user/v1.0/organizations/{parentOrganizationId}/organizations'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the parent organization's child organizations. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "parentOrganizationId"),
			@Parameter(in = ParameterIn.QUERY, name = "flatten"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/organizations/{parentOrganizationId}/organizations")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Organization")})
	public Page<Organization> getOrganizationOrganizationsPage(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentOrganizationId")
			String parentOrganizationId,
			@Parameter(hidden = true) @QueryParam("flatten") Boolean flatten,
			@Parameter(hidden = true) @QueryParam("search") String search,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<Organization> organizations,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeConsumer<Organization, Exception> organizationUnsafeConsumer =
			organization -> postOrganization(organization);

		for (Organization organization : organizations) {
			organizationUnsafeConsumer.accept(organization);
		}
	}

	@Override
	public void delete(
			java.util.Collection<Organization> organizations,
			Map<String, Serializable> parameters)
		throws Exception {

		for (Organization organization : organizations) {
			deleteOrganization(organization.getId());
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
	public Page<Organization> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return getOrganizationsPage(
			Boolean.parseBoolean((String)parameters.get("flatten")), search,
			filter, pagination, sorts);
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
			java.util.Collection<Organization> organizations,
			Map<String, Serializable> parameters)
		throws Exception {

		for (Organization organization : organizations) {
			putOrganization(
				organization.getId() != null ? organization.getId() :
					(String)parameters.get("organizationId"),
				organization);
		}
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
		Organization organization, Organization existingOrganization) {
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
		LogFactoryUtil.getLog(BaseOrganizationResourceImpl.class);

}
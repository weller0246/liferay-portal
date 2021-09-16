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

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.ChangeTransition;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToRole;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTasksBulkSelection;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource;
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
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseWorkflowTaskResourceImpl
	implements WorkflowTaskResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "workflowInstanceId"),
			@Parameter(in = ParameterIn.QUERY, name = "completed"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/workflow-instances/{workflowInstanceId}/workflow-tasks")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getWorkflowInstanceWorkflowTasksPage(
			@NotNull @Parameter(hidden = true) @PathParam("workflowInstanceId")
				Long workflowInstanceId,
			@Parameter(hidden = true) @QueryParam("completed") Boolean
				completed,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks/assigned-to-me'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "workflowInstanceId"),
			@Parameter(in = ParameterIn.QUERY, name = "completed"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path(
		"/workflow-instances/{workflowInstanceId}/workflow-tasks/assigned-to-me"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getWorkflowInstanceWorkflowTasksAssignedToMePage(
			@NotNull @Parameter(hidden = true) @PathParam("workflowInstanceId")
				Long workflowInstanceId,
			@Parameter(hidden = true) @QueryParam("completed") Boolean
				completed,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-instances/{workflowInstanceId}/workflow-tasks/assigned-to-user'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "workflowInstanceId"),
			@Parameter(in = ParameterIn.QUERY, name = "assigneeId"),
			@Parameter(in = ParameterIn.QUERY, name = "completed"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path(
		"/workflow-instances/{workflowInstanceId}/workflow-tasks/assigned-to-user"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask>
			getWorkflowInstanceWorkflowTasksAssignedToUserPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("workflowInstanceId")
				Long workflowInstanceId,
				@Parameter(hidden = true) @QueryParam("assigneeId") Long
					assigneeId,
				@Parameter(hidden = true) @QueryParam("completed") Boolean
					completed,
				@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks' -d $'{"andOperator": ___, "assetPrimaryKeys": ___, "assetTitle": ___, "assetTypes": ___, "assigneeIds": ___, "completed": ___, "dateDueEnd": ___, "dateDueStart": ___, "searchByRoles": ___, "searchByUserRoles": ___, "workflowDefinitionId": ___, "workflowInstanceIds": ___, "workflowTaskNames": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/workflow-tasks")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> postWorkflowTasksPage(
			@Context Pagination pagination, @Context Sort[] sorts,
			WorkflowTasksBulkSelection workflowTasksBulkSelection)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/assign-to-user'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@PATCH
	@Path("/workflow-tasks/assign-to-user")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public void patchWorkflowTaskAssignToUser(
			WorkflowTaskAssignToUser[] workflowTaskAssignToUsers)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-me'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/workflow-tasks/assigned-to-me")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getWorkflowTasksAssignedToMePage(
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-my-roles'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/workflow-tasks/assigned-to-my-roles")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getWorkflowTasksAssignedToMyRolesPage(
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-role'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "roleId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/workflow-tasks/assigned-to-role")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getWorkflowTasksAssignedToRolePage(
			@NotNull @Parameter(hidden = true) @QueryParam("roleId") Long
				roleId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-user'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "assigneeId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/workflow-tasks/assigned-to-user")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getWorkflowTasksAssignedToUserPage(
			@Parameter(hidden = true) @QueryParam("assigneeId") Long assigneeId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/assigned-to-user-roles'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "assigneeId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/workflow-tasks/assigned-to-user-roles")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getWorkflowTasksAssignedToUserRolesPage(
			@Parameter(hidden = true) @QueryParam("assigneeId") Long assigneeId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/change-transition'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@PATCH
	@Path("/workflow-tasks/change-transition")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public void patchWorkflowTaskChangeTransition(
			ChangeTransition[] changeTransitions)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/submitting-user'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "creatorId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/workflow-tasks/submitting-user")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Page<WorkflowTask> getWorkflowTasksSubmittingUserPage(
			@Parameter(hidden = true) @QueryParam("creatorId") Long creatorId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/update-due-date'  -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@PATCH
	@Path("/workflow-tasks/update-due-date")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public void patchWorkflowTaskUpdateDueDate(
			WorkflowTaskAssignToMe[] workflowTaskAssignToMes)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "workflowTaskId")}
	)
	@Path("/workflow-tasks/{workflowTaskId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask getWorkflowTask(
			@NotNull @Parameter(hidden = true) @PathParam("workflowTaskId") Long
				workflowTaskId)
		throws Exception {

		return new WorkflowTask();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-me' -d $'{"comment": ___, "dueDate": ___, "workflowTaskId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "workflowTaskId")}
	)
	@Path("/workflow-tasks/{workflowTaskId}/assign-to-me")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskAssignToMe(
			@NotNull @Parameter(hidden = true) @PathParam("workflowTaskId") Long
				workflowTaskId,
			WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		return new WorkflowTask();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-role' -d $'{"comment": ___, "dueDate": ___, "roleId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "workflowTaskId")}
	)
	@Path("/workflow-tasks/{workflowTaskId}/assign-to-role")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskAssignToRole(
			@NotNull @Parameter(hidden = true) @PathParam("workflowTaskId") Long
				workflowTaskId,
			WorkflowTaskAssignToRole workflowTaskAssignToRole)
		throws Exception {

		return new WorkflowTask();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/assign-to-user' -d $'{"assigneeId": ___, "comment": ___, "dueDate": ___, "workflowTaskId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "workflowTaskId")}
	)
	@Path("/workflow-tasks/{workflowTaskId}/assign-to-user")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskAssignToUser(
			@NotNull @Parameter(hidden = true) @PathParam("workflowTaskId") Long
				workflowTaskId,
			WorkflowTaskAssignToUser workflowTaskAssignToUser)
		throws Exception {

		return new WorkflowTask();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/change-transition' -d $'{"comment": ___, "transitionName": ___, "workflowTaskId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "workflowTaskId")}
	)
	@Path("/workflow-tasks/{workflowTaskId}/change-transition")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskChangeTransition(
			@NotNull @Parameter(hidden = true) @PathParam("workflowTaskId") Long
				workflowTaskId,
			ChangeTransition changeTransition)
		throws Exception {

		return new WorkflowTask();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/has-assignable-users'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "workflowTaskId")}
	)
	@Path("/workflow-tasks/{workflowTaskId}/has-assignable-users")
	@Produces("text/plain")
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public Boolean getWorkflowTaskHasAssignableUsers(
			@NotNull @Parameter(hidden = true) @PathParam("workflowTaskId") Long
				workflowTaskId)
		throws Exception {

		return false;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-workflow/v1.0/workflow-tasks/{workflowTaskId}/update-due-date' -d $'{"comment": ___, "dueDate": ___, "workflowTaskId": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.PATH, name = "workflowTaskId")}
	)
	@Path("/workflow-tasks/{workflowTaskId}/update-due-date")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "WorkflowTask")})
	public WorkflowTask postWorkflowTaskUpdateDueDate(
			@NotNull @Parameter(hidden = true) @PathParam("workflowTaskId") Long
				workflowTaskId,
			WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		return new WorkflowTask();
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

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseWorkflowTaskResourceImpl.class);

}
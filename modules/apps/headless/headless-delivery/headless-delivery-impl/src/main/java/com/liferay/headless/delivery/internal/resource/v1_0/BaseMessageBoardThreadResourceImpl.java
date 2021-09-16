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

import com.liferay.headless.delivery.dto.v1_0.MessageBoardThread;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardThreadResource;
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
public abstract class BaseMessageBoardThreadResourceImpl
	implements EntityModelResource, MessageBoardThreadResource,
			   VulcanBatchEngineTaskItemDelegate<MessageBoardThread> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/message-board-threads'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the message board section's threads. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/message-board-sections/{messageBoardSectionId}/message-board-threads"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Page<MessageBoardThread>
			getMessageBoardSectionMessageBoardThreadsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("messageBoardSectionId")
				Long messageBoardSectionId,
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/message-board-threads' -d $'{"articleBody": ___, "creatorStatistics": ___, "customFields": ___, "encodingFormat": ___, "friendlyUrlPath": ___, "hasValidAnswer": ___, "headline": ___, "keywords": ___, "messageBoardSectionId": ___, "seen": ___, "showAsQuestion": ___, "subscribed": ___, "taxonomyCategoryIds": ___, "threadType": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Creates a new message board thread inside a section."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId")
		}
	)
	@Path(
		"/message-board-sections/{messageBoardSectionId}/message-board-threads"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread postMessageBoardSectionMessageBoardThread(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardSectionId")
			Long messageBoardSectionId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		return new MessageBoardThread();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-sections/{messageBoardSectionId}/message-board-threads/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardSectionId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path(
		"/message-board-sections/{messageBoardSectionId}/message-board-threads/batch"
	)
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Response postMessageBoardSectionMessageBoardThreadBatch(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardSectionId")
			Long messageBoardSectionId,
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
				MessageBoardThread.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/ranked'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "dateCreated"),
			@Parameter(in = ParameterIn.QUERY, name = "dateModified"),
			@Parameter(in = ParameterIn.QUERY, name = "messageBoardSectionId"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path("/message-board-threads/ranked")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Page<MessageBoardThread> getMessageBoardThreadsRankedPage(
			@Parameter(hidden = true) @QueryParam("dateCreated") java.util.Date
				dateCreated,
			@Parameter(hidden = true) @QueryParam("dateModified") java.util.Date
				dateModified,
			@Parameter(hidden = true) @QueryParam("messageBoardSectionId") Long
				messageBoardSectionId,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the message board thread and returns a 204 if the operation succeeds."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public void deleteMessageBoardThread(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId")
			Long messageBoardThreadId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/message-board-threads/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Response deleteMessageBoardThreadBatch(
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
				MessageBoardThread.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the message board thread.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread getMessageBoardThread(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId")
			Long messageBoardThreadId)
		throws Exception {

		return new MessageBoardThread();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}' -d $'{"articleBody": ___, "creatorStatistics": ___, "customFields": ___, "encodingFormat": ___, "friendlyUrlPath": ___, "hasValidAnswer": ___, "headline": ___, "keywords": ___, "messageBoardSectionId": ___, "seen": ___, "showAsQuestion": ___, "subscribed": ___, "taxonomyCategoryIds": ___, "threadType": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@PATCH
	@Path("/message-board-threads/{messageBoardThreadId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread patchMessageBoardThread(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId")
			Long messageBoardThreadId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		MessageBoardThread existingMessageBoardThread = getMessageBoardThread(
			messageBoardThreadId);

		if (messageBoardThread.getActions() != null) {
			existingMessageBoardThread.setActions(
				messageBoardThread.getActions());
		}

		if (messageBoardThread.getArticleBody() != null) {
			existingMessageBoardThread.setArticleBody(
				messageBoardThread.getArticleBody());
		}

		if (messageBoardThread.getDateCreated() != null) {
			existingMessageBoardThread.setDateCreated(
				messageBoardThread.getDateCreated());
		}

		if (messageBoardThread.getDateModified() != null) {
			existingMessageBoardThread.setDateModified(
				messageBoardThread.getDateModified());
		}

		if (messageBoardThread.getEncodingFormat() != null) {
			existingMessageBoardThread.setEncodingFormat(
				messageBoardThread.getEncodingFormat());
		}

		if (messageBoardThread.getFriendlyUrlPath() != null) {
			existingMessageBoardThread.setFriendlyUrlPath(
				messageBoardThread.getFriendlyUrlPath());
		}

		if (messageBoardThread.getHasValidAnswer() != null) {
			existingMessageBoardThread.setHasValidAnswer(
				messageBoardThread.getHasValidAnswer());
		}

		if (messageBoardThread.getHeadline() != null) {
			existingMessageBoardThread.setHeadline(
				messageBoardThread.getHeadline());
		}

		if (messageBoardThread.getKeywords() != null) {
			existingMessageBoardThread.setKeywords(
				messageBoardThread.getKeywords());
		}

		if (messageBoardThread.getLocked() != null) {
			existingMessageBoardThread.setLocked(
				messageBoardThread.getLocked());
		}

		if (messageBoardThread.getMessageBoardSectionId() != null) {
			existingMessageBoardThread.setMessageBoardSectionId(
				messageBoardThread.getMessageBoardSectionId());
		}

		if (messageBoardThread.getNumberOfMessageBoardAttachments() != null) {
			existingMessageBoardThread.setNumberOfMessageBoardAttachments(
				messageBoardThread.getNumberOfMessageBoardAttachments());
		}

		if (messageBoardThread.getNumberOfMessageBoardMessages() != null) {
			existingMessageBoardThread.setNumberOfMessageBoardMessages(
				messageBoardThread.getNumberOfMessageBoardMessages());
		}

		if (messageBoardThread.getSeen() != null) {
			existingMessageBoardThread.setSeen(messageBoardThread.getSeen());
		}

		if (messageBoardThread.getShowAsQuestion() != null) {
			existingMessageBoardThread.setShowAsQuestion(
				messageBoardThread.getShowAsQuestion());
		}

		if (messageBoardThread.getSiteId() != null) {
			existingMessageBoardThread.setSiteId(
				messageBoardThread.getSiteId());
		}

		if (messageBoardThread.getStatus() != null) {
			existingMessageBoardThread.setStatus(
				messageBoardThread.getStatus());
		}

		if (messageBoardThread.getSubscribed() != null) {
			existingMessageBoardThread.setSubscribed(
				messageBoardThread.getSubscribed());
		}

		if (messageBoardThread.getTaxonomyCategoryIds() != null) {
			existingMessageBoardThread.setTaxonomyCategoryIds(
				messageBoardThread.getTaxonomyCategoryIds());
		}

		if (messageBoardThread.getThreadType() != null) {
			existingMessageBoardThread.setThreadType(
				messageBoardThread.getThreadType());
		}

		if (messageBoardThread.getViewCount() != null) {
			existingMessageBoardThread.setViewCount(
				messageBoardThread.getViewCount());
		}

		if (messageBoardThread.getViewableBy() != null) {
			existingMessageBoardThread.setViewableBy(
				messageBoardThread.getViewableBy());
		}

		preparePatch(messageBoardThread, existingMessageBoardThread);

		return putMessageBoardThread(
			messageBoardThreadId, existingMessageBoardThread);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}' -d $'{"articleBody": ___, "creatorStatistics": ___, "customFields": ___, "encodingFormat": ___, "friendlyUrlPath": ___, "hasValidAnswer": ___, "headline": ___, "keywords": ___, "messageBoardSectionId": ___, "seen": ___, "showAsQuestion": ___, "subscribed": ___, "taxonomyCategoryIds": ___, "threadType": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the message board thread with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread putMessageBoardThread(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId")
			Long messageBoardThreadId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		return new MessageBoardThread();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/message-board-threads/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Response putMessageBoardThreadBatch(
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
				MessageBoardThread.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the message board thread's rating and returns a 204 if the operation succeeds."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public void deleteMessageBoardThreadMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId")
			Long messageBoardThreadId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the message board thread's rating.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Rating getMessageBoardThreadMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId")
			Long messageBoardThreadId)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates the message board thread's rating.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}/my-rating")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Rating postMessageBoardThreadMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId")
			Long messageBoardThreadId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Rating putMessageBoardThreadMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId")
			Long messageBoardThreadId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getMessageBoardThreadPermissionsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("messageBoardThreadId")
				Long messageBoardThreadId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			messageBoardThreadId);
		Long resourceId = getPermissionCheckerResourceId(messageBoardThreadId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(messageBoardThreadId));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getMessageBoardThreadPermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putMessageBoardThreadPermission",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putMessageBoardThreadPermission(
				@NotNull @Parameter(hidden = true)
				@PathParam("messageBoardThreadId")
				Long messageBoardThreadId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			messageBoardThreadId);
		Long resourceId = getPermissionCheckerResourceId(messageBoardThreadId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(messageBoardThreadId));

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(),
			getPermissionCheckerGroupId(messageBoardThreadId), resourceName,
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
					"getMessageBoardThreadPermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putMessageBoardThreadPermission",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/subscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}/subscribe")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public void putMessageBoardThreadSubscribe(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId")
			Long messageBoardThreadId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path("/message-board-threads/{messageBoardThreadId}/unsubscribe")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public void putMessageBoardThreadUnsubscribe(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId")
			Long messageBoardThreadId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-threads'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the site's message board threads. Results can be paginated, filtered, searched, flattened, and sorted."
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
	@Path("/sites/{siteId}/message-board-threads")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Page<MessageBoardThread> getSiteMessageBoardThreadsPage(
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-threads' -d $'{"articleBody": ___, "creatorStatistics": ___, "customFields": ___, "encodingFormat": ___, "friendlyUrlPath": ___, "hasValidAnswer": ___, "headline": ___, "keywords": ___, "messageBoardSectionId": ___, "seen": ___, "showAsQuestion": ___, "subscribed": ___, "taxonomyCategoryIds": ___, "threadType": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new message board thread.")
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/message-board-threads")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread postSiteMessageBoardThread(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		return new MessageBoardThread();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-threads/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/sites/{siteId}/message-board-threads/batch")
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Response postSiteMessageBoardThreadBatch(
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
				MessageBoardThread.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-threads/by-friendly-url-path/{friendlyUrlPath}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "friendlyUrlPath")
		}
	)
	@Path(
		"/sites/{siteId}/message-board-threads/by-friendly-url-path/{friendlyUrlPath}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public MessageBoardThread getSiteMessageBoardThreadByFriendlyUrlPath(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true) @PathParam("friendlyUrlPath")
				String friendlyUrlPath)
		throws Exception {

		return new MessageBoardThread();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-threads/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/sites/{siteId}/message-board-threads/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteMessageBoardThreadPermissionsPage(
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
					"getSiteMessageBoardThreadPermissionsPage", portletName,
					siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putSiteMessageBoardThreadPermission", portletName, siteId)
			).build(),
			siteId, portletName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-threads/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/message-board-threads/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardThread")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteMessageBoardThreadPermission(
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
					"getSiteMessageBoardThreadPermissionsPage", portletName,
					siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putSiteMessageBoardThreadPermission", portletName, siteId)
			).build(),
			siteId, portletName, null);
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<MessageBoardThread> messageBoardThreads,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeConsumer<MessageBoardThread, Exception>
			messageBoardThreadUnsafeConsumer =
				messageBoardThread -> postMessageBoardSectionMessageBoardThread(
					Long.parseLong(
						(String)parameters.get("messageBoardSectionId")),
					messageBoardThread);

		if (parameters.containsKey("siteId")) {
			messageBoardThreadUnsafeConsumer =
				messageBoardThread -> postSiteMessageBoardThread(
					(Long)parameters.get("siteId"), messageBoardThread);
		}

		for (MessageBoardThread messageBoardThread : messageBoardThreads) {
			messageBoardThreadUnsafeConsumer.accept(messageBoardThread);
		}
	}

	@Override
	public void delete(
			java.util.Collection<MessageBoardThread> messageBoardThreads,
			Map<String, Serializable> parameters)
		throws Exception {

		for (MessageBoardThread messageBoardThread : messageBoardThreads) {
			deleteMessageBoardThread(messageBoardThread.getId());
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
	public Page<MessageBoardThread> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		if (parameters.containsKey("siteId")) {
			return getSiteMessageBoardThreadsPage(
				(Long)parameters.get("siteId"),
				Boolean.parseBoolean((String)parameters.get("flatten")), search,
				null, filter, pagination, sorts);
		}
		else {
			return getMessageBoardSectionMessageBoardThreadsPage(
				Long.parseLong((String)parameters.get("messageBoardSectionId")),
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
			java.util.Collection<MessageBoardThread> messageBoardThreads,
			Map<String, Serializable> parameters)
		throws Exception {

		for (MessageBoardThread messageBoardThread : messageBoardThreads) {
			putMessageBoardThread(
				messageBoardThread.getId() != null ?
					messageBoardThread.getId() :
						Long.parseLong(
							(String)parameters.get("messageBoardThreadId")),
				messageBoardThread);
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
		MessageBoardThread messageBoardThread,
		MessageBoardThread existingMessageBoardThread) {
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
		LogFactoryUtil.getLog(BaseMessageBoardThreadResourceImpl.class);

}
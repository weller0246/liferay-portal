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

import com.liferay.headless.delivery.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardMessageResource;
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
public abstract class BaseMessageBoardMessageResourceImpl
	implements EntityModelResource, MessageBoardMessageResource,
			   VulcanBatchEngineTaskItemDelegate<MessageBoardMessage> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the message board message and returns a 204 if the operation succeeds."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public void deleteMessageBoardMessage(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId")
			Long messageBoardMessageId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/message-board-messages/batch")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Response deleteMessageBoardMessageBatch(
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
				MessageBoardMessage.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the message board message.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage getMessageBoardMessage(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId")
			Long messageBoardMessageId)
		throws Exception {

		return new MessageBoardMessage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}' -d $'{"anonymous": ___, "articleBody": ___, "creatorStatistics": ___, "customFields": ___, "encodingFormat": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "headline": ___, "keywords": ___, "messageBoardSectionId": ___, "parentMessageBoardMessageId": ___, "showAsAnswer": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates only the fields received in the request body, leaving any other fields untouched."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@PATCH
	@Path("/message-board-messages/{messageBoardMessageId}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage patchMessageBoardMessage(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId")
			Long messageBoardMessageId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		MessageBoardMessage existingMessageBoardMessage =
			getMessageBoardMessage(messageBoardMessageId);

		if (messageBoardMessage.getActions() != null) {
			existingMessageBoardMessage.setActions(
				messageBoardMessage.getActions());
		}

		if (messageBoardMessage.getAnonymous() != null) {
			existingMessageBoardMessage.setAnonymous(
				messageBoardMessage.getAnonymous());
		}

		if (messageBoardMessage.getArticleBody() != null) {
			existingMessageBoardMessage.setArticleBody(
				messageBoardMessage.getArticleBody());
		}

		if (messageBoardMessage.getDateCreated() != null) {
			existingMessageBoardMessage.setDateCreated(
				messageBoardMessage.getDateCreated());
		}

		if (messageBoardMessage.getDateModified() != null) {
			existingMessageBoardMessage.setDateModified(
				messageBoardMessage.getDateModified());
		}

		if (messageBoardMessage.getEncodingFormat() != null) {
			existingMessageBoardMessage.setEncodingFormat(
				messageBoardMessage.getEncodingFormat());
		}

		if (messageBoardMessage.getExternalReferenceCode() != null) {
			existingMessageBoardMessage.setExternalReferenceCode(
				messageBoardMessage.getExternalReferenceCode());
		}

		if (messageBoardMessage.getFriendlyUrlPath() != null) {
			existingMessageBoardMessage.setFriendlyUrlPath(
				messageBoardMessage.getFriendlyUrlPath());
		}

		if (messageBoardMessage.getHeadline() != null) {
			existingMessageBoardMessage.setHeadline(
				messageBoardMessage.getHeadline());
		}

		if (messageBoardMessage.getKeywords() != null) {
			existingMessageBoardMessage.setKeywords(
				messageBoardMessage.getKeywords());
		}

		if (messageBoardMessage.getMessageBoardSectionId() != null) {
			existingMessageBoardMessage.setMessageBoardSectionId(
				messageBoardMessage.getMessageBoardSectionId());
		}

		if (messageBoardMessage.getMessageBoardThreadId() != null) {
			existingMessageBoardMessage.setMessageBoardThreadId(
				messageBoardMessage.getMessageBoardThreadId());
		}

		if (messageBoardMessage.getNumberOfMessageBoardAttachments() != null) {
			existingMessageBoardMessage.setNumberOfMessageBoardAttachments(
				messageBoardMessage.getNumberOfMessageBoardAttachments());
		}

		if (messageBoardMessage.getNumberOfMessageBoardMessages() != null) {
			existingMessageBoardMessage.setNumberOfMessageBoardMessages(
				messageBoardMessage.getNumberOfMessageBoardMessages());
		}

		if (messageBoardMessage.getParentMessageBoardMessageId() != null) {
			existingMessageBoardMessage.setParentMessageBoardMessageId(
				messageBoardMessage.getParentMessageBoardMessageId());
		}

		if (messageBoardMessage.getShowAsAnswer() != null) {
			existingMessageBoardMessage.setShowAsAnswer(
				messageBoardMessage.getShowAsAnswer());
		}

		if (messageBoardMessage.getSiteId() != null) {
			existingMessageBoardMessage.setSiteId(
				messageBoardMessage.getSiteId());
		}

		if (messageBoardMessage.getStatus() != null) {
			existingMessageBoardMessage.setStatus(
				messageBoardMessage.getStatus());
		}

		if (messageBoardMessage.getSubscribed() != null) {
			existingMessageBoardMessage.setSubscribed(
				messageBoardMessage.getSubscribed());
		}

		if (messageBoardMessage.getViewableBy() != null) {
			existingMessageBoardMessage.setViewableBy(
				messageBoardMessage.getViewableBy());
		}

		preparePatch(messageBoardMessage, existingMessageBoardMessage);

		return putMessageBoardMessage(
			messageBoardMessageId, existingMessageBoardMessage);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}' -d $'{"anonymous": ___, "articleBody": ___, "creatorStatistics": ___, "customFields": ___, "encodingFormat": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "headline": ___, "keywords": ___, "messageBoardSectionId": ___, "parentMessageBoardMessageId": ___, "showAsAnswer": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the message board message with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage putMessageBoardMessage(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId")
			Long messageBoardMessageId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		return new MessageBoardMessage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "callbackURL")}
	)
	@Path("/message-board-messages/batch")
	@Produces("application/json")
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Response putMessageBoardMessageBatch(
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
				MessageBoardMessage.class.getName(), callbackURL, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the message board message's rating and returns a 204 if the operation succeeds."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public void deleteMessageBoardMessageMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId")
			Long messageBoardMessageId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the message board message's rating.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Rating getMessageBoardMessageMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId")
			Long messageBoardMessageId)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a rating for the message board message.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}/my-rating")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Rating postMessageBoardMessageMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId")
			Long messageBoardMessageId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/my-rating' -d $'{"ratingValue": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Replaces the rating with the information sent in the request body. Any missing fields are deleted, unless they are required."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}/my-rating")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Rating putMessageBoardMessageMyRating(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId")
			Long messageBoardMessageId,
			Rating rating)
		throws Exception {

		return new Rating();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getMessageBoardMessagePermissionsPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("messageBoardMessageId")
				Long messageBoardMessageId,
				@Parameter(hidden = true) @QueryParam("roleNames") String
					roleNames)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			messageBoardMessageId);
		Long resourceId = getPermissionCheckerResourceId(messageBoardMessageId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(messageBoardMessageId));

		return toPermissionPage(
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.PERMISSIONS,
					"getMessageBoardMessagePermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putMessageBoardMessagePermission",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putMessageBoardMessagePermission(
				@NotNull @Parameter(hidden = true)
				@PathParam("messageBoardMessageId")
				Long messageBoardMessageId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		String resourceName = getPermissionCheckerResourceName(
			messageBoardMessageId);
		Long resourceId = getPermissionCheckerResourceId(messageBoardMessageId);

		PermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService, resourceName, resourceId,
			getPermissionCheckerGroupId(messageBoardMessageId));

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(),
			getPermissionCheckerGroupId(messageBoardMessageId), resourceName,
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
					"getMessageBoardMessagePermissionsPage", resourceName,
					resourceId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS, "putMessageBoardMessagePermission",
					resourceName, resourceId)
			).build(),
			resourceId, resourceName, null);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/subscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}/subscribe")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public void putMessageBoardMessageSubscribe(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId")
			Long messageBoardMessageId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{messageBoardMessageId}/unsubscribe'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardMessageId")
		}
	)
	@Path("/message-board-messages/{messageBoardMessageId}/unsubscribe")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public void putMessageBoardMessageUnsubscribe(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardMessageId")
			Long messageBoardMessageId)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{parentMessageBoardMessageId}/message-board-messages'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the parent message board message's child messages. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentMessageBoardMessageId"
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
		"/message-board-messages/{parentMessageBoardMessageId}/message-board-messages"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Page<MessageBoardMessage>
			getMessageBoardMessageMessageBoardMessagesPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("parentMessageBoardMessageId")
				Long parentMessageBoardMessageId,
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-messages/{parentMessageBoardMessageId}/message-board-messages' -d $'{"anonymous": ___, "articleBody": ___, "creatorStatistics": ___, "customFields": ___, "encodingFormat": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "headline": ___, "keywords": ___, "messageBoardSectionId": ___, "parentMessageBoardMessageId": ___, "showAsAnswer": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Creates a child message board message of the parent message."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(
				in = ParameterIn.PATH, name = "parentMessageBoardMessageId"
			)
		}
	)
	@Path(
		"/message-board-messages/{parentMessageBoardMessageId}/message-board-messages"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage postMessageBoardMessageMessageBoardMessage(
			@NotNull @Parameter(hidden = true)
			@PathParam("parentMessageBoardMessageId")
			Long parentMessageBoardMessageId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		return new MessageBoardMessage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-messages'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the message board thread's messages. Results can be paginated, filtered, searched, and sorted."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId"),
			@Parameter(in = ParameterIn.QUERY, name = "search"),
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sort")
		}
	)
	@Path(
		"/message-board-threads/{messageBoardThreadId}/message-board-messages"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Page<MessageBoardMessage>
			getMessageBoardThreadMessageBoardMessagesPage(
				@NotNull @Parameter(hidden = true)
				@PathParam("messageBoardThreadId")
				Long messageBoardThreadId,
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
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-messages' -d $'{"anonymous": ___, "articleBody": ___, "creatorStatistics": ___, "customFields": ___, "encodingFormat": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "headline": ___, "keywords": ___, "messageBoardSectionId": ___, "parentMessageBoardMessageId": ___, "showAsAnswer": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Creates a new message in the message board thread."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId")
		}
	)
	@Path(
		"/message-board-threads/{messageBoardThreadId}/message-board-messages"
	)
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage postMessageBoardThreadMessageBoardMessage(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId")
			Long messageBoardThreadId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		return new MessageBoardMessage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-delivery/v1.0/message-board-threads/{messageBoardThreadId}/message-board-messages/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "messageBoardThreadId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path(
		"/message-board-threads/{messageBoardThreadId}/message-board-messages/batch"
	)
	@POST
	@Produces("application/json")
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Response postMessageBoardThreadMessageBoardMessageBatch(
			@NotNull @Parameter(hidden = true)
			@PathParam("messageBoardThreadId")
			Long messageBoardThreadId,
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
				MessageBoardMessage.class.getName(), callbackURL, null, object)
		).build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-messages'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the site's message board messages.")
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
	@Path("/sites/{siteId}/message-board-messages")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Page<MessageBoardMessage> getSiteMessageBoardMessagesPage(
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
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(
		description = "Deletes the site's message board message by external reference code."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/sites/{siteId}/message-board-messages/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public void deleteSiteMessageBoardMessageByExternalReferenceCode(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true)
			@PathParam("externalReferenceCode")
			String externalReferenceCode)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{externalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(
		description = "Retrieves the site's message board message by external reference code."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/sites/{siteId}/message-board-messages/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage
			getSiteMessageBoardMessageByExternalReferenceCode(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				@NotNull @Parameter(hidden = true)
				@PathParam("externalReferenceCode")
				String externalReferenceCode)
		throws Exception {

		return new MessageBoardMessage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-external-reference-code/{externalReferenceCode}' -d $'{"anonymous": ___, "articleBody": ___, "creatorStatistics": ___, "customFields": ___, "encodingFormat": ___, "externalReferenceCode": ___, "friendlyUrlPath": ___, "headline": ___, "keywords": ___, "messageBoardSectionId": ___, "parentMessageBoardMessageId": ___, "showAsAnswer": ___, "viewableBy": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(
		description = "Updates the site's message board message with the given external reference code, or creates it if it not exists."
	)
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.PATH, name = "externalReferenceCode")
		}
	)
	@Path(
		"/sites/{siteId}/message-board-messages/by-external-reference-code/{externalReferenceCode}"
	)
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage
			putSiteMessageBoardMessageByExternalReferenceCode(
				@NotNull @Parameter(hidden = true) @PathParam("siteId") Long
					siteId,
				@NotNull @Parameter(hidden = true)
				@PathParam("externalReferenceCode")
				String externalReferenceCode,
				MessageBoardMessage messageBoardMessage)
		throws Exception {

		return new MessageBoardMessage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-messages/by-friendly-url-path/{friendlyUrlPath}'  -u 'test@liferay.com:test'
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
		"/sites/{siteId}/message-board-messages/by-friendly-url-path/{friendlyUrlPath}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public MessageBoardMessage getSiteMessageBoardMessageByFriendlyUrlPath(
			@NotNull @Parameter(hidden = true) @PathParam("siteId") Long siteId,
			@NotNull @Parameter(hidden = true) @PathParam("friendlyUrlPath")
				String friendlyUrlPath)
		throws Exception {

		return new MessageBoardMessage();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-messages/permissions'  -u 'test@liferay.com:test'
	 */
	@GET
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "siteId"),
			@Parameter(in = ParameterIn.QUERY, name = "roleNames")
		}
	)
	@Path("/sites/{siteId}/message-board-messages/permissions")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteMessageBoardMessagePermissionsPage(
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
					"getSiteMessageBoardMessagePermissionsPage", portletName,
					siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putSiteMessageBoardMessagePermission", portletName, siteId)
			).build(),
			siteId, portletName, roleNames);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/message-board-messages/permissions'  -u 'test@liferay.com:test'
	 */
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "siteId")})
	@Path("/sites/{siteId}/message-board-messages/permissions")
	@Produces({"application/json", "application/xml"})
	@PUT
	@Tags(value = {@Tag(name = "MessageBoardMessage")})
	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteMessageBoardMessagePermission(
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
					"getSiteMessageBoardMessagePermissionsPage", portletName,
					siteId)
			).put(
				"replace",
				addAction(
					ActionKeys.PERMISSIONS,
					"putSiteMessageBoardMessagePermission", portletName, siteId)
			).build(),
			siteId, portletName, null);
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			java.util.Collection<MessageBoardMessage> messageBoardMessages,
			Map<String, Serializable> parameters)
		throws Exception {

		UnsafeConsumer<MessageBoardMessage, Exception>
			messageBoardMessageUnsafeConsumer =
				messageBoardMessage ->
					postMessageBoardThreadMessageBoardMessage(
						Long.parseLong(
							(String)parameters.get("messageBoardThreadId")),
						messageBoardMessage);

		for (MessageBoardMessage messageBoardMessage : messageBoardMessages) {
			messageBoardMessageUnsafeConsumer.accept(messageBoardMessage);
		}
	}

	@Override
	public void delete(
			java.util.Collection<MessageBoardMessage> messageBoardMessages,
			Map<String, Serializable> parameters)
		throws Exception {

		for (MessageBoardMessage messageBoardMessage : messageBoardMessages) {
			deleteMessageBoardMessage(messageBoardMessage.getId());
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
	public Page<MessageBoardMessage> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		if (parameters.containsKey("siteId")) {
			return getSiteMessageBoardMessagesPage(
				(Long)parameters.get("siteId"),
				Boolean.parseBoolean((String)parameters.get("flatten")), search,
				null, filter, pagination, sorts);
		}
		else {
			return getMessageBoardThreadMessageBoardMessagesPage(
				Long.parseLong((String)parameters.get("messageBoardThreadId")),
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
			java.util.Collection<MessageBoardMessage> messageBoardMessages,
			Map<String, Serializable> parameters)
		throws Exception {

		for (MessageBoardMessage messageBoardMessage : messageBoardMessages) {
			putMessageBoardMessage(
				messageBoardMessage.getId() != null ?
					messageBoardMessage.getId() :
						Long.parseLong(
							(String)parameters.get("messageBoardMessageId")),
				messageBoardMessage);
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
		MessageBoardMessage messageBoardMessage,
		MessageBoardMessage existingMessageBoardMessage) {
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
		LogFactoryUtil.getLog(BaseMessageBoardMessageResourceImpl.class);

}
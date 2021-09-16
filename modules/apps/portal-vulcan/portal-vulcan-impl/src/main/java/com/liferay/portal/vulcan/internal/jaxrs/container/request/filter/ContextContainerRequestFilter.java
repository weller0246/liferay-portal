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

package com.liferay.portal.vulcan.internal.jaxrs.container.request.filter;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;
import com.liferay.portal.vulcan.internal.configuration.util.ConfigurationUtil;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.ContextProviderUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Javier Gamarra
 */
@Provider
public class ContextContainerRequestFilter implements ContainerRequestFilter {

	public ContextContainerRequestFilter(
		ConfigurationAdmin configurationAdmin,
		ExpressionConvert<Filter> expressionConvert,
		FilterParserProvider filterParserProvider,
		GroupLocalService groupLocalService, Language language, Portal portal,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService, Object scopeChecker,
		VulcanBatchEngineImportTaskResource
			vulcanBatchEngineImportTaskResource) {

		_configurationAdmin = configurationAdmin;
		_expressionConvert = expressionConvert;
		_filterParserProvider = filterParserProvider;
		_groupLocalService = groupLocalService;
		_language = language;
		_portal = portal;
		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
		_scopeChecker = scopeChecker;
		_vulcanBatchEngineImportTaskResource =
			vulcanBatchEngineImportTaskResource;
	}

	@Override
	public void filter(ContainerRequestContext containerRequestContext) {
		handleMessage(
			containerRequestContext, PhaseInterceptorChain.getCurrentMessage());
	}

	public void handleMessage(
			ContainerRequestContext containerRequestContext, Message message)
		throws Fault {

		try {
			_handleMessage(containerRequestContext, message);
		}
		catch (Exception exception) {
			throw new Fault(exception);
		}
	}

	private void _filterExcludedOperationIds(
		ContainerRequestContext containerRequestContext, Message message) {

		String path = StringUtil.removeSubstring(
			(String)message.get(Message.BASE_PATH), "/o");

		path = StringUtil.replaceLast(path, '/', "");

		Set<String> excludedOperationIds =
			ConfigurationUtil.getExcludedOperationIds(
				_configurationAdmin, path);

		Method method = (Method)message.get("org.apache.cxf.resource.method");

		if (excludedOperationIds.contains(method.getName())) {
			containerRequestContext.abortWith(
				Response.status(
					Response.Status.CONFLICT
				).entity(
					"Conflict with " + method.getName()
				).build());
		}
	}

	private void _handleMessage(
			ContainerRequestContext containerRequestContext, Message message)
		throws Exception {

		Object instance = ContextProviderUtil.getMatchedResource(message);

		if (instance == null) {
			return;
		}

		HttpServletRequest httpServletRequest =
			ContextProviderUtil.getHttpServletRequest(message);

		_filterExcludedOperationIds(containerRequestContext, message);

		Class<?> clazz = instance.getClass();

		Class<?> superClass = clazz.getSuperclass();

		for (Field field : superClass.getDeclaredFields()) {
			if (Modifier.isFinal(field.getModifiers()) ||
				Modifier.isStatic(field.getModifiers())) {

				continue;
			}

			Class<?> fieldClass = field.getType();

			if (fieldClass.equals(Object.class) &&
				Objects.equals(field.getName(), "contextScopeChecker")) {

				field.setAccessible(true);

				field.set(instance, _scopeChecker);

				continue;
			}

			if (fieldClass.isAssignableFrom(AcceptLanguage.class)) {
				field.setAccessible(true);

				field.set(
					instance,
					new AcceptLanguageImpl(
						httpServletRequest, _language, _portal));
			}
			else if (fieldClass.isAssignableFrom(Company.class)) {
				field.setAccessible(true);

				field.set(instance, _portal.getCompany(httpServletRequest));
			}
			else if (fieldClass.isAssignableFrom(ExpressionConvert.class)) {
				field.setAccessible(true);

				field.set(instance, _expressionConvert);
			}
			else if (fieldClass.isAssignableFrom(FilterParserProvider.class)) {
				field.setAccessible(true);

				field.set(instance, _filterParserProvider);
			}
			else if (fieldClass.isAssignableFrom(GroupLocalService.class)) {
				field.setAccessible(true);

				field.set(instance, _groupLocalService);
			}
			else if (fieldClass.isAssignableFrom(HttpServletRequest.class)) {
				field.setAccessible(true);

				field.set(instance, httpServletRequest);
			}
			else if (fieldClass.isAssignableFrom(HttpServletResponse.class)) {
				field.setAccessible(true);

				field.set(
					instance, message.getContextualProperty("HTTP.RESPONSE"));
			}
			else if (fieldClass.isAssignableFrom(
						ResourceActionLocalService.class)) {

				field.setAccessible(true);

				field.set(instance, _resourceActionLocalService);
			}
			else if (fieldClass.isAssignableFrom(
						ResourcePermissionLocalService.class)) {

				field.setAccessible(true);

				field.set(instance, _resourcePermissionLocalService);
			}
			else if (fieldClass.isAssignableFrom(RoleLocalService.class)) {
				field.setAccessible(true);

				field.set(instance, _roleLocalService);
			}
			else if (fieldClass.isAssignableFrom(UriInfo.class)) {
				field.setAccessible(true);

				field.set(instance, new UriInfoImpl(message));
			}
			else if (fieldClass.isAssignableFrom(User.class)) {
				field.setAccessible(true);

				field.set(instance, _portal.getUser(httpServletRequest));
			}
			else if (fieldClass.isAssignableFrom(
						VulcanBatchEngineImportTaskResource.class)) {

				field.setAccessible(true);

				field.set(instance, _vulcanBatchEngineImportTaskResource);
			}
		}
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final ExpressionConvert<Filter> _expressionConvert;
	private final FilterParserProvider _filterParserProvider;
	private final GroupLocalService _groupLocalService;
	private final Language _language;
	private final Portal _portal;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;
	private final Object _scopeChecker;
	private final VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}
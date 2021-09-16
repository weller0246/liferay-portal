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

package com.liferay.headless.admin.list.type.internal.graphql.query.v1_0;

import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeDefinition;
import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeEntry;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeDefinitionResource;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeEntryResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public class Query {

	public static void setListTypeDefinitionResourceComponentServiceObjects(
		ComponentServiceObjects<ListTypeDefinitionResource>
			listTypeDefinitionResourceComponentServiceObjects) {

		_listTypeDefinitionResourceComponentServiceObjects =
			listTypeDefinitionResourceComponentServiceObjects;
	}

	public static void setListTypeEntryResourceComponentServiceObjects(
		ComponentServiceObjects<ListTypeEntryResource>
			listTypeEntryResourceComponentServiceObjects) {

		_listTypeEntryResourceComponentServiceObjects =
			listTypeEntryResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {listTypeDefinitions(page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ListTypeDefinitionPage listTypeDefinitions(
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_listTypeDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			listTypeDefinitionResource -> new ListTypeDefinitionPage(
				listTypeDefinitionResource.getListTypeDefinitionsPage(
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {listTypeDefinition(listTypeDefinitionId: ___){actions, dateCreated, dateModified, id, listTypeEntries, name, name_i18n}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ListTypeDefinition listTypeDefinition(
			@GraphQLName("listTypeDefinitionId") Long listTypeDefinitionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_listTypeDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			listTypeDefinitionResource ->
				listTypeDefinitionResource.getListTypeDefinition(
					listTypeDefinitionId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {listTypeDefinitionListTypeEntries(listTypeDefinitionId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ListTypeEntryPage listTypeDefinitionListTypeEntries(
			@GraphQLName("listTypeDefinitionId") Long listTypeDefinitionId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_listTypeEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			listTypeEntryResource -> new ListTypeEntryPage(
				listTypeEntryResource.getListTypeDefinitionListTypeEntriesPage(
					listTypeDefinitionId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {listTypeEntry(listTypeEntryId: ___){actions, dateCreated, dateModified, id, key, name, name_i18n, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ListTypeEntry listTypeEntry(
			@GraphQLName("listTypeEntryId") Long listTypeEntryId)
		throws Exception {

		return _applyComponentServiceObjects(
			_listTypeEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			listTypeEntryResource -> listTypeEntryResource.getListTypeEntry(
				listTypeEntryId));
	}

	@GraphQLName("ListTypeDefinitionPage")
	public class ListTypeDefinitionPage {

		public ListTypeDefinitionPage(Page listTypeDefinitionPage) {
			actions = listTypeDefinitionPage.getActions();

			items = listTypeDefinitionPage.getItems();
			lastPage = listTypeDefinitionPage.getLastPage();
			page = listTypeDefinitionPage.getPage();
			pageSize = listTypeDefinitionPage.getPageSize();
			totalCount = listTypeDefinitionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ListTypeDefinition> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ListTypeEntryPage")
	public class ListTypeEntryPage {

		public ListTypeEntryPage(Page listTypeEntryPage) {
			actions = listTypeEntryPage.getActions();

			items = listTypeEntryPage.getItems();
			lastPage = listTypeEntryPage.getLastPage();
			page = listTypeEntryPage.getPage();
			pageSize = listTypeEntryPage.getPageSize();
			totalCount = listTypeEntryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ListTypeEntry> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			ListTypeDefinitionResource listTypeDefinitionResource)
		throws Exception {

		listTypeDefinitionResource.setContextAcceptLanguage(_acceptLanguage);
		listTypeDefinitionResource.setContextCompany(_company);
		listTypeDefinitionResource.setContextHttpServletRequest(
			_httpServletRequest);
		listTypeDefinitionResource.setContextHttpServletResponse(
			_httpServletResponse);
		listTypeDefinitionResource.setContextUriInfo(_uriInfo);
		listTypeDefinitionResource.setContextUser(_user);
		listTypeDefinitionResource.setGroupLocalService(_groupLocalService);
		listTypeDefinitionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ListTypeEntryResource listTypeEntryResource)
		throws Exception {

		listTypeEntryResource.setContextAcceptLanguage(_acceptLanguage);
		listTypeEntryResource.setContextCompany(_company);
		listTypeEntryResource.setContextHttpServletRequest(_httpServletRequest);
		listTypeEntryResource.setContextHttpServletResponse(
			_httpServletResponse);
		listTypeEntryResource.setContextUriInfo(_uriInfo);
		listTypeEntryResource.setContextUser(_user);
		listTypeEntryResource.setGroupLocalService(_groupLocalService);
		listTypeEntryResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<ListTypeDefinitionResource>
		_listTypeDefinitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<ListTypeEntryResource>
		_listTypeEntryResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}
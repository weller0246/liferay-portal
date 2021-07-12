<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
BatchEngineImportTaskDisplayContext batchEngineImportTaskDisplayContext = (BatchEngineImportTaskDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:navigation-bar
	navigationItems="<%= batchEngineImportTaskDisplayContext.getNavigationItems() %>"
/>

<div class="closed container" id="<portlet:namespace />batchEngineImportTasksPanelId">
	<liferay-ui:search-container
		id="batchEngineImportTasks"
		searchContainer="<%= batchEngineImportTaskDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.batch.engine.model.BatchEngineImportTask"
			keyProperty="batchEngineImportTaskId"
			modelVar="batchEngineImportTask"
		>
			<liferay-ui:search-container-column-text
				name="operation"
				property="operation"
			/>

			<liferay-ui:search-container-column-text
				name="content-type"
				property="contentType"
			/>

			<liferay-ui:search-container-column-date
				name="create-date"
				property="createDate"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="important table-cell-ws-nowrap"
				name="status"
				path="/batch_engine_import_task_status.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>
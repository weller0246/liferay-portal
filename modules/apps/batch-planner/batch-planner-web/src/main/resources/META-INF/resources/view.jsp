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
BatchPlannerPlanDisplayContext batchPlannerPlanDisplayContext = (BatchPlannerPlanDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<BatchPlannerPlan> batchPlannerPlanSearchContainer = batchPlannerPlanDisplayContext.getSearchContainer();

BatchPlannerPlanManagementToolbarDisplayContext batchPlannerPlanManagementToolbarDisplayContext = new BatchPlannerPlanManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, batchPlannerPlanSearchContainer);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= batchPlannerPlanManagementToolbarDisplayContext %>"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			searchContainer="<%= batchPlannerPlanSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.batch.planner.model.BatchPlannerPlan"
				keyProperty="batchPlannerPlanId"
				modelVar="batchPlannerPlan"
			>

				<%
				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(batchPlannerPlanManagementToolbarDisplayContext.getAvailableActions(), StringPool.COMMA)
					).build());
				%>

				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/batch_planner/edit_batch_planner_plan" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="batchPlannerPlanId" value="<%= String.valueOf(batchPlannerPlan.getBatchPlannerPlanId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					cssClass="important"
					href="<%= rowURL %>"
					name="name"
					value="<%= HtmlUtil.escape(batchPlannerPlan.getName()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="action"
					value='<%= LanguageUtil.get(request, batchPlannerPlan.isExport() ? "export" : "import") %>'
				/>

				<liferay-ui:search-container-column-text
					name="type"
					value="<%= batchPlannerPlanDisplayContext.getSimpleInternalClassName(batchPlannerPlan) %>"
				/>

				<liferay-ui:search-container-column-text
					name="user"
					value="<%= batchPlannerPlan.getUserName() %>"
				/>

				<liferay-ui:search-container-column-text
					name="create-date"
					value="<%= dateFormatDate.format(batchPlannerPlan.getCreateDate()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="status"
					value='<%= LanguageUtil.get(request, batchPlannerPlan.isActive() ? "active" : "inactive") %>'
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/batch_planner_plan_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>
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

<%@ include file="/definition/init.jsp" %>

<%
String definitionsNavigation = ParamUtil.getString(request, "definitionsNavigation");

int displayedStatus = WorkflowConstants.STATUS_ANY;

if (StringUtil.equals(definitionsNavigation, "published")) {
	displayedStatus = WorkflowConstants.STATUS_APPROVED;
}
else if (StringUtil.equals(definitionsNavigation, "not-published")) {
	displayedStatus = WorkflowConstants.STATUS_DRAFT;
}

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setParameter(
	"definitionsNavigation", definitionsNavigation
).buildPortletURL();
%>

<clay:management-toolbar
	clearResultsURL="<%= workflowDefinitionDisplayContext.getClearResultsURL(request) %>"
	creationMenu="<%= workflowDefinitionDisplayContext.getCreationMenu(pageContext) %>"
	filterDropdownItems="<%= workflowDefinitionDisplayContext.getFilterOptions(request) %>"
	itemsTotal="<%= workflowDefinitionDisplayContext.getTotalItems(request, renderRequest, displayedStatus) %>"
	searchActionURL="<%= workflowDefinitionDisplayContext.getSearchURL(request) %>"
	searchContainerId="workflowDefinitions"
	searchFormName="fm1"
	selectable="<%= false %>"
	sortingOrder='<%= ParamUtil.getString(request, "orderByType", "asc") %>'
	sortingURL="<%= workflowDefinitionDisplayContext.getSortingURL(request) %>"
/>

<clay:container-fluid
	cssClass="workflow-definition-container"
>
	<liferay-ui:error exception="<%= RequiredWorkflowDefinitionException.class %>">
		<liferay-ui:message arguments="<%= workflowDefinitionDisplayContext.getMessageArguments((RequiredWorkflowDefinitionException)errorException) %>" key="<%= workflowDefinitionDisplayContext.getMessageKey((RequiredWorkflowDefinitionException)errorException) %>" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= IncompleteWorkflowInstancesException.class %>">
		<liferay-ui:message arguments="<%= workflowDefinitionDisplayContext.getMessageArguments((IncompleteWorkflowInstancesException)errorException) %>" key="<%= workflowDefinitionDisplayContext.getMessageKey((IncompleteWorkflowInstancesException)errorException) %>" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-ui:search-container
		emptyResultsMessage="no-workflow-definitions-are-defined"
		id="workflowDefinitions"
		searchContainer="<%= workflowDefinitionDisplayContext.getSearch(request, renderRequest, displayedStatus) %>"
	>

		<%
		request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);
		%>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.workflow.WorkflowDefinition"
			modelVar="workflowDefinition"
		>

			<%
			PortletURL rowURL = PortletURLBuilder.createRenderURL(
				renderResponse
			).setMVCPath(
				"/definition/edit_workflow_definition.jsp"
			).setRedirect(
				currentURL
			).setParameter(
				"name", workflowDefinition.getName()
			).setParameter(
				"version", workflowDefinition.getVersion()
			).buildPortletURL();
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				href="<%= rowURL %>"
				name="title"
				value="<%= workflowDefinitionDisplayContext.getTitle(workflowDefinition) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-300"
				href="<%= rowURL %>"
				name="description"
				value="<%= workflowDefinitionDisplayContext.getDescription(workflowDefinition) %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand-smaller table-cell-ws-nowrap"
				href="<%= rowURL %>"
				name="last-modified"
				userName="<%= workflowDefinitionDisplayContext.getUserName(workflowDefinition) %>"
				value="<%= workflowDefinition.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				path="/definition/workflow_definition_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			resultRowSplitter="<%= new WorkflowDefinitionResultRowSplitter() %>"
			searchContainer="<%= new WorkflowDefinitionSearch(renderRequest, portletURL) %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>
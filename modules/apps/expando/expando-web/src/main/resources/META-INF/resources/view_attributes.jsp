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
String redirect = ParamUtil.getString(request, "redirect");

String modelResource = ParamUtil.getString(request, "modelResource");

String modelResourceName = ResourceActionsUtil.getModelResource(request, modelResource);

ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(company.getCompanyId(), modelResource);

List<String> attributeNames = Collections.list(expandoBridge.getAttributeNames());

ExpandoDisplayContext expandoDisplayContext = new ExpandoDisplayContext(request);

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/view_attributes.jsp"
).setRedirect(
	redirect
).setParameter(
	"modelResource", modelResource
).buildPortletURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(modelResourceName);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "custom-field"), String.valueOf(renderResponse.createRenderURL()));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "view-attributes"), null);
%>

<clay:navigation-bar
	navigationItems='<%= expandoDisplayContext.getNavigationItems("fields") %>'
/>

<clay:management-toolbar
	actionDropdownItems="<%= expandoDisplayContext.getActionDropdownItems() %>"
	additionalProps="<%= expandoDisplayContext.getAdditionalProps() %>"
	creationMenu="<%= expandoDisplayContext.getCreationMenu() %>"
	disabled="<%= attributeNames.size() == 0 %>"
	propsTransformer="js/ExpandoManagementToolbarPropsTransformer"
	searchContainerId="customFields"
	selectable="<%= true %>"
	showCreationMenu="<%= expandoDisplayContext.showCreationMenu() %>"
	showSearch="<%= false %>"
/>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="columnIds" type="hidden" />

	<clay:container-fluid>
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showPortletBreadcrumb="<%= true %>"
		/>
	</clay:container-fluid>

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.format(request, "no-custom-fields-are-defined-for-x", HtmlUtil.escape(modelResourceName), false) %>'
		id="customFields"
		iteratorURL="<%= portletURL %>"
		rowChecker="<%= new CustomFieldChecker(renderRequest, renderResponse) %>"
		total="<%= attributeNames.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= attributeNames %>"
		/>

		<liferay-ui:search-container-row
			className="java.lang.String"
			modelVar="name"
			stringKey="<%= true %>"
		>

			<%
			int type = expandoBridge.getAttributeType(name);

			ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getDefaultTableColumn(company.getCompanyId(), modelResource, name);

			UnicodeProperties typeSettings = expandoColumn.getTypeSettingsProperties();
			%>

			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/edit/expando.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="columnId" value="<%= String.valueOf(expandoColumn.getColumnId()) %>" />
				<portlet:param name="modelResource" value="<%= modelResource %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-row-parameter
				name="expandoColumn"
				value="<%= expandoColumn %>"
			/>

			<liferay-ui:search-container-row-parameter
				name="modelResource"
				value="<%= modelResource %>"
			/>

			<%@ include file="/attribute_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
		/>
	</liferay-ui:search-container>
</aui:form>
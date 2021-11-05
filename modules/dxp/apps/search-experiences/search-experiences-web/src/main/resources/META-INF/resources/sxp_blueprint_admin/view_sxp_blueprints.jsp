<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewSXPBlueprintsDisplayContext viewSXPBlueprintsDisplayContext = (ViewSXPBlueprintsDisplayContext)request.getAttribute(SXPWebKeys.VIEW_SXP_BLUEPRINTS_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteSXPBlueprintURL",
			PortletURLBuilder.createActionURL(
				renderResponse
			).setActionName(
				"/sxp_blueprint_admin/delete_sxp_blueprint"
			).setRedirect(
				currentURL
			).buildString()
		).put(
			"editSXPBlueprintURL",
			PortletURLBuilder.createRenderURL(
				renderResponse
			).setMVCRenderCommandName(
				"/sxp_blueprint_admin/edit_sxp_blueprint"
			).setRedirect(
				currentURL
			).buildString()
		).build()
	%>'
	managementToolbarDisplayContext="<%= (ViewSXPBlueprintsManagementToolbarDisplayContext)request.getAttribute(SXPWebKeys.VIEW_SXP_BLUEPRINTS_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT) %>"
	propsTransformer="sxp_blueprint_admin/js/view_sxp_blueprints/SXPBlueprintEntriesManagementToolbarPropsTransformer"
	searchContainerId="sxpBlueprintEntries"
	supportsBulkActions="<%= true %>"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			cssClass="sxp-blueprints-search-container"
			id="sxpBlueprintEntries"
			searchContainer="<%= viewSXPBlueprintsDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.search.experiences.model.SXPBlueprint"
				keyProperty="sxpBlueprintId"
				modelVar="sxpBlueprint"
			>
				<%@ include file="/sxp_blueprint_admin/sxp_blueprint_search_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= viewSXPBlueprintsDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>
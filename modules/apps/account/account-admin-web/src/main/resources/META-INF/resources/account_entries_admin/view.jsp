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
SearchContainer<AccountEntryDisplay> accountEntryDisplaySearchContainer = AccountEntryDisplaySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);

ViewAccountEntriesManagementToolbarDisplayContext viewAccountEntriesManagementToolbarDisplayContext = new ViewAccountEntriesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountEntryDisplaySearchContainer);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= viewAccountEntriesManagementToolbarDisplayContext %>"
	propsTransformer="account_entries_admin/js/AccountEntriesManagementToolbarPropsTransformer"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="accountEntryIds" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= accountEntryDisplaySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.account.admin.web.internal.display.AccountEntryDisplay"
				keyProperty="accountEntryId"
				modelVar="accountEntryDisplay"
			>

				<%
				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(viewAccountEntriesManagementToolbarDisplayContext.getAvailableActions(accountEntryDisplay))
					).build());
				%>

				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/account_admin/edit_account_entry" />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
				</portlet:renderURL>

				<%
				if (!AccountEntryPermission.contains(permissionChecker, accountEntryDisplay.getAccountEntryId(), ActionKeys.UPDATE)) {
					rowURL = null;
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-small table-title"
					href="<%= rowURL %>"
					name="name"
					value="<%= HtmlUtil.escape(accountEntryDisplay.getName()) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest"
					href="<%= rowURL %>"
					name="id"
					property="accountEntryId"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					href="<%= rowURL %>"
					name="organizations"
					property="organizationNames"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest"
					href="<%= rowURL %>"
					name="type"
					property="type"
					translate="<%= true %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest"
					name="status"
				>
					<clay:label
						displayType="<%= accountEntryDisplay.getStatusLabelStyle() %>"
						label="<%= accountEntryDisplay.getStatusLabel() %>"
					/>
				</liferay-ui:search-container-column-text>

				<c:if test="<%= portletName.equals(AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT) %>">
					<liferay-ui:search-container-column-text
						cssClass="table-column-text-center"
						name="selected"
					>
						<c:if test="<%= accountEntryDisplay.isSelectedAccountEntry(themeDisplay.getScopeGroupId(), user.getUserId()) %>">
							<clay:icon
								symbol="check"
							/>
						</c:if>
					</liferay-ui:search-container-column-text>
				</c:if>

				<liferay-ui:search-container-column-jsp
					path="/account_entries_admin/account_entry_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>
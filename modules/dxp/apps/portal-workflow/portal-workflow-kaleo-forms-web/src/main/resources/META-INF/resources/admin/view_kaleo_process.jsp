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

<%@ include file="/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	redirect = PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCPath(
		"/admin/view.jsp"
	).buildString();
}

KaleoFormsViewRecordsDisplayContext kaleoFormsViewRecordsDisplayContext = kaleoFormsAdminDisplayContext.getKaleoFormsViewRecordsDisplayContext();

KaleoProcess kaleoProcess = kaleoFormsViewRecordsDisplayContext.getKaleoProcess();
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= kaleoFormsViewRecordsDisplayContext.getNavigationItems() %>"
/>

<portlet:actionURL name="/kaleo_forms_admin/delete_record" var="deleteDDLRecordURL">
	<portlet:param name="mvcPath" value="/admin/view_kaleo_process.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcess.getKaleoProcessId()) %>" />
</portlet:actionURL>

<clay:management-toolbar
	actionDropdownItems="<%= kaleoFormsViewRecordsDisplayContext.getActionItemsDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteDDLRecordURL", deleteDDLRecordURL.toString()
		).build()
	%>'
	clearResultsURL="<%= kaleoFormsViewRecordsDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= kaleoFormsViewRecordsDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= kaleoFormsViewRecordsDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= kaleoFormsViewRecordsDisplayContext.getTotalItems() %>"
	propsTransformer="admin/js/KaleoFormsViewRecordsManagementToolbarPropsTransformer"
	searchActionURL="<%= kaleoFormsViewRecordsDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= kaleoFormsViewRecordsDisplayContext.getSearchContainerId() %>"
	searchFormName="fm"
	sortingOrder="<%= kaleoFormsViewRecordsDisplayContext.getOrderByType() %>"
	sortingURL="<%= kaleoFormsViewRecordsDisplayContext.getSortingURL() %>"
/>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "formContainer" %>'
>
	<aui:form
		action='<%=
			PortletURLBuilder.createRenderURL(
				renderResponse
			).setMVCPath(
				"/admin/view_kaleo_process.jsp"
			).setRedirect(
				redirect
			).setParameter(
				"kaleoProcessId", kaleoProcess.getKaleoProcessId()
			).buildString()
		%>'
		method="post"
		name="searchContainerForm"
	>
		<aui:input name="ddlRecordIds" type="hidden" />

		<liferay-ui:search-container
			id="<%= kaleoFormsViewRecordsDisplayContext.getSearchContainerId() %>"
			searchContainer="<%= kaleoFormsViewRecordsDisplayContext.getSearch() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.lists.model.DDLRecord"
				keyProperty="recordId"
				modelVar="record"
			>

				<%
				row.setParameter("kaleoProcessId", String.valueOf(kaleoProcess.getKaleoProcessId()));

				DDLRecordVersion recordVersion = record.getRecordVersion();

				DDMFormValues ddmFormValues = kaleoFormsAdminDisplayContext.getDDMFormValues(recordVersion.getDDMStorageId());

				Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap();

				PortletURL rowURL = PortletURLBuilder.createRenderURL(
					renderResponse
				).setMVCPath(
					"/admin/view_record.jsp"
				).setRedirect(
					currentURL
				).setParameter(
					"ddlRecordId", record.getRecordId()
				).setParameter(
					"kaleoProcessId", kaleoProcess.getKaleoProcessId()
				).setParameter(
					"version", recordVersion.getVersion()
				).buildPortletURL();

				// Columns

				for (DDMFormField ddmFormField : kaleoFormsViewRecordsDisplayContext.getDDMFormFields()) {
					String value = StringPool.BLANK;

					List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(ddmFormField.getName());

					if (ddmFormFieldValues != null) {
						DDMFormFieldValueRenderer ddmFormFieldValueRenderer = DDMFormFieldValueRendererRegistryUtil.getDDMFormFieldValueRenderer(ddmFormField.getType());

						value = ddmFormFieldValueRenderer.render(ddmFormFieldValues, themeDisplay.getLocale());
					}
				%>

					<liferay-ui:search-container-column-text
						name="<%= HtmlUtil.escape(kaleoFormsViewRecordsDisplayContext.getColumnName(ddmFormField)) %>"
						truncate="<%= true %>"
					>
						<span class="lfr-portal-tooltip text-truncate-inline" title="<%= value %>">
							<span class="text-truncate">
								<%= value %>
							</span>
						</span>
					</liferay-ui:search-container-column-text>

				<%
				}
				%>

				<c:if test="<%= KaleoProcessPermission.contains(permissionChecker, kaleoProcess, ActionKeys.SUBMIT) %>">
					<liferay-ui:search-container-column-status
						name="status"
						status="<%= recordVersion.getStatus() %>"
						statusByUserId="<%= recordVersion.getStatusByUserId() %>"
						statusDate="<%= recordVersion.getStatusDate() %>"
					/>

					<liferay-ui:search-container-column-date
						name="modified-date"
						value="<%= record.getModifiedDate() %>"
					/>

					<liferay-ui:search-container-column-text
						name="author"
						value="<%= HtmlUtil.escape(PortalUtil.getUserName(recordVersion)) %>"
					/>
				</c:if>

				<liferay-ui:search-container-column-jsp
					path="/admin/record_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= kaleoFormsViewRecordsDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
				paginate="<%= false %>"
				searchContainer="<%= kaleoFormsViewRecordsDisplayContext.getSearch() %>"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>

<clay:container-fluid>
	<liferay-ui:search-paginator
		searchContainer="<%= kaleoFormsViewRecordsDisplayContext.getSearch() %>"
	/>
</clay:container-fluid>

<%@ include file="/admin/export_kaleo_process.jspf" %>

<aui:script>
	AUI().use('liferay-portlet-dynamic-data-lists');

	Liferay.provide(
		window,
		'<portlet:namespace />openPreviewDialog',
		(content) => {
			var Util = Liferay.Util;

			var dialog = Util.getWindow('<portlet:namespace />previewDialog');

			if (!dialog) {
				dialog = Util.Window.getWindow({
					dialog: {
						bodyContent: content,
					},
					id: '<portlet:namespace />previewDialog',
					title: Liferay.Language.get('preview'),
				});
			}
			else {
				dialog.show();

				dialog.set('bodyContent', content);
			}
		},
		['liferay-util-window']
	);
</aui:script>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(kaleoProcess.getName(locale));

PortalUtil.setPageSubtitle(kaleoProcess.getName(locale), request);
PortalUtil.setPageDescription(kaleoProcess.getDescription(locale), request);

PortalUtil.addPortletBreadcrumbEntry(request, kaleoProcess.getName(locale), currentURL);
%>
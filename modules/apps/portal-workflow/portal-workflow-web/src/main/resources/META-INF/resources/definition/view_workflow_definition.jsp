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
String redirect = ParamUtil.getString(request, "redirect");

WorkflowDefinition workflowDefinition = (WorkflowDefinition)request.getAttribute(WebKeys.WORKFLOW_DEFINITION);

String content = workflowDefinition.getContent();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(workflowDefinition.getTitle(LanguageUtil.getLanguageId(request)));

String state = (String)request.getParameter(WorkflowWebKeys.WORKFLOW_JSP_STATE);

boolean previewBeforeRestore = WorkflowWebKeys.WORKFLOW_PREVIEW_BEFORE_RESTORE_STATE.equals(state);
%>

<liferay-portlet:renderURL var="editWorkflowDefinitionURL">
	<portlet:param name="mvcPath" value="/definition/edit_workflow_definition.jsp" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
	<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
</liferay-portlet:renderURL>

<liferay-frontend:info-bar>
	<clay:container-fluid>
		<c:if test="<%= !previewBeforeRestore %>">
			<div class="info-bar-item">
				<c:choose>
					<c:when test="<%= workflowDefinition.isActive() %>">
						<clay:label
							displayType="info"
							label="published"
							large="<%= true %>"
						/>
					</c:when>
					<c:otherwise>
						<clay:label
							label="not-published"
							large="<%= true %>"
						/>
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>

		<%
		String userName = workflowDefinitionDisplayContext.getUserName(workflowDefinition);
		%>

		<span>
			<c:choose>
				<c:when test="<%= userName == null %>">
					<%= dateFormatTime.format(workflowDefinition.getModifiedDate()) %>
				</c:when>
				<c:when test="<%= previewBeforeRestore %>">
					<liferay-ui:message arguments="<%= new String[] {dateFormatTime.format(workflowDefinition.getModifiedDate()), HtmlUtil.escape(userName)} %>" key="revision-from-x-by-x" translateArguments="<%= false %>" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message arguments="<%= new String[] {dateFormatTime.format(workflowDefinition.getModifiedDate()), HtmlUtil.escape(userName)} %>" key="x,-by-x" translateArguments="<%= false %>" />
				</c:otherwise>
			</c:choose>
		</span>
	</clay:container-fluid>
</liferay-frontend:info-bar>

<div class="<%= previewBeforeRestore ? "" : "container-fluid container-fluid-max-xl container-form-lg" %>" id="container">
	<aui:model-context bean="<%= workflowDefinition %>" model="<%= WorkflowDefinition.class %>" />

	<aui:input name="content" type="hidden" value="<%= content %>" />

	<aui:form method="post" name="form">
		<div class="sheet">
			<aui:fieldset cssClass="workflow-definition-content">
				<clay:col>
					<aui:field-wrapper label="title">
						<liferay-ui:input-localized
							disabled="<%= true %>"
							name=" <%= workflowDefinition.getName() %>_title"
							xml='<%= BeanPropertiesUtil.getString(workflowDefinition, "title") %>'
						/>
					</aui:field-wrapper>
				</clay:col>

				<clay:col
					cssClass="workflow-definition-content-source-wrapper"
					id='<%= liferayPortletResponse.getNamespace() + "contentSourceWrapper" %>'
				>
					<div class="workflow-definition-content-source" id="<portlet:namespace />contentEditor"></div>
				</clay:col>
			</aui:fieldset>

			<c:choose>
				<c:when test="<%= !previewBeforeRestore %>">
					<div class="sheet-footer">
						<aui:button href="<%= editWorkflowDefinitionURL %>" primary="<%= true %>" value="edit" />
					</div>
				</c:when>
			</c:choose>
		</div>
	</aui:form>
</div>

<aui:script use="aui-ace-editor">
	var contentEditor = new A.AceEditor({
		boundingBox: '#<portlet:namespace />contentEditor',
		height: 600,
		mode: 'xml',
		readOnly: 'true',
		tabSize: 4,
		width: '100%',
	}).render();

	var editorContentElement = document.getElementById(
		'<portlet:namespace />content'
	);

	if (editorContentElement) {
		contentEditor.set(
			'value',
			Liferay.Util.formatXML(editorContentElement.value)
		);
	}
</aui:script>
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

String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace", liferayPortletResponse.getNamespace());

long structureVersionId = ParamUtil.getLong(request, "structureVersionId");

DDMStructureVersion structureVersion = DDMStructureVersionServiceUtil.getStructureVersion(structureVersionId);

DDMStructure structure = structureVersion.getStructure();

String script = BeanParamUtil.getString(structureVersion, request, "definition");

JSONArray fieldsJSONArray = DDMUtil.getDDMFormFieldsJSONArray(structureVersion, script);

String fieldsJSONArrayString = StringPool.BLANK;

if (fieldsJSONArray != null) {
	fieldsJSONArrayString = fieldsJSONArray.toString();
}

String title = LanguageUtil.format(request, "x-version-x", new Object[] {structureVersion.getName(locale), structureVersion.getVersion()});

PortletURL backURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/view_structure_history.jsp"
).setRedirect(
	redirect
).setParameter(
	"structureId", structureVersion.getStructureId()
).buildPortletURL();
%>

<clay:container-fluid>
	<c:choose>
		<c:when test="<%= ddmDisplay.isShowBackURLInTitleBar() %>">

			<%
			portletDisplay.setShowBackIcon(true);
			portletDisplay.setURLBack(backURL.toString());

			renderResponse.setTitle(title);
			%>

		</c:when>
		<c:otherwise>
			<liferay-ui:header
				backURL="<%= backURL.toString() %>"
				localizeTitle="<%= false %>"
				title="<%= title %>"
			/>
		</c:otherwise>
	</c:choose>

	<aui:model-context bean="<%= structureVersion %>" model="<%= DDMStructureVersion.class %>" />

	<aui:input disabled="<%= true %>" name="name" />

	<aui:input disabled="<%= true %>" name="description" />

	<%@ include file="/form_builder.jspf" %>

	<aui:button-row>
		<aui:button href="<%= backURL.toString() %>" type="cancel" />
	</aui:button-row>
</clay:container-fluid>
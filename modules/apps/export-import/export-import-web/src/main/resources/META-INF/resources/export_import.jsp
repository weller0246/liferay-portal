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
String tabs2 = ParamUtil.getString(request, "tabs2", "export");

String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/export_import/export_import"
).setRedirect(
	ParamUtil.getString(request, "redirect")
).setPortletResource(
	portletResource
).setParameter(
	"returnToFullPageURL", returnToFullPageURL
).buildPortletURL();
%>

<c:choose>
	<c:when test="<%= !GroupPermissionUtil.contains(permissionChecker, themeDisplay.getScopeGroup(), ActionKeys.MANAGE_STAGING) %>">
		<div class="alert alert-info">
			<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />
		</div>
	</c:when>
	<c:otherwise>
		<clay:navigation-bar
			navigationItems='<%=
				new JSPNavigationItemList(pageContext) {
					{
						portletURL.setParameter("tabs2", "export");

						add(
							navigationItem -> {
								navigationItem.setActive(tabs2.equals("export"));
								navigationItem.setHref(portletURL.toString());
								navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "export"));
							});

						portletURL.setParameter("tabs2", "import");

						add(
							navigationItem -> {
								navigationItem.setActive(tabs2.equals("import"));
								navigationItem.setHref(portletURL.toString());
								navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "import"));
							});
					}
				}
			%>'
		/>

		<div class="portlet-export-import-container" id="<portlet:namespace />exportImportPortletContainer">
			<liferay-util:include page="/export_import_error.jsp" servletContext="<%= application %>" />

			<c:choose>
				<c:when test='<%= tabs2.equals("export") %>'>
					<liferay-util:include page="/export_portlet.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs2.equals("import") %>'>
					<liferay-util:include page="/import_portlet.jsp" servletContext="<%= application %>" />
				</c:when>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose>
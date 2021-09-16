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

<%@ include file="/portlet/init.jsp" %>

<%
String productMenuState = SessionClicks.get(request, "com.liferay.product.navigation.product.menu.web_productMenuState", "closed");
String pagesTreeState = SessionClicks.get(request, "com.liferay.product.navigation.product.menu.web_pagesTreeState", "closed");

ApplicationsMenuInstanceConfiguration applicationsMenuInstanceConfiguration = ConfigurationProviderUtil.getCompanyConfiguration(ApplicationsMenuInstanceConfiguration.class, themeDisplay.getCompanyId());
%>

<div class="lfr-product-menu-sidebar <%= applicationsMenuInstanceConfiguration.enableApplicationsMenu() ? "lfr-applications-menu" : "" %>" id="productMenuSidebar">
	<c:if test="<%= !applicationsMenuInstanceConfiguration.enableApplicationsMenu() %>">
		<div class="sidebar-header">
			<h1 class="sr-only"><liferay-ui:message key="product-admin-menu" /></h1>

			<clay:content-row>
				<clay:content-col
					expand="<%= true %>"
				>
					<a href="<%= PortalUtil.addPreservedParameters(themeDisplay, themeDisplay.getURLPortal(), false, true) %>">
						<span class="company-details text-truncate">
							<img alt="" class="company-logo" src="<%= themeDisplay.getPathImage() + "/company_logo?img_id=" + company.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(company.getLogoId()) %>" />

							<span class="company-name"><%= HtmlUtil.escape(company.getName()) %></span>
						</span>
					</a>
				</clay:content-col>

				<clay:content-col>
					<aui:icon cssClass="d-inline-block d-md-none icon-monospaced sidenav-close" image="times" markupView="lexicon" url="javascript:;" />
				</clay:content-col>
			</clay:content-row>
		</div>
	</c:if>

	<div class="sidebar-body">
		<c:choose>
			<c:when test='<%= !productMenuDisplayContext.isShowLayoutsTree() || (Objects.equals(productMenuState, "open") && !Objects.equals(pagesTreeState, "open")) %>'>
				<liferay-util:include page="/portlet/product_menu.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= Objects.equals(productMenuState, "open") && Objects.equals(pagesTreeState, "open") %>'>
				<div class="pages-tree">
					<liferay-util:include page="/portlet/pages_tree.jsp" servletContext="<%= application %>">
						<liferay-util:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
					</liferay-util:include>
				</div>
			</c:when>
		</c:choose>
	</div>
</div>
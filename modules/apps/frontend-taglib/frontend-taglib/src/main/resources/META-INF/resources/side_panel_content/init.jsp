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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />

<%
String screenNavigatorKey = (String)request.getAttribute("liferay-frontend:side-panel-content:screenNavigatorKey");
Object screenNavigatorModelBean = (Object)request.getAttribute("liferay-frontend:side-panel-content:screenNavigatorModelBean");
PortletURL screenNavigatorPortletURL = (PortletURL)request.getAttribute("liferay-frontend:side-panel-content:screenNavigatorPortletURL");
String title = (String)request.getAttribute("liferay-frontend:side-panel-content:title");
%>
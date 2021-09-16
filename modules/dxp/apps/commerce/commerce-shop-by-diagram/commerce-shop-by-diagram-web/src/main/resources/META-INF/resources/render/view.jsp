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
CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);

CSDiagramCPTypeDisplayContext csDiagramCPTypeDisplayContext = (CSDiagramCPTypeDisplayContext)request.getAttribute(CSDiagramWebKeys.CS_DIAGRAM_CP_TYPE_DISPLAY_CONTEXT);
%>

<div id="shop-by-diagram" />

<react:component
	module="js/Diagram"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"enablePanZoom", true
		).put(
			"enableResetZoom", true
		).put(
			"imageSettings",
			JSONUtil.put(
				"height", "500px"
			).put(
				"width", "100%"
			)
		).put(
			"imageURL", csDiagramCPTypeDisplayContext.getImageURL(cpCatalogEntry.getCPDefinitionId())
		).put(
			"isAdmin", false
		).put(
			"pinsEndpoint", "/o/headless-commerce-admin-catalog/v1.0/"
		).put(
			"productId", cpCatalogEntry.getCProductId()
		).put(
			"spritemap", themeDisplay.getPathThemeImages() + "/clay/icons.svg"
		).build()
	%>'
/>
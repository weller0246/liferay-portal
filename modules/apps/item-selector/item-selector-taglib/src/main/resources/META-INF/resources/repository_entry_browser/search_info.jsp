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

<%@ include file="/repository_entry_browser/init.jsp" %>

<div class="search-info">

	<%
	long folderId = ParamUtil.getLong(request, "folderId");
	String keywords = ParamUtil.getString(request, "keywords");

	boolean searchEverywhere = true;

	long searchFolderId = ParamUtil.getLong(request, "searchFolderId");

	boolean showRerunSearchButton = true;

	if (folderId > DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		searchEverywhere = false;
	}
	else {
		folderId = searchFolderId;
	}

	if ((folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (searchFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
		showRerunSearchButton = false;
	}
	%>

	<c:if test="<%= showRerunSearchButton %>">

		<%
		PortletURL portletURL = (PortletURL)request.getAttribute("liferay-item-selector:repository-entry-browser:portletURL");

		PortletURL searchEverywhereURL = PortletURLBuilder.create(
			PortletURLUtil.clone(portletURL, liferayPortletResponse)
		).setKeywords(
			keywords
		).setParameter(
			"folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID
		).setParameter(
			"searchFolderId", folderId
		).buildPortletURL();
		%>

		<liferay-util:whitespace-remover>
			<liferay-ui:message key="search" />

			<clay:link
				cssClass='<%= searchEverywhere ? "active" : "" %>'
				displayType="secondary"
				href="<%= searchEverywhereURL.toString() %>"
				label="everywhere"
				small="<%= true %>"
				type="button"
			/>

			<%
			Folder folder = DLAppServiceUtil.getFolder(folderId);
			%>

			<clay:link
				cssClass='<%= !searchEverywhere ? "active" : "" %>'
				displayType="secondary"
				href='<%=
					PortletURLBuilder.create(
						PortletURLUtil.clone(searchEverywhereURL, liferayPortletResponse)
					).setParameter(
						"folderId", folderId
					).buildString()
				%>'
				icon="folder"
				label="<%= folder.getName() %>"
				type="button"
			/>
		</liferay-util:whitespace-remover>
	</c:if>
</div>
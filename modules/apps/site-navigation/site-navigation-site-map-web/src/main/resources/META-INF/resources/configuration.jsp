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
List<LayoutDescription> layoutDescriptions = siteNavigationSiteMapDisplayContext.getLayoutDescriptions();
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<div class="display-template">
					<liferay-template:template-selector
						className="<%= LayoutSet.class.getName() %>"
						displayStyle="<%= siteNavigationSiteMapPortletInstanceConfiguration.displayStyle() %>"
						displayStyleGroupId="<%= siteNavigationSiteMapDisplayContext.getDisplayStyleGroupId() %>"
						refreshURL="<%= configurationRenderURL %>"
						showEmptyOption="<%= true %>"
					/>
				</div>

				<aui:select label="root-layout" name="preferences--rootLayoutUuid--">
					<aui:option value="" />

					<%
					for (LayoutDescription layoutDescription : layoutDescriptions) {
						Layout layoutDescriptionLayout = LayoutLocalServiceUtil.fetchLayout(layoutDescription.getPlid());
					%>

						<c:if test="<%= layoutDescriptionLayout != null %>">
							<aui:option label="<%= layoutDescription.getDisplayName() %>" selected="<%= Objects.equals(layoutDescriptionLayout.getUuid(), siteNavigationSiteMapPortletInstanceConfiguration.rootLayoutUuid()) %>" value="<%= layoutDescriptionLayout.getUuid() %>" />
						</c:if>

					<%
					}
					%>

				</aui:select>

				<aui:select name="preferences--displayDepth--">
					<aui:option label="unlimited" value="0" />

					<%
					for (int i = 1; i <= 20; i++) {
					%>

						<aui:option label="<%= i %>" selected="<%= siteNavigationSiteMapPortletInstanceConfiguration.displayDepth() == i %>" />

					<%
					}
					%>

				</aui:select>

				<div class="<%= Validator.isNotNull(siteNavigationSiteMapPortletInstanceConfiguration.rootLayoutUuid()) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />includeRootInTreeContainer">
					<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="preferences--includeRootInTree--" type="toggle-switch" value="<%= siteNavigationSiteMapDisplayContext.isIncludeRootInTree() %>" />
				</div>

				<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="preferences--showCurrentPage--" type="toggle-switch" value="<%= siteNavigationSiteMapPortletInstanceConfiguration.showCurrentPage() %>" />

				<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="preferences--useHtmlTitle--" type="toggle-switch" value="<%= siteNavigationSiteMapPortletInstanceConfiguration.useHtmlTitle() %>" />

				<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="preferences--showHiddenPages--" type="toggle-switch" value="<%= siteNavigationSiteMapPortletInstanceConfiguration.showHiddenPages() %>" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />isVisible(currentValue, value) {
		return currentValue != '';
	}

	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />rootLayoutUuid',
		<portlet:namespace />isVisible,
		'<portlet:namespace />includeRootInTreeContainer'
	);
</aui:script>
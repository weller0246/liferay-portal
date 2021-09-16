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
String className = ParamUtil.getString(request, "className");
long classPK = ParamUtil.getLong(request, "classPK");
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectRuleGroup");

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/select_rule_group.jsp"
).setParameter(
	"eventName", eventName
).setParameter(
	"groupId", groupId
).buildPortletURL();

RuleGroupSearch ruleGroupSearch = new RuleGroupSearch(liferayPortletRequest, portletURL);

RuleGroupDisplayTerms displayTerms = (RuleGroupDisplayTerms)ruleGroupSearch.getDisplayTerms();
RuleGroupSearchTerms searchTerms = (RuleGroupSearchTerms)ruleGroupSearch.getSearchTerms();

if (displayTerms.getGroupId() == 0) {
	displayTerms.setGroupId(groupId);
	searchTerms.setGroupId(groupId);
}

LinkedHashMap<String, Object> params = LinkedHashMapBuilder.<String, Object>put(
	"includeGlobalScope", Boolean.TRUE
).build();

int mdrRuleGroupsCount = MDRRuleGroupLocalServiceUtil.searchByKeywordsCount(searchTerms.getGroupId(), searchTerms.getKeywords(), params, searchTerms.isAndOperator());

ruleGroupSearch.setTotal(mdrRuleGroupsCount);

List<MDRRuleGroup> mdrRuleGroups = MDRRuleGroupLocalServiceUtil.searchByKeywords(searchTerms.getGroupId(), searchTerms.getKeywords(), params, searchTerms.isAndOperator(), ruleGroupSearch.getStart(), ruleGroupSearch.getEnd());

ruleGroupSearch.setResults(mdrRuleGroups);
%>

<c:if test="<%= (mdrRuleGroupsCount > 0) || searchTerms.isSearch() %>">
	<liferay-frontend:management-bar>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			/>

			<li>
				<aui:form action="<%= portletURL.toString() %>" name="searchFm">
					<liferay-ui:input-search
						markupView="lexicon"
					/>
				</aui:form>
			</li>
		</liferay-frontend:management-bar-filters>
	</liferay-frontend:management-bar>
</c:if>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="selectRuleGroupFm">
	<liferay-ui:search-container
		searchContainer="<%= ruleGroupSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.mobile.device.rules.model.MDRRuleGroup"
			escapedModel="<%= true %>"
			keyProperty="ruleGroupId"
			modelVar="ruleGroup"
		>

			<%
			MDRRuleGroupInstance ruleGroupInstance = MDRRuleGroupInstanceLocalServiceUtil.fetchRuleGroupInstance(className, classPK, ruleGroup.getRuleGroupId());

			Map<String, Object> data = HashMapBuilder.<String, Object>put(
				"rulegroupid", ruleGroup.getRuleGroupId()
			).put(
				"rulegroupname", ruleGroup.getName()
			).build();
			%>

			<c:choose>
				<c:when test='<%= displayStyle.equals("descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="mobile-portrait"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<c:choose>
								<c:when test="<%= ruleGroupInstance == null %>">
									<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
										<%= ruleGroup.getName(locale) %>
									</aui:a>
								</c:when>
								<c:otherwise>
									<%= ruleGroup.getName(locale) %>
								</c:otherwise>
							</c:choose>
						</h5>

						<h6 class="text-default">
							<%= ruleGroup.getDescription(locale) %>
						</h6>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>
					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							cssClass='<%= (ruleGroupInstance == null) ? "selector-button" : StringPool.BLANK %>'
							data="<%= data %>"
							icon="mobile-portrait"
							subtitle="<%= ruleGroup.getDescription(locale) %>"
							title="<%= ruleGroup.getName(locale) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("list") %>'>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="name"
					>
						<c:choose>
							<c:when test="<%= ruleGroupInstance == null %>">
								<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
									<%= ruleGroup.getName(locale) %>
								</aui:a>
							</c:when>
							<c:otherwise>
								<%= ruleGroup.getName(locale) %>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="description"
						value="<%= ruleGroup.getDescription(locale) %>"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
			type="more"
		/>
	</liferay-ui:search-container>
</aui:form>
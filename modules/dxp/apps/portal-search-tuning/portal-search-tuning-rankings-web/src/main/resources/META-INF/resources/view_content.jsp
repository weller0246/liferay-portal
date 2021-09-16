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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingResultContentDisplayBuilder" %><%@
page import="com.liferay.portal.search.tuning.rankings.web.internal.display.context.RankingResultContentDisplayContext" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
RankingResultContentDisplayBuilder rankingResultContentDisplayBuilder = new RankingResultContentDisplayBuilder();

rankingResultContentDisplayBuilder.setAssetEntryId(ParamUtil.getLong(request, "assetEntryId"));
rankingResultContentDisplayBuilder.setEntryClassName(ParamUtil.getString(request, "entryClassName"));
rankingResultContentDisplayBuilder.setEntryClassPK(ParamUtil.getLong(request, "entryClassPK"));
rankingResultContentDisplayBuilder.setLocale(locale);
rankingResultContentDisplayBuilder.setPermissionChecker(permissionChecker);
rankingResultContentDisplayBuilder.setPortal(PortalUtil.getPortal());
rankingResultContentDisplayBuilder.setRenderRequest(renderRequest);
rankingResultContentDisplayBuilder.setRenderResponse(renderResponse);

RankingResultContentDisplayContext rankingResultContentDisplayContext = rankingResultContentDisplayBuilder.build();
%>

<clay:container-fluid
	cssClass="container-no-gutters-sm-down container-view"
>
	<c:choose>
		<c:when test="<%= rankingResultContentDisplayContext.isVisible() %>">
			<clay:sheet
				cssClass="result-rankings-view-content-container"
			>
				<clay:content-row>
					<clay:content-col
						expand="<%= true %>"
					>
						<div class="sheet-title">
							<%= rankingResultContentDisplayContext.getHeaderTitle() %>
						</div>
					</clay:content-col>

					<clay:content-col
						cssClass="visible-interaction"
					>
						<c:if test="<%= rankingResultContentDisplayContext.hasEditPermission() %>">
							<div class="asset-actions c-mt-3">
								<liferay-ui:icon
									cssClass="visible-interaction"
									icon="pencil"
									label="<%= false %>"
									markupView="lexicon"
									message='<%= LanguageUtil.format(request, "edit-x-x", new Object[] {"hide-accessible", HtmlUtil.escape(rankingResultContentDisplayContext.getIconEditTarget())}, false) %>'
									method="get"
									url="<%= rankingResultContentDisplayContext.getIconURLString() %>"
								/>
							</div>
						</c:if>
					</clay:content-col>
				</clay:content-row>

				<liferay-asset:asset-display
					assetEntry="<%= rankingResultContentDisplayContext.getAssetEntry() %>"
					assetRenderer="<%= rankingResultContentDisplayContext.getAssetRenderer() %>"
					assetRendererFactory="<%= rankingResultContentDisplayContext.getAssetRendererFactory() %>"
				/>
			</clay:sheet>
		</c:when>
		<c:otherwise>
			<div class="alert alert-danger">
				<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />
			</div>
		</c:otherwise>
	</c:choose>
</clay:container-fluid>
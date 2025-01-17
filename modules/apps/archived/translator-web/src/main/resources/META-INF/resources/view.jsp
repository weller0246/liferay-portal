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
Translation translation = (Translation)request.getAttribute(TranslatorConfiguration.TRANSLATOR_TRANSLATION);

Map<String, String> languageIdsMap = TranslatorUtil.getLanguageIdsMap(locale, translatorConfiguration);

if (translation == null) {
	String translationId = translatorConfiguration.translationId();

	String[] fromAndToLanguageIds = TranslatorUtil.getFromAndToLanguageIds(translationId, languageIdsMap);

	if (fromAndToLanguageIds != null) {
		String fromLanguageId = fromAndToLanguageIds[0];
		String toLanguageId = fromAndToLanguageIds[1];

		translation = new Translation(fromLanguageId, toLanguageId, StringPool.BLANK, StringPool.BLANK);
	}
}
%>

<c:choose>
	<c:when test="<%= translation == null %>">
		<div class="alert alert-danger">
			<liferay-ui:message key="please-configure-valid-default-languages" />
		</div>
	</c:when>
	<c:otherwise>
		<portlet:actionURL var="portletURL" />

		<aui:form accept-charset="UTF-8" action="<%= portletURL %>" method="post" name="fm">
			<liferay-ui:error exception="<%= MicrosoftTranslatorException.class %>">

				<%
				MicrosoftTranslatorException mte = (MicrosoftTranslatorException)errorException;
				%>

				<liferay-ui:message key="<%= mte.getMessage() %>" />
			</liferay-ui:error>

			<c:if test="<%= Validator.isNotNull(translation.getToText()) %>">
				<%= HtmlUtil.escape(translation.getToText()) %>
			</c:if>

			<aui:fieldset>
				<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" cssClass="lfr-textarea-container" label="" name="text" type="textarea" value="<%= translation.getFromText() %>" wrap="soft" />

				<aui:select label="language-from" name="fromLanguageId">

					<%
					for (Map.Entry<String, String> entry : languageIdsMap.entrySet()) {
						String languageId = entry.getKey();
					%>

						<aui:option label="<%= entry.getValue() %>" selected="<%= languageId.equals(translation.getFromLanguageId()) %>" value="<%= languageId %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="language-to" name="toLanguageId">

					<%
					for (Map.Entry<String, String> entry : languageIdsMap.entrySet()) {
						String languageId = entry.getKey();
					%>

						<aui:option label="<%= entry.getValue() %>" selected="<%= languageId.equals(translation.getToLanguageId()) %>" value="<%= languageId %>" />

					<%
					}
					%>

				</aui:select>
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" value="translate" />
			</aui:button-row>
		</aui:form>
	</c:otherwise>
</c:choose>
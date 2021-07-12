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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.dynamic.data.mapping.constants.DDMPortletKeys" %><%@
page import="com.liferay.dynamic.data.mapping.exception.FormInstanceNameException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.FormInstanceSettingsRedirectURLException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StorageException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StructureDefinitionException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StructureLayoutException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StructureNameException" %><%@
page import="com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants" %><%@
page import="com.liferay.dynamic.data.mapping.form.web.internal.FormInstanceFieldSettingsException" %><%@
page import="com.liferay.dynamic.data.mapping.form.web.internal.display.context.DDMFormAdminDisplayContext" %><%@
page import="com.liferay.dynamic.data.mapping.form.web.internal.display.context.DDMFormViewFormInstanceRecordsDisplayContext" %><%@
page import="com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.FieldSetPermissionCheckerHelper" %><%@
page import="com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.FormInstancePermissionCheckerHelper" %><%@
page import="com.liferay.dynamic.data.mapping.form.web.internal.search.DDMFormInstanceRowChecker" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMForm" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMFormField" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMFormInstance" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructure" %><%@
page import="com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue" %><%@
page import="com.liferay.dynamic.data.mapping.storage.DDMFormValues" %><%@
page import="com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException" %><%@
page import="com.liferay.dynamic.data.mapping.validator.DDMFormValidationException" %><%@
page import="com.liferay.petra.portlet.url.builder.PortletURLBuilder" %><%@
page import="com.liferay.petra.string.StringBundler" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker" %><%@
page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.taglib.search.DateSearchEntry" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Map.Entry" %><%@
page import="java.util.Objects" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
DDMFormAdminDisplayContext ddmFormAdminDisplayContext = (DDMFormAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String dataProviderInstanceParameterSettingsURL = ddmFormAdminDisplayContext.getDataProviderInstanceParameterSettingsURL();
String dataProviderInstancesURL = ddmFormAdminDisplayContext.getDataProviderInstancesURL();
String displayStyle = ddmFormAdminDisplayContext.getDisplayStyle();
JSONObject functionsMetadataJSONObject = ddmFormAdminDisplayContext.getFunctionsMetadataJSONObject();
String functionsURL = ddmFormAdminDisplayContext.getFunctionsURL();
String mainRequire = ddmFormAdminDisplayContext.getMainRequire();
String rolesURL = ddmFormAdminDisplayContext.getRolesURL();
JSONArray ddmFormRulesJSONArray = ddmFormAdminDisplayContext.getDDMFormRulesJSONArray();
JSONObject formBuilderContextJSONObject = ddmFormAdminDisplayContext.getFormBuilderContextJSONObject();
%>

<%@ include file="/admin/init-ext.jsp" %>

<aui:script>
	Liferay.namespace('Forms').portletNamespace = '<portlet:namespace />';
</aui:script>
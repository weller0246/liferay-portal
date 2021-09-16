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
taglib uri="http://liferay.com/tld/learn" prefix="liferay-learn" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.configuration.admin.category.ConfigurationCategory" %><%@
page import="com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys" %><%@
page import="com.liferay.configuration.admin.display.ConfigurationFormRenderer" %><%@
page import="com.liferay.configuration.admin.display.ConfigurationScreen" %><%@
page import="com.liferay.configuration.admin.menu.ConfigurationMenuItem" %><%@
page import="com.liferay.configuration.admin.web.internal.constants.ConfigurationAdminWebKeys" %><%@
page import="com.liferay.configuration.admin.web.internal.display.ConfigurationCategoryDisplay" %><%@
page import="com.liferay.configuration.admin.web.internal.display.ConfigurationCategoryMenuDisplay" %><%@
page import="com.liferay.configuration.admin.web.internal.display.ConfigurationCategorySectionDisplay" %><%@
page import="com.liferay.configuration.admin.web.internal.display.ConfigurationEntry" %><%@
page import="com.liferay.configuration.admin.web.internal.display.ConfigurationModelConfigurationEntry" %><%@
page import="com.liferay.configuration.admin.web.internal.display.ConfigurationScopeDisplay" %><%@
page import="com.liferay.configuration.admin.web.internal.display.context.ConfigurationScopeDisplayContext" %><%@
page import="com.liferay.configuration.admin.web.internal.display.context.ConfigurationScopeDisplayContextFactory" %><%@
page import="com.liferay.configuration.admin.web.internal.model.ConfigurationModel" %><%@
page import="com.liferay.configuration.admin.web.internal.util.ConfigurationCategoryUtil" %><%@
page import="com.liferay.configuration.admin.web.internal.util.ConfigurationEntryIterator" %><%@
page import="com.liferay.configuration.admin.web.internal.util.ConfigurationEntryRetriever" %><%@
page import="com.liferay.configuration.admin.web.internal.util.ConfigurationModelIterator" %><%@
page import="com.liferay.configuration.admin.web.internal.util.ResourceBundleLoaderProvider" %><%@
page import="com.liferay.petra.portlet.url.builder.PortletURLBuilder" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition" %><%@
page import="com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.taglib.servlet.PipingServletResponseFactory" %>

<%@ page import="java.util.List" %><%@
page import="java.util.ResourceBundle" %>

<%@ page import="javax.portlet.PortletURL" %>

<%@ page import="org.osgi.service.cm.Configuration" %><%@
page import="org.osgi.service.metatype.AttributeDefinition" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />
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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPDefineObjectsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _formatDefineObjects(content);

		_checkDefineObjectsVariables(fileName, absolutePath, content);

		return content;
	}

	private void _checkDefineObjectsVariables(
		String fileName, String absolutePath, String content) {

		for (String[] defineObject : _LIFERAY_THEME_DEFINE_OBJECTS) {
			_checkDefineObjectsVariables(
				fileName, content, defineObject[0], defineObject[1],
				defineObject[2], "liferay-theme");
		}

		for (String[] defineObject : _PORTLET_DEFINE_OBJECTS) {
			_checkDefineObjectsVariables(
				fileName, content, defineObject[0], defineObject[1],
				defineObject[2], "portlet");
		}

		if (!isPortalSource() && !isSubrepository()) {
			return;
		}

		for (String directoryName : getPluginsInsideModulesDirectoryNames()) {
			if (absolutePath.contains(directoryName)) {
				return;
			}
		}

		for (String[] defineObject : _LIFERAY_FRONTEND_DEFINE_OBJECTS) {
			_checkDefineObjectsVariables(
				fileName, content, defineObject[0], defineObject[1],
				defineObject[2], "liferay-frontend");
		}
	}

	private void _checkDefineObjectsVariables(
		String fileName, String content, String objectType, String variableName,
		String value, String tag) {

		int x = -1;

		while (true) {
			x = content.indexOf(
				StringBundler.concat(
					objectType, " ", variableName, " = ", value, ";"),
				x + 1);

			if (x == -1) {
				return;
			}

			int y = content.lastIndexOf("<%", x);

			if ((y == -1) ||
				(getLevel(content.substring(y, x), "{", "}") > 0)) {

				continue;
			}

			addMessage(
				fileName, "Use '" + tag + ":defineObjects' or rename var",
				getLineNumber(content, x));
		}
	}

	private String _formatDefineObjects(String content) {
		Matcher matcher = _missingEmptyLineBetweenDefineOjbectsPattern.matcher(
			content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start());
		}

		String previousDefineObjectsTag = null;

		matcher = _defineObjectsPattern.matcher(content);

		while (matcher.find()) {
			String defineObjectsTag = matcher.group(1);

			if (Validator.isNotNull(previousDefineObjectsTag) &&
				(previousDefineObjectsTag.compareTo(defineObjectsTag) > 0)) {

				content = StringUtil.replaceFirst(
					content, previousDefineObjectsTag, defineObjectsTag);
				content = StringUtil.replaceLast(
					content, defineObjectsTag, previousDefineObjectsTag);

				return content;
			}

			previousDefineObjectsTag = defineObjectsTag;
		}

		return content;
	}

	private static final String[][] _LIFERAY_FRONTEND_DEFINE_OBJECTS = {
		{"String", "currentURL", "currentURLObj.toString()"},
		{
			"PortletURL", "currentURLObj",
			"PortletURLUtil.getCurrent(liferayPortletRequest, " +
				"liferayPortletResponse)"
		},
		{
			"ResourceBundle", "resourceBundle",
			"ResourceBundleUtil.getBundle(\"content.Language\", locale, " +
				"getClass()"
		},
		{"WindowState", "windowState", "liferayPortletRequest.getWindowState()"}
	};

	private static final String[][] _LIFERAY_THEME_DEFINE_OBJECTS = {
		{"ColorScheme", "colorScheme", "themeDisplay.getColorScheme()"},
		{"Company", "company", "themeDisplay.getCompany()"},
		{"Contact", "contact", "themeDisplay.getContact()"},
		{"Layout", "layout", "themeDisplay.getLayout()"},
		{"List<Layout>", "layouts", "themeDisplay.getLayouts()"},
		{
			"LayoutTypePortlet", "layoutTypePortlet",
			"themeDisplay.getLayoutTypePortlet()"
		},
		{"Locale", "locale", "themeDisplay.getLocale()"},
		{
			"PermissionChecker", "permissionChecker",
			"themeDisplay.getPermissionChecker()"
		},
		{"long", "plid", "themeDisplay.getPlid()"},
		{
			"PortletDisplay", "portletDisplay",
			"themeDisplay.getPortletDisplay()"
		},
		{"User", "realUser", "themeDisplay.getRealUser()"},
		{"long", "scopeGroupId", "themeDisplay.getScopeGroupId()"},
		{"Theme", "theme", "themeDisplay.getTheme()"},
		{
			"ThemeDisplay", "themeDisplay",
			"(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY)"
		},
		{"TimeZone", "timeZone", "themeDisplay.getTimeZone()"},
		{"User", "user", "themeDisplay.getUser()"},
		{"long", "portletGroupId", "themeDisplay.getScopeGroupId()"}
	};

	private static final String[][] _PORTLET_DEFINE_OBJECTS = {
		{
			"PortletConfig", "portletConfig",
			"(PortletConfig)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_CONFIG)"
		},
		{"String", "portletName", "portletConfig.getPortletName()"},
		{
			"LiferayPortletRequest", "liferayPortletRequest",
			"PortalUtil.getLiferayPortletRequest(portletRequest)"
		},
		{
			"PortletRequest", "actionRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		{
			"PortletRequest", "eventRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		{
			"PortletRequest", "renderRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		{
			"PortletRequest", "resourceRequest",
			"(PortletRequest)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_REQUEST)"
		},
		{
			"PortletPreferences", "portletPreferences",
			"portletRequest.getPreferences()"
		},
		{
			"Map<String, String[]>", "portletPreferencesValues",
			"portletPreferences.getMap()"
		},
		{
			"PortletSession", "portletSession",
			"portletRequest.getPortletSession()"
		},
		{
			"Map<String, Object>", "portletSessionScope",
			"portletSession.getAttributeMap()"
		},
		{
			"LiferayPortletResponse", "liferayPortletResponse",
			"PortalUtil.getLiferayPortletResponse(portletResponse)"
		},
		{
			"PortletResponse", "actionResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		{
			"PortletResponse", "eventResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		{
			"PortletResponse", "renderResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		},
		{
			"PortletResponse", "resourceResponse",
			"(PortletResponse)request.getAttribute(JavaConstants." +
				"JAVAX_PORTLET_RESPONSE)"
		}
	};

	private static final Pattern _defineObjectsPattern = Pattern.compile(
		"\n\t*(<.*:defineObjects />)(\n|$)");
	private static final Pattern _missingEmptyLineBetweenDefineOjbectsPattern =
		Pattern.compile("<.*:defineObjects />\n<.*:defineObjects />\n");

}
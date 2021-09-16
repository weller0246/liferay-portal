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

package com.liferay.mentions.web.internal.editor.configuration;

import com.liferay.mentions.constants.MentionsPortletKeys;
import com.liferay.mentions.matcher.MentionsMatcherUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class BaseMentionsEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		PortletURL portletURL = getPortletURL(
			themeDisplay, requestBackedPortletURLFactory);

		String source =
			portletURL.toString() + "&" +
				PortalUtil.getPortletNamespace(MentionsPortletKeys.MENTIONS);

		String tplResults = StringBundler.concat(
			"<div class=\"p-1 autofit-row autofit-row-center\">",
			"<div class=\"autofit-col inline-item-before\">{portraitHTML}",
			"</div><div class=\"autofit-col autofit-col-expand\">",
			"<strong class=\"text-truncate\">{fullName}</strong>",
			"<div class=\"autofit-col-expand\">",
			"<small class=\"text-truncate\">@{screenName}</small></div></div>",
			"</div>");

		jsonObject.put(
			"autocomplete",
			JSONUtil.put(
				"requestTemplate", "query={query}"
			).put(
				"trigger",
				JSONUtil.put(
					JSONUtil.put(
						"regExp",
						StringBundler.concat(
							"(?:\\strigger|^trigger)(",
							MentionsMatcherUtil.
								getScreenNameRegularExpression(),
							")")
					).put(
						"resultFilters",
						"function(query, results) {return results;}"
					).put(
						"resultTextLocator", "screenName"
					).put(
						"source", source
					).put(
						"term", "@"
					).put(
						"tplReplace", "{mention}"
					).put(
						"tplResults", tplResults
					))
			));

		String extraPlugins = jsonObject.getString("extraPlugins");

		if (Validator.isNotNull(extraPlugins)) {
			extraPlugins += ",autocomplete";
		}
		else {
			extraPlugins =
				"autocomplete,ae_placeholder,ae_selectionregion,ae_uicore";
		}

		jsonObject.put("extraPlugins", extraPlugins);
	}

	protected PortletURL getPortletURL(
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		return requestBackedPortletURLFactory.createResourceURL(
			MentionsPortletKeys.MENTIONS);
	}

}
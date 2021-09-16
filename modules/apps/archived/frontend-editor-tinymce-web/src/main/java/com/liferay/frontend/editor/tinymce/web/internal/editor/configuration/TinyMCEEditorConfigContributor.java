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

package com.liferay.frontend.editor.tinymce.web.internal.editor.configuration;

import com.liferay.frontend.editor.tinymce.web.internal.constants.TinyMCEEditorConstants;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	property = "editor.name=tinymce", service = EditorConfigContributor.class
)
public class TinyMCEEditorConfigContributor
	extends BaseTinyMCEEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		jsonObject.put(
			"mode", "exact"
		).put(
			"plugins", getPluginsJSONArray(inputEditorTaglibAttributes)
		).put(
			"style_formats", getStyleFormatsJSONArray(themeDisplay.getLocale())
		).put(
			"toolbar",
			getToolbarJSONArray(inputEditorTaglibAttributes, themeDisplay)
		);
	}

	@Override
	protected ItemSelector getItemSelector() {
		return _itemSelector;
	}

	protected JSONArray getPluginsJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONUtil.putAll(
			"advlist autolink autosave link image lists charmap print " +
				"preview hr anchor",
			"searchreplace wordcount fullscreen media");

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put("code");
		}

		jsonArray.put(
			"table contextmenu emoticons textcolor paste fullpage textcolor " +
				"colorpicker textpattern");

		return jsonArray;
	}

	protected JSONObject getStyleFormatJSONObject(
		String styleFormatName, String type, String element,
		String cssClasses) {

		return JSONUtil.put(
			type, element
		).put(
			"classes", cssClasses
		).put(
			"title", styleFormatName
		);
	}

	protected JSONArray getStyleFormatsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			getStyleFormatJSONObject(
				LanguageUtil.get(locale, "normal"), "inline", "p", null),
			getStyleFormatJSONObject(
				LanguageUtil.format(locale, "heading-x", "1"), "block", "h1",
				null),
			getStyleFormatJSONObject(
				LanguageUtil.format(locale, "heading-x", "2"), "block", "h2",
				null),
			getStyleFormatJSONObject(
				LanguageUtil.format(locale, "heading-x", "3"), "block", "h3",
				null),
			getStyleFormatJSONObject(
				LanguageUtil.format(locale, "heading-x", "4"), "block", "h4",
				null),
			getStyleFormatJSONObject(
				LanguageUtil.get(locale, "preformatted-text"), "block", "pre",
				null),
			getStyleFormatJSONObject(
				LanguageUtil.get(locale, "cited-work"), "inline", "cite", null),
			getStyleFormatJSONObject(
				LanguageUtil.get(locale, "computer-code"), "inline", "code",
				null),
			getStyleFormatJSONObject(
				LanguageUtil.get(locale, "info-message"), "block", "div",
				"portlet-msg-info"),
			getStyleFormatJSONObject(
				LanguageUtil.get(locale, "alert-message"), "block", "div",
				"portlet-msg-alert"),
			getStyleFormatJSONObject(
				LanguageUtil.get(locale, "error-message"), "block", "div",
				"portlet-msg-error"));
	}

	protected JSONArray getToolbarJSONArray(
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay) {

		JSONObject toolbarsJSONObject = getToolbarsJSONObject(
			inputEditorTaglibAttributes);

		String toolbarSet = (String)inputEditorTaglibAttributes.get(
			TinyMCEEditorConstants.ATTRIBUTE_NAMESPACE + ":toolbarSet");

		String currentToolbarSet = TextFormatter.format(
			HtmlUtil.escapeJS(toolbarSet), TextFormatter.M);

		if (_browserSniffer.isMobile(themeDisplay.getRequest())) {
			currentToolbarSet = "phone";
		}

		JSONArray toolbarJSONArray = toolbarsJSONObject.getJSONArray(
			currentToolbarSet);

		if (toolbarJSONArray == null) {
			toolbarJSONArray = toolbarsJSONObject.getJSONArray("liferay");
		}

		return toolbarJSONArray;
	}

	protected JSONArray getToolbarsEmailJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		String buttons =
			"cut copy paste bullist numlist | blockquote | undo redo | link " +
				"unlink image ";

		if (isShowSource(inputEditorTaglibAttributes)) {
			buttons += "code ";
		}

		buttons += "| hr removeformat | preview print fullscreen";

		return JSONUtil.putAll(
			"fontselect fontsizeselect | forecolor backcolor | bold italic " +
				"underline strikethrough | alignleft aligncenter alignright " +
					"alignjustify",
			buttons);
	}

	protected JSONObject getToolbarsJSONObject(
		Map<String, Object> inputEditorTaglibAttributes) {

		return JSONUtil.put(
			"email", getToolbarsEmailJSONArray(inputEditorTaglibAttributes)
		).put(
			"liferay", getToolbarsLiferayJSONArray(inputEditorTaglibAttributes)
		).put(
			"phone", getToolbarsPhoneJSONArray()
		).put(
			"simple", getToolbarsSimpleJSONArray(inputEditorTaglibAttributes)
		).put(
			"tablet", getToolbarsTabletJSONArray(inputEditorTaglibAttributes)
		);
	}

	protected JSONArray getToolbarsLiferayJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		String buttons =
			"cut copy paste searchreplace bullist numlist | outdent indent " +
				"blockquote | undo redo | link unlink anchor image media ";

		if (isShowSource(inputEditorTaglibAttributes)) {
			buttons += "code";
		}

		return JSONUtil.putAll(
			"styleselect fontselect fontsizeselect | forecolor backcolor | " +
				"bold italic underline strikethrough | alignleft aligncenter " +
					"alignright alignjustify",
			buttons,
			"table | hr removeformat | subscript superscript | charmap " +
				"emoticons | preview print fullscreen");
	}

	protected JSONArray getToolbarsPhoneJSONArray() {
		return JSONUtil.putAll(
			"bold italic underline | bullist numlist", "link unlink image");
	}

	protected JSONArray getToolbarsSimpleJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		String buttons =
			"bold italic underline strikethrough | bullist numlist | table | " +
				"link unlink image";

		if (isShowSource(inputEditorTaglibAttributes)) {
			buttons += " code";
		}

		return JSONUtil.put(buttons);
	}

	protected JSONArray getToolbarsTabletJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		String buttons = "bullist numlist | link unlink image";

		if (isShowSource(inputEditorTaglibAttributes)) {
			buttons += " code";
		}

		return JSONUtil.putAll(
			"styleselect fontselect fontsizeselect | bold italic underline " +
				"strikethrough | alignleft aligncenter alignright alignjustify",
			buttons);
	}

	@Reference
	private BrowserSniffer _browserSniffer;

	@Reference
	private ItemSelector _itemSelector;

}
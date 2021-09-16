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

package com.liferay.translation.web.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collection;
import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public class ExportTranslationUtil {

	public static JSONArray getLocalesJSONArray(
		Locale currentLocale, Collection<Locale> locales) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		locales.forEach(
			locale -> jsonArray.put(
				_getLocaleJSONObject(currentLocale, locale)));

		return jsonArray;
	}

	private static String _getDisplayName(Locale currentLocale, Locale locale) {
		String key = "language." + locale.getLanguage();

		String displayName = LanguageUtil.get(currentLocale, key);

		if (displayName.equals(key)) {
			return locale.getDisplayName(currentLocale);
		}

		return StringBundler.concat(
			displayName, " (", locale.getDisplayCountry(currentLocale), ")");
	}

	private static JSONObject _getLocaleJSONObject(
		Locale currentLocale, Locale locale) {

		return JSONUtil.put(
			"displayName", _getDisplayName(currentLocale, locale)
		).put(
			"languageId", LocaleUtil.toLanguageId(locale)
		);
	}

}
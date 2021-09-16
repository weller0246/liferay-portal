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

package com.liferay.data.engine.field.type.util;

import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public class LocalizedValueUtil {

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static Object getLocalizedValue(
		Locale locale, Map<String, Object> localizedValues) {

		if (MapUtil.isEmpty(localizedValues)) {
			return null;
		}

		return localizedValues.get(LocaleUtil.toLanguageId(locale));
	}

	public static <V> JSONObject toJSONObject(Map<String, V> map) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (MapUtil.isEmpty(map)) {
			return jsonObject;
		}

		for (Map.Entry<String, V> entry : map.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject;
	}

	public static Map<Locale, String> toLocaleStringMap(
		Map<String, Object> localizedValues) {

		if (MapUtil.isEmpty(localizedValues)) {
			return Collections.emptyMap();
		}

		Map<Locale, String> localeStringMap = new HashMap<>();

		for (Map.Entry<String, Object> entry : localizedValues.entrySet()) {
			localeStringMap.put(
				LocaleUtil.fromLanguageId(entry.getKey()),
				(String)entry.getValue());
		}

		return localeStringMap;
	}

	public static LocalizedValue toLocalizedValue(
		Map<String, Object> localizedValues) {

		return toLocalizedValue(localizedValues, null);
	}

	public static LocalizedValue toLocalizedValue(
		Map<String, Object> localizedValues, Locale locale) {

		if (localizedValues == null) {
			return null;
		}

		LocalizedValue localizedValue = new LocalizedValue();

		for (Map.Entry<String, Object> entry : localizedValues.entrySet()) {
			Object value = entry.getValue();

			if (value instanceof ArrayList) {
				localizedValue.addString(
					LocaleUtil.fromLanguageId(entry.getKey()),
					String.valueOf(
						JSONFactoryUtil.createJSONArray((ArrayList)value)));
			}
			else if (value instanceof Map) {
				localizedValue.addString(
					LocaleUtil.fromLanguageId(entry.getKey()),
					String.valueOf(
						JSONFactoryUtil.createJSONObject((Map)value)));
			}
			else if (value instanceof Object[]) {
				localizedValue.addString(
					LocaleUtil.fromLanguageId(entry.getKey()),
					String.valueOf(
						JSONFactoryUtil.createJSONArray((Object[])value)));
			}
			else if (value != null) {
				localizedValue.addString(
					LocaleUtil.fromLanguageId(entry.getKey()),
					String.valueOf(value));
			}
			else {
				localizedValue.addString(
					LocaleUtil.fromLanguageId(entry.getKey()),
					StringPool.BLANK);
			}

			if (locale != null) {
				localizedValue.setDefaultLocale(locale);
			}
		}

		return localizedValue;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static <V> Map<String, V> toLocalizedValues(JSONObject jsonObject) {
		if (jsonObject == null) {
			return Collections.emptyMap();
		}

		Map<String, V> localizedValues = new HashMap<>();

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			localizedValues.put(key, (V)jsonObject.get(key));
		}

		return localizedValues;
	}

	public static Map<String, Object> toLocalizedValuesMap(
		LocalizedValue localizedValue) {

		if (localizedValue == null) {
			return Collections.emptyMap();
		}

		Map<Locale, String> values = localizedValue.getValues();

		Set<Map.Entry<Locale, String>> entrySet = values.entrySet();

		Stream<Map.Entry<Locale, String>> stream = entrySet.stream();

		return stream.collect(
			Collectors.toMap(
				entry -> LanguageUtil.getLanguageId(entry.getKey()),
				entry -> {
					String value = entry.getValue();

					if (Validator.isNotNull(value)) {
						try {
							Object deserializedObject =
								JSONFactoryUtil.looseDeserialize(value);

							if (deserializedObject instanceof List) {
								return JSONFactoryUtil.createJSONArray(value);
							}
							else if (deserializedObject instanceof Map) {
								return JSONFactoryUtil.createJSONObject(value);
							}
						}
						catch (Exception exception) {
							if (_log.isDebugEnabled()) {
								_log.debug(exception, exception);
							}
						}
					}

					return value;
				}));
	}

	public static Map<String, Object> toStringObjectMap(
		Map<Locale, String> localizedValues) {

		Map<String, Object> stringObjectMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : localizedValues.entrySet()) {
			stringObjectMap.put(
				String.valueOf(entry.getKey()), entry.getValue());
		}

		return stringObjectMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LocalizedValueUtil.class);

}
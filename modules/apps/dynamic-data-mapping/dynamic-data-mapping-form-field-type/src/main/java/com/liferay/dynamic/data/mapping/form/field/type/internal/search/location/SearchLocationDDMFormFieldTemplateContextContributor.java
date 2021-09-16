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

package com.liferay.dynamic.data.mapping.form.field.type.internal.search.location;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.google.places.util.GooglePlacesUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Paulino
 */
@Component(
	immediate = true,
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.SEARCH_LOCATION,
	service = DDMFormFieldTemplateContextContributor.class
)
public class SearchLocationDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"googlePlacesAPIKey",
			_getGooglePlacesAPIKey(ddmFormField, ddmFormFieldRenderingContext)
		).put(
			"labels",
			_getLabelsJSONObject(
				ddmFormFieldRenderingContext.getLocale(),
				_getVisibleFields(ddmFormField, ddmFormFieldRenderingContext))
		).put(
			"layout",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext.getLocale(),
				"layout")
		).put(
			"placeholder",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext.getLocale(),
				"placeholder")
		).put(
			"visibleFields",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext.getLocale(),
				"visibleFields")
		).build();
	}

	private String _getGooglePlacesAPIKey(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		String googlePlacesAPIKey = GetterUtil.getString(
			ddmFormField.getProperty("googlePlacesAPIKey"));

		if (Validator.isNotNull(googlePlacesAPIKey)) {
			return googlePlacesAPIKey;
		}

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return GooglePlacesUtil.getGooglePlacesAPIKey(
			themeDisplay.getCompanyId(),
			GetterUtil.getLong(
				ddmFormFieldRenderingContext.getProperty("groupId")),
			_groupLocalService);
	}

	private JSONObject _getLabelsJSONObject(
		Locale locale, String[] visibleFields) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		Stream.of(
			visibleFields
		).forEach(
			visibleField -> jsonObject.put(
				visibleField,
				LanguageUtil.get(
					ResourceBundleUtil.getModuleAndPortalResourceBundle(
						locale, getClass()),
					visibleField))
		);

		return jsonObject;
	}

	private String[] _getVisibleFields(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		LocalizedValue visibleFields = (LocalizedValue)ddmFormField.getProperty(
			"visibleFields");

		try {
			return JSONUtil.toStringArray(
				_jsonFactory.createJSONArray(
					visibleFields.getString(
						ddmFormFieldRenderingContext.getLocale())));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
			}
		}

		return new String[0];
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchLocationDDMFormFieldTemplateContextContributor.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}
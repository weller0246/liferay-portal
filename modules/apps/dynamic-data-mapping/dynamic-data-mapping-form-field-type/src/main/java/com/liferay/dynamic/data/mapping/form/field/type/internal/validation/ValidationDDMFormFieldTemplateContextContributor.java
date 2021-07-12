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

package com.liferay.dynamic.data.mapping.form.field.type.internal.validation;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.VALIDATION,
	service = {
		DDMFormFieldTemplateContextContributor.class,
		ValidationDDMFormFieldTemplateContextContributor.class
	}
)
public class ValidationDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		return HashMapBuilder.<String, Object>put(
			"dataType", getDataType(ddmFormField, ddmFormFieldRenderingContext)
		).put(
			"value", _getValue(ddmFormFieldRenderingContext)
		).build();
	}

	protected String getDataType(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> changedProperties =
			(Map<String, Object>)ddmFormFieldRenderingContext.getProperty(
				"changedProperties");

		if (MapUtil.isNotEmpty(changedProperties)) {
			String validationDataType = (String)changedProperties.get(
				"validationDataType");

			if (Validator.isNotNull(validationDataType)) {
				return validationDataType;
			}
		}

		return ddmFormField.getDataType();
	}

	@Reference
	protected JSONFactory jsonFactory;

	private Map<String, Object> _getValue(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		String valueString = ddmFormFieldRenderingContext.getValue();

		if (Validator.isNotNull(valueString)) {
			try {
				JSONObject valueJSONObject = jsonFactory.createJSONObject(
					valueString);

				return HashMapBuilder.<String, Object>put(
					"errorMessage",
					valueJSONObject.getJSONObject("errorMessage")
				).put(
					"expression", valueJSONObject.getJSONObject("expression")
				).put(
					"parameter", valueJSONObject.getJSONObject("parameter")
				).build();
			}
			catch (JSONException jsonException) {
				if (_log.isWarnEnabled()) {
					_log.warn(jsonException, jsonException);
				}
			}
		}

		return HashMapBuilder.<String, Object>put(
			"errorMessage", jsonFactory.createJSONObject()
		).put(
			"expression", jsonFactory.createJSONObject()
		).put(
			"parameter", jsonFactory.createJSONObject()
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ValidationDDMFormFieldTemplateContextContributor.class);

}
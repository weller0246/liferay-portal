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

package com.liferay.dynamic.data.mapping.form.field.type.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInvoker;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldOptionsFactory;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMFormFieldOptionsFactory.class)
public class DDMFormFieldOptionsFactoryImpl
	implements DDMFormFieldOptionsFactory {

	@Override
	public DDMFormFieldOptions create(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		String dataSourceType = ddmFormField.getDataSourceType();

		if (Objects.equals(dataSourceType, "data-provider")) {
			return createDDMFormFieldOptionsFromDataProvider(
				ddmFormField, ddmFormFieldRenderingContext);
		}

		return createDDMFormFieldOptions(
			ddmFormField, ddmFormFieldRenderingContext, dataSourceType);
	}

	protected DDMFormFieldOptions createDDMFormFieldOptions(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		String dataSourceType) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.setDefaultLocale(
			ddmFormFieldRenderingContext.getLocale());

		List<Map<String, String>> options =
			(List<Map<String, String>>)ddmFormFieldRenderingContext.getProperty(
				"options");

		if (ListUtil.isEmpty(options)) {
			if (dataSourceType.equals("from-autofill")) {
				return ddmFormFieldOptions;
			}

			Map<String, Object> changedProperties =
				(Map<String, Object>)ddmFormFieldRenderingContext.getProperty(
					"changedProperties");

			if (MapUtil.isNotEmpty(changedProperties) &&
				changedProperties.containsKey("options")) {

				return ddmFormFieldOptions;
			}

			return ddmFormField.getDDMFormFieldOptions();
		}

		for (Map<String, String> option : options) {
			ddmFormFieldOptions.addOptionLabel(
				option.get("value"), ddmFormFieldRenderingContext.getLocale(),
				option.get("label"));
			ddmFormFieldOptions.addOptionReference(
				option.get("value"), option.get("reference"));
		}

		return ddmFormFieldOptions;
	}

	protected DDMFormFieldOptions createDDMFormFieldOptionsFromDataProvider(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.setDefaultLocale(
			ddmFormFieldRenderingContext.getLocale());

		try {
			String ddmDataProviderInstanceId = getJSONArrayFirstValue(
				GetterUtil.getString(
					ddmFormField.getProperty("ddmDataProviderInstanceId")));

			HttpServletRequest httpServletRequest =
				ddmFormFieldRenderingContext.getHttpServletRequest();

			DDMDataProviderRequest.Builder builder =
				DDMDataProviderRequest.Builder.newBuilder();

			DDMDataProviderRequest ddmDataProviderRequest =
				builder.withDDMDataProviderId(
					ddmDataProviderInstanceId
				).withCompanyId(
					portal.getCompanyId(httpServletRequest)
				).withGroupId(
					getGroupId(httpServletRequest)
				).withLocale(
					ddmFormFieldRenderingContext.getLocale()
				).withParameter(
					"filterParameterValue",
					HtmlUtil.escapeURL(
						String.valueOf(ddmFormFieldRenderingContext.getValue()))
				).withParameter(
					"httpServletRequest", httpServletRequest
				).build();

			DDMDataProviderResponse ddmDataProviderResponse =
				ddmDataProviderInvoker.invoke(ddmDataProviderRequest);

			String ddmDataProviderInstanceOutput = getJSONArrayFirstValue(
				GetterUtil.getString(
					ddmFormField.getProperty("ddmDataProviderInstanceOutput"),
					"Default-Output"));

			Optional<List<KeyValuePair>> keyValuesPairsOptional =
				ddmDataProviderResponse.getOutputOptional(
					ddmDataProviderInstanceOutput, List.class);

			if (!keyValuesPairsOptional.isPresent()) {
				return ddmFormFieldOptions;
			}

			for (KeyValuePair keyValuePair : keyValuesPairsOptional.get()) {
				ddmFormFieldOptions.addOptionLabel(
					keyValuePair.getKey(),
					ddmFormFieldRenderingContext.getLocale(),
					keyValuePair.getValue());
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return ddmFormFieldOptions;
	}

	protected long getGroupId(HttpServletRequest httpServletRequest) {
		long scopeGroupId = ParamUtil.getLong(
			httpServletRequest, "scopeGroupId");

		if (scopeGroupId == 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			scopeGroupId = themeDisplay.getScopeGroupId();
		}

		return scopeGroupId;
	}

	protected String getJSONArrayFirstValue(String value) {
		try {
			JSONArray jsonArray = jsonFactory.createJSONArray(value);

			return jsonArray.getString(0);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return value;
		}
	}

	@Reference
	protected DDMDataProviderInvoker ddmDataProviderInvoker;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormFieldOptionsFactoryImpl.class);

}
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

package com.liferay.dynamic.data.mapping.validator.internal;

import com.liferay.dynamic.data.mapping.constants.DDMConstants;
import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidationException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustNotDuplicateFieldName;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetAvailableLocales;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetDefaultLocale;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetDefaultLocaleAsAvailableLocale;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetFieldType;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetFieldsForForm;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetOptionsForField;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidAvailableLocalesForProperty;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidCharactersForFieldName;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidCharactersForFieldType;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidDefaultLocaleForProperty;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidFormRuleExpression;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidIndexType;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidType;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidValidationExpression;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidVisibilityExpression;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidator;
import com.liferay.dynamic.data.mapping.validator.internal.util.DDMFormRuleValidatorUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMFormValidator.class)
public class DDMFormValidatorImpl implements DDMFormValidator {

	@Override
	public void validate(DDMForm ddmForm)
		throws DDMFormFieldValueValidationException,
			   DDMFormValidationException {

		DDMFormRuleValidatorUtil.validateDDMFormRules(
			_ddmExpressionFactory, ddmForm.getDDMFormRules());

		validateDDMFormLocales(ddmForm);

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		if (ddmFormFields.isEmpty()) {
			throw new MustSetFieldsForForm();
		}

		_validateDDMFormFieldNames(ddmFormFields);

		validateDDMFormFields(
			ddmFormFields, new HashSet<String>(),
			ddmForm.allowInvalidAvailableLocalesForProperty(),
			ddmForm.getAvailableLocales(), ddmForm.getDefaultLocale());
	}

	@Reference(unbind = "-")
	protected void setDDMExpressionFactory(
		DDMExpressionFactory ddmExpressionFactory) {

		_ddmExpressionFactory = ddmExpressionFactory;
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	protected void validateDDMExpression(
			String expressionType, String ddmExpressionString)
		throws DDMFormValidationException {

		if (Validator.isNull(ddmExpressionString)) {
			return;
		}

		try {
			_ddmExpressionFactory.createExpression(
				CreateExpressionRequest.Builder.newBuilder(
					ddmExpressionString
				).build());
		}
		catch (DDMExpressionException ddmExpressionException) {
			throw new MustSetValidFormRuleExpression(
				expressionType, ddmExpressionString, ddmExpressionException);
		}
	}

	protected void validateDDMFormAvailableLocales(
			Set<Locale> availableLocales, Locale defaultLocale)
		throws DDMFormValidationException {

		if ((availableLocales == null) || availableLocales.isEmpty()) {
			throw new MustSetAvailableLocales();
		}

		if (!availableLocales.contains(defaultLocale)) {
			throw new MustSetDefaultLocaleAsAvailableLocale(defaultLocale);
		}
	}

	protected void validateDDMFormFieldIndexType(DDMFormField ddmFormField)
		throws DDMFormValidationException {

		if (!ArrayUtil.contains(
				_DDM_FORM_FIELD_INDEX_TYPES, ddmFormField.getIndexType())) {

			throw new MustSetValidIndexType(ddmFormField.getName());
		}
	}

	protected void validateDDMFormFieldName(
			DDMFormField ddmFormField, Set<String> ddmFormFieldNames)
		throws DDMFormValidationException {

		Matcher matcher = _ddmFormFieldNamePattern.matcher(
			ddmFormField.getName());

		if (!matcher.matches()) {
			throw new MustSetValidCharactersForFieldName(
				ddmFormField.getName());
		}

		if (ddmFormFieldNames.contains(
				StringUtil.toLowerCase(ddmFormField.getName()))) {

			throw new MustNotDuplicateFieldName(ddmFormField.getName());
		}

		ddmFormFieldNames.add(StringUtil.toLowerCase(ddmFormField.getName()));
	}

	protected void validateDDMFormFieldOptions(
			boolean allowInvalidAvailableLocalesForProperty,
			DDMFormField ddmFormField, Set<Locale> ddmFormAvailableLocales,
			Locale ddmFormDefaultLocale)
		throws DDMFormValidationException {

		try {
			validateDDMFormFieldOptions(
				ddmFormField, ddmFormAvailableLocales, ddmFormDefaultLocale);
		}
		catch (DDMFormValidationException ddmFormValidationException) {
			if ((ddmFormValidationException instanceof
					MustSetValidAvailableLocalesForProperty) &&
				allowInvalidAvailableLocalesForProperty) {

				return;
			}

			throw ddmFormValidationException;
		}
	}

	protected void validateDDMFormFieldOptions(
			DDMFormField ddmFormField, Set<Locale> ddmFormAvailableLocales,
			Locale ddmFormDefaultLocale)
		throws DDMFormValidationException {

		String fieldType = ddmFormField.getType();

		if (fieldType.equals(DDMFormFieldType.GRID)) {
			validateDDMFormFieldOptionsProperties(
				ddmFormField, "columns", ddmFormAvailableLocales,
				ddmFormDefaultLocale);
			validateDDMFormFieldOptionsProperties(
				ddmFormField, "rows", ddmFormAvailableLocales,
				ddmFormDefaultLocale);
		}

		if (!fieldType.equals(DDMFormFieldType.CHECKBOX_MULTIPLE) &&
			!fieldType.equals(DDMFormFieldType.RADIO) &&
			!fieldType.equals(DDMFormFieldType.SELECT)) {

			return;
		}

		if (!Validator.isBlank(ddmFormField.getDataSourceType()) &&
			!Objects.equals(ddmFormField.getDataSourceType(), "manual")) {

			return;
		}

		validateDDMFormFieldOptionsProperties(
			ddmFormField, "options", ddmFormAvailableLocales,
			ddmFormDefaultLocale);
	}

	protected void validateDDMFormFieldOptionsProperties(
			DDMFormField ddmFormField, String propertyName,
			Set<Locale> ddmFormAvailableLocales, Locale ddmFormDefaultLocale)
		throws DDMFormValidationException {

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		if (!propertyName.equals("options")) {
			ddmFormFieldOptions = (DDMFormFieldOptions)ddmFormField.getProperty(
				propertyName);
		}

		Set<String> optionsValues = Collections.emptySet();

		if (ddmFormFieldOptions != null) {
			optionsValues = ddmFormFieldOptions.getOptionsValues();
		}

		if (optionsValues.isEmpty()) {
			LocalizedValue localizedValue = ddmFormField.getLabel();

			throw new MustSetOptionsForField(
				localizedValue.getString(ddmFormDefaultLocale),
				ddmFormField.getName());
		}

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
			LocalizedValue localizedValue = ddmFormFieldOptions.getOptionLabels(
				optionValue);

			validateDDMFormFieldPropertyValue(
				ddmFormField.getName(), propertyName, localizedValue,
				ddmFormAvailableLocales, ddmFormDefaultLocale);
		}
	}

	protected void validateDDMFormFieldPropertyValue(
			String fieldName, String propertyName, LocalizedValue propertyValue,
			Set<Locale> ddmFormAvailableLocales, Locale ddmFormDefaultLocale)
		throws DDMFormValidationException {

		if (!ddmFormDefaultLocale.equals(propertyValue.getDefaultLocale())) {
			throw new MustSetValidDefaultLocaleForProperty(
				fieldName, propertyName);
		}

		if (!ddmFormAvailableLocales.equals(
				propertyValue.getAvailableLocales())) {

			throw new MustSetValidAvailableLocalesForProperty(
				fieldName, propertyName);
		}
	}

	protected void validateDDMFormFields(
			List<DDMFormField> ddmFormFields, Set<String> ddmFormFieldNames,
			boolean allowInvalidAvailableLocalesForProperty,
			Set<Locale> ddmFormAvailableLocales, Locale ddmFormDefaultLocale)
		throws DDMFormValidationException {

		for (DDMFormField ddmFormField : ddmFormFields) {
			validateDDMFormFieldName(ddmFormField, ddmFormFieldNames);

			validateDDMFormFieldType(ddmFormField);

			validateDDMFormFieldIndexType(ddmFormField);

			validateDDMFormFieldOptions(
				allowInvalidAvailableLocalesForProperty, ddmFormField,
				ddmFormAvailableLocales, ddmFormDefaultLocale);

			validateOptionalDDMFormFieldLocalizedProperty(
				ddmFormField, "label", allowInvalidAvailableLocalesForProperty,
				ddmFormAvailableLocales, ddmFormDefaultLocale);

			validateOptionalDDMFormFieldLocalizedProperty(
				ddmFormField, "tip", allowInvalidAvailableLocalesForProperty,
				ddmFormAvailableLocales, ddmFormDefaultLocale);

			validateDDMFormFieldValidationExpression(
				ddmFormField, ddmFormAvailableLocales);
			validateDDMFormFieldVisibilityExpression(ddmFormField);

			validateDDMFormFields(
				ddmFormField.getNestedDDMFormFields(), ddmFormFieldNames,
				allowInvalidAvailableLocalesForProperty,
				ddmFormAvailableLocales, ddmFormDefaultLocale);
		}
	}

	protected void validateDDMFormFieldType(DDMFormField ddmFormField)
		throws DDMFormValidationException {

		if (Validator.isNull(ddmFormField.getType())) {
			throw new MustSetFieldType(ddmFormField.getName());
		}

		Matcher matcher = _ddmFormFieldTypePattern.matcher(
			ddmFormField.getType());

		if (!matcher.matches()) {
			throw new MustSetValidCharactersForFieldType(
				ddmFormField.getType());
		}

		Set<String> ddmFormFieldTypeNames = new HashSet<>(
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeNames());

		ddmFormFieldTypeNames.addAll(
			SetUtil.fromArray(DDMConstants.SUPPORTED_DDM_FORM_FIELD_TYPES));

		if (!ddmFormFieldTypeNames.contains(ddmFormField.getType())) {
			throw new MustSetValidType(ddmFormField.getType());
		}
	}

	protected void validateDDMFormFieldValidationExpression(
			DDMFormField ddmFormField, Set<Locale> locales)
		throws DDMFormValidationException {

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		if (ddmFormFieldValidation == null) {
			return;
		}

		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			ddmFormFieldValidation.getDDMFormFieldValidationExpression();

		if ((ddmFormFieldValidationExpression == null) ||
			Validator.isNull(ddmFormFieldValidationExpression.getValue())) {

			return;
		}

		try {
			if (ddmFormFieldValidation.getParameterLocalizedValue() == null) {
				_ddmExpressionFactory.createExpression(
					CreateExpressionRequest.Builder.newBuilder(
						ddmFormFieldValidationExpression.getValue()
					).build());
			}
			else {
				String value = ddmFormFieldValidationExpression.getValue();

				LocalizedValue parameterLocalizedValue =
					ddmFormFieldValidation.getParameterLocalizedValue();

				for (Locale locale : locales) {
					_ddmExpressionFactory.createExpression(
						CreateExpressionRequest.Builder.newBuilder(
							StringUtil.replace(
								value, "{parameter}",
								parameterLocalizedValue.getString(locale))
						).withDDMExpressionDateValidation(
							StringUtil.equals(
								ddmFormField.getType(), FieldConstants.DATE)
						).build());
				}
			}
		}
		catch (DDMExpressionException ddmExpressionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmExpressionException, ddmExpressionException);
			}

			throw new MustSetValidValidationExpression(
				ddmFormField.getName(),
				ddmFormFieldValidationExpression.getValue());
		}
	}

	protected void validateDDMFormFieldVisibilityExpression(
			DDMFormField ddmFormField)
		throws DDMFormValidationException {

		String visibilityExpression = ddmFormField.getVisibilityExpression();

		if (Validator.isNull(visibilityExpression)) {
			return;
		}

		try {
			_ddmExpressionFactory.createExpression(
				CreateExpressionRequest.Builder.newBuilder(
					visibilityExpression
				).build());
		}
		catch (DDMExpressionException ddmExpressionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmExpressionException, ddmExpressionException);
			}

			throw new MustSetValidVisibilityExpression(
				ddmFormField.getName(), visibilityExpression);
		}
	}

	protected void validateDDMFormLocales(DDMForm ddmForm)
		throws DDMFormValidationException {

		Locale defaultLocale = ddmForm.getDefaultLocale();

		if (defaultLocale == null) {
			throw new MustSetDefaultLocale();
		}

		validateDDMFormAvailableLocales(
			ddmForm.getAvailableLocales(), defaultLocale);
	}

	protected void validateOptionalDDMFormFieldLocalizedProperty(
			DDMFormField ddmFormField, String propertyName,
			boolean allowInvalidAvailableLocalesForProperty,
			Set<Locale> ddmFormAvailableLocales, Locale ddmFormDefaultLocale)
		throws DDMFormValidationException {

		LocalizedValue propertyValue =
			(LocalizedValue)BeanPropertiesUtil.getObject(
				ddmFormField, propertyName);

		if ((propertyValue == null) ||
			MapUtil.isEmpty(propertyValue.getValues())) {

			return;
		}

		try {
			validateDDMFormFieldPropertyValue(
				ddmFormField.getName(), propertyName, propertyValue,
				ddmFormAvailableLocales, ddmFormDefaultLocale);
		}
		catch (DDMFormValidationException ddmFormValidationException) {
			if ((ddmFormValidationException instanceof
					MustSetValidAvailableLocalesForProperty) &&
				allowInvalidAvailableLocalesForProperty) {

				return;
			}

			throw ddmFormValidationException;
		}
	}

	private void _validateDDMFormFieldNames(List<DDMFormField> ddmFormFields)
		throws DDMFormValidationException {

		Stream<DDMFormField> stream = ddmFormFields.stream();

		Map<String, Long> ddmFormFieldNamesCount = stream.map(
			DDMFormField::getName
		).collect(
			Collectors.groupingBy(String::valueOf, Collectors.counting())
		);

		Set<Map.Entry<String, Long>> entrySet =
			ddmFormFieldNamesCount.entrySet();

		Stream<Map.Entry<String, Long>> entrySetStream = entrySet.stream();

		Set<String> duplicatedFieldNames = entrySetStream.filter(
			entry -> entry.getValue() > 1
		).map(
			Map.Entry::getKey
		).collect(
			Collectors.toSet()
		);

		if (SetUtil.isNotEmpty(duplicatedFieldNames)) {
			throw new MustNotDuplicateFieldName(duplicatedFieldNames);
		}
	}

	private static final String[] _DDM_FORM_FIELD_INDEX_TYPES = {
		StringPool.BLANK, "keyword", "none", "text"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormValidatorImpl.class);

	private static final Pattern _ddmFormFieldNamePattern = Pattern.compile(
		"([^\\p{Punct}|\\p{Space}$]|_)+");
	private static final Pattern _ddmFormFieldTypePattern = Pattern.compile(
		"([^\\p{Punct}|\\p{Space}$]|[-_])+");

	private DDMExpressionFactory _ddmExpressionFactory;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

}
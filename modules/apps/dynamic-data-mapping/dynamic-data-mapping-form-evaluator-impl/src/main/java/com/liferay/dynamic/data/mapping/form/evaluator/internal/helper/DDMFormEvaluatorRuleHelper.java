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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.helper;

import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.expression.DDMFormEvaluatorExpressionObserver;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Rafael Praxedes
 */
public class DDMFormEvaluatorRuleHelper {

	public DDMFormEvaluatorRuleHelper(
		Map<String, DDMFormField> ddmFormFieldsMap,
		DDMFormEvaluatorExpressionObserver ddmFormEvaluatorExpressionObserver) {

		_ddmFormFieldsMap = ddmFormFieldsMap;
		_ddmFormEvaluatorExpressionObserver =
			ddmFormEvaluatorExpressionObserver;
	}

	public void checkFieldAffectedByAction(DDMFormRule ddmFormRule) {
		Collection<DDMFormField> fieldNameSet = _ddmFormFieldsMap.values();

		Stream<DDMFormField> stream = fieldNameSet.stream();

		stream.forEach(field -> checkFieldAffectedByAction(ddmFormRule, field));
	}

	protected void checkFieldAffectedByAction(
		DDMFormRule ddmFormRule, DDMFormField ddmFormField) {

		checkFieldAffectedByCalculateAction(ddmFormRule, ddmFormField);
		checkFieldAffectedBySetReadOnlyAction(ddmFormRule, ddmFormField);
		checkFieldAffectedBySetRequiredAction(ddmFormRule, ddmFormField);
		checkFieldAffectedBySetVisibleAction(ddmFormRule, ddmFormField);
	}

	protected void checkFieldAffectedByCalculateAction(
		DDMFormRule ddmFormRule, DDMFormField ddmFormField) {

		if (containsAction(
				ddmFormRule, "calculate", ddmFormField.getName(),
				GetterUtil.getString(ddmFormField.getProperty("value")))) {

			String newValue = StringPool.BLANK;

			LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

			if (predefinedValue != null) {
				newValue = GetterUtil.getString(
					predefinedValue.getString(
						new Locale(
							(String)ddmFormField.getProperty("locale"))));
			}

			UpdateFieldPropertyRequest.Builder builder =
				UpdateFieldPropertyRequest.Builder.newBuilder(
					ddmFormField.getName(), "value", newValue);

			_ddmFormEvaluatorExpressionObserver.updateFieldProperty(
				builder.build());
		}
	}

	protected void checkFieldAffectedBySetReadOnlyAction(
		DDMFormRule ddmFormRule, DDMFormField ddmFormField) {

		if (containsAction(
				ddmFormRule, "setEnabled", ddmFormField.getName(),
				!ddmFormField.isReadOnly())) {

			UpdateFieldPropertyRequest.Builder builder =
				UpdateFieldPropertyRequest.Builder.newBuilder(
					ddmFormField.getName(), "readOnly",
					!ddmFormField.isReadOnly());

			_ddmFormEvaluatorExpressionObserver.updateFieldProperty(
				builder.build());
		}
	}

	protected void checkFieldAffectedBySetRequiredAction(
		DDMFormRule ddmFormRule, DDMFormField ddmFormField) {

		if (containsAction(
				ddmFormRule, "setRequired", ddmFormField.getName(),
				ddmFormField.isRequired())) {

			UpdateFieldPropertyRequest.Builder builder =
				UpdateFieldPropertyRequest.Builder.newBuilder(
					ddmFormField.getName(), "required",
					!ddmFormField.isRequired());

			_ddmFormEvaluatorExpressionObserver.updateFieldProperty(
				builder.build());
		}
	}

	protected void checkFieldAffectedBySetVisibleAction(
		DDMFormRule ddmFormRule, DDMFormField ddmFormField) {

		if (containsAction(
				ddmFormRule, "setVisible", ddmFormField.getName(), true)) {

			UpdateFieldPropertyRequest.Builder builder =
				UpdateFieldPropertyRequest.Builder.newBuilder(
					ddmFormField.getName(), "visible", false);

			_ddmFormEvaluatorExpressionObserver.updateFieldProperty(
				builder.build());
		}
	}

	protected boolean containsAction(
		DDMFormRule ddmFormRule, String functionName, String ddmFormFieldName,
		Object defaultValue) {

		String setPropertyAction = String.format(
			"%s('%s', %s)", functionName, ddmFormFieldName, defaultValue);

		List<String> actions = ddmFormRule.getActions();

		Stream<String> stream = actions.stream();

		return stream.anyMatch(
			action -> Objects.equals(setPropertyAction, action));
	}

	private final DDMFormEvaluatorExpressionObserver
		_ddmFormEvaluatorExpressionObserver;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;

}
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

import {Token, Tokenizer} from 'dynamic-data-mapping-form-builder';

import {RulesVisitor} from '../../utils/visitors.es';

const isEqualLengthOptions = (options1, options2) => {
	if (!!options1 && !!options2) {
		return options1.length === options2.length;
	}
	else {
		return false;
	}
};

const isFieldValueOperand = (operands) => {
	return (
		operands.length == 2 &&
		operands[0].type === 'field' &&
		operands[1].type == 'string'
	);
};

const isOptionReferencedByOperand = (options, operandValue) => {
	return options.some(({label}) => operandValue === label);
};

export const renameFieldInsideExpression = (
	expression,
	fieldName,
	newFieldName
) => {
	const tokens = Tokenizer.tokenize(expression);

	return Tokenizer.stringifyTokens(
		tokens.map((token) => {
			if (token.type === Token.VARIABLE && token.value === fieldName) {
				token = new Token(Token.VARIABLE, newFieldName);
			}

			return token;
		})
	);
};

const renameFieldInsideAutofill = (object, oldFieldName, newFieldName) => {
	Object.keys(object).map((key) => {
		if (object[key] === oldFieldName) {
			object[key] = newFieldName;
		}
	});

	return object;
};

export const updateRulesReferences = (rules, oldProperties, newProperties) => {
	const oldFieldName = oldProperties.fieldName;
	const newFieldName = newProperties.fieldName;
	const visitor = new RulesVisitor(rules);

	rules = visitor.mapActions((action) => {
		if (action.target === oldFieldName) {
			action = {
				...action,
				target: newFieldName,
			};
		}

		if (action.action === 'calculate') {
			action = {
				...action,
				expression: renameFieldInsideExpression(
					action.expression,
					oldFieldName,
					newFieldName
				),
			};
		}
		else if (action.action === 'auto-fill') {
			action = {
				...action,
				inputs: renameFieldInsideAutofill(
					action.inputs,
					oldFieldName,
					newFieldName
				),
				outputs: renameFieldInsideAutofill(
					action.outputs,
					oldFieldName,
					newFieldName
				),
			};
		}

		return action;
	});

	visitor.setRules(rules);

	return visitor.mapConditions((condition) => {
		return {
			...condition,
			operands: condition.operands.map((operand, index) => {
				const oldOptions = oldProperties.options;
				const newOptions = newProperties.options;

				if (
					operand.type === 'field' &&
					operand.value === oldFieldName
				) {
					return {
						...operand,
						value: newFieldName,
					};
				}
				else if (
					index === 1 &&
					isFieldValueOperand(condition.operands) &&
					isEqualLengthOptions(oldOptions, newOptions) &&
					isOptionReferencedByOperand(oldOptions, operand.value)
				) {
					const changedOption = newOptions.find(
						({value}) =>
							value ===
							oldOptions.find(
								({label}) => label === operand.value
							).value
					);

					if (changedOption) {
						return {
							...operand,
							value: changedOption.label,
						};
					}
				}

				return operand;
			}),
		};
	});
};

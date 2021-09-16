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

import {debounce} from 'frontend-js-web';

import {convertToFormData, makeFetch} from './fetch.es';
import {PagesVisitor} from './visitors.es';

const EVALUATOR_URL =
	themeDisplay.getPathContext() +
	'/o/dynamic-data-mapping-form-context-provider/';

let controller = null;

export const mergeFieldOptions = (field, newField) => {
	let newValue = {...newField.value};

	Object.keys(newValue).forEach((languageId) => {
		newValue = {
			...newValue,
			[languageId]: newValue[languageId].map((option) => {
				const existingOption =
					field.value &&
					field.value[languageId] &&
					field.value[languageId].find(
						({value}) => value === option.value
					);

				return {
					...option,
					edited: existingOption && existingOption.edited,
				};
			}),
		};
	});

	return newValue;
};

export const mergePages = (
	defaultLanguageId,
	editingLanguageId,
	fieldName,
	newPages,
	sourcePages,
	viewMode
) => {
	const newPagesVisitor = new PagesVisitor(newPages);
	const sourcePagesVisitor = new PagesVisitor(sourcePages);

	const sourceFieldsByName = new Map();
	const sourceFieldsByFieldName = new Map();

	sourcePagesVisitor.mapFields(
		(field) => {
			sourceFieldsByName.set(field.name, field);
			sourceFieldsByFieldName.set(field.fieldName, field);
		},
		false,
		true
	);

	return newPagesVisitor.mapFields(
		(field) => {
			const sourceField =
				sourceFieldsByName.get(field.name) ??
				sourceFieldsByFieldName.get(field.fieldName) ??
				{};

			let fieldValue = field.valueChanged
				? field.value
				: sourceField.value;

			if (
				field.visible !== sourceField.visible &&
				field.visible &&
				viewMode
			) {
				fieldValue = '';
			}

			let newField = {
				...sourceField,
				...field,
				defaultLanguageId,
				displayErrors:
					sourceField.displayErrors || field.fieldName === fieldName,
				editingLanguageId,
				valid: field.valid !== false,
				value: fieldValue,
			};

			if (newField.type === 'options') {
				newField = {
					...newField,
					value: mergeFieldOptions(sourceField, newField),
				};
			}

			if (newField.localizable) {
				if (
					field.type === 'numeric' &&
					field.valueChanged &&
					field.value != field.localizedValue[editingLanguageId]
				) {
					sourceField.localizedValue[editingLanguageId] = field.value;
				}

				newField = {
					...newField,
					localizedValue: {
						...sourceField.localizedValue,
					},
				};
			}

			return newField;
		},
		false,
		true
	);
};

const doEvaluate = debounce((fieldName, evaluatorContext, callback) => {
	const {
		defaultLanguageId,
		editingLanguageId,
		formId,
		groupId,
		nextPage,
		pages,
		portletNamespace,
		previousPage,
		viewMode,
	} = evaluatorContext;

	if (controller) {
		controller.abort();
	}

	if (window.AbortController) {
		controller = new AbortController();
	}

	makeFetch({
		body: convertToFormData({
			languageId: editingLanguageId,
			p_auth: Liferay.authToken,
			portletNamespace,
			serializedFormContext: JSON.stringify({
				...evaluatorContext,
				formId,
				groupId: groupId ? groupId : themeDisplay.getScopeGroupId(),
				nextPage: nextPage ? nextPage : null,
				portletNamespace,
				previousPage: previousPage ? previousPage : null,
			}),
			trigger: fieldName,
		}),
		signal: controller && controller.signal,
		url: EVALUATOR_URL,
	})
		.then((newPages) => {
			const mergedPages = mergePages(
				defaultLanguageId,
				editingLanguageId,
				fieldName,
				newPages,
				pages,
				viewMode
			);

			callback(null, mergedPages);
		})
		.catch((error) => callback(error));
}, 600);

export const evaluate = (fieldName, evaluatorContext) => {
	return new Promise((resolve, reject) => {
		doEvaluate(fieldName, evaluatorContext, (error, pages) => {
			if (error) {
				return reject(error);
			}

			resolve(pages);
		});
	});
};

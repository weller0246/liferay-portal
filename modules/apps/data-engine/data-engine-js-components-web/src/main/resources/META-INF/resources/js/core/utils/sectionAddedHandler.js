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

import {SettingsContext} from 'dynamic-data-mapping-form-builder';

import {
	addFieldToColumn,
	findFieldByFieldName,
} from '../../utils/FormSupport.es';
import {FIELD_TYPE_FIELDSET} from '../../utils/constants';
import {createFieldSet} from '../../utils/fieldSets';
import {addField, createField} from '../../utils/fieldSupport';
import {PagesVisitor} from '../../utils/visitors.es';
import handleFieldDeleted from './fieldDeletedHandler';

const addNestedField = ({field, indexes, nestedField, props}) => {
	const layout = addFieldToColumn(
		[{rows: field.rows}],
		indexes.pageIndex,
		indexes.rowIndex,
		indexes.columnIndex,
		nestedField.fieldName
	);
	const nestedFields = [...field.nestedFields, nestedField];

	field = SettingsContext.updateField(
		props,
		field,
		'nestedFields',
		nestedFields
	);

	const {rows} = layout[indexes.pageIndex];

	field = SettingsContext.updateField(props, field, 'rows', rows);

	return {
		...field,
		nestedFields,
		rows,
	};
};

const handleSectionAdded = (props, state, event) => {
	const {data, indexes} = event;
	const {fieldName, parentFieldName} = data;
	const {pages} = state;

	const newField = event.newField ?? createField(props, event);
	const existingField = findFieldByFieldName(pages, fieldName);
	const fieldSetField = createFieldSet(props, event, [
		existingField,
		newField,
	]);

	const visitor = new PagesVisitor(pages);

	let newPages;

	if (parentFieldName) {
		newPages = visitor.mapFields(
			(field) => {
				if (field.fieldName === parentFieldName) {
					const updatedParentField = findFieldByFieldName(
						handleFieldDeleted(props, state, {
							fieldName,
						}).pages,
						parentFieldName
					);

					return addNestedField({
						field: updatedParentField,
						indexes: {
							...indexes,
							pageIndex: 0,
						},
						nestedField: fieldSetField,
						props,
					});
				}

				return field;
			},
			false,
			true
		);
	}
	else if (existingField.type === FIELD_TYPE_FIELDSET) {
		newPages = addField({
			...props,
			indexes,
			newField,
			pages,
			parentFieldName: existingField.fieldName,
		}).pages;
	}
	else {
		newPages = visitor.mapFields((field) => {
			if (field.fieldName === fieldName) {
				return fieldSetField;
			}

			return field;
		});
	}

	return {
		focusedField: {
			...newField,
		},
		pages: newPages,
	};
};

export default handleSectionAdded;

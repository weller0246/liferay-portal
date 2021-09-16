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

import React, {useEffect} from 'react';

import {EVENT_TYPES as CORE_EVENT_TYPES} from '../../core/actions/eventTypes.es';
import {INITIAL_CONFIG_STATE} from '../../core/config/initialConfigState.es';
import {INITIAL_STATE} from '../../core/config/initialState.es';
import {ConfigProvider} from '../../core/hooks/useConfig.es';
import {FormProvider, useForm} from '../../core/hooks/useForm.es';
import {
	activePageReducer,
	fieldReducer,
	languageReducer,
	pageValidationReducer,
	pagesStructureReducer,
} from '../../core/reducers/index.es';
import {parseProps} from '../../utils/parseProps.es';
import {EVENT_TYPES} from './eventTypes.es';
import {
	formBuilderReducer,
	objectFieldsReducer,
	paginationReducer,
	rulesReducer,
} from './reducers/index.es';

/**
 * Updates the state of the FieldSettings when any value coming
 * from layers above changes.
 */
const StateSync = ({
	activePage,
	builderPages,
	defaultLanguageId,
	editingLanguageId,
	focusedField,
	objectFields,
	pages,
	rules,
}) => {
	const dispatch = useForm();

	useEffect(() => {
		dispatch({
			payload: rules,
			type: EVENT_TYPES.RULES.UPDATE,
		});
	}, [dispatch, rules]);

	useEffect(() => {
		dispatch({
			payload: pages,
			type: CORE_EVENT_TYPES.PAGE.UPDATE,
		});
	}, [dispatch, pages]);

	useEffect(() => {
		dispatch({payload: {activePage}, type: CORE_EVENT_TYPES.PAGE.CHANGE});
	}, [dispatch, activePage]);

	useEffect(() => {
		dispatch({
			payload: {
				activePage: focusedField?.settingsContext.currentPage,
				field: focusedField,
			},
			type: CORE_EVENT_TYPES.FIELD.CLICK,
		});
	}, [dispatch, focusedField]);

	useEffect(() => {
		dispatch({
			payload: {defaultLanguageId, editingLanguageId},
			type: CORE_EVENT_TYPES.LANGUAGE.CHANGE,
		});
	}, [dispatch, defaultLanguageId, editingLanguageId]);

	useEffect(() => {
		dispatch({
			payload: {objectFields},
			type: EVENT_TYPES.OBJECT_FIELDS.ADD,
		});
	}, [dispatch, objectFields]);

	useEffect(() => {
		dispatch({
			payload: {pages: builderPages},
			type: EVENT_TYPES.FORM_BUILDER.PAGES.UPDATE,
		});
	}, [dispatch, builderPages]);

	useEffect(() => {
		dispatch({
			payload: {focusedField},
			type: EVENT_TYPES.FORM_BUILDER.FOCUSED_FIELD.CHANGE,
		});
	}, [dispatch, focusedField]);

	return null;
};

/**
 * Render a new form to be used in the Sidebar so that can edit the
 * properties of a field, a new FormProvider is needed to control
 * the reducers of a Field's settingsContext structure.
 */
export const FormFieldSettings = ({children, onAction, ...otherProps}) => {
	const {config, state} = parseProps(otherProps);

	return (
		<ConfigProvider config={config} initialConfig={INITIAL_CONFIG_STATE}>
			<FormProvider
				initialState={{
					...INITIAL_STATE,
					formBuilder: {
						focusedField: {},
						pages: [],
					},
				}}
				onAction={onAction}
				reducers={[
					activePageReducer,
					fieldReducer,
					formBuilderReducer,
					languageReducer,
					objectFieldsReducer,
					pagesStructureReducer,
					pageValidationReducer,
					paginationReducer,
					rulesReducer,
				]}
				value={state}
			>
				<StateSync {...state} />
				{children}
			</FormProvider>
		</ConfigProvider>
	);
};

FormFieldSettings.displayName = 'FormFieldSettings';

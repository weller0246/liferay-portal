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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {
	ClayInput,
	ClaySelect,
	ClaySelectWithOption,
} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {fetch, navigate, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useReducer, useRef, useState} from 'react';

import Field from './Field';

const DEFAULT_OPTION = {
	label: `- ${Liferay.Language.get('not-selected')} -`,
	value: '-1',
};

export default function TemplateModal({
	addDDMTemplateURL,
	itemTypes = [],
	namespace,
	onModalClose,
}) {
	const {observer, onClose} = useModal({onClose: () => onModalClose()});

	const [loading, setLoading] = useState(false);

	const [errors, setErrors] = useReducer(
		(value, nextValue) => ({...value, ...nextValue}),
		{itemType: null, name: null}
	);

	const [name, setName] = useState('');
	const [itemType, setItemType] = useState(null);
	const [itemSubtype, setItemSubtype] = useState(null);

	const formRef = useRef(null);

	const handleSubmit = (event) => {
		event.preventDefault();

		const errors = validateFields(name, itemType, itemSubtype);

		if (Object.keys(errors).length > 0) {
			setErrors(errors);

			return;
		}

		setLoading(true);

		const body = Liferay.Util.ns(namespace, {
			classNameId: itemType.value,
			classPK: itemSubtype,
			name,
		});

		fetch(addDDMTemplateURL, {body: objectToFormData(body), method: 'POST'})
			.then((response) => response.json())
			.then((responseContent) => {
				if (responseContent.error) {
					setLoading(false);
					setErrors(responseContent.error);
				}
				else if (responseContent.redirectURL) {
					navigate(responseContent.redirectURL, {
						beforeScreenFlip: onClose,
					});
				}
			})
			.catch(() =>
				setErrors({
					other: Liferay.Language.get(
						'an-unexpected-error-occurred-while-creating-the-template'
					),
				})
			);
	};

	const nameId = `${namespace}name`;
	const itemTypeId = `${namespace}itemType`;
	const itemSubtypeId = `${namespace}itemSubtype`;

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('add-template')}
			</ClayModal.Header>
			<ClayModal.Body>
				{errors.other && (
					<ClayAlert
						displayType="danger"
						onClose={() => {}}
						title={Liferay.Language.get('error')}
					>
						{errors.other}
					</ClayAlert>
				)}
				<ClayForm onSubmit={handleSubmit} ref={formRef}>
					<Field
						errors={errors}
						id={nameId}
						label={Liferay.Language.get('name')}
						name="name"
					>
						<ClayInput
							id={nameId}
							onChange={(event) => {
								setName(event.target.value);

								setErrors({name: null});
							}}
							value={name}
						/>
					</Field>

					<Field
						errors={errors}
						id={itemTypeId}
						label={Liferay.Language.get('item-type')}
						name="itemType"
					>
						<ClaySelect
							id={itemTypeId}
							onChange={(event) => {
								const value = event.target.value;

								const itemType =
									value === -1 ? null : itemTypes[value];

								setItemType(itemType);
								setErrors({itemType: null});

								if (itemType?.subtypes?.length) {
									setItemSubtype(DEFAULT_OPTION.value);
								}
							}}
						>
							<ClaySelect.Option
								label={DEFAULT_OPTION.label}
								value={DEFAULT_OPTION.value}
							/>

							{itemTypes.map((itemType, index) => (
								<ClaySelect.Option
									key={itemType.value}
									label={itemType.label}
									value={index}
								/>
							))}
						</ClaySelect>
					</Field>

					{itemType?.subtypes?.length > 0 && (
						<Field
							errors={errors}
							id={itemSubtypeId}
							label={Liferay.Language.get('item-subtype')}
							name="itemSubtype"
						>
							<ClaySelectWithOption
								id={itemSubtypeId}
								onChange={(event) => {
									setItemSubtype(event.target.value);
									setErrors({itemSubtype: null});
								}}
								options={[DEFAULT_OPTION, ...itemType.subtypes]}
							/>
						</Field>
					)}
				</ClayForm>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton
							displayType="primary"
							onClick={handleSubmit}
						>
							{loading && (
								<span className="inline-item inline-item-before">
									<span
										aria-hidden="true"
										className="loading-animation"
									></span>
								</span>
							)}
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
TemplateModal.propTypes = {
	addDDMTemplateURL: PropTypes.string.isRequired,
	itemTypes: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			subtypes: PropTypes.arrayOf(
				PropTypes.shape({
					label: PropTypes.string,
					value: PropTypes.string,
				})
			),
			value: PropTypes.string,
		})
	),
	namespace: PropTypes.string.isRequired,
	onModalClose: PropTypes.func,
};

const validateFields = (name, itemType, itemSubtype) => {
	const errors = {};

	const errorMessage = Liferay.Language.get('this-field-is-required');

	if (!name) {
		errors.name = errorMessage;
	}

	if (!itemType) {
		errors.itemType = errorMessage;
	}

	if (itemSubtype === DEFAULT_OPTION.value) {
		errors.itemSubtype = errorMessage;
	}

	return errors;
};

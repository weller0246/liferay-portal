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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React from 'react';

import {errorToast} from '../../utils/toast';
import {Input} from '../form/FormBase';
import FileEntryList from './FileEntryList';
import FileSelector from './FileSelector';

const MAX_LENGTH = {
	ATTACHMENTS: 10,
	EMAIL_MESSAGE: 10000,
	RECEIPTS: 10,
};

const DigitalSignatureFormBase = ({
	defaultRecipient,
	errors,
	handleChange,
	setFieldValue,
	values,
}) => {
	const canAddMoreReceipt = values.recipients.length < MAX_LENGTH.RECEIPTS;

	const onAddFileEntry = (fileEntry) => {
		const fileEntryExist = values.fileEntries.find(
			({fileEntryId}) => fileEntryId === fileEntry.fileEntryId
		);

		if (fileEntryExist) {
			return errorToast(
				Liferay.Language.get('the-document-already-exists')
			);
		}

		setFieldValue('fileEntries', [...values.fileEntries, fileEntry]);
	};

	const onAddNewRecipient = () => {
		if (MAX_LENGTH.RECEIPTS && canAddMoreReceipt) {
			setFieldValue('recipients', [
				...values.recipients,
				defaultRecipient,
			]);
		}
	};

	const onRemoveRecipient = (recipientIndex) => {
		setFieldValue(
			'recipients',
			values.recipients.filter((_, index) => index !== recipientIndex)
		);
	};

	return (
		<>
			<Input
				error={errors.envelopeName}
				label={Liferay.Language.get('envelope-name')}
				name="envelopeName"
				onChange={handleChange}
				placeholder={Liferay.Language.get('my-envelope-name')}
				required
			/>

			<FileSelector
				disabled={values.fileEntries.length === MAX_LENGTH.ATTACHMENTS}
				onChange={(_, fileEntry) =>
					onAddFileEntry(JSON.parse(fileEntry))
				}
			/>

			<FileEntryList
				errors={errors.fileEntries}
				fileEntries={values.fileEntries}
				setFieldValue={setFieldValue}
			/>

			{values.recipients.map((recipient, index) => (
				<ClayForm.Group className="recipient" key={index}>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<Input
								className="mb-0"
								error={errors?.recipients?.[index]?.fullName}
								label={Liferay.Language.get(
									'recipient-full-name'
								)}
								name={`recipients[${index}].fullName`}
								onChange={handleChange}
								placeholder={Liferay.Language.get(
									'recipient-full-name'
								)}
								required
								value={recipient.fullName}
							/>
						</ClayInput.GroupItem>
						<ClayInput.GroupItem>
							<Input
								className="mb-0"
								error={errors?.recipients?.[index]?.email}
								label={Liferay.Language.get('recipient-email')}
								name={`recipients[${index}].email`}
								onChange={handleChange}
								placeholder={Liferay.Language.get(
									'recipient-email'
								)}
								required
								value={recipient.email}
							/>
						</ClayInput.GroupItem>
						<ClayInput.GroupItem shrink>
							<ClayButtonWithIcon
								className="recipient-icon-button"
								disabled={values.recipients.length === 1}
								displayType="secondary"
								onClick={() => onRemoveRecipient(index)}
								symbol="trash"
							/>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			))}

			{canAddMoreReceipt && (
				<ClayButton
					className="mb-4"
					displayType="unstyled"
					onClick={onAddNewRecipient}
				>
					<span className="inline-item inline-item-before">
						<ClayIcon symbol="plus" />
					</span>
					<span>{Liferay.Language.get('add-recipient')}</span>
				</ClayButton>
			)}

			<Input
				error={errors.emailSubject}
				label={Liferay.Language.get('email-subject')}
				name="emailSubject"
				onChange={handleChange}
				placeholder={Liferay.Language.get('please-sign-this-document')}
				required
				value={values.emailSubject}
			/>

			<Input
				error={errors.emailMessage}
				feedbackMessage={Liferay.Util.sub(
					Liferay.Language.get('x-characters-remaining'),
					MAX_LENGTH.EMAIL_MESSAGE - values.emailMessage.length
				)}
				label={Liferay.Language.get('email-message')}
				maxLength={MAX_LENGTH.EMAIL_MESSAGE + 1}
				name="emailMessage"
				onChange={handleChange}
				placeholder={Liferay.Language.get('email-message')}
				type="textarea"
				value={values.emailMessage}
			/>
		</>
	);
};

export default DigitalSignatureFormBase;

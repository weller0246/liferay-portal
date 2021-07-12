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

import {fetch, objectToFormData, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import LayoutPreview from './LayoutPreview';
import Sidebar from './Sidebar';
import {StyleBookContextProvider} from './StyleBookContext';
import {config, initializeConfig} from './config';
import {DRAFT_STATUS} from './constants/draftStatusConstants';
import {useCloseProductMenu} from './useCloseProductMenu';

const StyleBookEditor = ({
	frontendTokensValues: initialFrontendTokensValues,
	initialPreviewLayout,
}) => {
	useCloseProductMenu();

	const [frontendTokensValues, setFrontendTokensValues] = useState(
		initialFrontendTokensValues
	);
	const [draftStatus, setDraftStatus] = useState(DRAFT_STATUS.notSaved);
	const [previewLayout, setPreviewLayout] = useState(initialPreviewLayout);

	useEffect(() => {
		if (frontendTokensValues === initialFrontendTokensValues) {
			return;
		}

		setDraftStatus(DRAFT_STATUS.saving);

		saveDraft(frontendTokensValues, config.styleBookEntryId)
			.then(() => {
				setDraftStatus(DRAFT_STATUS.draftSaved);
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}

				setDraftStatus(DRAFT_STATUS.notSaved);

				openToast({
					message: error.message,
					type: 'danger',
				});
			});
	}, [initialFrontendTokensValues, frontendTokensValues]);

	return (
		<StyleBookContextProvider
			value={{
				draftStatus,
				frontendTokensValues,
				previewLayout,
				setFrontendTokensValues,
				setPreviewLayout,
			}}
		>
			<div className="cadmin style-book-editor">
				<LayoutPreview />
				<Sidebar />
			</div>
		</StyleBookContextProvider>
	);
};

export default function ({
	frontendTokenDefinition = [],
	frontendTokensValues = {},
	initialPreviewLayout,
	layoutsTreeURL,
	namespace,
	publishURL,
	redirectURL,
	saveDraftURL,
	styleBookEntryId,
	themeName,
} = {}) {
	initializeConfig({
		frontendTokenDefinition,
		initialPreviewLayout,
		layoutsTreeURL,
		namespace,
		publishURL,
		redirectURL,
		saveDraftURL,
		styleBookEntryId,
		themeName,
	});

	return (
		<StyleBookEditor
			frontendTokensValues={frontendTokensValues}
			initialPreviewLayout={initialPreviewLayout}
		/>
	);
}

function saveDraft(frontendTokensValues, styleBookEntryId) {
	const body = objectToFormData({
		[`${config.namespace}frontendTokensValues`]: JSON.stringify(
			frontendTokensValues
		),
		[`${config.namespace}styleBookEntryId`]: styleBookEntryId,
	});

	return fetch(config.saveDraftURL, {body, method: 'post'})
		.then((response) => {
			return response
				.clone()
				.json()
				.catch(() => response.text())
				.then((body) => [response, body]);
		})
		.then(([response, body]) => {
			if (response.status >= 400 || typeof body !== 'object') {
				throw new Error(
					Liferay.Language.get('an-unexpected-error-occurred')
				);
			}

			if (body.error) {
				throw new Error(body.error);
			}

			return body;
		});
}

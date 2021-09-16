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

import {usePrevious} from '@liferay/frontend-js-react-web';
import React, {useCallback, useContext, useEffect} from 'react';

import {updateFragmentEntryLinkContent} from '../actions/index';
import FragmentService from '../services/FragmentService';
import InfoItemService from '../services/InfoItemService';
import LayoutService from '../services/LayoutService';
import isMappedToInfoItem from '../utils/editable-value/isMappedToInfoItem';
import isMappedToLayout from '../utils/editable-value/isMappedToLayout';
import isMappedToStructure from '../utils/editable-value/isMappedToStructure';
import isNullOrUndefined from '../utils/isNullOrUndefined';
import {useDisplayPagePreviewItem} from './DisplayPagePreviewItemContext';
import {useDispatch} from './StoreContext';

const defaultFromControlsId = (itemId) => itemId;
const defaultToControlsId = (controlId) => controlId;

const DISPLAY_PAGE_CONTENT_FRAGMENT_ENTRY_KEY =
	'com.liferay.fragment.internal.renderer.LayoutDisplayObjectFragmentRenderer';

export const INITIAL_STATE = {
	collectionConfig: null,
	collectionId: null,
	collectionItem: null,
	collectionItemIndex: null,
	customCollectionSelectorURL: null,
	fromControlsId: defaultFromControlsId,
	parentToControlsId: defaultToControlsId,
	setCollectionItemContent: () => null,
	toControlsId: defaultToControlsId,
};

const CollectionItemContext = React.createContext(INITIAL_STATE);

const CollectionItemContextProvider = CollectionItemContext.Provider;

const useCollectionItemIndex = () => {
	const context = useContext(CollectionItemContext);

	return context.collectionItemIndex;
};

const useCustomCollectionSelectorURL = () => {
	const context = useContext(CollectionItemContext);

	return context.customCollectionSelectorURL;
};

const useParentToControlsId = () => {
	const context = useContext(CollectionItemContext);

	return context.parentToControlsId;
};

const useToControlsId = () => {
	const context = useContext(CollectionItemContext);

	return context.toControlsId || defaultToControlsId;
};

const useCollectionConfig = () => {
	const context = useContext(CollectionItemContext);

	return context.collectionConfig;
};

const useGetContent = (fragmentEntryLink, languageId, segmentsExperienceId) => {
	const {
		collectionContent = {},
		content,
		editableValues,
		fragmentEntryKey,
		fragmentEntryLinkId,
	} = fragmentEntryLink;

	const collectionItemContext = useContext(CollectionItemContext);
	const dispatch = useDispatch();
	const fieldSets = fragmentEntryLink.configuration?.fieldSets;
	const toControlsId = useToControlsId();

	const collectionContentId = toControlsId(fragmentEntryLinkId);

	const {className: collectionItemClassName, classPK: collectionItemClassPK} =
		collectionItemContext.collectionItem || {};
	const {collectionItemIndex} = collectionItemContext;

	const {
		className: displayPagePreviewItemClassName,
		classPK: displayPagePreviewItemClassPK,
	} = useDisplayPagePreviewItem()?.data || {};

	const [itemClassName, itemClassPK] =
		fragmentEntryKey === DISPLAY_PAGE_CONTENT_FRAGMENT_ENTRY_KEY &&
		displayPagePreviewItemClassName &&
		displayPagePreviewItemClassPK
			? [displayPagePreviewItemClassName, displayPagePreviewItemClassPK]
			: [collectionItemClassName, collectionItemClassPK];

	const previousEditableValues = usePrevious(editableValues);
	const previousLanguageId = usePrevious(languageId);
	const previousItemClassName = usePrevious(itemClassName);
	const previousItemClassPK = usePrevious(itemClassPK);

	useEffect(() => {
		const hasLocalizable =
			fieldSets?.some((fieldSet) =>
				fieldSet.fields.some((field) => field.localizable)
			) ?? false;

		if (
			editableValues !== previousEditableValues ||
			itemClassName !== previousItemClassName ||
			itemClassPK !== previousItemClassPK ||
			(hasLocalizable && languageId !== previousLanguageId)
		) {
			FragmentService.renderFragmentEntryLinkContent({
				fragmentEntryLinkId,
				itemClassName,
				itemClassPK,
				languageId,
				onNetworkStatus: dispatch,
				segmentsExperienceId,
			}).then(({content}) => {
				dispatch(
					updateFragmentEntryLinkContent({
						collectionContentId,
						content,
						fragmentEntryLinkId,
					})
				);
			});
		}
	}, [
		collectionContentId,
		dispatch,
		editableValues,
		fieldSets,
		fragmentEntryLinkId,
		itemClassName,
		itemClassPK,
		languageId,
		previousEditableValues,
		previousItemClassName,
		previousItemClassPK,
		previousLanguageId,
		segmentsExperienceId,
	]);

	return (
		(!isNullOrUndefined(collectionItemIndex)
			? collectionContent[collectionContentId]
			: null) || content
	);
};

const useGetFieldValue = () => {
	const {collectionItem} = useContext(CollectionItemContext);
	const displayPagePreviewItem = useDisplayPagePreviewItem();

	const getFromServer = useCallback(
		(editable) => {
			if (isMappedToInfoItem(editable)) {
				return InfoItemService.getInfoItemFieldValue({
					...editable,
					onNetworkStatus: () => {},
				}).then((response) => {
					if (!response || !Object.keys(response).length) {
						throw new Error('Field value does not exist');
					}

					const {fieldValue = ''} = response;

					return fieldValue;
				});
			}

			if (isMappedToLayout(editable)) {
				return LayoutService.getLayoutFriendlyURL(editable.layout).then(
					(response) => response.friendlyURL || ''
				);
			}

			if (isMappedToStructure(editable) && displayPagePreviewItem) {
				return InfoItemService.getInfoItemFieldValue({
					...displayPagePreviewItem.data,
					fieldId: editable.mappedField,
					onNetworkStatus: () => {},
				}).then((response) => {
					if (!response || !Object.keys(response).length) {
						throw new Error('Field value does not exist');
					}

					const {fieldValue = ''} = response;

					return fieldValue;
				});
			}

			return Promise.resolve(editable?.defaultValue || editable);
		},
		[displayPagePreviewItem]
	);

	const getFromCollectionItem = useCallback(
		({collectionFieldId}) =>
			!isNullOrUndefined(collectionItem[collectionFieldId])
				? Promise.resolve(collectionItem[collectionFieldId])
				: Promise.reject(),
		[collectionItem]
	);

	if (collectionItem) {
		return getFromCollectionItem;
	}
	else {
		return getFromServer;
	}
};

const useRenderFragmentContent = () => {
	const collectionItemContext = useContext(CollectionItemContext);

	const {className: collectionItemClassName, classPK: collectionItemClassPK} =
		collectionItemContext.collectionItem || {};
	const {collectionItemIndex} = collectionItemContext;

	return useCallback(
		({fragmentEntryLinkId, onNetworkStatus, segmentsExperienceId}) => {
			return FragmentService.renderFragmentEntryLinkContent({
				collectionItemClassName,
				collectionItemClassPK,
				fragmentEntryLinkId,
				onNetworkStatus,
				segmentsExperienceId,
			}).then(({content}) => {
				return {
					collectionItemIndex,
					content,
				};
			});
		},
		[collectionItemClassName, collectionItemClassPK, collectionItemIndex]
	);
};

export {
	CollectionItemContext,
	CollectionItemContextProvider,
	useRenderFragmentContent,
	useGetContent,
	useCollectionConfig,
	useCollectionItemIndex,
	useCustomCollectionSelectorURL,
	useParentToControlsId,
	useToControlsId,
	useGetFieldValue,
};

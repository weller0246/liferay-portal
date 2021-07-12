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

import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import {COLUMN_SIZE_MODULE_PER_ROW_SIZES} from '../../config/constants/columnSizes';
import {config} from '../../config/index';
import {
	CollectionItemContext,
	CollectionItemContextProvider,
	useToControlsId,
} from '../../contexts/CollectionItemContext';
import {useDisplayPagePreviewItem} from '../../contexts/DisplayPagePreviewItemContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';
import CollectionService from '../../services/CollectionService';
import UnsafeHTML from '../UnsafeHTML';
import CollectionPagination from './CollectionPagination';

const COLLECTION_ID_DIVIDER = '$';

function collectionIsMapped(collectionConfig) {
	return collectionConfig.collection;
}

function getCollectionPrefix(collectionId, index) {
	return `collection-${collectionId}-${index}${COLLECTION_ID_DIVIDER}`;
}

export function getToControlsId(collectionId, index, toControlsId) {
	return (itemId) => {
		if (!itemId) {
			return null;
		}

		return toControlsId(
			`${getCollectionPrefix(collectionId, index)}${itemId}`
		);
	};
}

export function fromControlsId(controlsItemId) {
	if (!controlsItemId) {
		return null;
	}

	const splits = controlsItemId.split(COLLECTION_ID_DIVIDER);

	const itemId = splits.pop();

	return itemId || controlsItemId;
}

const NotCollectionSelectedMessage = () => (
	<div className="page-editor__collection__message">
		{Liferay.Language.get('no-collection-selected-yet')}
	</div>
);

const EmptyCollectionMessage = () => (
	<div className="page-editor__collection__message">
		{Liferay.Language.get('there-are-no-items-to-display')}
	</div>
);

const Grid = ({
	child,
	collection,
	collectionConfig,
	collectionId,
	collectionLength,
	customCollectionSelectorURL,
}) => {
	const maxNumberOfItems = Math.min(
		collectionLength,
		config.collectionDisplayFragmentPaginationEnabled &&
			collectionConfig.paginationType
			? collectionConfig.numberOfItemsPerPage
			: collectionConfig.numberOfItems
	);
	const numberOfRows = Math.ceil(
		maxNumberOfItems / collectionConfig.numberOfColumns
	);

	return Array.from({length: numberOfRows}).map((_, i) => (
		<ClayLayout.Row key={`row-${i}`}>
			{Array.from({length: collectionConfig.numberOfColumns}).map(
				(_, j) => {
					const key = `col-${i}-${j}`;
					const index = i * collectionConfig.numberOfColumns + j;

					return (
						<ClayLayout.Col
							key={key}
							size={
								COLUMN_SIZE_MODULE_PER_ROW_SIZES[
									collectionConfig.numberOfColumns
								][collectionConfig.numberOfColumns][j]
							}
						>
							{index < maxNumberOfItems && (
								<ColumnContext
									collectionConfig={collectionConfig}
									collectionId={collectionId}
									collectionItem={collection[index]}
									customCollectionSelectorURL={
										customCollectionSelectorURL
									}
									index={index}
								>
									{React.cloneElement(child)}
								</ColumnContext>
							)}
						</ClayLayout.Col>
					);
				}
			)}
		</ClayLayout.Row>
	));
};

const ColumnContext = ({
	children,
	collectionConfig,
	collectionId,
	collectionItem,
	customCollectionSelectorURL,
	index,
}) => {
	const toControlsId = useToControlsId();

	const contextValue = useMemo(
		() => ({
			collectionConfig,
			collectionItem,
			collectionItemIndex: index,
			customCollectionSelectorURL,
			fromControlsId,
			parentToControlsId: toControlsId,
			toControlsId: getToControlsId(collectionId, index, toControlsId),
		}),
		[
			collectionConfig,
			collectionId,
			collectionItem,
			index,
			toControlsId,
			customCollectionSelectorURL,
		]
	);

	return (
		<CollectionItemContextProvider value={contextValue}>
			{children}
		</CollectionItemContextProvider>
	);
};

const DEFAULT_COLLECTION = {
	fakeCollection: true,
	items: [{}],
	length: 1,
};

const Collection = React.forwardRef(({children, item}, ref) => {
	const child = React.Children.toArray(children)[0];
	const collectionConfig = item.config;

	const dispatch = useDispatch();
	const languageId = useSelector(selectLanguageId);

	const [activePage, setActivePage] = useState(1);
	const [collection, setCollection] = useState(DEFAULT_COLLECTION);
	const [loading, setLoading] = useState(false);

	const totalPages = Math.ceil(
		collectionConfig.numberOfItems / collectionConfig.numberOfItemsPerPage
	);

	useEffect(() => {
		if (activePage > totalPages) {
			setActivePage(1);
		}
	}, [
		collectionConfig.numberOfItems,
		collectionConfig.numberOfItemsPerPage,
		activePage,
		totalPages,
	]);

	const context = useContext(CollectionItemContext);
	const {classNameId, classPK} = context.collectionItem || {};

	const displayPagePreviewItemData = useDisplayPagePreviewItem()?.data ?? {};

	const itemClassNameId =
		classNameId || displayPagePreviewItemData.classNameId;
	const itemClassPK = classPK || displayPagePreviewItemData.classPK;

	useEffect(() => {
		if (
			config.collectionDisplayFragmentPaginationEnabled
				? collectionConfig.collection && activePage <= totalPages
				: collectionConfig.collection
		) {
			setLoading(true);

			CollectionService.getCollectionField({
				activePage,
				classNameId: itemClassNameId,
				classPK: itemClassPK,
				collection: collectionConfig.collection,
				languageId,
				listItemStyle: collectionConfig.listItemStyle || null,
				listStyle: collectionConfig.listStyle,
				numberOfItems: collectionConfig.numberOfItems,
				numberOfItemsPerPage: collectionConfig.numberOfItemsPerPage,
				onNetworkStatus: dispatch,
				paginationType: config.collectionDisplayFragmentPaginationEnabled
					? collectionConfig.paginationType
					: '',
				templateKey: collectionConfig.templateKey || null,
			})
				.then((response) => {
					setCollection(
						response.length > 0 && response.items?.length > 0
							? response
							: {...response, ...DEFAULT_COLLECTION}
					);
				})
				.catch((error) => {
					if (process.env.NODE_ENV === 'development') {
						console.error(error);
					}
				})
				.finally(() => {
					setLoading(false);
				});
		}
	}, [
		activePage,
		itemClassNameId,
		itemClassPK,
		collectionConfig.collection,
		collectionConfig.listItemStyle,
		collectionConfig.listStyle,
		collectionConfig.numberOfItems,
		collectionConfig.numberOfItemsPerPage,
		collectionConfig.paginationType,
		collectionConfig.templateKey,
		dispatch,
		languageId,
		totalPages,
	]);

	const showEmptyMessage =
		collectionConfig.listStyle !== '' && collection.fakeCollection;

	return (
		<div className="page-editor__collection" ref={ref}>
			{loading ? (
				<ClayLoadingIndicator />
			) : !collectionIsMapped(collectionConfig) ? (
				<NotCollectionSelectedMessage />
			) : showEmptyMessage ? (
				<EmptyCollectionMessage />
			) : collection.content ? (
				<UnsafeHTML markup={collection.content} />
			) : (
				<Grid
					child={child}
					collection={collection.items}
					collectionConfig={collectionConfig}
					collectionId={item.itemId}
					collectionLength={collection.items.length}
					customCollectionSelectorURL={
						collection.customCollectionSelectorURL
					}
				/>
			)}

			{config.collectionDisplayFragmentPaginationEnabled &&
				collectionConfig.paginationType && (
					<CollectionPagination
						activePage={activePage}
						collectionConfig={collectionConfig}
						collectionId={item.itemId}
						onPageChange={setActivePage}
						totalNumberOfItems={collection.totalNumberOfItems || 0}
						totalPages={totalPages}
					/>
				)}
		</div>
	);
});

Collection.displayName = 'Collection';

export default Collection;

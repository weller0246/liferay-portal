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

import ClayForm, {
	ClayCheckbox,
	ClayInput,
	ClaySelect,
	ClaySelectWithOption,
} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import {COLLECTION_APPLIED_FILTERS_FRAGMENT_ENTRY_KEY} from '../../../../../../app/config/constants/collectionAppliedFiltersFragmentKey';
import {COLLECTION_FILTER_FRAGMENT_ENTRY_KEY} from '../../../../../../app/config/constants/collectionFilterFragmentEntryKey';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../app/config/constants/layoutDataItemTypes';
import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useGetState,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import CollectionService from '../../../../../../app/services/CollectionService';
import InfoItemService from '../../../../../../app/services/InfoItemService';
import updateCollectionDisplayCollection from '../../../../../../app/thunks/updateCollectionDisplayCollection';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import {useId} from '../../../../../../app/utils/useId';
import CollectionSelector from '../../../../../../common/components/CollectionSelector';
import CollectionFilterConfigurationModal from '../CollectionFilterConfigurationModal';

const LAYOUT_OPTIONS = [
	{label: Liferay.Language.get('full-width'), value: '1'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 2), value: '2'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 3), value: '3'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 4), value: '4'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 5), value: '5'},
	{label: Liferay.Util.sub(Liferay.Language.get('x-columns'), 6), value: '6'},
];

const PAGINATION_TYPE_OPTIONS = [
	{label: Liferay.Language.get('none'), value: ''},
	{label: Liferay.Language.get('numeric'), value: 'numeric'},
	{label: Liferay.Language.get('simple'), value: 'simple'},
];

const LIST_STYLE_GRID = '';

const DEFAULT_LIST_STYLE = {
	label: Liferay.Language.get('grid'),
	value: LIST_STYLE_GRID,
};

const ERROR_MESSAGES = {
	maximumItems: Liferay.Language.get(
		'the-current-number-of-items-in-this-collection-is-x'
	),
	maximumItemsPerPage: Liferay.Language.get(
		'you-can-only-display-a-maximum-of-x-items-per-page'
	),
	neededItem: Liferay.Language.get(
		'you-need-at-least-one-item-to-use-this-configuration'
	),
	noItems: Liferay.Language.get('this-collection-has-no-items'),
};

export const CollectionGeneralPanel = ({item}) => {
	const [availableListItemStyles, setAvailableListItemStyles] = useState([]);
	const [availableListStyles, setAvailableListStyles] = useState([
		DEFAULT_LIST_STYLE,
	]);
	const [collectionConfiguration, setCollectionConfiguration] = useState(
		null
	);
	const collectionItemType = item.config.collection?.itemType || null;
	const collectionLayoutId = useId();
	const collectionListItemStyleId = useId();
	const collectionNumberOfItemsId = useId();
	const collectionNumberOfItemsPerPageId = useId();
	const collectionPaginationTypeId = useId();
	const dispatch = useDispatch();
	const getState = useGetState();
	const isMaximumValuePerPageError =
		item.config.numberOfItemsPerPage > config.searchContainerPageMaxDelta;
	const isMounted = useIsMounted();
	const listStyleId = useId();
	const [nextValue, setNextValue] = useState({
		numberOfItems: item.config.numberOfItems,
		numberOfItemsPerPage: item.config.numberOfItemsPerPage,
	});
	const [showAllItems, setShowAllItems] = useState(item.config.showAllItems);
	const [totalNumberOfItems, setTotalNumberOfItems] = useState(0);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const [numberOfItemsError, setNumberOfItemsError] = useState(null);
	const [numberOfItemsPerPageError, setNumberOfItemsPerPageError] = useState(
		null
	);

	const {
		observer: filterConfigurationObserver,
		onClose: onFilterConfigurationClose,
	} = useModal({onClose: () => setFilterConfigurationVisible(false)});

	const [
		filterConfigurationVisible,
		setFilterConfigurationVisible,
	] = useState(false);

	const optionsMenuItems = useMemo(
		() =>
			collectionConfiguration
				? [
						{
							label: Liferay.Language.get('filter-collection'),
							onClick: () => setFilterConfigurationVisible(true),
						},
				  ]
				: [],
		[collectionConfiguration, setFilterConfigurationVisible]
	);

	const handleCollectionListItemStyleChanged = ({target}) => {
		const options = target.options;

		handleConfigurationChanged({
			listItemStyle: options[target.selectedIndex].dataset.key,
			templateKey: options[target.selectedIndex].dataset.templateKey,
		});
	};

	const handleCollectionNumberOfItemsBlurred = (event) => {
		if (Number(nextValue.numberOfItems) !== item.config.numberOfItems) {
			handleConfigurationChanged({
				numberOfItems: Number(event.target.value),
			});
		}
	};

	const handleCollectionNumberOfItemsChanged = (event) => {
		setNextValue({
			...nextValue,
			numberOfItems: event.target.value,
		});

		if (showAllItems) {
			setShowAllItems(false);
		}
	};

	const handleCollectionNumberOfItemsPerPageBlurred = (event) => {
		if (
			nextValue.numberOfItemsPerPage !== item.config.numberOfItemsPerPage
		) {
			handleConfigurationChanged({
				numberOfItemsPerPage: Number(event.target.value),
			});
		}
	};

	const handleCollectionNumberOfItemsPerPageChanged = (event) =>
		setNextValue({
			...nextValue,
			numberOfItemsPerPage: event.target.value,
		});

	const handleCollectionSelect = (collection = {}) => {
		dispatch(
			updateCollectionDisplayCollection({
				collection: Object.keys(collection).length ? collection : null,
				itemId: item.itemId,
				listStyle: LIST_STYLE_GRID,
			})
		);
	};

	const shouldPreventCollectionSelect = () => {
		const state = getState();

		const isLinkedToFilter = Object.values(state.layoutData.items).some(
			(layoutDataItem) => {
				if (layoutDataItem.type !== LAYOUT_DATA_ITEM_TYPES.fragment) {
					return false;
				}

				const fragmentEntryLink =
					state.fragmentEntryLinks[
						layoutDataItem.config.fragmentEntryLinkId
					];

				if (
					fragmentEntryLink.fragmentEntryKey !==
						COLLECTION_FILTER_FRAGMENT_ENTRY_KEY &&
					fragmentEntryLink.fragmentEntryKey !==
						COLLECTION_APPLIED_FILTERS_FRAGMENT_ENTRY_KEY
				) {
					return false;
				}

				return fragmentEntryLink.editableValues[
					FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
				]?.targetCollections?.includes(item.itemId);
			}
		);

		return (
			isLinkedToFilter &&
			!confirm(
				`${Liferay.Language.get(
					'if-you-change-the-collection-you-unlink-the-collection-filter'
				)}\n\n${Liferay.Language.get('do-you-want-to-continue')}`
			)
		);
	};

	const handleConfigurationChanged = useCallback(
		(itemConfig) => {
			dispatch(
				updateItemConfig({
					itemConfig,
					itemId: item.itemId,
					segmentsExperienceId,
				})
			);
		},
		[item.itemId, dispatch, segmentsExperienceId]
	);

	const handleShowAllItemsChanged = (event) => {
		setShowAllItems(event.target.checked);

		setNextValue({
			...nextValue,
			numberOfItems: totalNumberOfItems,
		});

		handleConfigurationChanged({
			numberOfItems: totalNumberOfItems,
			showAllItems: event.target.checked,
		});
	};

	useEffect(() => {
		let errorMessage = null;

		if (totalNumberOfItems) {
			if (item.config.numberOfItems > totalNumberOfItems) {
				errorMessage = Liferay.Util.sub(
					ERROR_MESSAGES.maximumItems,
					totalNumberOfItems
				);
			}
			else if (item.config.numberOfItems < 1) {
				errorMessage = ERROR_MESSAGES.neededItem;
			}
		}
		else {
			errorMessage = ERROR_MESSAGES.noItems;
		}

		setNumberOfItemsError(errorMessage);
	}, [totalNumberOfItems, item.config.numberOfItems]);

	useEffect(() => {
		let errorMessage = null;

		if (isMaximumValuePerPageError) {
			errorMessage = Liferay.Util.sub(
				ERROR_MESSAGES.maximumItemsPerPage,
				config.searchContainerPageMaxDelta
			);
		}
		else if (item.config.numberOfItemsPerPage < 1) {
			errorMessage = ERROR_MESSAGES.neededItem;
		}

		setNumberOfItemsPerPageError(errorMessage);
	}, [isMaximumValuePerPageError, item.config.numberOfItemsPerPage]);

	useEffect(() => {
		if (collectionItemType) {
			InfoItemService.getAvailableListRenderers({
				className: collectionItemType,
			})
				.then((response) => {
					setAvailableListStyles([
						DEFAULT_LIST_STYLE,
						{
							label: Liferay.Language.get('templates'),
							options: response,
							type: 'group',
						},
					]);
				})
				.catch(() => {
					setAvailableListStyles([DEFAULT_LIST_STYLE]);
				});
		}
	}, [collectionItemType]);

	useEffect(() => {
		if (item.config.collection) {
			CollectionService.getCollectionItemCount({
				collection: item.config.collection,
				onNetworkStatus: () => {},
			}).then(({totalNumberOfItems}) => {
				if (isMounted()) {
					setTotalNumberOfItems(totalNumberOfItems);

					if (showAllItems) {
						handleConfigurationChanged({
							numberOfItems: totalNumberOfItems,
						});

						setNextValue((prevValue) => ({
							...prevValue,
							numberOfItems: totalNumberOfItems,
						}));
					}
				}
			});
		}
	}, [
		item.config.collection,
		isMounted,
		showAllItems,
		handleConfigurationChanged,
	]);

	useEffect(() => {
		if (
			item.config.collection &&
			item.config.listStyle &&
			item.config.listStyle !== LIST_STYLE_GRID
		) {
			InfoItemService.getAvailableListItemRenderers({
				itemSubtype: item.config.collection.itemSubtype,
				itemType: item.config.collection.itemType,
				listStyle: item.config.listStyle,
			})
				.then((response) => {
					setAvailableListItemStyles(response);
				})
				.catch(() => {
					setAvailableListItemStyles([]);
				});
		}
	}, [item.config.collection, item.config.listStyle]);

	useEffect(() => {
		if (item.config.collection?.key) {
			CollectionService.getCollectionConfiguration(item.config.collection)
				.then((nextCollectionConfiguration) => {
					if (
						nextCollectionConfiguration?.fieldSets.some(
							(fieldSet) => fieldSet?.fields?.length
						)
					) {
						setCollectionConfiguration(nextCollectionConfiguration);
					}
					else {
						setCollectionConfiguration(null);
					}
				})
				.catch(() => setCollectionConfiguration(null));
		}
		else {
			setCollectionConfiguration(null);
		}
	}, [item.config.collection]);

	return (
		<>
			<CollectionSelector
				collectionItem={item.config.collection}
				itemSelectorURL={config.collectionSelectorURL}
				label={Liferay.Language.get('collection')}
				onCollectionSelect={handleCollectionSelect}
				optionsMenuItems={optionsMenuItems}
				shouldPreventCollectionSelect={shouldPreventCollectionSelect}
			/>
			{item.config.collection && (
				<>
					<ClayForm.Group small>
						<label htmlFor={listStyleId}>
							{Liferay.Language.get('list-style')}
						</label>
						<ClaySelectWithOption
							aria-label={Liferay.Language.get('list-style')}
							id={listStyleId}
							onChange={(event) =>
								handleConfigurationChanged({
									listStyle: event.target.value,
								})
							}
							options={availableListStyles}
							value={item.config.listStyle}
						/>
					</ClayForm.Group>

					{item.config.listStyle === LIST_STYLE_GRID && (
						<ClayForm.Group small>
							<label htmlFor={collectionLayoutId}>
								{Liferay.Language.get('layout')}
							</label>
							<ClaySelectWithOption
								aria-label={Liferay.Language.get('layout')}
								id={collectionLayoutId}
								onChange={(event) =>
									handleConfigurationChanged({
										numberOfColumns: event.target.value,
									})
								}
								options={LAYOUT_OPTIONS}
								value={item.config.numberOfColumns}
							/>
						</ClayForm.Group>
					)}

					{item.config.listStyle !== LIST_STYLE_GRID &&
						availableListItemStyles.length > 0 && (
							<ClayForm.Group small>
								<label htmlFor={collectionListItemStyleId}>
									{Liferay.Language.get('list-item-style')}
								</label>
								<ClaySelect
									aria-label={Liferay.Language.get(
										'list-item-style'
									)}
									id={collectionListItemStyleId}
									onChange={
										handleCollectionListItemStyleChanged
									}
								>
									<ListItemStylesOptions
										item={item}
										listItemStyles={availableListItemStyles}
									/>
								</ClaySelect>
							</ClayForm.Group>
						)}

					<ClayForm.Group small>
						<label htmlFor={collectionPaginationTypeId}>
							{Liferay.Language.get('pagination')}
						</label>
						<ClaySelectWithOption
							aria-label={Liferay.Language.get('pagination')}
							id={collectionPaginationTypeId}
							onChange={(event) =>
								handleConfigurationChanged({
									paginationType: event.target.value,
								})
							}
							options={PAGINATION_TYPE_OPTIONS}
							value={item.config.paginationType}
						/>
					</ClayForm.Group>

					{item.config.paginationType && (
						<div className="mb-1 pt-1">
							<ClayCheckbox
								checked={showAllItems}
								label={Liferay.Language.get(
									'display-all-collection-items'
								)}
								onChange={handleShowAllItemsChanged}
							/>
						</div>
					)}

					<ClayForm.Group
						className={classNames({
							'has-warning': numberOfItemsError,
						})}
						small
					>
						<label htmlFor={collectionNumberOfItemsId}>
							{Liferay.Language.get('maximum-number-of-items')}
						</label>
						<ClayInput
							id={collectionNumberOfItemsId}
							min="1"
							onBlur={handleCollectionNumberOfItemsBlurred}
							onChange={handleCollectionNumberOfItemsChanged}
							type="number"
							value={nextValue.numberOfItems}
						/>

						{numberOfItemsError && (
							<FeedbackMessage message={numberOfItemsError} />
						)}
					</ClayForm.Group>

					{item.config.paginationType && (
						<ClayForm.Group
							className={classNames({
								'has-warning': numberOfItemsPerPageError,
							})}
							small
						>
							<label htmlFor={collectionNumberOfItemsPerPageId}>
								{Liferay.Language.get(
									'maximum-number-of-items-per-page'
								)}
							</label>
							<ClayInput
								id={collectionNumberOfItemsPerPageId}
								min="1"
								onBlur={
									handleCollectionNumberOfItemsPerPageBlurred
								}
								onChange={
									handleCollectionNumberOfItemsPerPageChanged
								}
								type="number"
								value={nextValue.numberOfItemsPerPage}
							/>

							<div className="mb-2 mt-2">
								<span
									className={classNames(
										'mr-1 small',
										isMaximumValuePerPageError &&
											numberOfItemsPerPageError
											? 'text-warning'
											: 'text-secondary',
										{
											'font-weight-bold':
												isMaximumValuePerPageError &&
												numberOfItemsPerPageError,
										}
									)}
								>
									{Liferay.Util.sub(
										Liferay.Language.get('x-items-maximum'),
										config.searchContainerPageMaxDelta
									)}
								</span>

								{numberOfItemsPerPageError && (
									<FeedbackMessage
										message={numberOfItemsPerPageError}
									/>
								)}
							</div>
						</ClayForm.Group>
					)}
				</>
			)}

			{filterConfigurationVisible ? (
				<CollectionFilterConfigurationModal
					collectionConfiguration={collectionConfiguration}
					handleConfigurationChanged={handleConfigurationChanged}
					itemConfig={item.config}
					observer={filterConfigurationObserver}
					onClose={onFilterConfigurationClose}
				/>
			) : null}
		</>
	);
};

const ListItemStylesOptions = ({item, listItemStyles}) =>
	listItemStyles.map((listItemStyle) =>
		listItemStyle.templates ? (
			<ClaySelect.OptGroup
				key={listItemStyle.label}
				label={listItemStyle.label}
			>
				{listItemStyle.templates.map((template) => (
					<ClaySelect.Option
						data-key={template.key}
						data-template-key={template.templateKey}
						key={`${template.key}_${template.templateKey}`}
						label={template.label}
						selected={
							item.config.listItemStyle === template.key &&
							(!item.config.templateKey ||
								item.config.templateKey ===
									template.templateKey)
						}
					/>
				))}
			</ClaySelect.OptGroup>
		) : (
			<ClaySelect.Option
				data-key={listItemStyle.key}
				key={listItemStyle.label}
				label={listItemStyle.label}
				selected={item.config.listItemStyle === listItemStyle.key}
			/>
		)
	);

const FeedbackMessage = ({message}) => (
	<div className="autofit-row mt-2 small text-warning">
		<div className="autofit-col">
			<div className="autofit-section mr-2">
				<ClayIcon symbol="warning-full" />
			</div>
		</div>
		<div className="autofit-col autofit-col-expand">
			<div className="autofit-section">{message}</div>
		</div>
	</div>
);

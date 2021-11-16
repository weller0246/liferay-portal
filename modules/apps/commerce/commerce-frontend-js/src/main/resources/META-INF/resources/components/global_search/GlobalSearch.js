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

import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {debouncePromise} from '../../utilities/debounce';
import {formatActionUrl, getRandomId} from '../../utilities/index';
import {showErrorNotification} from '../../utilities/notifications';

const OrderResource = ServiceProvider.AdminOrderAPI('v1');
const AccountsResource = ServiceProvider.AdminAccountAPI('v1');
const CatalogResource = ServiceProvider.DeliveryCatalogAPI('v1');

function SearchItem({className, href, label, stickerShape, thumbnailUrl}) {
	return (
		<ClayDropDown.Item className={className} href={href}>
			<ClaySticker className="mr-2" shape={stickerShape}>
				<ClaySticker.Image alt={label} src={thumbnailUrl} />
			</ClaySticker>

			{label}
		</ClayDropDown.Item>
	);
}

const composeDataUpdate = (
	promise,
	isMounted,
	updateData,
	updateCounter,
	updateLoader
) => {
	return promise
		.then((response) => {
			if (isMounted()) {
				updateData(response.items);
				updateCounter(response.totalCount);
			}
		})
		.catch((error) => {
			showErrorNotification(error.message);
			updateData(null);
			updateCounter(null);
		})
		.finally(() => {
			if (isMounted()) {
				updateLoader(false);
			}
		});
};

function GlobalSearch(props) {
	const isMounted = useIsMounted();
	const inputRef = useRef(null);
	const dropdownRef = useRef(null);
	const [accountsLoading, setAccountsLoading] = useState(false);
	const [accounts, setAccounts] = useState(null);
	const [accountsCount, setAccountsCount] = useState(null);
	const [active, setActive] = useState(false);
	const [debouncedGetAccounts, setDebouncedGetAccounts] = useState(null);
	const [debouncedGetOrders, setDebouncedGetOrders] = useState(null);
	const [debouncedGetProducts, setDebouncedGetProducts] = useState(null);
	const [ids] = useState({
		input: 'global-search-input' + getRandomId(),
		menu: 'global-search-menu' + getRandomId(),
	});
	const [orders, setOrders] = useState(null);
	const [ordersCount, setOrdersCount] = useState(null);
	const [ordersLoading, setOrdersLoading] = useState(false);
	const [products, setProducts] = useState(null);
	const [productsCount, setProductsCount] = useState(null);
	const [productsLoading, setProductsLoading] = useState(false);
	const [query, setQuery] = useState('');

	useEffect(() => {
		setDebouncedGetAccounts(() =>
			debouncePromise(
				AccountsResource.getAccounts,
				props.fetchDataDebounce
			)
		);
		setDebouncedGetOrders(() =>
			debouncePromise(OrderResource.getOrders, props.fetchDataDebounce)
		);
		setDebouncedGetProducts(() =>
			debouncePromise(
				CatalogResource.getProductsByChannelId,
				props.fetchDataDebounce
			)
		);
	}, [props.fetchDataDebounce]);

	const getProducts = useCallback(() => {
		composeDataUpdate(
			debouncedGetProducts(props.channelId, null, {
				pageSize: props.resultsPageSize,
				search: query,
			}),
			isMounted,
			setProducts,
			setProductsCount,
			setProductsLoading
		);
	}, [
		debouncedGetProducts,
		props.channelId,
		props.resultsPageSize,
		query,
		isMounted,
	]);

	const getAccounts = useCallback(() => {
		composeDataUpdate(
			debouncedGetAccounts(null, {
				pageSize: props.resultsPageSize,
				search: query,
			}),
			isMounted,
			setAccounts,
			setAccountsCount,
			setAccountsLoading
		);
	}, [debouncedGetAccounts, props.resultsPageSize, query, isMounted]);

	const getOrders = useCallback(() => {
		composeDataUpdate(
			debouncedGetOrders(null, {
				pageSize: props.resultsPageSize,
				search: query,
			}),
			isMounted,
			setOrders,
			setOrdersCount,
			setOrdersLoading
		);
	}, [debouncedGetOrders, props.resultsPageSize, query, isMounted]);

	function resetContent() {
		setAccounts(null);
		setOrders(null);
		setProducts(null);
		setAccountsCount(null);
		setOrdersCount(null);
		setProductsCount(null);
	}

	useEffect(() => {
		setActive(Boolean(query));

		if (!query) {
			resetContent();

			return;
		}

		setProductsLoading(true);
		setAccountsLoading(true);
		setOrdersLoading(true);

		getProducts();
		getAccounts();
		getOrders();
	}, [query, getProducts, getAccounts, getOrders]);

	useEffect(() => {
		function handleClick(event) {
			if (
				!(
					event.target.closest(`#${ids.menu}`) ||
					event.target.id === ids.input
				)
			) {
				setActive(false);
			}
		}

		if (active) {
			document.addEventListener('click', handleClick);
		}
		else {
			document.removeEventListener('click', handleClick);
		}

		return () => {
			document.removeEventListener('click', handleClick);
		};
	}, [active, ids]);

	return (
		<>
			<ClayInput
				id={ids.input}
				onChange={(event) => setQuery(event.target.value)}
				onClick={() => {
					if (query) {
						setActive(true);
					}
				}}
				placeholder={Liferay.Language.get('search')}
				ref={inputRef}
				value={query}
			/>

			<ClayDropDown.Menu
				active={active}
				alignElementRef={inputRef}
				className="dropdown-wide"
				id={ids.menu}
				onSetActive={() => setActive(false)}
				ref={dropdownRef}
			>
				<ClayDropDown.ItemList>
					<ClayDropDown.Group
						header={Liferay.Language.get('catalog')}
					>
						{!productsLoading ? (
							products?.length ? (
								<>
									{products.map((product) => (
										<SearchItem
											className="product-item"
											href={formatActionUrl(
												props.productURLTemplate,
												product
											)}
											key={product.id}
											label={product.name}
											thumbnailUrl={product.urlImage}
										/>
									))}
									{productsCount > products.length && (
										<ClayDropDown.Item
											href={formatActionUrl(
												props.productsSearchURLTemplate,
												{
													query,
												}
											)}
										>
											{Liferay.Util.sub(
												Liferay.Language.get(
													'search-x-in-catalog'
												),
												query
											)}
										</ClayDropDown.Item>
									)}
								</>
							) : (
								<ClayDropDown.Item>
									{Liferay.Language.get(
										'no-products-were-found'
									)}
								</ClayDropDown.Item>
							)
						) : (
							<ClayDropDown.Item>
								<ClayLoadingIndicator small />
							</ClayDropDown.Item>
						)}
					</ClayDropDown.Group>

					<ClayDropDown.Group header={Liferay.Language.get('orders')}>
						{!ordersLoading ? (
							orders?.length ? (
								<>
									{orders.map((order) => (
										<ClayDropDown.Item
											className="order-item"
											href={formatActionUrl(
												props.orderURLTemplate,
												order
											)}
											key={order.id}
										>
											{order.id}
										</ClayDropDown.Item>
									))}
									{ordersCount > orders.length && (
										<ClayDropDown.Item
											href={formatActionUrl(
												props.ordersSearchURLTemplate,
												{
													query,
												}
											)}
										>
											{Liferay.Util.sub(
												Liferay.Language.get(
													'search-x-in-orders'
												),
												query
											)}
										</ClayDropDown.Item>
									)}
								</>
							) : (
								<ClayDropDown.Item>
									{Liferay.Language.get(
										'no-orders-were-found'
									)}
								</ClayDropDown.Item>
							)
						) : (
							<ClayDropDown.Item>
								<ClayLoadingIndicator small />
							</ClayDropDown.Item>
						)}
					</ClayDropDown.Group>

					<ClayDropDown.Group
						header={Liferay.Language.get('accounts')}
					>
						{!accountsLoading ? (
							accounts?.length ? (
								<>
									{accounts.map((account) => (
										<SearchItem
											className="account-item"
											href={formatActionUrl(
												props.accountURLTemplate,
												account
											)}
											key={account.id}
											label={account.name}
											stickerShape="circle"
											thumbnailUrl={account.logoURL}
										/>
									))}
									{accountsCount > accounts.length && (
										<ClayDropDown.Item
											href={formatActionUrl(
												props.accountsSearchURLTemplate,
												{
													query,
												}
											)}
										>
											{Liferay.Util.sub(
												Liferay.Language.get(
													'search-x-in-accounts'
												),
												query
											)}
										</ClayDropDown.Item>
									)}
								</>
							) : (
								<ClayDropDown.Item>
									{Liferay.Language.get(
										'no-accounts-were-found'
									)}
								</ClayDropDown.Item>
							)
						) : (
							<ClayDropDown.Item>
								<ClayLoadingIndicator small />
							</ClayDropDown.Item>
						)}
					</ClayDropDown.Group>

					<ClayDropDown.Divider />

					<ClayDropDown.Item
						href={formatActionUrl(props.globalSearchURLTemplate, {
							query,
						})}
					>
						{Liferay.Language.get('more-global-results')}
					</ClayDropDown.Item>
				</ClayDropDown.ItemList>
			</ClayDropDown.Menu>
		</>
	);
}

GlobalSearch.propTypes = {
	accountURLTemplate: PropTypes.string.isRequired,
	accountsSearchURLTemplate: PropTypes.string.isRequired,
	channelId: PropTypes.number.isRequired,
	fetchDataDebounce: PropTypes.number.isRequired,
	globalSearchURLTemplate: PropTypes.string.isRequired,
	orderURLTemplate: PropTypes.string.isRequired,
	ordersSearchURLTemplate: PropTypes.string.isRequired,
	productURLTemplate: PropTypes.string.isRequired,
	productsSearchURLTemplate: PropTypes.string.isRequired,
	resultsPageSize: PropTypes.number.isRequired,
};

GlobalSearch.defaultProps = {
	fetchDataDebounce: 300,
	resultsPageSize: 4,
};

export default GlobalSearch;

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

package com.liferay.commerce.internal.model.listener;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.account.exception.CommerceAccountOrdersException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, immediate = true, service = ModelListener.class)
public class AccountEntryModelListener extends BaseModelListener<AccountEntry> {

	@Override
	public void onAfterUpdate(
			AccountEntry originalAccountEntry, AccountEntry accountEntry)
		throws ModelListenerException {

		try {
			if (StringUtil.compare(
					originalAccountEntry.getName(), accountEntry.getName())) {

				return;
			}

			List<CommerceOrder> commerceOrders =
				_commerceOrderLocalService.getCommerceOrdersByCommerceAccountId(
					accountEntry.getAccountEntryId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			Indexer<CommerceOrder> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(CommerceOrder.class);

			for (CommerceOrder commerceOrder : commerceOrders) {
				indexer.reindex(commerceOrder);
			}
		}
		catch (SearchException searchException) {
			throw new ModelListenerException(searchException);
		}
	}

	@Override
	public void onBeforeRemove(AccountEntry accountEntry) {
		int accountOrders =
			_commerceOrderLocalService.
				getCommerceOrdersCountByCommerceAccountId(
					accountEntry.getAccountEntryId());

		if (accountOrders > 0) {
			throw new CommerceAccountOrdersException();
		}

		_commerceShippingOptionAccountEntryRelLocalService.
			deleteCommerceShippingOptionAccountEntryRelsByAccountEntryId(
				accountEntry.getAccountEntryId());
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceShippingOptionAccountEntryRelLocalService
		_commerceShippingOptionAccountEntryRelLocalService;

}
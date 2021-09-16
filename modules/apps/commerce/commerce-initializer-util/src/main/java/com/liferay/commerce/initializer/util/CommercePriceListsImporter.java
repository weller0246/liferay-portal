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

package com.liferay.commerce.initializer.util;

import com.liferay.commerce.account.exception.NoSuchAccountGroupException;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalService;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(enabled = false, service = CommercePriceListsImporter.class)
public class CommercePriceListsImporter {

	public void importCommercePriceLists(
			long catalogGroupId, JSONArray jsonArray, long scopeGroupId,
			long userId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		for (int i = 0; i < jsonArray.length(); i++) {
			_importCommercePriceList(
				catalogGroupId, jsonArray.getJSONObject(i), serviceContext);
		}
	}

	private void _importCommercePriceList(
			long catalogGroupId, JSONObject jsonObject,
			ServiceContext serviceContext)
		throws PortalException {

		String currencyCode = jsonObject.getString("currencyCode");

		if (Validator.isNull(currencyCode)) {

			// TODO Throw an exception

			return;
		}

		long parentPriceListId = 0;

		String parentPriceListName = jsonObject.getString("parentPriceList");

		if (!Validator.isBlank(parentPriceListName)) {
			String externalReferenceCode = FriendlyURLNormalizerUtil.normalize(
				parentPriceListName);

			CommercePriceList parentPriceList =
				_commercePriceListLocalService.
					fetchCommercePriceListByReferenceCode(
						serviceContext.getCompanyId(), externalReferenceCode);

			parentPriceListId = parentPriceList.getParentCommercePriceListId();
		}

		String name = jsonObject.getString("name");

		if (Validator.isBlank(name)) {

			// TODO Throw an exception

			return;
		}

		User user = _userLocalService.getUser(serviceContext.getUserId());

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

		int displayDateMonth = displayCalendar.get(
			jsonObject.getInt("displayDateMonth", Calendar.MONTH));
		int displayDateDay = displayCalendar.get(
			jsonObject.getInt("displayDateDayOfMonth", Calendar.DAY_OF_MONTH));
		int displayDateYear = displayCalendar.get(
			jsonObject.getInt("displayDateYear", Calendar.YEAR));
		int displayDateHour = displayCalendar.get(
			jsonObject.getInt("displayDateHour", Calendar.HOUR));
		int displayDateMinute = displayCalendar.get(
			jsonObject.getInt("displayDateMinute", Calendar.MINUTE));
		int displayDateAmPm = displayCalendar.get(
			jsonObject.getInt("displayDateAmPm", Calendar.AM_PM));

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		int expirationDateMonth = expirationCalendar.get(
			jsonObject.getInt("expirationDateMonth", Calendar.MONTH));
		int expirationDateDay = expirationCalendar.get(
			jsonObject.getInt(
				"expirationDateDayOfMonth", Calendar.DAY_OF_MONTH));
		int expirationDateYear = expirationCalendar.get(
			jsonObject.getInt("expirationDateYear", Calendar.YEAR));
		int expirationDateHour = expirationCalendar.get(
			jsonObject.getInt("expirationDateHour", Calendar.HOUR));
		int expirationDateMinute = expirationCalendar.get(
			jsonObject.getInt("expirationDateMinute", Calendar.MINUTE));
		int expirationDateAmPm = expirationCalendar.get(
			jsonObject.getInt("expirationDateAmPm", Calendar.AM_PM));

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		// Add Commerce Price List

		JSONArray accountGroupsJSONArray = jsonObject.getJSONArray(
			"accountGroups");

		if (accountGroupsJSONArray != null) {
			int priority = jsonObject.getInt("priority");
			boolean neverExpire = jsonObject.getBoolean("neverExpire", true);

			CommerceCurrency commerceCurrency =
				_commerceCurrencyLocalService.getCommerceCurrency(
					serviceContext.getCompanyId(), currencyCode);

			String externalReferenceCode = FriendlyURLNormalizerUtil.normalize(
				name);

			CommercePriceList commercePriceList =
				_commercePriceListLocalService.addOrUpdateCommercePriceList(
					externalReferenceCode, catalogGroupId, user.getUserId(), 0,
					commerceCurrency.getCommerceCurrencyId(), true,
					CommercePriceListConstants.TYPE_PRICE_LIST,
					parentPriceListId, false, name, priority, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			for (int i = 0; i < accountGroupsJSONArray.length(); i++) {
				try {
					String accountGroupExternalReferenceCode =
						FriendlyURLNormalizerUtil.normalize(
							accountGroupsJSONArray.getString(i));

					CommerceAccountGroup commerceAccountGroup =
						_commerceAccountGroupLocalService.
							fetchByExternalReferenceCode(
								serviceContext.getCompanyId(),
								accountGroupExternalReferenceCode);

					if (commerceAccountGroup == null) {
						throw new NoSuchAccountGroupException();
					}

					CommercePriceListCommerceAccountGroupRel
						commercePriceListCommerceAccountGroupRel =
							_commercePriceListCommerceAccountGroupRelLocalService.
								fetchCommercePriceListCommerceAccountGroupRel(
									commercePriceList.getCommercePriceListId(),
									commerceAccountGroup.
										getCommerceAccountGroupId());

					if (commercePriceListCommerceAccountGroupRel == null) {
						_commercePriceListCommerceAccountGroupRelLocalService.
							addCommercePriceListCommerceAccountGroupRel(
								serviceContext.getUserId(),
								commercePriceList.getCommercePriceListId(),
								commerceAccountGroup.
									getCommerceAccountGroupId(),
								0, serviceContext);
					}
				}
				catch (NoSuchAccountGroupException
							noSuchAccountGroupException) {

					_log.error(
						noSuchAccountGroupException,
						noSuchAccountGroupException);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListsImporter.class);

	@Reference
	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommercePriceListCommerceAccountGroupRelLocalService
		_commercePriceListCommerceAccountGroupRelLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
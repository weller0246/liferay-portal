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

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.display.AccountEntryDisplay;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Pei-Jung Lan
 */
public class AccountEntryDisplaySearchContainerFactory {

	public static SearchContainer<AccountEntryDisplay> create(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		return _create(
			liferayPortletRequest, liferayPortletResponse,
			new LinkedHashMap<>(), true);
	}

	public static SearchContainer<AccountEntryDisplay> createWithAccountGroupId(
			long accountGroupId, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		return _create(
			liferayPortletRequest, liferayPortletResponse,
			LinkedHashMapBuilder.<String, Object>put(
				"accountGroupIds", new long[] {accountGroupId}
			).build(),
			false);
	}

	public static SearchContainer<AccountEntryDisplay> createWithUserId(
			long userId, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		return _create(
			liferayPortletRequest, liferayPortletResponse,
			LinkedHashMapBuilder.<String, Object>put(
				"accountUserIds", new long[] {userId}
			).build(),
			false);
	}

	private static SearchContainer<AccountEntryDisplay> _create(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			LinkedHashMap<String, Object> params,
			boolean filterManageableAccountEntries)
		throws PortalException {

		SearchContainer<AccountEntryDisplay>
			accountEntryDisplaySearchContainer = new SearchContainer(
				liferayPortletRequest,
				PortletURLUtil.getCurrent(
					liferayPortletRequest, liferayPortletResponse),
				null, "no-accounts-were-found");

		accountEntryDisplaySearchContainer.setId("accountEntries");

		String orderByCol = ParamUtil.getString(
			liferayPortletRequest, "orderByCol", "name");

		accountEntryDisplaySearchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			liferayPortletRequest, "orderByType", "asc");

		accountEntryDisplaySearchContainer.setOrderByType(orderByType);

		accountEntryDisplaySearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));

		String keywords = ParamUtil.getString(
			liferayPortletRequest, "keywords");

		String navigation = ParamUtil.getString(
			liferayPortletRequest, "navigation", "active");

		params.put("status", _getStatus(navigation));

		String[] types = {
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
		};

		String type = ParamUtil.getString(liferayPortletRequest, "type");

		if (Validator.isNotNull(type) && !type.equals("all")) {
			params.put("type", type);

			types = new String[] {type};
		}

		BaseModelSearchResult<AccountEntry> baseModelSearchResult;

		if (filterManageableAccountEntries) {
			baseModelSearchResult = new BaseModelSearchResult<>(
				AccountEntryLocalServiceUtil.getUserAccountEntries(
					PortalUtil.getUserId(liferayPortletRequest), null, keywords,
					types, _getStatus(navigation),
					accountEntryDisplaySearchContainer.getStart(),
					accountEntryDisplaySearchContainer.getEnd()),
				AccountEntryLocalServiceUtil.getUserAccountEntriesCount(
					PortalUtil.getUserId(liferayPortletRequest), null, keywords,
					types, _getStatus(navigation)));
		}
		else {
			baseModelSearchResult =
				AccountEntryLocalServiceUtil.searchAccountEntries(
					CompanyThreadLocal.getCompanyId(), keywords, params,
					accountEntryDisplaySearchContainer.getStart(),
					accountEntryDisplaySearchContainer.getDelta(), orderByCol,
					_isReverseOrder(orderByType));
		}

		List<AccountEntryDisplay> accountEntryDisplays =
			TransformUtil.transform(
				baseModelSearchResult.getBaseModels(), AccountEntryDisplay::of);

		accountEntryDisplaySearchContainer.setResults(accountEntryDisplays);

		accountEntryDisplaySearchContainer.setTotal(
			baseModelSearchResult.getLength());

		return accountEntryDisplaySearchContainer;
	}

	private static int _getStatus(String navigation) {
		if (Objects.equals(navigation, "inactive")) {
			return WorkflowConstants.STATUS_INACTIVE;
		}

		return WorkflowConstants.STATUS_APPROVED;
	}

	private static boolean _isReverseOrder(String orderByType) {
		if (Objects.equals(orderByType, "desc")) {
			return true;
		}

		return false;
	}

}
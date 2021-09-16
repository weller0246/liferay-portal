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

package com.liferay.account.internal.manager;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountWebKeys;
import com.liferay.account.manager.CurrentAccountEntryManager;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = CurrentAccountEntryManager.class)
public class CurrentAccountEntryManagerImpl
	implements CurrentAccountEntryManager {

	public AccountEntry getCurrentAccountEntry(long groupId, long userId)
		throws PortalException {

		AccountEntry accountEntry = _getAccountEntryFromHttpSession(groupId);

		if (_isValid(accountEntry)) {
			return accountEntry;
		}

		accountEntry = _getAccountEntryFromPortalPreferences(groupId, userId);

		if (_isValid(accountEntry)) {
			_saveInHttpSession(accountEntry.getAccountEntryId(), groupId);

			return accountEntry;
		}

		accountEntry = _getDefaultAccountEntry(userId);

		if (_isValid(accountEntry)) {
			setCurrentAccountEntry(
				accountEntry.getAccountEntryId(), groupId, userId);

			return accountEntry;
		}

		setCurrentAccountEntry(
			AccountConstants.ACCOUNT_ENTRY_ID_GUEST, groupId, userId);

		return null;
	}

	public void setCurrentAccountEntry(
		long accountEntryId, long groupId, long userId) {

		_saveInHttpSession(accountEntryId, groupId);

		_saveInPortalPreferences(accountEntryId, groupId, userId);
	}

	private AccountEntry _getAccountEntryFromHttpSession(long groupId) {
		HttpSession httpSession = PortalSessionThreadLocal.getHttpSession();

		if (httpSession == null) {
			return null;
		}

		long currentAccountEntryId = GetterUtil.getLong(
			httpSession.getAttribute(_getKey(groupId)));

		return _accountEntryLocalService.fetchAccountEntry(
			currentAccountEntryId);
	}

	private AccountEntry _getAccountEntryFromPortalPreferences(
		long groupId, long userId) {

		PortalPreferences portalPreferences = _getPortalPreferences(userId);

		long accountEntryId = GetterUtil.getLong(
			portalPreferences.getValue(
				AccountEntry.class.getName(), _getKey(groupId)));

		if (accountEntryId > 0) {
			return _accountEntryLocalService.fetchAccountEntry(accountEntryId);
		}

		return null;
	}

	private AccountEntry _getDefaultAccountEntry(long userId)
		throws PortalException {

		User user = _userLocalService.fetchUser(userId);

		if ((user == null) || user.isDefaultUser()) {
			return _accountEntryLocalService.getGuestAccountEntry(
				CompanyThreadLocal.getCompanyId());
		}

		List<AccountEntry> accountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				user.getUserId(),
				AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, null,
				new String[] {
					AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
				},
				0, 1);

		if (accountEntries.size() == 1) {
			return accountEntries.get(0);
		}

		return null;
	}

	private String _getKey(long groupId) {
		return AccountWebKeys.CURRENT_ACCOUNT_ENTRY_ID + groupId;
	}

	private PortalPreferences _getPortalPreferences(long userId) {
		return _portletPreferencesFactory.getPortalPreferences(userId, true);
	}

	private boolean _isValid(AccountEntry accountEntry) {
		if ((accountEntry != null) &&
			Objects.equals(
				WorkflowConstants.STATUS_APPROVED, accountEntry.getStatus())) {

			return true;
		}

		return false;
	}

	private void _saveInHttpSession(long accountEntryId, long groupId) {
		HttpSession httpSession = PortalSessionThreadLocal.getHttpSession();

		if (httpSession == null) {
			return;
		}

		httpSession.setAttribute(_getKey(groupId), accountEntryId);
	}

	private void _saveInPortalPreferences(
		long accountEntryId, long groupId, long userId) {

		PortalPreferences portalPreferences = _getPortalPreferences(userId);

		String key = _getKey(groupId);

		long currentAccountEntryId = GetterUtil.getLong(
			portalPreferences.getValue(
				AccountEntry.class.getName(), key,
				String.valueOf(AccountConstants.ACCOUNT_ENTRY_ID_GUEST)));

		if (currentAccountEntryId == accountEntryId) {
			return;
		}

		portalPreferences.setValue(
			AccountEntry.class.getName(), key, String.valueOf(accountEntryId));

		_portalPreferencesLocalService.updatePreferences(
			userId, PortletKeys.PREFS_OWNER_TYPE_USER, portalPreferences);
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private UserLocalService _userLocalService;

}
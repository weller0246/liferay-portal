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

package com.liferay.document.library.google.docs.internal.util;

import com.liferay.document.library.google.docs.internal.util.constants.GoogleDocsConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Iván Zaera
 */
public class GoogleDocsDLFileEntryTypeHelper {

	public GoogleDocsDLFileEntryTypeHelper(
		Company company, DefaultDDMStructureHelper defaultDDMStructureHelper,
		long dlFileEntryMetadataClassNameId,
		DDMStructureLocalService ddmStructureLocalService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		UserLocalService userLocalService) {

		_company = company;
		_defaultDDMStructureHelper = defaultDDMStructureHelper;
		_dlFileEntryMetadataClassNameId = dlFileEntryMetadataClassNameId;
		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		_userLocalService = userLocalService;
	}

	public void addGoogleDocsDLFileEntryType() throws Exception {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			_company.getGroupId(), _dlFileEntryMetadataClassNameId,
			GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS);

		if (ddmStructure == null) {
			ddmStructure = _addGoogleDocsDDMStructure();
		}

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.fetchDataDefinitionFileEntryType(
				_company.getGroupId(), ddmStructure.getStructureId());

		if (dlFileEntryType == null) {
			_addGoogleDocsDLFileEntryType(ddmStructure.getStructureId());
		}
		else {
			_updateDLFileEntryTypeNameMap(dlFileEntryType);
		}
	}

	private DDMStructure _addGoogleDocsDDMStructure() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(_company.getGroupId());

		long defaultUserId = _userLocalService.getDefaultUserId(
			_company.getCompanyId());

		serviceContext.setUserId(defaultUserId);

		Class<?> clazz = getClass();

		_defaultDDMStructureHelper.addDDMStructures(
			defaultUserId, _company.getGroupId(),
			_dlFileEntryMetadataClassNameId, clazz.getClassLoader(),
			"com/liferay/document/library/google/docs/internal/util" +
				"/dependencies/google-docs-metadata-structure.xml",
			serviceContext);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			_company.getGroupId(), _dlFileEntryMetadataClassNameId,
			GoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY);

		ddmStructure.setNameMap(_updateNameMap(ddmStructure.getNameMap()));
		ddmStructure.setType(DDMStructureConstants.TYPE_AUTO);

		_ddmStructureLocalService.updateDDMStructure(ddmStructure);

		return ddmStructure;
	}

	private void _addGoogleDocsDLFileEntryType(long ddmStructureId)
		throws Exception {

		long defaultUserId = _userLocalService.getDefaultUserId(
			_company.getCompanyId());

		Map<Locale, String> descriptionMap = new HashMap<>();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(_company.getGroupId());
		serviceContext.setUserId(defaultUserId);

		_dlFileEntryTypeLocalService.addFileEntryType(
			defaultUserId, _company.getGroupId(), ddmStructureId,
			GoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY,
			_getGoogleDriveShortcutNameMap(LanguageUtil.getAvailableLocales()),
			descriptionMap,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_SYSTEM,
			serviceContext);
	}

	private Map<Locale, String> _getGoogleDriveShortcutNameMap(
		Set<Locale> locales) {

		Map<Locale, String> nameMap = new HashMap<>();

		for (Locale locale : locales) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				locale, GoogleDocsDLFileEntryTypeHelper.class);

			nameMap.put(
				locale,
				LanguageUtil.get(resourceBundle, "google-drive-shortcut"));
		}

		return nameMap;
	}

	private void _updateDLFileEntryTypeNameMap(
		DLFileEntryType dlFileEntryType) {

		Map<Locale, String> nameMap = dlFileEntryType.getNameMap();

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		if (nameMap.size() >= availableLocales.size()) {
			return;
		}

		dlFileEntryType.setNameMap(
			_getGoogleDriveShortcutNameMap(availableLocales));

		_dlFileEntryTypeLocalService.updateDLFileEntryType(dlFileEntryType);
	}

	private Map<Locale, String> _updateNameMap(Map<Locale, String> nameMap) {
		Map<Locale, String> updatedNameMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			updatedNameMap.put(
				entry.getKey(),
				LanguageUtil.get(entry.getKey(), "google-docs-metadata"));
		}

		return updatedNameMap;
	}

	private final Company _company;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private final long _dlFileEntryMetadataClassNameId;
	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private final UserLocalService _userLocalService;

}
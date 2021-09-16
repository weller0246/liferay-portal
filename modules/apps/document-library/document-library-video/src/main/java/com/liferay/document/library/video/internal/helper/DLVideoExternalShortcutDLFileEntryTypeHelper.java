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

package com.liferay.document.library.video.internal.helper;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.video.internal.constants.DLVideoConstants;
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
 * @author Alejandro Tardín
 */
public class DLVideoExternalShortcutDLFileEntryTypeHelper {

	public DLVideoExternalShortcutDLFileEntryTypeHelper(
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

	public void addDLVideoExternalShortcutDLFileEntryType() throws Exception {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			_company.getGroupId(), _dlFileEntryMetadataClassNameId,
			DLVideoConstants.DDM_STRUCTURE_KEY_DL_VIDEO_EXTERNAL_SHORTCUT);

		if (ddmStructure == null) {
			ddmStructure = _addDLVideoExternalShortcutDDMStructure();
		}

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.fetchDataDefinitionFileEntryType(
				_company.getGroupId(), ddmStructure.getStructureId());

		if (dlFileEntryType == null) {
			_addDLVideoExternalShortcutDLFileEntryType(
				ddmStructure.getStructureId());
		}
		else {
			_updateDLFileEntryTypeNameMap(dlFileEntryType);
		}
	}

	private DDMStructure _addDLVideoExternalShortcutDDMStructure()
		throws Exception {

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
			"com/liferay/document/library/video/internal/util/dependencies" +
				"/dl-video-external-shortcut-metadata-structure.xml",
			serviceContext);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			_company.getGroupId(), _dlFileEntryMetadataClassNameId,
			DLVideoConstants.DL_FILE_ENTRY_TYPE_KEY);

		ddmStructure.setNameMap(_updateNameMap(ddmStructure.getNameMap()));
		ddmStructure.setDescriptionMap(
			_updateDescriptionMap(ddmStructure.getDescriptionMap()));
		ddmStructure.setType(DDMStructureConstants.TYPE_AUTO);

		_ddmStructureLocalService.updateDDMStructure(ddmStructure);

		return ddmStructure;
	}

	private void _addDLVideoExternalShortcutDLFileEntryType(long ddmStructureId)
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
			DLVideoConstants.DL_FILE_ENTRY_TYPE_KEY,
			_getExternalVideoShortcutNameMap(
				LanguageUtil.getAvailableLocales()),
			descriptionMap,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_SYSTEM,
			serviceContext);
	}

	private Map<Locale, String> _getExternalVideoShortcutNameMap(
		Set<Locale> locales) {

		Map<Locale, String> nameMap = new HashMap<>();

		for (Locale locale : locales) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				locale, DLVideoExternalShortcutDLFileEntryTypeHelper.class);

			nameMap.put(
				locale,
				LanguageUtil.get(resourceBundle, "external-video-shortcut"));
		}

		return nameMap;
	}

	private Map<Locale, String> _updateDescriptionMap(
		Map<Locale, String> descriptionMap) {

		Map<Locale, String> updatedDescriptionMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				entry.getKey(),
				DLVideoExternalShortcutDLFileEntryTypeHelper.class);

			updatedDescriptionMap.put(
				entry.getKey(),
				LanguageUtil.get(resourceBundle, entry.getValue()));
		}

		return updatedDescriptionMap;
	}

	private void _updateDLFileEntryTypeNameMap(
		DLFileEntryType dlFileEntryType) {

		Map<Locale, String> nameMap = dlFileEntryType.getNameMap();

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		if (nameMap.size() >= availableLocales.size()) {
			return;
		}

		dlFileEntryType.setNameMap(
			_getExternalVideoShortcutNameMap(availableLocales));

		_dlFileEntryTypeLocalService.updateDLFileEntryType(dlFileEntryType);
	}

	private Map<Locale, String> _updateNameMap(Map<Locale, String> nameMap) {
		Map<Locale, String> updatedNameMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				entry.getKey(),
				DLVideoExternalShortcutDLFileEntryTypeHelper.class);

			updatedNameMap.put(
				entry.getKey(),
				LanguageUtil.get(
					resourceBundle, "dl-video-external-shortcut-metadata"));
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
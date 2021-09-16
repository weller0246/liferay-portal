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

package com.liferay.document.library.internal.upgrade.v3_2_2;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DLFileEntryUpgradeProcess extends UpgradeProcess {

	public DLFileEntryUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"delete from DLFileShortcut where not exists (select * from ",
				"DLFileEntry where fileEntryId = ",
				"DLFileShortcut.toFileEntryId)"));

		_delete(DLFileEntry.class, "AssetEntry", "DLFileEntry", "fileEntryId");
		_delete(
			DLFileEntry.class, "RatingsEntry", "DLFileEntry", "fileEntryId");
		_delete(
			DLFileEntry.class, "RatingsStats", "DLFileEntry", "fileEntryId");
		_delete(DLFolder.class, "AssetEntry", "DLFolder", "folderId");
	}

	private void _delete(
			Class<?> clazz, String tableName, String joinTableName,
			String joinColumnName)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"delete from ", tableName, " where ", tableName,
				".classNameId = ", _classNameLocalService.getClassNameId(clazz),
				" and not exists (select * from ", joinTableName, " where ",
				joinColumnName, " = ", tableName, ".classPK)"));
	}

	private final ClassNameLocalService _classNameLocalService;

}
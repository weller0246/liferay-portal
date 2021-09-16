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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Jonathan McCann
 */
public class UpgradeWorkflow extends UpgradeProcess {

	protected void deleteOrphaned() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			for (String[] orphanedAttachedModel : getOrphanedAttachedModels()) {
				String tableName = orphanedAttachedModel[0];
				String columnName = orphanedAttachedModel[1];

				if (!hasTable(tableName) || !hasColumn(tableName, columnName)) {
					continue;
				}

				String orphanedTableName = orphanedAttachedModel[3];
				String orphanedColumnName = orphanedAttachedModel[4];

				if (!hasTable(orphanedTableName) ||
					!hasColumn(orphanedTableName, orphanedColumnName)) {

					continue;
				}

				String columnValue = orphanedAttachedModel[2];

				deleteOrphaned(
					tableName, columnName, columnValue, orphanedTableName,
					orphanedColumnName);
			}
		}
	}

	protected void deleteOrphaned(
			String tableName, String columnName, String columnValue,
			String orphanedTableName, String orphanedColumnName)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"delete from ", tableName, " where ", columnName, " = ",
				columnValue, " and classPK not in (select ", orphanedColumnName,
				" from ", orphanedTableName, StringPool.CLOSE_PARENTHESIS));
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteOrphaned();
	}

	protected String[][] getOrphanedAttachedModels() {
		return _ORPHANED_ATTACHED_MODELS;
	}

	private static final String _CLASS_NAME_ID = String.valueOf(
		PortalUtil.getClassNameId(
			"com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess"));

	private static final String[][] _ORPHANED_ATTACHED_MODELS = {
		{
			"KaleoInstance", "className",
			"'com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess'",
			"DDLRecord", "recordId"
		},
		{
			"KaleoInstanceToken", "className",
			"'com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess'",
			"DDLRecord", "recordId"
		},
		{
			"WorkflowDefinitionLink", "classNameId", _CLASS_NAME_ID,
			"KaleoProcess", "kaleoProcessId"
		},
		{
			"WorkflowInstanceLink", "classNameId", _CLASS_NAME_ID, "DDLRecord",
			"recordId"
		}
	};

}
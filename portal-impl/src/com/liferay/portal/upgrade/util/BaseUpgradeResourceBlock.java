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

package com.liferay.portal.upgrade.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.lang.reflect.Field;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Preston Crary
 */
public abstract class BaseUpgradeResourceBlock extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String className = getClassName();

		_upgradeCompanyScopePermissions(className);

		_upgradeGroupScopePermissions(className);

		_upgradeGroupTemplateScopePermissions(className);

		_upgradeIndividualScopePermissions(className);

		_removeResourceBlocks(className);

		alter(getTableClass(), new AlterTableDropColumn("resourceBlockId"));
	}

	protected abstract String getClassName();

	protected abstract String getPrimaryKeyName();

	protected abstract Class<?> getTableClass();

	protected abstract boolean hasUserId();

	private void _addResourcePermissionBatch(
			PreparedStatement preparedStatement, long companyId, String name,
			int scope, long primKeyId, long roleId, long ownerId,
			long actionIds)
		throws SQLException {

		preparedStatement.setLong(1, 0L);
		preparedStatement.setLong(
			2, increment(ResourcePermission.class.getName()));
		preparedStatement.setLong(3, companyId);
		preparedStatement.setString(4, name);
		preparedStatement.setInt(5, scope);
		preparedStatement.setString(6, String.valueOf(primKeyId));
		preparedStatement.setLong(7, primKeyId);
		preparedStatement.setLong(8, roleId);
		preparedStatement.setLong(9, ownerId);
		preparedStatement.setLong(10, actionIds);
		preparedStatement.setBoolean(11, (actionIds % 2) == 1);

		preparedStatement.addBatch();
	}

	private void _removeResourceBlocks(String className) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from ResourceTypePermission where name = ?")) {

			preparedStatement.setString(1, className);

			preparedStatement.executeUpdate();
		}

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					"select resourceBlockId from ResourceBlock where name = " +
						"?")) {

			selectPreparedStatement.setString(1, className);

			try (ResultSet resultSet = selectPreparedStatement.executeQuery();
				PreparedStatement deletePreparedStatement =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"delete from ResourceBlockPermission where " +
							"resourceBlockId = ?")) {

				while (resultSet.next()) {
					long resourceBlockId = resultSet.getLong(1);

					deletePreparedStatement.setLong(1, resourceBlockId);

					deletePreparedStatement.addBatch();
				}

				deletePreparedStatement.executeBatch();
			}
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from ResourceBlock where name = ?")) {

			preparedStatement.setString(1, className);

			preparedStatement.executeUpdate();
		}
	}

	private void _upgradeCompanyScopePermissions(String className)
		throws Exception {

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					SQLTransformer.transform(
						StringBundler.concat(
							"select ResourceTypePermission.companyId, ",
							"ResourceTypePermission.roleId, ",
							"ResourceTypePermission.actionIds from ",
							"ResourceTypePermission inner join Role_ on ",
							"Role_.roleId = ResourceTypePermission.roleId ",
							"where ResourceTypePermission.groupId = 0 and ",
							"Role_.type_ = ", RoleConstants.TYPE_REGULAR,
							" and ResourceTypePermission.name = ?")))) {

			selectPreparedStatement.setString(1, className);

			try (ResultSet resultSet = selectPreparedStatement.executeQuery();
				PreparedStatement insertPreparedStatement =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, _INSERT_SQL)) {

				while (resultSet.next()) {
					long companyId = resultSet.getLong("companyId");
					long roleId = resultSet.getLong("roleId");
					long actionIds = resultSet.getLong("actionIds");

					_addResourcePermissionBatch(
						insertPreparedStatement, companyId, className,
						ResourceConstants.SCOPE_COMPANY, companyId, roleId, 0,
						actionIds);
				}

				insertPreparedStatement.executeBatch();
			}
		}
	}

	private void _upgradeGroupScopePermissions(String className)
		throws Exception {

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					SQLTransformer.transform(
						"select companyId, groupId, roleId, actionIds from " +
							"ResourceTypePermission where groupId != 0 and " +
								"name = ?"))) {

			selectPreparedStatement.setString(1, className);

			try (ResultSet resultSet = selectPreparedStatement.executeQuery();
				PreparedStatement insertPreparedStatement =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, _INSERT_SQL)) {

				while (resultSet.next()) {
					long companyId = resultSet.getLong("companyId");
					long groupId = resultSet.getLong("groupId");
					long roleId = resultSet.getLong("roleId");
					long actionIds = resultSet.getLong("actionIds");

					_addResourcePermissionBatch(
						insertPreparedStatement, companyId, className,
						ResourceConstants.SCOPE_GROUP, groupId, roleId, 0,
						actionIds);
				}

				insertPreparedStatement.executeBatch();
			}
		}
	}

	private void _upgradeGroupTemplateScopePermissions(String className)
		throws Exception {

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					SQLTransformer.transform(
						StringBundler.concat(
							"select ResourceTypePermission.companyId, ",
							"ResourceTypePermission.roleId, ",
							"ResourceTypePermission.actionIds from ",
							"ResourceTypePermission inner join Role_ on ",
							"Role_.roleId = ResourceTypePermission.roleId ",
							"where ResourceTypePermission.groupId = 0 and ",
							"Role_.type_ != ", RoleConstants.TYPE_REGULAR,
							" and ResourceTypePermission.name = ?")))) {

			selectPreparedStatement.setString(1, className);

			try (ResultSet resultSet = selectPreparedStatement.executeQuery();
				PreparedStatement insertPreparedStatement =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, _INSERT_SQL)) {

				while (resultSet.next()) {
					long companyId = resultSet.getLong("companyId");
					long roleId = resultSet.getLong("roleId");
					long actionIds = resultSet.getLong("actionIds");

					_addResourcePermissionBatch(
						insertPreparedStatement, companyId, className,
						ResourceConstants.SCOPE_GROUP_TEMPLATE, 0, roleId, 0,
						actionIds);
				}

				insertPreparedStatement.executeBatch();
			}
		}
	}

	private void _upgradeIndividualScopePermissions(String className)
		throws Exception {

		StringBundler sb = new StringBundler(16);

		Class<?> tableClass = getTableClass();

		Field tableNameField = tableClass.getField("TABLE_NAME");

		String tableName = (String)tableNameField.get(null);

		String primaryKeyName = getPrimaryKeyName();

		sb.append("select ResourceBlock.companyId, ");
		sb.append(tableName);
		sb.append(".");
		sb.append(primaryKeyName);
		sb.append(", ResourceBlockPermission.roleId, ");

		if (hasUserId()) {
			sb.append(tableName);
			sb.append(".userId, ");
		}

		sb.append("ResourceBlockPermission.resourceBlockPermissionId, ");
		sb.append("ResourceBlockPermission.actionIds from ");
		sb.append(tableName);
		sb.append(" inner join ResourceBlock on ");
		sb.append("(ResourceBlock.resourceBlockId = ");
		sb.append(tableName);
		sb.append(".resourceBlockId) inner join ResourceBlockPermission on ");
		sb.append("(ResourceBlockPermission.resourceBlockId = ResourceBlock.");
		sb.append("resourceBlockId) where ResourceBlock.name = ?");

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					SQLTransformer.transform(sb.toString()))) {

			selectPreparedStatement.setString(1, className);

			try (ResultSet resultSet = selectPreparedStatement.executeQuery();
				PreparedStatement insertPreparedStatement =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, _INSERT_SQL)) {

				while (resultSet.next()) {
					long companyId = resultSet.getLong("companyId");
					long primKeyId = resultSet.getLong(primaryKeyName);
					long roleId = resultSet.getLong("roleId");

					long userId = 0;

					if (hasUserId()) {
						userId = resultSet.getLong("userId");
					}

					long actionIds = resultSet.getLong("actionIds");

					_addResourcePermissionBatch(
						insertPreparedStatement, companyId, className,
						ResourceConstants.SCOPE_INDIVIDUAL, primKeyId, roleId,
						userId, actionIds);
				}

				insertPreparedStatement.executeBatch();
			}
		}
	}

	private static final String _INSERT_SQL;

	static {
		_INSERT_SQL = StringBundler.concat(
			"insert into ResourcePermission(mvccVersion, ",
			"resourcePermissionId, companyId, name, scope, primKey, ",
			"primKeyId, roleId, ownerId, actionIds, viewActionId) values(?, ",
			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	}

}
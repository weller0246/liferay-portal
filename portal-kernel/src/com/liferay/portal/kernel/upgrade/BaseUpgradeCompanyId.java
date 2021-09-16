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

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
 *             BaseCompanyIdUpgradeProcess}
 */
@Deprecated
public abstract class BaseUpgradeCompanyId extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		List<Callable<Void>> callables = new ArrayList<>();

		for (TableUpdater tableUpdater : getTableUpdaters()) {
			if (!hasColumn(tableUpdater.getTableName(), "companyId")) {
				tableUpdater.setCreateCompanyIdColumn(true);
			}

			callables.add(tableUpdater);
		}

		ExecutorService executorService = Executors.newFixedThreadPool(
			callables.size());

		try {
			List<Future<Void>> futures = executorService.invokeAll(callables);

			for (Future<Void> future : futures) {
				future.get();
			}
		}
		finally {
			executorService.shutdown();
		}
	}

	protected abstract TableUpdater[] getTableUpdaters();

	protected class TableUpdater extends BaseUpgradeCallable<Void> {

		public TableUpdater(
			String tableName, String foreignTableName, String columnName) {

			_tableName = tableName;

			_columnName = columnName;

			_foreignNamesArray = new String[][] {
				{foreignTableName, columnName}
			};
		}

		public TableUpdater(
			String tableName, String columnName, String[][] foreignNamesArray) {

			_tableName = tableName;
			_columnName = columnName;
			_foreignNamesArray = foreignNamesArray;
		}

		public String getTableName() {
			return _tableName;
		}

		public void setCreateCompanyIdColumn(boolean createCompanyIdColumn) {
			_createCompanyIdColumn = createCompanyIdColumn;
		}

		public void update(Connection connection)
			throws IOException, SQLException {

			for (String[] foreignNames : _foreignNamesArray) {
				runSQL(
					connection,
					getUpdateSQL(connection, foreignNames[0], foreignNames[1]));
			}
		}

		@Override
		protected final Void doCall() throws Exception {
			try (LoggingTimer loggingTimer = new LoggingTimer(_tableName);
				Connection connection = DataAccess.getConnection()) {

				if (_createCompanyIdColumn) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Adding column companyId to table " + _tableName);
					}

					runSQL(
						connection,
						"alter table " + _tableName + " add companyId LONG");
				}
				else {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Skipping the creation of companyId column for " +
								"table " + _tableName);
					}
				}

				update(connection);
			}

			return null;
		}

		protected List<Long> getCompanyIds(Connection connection)
			throws SQLException {

			List<Long> companyIds = new ArrayList<>();

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select companyId from Company");
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					long companyId = resultSet.getLong(1);

					companyIds.add(companyId);
				}
			}

			return companyIds;
		}

		protected String getSelectSQL(
				Connection connection, String foreignTableName,
				String foreignColumnName)
			throws SQLException {

			List<Long> companyIds = getCompanyIds(connection);

			if (companyIds.size() == 1) {
				return String.valueOf(companyIds.get(0));
			}

			return StringBundler.concat(
				"select max(companyId) from ", foreignTableName, " where ",
				foreignTableName, ".", foreignColumnName, " = ", _tableName,
				".", _columnName);
		}

		protected String getUpdateSQL(
				Connection connection, String foreignTableName,
				String foreignColumnName)
			throws SQLException {

			return getUpdateSQL(
				getSelectSQL(connection, foreignTableName, foreignColumnName));
		}

		protected String getUpdateSQL(String selectSQL) {
			return StringBundler.concat(
				"update ", _tableName, " set companyId = (", selectSQL, ")");
		}

		private final String _columnName;
		private boolean _createCompanyIdColumn;
		private final String[][] _foreignNamesArray;
		private final String _tableName;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUpgradeCompanyId.class);

}
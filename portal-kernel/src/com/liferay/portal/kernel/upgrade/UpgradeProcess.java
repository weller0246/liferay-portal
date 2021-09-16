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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.BaseDBProcess;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public abstract class UpgradeProcess
	extends BaseDBProcess implements UpgradeStep {

	public void clearIndexesCache() {
		_portalIndexesSQL.clear();
	}

	public int getThreshold() {

		// This upgrade process will only run if the build number is larger than
		// the returned threshold value. Return 0 to always run this upgrade
		// process.

		return 0;
	}

	public void upgrade() throws UpgradeException {
		long start = System.currentTimeMillis();

		String message = "Completed upgrade process ";

		try (Connection connection = DataAccess.getConnection()) {
			this.connection = connection;

			if (isSkipUpgradeProcess()) {
				return;
			}

			process(
				companyId -> {
					if (_log.isInfoEnabled()) {
						String info =
							"Upgrading " + ClassUtil.getClassName(this);

						if (Validator.isNotNull(companyId)) {
							info += "#" + companyId;
						}

						_log.info(info);
					}

					doUpgrade();
				});
		}
		catch (Throwable throwable) {
			message = "Failed upgrade process ";

			throw new UpgradeException(throwable);
		}
		finally {
			this.connection = null;

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						message, ClassUtil.getClassName(this), " in ",
						System.currentTimeMillis() - start, " ms"));
			}
		}
	}

	@Override
	public void upgrade(DBProcessContext dbProcessContext)
		throws UpgradeException {

		upgrade();
	}

	public void upgrade(UpgradeProcess upgradeProcess) throws UpgradeException {
		upgradeProcess.upgrade();
	}

	public interface Alterable {

		public static boolean containsIgnoreCase(
			Collection<String> columnNames, String columnName) {

			for (String curColumnName : columnNames) {
				if (StringUtil.equalsIgnoreCase(curColumnName, columnName)) {
					return true;
				}
			}

			return false;
		}

		public String getSQL(String tableName);

		public boolean shouldAddIndex(Collection<String> columnNames);

		public boolean shouldDropIndex(Collection<String> columnNames);

	}

	public class AlterColumnName implements Alterable {

		public AlterColumnName(String oldColumnName, String newColumn) {
			_oldColumnName = oldColumnName;
			_newColumn = newColumn;

			String newColumnName = StringUtil.extractFirst(
				newColumn, StringPool.SPACE);

			if (newColumnName != null) {
				_newColumnName = newColumnName;
			}
			else {
				_newColumnName = _newColumn;
			}
		}

		@Override
		public String getSQL(String tableName) {
			return StringBundler.concat(
				"alter_column_name ", tableName, StringPool.SPACE,
				_oldColumnName, StringPool.SPACE, _newColumn);
		}

		@Override
		public boolean shouldAddIndex(Collection<String> columnNames) {
			return Alterable.containsIgnoreCase(columnNames, _newColumnName);
		}

		@Override
		public boolean shouldDropIndex(Collection<String> columnNames) {
			return Alterable.containsIgnoreCase(columnNames, _oldColumnName);
		}

		private final String _newColumn;
		private final String _newColumnName;
		private final String _oldColumnName;

	}

	public class AlterColumnType implements Alterable {

		public AlterColumnType(String columnName, String newType) {
			_columnName = columnName;
			_newType = newType;
		}

		@Override
		public String getSQL(String tableName) {
			return StringBundler.concat(
				"alter_column_type ", tableName, StringPool.SPACE, _columnName,
				StringPool.SPACE, _newType);
		}

		@Override
		public boolean shouldAddIndex(Collection<String> columnNames) {
			return Alterable.containsIgnoreCase(columnNames, _columnName);
		}

		@Override
		public boolean shouldDropIndex(Collection<String> columnNames) {
			return Alterable.containsIgnoreCase(columnNames, _columnName);
		}

		private final String _columnName;
		private final String _newType;

	}

	public class AlterTableAddColumn implements Alterable {

		/**
		 * @deprecated As of Athanasius (7.3.x), replaced by {@link
		 *             #AlterTableAddColumn(String, String)}
		 */
		@Deprecated
		public AlterTableAddColumn(String columnName) {
			_columnName = columnName;

			_columnType = StringPool.BLANK;
		}

		public AlterTableAddColumn(String columnName, String columnType) {
			_columnName = columnName;
			_columnType = columnType;
		}

		@Override
		public String getSQL(String tableName) {
			return StringUtil.trim(
				StringBundler.concat(
					"alter table ", tableName, " add ", _columnName,
					StringPool.SPACE, _columnType));
		}

		@Override
		public boolean shouldAddIndex(Collection<String> columnNames) {
			return Alterable.containsIgnoreCase(columnNames, _columnName);
		}

		@Override
		public boolean shouldDropIndex(Collection<String> columnNames) {
			return false;
		}

		private final String _columnName;
		private final String _columnType;

	}

	public class AlterTableDropColumn implements Alterable {

		public AlterTableDropColumn(String columnName) {
			_columnName = columnName;
		}

		@Override
		public String getSQL(String tableName) {
			return StringBundler.concat(
				"alter table ", tableName, " drop column ", _columnName);
		}

		@Override
		public boolean shouldAddIndex(Collection<String> columnNames) {
			return false;
		}

		@Override
		public boolean shouldDropIndex(Collection<String> columnNames) {
			return Alterable.containsIgnoreCase(columnNames, _columnName);
		}

		private final String _columnName;

	}

	protected void alter(Class<?> tableClass, Alterable... alterables)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String tableName = getTableName(tableClass);

			DatabaseMetaData databaseMetaData = connection.getMetaData();
			DBInspector dbInspector = new DBInspector(connection);

			try (ResultSet resultSet1 = databaseMetaData.getPrimaryKeys(
					dbInspector.getCatalog(), dbInspector.getSchema(),
					tableName);
				ResultSet resultSet2 = databaseMetaData.getIndexInfo(
					dbInspector.getCatalog(), dbInspector.getSchema(),
					dbInspector.normalizeName(tableName), false, false)) {

				Set<String> primaryKeyNames = new HashSet<>();

				while (resultSet1.next()) {
					String primaryKeyName = StringUtil.toUpperCase(
						resultSet1.getString("PK_NAME"));

					if (primaryKeyName != null) {
						primaryKeyNames.add(primaryKeyName);
					}
				}

				Map<String, Set<String>> columnNamesMap = new HashMap<>();

				while (resultSet2.next()) {
					String indexName = StringUtil.toUpperCase(
						resultSet2.getString("INDEX_NAME"));

					if ((indexName == null) ||
						primaryKeyNames.contains(indexName)) {

						continue;
					}

					Set<String> columnNames = columnNamesMap.get(indexName);

					if (columnNames == null) {
						columnNames = new HashSet<>();

						columnNamesMap.put(indexName, columnNames);
					}

					columnNames.add(
						StringUtil.toUpperCase(
							resultSet2.getString("COLUMN_NAME")));
				}

				for (Alterable alterable : alterables) {
					for (Map.Entry<String, Set<String>> entry :
							columnNamesMap.entrySet()) {

						if (alterable.shouldDropIndex(entry.getValue())) {
							runSQL(
								StringBundler.concat(
									"drop index ", entry.getKey(), " on ",
									tableName));
						}
					}

					runSQL(alterable.getSQL(tableName));

					List<String> indexSQLs = getIndexSQLs(
						tableClass, tableName);

					if (ListUtil.isEmpty(indexSQLs)) {
						continue;
					}

					for (String indexSQL : indexSQLs) {
						if (alterable.shouldAddIndex(
								_getIndexColumnNames(indexSQL))) {

							runSQLTemplateString(indexSQL, true);
						}
					}
				}
			}
			catch (SQLException sqlException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Attempting to upgrade table ", tableName,
							" by recreating the table due to: ",
							sqlException.getMessage()));
				}

				Field tableColumnsField = tableClass.getField("TABLE_COLUMNS");
				Field tableSQLCreateField = tableClass.getField(
					"TABLE_SQL_CREATE");
				Field tableSQLAddIndexesField = tableClass.getField(
					"TABLE_SQL_ADD_INDEXES");

				upgradeTable(
					tableName, (Object[][])tableColumnsField.get(null),
					(String)tableSQLCreateField.get(null),
					(String[])tableSQLAddIndexesField.get(null));

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Successfully recreated and upgraded table " +
							tableName);
				}
			}
		}
	}

	protected abstract void doUpgrade() throws Exception;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getIndexSQLs(Class, String)}
	 */
	@Deprecated
	protected List<ObjectValuePair<String, IndexMetadata>> getIndexesSQL(
			ClassLoader classLoader, String tableName)
		throws IOException {

		if (!PortalClassLoaderUtil.isPortalClassLoader(classLoader)) {
			try (InputStream inputStream = classLoader.getResourceAsStream(
					"META-INF/sql/indexes.sql")) {

				if (inputStream == null) {
					return null;
				}

				List<ObjectValuePair<String, IndexMetadata>> objectValuePairs =
					new ArrayList<>();

				try (Reader reader = new InputStreamReader(inputStream);
					UnsyncBufferedReader unsyncBufferedReader =
						new UnsyncBufferedReader(reader)) {

					String line = null;

					while ((line = unsyncBufferedReader.readLine()) != null) {
						line = line.trim();

						if (line.isEmpty()) {
							continue;
						}

						IndexMetadata indexMetadata =
							IndexMetadataFactoryUtil.createIndexMetadata(line);

						if (tableName.equals(indexMetadata.getTableName())) {
							objectValuePairs.add(
								new ObjectValuePair<>(line, indexMetadata));
						}
					}
				}

				return objectValuePairs;
			}
		}

		if (!_portalIndexesSQL.isEmpty()) {
			return _portalIndexesSQL.get(tableName);
		}

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/portal/tools/sql/dependencies/indexes.sql");
			Reader reader = new InputStreamReader(inputStream);
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(reader)) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = line.trim();

				if (line.isEmpty()) {
					continue;
				}

				IndexMetadata indexMetadata =
					IndexMetadataFactoryUtil.createIndexMetadata(line);

				List<ObjectValuePair<String, IndexMetadata>> objectValuePairs =
					_portalIndexesSQL.get(indexMetadata.getTableName());

				if (objectValuePairs == null) {
					objectValuePairs = new ArrayList<>();

					_portalIndexesSQL.put(
						indexMetadata.getTableName(), objectValuePairs);
				}

				objectValuePairs.add(
					new ObjectValuePair<>(line, indexMetadata));
			}
		}

		return _portalIndexesSQL.get(tableName);
	}

	protected List<String> getIndexSQLs(Class<?> tableClass, String tableName)
		throws Exception {

		Field tableSQLAddIndexesField = tableClass.getField(
			"TABLE_SQL_ADD_INDEXES");

		String[] indexes = (String[])tableSQLAddIndexesField.get(null);

		return ListUtil.fromArray(indexes);
	}

	protected Map<String, Integer> getTableColumnsMap(Class<?> tableClass)
		throws Exception {

		Field tableNameField = tableClass.getField("TABLE_COLUMNS_MAP");

		return (Map<String, Integer>)tableNameField.get(null);
	}

	protected String getTableName(Class<?> tableClass) throws Exception {
		Field tableNameField = tableClass.getField("TABLE_NAME");

		return (String)tableNameField.get(null);
	}

	protected long increment() {
		return CounterLocalServiceUtil.increment();
	}

	protected long increment(String name) {
		return CounterLocalServiceUtil.increment(name);
	}

	protected long increment(String name, int size) {
		return CounterLocalServiceUtil.increment(name, size);
	}

	protected boolean isPortal62TableName(String tableName) {
		return _portal62TableNames.contains(StringUtil.toLowerCase(tableName));
	}

	protected boolean isSkipUpgradeProcess() throws Exception {
		return false;
	}

	protected boolean isSupportsAlterColumnName() {
		DB db = DBManagerUtil.getDB();

		return db.isSupportsAlterColumnName();
	}

	protected boolean isSupportsAlterColumnType() {
		DB db = DBManagerUtil.getDB();

		return db.isSupportsAlterColumnType();
	}

	protected boolean isSupportsStringCaseSensitiveQuery() {
		DB db = DBManagerUtil.getDB();

		return db.isSupportsStringCaseSensitiveQuery();
	}

	protected boolean isSupportsUpdateWithInnerJoin() {
		DB db = DBManagerUtil.getDB();

		return db.isSupportsUpdateWithInnerJoin();
	}

	protected void removePrimaryKey(String tableName) throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		String normalizedTableName = dbInspector.normalizeName(
			tableName, databaseMetaData);

		DB db = DBManagerUtil.getDB();

		DBType dbType = db.getDBType();

		if ((dbType == DBType.SQLSERVER) || (dbType == DBType.SYBASE)) {
			String primaryKeyConstraintName = null;

			if (dbType == DBType.SQLSERVER) {
				try (PreparedStatement preparedStatement =
						connection.prepareStatement(
							StringBundler.concat(
								"select name from sys.key_constraints where ",
								"type = 'PK' and ",
								"OBJECT_NAME(parent_object_id) = '",
								normalizedTableName, "'"));
					ResultSet resultSet = preparedStatement.executeQuery()) {

					if (resultSet.next()) {
						primaryKeyConstraintName = resultSet.getString("name");
					}
				}
			}
			else {
				try (PreparedStatement preparedStatement =
						connection.prepareStatement(
							"sp_helpconstraint " + normalizedTableName);
					ResultSet resultSet = preparedStatement.executeQuery()) {

					while (resultSet.next()) {
						String definition = resultSet.getString("definition");

						if (definition.startsWith("PRIMARY KEY INDEX")) {
							primaryKeyConstraintName = resultSet.getString(
								"name");

							break;
						}
					}
				}
			}

			if (primaryKeyConstraintName == null) {
				throw new UpgradeException(
					"No primary key constraint found for " +
						normalizedTableName);
			}

			runSQL(
				StringBundler.concat(
					"alter table ", normalizedTableName, " drop constraint ",
					primaryKeyConstraintName));
		}
		else {
			runSQL(
				StringBundler.concat(
					"alter table ", normalizedTableName, " drop primary key"));
		}
	}

	protected void updateIndexes(Class<?> tableClass) throws Exception {
		DB db = DBManagerUtil.getDB();

		Field tableSQLCreateField = tableClass.getField("TABLE_SQL_CREATE");
		Field tableSQLAddIndexesField = tableClass.getField(
			"TABLE_SQL_ADD_INDEXES");

		db.updateIndexes(
			connection, (String)tableSQLCreateField.get(null),
			StringUtil.merge(
				(String[])tableSQLAddIndexesField.get(null),
				System.lineSeparator()),
			true);
	}

	protected void upgradeTable(String tableName, Object[][] tableColumns)
		throws Exception {

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			tableName, tableColumns);

		upgradeTable.updateTable();
	}

	protected void upgradeTable(
			String tableName, Object[][] tableColumns, String createSQL,
			String[] indexesSQL, UpgradeColumn... upgradeColumns)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				tableName, tableColumns, upgradeColumns);

			upgradeTable.setCreateSQL(createSQL);
			upgradeTable.setIndexesSQL(indexesSQL);

			upgradeTable.updateTable();
		}
	}

	private Collection<String> _getIndexColumnNames(String indexSQL) {
		Matcher matcher = _sqlIndexRegexPattern.matcher(indexSQL);

		if (matcher.find()) {
			String indexColumnNames = matcher.group(1);

			indexColumnNames = indexColumnNames.trim();

			return Stream.of(
				indexColumnNames.split(StringPool.COMMA)
			).map(
				columnName -> columnName.replaceFirst("\\[.*", StringPool.BLANK)
			).collect(
				Collectors.toList()
			);
		}

		throw new IllegalArgumentException(
			"Not a valid SQL index: " + indexSQL);
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeProcess.class);

	private static final Set<String> _portal62TableNames = new HashSet<>(
		Arrays.asList(
			"account_", "address", "announcementsdelivery",
			"announcementsentry", "announcementsflag", "assetcategory",
			"assetcategoryproperty", "assetentries_assetcategories",
			"assetentries_assettags", "assetentry", "assetlink", "assettag",
			"assettagstats", "assetvocabulary", "backgroundtask", "blogsentry",
			"blogsstatsuser", "bookmarksentry", "bookmarksfolder",
			"browsertracker", "calevent", "classname_", "clustergroup",
			"company", "contact_", "counter", "country", "ddlrecord",
			"ddlrecordset", "ddlrecordversion", "ddmcontent", "ddmstoragelink",
			"ddmstructure", "ddmstructurelink", "ddmtemplate", "dlcontent",
			"dlfileentry", "dlfileentrymetadata", "dlfileentrytype",
			"dlfileentrytypes_dlfolders", "dlfilerank", "dlfileshortcut",
			"dlfileversion", "dlfolder", "dlsyncevent", "emailaddress",
			"expandocolumn", "expandorow", "expandotable", "expandovalue",
			"exportimportconfiguration", "group_", "groups_orgs",
			"groups_roles", "groups_usergroups", "image", "journalarticle",
			"journalarticleimage", "journalarticleresource",
			"journalcontentsearch", "journalfeed", "journalfolder",
			"journalstructure", "journaltemplate", "layout", "layoutbranch",
			"layoutfriendlyurl", "layoutprototype", "layoutrevision",
			"layoutset", "layoutsetbranch", "layoutsetprototype", "listtype",
			"lock_", "marketplace_app", "mbban", "mbcategory", "mbdiscussion",
			"mbmailinglist", "mbmessage", "mbstatsuser", "mbthread",
			"mbthreadflag", "mdraction", "mdrrule", "mdrrulegroup",
			"mdrrulegroupinstance", "membershiprequest", "organization_",
			"orggrouprole", "orglabor", "passwordpolicy", "passwordpolicyrel",
			"passwordtracker", "phone", "pluginsetting", "pollschoice",
			"pollsquestion", "pollsvote", "portalpreferences", "portlet",
			"portletitem", "portletpreferences", "ratingsentry", "ratingsstats",
			"recentlayoutbranch", "recentlayoutrevision",
			"recentlayoutsetbranch", "region", "release_", "repository",
			"repositoryentry", "resourceaction", "resourceblock",
			"resourceblockpermission", "resourcepermission",
			"resourcetypepermission", "role_", "servicecomponent",
			"socialactivity", "socialactivityachievement",
			"socialactivitycounter", "socialactivitylimit", "socialactivityset",
			"socialactivitysetting", "socialrelation", "socialrequest",
			"subscription", "systemevent", "team", "ticket", "trashentry",
			"trashversion", "usernotificationdelivery", "user_", "usergroup",
			"usergroupgrouprole", "usergrouprole", "usergroups_teams",
			"useridmapper", "usernotificationevent", "users_groups",
			"users_orgs", "users_roles", "users_teams", "users_usergroups",
			"usertracker", "usertrackerpath", "virtualhost", "webdavprops",
			"website", "wikinode", "wikipage", "wikipageresource",
			"workflowdefinitionlink", "workflowinstancelink"));
	private static final Map
		<String, List<ObjectValuePair<String, IndexMetadata>>>
			_portalIndexesSQL = new HashMap<>();
	private static final Pattern _sqlIndexRegexPattern = Pattern.compile(
		".+?\\((.*)\\)");

}
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

package com.liferay.portlet.documentlibrary.service.persistence.impl;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryFinder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class DLFileEntryFinderImpl
	extends DLFileEntryFinderBaseImpl implements DLFileEntryFinder {

	public static final String COUNT_BY_EXTRA_SETTINGS =
		DLFileEntryFinder.class.getName() + ".countByExtraSettings";

	public static final String COUNT_BY_G_F =
		DLFileEntryFinder.class.getName() + ".countByG_F";

	public static final String COUNT_BY_G_M_R =
		DLFileEntryFinder.class.getName() + ".countByG_M_R";

	public static final String COUNT_BY_G_U_F =
		DLFileEntryFinder.class.getName() + ".countByG_U_F";

	public static final String COUNT_BY_G_F_S =
		DLFileEntryFinder.class.getName() + ".countByG_F_S";

	public static final String FIND_BY_COMPANY_ID =
		DLFileEntryFinder.class.getName() + ".findByCompanyId";

	public static final String FIND_BY_DDM_STRUCTURE_IDS =
		DLFileEntryFinder.class.getName() + ".findByDDMStructureIds";

	public static final String FIND_BY_NO_ASSETS =
		DLFileEntryFinder.class.getName() + ".findByNoAssets";

	public static final String FIND_BY_EXTRA_SETTINGS =
		DLFileEntryFinder.class.getName() + ".findByExtraSettings";

	public static final String FIND_BY_ORPHANED_FILE_ENTRIES =
		DLFileEntryFinder.class.getName() + ".findByOrphanedFileEntries";

	public static final String FIND_BY_G_F =
		DLFileEntryFinder.class.getName() + ".findByG_F";

	public static final String FIND_BY_C_T =
		DLFileEntryFinder.class.getName() + ".findByC_T";

	public static final String FIND_BY_G_U_F =
		DLFileEntryFinder.class.getName() + ".findByG_U_F";

	@Override
	public int countByExtraSettings() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_EXTRA_SETTINGS);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByG_F(
		long groupId, List<Long> folderIds,
		QueryDefinition<DLFileEntry> queryDefinition) {

		return doCountByG_F(groupId, folderIds, queryDefinition, false);
	}

	@Override
	public int countByG_R_F(
		long groupId, List<Long> repositoryIds, List<Long> folderIds,
		QueryDefinition<DLFileEntry> queryDefinition) {

		return doCountByG_U_R_F_M(
			groupId, 0, repositoryIds, folderIds, null, queryDefinition, false);
	}

	@Override
	public int countByG_U_F_M(
		long groupId, long userId, List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition) {

		List<Long> repositoryIds = Collections.emptyList();

		return doCountByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition, false);
	}

	@Override
	public int countByG_U_R_F_M(
		long groupId, long userId, List<Long> repositoryIds,
		List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition) {

		return doCountByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition, false);
	}

	@Override
	public int filterCountByG_F(
		long groupId, List<Long> folderIds,
		QueryDefinition<DLFileEntry> queryDefinition) {

		return doCountByG_F(groupId, folderIds, queryDefinition, true);
	}

	@Override
	public int filterCountByG_R_F(
		long groupId, List<Long> repositoryIds, List<Long> folderIds,
		QueryDefinition<DLFileEntry> queryDefinition) {

		return doCountByG_U_R_F_M(
			groupId, 0, repositoryIds, folderIds, null, queryDefinition, true);
	}

	@Override
	public int filterCountByG_U_F_M(
		long groupId, long userId, List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition) {

		List<Long> repositoryIds = Collections.emptyList();

		return doCountByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition, true);
	}

	@Override
	public int filterCountByG_U_R_F_M(
		long groupId, long userId, List<Long> repositoryIds,
		List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition) {

		return doCountByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition, true);
	}

	@Override
	public List<DLFileEntry> filterFindByG_F(
		long groupId, List<Long> folderIds,
		QueryDefinition<DLFileEntry> queryDefinition) {

		List<Long> repositoryIds = Collections.emptyList();

		return doFindByG_U_R_F_M(
			groupId, 0, repositoryIds, folderIds, null, queryDefinition, true);
	}

	@Override
	public List<DLFileEntry> filterFindByG_R_F(
		long groupId, List<Long> repositoryIds, List<Long> folderIds,
		QueryDefinition<DLFileEntry> queryDefinition) {

		return doFindByG_U_R_F_M(
			groupId, 0, repositoryIds, folderIds, null, queryDefinition, true);
	}

	@Override
	public List<DLFileEntry> filterFindByG_U_F_M(
		long groupId, long userId, List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition) {

		List<Long> repositoryIds = Collections.emptyList();

		return doFindByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition, true);
	}

	@Override
	public List<DLFileEntry> filterFindByG_U_R_F_M(
		long groupId, long userId, List<Long> repositoryIds,
		List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition) {

		return doFindByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition, true);
	}

	@Override
	public List<DLFileEntry> findByCompanyId(
		long companyId, QueryDefinition<DLFileEntry> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				FIND_BY_COMPANY_ID, queryDefinition,
				DLFileVersionImpl.TABLE_NAME);

			sql = CustomSQLUtil.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				DLFileEntryImpl.TABLE_NAME, DLFileEntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);
			queryPos.add(queryDefinition.getStatus());

			return (List<DLFileEntry>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<DLFileEntry> findByDDMStructureIds(
		long groupId, long[] ddmStructureIds, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			if ((ddmStructureIds == null) || (ddmStructureIds.length <= 0)) {
				return Collections.emptyList();
			}

			String sql = CustomSQLUtil.get(FIND_BY_DDM_STRUCTURE_IDS);

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(
					sql, "(DLFileEntry.groupId = ?) AND");
			}

			sql = StringUtil.replace(
				sql, "[$DDM_STRUCTURE_ID$]",
				getDDMStructureIds(ddmStructureIds));

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				DLFileEntryImpl.TABLE_NAME, DLFileEntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (groupId > 0) {
				queryPos.add(groupId);
			}

			queryPos.add(ddmStructureIds);

			return (List<DLFileEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<DLFileEntry> findByDDMStructureIds(
		long[] ddmStructureIds, int start, int end) {

		return findByDDMStructureIds(0, ddmStructureIds, start, end);
	}

	@Override
	public List<DLFileEntry> findByNoAssets() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				DLFileEntryImpl.TABLE_NAME, DLFileEntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(
				PortalUtil.getClassNameId(DLFileEntryConstants.getClassName()));

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<DLFileEntry> findByExtraSettings(int start, int end) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_EXTRA_SETTINGS);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar("fileEntryId", Type.LONG);

			List<Long> fileEntryIds = (List<Long>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);

			List<DLFileEntry> dlFileEntries = new ArrayList<>(
				fileEntryIds.size());

			for (long fileEntryId : fileEntryIds) {
				dlFileEntries.add(
					dlFileEntryPersistence.findByPrimaryKey(fileEntryId));
			}

			return Collections.unmodifiableList(dlFileEntries);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<DLFileEntry> findByOrphanedFileEntries() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_ORPHANED_FILE_ENTRIES);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				DLFileEntryImpl.TABLE_NAME, DLFileEntryImpl.class);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<DLFileEntry> findByG_F(
		long groupId, List<Long> folderIds,
		QueryDefinition<DLFileEntry> queryDefinition) {

		List<Long> repositoryIds = Collections.emptyList();

		return doFindByG_U_R_F_M(
			groupId, 0, repositoryIds, folderIds, null, queryDefinition, false);
	}

	@Override
	public List<DLFileEntry> findByC_T(long classNameId, String treePath) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_T);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(
				CustomSQLUtil.keywords(treePath, WildcardMode.TRAILING)[0]);
			queryPos.add(classNameId);

			sqlQuery.addEntity(
				DLFileEntryImpl.TABLE_NAME, DLFileEntryImpl.class);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<DLFileEntry> findByG_R_F(
		long groupId, List<Long> repositoryIds, List<Long> folderIds,
		QueryDefinition<DLFileEntry> queryDefinition) {

		return doFindByG_U_R_F_M(
			groupId, 0, repositoryIds, folderIds, null, queryDefinition, false);
	}

	@Override
	public List<DLFileEntry> findByG_U_F(
		long groupId, long userId, List<Long> folderIds,
		QueryDefinition<DLFileEntry> queryDefinition) {

		List<Long> repositoryIds = Collections.emptyList();

		return doFindByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, null, queryDefinition,
			false);
	}

	@Override
	public List<DLFileEntry> findByG_U_F_M(
		long groupId, long userId, List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition) {

		List<Long> repositoryIds = Collections.emptyList();

		return doFindByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition, false);
	}

	@Override
	public List<DLFileEntry> findByG_U_R_F(
		long groupId, long userId, List<Long> repositoryIds,
		List<Long> folderIds, QueryDefinition<DLFileEntry> queryDefinition) {

		return doFindByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, null, queryDefinition,
			false);
	}

	@Override
	public List<DLFileEntry> findByG_U_R_F_M(
		long groupId, long userId, List<Long> repositoryIds,
		List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition) {

		return doFindByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition, false);
	}

	protected int doCountByG_F(
		long groupId, List<Long> folderIds,
		QueryDefinition<DLFileEntry> queryDefinition, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			List<Long> repositoryIds = Collections.emptyList();

			String sql = getFileEntriesSQL(
				COUNT_BY_G_F_S, groupId, repositoryIds, folderIds, null,
				queryDefinition, inlineSQLHelper);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(queryDefinition.getStatus());

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountByG_U_R_F_M(
		long groupId, long userId, List<Long> repositoryIds,
		List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String id = null;

			if (userId <= 0) {
				id = COUNT_BY_G_F;
			}
			else {
				id = COUNT_BY_G_U_F;
			}

			String sql = getFileEntriesSQL(
				id, groupId, repositoryIds, folderIds, mimeTypes,
				queryDefinition, inlineSQLHelper);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (userId > 0) {
				queryPos.add(userId);
				queryPos.add(userId);
			}

			queryPos.add(queryDefinition.getStatus());

			for (Long repositoryId : repositoryIds) {
				queryPos.add(repositoryId);
			}

			if (mimeTypes != null) {
				queryPos.add(mimeTypes);
			}

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<DLFileEntry> doFindByG_U_R_F_M(
		long groupId, long userId, List<Long> repositoryIds,
		List<Long> folderIds, String[] mimeTypes,
		QueryDefinition<DLFileEntry> queryDefinition, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String id = null;

			if (userId <= 0) {
				id = FIND_BY_G_F;
			}
			else {
				id = FIND_BY_G_U_F;
			}

			String sql = getFileEntriesSQL(
				id, groupId, repositoryIds, folderIds, mimeTypes,
				queryDefinition, inlineSQLHelper);

			sql = CustomSQLUtil.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				DLFileEntryImpl.TABLE_NAME, DLFileEntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (userId > 0) {
				queryPos.add(userId);
				queryPos.add(userId);
			}

			queryPos.add(queryDefinition.getStatus());

			for (Long repositoryId : repositoryIds) {
				queryPos.add(repositoryId);
			}

			if (mimeTypes != null) {
				queryPos.add(mimeTypes);
			}

			return (List<DLFileEntry>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getDDMStructureIds(long[] ddmStructureIds) {
		StringBundler sb = new StringBundler(
			((ddmStructureIds.length * 2) - 1) + 2);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < ddmStructureIds.length; i++) {
			sb.append("DDMStructureLink.structureId = ?");

			if ((i + 1) != ddmStructureIds.length) {
				sb.append(WHERE_OR);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected String getFileEntriesSQL(
		String id, long groupId, List<Long> repositoryIds, List<Long> folderIds,
		String[] mimeTypes, QueryDefinition<DLFileEntry> queryDefinition,
		boolean inlineSQLHelper) {

		String tableName = DLFileVersionImpl.TABLE_NAME;

		String sql = CustomSQLUtil.get(id, queryDefinition, tableName);

		if (queryDefinition.getStatus() == WorkflowConstants.STATUS_ANY) {
			sql = StringUtil.removeSubstring(sql, "[$JOIN$]");

			tableName = DLFileEntryImpl.TABLE_NAME;
		}
		else {
			sql = StringUtil.replace(
				sql, "[$JOIN$]",
				CustomSQLUtil.get(
					DLFolderFinderImpl.JOIN_FE_BY_DL_FILE_VERSION));
		}

		if (inlineSQLHelper && InlineSQLHelperUtil.isEnabled()) {
			if (queryDefinition.getStatus() == WorkflowConstants.STATUS_ANY) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFileEntry.class.getName(), "DLFileEntry.fileEntryId",
					groupId);
			}
			else {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFileEntry.class.getName(),
					"DLFileVersion.fileEntryId", groupId);
			}
		}

		if (ListUtil.isNotEmpty(repositoryIds) ||
			ListUtil.isNotEmpty(folderIds) || ArrayUtil.isNotEmpty(mimeTypes)) {

			StringBundler sb = new StringBundler(12);

			if (ListUtil.isNotEmpty(repositoryIds)) {
				sb.append(WHERE_AND);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(getRepositoryIds(repositoryIds, tableName));
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			if (ListUtil.isNotEmpty(folderIds)) {
				sb.append(WHERE_AND);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(getFolderIds(folderIds, tableName));
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			if (ArrayUtil.isNotEmpty(mimeTypes)) {
				sb.append(WHERE_AND);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(getMimeTypes(mimeTypes, tableName));
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			return StringUtil.replace(sql, "[$FOLDER_ID$]", sb.toString());
		}

		return StringUtil.removeSubstring(sql, "[$FOLDER_ID$]");
	}

	protected String getFolderIds(List<Long> folderIds, String tableName) {
		if (folderIds.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler((folderIds.size() * 3) + 1);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < folderIds.size(); i++) {
			sb.append(tableName);
			sb.append(".folderId = ");
			sb.append(folderIds.get(i));

			if ((i + 1) != folderIds.size()) {
				sb.append(WHERE_OR);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected String getMimeTypes(String[] mimeTypes, String tableName) {
		if (mimeTypes.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler((mimeTypes.length * 3) - 1);

		for (int i = 0; i < mimeTypes.length; i++) {
			sb.append(tableName);
			sb.append(".mimeType = ?");

			if ((i + 1) != mimeTypes.length) {
				sb.append(WHERE_OR);
			}
		}

		return sb.toString();
	}

	protected String getRepositoryIds(
		List<Long> repositoryIds, String tableName) {

		if (repositoryIds.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler((repositoryIds.size() * 3) + 1);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < repositoryIds.size(); i++) {
			sb.append(tableName);
			sb.append(".repositoryId = ? ");

			if ((i + 1) != repositoryIds.size()) {
				sb.append(WHERE_OR);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

}
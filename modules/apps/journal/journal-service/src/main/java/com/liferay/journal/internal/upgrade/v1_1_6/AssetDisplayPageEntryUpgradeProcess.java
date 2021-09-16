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

package com.liferay.journal.internal.upgrade.v1_1_6;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.BaseUpgradeCallable;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Vendel Toreki
 */
public class AssetDisplayPageEntryUpgradeProcess extends UpgradeProcess {

	public AssetDisplayPageEntryUpgradeProcess(
		AssetDisplayPageEntryLocalService assetDisplayPageEntryLocalService,
		CompanyLocalService companyLocalService) {

		_assetDisplayPageEntryLocalService = assetDisplayPageEntryLocalService;
		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> updateAssetDisplayPageEntry(company));
	}

	protected void updateAssetDisplayPageEntry(Company company)
		throws Exception {

		_init(company.getCompanyId());

		long journalArticleClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class);
		User user = company.getDefaultUser();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select JournalArticle.groupId, ",
						"JournalArticle.resourcePrimKey, AssetEntry.classUuid ",
						"from JournalArticle inner join AssetEntry on ( ",
						"AssetEntry.classNameId = ? and AssetEntry.classPK = ",
						"JournalArticle.resourcePrimKey ) inner join Group_ ",
						"on ( Group_.groupId = JournalArticle.groupId) where ",
						"JournalArticle.companyId = ? and ",
						"JournalArticle.layoutUuid is not null and ",
						"JournalArticle.layoutUuid != '' and ",
						"Group_.remoteStagingGroupCount = 0 and not exists ( ",
						"select 1 from AssetDisplayPageEntry where ",
						"AssetDisplayPageEntry.groupId = ",
						"JournalArticle.groupId and ",
						"AssetDisplayPageEntry.classNameId = ? and ",
						"AssetDisplayPageEntry.classPK = ",
						"JournalArticle.resourcePrimKey) group by ",
						"JournalArticle.groupId, ",
						"JournalArticle.resourcePrimKey, ",
						"AssetEntry.classUuid")))) {

			preparedStatement1.setLong(1, journalArticleClassNameId);
			preparedStatement1.setLong(2, company.getCompanyId());
			preparedStatement1.setLong(3, journalArticleClassNameId);

			List<SaveAssetDisplayPageEntryUpgradeCallable>
				saveAssetDisplayPageEntryUpgradeCallables = new ArrayList<>();

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long groupId = resultSet.getLong("groupId");
					long resourcePrimKey = resultSet.getLong("resourcePrimKey");
					String journalArticleUuid = resultSet.getString(
						"classUuid");

					SaveAssetDisplayPageEntryUpgradeCallable
						saveAssetDisplayPageEntryUpgradeCallable =
							new SaveAssetDisplayPageEntryUpgradeCallable(
								groupId, user.getUserId(),
								journalArticleClassNameId, resourcePrimKey,
								_generateLocalStagingAwareUUID(
									groupId, journalArticleUuid));

					saveAssetDisplayPageEntryUpgradeCallables.add(
						saveAssetDisplayPageEntryUpgradeCallable);
				}
			}

			ExecutorService executorService = Executors.newWorkStealingPool();

			List<Future<Boolean>> futures = executorService.invokeAll(
				saveAssetDisplayPageEntryUpgradeCallables);

			executorService.shutdown();

			for (Future<Boolean> future : futures) {
				boolean success = GetterUtil.get(future.get(), true);

				if (!success) {
					throw new UpgradeException(
						"Unable to add asset display pages for the journal " +
							"articles");
				}
			}
		}
	}

	private String _generateLocalStagingAwareUUID(
		long groupId, String journalArticleUuid) {

		if (!_stagedGroupIds.contains(groupId)) {
			return PortalUUIDUtil.generate();
		}

		long liveGroupId = groupId;

		if (_liveGroupIdsMap.containsKey(groupId)) {
			liveGroupId = _liveGroupIdsMap.get(groupId);
		}

		if (!_uuidsMaps.containsKey(liveGroupId)) {
			_uuidsMaps.put(liveGroupId, new HashMap<>());
		}

		Map<String, String> uuids = _uuidsMaps.get(liveGroupId);

		if (uuids.containsKey(journalArticleUuid)) {
			return uuids.get(journalArticleUuid);
		}

		String newUuid = PortalUUIDUtil.generate();

		uuids.put(journalArticleUuid, newUuid);

		return newUuid;
	}

	private void _init(long companyId) throws Exception {
		_liveGroupIdsMap.clear();
		_stagedGroupIds.clear();
		_uuidsMaps.clear();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select groupId, liveGroupId from Group_ where ",
						"companyId = ? and liveGroupId is not null and ",
						"liveGroupId != 0 and remoteStagingGroupCount = 0")))) {

			preparedStatement.setLong(1, companyId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long groupId = resultSet.getLong("groupId");
					long liveGroupId = resultSet.getLong("liveGroupId");

					_liveGroupIdsMap.put(groupId, liveGroupId);

					_stagedGroupIds.add(groupId);
					_stagedGroupIds.add(liveGroupId);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetDisplayPageEntryUpgradeProcess.class);

	private final AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;
	private final CompanyLocalService _companyLocalService;
	private final Map<Long, Long> _liveGroupIdsMap = new HashMap<>();
	private final Set<Long> _stagedGroupIds = new HashSet<>();
	private final Map<Long, Map<String, String>> _uuidsMaps = new HashMap<>();

	private class SaveAssetDisplayPageEntryUpgradeCallable
		extends BaseUpgradeCallable<Boolean> {

		public SaveAssetDisplayPageEntryUpgradeCallable(
			long groupId, long userId, long classNameId, long classPK,
			String uuid) {

			_groupId = groupId;
			_userId = userId;
			_classNameId = classNameId;
			_classPK = classPK;
			_uuid = uuid;
		}

		@Override
		protected Boolean doCall() throws Exception {
			try {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setUuid(_uuid);

				_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
					_userId, _groupId, _classNameId, _classPK, 0,
					AssetDisplayPageConstants.TYPE_SPECIFIC, serviceContext);
			}
			catch (Exception exception) {
				_log.error(
					"Unable to add asset display page entry for article " +
						_classPK,
					exception);

				return false;
			}

			return true;
		}

		private final long _classNameId;
		private final long _classPK;
		private final long _groupId;
		private final long _userId;
		private final String _uuid;

	}

}
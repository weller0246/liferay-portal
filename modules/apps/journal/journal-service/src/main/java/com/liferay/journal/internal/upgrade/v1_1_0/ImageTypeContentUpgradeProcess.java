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

package com.liferay.journal.internal.upgrade.v1_1_0;

import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.internal.upgrade.util.JournalArticleImageUpgradeHelper;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.upgrade.BaseUpgradeCallable;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Eudaldo Alonso
 */
public class ImageTypeContentUpgradeProcess extends UpgradeProcess {

	public ImageTypeContentUpgradeProcess(
		ImageLocalService imageLocalService,
		JournalArticleImageUpgradeHelper journalArticleImageUpgradeHelper,
		PortletFileRepository portletFileRepository) {

		_imageLocalService = imageLocalService;
		_journalArticleImageUpgradeHelper = journalArticleImageUpgradeHelper;
		_portletFileRepository = portletFileRepository;
	}

	protected void copyJournalArticleImagesToJournalRepository()
		throws Exception {

		List<SaveImageFileEntryUpgradeCallable>
			saveImageFileEntryUpgradeCallables = new ArrayList<>();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			Statement statement = connection.createStatement();
			ResultSet resultSet1 = statement.executeQuery(
				StringBundler.concat(
					"select JournalArticleImage.articleImageId, ",
					"JournalArticleImage.groupId, ",
					"JournalArticleImage.companyId, ",
					"JournalArticle.resourcePrimKey, JournalArticle.userId ",
					"from JournalArticleImage inner join JournalArticle on ",
					"(JournalArticle.groupId = JournalArticleImage.groupId ",
					"and JournalArticle.articleId = ",
					"JournalArticleImage.articleId and JournalArticle.version ",
					"= JournalArticleImage.version)"))) {

			while (resultSet1.next()) {
				long articleImageId = resultSet1.getLong(1);
				long groupId = resultSet1.getLong(2);
				long companyId = resultSet1.getLong(3);
				long resourcePrimKey = resultSet1.getLong(4);

				long userId = PortalUtil.getValidUserId(
					companyId, resultSet1.getLong(5));

				long folderId = _journalArticleImageUpgradeHelper.getFolderId(
					userId, groupId, resourcePrimKey);

				SaveImageFileEntryUpgradeCallable
					saveImageFileEntryUpgradeCallable =
						new SaveImageFileEntryUpgradeCallable(
							articleImageId, folderId, groupId, resourcePrimKey,
							userId);

				saveImageFileEntryUpgradeCallables.add(
					saveImageFileEntryUpgradeCallable);
			}

			ExecutorService executorService = Executors.newWorkStealingPool();

			List<Future<Boolean>> futures = executorService.invokeAll(
				saveImageFileEntryUpgradeCallables);

			executorService.shutdown();

			for (Future<Boolean> future : futures) {
				boolean success = GetterUtil.get(future.get(), true);

				if (!success) {
					throw new UpgradeException(
						"Unable to copy journal article images to the file " +
							"repository");
				}
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		copyJournalArticleImagesToJournalRepository();
		dropJournalArticleImageTable();
	}

	protected void dropJournalArticleImageTable() throws Exception {
		runSQL(connection, "drop table JournalArticleImage");

		if (_log.isInfoEnabled()) {
			_log.info("Deleted table JournalArticleImage");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageTypeContentUpgradeProcess.class);

	private final ImageLocalService _imageLocalService;
	private final JournalArticleImageUpgradeHelper
		_journalArticleImageUpgradeHelper;
	private final PortletFileRepository _portletFileRepository;

	private class SaveImageFileEntryUpgradeCallable
		extends BaseUpgradeCallable<Boolean> {

		public SaveImageFileEntryUpgradeCallable(
			long articleImageId, long folderId, long groupId,
			long resourcePrimaryKey, long userId) {

			_articleImageId = articleImageId;
			_folderId = folderId;
			_groupId = groupId;
			_resourcePrimaryKey = resourcePrimaryKey;
			_userId = userId;
		}

		@Override
		protected Boolean doCall() throws Exception {
			String fileName = String.valueOf(_articleImageId);

			FileEntry fileEntry = _portletFileRepository.fetchPortletFileEntry(
				_groupId, _folderId, fileName);

			if (fileEntry != null) {
				return null;
			}

			try {
				Image image = _imageLocalService.getImage(_articleImageId);

				if (image == null) {
					return null;
				}

				String mimeType = MimeTypesUtil.getContentType(
					fileName + StringPool.PERIOD + image.getType());

				_portletFileRepository.addPortletFileEntry(
					_groupId, _userId, JournalArticle.class.getName(),
					_resourcePrimaryKey, JournalConstants.SERVICE_NAME,
					_folderId, image.getTextObj(), fileName, mimeType, false);

				_imageLocalService.deleteImage(image.getImageId());
			}
			catch (Exception exception) {
				_log.error(
					"Unable to add the journal article image " + fileName +
						" into the file repository",
					exception);

				return false;
			}

			return true;
		}

		private final long _articleImageId;
		private final long _folderId;
		private final long _groupId;
		private final long _resourcePrimaryKey;
		private final long _userId;

	}

}
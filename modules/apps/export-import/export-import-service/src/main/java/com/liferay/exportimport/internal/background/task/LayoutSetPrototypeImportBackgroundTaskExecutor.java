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

package com.liferay.exportimport.internal.background.task;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.sites.kernel.util.Sites;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.File;
import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Tamas Molnar
 */
public class LayoutSetPrototypeImportBackgroundTaskExecutor
	extends BaseExportImportBackgroundTaskExecutor {

	public LayoutSetPrototypeImportBackgroundTaskExecutor() {
		setBackgroundTaskStatusMessageTranslator(
			new LayoutExportImportBackgroundTaskStatusMessageTranslator());

		// Isolation level guarantees this will be serial in a group

		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_GROUP);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		LayoutSetPrototypeImportBackgroundTaskExecutor
			layoutSetPrototypeImportBackgroundTaskExecutor =
				new LayoutSetPrototypeImportBackgroundTaskExecutor();

		layoutSetPrototypeImportBackgroundTaskExecutor.
			setBackgroundTaskStatusMessageTranslator(
				getBackgroundTaskStatusMessageTranslator());
		layoutSetPrototypeImportBackgroundTaskExecutor.setIsolationLevel(
			getIsolationLevel());

		return layoutSetPrototypeImportBackgroundTaskExecutor;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		List<FileEntry> attachmentsFileEntries =
			backgroundTask.getAttachmentsFileEntries();

		File file = null;

		for (FileEntry attachmentsFileEntry : attachmentsFileEntries) {
			try {
				file = FileUtil.createTempFile("lar");

				FileUtil.write(file, attachmentsFileEntry.getContentStream());

				TransactionInvokerUtil.invoke(
					transactionConfig,
					new LayoutImportCallable(exportImportConfiguration, file));
			}
			catch (Throwable throwable) {
				Map<String, Serializable> settingsMap =
					exportImportConfiguration.getSettingsMap();

				Map<String, String[]> parameterMap =
					(Map<String, String[]>)settingsMap.get("parameterMap");

				long layoutSetPrototypeId = MapUtil.getLong(
					parameterMap, "layoutSetPrototypeId");

				LayoutSetPrototype layoutSetPrototype =
					LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(
						layoutSetPrototypeId);

				LayoutSet layoutSetPrototypeLayoutSet =
					layoutSetPrototype.getLayoutSet();

				UnicodeProperties layoutSetPrototypeSettingsUnicodeProperties =
					layoutSetPrototypeLayoutSet.getSettingsProperties();

				int mergeFailCount = GetterUtil.getInteger(
					layoutSetPrototypeSettingsUnicodeProperties.getProperty(
						Sites.MERGE_FAIL_COUNT));

				mergeFailCount++;

				_log.error(
					StringBundler.concat(
						"Merge fail count increased to ", mergeFailCount,
						" for layout set prototype ",
						layoutSetPrototype.getLayoutSetPrototypeId()),
					throwable);

				SitesUtil.setMergeFailCount(layoutSetPrototype, mergeFailCount);
			}
			finally {
				MergeLayoutPrototypesThreadLocal.setInProgress(false);

				FileUtil.delete(file);
			}
		}

		return BackgroundTaskResult.SUCCESS;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeImportBackgroundTaskExecutor.class);

	private static class LayoutImportCallable implements Callable<Void> {

		public LayoutImportCallable(
			ExportImportConfiguration exportImportConfiguration, File file) {

			_exportImportConfiguration = exportImportConfiguration;
			_file = file;
		}

		@Override
		public Void call() throws PortalException {
			try {
				MergeLayoutPrototypesThreadLocal.setInProgress(true);

				ExportImportLocalServiceUtil.importLayoutsDataDeletions(
					_exportImportConfiguration, _file);

				ExportImportLocalServiceUtil.importLayouts(
					_exportImportConfiguration, _file);

				return null;
			}
			finally {
				MergeLayoutPrototypesThreadLocal.setInProgress(false);
			}
		}

		private final ExportImportConfiguration _exportImportConfiguration;
		private final File _file;

	}

}
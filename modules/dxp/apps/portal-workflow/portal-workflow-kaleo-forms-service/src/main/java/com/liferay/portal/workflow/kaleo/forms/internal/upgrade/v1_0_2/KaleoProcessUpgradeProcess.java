/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_2;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Locale;

/**
 * @author Inácio Nery
 */
public class KaleoProcessUpgradeProcess extends UpgradeProcess {

	public KaleoProcessUpgradeProcess(
		AssetEntryLocalService assetEntryLocalService,
		DDLRecordLocalService ddlRecordLocalService,
		DDLRecordSetLocalService ddlRecordSetLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_ddlRecordLocalService = ddlRecordLocalService;
		_ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from KaleoProcess");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String uuid = resultSet.getString("uuid_");
				long kaleoProcessId = resultSet.getLong("kaleoProcessId");
				long groupId = resultSet.getLong("groupId");
				long companyId = resultSet.getLong("companyId");
				long userId = resultSet.getLong("userId");
				Timestamp createDate = resultSet.getTimestamp("createDate");
				Timestamp modifiedDate = resultSet.getTimestamp("modifiedDate");
				long ddlRecordSetId = resultSet.getLong("DDLRecordSetId");

				if (Validator.isNull(uuid)) {
					uuid = PortalUUIDUtil.generate();

					runSQL(
						StringBundler.concat(
							"update KaleoProcess set uuid_ = '", uuid,
							"' where kaleoProcessId = ", kaleoProcessId));
				}

				updateAssetEntry(
					groupId, companyId, userId, createDate, modifiedDate,
					kaleoProcessId, uuid, ddlRecordSetId);
			}
		}
	}

	protected String getAssetEntryTitle(long companyId, long ddlRecordSetId)
		throws PortalException {

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getDDLRecordSet(
			ddlRecordSetId);

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		Locale locale = getDefaultLocale(companyId);

		return LanguageUtil.format(
			locale, "new-x-for-list-x",
			new Object[] {
				ddmStructure.getName(locale), ddlRecordSet.getName(locale)
			},
			false);
	}

	protected Locale getDefaultLocale(long companyId) {
		String locale = null;

		try {
			locale = UpgradeProcessUtil.getDefaultLanguageId(companyId);
		}
		catch (SQLException sqlException) {
			_log.error(
				"Unable to get default locale for company " + companyId,
				sqlException);

			throw new RuntimeException(sqlException);
		}

		return LocaleUtil.fromLanguageId(locale);
	}

	protected void updateAssetEntry(
			long groupId, long companyId, long userId, Timestamp createDate,
			Timestamp modifiedDate, long kaleoProcessId, String uuid,
			long ddlRecordSetId)
		throws PortalException {

		String title = getAssetEntryTitle(companyId, ddlRecordSetId);

		ActionableDynamicQuery actionableDynamicQuery =
			_ddlRecordLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property recordSetIdProperty = PropertyFactoryUtil.forName(
					"recordSetId");

				dynamicQuery.add(recordSetIdProperty.eq(ddlRecordSetId));
			});
		actionableDynamicQuery.setParallel(true);
		actionableDynamicQuery.setPerformActionMethod(
			(DDLRecord ddlRecord) -> _assetEntryLocalService.updateEntry(
				userId, groupId, createDate, modifiedDate,
				KaleoProcess.class.getName(), ddlRecord.getRecordId(), uuid, 0,
				null, null, true, true, null, null, null, null,
				ContentTypes.TEXT_HTML, title, null, StringPool.BLANK, null,
				null, 0, 0, null));

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoProcessUpgradeProcess.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDLRecordSetLocalService _ddlRecordSetLocalService;

}
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

package com.liferay.layout.page.template.internal.upgrade.v1_1_1;

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jonathan McCann
 */
public class LayoutPageTemplateEntryUpgradeProcess extends UpgradeProcess {

	public LayoutPageTemplateEntryUpgradeProcess(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select layoutPageTemplateEntryId, companyId, name, ",
						"layoutPrototypeId from LayoutPageTemplateEntry where ",
						"type_ = ",
						LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE,
						" and groupId in (select groupId from Group_ where ",
						"site = [$FALSE$])")));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long layoutPageTemplateEntryId = resultSet.getLong(
					"layoutPageTemplateEntryId");
				long companyId = resultSet.getLong("companyId");
				String name = resultSet.getString("name");
				long layoutPrototypeId = resultSet.getLong("layoutPrototypeId");

				_updateLayoutPageTemplateEntry(
					layoutPageTemplateEntryId, companyId, name,
					layoutPrototypeId);
			}
		}
	}

	private void _updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, long companyId, String name,
			long layoutPrototypeId)
		throws Exception {

		Company company = _companyLocalService.getCompany(companyId);

		String newName = name;

		for (int i = 1;; i++) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"select count(*) from LayoutPageTemplateEntry ",
							"where groupId = ", company.getGroupId(),
							" and name = '", newName, "'"));
				ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next() && (resultSet.getInt(1) > 0)) {
					newName = name + i;
				}
				else {
					break;
				}
			}
		}

		runSQL(
			StringBundler.concat(
				"update LayoutPageTemplateEntry set groupId = ",
				company.getGroupId(),
				", layoutPageTemplateCollectionId = 0, name = '", newName,
				"' where layoutPageTemplateEntryId = ",
				layoutPageTemplateEntryId));

		runSQL(
			StringBundler.concat(
				"delete from LayoutPageTemplateEntry where groupId <> ",
				company.getGroupId(),
				" and layoutPageTemplateCollectionId <> 0 and type_ = ",
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE,
				" and layoutPrototypeId = ", layoutPrototypeId));
	}

	private final CompanyLocalService _companyLocalService;

}
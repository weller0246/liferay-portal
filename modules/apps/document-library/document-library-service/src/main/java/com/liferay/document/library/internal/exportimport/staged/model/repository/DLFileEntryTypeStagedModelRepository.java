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

package com.liferay.document.library.internal.exportimport.staged.model.repository;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntryType",
	service = StagedModelRepository.class
)
public class DLFileEntryTypeStagedModelRepository
	implements StagedModelRepository<DLFileEntryType> {

	@Override
	public DLFileEntryType addStagedModel(
			PortletDataContext portletDataContext,
			DLFileEntryType dlFileEntryType)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(DLFileEntryType dlFileEntryType)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public DLFileEntryType fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DLFileEntryType fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<DLFileEntryType> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery actionableDynamicQuery =
			_dlFileEntryTypeLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			actionableDynamicQuery.getAddCriteriaMethod();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				Property property = PropertyFactoryUtil.forName("groupId");

				dynamicQuery.add(
					property.in(
						new Long[] {portletDataContext.getScopeGroupId()}));
			});

		actionableDynamicQuery.setPerformActionMethod(
			(DLFileEntryType dlFileEntryType) -> {
				if (dlFileEntryType.isExportable()) {
					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, dlFileEntryType);
				}
			});

		return actionableDynamicQuery;
	}

	@Override
	public DLFileEntryType getStagedModel(long fileEntryTypeId)
		throws PortalException {

		return _dlFileEntryTypeLocalService.getDLFileEntryType(fileEntryTypeId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			DLFileEntryType dlFileEntryType)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public DLFileEntryType saveStagedModel(DLFileEntryType dlFileEntryType)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public DLFileEntryType updateStagedModel(
			PortletDataContext portletDataContext,
			DLFileEntryType dlFileEntryType)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

}
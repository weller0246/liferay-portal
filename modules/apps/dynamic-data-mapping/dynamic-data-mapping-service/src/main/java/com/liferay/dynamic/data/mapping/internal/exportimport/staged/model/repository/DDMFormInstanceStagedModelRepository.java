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

package com.liferay.dynamic.data.mapping.internal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.DDMFormInstanceNameComparator;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tamas Molnar
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstance",
	service = {
		DDMFormInstanceStagedModelRepository.class, StagedModelRepository.class
	}
)
public class DDMFormInstanceStagedModelRepository
	implements StagedModelRepository<DDMFormInstance> {

	@Override
	public DDMFormInstance addStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstance ddmFormInstance)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			ddmFormInstance.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddmFormInstance);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(ddmFormInstance.getUuid());
		}

		return _ddmFormInstanceLocalService.addFormInstance(
			userId, ddmFormInstance.getGroupId(),
			ddmFormInstance.getStructureId(), ddmFormInstance.getNameMap(),
			ddmFormInstance.getDescriptionMap(), ddmFormInstance.getSettings(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(DDMFormInstance ddmFormInstance)
		throws PortalException {

		_ddmFormInstanceLocalService.deleteFormInstance(ddmFormInstance);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DDMFormInstance ddmFormInstance = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (ddmFormInstance != null) {
			deleteStagedModel(ddmFormInstance);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		Set<Long> formInstanceDDMStructureIds = new HashSet<>();

		List<DDMFormInstance> formInstances =
			_ddmFormInstanceLocalService.search(
				portletDataContext.getCompanyId(),
				portletDataContext.getScopeGroupId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new DDMFormInstanceNameComparator());

		for (DDMFormInstance formInstance : formInstances) {
			formInstanceDDMStructureIds.add(formInstance.getStructureId());

			_ddmFormInstanceLocalService.deleteFormInstance(formInstance);
		}

		deleteDDMStructures(formInstanceDDMStructureIds);
	}

	@Override
	public DDMFormInstance fetchMissingReference(String uuid, long groupId) {
		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public DDMFormInstance fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _ddmFormInstanceLocalService.
			fetchDDMFormInstanceByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<DDMFormInstance> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _ddmFormInstanceLocalService.
			getDDMFormInstancesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DDMFormInstance>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddmFormInstanceLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.setPerformActionMethod(
			(DDMFormInstance ddmFormInstance) -> {
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, ddmFormInstance);

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, ddmFormInstance.getStructure());
			});

		return exportActionableDynamicQuery;
	}

	@Override
	public DDMFormInstance getStagedModel(long ddmFormInstanceId)
		throws PortalException {

		return _ddmFormInstanceLocalService.getDDMFormInstance(
			ddmFormInstanceId);
	}

	@Override
	public DDMFormInstance saveStagedModel(DDMFormInstance ddmFormInstance)
		throws PortalException {

		return _ddmFormInstanceLocalService.updateDDMFormInstance(
			ddmFormInstance);
	}

	@Override
	public DDMFormInstance updateStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstance ddmFormInstance)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddmFormInstance);

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		return _ddmFormInstanceLocalService.updateFormInstance(
			serviceContext.getUserId(), ddmFormInstance.getFormInstanceId(),
			ddmFormInstance.getNameMap(), ddmFormInstance.getDescriptionMap(),
			ddmStructure.getDDMForm(), ddmStructure.getDDMFormLayout(),
			ddmFormInstance.getSettingsDDMFormValues(), serviceContext);
	}

	protected void deleteDDMStructures(Set<Long> ddmStructureIds)
		throws PortalException {

		for (Long ddmStructureId : ddmStructureIds) {
			if (_ddmStructureLocalService.fetchDDMStructure(ddmStructureId) !=
					null) {

				_ddmStructureLocalService.deleteStructure(ddmStructureId);
			}
		}
	}

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}
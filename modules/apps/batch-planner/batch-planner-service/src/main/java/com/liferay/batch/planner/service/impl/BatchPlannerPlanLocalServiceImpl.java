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

package com.liferay.batch.planner.service.impl;

import com.liferay.batch.planner.constants.BatchPlannerPlanConstants;
import com.liferay.batch.planner.exception.BatchPlannerPlanExternalTypeException;
import com.liferay.batch.planner.exception.BatchPlannerPlanNameException;
import com.liferay.batch.planner.exception.DuplicateBatchPlannerPlanException;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.base.BatchPlannerPlanLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	property = "model.class.name=com.liferay.batch.planner.model.BatchPlannerPlan",
	service = AopService.class
)
public class BatchPlannerPlanLocalServiceImpl
	extends BatchPlannerPlanLocalServiceBaseImpl {

	@Override
	public BatchPlannerPlan addBatchPlannerPlan(
			long userId, boolean export, String externalType,
			String externalURL, String internalClassName, String name)
		throws PortalException {

		_validateExternalType(externalType);

		User user = userLocalService.getUser(userId);

		_validateName(0, user.getCompanyId(), name);

		BatchPlannerPlan batchPlannerPlan = batchPlannerPlanPersistence.create(
			counterLocalService.increment());

		batchPlannerPlan.setCompanyId(user.getCompanyId());
		batchPlannerPlan.setUserId(userId);
		batchPlannerPlan.setUserName(user.getFullName());
		batchPlannerPlan.setExport(export);
		batchPlannerPlan.setExternalType(externalType);
		batchPlannerPlan.setExternalURL(externalURL);
		batchPlannerPlan.setInternalClassName(internalClassName);
		batchPlannerPlan.setName(name);

		batchPlannerPlan = batchPlannerPlanPersistence.update(batchPlannerPlan);

		resourceLocalService.addResources(
			user.getCompanyId(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			user.getUserId(), BatchPlannerPlan.class.getName(),
			batchPlannerPlan.getBatchPlannerPlanId(), false, true, false);

		return batchPlannerPlan;
	}

	@Override
	public BatchPlannerPlan deleteBatchPlannerPlan(long batchPlannerPlanId)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan = batchPlannerPlanPersistence.remove(
			batchPlannerPlanId);

		resourceLocalService.deleteResource(
			batchPlannerPlan, ResourceConstants.SCOPE_COMPANY);

		batchPlannerLogPersistence.removeByBatchPlannerPlanId(
			batchPlannerPlanId);

		batchPlannerMappingPersistence.removeByBatchPlannerPlanId(
			batchPlannerPlanId);

		batchPlannerPolicyPersistence.removeByBatchPlannerPlanId(
			batchPlannerPlanId);

		return batchPlannerPlan;
	}

	@Override
	public BatchPlannerPlan updateActive(
			long batchPlannerPlanId, boolean active)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			batchPlannerPlanPersistence.findByPrimaryKey(batchPlannerPlanId);

		batchPlannerPlan.setActive(active);

		return batchPlannerPlanPersistence.update(batchPlannerPlan);
	}

	@Override
	public BatchPlannerPlan updateBatchPlannerPlan(
			long userId, long batchPlannerPlanId, String name)
		throws PortalException {

		BatchPlannerPlan batchPlannerPlan =
			batchPlannerPlanPersistence.findByPrimaryKey(batchPlannerPlanId);

		User user = userLocalService.getUser(userId);

		_validateName(batchPlannerPlanId, user.getCompanyId(), name);

		batchPlannerPlan.setName(name);

		return batchPlannerPlanPersistence.update(batchPlannerPlan);
	}

	private void _validateExternalType(String externalType)
		throws PortalException {

		if (ArrayUtil.contains(
				BatchPlannerPlanConstants.EXTERNAL_TYPES, externalType)) {

			return;
		}

		String merge = StringUtil.merge(
			BatchPlannerPlanConstants.EXTERNAL_TYPES, StringPool.COMMA);

		throw new BatchPlannerPlanExternalTypeException(
			"Batch planner plan external type must be one of following: " +
				merge);
	}

	private void _validateName(
			long batchPlannerPlanId, long companyId, String name)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new BatchPlannerPlanNameException(
				"Batch planner plan name is null for company " + companyId);
		}

		int maxLength = ModelHintsUtil.getMaxLength(
			BatchPlannerPlan.class.getName(), "name");

		if (name.length() > maxLength) {
			throw new BatchPlannerPlanNameException(
				"Batch planner plan name must not be longer than " + maxLength);
		}

		BatchPlannerPlan batchPlannerPlan =
			batchPlannerPlanPersistence.fetchByC_N(companyId, name);

		if ((batchPlannerPlan == null) ||
			(batchPlannerPlan.getBatchPlannerPlanId() == batchPlannerPlanId)) {

			return;
		}

		throw new DuplicateBatchPlannerPlanException(
			StringBundler.concat(
				"Batch planner plan name \"", name,
				"\" already exists for company ", companyId));
	}

}
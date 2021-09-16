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

package com.liferay.portal.workflow.kaleo.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.base.KaleoTaskAssignmentInstanceLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance",
	service = AopService.class
)
public class KaleoTaskAssignmentInstanceLocalServiceImpl
	extends KaleoTaskAssignmentInstanceLocalServiceBaseImpl {

	@Override
	public KaleoTaskAssignmentInstance addKaleoTaskAssignmentInstance(
			long groupId, KaleoTaskInstanceToken kaleoTaskInstanceToken,
			String assigneeClassName, long assigneeClassPK,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date date = new Date();

		long kaleoTaskAssignmentInstanceId = counterLocalService.increment();

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			kaleoTaskAssignmentInstancePersistence.create(
				kaleoTaskAssignmentInstanceId);

		kaleoTaskAssignmentInstance.setGroupId(groupId);
		kaleoTaskAssignmentInstance.setCompanyId(user.getCompanyId());
		kaleoTaskAssignmentInstance.setUserId(user.getUserId());
		kaleoTaskAssignmentInstance.setUserName(user.getFullName());
		kaleoTaskAssignmentInstance.setCreateDate(date);
		kaleoTaskAssignmentInstance.setModifiedDate(date);
		kaleoTaskAssignmentInstance.setKaleoDefinitionId(
			kaleoTaskInstanceToken.getKaleoDefinitionId());
		kaleoTaskAssignmentInstance.setKaleoDefinitionVersionId(
			kaleoTaskInstanceToken.getKaleoDefinitionVersionId());
		kaleoTaskAssignmentInstance.setKaleoInstanceId(
			kaleoTaskInstanceToken.getKaleoInstanceId());
		kaleoTaskAssignmentInstance.setKaleoInstanceTokenId(
			kaleoTaskInstanceToken.getKaleoInstanceTokenId());
		kaleoTaskAssignmentInstance.setKaleoTaskInstanceTokenId(
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
		kaleoTaskAssignmentInstance.setKaleoTaskId(
			kaleoTaskInstanceToken.getKaleoTaskId());
		kaleoTaskAssignmentInstance.setKaleoTaskName(
			kaleoTaskInstanceToken.getKaleoTaskName());
		kaleoTaskAssignmentInstance.setAssigneeClassName(assigneeClassName);

		if ((assigneeClassPK == 0) &&
			assigneeClassName.equals(User.class.getName())) {

			KaleoInstance kaleoInstance =
				kaleoInstancePersistence.findByPrimaryKey(
					kaleoTaskInstanceToken.getKaleoInstanceId());

			kaleoTaskAssignmentInstance.setAssigneeClassPK(
				kaleoInstance.getUserId());
		}
		else {
			kaleoTaskAssignmentInstance.setAssigneeClassPK(assigneeClassPK);
		}

		kaleoTaskAssignmentInstance.setCompleted(false);

		return kaleoTaskAssignmentInstancePersistence.update(
			kaleoTaskAssignmentInstance);
	}

	@Override
	public List<KaleoTaskAssignmentInstance> addTaskAssignmentInstances(
			KaleoTaskInstanceToken kaleoTaskInstanceToken,
			Collection<KaleoTaskAssignment> kaleoTaskAssignments,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances =
			new ArrayList<>(kaleoTaskAssignments.size());

		for (KaleoTaskAssignment kaleoTaskAssignment : kaleoTaskAssignments) {
			long groupId = kaleoTaskAssignment.getGroupId();

			if (groupId <= 0) {
				groupId = kaleoTaskInstanceToken.getGroupId();
			}

			KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
				addKaleoTaskAssignmentInstance(
					groupId, kaleoTaskInstanceToken,
					kaleoTaskAssignment.getAssigneeClassName(),
					kaleoTaskAssignment.getAssigneeClassPK(), serviceContext);

			kaleoTaskAssignmentInstances.add(kaleoTaskAssignmentInstance);
		}

		return kaleoTaskAssignmentInstances;
	}

	@Override
	public KaleoTaskAssignmentInstance assignKaleoTaskAssignmentInstance(
			KaleoTaskInstanceToken kaleoTaskInstanceToken,
			String assigneeClassName, long assigneeClassPK,
			ServiceContext serviceContext)
		throws PortalException {

		deleteKaleoTaskAssignmentInstances(kaleoTaskInstanceToken);

		return addKaleoTaskAssignmentInstance(
			kaleoTaskInstanceToken.getGroupId(), kaleoTaskInstanceToken,
			assigneeClassName, assigneeClassPK, serviceContext);
	}

	@Override
	public List<KaleoTaskAssignmentInstance> assignKaleoTaskAssignmentInstances(
			KaleoTaskInstanceToken kaleoTaskInstanceToken,
			Collection<KaleoTaskAssignment> kaleoTaskAssignments,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		deleteKaleoTaskAssignmentInstances(kaleoTaskInstanceToken);

		return addTaskAssignmentInstances(
			kaleoTaskInstanceToken, kaleoTaskAssignments, workflowContext,
			serviceContext);
	}

	@Override
	public KaleoTaskAssignmentInstance completeKaleoTaskInstanceToken(
			long kaleoTaskInstanceTokenId, ServiceContext serviceContext)
		throws PortalException {

		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances =
			kaleoTaskAssignmentInstancePersistence.
				findByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);

		if (kaleoTaskAssignmentInstances.size() > 1) {
			throw new WorkflowException(
				"Cannot complete a task that is not assigned to an " +
					"individual user");
		}

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			kaleoTaskAssignmentInstances.get(0);

		kaleoTaskAssignmentInstance.setCompleted(true);
		kaleoTaskAssignmentInstance.setCompletionDate(new Date());

		return kaleoTaskAssignmentInstancePersistence.update(
			kaleoTaskAssignmentInstance);
	}

	@Override
	public void deleteCompanyKaleoTaskAssignmentInstances(long companyId) {
		kaleoTaskAssignmentInstancePersistence.removeByCompanyId(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoTaskAssignmentInstances(
		long kaleoDefinitionId) {

		kaleoTaskAssignmentInstancePersistence.removeByKaleoDefinitionVersionId(
			kaleoDefinitionId);
	}

	@Override
	public void deleteKaleoInstanceKaleoTaskAssignmentInstances(
		long kaleoInstanceId) {

		kaleoTaskAssignmentInstancePersistence.removeByKaleoInstanceId(
			kaleoInstanceId);
	}

	@Override
	public void deleteKaleoTaskAssignmentInstances(
		KaleoTaskInstanceToken kaleoTaskInstanceToken) {

		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances =
			kaleoTaskAssignmentInstancePersistence.
				findByKaleoTaskInstanceTokenId(
					kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());

		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				kaleoTaskAssignmentInstances) {

			kaleoTaskAssignmentInstancePersistence.remove(
				kaleoTaskAssignmentInstance);
		}
	}

	@Override
	public KaleoTaskAssignmentInstance fetchFirstKaleoTaskAssignmentInstance(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return kaleoTaskAssignmentInstancePersistence.
			fetchByKaleoTaskInstanceTokenId_First(
				kaleoTaskInstanceTokenId, orderByComparator);
	}

	@Override
	public KaleoTaskAssignmentInstance fetchFirstKaleoTaskAssignmentInstance(
		long kaleoTaskInstanceTokenId, String assigneeClassName,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return kaleoTaskAssignmentInstancePersistence.fetchByKTITI_ACN_First(
			kaleoTaskInstanceTokenId, assigneeClassName, orderByComparator);
	}

	@Override
	public List<KaleoTaskAssignmentInstance> getKaleoTaskAssignmentInstances(
		long kaleoTaskInstanceTokenId) {

		return kaleoTaskAssignmentInstancePersistence.
			findByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
	}

	@Override
	public int getKaleoTaskAssignmentInstancesCount(
		long kaleoTaskInstanceTokenId) {

		return kaleoTaskAssignmentInstancePersistence.
			countByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
	}

}
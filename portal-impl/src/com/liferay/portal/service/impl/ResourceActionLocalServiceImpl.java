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

package com.liferay.portal.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchResourceActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.base.ResourceActionLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ResourceActionLocalServiceImpl
	extends ResourceActionLocalServiceBaseImpl {

	@Override
	public ResourceAction addResourceAction(
		String name, String actionId, long bitwiseValue) {

		ResourceAction resourceAction = resourceActionPersistence.fetchByN_A(
			name, actionId);

		if (resourceAction == null) {
			long resourceActionId = counterLocalService.increment(
				ResourceAction.class.getName());

			resourceAction = resourceActionPersistence.create(resourceActionId);

			resourceAction.setName(name);
			resourceAction.setActionId(actionId);
			resourceAction.setBitwiseValue(bitwiseValue);

			resourceAction = resourceActionPersistence.update(resourceAction);
		}

		return resourceAction;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkResourceActions() {
		List<ResourceAction> resourceActions =
			resourceActionPersistence.findAll();

		for (ResourceAction resourceAction : resourceActions) {
			String key = encodeKey(
				resourceAction.getName(), resourceAction.getActionId());

			_resourceActions.put(key, resourceAction);
		}
	}

	@Override
	public void checkResourceActions(String name, List<String> actionIds) {
		checkResourceActions(name, actionIds, false);
	}

	@Override
	public void checkResourceActions(
		String name, List<String> actionIds, boolean addDefaultActions) {

		synchronized (name.intern()) {
			if ((actionIds.size() > Long.SIZE) ||
				((actionIds.size() == Long.SIZE) &&
				 !actionIds.contains(ActionKeys.VIEW))) {

				throw new SystemException(
					"There are too many actions for resource " + name);
			}

			long availableBits = -2;
			Map<String, ResourceAction> resourceActionsMap = null;

			List<Object[]> keyActionIdAndBitwiseValues = null;

			for (String actionId : actionIds) {
				String key = encodeKey(name, actionId);

				if (_resourceActions.get(key) != null) {
					continue;
				}

				if (resourceActionsMap == null) {
					resourceActionsMap = new HashMap<>();

					List<ResourceAction> resourceActions = getResourceActions(
						name);

					for (ResourceAction resourceAction : resourceActions) {
						availableBits &= ~resourceAction.getBitwiseValue();

						resourceActionsMap.put(
							resourceAction.getActionId(), resourceAction);
					}
				}

				ResourceAction resourceAction = resourceActionsMap.get(
					actionId);

				if (resourceAction == null) {
					long bitwiseValue = 1;

					if (!actionId.equals(ActionKeys.VIEW)) {
						bitwiseValue = Long.lowestOneBit(availableBits);

						availableBits ^= bitwiseValue;
					}

					if (keyActionIdAndBitwiseValues == null) {
						keyActionIdAndBitwiseValues = new ArrayList<>();
					}

					keyActionIdAndBitwiseValues.add(
						new Object[] {key, actionId, bitwiseValue});
				}
				else {
					_resourceActions.put(key, resourceAction);
				}
			}

			if (keyActionIdAndBitwiseValues == null) {
				return;
			}

			long batchCounter = counterLocalService.increment(
				ResourceAction.class.getName(),
				keyActionIdAndBitwiseValues.size());

			batchCounter -= keyActionIdAndBitwiseValues.size();

			for (Object[] keyActionIdAndBitwiseValue :
					keyActionIdAndBitwiseValues) {

				String key = (String)keyActionIdAndBitwiseValue[0];
				String actionId = (String)keyActionIdAndBitwiseValue[1];
				long bitwiseValue = (long)keyActionIdAndBitwiseValue[2];

				ResourceAction resourceAction = null;

				try {
					resourceAction = resourceActionPersistence.create(
						++batchCounter);

					resourceAction.setName(name);
					resourceAction.setActionId(actionId);
					resourceAction.setBitwiseValue(bitwiseValue);

					resourceAction = resourceActionPersistence.update(
						resourceAction);
				}
				catch (Throwable throwable) {
					resourceAction =
						resourceActionLocalService.addResourceAction(
							name, actionId, bitwiseValue);
				}

				_resourceActions.put(key, resourceAction);
			}

			if (!addDefaultActions) {
				return;
			}

			List<String> groupDefaultActions =
				ResourceActionsUtil.getModelResourceGroupDefaultActions(name);

			List<String> guestDefaultActions =
				ResourceActionsUtil.getModelResourceGuestDefaultActions(name);

			long guestBitwiseValue = 0;
			long ownerBitwiseValue = 0;
			long siteMemberBitwiseValue = 0;

			for (Object[] keyActionIdAndBitwiseValue :
					keyActionIdAndBitwiseValues) {

				String actionId = (String)keyActionIdAndBitwiseValue[1];
				long bitwiseValue = (long)keyActionIdAndBitwiseValue[2];

				if (guestDefaultActions.contains(actionId)) {
					guestBitwiseValue |= bitwiseValue;
				}

				ownerBitwiseValue |= bitwiseValue;

				if (groupDefaultActions.contains(actionId)) {
					siteMemberBitwiseValue |= bitwiseValue;
				}
			}

			if (guestBitwiseValue > 0) {
				resourcePermissionLocalService.addResourcePermissions(
					name, RoleConstants.GUEST,
					ResourceConstants.SCOPE_INDIVIDUAL, guestBitwiseValue);
			}

			if (ownerBitwiseValue > 0) {
				resourcePermissionLocalService.addResourcePermissions(
					name, RoleConstants.OWNER,
					ResourceConstants.SCOPE_INDIVIDUAL, ownerBitwiseValue);
			}

			if (siteMemberBitwiseValue > 0) {
				resourcePermissionLocalService.addResourcePermissions(
					name, RoleConstants.SITE_MEMBER,
					ResourceConstants.SCOPE_INDIVIDUAL, siteMemberBitwiseValue);
			}
		}
	}

	@Override
	public ResourceAction deleteResourceAction(long resourceActionId)
		throws PortalException {

		return deleteResourceAction(
			resourceActionPersistence.findByPrimaryKey(resourceActionId));
	}

	@Override
	public ResourceAction deleteResourceAction(ResourceAction resourceAction) {
		String name = resourceAction.getName();
		long bitwiseValue = resourceAction.getBitwiseValue();

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			dynamicQuery -> {
				Property nameProperty = PropertyFactoryUtil.forName("name");

				dynamicQuery.add(nameProperty.eq(name));
			};

		companyLocalService.forEachCompanyId(
			companyId -> {
				ActionableDynamicQuery actionableDynamicQuery =
					resourcePermissionLocalService.getActionableDynamicQuery();

				actionableDynamicQuery.setAddCriteriaMethod(addCriteriaMethod);
				actionableDynamicQuery.setCompanyId(companyId);
				actionableDynamicQuery.setPerformActionMethod(
					(ResourcePermission resourcePermission) -> {
						long actionIds = resourcePermission.getActionIds();

						if ((actionIds & bitwiseValue) != 0) {
							actionIds &= ~bitwiseValue;

							resourcePermission.setActionIds(actionIds);
							resourcePermission.setViewActionId(
								(actionIds % 2) == 1);

							resourcePermissionPersistence.update(
								resourcePermission);
						}
					});

				try {
					actionableDynamicQuery.performActions();
				}
				catch (PortalException portalException) {
					throw new SystemException(portalException);
				}
			});

		_resourceActions.remove(
			encodeKey(resourceAction.getName(), resourceAction.getActionId()));

		resourceActionPersistence.remove(resourceAction);

		PermissionCacheUtil.clearCache();

		return resourceAction;
	}

	@Override
	@Transactional(enabled = false)
	public ResourceAction fetchResourceAction(String name, String actionId) {
		String key = encodeKey(name, actionId);

		return _resourceActions.get(key);
	}

	@Override
	@Transactional(enabled = false)
	public ResourceAction getResourceAction(String name, String actionId)
		throws PortalException {

		String key = encodeKey(name, actionId);

		ResourceAction resourceAction = _resourceActions.get(key);

		if (resourceAction == null) {
			throw new NoSuchResourceActionException(key);
		}

		return resourceAction;
	}

	@Override
	public List<ResourceAction> getResourceActions(String name) {
		return resourceActionPersistence.findByName(name);
	}

	@Override
	public int getResourceActionsCount(String name) {
		return resourceActionPersistence.countByName(name);
	}

	protected String encodeKey(String name, String actionId) {
		return StringBundler.concat(name, StringPool.POUND, actionId);
	}

	private static final Map<String, ResourceAction> _resourceActions =
		new ConcurrentHashMap<>();

}
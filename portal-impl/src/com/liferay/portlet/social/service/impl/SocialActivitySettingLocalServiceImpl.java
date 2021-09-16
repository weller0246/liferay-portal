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

package com.liferay.portlet.social.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.social.service.base.SocialActivitySettingLocalServiceBaseImpl;
import com.liferay.social.kernel.model.SocialActivityCounterDefinition;
import com.liferay.social.kernel.model.SocialActivityDefinition;
import com.liferay.social.kernel.model.SocialActivitySetting;
import com.liferay.social.kernel.model.SocialActivitySettingConstants;
import com.liferay.social.kernel.util.SocialConfigurationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialActivitySettingLocalServiceImpl
	extends SocialActivitySettingLocalServiceBaseImpl {

	@Override
	public void deleteActivitySetting(
		long groupId, String className, long classPK) {

		String name = _PREFIX_CLASS_PK.concat(String.valueOf(classPK));

		SocialActivitySetting activitySetting =
			socialActivitySettingPersistence.fetchByG_C_A_N(
				groupId, classNameLocalService.getClassNameId(className), 0,
				name);

		if (activitySetting != null) {
			socialActivitySettingPersistence.remove(activitySetting);
		}
	}

	@Override
	public void deleteActivitySettings(long groupId) {
		socialActivitySettingPersistence.removeByGroupId(groupId);
	}

	@Override
	public SocialActivityDefinition getActivityDefinition(
		long groupId, String className, int activityType) {

		String key = encodeKey(groupId, className, activityType);

		SocialActivityDefinition activityDefinition =
			_activityDefinitionsPortalCache.get(key);

		if (activityDefinition != null) {
			return activityDefinition;
		}

		SocialActivityDefinition defaultActivityDefinition =
			SocialConfigurationUtil.getActivityDefinition(
				className, activityType);

		if (defaultActivityDefinition == null) {
			return null;
		}

		activityDefinition = getActivityDefinition(
			groupId, className, activityType, defaultActivityDefinition);

		_activityDefinitionsPortalCache.put(key, activityDefinition);

		return activityDefinition;
	}

	@Override
	public List<SocialActivityDefinition> getActivityDefinitions(
		long groupId, String className) {

		List<SocialActivityDefinition> activityDefinitions = new ArrayList<>();

		List<SocialActivityDefinition> defaultActivityDefinitions =
			SocialConfigurationUtil.getActivityDefinitions(className);

		for (SocialActivityDefinition defaultActivityDefinition :
				defaultActivityDefinitions) {

			SocialActivityDefinition activityDefinition = getActivityDefinition(
				groupId, className,
				defaultActivityDefinition.getActivityType());

			activityDefinitions.add(activityDefinition);
		}

		return activityDefinitions;
	}

	@Override
	public List<SocialActivitySetting> getActivitySettings(long groupId) {
		return socialActivitySettingPersistence.findByG_A(groupId, 0);
	}

	@Override
	public boolean isEnabled(long groupId, long classNameId) {
		SocialActivitySetting activitySetting =
			socialActivitySettingPersistence.fetchByG_C_A_N(
				groupId, classNameId, 0,
				SocialActivitySettingConstants.NAME_ENABLED);

		if (activitySetting == null) {
			return false;
		}

		return GetterUtil.getBoolean(activitySetting.getValue());
	}

	@Override
	public boolean isEnabled(long groupId, long classNameId, long classPK) {
		String name = _PREFIX_CLASS_PK.concat(String.valueOf(classPK));

		SocialActivitySetting activitySetting =
			socialActivitySettingPersistence.fetchByG_C_A_N(
				groupId, classNameId, 0, name);

		if (activitySetting == null) {
			return true;
		}

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				activitySetting.getValue());

			return jsonObject.getBoolean("enabled");
		}
		catch (JSONException jsonException) {
			_log.error(
				"Unable to create JSON object from " +
					activitySetting.getValue(),
				jsonException);

			return false;
		}
	}

	@Override
	public void updateActivitySetting(
			long groupId, String className, boolean enabled)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		SocialActivitySetting activitySetting =
			socialActivitySettingPersistence.fetchByG_C_A_N(
				groupId, classNameId, 0,
				SocialActivitySettingConstants.NAME_ENABLED);

		if (activitySetting == null) {
			Group group = groupLocalService.getGroup(groupId);

			long activitySettingId = counterLocalService.increment();

			activitySetting = socialActivitySettingPersistence.create(
				activitySettingId);

			activitySetting.setGroupId(groupId);
			activitySetting.setCompanyId(group.getCompanyId());
			activitySetting.setClassNameId(classNameId);
			activitySetting.setName(
				SocialActivitySettingConstants.NAME_ENABLED);
		}

		activitySetting.setValue(String.valueOf(enabled));

		socialActivitySettingPersistence.update(activitySetting);
	}

	@Override
	public void updateActivitySetting(
			long groupId, String className, int activityType,
			SocialActivityCounterDefinition activityCounterDefinition)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		SocialActivityDefinition defaultActivityDefinition =
			SocialConfigurationUtil.getActivityDefinition(
				className, activityType);

		SocialActivityCounterDefinition defaultActivityCounterDefinition =
			defaultActivityDefinition.getActivityCounterDefinition(
				activityCounterDefinition.getName());

		SocialActivitySetting activitySetting =
			socialActivitySettingPersistence.fetchByG_C_A_N(
				groupId, classNameId, activityType,
				activityCounterDefinition.getName());

		if ((defaultActivityCounterDefinition != null) &&
			defaultActivityCounterDefinition.equals(
				activityCounterDefinition)) {

			if (activitySetting != null) {
				socialActivitySettingPersistence.remove(activitySetting);
			}

			return;
		}

		if (activitySetting != null) {
			activitySetting.setValue(toJSON(activityCounterDefinition));
		}
		else {
			Group group = groupLocalService.getGroup(groupId);

			long activitySettingId = counterLocalService.increment();

			activitySetting = socialActivitySettingPersistence.create(
				activitySettingId);

			activitySetting.setGroupId(groupId);
			activitySetting.setCompanyId(group.getCompanyId());
			activitySetting.setClassNameId(classNameId);
			activitySetting.setActivityType(activityType);
			activitySetting.setName(activityCounterDefinition.getName());
			activitySetting.setValue(toJSON(activityCounterDefinition));
		}

		socialActivitySettingPersistence.update(activitySetting);

		String key = encodeKey(groupId, className, activityType);

		_activityDefinitionsPortalCache.remove(key);
	}

	@Override
	public void updateActivitySetting(
			long groupId, String className, long classPK, boolean enabled)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);
		String name = _PREFIX_CLASS_PK.concat(String.valueOf(classPK));

		SocialActivitySetting activitySetting =
			socialActivitySettingPersistence.fetchByG_C_A_N(
				groupId, classNameId, 0, name);

		if (activitySetting == null) {
			Group group = groupLocalService.getGroup(groupId);

			long activitySettingId = counterLocalService.increment();

			activitySetting = socialActivitySettingPersistence.create(
				activitySettingId);

			activitySetting.setGroupId(groupId);
			activitySetting.setCompanyId(group.getCompanyId());
			activitySetting.setClassNameId(classNameId);
			activitySetting.setName(name);
		}

		JSONObject jsonObject = JSONUtil.put("enabled", enabled);

		activitySetting.setValue(jsonObject.toString());

		socialActivitySettingPersistence.update(activitySetting);
	}

	@Override
	public void updateActivitySettings(
			long groupId, String className, int activityType,
			List<SocialActivityCounterDefinition> activityCounterDefinitions)
		throws PortalException {

		for (SocialActivityCounterDefinition activityCounterDefinition :
				activityCounterDefinitions) {

			updateActivitySetting(
				groupId, className, activityType, activityCounterDefinition);
		}
	}

	protected String encodeKey(
		long groupId, String className, int activityType) {

		return StringBundler.concat(
			groupId, StringPool.POUND, className, StringPool.POUND,
			activityType);
	}

	protected SocialActivityDefinition getActivityDefinition(
		long groupId, String className, int activityType,
		SocialActivityDefinition defaultActivityDefinition) {

		SocialActivityDefinition activityDefinition =
			defaultActivityDefinition.clone();

		List<SocialActivitySetting> activitySettings = getActivitySettings(
			groupId, className, defaultActivityDefinition.getActivityType());

		for (SocialActivitySetting activitySetting : activitySettings) {
			String name = activitySetting.getName();

			if (name.equals(SocialActivitySettingConstants.NAME_ENABLED)) {
				activityDefinition.setCountersEnabled(
					GetterUtil.getBoolean(
						activitySetting.getValue(),
						defaultActivityDefinition.isCountersEnabled()));
			}
			else if (name.equals(
						SocialActivitySettingConstants.NAME_LOG_ENABLED)) {

				activityDefinition.setLogActivity(
					GetterUtil.getBoolean(
						activitySetting.getValue(),
						defaultActivityDefinition.isLogActivity()));
			}
			else {
				JSONObject jsonObject = null;

				try {
					jsonObject = JSONFactoryUtil.createJSONObject(
						activitySetting.getValue());
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception, exception);
					}

					jsonObject = JSONFactoryUtil.createJSONObject();
				}

				SocialActivityCounterDefinition activityCounterDefinition =
					activityDefinition.getActivityCounterDefinition(name);

				if (activityCounterDefinition == null) {
					activityCounterDefinition =
						new SocialActivityCounterDefinition();

					activityCounterDefinition.setName(name);

					activityDefinition.addCounter(activityCounterDefinition);
				}

				activityCounterDefinition.setEnabled(
					jsonObject.getBoolean("enabled"));
				activityCounterDefinition.setIncrement(
					jsonObject.getInt("value"));
				activityCounterDefinition.setLimitEnabled(
					jsonObject.getBoolean("limitEnabled"));
				activityCounterDefinition.setLimitPeriod(
					jsonObject.getInt("limitPeriod"));
				activityCounterDefinition.setLimitValue(
					jsonObject.getInt("limitValue"));
				activityCounterDefinition.setOwnerType(
					jsonObject.getInt("ownerType"));
			}
		}

		return activityDefinition;
	}

	protected List<SocialActivitySetting> getActivitySettings(
		long groupId, String className, int activityType) {

		return socialActivitySettingPersistence.findByG_C_A(
			groupId, classNameLocalService.getClassNameId(className),
			activityType);
	}

	protected String toJSON(
		SocialActivityCounterDefinition activityCounterDefinition) {

		JSONObject jsonObject = JSONUtil.put(
			"enabled", activityCounterDefinition.isEnabled()
		).put(
			"limitEnabled", activityCounterDefinition.isLimitEnabled()
		).put(
			"limitPeriod", activityCounterDefinition.getLimitPeriod()
		).put(
			"limitValue", activityCounterDefinition.getLimitValue()
		).put(
			"ownerType", activityCounterDefinition.getOwnerType()
		).put(
			"value", activityCounterDefinition.getIncrement()
		);

		return jsonObject.toString();
	}

	private static final String _PREFIX_CLASS_PK = "_LFR_CLASS_PK_";

	private static final Log _log = LogFactoryUtil.getLog(
		SocialActivitySettingLocalServiceImpl.class);

	private static final PortalCache<String, SocialActivityDefinition>
		_activityDefinitionsPortalCache = PortalCacheHelperUtil.getPortalCache(
			PortalCacheManagerNames.MULTI_VM,
			SocialActivitySettingLocalServiceImpl.class.getName());

}
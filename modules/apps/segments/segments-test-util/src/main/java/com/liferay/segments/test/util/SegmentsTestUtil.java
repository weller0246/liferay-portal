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

package com.liferay.segments.test.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;
import com.liferay.segments.service.SegmentsEntryRelLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo García
 */
public class SegmentsTestUtil {

	public static SegmentsEntry addSegmentsEntry(long groupId)
		throws PortalException {

		return addSegmentsEntry(groupId, RandomTestUtil.randomString());
	}

	public static SegmentsEntry addSegmentsEntry(
			long groupId, String segmentsEntryKey)
		throws PortalException {

		return addSegmentsEntry(
			groupId, segmentsEntryKey, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _EMPTY_CRITERIA_STRING,
			RandomTestUtil.randomString());
	}

	public static SegmentsEntry addSegmentsEntry(
			long groupId, String className, long classPK)
		throws PortalException {

		SegmentsEntry segmentsEntry = addSegmentsEntry(
			groupId, _EMPTY_CRITERIA_STRING, className);

		SegmentsEntryRelLocalServiceUtil.addSegmentsEntryRel(
			segmentsEntry.getSegmentsEntryId(),
			PortalUtil.getClassNameId(className), classPK,
			ServiceContextTestUtil.getServiceContext(groupId));

		return segmentsEntry;
	}

	public static SegmentsEntry addSegmentsEntry(
			long groupId, String criteria, String type)
		throws PortalException {

		return addSegmentsEntry(
			groupId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			criteria, type);
	}

	public static SegmentsEntry addSegmentsEntry(
			long groupId, String segmentsEntryKey, String name,
			String description, String criteria, String type)
		throws PortalException {

		return addSegmentsEntry(
			segmentsEntryKey, name, description, criteria,
			SegmentsConstants.SOURCE_DEFAULT, type,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static SegmentsEntry addSegmentsEntry(ServiceContext serviceContext)
		throws PortalException {

		return addSegmentsEntry(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _EMPTY_CRITERIA_STRING,
			SegmentsConstants.SOURCE_DEFAULT, RandomTestUtil.randomString(),
			serviceContext);
	}

	public static SegmentsEntry addSegmentsEntry(
			String segmentsEntryKey, String name, String description,
			String criteria, String source, String type,
			ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getDefault(), description);

		return SegmentsEntryLocalServiceUtil.addSegmentsEntry(
			segmentsEntryKey, nameMap, descriptionMap, true, criteria, source,
			type, serviceContext);
	}

	public static SegmentsExperience addSegmentsExperience(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		SegmentsEntry segmentsEntry = addSegmentsEntry(groupId);

		return addSegmentsExperience(
			groupId, segmentsEntry.getSegmentsEntryId(), classNameId, classPK);
	}

	public static SegmentsExperience addSegmentsExperience(
			long groupId, long segmentsEntryId, long classNameId, long classPK)
		throws PortalException {

		return addSegmentsExperience(
			segmentsEntryId, classNameId, classPK,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static SegmentsExperience addSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		return SegmentsExperienceLocalServiceUtil.addSegmentsExperience(
			segmentsEntryId, classNameId, classPK,
			RandomTestUtil.randomLocaleStringMap(), true, serviceContext);
	}

	public static SegmentsExperience addSegmentsExperience(
			long classNameId, long classPK, ServiceContext serviceContext)
		throws PortalException {

		SegmentsEntry segmentsEntry = addSegmentsEntry(
			serviceContext.getScopeGroupId());

		return addSegmentsExperience(
			segmentsEntry.getSegmentsEntryId(), classNameId, classPK,
			serviceContext);
	}

	private static final String _EMPTY_CRITERIA_STRING =
		CriteriaSerializer.serialize(new Criteria());

}
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

package com.liferay.portal.events;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 * @author Raymond Augé
 */
public class EventsProcessorUtil {

	public static void process(String key, String[] classes)
		throws ActionException {

		process(key, classes, new LifecycleEvent());
	}

	public static void process(
			String key, String[] classes, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		process(
			key, classes,
			new LifecycleEvent(httpServletRequest, httpServletResponse));
	}

	public static void process(
			String key, String[] classes, HttpSession session)
		throws ActionException {

		process(key, classes, new LifecycleEvent(session));
	}

	public static void process(
			String key, String[] classes, LifecycleEvent lifecycleEvent)
		throws ActionException {

		for (String className : classes) {
			if (Validator.isNull(className)) {
				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Process event " + className);
			}

			LifecycleAction lifecycleAction = (LifecycleAction)InstancePool.get(
				className);

			lifecycleAction.processLifecycleEvent(lifecycleEvent);
		}

		if (Validator.isNull(key)) {
			return;
		}

		List<LifecycleAction> lifecycleActions = _lifecycleActions.getService(
			key);

		if (lifecycleActions != null) {
			for (LifecycleAction lifecycleAction : lifecycleActions) {
				lifecycleAction.processLifecycleEvent(lifecycleEvent);
			}
		}
	}

	public static void process(String key, String[] classes, String[] ids)
		throws ActionException {

		process(key, classes, new LifecycleEvent(ids));
	}

	public static void processEvent(
			LifecycleAction lifecycleAction, LifecycleEvent lifecycleEvent)
		throws ActionException {

		lifecycleAction.processLifecycleEvent(lifecycleEvent);
	}

	protected EventsProcessorUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EventsProcessorUtil.class);

	private static final ServiceTrackerMap<String, List<LifecycleAction>>
		_lifecycleActions = ServiceTrackerMapFactory.openMultiValueMap(
			SystemBundleUtil.getBundleContext(), LifecycleAction.class, "key");

}
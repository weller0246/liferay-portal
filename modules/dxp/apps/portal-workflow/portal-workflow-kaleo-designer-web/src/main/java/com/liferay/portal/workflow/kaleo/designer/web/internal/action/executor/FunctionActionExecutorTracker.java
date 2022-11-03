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

package com.liferay.portal.workflow.kaleo.designer.web.internal.action.executor;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutor;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import java.util.Collection;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
@Component(service = FunctionActionExecutorTracker.class)
public class FunctionActionExecutorTracker {

	public Collection<ServiceWrapper<ActionExecutor>>
		getFunctionActionExecutorServiceWrappers() {

		return _serviceTrackerMap.values();
	}

	public JSONArray getClientExtensionsJSONArray() throws Exception {
		return JSONUtil.toJSONArray(
			getFunctionActionExecutorServiceWrappers(),
			functionActionExecutorServiceWrapper -> {
				Map<String, Object> properties =
					functionActionExecutorServiceWrapper.getProperties();

				String description = MapUtil.getString(
					properties, "client.extension.description");

				String key = MapUtil.getString(
					properties, _KEY);

				if (Validator.isBlank(description)) {
					description = key;
				}

				return JSONUtil.put(
					"description", description
				).put(
					"key", key
				);
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ActionExecutor.class, "(" + _KEY + "=function*)",
			(serviceReference, emitter) -> emitter.emit(
				(String)serviceReference.getProperty(_KEY)),
			ServiceTrackerCustomizerFactory.<ActionExecutor>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final String _KEY =
		"com.liferay.portal.workflow.kaleo.runtime.action.executor.language";

	private ServiceTrackerMap<String, ServiceWrapper<ActionExecutor>>
		_serviceTrackerMap;

}
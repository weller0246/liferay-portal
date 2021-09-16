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

package com.liferay.portal.scheduler.quartz.internal.portal.profile;

import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.profile.BaseDSModulePortalProfile;
import com.liferay.portal.profile.PortalProfile;
import com.liferay.portal.scheduler.quartz.internal.QuartzSchedulerEngine;
import com.liferay.portal.scheduler.quartz.internal.QuartzSchemaManager;
import com.liferay.portal.scheduler.quartz.internal.QuartzTriggerFactory;
import com.liferay.portal.scheduler.quartz.internal.messaging.proxy.QuartzSchedulerProxyMessageListener;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = PortalProfile.class)
public class ModulePortalProfile extends BaseDSModulePortalProfile {

	@Activate
	protected void activate(ComponentContext componentContext) {
		Set<String> supportedPortalProfileNames = null;

		if (GetterUtil.getBoolean(_props.get(PropsKeys.SCHEDULER_ENABLED))) {
			supportedPortalProfileNames = new HashSet<>();

			supportedPortalProfileNames.add(
				PortalProfile.PORTAL_PROFILE_NAME_CE);
			supportedPortalProfileNames.add(
				PortalProfile.PORTAL_PROFILE_NAME_DXP);
		}
		else {
			supportedPortalProfileNames = Collections.emptySet();

			BundleContext bundleContext = componentContext.getBundleContext();

			_schedulerEngineServiceRegistration = bundleContext.registerService(
				SchedulerEngine.class,
				ProxyFactory.newDummyInstance(SchedulerEngine.class),
				new HashMapDictionary<>());

			_triggerFactoryServiceRegistration = bundleContext.registerService(
				TriggerFactory.class,
				ProxyFactory.newDummyInstance(TriggerFactory.class),
				new HashMapDictionary<>());
		}

		init(
			componentContext, supportedPortalProfileNames,
			QuartzSchedulerEngine.class.getName(),
			QuartzSchedulerProxyMessageListener.class.getName(),
			QuartzSchemaManager.class.getName(),
			QuartzTriggerFactory.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		if (_triggerFactoryServiceRegistration != null) {
			_triggerFactoryServiceRegistration.unregister();
		}

		if (_schedulerEngineServiceRegistration != null) {
			_schedulerEngineServiceRegistration.unregister();
		}
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	private Props _props;
	private ServiceRegistration<SchedulerEngine>
		_schedulerEngineServiceRegistration;
	private ServiceRegistration<TriggerFactory>
		_triggerFactoryServiceRegistration;

}
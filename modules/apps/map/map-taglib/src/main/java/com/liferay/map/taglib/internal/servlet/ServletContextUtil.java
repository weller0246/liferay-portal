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

package com.liferay.map.taglib.internal.servlet;

import com.liferay.map.MapProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.Collection;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = {})
public class ServletContextUtil {

	public static GroupLocalService getGroupLocalService() {
		return _groupLocalService;
	}

	public static MapProvider getMapProvider(String mapProviderKey) {
		return _mapProviders.getService(mapProviderKey);
	}

	public static Collection<MapProvider> getMapProviders() {
		return _mapProviders.values();
	}

	public static ServletContext getServletContext() {
		return _servletContext;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_mapProviders = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, MapProvider.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(mapProvider, emitter) -> emitter.emit(mapProvider.getKey())));
	}

	@Deactivate
	protected void deactivate() {
		_mapProviders.close();
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.map.taglib)", unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static GroupLocalService _groupLocalService;
	private static ServiceTrackerMap<String, MapProvider> _mapProviders;
	private static ServletContext _servletContext;

}
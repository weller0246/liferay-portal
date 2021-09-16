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

package com.liferay.portal.kernel.portlet.configuration.icon;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.locator.PortletConfigurationIconLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.filter.PortletRequestWrapper;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;

/**
 * @author Eudaldo Alonso
 */
public class PortletConfigurationIconTracker {

	public static List<PortletConfigurationIcon> getPortletConfigurationIcons(
		String portletId, PortletRequest portletRequest) {

		return _getPortletConfigurationIcons(portletId, portletRequest, false);
	}

	public static List<PortletConfigurationIcon> getPortletConfigurationIcons(
		String portletId, PortletRequest portletRequest,
		Comparator<?> comparator) {

		List<PortletConfigurationIcon> portletConfigurationIcons =
			_getPortletConfigurationIcons(portletId, portletRequest, true);

		Collections.sort(
			portletConfigurationIcons,
			(Comparator<PortletConfigurationIcon>)comparator);

		return portletConfigurationIcons;
	}

	protected static String getKey(String portletId, String path) {
		return StringBundler.concat(portletId, StringPool.COLON, path);
	}

	protected static Set<String> getPaths(
		String portletId, PortletRequest portletRequest) {

		Set<String> paths = _defaultPaths;

		for (PortletConfigurationIconLocator portletConfigurationIconLocator :
				_serviceTrackerList) {

			String path = portletConfigurationIconLocator.getPath(
				portletRequest);

			if (!path.isEmpty()) {
				if (paths == _defaultPaths) {
					paths = new HashSet<>();
				}

				paths.add(path);

				if (!path.equals(StringPool.DASH)) {
					List<String> defaultViews =
						portletConfigurationIconLocator.getDefaultViews(
							portletId);

					if (defaultViews.contains(path)) {
						paths.add(StringPool.DASH);
					}
				}
			}
		}

		return paths;
	}

	private static List<PortletConfigurationIcon> _getPortletConfigurationIcons(
		String portletId, PortletRequest portletRequest, boolean filter) {

		List<PortletConfigurationIcon> portletConfigurationIcons =
			new ArrayList<>();

		if (portletRequest == null) {
			return portletConfigurationIcons;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletRequestWrapper portletRequestWrapper = new PortletRequestWrapper(
			portletRequest) {

			@Override
			public Object getAttribute(String name) {
				if (Objects.equals(name, WebKeys.THEME_DISPLAY)) {
					return themeDisplay;
				}

				return super.getAttribute(name);
			}

		};

		HttpServletRequest originalHttpServletRequest =
			PortalUtil.getOriginalServletRequest(
				PortalUtil.getHttpServletRequest(portletRequest));

		String layoutMode = ParamUtil.getString(
			originalHttpServletRequest, "p_l_mode", Constants.VIEW);

		for (String path : getPaths(portletId, portletRequest)) {
			List<PortletConfigurationIcon> portletPortletConfigurationIcons =
				_serviceTrackerMap.getService(getKey(StringPool.STAR, path));

			if (portletPortletConfigurationIcons != null) {
				for (PortletConfigurationIcon portletConfigurationIcon :
						portletPortletConfigurationIcons) {

					if (Objects.equals(layoutMode, Constants.EDIT) &&
						!portletConfigurationIcon.isShowInEditMode(
							portletRequest)) {

						continue;
					}

					if (!filter ||
						portletConfigurationIcon.isShow(
							portletRequestWrapper)) {

						portletConfigurationIcons.add(portletConfigurationIcon);
					}
				}
			}

			portletPortletConfigurationIcons = _serviceTrackerMap.getService(
				getKey(portletId, path));

			if (portletPortletConfigurationIcons == null) {
				continue;
			}

			for (PortletConfigurationIcon portletConfigurationIcon :
					portletPortletConfigurationIcons) {

				if (Objects.equals(layoutMode, Constants.EDIT) &&
					!portletConfigurationIcon.isShowInEditMode(
						portletRequestWrapper)) {

					continue;
				}

				if (!portletConfigurationIcons.contains(
						portletConfigurationIcon) &&
					(!filter ||
					 portletConfigurationIcon.isShow(portletRequestWrapper))) {

					portletConfigurationIcons.add(portletConfigurationIcon);
				}
			}
		}

		return portletConfigurationIcons;
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final Set<String> _defaultPaths = Collections.singleton(
		StringPool.DASH);
	private static final ServiceTrackerList
		<PortletConfigurationIconLocator, PortletConfigurationIconLocator>
			_serviceTrackerList = ServiceTrackerListFactory.open(
				_bundleContext, PortletConfigurationIconLocator.class);
	private static final ServiceTrackerMap
		<String, List<PortletConfigurationIcon>> _serviceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				_bundleContext, PortletConfigurationIcon.class, null,
				new PortletConfigurationIconServiceReferenceMapper());

}
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

package com.liferay.portal.language.extender.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.UTF8Control;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class LanguageResourcesExtender
	implements BundleTrackerCustomizer<List<ServiceRegistration<?>>> {

	@Override
	public List<ServiceRegistration<?>> addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		List<BundleCapability> bundleCapabilities =
			bundleWiring.getCapabilities("liferay.language.resources");

		if (ListUtil.isEmpty(bundleCapabilities)) {
			return null;
		}

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		for (BundleCapability bundleCapability : bundleCapabilities) {
			Map<String, Object> attributes = bundleCapability.getAttributes();

			Object baseName = attributes.get("resource.bundle.base.name");

			if (baseName instanceof String) {
				_registerResourceBundles(
					bundle, (String)baseName,
					GetterUtil.getInteger(
						attributes.get(Constants.SERVICE_RANKING)),
					serviceRegistrations);
			}
		}

		return serviceRegistrations;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		List<ServiceRegistration<?>> serviceRegistrations) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		List<ServiceRegistration<?>> serviceRegistrations) {

		for (ServiceRegistration<?> serviceRegistration :
				serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_bundleTracker = new BundleTracker<>(
			bundleContext, ~Bundle.INSTALLED & ~Bundle.UNINSTALLED, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private void _registerResourceBundles(
		Bundle bundle, String baseName, int serviceRanking,
		List<ServiceRegistration<?>> serviceRegistrations) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		int index = baseName.lastIndexOf(StringPool.PERIOD);

		String path = StringPool.SLASH;
		String name = baseName;

		if (index > 0) {
			path = baseName.substring(0, index);

			path =
				StringPool.SLASH +
					StringUtil.replace(path, CharPool.PERIOD, CharPool.SLASH);

			name = baseName.substring(index + 1);
		}

		Enumeration<URL> enumeration = bundle.findEntries(
			path, name.concat("_*.properties"), false);

		if (enumeration == null) {
			return;
		}

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String urlPath = url.getPath();

			String languageId = urlPath.substring(
				path.length() + name.length() + 2,
				urlPath.length() - ".properties".length());

			Locale locale = LocaleUtil.fromLanguageId(languageId, false);

			ResourceBundle resourceBundle = ResourceBundle.getBundle(
				baseName, locale, bundleWiring.getClassLoader(),
				UTF8Control.INSTANCE);

			ServiceRegistration<?> serviceRegistration =
				_bundleContext.registerService(
					ResourceBundle.class, resourceBundle,
					HashMapDictionaryBuilder.<String, Object>put(
						Constants.SERVICE_RANKING, serviceRanking
					).put(
						"language.id", languageId
					).build());

			serviceRegistrations.add(serviceRegistration);
		}
	}

	private BundleContext _bundleContext;
	private BundleTracker<?> _bundleTracker;

}
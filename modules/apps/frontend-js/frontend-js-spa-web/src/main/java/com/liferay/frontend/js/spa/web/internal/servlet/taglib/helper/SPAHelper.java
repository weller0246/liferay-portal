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

package com.liferay.frontend.js.spa.web.internal.servlet.taglib.helper;

import com.liferay.frontend.js.spa.web.internal.configuration.SPAConfiguration;
import com.liferay.osgi.util.StringPlus;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Bruno Basto
 */
@Component(
	configurationPid = "com.liferay.frontend.js.spa.web.internal.configuration.SPAConfiguration",
	service = SPAHelper.class
)
public class SPAHelper {

	public long getCacheExpirationTime(long companyId) {
		return _cacheExpirationTime;
	}

	public JSONArray getExcludedPathsJSONArray() {
		return _spaExcludedPathsJSONArray;
	}

	public ResourceBundle getLanguageResourceBundle(
		String servletContextName, Locale locale) {

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByServletContextName(servletContextName);

		if (resourceBundleLoader == null) {
			resourceBundleLoader =
				ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
		}

		return resourceBundleLoader.loadResourceBundle(locale);
	}

	public String getLoginRedirect(HttpServletRequest httpServletRequest) {
		return ParamUtil.getString(httpServletRequest, _REDIRECT_PARAM_NAME);
	}

	public String getNavigationExceptionSelectors() {
		return _navigationExceptionSelectorsString;
	}

	public JSONArray getPortletsBlacklistJSONArray(ThemeDisplay themeDisplay) {
		JSONArray portletsBlacklistJSONArray =
			JSONFactoryUtil.createJSONArray();

		_portletLocalService.visitPortlets(
			themeDisplay.getCompanyId(),
			portlet -> {
				if (!portlet.isSinglePageApplication() &&
					!portlet.isUndeployedPortlet() && portlet.isActive() &&
					portlet.isReady()) {

					portletsBlacklistJSONArray.put(portlet.getPortletId());
				}
			});

		return portletsBlacklistJSONArray;
	}

	public int getRequestTimeout() {
		return _spaConfiguration.requestTimeout();
	}

	public int getUserNotificationTimeout() {
		return _spaConfiguration.userNotificationTimeout();
	}

	public JSONArray getValidStatusCodesJSONArray() {
		return _VALID_STATUS_CODES_JSON_ARRAY;
	}

	public boolean isClearScreensCache(
		HttpServletRequest httpServletRequest, HttpSession session) {

		boolean singlePageApplicationClearCache = GetterUtil.getBoolean(
			httpServletRequest.getAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE));

		if (singlePageApplicationClearCache) {
			return true;
		}

		String portletId = httpServletRequest.getParameter("p_p_id");

		if (Validator.isNull(portletId)) {
			return false;
		}

		String singlePageApplicationLastPortletId =
			(String)session.getAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_LAST_PORTLET_ID);

		if (Validator.isNotNull(singlePageApplicationLastPortletId) &&
			!Objects.equals(portletId, singlePageApplicationLastPortletId)) {

			return true;
		}

		return false;
	}

	public boolean isDebugEnabled() {
		return _log.isDebugEnabled();
	}

	@Activate
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws InvalidSyntaxException {

		_spaConfiguration = ConfigurableUtil.createConfigurable(
			SPAConfiguration.class, properties);

		_cacheExpirationTime = _getCacheExpirationTime(_spaConfiguration);
		_spaExcludedPathsJSONArray = _getExcludedPathsJSONArray(
			_spaConfiguration);

		Collections.addAll(
			_navigationExceptionSelectors,
			_spaConfiguration.navigationExceptionSelectors());

		_navigationExceptionSelectorsString = ListUtil.toString(
			_navigationExceptionSelectors, (String)null, StringPool.BLANK);

		Filter filter = bundleContext.createFilter(
			"(&(objectClass=java.lang.Object)(" +
				_SPA_NAVIGATION_EXCEPTION_SELECTOR_KEY + "=*))");

		_navigationExceptionSelectorTracker = new ServiceTracker<>(
			bundleContext, filter,
			new NavigationExceptionSelectorTrackerCustomizer(bundleContext));

		_navigationExceptionSelectorTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_navigationExceptionSelectorTracker.close();
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_navigationExceptionSelectors.removeAll(
			Arrays.asList(_spaConfiguration.navigationExceptionSelectors()));

		_spaConfiguration = ConfigurableUtil.createConfigurable(
			SPAConfiguration.class, properties);

		_cacheExpirationTime = _getCacheExpirationTime(_spaConfiguration);
		_spaExcludedPathsJSONArray = _getExcludedPathsJSONArray(
			_spaConfiguration);

		Collections.addAll(
			_navigationExceptionSelectors,
			_spaConfiguration.navigationExceptionSelectors());

		_navigationExceptionSelectorsString = ListUtil.toString(
			_navigationExceptionSelectors, (String)null, StringPool.BLANK);
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	private long _getCacheExpirationTime(SPAConfiguration spaConfiguration) {
		long cacheExpirationTime = spaConfiguration.cacheExpirationTime();

		if (cacheExpirationTime > 0) {
			cacheExpirationTime *= Time.MINUTE;
		}

		return cacheExpirationTime;
	}

	private JSONArray _getExcludedPathsJSONArray(
		SPAConfiguration spaConfiguration) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String excludedPath : _SPA_DEFAULT_EXCLUDED_PATHS) {
			jsonArray.put(_portal.getPathContext() + excludedPath);
		}

		String[] customExcludedPaths = spaConfiguration.customExcludedPaths();

		if (ArrayUtil.isEmpty(customExcludedPaths)) {
			return jsonArray;
		}

		for (String customExcludedPath : customExcludedPaths) {
			jsonArray.put(_portal.getPathContext() + customExcludedPath);
		}

		return jsonArray;
	}

	private static final String _REDIRECT_PARAM_NAME;

	private static final String[] _SPA_DEFAULT_EXCLUDED_PATHS = {
		"/c/document_library", "/documents", "/image"
	};

	private static final String _SPA_NAVIGATION_EXCEPTION_SELECTOR_KEY =
		"javascript.single.page.application.navigation.exception.selector";

	private static final JSONArray _VALID_STATUS_CODES_JSON_ARRAY;

	private static final Log _log = LogFactoryUtil.getLog(SPAHelper.class);

	private static final List<String> _navigationExceptionSelectors =
		new CopyOnWriteArrayList<>();
	private static volatile String _navigationExceptionSelectorsString;

	static {
		Class<?> clazz = ServletResponseConstants.class;

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Field field : clazz.getDeclaredFields()) {
			try {
				jsonArray.put(field.getInt(null));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}
		}

		_VALID_STATUS_CODES_JSON_ARRAY = jsonArray;

		String portletNamespace = PortalUtil.getPortletNamespace(
			PropsUtil.get(PropsKeys.AUTH_LOGIN_PORTLET_NAME));

		_REDIRECT_PARAM_NAME = portletNamespace.concat("redirect");
	}

	private volatile long _cacheExpirationTime;
	private ServiceTracker<Object, Object> _navigationExceptionSelectorTracker;

	@Reference
	private Portal _portal;

	private PortletLocalService _portletLocalService;
	private volatile SPAConfiguration _spaConfiguration;
	private volatile JSONArray _spaExcludedPathsJSONArray;

	private static final class NavigationExceptionSelectorTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		public NavigationExceptionSelectorTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			List<String> selectors = StringPlus.asList(
				serviceReference.getProperty(
					_SPA_NAVIGATION_EXCEPTION_SELECTOR_KEY));

			_navigationExceptionSelectors.addAll(selectors);

			_navigationExceptionSelectorsString = ListUtil.toString(
				_navigationExceptionSelectors, (String)null, StringPool.BLANK);

			Object service = _bundleContext.getService(serviceReference);

			_serviceReferences.add(serviceReference);

			return service;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, Object service) {

			removedService(serviceReference, service);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, Object service) {

			List<String> selectors = StringPlus.asList(
				serviceReference.getProperty(
					_SPA_NAVIGATION_EXCEPTION_SELECTOR_KEY));

			_navigationExceptionSelectors.removeAll(selectors);

			_navigationExceptionSelectorsString = ListUtil.toString(
				_navigationExceptionSelectors, (String)null, StringPool.BLANK);

			_serviceReferences.remove(serviceReference);

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;
		private final List<ServiceReference<Object>> _serviceReferences =
			new CopyOnWriteArrayList<>();

	}

}
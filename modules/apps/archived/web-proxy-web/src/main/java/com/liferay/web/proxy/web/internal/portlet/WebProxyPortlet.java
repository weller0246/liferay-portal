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

package com.liferay.web.proxy.web.internal.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.LiferayPortletUtil;
import com.liferay.web.proxy.web.internal.constants.WebProxyPortletKeys;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.Servlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import org.portletbridge.portlet.PortletBridgePortlet;
import org.portletbridge.portlet.PortletBridgeServlet;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-web-proxy",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=true",
		"com.liferay.portlet.render-weight=50",
		"javax.portlet.display-name=Web Proxy",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.authenticatorClassName=org.portletbridge.portlet.DefaultBridgeAuthenticator",
		"javax.portlet.init-param.cssRegex=(?:url\\((?:'|\")?(.*?)(?:'|\")?\\))|(?:@import\\s+[^url](?:'|\")?(.*?)(?:'|\")|;|\\s+|$)",
		"javax.portlet.init-param.editStylesheet=classpath:/org/portletbridge/xsl/pages/edit.xsl",
		"javax.portlet.init-param.errorStylesheet=classpath:/org/portletbridge/xsl/pages/error.xsl",
		"javax.portlet.init-param.helpStylesheet=classpath:/org/portletbridge/xsl/pages/help.xsl",
		"javax.portlet.init-param.idParamKey=id",
		"javax.portlet.init-param.jsRegex=open\\('([^']*)'|open\\(\"([^\\\"]*)\"",
		"javax.portlet.init-param.mementoSessionKey=mementoSessionKey",
		"javax.portlet.init-param.parserClassName=org.cyberneko.html.parsers.SAXParser",
		"javax.portlet.init-param.servletName=pbhs",
		"javax.portlet.init-param.stylesheetUrl=classpath:/org/portletbridge/xsl/default.xsl",
		"javax.portlet.name=" + WebProxyPortletKeys.WEB_PROXY,
		"javax.portlet.preferences=classpath:/META-INF/portlet-preferences/default-portlet-preferences.xml",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class WebProxyPortlet extends PortletBridgePortlet {

	@Override
	public void destroy() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		super.destroy();
	}

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (!_enabled) {
			printError(renderResponse);

			return;
		}

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String initUrl = portletPreferences.getValue(
			"initUrl", StringPool.BLANK);

		if (Validator.isNull(initUrl)) {
			PortletContext portletContext = getPortletContext();

			PortletRequestDispatcher portletRequestDispatcher =
				portletContext.getRequestDispatcher("/portlet_not_setup.jsp");

			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
		else {
			super.doView(renderRequest, renderResponse);

			LiferayPortletResponse liferayPortletResponse =
				LiferayPortletUtil.getLiferayPortletResponse(renderResponse);

			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)
					liferayPortletResponse.getHttpServletResponse();

			String output = bufferCacheServletResponse.getString();

			output = StringUtil.replace(
				output, "//pbhs/", renderRequest.getContextPath() + "/pbhs/");

			bufferCacheServletResponse.setString(output);
		}
	}

	@Override
	public void init() {
		try {
			super.init();

			doInit();

			_enabled = true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception.getMessage());
			}
		}
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		_componentContext = componentContext;
	}

	@Deactivate
	protected void deactivate() {
		_componentContext = null;
	}

	protected void doInit() {
		BundleContext bundleContext = _componentContext.getBundleContext();

		PortletConfig portletConfig = getPortletConfig();

		_serviceRegistration = bundleContext.registerService(
			Servlet.class, new PortletBridgeServlet(),
			HashMapDictionaryBuilder.<String, Object>put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT,
				() -> {
					PortletContext portletContext = getPortletContext();

					return portletContext.getPortletContextName();
				}
			).put(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX +
						"ignorePostToGetRequestHeaders",
				"content-type,content-length"
			).put(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX +
						"ignoreRequestHeaders",
				"accept-encoding,connection,keep-alive"
			).put(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX +
						"mementoSessionKey",
				portletConfig.getInitParameter("mementoSessionKey")
			).put(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX + "cssRegex",
				portletConfig.getInitParameter("cssRegex")
			).put(
				HttpWhiteboardConstants.
					HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX + "jsRegex",
				portletConfig.getInitParameter("jsRegex")
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME,
				PortletBridgeServlet.class.getName()
			).put(
				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN,
				"/pbhs/*"
			).build());
	}

	protected void printError(RenderResponse renderResponse)
		throws IOException {

		renderResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);

		try (PrintWriter writer = renderResponse.getWriter()) {
			writer.print(
				"WebProxyPortlet will not be enabled unless Liferay's " +
					"serializer.jar and xalan.jar files are copied to the " +
						"JDK's endorsed directory");
		}
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.web.proxy.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebProxyPortlet.class);

	private ComponentContext _componentContext;
	private boolean _enabled;
	private ServiceRegistration<?> _serviceRegistration;

}
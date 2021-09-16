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

package com.liferay.login.authentication.openid.connect.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnect;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectAuthenticationHandler;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ActionURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Enables the Sign In portlet to process an OpenID login attempt. When invoked,
 * the following steps are carried out:
 *
 * <ol>
 * <li>
 * Discover the OpenID provider's XRDS document by performing HTTP GET on
 * the user's OpenID URL. This document tells Liferay Portal the OpenID
 * provider's URL.
 * </li>
 * <li>
 * Retrieve the OpenID provider's authentication URL which is provided by
 * the XRDS document and prepare an OpenID authorization request URL. This URL
 * includes a return URL parameter (encoded) which points back to this
 * MVCActionRequest with an additional parameter <code>cmd = read</code> (used
 * in step 7).
 * </li>
 * <li>
 * Search for an existing Liferay Portal user with the user provided OpenID.
 * </li>
 * <li>
 * If found, redirect the browser to the OpenID authentication request URL
 * and wait for the browser to be redirected back to Liferay Portal when all
 * steps repeat. Otherwise, ...
 * </li>
 * <li>
 * Generate a valid Liferay Portal user screen name based on the OpenID
 * and search for an existing Liferay Portal user with a matching screen name.
 * If found, then update the Liferay Portal user's OpenID to match and redirect
 * the browser to the OpenID authentication request URL. Otherwise, ...
 * </li>
 * <li>
 * Enrich the OpenID authentication request URL with a request for specific
 * attributes (the user's <code>fullname</code> and <code>email</code>). Then
 * redirect the browser to the enriched OpenID authentication request URL.
 * </li>
 * <li>
 * Upon returning from the OpenID provider's authentication process, the
 * MVCActionCommand finds the URL parameter <code>cmd</code> set to
 * <code>read</code> (see step 2).
 * </li>
 * <li>
 * The request is verified as being from the same OpenID provider.
 * </li>
 * <li>
 * If the attributes requested in step 6 are not found, then the web browser
 * is redirected to the Create Account page where the missing information must
 * be entered before a Liferay Portal user can be created. Otherwise, ...
 * </li>
 * <li>
 * The attributes are used to create a Liferay Portal user and the HTTP
 * session attribute <code>OPEN_ID_LOGIN</code> is set equal to the Liferay
 * Portal user's ID.
 * </li>
 * </ol>
 *
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"auth.token.ignore.mvc.action=true",
		"javax.portlet.name=" + PortletKeys.FAST_LOGIN,
		"javax.portlet.name=" + PortletKeys.LOGIN,
		"mvc.command.name=" + OpenIdConnectWebKeys.OPEN_ID_CONNECT_REQUEST_ACTION_NAME
	},
	service = MVCActionCommand.class
)
public class OpenIdConnectLoginRequestMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String openIdConnectProviderName = ParamUtil.getString(
				actionRequest,
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_PROVIDER_NAME);

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(actionRequest);

			httpServletRequest = _portal.getOriginalServletRequest(
				httpServletRequest);

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			HttpSession session = httpServletRequest.getSession();

			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(actionResponse);

			ActionURL actionURL = liferayPortletResponse.createActionURL();

			actionURL.setParameter(
				ActionRequest.ACTION_NAME,
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_RESPONSE_ACTION_NAME);
			actionURL.setParameter("saveLastPath", Boolean.FALSE.toString());

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				actionURL.setParameter("redirect", redirect);
			}

			session.setAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_ACTION_URL,
				actionURL.toString());

			_openIdConnectAuthenticationHandler.requestAuthentication(
				openIdConnectProviderName, httpServletRequest,
				httpServletResponse);
		}
		catch (Exception exception) {
			actionResponse.setRenderParameter(
				"mvcRenderCommandName",
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_REQUEST_ACTION_NAME);

			if (exception instanceof OpenIdConnectServiceException) {
				String message =
					"Unable to communicate with OpenID Connect provider: " +
						exception.getMessage();

				if (_log.isDebugEnabled()) {
					_log.debug(message, exception);
				}

				if (_log.isWarnEnabled()) {
					_log.warn(message);
				}

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else if (exception instanceof
						UserEmailAddressException.MustNotBeDuplicate) {

				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				_log.error(
					"Unable to process the OpenID Connect login: " +
						exception.getMessage(),
					exception);

				_portal.sendError(exception, actionRequest, actionResponse);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectLoginRequestMVCActionCommand.class);

	@Reference
	private OpenIdConnect _openIdConnect;

	@Reference
	private OpenIdConnectAuthenticationHandler
		_openIdConnectAuthenticationHandler;

	@Reference
	private Portal _portal;

}
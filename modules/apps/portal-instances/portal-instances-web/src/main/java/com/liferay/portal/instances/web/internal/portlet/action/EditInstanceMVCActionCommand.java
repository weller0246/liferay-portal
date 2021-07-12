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

package com.liferay.portal.instances.web.internal.portlet.action;

import com.liferay.portal.instances.initializer.PortalInstanceInitializer;
import com.liferay.portal.instances.initializer.PortalInstanceInitializerRegistry;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.instances.web.internal.constants.PortalInstancesPortletKeys;
import com.liferay.portal.kernel.exception.CompanyMxException;
import com.liferay.portal.kernel.exception.CompanyVirtualHostException;
import com.liferay.portal.kernel.exception.CompanyWebIdException;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredCompanyException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortalInstancesPortletKeys.PORTAL_INSTANCES,
		"mvc.command.name=/portal_instances/edit_instance"
	},
	service = MVCActionCommand.class
)
public class EditInstanceMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteInstance(ActionRequest actionRequest)
		throws Exception {

		long companyId = ParamUtil.getLong(actionRequest, "companyId");

		_companyService.deleteCompany(companyId);

		synchronizePortalInstances();
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteInstance(actionRequest);
			}
			else {
				updateInstance(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception exception) {
			String mvcPath = "/error.jsp";

			if (exception instanceof NoSuchCompanyException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else if (exception instanceof CompanyMxException ||
					 exception instanceof CompanyVirtualHostException ||
					 exception instanceof CompanyWebIdException) {

				long companyId = ParamUtil.getLong(actionRequest, "companyId");

				Company company = _companyLocalService.fetchCompanyById(
					companyId);

				if (company != null) {
					actionRequest.setAttribute(WebKeys.SEL_COMPANY, company);
				}

				SessionErrors.add(actionRequest, exception.getClass());

				SessionMessages.add(
					actionRequest,
					_portal.getPortletId(actionRequest) +
						SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);

				mvcPath = "/edit_instance.jsp";
			}
			else if (exception instanceof RequiredCompanyException) {
				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				_log.error(exception, exception);

				throw exception;
			}

			actionResponse.setRenderParameter("mvcPath", mvcPath);
		}
	}

	protected void synchronizePortalInstances() {
		_portalInstancesLocalService.synchronizePortalInstances();
	}

	protected void updateInstance(ActionRequest actionRequest)
		throws Exception {

		long companyId = ParamUtil.getLong(actionRequest, "companyId");

		String virtualHostname = ParamUtil.getString(
			actionRequest, "virtualHostname");
		String mx = ParamUtil.getString(actionRequest, "mx");
		int maxUsers = ParamUtil.getInteger(actionRequest, "maxUsers");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		if (companyId <= 0) {

			// Add instance

			String webId = ParamUtil.getString(actionRequest, "webId");

			Company company = _companyService.addCompany(
				webId, virtualHostname, mx, false, maxUsers, active);

			ServletContext servletContext =
				(ServletContext)actionRequest.getAttribute(WebKeys.CTX);

			_portalInstancesLocalService.initializePortalInstance(
				servletContext, company.getWebId());

			// Initialize instance with portal instance initializer

			String portalInstanceInitializerKey = ParamUtil.getString(
				actionRequest, "portalInstanceInitializerKey");

			if (Validator.isNotNull(portalInstanceInitializerKey)) {
				PortalInstanceInitializer portalInstanceInitializer =
					_portalInstanceInitializerRegistry.
						getPortalInstanceInitializer(
							portalInstanceInitializerKey);

				if (portalInstanceInitializer == null) {
					throw new PortalException(
						"Invalid portal instance initializer key " +
							portalInstanceInitializerKey);
				}

				portalInstanceInitializer.initialize(
					company.getCompanyId(),
					_portal.getHttpServletRequest(actionRequest),
					Collections.emptyMap());
			}
		}
		else {

			// Update instance

			_companyService.updateCompany(
				companyId, virtualHostname, mx, maxUsers, active);
		}

		synchronizePortalInstances();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditInstanceMVCActionCommand.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CompanyService _companyService;

	@Reference
	private Portal _portal;

	@Reference
	private PortalInstanceInitializerRegistry
		_portalInstanceInitializerRegistry;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

}
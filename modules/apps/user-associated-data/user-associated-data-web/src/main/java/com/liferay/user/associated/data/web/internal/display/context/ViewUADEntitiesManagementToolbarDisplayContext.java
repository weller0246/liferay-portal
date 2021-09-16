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

package com.liferay.user.associated.data.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.web.internal.constants.UADConstants;
import com.liferay.user.associated.data.web.internal.display.ViewUADEntitiesDisplay;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class ViewUADEntitiesManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ViewUADEntitiesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		ViewUADEntitiesDisplay viewUADEntitiesDisplay) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			viewUADEntitiesDisplay.getSearchContainer());

		_viewUADEntitiesDisplay = viewUADEntitiesDisplay;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(
					StringBundler.concat(
						"javascript:", getNamespace(),
						"doAnonymizeMultiple();"));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "anonymize"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(
					StringBundler.concat(
						"javascript:", getNamespace(), "doDeleteMultiple();"));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			(String)null
		).buildString();
	}

	@Override
	public String getComponentId() {
		return "viewUADEntitiesManagementToolbar_" + StringUtil.randomId();
	}

	@Override
	public String getInfoPanelId() {
		return "infoPanelId";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL portletURL = getPortletURL();

		return portletURL.toString();
	}

	@Override
	public Boolean isShowInfoButton() {
		String applicationKey = _viewUADEntitiesDisplay.getApplicationKey();

		if (applicationKey.equals(UADConstants.ALL_APPLICATIONS)) {
			return false;
		}

		return true;
	}

	@Override
	public Boolean isShowSearch() {
		return true;
	}

	@Override
	protected Map<String, String> getOrderByEntriesMap() {
		return searchContainer.getOrderableHeaders();
	}

	@Override
	protected PortletURL getPortletURL() {
		PortletURL portletURL = searchContainer.getIteratorURL();

		try {
			portletURL = PortletURLUtil.clone(
				portletURL, liferayPortletResponse);
		}
		catch (PortletException portletException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portletException, portletException);
			}

			portletURL = PortletURLBuilder.createRenderURL(
				liferayPortletResponse
			).setParameters(
				portletURL.getParameterMap()
			).buildPortletURL();
		}

		String[] parameterNames = {
			"keywords", "orderByCol", "orderByType", "cur", "delta"
		};

		for (String parameterName : parameterNames) {
			String value = ParamUtil.getString(
				httpServletRequest, parameterName);

			if (Validator.isNotNull(value)) {
				portletURL.setParameter(parameterName, (String)null);
				portletURL.setParameter(parameterName, value);
			}
		}

		return portletURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewUADEntitiesManagementToolbarDisplayContext.class);

	private final ViewUADEntitiesDisplay _viewUADEntitiesDisplay;

}
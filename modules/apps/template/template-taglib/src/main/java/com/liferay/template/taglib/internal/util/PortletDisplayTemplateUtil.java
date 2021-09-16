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

package com.liferay.template.taglib.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = {})
public class PortletDisplayTemplateUtil {

	public static long getDDMTemplateGroupId(long groupId) {
		return _portletDisplayTemplate.getDDMTemplateGroupId(groupId);
	}

	public static String getDisplayStyle(String ddmTemplateKey) {
		return _portletDisplayTemplate.getDisplayStyle(ddmTemplateKey);
	}

	public static DDMTemplate getPortletDisplayTemplateDDMTemplate(
		long groupId, long classNameId, String displayStyle,
		boolean useDefault) {

		return _portletDisplayTemplate.getPortletDisplayTemplateDDMTemplate(
			groupId, classNameId, displayStyle, useDefault);
	}

	@Deactivate
	protected void deactivate() {
		_portletDisplayTemplate = null;
	}

	@Reference(unbind = "-")
	protected void setPortletDisplayTemplate(
		PortletDisplayTemplate portletDisplayTemplate) {

		_portletDisplayTemplate = portletDisplayTemplate;
	}

	private static PortletDisplayTemplate _portletDisplayTemplate;

}
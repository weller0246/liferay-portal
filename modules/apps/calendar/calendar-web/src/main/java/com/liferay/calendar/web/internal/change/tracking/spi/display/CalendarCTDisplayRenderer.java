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

package com.liferay.calendar.web.internal.change.tracking.spi.display;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.web.internal.util.ColorUtil;
import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class CalendarCTDisplayRenderer extends BaseCTDisplayRenderer<Calendar> {

	@Override
	public String[] getAvailableLanguageIds(Calendar calendar) {
		return calendar.getAvailableLanguageIds();
	}

	@Override
	public String getDefaultLanguageId(Calendar calendar) {
		return calendar.getDefaultLanguageId();
	}

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest, Calendar calendar)
		throws Exception {

		Group group = _groupLocalService.getGroup(calendar.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, CalendarPortletKeys.CALENDAR, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_calendar.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setBackURL(
			ParamUtil.getString(httpServletRequest, "backURL")
		).setParameter(
			"calendarId", calendar.getCalendarId()
		).setParameter(
			"calendarResourceId", calendar.getCalendarResourceId()
		).buildString();
	}

	@Override
	public Class<Calendar> getModelClass() {
		return Calendar.class;
	}

	@Override
	public String getTitle(Locale locale, Calendar calendar) {
		return calendar.getName(locale);
	}

	@Override
	protected void buildDisplay(DisplayBuilder<Calendar> displayBuilder) {
		Calendar calendar = displayBuilder.getModel();

		Locale locale = displayBuilder.getLocale();

		displayBuilder.display(
			"name", calendar.getName(locale)
		).display(
			"description", calendar.getDescription(locale)
		).display(
			"color",
			StringBundler.concat(
				"<span style=\"background-color: ",
				ColorUtil.toHexString(calendar.getColor()),
				"; display: inline-block; height: 80%; vertical-align: ",
				"middle; width: 80%;\">&nbsp;</span>"),
			false
		).display(
			"default-calendar", calendar.isDefaultCalendar()
		).display(
			"time-zone", calendar.getTimeZoneId()
		);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}
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

package com.liferay.dynamic.data.lists.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.taglib.servlet.taglib.HTMLTag;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DDLRecordCTDisplayRenderer
	extends BaseCTDisplayRenderer<DDLRecord> {

	@Override
	public String getContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Locale locale,
			DDLRecord ddlRecord)
		throws Exception {

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		HTMLTag htmlTag = new HTMLTag();

		htmlTag.setClassNameId(
			_classNameLocalService.getClassNameId(DDMStructure.class));
		htmlTag.setClassPK(ddlRecordSet.getDDMStructureId());
		htmlTag.setDdmFormValues(ddlRecord.getDDMFormValues());
		htmlTag.setGroupId(ddlRecord.getGroupId());
		htmlTag.setReadOnly(true);
		htmlTag.setRequestedLocale(locale);

		try (UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter()) {
			htmlTag.doTag(
				httpServletRequest,
				new PipingServletResponse(
					httpServletResponse, unsyncStringWriter));

			return unsyncStringWriter.toString();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return null;
	}

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest, DDLRecord ddlRecord)
		throws PortalException {

		Group group = _groupLocalService.getGroup(ddlRecord.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, DDLPortletKeys.DYNAMIC_DATA_LISTS, 0,
				0, PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_record.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"groupId", ddlRecord.getGroupId()
		).setParameter(
			"recordId", ddlRecord.getPrimaryKey()
		).setParameter(
			"version", ddlRecord.getVersion()
		).buildString();
	}

	@Override
	public Class<DDLRecord> getModelClass() {
		return DDLRecord.class;
	}

	@Override
	public String getTitle(Locale locale, DDLRecord ddlRecord) {
		return String.valueOf(ddlRecord.getPrimaryKey());
	}

	@Override
	protected void buildDisplay(DisplayBuilder<DDLRecord> displayBuilder) {
		DDLRecord ddlRecord = displayBuilder.getModel();

		displayBuilder.display(
			"created-by",
			() -> {
				String userName = ddlRecord.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"version", ddlRecord.getVersion()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordCTDisplayRenderer.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}
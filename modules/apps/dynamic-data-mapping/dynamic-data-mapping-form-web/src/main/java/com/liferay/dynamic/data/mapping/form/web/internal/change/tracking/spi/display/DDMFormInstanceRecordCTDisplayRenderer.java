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

package com.liferay.dynamic.data.mapping.form.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.taglib.servlet.taglib.HTMLTag;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DDMFormInstanceRecordCTDisplayRenderer
	extends BaseCTDisplayRenderer<DDMFormInstanceRecord> {

	@Override
	public String getContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Locale locale,
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		HTMLTag htmlTag = new HTMLTag();

		htmlTag.setClassNameId(
			_classNameLocalService.getClassNameId(DDMStructure.class));

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecord.getFormInstance();

		htmlTag.setClassPK(ddmFormInstance.getStructureId());

		htmlTag.setDdmFormValues(ddmFormInstanceRecord.getDDMFormValues());
		htmlTag.setGroupId(ddmFormInstanceRecord.getGroupId());
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
			HttpServletRequest httpServletRequest,
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		Group group = _groupLocalService.getGroup(
			ddmFormInstanceRecord.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group,
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/admin/edit_form_instance_record.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setBackURL(
			ParamUtil.getString(httpServletRequest, "backURL")
		).setParameter(
			"formInstanceId", ddmFormInstanceRecord.getFormInstanceId()
		).setParameter(
			"formInstanceRecordId",
			ddmFormInstanceRecord.getFormInstanceRecordId()
		).buildString();
	}

	@Override
	public Class<DDMFormInstanceRecord> getModelClass() {
		return DDMFormInstanceRecord.class;
	}

	@Override
	public String getTitle(
		Locale locale, DDMFormInstanceRecord ddmFormInstanceRecord) {

		return String.valueOf(ddmFormInstanceRecord.getPrimaryKey());
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<DDMFormInstanceRecord> displayBuilder) {

		DDMFormInstanceRecord ddmFormInstanceRecord = displayBuilder.getModel();

		displayBuilder.display(
			"author",
			() -> {
				String userName = ddmFormInstanceRecord.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"status",
			() -> {
				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					displayBuilder.getLocale(), getClass());

				return _language.get(
					resourceBundle,
					WorkflowConstants.getStatusLabel(
						ddmFormInstanceRecord.getStatus()));
			}
		).display(
			"version", ddmFormInstanceRecord.getVersion()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordCTDisplayRenderer.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}
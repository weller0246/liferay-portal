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

package com.liferay.dynamic.data.mapping.form.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.constants.DDMFormWebKeys;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.DDMFormViewFormInstanceRecordDisplayContext;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leonardo Barros
 */
public class DDMFormAssetRenderer
	extends BaseJSPAssetRenderer<DDMFormInstanceRecord> {

	public DDMFormAssetRenderer(
		DDMFormInstanceRecord ddmFormInstanceRecord,
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService,
		ModelResourcePermission<DDMFormInstanceRecord>
			ddmFormInstanceRecordModelResourcePermission,
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
		DDMFormInstanceVersionLocalService ddmFormInstanceVersionLocalService,
		DDMFormRenderer ddmFormRenderer,
		DDMFormValuesFactory ddmFormValuesFactory,
		DDMFormValuesMerger ddmFormValuesMerger, Portal portal) {

		_ddmFormInstanceRecord = ddmFormInstanceRecord;
		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
		_ddmFormInstanceRecordModelResourcePermission =
			ddmFormInstanceRecordModelResourcePermission;
		_ddmFormInstanceRecordVersion = ddmFormInstanceRecordVersion;
		_ddmFormInstanceVersionLocalService =
			ddmFormInstanceVersionLocalService;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormValuesFactory = ddmFormValuesFactory;
		_ddmFormValuesMerger = ddmFormValuesMerger;
		_portal = portal;

		DDMFormInstance ddmFormInstance = null;

		try {
			ddmFormInstance = _ddmFormInstanceRecordVersion.getFormInstance();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		_ddmFormInstance = ddmFormInstance;
	}

	@Override
	public DDMFormInstanceRecord getAssetObject() {
		return _ddmFormInstanceRecord;
	}

	@Override
	public AssetRendererFactory<DDMFormInstanceRecord>
		getAssetRendererFactory() {

		return new DDMFormAssetRendererFactory();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _ddmFormInstance.getAvailableLanguageIds();
	}

	@Override
	public String getClassName() {
		return DDMFormInstanceRecord.class.getName();
	}

	@Override
	public long getClassPK() {
		return _ddmFormInstanceRecord.getFormInstanceRecordId();
	}

	@Override
	public long getGroupId() {
		return _ddmFormInstanceRecord.getGroupId();
	}

	@Override
	public String getJspPath(
		HttpServletRequest httpServletRequest, String template) {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/full_content.jsp";
		}

		return null;
	}

	@Override
	public int getStatus() {
		return _ddmFormInstanceRecordVersion.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.format(
			locale, "form-record-for-form-x", _ddmFormInstance.getName(locale),
			false);
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		HttpServletRequest httpServletRequest =
			liferayPortletRequest.getHttpServletRequest();

		String portletNamespace = DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM;

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, portletNamespace,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/display/edit_form_instance_record.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"defaultLanguageId",
			() -> {
				DDMFormValues ddmFormValues =
					_ddmFormInstanceRecordVersion.getDDMFormValues();

				return LocaleUtil.toLanguageId(
					ddmFormValues.getDefaultLocale());
			}
		).setParameter(
			"formInstanceId", _ddmFormInstanceRecord.getFormInstanceId()
		).setParameter(
			"formInstanceRecordId",
			_ddmFormInstanceRecord.getFormInstanceRecordId()
		).buildPortletURL();
	}

	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		return noSuchEntryRedirect;
	}

	@Override
	public long getUserId() {
		return _ddmFormInstanceRecord.getUserId();
	}

	@Override
	public String getUserName() {
		return _ddmFormInstanceRecord.getUserName();
	}

	@Override
	public String getUuid() {
		return _ddmFormInstanceRecord.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		try {
			if (_ddmFormInstanceRecordModelResourcePermission.contains(
					permissionChecker, _ddmFormInstanceRecord,
					ActionKeys.UPDATE) ||
				_ddmFormInstanceRecordModelResourcePermission.contains(
					permissionChecker, _ddmFormInstanceRecord,
					DDMActionKeys.ADD_FORM_INSTANCE_RECORD)) {

				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return false;
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		try {
			return _ddmFormInstanceRecordModelResourcePermission.contains(
				permissionChecker, _ddmFormInstanceRecord, ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return false;
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		httpServletRequest.setAttribute(
			DDMFormWebKeys.DYNAMIC_DATA_MAPPING_FORM_INSTANCE_RECORD,
			_ddmFormInstanceRecord);

		DDMFormViewFormInstanceRecordDisplayContext
			ddmFormViewFormInstanceRecordDisplayContext =
				new DDMFormViewFormInstanceRecordDisplayContext(
					httpServletRequest, httpServletResponse,
					_ddmFormInstanceRecordLocalService,
					_ddmFormInstanceVersionLocalService, _ddmFormRenderer,
					_ddmFormValuesFactory, _ddmFormValuesMerger);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			ddmFormViewFormInstanceRecordDisplayContext);

		return super.include(httpServletRequest, httpServletResponse, template);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormAssetRenderer.class);

	private final DDMFormInstance _ddmFormInstance;
	private final DDMFormInstanceRecord _ddmFormInstanceRecord;
	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;
	private final ModelResourcePermission<DDMFormInstanceRecord>
		_ddmFormInstanceRecordModelResourcePermission;
	private final DDMFormInstanceRecordVersion _ddmFormInstanceRecordVersion;
	private final DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesFactory _ddmFormValuesFactory;
	private final DDMFormValuesMerger _ddmFormValuesMerger;
	private final Portal _portal;

}
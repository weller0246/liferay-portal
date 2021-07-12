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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.configuration.FFLayoutTranslationConfiguration;
import com.liferay.layout.admin.web.internal.configuration.LayoutConverterConfiguration;
import com.liferay.layout.admin.web.internal.display.context.LayoutsAdminDisplayContext;
import com.liferay.layout.admin.web.internal.display.context.MillerColumnsDisplayContext;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.template.LayoutConverterRegistry;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterTracker;
import com.liferay.translation.security.permission.TranslationPermission;
import com.liferay.translation.url.provider.TranslationURLProvider;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = {
		"com.liferay.layout.admin.web.internal.configuration.FFLayoutTranslationConfiguration",
		"com.liferay.layout.admin.web.internal.configuration.LayoutConverterConfiguration"
	},
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout_admin/get_layout_children"
	},
	service = MVCActionCommand.class
)
public class GetLayoutChildrenMVCActionCommand extends BaseMVCActionCommand {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffLayoutTranslationConfiguration = ConfigurableUtil.createConfigurable(
			FFLayoutTranslationConfiguration.class, properties);
		_layoutConverterConfiguration = ConfigurableUtil.createConfigurable(
			LayoutConverterConfiguration.class, properties);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long plid = ParamUtil.getLong(actionRequest, "plid");

		Layout layout = _layoutLocalService.fetchLayout(plid);

		LayoutsAdminDisplayContext layoutsAdminDisplayContext =
			new LayoutsAdminDisplayContext(
				_layoutConverterConfiguration, _layoutConverterRegistry,
				_layoutCopyHelper,
				_portal.getLiferayPortletRequest(actionRequest),
				_portal.getLiferayPortletResponse(actionResponse),
				_stagingGroupHelper);

		MillerColumnsDisplayContext millerColumnsDisplayContext =
			new MillerColumnsDisplayContext(
				_ffLayoutTranslationConfiguration, layoutsAdminDisplayContext,
				_portal.getLiferayPortletRequest(actionRequest),
				_portal.getLiferayPortletResponse(actionResponse),
				_translationInfoItemFieldValuesExporterTracker,
				_translationPermission, _translationURLProvider);

		JSONArray jsonArray = millerColumnsDisplayContext.getLayoutsJSONArray(
			layout.getLayoutId(), layout.isPrivateLayout());

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, JSONUtil.put("children", jsonArray));
	}

	private volatile FFLayoutTranslationConfiguration
		_ffLayoutTranslationConfiguration;
	private volatile LayoutConverterConfiguration _layoutConverterConfiguration;

	@Reference
	private LayoutConverterRegistry _layoutConverterRegistry;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

	@Reference
	private TranslationInfoItemFieldValuesExporterTracker
		_translationInfoItemFieldValuesExporterTracker;

	@Reference
	private TranslationPermission _translationPermission;

	@Reference
	private TranslationURLProvider _translationURLProvider;

}
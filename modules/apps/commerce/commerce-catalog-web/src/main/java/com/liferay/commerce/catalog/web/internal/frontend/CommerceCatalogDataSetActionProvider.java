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

package com.liferay.commerce.catalog.web.internal.frontend;

import com.liferay.commerce.catalog.web.internal.frontend.constants.CommerceCatalogDataSetConstants;
import com.liferay.commerce.catalog.web.internal.model.Catalog;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gianmarco Brunialti Masera
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceCatalogDataSetConstants.COMMERCE_DATA_SET_KEY_CATALOGS,
	service = ClayDataSetActionProvider.class
)
public class CommerceCatalogDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		Catalog catalog = (Catalog)model;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return DropdownItemListBuilder.add(
			() -> _commerceCatalogModelResourcePermission.contains(
				permissionChecker, catalog.getCatalogId(), ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getCatalogEditURL(
						catalog.getCatalogId(), httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, Constants.EDIT));
			}
		).add(
			() -> _commerceCatalogModelResourcePermission.contains(
				permissionChecker, catalog.getCatalogId(),
				ActionKeys.PERMISSIONS),
			dropdownItem -> {
				dropdownItem.setHref(
					_getManageCatalogPermissionsURL(
						catalog, httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "permissions"));
				dropdownItem.setTarget("modal-permissions");
			}
		).add(
			() -> _commerceCatalogModelResourcePermission.contains(
				permissionChecker, catalog.getCatalogId(), ActionKeys.DELETE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getCatalogDeleteURL(
						catalog.getCatalogId(), httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, Constants.DELETE));
			}
		).build();
	}

	private PortletURL _getCatalogDeleteURL(
		long catalogId, HttpServletRequest httpServletRequest) {

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, CPPortletKeys.COMMERCE_CATALOGS,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/commerce_catalogs/edit_commerce_catalog"
		).setCMD(
			Constants.DELETE
		).buildPortletURL();

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter("commerceCatalogId", String.valueOf(catalogId));

		return portletURL;
	}

	private PortletURL _getCatalogEditURL(
		long catalogId, HttpServletRequest httpServletRequest) {

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, CPPortletKeys.COMMERCE_CATALOGS,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/commerce_catalogs/edit_commerce_catalog"
		).buildPortletURL();

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter("commerceCatalogId", String.valueOf(catalogId));

		return portletURL;
	}

	private PortletURL _getManageCatalogPermissionsURL(
			Catalog catalog, HttpServletRequest httpServletRequest)
		throws PortalException {

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest,
				"com_liferay_portlet_configuration_web_portlet_" +
					"PortletConfigurationPortlet",
				ActionRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_permissions.jsp"
		).setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL", redirect
		).setParameter(
			"modelResource", CommerceCatalog.class.getName()
		).setParameter(
			"modelResourceDescription", catalog.getName()
		).setParameter(
			"resourcePrimKey", catalog.getCatalogId()
		).buildPortletURL();

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			throw new PortalException(windowStateException);
		}

		return portletURL;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceCatalog)"
	)
	private ModelResourcePermission<CommerceCatalog>
		_commerceCatalogModelResourcePermission;

	@Reference
	private Portal _portal;

}
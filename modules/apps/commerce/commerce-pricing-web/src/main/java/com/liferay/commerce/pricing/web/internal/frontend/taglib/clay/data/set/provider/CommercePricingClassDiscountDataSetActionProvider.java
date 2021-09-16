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

package com.liferay.commerce.pricing.web.internal.frontend.taglib.clay.data.set.provider;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingDataSetConstants;
import com.liferay.commerce.pricing.web.internal.model.PricingClassDiscount;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRICING_CLASSES_DISCOUNTS,
	service = ClayDataSetActionProvider.class
)
public class CommercePricingClassDiscountDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		PricingClassDiscount pricingClassDiscount = (PricingClassDiscount)model;

		return DropdownItemListBuilder.add(
			() -> _commerceDiscountModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				pricingClassDiscount.getCommerceDiscountId(),
				ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getDiscountEditURL(
						pricingClassDiscount.getCommerceDiscountId(),
						httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
			}
		).build();
	}

	private PortletURL _getDiscountEditURL(
		long commerceDiscountId, HttpServletRequest httpServletRequest) {

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest,
				CommercePricingPortletKeys.COMMERCE_DISCOUNT,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/commerce_discount/edit_commerce_discount"
		).setRedirect(
			redirect
		).setParameter(
			"commerceDiscountId", commerceDiscountId
		).buildPortletURL();
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.discount.model.CommerceDiscount)"
	)
	private ModelResourcePermission<CommerceDiscount>
		_commerceDiscountModelResourcePermission;

	@Reference
	private Portal _portal;

}
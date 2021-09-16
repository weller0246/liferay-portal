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

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Gianmarco Brunialti Masera
 * @author Ivica Cardic
 */
public class AddToCartTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			HttpServletRequest httpServletRequest = getRequest();

			CommerceContext commerceContext =
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			CommerceAccount commerceAccount =
				commerceContext.getCommerceAccount();

			if (commerceAccount != null) {
				_commerceAccountId = commerceAccount.getCommerceAccountId();
			}

			_commerceChannelGroupId =
				commerceContext.getCommerceChannelGroupId();
			_commerceChannelId = commerceContext.getCommerceChannelId();

			CommerceCurrency commerceCurrency =
				commerceContext.getCommerceCurrency();

			_commerceCurrencyCode = commerceCurrency.getCode();

			CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

			if (commerceOrder != null) {
				_commerceOrderId = commerceOrder.getCommerceOrderId();
			}

			CPSku cpSku = null;
			boolean hasChildCPDefinitions = false;

			if (_cpCatalogEntry != null) {
				cpSku = _cpContentHelper.getDefaultCPSku(_cpCatalogEntry);
				hasChildCPDefinitions = _cpContentHelper.hasChildCPDefinitions(
					_cpCatalogEntry.getCPDefinitionId());
			}

			String sku = null;

			if ((cpSku != null) && !hasChildCPDefinitions) {
				_cpInstanceId = cpSku.getCPInstanceId();
				_disabled =
					!cpSku.isPurchasable() ||
					((_commerceAccountId == 0) &&
					 !_commerceOrderHttpHelper.isGuestCheckoutEnabled(
						 httpServletRequest));
				sku = cpSku.getSku();

				if (commerceOrder != null) {
					List<CommerceOrderItem> commerceOrderItems =
						_commerceOrderItemLocalService.getCommerceOrderItems(
							commerceOrder.getCommerceOrderId(),
							cpSku.getCPInstanceId(), 0, 1);

					if (!commerceOrderItems.isEmpty()) {
						_inCart = true;
					}
				}
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			String pathThemeImages = themeDisplay.getPathThemeImages();

			_spritemap = pathThemeImages + "/icons.svg";

			if (pathThemeImages.contains("classic")) {
				_spritemap = pathThemeImages + "/lexicon/icons.svg";
			}

			if (sku != null) {
				_stockQuantity = _commerceInventoryEngine.getStockQuantity(
					PortalUtil.getCompanyId(httpServletRequest),
					commerceContext.getCommerceChannelGroupId(), sku);

				_productSettingsModel = _productHelper.getProductSettingsModel(
					cpSku.getCPInstanceId());

				if (!_disabled) {
					_disabled =
						(!_productSettingsModel.isBackOrders() &&
						 (_stockQuantity <= 0)) ||
						!cpSku.isPublished();
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public boolean getBlock() {
		return _block;
	}

	public CPCatalogEntry getCPCatalogEntry() {
		return _cpCatalogEntry;
	}

	public long getCPInstanceId() {
		return _cpInstanceId;
	}

	public String getNamespace() {
		return _namespace;
	}

	public String getOptions() {
		return _options;
	}

	public String getSpritemap() {
		return _spritemap;
	}

	@Override
	public void setAttributes(HttpServletRequest httpServletRequest) {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setNamespacedAttribute(httpServletRequest, "block", _block);
		setNamespacedAttribute(
			httpServletRequest, "commerceAccountId", _commerceAccountId);
		setNamespacedAttribute(
			httpServletRequest, "commerceChannelGroupId",
			_commerceChannelGroupId);
		setNamespacedAttribute(
			httpServletRequest, "commerceChannelId", _commerceChannelId);
		setNamespacedAttribute(
			httpServletRequest, "commerceCurrencyCode", _commerceCurrencyCode);
		setNamespacedAttribute(
			httpServletRequest, "commerceOrderId", _commerceOrderId);
		setNamespacedAttribute(
			httpServletRequest, "cpInstanceId", _cpInstanceId);
		setNamespacedAttribute(httpServletRequest, "disabled", _disabled);
		setNamespacedAttribute(httpServletRequest, "inCart", _inCart);
		setNamespacedAttribute(httpServletRequest, "namespace", _namespace);
		setNamespacedAttribute(httpServletRequest, "options", _options);
		setNamespacedAttribute(
			httpServletRequest, "productSettingsModel", _productSettingsModel);

		if (Validator.isNull(_spritemap)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_spritemap = themeDisplay.getPathThemeImages() + "/clay/icons.svg";
		}

		setNamespacedAttribute(httpServletRequest, "spritemap", _spritemap);

		setNamespacedAttribute(
			httpServletRequest, "stockQuantity", _stockQuantity);
	}

	public void setBlock(boolean block) {
		_block = block;
	}

	public void setCPCatalogEntry(CPCatalogEntry cpCatalogEntry) {
		_cpCatalogEntry = cpCatalogEntry;
	}

	public void setCPInstanceId(long cpInstanceId) {
		_cpInstanceId = cpInstanceId;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public void setOptions(String options) {
		_options = options;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());

		_commerceOrderHttpHelper =
			ServletContextUtil.getCommerceOrderHttpHelper();
		_commerceInventoryEngine =
			ServletContextUtil.getCommerceInventoryEngine();
		_commerceOrderItemLocalService =
			ServletContextUtil.getCommerceOrderItemLocalService();
		_cpContentHelper = ServletContextUtil.getCPContentHelper();
		_productHelper = ServletContextUtil.getProductHelper();
	}

	public void setSpritemap(String spritemap) {
		_spritemap = spritemap;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_block = false;
		_commerceAccountId = 0;
		_commerceChannelGroupId = 0;
		_commerceChannelId = 0;
		_commerceCurrencyCode = null;
		_commerceInventoryEngine = null;
		_commerceOrderHttpHelper = null;
		_commerceOrderId = 0;
		_commerceOrderItemLocalService = null;
		_cpCatalogEntry = null;
		_cpContentHelper = null;
		_cpInstanceId = 0;
		_disabled = false;
		_inCart = false;
		_namespace = StringPool.BLANK;
		_options = null;
		_productHelper = null;
		_productSettingsModel = null;
		_spritemap = null;
		_stockQuantity = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-commerce:add-to-cart:";

	private static final String _PAGE = "/add_to_cart/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(AddToCartTag.class);

	private boolean _block;
	private long _commerceAccountId;
	private long _commerceChannelGroupId;
	private long _commerceChannelId;
	private String _commerceCurrencyCode;
	private CommerceInventoryEngine _commerceInventoryEngine;
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private long _commerceOrderId;
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;
	private CPCatalogEntry _cpCatalogEntry;
	private CPContentHelper _cpContentHelper;
	private long _cpInstanceId;
	private boolean _disabled;
	private boolean _inCart;
	private String _namespace = StringPool.BLANK;
	private String _options;
	private ProductHelper _productHelper;
	private ProductSettingsModel _productSettingsModel;
	private String _spritemap;
	private int _stockQuantity;

}
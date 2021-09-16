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

package com.liferay.commerce.catalog.web.internal.display.context;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.media.CommerceCatalogDefaultImage;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.configuration.AttachmentsConfiguration;
import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
public class CommerceCatalogDisplayContext {

	public CommerceCatalogDisplayContext(
		AttachmentsConfiguration attachmentsConfiguration,
		HttpServletRequest httpServletRequest,
		CommerceCatalogDefaultImage commerceCatalogDefaultImage,
		CommerceCatalogService commerceCatalogService,
		ModelResourcePermission<CommerceCatalog>
			commerceCatalogModelResourcePermission,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		CommercePriceListService commercePriceListService,
		ConfigurationProvider configurationProvider, DLAppService dlAppService,
		ItemSelector itemSelector, Portal portal) {

		_attachmentsConfiguration = attachmentsConfiguration;
		_commerceCatalogDefaultImage = commerceCatalogDefaultImage;
		_commerceCatalogService = commerceCatalogService;
		_commerceCatalogModelResourcePermission =
			commerceCatalogModelResourcePermission;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
		_commercePriceListService = commercePriceListService;
		_configurationProvider = configurationProvider;
		_dlAppService = dlAppService;
		_itemSelector = itemSelector;
		_portal = portal;

		cpRequestHelper = new CPRequestHelper(httpServletRequest);
	}

	public String getAddCommerceCatalogRenderURL() throws Exception {
		return PortletURLBuilder.createRenderURL(
			cpRequestHelper.getLiferayPortletResponse()
		).setMVCRenderCommandName(
			"/commerce_catalogs/add_commerce_catalog"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public CommercePriceList getBaseCommercePriceList(String type)
		throws PortalException {

		CommerceCatalog commerceCatalog = getCommerceCatalog();

		return _commercePriceListService.
			fetchCatalogBaseCommercePriceListByType(
				commerceCatalog.getGroupId(), type);
	}

	public long getBaseCommercePriceListId(String type) throws PortalException {
		CommercePriceList baseCommercePriceList = getBaseCommercePriceList(
			type);

		if (baseCommercePriceList == null) {
			return 0;
		}

		return baseCommercePriceList.getCommercePriceListId();
	}

	public CommerceCatalog getCommerceCatalog() throws PortalException {
		long commerceCatalogId = ParamUtil.getLong(
			cpRequestHelper.getRequest(), "commerceCatalogId");

		if (commerceCatalogId == 0) {
			return null;
		}

		return _commerceCatalogService.fetchCommerceCatalog(commerceCatalogId);
	}

	public long getCommerceCatalogId() throws PortalException {
		CommerceCatalog commerceCatalog = getCommerceCatalog();

		if (commerceCatalog == null) {
			return 0;
		}

		return commerceCatalog.getCommerceCatalogId();
	}

	public List<CommerceCurrency> getCommerceCurrencies()
		throws PortalException {

		return _commerceCurrencyLocalService.getCommerceCurrencies(
			cpRequestHelper.getCompanyId(), true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (hasAddCatalogPermission()) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(getAddCommerceCatalogRenderURL());
					dropdownItem.setLabel(
						LanguageUtil.get(
							cpRequestHelper.getRequest(), "add-catalog"));
					dropdownItem.setTarget("modal-lg");
				});
		}

		return creationMenu;
	}

	public FileEntry getDefaultFileEntry() throws PortalException {
		long fileEntryId = getDefaultFileEntryId();

		if (fileEntryId == 0) {
			return null;
		}

		return _dlAppService.getFileEntry(fileEntryId);
	}

	public long getDefaultFileEntryId() throws PortalException {
		CommerceCatalog commerceCatalog = getCommerceCatalog();

		return _commerceCatalogDefaultImage.getDefaultCatalogFileEntryId(
			commerceCatalog.getGroupId());
	}

	public String getEditCommerceCatalogActionURL() throws Exception {
		CommerceCatalog commerceCatalog = getCommerceCatalog();

		if (commerceCatalog == null) {
			return StringPool.BLANK;
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				cpRequestHelper.getRequest(), CPPortletKeys.COMMERCE_CATALOGS,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/commerce_catalogs/edit_commerce_catalog"
		).setCMD(
			Constants.UPDATE
		).setParameter(
			"commerceCatalogId", commerceCatalog.getCommerceCatalogId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public PortletURL getEditCommerceCatalogRenderURL() {
		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				cpRequestHelper.getRequest(), CPPortletKeys.COMMERCE_CATALOGS,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/commerce_catalogs/edit_commerce_catalog"
		).buildPortletURL();
	}

	public List<HeaderActionModel> getHeaderActionModels() throws Exception {
		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		RenderResponse renderResponse = cpRequestHelper.getRenderResponse();

		RenderURL cancelURL = renderResponse.createRenderURL();

		HeaderActionModel cancelHeaderActionModel = new HeaderActionModel(
			null, cancelURL.toString(), null, "cancel");

		headerActionModels.add(cancelHeaderActionModel);

		if (hasPermission(getCommerceCatalogId(), ActionKeys.UPDATE)) {
			headerActionModels.add(
				new HeaderActionModel(
					"btn-primary", renderResponse.getNamespace() + "fm",
					getEditCommerceCatalogActionURL(), null, "save"));
		}

		return headerActionModels;
	}

	public String[] getImageExtensions() {
		return _attachmentsConfiguration.imageExtensions();
	}

	public String getImageItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		ImageItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new FileEntryItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "addFileEntry",
			imageItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public long getImageMaxSize() {
		return _attachmentsConfiguration.imageMaxSize();
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			cpRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(
			cpRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String filterFields = ParamUtil.getString(
			cpRequestHelper.getRequest(), "filterFields");

		if (Validator.isNotNull(filterFields)) {
			portletURL.setParameter("filterFields", filterFields);
		}

		String filtersLabels = ParamUtil.getString(
			cpRequestHelper.getRequest(), "filtersLabels");

		if (Validator.isNotNull(filtersLabels)) {
			portletURL.setParameter("filtersLabels", filtersLabels);
		}

		String filtersValues = ParamUtil.getString(
			cpRequestHelper.getRequest(), "filtersValues");

		if (Validator.isNotNull(filtersValues)) {
			portletURL.setParameter("filtersValues", filtersValues);
		}

		return portletURL;
	}

	public String getPriceListsApiUrl(String type) throws PortalException {
		String encodedFilter = URLCodec.encodeURL(
			StringBundler.concat(
				"(catalogId/any(x:(x eq ", getCommerceCatalogId(),
				"))) and type eq '", type, StringPool.APOSTROPHE),
			true);

		return StringBundler.concat(
			_portal.getPortalURL(cpRequestHelper.getRequest()),
			"/o/headless-commerce-admin-pricing/v2.0/price-lists?filter=",
			encodedFilter);
	}

	public boolean hasAddCatalogPermission() {
		PortletResourcePermission portletResourcePermission =
			_commerceCatalogModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			cpRequestHelper.getPermissionChecker(), null,
			CPActionKeys.ADD_COMMERCE_CATALOG);
	}

	public boolean hasPermission(long commerceCatalogId, String actionId)
		throws PortalException {

		return _commerceCatalogModelResourcePermission.contains(
			cpRequestHelper.getPermissionChecker(), commerceCatalogId,
			actionId);
	}

	public boolean showBasePriceListInputs() throws PortalException {
		CommercePricingConfiguration commercePricingConfiguration =
			_configurationProvider.getConfiguration(
				CommercePricingConfiguration.class,
				new SystemSettingsLocator(
					CommercePricingConstants.SERVICE_NAME));

		return Objects.equals(
			commercePricingConfiguration.commercePricingCalculationKey(),
			CommercePricingConstants.VERSION_2_0);
	}

	protected final CPRequestHelper cpRequestHelper;

	private final AttachmentsConfiguration _attachmentsConfiguration;
	private final CommerceCatalogDefaultImage _commerceCatalogDefaultImage;
	private final ModelResourcePermission<CommerceCatalog>
		_commerceCatalogModelResourcePermission;
	private final CommerceCatalogService _commerceCatalogService;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private final CommercePriceListService _commercePriceListService;
	private final ConfigurationProvider _configurationProvider;
	private final DLAppService _dlAppService;
	private final ItemSelector _itemSelector;
	private final Portal _portal;

}
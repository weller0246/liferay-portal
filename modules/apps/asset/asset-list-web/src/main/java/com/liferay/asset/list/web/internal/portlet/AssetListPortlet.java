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

package com.liferay.asset.list.web.internal.portlet;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.exception.AssetListEntryTitleException;
import com.liferay.asset.list.exception.DuplicateAssetListEntryTitleException;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.web.internal.constants.AssetListWebKeys;
import com.liferay.asset.list.web.internal.display.context.AssetListDisplayContext;
import com.liferay.asset.list.web.internal.display.context.AssetListItemsDisplayContext;
import com.liferay.asset.list.web.internal.display.context.EditAssetListDisplayContext;
import com.liferay.asset.list.web.internal.display.context.InfoCollectionProviderDisplayContext;
import com.liferay.asset.list.web.internal.display.context.InfoCollectionProviderItemsDisplayContext;
import com.liferay.asset.list.web.internal.servlet.taglib.util.ListItemsActionDropdownItems;
import com.liferay.asset.util.AssetRendererFactoryClassProvider;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.info.display.url.provider.InfoEditURLProviderTracker;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-asset-list-web",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Asset List",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + AssetListPortletKeys.ASSET_LIST,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class AssetListPortlet extends MVCPortlet {

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			AssetListWebKeys.ASSET_LIST_ITEMS_DISPLAY_CONTEXT,
			new AssetListItemsDisplayContext(
				_assetListAssetEntryProvider, renderRequest, renderResponse));

		AssetListDisplayContext assetListDisplayContext =
			new AssetListDisplayContext(
				_assetRendererFactoryClassProvider, renderRequest,
				renderResponse);

		renderRequest.setAttribute(
			AssetListWebKeys.ASSET_LIST_DISPLAY_CONTEXT,
			assetListDisplayContext);

		renderRequest.setAttribute(AssetListWebKeys.DDM_INDEXER, _ddmIndexer);
		renderRequest.setAttribute(
			AssetListWebKeys.EDIT_ASSET_LIST_DISPLAY_CONTEXT,
			new EditAssetListDisplayContext(
				_assetRendererFactoryClassProvider, _itemSelector,
				renderRequest, renderResponse,
				_getUnicodeProperties(assetListDisplayContext)));

		renderRequest.setAttribute(
			AssetListWebKeys.INFO_COLLECTION_PROVIDER_DISPLAY_CONTEXT,
			new InfoCollectionProviderDisplayContext(
				_infoItemServiceTracker, renderRequest, renderResponse));
		renderRequest.setAttribute(
			AssetListWebKeys.INFO_COLLECTION_PROVIDER_ITEMS_DISPLAY_CONTEXT,
			new InfoCollectionProviderItemsDisplayContext(
				_infoItemServiceTracker, renderRequest, renderResponse));
		renderRequest.setAttribute(
			AssetListWebKeys.LIST_ITEMS_ACTION_DROPDOWN_ITEMS,
			new ListItemsActionDropdownItems(
				_assetDisplayPageFriendlyURLProvider, _dlAppService,
				_infoEditURLProviderTracker, _infoItemServiceTracker,
				_portal.getHttpServletRequest(renderRequest)));

		renderRequest.setAttribute(
			AssetListWebKeys.ITEM_SELECTOR, _itemSelector);

		super.doDispatch(renderRequest, renderResponse);
	}

	@Override
	protected boolean isSessionErrorException(Throwable throwable) {
		if (throwable instanceof AssetListEntryTitleException ||
			throwable instanceof DuplicateAssetListEntryTitleException) {

			return true;
		}

		return super.isSessionErrorException(throwable);
	}

	private UnicodeProperties _getUnicodeProperties(
			AssetListDisplayContext assetListDisplayContext)
		throws IOException {

		AssetListEntry assetListEntry =
			assetListDisplayContext.getAssetListEntry();

		if (assetListEntry == null) {
			return new UnicodeProperties();
		}

		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.load(
			assetListEntry.getTypeSettings(
				assetListDisplayContext.getSegmentsEntryId()));

		return unicodeProperties;
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AssetListAssetEntryProvider _assetListAssetEntryProvider;

	@Reference
	private AssetRendererFactoryClassProvider
		_assetRendererFactoryClassProvider;

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private InfoEditURLProviderTracker _infoEditURLProviderTracker;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

}
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

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.exception.ClientExtensionEntryTypeException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CETCustomElement;
import com.liferay.client.extension.type.CETIFrame;
import com.liferay.client.extension.type.CETThemeCSS;
import com.liferay.client.extension.type.CETThemeFavicon;
import com.liferay.client.extension.type.CETThemeJS;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.client.extension.type.internal.CETCustomElementImpl;
import com.liferay.client.extension.type.internal.CETIFrameImpl;
import com.liferay.client.extension.type.internal.CETThemeCSSImpl;
import com.liferay.client.extension.type.internal.CETThemeFaviconImpl;
import com.liferay.client.extension.type.internal.CETThemeJSImpl;
import com.liferay.portal.kernel.exception.PortalException;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = CETFactory.class)
public class CETFactoryImpl implements CETFactory {

	@Override
	public CETCustomElement customElement(
		ClientExtensionEntry clientExtensionEntry) {

		return new CETCustomElementImpl(clientExtensionEntry);
	}

	@Override
	public CETCustomElement customElement(PortletRequest portletRequest) {
		return new CETCustomElementImpl(portletRequest);
	}

	@Override
	public CETIFrame iFrame(ClientExtensionEntry clientExtensionEntry) {
		return new CETIFrameImpl(clientExtensionEntry);
	}

	@Override
	public CETIFrame iFrame(PortletRequest portletRequest) {
		return new CETIFrameImpl(portletRequest);
	}

	@Override
	public CETThemeCSS themeCSS(ClientExtensionEntry clientExtensionEntry) {
		return new CETThemeCSSImpl(clientExtensionEntry);
	}

	@Override
	public CETThemeCSS themeCSS(PortletRequest portletRequest) {
		return new CETThemeCSSImpl(portletRequest);
	}

	@Override
	public CETThemeFavicon themeFavicon(
		ClientExtensionEntry clientExtensionEntry) {

		return new CETThemeFaviconImpl(clientExtensionEntry);
	}

	@Override
	public CETThemeFavicon themeFavicon(PortletRequest portletRequest) {
		return new CETThemeFaviconImpl(portletRequest);
	}

	@Override
	public CETThemeJS themeJS(ClientExtensionEntry clientExtensionEntry) {
		return new CETThemeJSImpl(clientExtensionEntry);
	}

	@Override
	public CETThemeJS themeJS(PortletRequest portletRequest) {
		return new CETThemeJSImpl(portletRequest);
	}

	@Override
	public String typeSettings(PortletRequest portletRequest, String type)
		throws PortalException {

		if (type.equals(ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT)) {
			return String.valueOf(customElement(portletRequest));
		}
		else if (type.equals(ClientExtensionEntryConstants.TYPE_IFRAME)) {
			return String.valueOf(iFrame(portletRequest));
		}
		else if (type.equals(ClientExtensionEntryConstants.TYPE_THEME_CSS)) {
			return String.valueOf(themeCSS(portletRequest));
		}
		else if (type.equals(
					ClientExtensionEntryConstants.TYPE_THEME_FAVICON)) {

			return String.valueOf(themeFavicon(portletRequest));
		}
		else if (type.equals(ClientExtensionEntryConstants.TYPE_THEME_JS)) {
			return String.valueOf(themeJS(portletRequest));
		}

		throw new ClientExtensionEntryTypeException("Invalid type " + type);
	}

}
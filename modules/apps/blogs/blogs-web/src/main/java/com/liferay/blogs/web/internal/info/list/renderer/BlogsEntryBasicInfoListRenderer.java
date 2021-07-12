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

package com.liferay.blogs.web.internal.info.list.renderer;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.web.internal.info.item.renderer.BlogsEntryAbstractInfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
import com.liferay.info.list.renderer.DefaultInfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRendererContext;
import com.liferay.info.taglib.list.renderer.BasicInfoListRenderer;
import com.liferay.info.taglib.servlet.taglib.InfoListBasicListTag;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
public abstract class BlogsEntryBasicInfoListRenderer
	implements BasicInfoListRenderer<BlogsEntry> {

	@Override
	public List<InfoItemRenderer<?>> getAvailableInfoItemRenderers() {
		return infoItemRendererTracker.getInfoItemRenderers(
			BlogsEntry.class.getName());
	}

	@Override
	public void render(
		List<BlogsEntry> blogEntries, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		render(
			blogEntries,
			new DefaultInfoListRendererContext(
				httpServletRequest, httpServletResponse));
	}

	@Override
	public void render(
		List<BlogsEntry> blogEntries,
		InfoListRendererContext infoListRendererContext) {

		InfoListBasicListTag infoListBasicListTag = new InfoListBasicListTag();

		infoListBasicListTag.setInfoListObjects(blogEntries);

		Optional<String> infoListItemRendererKeyOptional =
			infoListRendererContext.getListItemRendererKeyOptional();

		if (infoListItemRendererKeyOptional.isPresent() &&
			Validator.isNotNull(infoListItemRendererKeyOptional.get())) {

			infoListBasicListTag.setItemRendererKey(
				infoListItemRendererKeyOptional.get());
		}
		else {
			infoListBasicListTag.setItemRendererKey(
				BlogsEntryAbstractInfoItemRenderer.class.getName());
		}

		infoListBasicListTag.setListStyleKey(getListStyle());

		try {
			infoListBasicListTag.doTag(
				infoListRendererContext.getHttpServletRequest(),
				infoListRendererContext.getHttpServletResponse());
		}
		catch (Exception exception) {
			_log.error("Unable to render blog entries list", exception);
		}
	}

	@Reference
	protected InfoItemRendererTracker infoItemRendererTracker;

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryBasicInfoListRenderer.class);

}
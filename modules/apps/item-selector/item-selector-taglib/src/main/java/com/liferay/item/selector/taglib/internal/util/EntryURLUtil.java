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

package com.liferay.item.selector.taglib.internal.util;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.taglib.internal.servlet.item.selector.ItemSelectorUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

/**
 * @author Adolfo Pérez
 */
public class EntryURLUtil {

	public static PortletURL getFolderPortletURL(
			Folder folder, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			PortletURL portletURL)
		throws PortalException, PortletException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (folder.getGroupId() != group.getGroupId()) {
			group = GroupServiceUtil.getGroup(folder.getGroupId());
		}

		PortletURL folderPortletURL = null;

		String scope = ParamUtil.getString(liferayPortletRequest, "scope");

		if (Objects.equals(scope, "everywhere")) {
			folderPortletURL = getGroupPortletURL(group, liferayPortletRequest);
		}
		else {
			folderPortletURL = PortletURLUtil.clone(
				portletURL, liferayPortletResponse);
		}

		folderPortletURL.setParameter(
			"folderId", String.valueOf(folder.getFolderId()));

		return folderPortletURL;
	}

	public static PortletURL getGroupPortletURL(
		Group group, LiferayPortletRequest liferayPortletRequest) {

		ItemSelector itemSelector = ItemSelectorUtil.getItemSelector();

		String itemSelectedEventName = ParamUtil.getString(
			liferayPortletRequest, "itemSelectedEventName");

		List<ItemSelectorCriterion> itemSelectorCriteria =
			itemSelector.getItemSelectorCriteria(
				liferayPortletRequest.getParameterMap());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long refererGroupId = themeDisplay.getRefererGroupId();

		if (refererGroupId == 0) {
			refererGroupId = themeDisplay.getScopeGroupId();
		}

		return PortletURLBuilder.create(
			itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(
					liferayPortletRequest),
				group, refererGroupId, itemSelectedEventName,
				itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]))
		).setParameter(
			"selectedTab",
			ParamUtil.getString(liferayPortletRequest, "selectedTab")
		).buildPortletURL();
	}

}
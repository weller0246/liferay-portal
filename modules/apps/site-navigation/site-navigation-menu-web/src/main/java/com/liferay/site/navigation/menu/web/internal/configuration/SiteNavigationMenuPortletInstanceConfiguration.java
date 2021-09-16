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

package com.liferay.site.navigation.menu.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Juergen Kappler
 */
@ExtendedObjectClassDefinition(
	category = "navigation",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.site.navigation.menu.web.internal.configuration.SiteNavigationMenuPortletInstanceConfiguration",
	localization = "content/Language",
	name = "site-navigation-menu-portlet-instance-configuration-name"
)
public interface SiteNavigationMenuPortletInstanceConfiguration {

	@Meta.AD(name = "site-navigation-menu-id", required = false)
	public long siteNavigationMenuId();

	@Meta.AD(name = "site-navigation-menu-name", required = false)
	public String siteNavigationMenuName();

	@Meta.AD(deflt = "0", name = "display-style-group-id", required = false)
	public long displayStyleGroupId();

	@Meta.AD(name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(deflt = "absolute", name = "root-menu-item-type", required = false)
	public String rootMenuItemType();

	@Meta.AD(deflt = "0", name = "root-menu-item-level", required = false)
	public int rootMenuItemLevel();

	@Meta.AD(name = "root-menu-item-id", required = false)
	public String rootMenuItemId();

	@Meta.AD(deflt = "-1", name = "site-navigation-menu-type", required = false)
	public int siteNavigationMenuType();

	@Meta.AD(deflt = "0", name = "display-depth", required = false)
	public int displayDepth();

	@Meta.AD(deflt = "auto", name = "expand-sublevels", required = false)
	public String expandedLevels();

	@Meta.AD(deflt = "preview", name = "preview", required = false)
	public boolean preview();

}
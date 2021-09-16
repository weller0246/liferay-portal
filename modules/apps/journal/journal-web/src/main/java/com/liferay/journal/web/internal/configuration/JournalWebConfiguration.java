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

package com.liferay.journal.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jürgen Kappler
 */
@ExtendedObjectClassDefinition(category = "web-content")
@Meta.OCD(
	id = "com.liferay.journal.web.internal.configuration.JournalWebConfiguration",
	localization = "content/Language", name = "journal-web-configuration-name"
)
public interface JournalWebConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "if-checked,-the-default-language-of-web-content-articles-will-be-changeable",
		name = "changeable-default-language", required = false
	)
	public boolean changeableDefaultLanguage();

	@Meta.AD(
		deflt = "true", description = "structure-field-indexable-enable-help",
		name = "structure-field-indexable-enable", required = false
	)
	public boolean structureFieldIndexableEnable();

	@Meta.AD(
		deflt = "descriptive", name = "default-display-view", required = false
	)
	public String defaultDisplayView();

	@Meta.AD(
		deflt = "icon|descriptive|list", name = "display-views",
		required = false
	)
	public String[] displayViews();

	@Meta.AD(
		deflt = "false", name = "journal-article-show-id", required = false
	)
	public boolean journalArticleShowId();

	@Meta.AD(
		deflt = "true", name = "journal-article-force-autogenerate-id",
		required = false
	)
	public boolean journalArticleForceAutogenerateId();

	@Meta.AD(
		deflt = "true",
		description = "check-this-if-structure-keys-should-always-be-autogenerated",
		name = "autogenerate-structure-key", required = false
	)
	public boolean autogenerateDDMStructureKey();

	@Meta.AD(
		deflt = "true",
		description = "check-this-if-template-keys-should-always-be-autogenerated",
		name = "autogenerate-template-key", required = false
	)
	public boolean autogenerateDDMTemplateKey();

	@Meta.AD(
		deflt = "true",
		description = "journal-browse-by-structures-sorted-by-name-help",
		name = "journal-browse-by-structures-sorted-by-name", required = false
	)
	public boolean journalBrowseByStructuresSortedByName();

	@Meta.AD(
		deflt = "ftl", description = "template-language-types-help",
		name = "template-language-types", required = false
	)
	public String[] journalDDMTemplateLanguageTypes();

	@Meta.AD(
		deflt = "true", name = "journal-feed-force-autogenerate-id",
		required = false
	)
	public boolean journalFeedForceAutogenerateId();

	@Meta.AD(deflt = "7", name = "max-add-menu-items", required = false)
	public int maxAddMenuItems();

	@Meta.AD(
		deflt = "false", name = "show-ancestor-scopes-by-default",
		required = false
	)
	public boolean showAncestorScopesByDefault();

	@Meta.AD(deflt = "false", name = "show-feeds", required = false)
	public boolean showFeeds();

}
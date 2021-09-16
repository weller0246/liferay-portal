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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.LocaleException;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the LayoutPrototype service. Represents a row in the &quot;LayoutPrototype&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.portal.model.impl.LayoutPrototypeModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.portal.model.impl.LayoutPrototypeImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPrototype
 * @generated
 */
@ProviderType
public interface LayoutPrototypeModel
	extends BaseModel<LayoutPrototype>, LocalizedModel, MVCCModel, ShardedModel,
			StagedAuditedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a layout prototype model instance should use the {@link LayoutPrototype} interface instead.
	 */

	/**
	 * Returns the primary key of this layout prototype.
	 *
	 * @return the primary key of this layout prototype
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this layout prototype.
	 *
	 * @param primaryKey the primary key of this layout prototype
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this layout prototype.
	 *
	 * @return the mvcc version of this layout prototype
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this layout prototype.
	 *
	 * @param mvccVersion the mvcc version of this layout prototype
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the uuid of this layout prototype.
	 *
	 * @return the uuid of this layout prototype
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this layout prototype.
	 *
	 * @param uuid the uuid of this layout prototype
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the layout prototype ID of this layout prototype.
	 *
	 * @return the layout prototype ID of this layout prototype
	 */
	public long getLayoutPrototypeId();

	/**
	 * Sets the layout prototype ID of this layout prototype.
	 *
	 * @param layoutPrototypeId the layout prototype ID of this layout prototype
	 */
	public void setLayoutPrototypeId(long layoutPrototypeId);

	/**
	 * Returns the company ID of this layout prototype.
	 *
	 * @return the company ID of this layout prototype
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this layout prototype.
	 *
	 * @param companyId the company ID of this layout prototype
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this layout prototype.
	 *
	 * @return the user ID of this layout prototype
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this layout prototype.
	 *
	 * @param userId the user ID of this layout prototype
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this layout prototype.
	 *
	 * @return the user uuid of this layout prototype
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this layout prototype.
	 *
	 * @param userUuid the user uuid of this layout prototype
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this layout prototype.
	 *
	 * @return the user name of this layout prototype
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this layout prototype.
	 *
	 * @param userName the user name of this layout prototype
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this layout prototype.
	 *
	 * @return the create date of this layout prototype
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this layout prototype.
	 *
	 * @param createDate the create date of this layout prototype
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this layout prototype.
	 *
	 * @return the modified date of this layout prototype
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this layout prototype.
	 *
	 * @param modifiedDate the modified date of this layout prototype
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the name of this layout prototype.
	 *
	 * @return the name of this layout prototype
	 */
	public String getName();

	/**
	 * Returns the localized name of this layout prototype in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this layout prototype
	 */
	@AutoEscape
	public String getName(Locale locale);

	/**
	 * Returns the localized name of this layout prototype in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this layout prototype. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getName(Locale locale, boolean useDefault);

	/**
	 * Returns the localized name of this layout prototype in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this layout prototype
	 */
	@AutoEscape
	public String getName(String languageId);

	/**
	 * Returns the localized name of this layout prototype in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this layout prototype
	 */
	@AutoEscape
	public String getName(String languageId, boolean useDefault);

	@AutoEscape
	public String getNameCurrentLanguageId();

	@AutoEscape
	public String getNameCurrentValue();

	/**
	 * Returns a map of the locales and localized names of this layout prototype.
	 *
	 * @return the locales and localized names of this layout prototype
	 */
	public Map<Locale, String> getNameMap();

	/**
	 * Sets the name of this layout prototype.
	 *
	 * @param name the name of this layout prototype
	 */
	public void setName(String name);

	/**
	 * Sets the localized name of this layout prototype in the language.
	 *
	 * @param name the localized name of this layout prototype
	 * @param locale the locale of the language
	 */
	public void setName(String name, Locale locale);

	/**
	 * Sets the localized name of this layout prototype in the language, and sets the default locale.
	 *
	 * @param name the localized name of this layout prototype
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameCurrentLanguageId(String languageId);

	/**
	 * Sets the localized names of this layout prototype from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this layout prototype
	 */
	public void setNameMap(Map<Locale, String> nameMap);

	/**
	 * Sets the localized names of this layout prototype from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this layout prototype
	 * @param defaultLocale the default locale
	 */
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	/**
	 * Returns the description of this layout prototype.
	 *
	 * @return the description of this layout prototype
	 */
	public String getDescription();

	/**
	 * Returns the localized description of this layout prototype in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this layout prototype
	 */
	@AutoEscape
	public String getDescription(Locale locale);

	/**
	 * Returns the localized description of this layout prototype in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this layout prototype. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getDescription(Locale locale, boolean useDefault);

	/**
	 * Returns the localized description of this layout prototype in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this layout prototype
	 */
	@AutoEscape
	public String getDescription(String languageId);

	/**
	 * Returns the localized description of this layout prototype in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this layout prototype
	 */
	@AutoEscape
	public String getDescription(String languageId, boolean useDefault);

	@AutoEscape
	public String getDescriptionCurrentLanguageId();

	@AutoEscape
	public String getDescriptionCurrentValue();

	/**
	 * Returns a map of the locales and localized descriptions of this layout prototype.
	 *
	 * @return the locales and localized descriptions of this layout prototype
	 */
	public Map<Locale, String> getDescriptionMap();

	/**
	 * Sets the description of this layout prototype.
	 *
	 * @param description the description of this layout prototype
	 */
	public void setDescription(String description);

	/**
	 * Sets the localized description of this layout prototype in the language.
	 *
	 * @param description the localized description of this layout prototype
	 * @param locale the locale of the language
	 */
	public void setDescription(String description, Locale locale);

	/**
	 * Sets the localized description of this layout prototype in the language, and sets the default locale.
	 *
	 * @param description the localized description of this layout prototype
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setDescription(
		String description, Locale locale, Locale defaultLocale);

	public void setDescriptionCurrentLanguageId(String languageId);

	/**
	 * Sets the localized descriptions of this layout prototype from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this layout prototype
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap);

	/**
	 * Sets the localized descriptions of this layout prototype from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this layout prototype
	 * @param defaultLocale the default locale
	 */
	public void setDescriptionMap(
		Map<Locale, String> descriptionMap, Locale defaultLocale);

	/**
	 * Returns the settings of this layout prototype.
	 *
	 * @return the settings of this layout prototype
	 */
	@AutoEscape
	public String getSettings();

	/**
	 * Sets the settings of this layout prototype.
	 *
	 * @param settings the settings of this layout prototype
	 */
	public void setSettings(String settings);

	/**
	 * Returns the active of this layout prototype.
	 *
	 * @return the active of this layout prototype
	 */
	public boolean getActive();

	/**
	 * Returns <code>true</code> if this layout prototype is active.
	 *
	 * @return <code>true</code> if this layout prototype is active; <code>false</code> otherwise
	 */
	public boolean isActive();

	/**
	 * Sets whether this layout prototype is active.
	 *
	 * @param active the active of this layout prototype
	 */
	public void setActive(boolean active);

	@Override
	public String[] getAvailableLanguageIds();

	@Override
	public String getDefaultLanguageId();

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException;

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

	@Override
	public LayoutPrototype cloneWithOriginalValues();

}
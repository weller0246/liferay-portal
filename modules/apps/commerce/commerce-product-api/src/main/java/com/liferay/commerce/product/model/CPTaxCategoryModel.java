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

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.LocalizedModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the CPTaxCategory service. Represents a row in the &quot;CPTaxCategory&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.commerce.product.model.impl.CPTaxCategoryModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.commerce.product.model.impl.CPTaxCategoryImpl</code>.
 * </p>
 *
 * @author Marco Leo
 * @see CPTaxCategory
 * @generated
 */
@ProviderType
public interface CPTaxCategoryModel
	extends AuditedModel, BaseModel<CPTaxCategory>, LocalizedModel,
			ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a cp tax category model instance should use the {@link CPTaxCategory} interface instead.
	 */

	/**
	 * Returns the primary key of this cp tax category.
	 *
	 * @return the primary key of this cp tax category
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this cp tax category.
	 *
	 * @param primaryKey the primary key of this cp tax category
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the external reference code of this cp tax category.
	 *
	 * @return the external reference code of this cp tax category
	 */
	@AutoEscape
	public String getExternalReferenceCode();

	/**
	 * Sets the external reference code of this cp tax category.
	 *
	 * @param externalReferenceCode the external reference code of this cp tax category
	 */
	public void setExternalReferenceCode(String externalReferenceCode);

	/**
	 * Returns the cp tax category ID of this cp tax category.
	 *
	 * @return the cp tax category ID of this cp tax category
	 */
	public long getCPTaxCategoryId();

	/**
	 * Sets the cp tax category ID of this cp tax category.
	 *
	 * @param CPTaxCategoryId the cp tax category ID of this cp tax category
	 */
	public void setCPTaxCategoryId(long CPTaxCategoryId);

	/**
	 * Returns the company ID of this cp tax category.
	 *
	 * @return the company ID of this cp tax category
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this cp tax category.
	 *
	 * @param companyId the company ID of this cp tax category
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this cp tax category.
	 *
	 * @return the user ID of this cp tax category
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this cp tax category.
	 *
	 * @param userId the user ID of this cp tax category
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this cp tax category.
	 *
	 * @return the user uuid of this cp tax category
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this cp tax category.
	 *
	 * @param userUuid the user uuid of this cp tax category
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this cp tax category.
	 *
	 * @return the user name of this cp tax category
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this cp tax category.
	 *
	 * @param userName the user name of this cp tax category
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this cp tax category.
	 *
	 * @return the create date of this cp tax category
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this cp tax category.
	 *
	 * @param createDate the create date of this cp tax category
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this cp tax category.
	 *
	 * @return the modified date of this cp tax category
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this cp tax category.
	 *
	 * @param modifiedDate the modified date of this cp tax category
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the name of this cp tax category.
	 *
	 * @return the name of this cp tax category
	 */
	public String getName();

	/**
	 * Returns the localized name of this cp tax category in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this cp tax category
	 */
	@AutoEscape
	public String getName(Locale locale);

	/**
	 * Returns the localized name of this cp tax category in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this cp tax category. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getName(Locale locale, boolean useDefault);

	/**
	 * Returns the localized name of this cp tax category in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this cp tax category
	 */
	@AutoEscape
	public String getName(String languageId);

	/**
	 * Returns the localized name of this cp tax category in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this cp tax category
	 */
	@AutoEscape
	public String getName(String languageId, boolean useDefault);

	@AutoEscape
	public String getNameCurrentLanguageId();

	@AutoEscape
	public String getNameCurrentValue();

	/**
	 * Returns a map of the locales and localized names of this cp tax category.
	 *
	 * @return the locales and localized names of this cp tax category
	 */
	public Map<Locale, String> getNameMap();

	/**
	 * Sets the name of this cp tax category.
	 *
	 * @param name the name of this cp tax category
	 */
	public void setName(String name);

	/**
	 * Sets the localized name of this cp tax category in the language.
	 *
	 * @param name the localized name of this cp tax category
	 * @param locale the locale of the language
	 */
	public void setName(String name, Locale locale);

	/**
	 * Sets the localized name of this cp tax category in the language, and sets the default locale.
	 *
	 * @param name the localized name of this cp tax category
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameCurrentLanguageId(String languageId);

	/**
	 * Sets the localized names of this cp tax category from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this cp tax category
	 */
	public void setNameMap(Map<Locale, String> nameMap);

	/**
	 * Sets the localized names of this cp tax category from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this cp tax category
	 * @param defaultLocale the default locale
	 */
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	/**
	 * Returns the description of this cp tax category.
	 *
	 * @return the description of this cp tax category
	 */
	public String getDescription();

	/**
	 * Returns the localized description of this cp tax category in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this cp tax category
	 */
	@AutoEscape
	public String getDescription(Locale locale);

	/**
	 * Returns the localized description of this cp tax category in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this cp tax category. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getDescription(Locale locale, boolean useDefault);

	/**
	 * Returns the localized description of this cp tax category in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this cp tax category
	 */
	@AutoEscape
	public String getDescription(String languageId);

	/**
	 * Returns the localized description of this cp tax category in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this cp tax category
	 */
	@AutoEscape
	public String getDescription(String languageId, boolean useDefault);

	@AutoEscape
	public String getDescriptionCurrentLanguageId();

	@AutoEscape
	public String getDescriptionCurrentValue();

	/**
	 * Returns a map of the locales and localized descriptions of this cp tax category.
	 *
	 * @return the locales and localized descriptions of this cp tax category
	 */
	public Map<Locale, String> getDescriptionMap();

	/**
	 * Sets the description of this cp tax category.
	 *
	 * @param description the description of this cp tax category
	 */
	public void setDescription(String description);

	/**
	 * Sets the localized description of this cp tax category in the language.
	 *
	 * @param description the localized description of this cp tax category
	 * @param locale the locale of the language
	 */
	public void setDescription(String description, Locale locale);

	/**
	 * Sets the localized description of this cp tax category in the language, and sets the default locale.
	 *
	 * @param description the localized description of this cp tax category
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setDescription(
		String description, Locale locale, Locale defaultLocale);

	public void setDescriptionCurrentLanguageId(String languageId);

	/**
	 * Sets the localized descriptions of this cp tax category from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this cp tax category
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap);

	/**
	 * Sets the localized descriptions of this cp tax category from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this cp tax category
	 * @param defaultLocale the default locale
	 */
	public void setDescriptionMap(
		Map<Locale, String> descriptionMap, Locale defaultLocale);

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
	public CPTaxCategory cloneWithOriginalValues();

}
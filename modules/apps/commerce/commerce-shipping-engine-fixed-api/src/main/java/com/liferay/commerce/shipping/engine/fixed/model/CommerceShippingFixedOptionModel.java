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

package com.liferay.commerce.shipping.engine.fixed.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.LocalizedModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the CommerceShippingFixedOption service. Represents a row in the &quot;CommerceShippingFixedOption&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionImpl</code>.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOption
 * @generated
 */
@ProviderType
public interface CommerceShippingFixedOptionModel
	extends BaseModel<CommerceShippingFixedOption>, GroupedModel,
			LocalizedModel, ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a commerce shipping fixed option model instance should use the {@link CommerceShippingFixedOption} interface instead.
	 */

	/**
	 * Returns the primary key of this commerce shipping fixed option.
	 *
	 * @return the primary key of this commerce shipping fixed option
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this commerce shipping fixed option.
	 *
	 * @param primaryKey the primary key of this commerce shipping fixed option
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the commerce shipping fixed option ID of this commerce shipping fixed option.
	 *
	 * @return the commerce shipping fixed option ID of this commerce shipping fixed option
	 */
	public long getCommerceShippingFixedOptionId();

	/**
	 * Sets the commerce shipping fixed option ID of this commerce shipping fixed option.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID of this commerce shipping fixed option
	 */
	public void setCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId);

	/**
	 * Returns the group ID of this commerce shipping fixed option.
	 *
	 * @return the group ID of this commerce shipping fixed option
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this commerce shipping fixed option.
	 *
	 * @param groupId the group ID of this commerce shipping fixed option
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this commerce shipping fixed option.
	 *
	 * @return the company ID of this commerce shipping fixed option
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this commerce shipping fixed option.
	 *
	 * @param companyId the company ID of this commerce shipping fixed option
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this commerce shipping fixed option.
	 *
	 * @return the user ID of this commerce shipping fixed option
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this commerce shipping fixed option.
	 *
	 * @param userId the user ID of this commerce shipping fixed option
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this commerce shipping fixed option.
	 *
	 * @return the user uuid of this commerce shipping fixed option
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this commerce shipping fixed option.
	 *
	 * @param userUuid the user uuid of this commerce shipping fixed option
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this commerce shipping fixed option.
	 *
	 * @return the user name of this commerce shipping fixed option
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this commerce shipping fixed option.
	 *
	 * @param userName the user name of this commerce shipping fixed option
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this commerce shipping fixed option.
	 *
	 * @return the create date of this commerce shipping fixed option
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this commerce shipping fixed option.
	 *
	 * @param createDate the create date of this commerce shipping fixed option
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this commerce shipping fixed option.
	 *
	 * @return the modified date of this commerce shipping fixed option
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this commerce shipping fixed option.
	 *
	 * @param modifiedDate the modified date of this commerce shipping fixed option
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the commerce shipping method ID of this commerce shipping fixed option.
	 *
	 * @return the commerce shipping method ID of this commerce shipping fixed option
	 */
	public long getCommerceShippingMethodId();

	/**
	 * Sets the commerce shipping method ID of this commerce shipping fixed option.
	 *
	 * @param commerceShippingMethodId the commerce shipping method ID of this commerce shipping fixed option
	 */
	public void setCommerceShippingMethodId(long commerceShippingMethodId);

	/**
	 * Returns the name of this commerce shipping fixed option.
	 *
	 * @return the name of this commerce shipping fixed option
	 */
	public String getName();

	/**
	 * Returns the localized name of this commerce shipping fixed option in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this commerce shipping fixed option
	 */
	@AutoEscape
	public String getName(Locale locale);

	/**
	 * Returns the localized name of this commerce shipping fixed option in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this commerce shipping fixed option. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getName(Locale locale, boolean useDefault);

	/**
	 * Returns the localized name of this commerce shipping fixed option in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this commerce shipping fixed option
	 */
	@AutoEscape
	public String getName(String languageId);

	/**
	 * Returns the localized name of this commerce shipping fixed option in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this commerce shipping fixed option
	 */
	@AutoEscape
	public String getName(String languageId, boolean useDefault);

	@AutoEscape
	public String getNameCurrentLanguageId();

	@AutoEscape
	public String getNameCurrentValue();

	/**
	 * Returns a map of the locales and localized names of this commerce shipping fixed option.
	 *
	 * @return the locales and localized names of this commerce shipping fixed option
	 */
	public Map<Locale, String> getNameMap();

	/**
	 * Sets the name of this commerce shipping fixed option.
	 *
	 * @param name the name of this commerce shipping fixed option
	 */
	public void setName(String name);

	/**
	 * Sets the localized name of this commerce shipping fixed option in the language.
	 *
	 * @param name the localized name of this commerce shipping fixed option
	 * @param locale the locale of the language
	 */
	public void setName(String name, Locale locale);

	/**
	 * Sets the localized name of this commerce shipping fixed option in the language, and sets the default locale.
	 *
	 * @param name the localized name of this commerce shipping fixed option
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameCurrentLanguageId(String languageId);

	/**
	 * Sets the localized names of this commerce shipping fixed option from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this commerce shipping fixed option
	 */
	public void setNameMap(Map<Locale, String> nameMap);

	/**
	 * Sets the localized names of this commerce shipping fixed option from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this commerce shipping fixed option
	 * @param defaultLocale the default locale
	 */
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	/**
	 * Returns the description of this commerce shipping fixed option.
	 *
	 * @return the description of this commerce shipping fixed option
	 */
	public String getDescription();

	/**
	 * Returns the localized description of this commerce shipping fixed option in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this commerce shipping fixed option
	 */
	@AutoEscape
	public String getDescription(Locale locale);

	/**
	 * Returns the localized description of this commerce shipping fixed option in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this commerce shipping fixed option. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getDescription(Locale locale, boolean useDefault);

	/**
	 * Returns the localized description of this commerce shipping fixed option in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this commerce shipping fixed option
	 */
	@AutoEscape
	public String getDescription(String languageId);

	/**
	 * Returns the localized description of this commerce shipping fixed option in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this commerce shipping fixed option
	 */
	@AutoEscape
	public String getDescription(String languageId, boolean useDefault);

	@AutoEscape
	public String getDescriptionCurrentLanguageId();

	@AutoEscape
	public String getDescriptionCurrentValue();

	/**
	 * Returns a map of the locales and localized descriptions of this commerce shipping fixed option.
	 *
	 * @return the locales and localized descriptions of this commerce shipping fixed option
	 */
	public Map<Locale, String> getDescriptionMap();

	/**
	 * Sets the description of this commerce shipping fixed option.
	 *
	 * @param description the description of this commerce shipping fixed option
	 */
	public void setDescription(String description);

	/**
	 * Sets the localized description of this commerce shipping fixed option in the language.
	 *
	 * @param description the localized description of this commerce shipping fixed option
	 * @param locale the locale of the language
	 */
	public void setDescription(String description, Locale locale);

	/**
	 * Sets the localized description of this commerce shipping fixed option in the language, and sets the default locale.
	 *
	 * @param description the localized description of this commerce shipping fixed option
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setDescription(
		String description, Locale locale, Locale defaultLocale);

	public void setDescriptionCurrentLanguageId(String languageId);

	/**
	 * Sets the localized descriptions of this commerce shipping fixed option from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this commerce shipping fixed option
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap);

	/**
	 * Sets the localized descriptions of this commerce shipping fixed option from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this commerce shipping fixed option
	 * @param defaultLocale the default locale
	 */
	public void setDescriptionMap(
		Map<Locale, String> descriptionMap, Locale defaultLocale);

	/**
	 * Returns the amount of this commerce shipping fixed option.
	 *
	 * @return the amount of this commerce shipping fixed option
	 */
	public BigDecimal getAmount();

	/**
	 * Sets the amount of this commerce shipping fixed option.
	 *
	 * @param amount the amount of this commerce shipping fixed option
	 */
	public void setAmount(BigDecimal amount);

	/**
	 * Returns the priority of this commerce shipping fixed option.
	 *
	 * @return the priority of this commerce shipping fixed option
	 */
	public double getPriority();

	/**
	 * Sets the priority of this commerce shipping fixed option.
	 *
	 * @param priority the priority of this commerce shipping fixed option
	 */
	public void setPriority(double priority);

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
	public CommerceShippingFixedOption cloneWithOriginalValues();

}
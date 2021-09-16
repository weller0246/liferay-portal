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

package com.liferay.commerce.payment.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommercePaymentMethodGroupRel}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRel
 * @generated
 */
public class CommercePaymentMethodGroupRelWrapper
	extends BaseModelWrapper<CommercePaymentMethodGroupRel>
	implements CommercePaymentMethodGroupRel,
			   ModelWrapper<CommercePaymentMethodGroupRel> {

	public CommercePaymentMethodGroupRelWrapper(
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel) {

		super(commercePaymentMethodGroupRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"commercePaymentMethodGroupRelId",
			getCommercePaymentMethodGroupRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("imageId", getImageId());
		attributes.put("engineKey", getEngineKey());
		attributes.put("priority", getPriority());
		attributes.put("active", isActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commercePaymentMethodGroupRelId = (Long)attributes.get(
			"commercePaymentMethodGroupRelId");

		if (commercePaymentMethodGroupRelId != null) {
			setCommercePaymentMethodGroupRelId(commercePaymentMethodGroupRelId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Long imageId = (Long)attributes.get("imageId");

		if (imageId != null) {
			setImageId(imageId);
		}

		String engineKey = (String)attributes.get("engineKey");

		if (engineKey != null) {
			setEngineKey(engineKey);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	@Override
	public CommercePaymentMethodGroupRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this commerce payment method group rel.
	 *
	 * @return the active of this commerce payment method group rel
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the commerce payment method group rel ID of this commerce payment method group rel.
	 *
	 * @return the commerce payment method group rel ID of this commerce payment method group rel
	 */
	@Override
	public long getCommercePaymentMethodGroupRelId() {
		return model.getCommercePaymentMethodGroupRelId();
	}

	/**
	 * Returns the company ID of this commerce payment method group rel.
	 *
	 * @return the company ID of this commerce payment method group rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce payment method group rel.
	 *
	 * @return the create date of this commerce payment method group rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the description of this commerce payment method group rel.
	 *
	 * @return the description of this commerce payment method group rel
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the localized description of this commerce payment method group rel in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this commerce payment method group rel
	 */
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	 * Returns the localized description of this commerce payment method group rel in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this commerce payment method group rel. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	 * Returns the localized description of this commerce payment method group rel in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this commerce payment method group rel
	 */
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	 * Returns the localized description of this commerce payment method group rel in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this commerce payment method group rel
	 */
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return model.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return model.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return model.getDescriptionCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized descriptions of this commerce payment method group rel.
	 *
	 * @return the locales and localized descriptions of this commerce payment method group rel
	 */
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	 * Returns the engine key of this commerce payment method group rel.
	 *
	 * @return the engine key of this commerce payment method group rel
	 */
	@Override
	public String getEngineKey() {
		return model.getEngineKey();
	}

	/**
	 * Returns the group ID of this commerce payment method group rel.
	 *
	 * @return the group ID of this commerce payment method group rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the image ID of this commerce payment method group rel.
	 *
	 * @return the image ID of this commerce payment method group rel
	 */
	@Override
	public long getImageId() {
		return model.getImageId();
	}

	@Override
	public String getImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {

		return model.getImageURL(themeDisplay);
	}

	/**
	 * Returns the modified date of this commerce payment method group rel.
	 *
	 * @return the modified date of this commerce payment method group rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this commerce payment method group rel.
	 *
	 * @return the name of this commerce payment method group rel
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this commerce payment method group rel in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this commerce payment method group rel
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this commerce payment method group rel in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this commerce payment method group rel. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this commerce payment method group rel in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this commerce payment method group rel
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this commerce payment method group rel in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this commerce payment method group rel
	 */
	@Override
	public String getName(String languageId, boolean useDefault) {
		return model.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return model.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return model.getNameCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized names of this commerce payment method group rel.
	 *
	 * @return the locales and localized names of this commerce payment method group rel
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the primary key of this commerce payment method group rel.
	 *
	 * @return the primary key of this commerce payment method group rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this commerce payment method group rel.
	 *
	 * @return the priority of this commerce payment method group rel
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the user ID of this commerce payment method group rel.
	 *
	 * @return the user ID of this commerce payment method group rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce payment method group rel.
	 *
	 * @return the user name of this commerce payment method group rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce payment method group rel.
	 *
	 * @return the user uuid of this commerce payment method group rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce payment method group rel is active.
	 *
	 * @return <code>true</code> if this commerce payment method group rel is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	 * Sets whether this commerce payment method group rel is active.
	 *
	 * @param active the active of this commerce payment method group rel
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the commerce payment method group rel ID of this commerce payment method group rel.
	 *
	 * @param commercePaymentMethodGroupRelId the commerce payment method group rel ID of this commerce payment method group rel
	 */
	@Override
	public void setCommercePaymentMethodGroupRelId(
		long commercePaymentMethodGroupRelId) {

		model.setCommercePaymentMethodGroupRelId(
			commercePaymentMethodGroupRelId);
	}

	/**
	 * Sets the company ID of this commerce payment method group rel.
	 *
	 * @param companyId the company ID of this commerce payment method group rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce payment method group rel.
	 *
	 * @param createDate the create date of this commerce payment method group rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this commerce payment method group rel.
	 *
	 * @param description the description of this commerce payment method group rel
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the localized description of this commerce payment method group rel in the language.
	 *
	 * @param description the localized description of this commerce payment method group rel
	 * @param locale the locale of the language
	 */
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	 * Sets the localized description of this commerce payment method group rel in the language, and sets the default locale.
	 *
	 * @param description the localized description of this commerce payment method group rel
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescription(
		String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		model.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized descriptions of this commerce payment method group rel from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this commerce payment method group rel
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		model.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the localized descriptions of this commerce payment method group rel from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this commerce payment method group rel
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {

		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	 * Sets the engine key of this commerce payment method group rel.
	 *
	 * @param engineKey the engine key of this commerce payment method group rel
	 */
	@Override
	public void setEngineKey(String engineKey) {
		model.setEngineKey(engineKey);
	}

	/**
	 * Sets the group ID of this commerce payment method group rel.
	 *
	 * @param groupId the group ID of this commerce payment method group rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the image ID of this commerce payment method group rel.
	 *
	 * @param imageId the image ID of this commerce payment method group rel
	 */
	@Override
	public void setImageId(long imageId) {
		model.setImageId(imageId);
	}

	/**
	 * Sets the modified date of this commerce payment method group rel.
	 *
	 * @param modifiedDate the modified date of this commerce payment method group rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this commerce payment method group rel.
	 *
	 * @param name the name of this commerce payment method group rel
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this commerce payment method group rel in the language.
	 *
	 * @param name the localized name of this commerce payment method group rel
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this commerce payment method group rel in the language, and sets the default locale.
	 *
	 * @param name the localized name of this commerce payment method group rel
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setName(
		String name, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized names of this commerce payment method group rel from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this commerce payment method group rel
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this commerce payment method group rel from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this commerce payment method group rel
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the primary key of this commerce payment method group rel.
	 *
	 * @param primaryKey the primary key of this commerce payment method group rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this commerce payment method group rel.
	 *
	 * @param priority the priority of this commerce payment method group rel
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the user ID of this commerce payment method group rel.
	 *
	 * @param userId the user ID of this commerce payment method group rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce payment method group rel.
	 *
	 * @param userName the user name of this commerce payment method group rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce payment method group rel.
	 *
	 * @param userUuid the user uuid of this commerce payment method group rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommercePaymentMethodGroupRelWrapper wrap(
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel) {

		return new CommercePaymentMethodGroupRelWrapper(
			commercePaymentMethodGroupRel);
	}

}
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

package com.liferay.object.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ObjectRelationship}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectRelationship
 * @generated
 */
public class ObjectRelationshipWrapper
	extends BaseModelWrapper<ObjectRelationship>
	implements ModelWrapper<ObjectRelationship>, ObjectRelationship {

	public ObjectRelationshipWrapper(ObjectRelationship objectRelationship) {
		super(objectRelationship);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectRelationshipId", getObjectRelationshipId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectDefinitionId1", getObjectDefinitionId1());
		attributes.put("objectDefinitionId2", getObjectDefinitionId2());
		attributes.put("objectFieldId2", getObjectFieldId2());
		attributes.put("dbTableName", getDBTableName());
		attributes.put("label", getLabel());
		attributes.put("name", getName());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long objectRelationshipId = (Long)attributes.get(
			"objectRelationshipId");

		if (objectRelationshipId != null) {
			setObjectRelationshipId(objectRelationshipId);
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

		Long objectDefinitionId1 = (Long)attributes.get("objectDefinitionId1");

		if (objectDefinitionId1 != null) {
			setObjectDefinitionId1(objectDefinitionId1);
		}

		Long objectDefinitionId2 = (Long)attributes.get("objectDefinitionId2");

		if (objectDefinitionId2 != null) {
			setObjectDefinitionId2(objectDefinitionId2);
		}

		Long objectFieldId2 = (Long)attributes.get("objectFieldId2");

		if (objectFieldId2 != null) {
			setObjectFieldId2(objectFieldId2);
		}

		String dbTableName = (String)attributes.get("dbTableName");

		if (dbTableName != null) {
			setDBTableName(dbTableName);
		}

		String label = (String)attributes.get("label");

		if (label != null) {
			setLabel(label);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public ObjectRelationship cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this object relationship.
	 *
	 * @return the company ID of this object relationship
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object relationship.
	 *
	 * @return the create date of this object relationship
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the db table name of this object relationship.
	 *
	 * @return the db table name of this object relationship
	 */
	@Override
	public String getDBTableName() {
		return model.getDBTableName();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the label of this object relationship.
	 *
	 * @return the label of this object relationship
	 */
	@Override
	public String getLabel() {
		return model.getLabel();
	}

	/**
	 * Returns the localized label of this object relationship in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized label of this object relationship
	 */
	@Override
	public String getLabel(java.util.Locale locale) {
		return model.getLabel(locale);
	}

	/**
	 * Returns the localized label of this object relationship in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this object relationship. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getLabel(java.util.Locale locale, boolean useDefault) {
		return model.getLabel(locale, useDefault);
	}

	/**
	 * Returns the localized label of this object relationship in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized label of this object relationship
	 */
	@Override
	public String getLabel(String languageId) {
		return model.getLabel(languageId);
	}

	/**
	 * Returns the localized label of this object relationship in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this object relationship
	 */
	@Override
	public String getLabel(String languageId, boolean useDefault) {
		return model.getLabel(languageId, useDefault);
	}

	@Override
	public String getLabelCurrentLanguageId() {
		return model.getLabelCurrentLanguageId();
	}

	@Override
	public String getLabelCurrentValue() {
		return model.getLabelCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized labels of this object relationship.
	 *
	 * @return the locales and localized labels of this object relationship
	 */
	@Override
	public Map<java.util.Locale, String> getLabelMap() {
		return model.getLabelMap();
	}

	/**
	 * Returns the modified date of this object relationship.
	 *
	 * @return the modified date of this object relationship
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object relationship.
	 *
	 * @return the mvcc version of this object relationship
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this object relationship.
	 *
	 * @return the name of this object relationship
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the object definition id1 of this object relationship.
	 *
	 * @return the object definition id1 of this object relationship
	 */
	@Override
	public long getObjectDefinitionId1() {
		return model.getObjectDefinitionId1();
	}

	/**
	 * Returns the object definition id2 of this object relationship.
	 *
	 * @return the object definition id2 of this object relationship
	 */
	@Override
	public long getObjectDefinitionId2() {
		return model.getObjectDefinitionId2();
	}

	/**
	 * Returns the object field id2 of this object relationship.
	 *
	 * @return the object field id2 of this object relationship
	 */
	@Override
	public long getObjectFieldId2() {
		return model.getObjectFieldId2();
	}

	/**
	 * Returns the object relationship ID of this object relationship.
	 *
	 * @return the object relationship ID of this object relationship
	 */
	@Override
	public long getObjectRelationshipId() {
		return model.getObjectRelationshipId();
	}

	/**
	 * Returns the primary key of this object relationship.
	 *
	 * @return the primary key of this object relationship
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this object relationship.
	 *
	 * @return the type of this object relationship
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this object relationship.
	 *
	 * @return the user ID of this object relationship
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object relationship.
	 *
	 * @return the user name of this object relationship
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object relationship.
	 *
	 * @return the user uuid of this object relationship
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object relationship.
	 *
	 * @return the uuid of this object relationship
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
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
	 * Sets the company ID of this object relationship.
	 *
	 * @param companyId the company ID of this object relationship
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object relationship.
	 *
	 * @param createDate the create date of this object relationship
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the db table name of this object relationship.
	 *
	 * @param dbTableName the db table name of this object relationship
	 */
	@Override
	public void setDBTableName(String dbTableName) {
		model.setDBTableName(dbTableName);
	}

	/**
	 * Sets the label of this object relationship.
	 *
	 * @param label the label of this object relationship
	 */
	@Override
	public void setLabel(String label) {
		model.setLabel(label);
	}

	/**
	 * Sets the localized label of this object relationship in the language.
	 *
	 * @param label the localized label of this object relationship
	 * @param locale the locale of the language
	 */
	@Override
	public void setLabel(String label, java.util.Locale locale) {
		model.setLabel(label, locale);
	}

	/**
	 * Sets the localized label of this object relationship in the language, and sets the default locale.
	 *
	 * @param label the localized label of this object relationship
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabel(
		String label, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setLabel(label, locale, defaultLocale);
	}

	@Override
	public void setLabelCurrentLanguageId(String languageId) {
		model.setLabelCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized labels of this object relationship from the map of locales and localized labels.
	 *
	 * @param labelMap the locales and localized labels of this object relationship
	 */
	@Override
	public void setLabelMap(Map<java.util.Locale, String> labelMap) {
		model.setLabelMap(labelMap);
	}

	/**
	 * Sets the localized labels of this object relationship from the map of locales and localized labels, and sets the default locale.
	 *
	 * @param labelMap the locales and localized labels of this object relationship
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabelMap(
		Map<java.util.Locale, String> labelMap,
		java.util.Locale defaultLocale) {

		model.setLabelMap(labelMap, defaultLocale);
	}

	/**
	 * Sets the modified date of this object relationship.
	 *
	 * @param modifiedDate the modified date of this object relationship
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object relationship.
	 *
	 * @param mvccVersion the mvcc version of this object relationship
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this object relationship.
	 *
	 * @param name the name of this object relationship
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the object definition id1 of this object relationship.
	 *
	 * @param objectDefinitionId1 the object definition id1 of this object relationship
	 */
	@Override
	public void setObjectDefinitionId1(long objectDefinitionId1) {
		model.setObjectDefinitionId1(objectDefinitionId1);
	}

	/**
	 * Sets the object definition id2 of this object relationship.
	 *
	 * @param objectDefinitionId2 the object definition id2 of this object relationship
	 */
	@Override
	public void setObjectDefinitionId2(long objectDefinitionId2) {
		model.setObjectDefinitionId2(objectDefinitionId2);
	}

	/**
	 * Sets the object field id2 of this object relationship.
	 *
	 * @param objectFieldId2 the object field id2 of this object relationship
	 */
	@Override
	public void setObjectFieldId2(long objectFieldId2) {
		model.setObjectFieldId2(objectFieldId2);
	}

	/**
	 * Sets the object relationship ID of this object relationship.
	 *
	 * @param objectRelationshipId the object relationship ID of this object relationship
	 */
	@Override
	public void setObjectRelationshipId(long objectRelationshipId) {
		model.setObjectRelationshipId(objectRelationshipId);
	}

	/**
	 * Sets the primary key of this object relationship.
	 *
	 * @param primaryKey the primary key of this object relationship
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this object relationship.
	 *
	 * @param type the type of this object relationship
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this object relationship.
	 *
	 * @param userId the user ID of this object relationship
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object relationship.
	 *
	 * @param userName the user name of this object relationship
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object relationship.
	 *
	 * @param userUuid the user uuid of this object relationship
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object relationship.
	 *
	 * @param uuid the uuid of this object relationship
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ObjectRelationshipWrapper wrap(
		ObjectRelationship objectRelationship) {

		return new ObjectRelationshipWrapper(objectRelationship);
	}

}
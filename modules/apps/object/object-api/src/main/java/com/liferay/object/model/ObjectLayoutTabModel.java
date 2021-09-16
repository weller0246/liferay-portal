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

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.LocalizedModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedAuditedModel;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the ObjectLayoutTab service. Represents a row in the &quot;ObjectLayoutTab&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.object.model.impl.ObjectLayoutTabModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.object.model.impl.ObjectLayoutTabImpl</code>.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutTab
 * @generated
 */
@ProviderType
public interface ObjectLayoutTabModel
	extends BaseModel<ObjectLayoutTab>, LocalizedModel, MVCCModel, ShardedModel,
			StagedAuditedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a object layout tab model instance should use the {@link ObjectLayoutTab} interface instead.
	 */

	/**
	 * Returns the primary key of this object layout tab.
	 *
	 * @return the primary key of this object layout tab
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this object layout tab.
	 *
	 * @param primaryKey the primary key of this object layout tab
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this object layout tab.
	 *
	 * @return the mvcc version of this object layout tab
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this object layout tab.
	 *
	 * @param mvccVersion the mvcc version of this object layout tab
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the uuid of this object layout tab.
	 *
	 * @return the uuid of this object layout tab
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this object layout tab.
	 *
	 * @param uuid the uuid of this object layout tab
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the object layout tab ID of this object layout tab.
	 *
	 * @return the object layout tab ID of this object layout tab
	 */
	public long getObjectLayoutTabId();

	/**
	 * Sets the object layout tab ID of this object layout tab.
	 *
	 * @param objectLayoutTabId the object layout tab ID of this object layout tab
	 */
	public void setObjectLayoutTabId(long objectLayoutTabId);

	/**
	 * Returns the company ID of this object layout tab.
	 *
	 * @return the company ID of this object layout tab
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this object layout tab.
	 *
	 * @param companyId the company ID of this object layout tab
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this object layout tab.
	 *
	 * @return the user ID of this object layout tab
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this object layout tab.
	 *
	 * @param userId the user ID of this object layout tab
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this object layout tab.
	 *
	 * @return the user uuid of this object layout tab
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this object layout tab.
	 *
	 * @param userUuid the user uuid of this object layout tab
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this object layout tab.
	 *
	 * @return the user name of this object layout tab
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this object layout tab.
	 *
	 * @param userName the user name of this object layout tab
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this object layout tab.
	 *
	 * @return the create date of this object layout tab
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this object layout tab.
	 *
	 * @param createDate the create date of this object layout tab
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this object layout tab.
	 *
	 * @return the modified date of this object layout tab
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this object layout tab.
	 *
	 * @param modifiedDate the modified date of this object layout tab
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the object layout ID of this object layout tab.
	 *
	 * @return the object layout ID of this object layout tab
	 */
	public long getObjectLayoutId();

	/**
	 * Sets the object layout ID of this object layout tab.
	 *
	 * @param objectLayoutId the object layout ID of this object layout tab
	 */
	public void setObjectLayoutId(long objectLayoutId);

	/**
	 * Returns the object relationship ID of this object layout tab.
	 *
	 * @return the object relationship ID of this object layout tab
	 */
	public long getObjectRelationshipId();

	/**
	 * Sets the object relationship ID of this object layout tab.
	 *
	 * @param objectRelationshipId the object relationship ID of this object layout tab
	 */
	public void setObjectRelationshipId(long objectRelationshipId);

	/**
	 * Returns the name of this object layout tab.
	 *
	 * @return the name of this object layout tab
	 */
	public String getName();

	/**
	 * Returns the localized name of this object layout tab in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this object layout tab
	 */
	@AutoEscape
	public String getName(Locale locale);

	/**
	 * Returns the localized name of this object layout tab in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this object layout tab. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getName(Locale locale, boolean useDefault);

	/**
	 * Returns the localized name of this object layout tab in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this object layout tab
	 */
	@AutoEscape
	public String getName(String languageId);

	/**
	 * Returns the localized name of this object layout tab in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this object layout tab
	 */
	@AutoEscape
	public String getName(String languageId, boolean useDefault);

	@AutoEscape
	public String getNameCurrentLanguageId();

	@AutoEscape
	public String getNameCurrentValue();

	/**
	 * Returns a map of the locales and localized names of this object layout tab.
	 *
	 * @return the locales and localized names of this object layout tab
	 */
	public Map<Locale, String> getNameMap();

	/**
	 * Sets the name of this object layout tab.
	 *
	 * @param name the name of this object layout tab
	 */
	public void setName(String name);

	/**
	 * Sets the localized name of this object layout tab in the language.
	 *
	 * @param name the localized name of this object layout tab
	 * @param locale the locale of the language
	 */
	public void setName(String name, Locale locale);

	/**
	 * Sets the localized name of this object layout tab in the language, and sets the default locale.
	 *
	 * @param name the localized name of this object layout tab
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameCurrentLanguageId(String languageId);

	/**
	 * Sets the localized names of this object layout tab from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this object layout tab
	 */
	public void setNameMap(Map<Locale, String> nameMap);

	/**
	 * Sets the localized names of this object layout tab from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this object layout tab
	 * @param defaultLocale the default locale
	 */
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	/**
	 * Returns the priority of this object layout tab.
	 *
	 * @return the priority of this object layout tab
	 */
	public int getPriority();

	/**
	 * Sets the priority of this object layout tab.
	 *
	 * @param priority the priority of this object layout tab
	 */
	public void setPriority(int priority);

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
	public ObjectLayoutTab cloneWithOriginalValues();

}
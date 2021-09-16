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

package com.liferay.analytics.message.storage.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.sql.Blob;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AnalyticsMessage}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessage
 * @generated
 */
public class AnalyticsMessageWrapper
	extends BaseModelWrapper<AnalyticsMessage>
	implements AnalyticsMessage, ModelWrapper<AnalyticsMessage> {

	public AnalyticsMessageWrapper(AnalyticsMessage analyticsMessage) {
		super(analyticsMessage);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("analyticsMessageId", getAnalyticsMessageId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("body", getBody());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long analyticsMessageId = (Long)attributes.get("analyticsMessageId");

		if (analyticsMessageId != null) {
			setAnalyticsMessageId(analyticsMessageId);
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

		Blob body = (Blob)attributes.get("body");

		if (body != null) {
			setBody(body);
		}
	}

	@Override
	public AnalyticsMessage cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the analytics message ID of this analytics message.
	 *
	 * @return the analytics message ID of this analytics message
	 */
	@Override
	public long getAnalyticsMessageId() {
		return model.getAnalyticsMessageId();
	}

	/**
	 * Returns the body of this analytics message.
	 *
	 * @return the body of this analytics message
	 */
	@Override
	public Blob getBody() {
		return model.getBody();
	}

	/**
	 * Returns the company ID of this analytics message.
	 *
	 * @return the company ID of this analytics message
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this analytics message.
	 *
	 * @return the create date of this analytics message
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the mvcc version of this analytics message.
	 *
	 * @return the mvcc version of this analytics message
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this analytics message.
	 *
	 * @return the primary key of this analytics message
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this analytics message.
	 *
	 * @return the user ID of this analytics message
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this analytics message.
	 *
	 * @return the user name of this analytics message
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this analytics message.
	 *
	 * @return the user uuid of this analytics message
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the analytics message ID of this analytics message.
	 *
	 * @param analyticsMessageId the analytics message ID of this analytics message
	 */
	@Override
	public void setAnalyticsMessageId(long analyticsMessageId) {
		model.setAnalyticsMessageId(analyticsMessageId);
	}

	/**
	 * Sets the body of this analytics message.
	 *
	 * @param body the body of this analytics message
	 */
	@Override
	public void setBody(Blob body) {
		model.setBody(body);
	}

	/**
	 * Sets the company ID of this analytics message.
	 *
	 * @param companyId the company ID of this analytics message
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this analytics message.
	 *
	 * @param createDate the create date of this analytics message
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the mvcc version of this analytics message.
	 *
	 * @param mvccVersion the mvcc version of this analytics message
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this analytics message.
	 *
	 * @param primaryKey the primary key of this analytics message
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this analytics message.
	 *
	 * @param userId the user ID of this analytics message
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this analytics message.
	 *
	 * @param userName the user name of this analytics message
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this analytics message.
	 *
	 * @param userUuid the user uuid of this analytics message
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected AnalyticsMessageWrapper wrap(AnalyticsMessage analyticsMessage) {
		return new AnalyticsMessageWrapper(analyticsMessage);
	}

}
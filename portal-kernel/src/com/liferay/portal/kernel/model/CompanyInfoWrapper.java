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

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CompanyInfo}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CompanyInfo
 * @generated
 */
public class CompanyInfoWrapper
	extends BaseModelWrapper<CompanyInfo>
	implements CompanyInfo, ModelWrapper<CompanyInfo> {

	public CompanyInfoWrapper(CompanyInfo companyInfo) {
		super(companyInfo);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("companyInfoId", getCompanyInfoId());
		attributes.put("companyId", getCompanyId());
		attributes.put("key", getKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long companyInfoId = (Long)attributes.get("companyInfoId");

		if (companyInfoId != null) {
			setCompanyInfoId(companyInfoId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}
	}

	@Override
	public CompanyInfo cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this company info.
	 *
	 * @return the company ID of this company info
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the company info ID of this company info.
	 *
	 * @return the company info ID of this company info
	 */
	@Override
	public long getCompanyInfoId() {
		return model.getCompanyInfoId();
	}

	/**
	 * Returns the key of this company info.
	 *
	 * @return the key of this company info
	 */
	@Override
	public String getKey() {
		return model.getKey();
	}

	/**
	 * Returns the mvcc version of this company info.
	 *
	 * @return the mvcc version of this company info
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this company info.
	 *
	 * @return the primary key of this company info
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this company info.
	 *
	 * @param companyId the company ID of this company info
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the company info ID of this company info.
	 *
	 * @param companyInfoId the company info ID of this company info
	 */
	@Override
	public void setCompanyInfoId(long companyInfoId) {
		model.setCompanyInfoId(companyInfoId);
	}

	/**
	 * Sets the key of this company info.
	 *
	 * @param key the key of this company info
	 */
	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	/**
	 * Sets the mvcc version of this company info.
	 *
	 * @param mvccVersion the mvcc version of this company info
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this company info.
	 *
	 * @param primaryKey the primary key of this company info
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected CompanyInfoWrapper wrap(CompanyInfo companyInfo) {
		return new CompanyInfoWrapper(companyInfo);
	}

}
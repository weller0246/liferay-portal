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

package com.liferay.commerce.inventory.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the CommerceInventoryAudit service. Represents a row in the &quot;CIAudit&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.commerce.inventory.model.impl.CommerceInventoryAuditModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.commerce.inventory.model.impl.CommerceInventoryAuditImpl</code>.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryAudit
 * @generated
 */
@ProviderType
public interface CommerceInventoryAuditModel
	extends AuditedModel, BaseModel<CommerceInventoryAudit>, ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a commerce inventory audit model instance should use the {@link CommerceInventoryAudit} interface instead.
	 */

	/**
	 * Returns the primary key of this commerce inventory audit.
	 *
	 * @return the primary key of this commerce inventory audit
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this commerce inventory audit.
	 *
	 * @param primaryKey the primary key of this commerce inventory audit
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the commerce inventory audit ID of this commerce inventory audit.
	 *
	 * @return the commerce inventory audit ID of this commerce inventory audit
	 */
	public long getCommerceInventoryAuditId();

	/**
	 * Sets the commerce inventory audit ID of this commerce inventory audit.
	 *
	 * @param commerceInventoryAuditId the commerce inventory audit ID of this commerce inventory audit
	 */
	public void setCommerceInventoryAuditId(long commerceInventoryAuditId);

	/**
	 * Returns the company ID of this commerce inventory audit.
	 *
	 * @return the company ID of this commerce inventory audit
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this commerce inventory audit.
	 *
	 * @param companyId the company ID of this commerce inventory audit
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this commerce inventory audit.
	 *
	 * @return the user ID of this commerce inventory audit
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this commerce inventory audit.
	 *
	 * @param userId the user ID of this commerce inventory audit
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this commerce inventory audit.
	 *
	 * @return the user uuid of this commerce inventory audit
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this commerce inventory audit.
	 *
	 * @param userUuid the user uuid of this commerce inventory audit
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this commerce inventory audit.
	 *
	 * @return the user name of this commerce inventory audit
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this commerce inventory audit.
	 *
	 * @param userName the user name of this commerce inventory audit
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this commerce inventory audit.
	 *
	 * @return the create date of this commerce inventory audit
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this commerce inventory audit.
	 *
	 * @param createDate the create date of this commerce inventory audit
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this commerce inventory audit.
	 *
	 * @return the modified date of this commerce inventory audit
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this commerce inventory audit.
	 *
	 * @param modifiedDate the modified date of this commerce inventory audit
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the sku of this commerce inventory audit.
	 *
	 * @return the sku of this commerce inventory audit
	 */
	@AutoEscape
	public String getSku();

	/**
	 * Sets the sku of this commerce inventory audit.
	 *
	 * @param sku the sku of this commerce inventory audit
	 */
	public void setSku(String sku);

	/**
	 * Returns the log type of this commerce inventory audit.
	 *
	 * @return the log type of this commerce inventory audit
	 */
	@AutoEscape
	public String getLogType();

	/**
	 * Sets the log type of this commerce inventory audit.
	 *
	 * @param logType the log type of this commerce inventory audit
	 */
	public void setLogType(String logType);

	/**
	 * Returns the log type settings of this commerce inventory audit.
	 *
	 * @return the log type settings of this commerce inventory audit
	 */
	@AutoEscape
	public String getLogTypeSettings();

	/**
	 * Sets the log type settings of this commerce inventory audit.
	 *
	 * @param logTypeSettings the log type settings of this commerce inventory audit
	 */
	public void setLogTypeSettings(String logTypeSettings);

	/**
	 * Returns the quantity of this commerce inventory audit.
	 *
	 * @return the quantity of this commerce inventory audit
	 */
	public int getQuantity();

	/**
	 * Sets the quantity of this commerce inventory audit.
	 *
	 * @param quantity the quantity of this commerce inventory audit
	 */
	public void setQuantity(int quantity);

	@Override
	public CommerceInventoryAudit cloneWithOriginalValues();

}
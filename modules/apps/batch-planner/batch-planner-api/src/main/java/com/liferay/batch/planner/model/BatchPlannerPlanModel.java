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

package com.liferay.batch.planner.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the BatchPlannerPlan service. Represents a row in the &quot;BatchPlannerPlan&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.batch.planner.model.impl.BatchPlannerPlanModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.batch.planner.model.impl.BatchPlannerPlanImpl</code>.
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerPlan
 * @generated
 */
@ProviderType
public interface BatchPlannerPlanModel
	extends AuditedModel, BaseModel<BatchPlannerPlan>, MVCCModel, ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a batch planner plan model instance should use the {@link BatchPlannerPlan} interface instead.
	 */

	/**
	 * Returns the primary key of this batch planner plan.
	 *
	 * @return the primary key of this batch planner plan
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this batch planner plan.
	 *
	 * @param primaryKey the primary key of this batch planner plan
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this batch planner plan.
	 *
	 * @return the mvcc version of this batch planner plan
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this batch planner plan.
	 *
	 * @param mvccVersion the mvcc version of this batch planner plan
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the batch planner plan ID of this batch planner plan.
	 *
	 * @return the batch planner plan ID of this batch planner plan
	 */
	public long getBatchPlannerPlanId();

	/**
	 * Sets the batch planner plan ID of this batch planner plan.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID of this batch planner plan
	 */
	public void setBatchPlannerPlanId(long batchPlannerPlanId);

	/**
	 * Returns the company ID of this batch planner plan.
	 *
	 * @return the company ID of this batch planner plan
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this batch planner plan.
	 *
	 * @param companyId the company ID of this batch planner plan
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this batch planner plan.
	 *
	 * @return the user ID of this batch planner plan
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this batch planner plan.
	 *
	 * @param userId the user ID of this batch planner plan
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this batch planner plan.
	 *
	 * @return the user uuid of this batch planner plan
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this batch planner plan.
	 *
	 * @param userUuid the user uuid of this batch planner plan
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this batch planner plan.
	 *
	 * @return the user name of this batch planner plan
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this batch planner plan.
	 *
	 * @param userName the user name of this batch planner plan
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this batch planner plan.
	 *
	 * @return the create date of this batch planner plan
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this batch planner plan.
	 *
	 * @param createDate the create date of this batch planner plan
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this batch planner plan.
	 *
	 * @return the modified date of this batch planner plan
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this batch planner plan.
	 *
	 * @param modifiedDate the modified date of this batch planner plan
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the active of this batch planner plan.
	 *
	 * @return the active of this batch planner plan
	 */
	public boolean getActive();

	/**
	 * Returns <code>true</code> if this batch planner plan is active.
	 *
	 * @return <code>true</code> if this batch planner plan is active; <code>false</code> otherwise
	 */
	public boolean isActive();

	/**
	 * Sets whether this batch planner plan is active.
	 *
	 * @param active the active of this batch planner plan
	 */
	public void setActive(boolean active);

	/**
	 * Returns the export of this batch planner plan.
	 *
	 * @return the export of this batch planner plan
	 */
	public boolean getExport();

	/**
	 * Returns <code>true</code> if this batch planner plan is export.
	 *
	 * @return <code>true</code> if this batch planner plan is export; <code>false</code> otherwise
	 */
	public boolean isExport();

	/**
	 * Sets whether this batch planner plan is export.
	 *
	 * @param export the export of this batch planner plan
	 */
	public void setExport(boolean export);

	/**
	 * Returns the external type of this batch planner plan.
	 *
	 * @return the external type of this batch planner plan
	 */
	@AutoEscape
	public String getExternalType();

	/**
	 * Sets the external type of this batch planner plan.
	 *
	 * @param externalType the external type of this batch planner plan
	 */
	public void setExternalType(String externalType);

	/**
	 * Returns the external url of this batch planner plan.
	 *
	 * @return the external url of this batch planner plan
	 */
	@AutoEscape
	public String getExternalURL();

	/**
	 * Sets the external url of this batch planner plan.
	 *
	 * @param externalURL the external url of this batch planner plan
	 */
	public void setExternalURL(String externalURL);

	/**
	 * Returns the internal class name of this batch planner plan.
	 *
	 * @return the internal class name of this batch planner plan
	 */
	@AutoEscape
	public String getInternalClassName();

	/**
	 * Sets the internal class name of this batch planner plan.
	 *
	 * @param internalClassName the internal class name of this batch planner plan
	 */
	public void setInternalClassName(String internalClassName);

	/**
	 * Returns the name of this batch planner plan.
	 *
	 * @return the name of this batch planner plan
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this batch planner plan.
	 *
	 * @param name the name of this batch planner plan
	 */
	public void setName(String name);

	/**
	 * Returns the template of this batch planner plan.
	 *
	 * @return the template of this batch planner plan
	 */
	public boolean getTemplate();

	/**
	 * Returns <code>true</code> if this batch planner plan is template.
	 *
	 * @return <code>true</code> if this batch planner plan is template; <code>false</code> otherwise
	 */
	public boolean isTemplate();

	/**
	 * Sets whether this batch planner plan is template.
	 *
	 * @param template the template of this batch planner plan
	 */
	public void setTemplate(boolean template);

	@Override
	public BatchPlannerPlan cloneWithOriginalValues();

}
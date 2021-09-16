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

package com.liferay.account.model;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the AccountEntryUserRel service. Represents a row in the &quot;AccountEntryUserRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.account.model.impl.AccountEntryUserRelModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.account.model.impl.AccountEntryUserRelImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryUserRel
 * @generated
 */
@ProviderType
public interface AccountEntryUserRelModel
	extends BaseModel<AccountEntryUserRel>, MVCCModel, ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a account entry user rel model instance should use the {@link AccountEntryUserRel} interface instead.
	 */

	/**
	 * Returns the primary key of this account entry user rel.
	 *
	 * @return the primary key of this account entry user rel
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this account entry user rel.
	 *
	 * @param primaryKey the primary key of this account entry user rel
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this account entry user rel.
	 *
	 * @return the mvcc version of this account entry user rel
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this account entry user rel.
	 *
	 * @param mvccVersion the mvcc version of this account entry user rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the account entry user rel ID of this account entry user rel.
	 *
	 * @return the account entry user rel ID of this account entry user rel
	 */
	public long getAccountEntryUserRelId();

	/**
	 * Sets the account entry user rel ID of this account entry user rel.
	 *
	 * @param accountEntryUserRelId the account entry user rel ID of this account entry user rel
	 */
	public void setAccountEntryUserRelId(long accountEntryUserRelId);

	/**
	 * Returns the company ID of this account entry user rel.
	 *
	 * @return the company ID of this account entry user rel
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this account entry user rel.
	 *
	 * @param companyId the company ID of this account entry user rel
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the account entry ID of this account entry user rel.
	 *
	 * @return the account entry ID of this account entry user rel
	 */
	public long getAccountEntryId();

	/**
	 * Sets the account entry ID of this account entry user rel.
	 *
	 * @param accountEntryId the account entry ID of this account entry user rel
	 */
	public void setAccountEntryId(long accountEntryId);

	/**
	 * Returns the account user ID of this account entry user rel.
	 *
	 * @return the account user ID of this account entry user rel
	 */
	public long getAccountUserId();

	/**
	 * Sets the account user ID of this account entry user rel.
	 *
	 * @param accountUserId the account user ID of this account entry user rel
	 */
	public void setAccountUserId(long accountUserId);

	/**
	 * Returns the account user uuid of this account entry user rel.
	 *
	 * @return the account user uuid of this account entry user rel
	 */
	public String getAccountUserUuid();

	/**
	 * Sets the account user uuid of this account entry user rel.
	 *
	 * @param accountUserUuid the account user uuid of this account entry user rel
	 */
	public void setAccountUserUuid(String accountUserUuid);

	@Override
	public AccountEntryUserRel cloneWithOriginalValues();

}
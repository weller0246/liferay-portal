/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the SamlPeerBinding service. Represents a row in the &quot;SamlPeerBinding&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.saml.persistence.model.impl.SamlPeerBindingModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.saml.persistence.model.impl.SamlPeerBindingImpl</code>.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlPeerBinding
 * @generated
 */
@ProviderType
public interface SamlPeerBindingModel
	extends BaseModel<SamlPeerBinding>, ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a saml peer binding model instance should use the {@link SamlPeerBinding} interface instead.
	 */

	/**
	 * Returns the primary key of this saml peer binding.
	 *
	 * @return the primary key of this saml peer binding
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this saml peer binding.
	 *
	 * @param primaryKey the primary key of this saml peer binding
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the saml peer binding ID of this saml peer binding.
	 *
	 * @return the saml peer binding ID of this saml peer binding
	 */
	public long getSamlPeerBindingId();

	/**
	 * Sets the saml peer binding ID of this saml peer binding.
	 *
	 * @param samlPeerBindingId the saml peer binding ID of this saml peer binding
	 */
	public void setSamlPeerBindingId(long samlPeerBindingId);

	/**
	 * Returns the company ID of this saml peer binding.
	 *
	 * @return the company ID of this saml peer binding
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this saml peer binding.
	 *
	 * @param companyId the company ID of this saml peer binding
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the create date of this saml peer binding.
	 *
	 * @return the create date of this saml peer binding
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this saml peer binding.
	 *
	 * @param createDate the create date of this saml peer binding
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Returns the user ID of this saml peer binding.
	 *
	 * @return the user ID of this saml peer binding
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this saml peer binding.
	 *
	 * @param userId the user ID of this saml peer binding
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this saml peer binding.
	 *
	 * @return the user uuid of this saml peer binding
	 */
	public String getUserUuid();

	/**
	 * Sets the user uuid of this saml peer binding.
	 *
	 * @param userUuid the user uuid of this saml peer binding
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this saml peer binding.
	 *
	 * @return the user name of this saml peer binding
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this saml peer binding.
	 *
	 * @param userName the user name of this saml peer binding
	 */
	public void setUserName(String userName);

	/**
	 * Returns the deleted of this saml peer binding.
	 *
	 * @return the deleted of this saml peer binding
	 */
	public boolean getDeleted();

	/**
	 * Returns <code>true</code> if this saml peer binding is deleted.
	 *
	 * @return <code>true</code> if this saml peer binding is deleted; <code>false</code> otherwise
	 */
	public boolean isDeleted();

	/**
	 * Sets whether this saml peer binding is deleted.
	 *
	 * @param deleted the deleted of this saml peer binding
	 */
	public void setDeleted(boolean deleted);

	/**
	 * Returns the saml name ID format of this saml peer binding.
	 *
	 * @return the saml name ID format of this saml peer binding
	 */
	@AutoEscape
	public String getSamlNameIdFormat();

	/**
	 * Sets the saml name ID format of this saml peer binding.
	 *
	 * @param samlNameIdFormat the saml name ID format of this saml peer binding
	 */
	public void setSamlNameIdFormat(String samlNameIdFormat);

	/**
	 * Returns the saml name ID name qualifier of this saml peer binding.
	 *
	 * @return the saml name ID name qualifier of this saml peer binding
	 */
	@AutoEscape
	public String getSamlNameIdNameQualifier();

	/**
	 * Sets the saml name ID name qualifier of this saml peer binding.
	 *
	 * @param samlNameIdNameQualifier the saml name ID name qualifier of this saml peer binding
	 */
	public void setSamlNameIdNameQualifier(String samlNameIdNameQualifier);

	/**
	 * Returns the saml name ID sp name qualifier of this saml peer binding.
	 *
	 * @return the saml name ID sp name qualifier of this saml peer binding
	 */
	@AutoEscape
	public String getSamlNameIdSpNameQualifier();

	/**
	 * Sets the saml name ID sp name qualifier of this saml peer binding.
	 *
	 * @param samlNameIdSpNameQualifier the saml name ID sp name qualifier of this saml peer binding
	 */
	public void setSamlNameIdSpNameQualifier(String samlNameIdSpNameQualifier);

	/**
	 * Returns the saml name ID sp provided ID of this saml peer binding.
	 *
	 * @return the saml name ID sp provided ID of this saml peer binding
	 */
	@AutoEscape
	public String getSamlNameIdSpProvidedId();

	/**
	 * Sets the saml name ID sp provided ID of this saml peer binding.
	 *
	 * @param samlNameIdSpProvidedId the saml name ID sp provided ID of this saml peer binding
	 */
	public void setSamlNameIdSpProvidedId(String samlNameIdSpProvidedId);

	/**
	 * Returns the saml name ID value of this saml peer binding.
	 *
	 * @return the saml name ID value of this saml peer binding
	 */
	@AutoEscape
	public String getSamlNameIdValue();

	/**
	 * Sets the saml name ID value of this saml peer binding.
	 *
	 * @param samlNameIdValue the saml name ID value of this saml peer binding
	 */
	public void setSamlNameIdValue(String samlNameIdValue);

	/**
	 * Returns the saml peer entity ID of this saml peer binding.
	 *
	 * @return the saml peer entity ID of this saml peer binding
	 */
	@AutoEscape
	public String getSamlPeerEntityId();

	/**
	 * Sets the saml peer entity ID of this saml peer binding.
	 *
	 * @param samlPeerEntityId the saml peer entity ID of this saml peer binding
	 */
	public void setSamlPeerEntityId(String samlPeerEntityId);

	@Override
	public SamlPeerBinding cloneWithOriginalValues();

}
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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceInventoryBookedQuantity}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryBookedQuantity
 * @generated
 */
public class CommerceInventoryBookedQuantityWrapper
	extends BaseModelWrapper<CommerceInventoryBookedQuantity>
	implements CommerceInventoryBookedQuantity,
			   ModelWrapper<CommerceInventoryBookedQuantity> {

	public CommerceInventoryBookedQuantityWrapper(
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) {

		super(commerceInventoryBookedQuantity);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"commerceInventoryBookedQuantityId",
			getCommerceInventoryBookedQuantityId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("sku", getSku());
		attributes.put("quantity", getQuantity());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("bookedNote", getBookedNote());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceInventoryBookedQuantityId = (Long)attributes.get(
			"commerceInventoryBookedQuantityId");

		if (commerceInventoryBookedQuantityId != null) {
			setCommerceInventoryBookedQuantityId(
				commerceInventoryBookedQuantityId);
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

		String sku = (String)attributes.get("sku");

		if (sku != null) {
			setSku(sku);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		String bookedNote = (String)attributes.get("bookedNote");

		if (bookedNote != null) {
			setBookedNote(bookedNote);
		}
	}

	@Override
	public CommerceInventoryBookedQuantity cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the booked note of this commerce inventory booked quantity.
	 *
	 * @return the booked note of this commerce inventory booked quantity
	 */
	@Override
	public String getBookedNote() {
		return model.getBookedNote();
	}

	/**
	 * Returns the commerce inventory booked quantity ID of this commerce inventory booked quantity.
	 *
	 * @return the commerce inventory booked quantity ID of this commerce inventory booked quantity
	 */
	@Override
	public long getCommerceInventoryBookedQuantityId() {
		return model.getCommerceInventoryBookedQuantityId();
	}

	/**
	 * Returns the company ID of this commerce inventory booked quantity.
	 *
	 * @return the company ID of this commerce inventory booked quantity
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce inventory booked quantity.
	 *
	 * @return the create date of this commerce inventory booked quantity
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the expiration date of this commerce inventory booked quantity.
	 *
	 * @return the expiration date of this commerce inventory booked quantity
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the modified date of this commerce inventory booked quantity.
	 *
	 * @return the modified date of this commerce inventory booked quantity
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce inventory booked quantity.
	 *
	 * @return the mvcc version of this commerce inventory booked quantity
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce inventory booked quantity.
	 *
	 * @return the primary key of this commerce inventory booked quantity
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the quantity of this commerce inventory booked quantity.
	 *
	 * @return the quantity of this commerce inventory booked quantity
	 */
	@Override
	public int getQuantity() {
		return model.getQuantity();
	}

	/**
	 * Returns the sku of this commerce inventory booked quantity.
	 *
	 * @return the sku of this commerce inventory booked quantity
	 */
	@Override
	public String getSku() {
		return model.getSku();
	}

	/**
	 * Returns the user ID of this commerce inventory booked quantity.
	 *
	 * @return the user ID of this commerce inventory booked quantity
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce inventory booked quantity.
	 *
	 * @return the user name of this commerce inventory booked quantity
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce inventory booked quantity.
	 *
	 * @return the user uuid of this commerce inventory booked quantity
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
	 * Sets the booked note of this commerce inventory booked quantity.
	 *
	 * @param bookedNote the booked note of this commerce inventory booked quantity
	 */
	@Override
	public void setBookedNote(String bookedNote) {
		model.setBookedNote(bookedNote);
	}

	/**
	 * Sets the commerce inventory booked quantity ID of this commerce inventory booked quantity.
	 *
	 * @param commerceInventoryBookedQuantityId the commerce inventory booked quantity ID of this commerce inventory booked quantity
	 */
	@Override
	public void setCommerceInventoryBookedQuantityId(
		long commerceInventoryBookedQuantityId) {

		model.setCommerceInventoryBookedQuantityId(
			commerceInventoryBookedQuantityId);
	}

	/**
	 * Sets the company ID of this commerce inventory booked quantity.
	 *
	 * @param companyId the company ID of this commerce inventory booked quantity
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce inventory booked quantity.
	 *
	 * @param createDate the create date of this commerce inventory booked quantity
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the expiration date of this commerce inventory booked quantity.
	 *
	 * @param expirationDate the expiration date of this commerce inventory booked quantity
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the modified date of this commerce inventory booked quantity.
	 *
	 * @param modifiedDate the modified date of this commerce inventory booked quantity
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce inventory booked quantity.
	 *
	 * @param mvccVersion the mvcc version of this commerce inventory booked quantity
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce inventory booked quantity.
	 *
	 * @param primaryKey the primary key of this commerce inventory booked quantity
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the quantity of this commerce inventory booked quantity.
	 *
	 * @param quantity the quantity of this commerce inventory booked quantity
	 */
	@Override
	public void setQuantity(int quantity) {
		model.setQuantity(quantity);
	}

	/**
	 * Sets the sku of this commerce inventory booked quantity.
	 *
	 * @param sku the sku of this commerce inventory booked quantity
	 */
	@Override
	public void setSku(String sku) {
		model.setSku(sku);
	}

	/**
	 * Sets the user ID of this commerce inventory booked quantity.
	 *
	 * @param userId the user ID of this commerce inventory booked quantity
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce inventory booked quantity.
	 *
	 * @param userName the user name of this commerce inventory booked quantity
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce inventory booked quantity.
	 *
	 * @param userUuid the user uuid of this commerce inventory booked quantity
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceInventoryBookedQuantityWrapper wrap(
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) {

		return new CommerceInventoryBookedQuantityWrapper(
			commerceInventoryBookedQuantity);
	}

}
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
 * This class is a wrapper for {@link CommerceInventoryReplenishmentItem}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryReplenishmentItem
 * @generated
 */
public class CommerceInventoryReplenishmentItemWrapper
	extends BaseModelWrapper<CommerceInventoryReplenishmentItem>
	implements CommerceInventoryReplenishmentItem,
			   ModelWrapper<CommerceInventoryReplenishmentItem> {

	public CommerceInventoryReplenishmentItemWrapper(
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem) {

		super(commerceInventoryReplenishmentItem);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"commerceInventoryReplenishmentItemId",
			getCommerceInventoryReplenishmentItemId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceInventoryWarehouseId", getCommerceInventoryWarehouseId());
		attributes.put("sku", getSku());
		attributes.put("availabilityDate", getAvailabilityDate());
		attributes.put("quantity", getQuantity());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceInventoryReplenishmentItemId = (Long)attributes.get(
			"commerceInventoryReplenishmentItemId");

		if (commerceInventoryReplenishmentItemId != null) {
			setCommerceInventoryReplenishmentItemId(
				commerceInventoryReplenishmentItemId);
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

		Long commerceInventoryWarehouseId = (Long)attributes.get(
			"commerceInventoryWarehouseId");

		if (commerceInventoryWarehouseId != null) {
			setCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
		}

		String sku = (String)attributes.get("sku");

		if (sku != null) {
			setSku(sku);
		}

		Date availabilityDate = (Date)attributes.get("availabilityDate");

		if (availabilityDate != null) {
			setAvailabilityDate(availabilityDate);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}
	}

	@Override
	public CommerceInventoryReplenishmentItem cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the availability date of this commerce inventory replenishment item.
	 *
	 * @return the availability date of this commerce inventory replenishment item
	 */
	@Override
	public Date getAvailabilityDate() {
		return model.getAvailabilityDate();
	}

	/**
	 * Returns the commerce inventory replenishment item ID of this commerce inventory replenishment item.
	 *
	 * @return the commerce inventory replenishment item ID of this commerce inventory replenishment item
	 */
	@Override
	public long getCommerceInventoryReplenishmentItemId() {
		return model.getCommerceInventoryReplenishmentItemId();
	}

	@Override
	public CommerceInventoryWarehouse getCommerceInventoryWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceInventoryWarehouse();
	}

	/**
	 * Returns the commerce inventory warehouse ID of this commerce inventory replenishment item.
	 *
	 * @return the commerce inventory warehouse ID of this commerce inventory replenishment item
	 */
	@Override
	public long getCommerceInventoryWarehouseId() {
		return model.getCommerceInventoryWarehouseId();
	}

	/**
	 * Returns the company ID of this commerce inventory replenishment item.
	 *
	 * @return the company ID of this commerce inventory replenishment item
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce inventory replenishment item.
	 *
	 * @return the create date of this commerce inventory replenishment item
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this commerce inventory replenishment item.
	 *
	 * @return the modified date of this commerce inventory replenishment item
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce inventory replenishment item.
	 *
	 * @return the mvcc version of this commerce inventory replenishment item
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce inventory replenishment item.
	 *
	 * @return the primary key of this commerce inventory replenishment item
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the quantity of this commerce inventory replenishment item.
	 *
	 * @return the quantity of this commerce inventory replenishment item
	 */
	@Override
	public int getQuantity() {
		return model.getQuantity();
	}

	/**
	 * Returns the sku of this commerce inventory replenishment item.
	 *
	 * @return the sku of this commerce inventory replenishment item
	 */
	@Override
	public String getSku() {
		return model.getSku();
	}

	/**
	 * Returns the user ID of this commerce inventory replenishment item.
	 *
	 * @return the user ID of this commerce inventory replenishment item
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce inventory replenishment item.
	 *
	 * @return the user name of this commerce inventory replenishment item
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce inventory replenishment item.
	 *
	 * @return the user uuid of this commerce inventory replenishment item
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
	 * Sets the availability date of this commerce inventory replenishment item.
	 *
	 * @param availabilityDate the availability date of this commerce inventory replenishment item
	 */
	@Override
	public void setAvailabilityDate(Date availabilityDate) {
		model.setAvailabilityDate(availabilityDate);
	}

	/**
	 * Sets the commerce inventory replenishment item ID of this commerce inventory replenishment item.
	 *
	 * @param commerceInventoryReplenishmentItemId the commerce inventory replenishment item ID of this commerce inventory replenishment item
	 */
	@Override
	public void setCommerceInventoryReplenishmentItemId(
		long commerceInventoryReplenishmentItemId) {

		model.setCommerceInventoryReplenishmentItemId(
			commerceInventoryReplenishmentItemId);
	}

	/**
	 * Sets the commerce inventory warehouse ID of this commerce inventory replenishment item.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID of this commerce inventory replenishment item
	 */
	@Override
	public void setCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		model.setCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
	}

	/**
	 * Sets the company ID of this commerce inventory replenishment item.
	 *
	 * @param companyId the company ID of this commerce inventory replenishment item
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce inventory replenishment item.
	 *
	 * @param createDate the create date of this commerce inventory replenishment item
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this commerce inventory replenishment item.
	 *
	 * @param modifiedDate the modified date of this commerce inventory replenishment item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce inventory replenishment item.
	 *
	 * @param mvccVersion the mvcc version of this commerce inventory replenishment item
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce inventory replenishment item.
	 *
	 * @param primaryKey the primary key of this commerce inventory replenishment item
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the quantity of this commerce inventory replenishment item.
	 *
	 * @param quantity the quantity of this commerce inventory replenishment item
	 */
	@Override
	public void setQuantity(int quantity) {
		model.setQuantity(quantity);
	}

	/**
	 * Sets the sku of this commerce inventory replenishment item.
	 *
	 * @param sku the sku of this commerce inventory replenishment item
	 */
	@Override
	public void setSku(String sku) {
		model.setSku(sku);
	}

	/**
	 * Sets the user ID of this commerce inventory replenishment item.
	 *
	 * @param userId the user ID of this commerce inventory replenishment item
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce inventory replenishment item.
	 *
	 * @param userName the user name of this commerce inventory replenishment item
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce inventory replenishment item.
	 *
	 * @param userUuid the user uuid of this commerce inventory replenishment item
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceInventoryReplenishmentItemWrapper wrap(
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem) {

		return new CommerceInventoryReplenishmentItemWrapper(
			commerceInventoryReplenishmentItem);
	}

}
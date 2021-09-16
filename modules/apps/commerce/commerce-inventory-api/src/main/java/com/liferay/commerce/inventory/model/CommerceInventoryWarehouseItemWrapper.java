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
 * This class is a wrapper for {@link CommerceInventoryWarehouseItem}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseItem
 * @generated
 */
public class CommerceInventoryWarehouseItemWrapper
	extends BaseModelWrapper<CommerceInventoryWarehouseItem>
	implements CommerceInventoryWarehouseItem,
			   ModelWrapper<CommerceInventoryWarehouseItem> {

	public CommerceInventoryWarehouseItemWrapper(
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem) {

		super(commerceInventoryWarehouseItem);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put(
			"commerceInventoryWarehouseItemId",
			getCommerceInventoryWarehouseItemId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceInventoryWarehouseId", getCommerceInventoryWarehouseId());
		attributes.put("sku", getSku());
		attributes.put("quantity", getQuantity());
		attributes.put("reservedQuantity", getReservedQuantity());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceInventoryWarehouseItemId = (Long)attributes.get(
			"commerceInventoryWarehouseItemId");

		if (commerceInventoryWarehouseItemId != null) {
			setCommerceInventoryWarehouseItemId(
				commerceInventoryWarehouseItemId);
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

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}

		Integer reservedQuantity = (Integer)attributes.get("reservedQuantity");

		if (reservedQuantity != null) {
			setReservedQuantity(reservedQuantity);
		}
	}

	@Override
	public CommerceInventoryWarehouseItem cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public CommerceInventoryWarehouse getCommerceInventoryWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceInventoryWarehouse();
	}

	/**
	 * Returns the commerce inventory warehouse ID of this commerce inventory warehouse item.
	 *
	 * @return the commerce inventory warehouse ID of this commerce inventory warehouse item
	 */
	@Override
	public long getCommerceInventoryWarehouseId() {
		return model.getCommerceInventoryWarehouseId();
	}

	/**
	 * Returns the commerce inventory warehouse item ID of this commerce inventory warehouse item.
	 *
	 * @return the commerce inventory warehouse item ID of this commerce inventory warehouse item
	 */
	@Override
	public long getCommerceInventoryWarehouseItemId() {
		return model.getCommerceInventoryWarehouseItemId();
	}

	/**
	 * Returns the company ID of this commerce inventory warehouse item.
	 *
	 * @return the company ID of this commerce inventory warehouse item
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce inventory warehouse item.
	 *
	 * @return the create date of this commerce inventory warehouse item
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the external reference code of this commerce inventory warehouse item.
	 *
	 * @return the external reference code of this commerce inventory warehouse item
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the modified date of this commerce inventory warehouse item.
	 *
	 * @return the modified date of this commerce inventory warehouse item
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce inventory warehouse item.
	 *
	 * @return the mvcc version of this commerce inventory warehouse item
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce inventory warehouse item.
	 *
	 * @return the primary key of this commerce inventory warehouse item
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the quantity of this commerce inventory warehouse item.
	 *
	 * @return the quantity of this commerce inventory warehouse item
	 */
	@Override
	public int getQuantity() {
		return model.getQuantity();
	}

	/**
	 * Returns the reserved quantity of this commerce inventory warehouse item.
	 *
	 * @return the reserved quantity of this commerce inventory warehouse item
	 */
	@Override
	public int getReservedQuantity() {
		return model.getReservedQuantity();
	}

	/**
	 * Returns the sku of this commerce inventory warehouse item.
	 *
	 * @return the sku of this commerce inventory warehouse item
	 */
	@Override
	public String getSku() {
		return model.getSku();
	}

	/**
	 * Returns the user ID of this commerce inventory warehouse item.
	 *
	 * @return the user ID of this commerce inventory warehouse item
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce inventory warehouse item.
	 *
	 * @return the user name of this commerce inventory warehouse item
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce inventory warehouse item.
	 *
	 * @return the user uuid of this commerce inventory warehouse item
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
	 * Sets the commerce inventory warehouse ID of this commerce inventory warehouse item.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID of this commerce inventory warehouse item
	 */
	@Override
	public void setCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		model.setCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
	}

	/**
	 * Sets the commerce inventory warehouse item ID of this commerce inventory warehouse item.
	 *
	 * @param commerceInventoryWarehouseItemId the commerce inventory warehouse item ID of this commerce inventory warehouse item
	 */
	@Override
	public void setCommerceInventoryWarehouseItemId(
		long commerceInventoryWarehouseItemId) {

		model.setCommerceInventoryWarehouseItemId(
			commerceInventoryWarehouseItemId);
	}

	/**
	 * Sets the company ID of this commerce inventory warehouse item.
	 *
	 * @param companyId the company ID of this commerce inventory warehouse item
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce inventory warehouse item.
	 *
	 * @param createDate the create date of this commerce inventory warehouse item
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external reference code of this commerce inventory warehouse item.
	 *
	 * @param externalReferenceCode the external reference code of this commerce inventory warehouse item
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the modified date of this commerce inventory warehouse item.
	 *
	 * @param modifiedDate the modified date of this commerce inventory warehouse item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce inventory warehouse item.
	 *
	 * @param mvccVersion the mvcc version of this commerce inventory warehouse item
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce inventory warehouse item.
	 *
	 * @param primaryKey the primary key of this commerce inventory warehouse item
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the quantity of this commerce inventory warehouse item.
	 *
	 * @param quantity the quantity of this commerce inventory warehouse item
	 */
	@Override
	public void setQuantity(int quantity) {
		model.setQuantity(quantity);
	}

	/**
	 * Sets the reserved quantity of this commerce inventory warehouse item.
	 *
	 * @param reservedQuantity the reserved quantity of this commerce inventory warehouse item
	 */
	@Override
	public void setReservedQuantity(int reservedQuantity) {
		model.setReservedQuantity(reservedQuantity);
	}

	/**
	 * Sets the sku of this commerce inventory warehouse item.
	 *
	 * @param sku the sku of this commerce inventory warehouse item
	 */
	@Override
	public void setSku(String sku) {
		model.setSku(sku);
	}

	/**
	 * Sets the user ID of this commerce inventory warehouse item.
	 *
	 * @param userId the user ID of this commerce inventory warehouse item
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce inventory warehouse item.
	 *
	 * @param userName the user name of this commerce inventory warehouse item
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce inventory warehouse item.
	 *
	 * @param userUuid the user uuid of this commerce inventory warehouse item
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceInventoryWarehouseItemWrapper wrap(
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem) {

		return new CommerceInventoryWarehouseItemWrapper(
			commerceInventoryWarehouseItem);
	}

}
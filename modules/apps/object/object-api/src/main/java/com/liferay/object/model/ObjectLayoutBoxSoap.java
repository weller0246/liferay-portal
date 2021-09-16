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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ObjectLayoutBoxSoap implements Serializable {

	public static ObjectLayoutBoxSoap toSoapModel(ObjectLayoutBox model) {
		ObjectLayoutBoxSoap soapModel = new ObjectLayoutBoxSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setObjectLayoutBoxId(model.getObjectLayoutBoxId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setObjectLayoutTabId(model.getObjectLayoutTabId());
		soapModel.setCollapsable(model.isCollapsable());
		soapModel.setName(model.getName());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static ObjectLayoutBoxSoap[] toSoapModels(ObjectLayoutBox[] models) {
		ObjectLayoutBoxSoap[] soapModels =
			new ObjectLayoutBoxSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ObjectLayoutBoxSoap[][] toSoapModels(
		ObjectLayoutBox[][] models) {

		ObjectLayoutBoxSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ObjectLayoutBoxSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ObjectLayoutBoxSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ObjectLayoutBoxSoap[] toSoapModels(
		List<ObjectLayoutBox> models) {

		List<ObjectLayoutBoxSoap> soapModels =
			new ArrayList<ObjectLayoutBoxSoap>(models.size());

		for (ObjectLayoutBox model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ObjectLayoutBoxSoap[soapModels.size()]);
	}

	public ObjectLayoutBoxSoap() {
	}

	public long getPrimaryKey() {
		return _objectLayoutBoxId;
	}

	public void setPrimaryKey(long pk) {
		setObjectLayoutBoxId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getObjectLayoutBoxId() {
		return _objectLayoutBoxId;
	}

	public void setObjectLayoutBoxId(long objectLayoutBoxId) {
		_objectLayoutBoxId = objectLayoutBoxId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getObjectLayoutTabId() {
		return _objectLayoutTabId;
	}

	public void setObjectLayoutTabId(long objectLayoutTabId) {
		_objectLayoutTabId = objectLayoutTabId;
	}

	public boolean getCollapsable() {
		return _collapsable;
	}

	public boolean isCollapsable() {
		return _collapsable;
	}

	public void setCollapsable(boolean collapsable) {
		_collapsable = collapsable;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectLayoutBoxId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _objectLayoutTabId;
	private boolean _collapsable;
	private String _name;
	private int _priority;

}
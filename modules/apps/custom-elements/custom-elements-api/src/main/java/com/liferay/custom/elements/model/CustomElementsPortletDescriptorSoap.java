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

package com.liferay.custom.elements.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CustomElementsPortletDescriptorSoap implements Serializable {

	public static CustomElementsPortletDescriptorSoap toSoapModel(
		CustomElementsPortletDescriptor model) {

		CustomElementsPortletDescriptorSoap soapModel =
			new CustomElementsPortletDescriptorSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setCustomElementsPortletDescriptorId(
			model.getCustomElementsPortletDescriptorId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCSSURLs(model.getCSSURLs());
		soapModel.setHTMLElementName(model.getHTMLElementName());
		soapModel.setInstanceable(model.isInstanceable());
		soapModel.setName(model.getName());
		soapModel.setProperties(model.getProperties());

		return soapModel;
	}

	public static CustomElementsPortletDescriptorSoap[] toSoapModels(
		CustomElementsPortletDescriptor[] models) {

		CustomElementsPortletDescriptorSoap[] soapModels =
			new CustomElementsPortletDescriptorSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CustomElementsPortletDescriptorSoap[][] toSoapModels(
		CustomElementsPortletDescriptor[][] models) {

		CustomElementsPortletDescriptorSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CustomElementsPortletDescriptorSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CustomElementsPortletDescriptorSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CustomElementsPortletDescriptorSoap[] toSoapModels(
		List<CustomElementsPortletDescriptor> models) {

		List<CustomElementsPortletDescriptorSoap> soapModels =
			new ArrayList<CustomElementsPortletDescriptorSoap>(models.size());

		for (CustomElementsPortletDescriptor model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CustomElementsPortletDescriptorSoap[soapModels.size()]);
	}

	public CustomElementsPortletDescriptorSoap() {
	}

	public long getPrimaryKey() {
		return _customElementsPortletDescriptorId;
	}

	public void setPrimaryKey(long pk) {
		setCustomElementsPortletDescriptorId(pk);
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

	public long getCustomElementsPortletDescriptorId() {
		return _customElementsPortletDescriptorId;
	}

	public void setCustomElementsPortletDescriptorId(
		long customElementsPortletDescriptorId) {

		_customElementsPortletDescriptorId = customElementsPortletDescriptorId;
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

	public String getCSSURLs() {
		return _cssURLs;
	}

	public void setCSSURLs(String cssURLs) {
		_cssURLs = cssURLs;
	}

	public String getHTMLElementName() {
		return _htmlElementName;
	}

	public void setHTMLElementName(String htmlElementName) {
		_htmlElementName = htmlElementName;
	}

	public boolean getInstanceable() {
		return _instanceable;
	}

	public boolean isInstanceable() {
		return _instanceable;
	}

	public void setInstanceable(boolean instanceable) {
		_instanceable = instanceable;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getProperties() {
		return _properties;
	}

	public void setProperties(String properties) {
		_properties = properties;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _customElementsPortletDescriptorId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _cssURLs;
	private String _htmlElementName;
	private boolean _instanceable;
	private String _name;
	private String _properties;

}
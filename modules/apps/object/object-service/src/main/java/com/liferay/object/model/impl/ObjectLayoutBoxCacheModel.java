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

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ObjectLayoutBox in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectLayoutBoxCacheModel
	implements CacheModel<ObjectLayoutBox>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectLayoutBoxCacheModel)) {
			return false;
		}

		ObjectLayoutBoxCacheModel objectLayoutBoxCacheModel =
			(ObjectLayoutBoxCacheModel)object;

		if ((objectLayoutBoxId ==
				objectLayoutBoxCacheModel.objectLayoutBoxId) &&
			(mvccVersion == objectLayoutBoxCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectLayoutBoxId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", objectLayoutBoxId=");
		sb.append(objectLayoutBoxId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", objectLayoutTabId=");
		sb.append(objectLayoutTabId);
		sb.append(", collapsable=");
		sb.append(collapsable);
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectLayoutBox toEntityModel() {
		ObjectLayoutBoxImpl objectLayoutBoxImpl = new ObjectLayoutBoxImpl();

		objectLayoutBoxImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectLayoutBoxImpl.setUuid("");
		}
		else {
			objectLayoutBoxImpl.setUuid(uuid);
		}

		objectLayoutBoxImpl.setObjectLayoutBoxId(objectLayoutBoxId);
		objectLayoutBoxImpl.setCompanyId(companyId);
		objectLayoutBoxImpl.setUserId(userId);

		if (userName == null) {
			objectLayoutBoxImpl.setUserName("");
		}
		else {
			objectLayoutBoxImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectLayoutBoxImpl.setCreateDate(null);
		}
		else {
			objectLayoutBoxImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectLayoutBoxImpl.setModifiedDate(null);
		}
		else {
			objectLayoutBoxImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectLayoutBoxImpl.setObjectLayoutTabId(objectLayoutTabId);
		objectLayoutBoxImpl.setCollapsable(collapsable);

		if (name == null) {
			objectLayoutBoxImpl.setName("");
		}
		else {
			objectLayoutBoxImpl.setName(name);
		}

		objectLayoutBoxImpl.setPriority(priority);

		objectLayoutBoxImpl.resetOriginalValues();

		return objectLayoutBoxImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectLayoutBoxId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectLayoutTabId = objectInput.readLong();

		collapsable = objectInput.readBoolean();
		name = objectInput.readUTF();

		priority = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(objectLayoutBoxId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(objectLayoutTabId);

		objectOutput.writeBoolean(collapsable);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(priority);
	}

	public long mvccVersion;
	public String uuid;
	public long objectLayoutBoxId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectLayoutTabId;
	public boolean collapsable;
	public String name;
	public int priority;

}
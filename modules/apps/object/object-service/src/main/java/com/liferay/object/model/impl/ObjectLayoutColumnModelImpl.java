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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.object.model.ObjectLayoutColumn;
import com.liferay.object.model.ObjectLayoutColumnModel;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Blob;
import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the ObjectLayoutColumn service. Represents a row in the &quot;ObjectLayoutColumn&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>ObjectLayoutColumnModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ObjectLayoutColumnImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutColumnImpl
 * @generated
 */
public class ObjectLayoutColumnModelImpl
	extends BaseModelImpl<ObjectLayoutColumn>
	implements ObjectLayoutColumnModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a object layout column model instance should use the <code>ObjectLayoutColumn</code> interface instead.
	 */
	public static final String TABLE_NAME = "ObjectLayoutColumn";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"objectLayoutColumnId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"objectFieldId", Types.BIGINT}, {"objectLayoutRowId", Types.BIGINT},
		{"priority", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("objectLayoutColumnId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("objectFieldId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("objectLayoutRowId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table ObjectLayoutColumn (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,objectLayoutColumnId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,objectFieldId LONG,objectLayoutRowId LONG,priority INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table ObjectLayoutColumn";

	public static final String ORDER_BY_JPQL =
		" ORDER BY objectLayoutColumn.objectLayoutColumnId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY ObjectLayoutColumn.objectLayoutColumnId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long OBJECTLAYOUTROWID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long UUID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long OBJECTLAYOUTCOLUMNID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
	}

	public ObjectLayoutColumnModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _objectLayoutColumnId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setObjectLayoutColumnId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _objectLayoutColumnId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return ObjectLayoutColumn.class;
	}

	@Override
	public String getModelClassName() {
		return ObjectLayoutColumn.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<ObjectLayoutColumn, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<ObjectLayoutColumn, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ObjectLayoutColumn, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((ObjectLayoutColumn)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<ObjectLayoutColumn, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<ObjectLayoutColumn, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(ObjectLayoutColumn)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<ObjectLayoutColumn, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<ObjectLayoutColumn, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, ObjectLayoutColumn>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			ObjectLayoutColumn.class.getClassLoader(), ObjectLayoutColumn.class,
			ModelWrapper.class);

		try {
			Constructor<ObjectLayoutColumn> constructor =
				(Constructor<ObjectLayoutColumn>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private static final Map<String, Function<ObjectLayoutColumn, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<ObjectLayoutColumn, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<ObjectLayoutColumn, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<ObjectLayoutColumn, Object>>();
		Map<String, BiConsumer<ObjectLayoutColumn, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<ObjectLayoutColumn, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", ObjectLayoutColumn::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<ObjectLayoutColumn, Long>)
				ObjectLayoutColumn::setMvccVersion);
		attributeGetterFunctions.put("uuid", ObjectLayoutColumn::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<ObjectLayoutColumn, String>)
				ObjectLayoutColumn::setUuid);
		attributeGetterFunctions.put(
			"objectLayoutColumnId",
			ObjectLayoutColumn::getObjectLayoutColumnId);
		attributeSetterBiConsumers.put(
			"objectLayoutColumnId",
			(BiConsumer<ObjectLayoutColumn, Long>)
				ObjectLayoutColumn::setObjectLayoutColumnId);
		attributeGetterFunctions.put(
			"companyId", ObjectLayoutColumn::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<ObjectLayoutColumn, Long>)
				ObjectLayoutColumn::setCompanyId);
		attributeGetterFunctions.put("userId", ObjectLayoutColumn::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<ObjectLayoutColumn, Long>)
				ObjectLayoutColumn::setUserId);
		attributeGetterFunctions.put(
			"userName", ObjectLayoutColumn::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<ObjectLayoutColumn, String>)
				ObjectLayoutColumn::setUserName);
		attributeGetterFunctions.put(
			"createDate", ObjectLayoutColumn::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<ObjectLayoutColumn, Date>)
				ObjectLayoutColumn::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", ObjectLayoutColumn::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<ObjectLayoutColumn, Date>)
				ObjectLayoutColumn::setModifiedDate);
		attributeGetterFunctions.put(
			"objectFieldId", ObjectLayoutColumn::getObjectFieldId);
		attributeSetterBiConsumers.put(
			"objectFieldId",
			(BiConsumer<ObjectLayoutColumn, Long>)
				ObjectLayoutColumn::setObjectFieldId);
		attributeGetterFunctions.put(
			"objectLayoutRowId", ObjectLayoutColumn::getObjectLayoutRowId);
		attributeSetterBiConsumers.put(
			"objectLayoutRowId",
			(BiConsumer<ObjectLayoutColumn, Long>)
				ObjectLayoutColumn::setObjectLayoutRowId);
		attributeGetterFunctions.put(
			"priority", ObjectLayoutColumn::getPriority);
		attributeSetterBiConsumers.put(
			"priority",
			(BiConsumer<ObjectLayoutColumn, Integer>)
				ObjectLayoutColumn::setPriority);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_mvccVersion = mvccVersion;
	}

	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_uuid = uuid;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalUuid() {
		return getColumnOriginalValue("uuid_");
	}

	@Override
	public long getObjectLayoutColumnId() {
		return _objectLayoutColumnId;
	}

	@Override
	public void setObjectLayoutColumnId(long objectLayoutColumnId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_objectLayoutColumnId = objectLayoutColumnId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_companyId = companyId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCompanyId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("companyId"));
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userName = userName;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_modifiedDate = modifiedDate;
	}

	@Override
	public long getObjectFieldId() {
		return _objectFieldId;
	}

	@Override
	public void setObjectFieldId(long objectFieldId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_objectFieldId = objectFieldId;
	}

	@Override
	public long getObjectLayoutRowId() {
		return _objectLayoutRowId;
	}

	@Override
	public void setObjectLayoutRowId(long objectLayoutRowId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_objectLayoutRowId = objectLayoutRowId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalObjectLayoutRowId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("objectLayoutRowId"));
	}

	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public void setPriority(int priority) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_priority = priority;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(ObjectLayoutColumn.class.getName()));
	}

	public long getColumnBitmask() {
		if (_columnBitmask > 0) {
			return _columnBitmask;
		}

		if ((_columnOriginalValues == null) ||
			(_columnOriginalValues == Collections.EMPTY_MAP)) {

			return 0;
		}

		for (Map.Entry<String, Object> entry :
				_columnOriginalValues.entrySet()) {

			if (!Objects.equals(
					entry.getValue(), getColumnValue(entry.getKey()))) {

				_columnBitmask |= _columnBitmasks.get(entry.getKey());
			}
		}

		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), ObjectLayoutColumn.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public ObjectLayoutColumn toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, ObjectLayoutColumn>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		ObjectLayoutColumnImpl objectLayoutColumnImpl =
			new ObjectLayoutColumnImpl();

		objectLayoutColumnImpl.setMvccVersion(getMvccVersion());
		objectLayoutColumnImpl.setUuid(getUuid());
		objectLayoutColumnImpl.setObjectLayoutColumnId(
			getObjectLayoutColumnId());
		objectLayoutColumnImpl.setCompanyId(getCompanyId());
		objectLayoutColumnImpl.setUserId(getUserId());
		objectLayoutColumnImpl.setUserName(getUserName());
		objectLayoutColumnImpl.setCreateDate(getCreateDate());
		objectLayoutColumnImpl.setModifiedDate(getModifiedDate());
		objectLayoutColumnImpl.setObjectFieldId(getObjectFieldId());
		objectLayoutColumnImpl.setObjectLayoutRowId(getObjectLayoutRowId());
		objectLayoutColumnImpl.setPriority(getPriority());

		objectLayoutColumnImpl.resetOriginalValues();

		return objectLayoutColumnImpl;
	}

	@Override
	public ObjectLayoutColumn cloneWithOriginalValues() {
		ObjectLayoutColumnImpl objectLayoutColumnImpl =
			new ObjectLayoutColumnImpl();

		objectLayoutColumnImpl.setMvccVersion(
			this.<Long>getColumnOriginalValue("mvccVersion"));
		objectLayoutColumnImpl.setUuid(
			this.<String>getColumnOriginalValue("uuid_"));
		objectLayoutColumnImpl.setObjectLayoutColumnId(
			this.<Long>getColumnOriginalValue("objectLayoutColumnId"));
		objectLayoutColumnImpl.setCompanyId(
			this.<Long>getColumnOriginalValue("companyId"));
		objectLayoutColumnImpl.setUserId(
			this.<Long>getColumnOriginalValue("userId"));
		objectLayoutColumnImpl.setUserName(
			this.<String>getColumnOriginalValue("userName"));
		objectLayoutColumnImpl.setCreateDate(
			this.<Date>getColumnOriginalValue("createDate"));
		objectLayoutColumnImpl.setModifiedDate(
			this.<Date>getColumnOriginalValue("modifiedDate"));
		objectLayoutColumnImpl.setObjectFieldId(
			this.<Long>getColumnOriginalValue("objectFieldId"));
		objectLayoutColumnImpl.setObjectLayoutRowId(
			this.<Long>getColumnOriginalValue("objectLayoutRowId"));
		objectLayoutColumnImpl.setPriority(
			this.<Integer>getColumnOriginalValue("priority"));

		return objectLayoutColumnImpl;
	}

	@Override
	public int compareTo(ObjectLayoutColumn objectLayoutColumn) {
		long primaryKey = objectLayoutColumn.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectLayoutColumn)) {
			return false;
		}

		ObjectLayoutColumn objectLayoutColumn = (ObjectLayoutColumn)object;

		long primaryKey = objectLayoutColumn.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isEntityCacheEnabled() {
		return true;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return true;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<ObjectLayoutColumn> toCacheModel() {
		ObjectLayoutColumnCacheModel objectLayoutColumnCacheModel =
			new ObjectLayoutColumnCacheModel();

		objectLayoutColumnCacheModel.mvccVersion = getMvccVersion();

		objectLayoutColumnCacheModel.uuid = getUuid();

		String uuid = objectLayoutColumnCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			objectLayoutColumnCacheModel.uuid = null;
		}

		objectLayoutColumnCacheModel.objectLayoutColumnId =
			getObjectLayoutColumnId();

		objectLayoutColumnCacheModel.companyId = getCompanyId();

		objectLayoutColumnCacheModel.userId = getUserId();

		objectLayoutColumnCacheModel.userName = getUserName();

		String userName = objectLayoutColumnCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			objectLayoutColumnCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			objectLayoutColumnCacheModel.createDate = createDate.getTime();
		}
		else {
			objectLayoutColumnCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			objectLayoutColumnCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			objectLayoutColumnCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		objectLayoutColumnCacheModel.objectFieldId = getObjectFieldId();

		objectLayoutColumnCacheModel.objectLayoutRowId = getObjectLayoutRowId();

		objectLayoutColumnCacheModel.priority = getPriority();

		return objectLayoutColumnCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<ObjectLayoutColumn, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<ObjectLayoutColumn, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ObjectLayoutColumn, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("\"");
			sb.append(attributeName);
			sb.append("\": ");

			Object value = attributeGetterFunction.apply(
				(ObjectLayoutColumn)this);

			if (value == null) {
				sb.append("null");
			}
			else if (value instanceof Blob || value instanceof Date ||
					 value instanceof Map || value instanceof String) {

				sb.append(
					"\"" + StringUtil.replace(value.toString(), "\"", "'") +
						"\"");
			}
			else {
				sb.append(value);
			}

			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<ObjectLayoutColumn, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<ObjectLayoutColumn, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ObjectLayoutColumn, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((ObjectLayoutColumn)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, ObjectLayoutColumn>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private String _uuid;
	private long _objectLayoutColumnId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _objectFieldId;
	private long _objectLayoutRowId;
	private int _priority;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<ObjectLayoutColumn, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((ObjectLayoutColumn)this);
	}

	public <T> T getColumnOriginalValue(String columnName) {
		if (_columnOriginalValues == null) {
			return null;
		}

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		return (T)_columnOriginalValues.get(columnName);
	}

	private void _setColumnOriginalValues() {
		_columnOriginalValues = new HashMap<String, Object>();

		_columnOriginalValues.put("mvccVersion", _mvccVersion);
		_columnOriginalValues.put("uuid_", _uuid);
		_columnOriginalValues.put(
			"objectLayoutColumnId", _objectLayoutColumnId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("objectFieldId", _objectFieldId);
		_columnOriginalValues.put("objectLayoutRowId", _objectLayoutRowId);
		_columnOriginalValues.put("priority", _priority);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("uuid_", "uuid");

		_attributeNames = Collections.unmodifiableMap(attributeNames);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("mvccVersion", 1L);

		columnBitmasks.put("uuid_", 2L);

		columnBitmasks.put("objectLayoutColumnId", 4L);

		columnBitmasks.put("companyId", 8L);

		columnBitmasks.put("userId", 16L);

		columnBitmasks.put("userName", 32L);

		columnBitmasks.put("createDate", 64L);

		columnBitmasks.put("modifiedDate", 128L);

		columnBitmasks.put("objectFieldId", 256L);

		columnBitmasks.put("objectLayoutRowId", 512L);

		columnBitmasks.put("priority", 1024L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private ObjectLayoutColumn _escapedModel;

}
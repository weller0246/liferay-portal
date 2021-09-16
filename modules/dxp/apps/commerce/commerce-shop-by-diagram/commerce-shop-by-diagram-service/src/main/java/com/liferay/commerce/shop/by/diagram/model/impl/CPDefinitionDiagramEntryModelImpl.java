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

package com.liferay.commerce.shop.by.diagram.model.impl;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntryModel;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntrySoap;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Blob;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the CPDefinitionDiagramEntry service. Represents a row in the &quot;CPDefinitionDiagramEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>CPDefinitionDiagramEntryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CPDefinitionDiagramEntryImpl}.
 * </p>
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramEntryImpl
 * @generated
 */
@JSON(strict = true)
public class CPDefinitionDiagramEntryModelImpl
	extends BaseModelImpl<CPDefinitionDiagramEntry>
	implements CPDefinitionDiagramEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cp definition diagram entry model instance should use the <code>CPDefinitionDiagramEntry</code> interface instead.
	 */
	public static final String TABLE_NAME = "CPDefinitionDiagramEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"CPDefinitionDiagramEntryId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"CPDefinitionId", Types.BIGINT},
		{"CPInstanceUuid", Types.VARCHAR}, {"CProductId", Types.BIGINT},
		{"diagram", Types.BOOLEAN}, {"quantity", Types.INTEGER},
		{"sequence", Types.VARCHAR}, {"sku", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("CPDefinitionDiagramEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("CPDefinitionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("CPInstanceUuid", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("CProductId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("diagram", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("quantity", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("sequence", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("sku", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table CPDefinitionDiagramEntry (CPDefinitionDiagramEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,CPDefinitionId LONG,CPInstanceUuid VARCHAR(75) null,CProductId LONG,diagram BOOLEAN,quantity INTEGER,sequence VARCHAR(75) null,sku VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP =
		"drop table CPDefinitionDiagramEntry";

	public static final String ORDER_BY_JPQL =
		" ORDER BY cpDefinitionDiagramEntry.sequence ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY CPDefinitionDiagramEntry.sequence ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long CPDEFINITIONID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long SEQUENCE_COLUMN_BITMASK = 2L;

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

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static CPDefinitionDiagramEntry toModel(
		CPDefinitionDiagramEntrySoap soapModel) {

		if (soapModel == null) {
			return null;
		}

		CPDefinitionDiagramEntry model = new CPDefinitionDiagramEntryImpl();

		model.setCPDefinitionDiagramEntryId(
			soapModel.getCPDefinitionDiagramEntryId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCPDefinitionId(soapModel.getCPDefinitionId());
		model.setCPInstanceUuid(soapModel.getCPInstanceUuid());
		model.setCProductId(soapModel.getCProductId());
		model.setDiagram(soapModel.isDiagram());
		model.setQuantity(soapModel.getQuantity());
		model.setSequence(soapModel.getSequence());
		model.setSku(soapModel.getSku());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static List<CPDefinitionDiagramEntry> toModels(
		CPDefinitionDiagramEntrySoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<CPDefinitionDiagramEntry> models =
			new ArrayList<CPDefinitionDiagramEntry>(soapModels.length);

		for (CPDefinitionDiagramEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public CPDefinitionDiagramEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _CPDefinitionDiagramEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCPDefinitionDiagramEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _CPDefinitionDiagramEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinitionDiagramEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinitionDiagramEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<CPDefinitionDiagramEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<CPDefinitionDiagramEntry, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CPDefinitionDiagramEntry, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((CPDefinitionDiagramEntry)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<CPDefinitionDiagramEntry, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<CPDefinitionDiagramEntry, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(CPDefinitionDiagramEntry)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<CPDefinitionDiagramEntry, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<CPDefinitionDiagramEntry, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, CPDefinitionDiagramEntry>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			CPDefinitionDiagramEntry.class.getClassLoader(),
			CPDefinitionDiagramEntry.class, ModelWrapper.class);

		try {
			Constructor<CPDefinitionDiagramEntry> constructor =
				(Constructor<CPDefinitionDiagramEntry>)
					proxyClass.getConstructor(InvocationHandler.class);

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

	private static final Map<String, Function<CPDefinitionDiagramEntry, Object>>
		_attributeGetterFunctions;
	private static final Map
		<String, BiConsumer<CPDefinitionDiagramEntry, Object>>
			_attributeSetterBiConsumers;

	static {
		Map<String, Function<CPDefinitionDiagramEntry, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<CPDefinitionDiagramEntry, Object>>();
		Map<String, BiConsumer<CPDefinitionDiagramEntry, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<CPDefinitionDiagramEntry, ?>>();

		attributeGetterFunctions.put(
			"CPDefinitionDiagramEntryId",
			CPDefinitionDiagramEntry::getCPDefinitionDiagramEntryId);
		attributeSetterBiConsumers.put(
			"CPDefinitionDiagramEntryId",
			(BiConsumer<CPDefinitionDiagramEntry, Long>)
				CPDefinitionDiagramEntry::setCPDefinitionDiagramEntryId);
		attributeGetterFunctions.put(
			"companyId", CPDefinitionDiagramEntry::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<CPDefinitionDiagramEntry, Long>)
				CPDefinitionDiagramEntry::setCompanyId);
		attributeGetterFunctions.put(
			"userId", CPDefinitionDiagramEntry::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<CPDefinitionDiagramEntry, Long>)
				CPDefinitionDiagramEntry::setUserId);
		attributeGetterFunctions.put(
			"userName", CPDefinitionDiagramEntry::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<CPDefinitionDiagramEntry, String>)
				CPDefinitionDiagramEntry::setUserName);
		attributeGetterFunctions.put(
			"createDate", CPDefinitionDiagramEntry::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<CPDefinitionDiagramEntry, Date>)
				CPDefinitionDiagramEntry::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", CPDefinitionDiagramEntry::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<CPDefinitionDiagramEntry, Date>)
				CPDefinitionDiagramEntry::setModifiedDate);
		attributeGetterFunctions.put(
			"CPDefinitionId", CPDefinitionDiagramEntry::getCPDefinitionId);
		attributeSetterBiConsumers.put(
			"CPDefinitionId",
			(BiConsumer<CPDefinitionDiagramEntry, Long>)
				CPDefinitionDiagramEntry::setCPDefinitionId);
		attributeGetterFunctions.put(
			"CPInstanceUuid", CPDefinitionDiagramEntry::getCPInstanceUuid);
		attributeSetterBiConsumers.put(
			"CPInstanceUuid",
			(BiConsumer<CPDefinitionDiagramEntry, String>)
				CPDefinitionDiagramEntry::setCPInstanceUuid);
		attributeGetterFunctions.put(
			"CProductId", CPDefinitionDiagramEntry::getCProductId);
		attributeSetterBiConsumers.put(
			"CProductId",
			(BiConsumer<CPDefinitionDiagramEntry, Long>)
				CPDefinitionDiagramEntry::setCProductId);
		attributeGetterFunctions.put(
			"diagram", CPDefinitionDiagramEntry::getDiagram);
		attributeSetterBiConsumers.put(
			"diagram",
			(BiConsumer<CPDefinitionDiagramEntry, Boolean>)
				CPDefinitionDiagramEntry::setDiagram);
		attributeGetterFunctions.put(
			"quantity", CPDefinitionDiagramEntry::getQuantity);
		attributeSetterBiConsumers.put(
			"quantity",
			(BiConsumer<CPDefinitionDiagramEntry, Integer>)
				CPDefinitionDiagramEntry::setQuantity);
		attributeGetterFunctions.put(
			"sequence", CPDefinitionDiagramEntry::getSequence);
		attributeSetterBiConsumers.put(
			"sequence",
			(BiConsumer<CPDefinitionDiagramEntry, String>)
				CPDefinitionDiagramEntry::setSequence);
		attributeGetterFunctions.put("sku", CPDefinitionDiagramEntry::getSku);
		attributeSetterBiConsumers.put(
			"sku",
			(BiConsumer<CPDefinitionDiagramEntry, String>)
				CPDefinitionDiagramEntry::setSku);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getCPDefinitionDiagramEntryId() {
		return _CPDefinitionDiagramEntryId;
	}

	@Override
	public void setCPDefinitionDiagramEntryId(long CPDefinitionDiagramEntryId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_CPDefinitionDiagramEntryId = CPDefinitionDiagramEntryId;
	}

	@JSON
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

	@JSON
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

	@JSON
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

	@JSON
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

	@JSON
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

	@JSON
	@Override
	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_CPDefinitionId = CPDefinitionId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCPDefinitionId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("CPDefinitionId"));
	}

	@JSON
	@Override
	public String getCPInstanceUuid() {
		if (_CPInstanceUuid == null) {
			return "";
		}
		else {
			return _CPInstanceUuid;
		}
	}

	@Override
	public void setCPInstanceUuid(String CPInstanceUuid) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_CPInstanceUuid = CPInstanceUuid;
	}

	@JSON
	@Override
	public long getCProductId() {
		return _CProductId;
	}

	@Override
	public void setCProductId(long CProductId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_CProductId = CProductId;
	}

	@JSON
	@Override
	public boolean getDiagram() {
		return _diagram;
	}

	@JSON
	@Override
	public boolean isDiagram() {
		return _diagram;
	}

	@Override
	public void setDiagram(boolean diagram) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_diagram = diagram;
	}

	@JSON
	@Override
	public int getQuantity() {
		return _quantity;
	}

	@Override
	public void setQuantity(int quantity) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_quantity = quantity;
	}

	@JSON
	@Override
	public String getSequence() {
		if (_sequence == null) {
			return "";
		}
		else {
			return _sequence;
		}
	}

	@Override
	public void setSequence(String sequence) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_sequence = sequence;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalSequence() {
		return getColumnOriginalValue("sequence");
	}

	@JSON
	@Override
	public String getSku() {
		if (_sku == null) {
			return "";
		}
		else {
			return _sku;
		}
	}

	@Override
	public void setSku(String sku) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_sku = sku;
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
			getCompanyId(), CPDefinitionDiagramEntry.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CPDefinitionDiagramEntry toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, CPDefinitionDiagramEntry>
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
		CPDefinitionDiagramEntryImpl cpDefinitionDiagramEntryImpl =
			new CPDefinitionDiagramEntryImpl();

		cpDefinitionDiagramEntryImpl.setCPDefinitionDiagramEntryId(
			getCPDefinitionDiagramEntryId());
		cpDefinitionDiagramEntryImpl.setCompanyId(getCompanyId());
		cpDefinitionDiagramEntryImpl.setUserId(getUserId());
		cpDefinitionDiagramEntryImpl.setUserName(getUserName());
		cpDefinitionDiagramEntryImpl.setCreateDate(getCreateDate());
		cpDefinitionDiagramEntryImpl.setModifiedDate(getModifiedDate());
		cpDefinitionDiagramEntryImpl.setCPDefinitionId(getCPDefinitionId());
		cpDefinitionDiagramEntryImpl.setCPInstanceUuid(getCPInstanceUuid());
		cpDefinitionDiagramEntryImpl.setCProductId(getCProductId());
		cpDefinitionDiagramEntryImpl.setDiagram(isDiagram());
		cpDefinitionDiagramEntryImpl.setQuantity(getQuantity());
		cpDefinitionDiagramEntryImpl.setSequence(getSequence());
		cpDefinitionDiagramEntryImpl.setSku(getSku());

		cpDefinitionDiagramEntryImpl.resetOriginalValues();

		return cpDefinitionDiagramEntryImpl;
	}

	@Override
	public CPDefinitionDiagramEntry cloneWithOriginalValues() {
		CPDefinitionDiagramEntryImpl cpDefinitionDiagramEntryImpl =
			new CPDefinitionDiagramEntryImpl();

		cpDefinitionDiagramEntryImpl.setCPDefinitionDiagramEntryId(
			this.<Long>getColumnOriginalValue("CPDefinitionDiagramEntryId"));
		cpDefinitionDiagramEntryImpl.setCompanyId(
			this.<Long>getColumnOriginalValue("companyId"));
		cpDefinitionDiagramEntryImpl.setUserId(
			this.<Long>getColumnOriginalValue("userId"));
		cpDefinitionDiagramEntryImpl.setUserName(
			this.<String>getColumnOriginalValue("userName"));
		cpDefinitionDiagramEntryImpl.setCreateDate(
			this.<Date>getColumnOriginalValue("createDate"));
		cpDefinitionDiagramEntryImpl.setModifiedDate(
			this.<Date>getColumnOriginalValue("modifiedDate"));
		cpDefinitionDiagramEntryImpl.setCPDefinitionId(
			this.<Long>getColumnOriginalValue("CPDefinitionId"));
		cpDefinitionDiagramEntryImpl.setCPInstanceUuid(
			this.<String>getColumnOriginalValue("CPInstanceUuid"));
		cpDefinitionDiagramEntryImpl.setCProductId(
			this.<Long>getColumnOriginalValue("CProductId"));
		cpDefinitionDiagramEntryImpl.setDiagram(
			this.<Boolean>getColumnOriginalValue("diagram"));
		cpDefinitionDiagramEntryImpl.setQuantity(
			this.<Integer>getColumnOriginalValue("quantity"));
		cpDefinitionDiagramEntryImpl.setSequence(
			this.<String>getColumnOriginalValue("sequence"));
		cpDefinitionDiagramEntryImpl.setSku(
			this.<String>getColumnOriginalValue("sku"));

		return cpDefinitionDiagramEntryImpl;
	}

	@Override
	public int compareTo(CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {
		int value = 0;

		value = getSequence().compareTo(cpDefinitionDiagramEntry.getSequence());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDefinitionDiagramEntry)) {
			return false;
		}

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			(CPDefinitionDiagramEntry)object;

		long primaryKey = cpDefinitionDiagramEntry.getPrimaryKey();

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
	public CacheModel<CPDefinitionDiagramEntry> toCacheModel() {
		CPDefinitionDiagramEntryCacheModel cpDefinitionDiagramEntryCacheModel =
			new CPDefinitionDiagramEntryCacheModel();

		cpDefinitionDiagramEntryCacheModel.CPDefinitionDiagramEntryId =
			getCPDefinitionDiagramEntryId();

		cpDefinitionDiagramEntryCacheModel.companyId = getCompanyId();

		cpDefinitionDiagramEntryCacheModel.userId = getUserId();

		cpDefinitionDiagramEntryCacheModel.userName = getUserName();

		String userName = cpDefinitionDiagramEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			cpDefinitionDiagramEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			cpDefinitionDiagramEntryCacheModel.createDate =
				createDate.getTime();
		}
		else {
			cpDefinitionDiagramEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			cpDefinitionDiagramEntryCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			cpDefinitionDiagramEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		cpDefinitionDiagramEntryCacheModel.CPDefinitionId = getCPDefinitionId();

		cpDefinitionDiagramEntryCacheModel.CPInstanceUuid = getCPInstanceUuid();

		String CPInstanceUuid =
			cpDefinitionDiagramEntryCacheModel.CPInstanceUuid;

		if ((CPInstanceUuid != null) && (CPInstanceUuid.length() == 0)) {
			cpDefinitionDiagramEntryCacheModel.CPInstanceUuid = null;
		}

		cpDefinitionDiagramEntryCacheModel.CProductId = getCProductId();

		cpDefinitionDiagramEntryCacheModel.diagram = isDiagram();

		cpDefinitionDiagramEntryCacheModel.quantity = getQuantity();

		cpDefinitionDiagramEntryCacheModel.sequence = getSequence();

		String sequence = cpDefinitionDiagramEntryCacheModel.sequence;

		if ((sequence != null) && (sequence.length() == 0)) {
			cpDefinitionDiagramEntryCacheModel.sequence = null;
		}

		cpDefinitionDiagramEntryCacheModel.sku = getSku();

		String sku = cpDefinitionDiagramEntryCacheModel.sku;

		if ((sku != null) && (sku.length() == 0)) {
			cpDefinitionDiagramEntryCacheModel.sku = null;
		}

		return cpDefinitionDiagramEntryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<CPDefinitionDiagramEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<CPDefinitionDiagramEntry, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CPDefinitionDiagramEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("\"");
			sb.append(attributeName);
			sb.append("\": ");

			Object value = attributeGetterFunction.apply(
				(CPDefinitionDiagramEntry)this);

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
		Map<String, Function<CPDefinitionDiagramEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<CPDefinitionDiagramEntry, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CPDefinitionDiagramEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((CPDefinitionDiagramEntry)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, CPDefinitionDiagramEntry>
				_escapedModelProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private long _CPDefinitionDiagramEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _CPDefinitionId;
	private String _CPInstanceUuid;
	private long _CProductId;
	private boolean _diagram;
	private int _quantity;
	private String _sequence;
	private String _sku;

	public <T> T getColumnValue(String columnName) {
		Function<CPDefinitionDiagramEntry, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((CPDefinitionDiagramEntry)this);
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

		_columnOriginalValues.put(
			"CPDefinitionDiagramEntryId", _CPDefinitionDiagramEntryId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("CPDefinitionId", _CPDefinitionId);
		_columnOriginalValues.put("CPInstanceUuid", _CPInstanceUuid);
		_columnOriginalValues.put("CProductId", _CProductId);
		_columnOriginalValues.put("diagram", _diagram);
		_columnOriginalValues.put("quantity", _quantity);
		_columnOriginalValues.put("sequence", _sequence);
		_columnOriginalValues.put("sku", _sku);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("CPDefinitionDiagramEntryId", 1L);

		columnBitmasks.put("companyId", 2L);

		columnBitmasks.put("userId", 4L);

		columnBitmasks.put("userName", 8L);

		columnBitmasks.put("createDate", 16L);

		columnBitmasks.put("modifiedDate", 32L);

		columnBitmasks.put("CPDefinitionId", 64L);

		columnBitmasks.put("CPInstanceUuid", 128L);

		columnBitmasks.put("CProductId", 256L);

		columnBitmasks.put("diagram", 512L);

		columnBitmasks.put("quantity", 1024L);

		columnBitmasks.put("sequence", 2048L);

		columnBitmasks.put("sku", 4096L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private CPDefinitionDiagramEntry _escapedModel;

}
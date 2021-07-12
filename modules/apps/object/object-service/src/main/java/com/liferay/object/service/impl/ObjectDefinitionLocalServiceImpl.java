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

package com.liferay.object.service.impl;

import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.exception.DuplicateObjectDefinitionException;
import com.liferay.object.exception.ObjectDefinitionNameException;
import com.liferay.object.exception.ObjectDefinitionVersionException;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.impl.ObjectDefinitionImpl;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.base.ObjectDefinitionLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectEntryPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectDefinition",
	service = AopService.class
)
public class ObjectDefinitionLocalServiceImpl
	extends ObjectDefinitionLocalServiceBaseImpl {

	@Override
	public ObjectDefinition addCustomObjectDefinition(
			long userId, String name, List<ObjectField> objectFields)
		throws PortalException {

		return _addObjectDefinition(
			userId, null, name, null, null, false, 0, objectFields);
	}

	@Override
	public ObjectDefinition addOrUpdateSystemObjectDefinition(
			long companyId,
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.fetchByC_N(
				companyId, systemObjectDefinitionMetadata.getName());

		if ((objectDefinition != null) &&
			(objectDefinition.getVersion() ==
				systemObjectDefinitionMetadata.getVersion())) {

			return objectDefinition;
		}

		long userId = _userLocalService.getDefaultUserId(companyId);

		if (objectDefinition == null) {
			return addSystemObjectDefinition(
				userId, systemObjectDefinitionMetadata.getDBTableName(),
				systemObjectDefinitionMetadata.getName(),
				systemObjectDefinitionMetadata.getPKObjectFieldDBColumnName(),
				systemObjectDefinitionMetadata.getPKObjectFieldName(),
				systemObjectDefinitionMetadata.getVersion(),
				systemObjectDefinitionMetadata.getObjectFields());
		}

		objectDefinition.setVersion(
			systemObjectDefinitionMetadata.getVersion());

		objectDefinition = objectDefinitionPersistence.update(objectDefinition);

		List<ObjectField> newObjectFields =
			systemObjectDefinitionMetadata.getObjectFields();

		List<ObjectField> oldObjectFields =
			_objectFieldPersistence.findByObjectDefinitionId(
				objectDefinition.getObjectDefinitionId());

		for (ObjectField oldObjectField : oldObjectFields) {
			if (!_hasObjectField(newObjectFields, oldObjectField)) {
				_objectFieldPersistence.remove(oldObjectField);
			}
		}

		for (ObjectField newObjectField : newObjectFields) {
			ObjectField oldObjectField = _objectFieldPersistence.fetchByODI_N(
				objectDefinition.getObjectDefinitionId(),
				newObjectField.getName());

			if (oldObjectField == null) {
				_objectFieldLocalService.addObjectField(
					userId, objectDefinition.getObjectDefinitionId(),
					newObjectField.getDBColumnName(), false, false, "",
					newObjectField.getName(), newObjectField.isRequired(),
					newObjectField.getType());
			}
			else {
				if (!Objects.equals(oldObjectField, newObjectField.getType())) {
					oldObjectField.setRequired(newObjectField.isRequired());
					oldObjectField.setType(newObjectField.getType());

					_objectFieldPersistence.update(oldObjectField);
				}
			}
		}

		return objectDefinition;
	}

	@Override
	public ObjectDefinition addSystemObjectDefinition(
			long userId, String dbTableName, String name,
			String pkObjectFieldDBColumnName, String pkObjectFieldName,
			int version, List<ObjectField> objectFields)
		throws PortalException {

		return _addObjectDefinition(
			userId, dbTableName, name, pkObjectFieldDBColumnName,
			pkObjectFieldName, true, version, objectFields);
	}

	@Override
	public void deleteCompanyObjectDefinitions(long companyId)
		throws PortalException {

		List<ObjectDefinition> objectDefinitions =
			objectDefinitionPersistence.findByCompanyId(companyId);

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			deleteObjectDefinition(objectDefinition);
		}
	}

	@Override
	public ObjectDefinition deleteObjectDefinition(long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		return deleteObjectDefinition(objectDefinition);
	}

	@Override
	public ObjectDefinition deleteObjectDefinition(
			ObjectDefinition objectDefinition)
		throws PortalException {

		long objectDefinitionId = objectDefinition.getObjectDefinitionId();

		if (!objectDefinition.isSystem()) {
			List<ObjectEntry> objectEntries =
				_objectEntryPersistence.findByObjectDefinitionId(
					objectDefinitionId);

			for (ObjectEntry objectEntry : objectEntries) {
				_objectEntryLocalService.deleteObjectEntry(objectEntry);
			}
		}

		_objectFieldPersistence.removeByObjectDefinitionId(objectDefinitionId);

		objectDefinitionPersistence.remove(objectDefinition);

		if (!objectDefinition.isSystem()) {
			_dropTable(objectDefinition);

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					objectDefinitionLocalService.undeployObjectDefinition(
						objectDefinition);

					return null;
				});
		}

		return objectDefinition;
	}

	@Clusterable
	@Override
	public void deployObjectDefinition(ObjectDefinition objectDefinition) {
		if (objectDefinition.isSystem()) {
			return;
		}

		for (Map.Entry
				<ObjectDefinitionDeployer,
				 Map<Long, List<ServiceRegistration<?>>>> entry :
					_serviceRegistrationsMaps.entrySet()) {

			ObjectDefinitionDeployer objectDefinitionDeployer = entry.getKey();
			Map<Long, List<ServiceRegistration<?>>> serviceRegistrationsMap =
				entry.getValue();

			serviceRegistrationsMap.computeIfAbsent(
				objectDefinition.getObjectDefinitionId(),
				objectDefinitionId -> objectDefinitionDeployer.deploy(
					objectDefinition));
		}
	}

	@Override
	public List<ObjectDefinition> getCustomObjectDefinitions() {
		return objectDefinitionPersistence.findBySystem(false);
	}

	@Override
	public ObjectDefinition getObjectDefinition(long objectDefinitionId)
		throws PortalException {

		return objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);
	}

	@Override
	public int getObjectDefinitionsCount(long companyId)
		throws PortalException {

		return objectDefinitionPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<ObjectDefinition> getSystemObjectDefinitions() {
		return objectDefinitionPersistence.findBySystem(true);
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		super.setAopProxy(aopProxy);

		_objectDefinitionDeployerServiceTracker = new ServiceTracker<>(
			_bundleContext, ObjectDefinitionDeployer.class,
			new ServiceTrackerCustomizer
				<ObjectDefinitionDeployer, ObjectDefinitionDeployer>() {

				@Override
				public ObjectDefinitionDeployer addingService(
					ServiceReference<ObjectDefinitionDeployer>
						serviceReference) {

					ObjectDefinitionDeployer objectDefinitionDeployer =
						_bundleContext.getService(serviceReference);

					Map<Long, List<ServiceRegistration<?>>>
						serviceRegistrationsMap = new ConcurrentHashMap<>();

					List<ObjectDefinition> objectDefinitions =
						objectDefinitionLocalService.
							getCustomObjectDefinitions();

					for (ObjectDefinition objectDefinition :
							objectDefinitions) {

						serviceRegistrationsMap.put(
							objectDefinition.getObjectDefinitionId(),
							objectDefinitionDeployer.deploy(objectDefinition));
					}

					_serviceRegistrationsMaps.put(
						objectDefinitionDeployer, serviceRegistrationsMap);

					return objectDefinitionDeployer;
				}

				@Override
				public void modifiedService(
					ServiceReference<ObjectDefinitionDeployer> serviceReference,
					ObjectDefinitionDeployer objectDefinitionDeployer) {
				}

				@Override
				public void removedService(
					ServiceReference<ObjectDefinitionDeployer> serviceReference,
					ObjectDefinitionDeployer objectDefinitionDeployer) {

					Map<Long, List<ServiceRegistration<?>>>
						serviceRegistrationsMap =
							_serviceRegistrationsMaps.remove(
								objectDefinitionDeployer);

					for (List<ServiceRegistration<?>> serviceRegistrations :
							serviceRegistrationsMap.values()) {

						for (ServiceRegistration<?> serviceRegistration :
								serviceRegistrations) {

							serviceRegistration.unregister();
						}
					}

					_bundleContext.ungetService(serviceReference);
				}

			});

		_objectDefinitionDeployerServiceTracker.open();
	}

	@Clusterable
	@Override
	public void undeployObjectDefinition(ObjectDefinition objectDefinition) {
		if (objectDefinition.isSystem()) {
			return;
		}

		for (Map.Entry
				<ObjectDefinitionDeployer,
				 Map<Long, List<ServiceRegistration<?>>>> entry :
					_serviceRegistrationsMaps.entrySet()) {

			ObjectDefinitionDeployer objectDefinitionDeployer = entry.getKey();

			objectDefinitionDeployer.undeploy(objectDefinition);

			Map<Long, List<ServiceRegistration<?>>> serviceRegistrationsMap =
				entry.getValue();

			List<ServiceRegistration<?>> serviceRegistrations =
				serviceRegistrationsMap.remove(
					objectDefinition.getObjectDefinitionId());

			if (serviceRegistrations != null) {
				for (ServiceRegistration<?> serviceRegistration :
						serviceRegistrations) {

					serviceRegistration.unregister();
				}
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	@Override
	protected void deactivate() {
		super.deactivate();

		if (_objectDefinitionDeployerServiceTracker != null) {
			_objectDefinitionDeployerServiceTracker.close();
		}
	}

	private ObjectDefinition _addObjectDefinition(
			long userId, String dbTableName, String name,
			String pkObjectFieldDBColumnName, String pkObjectFieldName,
			boolean system, int version, List<ObjectField> objectFields)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		name = StringUtil.trim(name);

		if (!system) {
			name = "C_" + name;
		}

		String shortName = ObjectDefinitionImpl.getShortName(name);

		if (Validator.isNull(dbTableName)) {
			if (system) {
				dbTableName = name;
			}
			else {
				dbTableName = StringBundler.concat(
					"O_", user.getCompanyId(), StringPool.UNDERLINE, shortName);
			}
		}

		if (Validator.isNull(pkObjectFieldName)) {
			pkObjectFieldName = TextFormatter.format(
				shortName + "Id", TextFormatter.I);

			if (!system) {
				pkObjectFieldName = "c_" + pkObjectFieldName;
			}
		}

		if (Validator.isNull(pkObjectFieldDBColumnName)) {
			if (system) {
				pkObjectFieldDBColumnName = pkObjectFieldName;
			}
			else {
				pkObjectFieldDBColumnName =
					pkObjectFieldName + StringPool.UNDERLINE;
			}
		}

		_validateName(user.getCompanyId(), name, system);
		_validateVersion(system, version);

		long objectDefinitionId = counterLocalService.increment();

		ObjectDefinition objectDefinition = objectDefinitionPersistence.create(
			objectDefinitionId);

		objectDefinition.setCompanyId(user.getCompanyId());
		objectDefinition.setUserId(user.getUserId());
		objectDefinition.setUserName(user.getFullName());
		objectDefinition.setDBTableName(dbTableName);
		objectDefinition.setName(name);
		objectDefinition.setPKObjectFieldDBColumnName(
			pkObjectFieldDBColumnName);
		objectDefinition.setPKObjectFieldName(pkObjectFieldName);
		objectDefinition.setSystem(system);
		objectDefinition.setVersion(version);

		ObjectDefinition updatedObjectDefinition =
			objectDefinitionPersistence.update(objectDefinition);

		for (ObjectField objectField : objectFields) {
			_objectFieldLocalService.addObjectField(
				userId, objectDefinitionId, objectField.getDBColumnName(),
				objectField.getIndexed(), objectField.getIndexedAsKeyword(),
				objectField.getIndexedLanguageId(), objectField.getName(),
				objectField.isRequired(), objectField.getType());
		}

		objectFields = _objectFieldPersistence.findByObjectDefinitionId(
			objectDefinitionId);

		if (!objectDefinition.isSystem()) {
			_createTable(updatedObjectDefinition, objectFields);

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					objectDefinitionLocalService.deployObjectDefinition(
						updatedObjectDefinition);

					return null;
				});
		}

		return updatedObjectDefinition;
	}

	private void _createTable(
		ObjectDefinition objectDefinition, List<ObjectField> objectFields) {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			new DynamicObjectDefinitionTable(objectDefinition, objectFields);

		runSQL(dynamicObjectDefinitionTable.getCreateTableSQL());
	}

	private void _dropTable(ObjectDefinition objectDefinition) {
		String sql = "drop table " + objectDefinition.getDBTableName();

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		runSQL(sql);
	}

	private boolean _hasObjectField(
		List<ObjectField> newObjectFields, ObjectField oldObjectField) {

		for (ObjectField newObjectField : newObjectFields) {
			if (Objects.equals(
					newObjectField.getName(), oldObjectField.getName())) {

				return true;
			}
		}

		return false;
	}

	private void _validateName(long companyId, String name, boolean system)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ObjectDefinitionNameException("Name is null");
		}

		if (!system && name.equals("C_")) {
			throw new ObjectDefinitionNameException("Name is null");
		}

		if (system && (name.startsWith("C_") || name.startsWith("c_"))) {
			throw new ObjectDefinitionNameException(
				"System object definition names must not start with \"C_\"");
		}
		else if (!system && !name.startsWith("C_")) {
			throw new ObjectDefinitionNameException(
				"Custom object definition names must start with \"C_\"");
		}

		char[] nameCharArray = name.toCharArray();

		for (int i = 0; i < nameCharArray.length; i++) {
			if (!system) {

				// Skip C_

				if ((i == 0) || (i == 1)) {
					continue;
				}
			}

			char c = nameCharArray[i];

			if (!Validator.isChar(c) && !Validator.isDigit(c)) {
				throw new ObjectDefinitionNameException(
					"Name must only contain letters and digits");
			}
		}

		if ((system && !Character.isUpperCase(nameCharArray[0])) ||
			(!system && !Character.isUpperCase(nameCharArray[2]))) {

			throw new ObjectDefinitionNameException(
				"The first character of a name must be an upper case letter");
		}

		if ((system && (nameCharArray.length > 41)) ||
			(!system && (nameCharArray.length > 43))) {

			throw new ObjectDefinitionNameException(
				"Names must be less than 41 characters");
		}

		if (objectDefinitionPersistence.fetchByC_N(companyId, name) != null) {
			throw new DuplicateObjectDefinitionException(
				"Duplicate name " + name);
		}
	}

	private void _validateVersion(boolean system, int version)
		throws PortalException {

		if (system) {
			if (version <= 0) {
				throw new ObjectDefinitionVersionException(
					"System object definition versions must greater than 0");
			}
		}
		else {
			if (version != 0) {
				throw new ObjectDefinitionVersionException(
					"Custom object definition versions must be 0");
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionLocalServiceImpl.class);

	private BundleContext _bundleContext;
	private ServiceTracker<ObjectDefinitionDeployer, ObjectDefinitionDeployer>
		_objectDefinitionDeployerServiceTracker;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryPersistence _objectEntryPersistence;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	private final Map
		<ObjectDefinitionDeployer, Map<Long, List<ServiceRegistration<?>>>>
			_serviceRegistrationsMaps = new ConcurrentHashMap<>();

	@Reference
	private UserLocalService _userLocalService;

}
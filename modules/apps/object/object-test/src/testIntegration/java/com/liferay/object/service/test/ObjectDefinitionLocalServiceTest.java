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

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.exception.DuplicateObjectDefinitionException;
import com.liferay.object.exception.NoSuchObjectFieldException;
import com.liferay.object.exception.ObjectDefinitionNameException;
import com.liferay.object.exception.ObjectDefinitionVersionException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.system.BaseSystemObjectDefinitionMetadata;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class ObjectDefinitionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddCustomObjectDefinition() throws Exception {

		// Name is null

		try {
			_testAddCustomObjectDefinition("");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Name is null", objectDefinitionNameException.getMessage());
		}

		// Custom object definition names are automatically prepended with
		// with "C_"

		try {
			_testAddCustomObjectDefinition("Test");
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			throw objectDefinitionNameException;
		}

		// Name must only contain letters and digits

		_testAddCustomObjectDefinition(" Test ");

		try {
			_testAddCustomObjectDefinition("Tes t");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectDefinitionNameException.getMessage());
		}

		try {
			_testAddCustomObjectDefinition("Tes-t");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectDefinitionNameException.getMessage());
		}

		// The first character of a name must be an upper case letter

		try {
			_testAddCustomObjectDefinition("test");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"The first character of a name must be an upper case letter",
				objectDefinitionNameException.getMessage());
		}

		// Names must be less than 41 characters

		_testAddCustomObjectDefinition(
			"A123456789a123456789a123456789a1234567891");

		try {
			_testAddCustomObjectDefinition(
				"A123456789a123456789a123456789a12345678912");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Names must be less than 41 characters",
				objectDefinitionNameException.getMessage());
		}

		// Duplicate name

		ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
			TestPropsValues.getUserId(), "Test",
			Collections.<ObjectField>emptyList());

		try {
			_testAddCustomObjectDefinition("Test");
		}
		catch (DuplicateObjectDefinitionException
					duplicateObjectDefinitionException) {

			Assert.assertEquals(
				"Duplicate name C_Test",
				duplicateObjectDefinitionException.getMessage());
		}

		// Database table

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(), _randomName(),
				Arrays.asList(
					_createObjectField("able", "String"),
					_createObjectField("baker", "String")));

		Assert.assertEquals(
			false, _hasColumn(objectDefinition.getDBTableName(), "able"));
		Assert.assertEquals(
			true, _hasColumn(objectDefinition.getDBTableName(), "able_"));
		Assert.assertEquals(
			false, _hasColumn(objectDefinition.getDBTableName(), "baker"));
		Assert.assertEquals(
			true, _hasColumn(objectDefinition.getDBTableName(), "baker_"));
		Assert.assertEquals(true, _hasTable(objectDefinition.getDBTableName()));
	}

	@Test
	public void testAddOrUpdateSystemObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addOrUpdateSystemObjectDefinition(
				TestPropsValues.getCompanyId(),
				new BaseSystemObjectDefinitionMetadata() {
					{
						objectFieldLocalService =
							ObjectFieldLocalServiceUtil.getService();
					}

					@Override
					public String getName() {
						return "UserNotificationEvent";
					}

					@Override
					public List<ObjectField> getObjectFields() {
						return Arrays.asList(
							createObjectField(
								"actionRequired", true, "Boolean"),
							createObjectField("deliveryType", false, "Long"),
							createObjectField("type_", "type", true, "String"));
					}

					@Override
					public int getVersion() {
						return 1;
					}

				});

		Assert.assertEquals(
			"UserNotificationEvent", objectDefinition.getDBTableName());
		Assert.assertEquals(
			"userNotificationEventId",
			objectDefinition.getPKObjectFieldDBColumnName());
		Assert.assertEquals(
			"userNotificationEventId", objectDefinition.getPKObjectFieldName());
		Assert.assertEquals(objectDefinition.isSystem(), true);
		Assert.assertEquals(1, objectDefinition.getVersion());

		_assertObjectField(
			objectDefinition, "actionRequired", "actionRequired", true,
			"Boolean");

		try {
			ObjectFieldLocalServiceUtil.getObjectField(
				objectDefinition.getObjectDefinitionId(), "archived");

			Assert.fail();
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			Assert.assertNotNull(noSuchObjectFieldException);
		}

		_assertObjectField(
			objectDefinition, "deliveryType", "deliveryType", false, "Long");
		_assertObjectField(objectDefinition, "type_", "type", true, "String");

		objectDefinition =
			ObjectDefinitionLocalServiceUtil.addOrUpdateSystemObjectDefinition(
				TestPropsValues.getCompanyId(),
				new BaseSystemObjectDefinitionMetadata() {
					{
						objectFieldLocalService =
							ObjectFieldLocalServiceUtil.getService();
					}

					@Override
					public String getName() {
						return "UserNotificationEvent";
					}

					@Override
					public List<ObjectField> getObjectFields() {
						return Arrays.asList(
							createObjectField("archived", true, "Boolean"),
							createObjectField("deliveryType", true, "Long"),
							createObjectField(
								"type_", "type", false, "String"));
					}

					@Override
					public int getVersion() {
						return 2;
					}

				});

		Assert.assertEquals(2, objectDefinition.getVersion());

		try {
			ObjectFieldLocalServiceUtil.getObjectField(
				objectDefinition.getObjectDefinitionId(), "actionRequired");

			Assert.fail();
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			Assert.assertNotNull(noSuchObjectFieldException);
		}

		_assertObjectField(
			objectDefinition, "archived", "archived", true, "Boolean");
		_assertObjectField(
			objectDefinition, "deliveryType", "deliveryType", true, "Long");
		_assertObjectField(objectDefinition, "type_", "type", false, "String");

		ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
			objectDefinition);
	}

	@Test
	public void testAddSystemObjectDefinition() throws Exception {

		// Name is null

		try {
			_testAddSystemObjectDefinition("");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Name is null", objectDefinitionNameException.getMessage());
		}

		// System object definition names must not start with "C_"

		try {
			_testAddSystemObjectDefinition("C_Test");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"System object definition names must not start with \"C_\"",
				objectDefinitionNameException.getMessage());
		}

		try {
			_testAddSystemObjectDefinition("c_Test");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"System object definition names must not start with \"C_\"",
				objectDefinitionNameException.getMessage());
		}

		// Name must only contain letters and digits

		_testAddSystemObjectDefinition(" Test ");

		try {
			_testAddSystemObjectDefinition("Tes t");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectDefinitionNameException.getMessage());
		}

		try {
			_testAddSystemObjectDefinition("Tes-t");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectDefinitionNameException.getMessage());
		}

		// The first character of a name must be an upper case letter

		try {
			_testAddSystemObjectDefinition("test");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"The first character of a name must be an upper case letter",
				objectDefinitionNameException.getMessage());
		}

		// Names must be less than 41 characters

		_testAddSystemObjectDefinition(
			"A123456789a123456789a123456789a1234567891");

		try {
			_testAddSystemObjectDefinition(
				"A123456789a123456789a123456789a12345678912");

			Assert.fail();
		}
		catch (ObjectDefinitionNameException objectDefinitionNameException) {
			Assert.assertEquals(
				"Names must be less than 41 characters",
				objectDefinitionNameException.getMessage());
		}

		// Duplicate name

		ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
			TestPropsValues.getUserId(), null, "Test", null, null, 1,
			Collections.<ObjectField>emptyList());

		try {
			_testAddSystemObjectDefinition("Test");
		}
		catch (DuplicateObjectDefinitionException
					duplicateObjectDefinitionException) {

			Assert.assertEquals(
				"Duplicate name Test",
				duplicateObjectDefinitionException.getMessage());
		}

		// System object definition versions must greater than 0

		try {
			ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
				TestPropsValues.getUserId(), null, _randomName(), null, null,
				-1, Collections.<ObjectField>emptyList());
		}
		catch (ObjectDefinitionVersionException
					objectDefinitionVersionException) {

			Assert.assertEquals(
				"System object definition versions must greater than 0",
				objectDefinitionVersionException.getMessage());
		}

		try {
			ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
				TestPropsValues.getUserId(), null, _randomName(), null, null, 0,
				Collections.<ObjectField>emptyList());
		}
		catch (ObjectDefinitionVersionException
					objectDefinitionVersionException) {

			Assert.assertEquals(
				"System object definition versions must greater than 0",
				objectDefinitionVersionException.getMessage());
		}

		// Database table name

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
				TestPropsValues.getUserId(), null, _randomName(), null, null, 1,
				Collections.<ObjectField>emptyList());

		Assert.assertEquals(
			false, _hasTable(objectDefinition.getDBTableName()));
	}

	private void _assertObjectField(
			ObjectDefinition objectDefinition, String dbColumnName, String name,
			boolean required, String type)
		throws Exception {

		ObjectField objectField = ObjectFieldLocalServiceUtil.getObjectField(
			objectDefinition.getObjectDefinitionId(), name);

		Assert.assertEquals(dbColumnName, objectField.getDBColumnName());
		Assert.assertEquals(false, objectField.isIndexed());
		Assert.assertEquals(false, objectField.isIndexedAsKeyword());
		Assert.assertEquals("", objectField.getIndexedLanguageId());
		Assert.assertEquals(required, objectField.isRequired());
		Assert.assertEquals(type, objectField.getType());
	}

	private ObjectField _createObjectField(String name, String type) {
		ObjectField objectField = ObjectFieldLocalServiceUtil.createObjectField(
			0);

		objectField.setName(name);
		objectField.setType(type);

		return objectField;
	}

	private boolean _hasColumn(String tableName, String columnName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.hasColumn(tableName, columnName);
		}
	}

	private boolean _hasTable(String tableName) throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.hasTable(tableName);
		}
	}

	private String _randomName() {
		return StringUtil.upperCase(RandomStringUtils.randomAlphabetic(8));
	}

	private void _testAddCustomObjectDefinition(String name) throws Exception {
		ObjectDefinition objectDefinition = null;

		try {
			objectDefinition =
				ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
					TestPropsValues.getUserId(), name,
					Collections.<ObjectField>emptyList());
		}
		finally {
			if (objectDefinition != null) {
				ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void _testAddSystemObjectDefinition(String name) throws Exception {
		ObjectDefinition objectDefinition = null;

		try {
			objectDefinition =
				ObjectDefinitionLocalServiceUtil.addSystemObjectDefinition(
					TestPropsValues.getUserId(), null, name, null, null, 1,
					Collections.<ObjectField>emptyList());
		}
		finally {
			if (objectDefinition != null) {
				ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

}
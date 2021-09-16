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

package com.liferay.object.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.exception.NoSuchObjectLayoutRowException;
import com.liferay.object.model.ObjectLayoutRow;
import com.liferay.object.service.persistence.ObjectLayoutRowPersistence;
import com.liferay.object.service.persistence.ObjectLayoutRowUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class ObjectLayoutRowPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.object.service"));

	@Before
	public void setUp() {
		_persistence = ObjectLayoutRowUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ObjectLayoutRow> iterator = _objectLayoutRows.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutRow objectLayoutRow = _persistence.create(pk);

		Assert.assertNotNull(objectLayoutRow);

		Assert.assertEquals(objectLayoutRow.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ObjectLayoutRow newObjectLayoutRow = addObjectLayoutRow();

		_persistence.remove(newObjectLayoutRow);

		ObjectLayoutRow existingObjectLayoutRow =
			_persistence.fetchByPrimaryKey(newObjectLayoutRow.getPrimaryKey());

		Assert.assertNull(existingObjectLayoutRow);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addObjectLayoutRow();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutRow newObjectLayoutRow = _persistence.create(pk);

		newObjectLayoutRow.setMvccVersion(RandomTestUtil.nextLong());

		newObjectLayoutRow.setUuid(RandomTestUtil.randomString());

		newObjectLayoutRow.setCompanyId(RandomTestUtil.nextLong());

		newObjectLayoutRow.setUserId(RandomTestUtil.nextLong());

		newObjectLayoutRow.setUserName(RandomTestUtil.randomString());

		newObjectLayoutRow.setCreateDate(RandomTestUtil.nextDate());

		newObjectLayoutRow.setModifiedDate(RandomTestUtil.nextDate());

		newObjectLayoutRow.setObjectLayoutBoxId(RandomTestUtil.nextLong());

		newObjectLayoutRow.setPriority(RandomTestUtil.nextInt());

		_objectLayoutRows.add(_persistence.update(newObjectLayoutRow));

		ObjectLayoutRow existingObjectLayoutRow = _persistence.findByPrimaryKey(
			newObjectLayoutRow.getPrimaryKey());

		Assert.assertEquals(
			existingObjectLayoutRow.getMvccVersion(),
			newObjectLayoutRow.getMvccVersion());
		Assert.assertEquals(
			existingObjectLayoutRow.getUuid(), newObjectLayoutRow.getUuid());
		Assert.assertEquals(
			existingObjectLayoutRow.getObjectLayoutRowId(),
			newObjectLayoutRow.getObjectLayoutRowId());
		Assert.assertEquals(
			existingObjectLayoutRow.getCompanyId(),
			newObjectLayoutRow.getCompanyId());
		Assert.assertEquals(
			existingObjectLayoutRow.getUserId(),
			newObjectLayoutRow.getUserId());
		Assert.assertEquals(
			existingObjectLayoutRow.getUserName(),
			newObjectLayoutRow.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingObjectLayoutRow.getCreateDate()),
			Time.getShortTimestamp(newObjectLayoutRow.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingObjectLayoutRow.getModifiedDate()),
			Time.getShortTimestamp(newObjectLayoutRow.getModifiedDate()));
		Assert.assertEquals(
			existingObjectLayoutRow.getObjectLayoutBoxId(),
			newObjectLayoutRow.getObjectLayoutBoxId());
		Assert.assertEquals(
			existingObjectLayoutRow.getPriority(),
			newObjectLayoutRow.getPriority());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByObjectLayoutBoxId() throws Exception {
		_persistence.countByObjectLayoutBoxId(RandomTestUtil.nextLong());

		_persistence.countByObjectLayoutBoxId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ObjectLayoutRow newObjectLayoutRow = addObjectLayoutRow();

		ObjectLayoutRow existingObjectLayoutRow = _persistence.findByPrimaryKey(
			newObjectLayoutRow.getPrimaryKey());

		Assert.assertEquals(existingObjectLayoutRow, newObjectLayoutRow);
	}

	@Test(expected = NoSuchObjectLayoutRowException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ObjectLayoutRow> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ObjectLayoutRow", "mvccVersion", true, "uuid", true,
			"objectLayoutRowId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"objectLayoutBoxId", true, "priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ObjectLayoutRow newObjectLayoutRow = addObjectLayoutRow();

		ObjectLayoutRow existingObjectLayoutRow =
			_persistence.fetchByPrimaryKey(newObjectLayoutRow.getPrimaryKey());

		Assert.assertEquals(existingObjectLayoutRow, newObjectLayoutRow);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutRow missingObjectLayoutRow = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingObjectLayoutRow);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ObjectLayoutRow newObjectLayoutRow1 = addObjectLayoutRow();
		ObjectLayoutRow newObjectLayoutRow2 = addObjectLayoutRow();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectLayoutRow1.getPrimaryKey());
		primaryKeys.add(newObjectLayoutRow2.getPrimaryKey());

		Map<Serializable, ObjectLayoutRow> objectLayoutRows =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, objectLayoutRows.size());
		Assert.assertEquals(
			newObjectLayoutRow1,
			objectLayoutRows.get(newObjectLayoutRow1.getPrimaryKey()));
		Assert.assertEquals(
			newObjectLayoutRow2,
			objectLayoutRows.get(newObjectLayoutRow2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ObjectLayoutRow> objectLayoutRows =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(objectLayoutRows.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ObjectLayoutRow newObjectLayoutRow = addObjectLayoutRow();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectLayoutRow.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ObjectLayoutRow> objectLayoutRows =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, objectLayoutRows.size());
		Assert.assertEquals(
			newObjectLayoutRow,
			objectLayoutRows.get(newObjectLayoutRow.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ObjectLayoutRow> objectLayoutRows =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(objectLayoutRows.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ObjectLayoutRow newObjectLayoutRow = addObjectLayoutRow();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectLayoutRow.getPrimaryKey());

		Map<Serializable, ObjectLayoutRow> objectLayoutRows =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, objectLayoutRows.size());
		Assert.assertEquals(
			newObjectLayoutRow,
			objectLayoutRows.get(newObjectLayoutRow.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ObjectLayoutRow newObjectLayoutRow = addObjectLayoutRow();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutRow.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectLayoutRowId",
				newObjectLayoutRow.getObjectLayoutRowId()));

		List<ObjectLayoutRow> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ObjectLayoutRow existingObjectLayoutRow = result.get(0);

		Assert.assertEquals(existingObjectLayoutRow, newObjectLayoutRow);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutRow.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectLayoutRowId", RandomTestUtil.nextLong()));

		List<ObjectLayoutRow> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ObjectLayoutRow newObjectLayoutRow = addObjectLayoutRow();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutRow.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("objectLayoutRowId"));

		Object newObjectLayoutRowId = newObjectLayoutRow.getObjectLayoutRowId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"objectLayoutRowId", new Object[] {newObjectLayoutRowId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingObjectLayoutRowId = result.get(0);

		Assert.assertEquals(existingObjectLayoutRowId, newObjectLayoutRowId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutRow.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("objectLayoutRowId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"objectLayoutRowId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ObjectLayoutRow addObjectLayoutRow() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutRow objectLayoutRow = _persistence.create(pk);

		objectLayoutRow.setMvccVersion(RandomTestUtil.nextLong());

		objectLayoutRow.setUuid(RandomTestUtil.randomString());

		objectLayoutRow.setCompanyId(RandomTestUtil.nextLong());

		objectLayoutRow.setUserId(RandomTestUtil.nextLong());

		objectLayoutRow.setUserName(RandomTestUtil.randomString());

		objectLayoutRow.setCreateDate(RandomTestUtil.nextDate());

		objectLayoutRow.setModifiedDate(RandomTestUtil.nextDate());

		objectLayoutRow.setObjectLayoutBoxId(RandomTestUtil.nextLong());

		objectLayoutRow.setPriority(RandomTestUtil.nextInt());

		_objectLayoutRows.add(_persistence.update(objectLayoutRow));

		return objectLayoutRow;
	}

	private List<ObjectLayoutRow> _objectLayoutRows =
		new ArrayList<ObjectLayoutRow>();
	private ObjectLayoutRowPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
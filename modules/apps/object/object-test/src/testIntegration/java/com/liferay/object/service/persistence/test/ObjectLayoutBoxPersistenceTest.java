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
import com.liferay.object.exception.NoSuchObjectLayoutBoxException;
import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.object.service.persistence.ObjectLayoutBoxPersistence;
import com.liferay.object.service.persistence.ObjectLayoutBoxUtil;
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
public class ObjectLayoutBoxPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.object.service"));

	@Before
	public void setUp() {
		_persistence = ObjectLayoutBoxUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ObjectLayoutBox> iterator = _objectLayoutBoxes.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutBox objectLayoutBox = _persistence.create(pk);

		Assert.assertNotNull(objectLayoutBox);

		Assert.assertEquals(objectLayoutBox.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ObjectLayoutBox newObjectLayoutBox = addObjectLayoutBox();

		_persistence.remove(newObjectLayoutBox);

		ObjectLayoutBox existingObjectLayoutBox =
			_persistence.fetchByPrimaryKey(newObjectLayoutBox.getPrimaryKey());

		Assert.assertNull(existingObjectLayoutBox);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addObjectLayoutBox();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutBox newObjectLayoutBox = _persistence.create(pk);

		newObjectLayoutBox.setMvccVersion(RandomTestUtil.nextLong());

		newObjectLayoutBox.setUuid(RandomTestUtil.randomString());

		newObjectLayoutBox.setCompanyId(RandomTestUtil.nextLong());

		newObjectLayoutBox.setUserId(RandomTestUtil.nextLong());

		newObjectLayoutBox.setUserName(RandomTestUtil.randomString());

		newObjectLayoutBox.setCreateDate(RandomTestUtil.nextDate());

		newObjectLayoutBox.setModifiedDate(RandomTestUtil.nextDate());

		newObjectLayoutBox.setObjectLayoutTabId(RandomTestUtil.nextLong());

		newObjectLayoutBox.setCollapsable(RandomTestUtil.randomBoolean());

		newObjectLayoutBox.setName(RandomTestUtil.randomString());

		newObjectLayoutBox.setPriority(RandomTestUtil.nextInt());

		_objectLayoutBoxes.add(_persistence.update(newObjectLayoutBox));

		ObjectLayoutBox existingObjectLayoutBox = _persistence.findByPrimaryKey(
			newObjectLayoutBox.getPrimaryKey());

		Assert.assertEquals(
			existingObjectLayoutBox.getMvccVersion(),
			newObjectLayoutBox.getMvccVersion());
		Assert.assertEquals(
			existingObjectLayoutBox.getUuid(), newObjectLayoutBox.getUuid());
		Assert.assertEquals(
			existingObjectLayoutBox.getObjectLayoutBoxId(),
			newObjectLayoutBox.getObjectLayoutBoxId());
		Assert.assertEquals(
			existingObjectLayoutBox.getCompanyId(),
			newObjectLayoutBox.getCompanyId());
		Assert.assertEquals(
			existingObjectLayoutBox.getUserId(),
			newObjectLayoutBox.getUserId());
		Assert.assertEquals(
			existingObjectLayoutBox.getUserName(),
			newObjectLayoutBox.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingObjectLayoutBox.getCreateDate()),
			Time.getShortTimestamp(newObjectLayoutBox.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingObjectLayoutBox.getModifiedDate()),
			Time.getShortTimestamp(newObjectLayoutBox.getModifiedDate()));
		Assert.assertEquals(
			existingObjectLayoutBox.getObjectLayoutTabId(),
			newObjectLayoutBox.getObjectLayoutTabId());
		Assert.assertEquals(
			existingObjectLayoutBox.isCollapsable(),
			newObjectLayoutBox.isCollapsable());
		Assert.assertEquals(
			existingObjectLayoutBox.getName(), newObjectLayoutBox.getName());
		Assert.assertEquals(
			existingObjectLayoutBox.getPriority(),
			newObjectLayoutBox.getPriority());
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
	public void testCountByObjectLayoutTabId() throws Exception {
		_persistence.countByObjectLayoutTabId(RandomTestUtil.nextLong());

		_persistence.countByObjectLayoutTabId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ObjectLayoutBox newObjectLayoutBox = addObjectLayoutBox();

		ObjectLayoutBox existingObjectLayoutBox = _persistence.findByPrimaryKey(
			newObjectLayoutBox.getPrimaryKey());

		Assert.assertEquals(existingObjectLayoutBox, newObjectLayoutBox);
	}

	@Test(expected = NoSuchObjectLayoutBoxException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ObjectLayoutBox> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ObjectLayoutBox", "mvccVersion", true, "uuid", true,
			"objectLayoutBoxId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"objectLayoutTabId", true, "collapsable", true, "name", true,
			"priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ObjectLayoutBox newObjectLayoutBox = addObjectLayoutBox();

		ObjectLayoutBox existingObjectLayoutBox =
			_persistence.fetchByPrimaryKey(newObjectLayoutBox.getPrimaryKey());

		Assert.assertEquals(existingObjectLayoutBox, newObjectLayoutBox);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutBox missingObjectLayoutBox = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingObjectLayoutBox);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ObjectLayoutBox newObjectLayoutBox1 = addObjectLayoutBox();
		ObjectLayoutBox newObjectLayoutBox2 = addObjectLayoutBox();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectLayoutBox1.getPrimaryKey());
		primaryKeys.add(newObjectLayoutBox2.getPrimaryKey());

		Map<Serializable, ObjectLayoutBox> objectLayoutBoxes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, objectLayoutBoxes.size());
		Assert.assertEquals(
			newObjectLayoutBox1,
			objectLayoutBoxes.get(newObjectLayoutBox1.getPrimaryKey()));
		Assert.assertEquals(
			newObjectLayoutBox2,
			objectLayoutBoxes.get(newObjectLayoutBox2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ObjectLayoutBox> objectLayoutBoxes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(objectLayoutBoxes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ObjectLayoutBox newObjectLayoutBox = addObjectLayoutBox();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectLayoutBox.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ObjectLayoutBox> objectLayoutBoxes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, objectLayoutBoxes.size());
		Assert.assertEquals(
			newObjectLayoutBox,
			objectLayoutBoxes.get(newObjectLayoutBox.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ObjectLayoutBox> objectLayoutBoxes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(objectLayoutBoxes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ObjectLayoutBox newObjectLayoutBox = addObjectLayoutBox();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectLayoutBox.getPrimaryKey());

		Map<Serializable, ObjectLayoutBox> objectLayoutBoxes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, objectLayoutBoxes.size());
		Assert.assertEquals(
			newObjectLayoutBox,
			objectLayoutBoxes.get(newObjectLayoutBox.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ObjectLayoutBox newObjectLayoutBox = addObjectLayoutBox();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutBox.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectLayoutBoxId",
				newObjectLayoutBox.getObjectLayoutBoxId()));

		List<ObjectLayoutBox> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ObjectLayoutBox existingObjectLayoutBox = result.get(0);

		Assert.assertEquals(existingObjectLayoutBox, newObjectLayoutBox);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutBox.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectLayoutBoxId", RandomTestUtil.nextLong()));

		List<ObjectLayoutBox> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ObjectLayoutBox newObjectLayoutBox = addObjectLayoutBox();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutBox.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("objectLayoutBoxId"));

		Object newObjectLayoutBoxId = newObjectLayoutBox.getObjectLayoutBoxId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"objectLayoutBoxId", new Object[] {newObjectLayoutBoxId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingObjectLayoutBoxId = result.get(0);

		Assert.assertEquals(existingObjectLayoutBoxId, newObjectLayoutBoxId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutBox.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("objectLayoutBoxId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"objectLayoutBoxId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ObjectLayoutBox addObjectLayoutBox() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutBox objectLayoutBox = _persistence.create(pk);

		objectLayoutBox.setMvccVersion(RandomTestUtil.nextLong());

		objectLayoutBox.setUuid(RandomTestUtil.randomString());

		objectLayoutBox.setCompanyId(RandomTestUtil.nextLong());

		objectLayoutBox.setUserId(RandomTestUtil.nextLong());

		objectLayoutBox.setUserName(RandomTestUtil.randomString());

		objectLayoutBox.setCreateDate(RandomTestUtil.nextDate());

		objectLayoutBox.setModifiedDate(RandomTestUtil.nextDate());

		objectLayoutBox.setObjectLayoutTabId(RandomTestUtil.nextLong());

		objectLayoutBox.setCollapsable(RandomTestUtil.randomBoolean());

		objectLayoutBox.setName(RandomTestUtil.randomString());

		objectLayoutBox.setPriority(RandomTestUtil.nextInt());

		_objectLayoutBoxes.add(_persistence.update(objectLayoutBox));

		return objectLayoutBox;
	}

	private List<ObjectLayoutBox> _objectLayoutBoxes =
		new ArrayList<ObjectLayoutBox>();
	private ObjectLayoutBoxPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
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

package com.liferay.commerce.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.exception.NoSuchOrderTypeException;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.CommerceOrderTypeLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceOrderTypePersistence;
import com.liferay.commerce.service.persistence.CommerceOrderTypeUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
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
public class CommerceOrderTypePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceOrderTypeUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceOrderType> iterator = _commerceOrderTypes.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderType commerceOrderType = _persistence.create(pk);

		Assert.assertNotNull(commerceOrderType);

		Assert.assertEquals(commerceOrderType.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceOrderType newCommerceOrderType = addCommerceOrderType();

		_persistence.remove(newCommerceOrderType);

		CommerceOrderType existingCommerceOrderType =
			_persistence.fetchByPrimaryKey(
				newCommerceOrderType.getPrimaryKey());

		Assert.assertNull(existingCommerceOrderType);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceOrderType();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderType newCommerceOrderType = _persistence.create(pk);

		newCommerceOrderType.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newCommerceOrderType.setCompanyId(RandomTestUtil.nextLong());

		newCommerceOrderType.setUserId(RandomTestUtil.nextLong());

		newCommerceOrderType.setUserName(RandomTestUtil.randomString());

		newCommerceOrderType.setCreateDate(RandomTestUtil.nextDate());

		newCommerceOrderType.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceOrderType.setName(RandomTestUtil.randomString());

		newCommerceOrderType.setDescription(RandomTestUtil.randomString());

		newCommerceOrderType.setActive(RandomTestUtil.randomBoolean());

		newCommerceOrderType.setDisplayDate(RandomTestUtil.nextDate());

		newCommerceOrderType.setDisplayOrder(RandomTestUtil.nextInt());

		newCommerceOrderType.setExpirationDate(RandomTestUtil.nextDate());

		newCommerceOrderType.setLastPublishDate(RandomTestUtil.nextDate());

		newCommerceOrderType.setStatus(RandomTestUtil.nextInt());

		newCommerceOrderType.setStatusByUserId(RandomTestUtil.nextLong());

		newCommerceOrderType.setStatusByUserName(RandomTestUtil.randomString());

		newCommerceOrderType.setStatusDate(RandomTestUtil.nextDate());

		_commerceOrderTypes.add(_persistence.update(newCommerceOrderType));

		CommerceOrderType existingCommerceOrderType =
			_persistence.findByPrimaryKey(newCommerceOrderType.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceOrderType.getExternalReferenceCode(),
			newCommerceOrderType.getExternalReferenceCode());
		Assert.assertEquals(
			existingCommerceOrderType.getCommerceOrderTypeId(),
			newCommerceOrderType.getCommerceOrderTypeId());
		Assert.assertEquals(
			existingCommerceOrderType.getCompanyId(),
			newCommerceOrderType.getCompanyId());
		Assert.assertEquals(
			existingCommerceOrderType.getUserId(),
			newCommerceOrderType.getUserId());
		Assert.assertEquals(
			existingCommerceOrderType.getUserName(),
			newCommerceOrderType.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceOrderType.getCreateDate()),
			Time.getShortTimestamp(newCommerceOrderType.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceOrderType.getModifiedDate()),
			Time.getShortTimestamp(newCommerceOrderType.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceOrderType.getName(),
			newCommerceOrderType.getName());
		Assert.assertEquals(
			existingCommerceOrderType.getDescription(),
			newCommerceOrderType.getDescription());
		Assert.assertEquals(
			existingCommerceOrderType.isActive(),
			newCommerceOrderType.isActive());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceOrderType.getDisplayDate()),
			Time.getShortTimestamp(newCommerceOrderType.getDisplayDate()));
		Assert.assertEquals(
			existingCommerceOrderType.getDisplayOrder(),
			newCommerceOrderType.getDisplayOrder());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceOrderType.getExpirationDate()),
			Time.getShortTimestamp(newCommerceOrderType.getExpirationDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceOrderType.getLastPublishDate()),
			Time.getShortTimestamp(newCommerceOrderType.getLastPublishDate()));
		Assert.assertEquals(
			existingCommerceOrderType.getStatus(),
			newCommerceOrderType.getStatus());
		Assert.assertEquals(
			existingCommerceOrderType.getStatusByUserId(),
			newCommerceOrderType.getStatusByUserId());
		Assert.assertEquals(
			existingCommerceOrderType.getStatusByUserName(),
			newCommerceOrderType.getStatusByUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceOrderType.getStatusDate()),
			Time.getShortTimestamp(newCommerceOrderType.getStatusDate()));
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByLtD_S() throws Exception {
		_persistence.countByLtD_S(
			RandomTestUtil.nextDate(), RandomTestUtil.nextInt());

		_persistence.countByLtD_S(RandomTestUtil.nextDate(), 0);
	}

	@Test
	public void testCountByLtE_S() throws Exception {
		_persistence.countByLtE_S(
			RandomTestUtil.nextDate(), RandomTestUtil.nextInt());

		_persistence.countByLtE_S(RandomTestUtil.nextDate(), 0);
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceOrderType newCommerceOrderType = addCommerceOrderType();

		CommerceOrderType existingCommerceOrderType =
			_persistence.findByPrimaryKey(newCommerceOrderType.getPrimaryKey());

		Assert.assertEquals(existingCommerceOrderType, newCommerceOrderType);
	}

	@Test(expected = NoSuchOrderTypeException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceOrderType> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CommerceOrderType", "externalReferenceCode", true,
			"commerceOrderTypeId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true, "description", true, "active", true, "displayDate", true,
			"displayOrder", true, "expirationDate", true, "lastPublishDate",
			true, "status", true, "statusByUserId", true, "statusByUserName",
			true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceOrderType newCommerceOrderType = addCommerceOrderType();

		CommerceOrderType existingCommerceOrderType =
			_persistence.fetchByPrimaryKey(
				newCommerceOrderType.getPrimaryKey());

		Assert.assertEquals(existingCommerceOrderType, newCommerceOrderType);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderType missingCommerceOrderType =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceOrderType);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceOrderType newCommerceOrderType1 = addCommerceOrderType();
		CommerceOrderType newCommerceOrderType2 = addCommerceOrderType();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrderType1.getPrimaryKey());
		primaryKeys.add(newCommerceOrderType2.getPrimaryKey());

		Map<Serializable, CommerceOrderType> commerceOrderTypes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceOrderTypes.size());
		Assert.assertEquals(
			newCommerceOrderType1,
			commerceOrderTypes.get(newCommerceOrderType1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceOrderType2,
			commerceOrderTypes.get(newCommerceOrderType2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceOrderType> commerceOrderTypes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceOrderTypes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceOrderType newCommerceOrderType = addCommerceOrderType();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrderType.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceOrderType> commerceOrderTypes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceOrderTypes.size());
		Assert.assertEquals(
			newCommerceOrderType,
			commerceOrderTypes.get(newCommerceOrderType.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceOrderType> commerceOrderTypes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceOrderTypes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceOrderType newCommerceOrderType = addCommerceOrderType();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrderType.getPrimaryKey());

		Map<Serializable, CommerceOrderType> commerceOrderTypes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceOrderTypes.size());
		Assert.assertEquals(
			newCommerceOrderType,
			commerceOrderTypes.get(newCommerceOrderType.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceOrderTypeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceOrderType>() {

				@Override
				public void performAction(CommerceOrderType commerceOrderType) {
					Assert.assertNotNull(commerceOrderType);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceOrderType newCommerceOrderType = addCommerceOrderType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderType.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceOrderTypeId",
				newCommerceOrderType.getCommerceOrderTypeId()));

		List<CommerceOrderType> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceOrderType existingCommerceOrderType = result.get(0);

		Assert.assertEquals(existingCommerceOrderType, newCommerceOrderType);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderType.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceOrderTypeId", RandomTestUtil.nextLong()));

		List<CommerceOrderType> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceOrderType newCommerceOrderType = addCommerceOrderType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderType.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceOrderTypeId"));

		Object newCommerceOrderTypeId =
			newCommerceOrderType.getCommerceOrderTypeId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceOrderTypeId", new Object[] {newCommerceOrderTypeId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceOrderTypeId = result.get(0);

		Assert.assertEquals(
			existingCommerceOrderTypeId, newCommerceOrderTypeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderType.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceOrderTypeId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceOrderTypeId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceOrderType newCommerceOrderType = addCommerceOrderType();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCommerceOrderType.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		CommerceOrderType newCommerceOrderType = addCommerceOrderType();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderType.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceOrderTypeId",
				newCommerceOrderType.getCommerceOrderTypeId()));

		List<CommerceOrderType> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(CommerceOrderType commerceOrderType) {
		Assert.assertEquals(
			Long.valueOf(commerceOrderType.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				commerceOrderType, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			commerceOrderType.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				commerceOrderType, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected CommerceOrderType addCommerceOrderType() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderType commerceOrderType = _persistence.create(pk);

		commerceOrderType.setExternalReferenceCode(
			RandomTestUtil.randomString());

		commerceOrderType.setCompanyId(RandomTestUtil.nextLong());

		commerceOrderType.setUserId(RandomTestUtil.nextLong());

		commerceOrderType.setUserName(RandomTestUtil.randomString());

		commerceOrderType.setCreateDate(RandomTestUtil.nextDate());

		commerceOrderType.setModifiedDate(RandomTestUtil.nextDate());

		commerceOrderType.setName(RandomTestUtil.randomString());

		commerceOrderType.setDescription(RandomTestUtil.randomString());

		commerceOrderType.setActive(RandomTestUtil.randomBoolean());

		commerceOrderType.setDisplayDate(RandomTestUtil.nextDate());

		commerceOrderType.setDisplayOrder(RandomTestUtil.nextInt());

		commerceOrderType.setExpirationDate(RandomTestUtil.nextDate());

		commerceOrderType.setLastPublishDate(RandomTestUtil.nextDate());

		commerceOrderType.setStatus(RandomTestUtil.nextInt());

		commerceOrderType.setStatusByUserId(RandomTestUtil.nextLong());

		commerceOrderType.setStatusByUserName(RandomTestUtil.randomString());

		commerceOrderType.setStatusDate(RandomTestUtil.nextDate());

		_commerceOrderTypes.add(_persistence.update(commerceOrderType));

		return commerceOrderType;
	}

	private List<CommerceOrderType> _commerceOrderTypes =
		new ArrayList<CommerceOrderType>();
	private CommerceOrderTypePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
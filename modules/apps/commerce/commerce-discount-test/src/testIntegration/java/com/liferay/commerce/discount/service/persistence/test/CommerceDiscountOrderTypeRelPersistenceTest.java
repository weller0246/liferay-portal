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

package com.liferay.commerce.discount.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.discount.exception.NoSuchDiscountOrderTypeRelException;
import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel;
import com.liferay.commerce.discount.service.CommerceDiscountOrderTypeRelLocalServiceUtil;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountOrderTypeRelPersistence;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountOrderTypeRelUtil;
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
public class CommerceDiscountOrderTypeRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.discount.service"));

	@Before
	public void setUp() {
		_persistence = CommerceDiscountOrderTypeRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceDiscountOrderTypeRel> iterator =
			_commerceDiscountOrderTypeRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			_persistence.create(pk);

		Assert.assertNotNull(commerceDiscountOrderTypeRel);

		Assert.assertEquals(commerceDiscountOrderTypeRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceDiscountOrderTypeRel newCommerceDiscountOrderTypeRel =
			addCommerceDiscountOrderTypeRel();

		_persistence.remove(newCommerceDiscountOrderTypeRel);

		CommerceDiscountOrderTypeRel existingCommerceDiscountOrderTypeRel =
			_persistence.fetchByPrimaryKey(
				newCommerceDiscountOrderTypeRel.getPrimaryKey());

		Assert.assertNull(existingCommerceDiscountOrderTypeRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceDiscountOrderTypeRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceDiscountOrderTypeRel newCommerceDiscountOrderTypeRel =
			_persistence.create(pk);

		newCommerceDiscountOrderTypeRel.setUuid(RandomTestUtil.randomString());

		newCommerceDiscountOrderTypeRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceDiscountOrderTypeRel.setUserId(RandomTestUtil.nextLong());

		newCommerceDiscountOrderTypeRel.setUserName(
			RandomTestUtil.randomString());

		newCommerceDiscountOrderTypeRel.setCreateDate(
			RandomTestUtil.nextDate());

		newCommerceDiscountOrderTypeRel.setModifiedDate(
			RandomTestUtil.nextDate());

		newCommerceDiscountOrderTypeRel.setCommerceDiscountId(
			RandomTestUtil.nextLong());

		newCommerceDiscountOrderTypeRel.setCommerceOrderTypeId(
			RandomTestUtil.nextLong());

		newCommerceDiscountOrderTypeRel.setPriority(RandomTestUtil.nextInt());

		newCommerceDiscountOrderTypeRel.setLastPublishDate(
			RandomTestUtil.nextDate());

		_commerceDiscountOrderTypeRels.add(
			_persistence.update(newCommerceDiscountOrderTypeRel));

		CommerceDiscountOrderTypeRel existingCommerceDiscountOrderTypeRel =
			_persistence.findByPrimaryKey(
				newCommerceDiscountOrderTypeRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceDiscountOrderTypeRel.getUuid(),
			newCommerceDiscountOrderTypeRel.getUuid());
		Assert.assertEquals(
			existingCommerceDiscountOrderTypeRel.
				getCommerceDiscountOrderTypeRelId(),
			newCommerceDiscountOrderTypeRel.
				getCommerceDiscountOrderTypeRelId());
		Assert.assertEquals(
			existingCommerceDiscountOrderTypeRel.getCompanyId(),
			newCommerceDiscountOrderTypeRel.getCompanyId());
		Assert.assertEquals(
			existingCommerceDiscountOrderTypeRel.getUserId(),
			newCommerceDiscountOrderTypeRel.getUserId());
		Assert.assertEquals(
			existingCommerceDiscountOrderTypeRel.getUserName(),
			newCommerceDiscountOrderTypeRel.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceDiscountOrderTypeRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceDiscountOrderTypeRel.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceDiscountOrderTypeRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceDiscountOrderTypeRel.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceDiscountOrderTypeRel.getCommerceDiscountId(),
			newCommerceDiscountOrderTypeRel.getCommerceDiscountId());
		Assert.assertEquals(
			existingCommerceDiscountOrderTypeRel.getCommerceOrderTypeId(),
			newCommerceDiscountOrderTypeRel.getCommerceOrderTypeId());
		Assert.assertEquals(
			existingCommerceDiscountOrderTypeRel.getPriority(),
			newCommerceDiscountOrderTypeRel.getPriority());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceDiscountOrderTypeRel.getLastPublishDate()),
			Time.getShortTimestamp(
				newCommerceDiscountOrderTypeRel.getLastPublishDate()));
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
	public void testCountByCommerceDiscountId() throws Exception {
		_persistence.countByCommerceDiscountId(RandomTestUtil.nextLong());

		_persistence.countByCommerceDiscountId(0L);
	}

	@Test
	public void testCountByCommerceOrderTypeId() throws Exception {
		_persistence.countByCommerceOrderTypeId(RandomTestUtil.nextLong());

		_persistence.countByCommerceOrderTypeId(0L);
	}

	@Test
	public void testCountByCDI_COTI() throws Exception {
		_persistence.countByCDI_COTI(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByCDI_COTI(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceDiscountOrderTypeRel newCommerceDiscountOrderTypeRel =
			addCommerceDiscountOrderTypeRel();

		CommerceDiscountOrderTypeRel existingCommerceDiscountOrderTypeRel =
			_persistence.findByPrimaryKey(
				newCommerceDiscountOrderTypeRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceDiscountOrderTypeRel,
			newCommerceDiscountOrderTypeRel);
	}

	@Test(expected = NoSuchDiscountOrderTypeRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceDiscountOrderTypeRel>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CommerceDiscountOrderTypeRel", "uuid", true,
			"commerceDiscountOrderTypeRelId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"commerceDiscountId", true, "commerceOrderTypeId", true, "priority",
			true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceDiscountOrderTypeRel newCommerceDiscountOrderTypeRel =
			addCommerceDiscountOrderTypeRel();

		CommerceDiscountOrderTypeRel existingCommerceDiscountOrderTypeRel =
			_persistence.fetchByPrimaryKey(
				newCommerceDiscountOrderTypeRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceDiscountOrderTypeRel,
			newCommerceDiscountOrderTypeRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceDiscountOrderTypeRel missingCommerceDiscountOrderTypeRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceDiscountOrderTypeRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceDiscountOrderTypeRel newCommerceDiscountOrderTypeRel1 =
			addCommerceDiscountOrderTypeRel();
		CommerceDiscountOrderTypeRel newCommerceDiscountOrderTypeRel2 =
			addCommerceDiscountOrderTypeRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceDiscountOrderTypeRel1.getPrimaryKey());
		primaryKeys.add(newCommerceDiscountOrderTypeRel2.getPrimaryKey());

		Map<Serializable, CommerceDiscountOrderTypeRel>
			commerceDiscountOrderTypeRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, commerceDiscountOrderTypeRels.size());
		Assert.assertEquals(
			newCommerceDiscountOrderTypeRel1,
			commerceDiscountOrderTypeRels.get(
				newCommerceDiscountOrderTypeRel1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceDiscountOrderTypeRel2,
			commerceDiscountOrderTypeRels.get(
				newCommerceDiscountOrderTypeRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceDiscountOrderTypeRel>
			commerceDiscountOrderTypeRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(commerceDiscountOrderTypeRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceDiscountOrderTypeRel newCommerceDiscountOrderTypeRel =
			addCommerceDiscountOrderTypeRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceDiscountOrderTypeRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceDiscountOrderTypeRel>
			commerceDiscountOrderTypeRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, commerceDiscountOrderTypeRels.size());
		Assert.assertEquals(
			newCommerceDiscountOrderTypeRel,
			commerceDiscountOrderTypeRels.get(
				newCommerceDiscountOrderTypeRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceDiscountOrderTypeRel>
			commerceDiscountOrderTypeRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(commerceDiscountOrderTypeRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceDiscountOrderTypeRel newCommerceDiscountOrderTypeRel =
			addCommerceDiscountOrderTypeRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceDiscountOrderTypeRel.getPrimaryKey());

		Map<Serializable, CommerceDiscountOrderTypeRel>
			commerceDiscountOrderTypeRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, commerceDiscountOrderTypeRels.size());
		Assert.assertEquals(
			newCommerceDiscountOrderTypeRel,
			commerceDiscountOrderTypeRels.get(
				newCommerceDiscountOrderTypeRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceDiscountOrderTypeRelLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceDiscountOrderTypeRel>() {

				@Override
				public void performAction(
					CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

					Assert.assertNotNull(commerceDiscountOrderTypeRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceDiscountOrderTypeRel newCommerceDiscountOrderTypeRel =
			addCommerceDiscountOrderTypeRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceDiscountOrderTypeRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceDiscountOrderTypeRelId",
				newCommerceDiscountOrderTypeRel.
					getCommerceDiscountOrderTypeRelId()));

		List<CommerceDiscountOrderTypeRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceDiscountOrderTypeRel existingCommerceDiscountOrderTypeRel =
			result.get(0);

		Assert.assertEquals(
			existingCommerceDiscountOrderTypeRel,
			newCommerceDiscountOrderTypeRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceDiscountOrderTypeRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceDiscountOrderTypeRelId", RandomTestUtil.nextLong()));

		List<CommerceDiscountOrderTypeRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceDiscountOrderTypeRel newCommerceDiscountOrderTypeRel =
			addCommerceDiscountOrderTypeRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceDiscountOrderTypeRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceDiscountOrderTypeRelId"));

		Object newCommerceDiscountOrderTypeRelId =
			newCommerceDiscountOrderTypeRel.getCommerceDiscountOrderTypeRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceDiscountOrderTypeRelId",
				new Object[] {newCommerceDiscountOrderTypeRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceDiscountOrderTypeRelId = result.get(0);

		Assert.assertEquals(
			existingCommerceDiscountOrderTypeRelId,
			newCommerceDiscountOrderTypeRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceDiscountOrderTypeRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceDiscountOrderTypeRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceDiscountOrderTypeRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceDiscountOrderTypeRel newCommerceDiscountOrderTypeRel =
			addCommerceDiscountOrderTypeRel();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCommerceDiscountOrderTypeRel.getPrimaryKey()));
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

		CommerceDiscountOrderTypeRel newCommerceDiscountOrderTypeRel =
			addCommerceDiscountOrderTypeRel();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceDiscountOrderTypeRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceDiscountOrderTypeRelId",
				newCommerceDiscountOrderTypeRel.
					getCommerceDiscountOrderTypeRelId()));

		List<CommerceDiscountOrderTypeRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		Assert.assertEquals(
			Long.valueOf(commerceDiscountOrderTypeRel.getCommerceDiscountId()),
			ReflectionTestUtil.<Long>invoke(
				commerceDiscountOrderTypeRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "commerceDiscountId"));
		Assert.assertEquals(
			Long.valueOf(commerceDiscountOrderTypeRel.getCommerceOrderTypeId()),
			ReflectionTestUtil.<Long>invoke(
				commerceDiscountOrderTypeRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "commerceOrderTypeId"));
	}

	protected CommerceDiscountOrderTypeRel addCommerceDiscountOrderTypeRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			_persistence.create(pk);

		commerceDiscountOrderTypeRel.setUuid(RandomTestUtil.randomString());

		commerceDiscountOrderTypeRel.setCompanyId(RandomTestUtil.nextLong());

		commerceDiscountOrderTypeRel.setUserId(RandomTestUtil.nextLong());

		commerceDiscountOrderTypeRel.setUserName(RandomTestUtil.randomString());

		commerceDiscountOrderTypeRel.setCreateDate(RandomTestUtil.nextDate());

		commerceDiscountOrderTypeRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceDiscountOrderTypeRel.setCommerceDiscountId(
			RandomTestUtil.nextLong());

		commerceDiscountOrderTypeRel.setCommerceOrderTypeId(
			RandomTestUtil.nextLong());

		commerceDiscountOrderTypeRel.setPriority(RandomTestUtil.nextInt());

		commerceDiscountOrderTypeRel.setLastPublishDate(
			RandomTestUtil.nextDate());

		_commerceDiscountOrderTypeRels.add(
			_persistence.update(commerceDiscountOrderTypeRel));

		return commerceDiscountOrderTypeRel;
	}

	private List<CommerceDiscountOrderTypeRel> _commerceDiscountOrderTypeRels =
		new ArrayList<CommerceDiscountOrderTypeRel>();
	private CommerceDiscountOrderTypeRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
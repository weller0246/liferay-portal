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
import com.liferay.commerce.exception.NoSuchOrderTypeRelException;
import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.commerce.service.CommerceOrderTypeRelLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceOrderTypeRelPersistence;
import com.liferay.commerce.service.persistence.CommerceOrderTypeRelUtil;
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
public class CommerceOrderTypeRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceOrderTypeRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceOrderTypeRel> iterator =
			_commerceOrderTypeRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderTypeRel commerceOrderTypeRel = _persistence.create(pk);

		Assert.assertNotNull(commerceOrderTypeRel);

		Assert.assertEquals(commerceOrderTypeRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceOrderTypeRel newCommerceOrderTypeRel =
			addCommerceOrderTypeRel();

		_persistence.remove(newCommerceOrderTypeRel);

		CommerceOrderTypeRel existingCommerceOrderTypeRel =
			_persistence.fetchByPrimaryKey(
				newCommerceOrderTypeRel.getPrimaryKey());

		Assert.assertNull(existingCommerceOrderTypeRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceOrderTypeRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderTypeRel newCommerceOrderTypeRel = _persistence.create(pk);

		newCommerceOrderTypeRel.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newCommerceOrderTypeRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceOrderTypeRel.setUserId(RandomTestUtil.nextLong());

		newCommerceOrderTypeRel.setUserName(RandomTestUtil.randomString());

		newCommerceOrderTypeRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceOrderTypeRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceOrderTypeRel.setClassNameId(RandomTestUtil.nextLong());

		newCommerceOrderTypeRel.setClassPK(RandomTestUtil.nextLong());

		newCommerceOrderTypeRel.setCommerceOrderTypeId(
			RandomTestUtil.nextLong());

		_commerceOrderTypeRels.add(
			_persistence.update(newCommerceOrderTypeRel));

		CommerceOrderTypeRel existingCommerceOrderTypeRel =
			_persistence.findByPrimaryKey(
				newCommerceOrderTypeRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceOrderTypeRel.getExternalReferenceCode(),
			newCommerceOrderTypeRel.getExternalReferenceCode());
		Assert.assertEquals(
			existingCommerceOrderTypeRel.getCommerceOrderTypeRelId(),
			newCommerceOrderTypeRel.getCommerceOrderTypeRelId());
		Assert.assertEquals(
			existingCommerceOrderTypeRel.getCompanyId(),
			newCommerceOrderTypeRel.getCompanyId());
		Assert.assertEquals(
			existingCommerceOrderTypeRel.getUserId(),
			newCommerceOrderTypeRel.getUserId());
		Assert.assertEquals(
			existingCommerceOrderTypeRel.getUserName(),
			newCommerceOrderTypeRel.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceOrderTypeRel.getCreateDate()),
			Time.getShortTimestamp(newCommerceOrderTypeRel.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceOrderTypeRel.getModifiedDate()),
			Time.getShortTimestamp(newCommerceOrderTypeRel.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceOrderTypeRel.getClassNameId(),
			newCommerceOrderTypeRel.getClassNameId());
		Assert.assertEquals(
			existingCommerceOrderTypeRel.getClassPK(),
			newCommerceOrderTypeRel.getClassPK());
		Assert.assertEquals(
			existingCommerceOrderTypeRel.getCommerceOrderTypeId(),
			newCommerceOrderTypeRel.getCommerceOrderTypeId());
	}

	@Test
	public void testCountByCommerceOrderTypeId() throws Exception {
		_persistence.countByCommerceOrderTypeId(RandomTestUtil.nextLong());

		_persistence.countByCommerceOrderTypeId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByC_C_C() throws Exception {
		_persistence.countByC_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C_C(0L, 0L, 0L);
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceOrderTypeRel newCommerceOrderTypeRel =
			addCommerceOrderTypeRel();

		CommerceOrderTypeRel existingCommerceOrderTypeRel =
			_persistence.findByPrimaryKey(
				newCommerceOrderTypeRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceOrderTypeRel, newCommerceOrderTypeRel);
	}

	@Test(expected = NoSuchOrderTypeRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceOrderTypeRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CommerceOrderTypeRel", "externalReferenceCode", true,
			"commerceOrderTypeRelId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "commerceOrderTypeId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceOrderTypeRel newCommerceOrderTypeRel =
			addCommerceOrderTypeRel();

		CommerceOrderTypeRel existingCommerceOrderTypeRel =
			_persistence.fetchByPrimaryKey(
				newCommerceOrderTypeRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceOrderTypeRel, newCommerceOrderTypeRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderTypeRel missingCommerceOrderTypeRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceOrderTypeRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceOrderTypeRel newCommerceOrderTypeRel1 =
			addCommerceOrderTypeRel();
		CommerceOrderTypeRel newCommerceOrderTypeRel2 =
			addCommerceOrderTypeRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrderTypeRel1.getPrimaryKey());
		primaryKeys.add(newCommerceOrderTypeRel2.getPrimaryKey());

		Map<Serializable, CommerceOrderTypeRel> commerceOrderTypeRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceOrderTypeRels.size());
		Assert.assertEquals(
			newCommerceOrderTypeRel1,
			commerceOrderTypeRels.get(
				newCommerceOrderTypeRel1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceOrderTypeRel2,
			commerceOrderTypeRels.get(
				newCommerceOrderTypeRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceOrderTypeRel> commerceOrderTypeRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceOrderTypeRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceOrderTypeRel newCommerceOrderTypeRel =
			addCommerceOrderTypeRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrderTypeRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceOrderTypeRel> commerceOrderTypeRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceOrderTypeRels.size());
		Assert.assertEquals(
			newCommerceOrderTypeRel,
			commerceOrderTypeRels.get(newCommerceOrderTypeRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceOrderTypeRel> commerceOrderTypeRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceOrderTypeRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceOrderTypeRel newCommerceOrderTypeRel =
			addCommerceOrderTypeRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrderTypeRel.getPrimaryKey());

		Map<Serializable, CommerceOrderTypeRel> commerceOrderTypeRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceOrderTypeRels.size());
		Assert.assertEquals(
			newCommerceOrderTypeRel,
			commerceOrderTypeRels.get(newCommerceOrderTypeRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceOrderTypeRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceOrderTypeRel>() {

				@Override
				public void performAction(
					CommerceOrderTypeRel commerceOrderTypeRel) {

					Assert.assertNotNull(commerceOrderTypeRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceOrderTypeRel newCommerceOrderTypeRel =
			addCommerceOrderTypeRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderTypeRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceOrderTypeRelId",
				newCommerceOrderTypeRel.getCommerceOrderTypeRelId()));

		List<CommerceOrderTypeRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceOrderTypeRel existingCommerceOrderTypeRel = result.get(0);

		Assert.assertEquals(
			existingCommerceOrderTypeRel, newCommerceOrderTypeRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderTypeRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceOrderTypeRelId", RandomTestUtil.nextLong()));

		List<CommerceOrderTypeRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceOrderTypeRel newCommerceOrderTypeRel =
			addCommerceOrderTypeRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderTypeRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceOrderTypeRelId"));

		Object newCommerceOrderTypeRelId =
			newCommerceOrderTypeRel.getCommerceOrderTypeRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceOrderTypeRelId",
				new Object[] {newCommerceOrderTypeRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceOrderTypeRelId = result.get(0);

		Assert.assertEquals(
			existingCommerceOrderTypeRelId, newCommerceOrderTypeRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderTypeRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceOrderTypeRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceOrderTypeRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceOrderTypeRel newCommerceOrderTypeRel =
			addCommerceOrderTypeRel();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCommerceOrderTypeRel.getPrimaryKey()));
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

		CommerceOrderTypeRel newCommerceOrderTypeRel =
			addCommerceOrderTypeRel();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderTypeRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceOrderTypeRelId",
				newCommerceOrderTypeRel.getCommerceOrderTypeRelId()));

		List<CommerceOrderTypeRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CommerceOrderTypeRel commerceOrderTypeRel) {

		Assert.assertEquals(
			Long.valueOf(commerceOrderTypeRel.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				commerceOrderTypeRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classNameId"));
		Assert.assertEquals(
			Long.valueOf(commerceOrderTypeRel.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				commerceOrderTypeRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classPK"));
		Assert.assertEquals(
			Long.valueOf(commerceOrderTypeRel.getCommerceOrderTypeId()),
			ReflectionTestUtil.<Long>invoke(
				commerceOrderTypeRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "commerceOrderTypeId"));

		Assert.assertEquals(
			Long.valueOf(commerceOrderTypeRel.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				commerceOrderTypeRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			commerceOrderTypeRel.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				commerceOrderTypeRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected CommerceOrderTypeRel addCommerceOrderTypeRel() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderTypeRel commerceOrderTypeRel = _persistence.create(pk);

		commerceOrderTypeRel.setExternalReferenceCode(
			RandomTestUtil.randomString());

		commerceOrderTypeRel.setCompanyId(RandomTestUtil.nextLong());

		commerceOrderTypeRel.setUserId(RandomTestUtil.nextLong());

		commerceOrderTypeRel.setUserName(RandomTestUtil.randomString());

		commerceOrderTypeRel.setCreateDate(RandomTestUtil.nextDate());

		commerceOrderTypeRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceOrderTypeRel.setClassNameId(RandomTestUtil.nextLong());

		commerceOrderTypeRel.setClassPK(RandomTestUtil.nextLong());

		commerceOrderTypeRel.setCommerceOrderTypeId(RandomTestUtil.nextLong());

		_commerceOrderTypeRels.add(_persistence.update(commerceOrderTypeRel));

		return commerceOrderTypeRel;
	}

	private List<CommerceOrderTypeRel> _commerceOrderTypeRels =
		new ArrayList<CommerceOrderTypeRel>();
	private CommerceOrderTypeRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
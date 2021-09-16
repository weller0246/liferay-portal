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

package com.liferay.commerce.shop.by.diagram.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramEntryException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramEntryLocalServiceUtil;
import com.liferay.commerce.shop.by.diagram.service.persistence.CPDefinitionDiagramEntryPersistence;
import com.liferay.commerce.shop.by.diagram.service.persistence.CPDefinitionDiagramEntryUtil;
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
public class CPDefinitionDiagramEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.commerce.shop.by.diagram.service"));

	@Before
	public void setUp() {
		_persistence = CPDefinitionDiagramEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPDefinitionDiagramEntry> iterator =
			_cpDefinitionDiagramEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry = _persistence.create(
			pk);

		Assert.assertNotNull(cpDefinitionDiagramEntry);

		Assert.assertEquals(cpDefinitionDiagramEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPDefinitionDiagramEntry newCPDefinitionDiagramEntry =
			addCPDefinitionDiagramEntry();

		_persistence.remove(newCPDefinitionDiagramEntry);

		CPDefinitionDiagramEntry existingCPDefinitionDiagramEntry =
			_persistence.fetchByPrimaryKey(
				newCPDefinitionDiagramEntry.getPrimaryKey());

		Assert.assertNull(existingCPDefinitionDiagramEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPDefinitionDiagramEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramEntry newCPDefinitionDiagramEntry =
			_persistence.create(pk);

		newCPDefinitionDiagramEntry.setCompanyId(RandomTestUtil.nextLong());

		newCPDefinitionDiagramEntry.setUserId(RandomTestUtil.nextLong());

		newCPDefinitionDiagramEntry.setUserName(RandomTestUtil.randomString());

		newCPDefinitionDiagramEntry.setCreateDate(RandomTestUtil.nextDate());

		newCPDefinitionDiagramEntry.setModifiedDate(RandomTestUtil.nextDate());

		newCPDefinitionDiagramEntry.setCPDefinitionId(
			RandomTestUtil.nextLong());

		newCPDefinitionDiagramEntry.setCPInstanceUuid(
			RandomTestUtil.randomString());

		newCPDefinitionDiagramEntry.setCProductId(RandomTestUtil.nextLong());

		newCPDefinitionDiagramEntry.setDiagram(RandomTestUtil.randomBoolean());

		newCPDefinitionDiagramEntry.setQuantity(RandomTestUtil.nextInt());

		newCPDefinitionDiagramEntry.setSequence(RandomTestUtil.randomString());

		newCPDefinitionDiagramEntry.setSku(RandomTestUtil.randomString());

		_cpDefinitionDiagramEntries.add(
			_persistence.update(newCPDefinitionDiagramEntry));

		CPDefinitionDiagramEntry existingCPDefinitionDiagramEntry =
			_persistence.findByPrimaryKey(
				newCPDefinitionDiagramEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionDiagramEntry.getCPDefinitionDiagramEntryId(),
			newCPDefinitionDiagramEntry.getCPDefinitionDiagramEntryId());
		Assert.assertEquals(
			existingCPDefinitionDiagramEntry.getCompanyId(),
			newCPDefinitionDiagramEntry.getCompanyId());
		Assert.assertEquals(
			existingCPDefinitionDiagramEntry.getUserId(),
			newCPDefinitionDiagramEntry.getUserId());
		Assert.assertEquals(
			existingCPDefinitionDiagramEntry.getUserName(),
			newCPDefinitionDiagramEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionDiagramEntry.getCreateDate()),
			Time.getShortTimestamp(
				newCPDefinitionDiagramEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionDiagramEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newCPDefinitionDiagramEntry.getModifiedDate()));
		Assert.assertEquals(
			existingCPDefinitionDiagramEntry.getCPDefinitionId(),
			newCPDefinitionDiagramEntry.getCPDefinitionId());
		Assert.assertEquals(
			existingCPDefinitionDiagramEntry.getCPInstanceUuid(),
			newCPDefinitionDiagramEntry.getCPInstanceUuid());
		Assert.assertEquals(
			existingCPDefinitionDiagramEntry.getCProductId(),
			newCPDefinitionDiagramEntry.getCProductId());
		Assert.assertEquals(
			existingCPDefinitionDiagramEntry.isDiagram(),
			newCPDefinitionDiagramEntry.isDiagram());
		Assert.assertEquals(
			existingCPDefinitionDiagramEntry.getQuantity(),
			newCPDefinitionDiagramEntry.getQuantity());
		Assert.assertEquals(
			existingCPDefinitionDiagramEntry.getSequence(),
			newCPDefinitionDiagramEntry.getSequence());
		Assert.assertEquals(
			existingCPDefinitionDiagramEntry.getSku(),
			newCPDefinitionDiagramEntry.getSku());
	}

	@Test
	public void testCountByCPDefinitionId() throws Exception {
		_persistence.countByCPDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByCPDefinitionId(0L);
	}

	@Test
	public void testCountByCPDI_S() throws Exception {
		_persistence.countByCPDI_S(RandomTestUtil.nextLong(), "");

		_persistence.countByCPDI_S(0L, "null");

		_persistence.countByCPDI_S(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPDefinitionDiagramEntry newCPDefinitionDiagramEntry =
			addCPDefinitionDiagramEntry();

		CPDefinitionDiagramEntry existingCPDefinitionDiagramEntry =
			_persistence.findByPrimaryKey(
				newCPDefinitionDiagramEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionDiagramEntry, newCPDefinitionDiagramEntry);
	}

	@Test(expected = NoSuchCPDefinitionDiagramEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CPDefinitionDiagramEntry>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CPDefinitionDiagramEntry", "CPDefinitionDiagramEntryId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "CPDefinitionId", true,
			"CPInstanceUuid", true, "CProductId", true, "diagram", true,
			"quantity", true, "sequence", true, "sku", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPDefinitionDiagramEntry newCPDefinitionDiagramEntry =
			addCPDefinitionDiagramEntry();

		CPDefinitionDiagramEntry existingCPDefinitionDiagramEntry =
			_persistence.fetchByPrimaryKey(
				newCPDefinitionDiagramEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionDiagramEntry, newCPDefinitionDiagramEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramEntry missingCPDefinitionDiagramEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPDefinitionDiagramEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CPDefinitionDiagramEntry newCPDefinitionDiagramEntry1 =
			addCPDefinitionDiagramEntry();
		CPDefinitionDiagramEntry newCPDefinitionDiagramEntry2 =
			addCPDefinitionDiagramEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionDiagramEntry1.getPrimaryKey());
		primaryKeys.add(newCPDefinitionDiagramEntry2.getPrimaryKey());

		Map<Serializable, CPDefinitionDiagramEntry> cpDefinitionDiagramEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpDefinitionDiagramEntries.size());
		Assert.assertEquals(
			newCPDefinitionDiagramEntry1,
			cpDefinitionDiagramEntries.get(
				newCPDefinitionDiagramEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newCPDefinitionDiagramEntry2,
			cpDefinitionDiagramEntries.get(
				newCPDefinitionDiagramEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPDefinitionDiagramEntry> cpDefinitionDiagramEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionDiagramEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CPDefinitionDiagramEntry newCPDefinitionDiagramEntry =
			addCPDefinitionDiagramEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionDiagramEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPDefinitionDiagramEntry> cpDefinitionDiagramEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionDiagramEntries.size());
		Assert.assertEquals(
			newCPDefinitionDiagramEntry,
			cpDefinitionDiagramEntries.get(
				newCPDefinitionDiagramEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPDefinitionDiagramEntry> cpDefinitionDiagramEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionDiagramEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CPDefinitionDiagramEntry newCPDefinitionDiagramEntry =
			addCPDefinitionDiagramEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionDiagramEntry.getPrimaryKey());

		Map<Serializable, CPDefinitionDiagramEntry> cpDefinitionDiagramEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionDiagramEntries.size());
		Assert.assertEquals(
			newCPDefinitionDiagramEntry,
			cpDefinitionDiagramEntries.get(
				newCPDefinitionDiagramEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CPDefinitionDiagramEntryLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CPDefinitionDiagramEntry>() {

				@Override
				public void performAction(
					CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

					Assert.assertNotNull(cpDefinitionDiagramEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CPDefinitionDiagramEntry newCPDefinitionDiagramEntry =
			addCPDefinitionDiagramEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionDiagramEntryId",
				newCPDefinitionDiagramEntry.getCPDefinitionDiagramEntryId()));

		List<CPDefinitionDiagramEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPDefinitionDiagramEntry existingCPDefinitionDiagramEntry = result.get(
			0);

		Assert.assertEquals(
			existingCPDefinitionDiagramEntry, newCPDefinitionDiagramEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionDiagramEntryId", RandomTestUtil.nextLong()));

		List<CPDefinitionDiagramEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CPDefinitionDiagramEntry newCPDefinitionDiagramEntry =
			addCPDefinitionDiagramEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CPDefinitionDiagramEntryId"));

		Object newCPDefinitionDiagramEntryId =
			newCPDefinitionDiagramEntry.getCPDefinitionDiagramEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPDefinitionDiagramEntryId",
				new Object[] {newCPDefinitionDiagramEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPDefinitionDiagramEntryId = result.get(0);

		Assert.assertEquals(
			existingCPDefinitionDiagramEntryId, newCPDefinitionDiagramEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CPDefinitionDiagramEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPDefinitionDiagramEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPDefinitionDiagramEntry newCPDefinitionDiagramEntry =
			addCPDefinitionDiagramEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCPDefinitionDiagramEntry.getPrimaryKey()));
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

		CPDefinitionDiagramEntry newCPDefinitionDiagramEntry =
			addCPDefinitionDiagramEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionDiagramEntryId",
				newCPDefinitionDiagramEntry.getCPDefinitionDiagramEntryId()));

		List<CPDefinitionDiagramEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		Assert.assertEquals(
			Long.valueOf(cpDefinitionDiagramEntry.getCPDefinitionId()),
			ReflectionTestUtil.<Long>invoke(
				cpDefinitionDiagramEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "CPDefinitionId"));
		Assert.assertEquals(
			cpDefinitionDiagramEntry.getSequence(),
			ReflectionTestUtil.invoke(
				cpDefinitionDiagramEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "sequence"));
	}

	protected CPDefinitionDiagramEntry addCPDefinitionDiagramEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry = _persistence.create(
			pk);

		cpDefinitionDiagramEntry.setCompanyId(RandomTestUtil.nextLong());

		cpDefinitionDiagramEntry.setUserId(RandomTestUtil.nextLong());

		cpDefinitionDiagramEntry.setUserName(RandomTestUtil.randomString());

		cpDefinitionDiagramEntry.setCreateDate(RandomTestUtil.nextDate());

		cpDefinitionDiagramEntry.setModifiedDate(RandomTestUtil.nextDate());

		cpDefinitionDiagramEntry.setCPDefinitionId(RandomTestUtil.nextLong());

		cpDefinitionDiagramEntry.setCPInstanceUuid(
			RandomTestUtil.randomString());

		cpDefinitionDiagramEntry.setCProductId(RandomTestUtil.nextLong());

		cpDefinitionDiagramEntry.setDiagram(RandomTestUtil.randomBoolean());

		cpDefinitionDiagramEntry.setQuantity(RandomTestUtil.nextInt());

		cpDefinitionDiagramEntry.setSequence(RandomTestUtil.randomString());

		cpDefinitionDiagramEntry.setSku(RandomTestUtil.randomString());

		_cpDefinitionDiagramEntries.add(
			_persistence.update(cpDefinitionDiagramEntry));

		return cpDefinitionDiagramEntry;
	}

	private List<CPDefinitionDiagramEntry> _cpDefinitionDiagramEntries =
		new ArrayList<CPDefinitionDiagramEntry>();
	private CPDefinitionDiagramEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
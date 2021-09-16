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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry;
import com.liferay.portal.tools.service.builder.test.service.persistence.CacheFieldEntryPersistence;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class CacheFieldEntryPersistenceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Test
	public void testCacheFieldEntry() {
		CacheFieldEntry cacheFieldEntry = _cacheFieldEntryPersistence.create(
			RandomTestUtil.nextLong());

		cacheFieldEntry.setName("test.name");

		_cacheFieldEntry = _cacheFieldEntryPersistence.update(cacheFieldEntry);

		CacheFieldEntry existingCacheFieldEntry =
			_cacheFieldEntryPersistence.fetchByPrimaryKey(
				_cacheFieldEntry.getPrimaryKey());

		Assert.assertEquals(
			_cacheFieldEntry.getNickname(),
			existingCacheFieldEntry.getNickname());

		cacheFieldEntry.setName("test.name.update");

		_cacheFieldEntry = _cacheFieldEntryPersistence.update(cacheFieldEntry);

		existingCacheFieldEntry = _cacheFieldEntryPersistence.fetchByPrimaryKey(
			_cacheFieldEntry.getPrimaryKey());

		Assert.assertEquals(
			_cacheFieldEntry.getNickname(),
			existingCacheFieldEntry.getNickname());
	}

	@Test
	public void testCacheFieldEntryWithCollectionResult() {
		long groupId = RandomTestUtil.nextLong();

		List<CacheFieldEntry> cacheFieldEntries =
			_cacheFieldEntryPersistence.findByGroupId(groupId);

		Assert.assertTrue(cacheFieldEntries.isEmpty());

		CacheFieldEntry cacheFieldEntry = _cacheFieldEntryPersistence.create(
			RandomTestUtil.nextLong());

		cacheFieldEntry.setGroupId(groupId);
		cacheFieldEntry.setName("test.name");

		_cacheFieldEntry = _cacheFieldEntryPersistence.update(cacheFieldEntry);

		cacheFieldEntries = _cacheFieldEntryPersistence.findByGroupId(groupId);

		Assert.assertEquals(
			cacheFieldEntries.toString(), 1, cacheFieldEntries.size());

		CacheFieldEntry existingCacheFieldEntry = cacheFieldEntries.get(0);

		Assert.assertNotNull(
			ReflectionTestUtil.getFieldValue(
				existingCacheFieldEntry, "_nickname"));
	}

	@Test
	public void testCacheFieldEntryWithSingleResult() {
		CacheFieldEntry cacheFieldEntry = _cacheFieldEntryPersistence.create(
			RandomTestUtil.nextLong());

		cacheFieldEntry.setName("test.name");

		_cacheFieldEntry = _cacheFieldEntryPersistence.update(cacheFieldEntry);

		CacheFieldEntry existingCacheFieldEntry =
			_cacheFieldEntryPersistence.fetchByPrimaryKey(
				_cacheFieldEntry.getPrimaryKey());

		Assert.assertNotNull(existingCacheFieldEntry);
		Assert.assertNotNull(
			ReflectionTestUtil.getFieldValue(
				existingCacheFieldEntry, "_nickname"));
	}

	@DeleteAfterTestRun
	private CacheFieldEntry _cacheFieldEntry;

	@Inject
	private CacheFieldEntryPersistence _cacheFieldEntryPersistence;

}
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

package com.liferay.commerce.product.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Beslic
 */
@RunWith(Arquillian.class)
public class CommerceCatalogServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testAddCommerceCatalog() throws Exception {
		RoleTestUtil.addResourcePermission(
			"User", CPConstants.RESOURCE_NAME_CATALOG,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			CPActionKeys.ADD_COMMERCE_CATALOG);

		User user1 = UserTestUtil.addUser();

		UserTestUtil.setUser(user1);

		CommerceCatalog commerceCatalog1 =
			_commerceCatalogService.addCommerceCatalog(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				"USD", "en_US",
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getCompanyId(), user1.getGroupId(),
					user1.getUserId()));

		Assert.assertNotNull(
			_commerceCatalogService.getCommerceCatalog(
				commerceCatalog1.getCommerceCatalogId()));

		User user2 = UserTestUtil.addUser();

		UserTestUtil.setUser(user2);

		Class<? extends Exception> exceptionClass = Exception.class;

		try {
			CommerceCatalog commercePricingClass2 =
				_commerceCatalogService.getCommerceCatalog(
					commerceCatalog1.getCommerceCatalogId());

			Assert.assertNull(
				"User 2 has no owner permission", commercePricingClass2);
		}
		catch (Exception exception) {
			exceptionClass = exception.getClass();
		}

		Assert.assertEquals(
			PrincipalException.MustHavePermission.class, exceptionClass);

		User user3 = UserTestUtil.addCompanyAdminUser(
			_companyLocalService.getCompany(commerceCatalog1.getCompanyId()));

		UserTestUtil.setUser(user3);

		Assert.assertNotNull(
			_commerceCatalogService.getCommerceCatalog(
				commerceCatalog1.getCommerceCatalogId()));

		User user4 = UserTestUtil.addOmniAdminUser();

		UserTestUtil.setUser(user4);

		Assert.assertNotNull(
			_commerceCatalogService.getCommerceCatalog(
				commerceCatalog1.getCommerceCatalogId()));
	}

	@Inject
	private CommerceCatalogService _commerceCatalogService;

	@Inject
	private CompanyLocalService _companyLocalService;

}
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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.asm.ASMWrapperUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.ConsoleTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.InjectTestBag;
import com.liferay.portal.test.rule.InjectTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;

/**
 * @author Preston Crary
 */
public class InjectTestRuleTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@After
	public void tearDown() {
		_serviceRegistrations.forEach(ServiceRegistration::unregister);

		_serviceRegistrations.clear();
	}

	@Test
	public void testInjectBaseTestCase() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Service1 service1 = new Service1();

		_serviceRegistrations.add(
			registry.registerService(Service1.class, service1));

		Service2 service2 = new Service2();

		_serviceRegistrations.add(
			registry.registerService(Service2.class, service2));

		TestCase1 testCase1 = new TestCase1();

		Assert.assertNull(TestCase1.getService1());
		Assert.assertNull(testCase1.getService2());

		Description description = Description.createTestDescription(
			TestCase1.class, TestCase1.class.getName());

		InjectTestBag classInjectTestBag = InjectTestRule.INSTANCE.beforeClass(
			description);

		Assert.assertSame(service1, TestCase1.getService1());
		Assert.assertNull(testCase1.getService2());

		InjectTestBag methodInjectTestBag =
			InjectTestRule.INSTANCE.beforeMethod(description, testCase1);

		Assert.assertSame(service1, TestCase1.getService1());
		Assert.assertSame(service2, testCase1.getService2());

		InjectTestRule.INSTANCE.afterMethod(
			description, methodInjectTestBag, testCase1);

		Assert.assertSame(service1, TestCase1.getService1());
		Assert.assertNull(testCase1.getService2());

		InjectTestRule.INSTANCE.afterClass(description, classInjectTestBag);

		Assert.assertNull(TestCase1.getService1());
		Assert.assertNull(testCase1.getService2());
	}

	@Test
	public void testInjectBlockingStaticWithoutFilter() throws Exception {
		Description description = Description.createTestDescription(
			TestCase2.class, TestCase2.class.getName());

		Assert.assertNull(TestCase2._service1);

		Service1 service1 = new Service1();

		InjectTestBag injectTestBag = null;

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			ConsoleTestUtil.hijackStdOut();

		try {
			Thread registerThread = new Thread(
				() -> {
					while (true) {
						String stdOut = unsyncByteArrayOutputStream.toString();

						if (!stdOut.contains(
								"Waiting for service " +
									Service1.class.getName())) {

							continue;
						}

						Assert.assertNull(TestCase2._service1);

						Registry registry = RegistryUtil.getRegistry();

						_serviceRegistrations.add(
							registry.registerService(Service1.class, service1));

						return;
					}
				},
				"Registering " + Service1.class);

			registerThread.start();

			injectTestBag = InjectTestRule.INSTANCE.beforeClass(description);

			registerThread.join();
		}
		finally {
			ConsoleTestUtil.restoreStdOut(unsyncByteArrayOutputStream);
		}

		Assert.assertSame(service1, TestCase2._service1);

		AtomicBoolean ungetServiceCalled = new AtomicBoolean();

		RegistryUtil.setRegistry(
			ASMWrapperUtil.createASMWrapper(
				InjectTestRuleTest.class.getClassLoader(), Registry.class,
				new Object() {

					public <T> boolean ungetService(
						ServiceReference<T> serviceReference) {

						ungetServiceCalled.set(true);

						return false;
					}

				},
				RegistryUtil.getRegistry()));

		InjectTestRule.INSTANCE.afterClass(description, injectTestBag);

		Assert.assertNull(TestCase2._service1);

		Assert.assertTrue(ungetServiceCalled.get());
	}

	@Test
	public void testInjectNonblockingNonstaticWithFilter() throws Exception {
		Description description = Description.createTestDescription(
			TestCase2.class, TestCase2.class.getName());
		TestCase2 testCase2 = new TestCase2();

		InjectTestBag injectTestBag = InjectTestRule.INSTANCE.beforeMethod(
			description, testCase2);

		Assert.assertNull(testCase2._service2);

		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistrations.add(
			registry.registerService(Service2.class, new Service2()));

		injectTestBag.injectFields();

		Assert.assertNull(testCase2._service2);

		_serviceRegistrations.add(
			registry.registerService(Service3.class, new Service3()));

		injectTestBag.injectFields();

		Assert.assertNull(testCase2._service2);

		Service3 service3b = new Service3();

		_serviceRegistrations.add(
			registry.registerService(
				Service3.class, service3b,
				HashMapBuilder.<String, Object>put(
					"inject.test.rule.test", true
				).build()));

		injectTestBag.injectFields();

		Assert.assertSame(service3b, testCase2._service2);

		Assert.assertNull(TestCase2._service1);
		Assert.assertNull(testCase2._service3);

		InjectTestRule.INSTANCE.afterMethod(
			description, injectTestBag, testCase2);

		Assert.assertNull(testCase2._service2);
	}

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

	private static class BaseTestCase {

		public static Service1 getService1() {
			return _service1;
		}

		public Service2 getService2() {
			return _service2;
		}

		@Inject
		private static final Service1 _service1 = null;

		@Inject
		private final Service2 _service2 = null;

	}

	private static class TestCase1 extends BaseTestCase {
	}

	private static class TestCase2 {

		@Inject
		private static Service1 _service1;

		@Inject(
			blocking = false, filter = "inject.test.rule.test=true",
			type = Service3.class
		)
		private final Service2 _service2 = null;

		private final Service3 _service3 = null;

	}

	private class Service1 {
	}

	private class Service2 {
	}

	private class Service3 extends Service2 {
	}

}
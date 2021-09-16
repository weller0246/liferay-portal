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

package com.liferay.portal.template.freemarker.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvoker;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.AopCacheManager;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import freemarker.ext.beans.InvalidPropertyException;
import freemarker.ext.beans.SimpleMethodModel;
import freemarker.ext.beans.StringModel;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.logging.Level;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class RestrictedLiferayObjectWrapperTest
	extends BaseObjectWrapperTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		TransactionInvokerUtil transactionInvokerUtil =
			new TransactionInvokerUtil();

		transactionInvokerUtil.setTransactionInvoker(
			new TestTransactionInvoker());
	}

	@Test
	public void testConstructor() {
		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				RestrictedLiferayObjectWrapper.class.getName(), Level.INFO)) {

			Assert.assertEquals(
				Collections.singletonList("com.liferay.package.name"),
				ReflectionTestUtil.getFieldValue(
					new RestrictedLiferayObjectWrapper(
						null, new String[] {"com.liferay.package.name"}, null),
					"_restrictedPackageNames"));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to find restricted class com.liferay.package.name. " +
					"Registering as a package.",
				logEntry.getMessage());
		}

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				RestrictedLiferayObjectWrapper.class.getName(), Level.OFF)) {

			Assert.assertEquals(
				Collections.singletonList("com.liferay.package.name"),
				ReflectionTestUtil.getFieldValue(
					new RestrictedLiferayObjectWrapper(
						null, new String[] {"com.liferay.package.name"}, null),
					"_restrictedPackageNames"));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 0, logEntries.size());
		}
	}

	@Test
	public void testIsRestricted() {
		Assert.assertFalse(
			_isRestricted(
				new RestrictedLiferayObjectWrapper(null, null, null),
				TestLiferayObject.class));

		Assert.assertFalse(
			_isRestricted(
				new RestrictedLiferayObjectWrapper(
					new String[] {TestLiferayObject.class.getName()},
					new String[] {TestLiferayObject.class.getName()}, null),
				TestLiferayObject.class));

		Assert.assertFalse(
			_isRestricted(
				new RestrictedLiferayObjectWrapper(
					null, new String[] {"java.lang.String"}, null),
				TestLiferayObject.class));

		Assert.assertFalse(
			_isRestricted(
				new RestrictedLiferayObjectWrapper(
					null, new String[] {"com.liferay.portal.cache"}, null),
				TestLiferayObject.class));

		Assert.assertTrue(
			_isRestricted(
				new RestrictedLiferayObjectWrapper(
					null, new String[] {TestLiferayObject.class.getName()},
					null),
				TestLiferayObject.class));

		Assert.assertTrue(
			_isRestricted(
				new RestrictedLiferayObjectWrapper(
					null,
					new String[] {"com.liferay.portal.template.freemarker"},
					null),
				TestLiferayObject.class));

		Assert.assertTrue(
			_isRestricted(
				new RestrictedLiferayObjectWrapper(
					null, new String[] {"com.liferay.portal.*"}, null),
				TestLiferayObject.class));

		Assert.assertFalse(
			_isRestricted(
				new RestrictedLiferayObjectWrapper(
					null,
					new String[] {"com.liferay.portal.template.freemarker"},
					null),
				byte.class));

		Assert.assertFalse(
			_isRestricted(
				new RestrictedLiferayObjectWrapper(
					null,
					new String[] {"com.liferay.portal.template.freemarker"},
					null),
				byte.class));
	}

	@Test
	public void testIsRestrictedWithNoContextClassloader() {
		Thread thread = Thread.currentThread();

		ClassLoader contextClassLoader = thread.getContextClassLoader();

		thread.setContextClassLoader(null);

		try {
			Assert.assertFalse(
				_isRestricted(
					new RestrictedLiferayObjectWrapper(
						new String[] {TestLiferayObject.class.getName()},
						new String[] {TestLiferayObject.class.getName()}, null),
					TestLiferayObject.class));
		}
		finally {
			thread.setContextClassLoader(contextClassLoader);
		}
	}

	@Test
	public void testRestrictedClass() throws Exception {
		RestrictedLiferayObjectWrapper restrictedLiferayObjectWrapper =
			new RestrictedLiferayObjectWrapper(
				null, new String[] {TestLiferayMethodObject.class.getName()},
				null);

		TemplateModel templateModel = restrictedLiferayObjectWrapper.wrap(
			new TestLiferayMethodObject("test"));

		Assert.assertThat(
			templateModel,
			CoreMatchers.instanceOf(LiferayFreeMarkerStringModel.class));

		LiferayFreeMarkerStringModel liferayFreeMarkerStringModel =
			(LiferayFreeMarkerStringModel)templateModel;

		_testRestrictedMethodNames(liferayFreeMarkerStringModel, "name");
		_testRestrictedMethodNames(liferayFreeMarkerStringModel, "Name");
		_testRestrictedMethodNames(liferayFreeMarkerStringModel, "getName");
		_testRestrictedMethodNames(liferayFreeMarkerStringModel, "getname");
		_testRestrictedMethodNames(
			liferayFreeMarkerStringModel, "generateName");
	}

	@Test
	public void testRestrictedMethodNames() throws Exception {
		RestrictedLiferayObjectWrapper restrictedLiferayObjectWrapper =
			new RestrictedLiferayObjectWrapper(
				null, null,
				new String[] {
					TestLiferayMethodObject.class.getName() + "#getName"
				});

		TemplateModel templateModel = restrictedLiferayObjectWrapper.wrap(
			new TestLiferayMethodObject("test"));

		Assert.assertThat(
			templateModel,
			CoreMatchers.instanceOf(LiferayFreeMarkerStringModel.class));

		LiferayFreeMarkerStringModel liferayFreeMarkerStringModel =
			(LiferayFreeMarkerStringModel)templateModel;

		_testRestrictedMethodNames(liferayFreeMarkerStringModel, "name");
		_testRestrictedMethodNames(liferayFreeMarkerStringModel, "Name");
		_testRestrictedMethodNames(liferayFreeMarkerStringModel, "getName");
		_testRestrictedMethodNames(liferayFreeMarkerStringModel, "getname");

		SimpleMethodModel simpleMethodModel =
			(SimpleMethodModel)liferayFreeMarkerStringModel.get("generate");

		TemplateModel resultTemplateModel =
			(TemplateModel)simpleMethodModel.exec(
				Collections.singletonList(new SimpleScalar("generate")));

		Assert.assertEquals("test-generate", resultTemplateModel.toString());
	}

	@Test
	public void testRestrictedMethodNamesIncorrectSyntax() {
		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				RestrictedLiferayObjectWrapper.class.getName(), Level.INFO)) {

			String methodName =
				TestLiferayMethodObject.class.getName() + ".getName";

			new RestrictedLiferayObjectWrapper(
				null, null, new String[] {methodName});

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"\"", methodName, "\" does not match format ",
					"\"className#methodName\""),
				logEntry.getMessage());
		}
	}

	@Test
	public void testWrap() throws Exception {
		testWrap(new RestrictedLiferayObjectWrapper(null, null, null));

		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.STAR}, null, null));
		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.STAR},
				new String[] {LiferayObjectWrapper.class.getName()}, null));

		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.BLANK}, null, null));
		testWrap(
			new RestrictedLiferayObjectWrapper(
				null, new String[] {StringPool.BLANK}, null));
		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.BLANK},
				new String[] {StringPool.BLANK}, null));
		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.BLANK},
				new String[] {StringPool.BLANK},
				new String[] {StringPool.BLANK}));
		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.BLANK},
				new String[] {StringPool.BLANK},
				new String[] {TestBaseModel.class.getName() + "#getName"}));
		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.BLANK},
				new String[] {StringPool.BLANK},
				new String[] {
					TestLiferayMethodObject.class.getName() + "#getName"
				}));
	}

	public class TestLiferayMethodObject {

		public String generate(String postfix) {
			return _name + StringPool.DASH + postfix;
		}

		public String getName() {
			return _name;
		}

		public void setName(String name) {
			_name = name;
		}

		@Override
		public String toString() {
			return _name;
		}

		private TestLiferayMethodObject(String name) {
			_name = name;
		}

		private String _name;

	}

	protected void testWrap(ObjectWrapper objectWrapper) throws Exception {
		super.testWrap(objectWrapper);

		// Local service object without proxy

		assertTemplateModel(
			"TestLocalServiceObject", stringModel -> stringModel.getAsString(),
			StringModel.class.cast(
				objectWrapper.wrap(
					new TestLocalService("TestLocalServiceObject"))));

		// Local service object with proxy

		assertTemplateModel(
			"TestLocalServiceObject1", stringModel -> stringModel.getAsString(),
			StringModel.class.cast(
				objectWrapper.wrap(
					_createAopProxy(
						new TestLocalService("TestLocalServiceObject1")))));

		assertTemplateModel(
			"TestLocalServiceObject2", stringModel -> stringModel.getAsString(),
			StringModel.class.cast(
				objectWrapper.wrap(
					_createAopProxy(
						new TestLocalService("TestLocalServiceObject2")))));

		// Service object

		assertTemplateModel(
			"TestServiceObject", stringModel -> stringModel.getAsString(),
			StringModel.class.cast(
				objectWrapper.wrap(
					_createAopProxy(new TestService("TestServiceObject")))));

		// System company ID

		assertTemplateModel(
			"123", stringModel -> stringModel.getAsString(),
			StringModel.class.cast(
				objectWrapper.wrap(new TestBaseModel(123L))));

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				CompanyThreadLocal.class.getName(), Level.OFF)) {

			try {
				CompanyThreadLocal.setCompanyId(1L);

				// Base model without company ID

				assertTemplateModel(
					"123", stringModel -> stringModel.getAsString(),
					StringModel.class.cast(
						objectWrapper.wrap(
							new TestBaseModel(123L) {

								public Map
									<String, Function<TestBaseModel, Object>>
										getAttributeGetterFunctions() {

									return Collections.emptyMap();
								}

							})));

				// Base model with company ID

				assertTemplateModel(
					"1", stringModel -> stringModel.getAsString(),
					StringModel.class.cast(
						objectWrapper.wrap(new TestBaseModel(1L))));

				// Base model with wrong company ID

				try {
					objectWrapper.wrap(new TestBaseModel(123L));

					Assert.fail();
				}
				catch (TemplateModelException templateModelException) {
					Assert.assertEquals(
						"Denied access to model object as it does not belong " +
							"to current company 1",
						templateModelException.getMessage());
				}
			}
			finally {
				CompanyThreadLocal.setCompanyId(0L);
			}
		}
	}

	private Object _createAopProxy(Object target) {
		Class<?> clazz = target.getClass();

		return ProxyUtil.newProxyInstance(
			clazz.getClassLoader(), clazz.getInterfaces(),
			AopCacheManager.create(target, null));
	}

	private boolean _isRestricted(
		RestrictedLiferayObjectWrapper restrictedLiferayObjectWrapper,
		Class<?> targetClass) {

		return ReflectionTestUtil.invoke(
			restrictedLiferayObjectWrapper, "_isRestricted",
			new Class<?>[] {Class.class}, targetClass);
	}

	private void _testRestrictedMethodNames(
		LiferayFreeMarkerStringModel liferayFreeMarkerStringModel, String key) {

		try {
			liferayFreeMarkerStringModel.get(key);

			Assert.assertNull("Should throw TemplateModelException for " + key);
		}
		catch (TemplateModelException templateModelException) {
			Assert.assertSame(
				InvalidPropertyException.class,
				templateModelException.getClass());

			Assert.assertEquals(
				StringBundler.concat(
					"Denied access to method or field ", key, " of ",
					TestLiferayMethodObject.class.toString()),
				templateModelException.getMessage());
		}
	}

	private static class TestBaseModel extends BaseModelImpl<TestBaseModel> {

		@Override
		public Object clone() {
			return null;
		}

		@Override
		public TestBaseModel cloneWithOriginalValues() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int compareTo(TestBaseModel testBaseModel) {
			return 0;
		}

		public Map<String, Function<TestBaseModel, Object>>
			getAttributeGetterFunctions() {

			return Collections.singletonMap(
				"companyId", TestBaseModel::getCompanyId);
		}

		public long getCompanyId() {
			return _companyId;
		}

		@Override
		public Map<String, Object> getModelAttributes() {
			return null;
		}

		@Override
		public Class<?> getModelClass() {
			return null;
		}

		@Override
		public String getModelClassName() {
			return null;
		}

		@Override
		public Serializable getPrimaryKeyObj() {
			return null;
		}

		@Override
		public boolean isEntityCacheEnabled() {
			return false;
		}

		@Override
		public boolean isFinderCacheEnabled() {
			return false;
		}

		@Override
		public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		}

		@Override
		public String toString() {
			return String.valueOf(_companyId);
		}

		@Override
		public String toXmlString() {
			return null;
		}

		private TestBaseModel(long companyId) {
			_companyId = companyId;
		}

		private final long _companyId;

	}

	private static class TestLocalService implements BaseLocalService {

		@Override
		public String toString() {
			return _name;
		}

		private TestLocalService(String name) {
			_name = name;
		}

		private final String _name;

	}

	private static class TestService implements BaseService {

		@Override
		public String toString() {
			return _name;
		}

		private TestService(String name) {
			_name = name;
		}

		private final String _name;

	}

	private static class TestTransactionInvoker implements TransactionInvoker {

		@Override
		public <T> T invoke(
				TransactionConfig transactionConfig, Callable<T> callable)
			throws Throwable {

			if (transactionConfig.isStrictReadOnly()) {
				return callable.call();
			}

			return null;
		}

	}

}
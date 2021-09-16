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

package com.liferay.poshi.runner;

import com.liferay.data.guard.connector.client.DataGuardClient;
import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.PoshiGetterUtil;
import com.liferay.poshi.core.PoshiStackTraceUtil;
import com.liferay.poshi.core.PoshiValidation;
import com.liferay.poshi.core.PoshiVariablesUtil;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.PropsValues;
import com.liferay.poshi.runner.logger.PoshiLogger;
import com.liferay.poshi.runner.logger.SummaryLogger;
import com.liferay.poshi.runner.selenium.LiferaySeleniumUtil;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.ProxyUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Hashimoto
 * @author Karen Dang
 * @author Leslie Wong
 */
@RunWith(Parameterized.class)
public class PoshiRunner {

	@AfterClass
	public static void evaluateResults() throws IOException {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, List<String>> testResult :
				_testResults.entrySet()) {

			List<String> testResultMessages = testResult.getValue();

			if (testResultMessages.size() == 1) {
				continue;
			}

			int passes = Collections.frequency(testResultMessages, "PASS");

			int failures = testResultMessages.size() - passes;

			if ((passes > 0) && (failures > 0)) {
				sb.append("\n");
				sb.append(testResult.getKey());
			}
		}

		if (sb.length() != 0) {
			FileUtil.write(
				FileUtil.getCanonicalPath(".") + "/test-results/flaky-tests",
				sb.toString());
		}
	}

	@Parameterized.Parameters(name = "{0}")
	public static List<String> getList() throws Exception {
		List<String> namespacedClassCommandNames = new ArrayList<>();

		List<String> testNames = Arrays.asList(
			PropsValues.TEST_NAME.split("\\s*,\\s*"));

		PoshiContext.readFiles();

		for (String testName : testNames) {
			PoshiValidation.validate(testName);

			String namespace =
				PoshiGetterUtil.getNamespaceFromNamespacedClassCommandName(
					testName);

			if (testName.contains("#")) {
				String classCommandName =
					PoshiGetterUtil.
						getClassCommandNameFromNamespacedClassCommandName(
							testName);

				namespacedClassCommandNames.add(
					namespace + "." + classCommandName);
			}
			else {
				String className =
					PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
						testName);

				Element rootElement = PoshiContext.getTestCaseRootElement(
					className, namespace);

				List<Element> commandElements = rootElement.elements("command");

				for (Element commandElement : commandElements) {
					namespacedClassCommandNames.add(
						namespace + "." + className + "#" +
							commandElement.attributeValue("name"));
				}
			}
		}

		return namespacedClassCommandNames;
	}

	public PoshiRunner(String namespacedClassCommandName) throws Exception {
		_testNamespacedClassCommandName = namespacedClassCommandName;

		_testNamespacedClassName =
			PoshiGetterUtil.
				getNamespacedClassNameFromNamespacedClassCommandName(
					_testNamespacedClassCommandName);

		_poshiLogger = new PoshiLogger(namespacedClassCommandName);

		_poshiRunnerExecutor = new PoshiRunnerExecutor(_poshiLogger);
	}

	@Before
	public void setUp() throws Exception {
		System.out.println();
		System.out.println("###");
		System.out.println("### " + _testNamespacedClassCommandName);
		System.out.println("###");
		System.out.println();

		PoshiContext.setTestCaseNamespacedClassCommandName(
			_testNamespacedClassCommandName);

		PoshiVariablesUtil.clear();

		FileUtil.delete(new File(PropsValues.OUTPUT_DIR_NAME));

		try {
			if (PropsValues.LIFERAY_DATA_GUARD_ENABLED) {
				_dataGuardClient = new DataGuardClient();

				_dataGuardClient.connect();

				_dataGuardId = _dataGuardClient.startCapture();
			}

			SummaryLogger.startRunning();

			SeleniumUtil.startSelenium();

			_runSetUp();
		}
		catch (WebDriverException webDriverException) {
			webDriverException.printStackTrace();

			throw webDriverException;
		}
		catch (Exception exception) {
			LiferaySeleniumUtil.printJavaProcessStacktrace();

			PoshiStackTraceUtil.printStackTrace(exception.getMessage());

			PoshiStackTraceUtil.emptyStackTrace();

			exception.printStackTrace();

			throw exception;
		}
	}

	@After
	public void tearDown() throws Throwable {
		LiferaySeleniumUtil.writePoshiWarnings();

		SummaryLogger.createSummaryReport();

		try {
			if (!PropsValues.TEST_SKIP_TEAR_DOWN) {
				_runTearDown();
			}
		}
		catch (Exception exception) {
			PoshiStackTraceUtil.printStackTrace(exception.getMessage());

			PoshiStackTraceUtil.emptyStackTrace();
		}
		finally {
			if (PropsValues.PROXY_SERVER_ENABLED) {
				ProxyUtil.stopBrowserMobProxy();
			}

			SummaryLogger.stopRunning();

			_poshiLogger.createPoshiReport();

			SeleniumUtil.stopSelenium();
		}

		if (!PropsValues.LIFERAY_DATA_GUARD_ENABLED) {
			return;
		}

		try {
			_dataGuardClient.endCapture(
				_dataGuardId, _testNamespacedClassCommandName);
		}
		catch (Throwable throwable) {
			System.out.println(throwable.getMessage());

			throwable.printStackTrace();

			throw throwable;
		}
		finally {
			_dataGuardClient.close();
		}
	}

	@Test
	public void test() throws Exception {
		try {
			_runCommand();

			LiferaySeleniumUtil.assertNoPoshiWarnings();
		}
		catch (Exception exception) {
			LiferaySeleniumUtil.printJavaProcessStacktrace();

			PoshiStackTraceUtil.printStackTrace(exception.getMessage());

			PoshiStackTraceUtil.emptyStackTrace();

			exception.printStackTrace();

			throw exception;
		}
	}

	@Rule
	public RetryTestRule retryTestRule = new RetryTestRule();

	private void _runCommand() throws Exception {
		_poshiLogger.logNamespacedClassCommandName(
			_testNamespacedClassCommandName);

		_runNamespacedClassCommandName(_testNamespacedClassCommandName);
	}

	private void _runNamespacedClassCommandName(
			String namespacedClassCommandName)
		throws Exception {

		String namespace =
			PoshiGetterUtil.getNamespaceFromNamespacedClassCommandName(
				namespacedClassCommandName);

		String classCommandName =
			PoshiGetterUtil.getClassCommandNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		Element commandElement = PoshiContext.getTestCaseCommandElement(
			classCommandName, namespace);

		if (commandElement != null) {
			PoshiStackTraceUtil.startStackTrace(
				namespacedClassCommandName, "test-case");

			_poshiLogger.updateStatus(commandElement, "pending");

			_poshiRunnerExecutor.runTestCaseCommandElement(
				commandElement, namespacedClassCommandName);

			_poshiLogger.updateStatus(commandElement, "pass");

			PoshiStackTraceUtil.emptyStackTrace();
		}
	}

	private void _runSetUp() throws Exception {
		_poshiLogger.logNamespacedClassCommandName(
			_testNamespacedClassName + "#set-up");

		SummaryLogger.startMajorSteps();

		_runNamespacedClassCommandName(_testNamespacedClassName + "#set-up");
	}

	private void _runTearDown() throws Exception {
		_poshiLogger.logNamespacedClassCommandName(
			_testNamespacedClassName + "#tear-down");

		SummaryLogger.startMajorSteps();

		_runNamespacedClassCommandName(_testNamespacedClassName + "#tear-down");
	}

	private static DataGuardClient _dataGuardClient;
	private static long _dataGuardId;
	private static int _jvmRetryCount;
	private static final Map<String, List<String>> _testResults =
		new HashMap<>();

	private final PoshiLogger _poshiLogger;
	private final PoshiRunnerExecutor _poshiRunnerExecutor;
	private final String _testNamespacedClassCommandName;
	private final String _testNamespacedClassName;

	private class RetryTestRule implements TestRule {

		public Statement apply(Statement statement, Description description) {
			return new RetryStatement(statement);
		}

		public class RetryStatement extends Statement {

			public RetryStatement(Statement statement) {
				_statement = statement;
			}

			@Override
			public void evaluate() throws Throwable {
				while (true) {
					try {
						_statement.evaluate();

						_testResultMessages.add("PASS");

						_testResults.put(
							_testNamespacedClassCommandName,
							_testResultMessages);

						return;
					}
					catch (Throwable throwable) {
						_testResultMessages.add(throwable.getMessage());

						if (!_isRetryable(throwable)) {
							_testResults.put(
								_testNamespacedClassCommandName,
								_testResultMessages);

							throw throwable;
						}

						_jvmRetryCount++;
						_testcaseRetryCount++;

						System.out.println(
							"Retrying test attempt " + _testcaseRetryCount +
								" of " + PropsValues.TEST_TESTCASE_MAX_RETRIES);
					}
				}
			}

			private String _getShortMessage(Throwable throwable) {
				String message = throwable.getMessage();

				if (throwable instanceof WebDriverException) {
					int index = message.indexOf("Build info:");

					message = message.substring(0, index);

					message = message.trim();
				}

				return message;
			}

			private boolean _isKnownFlakyIssue(Throwable throwable1) {
				List<Throwable> throwables = null;

				if (throwable1 instanceof MultipleFailureException) {
					MultipleFailureException multipleFailureException =
						(MultipleFailureException)throwable1;

					throwables = multipleFailureException.getFailures();
				}
				else {
					throwables = Arrays.asList(throwable1);
				}

				for (Throwable validRetryThrowable : _validRetryThrowables) {
					Class<?> validRetryThrowableClass =
						validRetryThrowable.getClass();
					String validRetryThrowableShortMessage = _getShortMessage(
						validRetryThrowable);

					for (Throwable throwable2 : throwables) {
						if (validRetryThrowableClass.equals(
								throwable2.getClass()) &&
							((validRetryThrowableShortMessage == null) ||
							 validRetryThrowableShortMessage.isEmpty() ||
							 validRetryThrowableShortMessage.equals(
								 _getShortMessage(throwable2)))) {

							return true;
						}
					}
				}

				return false;
			}

			private boolean _isRetryable(Throwable throwable) {
				if (_jvmRetryCount >= PropsValues.TEST_JVM_MAX_RETRIES) {
					System.out.println(
						"Test retry attempts exceeded in Poshi Runner JVM");

					return false;
				}

				if (_isKnownFlakyIssue(throwable) || _isTestcaseRetryable()) {
					return true;
				}

				return false;
			}

			private boolean _isTestcaseRetryable() {
				if ((_testcaseRetryCount >=
						PropsValues.TEST_TESTCASE_MAX_RETRIES) ||
					PropsValues.TEST_SKIP_TEAR_DOWN ||
					(PropsValues.TEST_TESTCASE_MAX_RETRIES == 0)) {

					return false;
				}

				return true;
			}

			private final Statement _statement;
			private int _testcaseRetryCount;
			private final List<String> _testResultMessages = new ArrayList<>();
			private final Throwable[] _validRetryThrowables = {
				new TimeoutException(), new UnreachableBrowserException(null),
				new WebDriverException(
					"Timed out waiting 45 seconds for Firefox to start."),
				new WebDriverException(
					"unknown error: unable to discover open pages")
			};

		}

	}

}
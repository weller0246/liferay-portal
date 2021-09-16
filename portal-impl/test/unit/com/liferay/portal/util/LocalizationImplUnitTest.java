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

package com.liferay.portal.util;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class LocalizationImplUnitTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetDefaultImportLocale1() {
		_testGetDefaultImportLocale(
			"es_ES", "es_ES,en_US,de_DE", "es_ES", true);
	}

	@Test
	public void testGetDefaultImportLocale2() {
		_testGetDefaultImportLocale(
			"en_US", "bg_BG,en_US,de_DE", "en_US", true);
	}

	@Test
	public void testGetDefaultImportLocale3() {
		_testGetDefaultImportLocale(
			"bg_BG", "bg_BG,en_US,de_DE", "en_US", true);
	}

	@Test
	public void testGetDefaultImportLocale4() {
		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				LocalizationImpl.class.getName(), Level.WARNING)) {

			_testGetDefaultImportLocale("bg_BG", "bg_BG,fr_FR", "bg_BG", true);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Language es_ES is missing for com.liferay.portal.className " +
					"with primary key 0. Setting default language to bg_BG.",
				logEntry.getMessage());
		}
	}

	@Test
	public void testGetDefaultImportLocale5() {
		_testGetDefaultImportLocale(
			"es_ES", "es_ES,en_US,de_DE", "en_US", false);
	}

	private Locale[] _getContentAvailableLocales(String languageIdsString) {
		String[] languageIds = StringUtil.split(languageIdsString);

		Locale[] locale = new Locale[languageIds.length];

		for (int i = 0; i < languageIds.length; i++) {
			locale[i] = LocaleUtil.fromLanguageId(languageIds[i], false);
		}

		return locale;
	}

	private void _testGetDefaultImportLocale(
		String defaultContentLanguageId, String portalAvailableLanguageIds,
		String expectedLanguageId, boolean expectedResult) {

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(
			(Language)ProxyUtil.newProxyInstance(
				Language.class.getClassLoader(),
				new Class<?>[] {Language.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
						Object proxy, Method method, Object[] args) {

						String methodName = method.getName();

						if (methodName.equals("getAvailableLocales")) {
							return _getContentAvailableLocales(
								portalAvailableLanguageIds);
						}

						if (methodName.equals("isAvailableLocale")) {
							Locale locale = (Locale)args[0];

							Locale[] portalLocales =
								_getContentAvailableLocales(
									portalAvailableLanguageIds);

							return ArrayUtil.contains(portalLocales, locale);
						}

						throw new UnsupportedOperationException();
					}

				}));

		Locale locale = LocaleUtil.fromLanguageId(defaultContentLanguageId);

		LocaleUtil.setDefault(
			locale.getLanguage(), locale.getCountry(), locale.getVariant());

		LocalizationUtil localizationUtil = new LocalizationUtil();

		localizationUtil.setLocalization(new LocalizationImpl());

		Locale contentDefaultLocale = LocaleUtil.fromLanguageId("es_ES");

		Locale[] contentAvailableLocales = _getContentAvailableLocales(
			"es_ES,en_US,de_DE");

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(
			"com.liferay.portal.className", 0L, contentDefaultLocale,
			contentAvailableLocales);

		if (expectedResult) {
			Assert.assertTrue(
				LocaleUtil.equals(
					LocaleUtil.fromLanguageId(expectedLanguageId),
					defaultImportLocale));
		}
		else {
			Assert.assertFalse(
				LocaleUtil.equals(
					LocaleUtil.fromLanguageId(expectedLanguageId),
					defaultImportLocale));
		}
	}

}
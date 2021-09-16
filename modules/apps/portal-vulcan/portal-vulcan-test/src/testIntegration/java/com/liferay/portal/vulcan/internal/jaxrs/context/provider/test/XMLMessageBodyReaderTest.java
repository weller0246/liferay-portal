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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.vulcan.internal.test.util.URLConnectionUtil;

import java.io.InputStream;
import java.io.OutputStreamWriter;

import java.net.URLConnection;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Ivica Cardic
 */
@RunWith(Arquillian.class)
public class XMLMessageBodyReaderTest {

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(XMLMessageBodyReaderTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			Application.class, new TestApplication(),
			HashMapDictionaryBuilder.<String, Object>put(
				"liferay.auth.verifier", true
			).put(
				"liferay.oauth2", false
			).put(
				"osgi.jaxrs.application.base", "/test-vulcan"
			).put(
				"osgi.jaxrs.extension.select",
				"(osgi.jaxrs.name=Liferay.Vulcan)"
			).build());
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testIsReadFromXML() throws Exception {
		URLConnection urlConnection = URLConnectionUtil.createURLConnection(
			"http://localhost:8080/o/test-vulcan/test-class");

		urlConnection.setDoOutput(true);
		urlConnection.setRequestProperty(
			"Content-Type", MediaType.APPLICATION_XML);

		try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				urlConnection.getOutputStream())) {

			outputStreamWriter.write(
				"<TestClass><number>1</number><string>hello</string>" +
					"<testClass><number>6</number><string>hi</string>" +
						"</testClass></TestClass>");
		}

		Document document = null;

		try (InputStream inputStream = urlConnection.getInputStream()) {
			String content = StringUtil.read(inputStream);

			document = SAXReaderUtil.read(content);
		}

		Element testClassElement = document.getRootElement();

		Assert.assertEquals(
			1L, GetterUtil.getLong(testClassElement.elementText("number")));
		Assert.assertEquals("hello", testClassElement.elementText("string"));

		Element innerTestClassElement = testClassElement.element("testClass");

		Assert.assertEquals(
			6L,
			GetterUtil.getLong(innerTestClassElement.elementText("number")));
		Assert.assertEquals("hi", innerTestClassElement.elementText("string"));
		Assert.assertNull(innerTestClassElement.element("testClass"));
	}

	public static class TestApplication extends Application {

		@Override
		public Set<Object> getSingletons() {
			return Collections.singleton(this);
		}

		@Path("/test-class")
		@POST
		@Produces(MediaType.APPLICATION_XML)
		public TestClass testClass(TestClass testClass) {
			return testClass;
		}

	}

	public static class TestClass {

		public Long number;
		public String string;
		public TestClass testClass;

	}

	private ServiceRegistration<Application> _serviceRegistration;

}
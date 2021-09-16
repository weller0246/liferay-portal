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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.util.PortalImpl;
import com.liferay.util.SerializableUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Dante Wang
 */
public abstract class BaseSessionMapsTestCase {

	@BeforeClass
	public static void setUpClass() {
		httpSessionInvocationHandler = new HttpSessionInvocationHandler();

		httpSession = (HttpSession)ProxyUtil.newProxyInstance(
			HttpSession.class.getClassLoader(),
			new Class<?>[] {HttpSession.class}, httpSessionInvocationHandler);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@Before
	public void setUp() {
		httpSessionInvocationHandler._attributes.clear();
		httpSessionInvocationHandler._invalidated = false;
	}

	protected static final String KEY1 = "key1";

	protected static final String KEY2 = "key2";

	protected static final String KEY3 = "key3";

	protected static final String VALUE1 = "value1";

	protected static final String VALUE2 = "value2";

	protected static final String VALUE3 = "value3";

	protected static HttpSession httpSession;
	protected static HttpSessionInvocationHandler httpSessionInvocationHandler;

	protected static class HttpSessionInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (_invalidated) {
				throw new IllegalStateException("Invalidated");
			}

			String methodName = method.getName();

			if (methodName.equals("setAttribute")) {
				_attributes.put(
					(String)args[0], SerializableUtil.serialize(args[1]));
			}
			else if (methodName.equals("getAttribute")) {
				Object result = _attributes.get(args[0]);

				if (result != null) {
					return SerializableUtil.deserialize((byte[])result);
				}
			}

			return null;
		}

		public void setInvalidated(boolean invalidated) {
			_invalidated = invalidated;
		}

		private final Map<String, Object> _attributes = new HashMap<>();
		private boolean _invalidated;

	}

}
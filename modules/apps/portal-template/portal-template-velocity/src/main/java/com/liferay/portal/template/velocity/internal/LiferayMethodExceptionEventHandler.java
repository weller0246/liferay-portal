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

package com.liferay.portal.template.velocity.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.app.event.MethodExceptionEventHandler;

/**
 * @author Raymond Augé
 * @author Peter Shin
 */
public class LiferayMethodExceptionEventHandler
	implements MethodExceptionEventHandler {

	@Override
	public Object methodException(
			@SuppressWarnings("rawtypes") Class clazz, String method,
			Exception exception)
		throws Exception {

		_log.error(
			StringBundler.concat(
				"Unable to execute method ", method, " {exception=", exception,
				StringPool.COMMA_AND_SPACE, getKeyValuePair(clazz),
				StringPool.CLOSE_CURLY_BRACE),
			exception);

		return null;
	}

	protected String getKeyValuePair(Class<?> clazz) {
		if (clazz == null) {
			return "class=null";
		}

		if (!ProxyUtil.isProxyClass(clazz)) {
			return "className=" + clazz.getName();
		}

		Class<?>[] interfaceClasses = clazz.getInterfaces();

		if (interfaceClasses == null) {
			return "className=" + clazz.getName();
		}

		List<String> proxyInterfaceClassNames = new ArrayList<>();

		for (Class<?> interfaceClass : interfaceClasses) {
			proxyInterfaceClassNames.add(interfaceClass.getName());
		}

		String mergedProxyInterfaceClassNames = StringUtil.merge(
			proxyInterfaceClassNames, StringPool.COMMA_AND_SPACE);

		return "proxyInterfaceClassNames=" + mergedProxyInterfaceClassNames;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayMethodExceptionEventHandler.class);

}
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

package com.liferay.batch.planner.service.http;

import com.liferay.batch.planner.service.BatchPlannerPlanServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>BatchPlannerPlanServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerPlanServiceSoap
 * @generated
 */
public class BatchPlannerPlanServiceHttp {

	public static com.liferay.batch.planner.model.BatchPlannerPlan
			addBatchPlannerPlan(
				HttpPrincipal httpPrincipal, boolean export,
				String externalType, String externalURL,
				String internalClassName, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "addBatchPlannerPlan",
				_addBatchPlannerPlanParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, export, externalType, externalURL, internalClassName,
				name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.batch.planner.model.BatchPlannerPlan)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlan
			deleteBatchPlannerPlan(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "deleteBatchPlannerPlan",
				_deleteBatchPlannerPlanParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.batch.planner.model.BatchPlannerPlan)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlan
			getBatchPlannerPlan(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "getBatchPlannerPlan",
				_getBatchPlannerPlanParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.batch.planner.model.BatchPlannerPlan)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPlan
			updateBatchPlannerPlan(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId,
				String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPlanServiceUtil.class, "updateBatchPlannerPlan",
				_updateBatchPlannerPlanParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.batch.planner.model.BatchPlannerPlan)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BatchPlannerPlanServiceHttp.class);

	private static final Class<?>[] _addBatchPlannerPlanParameterTypes0 =
		new Class[] {
			boolean.class, String.class, String.class, String.class,
			String.class
		};
	private static final Class<?>[] _deleteBatchPlannerPlanParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getBatchPlannerPlanParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _updateBatchPlannerPlanParameterTypes3 =
		new Class[] {long.class, String.class};

}
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

package com.liferay.portal.workflow.kaleo.runtime;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface KaleoSignaler {

	public void signalEntry(
			String transitionName, ExecutionContext executionContext)
		throws PortalException;

	public default void signalEntry(
			String transitionName, ExecutionContext executionContext,
			boolean waitForCompletion)
		throws PortalException {

		if (waitForCompletion) {
			throw new UnsupportedOperationException();
		}

		signalEntry(transitionName, executionContext);
	}

	public void signalExecute(
			KaleoNode currentKaleoNode, ExecutionContext executionContext)
		throws PortalException;

	public default void signalExecute(
			KaleoNode currentKaleoNode, ExecutionContext executionContext,
			boolean waitForCompletion)
		throws PortalException {

		if (waitForCompletion) {
			throw new UnsupportedOperationException();
		}

		signalExecute(currentKaleoNode, executionContext);
	}

	public void signalExit(
			String transitionName, ExecutionContext executionContext)
		throws PortalException;

	public default void signalExit(
			String transitionName, ExecutionContext executionContext,
			boolean waitForCompletion)
		throws PortalException {

		if (waitForCompletion) {
			throw new UnsupportedOperationException();
		}

		signalExit(transitionName, executionContext);
	}

}
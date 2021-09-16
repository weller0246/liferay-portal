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

package com.liferay.portal.test.rule;

import com.liferay.petra.string.StringBundler;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

/**
 * @author William Newbury
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
public class LogAssertionAppender extends AppenderSkeleton {

	public static final LogAssertionAppender INSTANCE =
		new LogAssertionAppender();

	@Override
	public void close() {
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected void append(LoggingEvent loggingEvent) {
		Level level = loggingEvent.getLevel();

		if (level.equals(Level.ERROR) || level.equals(Level.FATAL)) {
			ThrowableInformation throwableInformation =
				loggingEvent.getThrowableInformation();

			LogAssertionTestRule.caughtFailure(
				new AssertionError(
					StringBundler.concat(
						"{level=", loggingEvent.getLevel(), ", loggerName=",
						loggingEvent.getLoggerName(), ", message=",
						loggingEvent.getMessage()),
					throwableInformation.getThrowable()));
		}
	}

}
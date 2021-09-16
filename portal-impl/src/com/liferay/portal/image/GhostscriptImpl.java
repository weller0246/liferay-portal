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

package com.liferay.portal.image;

import com.liferay.petra.process.LoggingOutputProcessor;
import com.liferay.petra.process.ProcessUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.image.Ghostscript;
import com.liferay.portal.kernel.image.ImageMagickUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OSDetector;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Ivica Cardic
 */
public class GhostscriptImpl implements Ghostscript {

	@Override
	public Future<?> execute(List<String> commandArguments) throws Exception {
		if (!isEnabled()) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Cannot execute the Ghostscript command. Please install ",
					"ImageMagick and Ghostscript and enable ImageMagick in ",
					"portal-ext.properties or in the Server Administration ",
					"section of the Control Panel at: ",
					"http://<server>/group/control_panel/manage/-/server",
					"/external-services."));
		}

		LinkedList<String> arguments = new LinkedList<>();

		arguments.add(_commandPath);
		arguments.add("-dBATCH");
		arguments.add("-dSAFER");
		arguments.add("-dNOPAUSE");
		arguments.add("-dNOPROMPT");
		arguments.add("-sFONTPATH=" + _globalSearchPath);
		arguments.addAll(commandArguments);

		if (_log.isInfoEnabled()) {
			StringBundler sb = new StringBundler(arguments.size() * 2);

			for (String argument : arguments) {
				sb.append(argument);
				sb.append(StringPool.SPACE);
			}

			_log.info("Executing command '" + sb.toString() + "'");
		}

		return ProcessUtil.execute(
			new LoggingOutputProcessor(
				(stdErr, line) -> {
					if (stdErr) {
						_log.error(line);
					}
					else if (_log.isInfoEnabled()) {
						_log.info(line);
					}
				}),
			arguments);
	}

	@Override
	public boolean isEnabled() {
		return ImageMagickUtil.isEnabled();
	}

	@Override
	public void reset() {
		if (isEnabled()) {
			try {
				_globalSearchPath = ImageMagickUtil.getGlobalSearchPath();

				_commandPath = getCommandPath();
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}
	}

	protected String getCommandPath() throws Exception {
		String commandPath = null;

		if (OSDetector.isWindows()) {
			commandPath = getCommandPathWindows();
		}
		else {
			commandPath = getCommandPathUnix();
		}

		if (commandPath == null) {
			throw new FileNotFoundException(
				StringBundler.concat(
					"Unable to find the Ghostscript command. Please verify ",
					"the path specified in the Server Administration control ",
					"panel at: http://<server>/group/control_panel/manage/-",
					"/server/external-services."));
		}

		return commandPath;
	}

	protected String getCommandPathUnix() throws Exception {
		String[] dirNames = _globalSearchPath.split(File.pathSeparator);

		for (String dirName : dirNames) {
			File file = new File(dirName, _GHOSTSCRIPT_COMMAND_UNIX);

			if (file.exists()) {
				return file.getCanonicalPath();
			}
		}

		return null;
	}

	protected String getCommandPathWindows() throws Exception {
		String[] dirNames = _globalSearchPath.split(File.pathSeparator);

		for (String dirName : dirNames) {
			for (String command : _GHOSTSCRIPT_COMMAND_WINDOWS) {
				File file = new File(dirName, command + ".exe");

				if (!file.exists()) {
					file = new File(dirName, command + ".cmd");

					if (!file.exists()) {
						file = new File(dirName, command + ".bat");

						if (!file.exists()) {
							continue;
						}
					}
				}

				return file.getCanonicalPath();
			}
		}

		return null;
	}

	private static final String _GHOSTSCRIPT_COMMAND_UNIX = "gs";

	private static final String[] _GHOSTSCRIPT_COMMAND_WINDOWS = {
		"gswin32c", "gswin64c"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		GhostscriptImpl.class);

	private String _commandPath;
	private String _globalSearchPath;

}
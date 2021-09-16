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

package com.liferay.portal.store.s3;

import com.liferay.petra.io.unsync.UnsyncFilterInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.store.s3.configuration.S3StoreConfiguration;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.store.s3.configuration.S3StoreConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = S3FileCache.class
)
public class S3FileCache {

	public void cleanUpCacheFiles() {
		_calledCleanUpCacheFilesCount++;

		if (_calledCleanUpCacheFilesCount <
				_cacheDirCleanUpFrequency.intValue()) {

			return;
		}

		synchronized (this) {
			if (_calledCleanUpCacheFilesCount == 0) {
				return;
			}

			_calledCleanUpCacheFilesCount = 0;

			Path cacheDirPath = Paths.get(getCacheDirName());

			long lastModified = System.currentTimeMillis();

			lastModified -= _cacheDirCleanUpExpunge.intValue() * Time.DAY;

			cleanUpCacheFiles(cacheDirPath, lastModified);
		}
	}

	public InputStream getCacheFileInputStream(
			Closeable closeable, String fileName,
			Supplier<InputStream> inputStreamSupplier, Date lastModifiedDate)
		throws IOException {

		String cacheFileName = StringBundler.concat(
			getCacheDirName(),
			DateUtil.getCurrentDate(
				_CACHE_DIR_PATTERN, LocaleUtil.getDefault()),
			_s3KeyTransformer.getNormalizedFileName(fileName),
			lastModifiedDate.getTime());

		File cacheFile = new File(cacheFileName);

		if (cacheFile.exists() &&
			(cacheFile.lastModified() >= lastModifiedDate.getTime())) {

			closeable.close();

			return new FileInputStream(cacheFile);
		}

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			InputStream s3InputStream = inputStreamSupplier.get();

			if (s3InputStream == null) {
				throw new IOException("S3 object input stream is null");
			}

			return new UnsyncFilterInputStream(s3InputStream) {

				@Override
				public void close() throws IOException {
					super.close();

					closeable.close();
				}

			};
		}

		try (InputStream inputStream = inputStreamSupplier.get()) {
			if (inputStream == null) {
				throw new IOException("S3 object input stream is null");
			}

			FileUtil.mkdirs(cacheFile.getParentFile());

			try (OutputStream outputStream = new FileOutputStream(cacheFile)) {
				StreamUtil.transfer(inputStream, outputStream);
			}
		}
		finally {
			closeable.close();
		}

		return new FileInputStream(cacheFile);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_s3StoreConfiguration = ConfigurableUtil.createConfigurable(
			S3StoreConfiguration.class, properties);

		_cacheDirCleanUpExpunge = new AtomicInteger(
			_s3StoreConfiguration.cacheDirCleanUpExpunge());
		_cacheDirCleanUpFrequency = new AtomicInteger(
			_s3StoreConfiguration.cacheDirCleanUpFrequency());
	}

	protected void cleanUpCacheFiles(Path cacheDirPath, long lastModified) {
		if (Files.notExists(cacheDirPath)) {
			return;
		}

		try {
			Files.walkFileTree(
				cacheDirPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult postVisitDirectory(
							Path dirPath, IOException ioException)
						throws IOException {

						try (DirectoryStream<Path> directoryStream =
								Files.newDirectoryStream(dirPath)) {

							Iterator<Path> iterator =
								directoryStream.iterator();

							if (!iterator.hasNext()) {
								Files.delete(dirPath);
							}
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						FileTime fileTime = Files.getLastModifiedTime(filePath);

						if (fileTime.toMillis() < lastModified) {
							Files.delete(filePath);
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to clean up cache files for " + cacheDirPath,
				ioException);
		}
	}

	@Deactivate
	protected void deactivate() {
		File cacheDir = new File(getCacheDirName());

		boolean deleted = cacheDir.delete();

		if (!deleted) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to delete " + getCacheDirName());
			}
		}
	}

	protected String getCacheDirName() {
		return SystemProperties.get(SystemProperties.TMP_DIR) + _CACHE_DIR_NAME;
	}

	@Reference(unbind = "-")
	protected void setS3KeyTransformer(S3KeyTransformer s3KeyTransformer) {
		_s3KeyTransformer = s3KeyTransformer;
	}

	private static final String _CACHE_DIR_NAME = "/liferay/s3";

	private static final String _CACHE_DIR_PATTERN = "/yyyy/MM/dd/HH/";

	private static final Log _log = LogFactoryUtil.getLog(S3FileCache.class);

	private volatile AtomicInteger _cacheDirCleanUpExpunge;
	private volatile AtomicInteger _cacheDirCleanUpFrequency;
	private int _calledCleanUpCacheFilesCount;
	private S3KeyTransformer _s3KeyTransformer;
	private volatile S3StoreConfiguration _s3StoreConfiguration;

}
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

package com.liferay.portal.fabric.netty.fileserver;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FileRequestTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Before
	public void setUp() {
		_fileRequest = new FileRequest(
			_path, _LAST_MODIFIED_TIME, _DELETE_AFTER_FETCH);
	}

	@Test
	public void testConstructor() {
		try {
			new FileRequest(null, System.currentTimeMillis(), true);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
		}

		Assert.assertEquals(_path, _fileRequest.getPath());
		Assert.assertEquals(
			_LAST_MODIFIED_TIME, _fileRequest.getLastModifiedTime());
		Assert.assertEquals(
			_DELETE_AFTER_FETCH, _fileRequest.isDeleteAfterFetch());
	}

	@Test
	public void testEquals() {
		Assert.assertTrue(_fileRequest.equals(_fileRequest));
		Assert.assertFalse(_fileRequest.equals(new Object()));
		Assert.assertFalse(
			_fileRequest.equals(
				new FileRequest(
					Paths.get("unknown"), _LAST_MODIFIED_TIME,
					_DELETE_AFTER_FETCH)));
		Assert.assertFalse(
			_fileRequest.equals(
				new FileRequest(
					_path, _LAST_MODIFIED_TIME + 1, _DELETE_AFTER_FETCH)));
		Assert.assertFalse(
			_fileRequest.equals(
				new FileRequest(
					_path, _LAST_MODIFIED_TIME, !_DELETE_AFTER_FETCH)));
		Assert.assertTrue(
			_fileRequest.equals(
				new FileRequest(
					_path, _LAST_MODIFIED_TIME, _DELETE_AFTER_FETCH)));
	}

	@Test
	public void testHashCode() {
		int hash = HashUtil.hash(0, _DELETE_AFTER_FETCH);

		hash = HashUtil.hash(hash, _LAST_MODIFIED_TIME);

		Assert.assertEquals(
			HashUtil.hash(hash, _path), _fileRequest.hashCode());
	}

	@Test
	public void testToString() {
		Assert.assertEquals(
			StringBundler.concat(
				"{deleteAfterFetch=", _DELETE_AFTER_FETCH,
				", lastModifiedTime=", _LAST_MODIFIED_TIME, ", pathHolder=",
				_path, "}"),
			_fileRequest.toString());
	}

	private static final boolean _DELETE_AFTER_FETCH = true;

	private static final long _LAST_MODIFIED_TIME = System.currentTimeMillis();

	private FileRequest _fileRequest;
	private final Path _path = Paths.get("testPath");

}
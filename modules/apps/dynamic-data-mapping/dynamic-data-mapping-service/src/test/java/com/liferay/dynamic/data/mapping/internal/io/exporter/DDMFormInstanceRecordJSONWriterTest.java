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

package com.liferay.dynamic.data.mapping.internal.io.exporter;

import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DDMFormInstanceRecordJSONWriterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testWrite() throws Exception {
		DDMFormInstanceRecordJSONWriter ddmFormInstanceRecordJSONWriter =
			new DDMFormInstanceRecordJSONWriter();

		ddmFormInstanceRecordJSONWriter.jsonFactory = new JSONFactoryImpl();

		List<Map<String, String>> ddmFormFieldValues =
			new ArrayList<Map<String, String>>() {
				{
					add(
						HashMapBuilder.put(
							"field1", "2"
						).put(
							"field2", "false"
						).put(
							"field3", "11.7"
						).build());

					add(
						HashMapBuilder.put(
							"field1", "1"
						).put(
							"field2", ""
						).put(
							"field3", "10"
						).build());
				}
			};

		DDMFormInstanceRecordWriterRequest.Builder builder =
			DDMFormInstanceRecordWriterRequest.Builder.newBuilder(
				Collections.emptyMap(), ddmFormFieldValues);

		DDMFormInstanceRecordWriterResponse
			ddmFormInstanceRecordWriterResponse =
				ddmFormInstanceRecordJSONWriter.write(builder.build());

		String expectedJSON = StringBundler.concat(
			"[{\"field1\":\"2\",\"field3\":\"11.7\",\"field2\":",
			"\"false\"},{\"field1\":\"1\",\"field3\":\"10\",\"field2\":",
			"\"\"}]");

		Assert.assertArrayEquals(
			expectedJSON.getBytes(),
			ddmFormInstanceRecordWriterResponse.getContent());
	}

}
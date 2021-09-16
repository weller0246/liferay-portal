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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DDMFormInstanceRecordCSVWriterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testWrite() throws Exception {
		Map<String, String> ddmFormFieldsLabel = LinkedHashMapBuilder.put(
			"field1", "Field 1"
		).put(
			"field2", "Field 2"
		).put(
			"field3", "Field 3"
		).put(
			"field4", "Field 4"
		).build();

		List<Map<String, String>> ddmFormFieldValues =
			new ArrayList<Map<String, String>>() {
				{
					add(
						LinkedHashMapBuilder.put(
							"field1", "2"
						).put(
							"field2", "esta é uma 'string'"
						).put(
							"field3", "false"
						).put(
							"field4", "11.7"
						).build());

					add(
						LinkedHashMapBuilder.put(
							"field1", "1"
						).put(
							"field2", "esta é uma 'string'"
						).put(
							"field3", ""
						).put(
							"field4", "10"
						).build());
				}
			};

		DDMFormInstanceRecordWriterRequest.Builder builder =
			DDMFormInstanceRecordWriterRequest.Builder.newBuilder(
				ddmFormFieldsLabel, ddmFormFieldValues);

		DDMFormInstanceRecordCSVWriter ddmFormInstanceRecordCSVWriter =
			new DDMFormInstanceRecordCSVWriter();

		DDMFormInstanceRecordWriterResponse
			ddmFormInstanceRecordWriterResponse =
				ddmFormInstanceRecordCSVWriter.write(builder.build());

		String expected = StringBundler.concat(
			"Field 1,Field 2,Field 3,Field 4\n",
			"2,esta é uma 'string',false,11.7\n", "1,esta é uma 'string',,10");

		Assert.assertArrayEquals(
			expected.getBytes(),
			ddmFormInstanceRecordWriterResponse.getContent());
	}

	@Test
	public void testWriteAfterChangeFieldName() throws Exception {
		Map<String, String> ddmFormFieldsLabel = LinkedHashMapBuilder.put(
			"field1", "Field 1"
		).put(
			"field1AfterChangeName", "Field 1"
		).put(
			"field2", "Field 2"
		).build();

		List<Map<String, String>> ddmFormFieldValues =
			new ArrayList<Map<String, String>>() {
				{
					add(
						HashMapBuilder.put(
							"field1", "1"
						).put(
							"field1AfterChangeName", ""
						).put(
							"field2", "esta é uma 'string'"
						).build());

					add(
						HashMapBuilder.put(
							"field1", ""
						).put(
							"field1AfterChangeName", "2"
						).put(
							"field2", "esta é uma 'string'"
						).build());
				}
			};

		DDMFormInstanceRecordWriterRequest.Builder builder =
			DDMFormInstanceRecordWriterRequest.Builder.newBuilder(
				ddmFormFieldsLabel, ddmFormFieldValues);

		DDMFormInstanceRecordCSVWriter ddmFormInstanceRecordCSVWriter =
			new DDMFormInstanceRecordCSVWriter();

		DDMFormInstanceRecordWriterResponse
			ddmFormInstanceRecordWriterResponse =
				ddmFormInstanceRecordCSVWriter.write(builder.build());

		String expected = StringBundler.concat(
			"Field 1 (field1),Field 1 (field1AfterChangeName),Field 2\n",
			"1,,esta é uma 'string'\n", ",2,esta é uma 'string'");

		Assert.assertArrayEquals(
			expected.getBytes(),
			ddmFormInstanceRecordWriterResponse.getContent());
	}

	@Test
	public void testWriteRecords() {
		DDMFormInstanceRecordCSVWriter ddmFormInstanceRecordCSVWriter =
			new DDMFormInstanceRecordCSVWriter();

		List<Map<String, String>> ddmFormFieldValues =
			new ArrayList<Map<String, String>>() {
				{
					add(
						LinkedHashMapBuilder.put(
							"field1", "value1"
						).put(
							"field2", "false"
						).put(
							"field3", "134.5"
						).build());

					add(
						LinkedHashMapBuilder.put(
							"field1", ""
						).put(
							"field2", "true"
						).put(
							"field3", "45"
						).build());
				}
			};

		String actual = ddmFormInstanceRecordCSVWriter.writeRecords(
			ddmFormFieldValues);

		Assert.assertEquals("value1,false,134.5\n,true,45", actual);
	}

	@Test
	public void testWriteValues() {
		DDMFormInstanceRecordCSVWriter ddmFormInstanceRecordCSVWriter =
			new DDMFormInstanceRecordCSVWriter();

		List<String> values = new ArrayList<String>() {
			{
				add("value1");
				add("2");
				add("true");
				add("this is a \"string\"");
			}
		};

		String actualValue = ddmFormInstanceRecordCSVWriter.writeValues(values);

		Assert.assertEquals(
			"value1,2,true,\"this is a \"\"string\"\"\"", actualValue);
	}

}
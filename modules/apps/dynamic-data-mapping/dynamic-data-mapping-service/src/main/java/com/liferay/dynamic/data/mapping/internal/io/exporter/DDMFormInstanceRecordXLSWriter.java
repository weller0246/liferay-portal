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

import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriter;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.ByteArrayOutputStream;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.instance.record.writer.type=xls",
	service = DDMFormInstanceRecordWriter.class
)
public class DDMFormInstanceRecordXLSWriter
	implements DDMFormInstanceRecordWriter {

	@Override
	public DDMFormInstanceRecordWriterResponse write(
			DDMFormInstanceRecordWriterRequest
				ddmFormInstanceRecordWriterRequest)
		throws Exception {

		Map<String, String> ddmFormFieldsLabel =
			ddmFormInstanceRecordWriterRequest.getDDMFormFieldsLabel();

		int rowIndex = 0;

		try (ByteArrayOutputStream byteArrayOutputStream =
				createByteArrayOutputStream();
			Workbook workbook = createWorkbook()) {

			Sheet sheet = workbook.createSheet();

			CellStyle headerCellStyle = createCellStyle(
				workbook, true, "Courier New", (short)14);

			Collection<String> values = ddmFormFieldsLabel.values();

			if ((values.size() > _COLUMNS_MAX_COUNT) && _log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Form has ", values.size(),
						" fields. Due to XLS file format limitations, the ",
						"first ", _COLUMNS_MAX_COUNT,
						" will be included in the exported file."));
			}

			createRow(rowIndex++, headerCellStyle, values, sheet);

			CellStyle rowCellStyle = createCellStyle(
				workbook, false, "Courier New", (short)12);

			List<Map<String, String>> ddmFormFieldsValueList =
				ddmFormInstanceRecordWriterRequest.getDDMFormFieldValues();

			for (Map<String, String> ddmFormFieldsValue :
					ddmFormFieldsValueList) {

				createRow(
					rowIndex++, rowCellStyle, ddmFormFieldsValue.values(),
					sheet);
			}

			workbook.write(byteArrayOutputStream);

			DDMFormInstanceRecordWriterResponse.Builder builder =
				DDMFormInstanceRecordWriterResponse.Builder.newBuilder(
					byteArrayOutputStream.toByteArray());

			return builder.build();
		}
	}

	protected ByteArrayOutputStream createByteArrayOutputStream() {
		return new ByteArrayOutputStream();
	}

	protected CellStyle createCellStyle(
		Workbook workbook, boolean bold, String fontName,
		short heightInPoints) {

		Font font = workbook.createFont();

		font.setBold(bold);
		font.setFontHeightInPoints(heightInPoints);
		font.setFontName(fontName);

		CellStyle style = workbook.createCellStyle();

		style.setFont(font);

		return style;
	}

	protected void createRow(
		int rowIndex, CellStyle cellStyle, Collection<String> values,
		Sheet sheet) {

		Row row = sheet.createRow(rowIndex);

		int cellIndex = 0;

		for (String value : values) {
			if (value.length() > _CELL_MAX_LENGTH) {
				value = value.substring(0, _CELL_MAX_LENGTH - 1);

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Cell ", rowIndex, ",", cellIndex,
							" value trimmed to ", _CELL_MAX_LENGTH,
							" characters"));
				}
			}

			Cell cell = row.createCell(cellIndex++, CellType.STRING);

			cell.setCellStyle(cellStyle);
			cell.setCellValue(value);

			if (cellIndex == _COLUMNS_MAX_COUNT) {
				break;
			}
		}
	}

	protected Workbook createWorkbook() {
		return new HSSFWorkbook();
	}

	private static final int _CELL_MAX_LENGTH = 32767;

	private static final int _COLUMNS_MAX_COUNT = 256;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordXLSWriter.class);

}
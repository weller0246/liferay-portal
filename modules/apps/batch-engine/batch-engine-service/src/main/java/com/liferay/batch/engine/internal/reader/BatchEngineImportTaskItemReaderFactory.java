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

package com.liferay.batch.engine.internal.reader;

import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.internal.ZipInputStreamUtil;

import java.io.InputStream;

/**
 * @author Shuyang Zhou
 * @author Ivica Cardic
 */
public class BatchEngineImportTaskItemReaderFactory {

	public BatchEngineImportTaskItemReaderFactory(
		String csvFileColumnDelimiter) {

		_csvFileColumnDelimiter = csvFileColumnDelimiter;
	}

	public BatchEngineImportTaskItemReader create(
			BatchEngineTaskContentType batchEngineTaskContentType,
			InputStream inputStream)
		throws Exception {

		inputStream = ZipInputStreamUtil.asZipInputStream(inputStream);

		if (batchEngineTaskContentType == BatchEngineTaskContentType.CSV) {
			return new CSVBatchEngineImportTaskItemReader(
				_csvFileColumnDelimiter, inputStream);
		}

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSON) {
			return new JSONBatchEngineImportTaskItemReader(inputStream);
		}

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSONL) {
			return new JSONLBatchEngineImportTaskItemReader(inputStream);
		}

		if ((batchEngineTaskContentType == BatchEngineTaskContentType.XLS) ||
			(batchEngineTaskContentType == BatchEngineTaskContentType.XLSX)) {

			return new XLSBatchEngineImportTaskItemReader(inputStream);
		}

		throw new IllegalArgumentException(
			"Unknown batch engine task content type " +
				batchEngineTaskContentType);
	}

	private final String _csvFileColumnDelimiter;

}
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

package com.liferay.batch.planner.internal.batch.engine.broker;

import com.liferay.batch.planner.batch.engine.broker.BatchEngineBroker;
import com.liferay.batch.planner.exception.BatchPlannerMappingExternalFieldNameException;
import com.liferay.batch.planner.internal.jaxrs.uri.EmptyUriInfo;
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.service.BatchPlannerLogLocalService;
import com.liferay.batch.planner.service.BatchPlannerMappingLocalService;
import com.liferay.batch.planner.service.BatchPlannerPlanLocalService;
import com.liferay.batch.planner.service.BatchPlannerPolicyLocalService;
import com.liferay.headless.batch.engine.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.resource.v1_0.ImportTaskResource;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;

import java.net.URI;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(service = BatchEngineBroker.class)
public class BatchEngineBrokerImpl implements BatchEngineBroker {

	public void submit(long batchPlannerPlanId) {
		try {
			_submit(batchPlannerPlanId);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to execute batch planner plan ID " + batchPlannerPlanId,
				exception);
		}
	}

	private String _getBatchPlannerPolicyValue(
		List<BatchPlannerPolicy> batchPlannerPolicies, String name) {

		for (BatchPlannerPolicy batchPlannerPolicy : batchPlannerPolicies) {
			if (Objects.equals(batchPlannerPolicy.getName(), name)) {
				return batchPlannerPolicy.getValue();
			}
		}

		return null;
	}

	private String[] _getHeaderNames(
		List<BatchPlannerMapping> batchPlannerMappings) {

		return TransformUtil.transformToArray(
			batchPlannerMappings,
			batchPlannerMapping -> batchPlannerMapping.getExternalFieldName(),
			String.class);
	}

	private String[] _getHeaderNames(
		List<BatchPlannerMapping> batchPlannerMappings, String delimiter,
		String headerNamesString) {

		if (Validator.isNull(headerNamesString)) {
			return _getHeaderNames(batchPlannerMappings);
		}

		String[] headerNames = headerNamesString.split(delimiter);

		if (batchPlannerMappings.size() != headerNames.length) {
			return _getHeaderNames(batchPlannerMappings);
		}

		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			if (!ArrayUtil.contains(
					headerNames, batchPlannerMapping.getExternalFieldName())) {

				return _getHeaderNames(batchPlannerMappings);
			}
		}

		return headerNames;
	}

	private File _getJSONLFile(long batchPlannerPlanId) throws Exception {
		File jsonlFile = FileUtil.createTempFile(
			String.valueOf(batchPlannerPlanId), "jsonl");

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanLocalService.getBatchPlannerPlan(
				batchPlannerPlanId);

		try (FileReader fileReader = new FileReader(
				new File(new URI(batchPlannerPlan.getExternalURL())));
			FileWriter fileWriter = new FileWriter(jsonlFile)) {

			List<BatchPlannerPolicy> batchPlannerPolicies =
				_batchPlannerPolicyLocalService.getBatchPlannerPolicies(
					batchPlannerPlanId);

			String delimiter = GetterUtil.getString(
				_getBatchPlannerPolicyValue(batchPlannerPolicies, "delimiter"),
				StringPool.SEMICOLON);

			String line = null;

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			if (GetterUtil.getBoolean(
					_getBatchPlannerPolicyValue(
						batchPlannerPolicies, "hasColumnHeaders"))) {

				line = bufferedReader.readLine();
			}

			Map<Integer, BatchPlannerMapping> batchPlannerMappingsMap =
				_toBatchPlannerMappingsMap(
					_batchPlannerMappingLocalService.getBatchPlannerMappings(
						batchPlannerPlanId),
					delimiter, line);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			line = bufferedReader.readLine();

			while (line != null) {
				bufferedWriter.append(
					_toJSON(batchPlannerMappingsMap, line.split(delimiter)));

				bufferedWriter.newLine();

				line = bufferedReader.readLine();
			}

			bufferedWriter.flush();

			return jsonlFile;
		}
		catch (Exception exception) {
			FileUtil.delete(jsonlFile);

			throw exception;
		}
	}

	private void _submit(long batchPlannerPlanId) throws Exception {
		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanLocalService.getBatchPlannerPlan(
				batchPlannerPlanId);

		_importTaskResource.setContextCompany(
			_companyLocalService.getCompany(batchPlannerPlan.getCompanyId()));

		_importTaskResource.setContextUriInfo(new EmptyUriInfo());
		_importTaskResource.setContextUser(
			_userLocalService.getUser(batchPlannerPlan.getUserId()));

		File file = _getJSONLFile(batchPlannerPlanId);

		ImportTask importTask = _importTaskResource.postImportTask(
			batchPlannerPlan.getInternalClassName(), null, null,
			"batch-planner-plan-" + batchPlannerPlanId,
			MultipartBody.of(
				Collections.singletonMap(
					"file",
					new BinaryFile(
						"application/json", file.getName(),
						new FileInputStream(file), file.length())),
				null, Collections.emptyMap()));

		_batchPlannerLogLocalService.addBatchPlannerLog(
			batchPlannerPlan.getUserId(), batchPlannerPlanId, null,
			String.valueOf(importTask.getId()), null, (int)file.length(), 1);

		_batchPlannerPlanLocalService.updateActive(batchPlannerPlanId, true);
	}

	private Map<Integer, BatchPlannerMapping> _toBatchPlannerMappingsMap(
			List<BatchPlannerMapping> batchPlannerMappings, String delimiter,
			String headerNamesString)
		throws PortalException {

		Map<Integer, BatchPlannerMapping> batchPlannerMappingsMap =
			new HashMap<>();

		String[] headerNames = _getHeaderNames(
			batchPlannerMappings, delimiter, headerNamesString);

		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			for (int i = 0; i < headerNames.length; i++) {
				if (Objects.equals(
						batchPlannerMapping.getExternalFieldName(),
						headerNames[i])) {

					batchPlannerMappingsMap.put(i, batchPlannerMapping);

					break;
				}
			}
		}

		if (batchPlannerMappingsMap.isEmpty()) {
			throw new BatchPlannerMappingExternalFieldNameException(
				"Unable to map external field names to header names");
		}

		return batchPlannerMappingsMap;
	}

	private String _toJSON(
		Map<Integer, BatchPlannerMapping> batchPlannerMappingsMap,
		String[] columns) {

		StringBundler sb = new StringBundler(
			2 + (batchPlannerMappingsMap.size() * 6));

		sb.append(CharPool.OPEN_CURLY_BRACE);

		Set<Map.Entry<Integer, BatchPlannerMapping>> set =
			batchPlannerMappingsMap.entrySet();

		Iterator<Map.Entry<Integer, BatchPlannerMapping>> iterator =
			set.iterator();

		while (iterator.hasNext()) {
			sb.append(CharPool.QUOTE);

			Map.Entry<Integer, BatchPlannerMapping> entry = iterator.next();

			BatchPlannerMapping batchPlannerMapping = entry.getValue();

			sb.append(batchPlannerMapping.getInternalFieldName());

			sb.append("\": \"");
			sb.append(columns[entry.getKey()]);
			sb.append(CharPool.QUOTE);

			if (iterator.hasNext()) {
				sb.append(CharPool.COMMA);
			}
		}

		sb.append(CharPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineBrokerImpl.class);

	@Reference
	private BatchPlannerLogLocalService _batchPlannerLogLocalService;

	@Reference
	private BatchPlannerMappingLocalService _batchPlannerMappingLocalService;

	@Reference
	private BatchPlannerPlanLocalService _batchPlannerPlanLocalService;

	@Reference
	private BatchPlannerPolicyLocalService _batchPlannerPolicyLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ImportTaskResource _importTaskResource;

	@Reference
	private UserLocalService _userLocalService;

}
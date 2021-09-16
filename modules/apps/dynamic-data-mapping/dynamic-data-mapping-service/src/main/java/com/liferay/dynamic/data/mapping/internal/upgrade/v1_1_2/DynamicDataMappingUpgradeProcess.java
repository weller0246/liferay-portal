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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_2;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueTransformer;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesSerializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesTransformer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class DynamicDataMappingUpgradeProcess extends UpgradeProcess {

	public DynamicDataMappingUpgradeProcess(
		DDMFormDeserializer ddmFormDeserializer,
		DDMFormSerializer ddmFormSerializer,
		DDMFormValuesDeserializer ddmFormValuesDeserializer,
		DDMFormValuesSerializer ddmFormValuesSerializer,
		JSONFactory jsonFactory) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormSerializer = ddmFormSerializer;
		_ddmFormValuesDeserializer = ddmFormValuesDeserializer;
		_ddmFormValuesSerializer = ddmFormValuesSerializer;
		_jsonFactory = jsonFactory;
	}

	protected String convertJSONArrayToString(String value) {
		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(value);

			if (jsonArray.length() == 0) {
				return StringPool.BLANK;
			}

			return jsonArray.getString(0);
		}
		catch (JSONException jsonException) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsonException, jsonException);
			}

			return value;
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeDDMStructureReferences();

		upgradeDDLDDMContentReferences();
		upgradeDLDDMContentReferences();
	}

	protected DDMForm getDDMForm(long structureId) throws Exception {
		DDMForm ddmForm = _ddmForms.get(structureId);

		if (ddmForm != null) {
			return ddmForm;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select definition from DDMStructure where structureId = ?")) {

			preparedStatement.setLong(1, structureId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					ddmForm = DDMFormDeserializeUtil.deserialize(
						_ddmFormDeserializer,
						resultSet.getString("definition"));

					_ddmForms.put(structureId, ddmForm);

					return ddmForm;
				}
			}
		}

		throw new UpgradeException(
			"Unable to find dynamic data mapping structure with ID " +
				structureId);
	}

	protected DDMForm getFullHierarchyDDMForm(long structureId)
		throws Exception {

		DDMForm fullHierarchyDDMForm = _fullHierarchyDDMForms.get(structureId);

		if (fullHierarchyDDMForm != null) {
			return fullHierarchyDDMForm;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select parentStructureId from DDMStructure where " +
					"structureId = ?")) {

			preparedStatement.setLong(1, structureId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					long parentStructureId = resultSet.getLong(
						"parentStructureId");

					fullHierarchyDDMForm = getDDMForm(structureId);

					if (parentStructureId > 0) {
						DDMForm parentDDMForm = getFullHierarchyDDMForm(
							parentStructureId);

						List<DDMFormField> ddmFormFields =
							fullHierarchyDDMForm.getDDMFormFields();

						ddmFormFields.addAll(parentDDMForm.getDDMFormFields());
					}

					_fullHierarchyDDMForms.put(
						structureId, fullHierarchyDDMForm);

					return fullHierarchyDDMForm;
				}
			}
		}

		throw new UpgradeException(
			"Unable to find dynamic data mapping structure with ID " +
				structureId);
	}

	protected void transformDDMFormFieldValues(DDMFormValues ddmFormValues)
		throws Exception {

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		ddmFormValuesTransformer.addTransformer(
			new RadioDDMFormFieldValueTransformer());
		ddmFormValuesTransformer.addTransformer(
			new SelectDDMFormFieldValueTransformer());

		ddmFormValuesTransformer.transform();
	}

	protected void updateDDMFormField(DDMFormField ddmFormField)
		throws Exception {

		String type = ddmFormField.getType();

		if (type.equals("radio")) {
			LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

			for (Locale locale : predefinedValue.getAvailableLocales()) {
				String valueString = predefinedValue.getString(locale);

				if (Validator.isNull(valueString)) {
					continue;
				}

				predefinedValue.addString(
					locale, convertJSONArrayToString(valueString));
			}
		}
		else if (type.equals("select")) {
			String dataSourceType = ddmFormField.getDataSourceType();

			if (!dataSourceType.startsWith(StringPool.OPEN_BRACKET) ||
				!dataSourceType.endsWith(StringPool.CLOSE_BRACKET)) {

				ddmFormField.setProperty(
					"dataSourceType", "[\"" + dataSourceType + "\"]");
			}
		}
	}

	protected void updateDDMFormFields(DDMForm ddmForm) throws Exception {
		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		for (Map.Entry<String, DDMFormField> entry :
				ddmFormFieldsMap.entrySet()) {

			updateDDMFormField(entry.getValue());
		}
	}

	protected void upgradeDDLDDMContentReferences() throws Exception {
		upgradeDDMContentReferences(
			StringBundler.concat(
				"select DDMContent.contentId, DDMContent.data_, ",
				"DDMStructure.structureId from DDLRecordVersion inner join ",
				"DDLRecordSet on DDLRecordVersion.recordSetId = ",
				"DDLRecordSet.recordSetId inner join DDMContent on  ",
				"DDLRecordVersion.DDMStorageId = DDMContent.contentId inner ",
				"join DDMStructure on DDLRecordSet.DDMStructureId = ",
				"DDMStructure.structureId"));
	}

	protected void upgradeDDMContentReferences(String query) throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				query);
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMContent set data_= ? where contentId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String data = resultSet.getString("data_");

				long ddmStructureId = resultSet.getLong("structureId");

				DDMForm ddmForm = getFullHierarchyDDMForm(ddmStructureId);

				DDMFormValues ddmFormValues =
					DDMFormValuesDeserializeUtil.deserialize(
						data, ddmForm, _ddmFormValuesDeserializer);

				transformDDMFormFieldValues(ddmFormValues);

				preparedStatement2.setString(
					1,
					DDMFormValuesSerializeUtil.serialize(
						ddmFormValues, _ddmFormValuesSerializer));

				long contentId = resultSet.getLong("contentId");

				preparedStatement2.setLong(2, contentId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	protected void upgradeDDMStructureReferences() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select DDMStructure.structureId from DDMStructure");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?");
			PreparedStatement preparedStatement3 = connection.prepareStatement(
				"select structureVersionId, definition from " +
					"DDMStructureVersion where structureId = ?");
			PreparedStatement preparedStatement4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long ddmStructureId = resultSet.getLong("structureId");

					DDMForm ddmForm = getDDMForm(ddmStructureId);

					updateDDMFormFields(ddmForm);

					preparedStatement2.setString(
						1,
						DDMFormSerializeUtil.serialize(
							ddmForm, _ddmFormSerializer));

					preparedStatement2.setLong(2, ddmStructureId);

					preparedStatement2.addBatch();

					preparedStatement3.setLong(1, ddmStructureId);

					try (ResultSet resultSet2 =
							preparedStatement3.executeQuery()) {

						while (resultSet2.next()) {
							ddmForm = DDMFormDeserializeUtil.deserialize(
								_ddmFormDeserializer,
								resultSet2.getString("definition"));

							updateDDMFormFields(ddmForm);

							preparedStatement4.setString(
								1,
								DDMFormSerializeUtil.serialize(
									ddmForm, _ddmFormSerializer));

							long structureVersionId = resultSet2.getLong(
								"structureVersionId");

							preparedStatement4.setLong(2, structureVersionId);

							preparedStatement4.addBatch();
						}
					}
				}
			}

			preparedStatement2.executeBatch();

			preparedStatement4.executeBatch();
		}
	}

	protected void upgradeDLDDMContentReferences() throws Exception {
		upgradeDDMContentReferences(
			StringBundler.concat(
				"select DDMContent.contentId, DDMContent.data_,",
				"DDMStructure.structureId from DLFileEntryMetadata inner join ",
				"DDMContent on DLFileEntryMetadata.DDMStorageId = ",
				"DDMContent.contentId inner join DDMStructure on ",
				"DLFileEntryMetadata.DDMStructureId = DDMStructure.",
				"structureId inner join DLFileVersion on ",
				"DLFileEntryMetadata.fileVersionId = DLFileVersion.",
				"fileVersionId and DLFileEntryMetadata.fileEntryId = ",
				"DLFileVersion.fileEntryId"));
	}

	protected class RadioDDMFormFieldValueTransformer
		implements DDMFormFieldValueTransformer {

		@Override
		public String getFieldType() {
			return "radio";
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			if (value != null) {
				for (Locale locale : value.getAvailableLocales()) {
					String valueString = value.getString(locale);

					if (Validator.isNull(valueString)) {
						continue;
					}

					value.addString(
						locale, convertJSONArrayToString(valueString));
				}
			}
		}

	}

	protected class SelectDDMFormFieldValueTransformer
		implements DDMFormFieldValueTransformer {

		@Override
		public String getFieldType() {
			return "select";
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			if (value != null) {
				for (Locale locale : value.getAvailableLocales()) {
					String valueString = value.getString(locale);

					JSONArray jsonArray = convertToJSONArray(valueString);

					value.addString(locale, jsonArray.toString());
				}
			}
		}

		protected JSONArray convertToJSONArray(String value) {
			if (Validator.isNull(value)) {
				return _jsonFactory.createJSONArray();
			}

			try {
				return _jsonFactory.createJSONArray(value);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}

				JSONArray jsonArray = _jsonFactory.createJSONArray();

				jsonArray.put(value);

				return jsonArray;
			}
		}

	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicDataMappingUpgradeProcess.class);

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final Map<Long, DDMForm> _ddmForms = new HashMap<>();
	private final DDMFormSerializer _ddmFormSerializer;
	private final DDMFormValuesDeserializer _ddmFormValuesDeserializer;
	private final DDMFormValuesSerializer _ddmFormValuesSerializer;
	private final Map<Long, DDMForm> _fullHierarchyDDMForms = new HashMap<>();
	private final JSONFactory _jsonFactory;

}
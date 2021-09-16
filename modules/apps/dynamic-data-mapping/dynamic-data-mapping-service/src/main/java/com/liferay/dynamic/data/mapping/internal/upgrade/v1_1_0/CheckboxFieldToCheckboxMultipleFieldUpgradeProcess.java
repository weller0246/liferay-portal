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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueTransformer;
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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class CheckboxFieldToCheckboxMultipleFieldUpgradeProcess
	extends UpgradeProcess {

	public CheckboxFieldToCheckboxMultipleFieldUpgradeProcess(
		DDMFormDeserializer ddmFormDeserializer,
		DDMFormValuesDeserializer ddmFormValuesDeserializer,
		DDMFormValuesSerializer ddmFormValuesSerializer,
		JSONFactory jsonFactory) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormValuesDeserializer = ddmFormValuesDeserializer;
		_ddmFormValuesSerializer = ddmFormValuesSerializer;
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStructure.definition, DDMStructure.version, ",
					"DDMStructure.structureId, DDLRecordSet.recordSetId from ",
					"DDLRecordSet inner join DDMStructure on ",
					"DDLRecordSet.DDMStructureId = DDMStructure.structureId ",
					"where DDLRecordSet.scope = ? and DDMStructure.definition ",
					"like ?"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureId = ? and version = ?")) {

			preparedStatement1.setInt(1, _SCOPE_FORMS);
			preparedStatement1.setString(2, "%checkbox%");

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String definition = resultSet.getString(1);
					String version = resultSet.getString(2);
					long structureId = resultSet.getLong(3);
					long recordSetId = resultSet.getLong(4);

					String newDefinition = upgradeRecordSetStructureDefinition(
						definition);

					preparedStatement2.setString(1, newDefinition);

					preparedStatement2.setLong(2, structureId);

					preparedStatement2.addBatch();

					preparedStatement3.setString(1, newDefinition);
					preparedStatement3.setLong(2, structureId);
					preparedStatement3.setString(3, version);

					preparedStatement3.addBatch();

					updateRecords(
						DDMFormDeserializeUtil.deserialize(
							_ddmFormDeserializer, definition),
						recordSetId);
				}

				preparedStatement2.executeBatch();

				preparedStatement3.executeBatch();
			}
		}
	}

	protected JSONArray getOptionsJSONArray(
		JSONObject checkboxFieldJSONObject) {

		return JSONUtil.putAll(
			JSONUtil.put(
				"label", checkboxFieldJSONObject.getJSONObject("label")
			).put(
				"value", checkboxFieldJSONObject.getString("name")
			));
	}

	protected JSONObject getPredefinedValueJSONObject(
		JSONObject checkboxFieldJSONObject) {

		JSONObject oldPredefinedValueJSONObject =
			checkboxFieldJSONObject.getJSONObject("predefinedValue");

		JSONObject newPredefinedValueJSONObject =
			_jsonFactory.createJSONObject();

		Iterator<String> iterator = oldPredefinedValueJSONObject.keys();

		while (iterator.hasNext()) {
			String languageKey = iterator.next();

			String predefinedValue = oldPredefinedValueJSONObject.getString(
				languageKey);

			if (Objects.equals(predefinedValue, "true")) {
				predefinedValue = checkboxFieldJSONObject.getString("name");
			}
			else {
				predefinedValue = StringPool.BLANK;
			}

			newPredefinedValueJSONObject.put(languageKey, predefinedValue);
		}

		return newPredefinedValueJSONObject;
	}

	protected void transformCheckboxDDMFormField(
		JSONObject checkboxFieldJSONObject) {

		checkboxFieldJSONObject.put(
			"dataType", "string"
		).put(
			"options", getOptionsJSONArray(checkboxFieldJSONObject)
		).put(
			"predefinedValue",
			getPredefinedValueJSONObject(checkboxFieldJSONObject)
		).put(
			"type", "checkbox_multiple"
		);
	}

	protected void transformCheckboxDDMFormFieldValues(
			DDMFormValues ddmFormValues)
		throws Exception {

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		ddmFormValuesTransformer.addTransformer(
			new CheckboxDDMFormFieldValueTransformer(_jsonFactory));

		ddmFormValuesTransformer.transform();
	}

	protected void updateRecords(DDMForm ddmForm, long recordSetId)
		throws Exception {

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDLRecordVersion.DDMStorageId, DDMContent.data_ ",
					"from DDLRecordVersion inner join DDLRecordSet on ",
					"DDLRecordVersion.recordSetId = DDLRecordSet.recordSetId ",
					"inner join DDMContent on DDLRecordVersion.DDMStorageId = ",
					"DDMContent.contentId where DDLRecordSet.recordSetId = ?"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMContent set data_ = ? where contentId = ? ")) {

			preparedStatement1.setLong(1, recordSetId);

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String data_ = resultSet.getString("data_");

					DDMFormValues ddmFormValues =
						DDMFormValuesDeserializeUtil.deserialize(
							data_, ddmForm, _ddmFormValuesDeserializer);

					transformCheckboxDDMFormFieldValues(ddmFormValues);

					preparedStatement2.setString(
						1,
						DDMFormValuesSerializeUtil.serialize(
							ddmFormValues, _ddmFormValuesSerializer));

					long contentId = resultSet.getLong("DDMStorageId");

					preparedStatement2.setLong(2, contentId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	protected String upgradeRecordSetStructureDefinition(String definition)
		throws JSONException {

		JSONObject definitionJSONObject = _jsonFactory.createJSONObject(
			definition);

		JSONArray fieldsJSONArray = definitionJSONObject.getJSONArray("fields");

		upgradeRecordSetStructureFields(fieldsJSONArray);

		return definitionJSONObject.toString();
	}

	protected void upgradeRecordSetStructureFields(JSONArray fieldsJSONArray) {
		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			String type = fieldJSONObject.getString("type");

			if (type.equals("checkbox")) {
				transformCheckboxDDMFormField(fieldJSONObject);
			}

			JSONArray nestedFieldsJSONArray = fieldJSONObject.getJSONArray(
				"nestedFields");

			if (nestedFieldsJSONArray != null) {
				upgradeRecordSetStructureFields(nestedFieldsJSONArray);
			}
		}
	}

	private static final int _SCOPE_FORMS = 2;

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormValuesDeserializer _ddmFormValuesDeserializer;
	private final DDMFormValuesSerializer _ddmFormValuesSerializer;
	private final JSONFactory _jsonFactory;

	private static class CheckboxDDMFormFieldValueTransformer
		implements DDMFormFieldValueTransformer {

		public CheckboxDDMFormFieldValueTransformer(JSONFactory jsonFactory) {
			_jsonFactory = jsonFactory;
		}

		@Override
		public String getFieldType() {
			return "checkbox";
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			for (Locale locale : value.getAvailableLocales()) {
				String valueString = value.getString(locale);

				JSONArray valueJSONArray = _jsonFactory.createJSONArray();

				if (Objects.equals(valueString, "true")) {
					DDMFormField ddmFormField =
						ddmFormFieldValue.getDDMFormField();

					valueJSONArray.put(ddmFormField.getName());
				}

				value.addString(locale, valueJSONArray.toString());
			}
		}

		private JSONFactory _jsonFactory;

	}

}
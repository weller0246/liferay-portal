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

package com.liferay.object.internal.petra.sql.dsl;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectFilter;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.petra.sql.dsl.expression.FilterPredicateFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.util.ObjectFilterUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true, service = DynamicObjectDefinitionTableFactory.class
)
public class DynamicObjectDefinitionTableFactory {

	public DynamicObjectDefinitionTable create(
			ObjectDefinition objectDefinition, List<ObjectField> objectFields,
			String tableName)
		throws PortalException {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			new DynamicObjectDefinitionTable(
				objectFields, objectDefinition.getPKObjectFieldDBColumnName(),
				tableName);

		List<Expression<?>> selectExpressions = new ArrayList<>();

		selectExpressions.add(
			dynamicObjectDefinitionTable.createColumn(
				dynamicObjectDefinitionTable.getPrimaryKeyColumnName(),
				Long.class, Types.BIGINT, Column.FLAG_DEFAULT));

		for (ObjectField objectField : objectFields) {
			_addObjectField(
				dynamicObjectDefinitionTable, objectDefinition, objectField,
				selectExpressions);
		}

		dynamicObjectDefinitionTable.setSelectExpressions(
			selectExpressions.toArray(new Expression<?>[0]));

		return dynamicObjectDefinitionTable;
	}

	private void _addObjectField(
			DynamicObjectDefinitionTable dynamicObjectDefinitionTable,
			ObjectDefinition objectDefinition, ObjectField objectField,
			List<Expression<?>> selectExpressions)
		throws PortalException {

		if (!Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION)) {

			selectExpressions.add(
				dynamicObjectDefinitionTable.createColumn(
					objectField.getDBColumnName(),
					_getJavaClass(objectField.getDBType()),
					_getSQLType(objectField.getDBType()), Column.FLAG_DEFAULT));

			return;
		}

		Map<String, Object> objectFieldSettingsValuesMap = new HashMap<>();

		List<ObjectFieldSetting> objectFieldSettings =
			_objectFieldSettingLocalService.getObjectFieldObjectFieldSettings(
				objectField.getObjectFieldId());

		for (ObjectFieldSetting objectFieldSetting : objectFieldSettings) {
			if (!StringUtil.equals(objectFieldSetting.getName(), "filters")) {
				objectFieldSettingsValuesMap.put(
					objectFieldSetting.getName(),
					objectFieldSetting.getValue());
			}
			else {
				objectFieldSettingsValuesMap.put(
					objectFieldSetting.getName(),
					objectFieldSetting.getObjectFilters());
			}
		}

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectDefinition.getObjectDefinitionId(),
				GetterUtil.getString(
					objectFieldSettingsValuesMap.get(
						"objectRelationshipName")));

		ObjectDefinition relatedObjectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		DynamicObjectDefinitionTable relatedObjectDefinitionTable = create(
			relatedObjectDefinition,
			_objectFieldLocalService.getObjectFields(
				relatedObjectDefinition.getObjectDefinitionId()),
			relatedObjectDefinition.getDBTableName());

		Expression<? extends Comparable> column = null;

		String function = GetterUtil.getString(
			objectFieldSettingsValuesMap.get("function"));

		if (!Objects.equals(function, "COUNT")) {
			column =
				(Expression<? extends Comparable>)
					_objectFieldLocalService.getColumn(
						relatedObjectDefinition.getObjectDefinitionId(),
						GetterUtil.getString(
							objectFieldSettingsValuesMap.get(
								"objectFieldName")));
		}
		else {
			column = relatedObjectDefinitionTable.getPrimaryKeyColumn();
		}

		Expression<?> expression = null;

		if (function.equals("SUM")) {
			expression = DSLFunctionFactoryUtil.sum(
				(Expression<? extends Number>)column);
		}
		else if (function.equals("COUNT")) {
			expression = DSLFunctionFactoryUtil.count(column);
		}
		else if (function.equals("AVERAGE")) {
			expression = DSLFunctionFactoryUtil.avg(
				(Expression<? extends Number>)column);
		}
		else if (function.equals("MAX")) {
			expression = DSLFunctionFactoryUtil.max(column);
		}
		else if (function.equals("MIN")) {
			expression = DSLFunctionFactoryUtil.min(column);
		}

		DynamicObjectDefinitionTable relatedObjectDefinitionExtensionTable =
			create(
				relatedObjectDefinition,
				_objectFieldLocalService.getObjectFields(
					relatedObjectDefinition.getObjectDefinitionId()),
				relatedObjectDefinition.getExtensionDBTableName());

		JoinStep joinStep = DSLQueryFactoryUtil.select(
			expression
		).from(
			relatedObjectDefinitionTable
		).innerJoinON(
			relatedObjectDefinitionExtensionTable,
			relatedObjectDefinitionExtensionTable.getPrimaryKeyColumn(
			).eq(
				relatedObjectDefinitionTable.getPrimaryKeyColumn()
			)
		);

		if (!relatedObjectDefinition.isSystem()) {
			joinStep = joinStep.innerJoinON(
				ObjectEntryTable.INSTANCE,
				ObjectEntryTable.INSTANCE.objectEntryId.eq(
					relatedObjectDefinitionTable.getPrimaryKeyColumn()));
		}

		Predicate predicate = null;

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

			ObjectField relatedField = _objectFieldLocalService.getObjectField(
				objectRelationship.getObjectFieldId2());

			Column<DynamicObjectDefinitionTable, Long>
				relatedObjectDefinitionColumn =
					(Column<DynamicObjectDefinitionTable, Long>)
						_objectFieldLocalService.getColumn(
							relatedObjectDefinition.getObjectDefinitionId(),
							relatedField.getName());

			predicate = relatedObjectDefinitionColumn.eq(
				dynamicObjectDefinitionTable.getPrimaryKeyColumn());
		}
		else if (Objects.equals(
					objectRelationship.getType(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			DynamicObjectRelationshipMappingTable
				dynamicObjectRelationshipMappingTable =
					new DynamicObjectRelationshipMappingTable(
						objectDefinition.getPKObjectFieldDBColumnName(),
						relatedObjectDefinition.getPKObjectFieldDBColumnName(),
						objectRelationship.getDBTableName());

			Column<DynamicObjectRelationshipMappingTable, Long>
				primaryKeyColumn1 =
					dynamicObjectRelationshipMappingTable.
						getPrimaryKeyColumn1();

			Column<DynamicObjectRelationshipMappingTable, Long>
				primaryKeyColumn2 =
					dynamicObjectRelationshipMappingTable.
						getPrimaryKeyColumn2();

			joinStep = joinStep.innerJoinON(
				dynamicObjectRelationshipMappingTable,
				primaryKeyColumn2.eq(
					relatedObjectDefinitionTable.getPrimaryKeyColumn()));

			predicate = primaryKeyColumn1.eq(
				dynamicObjectDefinitionTable.getPrimaryKeyColumn());
		}

		List<String> oDataFilterStrings =
			ObjectFilterUtil.getODataFilterStrings(
				(List<ObjectFilter>)objectFieldSettingsValuesMap.get(
					"filters"));

		for (String oDataFilter : oDataFilterStrings) {
			predicate = predicate.and(
				_filterPredicateFactory.create(
					oDataFilter,
					relatedObjectDefinition.getObjectDefinitionId()));
		}

		selectExpressions.add(
			DSLQueryFactoryUtil.scalarSubDSLQuery(
				joinStep.where(predicate),
				_getJavaClass(objectField.getDBType()), objectField.getName(),
				_getSQLType(objectField.getDBType())));
	}

	private Class<?> _getJavaClass(String type) {
		Class<?> javaClass = _javaClasses.get(type);

		if (javaClass == null) {
			throw new IllegalArgumentException("Invalid type " + type);
		}

		return javaClass;
	}

	private Integer _getSQLType(String type) {
		Integer sqlType = _sqlTypes.get(type);

		if (sqlType == null) {
			throw new IllegalArgumentException("Invalid type " + type);
		}

		return sqlType;
	}

	private static final Map<String, Class<?>> _javaClasses =
		HashMapBuilder.<String, Class<?>>put(
			"BigDecimal", BigDecimal.class
		).put(
			"Blob", Blob.class
		).put(
			"Boolean", Boolean.class
		).put(
			"Clob", Clob.class
		).put(
			"Date", Date.class
		).put(
			"Double", Double.class
		).put(
			"Integer", Integer.class
		).put(
			"Long", Long.class
		).put(
			"String", String.class
		).build();
	private static final Map<String, Integer> _sqlTypes = HashMapBuilder.put(
		"BigDecimal", Types.DECIMAL
	).put(
		"Blob", Types.BLOB
	).put(
		"Boolean", Types.BOOLEAN
	).put(
		"Clob", Types.CLOB
	).put(
		"Date", Types.DATE
	).put(
		"Double", Types.DOUBLE
	).put(
		"Integer", Types.INTEGER
	).put(
		"Long", Types.BIGINT
	).put(
		"String", Types.VARCHAR
	).build();

	@Reference
	private FilterPredicateFactory _filterPredicateFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}
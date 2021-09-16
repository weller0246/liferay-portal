/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service.persistence.impl;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettingTable;
import com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingImpl;
import com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from CPDefinitionDiagramSetting.
 *
 * @author Andrea Sbarra
 * @generated
 */
@Component(
	immediate = true,
	service = {
		CPDefinitionDiagramSettingModelArgumentsResolver.class,
		ArgumentsResolver.class
	}
)
public class CPDefinitionDiagramSettingModelArgumentsResolver
	implements ArgumentsResolver {

	@Override
	public Object[] getArguments(
		FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
		boolean original) {

		String[] columnNames = finderPath.getColumnNames();

		if ((columnNames == null) || (columnNames.length == 0)) {
			if (baseModel.isNew()) {
				return new Object[0];
			}

			return null;
		}

		CPDefinitionDiagramSettingModelImpl
			cpDefinitionDiagramSettingModelImpl =
				(CPDefinitionDiagramSettingModelImpl)baseModel;

		long columnBitmask =
			cpDefinitionDiagramSettingModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				cpDefinitionDiagramSettingModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					cpDefinitionDiagramSettingModelImpl.getColumnBitmask(
						columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				cpDefinitionDiagramSettingModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return CPDefinitionDiagramSettingImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return CPDefinitionDiagramSettingTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		CPDefinitionDiagramSettingModelImpl cpDefinitionDiagramSettingModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					cpDefinitionDiagramSettingModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] =
					cpDefinitionDiagramSettingModelImpl.getColumnValue(
						columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}
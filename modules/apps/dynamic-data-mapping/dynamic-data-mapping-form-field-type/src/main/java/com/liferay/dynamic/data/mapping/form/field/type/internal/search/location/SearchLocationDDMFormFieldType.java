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

package com.liferay.dynamic.data.mapping.form.field.type.internal.search.location;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = {
		"ddm.form.field.type.description=search-location-field-type-description",
		"ddm.form.field.type.display.order:Integer=11",
		"ddm.form.field.type.group=basic",
		"ddm.form.field.type.icon=geolocation",
		"ddm.form.field.type.label=search-location-field-type-label",
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.SEARCH_LOCATION,
		"ddm.form.field.type.scope=forms"
	},
	service = DDMFormFieldType.class
)
public class SearchLocationDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return SearchLocationDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		return "dynamic-data-mapping-form-field-type/SearchLocation" +
			"/SearchLocation.es";
	}

	@Override
	public String getName() {
		return DDMFormFieldTypeConstants.SEARCH_LOCATION;
	}

}
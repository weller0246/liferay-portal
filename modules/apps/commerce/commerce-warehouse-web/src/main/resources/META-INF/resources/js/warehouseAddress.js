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

export default function ({
	commerceRegionCode,
	companyId,
	countryTwoLettersISOCode,
	namespace,
}) {
	new Liferay.DynamicSelect([
		{
			select: `${namespace}countryTwoLettersISOCode`,
			selectData: (callback) => {
				Liferay.Service(
					'/country/get-company-countries',
					{
						active: true,
						companyId,
					},
					callback
				);
			},
			selectDesc: 'nameCurrentValue',
			selectId: 'a2',
			selectSort: true,
			selectVal: countryTwoLettersISOCode,
		},
		{
			select: `${namespace}commerceRegionCode`,
			selectData: (callback, selectKey) => {
				Liferay.Service(
					'/region/get-regions',
					{
						a2: selectKey,
						active: true,
						companyId,
					},
					callback
				);
			},
			selectDesc: 'name',
			selectId: 'regionCode',
			selectVal: commerceRegionCode,
		},
	]);
}

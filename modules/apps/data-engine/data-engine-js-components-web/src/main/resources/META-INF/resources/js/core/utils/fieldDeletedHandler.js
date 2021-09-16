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

import {removeEmptyRows as removeEmptyRowsUtil} from '../../utils/FormSupport.es';
import {removeField} from '../../utils/fieldSupport';
import {formatRules} from '../../utils/rulesSupport';

export const handleFieldDeleted = (
	props,
	state,
	{activePage, editRule = true, fieldName, removeEmptyRows = true}
) => {
	const {pages} = state;

	if (activePage === undefined) {
		activePage = state.activePage;
	}

	const newPages = pages.map((page, pageIndex) => {
		if (activePage === pageIndex) {
			const pagesWithFieldRemoved = removeField(
				props,
				pages,
				fieldName,
				removeEmptyRows
			);

			return {
				...page,
				rows: removeEmptyRows
					? removeEmptyRowsUtil(pagesWithFieldRemoved, pageIndex)
					: pagesWithFieldRemoved[pageIndex].rows,
			};
		}

		return page;
	});

	return {
		focusedField: {},
		pages: newPages,
		rules: editRule ? formatRules(newPages, state.rules) : state.rules,
	};
};

export default handleFieldDeleted;

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

import {generateFieldName} from '../../../../src/main/resources/META-INF/resources/js/core/utils/fields';
import mockPages from '../../__mock__/mockPages.es';

describe('core/utils/fields', () => {
	describe('generateFieldName(pages, desiredName, currentName)', () => {
		it('generates a name based on the desired name', () => {
			const name = generateFieldName(
				mockPages,
				'New  Name!',
				undefined,
				undefined,
				true
			);
			expect(name).toEqual('NewName');
		});

		it('generates an incremental name when desired name is already being used', () => {
			const name = generateFieldName(
				mockPages,
				'radio',
				undefined,
				undefined,
				true
			);
			expect(name).toEqual('radio1');
		});

		it('generates an incremental name when changing desired name to an already used one', () => {
			const name = generateFieldName(
				mockPages,
				'radio!!',
				undefined,
				undefined,
				true
			);
			expect(name).toEqual('radio1');
		});

		it('fallbacks to currentName when generated name is invalid', () => {
			const name = generateFieldName(
				mockPages,
				'radio!',
				'radio',
				undefined,
				true
			);
			expect(name).toEqual('radio');
		});
	});
});

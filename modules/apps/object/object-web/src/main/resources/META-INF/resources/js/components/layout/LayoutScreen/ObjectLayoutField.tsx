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

import React, {useContext} from 'react';

import {normalizeLanguageId} from '../../../utils/string';
import Panel from '../../Panel/Panel';
import LayoutContext, {TYPES} from '../context';
import {TObjectField} from '../types';
import DropdownWithDeleteButton from './DropdownWithDeleteButton';
import RequiredLabel from './RequiredLabel';

const defaultLanguageId = normalizeLanguageId(
	Liferay.ThemeDisplay.getDefaultLanguageId()
);

interface IObjectLayoutFieldProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	columnIndex: number;
	objectFieldId: number;
	rowIndex: number;
	tabIndex: number;
}

const ObjectLayoutField: React.FC<IObjectLayoutFieldProps> = ({
	boxIndex,
	columnIndex,
	objectFieldId,
	rowIndex,
	tabIndex,
}) => {
	const [{objectFields}, dispatch] = useContext(LayoutContext);

	const objectField = objectFields.find(
		({id}) => id === objectFieldId
	) as TObjectField;

	return (
		<>
			<Panel key={`field_${objectFieldId}`}>
				<Panel.SimpleBody
					contentRight={
						<DropdownWithDeleteButton
							onClick={() => {
								dispatch({
									payload: {
										boxIndex,
										columnIndex,
										rowIndex,
										tabIndex,
									},
									type: TYPES.DELETE_OBJECT_LAYOUT_FIELD,
								});
							}}
						/>
					}
					title={objectField?.label[defaultLanguageId]}
				>
					<small className="text-secondary">
						{objectField?.type} |{' '}
					</small>

					<RequiredLabel required={objectField?.required} />
				</Panel.SimpleBody>
			</Panel>
		</>
	);
};

export default ObjectLayoutField;

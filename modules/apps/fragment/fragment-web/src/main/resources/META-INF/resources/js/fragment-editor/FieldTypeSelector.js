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

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import classNames from 'classnames';
import React from 'react';

export function FieldTypeSelector({
	availableFieldTypes,
	description,
	fieldTypes,
	onAddFieldType,
	onRemoveFieldType,
	readOnly,
	small,
	title,
}) {
	return (
		<ClayForm.Group className={classNames({'form-group-sm': small})}>
			<div className="sheet-section">
				<h2 className="sheet-subtitle">{title}</h2>

				{readOnly ? (
					fieldTypes.length ? (
						fieldTypes.map((fieldType) => {
							const label = availableFieldTypes.find(
								({key}) => key === fieldType
							).label;

							return (
								<p className="mb-1" key={fieldType}>
									{label}
								</p>
							);
						})
					) : (
						<ClayAlert displayType="info">
							{Liferay.Language.get(
								'no-field-type-is-defined-for-this-fragment'
							)}
						</ClayAlert>
					)
				) : (
					<>
						<p>{description}</p>

						{availableFieldTypes.map(({key, label}) => (
							<ClayCheckbox
								aria-label={label}
								checked={fieldTypes.includes(key)}
								key={key}
								label={label}
								onChange={(event) =>
									event.target.checked
										? onAddFieldType(key)
										: onRemoveFieldType(key)
								}
							/>
						))}
					</>
				)}
			</div>
		</ClayForm.Group>
	);
}

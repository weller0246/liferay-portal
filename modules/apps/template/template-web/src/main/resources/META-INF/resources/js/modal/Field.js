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

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React from 'react';

export default function Field({children, errors, id, label, name}) {
	const errorMessage = errors[name];

	return (
		<ClayForm.Group className={errorMessage ? 'has-error' : null}>
			<label htmlFor={id}>
				{label}
				<span className="reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>
			</label>

			{children}

			{errorMessage && (
				<div className="form-feedback-item">
					<span className="form-feedback-indicator mr-1">
						<ClayIcon symbol="exclamation-full" />
					</span>
					{errorMessage}
				</div>
			)}
		</ClayForm.Group>
	);
}

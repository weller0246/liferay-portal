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

import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React from 'react';

// @ts-ignore

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const Switcher: React.FC<ISwitcherProps> = ({
	checked,
	disabled,
	label,
	name,
	onChange,
	required,
	showLabel,
	showMaximumRepetitionsInfo,
	systemSettingsURL,
}) => {
	return (
		<>
			<label className="ddm-toggle-switch toggle-switch">
				<input
					checked={checked}
					className="toggle-switch-check"
					disabled={disabled}
					name={name}
					onChange={({target: {checked}}) => {
						onChange({target: {value: checked}});
					}}
					type="checkbox"
				/>

				<span aria-hidden="true" className="toggle-switch-bar">
					<span className="toggle-switch-handle"></span>

					{(showLabel || required) && (
						<span className="toggle-switch-text toggle-switch-text-right">
							{showLabel && label}

							{required && (
								<span className="ddm-label-required reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							)}
						</span>
					)}
				</span>
			</label>
			{checked && showMaximumRepetitionsInfo && (
				<div className="ddm-info">
					<span className="ddm-tooltip">
						<ClayIcon symbol="info-circle" />
					</span>
					<div
						className="ddm-info-text"
						dangerouslySetInnerHTML={{
							__html: Liferay.Util.sub(
								Liferay.Language.get(
									'for-security-reasons-upload-field-repeatability-is-limited-the-limit-is-defined-in-x-system-settings-x'
								),
								`<a href=${systemSettingsURL} target="_blank">`,
								'</a>'
							),
						}}
					/>
				</div>
			)}
		</>
	);
};

const Checkbox: React.FC<ICheckboxProps> = ({
	checked,
	disabled,
	label,
	name,
	onChange,
	required,
	showLabel,
}) => {
	return (
		<ClayCheckbox
			checked={checked}
			disabled={disabled}
			label={showLabel ? label : ''}
			name={name}
			onChange={({target: {checked}}) => {
				onChange({target: {value: checked}});
			}}
		>
			{showLabel && required && (
				<span className="ddm-label-required reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>
			)}
		</ClayCheckbox>
	);
};

const Main: React.FC<IProps> = ({
	label,
	name,
	onChange,
	predefinedValue,
	readOnly,
	required,
	showAsSwitcher = true,
	showLabel = true,
	showMaximumRepetitionsInfo = false,
	systemSettingsURL,
	value,
	visible,
	...otherProps
}) => {
	const Toggle = showAsSwitcher ? Switcher : Checkbox;

	const checked = !!(
		value ??
		(Array.isArray(predefinedValue)
			? predefinedValue[0] === 'true'
			: predefinedValue)
	);

	return (
		<FieldBase
			name={name}
			showLabel={false}
			visible={visible}
			{...otherProps}
		>
			<Toggle
				checked={checked}
				disabled={readOnly}
				label={label}
				name={name}
				onChange={onChange}
				required={required}
				showLabel={showLabel}
				showMaximumRepetitionsInfo={showMaximumRepetitionsInfo}
				systemSettingsURL={systemSettingsURL}
			/>
			<ClayInput name={name} type="hidden" value={`${checked}`} />
		</FieldBase>
	);
};

interface IProps extends ICheckboxProps {
	predefinedValue?: boolean | String[];
	readOnly?: boolean;
	showAsSwitcher?: boolean;
	showMaximumRepetitionsInfo?: boolean;
	systemSettingsURL: string;
	value?: boolean;
	visible?: boolean;
}
interface ISwitcherProps extends ICheckboxProps {
	showMaximumRepetitionsInfo: boolean;
	systemSettingsURL: string;
}

interface ICheckboxProps {
	checked: boolean;
	disabled?: boolean;
	label?: string;
	name: string;
	onChange: (event: {target: {value: boolean}}) => void;
	required?: boolean;
	showLabel?: boolean;
}

Main.displayName = 'Checkbox';

export {Main};
export default Main;

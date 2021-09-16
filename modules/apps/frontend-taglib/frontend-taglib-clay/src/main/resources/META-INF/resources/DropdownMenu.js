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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React from 'react';

import normalizeDropdownItems from './normalize_dropdown_items';

export default function DropdownMenu({
	actionsDropdown = false,
	additionalProps: _additionalProps,
	componentId: _componentId,
	cssClass,
	icon,
	items,
	label,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	...otherProps
}) {
	return (
		<>
			<ClayDropDownWithItems
				className={classNames({
					'dropdown-action': actionsDropdown,
				})}
				items={normalizeDropdownItems(items)}
				trigger={
					<ClayButton
						className={classNames(cssClass, {
							'component-action': actionsDropdown,
						})}
						{...otherProps}
					>
						{icon && (
							<span
								className={classNames('inline-item', {
									'inline-item-before': label,
								})}
							>
								<ClayIcon symbol={icon} />
							</span>
						)}

						{label}
					</ClayButton>
				}
			/>

			<div className="quick-action-menu">
				{items.map(({data, href, icon, quickAction, ...rest}) =>
					data?.action && quickAction ? (
						<ClayButtonWithIcon
							className="component-action quick-action-item"
							displayType="unstyled"
							key={data.action}
							small={true}
							symbol={icon}
							{...rest}
						/>
					) : (
						href &&
						icon &&
						quickAction && (
							<ClayLink
								className="component-action quick-action-item"
								href={href}
								key={href}
								{...rest}
							>
								<ClayIcon symbol={icon} />
							</ClayLink>
						)
					)
				)}
			</div>
		</>
	);
}

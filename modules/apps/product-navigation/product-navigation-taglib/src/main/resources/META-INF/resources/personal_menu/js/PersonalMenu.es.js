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

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

function PersonalMenu({
	color,
	isImpersonated,
	itemsURL,
	label,
	size,
	userPortraitURL,
}) {
	const [items, setItems] = useState([]);
	const preloadPromise = useRef();

	function preloadItems() {
		if (!preloadPromise.current) {
			preloadPromise.current = fetch(itemsURL)
				.then((response) => response.json())
				.then((items) => setItems(items));
		}
	}

	return (
		<ClayDropDownWithItems
			items={items}
			menuElementAttrs={{className: 'dropdown-menu-personal-menu'}}
			trigger={
				label ? (
					<div
						dangerouslySetInnerHTML={{__html: label}}
						onFocus={preloadItems}
						onMouseOver={preloadItems}
					/>
				) : (
					<ClayButton
						aria-label={Liferay.Language.get('personal-menu')}
						className="rounded-circle"
						displayType="unstyled"
						onFocus={preloadItems}
						onMouseOver={preloadItems}
					>
						<span
							className={`sticker sticker-user-icon sticker-${size}`}
						>
							<ClaySticker
								className={`user-icon-color-${color}`}
								shape="circle"
								size={size}
							>
								{userPortraitURL ? (
									<img
										className="sticker-img"
										src={userPortraitURL}
									/>
								) : (
									<ClayIcon symbol="user" />
								)}
							</ClaySticker>

							{isImpersonated && (
								<ClaySticker
									className="sticker-user-icon"
									id="impersonate-user-sticker"
									outside
									position="bottom-right"
									shape="circle"
									size={size ? 'sm' : ''}
								>
									<span id="impersonate-user-icon">
										<ClayIcon symbol="user" />
									</span>
								</ClaySticker>
							)}
						</span>
					</ClayButton>
				)
			}
		/>
	);
}

PersonalMenu.propTypes = {
	itemsURL: PropTypes.string,
};

export default PersonalMenu;

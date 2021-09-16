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

import classNames from 'classnames';
import React, {useContext} from 'react';

import {CollectionItemContext} from '../../contexts/CollectionItemContext';
import TopperEmpty from '../TopperEmpty';

const CollectionItemWithControls = React.forwardRef(({children, item}, ref) => {
	const {collectionItem} = useContext(CollectionItemContext);
	const title =
		collectionItem.title ||
		collectionItem.name ||
		collectionItem.defaultTitle;

	return (
		<div
			className={classNames('page-editor__collection__block', {
				empty: !title,
			})}
		>
			<TopperEmpty item={item}>
				{React.Children.count(children) === 0 ? (
					<div
						className={classNames('page-editor__collection-item', {
							empty: !children.length,
						})}
						ref={ref}
					>
						<div className="page-editor__collection-item__border">
							<p className="page-editor__collection-item__title">
								{title ||
									Liferay.Language.get(
										'sample-collection-item'
									)}
							</p>
						</div>
					</div>
				) : (
					<div ref={ref}>{children}</div>
				)}
			</TopperEmpty>
		</div>
	);
});

export default CollectionItemWithControls;

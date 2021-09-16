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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {useTimeout} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useContext, useEffect, useState} from 'react';

const SidebarContext = React.createContext();

const noop = () => {};

const SidebarBody = ({children, className}) => {
	return (
		<div className={classNames('sidebar-body', className)}>{children}</div>
	);
};

const SidebarHeader = ({title}) => {
	const {onClose} = useContext(SidebarContext);

	return (
		<section className="sidebar-header">
			<ClayLayout.ContentRow className="sidebar-section">
				<ClayLayout.ContentCol expand>
					<h2 className="font-weight-bold mb-0 pr-2 small">
						{title}
					</h2>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol>
					<ClayButtonWithIcon
						aria-label="Close"
						className="mt-n2 text-secondary"
						displayType="unstyled"
						onClick={onClose}
						symbol="times"
					/>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</section>
	);
};

const Sidebar = ({children, onClose = noop, open = true}) => {
	const [isOpen, setIsOpen] = useState(false);

	const delay = useTimeout();

	// Wait until the component is rendered to show it so animation happens

	useEffect(() => {
		if (open !== false) {
			delay(() => setIsOpen(true), 100);
		}
		else {
			setIsOpen(false);
		}
	}, [delay, open]);

	useEffect(() => {
		if (isOpen) {
			document.body.classList.add('sidebar-open');
		}
		else {
			document.body.classList.remove('sidebar-open');
		}
	}, [isOpen]);

	return (
		<div className="content-dashboard sidebar sidebar-light sidebar-sm">
			<SidebarContext.Provider value={{onClose}}>
				{children}
			</SidebarContext.Provider>
		</div>
	);
};

Sidebar.Body = SidebarBody;
Sidebar.Header = SidebarHeader;

export {SidebarContext};
export default Sidebar;

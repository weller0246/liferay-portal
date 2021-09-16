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

const body = document.querySelector('body');

const navbarButton = fragmentElement.querySelector('.navbar-toggler');
const navbarCollapse = fragmentElement.querySelector('.navbar-collapse');
const siteNavbar = fragmentElement.querySelector('.bootcamp-navbar');

navbarButton.addEventListener('click', function () {
	body.classList.toggle('overflow-hidden');
	navbarCollapse.classList.toggle('show');
	siteNavbar.classList.toggle('open');

	navbarButton.setAttribute(
		'aria-expanded',
		navbarCollapse.classList.contains('show').toString()
	);
});

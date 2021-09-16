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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React, {useState} from 'react';

import UnsafeHTML from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/UnsafeHTML';

describe('UnsafeHTML', () => {
	afterEach(cleanup);

	it('renders the given HTML markup', () => {
		const {getByRole} = render(
			<UnsafeHTML markup="<h1>Hello <strong>Gürjen</strong></h1>" />
		);

		expect(getByRole('heading')).toBeInTheDocument();
	});

	it('allows adding any id', () => {
		const {container} = render(
			<UnsafeHTML id="food" markup="Pi<strong>zz</strong>a" />
		);

		expect(container.querySelector('#food')).toBeInTheDocument();
	});

	it('allows adding any className', () => {
		const {container} = render(
			<UnsafeHTML className="food" markup="Pi<strong>zz</strong>a" />
		);

		expect(container.querySelector('.food')).toBeInTheDocument();
	});

	it('allows using a custom HTML tag as container', () => {
		const {getByRole} = render(
			<UnsafeHTML TagName="h1" markup="The Title" />
		);

		expect(getByRole('heading')).toBeInTheDocument();
	});

	it('calls onRender prop whenever the content is updated', () => {
		const onRender = jest.fn();

		render(<UnsafeHTML markup="Some content" onRender={onRender} />);
		expect(onRender).toHaveBeenCalledWith(expect.any(HTMLElement));
	});

	it('uses given globalContext to execute scripts', () => {
		const globalContext = {
			document: {
				createElement: jest.fn((...args) =>
					document.createElement(...args)
				),

				createTextNode: jest.fn((...args) =>
					document.createTextNode(...args)
				),
			},
		};

		render(
			<UnsafeHTML
				globalContext={globalContext}
				markup="<script>const name = 'someScript';</script>"
			/>
		);

		expect(globalContext.document.createElement).toHaveBeenCalled();
		expect(globalContext.document.createTextNode).toHaveBeenCalled();
	});

	it('mounts given portals inside HTML', () => {
		const getPortals = jest.fn((parent) => [
			{
				Component: () => {
					const [counter] = useState(123);

					return (
						<h1 data-testid="portal-content">
							Some portal {counter}
						</h1>
					);
				},
				element: parent.querySelector('#placePortalHere'),
			},
		]);

		const {getByTestId} = render(
			<UnsafeHTML
				getPortals={getPortals}
				markup={`
          <main>
            <h1>Random title</h1>
            <article id="placePortalHere"></article>
          </main>
        `}
			/>
		);

		const portalContent = getByTestId('portal-content');

		expect(portalContent).toBeInTheDocument();
		expect(portalContent.innerHTML).toBe('Some portal 123');
	});

	it('does nothing if there is no markup', () => {
		const onRender = jest.fn();

		render(<UnsafeHTML markup="" onRender={onRender} />);
		expect(onRender).not.toHaveBeenCalled();
	});
});

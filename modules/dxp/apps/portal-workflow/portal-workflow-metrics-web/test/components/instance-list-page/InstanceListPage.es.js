/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {act, fireEvent, render} from '@testing-library/react';
import React from 'react';

import InstanceListPage from '../../../src/main/resources/META-INF/resources/js/components/instance-list-page/InstanceListPage.es';
import ToasterProvider from '../../../src/main/resources/META-INF/resources/js/shared/components/toaster/ToasterProvider.es';
import {MockRouter} from '../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const items = [
	{
		assetTitle: 'New Post 1',
		assetType: 'Blog',
		assignees: [{id: -1, name: 'Unassigned', reviewer: true}],
		dateCreated: new Date('2019-01-01'),
		id: 1,
		taskNames: [],
	},
	{
		assetTitle: 'New Post 2',
		assetType: 'Blog',
		assignees: [{id: -1, name: 'Unassigned', reviewer: true}],
		creator: {
			name: 'User 1',
		},
		dateCreated: new Date('2019-01-03'),
		id: 2,
		taskNames: ['Update'],
	},
];

const routeParams = {
	page: 1,
	pageSize: 2,
	query: '',
	sort: 'overdueInstanceCount%3Adesc',
};

describe('The instance list card should', () => {
	const clientMock = {
		get: jest
			.fn()
			.mockResolvedValue({data: {items, totalCount: items.length + 1}}),
		request: jest
			.fn()
			.mockResolvedValue({data: {items, totalCount: items.length + 1}}),
	};
	let container, findByText, getByText;

	beforeAll(async () => {
		const renderResult = render(
			<MockRouter client={clientMock}>
				<InstanceListPage routeParams={routeParams} />
			</MockRouter>,
			{wrapper: ToasterProvider}
		);

		container = renderResult.container;
		findByText = renderResult.findByText;
		getByText = renderResult.getByText;

		await act(async () => {
			jest.runAllTimers();
		});
	});

	it('Be rendered with "sla-status", "process-status", "completion-period", "process-step" and "assignee" filters', () => {
		const filters = container.querySelectorAll('.dropdown-toggle');

		expect(filters[0]).toHaveTextContent('sla-status');
		expect(filters[1]).toHaveTextContent('process-status');
		expect(filters[2]).toHaveTextContent('completion-period');
		expect(filters[3]).toHaveTextContent('process-step');
		expect(filters[4]).toHaveTextContent('assignee');
	});

	it('Select all page by clicking on check all button', async () => {
		const checkAllButton = container.querySelectorAll(
			'input.custom-control-input'
		)[0];
		const firstTableElements = container.querySelectorAll(
			'.table-first-element-group'
		);

		const instanceCheckbox1 = firstTableElements[0].querySelector(
			'input.custom-control-input'
		);
		const instanceCheckbox2 = firstTableElements[1].querySelector(
			'input.custom-control-input'
		);

		expect(checkAllButton.checked).toEqual(false);
		expect(instanceCheckbox1.checked).toEqual(false);
		expect(instanceCheckbox2.checked).toEqual(false);

		fireEvent.click(checkAllButton);

		await act(async () => {
			jest.runAllTimers();
		});

		const label = getByText('x-of-x-selected');

		expect(checkAllButton.checked).toEqual(true);
		expect(label).toBeTruthy();
		expect(instanceCheckbox1.checked).toEqual(true);
		expect(instanceCheckbox2.checked).toEqual(true);

		fireEvent.click(checkAllButton);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(checkAllButton.checked).toEqual(false);
		expect(instanceCheckbox1.checked).toEqual(false);
		expect(instanceCheckbox2.checked).toEqual(false);
	});

	it('Select all instances by clicking on select all button', async () => {
		const checkAllButton = container.querySelectorAll(
			'input.custom-control-input'
		)[0];
		const firstTableElements = container.querySelectorAll(
			'.table-first-element-group'
		);

		const instanceCheckbox1 = firstTableElements[0].querySelector(
			'input.custom-control-input'
		);
		const instanceCheckbox2 = firstTableElements[1].querySelector(
			'input.custom-control-input'
		);

		expect(checkAllButton.checked).toEqual(false);
		expect(instanceCheckbox1.checked).toEqual(false);
		expect(instanceCheckbox2.checked).toEqual(false);

		fireEvent.click(instanceCheckbox1);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(checkAllButton.checked).toEqual(false);
		expect(instanceCheckbox1.checked).toEqual(true);
		expect(instanceCheckbox2.checked).toEqual(false);

		const clearButton = getByText('clear');

		fireEvent.click(clearButton);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(checkAllButton.checked).toEqual(false);
		expect(instanceCheckbox1.checked).toEqual(false);
		expect(instanceCheckbox2.checked).toEqual(false);

		fireEvent.click(checkAllButton);

		let label = getByText('x-of-x-selected');

		expect(checkAllButton.checked).toEqual(true);
		expect(label).toBeTruthy();
		expect(instanceCheckbox1.checked).toEqual(true);
		expect(instanceCheckbox2.checked).toEqual(true);

		const selectAllButton = getByText('select-all');

		fireEvent.click(selectAllButton);

		await act(async () => {
			jest.runAllTimers();
		});

		label = getByText('all-selected');

		expect(label).toBeTruthy();
	});

	it('Show last metrics calculated info', () => {
		const metricsCalculated = findByText('Metrics calculated');

		expect(metricsCalculated).toBeTruthy();
	});
});

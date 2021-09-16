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
import {act, fireEvent, render} from '@testing-library/react';
import React from 'react';

import WorkflowInstanceTracker from '../../src/main/resources/META-INF/resources/js/components/WorkflowInstanceTracker';
import EventObserver from '../../src/main/resources/META-INF/resources/js/util/EventObserver';

jest.mock('axios', () => {
	const workflowInstanceData = {
		state: 'Review',
		workflowDefinitionName: 'Single Approver',
	};

	const visitedNodes = {
		items: [
			{
				state: 'review',
			},
			{
				state: 'created',
			},
		],
	};

	const WorkflowDefinitionData = {
		name: 'Single Approver',
		nodes: [
			{
				label: 'Approved',
				name: 'approved',
				type: 'TERMINAL_STATE',
			},
			{
				label: 'Created',
				name: 'created',
				type: 'INITIAL_STATE',
			},
			{
				label: 'Review',
				name: 'review',
				type: 'TASK',
			},
			{
				label: 'Update',
				name: 'update',
				type: 'TASK',
			},
		],
		title: 'Single Approver',
		transitions: [
			{
				label: 'Review',
				name: 'review',
				sourceNodeName: 'created',
				targetNodeName: 'review',
			},
			{
				label: 'Approve',
				name: 'approve',
				sourceNodeName: 'review',
				targetNodeName: 'approved',
			},
			{
				label: 'Reject',
				name: 'reject',
				sourceNodeName: 'review',
				targetNodeName: 'update',
			},
			{
				label: 'Resubmit',
				name: 'resubmit',
				sourceNodeName: 'update',
				targetNodeName: 'review',
			},
		],
	};

	return {
		all: (promises) => Promise.all(promises),
		create: jest.fn(() => ({
			get: jest
				.fn()
				.mockResolvedValueOnce({data: workflowInstanceData})
				.mockResolvedValueOnce({data: visitedNodes})
				.mockResolvedValueOnce({data: WorkflowDefinitionData}),
		})),
		defaults: {
			headers: {
				common: {
					'Accept-Language': null,
				},
			},
			params: {},
		},
		spread: (callback) => (array) => callback?.apply(null, array),
	};
});

window.ResizeObserver =
	window.ResizeObserver ||
	jest.fn().mockImplementation(() => ({
		disconnect: jest.fn(),
		observe: jest.fn(),
		unobserve: jest.fn(),
	}));

Object.defineProperties(window.HTMLElement.prototype, {
	offsetHeight: {
		get() {
			return parseFloat(window.getComputedStyle(this).height) || 1;
		},
	},
	offsetWidth: {
		get() {
			return parseFloat(window.getComputedStyle(this).width) || 1;
		},
	},
});

describe('The WorkflowInstanceTracker component should', () => {
	let container, queryAllByText, queryByText;

	beforeAll(async () => {
		window.SVGElement.prototype.getBBox = () => ({});

		const renderResult = render(<WorkflowInstanceTracker />);

		container = renderResult.container;
		queryAllByText = renderResult.queryAllByText;
		queryByText = renderResult.queryByText;

		await act(async () => {
			jest.runAllTimers();
		});
	});

	afterAll(() => {
		delete window.SVGElement.prototype.getBBox;
	});

	it('Be rendered with nodes and transitions, but with transition labels hidden', () => {
		expect(container.querySelector('.react-flow__nodes')).toBeTruthy();
		expect(container.querySelector('.react-flow__edges')).toBeTruthy();
		expect(queryAllByText('Review')).toHaveLength(1);
		expect(queryByText('Approve')).toBeFalsy();
		expect(queryByText('Reject')).toBeFalsy();
		expect(queryByText('Resubmit')).toBeFalsy();
	});

	it('Display the labels of transitions originated from a node while hovering it', async () => {
		jest.spyOn(
			EventObserver.prototype,
			'notify'
		).mockImplementation(() => () => jest.fn());

		const reviewNode = queryByText('Review');

		expect(reviewNode).toBeTruthy();

		fireEvent.mouseEnter(reviewNode);

		expect(EventObserver.prototype.notify).toHaveBeenCalled();
	});
});

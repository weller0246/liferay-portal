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

import {
	cleanup,
	render,
	wait,
	waitForElement,
	within,
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import configModule from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config';
import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import serviceFetch from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch';
import {
	CREATE_SEGMENTS_EXPERIENCE,
	DELETE_SEGMENTS_EXPERIENCE,
	UPDATE_SEGMENTS_EXPERIENCE,
	UPDATE_SEGMENTS_EXPERIENCES_LIST,
} from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/experience/actions';
import ExperienceToolbarSection from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/experience/components/ExperienceToolbarSection';

import '@testing-library/jest-dom/extend-expect';

const MOCK_DELETE_URL = 'delete-experience-test-url';
const MOCK_DUPLICATE_URL = 'duplicate-experience-test-url';
const MOCK_CREATE_URL = 'create-experience-test-url';
const MOCK_UPDATE_PRIORITY_URL = 'update-experience-priority-test-url';
const MOCK_UPDATE_URL = 'update-experience-test-url';

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({config: {}})
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch',
	() => jest.fn(() => {})
);

function renderExperienceToolbarSection(
	mockState = {},
	mockConfig = {},
	mockDispatch = () => {}
) {
	Object.defineProperty(configModule, 'config', {
		get: () => mockConfig,
	});

	return render(
		<StoreAPIContextProvider
			dispatch={mockDispatch}
			getState={() => mockState}
		>
			<ExperienceToolbarSection selectId="test-select-id" />
		</StoreAPIContextProvider>,
		{
			baseElement: document.body,
		}
	);
}

const mockState = {
	availableSegmentsExperiences: {
		0: {
			hasLockedSegmentsExperiment: false,
			name: 'Default Experience',
			priority: -1,
			segmentsEntryId: 'test-segment-id-00',
			segmentsExperienceId: '0',
			segmentsExperimentStatus: undefined,
			segmentsExperimentURL: 'https//:default-experience.com',
		},
		'test-experience-id-01': {
			hasLockedSegmentsExperiment: false,
			languageIds: ['en_US', 'es_ES'],
			name: 'Experience #1',
			priority: 3,
			segmentsEntryId: 'test-segment-id-00',
			segmentsExperienceId: 'test-experience-id-01',
			segmentsExperimentStatus: undefined,
			segmentsExperimentURL: 'https//:experience-1.com',
		},
		'test-experience-id-02': {
			hasLockedSegmentsExperiment: false,
			languageIds: ['en_US', 'es_ES', 'ar_SA'],
			name: 'Experience #2',
			priority: 1,
			segmentsEntryId: 'test-segment-id-01',
			segmentsExperienceId: 'test-experience-id-02',
			segmentsExperimentStatus: undefined,
			segmentsExperimentURL: 'https//:experience-2.com',
		},
	},
	permissions: {
		EDIT_SEGMENTS_ENTRY: true,
		UPDATE: true,
	},
	segmentsExperienceId: '0',
	widgets: [],
};

const mockConfig = {
	addSegmentsExperienceURL: MOCK_CREATE_URL,
	availableLanguages: {
		ar_SA: {
			default: false,
			displayName: 'Arabic (Saudi Arabia)',
			languageIcon: 'ar-sa',
			languageId: 'ar_SA',
			w3cLanguageId: 'ar-SA',
		},
		en_US: {
			default: false,
			displayName: 'English (United States)',
			languageIcon: 'en-us',
			languageId: 'en_US',
			w3cLanguageId: 'en-US',
		},
		es_ES: {
			default: true,
			displayName: 'Spanish (Spain)',
			languageIcon: 'es-es',
			languageId: 'es_ES',
			w3cLanguageId: 'es-ES',
		},
	},
	availableSegmentsEntries: {
		'test-segment-id-00': {
			name: 'A segment 0',
			segmentsEntryId: 'test-segment-id-00',
		},
		'test-segment-id-01': {
			name: 'A segment 1',
			segmentsEntryId: 'test-segment-id-01',
		},
	},
	classPK: 'test-classPK',
	defaultSegmentsExperienceId: '0',
	deleteSegmentsExperienceURL: MOCK_DELETE_URL,
	duplicateSegmentsExperienceURL: MOCK_DUPLICATE_URL,
	updateSegmentsExperiencePriorityURL: MOCK_UPDATE_PRIORITY_URL,
	updateSegmentsExperienceURL: MOCK_UPDATE_URL,
};

describe('ExperienceToolbarSection', () => {
	beforeAll(() => {
		Liferay.component = jest.fn();
	});

	afterEach(() => {
		cleanup();
		serviceFetch.mockReset();
	});

	it('shows a list of Experiences ordered by priority', async () => {
		const {
			getAllByRole,
			getByLabelText,
			getByRole,
		} = renderExperienceToolbarSection(mockState, mockConfig);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const listedExperiences = getAllByRole('listitem');

		expect(listedExperiences.length).toBe(3);

		/**
		 * Experiences are ordered by priority
		 */
		expect(
			within(listedExperiences[0]).getByText('Experience #1')
		).toBeInTheDocument();
		expect(
			within(listedExperiences[1]).getByText('Experience #2')
		).toBeInTheDocument();
		expect(
			within(listedExperiences[2]).getByText('Default Experience')
		).toBeInTheDocument();
	});

	it('displays a help hint on the locked icon for a locked Experience', async () => {
		serviceFetch.mockImplementation(() => Promise.resolve());

		const mockStateWithLockedExperience = {
			...mockState,
			availableSegmentsExperiences: {
				...mockState.availableSegmentsExperiences,
				'test-experience-id-03': {
					hasLockedSegmentsExperiment: true,
					name: 'Experience #3',
					priority: 5,
					segmentsEntryId: 'test-segment-id-00',
					segmentsExperienceId: 'test-experience-id-03',
					segmentsExperimentStatus: {
						label: 'running',
						value: 3,
					},
					segmentsExperimentURL: 'https//:locked-experience.com',
				},
			},
		};
		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		const {
			getAllByRole,
			getByLabelText,
			getByRole,
			getByText,
		} = renderExperienceToolbarSection(
			mockStateWithLockedExperience,
			mockConfig,
			mockDispatch
		);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		expect(getByText('Experience #3')).toBeInTheDocument();

		const icons = getAllByRole('presentation');

		const lockIcon = icons[1];

		// Hackily work around:
		//
		//      "TypeError: Cannot read property '_defaultView' of undefined"
		//
		// Caused by: https://github.com/jsdom/jsdom/issues/2499

		document.activeElement.blur = () => {};

		userEvent.click(lockIcon);

		getByText('experience-locked');
		getByText('edit-is-not-allowed-for-this-experience');
	});

	it('calls the backend to increase priority', async () => {
		serviceFetch.mockImplementation((url, {body}) =>
			Promise.resolve({
				priority: body.newPriority,
				segmentsExperienceId: 'test-experience-id-02',
			})
		);

		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		const {
			getAllByRole,
			getByLabelText,
			getByRole,
		} = renderExperienceToolbarSection(mockState, mockConfig, mockDispatch);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const experienceItems = getAllByRole('listitem');

		expect(experienceItems.length).toBe(3);

		expect(
			within(experienceItems[0]).getByText('Experience #1')
		).toBeInTheDocument();
		expect(
			within(experienceItems[1]).getByText('Experience #2')
		).toBeInTheDocument();

		const bottomExperiencePriorityButton = within(
			experienceItems[1]
		).getByTitle('prioritize-experience');
		const topExperiencePriorityButton = within(
			experienceItems[0]
		).getByTitle('prioritize-experience');

		/**
		 * Top Experience cannot be prioritized
		 */
		expect(topExperiencePriorityButton.disabled).toBe(true);

		/**
		 * Bottom Experience can be prioritized
		 */
		expect(bottomExperiencePriorityButton.disabled).toBe(false);

		userEvent.click(bottomExperiencePriorityButton);

		await wait(() => expect(serviceFetch).toHaveBeenCalledTimes(1));

		expect(serviceFetch).toHaveBeenCalledWith(
			expect.stringContaining(MOCK_UPDATE_PRIORITY_URL),
			expect.objectContaining({
				body: expect.objectContaining({
					newPriority: 3,
					segmentsExperienceId: 'test-experience-id-02',
				}),
			}),
			expect.any(Function)
		);

		expect(mockDispatch).toHaveBeenCalledWith(
			expect.objectContaining({
				type: UPDATE_SEGMENTS_EXPERIENCES_LIST,
			})
		);
	});

	it('calls the backend to decrease priority', async () => {
		serviceFetch.mockImplementation((url, {body}) =>
			Promise.resolve({
				priority: body.newPriority,
				segmentsExperienceId: 'test-experience-id-01',
			})
		);

		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		const {
			getAllByRole,
			getByLabelText,
			getByRole,
		} = renderExperienceToolbarSection(mockState, mockConfig, mockDispatch);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const experienceItems = getAllByRole('listitem');

		expect(experienceItems.length).toBe(3);

		expect(
			within(experienceItems[0]).getByText('Experience #1')
		).toBeInTheDocument();
		expect(
			within(experienceItems[1]).getByText('Experience #2')
		).toBeInTheDocument();

		const bottomExperiencePriorityButton = within(
			experienceItems[1]
		).getByTitle('deprioritize-experience');
		const topExperiencePriorityButton = within(
			experienceItems[0]
		).getByTitle('deprioritize-experience');

		/**
		 * Top Experience can be deprioritized
		 */
		expect(topExperiencePriorityButton.disabled).toBe(false);

		/**
		 * Bottom Experience cannot be deprioritized
		 */
		expect(bottomExperiencePriorityButton.disabled).toBe(false);

		userEvent.click(topExperiencePriorityButton);

		await wait(() => expect(serviceFetch).toHaveBeenCalledTimes(1));

		expect(serviceFetch).toHaveBeenCalledWith(
			expect.stringContaining(MOCK_UPDATE_PRIORITY_URL),
			expect.objectContaining({
				body: expect.objectContaining({
					newPriority: 1,
					segmentsExperienceId: 'test-experience-id-01',
				}),
			}),
			expect.any(Function)
		);

		expect(mockDispatch).toHaveBeenCalledWith(
			expect.objectContaining({
				type: UPDATE_SEGMENTS_EXPERIENCES_LIST,
			})
		);
	});

	it('calls the backend to create a new experience', async () => {
		serviceFetch
			.mockImplementationOnce((url, {body}) =>
				Promise.resolve({
					segmentsExperience: {
						active: true,
						name: body.name,
						priority: '1000',
						segmentsEntryId: body.segmentsEntryId,
						segmentsExperienceId: 'a-new-test-experience-id',
					},
				})
			)
			.mockImplementationOnce(() => {
				return Promise.resolve([]);
			});

		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		const {
			getAllByRole,
			getByLabelText,
			getByRole,
			getByText,
		} = renderExperienceToolbarSection(mockState, mockConfig, mockDispatch);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const experienceItems = getAllByRole('listitem');

		expect(experienceItems.length).toBe(3);

		const newExperienceButton = getByText('new-experience');

		userEvent.click(newExperienceButton);

		await wait(() => getByLabelText('name'));

		const nameInput = getByLabelText('name');
		const audienceInput = getByLabelText('audience');

		userEvent.type(nameInput, 'New Experience #1');

		userEvent.selectOptions(audienceInput, 'A segment #1');

		// Grab parentElement here to work around jsdom v13 issue.
		// "TypeError: Cannot read property '_defaultView' of undefined"

		userEvent.click(getByText('save').parentElement);

		await wait(() => expect(serviceFetch).toHaveBeenCalledTimes(2));

		expect(serviceFetch).toHaveBeenCalledWith(
			expect.stringContaining(MOCK_CREATE_URL),
			expect.objectContaining({
				body: expect.objectContaining({
					name: 'New Experience #1',
					segmentsEntryId: 'test-segment-id-00',
				}),
			}),
			expect.any(Function)
		);

		expect(mockDispatch).toHaveBeenCalledWith(
			expect.objectContaining({
				type: CREATE_SEGMENTS_EXPERIENCE,
			})
		);
	});

	it('calls the backend to update the experience', async () => {
		serviceFetch.mockImplementation((url, {body}) =>
			Promise.resolve({
				name: body.name,
				segmentsEntryId: body.segmentsEntryId,
			})
		);

		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		const {
			getAllByRole,
			getByLabelText,
			getByRole,
			getByText,
		} = renderExperienceToolbarSection(mockState, mockConfig, mockDispatch);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const experienceItems = getAllByRole('listitem');

		expect(experienceItems.length).toBe(3);

		expect(
			within(experienceItems[0]).getByText('Experience #1')
		).toBeInTheDocument();

		const editExperienceButton = within(experienceItems[0]).getByTitle(
			'edit-experience'
		);

		expect(editExperienceButton.disabled).toBe(false);

		userEvent.click(editExperienceButton);

		await waitForElement(() => getByLabelText('name'));

		const nameInput = getByLabelText('name');
		const segmentSelect = getByLabelText('audience');

		expect(nameInput.value).toBe('Experience #1');
		expect(segmentSelect.value).toBe('test-segment-id-00');

		userEvent.type(nameInput, 'New Experience #1');
		userEvent.selectOptions(segmentSelect, 'A segment 0');

		expect(nameInput.value).toBe('New Experience #1');
		expect(segmentSelect.value).toBe('test-segment-id-00');

		// Grab parentElement here to work around jsdom v13 issue.
		// "TypeError: Cannot read property '_defaultView' of undefined"

		userEvent.click(getByText('save').parentElement);

		await wait(() => expect(serviceFetch).toHaveBeenCalledTimes(1));

		expect(serviceFetch).toHaveBeenCalledWith(
			expect.stringContaining(MOCK_UPDATE_URL),
			expect.objectContaining({
				body: expect.objectContaining({
					name: 'New Experience #1',
					segmentsEntryId: 'test-segment-id-00',
					segmentsExperienceId: 'test-experience-id-01',
				}),
			}),
			expect.any(Function)
		);

		expect(mockDispatch).toHaveBeenCalledWith(
			expect.objectContaining({
				type: UPDATE_SEGMENTS_EXPERIENCE,
			})
		);
	});

	it('calls the backend to delete experience', async () => {
		serviceFetch
			.mockImplementationOnce(() => Promise.resolve())
			.mockImplementationOnce(() => Promise.resolve([]));

		/**
		 * Auto confirm deletion
		 */
		window.confirm = jest.fn(() => true);

		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		const mockStateForDelete = {
			...mockState,
			layoutData: {
				items: {
					'00001': {
						config: {
							fragmentEntryLinkId: 1000,
						},
						type: 'fragment',
					},
					'00004': {
						config: {
							fragmentEntryLinkId: 4000, // latest version of layoutData is not in layoutDataList
						},
						type: 'fragment',
					},
				},
			},
			layoutDataList: [
				{
					layoutData: {
						items: {
							'00001': {
								config: {
									fragmentEntryLinkId: 10000,
								},
								type: 'fragment',
							},
						},
					},
					segmentsExperienceId: 'test-experience-id-00',
				},
				{
					layoutData: {
						items: {
							'00001': {
								config: {
									fragmentEntryLinkId: 1000,
								},
								type: 'fragment',
							},
							'0002': {
								config: {
									fragmentEntryLinkId: 2000,
								},
								type: 'fragment', // unique to the experience we delete
							},
							'0004': {
								config: {
									fragmentEntryLinkId: 4000,
								},
								type: 'fragment',
							},
						},
					},
					segmentsExperienceId: 'test-experience-id-01',
				},
				{
					layoutData: {
						items: {
							'00001': {
								config: {
									fragmentEntryLinkId: 1000,
								},
								type: 'fragment',
							},
							'0003': {
								config: {
									fragmentEntryLinkId: 3000,
								},
								type: 'fragment',
							},
						},
					},
					segmentsExperienceId: 'test-experience-id-02',
				},
			],
		};

		const {
			getAllByRole,
			getByLabelText,
			getByRole,
		} = renderExperienceToolbarSection(
			mockStateForDelete,
			mockConfig,
			mockDispatch
		);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const experienceItems = getAllByRole('listitem');

		expect(experienceItems.length).toBe(3);

		expect(
			within(experienceItems[0]).getByText('Experience #1')
		).toBeInTheDocument();

		const deleteExperienceButton = within(experienceItems[0]).getByTitle(
			'delete-experience'
		);

		userEvent.click(deleteExperienceButton);

		await wait(() => expect(window.confirm).toHaveBeenCalledTimes(1));

		await wait(() => expect(serviceFetch).toHaveBeenCalledTimes(1));

		expect(serviceFetch).toHaveBeenCalledWith(
			expect.stringContaining(MOCK_DELETE_URL),
			expect.objectContaining({
				body: expect.objectContaining({
					segmentsExperienceId: 'test-experience-id-01',
				}),
			}),
			expect.any(Function)
		);

		expect(mockDispatch).toHaveBeenCalledWith(
			expect.objectContaining({
				type: DELETE_SEGMENTS_EXPERIENCE,
			})
		);
	});

	it('calls the backend to duplicate an experience', async () => {
		serviceFetch
			.mockImplementationOnce((url, {body}) =>
				Promise.resolve({
					segmentsExperience: {
						active: true,
						name: body.name,
						priority: '1000',
						segmentsEntryId: body.segmentsEntryId,
						segmentsExperienceId: 'a-new-test-experience-id',
					},
				})
			)
			.mockImplementationOnce(() => {
				return Promise.resolve([]);
			});

		const mockDispatch = jest.fn((a) => {
			if (typeof a === 'function') {
				return a(mockDispatch);
			}
		});

		const {
			getAllByRole,
			getByLabelText,
			getByRole,
		} = renderExperienceToolbarSection(mockState, mockConfig, mockDispatch);

		const dropDownButton = getByLabelText('experience');

		userEvent.click(dropDownButton);

		await waitForElement(() => getByRole('list'));

		const experienceItems = getAllByRole('listitem');

		expect(experienceItems.length).toBe(3);

		expect(
			within(experienceItems[0]).getByText('Experience #1')
		).toBeInTheDocument();

		const duplicateExperienceButton = within(experienceItems[0]).getByTitle(
			'duplicate-experience'
		);

		userEvent.click(duplicateExperienceButton);

		await wait(() => expect(serviceFetch).toHaveBeenCalledTimes(2));

		expect(serviceFetch).toHaveBeenCalledWith(
			expect.stringContaining(MOCK_DUPLICATE_URL),
			expect.objectContaining({
				body: expect.objectContaining({
					segmentsExperienceId: 'test-experience-id-01',
				}),
			}),
			expect.any(Function)
		);

		expect(mockDispatch).toHaveBeenCalledWith(
			expect.objectContaining({
				type: CREATE_SEGMENTS_EXPERIENCE,
			})
		);
	});
});

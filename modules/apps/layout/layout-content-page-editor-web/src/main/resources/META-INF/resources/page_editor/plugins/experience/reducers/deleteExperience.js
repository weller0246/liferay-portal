import {
	selectExperience,
	deleteExperienceById,
	removeLayoutDataItemById,
	switchLayoutData,
	setExperienceLock
} from './utils';

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

function deleteExperienceReducer(state, payload) {
	let nextState = state;
	const {defaultExperienceId, segmentsExperienceId} = payload;

	if (nextState.segmentsExperienceId === segmentsExperienceId) {
		nextState = selectExperience(nextState, defaultExperienceId);
		nextState = setExperienceLock(
			nextState,
			nextState.availableSegmentsExperiences[defaultExperienceId]
		);
		nextState = switchLayoutData(nextState, {
			currentExperienceId: segmentsExperienceId,
			targetExperienceId: defaultExperienceId
		});
	}

	nextState = removeLayoutDataItemById(nextState, segmentsExperienceId);
	nextState = deleteExperienceById(nextState, segmentsExperienceId);

	// TODO setWidgets

	return nextState;
}

export default deleteExperienceReducer;

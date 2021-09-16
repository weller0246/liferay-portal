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

import {HEADER_PROJECT_ID, REQUEST_TIMEOUT} from './utils/constants';

/**
 * Client used to abstract communication with the Analytics Endpoint. It exposes the send
 * as only valid entry point for sending and modifying requests.
 * It process queues to send messages to the server. It will periodically run a task to
 * process messages in its queue.
 */
class ClientAdapter {
	constructor({endpointUrl, projectId, timeout} = {}) {
		this.projectId = projectId;
		this.timeout = timeout || REQUEST_TIMEOUT;
		this.url = endpointUrl;
	}

	/**
	 * Returns a Request object with all data from the analytics instance
	 * including the batched event objects
	 * @param {Object} analytics The Analytics instance from which the data is extracted
	 * @param {string} userId The userId string representation
	 * @protected
	 * @returns {Object} Parameters of the request to be sent.
	 */
	_getRequestParameters() {
		const headers = {'Content-Type': 'application/json'};

		if (this.projectId) {
			Object.assign(headers, {[HEADER_PROJECT_ID]: this.projectId});
		}

		return {
			cache: 'default',
			credentials: 'same-origin',
			headers,
			method: 'POST',
			mode: 'cors',
		};
	}

	/**
	 * Returns the Response object or a rejected Promise based on the
	 * HTTP Response Code of the Response object
	 * @param {Object} response Response
	 * @returns {Object} Promise
	 */
	_validateResponse(response) {
		if (!response.ok) {
			response = new Promise((_, reject) => reject(response));
		}

		return response;
	}

	/**
	 * Returns a resolved or rejected promise as per the response status or if the request times out.
	 * @param {Object} analytics The Analytics instance from which the data is extracted
	 * @param {string} userId The userId string representation
	 * @returns {Object} Promise object representing the result of the operation
	 */
	sendWithTimeout(payload) {
		return Promise.race([this.send(payload), this._timeout()]);
	}

	/**
	 * Send a request with given payload and url.
	 */
	send(payload) {
		const parameters = this._getRequestParameters();

		Object.assign(parameters, {
			body: JSON.stringify(payload),
		});

		return fetch(this.url, parameters).then(this._validateResponse);
	}

	/**
	 * Returns a promise that times out after the given time limit is exceeded
	 * @param {number} timeout
	 * @returns {Object} Promise
	 */
	_timeout() {
		return new Promise((_, reject) => setTimeout(reject, this.timeout));
	}
}

export {ClientAdapter};
export default ClientAdapter;

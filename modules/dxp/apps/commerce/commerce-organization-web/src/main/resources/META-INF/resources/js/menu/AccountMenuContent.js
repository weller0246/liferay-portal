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

import ClayDropDown from '@clayui/drop-down';
import React, {useContext} from 'react';

import ChartContext from '../ChartContext';
import {deleteAccount, updateAccount} from '../data/accounts';
import {ACTION_KEYS} from '../utils/constants';
import {hasPermission} from '../utils/index';

export default function AccountMenuContent({closeMenu, data, parentData}) {
	const {chartInstanceRef} = useContext(ChartContext);

	function handleDelete() {
		if (
			confirm(
				Liferay.Util.sub(
					Liferay.Language.get('x-will-be-deleted'),
					data.name
				)
			)
		) {
			deleteAccount(data.id).then(() => {
				chartInstanceRef.current.deleteNodes([data], true);

				closeMenu();
			});
		}
	}

	function handleRemove() {
		if (
			confirm(
				Liferay.Util.sub(
					Liferay.Language.get('x-will-be-removed-from-x'),
					data.name,
					parentData.name
				)
			)
		) {
			updateAccount(data.id, {
				organizationIds: data.organizationIds.filter(
					(id) => Number(id) !== Number(parentData.id)
				),
			}).then(() => {
				chartInstanceRef.current.deleteNodes([data], false);

				closeMenu();
			});
		}
	}

	const actions = [];

	if (hasPermission(data, ACTION_KEYS.account.REMOVE)) {
		actions.push(
			<ClayDropDown.Item key="remove" onClick={handleRemove}>
				{Liferay.Language.get('remove')}
			</ClayDropDown.Item>
		);
	}

	if (hasPermission(data, ACTION_KEYS.account.DELETE)) {
		actions.push(
			<ClayDropDown.Item key="delete" onClick={handleDelete}>
				{Liferay.Language.get('delete')}
			</ClayDropDown.Item>
		);
	}

	return actions;
}

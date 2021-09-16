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

import './Violation.scss';

import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import React from 'react';

import PanelNavigator from './PanelNavigator';
import Rule from './Rule';

import type {Violations} from '../hooks/useA11y';

type Params = {
	name: string;
	ruleId: string;
	target: string;
};

type ViolationProps = {
	next?: (payload: Params) => void;
	params?: Pick<Params, 'ruleId'>;
	previous?: () => void;
	violations: Omit<Violations, 'iframes'>;
};

function Violation({next, params, previous, violations}: ViolationProps) {
	if (!params) {
		return null;
	}

	const {ruleId} = params;

	const {description, helpUrl, id, impact, nodes, tags} = violations.rules[
		ruleId
	];

	return (
		<>
			<PanelNavigator
				helpUrl={helpUrl}
				impact={impact}
				onBack={() => {
					if (previous) {
						previous();
					}
				}}
				tags={tags}
				title={id}
			/>
			<div className="a11y-panel--violation-body">
				<ClayPanel.Group flush>
					<ClayPanel
						displayTitle={Liferay.Language.get('details')}
						displayType="unstyled"
						role="tab"
						showCollapseIcon={false}
					>
						<ClayPanel.Body>{description}</ClayPanel.Body>
					</ClayPanel>
					<ClayPanel
						displayTitle={Liferay.Language.get('occurrences')}
						displayType="unstyled"
						role="tab"
						showCollapseIcon={false}
					>
						<ClayPanel.Body className="panel-no-padding">
							<ClayList className="list-group-flush">
								{nodes.map((target, index) => {
									const name = Liferay.Util.sub(
										Liferay.Language.get('occurrence-x'),
										String(index + 1)
									);

									return (
										<Rule
											aria-label={name}
											key={index}
											onClick={() => {
												if (next) {
													next({
														name,
														ruleId,
														target,
													});
												}
											}}
											ruleText={name}
										/>
									);
								})}
							</ClayList>
						</ClayPanel.Body>
					</ClayPanel>
				</ClayPanel.Group>
			</div>
		</>
	);
}

export default Violation;

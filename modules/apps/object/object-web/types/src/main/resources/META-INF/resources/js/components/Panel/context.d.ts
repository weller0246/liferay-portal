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

import React from 'react';
declare type TState = {
	expanded: boolean;
};
declare type TAction = {
	payload: {
		[key: string]: any;
	};
	type: keyof typeof TYPES;
};
declare type TDispatch = React.Dispatch<
	React.ReducerAction<React.Reducer<TState, TAction>>
>;
declare const initialState: {
	expanded: boolean;
};
interface IPanelContextProps extends Array<TState | TDispatch> {
	0: typeof initialState;
	1: TDispatch;
}
export declare const PanelContext: React.Context<IPanelContextProps>;
export declare const TYPES: {
	readonly CHANGE_PANEL_EXPANDED: 'CHANGE_PANEL_EXPANDED';
};
declare const PanelContextProvider: React.FC<React.HTMLAttributes<HTMLElement>>;
export default PanelContextProvider;

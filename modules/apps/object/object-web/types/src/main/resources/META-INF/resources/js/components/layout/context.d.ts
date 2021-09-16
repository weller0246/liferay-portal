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
import {TObjectField, TObjectLayout} from './types';
declare type TState = {
	objectLayout: TObjectLayout;
	objectFields: TObjectField[];
	objectLayoutId: string;
};
declare type TAction = {
	payload: {
		[key: string]: any;
	};
	type: keyof typeof TYPES;
};
interface ILayoutContextProps extends Array<TState | Function> {
	0: typeof initialState;
	1: React.Dispatch<React.ReducerAction<React.Reducer<TState, TAction>>>;
}
declare const LayoutContext: React.Context<ILayoutContextProps>;
export declare const TYPES: {
	readonly ADD_OBJECT_FIELDS: 'ADD_OBJECT_FIELDS';
	readonly ADD_OBJECT_LAYOUT: 'ADD_OBJECT_LAYOUT';
	readonly ADD_OBJECT_LAYOUT_BOX: 'ADD_OBJECT_LAYOUT_BOX';
	readonly ADD_OBJECT_LAYOUT_FIELD: 'ADD_OBJECT_LAYOUT_FIELD';
	readonly ADD_OBJECT_LAYOUT_TAB: 'ADD_OBJECT_LAYOUT_TAB';
	readonly CHANGE_OBJECT_LAYOUT_BOX_ATTRIBUTE: 'CHANGE_OBJECT_LAYOUT_BOX_ATTRIBUTE';
	readonly DELETE_OBJECT_LAYOUT_BOX: 'DELETE_OBJECT_LAYOUT_BOX';
	readonly DELETE_OBJECT_LAYOUT_FIELD: 'DELETE_OBJECT_LAYOUT_FIELD';
	readonly DELETE_OBJECT_LAYOUT_TAB: 'DELETE_OBJECT_LAYOUT_TAB';
};
declare const initialState: TState;
interface ILayoutContextProviderProps
	extends React.HTMLAttributes<HTMLElement> {
	value: {
		objectLayoutId: string;
	};
}
export declare const LayoutContextProvider: React.FC<ILayoutContextProviderProps>;
export default LayoutContext;

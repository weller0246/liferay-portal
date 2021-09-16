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

declare type TName = {
	[key: string]: string;
};
export declare type TObjectLayout = {
	name: TName;
	defaultObjectLayout: boolean;
	objectLayoutTabs: TObjectLayoutTab[];
};
export declare type TObjectLayoutTab = {
	name: TName;
	objectLayoutBoxes: TObjectLayoutBox[];
	priority: number;
};
export declare type TObjectLayoutBox = {
	collapsable: boolean;
	name: TName;
	objectLayoutRows: TObjectLayoutRow[];
	priority: number;
};
export declare type TObjectLayoutRow = {
	priority: number;
	objectLayoutColumns: TObjectLayoutColumn[];
};
export declare type TObjectLayoutColumn = {
	objectFieldId: number;
	priority: number;
};
export declare type TObjectField = {
	id: number;
	indexed: boolean;
	indexedAsKeyword: boolean;
	indexedLanguageId: string;
	label: TName;
	listTypeDefinitionId: boolean;
	name: string;
	required: boolean;
	type: string;
};
export {};

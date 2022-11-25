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

/// <reference types="react" />

import 'codemirror/mode/groovy/groovy';
import {
	CustomItem,
	FormError,
	SidebarCategory,
} from '@liferay/object-js-components-web';
interface ActionProps {
	isApproved?: boolean;
	objectAction: Partial<ObjectAction>;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	readOnly?: boolean;
	requestParams: {
		method: 'POST' | 'PUT';
		url: string;
	};
	successMessage: string;
	systemObject: boolean;
	title: string;
	validateExpressionURL: string;
}
export declare type ActionError = FormError<
	ObjectAction & ObjectActionParameters
> & {
	predefinedValues?: {
		[key: string]: string;
	};
};
export default function Action({
	isApproved,
	objectAction: initialValues,
	objectActionCodeEditorElements,
	objectActionExecutors,
	objectActionTriggers,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	readOnly,
	requestParams: {method, url},
	successMessage,
	systemObject,
	validateExpressionURL,
}: ActionProps): JSX.Element;
export {};

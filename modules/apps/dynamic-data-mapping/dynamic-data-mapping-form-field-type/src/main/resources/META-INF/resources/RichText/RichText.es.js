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

import {ClassicEditor} from 'frontend-editor-ckeditor-web';
import React, {useEffect, useRef} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const RichText = ({
	editingLanguageId,
	editorConfig,
	id,
	name,
	onChange,
	predefinedValue,
	readOnly,
	value,
	visible,
	...otherProps
}) => {
	const editorRef = useRef();

	useEffect(() => {
		const editor = editorRef.current?.editor;

		if (editor) {
			const data = {...editor.getData()};
			const config = {...data.config};

			config.contentsLangDirection =
				Liferay.Language.direction[editingLanguageId];

			config.contentsLanguage = editingLanguageId;

			editor.setData({...data, config});
		}
	}, [editingLanguageId, editorRef]);

	const currentValue = value ?? predefinedValue;

	return (
		<FieldBase
			{...otherProps}
			id={id}
			name={name}
			readOnly={readOnly}
			style={readOnly ? {pointerEvents: 'none'} : null}
			visible={visible}
		>
			<ClassicEditor
				contents={currentValue}
				editorConfig={editorConfig}
				name={name}
				onChange={(content) => {
					if (currentValue !== content) {
						onChange({target: {value: content}});
					}
				}}
				onSetData={({data: {dataValue: value}, editor: {mode}}) => {
					if (mode === 'source') {
						onChange({target: {value}});
					}
				}}
				readOnly={readOnly}
				ref={editorRef}
			/>

			<input name={name} type="hidden" value={currentValue} />
		</FieldBase>
	);
};

export default RichText;

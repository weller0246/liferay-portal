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

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectLayout;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.object.model.ObjectLayout",
	service = ModelDocumentContributor.class
)
public class ObjectLayoutModelDocumentContributor
	implements ModelDocumentContributor<ObjectLayout> {

	@Override
	public void contribute(Document document, ObjectLayout objectLayout) {
		document.addLocalizedText(Field.NAME, objectLayout.getNameMap());
		document.addLocalizedKeyword(
			"localized_name", objectLayout.getNameMap(), true);
		document.addKeyword(
			"objectDefinitionId", objectLayout.getObjectDefinitionId());
		document.remove(Field.USER_NAME);
	}

}
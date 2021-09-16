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

package com.liferay.layout.admin.web.internal.model.listener;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = ModelListener.class)
public class LayoutPageTemplateCollectionStagingModelListener
	extends BaseModelListener<LayoutPageTemplateCollection> {

	@Override
	public void onAfterCreate(
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(layoutPageTemplateCollection);
	}

	@Override
	public void onAfterRemove(
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(layoutPageTemplateCollection);
	}

	@Override
	public void onAfterUpdate(
			LayoutPageTemplateCollection originalLayoutPageTemplateCollection,
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(layoutPageTemplateCollection);
	}

	@Reference
	private StagingModelListener<LayoutPageTemplateCollection>
		_stagingModelListener;

}
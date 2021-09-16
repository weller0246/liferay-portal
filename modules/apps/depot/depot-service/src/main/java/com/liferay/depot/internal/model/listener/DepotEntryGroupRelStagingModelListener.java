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

package com.liferay.depot.internal.model.listener;

import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ModelListener.class)
public class DepotEntryGroupRelStagingModelListener
	extends BaseModelListener<DepotEntryGroupRel> {

	@Override
	public void onAfterCreate(DepotEntryGroupRel depotEntryGroupRel)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(depotEntryGroupRel);
	}

	@Override
	public void onAfterRemove(DepotEntryGroupRel depotEntryGroupRel)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(depotEntryGroupRel);
	}

	@Override
	public void onAfterUpdate(
			DepotEntryGroupRel originalDepotEntryGroupRel,
			DepotEntryGroupRel depotEntryGroupRel)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(depotEntryGroupRel);
	}

	@Reference
	private StagingModelListener<DepotEntryGroupRel> _stagingModelListener;

}
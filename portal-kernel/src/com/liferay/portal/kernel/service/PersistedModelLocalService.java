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

package com.liferay.portal.kernel.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public interface PersistedModelLocalService {

	public default PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public <T> T dslQuery(DSLQuery dslQuery);

	public int dslQueryCount(DSLQuery dslQuery);

	public default BasePersistence<?> getBasePersistence() {
		throw new UnsupportedOperationException();
	}

	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

}
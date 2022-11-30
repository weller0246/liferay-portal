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

package com.liferay.portal.search.web.internal.facet.display.context;

import java.io.Serializable;

/**
 * @author Lino Alves
 */
public class AssetEntriesSearchFacetTermDisplayContext extends BucketDisplayContext {

	public String getAssetType() {
		return _assetType;
	}
	public String getTypeName() {
		return _typeName;
	}

	public void setAssetType(String assetType) {
		_assetType = assetType;
	}

	public void setTypeName(String typeName) {
		_typeName = typeName;
	}

	private String _assetType;
	private String _typeName;

}
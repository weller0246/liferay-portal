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

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.portal.kernel.search.filter.GeoBoundingBoxFilter;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = GeoBoundingBoxFilterTranslator.class)
public class GeoBoundingBoxFilterTranslatorImpl
	implements GeoBoundingBoxFilterTranslator {

	@Override
	public QueryBuilder translate(GeoBoundingBoxFilter geoBoundingBoxFilter) {
		GeoBoundingBoxQueryBuilder geoBoundingBoxQueryBuilder =
			QueryBuilders.geoBoundingBoxQuery(geoBoundingBoxFilter.getField());

		GeoLocationPoint bottomRightGeoLocationPoint =
			geoBoundingBoxFilter.getBottomRightGeoLocationPoint();

		GeoPoint bottomRightGeoPoint = new GeoPoint(
			bottomRightGeoLocationPoint.getLatitude(),
			bottomRightGeoLocationPoint.getLongitude());

		GeoLocationPoint topLeftGeoLocationPoint =
			geoBoundingBoxFilter.getTopLeftGeoLocationPoint();

		GeoPoint topLeftGeoPoint = new GeoPoint(
			topLeftGeoLocationPoint.getLatitude(),
			topLeftGeoLocationPoint.getLongitude());

		geoBoundingBoxQueryBuilder.setCorners(
			topLeftGeoPoint, bottomRightGeoPoint);

		return geoBoundingBoxQueryBuilder;
	}

}
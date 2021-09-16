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

package com.liferay.batch.engine.web.internal.display.context;

import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Matija Petanjek
 */
public class BatchEngineImportTaskDisplayContext extends BaseDisplayContext {

	public BatchEngineImportTaskDisplayContext(
		BatchEngineImportTaskService batchEngineImportTaskService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(renderRequest, renderResponse);

		_batchEngineImportTaskService = batchEngineImportTaskService;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			renderResponse
		).setTabs1(
			"import"
		).buildPortletURL();

		String delta = ParamUtil.getString(httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		return portletURL;
	}

	public SearchContainer<BatchEngineImportTask> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, "no-entries-were-found");

		String orderByCol = getOrderByCol();

		_searchContainer.setOrderByCol(orderByCol);

		String orderByType = getOrderByType();

		_searchContainer.setOrderByType(orderByType);

		_searchContainer.setRowChecker(null);
		_searchContainer.setResults(
			_batchEngineImportTaskService.getBatchEngineImportTasks(
				companyId, _searchContainer.getStart(),
				_searchContainer.getEnd(),
				OrderByComparatorFactoryUtil.create(
					"BatchEngineImportTask", orderByCol, orderByType)));
		_searchContainer.setTotal(
			_batchEngineImportTaskService.getBatchEngineImportTasksCount(
				companyId));

		return _searchContainer;
	}

	private final BatchEngineImportTaskService _batchEngineImportTaskService;
	private SearchContainer<BatchEngineImportTask> _searchContainer;

}
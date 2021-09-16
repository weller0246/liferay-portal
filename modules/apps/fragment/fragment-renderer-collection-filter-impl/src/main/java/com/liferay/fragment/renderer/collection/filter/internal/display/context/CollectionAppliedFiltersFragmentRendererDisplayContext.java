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

package com.liferay.fragment.renderer.collection.filter.internal.display.context;

import com.liferay.fragment.collection.filter.FragmentCollectionFilter;
import com.liferay.fragment.collection.filter.FragmentCollectionFilterTracker;
import com.liferay.fragment.collection.filter.constants.FragmentCollectionFilterConstants;
import com.liferay.fragment.constants.FragmentConfigurationFieldDataType;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pablo.Molina
 */
public class CollectionAppliedFiltersFragmentRendererDisplayContext {

	public CollectionAppliedFiltersFragmentRendererDisplayContext(
		FragmentCollectionFilterTracker fragmentCollectionFilterTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		_fragmentCollectionFilterTracker = fragmentCollectionFilterTracker;
		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_httpServletRequest = httpServletRequest;

		_editMode = Objects.equals(
			fragmentRendererContext.getMode(), FragmentEntryLinkConstants.EDIT);
		_fragmentEntryLink = fragmentRendererContext.getFragmentEntryLink();
		_locale = fragmentRendererContext.getLocale();
	}

	public List<Map<String, String>> getAppliedFilters() {
		List<Map<String, String>> appliedFilters = new ArrayList<>();

		HttpServletRequest originalHttpServletRequest =
			PortalUtil.getOriginalServletRequest(_httpServletRequest);

		Map<String, String[]> parameters =
			originalHttpServletRequest.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String parameterName = entry.getKey();

			if (!parameterName.startsWith(
					FragmentCollectionFilterConstants.FILTER_PREFIX) ||
				ArrayUtil.isEmpty(entry.getValue())) {

				continue;
			}

			List<String> parameterData = StringUtil.split(
				parameterName, CharPool.UNDERLINE);

			if (parameterData.size() != 3) {
				continue;
			}

			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					GetterUtil.getLong(parameterData.get(2)));

			if (fragmentEntryLink == null) {
				continue;
			}

			JSONArray targetCollectionsJSONArray =
				(JSONArray)
					_fragmentEntryConfigurationParser.
						getConfigurationFieldValue(
							fragmentEntryLink.getEditableValues(),
							"targetCollections",
							FragmentConfigurationFieldDataType.ARRAY);

			if ((targetCollectionsJSONArray == null) ||
				Collections.disjoint(
					JSONUtil.toStringSet(targetCollectionsJSONArray),
					_getTargetCollections())) {

				continue;
			}

			FragmentCollectionFilter fragmentCollectionFilter =
				_fragmentCollectionFilterTracker.getFragmentCollectionFilter(
					parameterData.get(1));

			if (fragmentCollectionFilter == null) {
				continue;
			}

			for (String filterValue : entry.getValue()) {
				appliedFilters.add(
					HashMapBuilder.put(
						"filterFragmentEntryLinkId", parameterData.get(2)
					).put(
						"filterLabel",
						fragmentCollectionFilter.getFilterValueLabel(
							filterValue, _locale)
					).put(
						"filterType", parameterData.get(1)
					).put(
						"filterValue", filterValue
					).build());
			}
		}

		return appliedFilters;
	}

	public Map<String, Object> getCollectionAppliedFiltersProps() {
		if (_collectionAppliedFiltersProps != null) {
			return _collectionAppliedFiltersProps;
		}

		_collectionAppliedFiltersProps = HashMapBuilder.<String, Object>put(
			"filterPrefix", FragmentCollectionFilterConstants.FILTER_PREFIX
		).put(
			"fragmentEntryLinkNamespace", getFragmentEntryLinkNamespace()
		).build();

		return _collectionAppliedFiltersProps;
	}

	public String getFragmentEntryLinkNamespace() {
		return StringBundler.concat(
			"fragment_", _fragmentEntryLink.getFragmentEntryLinkId(),
			StringPool.UNDERLINE, _fragmentEntryLink.getNamespace());
	}

	public boolean isEditMode() {
		return _editMode;
	}

	public boolean showClearFiltersButton() {
		return (boolean)
			_fragmentEntryConfigurationParser.getConfigurationFieldValue(
				_fragmentEntryLink.getEditableValues(), "showClearFilters",
				FragmentConfigurationFieldDataType.BOOLEAN);
	}

	private Set<String> _getTargetCollections() {
		if (_targetCollections != null) {
			return _targetCollections;
		}

		JSONArray targetCollectionsJSONArray =
			(JSONArray)
				_fragmentEntryConfigurationParser.getConfigurationFieldValue(
					_fragmentEntryLink.getEditableValues(), "targetCollections",
					FragmentConfigurationFieldDataType.ARRAY);

		_targetCollections = JSONUtil.toStringSet(targetCollectionsJSONArray);

		return _targetCollections;
	}

	private Map<String, Object> _collectionAppliedFiltersProps;
	private final boolean _editMode;
	private final FragmentCollectionFilterTracker
		_fragmentCollectionFilterTracker;
	private final FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;
	private final FragmentEntryLink _fragmentEntryLink;
	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final Locale _locale;
	private Set<String> _targetCollections;

}
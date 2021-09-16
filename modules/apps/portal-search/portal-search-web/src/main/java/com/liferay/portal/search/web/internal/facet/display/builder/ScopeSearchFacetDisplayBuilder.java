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

package com.liferay.portal.search.web.internal.facet.display.builder;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.facet.display.context.ScopeSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.ScopeSearchFacetTermDisplayContext;
import com.liferay.portal.search.web.internal.site.facet.configuration.SiteFacetPortletInstanceConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author André de Oliveira
 */
public class ScopeSearchFacetDisplayBuilder {

	public ScopeSearchFacetDisplayBuilder(RenderRequest renderRequest)
		throws ConfigurationException {

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_siteFacetPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SiteFacetPortletInstanceConfiguration.class);
	}

	public ScopeSearchFacetDisplayContext build() {
		ScopeSearchFacetDisplayContext scopeSearchFacetDisplayContext =
			new ScopeSearchFacetDisplayContext();

		scopeSearchFacetDisplayContext.setDisplayStyleGroupId(
			getDisplayStyleGroupId());
		scopeSearchFacetDisplayContext.setNothingSelected(isNothingSelected());
		scopeSearchFacetDisplayContext.setPaginationStartParameterName(
			_paginationStartParameterName);
		scopeSearchFacetDisplayContext.setParameterName(_parameterName);
		scopeSearchFacetDisplayContext.setParameterValue(
			getFirstParameterValueString());
		scopeSearchFacetDisplayContext.setParameterValues(
			getParameterValueStrings());
		scopeSearchFacetDisplayContext.setRenderNothing(isRenderNothing());
		scopeSearchFacetDisplayContext.setSiteFacetPortletInstanceConfiguration(
			_siteFacetPortletInstanceConfiguration);
		scopeSearchFacetDisplayContext.setTermDisplayContexts(
			buildTermDisplayContexts(getTermCollectors()));

		return scopeSearchFacetDisplayContext;
	}

	public void setFacet(Facet facet) {
		_facet = facet;
	}

	public void setFilteredGroupIds(long[] groupIds) {
		_filteredGroupIds = groupIds;
	}

	public void setFrequenciesVisible(boolean frequenciesVisible) {
		_showCounts = frequenciesVisible;
	}

	public void setFrequencyThreshold(int frequencyThreshold) {
		_countThreshold = frequencyThreshold;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	public void setLanguage(Language language) {
		_language = language;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setMaxTerms(int maxTerms) {
		_maxTerms = maxTerms;
	}

	public void setPaginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;
	}

	public void setParameterName(String parameterName) {
		_parameterName = parameterName;
	}

	public void setParameterValue(String parameterValue) {
		setParameterValues(
			Collections.singletonList(Objects.requireNonNull(parameterValue)));
	}

	public void setParameterValues(List<String> parameterValues) {
		Objects.requireNonNull(parameterValues);

		Stream<String> parameterValuesStream = parameterValues.stream();

		Stream<Long> groupIdsStream = parameterValuesStream.map(
			GetterUtil::getLong);

		groupIdsStream = groupIdsStream.filter(groupId -> groupId > 0);

		_selectedGroupIds = groupIdsStream.collect(Collectors.toList());
	}

	public void setRequest(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	protected ScopeSearchFacetTermDisplayContext buildTermDisplayContext(
		long groupId, int count, boolean selected) {

		ScopeSearchFacetTermDisplayContext scopeSearchFacetTermDisplayContext =
			new ScopeSearchFacetTermDisplayContext();

		scopeSearchFacetTermDisplayContext.setCount(count);
		scopeSearchFacetTermDisplayContext.setDescriptiveName(
			getDescriptiveName(groupId));
		scopeSearchFacetTermDisplayContext.setGroupId(groupId);
		scopeSearchFacetTermDisplayContext.setSelected(selected);
		scopeSearchFacetTermDisplayContext.setShowCount(_showCounts);

		return scopeSearchFacetTermDisplayContext;
	}

	protected ScopeSearchFacetTermDisplayContext buildTermDisplayContext(
		TermCollector termCollector) {

		int count = termCollector.getFrequency();

		if ((_countThreshold > 0) && (_countThreshold > count)) {
			return null;
		}

		return buildTermDisplayContext(termCollector, count);
	}

	protected ScopeSearchFacetTermDisplayContext buildTermDisplayContext(
		TermCollector termCollector, int count) {

		long groupId = GetterUtil.getLong(termCollector.getTerm());

		return buildTermDisplayContext(groupId, count, isSelected(groupId));
	}

	protected List<ScopeSearchFacetTermDisplayContext> buildTermDisplayContexts(
		List<TermCollector> termCollectors) {

		if (termCollectors.isEmpty()) {
			return getEmptySearchResultTermDisplayContexts();
		}

		List<ScopeSearchFacetTermDisplayContext>
			scopeSearchFacetTermDisplayContexts = new ArrayList<>(
				termCollectors.size());

		int limit = termCollectors.size();

		if ((_maxTerms > 0) && (limit > _maxTerms)) {
			limit = _maxTerms;
		}

		for (int i = 0; i < limit; i++) {
			TermCollector termCollector = termCollectors.get(i);

			int count = termCollector.getFrequency();

			if (_countThreshold <= count) {
				scopeSearchFacetTermDisplayContexts.add(
					buildTermDisplayContext(termCollector, count));
			}
		}

		return scopeSearchFacetTermDisplayContexts;
	}

	protected String getDescriptiveName(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return "[" + groupId + "]";
		}

		try {
			String name = group.getDescriptiveName(_locale);

			if (group.isStagingGroup()) {
				name = StringBundler.concat(
					name, StringPool.SPACE, StringPool.OPEN_PARENTHESIS,
					_language.get(_httpServletRequest, "staged"),
					StringPool.CLOSE_PARENTHESIS);
			}

			return name;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	protected long getDisplayStyleGroupId() {
		long displayStyleGroupId =
			_siteFacetPortletInstanceConfiguration.displayStyleGroupId();

		if (displayStyleGroupId <= 0) {
			displayStyleGroupId = _themeDisplay.getScopeGroupId();
		}

		return displayStyleGroupId;
	}

	protected List<ScopeSearchFacetTermDisplayContext>
		getEmptySearchResultTermDisplayContexts() {

		Stream<Long> groupIdsStream = _selectedGroupIds.stream();

		Stream<ScopeSearchFacetTermDisplayContext>
			scopeSearchFacetTermDisplayContextsStream = groupIdsStream.map(
				groupId -> buildTermDisplayContext(groupId, 0, true));

		return scopeSearchFacetTermDisplayContextsStream.collect(
			Collectors.toList());
	}

	protected String getFirstParameterValueString() {
		if (_selectedGroupIds.isEmpty()) {
			return "0";
		}

		return String.valueOf(_selectedGroupIds.get(0));
	}

	protected List<String> getParameterValueStrings() {
		Stream<Long> groupIdsStream = _selectedGroupIds.stream();

		Stream<String> parameterValuesStream = groupIdsStream.map(
			String::valueOf);

		return parameterValuesStream.collect(Collectors.toList());
	}

	protected List<TermCollector> getTermCollectors() {
		if (_facet == null) {
			return Collections.emptyList();
		}

		FacetCollector facetCollector = _facet.getFacetCollector();

		if (facetCollector != null) {
			return facetCollector.getTermCollectors();
		}

		return Collections.<TermCollector>emptyList();
	}

	protected boolean isFilteredByThisSite() {
		if (_filteredGroupIds.length == 1) {
			return true;
		}

		return false;
	}

	protected boolean isNothingSelected() {
		if (_selectedGroupIds.isEmpty()) {
			return true;
		}

		return false;
	}

	protected boolean isRenderNothing() {
		if (isFilteredByThisSite()) {
			return true;
		}

		if (!isNothingSelected()) {
			return false;
		}

		List<TermCollector> termCollectors = getTermCollectors();

		if (!termCollectors.isEmpty()) {
			return false;
		}

		return true;
	}

	protected boolean isSelected(Long groupId) {
		if (_selectedGroupIds.contains(groupId)) {
			return true;
		}

		return false;
	}

	private int _countThreshold;
	private Facet _facet;
	private long[] _filteredGroupIds = {};
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private Language _language;
	private Locale _locale;
	private int _maxTerms;
	private String _paginationStartParameterName;
	private String _parameterName;
	private List<Long> _selectedGroupIds = Collections.emptyList();
	private boolean _showCounts;
	private final SiteFacetPortletInstanceConfiguration
		_siteFacetPortletInstanceConfiguration;
	private final ThemeDisplay _themeDisplay;

}
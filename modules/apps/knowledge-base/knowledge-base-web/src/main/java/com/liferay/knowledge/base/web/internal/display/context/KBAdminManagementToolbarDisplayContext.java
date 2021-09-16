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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBArticleSearchDisplay;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBArticleServiceUtil;
import com.liferay.knowledge.base.service.KBFolderServiceUtil;
import com.liferay.knowledge.base.service.KBTemplateServiceUtil;
import com.liferay.knowledge.base.web.internal.KBUtil;
import com.liferay.knowledge.base.web.internal.search.EntriesChecker;
import com.liferay.knowledge.base.web.internal.search.KBObjectsSearch;
import com.liferay.knowledge.base.web.internal.security.permission.resource.AdminPermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBArticlePermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBFolderPermission;
import com.liferay.knowledge.base.web.internal.util.comparator.KBOrderByComparatorAdapter;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class KBAdminManagementToolbarDisplayContext {

	public KBAdminManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			RenderRequest renderRequest, RenderResponse renderResponse,
			PortletConfig portletConfig)
		throws PortalException, PortletException {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_portletConfig = portletConfig;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_createSearchContainer();
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteEntries");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public List<String> getAvailableActions(KBArticle kbArticle)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (KBArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), kbArticle,
				ActionKeys.DELETE)) {

			availableActions.add("deleteEntries");
		}

		return availableActions;
	}

	public List<String> getAvailableActions(KBFolder kbFolder)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (KBFolderPermission.contains(
				_themeDisplay.getPermissionChecker(), kbFolder,
				ActionKeys.DELETE)) {

			availableActions.add("deleteEntries");
		}

		return availableActions;
	}

	public CreationMenu getCreationMenu() throws PortalException {
		CreationMenu creationMenu = null;

		long kbFolderClassNameId = PortalUtil.getClassNameId(
			KBFolderConstants.getClassName());

		long parentResourceClassNameId = ParamUtil.getLong(
			_httpServletRequest, "parentResourceClassNameId",
			kbFolderClassNameId);

		long parentResourcePrimKey = ParamUtil.getLong(
			_httpServletRequest, "parentResourcePrimKey",
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		boolean hasAddKBArticlePermission = false;
		boolean hasAddKBFolderPermission = false;

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		if (parentResourceClassNameId == kbFolderClassNameId) {
			hasAddKBArticlePermission = KBFolderPermission.contains(
				permissionChecker, _themeDisplay.getScopeGroupId(),
				parentResourcePrimKey, KBActionKeys.ADD_KB_ARTICLE);
			hasAddKBFolderPermission = KBFolderPermission.contains(
				permissionChecker, _themeDisplay.getScopeGroupId(),
				parentResourcePrimKey, KBActionKeys.ADD_KB_FOLDER);
		}
		else {
			hasAddKBArticlePermission = AdminPermission.contains(
				permissionChecker, _themeDisplay.getScopeGroupId(),
				KBActionKeys.ADD_KB_ARTICLE);
		}

		if (hasAddKBFolderPermission) {
			if (creationMenu == null) {
				creationMenu = new CreationMenu();
			}

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						PortletURLBuilder.createRenderURL(
							_liferayPortletResponse
						).setMVCPath(
							"/admin/common/edit_folder.jsp"
						).setRedirect(
							PortalUtil.getCurrentURL(_httpServletRequest)
						).setParameter(
							"parentResourceClassNameId",
							PortalUtil.getClassNameId(
								KBFolderConstants.getClassName())
						).setParameter(
							"parentResourcePrimKey", parentResourcePrimKey
						).buildPortletURL());

					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "folder"));
				});
		}

		if (hasAddKBArticlePermission) {
			if (creationMenu == null) {
				creationMenu = new CreationMenu();
			}

			String templatePath = _getTemplatePath();

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						PortletURLBuilder.createRenderURL(
							_liferayPortletResponse
						).setMVCPath(
							templatePath + "edit_article.jsp"
						).setRedirect(
							PortalUtil.getCurrentURL(_httpServletRequest)
						).setParameter(
							"parentResourceClassNameId",
							parentResourceClassNameId
						).setParameter(
							"parentResourcePrimKey", parentResourcePrimKey
						).buildPortletURL());

					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "basic-article"));
				});

			OrderByComparator<KBTemplate> orderByComparator =
				OrderByComparatorFactoryUtil.create(
					"KBTemplate", "title", false);

			List<KBTemplate> kbTemplates =
				KBTemplateServiceUtil.getGroupKBTemplates(
					_themeDisplay.getScopeGroupId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator);

			if (!kbTemplates.isEmpty()) {
				for (KBTemplate kbTemplate : kbTemplates) {
					creationMenu.addDropdownItem(
						dropdownItem -> {
							dropdownItem.setHref(
								PortletURLBuilder.createRenderURL(
									_liferayPortletResponse
								).setMVCPath(
									templatePath + "edit_article.jsp"
								).setRedirect(
									PortalUtil.getCurrentURL(
										_httpServletRequest)
								).setParameter(
									"kbTemplateId", kbTemplate.getKbTemplateId()
								).setParameter(
									"parentResourceClassNameId",
									parentResourceClassNameId
								).setParameter(
									"parentResourcePrimKey",
									parentResourcePrimKey
								).buildPortletURL());

							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									HtmlUtil.escape(kbTemplate.getTitle())));
						});
				}
			}
		}

		if ((parentResourceClassNameId == kbFolderClassNameId) &&
			AdminPermission.contains(
				permissionChecker, _themeDisplay.getScopeGroupId(),
				KBActionKeys.ADD_KB_ARTICLE)) {

			if (creationMenu == null) {
				creationMenu = new CreationMenu();
			}

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						PortletURLBuilder.createRenderURL(
							_liferayPortletResponse
						).setMVCPath(
							"/admin/import.jsp"
						).setRedirect(
							PortalUtil.getCurrentURL(_httpServletRequest)
						).setParameter(
							"parentKBFolderId", parentResourcePrimKey
						).buildPortletURL());

					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "import"));
				});
		}

		return creationMenu;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	public String getOrderByType() {
		return _searchContainer.getOrderByType();
	}

	public SearchContainer<Object> getSearchContainer() {
		return _searchContainer;
	}

	public PortletURL getSearchURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/admin/search.jsp"
		).setRedirect(
			_getRedirect()
		).buildPortletURL();
	}

	public PortletURL getSortingURL() throws PortletException {
		return PortletURLBuilder.create(
			_getCurrentSortingURL()
		).setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc"
		).buildPortletURL();
	}

	public int getTotal() {
		return _searchContainer.getTotal();
	}

	public boolean isDisabled() {
		return !_searchContainer.hasResults();
	}

	public boolean isShowInfoButton() {
		return Validator.isNull(_getKeywords());
	}

	private SearchContainer<Object> _createSearchContainer()
		throws PortalException, PortletException {

		long kbFolderClassNameId = PortalUtil.getClassNameId(
			KBFolderConstants.getClassName());

		long parentResourceClassNameId = ParamUtil.getLong(
			_httpServletRequest, "parentResourceClassNameId",
			kbFolderClassNameId);

		long parentResourcePrimKey = ParamUtil.getLong(
			_httpServletRequest, "parentResourcePrimKey",
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_searchContainer = new KBObjectsSearch(
			_renderRequest,
			PortletURLUtil.clone(
				PortletURLUtil.getCurrent(
					_liferayPortletRequest, _liferayPortletResponse),
				_renderResponse));

		boolean kbFolderView = false;

		if (parentResourceClassNameId == kbFolderClassNameId) {
			kbFolderView = true;
		}

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			OrderByComparator<KBArticle> kbArticleOrderByComparator =
				KBUtil.getKBArticleOrderByComparator(
					_searchContainer.getOrderByCol(),
					_searchContainer.getOrderByType());

			_searchContainer.setOrderByComparator(
				new KBOrderByComparatorAdapter<>(kbArticleOrderByComparator));

			KBArticleSearchDisplay kbArticleSearchDisplay =
				KBArticleServiceUtil.getKBArticleSearchDisplay(
					_themeDisplay.getScopeGroupId(), keywords, keywords,
					WorkflowConstants.STATUS_ANY, null, null, false, new int[0],
					_searchContainer.getCur(), _searchContainer.getDelta(),
					kbArticleOrderByComparator);

			_searchContainer.setResults(
				new ArrayList<>(kbArticleSearchDisplay.getResults()));
			_searchContainer.setTotal(kbArticleSearchDisplay.getTotal());
		}
		else if (kbFolderView) {
			_searchContainer.setTotal(
				KBFolderServiceUtil.getKBFoldersAndKBArticlesCount(
					_themeDisplay.getScopeGroupId(), parentResourcePrimKey,
					WorkflowConstants.STATUS_ANY));
			_searchContainer.setResults(
				KBFolderServiceUtil.getKBFoldersAndKBArticles(
					_themeDisplay.getScopeGroupId(), parentResourcePrimKey,
					WorkflowConstants.STATUS_ANY, _searchContainer.getStart(),
					_searchContainer.getEnd(),
					KBUtil.getKBObjectsOrderByComparator(
						_searchContainer.getOrderByCol(),
						_searchContainer.getOrderByType())));
		}
		else {
			OrderByComparator<KBArticle> kbArticleOrderByComparator =
				KBUtil.getKBArticleOrderByComparator(
					_searchContainer.getOrderByCol(),
					_searchContainer.getOrderByType());

			_searchContainer.setOrderByComparator(
				new KBOrderByComparatorAdapter<>(kbArticleOrderByComparator));

			_searchContainer.setTotal(
				KBArticleServiceUtil.getKBArticlesCount(
					_themeDisplay.getScopeGroupId(), parentResourcePrimKey,
					WorkflowConstants.STATUS_ANY));
			_searchContainer.setResults(
				new ArrayList<>(
					KBArticleServiceUtil.getKBArticles(
						_themeDisplay.getScopeGroupId(), parentResourcePrimKey,
						WorkflowConstants.STATUS_ANY,
						_searchContainer.getStart(), _searchContainer.getEnd(),
						kbArticleOrderByComparator)));
		}

		_searchContainer.setRowChecker(
			new EntriesChecker(
				_liferayPortletRequest, _liferayPortletResponse));

		return _searchContainer;
	}

	private PortletURL _getCurrentSortingURL() throws PortletException {
		PortletURL sortingURL = PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_liferayPortletRequest, _liferayPortletResponse),
			_liferayPortletResponse);

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			sortingURL.setParameter("keywords", keywords);
		}

		return sortingURL;
	}

	private String _getKeywords() {
		return ParamUtil.getString(_httpServletRequest, "keywords");
	}

	private String _getOrderByCol() {
		return _searchContainer.getOrderByCol();
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				final Map<String, String> orderColumnsMap = HashMapBuilder.put(
					"modifiedDate", "modified-date"
				).put(
					"priority", "priority"
				).put(
					"title", "title"
				).put(
					"viewCount", "view-count"
				).build();

				String[] orderColumns = {
					"priority", "modifiedDate", "title", "viewCount"
				};

				for (String orderByCol : orderColumns) {
					add(
						dropdownItem -> {
							dropdownItem.setActive(
								orderByCol.equals(_getOrderByCol()));
							dropdownItem.setHref(
								_getCurrentSortingURL(), "orderByCol",
								orderByCol);
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									orderColumnsMap.get(orderByCol)));
						});
				}
			}
		};
	}

	private String _getRedirect() {
		return PortalUtil.escapeRedirect(
			ParamUtil.getString(
				_httpServletRequest, "redirect",
				PortalUtil.getCurrentURL(_httpServletRequest)));
	}

	private String _getTemplatePath() {
		return _portletConfig.getInitParameter("template-path");
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortletConfig _portletConfig;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<Object> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}
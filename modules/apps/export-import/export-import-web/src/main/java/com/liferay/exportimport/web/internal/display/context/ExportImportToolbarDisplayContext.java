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

package com.liferay.exportimport.web.internal.display.context;

import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.layoutsadmin.display.context.GroupDisplayContextHelper;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Péter Alius
 */
public class ExportImportToolbarDisplayContext {

	public ExportImportToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;

		_liferayPortletResponse = liferayPortletResponse;

		Portlet portlet = liferayPortletResponse.getPortlet();

		_portletNamespace = PortalUtil.getPortletNamespace(
			portlet.getRootPortletId());
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteEntries");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
			}
		).build();
	}

	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				GroupDisplayContextHelper groupDisplayContextHelper =
					new GroupDisplayContextHelper(_httpServletRequest);

				String mvcRenderCommandName = ParamUtil.getString(
					_httpServletRequest, "mvcRenderCommandName");

				String cmd;
				String label;
				String mvcPath;

				if (mvcRenderCommandName.equals(
						"/export_import/view_export_layouts")) {

					cmd = Constants.EXPORT;
					label = "custom-export";
					mvcPath = "/export/new_export/export_layouts.jsp";
				}
				else {
					cmd = Constants.IMPORT;
					label = "import";
					mvcPath = "/import/new_import/import_layouts.jsp";
				}

				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							getRenderURL(), "mvcPath", mvcPath, Constants.CMD,
							cmd, "groupId",
							String.valueOf(
								ParamUtil.getLong(
									_httpServletRequest, "groupId")),
							"liveGroupId",
							String.valueOf(
								groupDisplayContextHelper.getLiveGroupId()),
							"privateLayout",
							ParamUtil.getString(
								_httpServletRequest, "privateLayout",
								Boolean.FALSE.toString()),
							"displayStyle",
							ParamUtil.getString(
								_httpServletRequest, "displayStyle",
								"descriptive"));
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, label));
					});

				if (Objects.equals(cmd, Constants.EXPORT)) {
					List<ExportImportConfiguration> exportImportConfigurations =
						ExportImportConfigurationLocalServiceUtil.
							getExportImportConfigurations(
								groupDisplayContextHelper.getLiveGroupId(),
								ExportImportConfigurationConstants.
									TYPE_EXPORT_LAYOUT);

					for (ExportImportConfiguration exportImportConfiguration :
							exportImportConfigurations) {

						Map<String, Serializable> settingsMap =
							exportImportConfiguration.getSettingsMap();

						addRestDropdownItem(
							dropdownItem -> {
								dropdownItem.setHref(
									getRenderURL(), "mvcPath",
									"/export/new_export/export_layouts.jsp",
									Constants.CMD, Constants.EXPORT,
									"exportImportConfigurationId",
									String.valueOf(
										exportImportConfiguration.
											getExportImportConfigurationId()),
									"groupId",
									String.valueOf(
										ParamUtil.getLong(
											_httpServletRequest, "groupId")),
									"liveGroupId",
									String.valueOf(
										groupDisplayContextHelper.
											getLiveGroupId()),
									"privateLayout",
									MapUtil.getString(
										settingsMap, "privateLayout"),
									"displayStyle",
									ParamUtil.getString(
										_httpServletRequest, "displayStyle",
										"descriptive"));

								dropdownItem.setLabel(
									exportImportConfiguration.getName());
							});
					}
				}
			}
		};
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigatioDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "filter"));
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropDownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	public String getSearchContainerId() {
		return ParamUtil.getString(_httpServletRequest, "searchContainerId");
	}

	public String getSortingOrder() {
		return ParamUtil.getString(_httpServletRequest, "orderByType", "asc");
	}

	public String getSortingURL() {
		return PortletURLBuilder.create(
			getRenderURL()
		).setNavigation(
			ParamUtil.getString(_httpServletRequest, "navigation", "all")
		).setParameter(
			"displayStyle",
			ParamUtil.getString(
				_httpServletRequest, "displayStyle", "descriptive")
		).setParameter(
			"groupId", ParamUtil.getLong(_httpServletRequest, "groupId")
		).setParameter(
			"orderByCol", ParamUtil.getString(_httpServletRequest, "orderByCol")
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = ParamUtil.getString(
					_httpServletRequest, "orderByType");

				if (orderByType.equals("asc")) {
					return "desc";
				}

				return "asc";
			}
		).setParameter(
			"privateLayout",
			ParamUtil.getBoolean(_httpServletRequest, "privateLayout")
		).setParameter(
			"searchContainerId",
			ParamUtil.getString(_httpServletRequest, "searchContainerId")
		).buildString();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(getRenderURL(), getDisplayStyle()) {
			{
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	protected String getDisplayStyle() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_httpServletRequest);

		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		String displayPreferences = portalPreferences.getValue(
			ExportImportPortletKeys.EXPORT_IMPORT, "display-style",
			"descriptive");

		if (Validator.isNull(displayStyle)) {
			displayStyle = displayPreferences;
		}

		if (displayStyle != displayPreferences) {
			portalPreferences.setValue(
				ExportImportPortletKeys.EXPORT_IMPORT, "display-style",
				displayStyle);
		}

		return displayStyle;
	}

	protected PortletURL getRenderURL() {
		return _liferayPortletResponse.createRenderURL();
	}

	private List<DropdownItem> _getFilterNavigatioDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(
					getRenderURL(), "groupId",
					String.valueOf(
						ParamUtil.getLong(_httpServletRequest, "groupId")),
					"privateLayout",
					String.valueOf(
						ParamUtil.getBoolean(
							_httpServletRequest, "privateLayout")),
					"displayStyle",
					ParamUtil.getString(
						_httpServletRequest, "displayStyle", "descriptive"),
					"orderByCol",
					ParamUtil.getString(_httpServletRequest, "orderByCol"),
					"orderByType",
					ParamUtil.getString(_httpServletRequest, "orderByType"),
					"navigation", "all", "searchContainerId",
					ParamUtil.getString(
						_httpServletRequest, "searchContainerId"));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "all"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(
					getRenderURL(), "groupId",
					String.valueOf(
						ParamUtil.getLong(_httpServletRequest, "groupId")),
					"privateLayout",
					String.valueOf(
						ParamUtil.getBoolean(
							_httpServletRequest, "privateLayout")),
					"displayStyle",
					ParamUtil.getString(
						_httpServletRequest, "displayStyle", "descriptive"),
					"orderByCol",
					ParamUtil.getString(_httpServletRequest, "orderByCol"),
					"orderByType",
					ParamUtil.getString(_httpServletRequest, "orderByType"),
					"navigation", "completed", "searchContainerId",
					ParamUtil.getString(
						_httpServletRequest, "searchContainerId"));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "completed"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(
					getRenderURL(), "groupId",
					String.valueOf(
						ParamUtil.getLong(_httpServletRequest, "groupId")),
					"privateLayout",
					String.valueOf(
						ParamUtil.getBoolean(
							_httpServletRequest, "privateLayout")),
					"displayStyle",
					ParamUtil.getString(
						_httpServletRequest, "displayStyle", "descriptive"),
					"orderByCol",
					ParamUtil.getString(_httpServletRequest, "orderByCol"),
					"orderByType",
					ParamUtil.getString(_httpServletRequest, "orderByType"),
					"navigation", "in-progress", "searchContainerId",
					ParamUtil.getString(
						_httpServletRequest, "searchContainerId"));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "in-progress"));
			}
		).build();
	}

	private List<DropdownItem> _getOrderByDropDownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(
					getRenderURL(), "groupId",
					String.valueOf(
						ParamUtil.getLong(_httpServletRequest, "groupId")),
					"privateLayout",
					String.valueOf(
						ParamUtil.getBoolean(
							_httpServletRequest, "privateLayout")),
					"displayStyle",
					ParamUtil.getString(
						_httpServletRequest, "displayStyle", "descriptive"),
					"orderByCol", "name", "orderByType",
					ParamUtil.getString(_httpServletRequest, "orderByType"),
					"navigation",
					ParamUtil.getString(
						_httpServletRequest, "navigation", "all"),
					"searchContainerId",
					ParamUtil.getString(
						_httpServletRequest, "searchContainerId"));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "name"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(
					getRenderURL(), "groupId",
					String.valueOf(
						ParamUtil.getLong(_httpServletRequest, "groupId")),
					"privateLayout",
					String.valueOf(
						ParamUtil.getBoolean(
							_httpServletRequest, "privateLayout")),
					"displayStyle",
					ParamUtil.getString(
						_httpServletRequest, "displayStyle", "descriptive"),
					"orderByCol", "create-date", "orderByType",
					ParamUtil.getString(_httpServletRequest, "orderByType"),
					"navigation",
					ParamUtil.getString(
						_httpServletRequest, "navigation", "all"),
					"searchContainerId",
					ParamUtil.getString(
						_httpServletRequest, "searchContainerId"));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "create-date"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(
					getRenderURL(), "groupId",
					String.valueOf(
						ParamUtil.getLong(_httpServletRequest, "groupId")),
					"privateLayout",
					String.valueOf(
						ParamUtil.getBoolean(
							_httpServletRequest, "privateLayout")),
					"displayStyle",
					ParamUtil.getString(
						_httpServletRequest, "displayStyle", "descriptive"),
					"orderByCol", "completion-date", "orderByType",
					ParamUtil.getString(_httpServletRequest, "orderByType"),
					"navigation",
					ParamUtil.getString(
						_httpServletRequest, "navigation", "all"),
					"searchContainerId",
					ParamUtil.getString(
						_httpServletRequest, "searchContainerId"));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "completion-date"));
			}
		).build();
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final String _portletNamespace;

}
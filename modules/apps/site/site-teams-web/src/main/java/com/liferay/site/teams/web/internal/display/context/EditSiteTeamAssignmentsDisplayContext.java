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

package com.liferay.site.teams.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EditSiteTeamAssignmentsDisplayContext {

	public EditSiteTeamAssignmentsDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		this.httpServletRequest = httpServletRequest;
		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;
	}

	public PortletURL getEditTeamAssignmentsURL() {
		return PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCPath(
			"/edit_team_assignments.jsp"
		).setTabs1(
			getTabs1()
		).setParameter(
			"teamId", getTeamId()
		).buildPortletURL();
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(Objects.equals(getTabs1(), "users"));
				navigationItem.setHref(
					getEditTeamAssignmentsURL(), "tabs1", "users");
				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "users"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(
					Objects.equals(getTabs1(), "user-groups"));
				navigationItem.setHref(
					getEditTeamAssignmentsURL(), "tabs1", "user-groups");
				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "user-groups"));
			}
		).build();
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(httpServletRequest, "tabs1", "users");

		return _tabs1;
	}

	public Team getTeam() {
		if (_team != null) {
			return _team;
		}

		_team = TeamLocalServiceUtil.fetchTeam(getTeamId());

		return _team;
	}

	public long getTeamId() {
		if (_teamId != null) {
			return _teamId;
		}

		_teamId = ParamUtil.getLong(httpServletRequest, "teamId");

		return _teamId;
	}

	public String getTeamName() {
		if (_teamName != null) {
			return _teamName;
		}

		Team team = getTeam();

		_teamName = team.getName();

		return _teamName;
	}

	protected final HttpServletRequest httpServletRequest;
	protected final RenderRequest renderRequest;
	protected final RenderResponse renderResponse;

	private String _tabs1;
	private Team _team;
	private Long _teamId;
	private String _teamName;

}
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

package com.liferay.portal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.VirtualLayoutConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PropsValues;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 */
@RunWith(Arquillian.class)
public class PortalImplControlPanelFullURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testControlPanelPortlet() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append(_getPortalURL());
		sb.append(_portalImpl.getPathFriendlyURLPrivateGroup());
		sb.append(GroupConstants.CONTROL_PANEL_FRIENDLY_URL);
		sb.append(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		String portletId = PortletKeys.SERVER_ADMIN;

		sb.append(_getQueryString(portletId));

		Assert.assertEquals(
			sb.toString(),
			_portalImpl.getControlPanelFullURL(
				_group.getGroupId(), portletId, null));
	}

	@Test
	public void testMyAccountPortlet() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append(_getPortalURL());

		sb.append(_portalImpl.getPathFriendlyURLPrivateGroup());
		sb.append(GroupConstants.CONTROL_PANEL_FRIENDLY_URL);
		sb.append(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		String portletId = PortletKeys.MY_ACCOUNT;

		sb.append(_getQueryString(portletId));

		Portlet portlet = _portletLocalService.getPortletById(
			_group.getCompanyId(), portletId);

		Assert.assertEquals(
			portlet.toString() + " with control panel entry category " +
				portlet.getControlPanelEntryCategory(),
			sb.toString(),
			_portalImpl.getControlPanelFullURL(
				_group.getGroupId(), portletId, null));
	}

	@Test
	public void testSiteAdministrationPortlet() throws Exception {
		StringBundler sb = new StringBundler(7);

		sb.append(_getPortalURL());
		sb.append(_portalImpl.getPathFriendlyURLPrivateGroup());
		sb.append(_group.getFriendlyURL());
		sb.append(VirtualLayoutConstants.CANONICAL_URL_SEPARATOR);
		sb.append(GroupConstants.CONTROL_PANEL_FRIENDLY_URL);
		sb.append(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		String portletId = "com_liferay_journal_web_portlet_JournalPortlet";

		sb.append(_getQueryString(portletId));

		Assert.assertEquals(
			sb.toString(),
			_portalImpl.getControlPanelFullURL(
				_group.getGroupId(), portletId, null));
	}

	private String _getPortalURL() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		return _portalImpl.getPortalURL(
			company.getVirtualHostname(),
			_portalImpl.getPortalServerPort(false), false);
	}

	private String _getQueryString(String portletId) {
		return StringBundler.concat(
			"?p_p_id=", portletId, "&p_p_lifecycle=0&p_p_state=",
			WindowState.MAXIMIZED.toString(), "&p_p_mode=",
			PortletMode.VIEW.toString());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private final PortalImpl _portalImpl = new PortalImpl();

	@Inject
	private PortletLocalService _portletLocalService;

}
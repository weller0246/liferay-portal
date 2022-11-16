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

package com.liferay.portal.security.auto.login.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.action.UpdatePasswordAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.struts.Action;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Alvaro Saugar
 */
@RunWith(Arquillian.class)
public class AdminPasswordAutoLoginTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		try {
			_emailAdressAdminUser =
				PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX + StringPool.AT +
					_company.getMx();

			_user = UserLocalServiceUtil.getUserByEmailAddress(
				_company.getCompanyId(), _emailAdressAdminUser);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Error getting user ", exception);
			}
		}
	}

	@Test
	public void testAutologinToSetAdminPassword() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.COMPANY_ID, _company.getCompanyId());

		String[] credentials = _adminPasswordAutoLogin.login(
			mockHttpServletRequest, null);

		Assert.assertEquals(credentials[0], String.valueOf(_user.getUserId()));
		Assert.assertEquals(credentials[1], _user.getPassword());
		Assert.assertEquals("true", credentials[2]);
	}

	@Test
	public void testChangeUserConditionSetAdminPassword() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_prepareHttpServletRequest();

		_updatePasswordAction.execute(
			null, mockHttpServletRequest, new MockHttpServletResponse());

		User setPasswordUser = UserLocalServiceUtil.getUserByEmailAddress(
			_company.getCompanyId(), _emailAdressAdminUser);

		Assert.assertEquals(
			_user.getReminderQueryAnswer(), WorkflowConstants.LABEL_PENDING);
		Assert.assertEquals("", setPasswordUser.getReminderQueryAnswer());
	}

	private MockHttpServletRequest _prepareHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				ServletContextPool.get(StringPool.BLANK), HttpMethods.GET,
				StringPool.SLASH);

		mockHttpServletRequest.addParameter(Constants.CMD, "update");
		mockHttpServletRequest.addParameter("password1", "test");
		mockHttpServletRequest.addParameter("password2", "test");
		mockHttpServletRequest.addParameter("p_auth", "test");

		HttpSession httpSession = mockHttpServletRequest.getSession();

		httpSession.setAttribute(
			"LIFERAY_SHARED_AUTHENTICATION_TOKEN#CSRF", "test");

		Layout layout = LayoutLocalServiceUtil.getLayout(1);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setUser(_user);

		Group group = layout.getGroup();

		themeDisplay.setSiteGroupId(group.getGroupId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			mockHttpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		return mockHttpServletRequest;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdminPasswordAutoLoginTest.class);

	private static Company _company;
	private static String _emailAdressAdminUser;
	private static User _user;

	@Inject(filter = "component.name=*.AdminPasswordAutoLogin")
	private AutoLogin _adminPasswordAutoLogin;

	private final Action _updatePasswordAction = new UpdatePasswordAction();

}
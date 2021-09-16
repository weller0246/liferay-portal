/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMTemplateLink;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@RunWith(Arquillian.class)
public class UpgradeKaleoProcessTemplateLinkTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_timestamp = new Timestamp(System.currentTimeMillis());

		setUpClassNameIds();
		setUpPrimaryKeys();
		setUpUpgradeKaleoProcessTemplateLink();
	}

	@After
	public void tearDown() throws Exception {
		deleteKaleoProcess(_kaleoProcessId);
		deleteKaleoProcessLink(_kaleoProcessLinkId);
	}

	@Test
	public void testCreateKaleoProcess() throws Exception {
		addKaleoProcess(_kaleoProcessId);

		_kaleoProcessTemplateLinkUpgradeProcess.upgrade();

		DDMTemplateLink ddmTemplateLink =
			DDMTemplateLinkLocalServiceUtil.getTemplateLink(
				_kaleoProcessClassNameId, _kaleoProcessId);

		Assert.assertNotNull(ddmTemplateLink);

		_ddmTemplateLinks.add(ddmTemplateLink);
	}

	@Test
	public void testCreateKaleoProcessLink() throws Exception {
		addKaleoProcessLink(_kaleoProcessLinkId);

		_kaleoProcessTemplateLinkUpgradeProcess.upgrade();

		DDMTemplateLink ddmTemplateLink =
			DDMTemplateLinkLocalServiceUtil.getTemplateLink(
				_kaleoProcessLinkClassNameId, _kaleoProcessLinkId);

		Assert.assertNotNull(ddmTemplateLink);

		_ddmTemplateLinks.add(ddmTemplateLink);
	}

	protected void addKaleoProcess(long kaleoProcessId) throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into KaleoProcess (uuid_, kaleoProcessId, ",
					"groupId, companyId, userId, userName, createDate, ",
					"modifiedDate, DDLRecordSetId, DDMTemplateId, ",
					"workflowDefinitionName, workflowDefinitionVersion) ",
					"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			preparedStatement.setString(1, PortalUUIDUtil.generate());
			preparedStatement.setLong(2, kaleoProcessId);
			preparedStatement.setLong(3, _group.getGroupId());
			preparedStatement.setLong(4, _group.getCompanyId());
			preparedStatement.setLong(5, TestPropsValues.getUserId());
			preparedStatement.setString(6, null);
			preparedStatement.setTimestamp(7, _timestamp);
			preparedStatement.setTimestamp(8, _timestamp);
			preparedStatement.setLong(9, RandomTestUtil.randomLong());
			preparedStatement.setLong(10, RandomTestUtil.randomLong());
			preparedStatement.setString(11, StringUtil.randomString());
			preparedStatement.setInt(12, RandomTestUtil.randomInt());

			preparedStatement.executeUpdate();
		}
	}

	protected void addKaleoProcessLink(long kaleoProcessLinkId)
		throws Exception {

		String sql = StringBundler.concat(
			"insert into KaleoProcessLink (kaleoProcessLinkId, ",
			"kaleoProcessId, workflowTaskName, DDMTemplateId) values (?, ?, ",
			"?, ?)");

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setLong(1, kaleoProcessLinkId);
			preparedStatement.setLong(2, _kaleoProcessId);
			preparedStatement.setString(3, StringUtil.randomString());
			preparedStatement.setLong(4, RandomTestUtil.randomLong());

			preparedStatement.executeUpdate();
		}
	}

	protected void deleteKaleoProcess(long kaleoProcessId) throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from KaleoProcess where kaleoProcessId = " +
				kaleoProcessId);
	}

	protected void deleteKaleoProcessLink(long kaleoProcessLinkId)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		db.runSQL(
			"delete from KaleoProcessLink where kaleoProcessLinkId = " +
				kaleoProcessLinkId);
	}

	protected void setUpClassNameIds() {
		_kaleoProcessLinkClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink");
		_kaleoProcessClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess");
	}

	protected void setUpPrimaryKeys() {
		_kaleoProcessId = RandomTestUtil.randomLong();
		_kaleoProcessLinkId = RandomTestUtil.randomLong();
	}

	protected void setUpUpgradeKaleoProcessTemplateLink() {
		_upgradeStepRegistrator.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						String className = clazz.getName();

						if (className.contains(
								"KaleoProcessTemplateLinkUpgradeProcess")) {

							_kaleoProcessTemplateLinkUpgradeProcess =
								(UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	@DeleteAfterTestRun
	private final List<DDMTemplateLink> _ddmTemplateLinks = new ArrayList<>();

	@DeleteAfterTestRun
	private Group _group;

	private long _kaleoProcessClassNameId;
	private long _kaleoProcessId;
	private long _kaleoProcessLinkClassNameId;
	private long _kaleoProcessLinkId;
	private UpgradeProcess _kaleoProcessTemplateLinkUpgradeProcess;
	private Timestamp _timestamp;

	@Inject(
		filter = "component.name=com.liferay.portal.workflow.kaleo.forms.internal.upgrade.KaleoFormsServiceUpgrade"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}
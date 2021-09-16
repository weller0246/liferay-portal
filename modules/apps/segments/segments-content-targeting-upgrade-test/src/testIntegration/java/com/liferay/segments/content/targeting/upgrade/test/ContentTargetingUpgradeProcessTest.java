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

package com.liferay.segments.content.targeting.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.odata.normalizer.Normalizer;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.DateFormat;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class ContentTargetingUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_expandoTable = ExpandoTestUtil.addTable(
			PortalUtil.getClassNameId(User.class), "CUSTOM_FIELDS");

		_group = GroupTestUtil.addGroup();

		_db = DBManagerUtil.getDB();

		createContentTargetingTables();
		setUpContentTargetingUpgradeProcess();
	}

	@After
	public void tearDown() throws Exception {
		dropContentTargetingTables();
	}

	@Test
	public void testUpgradeContentTargetingUserSegments() throws Exception {
		long contentTargetingUserSegmentId = -1L;

		Map<Locale, String> nameMap = RandomTestUtil.randomLocaleStringMap();
		Map<Locale, String> descriptionMap =
			RandomTestUtil.randomLocaleStringMap();

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId, nameMap, descriptionMap);

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Assert.assertEquals(nameMap, segmentsEntry.getNameMap());
		Assert.assertEquals(descriptionMap, segmentsEntry.getDescriptionMap());
	}

	@Test
	public void testUpgradeContentTargetingUserSegmentsWithBrowserRule()
		throws Exception {

		long contentTargetingUserSegmentId = -1L;

		insertContentTargetingRuleInstance(
			contentTargetingUserSegmentId, "BrowserRule", "Chrome");

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Criteria criteriaObj = segmentsEntry.getCriteriaObj();

		Assert.assertNotNull(criteriaObj);

		Criteria.Criterion criterion = criteriaObj.getCriterion("context");

		Assert.assertNotNull(criterion);

		Assert.assertEquals(
			Criteria.Conjunction.AND,
			Criteria.Conjunction.parse(criterion.getConjunction()));
		Assert.assertEquals(
			"(browser eq 'Chrome')", criterion.getFilterString());
	}

	@Test
	public void testUpgradeContentTargetingUserSegmentsWithCustomFieldRule()
		throws Exception {

		long contentTargetingUserSegmentId = -1L;

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING);

		String expandoValue = RandomTestUtil.randomString();

		insertContentTargetingRuleInstance(
			contentTargetingUserSegmentId, "CustomFieldRule",
			_getCustomFieldRuleTypeSettings(expandoColumn, expandoValue));

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Criteria criteriaObj = segmentsEntry.getCriteriaObj();

		Assert.assertNotNull(criteriaObj);

		Criteria.Criterion criterion = criteriaObj.getCriterion("user");

		Assert.assertNotNull(criterion);

		Assert.assertEquals(
			Criteria.Conjunction.AND,
			Criteria.Conjunction.parse(criterion.getConjunction()));
		Assert.assertEquals(
			_getCustomFieldFilterString(expandoColumn, expandoValue),
			criterion.getFilterString());
	}

	@Test
	public void testUpgradeContentTargetingUserSegmentsWithLanguageRule()
		throws Exception {

		long contentTargetingUserSegmentId = -1L;

		insertContentTargetingRuleInstance(
			contentTargetingUserSegmentId, "LanguageRule", "es_ES");

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Criteria criteriaObj = segmentsEntry.getCriteriaObj();

		Assert.assertNotNull(criteriaObj);

		Criteria.Criterion criterion = criteriaObj.getCriterion("context");

		Assert.assertNotNull(criterion);

		Assert.assertEquals(
			Criteria.Conjunction.AND,
			Criteria.Conjunction.parse(criterion.getConjunction()));
		Assert.assertEquals(
			"(languageId eq 'es_ES')", criterion.getFilterString());
	}

	@Test
	public void testUpgradeContentTargetingUserSegmentsWithLastLoginDateRule()
		throws Exception {

		long contentTargetingUserSegmentId = -1L;

		ZoneId zoneId = ZoneOffset.UTC;

		ZonedDateTime startZonedDateTime = ZonedDateTime.of(
			2018, 1, 1, 10, 0, 0, 0, zoneId);
		ZonedDateTime endZonedDateTime = ZonedDateTime.of(
			2019, 1, 1, 10, 0, 0, 0, zoneId);

		insertContentTargetingRuleInstance(
			contentTargetingUserSegmentId, "LastLoginDateRule",
			_getDateRangeTypeSettings(
				startZonedDateTime, endZonedDateTime, zoneId.getId(),
				"between"));

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Criteria criteriaObj = segmentsEntry.getCriteriaObj();

		Assert.assertNotNull(criteriaObj);

		Criteria.Criterion criterion = criteriaObj.getCriterion("context");

		Assert.assertNotNull(criterion);

		Assert.assertEquals(
			Criteria.Conjunction.AND,
			Criteria.Conjunction.parse(criterion.getConjunction()));
		Assert.assertEquals(
			StringBundler.concat(
				"(lastSignInDateTime gt ",
				startZonedDateTime.format(
					DateTimeFormatter.ISO_OFFSET_DATE_TIME),
				") and (lastSignInDateTime lt ",
				endZonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
				")"),
			criterion.getFilterString());
	}

	@Test
	public void testUpgradeContentTargetingUserSegmentsWithOrganizationMemberRule()
		throws Exception {

		long contentTargetingUserSegmentId = -1L;

		insertContentTargetingRuleInstance(
			contentTargetingUserSegmentId, "OrganizationMemberRule", "12345");

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Criteria criteriaObj = segmentsEntry.getCriteriaObj();

		Assert.assertNotNull(criteriaObj);

		Criteria.Criterion criterion = criteriaObj.getCriterion(
			"user-organization");

		Assert.assertNotNull(criterion);

		Assert.assertEquals(
			Criteria.Conjunction.AND,
			Criteria.Conjunction.parse(criterion.getConjunction()));
		Assert.assertEquals(
			"(organizationId eq '12345')", criterion.getFilterString());
	}

	@Test
	public void testUpgradeContentTargetingUserSegmentsWithOSRule()
		throws Exception {

		long contentTargetingUserSegmentId = -1L;

		insertContentTargetingRuleInstance(
			contentTargetingUserSegmentId, "OSRule", "iOS");

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Criteria criteriaObj = segmentsEntry.getCriteriaObj();

		Assert.assertNotNull(criteriaObj);

		Criteria.Criterion criterion = criteriaObj.getCriterion("context");

		Assert.assertNotNull(criterion);

		Assert.assertEquals(
			Criteria.Conjunction.AND,
			Criteria.Conjunction.parse(criterion.getConjunction()));
		Assert.assertEquals(
			"contains(userAgent, 'iOS')", criterion.getFilterString());
	}

	@Test
	public void testUpgradeContentTargetingUserSegmentsWithPreviousVisitedSiteRule()
		throws Exception {

		long contentTargetingUserSegmentId = -1L;

		JSONObject jsonObject = JSONUtil.put("value", "liferay");

		JSONArray jsonArray = JSONUtil.put(jsonObject);

		insertContentTargetingRuleInstance(
			contentTargetingUserSegmentId, "PreviousVisitedSiteRule",
			jsonArray.toString());

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Criteria criteriaObj = segmentsEntry.getCriteriaObj();

		Assert.assertNotNull(criteriaObj);

		Criteria.Criterion criterion = criteriaObj.getCriterion("context");

		Assert.assertNotNull(criterion);

		Assert.assertEquals(
			Criteria.Conjunction.AND,
			Criteria.Conjunction.parse(criterion.getConjunction()));
		Assert.assertEquals(
			"contains(referrerURL, 'liferay')", criterion.getFilterString());
	}

	@Test
	public void testUpgradeContentTargetingUserSegmentsWithRegularRoleRule()
		throws Exception {

		long contentTargetingUserSegmentId = -1L;

		insertContentTargetingRuleInstance(
			contentTargetingUserSegmentId, "RegularRoleRule", "12345");

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Criteria criteriaObj = segmentsEntry.getCriteriaObj();

		Assert.assertNotNull(criteriaObj);

		Criteria.Criterion criterion = criteriaObj.getCriterion("user");

		Assert.assertNotNull(criterion);

		Assert.assertEquals(
			Criteria.Conjunction.AND,
			Criteria.Conjunction.parse(criterion.getConjunction()));
		Assert.assertEquals(
			"(roleIds eq '12345')", criterion.getFilterString());
	}

	@Test
	public void testUpgradeContentTargetingUserSegmentsWithSiteLocale()
		throws Exception {

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, LocaleUtil.SPAIN);

		long contentTargetingUserSegmentId = -1L;

		Map<Locale, String> nameMap = HashMapBuilder.put(
			PortalUtil.getSiteDefaultLocale(_group),
			RandomTestUtil.randomString()
		).build();

		Map<Locale, String> descriptionMap =
			RandomTestUtil.randomLocaleStringMap();

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId, nameMap, descriptionMap);

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Assert.assertEquals(nameMap, segmentsEntry.getNameMap());
		Assert.assertEquals(descriptionMap, segmentsEntry.getDescriptionMap());
	}

	@Test
	public void testUpgradeContentTargetingUserSegmentsWithSiteMemberRule()
		throws Exception {

		long contentTargetingUserSegmentId = -1L;

		insertContentTargetingRuleInstance(
			contentTargetingUserSegmentId, "SiteMemberRule", "12345");

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Criteria criteriaObj = segmentsEntry.getCriteriaObj();

		Assert.assertNotNull(criteriaObj);

		Criteria.Criterion criterion = criteriaObj.getCriterion("user");

		Assert.assertNotNull(criterion);

		Assert.assertEquals(
			Criteria.Conjunction.AND,
			Criteria.Conjunction.parse(criterion.getConjunction()));
		Assert.assertEquals(
			"(groupIds eq '12345')", criterion.getFilterString());
	}

	@Test
	public void testUpgradeContentTargetingUserSegmentsWithUserGroupMemberRule()
		throws Exception {

		long contentTargetingUserSegmentId = -1L;

		insertContentTargetingRuleInstance(
			contentTargetingUserSegmentId, "UserGroupMemberRule", "12345");

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Criteria criteriaObj = segmentsEntry.getCriteriaObj();

		Assert.assertNotNull(criteriaObj);

		Criteria.Criterion criterion = criteriaObj.getCriterion("user");

		Assert.assertNotNull(criterion);

		Assert.assertEquals(
			Criteria.Conjunction.AND,
			Criteria.Conjunction.parse(criterion.getConjunction()));
		Assert.assertEquals(
			"(userGroupIds eq '12345')", criterion.getFilterString());
	}

	@Test
	public void testUpgradeContentTargetingUserSegmentsWithUserLoggedRule()
		throws Exception {

		long contentTargetingUserSegmentId = -1L;

		insertContentTargetingRuleInstance(
			contentTargetingUserSegmentId, "UserLoggedRule", StringPool.BLANK);

		insertContentTargetingUserSegment(
			contentTargetingUserSegmentId,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());

		_contentTargetingUpgradeProcess.upgrade();

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				_group.getGroupId(), "CT_" + contentTargetingUserSegmentId,
				false);

		Assert.assertNotNull(segmentsEntry);

		Criteria criteriaObj = segmentsEntry.getCriteriaObj();

		Assert.assertNotNull(criteriaObj);

		Criteria.Criterion criterion = criteriaObj.getCriterion("context");

		Assert.assertNotNull(criterion);

		Assert.assertEquals(
			Criteria.Conjunction.AND,
			Criteria.Conjunction.parse(criterion.getConjunction()));
		Assert.assertEquals("(signedIn eq true)", criterion.getFilterString());
	}

	protected void createContentTargetingTables()
		throws IOException, SQLException {

		_db.runSQL(
			StringBundler.concat(
				"create table CT_RuleInstance (uuid_ VARCHAR (75) null, ",
				"ruleInstanceId LONG not null primary key, groupId LONG, ",
				"companyId LONG, userId LONG, userName VARCHAR(75) null, ",
				"createDate DATE null, modifiedDate DATE null, userSegmentId ",
				"LONG, ruleKey VARCHAR(75) null, displayOrder INTEGER, ",
				"typeSettings TEXT null)"));

		_db.runSQL(
			StringBundler.concat(
				"create table CT_UserSegment (uuid_ VARCHAR (75) null, ",
				"userSegmentId LONG not null primary key, groupId LONG, ",
				"companyId LONG, userId LONG, userName VARCHAR(75) null, ",
				"createDate DATE null, modifiedDate DATE null, ",
				"lastPublishDate DATE null, name STRING null, description ",
				"STRING null)"));
	}

	protected void dropContentTargetingTables() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			if (dbInspector.hasTable("CT_RuleInstance")) {
				_db.runSQL("drop table CT_RuleInstance");
			}

			if (dbInspector.hasTable("CT_UserSegment")) {
				_db.runSQL("drop table CT_UserSegment");
			}
		}
	}

	protected void insertContentTargetingRuleInstance(
			long contentTargetingUserSegmentId, String ruleKey,
			String typeSettings)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into CT_RuleInstance(ruleInstanceId, groupId, ",
					"companyId, userId, userName, createDate, modifiedDate, ",
					"userSegmentId, ruleKey, typeSettings) values (?, ?, ?, ",
					"?, ?, ?, ?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, _counterLocalService.increment());
			preparedStatement.setLong(2, _group.getGroupId());
			preparedStatement.setLong(3, _group.getCompanyId());
			preparedStatement.setLong(4, TestPropsValues.getUserId());

			User user = TestPropsValues.getUser();

			preparedStatement.setString(5, user.getFullName());

			preparedStatement.setTimestamp(
				6, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setTimestamp(
				7, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setLong(8, contentTargetingUserSegmentId);
			preparedStatement.setString(9, ruleKey);
			preparedStatement.setString(10, typeSettings);

			preparedStatement.executeUpdate();
		}
	}

	protected void insertContentTargetingUserSegment(
			long contentTargetingUserSegmentId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into CT_UserSegment(userSegmentId, groupId, ",
					"companyId, userId, userName, createDate, modifiedDate, ",
					"name, description) values (?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, contentTargetingUserSegmentId);
			preparedStatement.setLong(2, _group.getGroupId());
			preparedStatement.setLong(3, _group.getCompanyId());
			preparedStatement.setLong(4, TestPropsValues.getUserId());

			User user = TestPropsValues.getUser();

			preparedStatement.setString(5, user.getFullName());

			preparedStatement.setTimestamp(
				6, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setTimestamp(
				7, new Timestamp(System.currentTimeMillis()));

			Locale defaultLocale = PortalUtil.getSiteDefaultLocale(_group);

			String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

			preparedStatement.setString(
				8,
				LocalizationUtil.updateLocalization(
					nameMap, StringPool.BLANK, "Name", defaultLanguageId));
			preparedStatement.setString(
				9,
				LocalizationUtil.updateLocalization(
					descriptionMap, StringPool.BLANK, "Description",
					defaultLanguageId));

			preparedStatement.executeUpdate();
		}
	}

	protected void setUpContentTargetingUpgradeProcess() {
		_upgradeStepRegistror.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						if (Objects.equals(clazz.getName(), _CLASS_NAME)) {
							_contentTargetingUpgradeProcess =
								(UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	private String _getCustomFieldFilterString(
		ExpandoColumn expandoColumn, String expandoValue) {

		return StringBundler.concat(
			"(customField/_", expandoColumn.getColumnId(), "_",
			Normalizer.normalizeIdentifier(expandoColumn.getName()), " eq '",
			expandoValue, "')");
	}

	private String _getCustomFieldRuleTypeSettings(
		ExpandoColumn expandoColumn, String expandoValue) {

		JSONObject jsonObject = JSONUtil.put(
			"attributeName", expandoColumn.getName()
		).put(
			"value", expandoValue
		);

		return jsonObject.toString();
	}

	private String _getDateRangeTypeSettings(
		ZonedDateTime startZonedDateTime, ZonedDateTime endZonedDateTime,
		String timeZoneId, String type) {

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd HH:mm", LocaleUtil.ENGLISH);

		JSONObject jsonObject = JSONUtil.put(
			"startDate",
			dateFormat.format(Date.from(startZonedDateTime.toInstant()))
		).put(
			"startDateTimeZoneId", timeZoneId
		).put(
			"type", type
		);

		if (type.equals("between")) {
			jsonObject.put(
				"endDate",
				dateFormat.format(Date.from(endZonedDateTime.toInstant()))
			).put(
				"endDateTimeZoneId", timeZoneId
			);
		}

		return jsonObject.toString();
	}

	private static final String _CLASS_NAME =
		"com.liferay.segments.content.targeting.upgrade.internal.v1_0_0." +
			"ContentTargetingUpgradeProcess";

	private static DB _db;

	private UpgradeProcess _contentTargetingUpgradeProcess;

	@Inject
	private CounterLocalService _counterLocalService;

	@DeleteAfterTestRun
	private ExpandoTable _expandoTable;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.segments.content.targeting.upgrade.internal.SegmentsContentTargetingUpgrade"
	)
	private UpgradeStepRegistrator _upgradeStepRegistror;

}
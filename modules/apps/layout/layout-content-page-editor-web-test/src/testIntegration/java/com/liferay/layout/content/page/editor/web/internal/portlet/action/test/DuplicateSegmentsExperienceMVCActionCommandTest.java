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

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Sarai Díaz
 */
@RunWith(Arquillian.class)
public class DuplicateSegmentsExperienceMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentPublishedLayout(
			_group, "Test layout", WorkflowConstants.STATUS_APPROVED);

		_draftLayout = _layout.fetchDraftLayout();

		ServiceContextThreadLocal.pushServiceContext(new ServiceContext());
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testDuplicateSegmentsExperience() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceService.addSegmentsExperience(
				_group.getGroupId(), segmentsEntry.getSegmentsEntryId(),
				_classNameLocalService.getClassNameId(Layout.class.getName()),
				_layout.getPlid(),
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), "Experience"),
				true, new UnicodeProperties(true),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_addSegmentsExperienceData(
			_draftLayout, _layoutPageTemplateStructureLocalService,
			_layoutPageTemplatesImporter,
			_read("fragment_composition_with_a_card.json"),
			segmentsExperience.getSegmentsExperienceId());

		_addSegmentsExperienceData(
			_layout, _layoutPageTemplateStructureLocalService,
			_layoutPageTemplatesImporter,
			_read("fragment_composition_with_a_button.json"),
			segmentsExperience.getSegmentsExperienceId());

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			"segmentsExperienceId",
			String.valueOf(segmentsExperience.getSegmentsExperienceId()));

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "doTransactionalCommand",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		JSONObject segmentsExperienceJSONObject = jsonObject.getJSONObject(
			"segmentsExperience");

		Assert.assertEquals(
			segmentsExperience.isActive(),
			segmentsExperienceJSONObject.getBoolean("active"));
		Assert.assertEquals(
			"Copy of Experience",
			segmentsExperienceJSONObject.getString("name"));
		Assert.assertEquals(
			segmentsExperience.getPriority() - 1,
			segmentsExperienceJSONObject.getInt("priority"));
		Assert.assertEquals(
			segmentsExperience.getSegmentsEntryId(),
			segmentsExperienceJSONObject.getLong("segmentsEntryId"));
		Assert.assertTrue(
			segmentsExperienceJSONObject.getLong("segmentsExperienceId") > 0);

		_assertContentEquals(
			_draftLayout, segmentsExperience.getSegmentsExperienceId(),
			segmentsExperienceJSONObject.getLong("segmentsExperienceId"));

		_assertContentEquals(
			_layout, segmentsExperience.getSegmentsExperienceId(),
			segmentsExperienceJSONObject.getLong("segmentsExperienceId"));
	}

	private void _addSegmentsExperienceData(
			Layout layout,
			LayoutPageTemplateStructureLocalService
				layoutPageTemplateStructureLocalService,
			LayoutPageTemplatesImporter layoutPageTemplatesImporter,
			String pageElementJSON, long segmentsExperienceId)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		String segmentsExperienceData =
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData();

		LayoutStructure layoutStructure = LayoutStructure.of(
			segmentsExperienceData);

		layoutPageTemplatesImporter.importPageElement(
			layout, layoutStructure, layoutStructure.getMainItemId(),
			pageElementJSON, 0, segmentsExperienceId);
	}

	private void _assertContentEquals(
		Layout layout, long sourceSegmentsExperienceId,
		long targetSegmentsExperienceId) {

		List<FragmentEntryLink> expectedFragmentEntryLinks =
			_fragmentEntryLinkLocalService.
				getFragmentEntryLinksBySegmentsExperienceId(
					layout.getGroupId(), sourceSegmentsExperienceId,
					layout.getPlid());

		List<FragmentEntryLink> actualFragmentEntryLinks =
			_fragmentEntryLinkLocalService.
				getFragmentEntryLinksBySegmentsExperienceId(
					layout.getGroupId(), targetSegmentsExperienceId,
					layout.getPlid());

		Assert.assertEquals(
			expectedFragmentEntryLinks.toString(), 1,
			actualFragmentEntryLinks.size());

		Assert.assertEquals(
			expectedFragmentEntryLinks.toString(), 1,
			actualFragmentEntryLinks.size());

		FragmentEntryLink expectedFragmentEntryLink =
			expectedFragmentEntryLinks.get(0);

		FragmentEntryLink actualFragmentEntryLink =
			actualFragmentEntryLinks.get(0);

		Assert.assertEquals(
			expectedFragmentEntryLink.getCss(),
			actualFragmentEntryLink.getCss());
		Assert.assertEquals(
			expectedFragmentEntryLink.getHtml(),
			actualFragmentEntryLink.getHtml());
		Assert.assertEquals(
			expectedFragmentEntryLink.getJs(), actualFragmentEntryLink.getJs());
		Assert.assertEquals(
			expectedFragmentEntryLink.getConfiguration(),
			actualFragmentEntryLink.getConfiguration());
		Assert.assertEquals(
			expectedFragmentEntryLink.getEditableValues(),
			actualFragmentEntryLink.getEditableValues());
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
		ThemeDisplay themeDisplay) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private MockLiferayPortletActionRequest
			_getMockLiferayPortletActionRequest()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setLayout(_draftLayout);
		themeDisplay.setLayoutSet(_draftLayout.getLayoutSet());
		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setLookAndFeel(
			_draftLayout.getTheme(), _draftLayout.getColorScheme());
		themeDisplay.setPlid(_draftLayout.getPlid());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(_getMockHttpServletRequest(themeDisplay));
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	private Layout _draftLayout;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/duplicate_segments_experience"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private SegmentsExperienceService _segmentsExperienceService;

}
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

package com.liferay.layout.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.constants.LayoutPortletKeys;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;

import java.util.List;

import javax.imageio.ImageIO;

import javax.portlet.Portlet;
import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
public class LayoutCopyHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@Test
	public void testCopyAssetCategoryIdsAndAssetTagNames() throws Exception {
		Layout sourceLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), StringPool.BLANK);

		Layout targetLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), StringPool.BLANK);

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		AssetTag assetTag = AssetTestUtil.addTag(_group.getGroupId());

		_layoutLocalService.updateAsset(
			sourceLayout.getUserId(), sourceLayout,
			new long[] {assetCategory.getCategoryId()},
			new String[] {assetTag.getName()});

		_layoutCopyHelper.copyLayout(sourceLayout, targetLayout);

		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(
				Layout.class.getName(), targetLayout.getPlid());

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			Layout.class.getName(), targetLayout.getPlid());

		Assert.assertEquals(assetCategory, assetCategories.get(0));
		Assert.assertEquals(assetTag, assetTags.get(0));
	}

	@Test
	public void testCopyContentLayoutStructure() throws Exception {
		Layout sourceLayout = _addContentLayout();

		LayoutStructure layoutStructure = new LayoutStructure();

		layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem containerLayoutStructureItem =
			layoutStructure.addContainerStyledLayoutStructureItem(
				layoutStructure.getMainItemId(), 0);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				sourceLayout.getUserId(), sourceLayout.getGroupId(), 0, 0, 0,
				sourceLayout.getPlid(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, 0, null, _serviceContext);

		layoutStructure.addFragmentStyledLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(),
			containerLayoutStructureItem.getItemId(), 0);

		fragmentEntryLink = _fragmentEntryLinkLocalService.addFragmentEntryLink(
			sourceLayout.getUserId(), sourceLayout.getGroupId(), 0, 0, 0,
			sourceLayout.getPlid(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, 0, null, _serviceContext);

		layoutStructure.addFragmentStyledLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(),
			containerLayoutStructureItem.getItemId(), 0);

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			sourceLayout.getUserId(), sourceLayout.getGroupId(),
			sourceLayout.getPlid(), layoutStructure.toString(),
			_serviceContext);

		Layout targetLayout = _addContentLayout();

		Assert.assertTrue(
			ListUtil.isNotEmpty(
				_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
					_group.getGroupId(), sourceLayout.getPlid())));

		Assert.assertFalse(
			ListUtil.isNotEmpty(
				_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
					_group.getGroupId(), targetLayout.getPlid())));

		_layoutCopyHelper.copyLayout(sourceLayout, targetLayout);

		Assert.assertNotNull(
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					targetLayout.getGroupId(), targetLayout.getPlid()));

		Assert.assertTrue(
			ListUtil.isNotEmpty(
				_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
					_group.getGroupId(), targetLayout.getPlid())));
	}

	@Test
	public void testCopyLayoutIcon() throws Exception {
		Layout sourceLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), StringPool.BLANK);

		BufferedImage bufferedImage = new BufferedImage(
			1, 1, BufferedImage.TYPE_INT_RGB);

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);

		byteArrayOutputStream.flush();

		sourceLayout = LayoutLocalServiceUtil.updateIconImage(
			sourceLayout.getPlid(), byteArrayOutputStream.toByteArray());

		Layout targetLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), StringPool.BLANK);

		Assert.assertTrue(sourceLayout.isIconImage());
		Assert.assertFalse(targetLayout.isIconImage());

		Assert.assertNotEquals(
			sourceLayout.getIconImageId(), targetLayout.getIconImageId());

		targetLayout = _layoutCopyHelper.copyLayout(sourceLayout, targetLayout);

		Assert.assertTrue(sourceLayout.isIconImage());
		Assert.assertTrue(targetLayout.isIconImage());

		Image sourceImage = _imageLocalService.getImage(
			sourceLayout.getIconImageId());

		Image targetImage = _imageLocalService.getImage(
			sourceLayout.getIconImageId());

		Assert.assertNotEquals(
			sourceImage.getTextObj(), targetImage.getTextObj());
	}

	@Test
	public void testCopyLayoutLookAndFeel() throws Exception {
		Layout sourceLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), StringPool.BLANK);

		sourceLayout.setThemeId("l1-theme");
		sourceLayout.setCss("l1-css");

		LayoutLocalServiceUtil.updateLayout(sourceLayout);

		Layout targetLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), StringPool.BLANK);

		Assert.assertNotEquals(
			sourceLayout.getThemeId(), targetLayout.getThemeId());

		Assert.assertNotEquals(sourceLayout.getCss(), targetLayout.getCss());

		targetLayout = _layoutCopyHelper.copyLayout(sourceLayout, targetLayout);

		Assert.assertEquals(
			sourceLayout.getThemeId(), targetLayout.getThemeId());

		Assert.assertEquals(sourceLayout.getCss(), targetLayout.getCss());
	}

	@Test
	public void testCopyLayoutPortletPreferences() throws Exception {
		String portletId = LayoutPortletKeys.LAYOUT_TEST_PORTLET;

		Layout sourceLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), "column-1=" + portletId);

		PortletPreferences sourcePortletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				sourceLayout, portletId,
				"<portlet-preferences><preference><name>layout</name><value>1" +
					"</value></preference></portlet-preferences>");

		Layout targetLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), StringPool.BLANK);

		PortletPreferences targetPortletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				targetLayout, portletId);

		Assert.assertNotEquals(
			PortletPreferencesFactoryUtil.toXML(targetPortletPreferences),
			PortletPreferencesFactoryUtil.toXML(sourcePortletPreferences));

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_layoutCopyHelper.copyLayout(sourceLayout, targetLayout);

		targetPortletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				targetLayout, portletId);

		Assert.assertEquals(
			PortletPreferencesFactoryUtil.toXML(sourcePortletPreferences),
			PortletPreferencesFactoryUtil.toXML(targetPortletPreferences));
	}

	@Test
	public void testCopyTypeSettings() throws Exception {
		UnicodeProperties sourceUnicodeProperties = new UnicodeProperties();

		sourceUnicodeProperties.setProperty(
			"lfr-theme:regular:show-footer", Boolean.TRUE.toString());
		sourceUnicodeProperties.setProperty(
			"lfr-theme:regular:show-header", Boolean.TRUE.toString());

		Layout sourceLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), sourceUnicodeProperties.toString());

		Layout targetLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), StringPool.BLANK);

		UnicodeProperties targetUnicodeProperties = new UnicodeProperties();

		targetUnicodeProperties.fastLoad(targetLayout.getTypeSettings());

		Assert.assertNull(
			targetUnicodeProperties.getProperty(
				"lfr-theme:regular:show-footer"));
		Assert.assertNull(
			targetUnicodeProperties.getProperty(
				"lfr-theme:regular:show-header"));

		_layoutCopyHelper.copyLayout(sourceLayout, targetLayout);

		targetLayout = _layoutLocalService.fetchLayout(targetLayout.getPlid());

		targetUnicodeProperties.fastLoad(targetLayout.getTypeSettings());

		Assert.assertEquals(
			Boolean.TRUE.toString(),
			targetUnicodeProperties.getProperty(
				"lfr-theme:regular:show-footer"));
		Assert.assertEquals(
			Boolean.TRUE.toString(),
			targetUnicodeProperties.getProperty(
				"lfr-theme:regular:show-header"));
	}

	private Layout _addContentLayout() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		return _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK, serviceContext);
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ImageLocalService _imageLocalService;

	@Inject
	private LayoutCopyHelper _layoutCopyHelper;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "javax.portlet.name=" + LayoutPortletKeys.LAYOUT_TEST_PORTLET
	)
	private final Portlet _portlet = null;

	private ServiceContext _serviceContext;

}
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

package com.liferay.asset.info.collection.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.pagination.InfoPage;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
public class AssetInfoCollectionProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_httpServletRequest = _getHttpServletRequest();

		serviceContext.setRequest(_httpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testHighestRatedAssetsInfoCollectionProvider()
		throws Exception {

		JournalArticle article1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		JournalArticle article2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		_ratingsEntryLocalService.updateEntry(
			TestPropsValues.getUserId(), JournalArticle.class.getName(),
			article2.getResourcePrimKey(), 1, serviceContext);

		InfoCollectionProvider<AssetEntry> infoCollectionProvider =
			_infoItemServiceTracker.getInfoItemService(
				InfoCollectionProvider.class,
				"com.liferay.asset.internal.info.collection.provider." +
					"HighestRatedAssetsInfoCollectionProvider");

		InfoPage<AssetEntry> assetEntriesInfoPage =
			infoCollectionProvider.getCollectionInfoPage(new CollectionQuery());

		Assert.assertEquals(2, assetEntriesInfoPage.getTotalCount());

		List<AssetEntry> assetEntries =
			(List<AssetEntry>)assetEntriesInfoPage.getPageItems();

		Assert.assertEquals(
			Long.valueOf(article2.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(0)));

		Assert.assertEquals(
			Long.valueOf(article1.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(1)));

		_ratingsEntryLocalService.deleteEntry(
			TestPropsValues.getUserId(), JournalArticle.class.getName(),
			article2.getResourcePrimKey());

		_ratingsEntryLocalService.updateEntry(
			TestPropsValues.getUserId(), JournalArticle.class.getName(),
			article1.getResourcePrimKey(), 1, serviceContext);

		assetEntriesInfoPage = infoCollectionProvider.getCollectionInfoPage(
			new CollectionQuery());

		assetEntries = (List<AssetEntry>)assetEntriesInfoPage.getPageItems();

		Assert.assertEquals(
			Long.valueOf(article1.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(0)));
		Assert.assertEquals(
			Long.valueOf(article2.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(1)));
	}

	@Test
	public void testMostViewedAssetsInfoCollectionProvider() throws Exception {
		JournalArticle article1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JournalArticle article2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_assetEntryLocalService.incrementViewCounter(
			_group.getCompanyId(), TestPropsValues.getUserId(),
			JournalArticle.class.getName(), article2.getResourcePrimKey());

		InfoCollectionProvider<AssetEntry> infoCollectionProvider =
			_infoItemServiceTracker.getInfoItemService(
				InfoCollectionProvider.class,
				"com.liferay.asset.internal.info.collection.provider." +
					"MostViewedAssetsInfoCollectionProvider");

		InfoPage<AssetEntry> assetEntriesInfoPage =
			infoCollectionProvider.getCollectionInfoPage(new CollectionQuery());

		Assert.assertEquals(2, assetEntriesInfoPage.getTotalCount());

		List<AssetEntry> assetEntries =
			(List<AssetEntry>)assetEntriesInfoPage.getPageItems();

		Assert.assertEquals(
			Long.valueOf(article2.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(0)));
		Assert.assertEquals(
			Long.valueOf(article1.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(1)));

		_assetEntryLocalService.incrementViewCounter(
			_group.getCompanyId(), TestPropsValues.getUserId(),
			JournalArticle.class.getName(), article1.getResourcePrimKey(), 2);

		assetEntriesInfoPage = infoCollectionProvider.getCollectionInfoPage(
			new CollectionQuery());

		assetEntries = (List<AssetEntry>)assetEntriesInfoPage.getPageItems();

		Assert.assertEquals(
			Long.valueOf(article1.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(0)));
		Assert.assertEquals(
			Long.valueOf(article2.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(1)));
	}

	@Test
	public void testRecentContentInfoCollectionProvider() throws Exception {
		JournalArticle article1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		JournalArticle article2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		InfoCollectionProvider<AssetEntry> infoCollectionProvider =
			_infoItemServiceTracker.getInfoItemService(
				InfoCollectionProvider.class,
				"com.liferay.asset.internal.info.collection.provider." +
					"RecentContentInfoCollectionProvider");

		InfoPage<AssetEntry> assetEntriesInfoPage =
			infoCollectionProvider.getCollectionInfoPage(new CollectionQuery());

		Assert.assertEquals(2, assetEntriesInfoPage.getTotalCount());

		List<AssetEntry> assetEntries =
			(List<AssetEntry>)assetEntriesInfoPage.getPageItems();

		Assert.assertEquals(
			Long.valueOf(article2.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(0)));
		Assert.assertEquals(
			Long.valueOf(article1.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(1)));

		JournalTestUtil.updateArticle(article1, StringUtil.randomString());

		assetEntriesInfoPage = infoCollectionProvider.getCollectionInfoPage(
			new CollectionQuery());

		assetEntries = (List<AssetEntry>)assetEntriesInfoPage.getPageItems();

		Assert.assertEquals(
			Long.valueOf(article1.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(0)));
		Assert.assertEquals(
			Long.valueOf(article2.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(1)));
	}

	@Test
	public void testRelatedAssetsInfoCollectionProvider() throws Exception {
		JournalArticle article1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		JournalArticle article2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		JournalArticle article3 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry1 = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(), article1.getResourcePrimKey());

		AssetEntry assetEntry2 = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(), article2.getResourcePrimKey());
		AssetEntry relatedAssetEntry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(), article3.getResourcePrimKey());

		_assetLinkLocalService.addLink(
			TestPropsValues.getUserId(), assetEntry2.getEntryId(),
			relatedAssetEntry.getEntryId(), AssetLinkConstants.TYPE_RELATED, 1);

		_httpServletRequest.setAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY, assetEntry1);

		InfoCollectionProvider<AssetEntry> infoCollectionProvider =
			_infoItemServiceTracker.getInfoItemService(
				InfoCollectionProvider.class,
				"com.liferay.asset.internal.info.collection.provider." +
					"RelatedAssetsInfoCollectionProvider");

		InfoPage<AssetEntry> assetEntriesInfoPage =
			infoCollectionProvider.getCollectionInfoPage(new CollectionQuery());

		Assert.assertEquals(0, assetEntriesInfoPage.getTotalCount());

		List<AssetEntry> assetEntries =
			(List<AssetEntry>)assetEntriesInfoPage.getPageItems();

		Assert.assertTrue(ListUtil.isEmpty(assetEntries));

		_httpServletRequest.setAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY, assetEntry2);

		assetEntriesInfoPage = infoCollectionProvider.getCollectionInfoPage(
			new CollectionQuery());

		Assert.assertEquals(1, assetEntriesInfoPage.getTotalCount());

		assetEntries = (List<AssetEntry>)assetEntriesInfoPage.getPageItems();

		Assert.assertEquals(
			Long.valueOf(article3.getResourcePrimKey()),
			_CLASS_PK_ACCESSOR.get(assetEntries.get(0)));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private HttpServletRequest _getHttpServletRequest() throws Exception {
		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletRenderResponse());

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setResponse(new MockHttpServletResponse());
		themeDisplay.setUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_group.getGroupId());

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		httpServletRequest.setMethod(HttpMethods.GET);

		return httpServletRequest;
	}

	private static final Accessor<AssetEntry, Long> _CLASS_PK_ACCESSOR =
		new Accessor<AssetEntry, Long>() {

			@Override
			public Long get(AssetEntry assetEntry) {
				return assetEntry.getClassPK();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetEntry> getTypeClass() {
				return AssetEntry.class;
			}

		};

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private AssetLinkLocalService _assetLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private HttpServletRequest _httpServletRequest;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Inject
	private RatingsEntryLocalService _ratingsEntryLocalService;

}
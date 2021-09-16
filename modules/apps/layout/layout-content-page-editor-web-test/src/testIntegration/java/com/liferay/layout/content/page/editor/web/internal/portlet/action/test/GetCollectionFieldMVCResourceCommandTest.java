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
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.pagination.InfoPage;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
@Sync
public class GetCollectionFieldMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = new ServiceContext();

		_serviceContext.setScopeGroupId(_group.getGroupId());
		_serviceContext.setUserId(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		Bundle bundle = FrameworkUtil.getBundle(
			GetCollectionFieldMVCResourceCommandTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_infoCollectionProviderServiceRegistration =
			bundleContext.registerService(
				(Class<InfoCollectionProvider<?>>)
					(Class<?>)InfoCollectionProvider.class,
				new TestInfoCollectionProvider(), null);

		_originalThemeDisplayDefaultLocale =
			LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();

		if (_infoCollectionProviderServiceRegistration != null) {
			_infoCollectionProviderServiceRegistration.unregister();
		}

		LocaleThreadLocal.setThemeDisplayLocale(
			_originalThemeDisplayDefaultLocale);
	}

	@Test
	public void testGetCollectionFieldFromCollectionProvider()
		throws Exception {

		BlogsEntry blogsEntry = _addBlogsEntry();

		JSONObject layoutObjectReferenceJSONObject = JSONUtil.put(
			"itemType", BlogsEntry.class.getName()
		).put(
			"key", TestInfoCollectionProvider.class.getName()
		).put(
			"type", InfoListProviderItemSelectorReturnType.class.getName()
		);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getCollectionFieldsJSONObject",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class, int.class,
				String.class, String.class, String.class, String.class,
				String.class, int.class, int.class, String.class, String.class
			},
			new MockHttpServletRequest(), new MockHttpServletResponse(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.US),
			layoutObjectReferenceJSONObject.toString(), StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, 1, 20, "regular",
			StringPool.BLANK);

		Assert.assertEquals(1, jsonObject.getInt("length"));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, jsonArray.length());

		JSONObject itemJSONObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			blogsEntry.getTitle(), itemJSONObject.getString("title"));
	}

	@Test
	public void testGetCollectionFieldFromDynamicCollection() throws Exception {
		BlogsEntry blogsEntry1 = _addBlogsEntry();

		BlogsEntry blogsEntry2 = _addBlogsEntry();

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addDynamicAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Collection Title", _getTypeSettings(), _serviceContext);

		JSONObject layoutObjectReferenceJSONObject = JSONUtil.put(
			"classNameId",
			String.valueOf(
				_portal.getClassNameId(AssetListEntry.class.getName()))
		).put(
			"classPK", String.valueOf(assetListEntry.getAssetListEntryId())
		).put(
			"itemType", BlogsEntry.class.getName()
		).put(
			"type", InfoListItemSelectorReturnType.class.getName()
		);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getCollectionFieldsJSONObject",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class, int.class,
				String.class, String.class, String.class, String.class,
				String.class, int.class, int.class, String.class, String.class
			},
			new MockHttpServletRequest(), new MockHttpServletResponse(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.US),
			layoutObjectReferenceJSONObject.toString(), StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, 2, 20, "regular",
			StringPool.BLANK);

		Assert.assertEquals(2, jsonObject.getInt("length"));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(2, jsonArray.length());

		JSONObject itemJSONObject1 = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			blogsEntry1.getTitle(), itemJSONObject1.getString("title"));

		JSONObject itemJSONObject2 = jsonArray.getJSONObject(1);

		Assert.assertEquals(
			blogsEntry2.getTitle(), itemJSONObject2.getString("title"));
	}

	@Test
	public void testGetCollectionFieldFromDynamicCollectionWithSize()
		throws Exception {

		BlogsEntry blogsEntry = _addBlogsEntry();

		_addBlogsEntry();

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addDynamicAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Collection Title", _getTypeSettings(), _serviceContext);

		JSONObject layoutObjectReferenceJSONObject = JSONUtil.put(
			"classNameId",
			String.valueOf(
				_portal.getClassNameId(AssetListEntry.class.getName()))
		).put(
			"classPK", String.valueOf(assetListEntry.getAssetListEntryId())
		).put(
			"itemType", BlogsEntry.class.getName()
		).put(
			"type", InfoListItemSelectorReturnType.class.getName()
		);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getCollectionFieldsJSONObject",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class, int.class,
				String.class, String.class, String.class, String.class,
				String.class, int.class, int.class, String.class, String.class
			},
			new MockHttpServletRequest(), new MockHttpServletResponse(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.US),
			layoutObjectReferenceJSONObject.toString(), StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, 1, 20, "regular",
			StringPool.BLANK);

		Assert.assertEquals(2, jsonObject.getInt("length"));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, jsonArray.length());

		JSONObject itemJSONObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			blogsEntry.getTitle(), itemJSONObject.getString("title"));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private BlogsEntry _addBlogsEntry() throws Exception {
		return _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _serviceContext);
	}

	private String _getTypeSettings() {
		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		unicodeProperties.put(
			"anyAssetType",
			String.valueOf(_portal.getClassNameId(BlogsEntry.class)));
		unicodeProperties.put("classNameIds", BlogsEntry.class.getName());
		unicodeProperties.put("groupIds", String.valueOf(_group.getGroupId()));
		unicodeProperties.put("orderByColumn1", "modifiedDate");
		unicodeProperties.put("orderByColumn2", "title");
		unicodeProperties.put("orderByType1", "ASC");
		unicodeProperties.put("orderByType2", "ASC");

		return unicodeProperties.toString();
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceRegistration<InfoCollectionProvider<?>>
		_infoCollectionProviderServiceRegistration;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/get_collection_field"
	)
	private MVCResourceCommand _mvcResourceCommand;

	private Locale _originalThemeDisplayDefaultLocale;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	private class TestInfoCollectionProvider
		implements InfoCollectionProvider<BlogsEntry> {

		@Override
		public InfoPage<BlogsEntry> getCollectionInfoPage(
			CollectionQuery collectionQuery) {

			return InfoPage.of(
				_blogsEntryLocalService.getGroupEntries(
					_group.getGroupId(), _queryDefinition),
				collectionQuery.getPagination(),
				_blogsEntryLocalService.getGroupEntriesCount(
					_group.getGroupId(), _queryDefinition));
		}

		@Override
		public String getLabel(Locale locale) {
			return TestInfoCollectionProvider.class.getSimpleName();
		}

		private final QueryDefinition<BlogsEntry> _queryDefinition =
			new QueryDefinition<>(
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

	}

}
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

package com.liferay.content.dashboard.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.type.WebImage;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.BrowserSnifferImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.PortalImpl;

import java.io.ByteArrayOutputStream;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class GetContentDashboardItemInfoMVCResourceCommandTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		BrowserSnifferUtil browserSnifferUtil = new BrowserSnifferUtil();

		browserSnifferUtil.setBrowserSniffer(new BrowserSnifferImpl());

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testServeResource() throws Exception {
		ContentDashboardItem contentDashboardItem = _getContentDashboardItem(
			"assetCategory", "assetTag", "className", 12345L);

		_initGetContentDashboardItemInfoMVCResourceCommand(
			contentDashboardItem);

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(WebKeys.LOCALE, LocaleUtil.US);

		InfoItemReference infoItemReference =
			contentDashboardItem.getInfoItemReference();

		mockLiferayResourceRequest.addParameter(
			"className", infoItemReference.getClassName());
		mockLiferayResourceRequest.addParameter(
			"classPK", String.valueOf(infoItemReference.getClassPK()));

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, new ThemeDisplay());

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_getContentDashboardItemInfoMVCResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));

		JSONArray categoriesJSONArray = jsonObject.getJSONArray("categories");

		Assert.assertEquals(
			JSONUtil.put(
				"assetCategory"
			).toString(),
			categoriesJSONArray.toString());

		Assert.assertEquals(
			infoItemReference.getClassName(),
			jsonObject.getString("className"));
		Assert.assertEquals(
			infoItemReference.getClassPK(), jsonObject.getLong("classPK"), 0);

		JSONArray tagsJSONArray = jsonObject.getJSONArray("tags");

		Assert.assertEquals(
			JSONUtil.put(
				"assetTag"
			).toString(),
			tagsJSONArray.toString());

		Assert.assertEquals(
			contentDashboardItem.getTitle(LocaleUtil.US),
			jsonObject.getString("title"));

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			contentDashboardItem.getContentDashboardItemSubtype();

		Assert.assertEquals(
			contentDashboardItemSubtype.getLabel(LocaleUtil.US),
			jsonObject.getString("subType"));

		Portal portal = PortalUtil.getPortal();

		JSONObject getSpecificInformationJSONObject =
			contentDashboardItem.getSpecificInformationJSONObject(
				"backURL",
				portal.getLiferayPortletResponse(mockLiferayResourceResponse),
				LocaleUtil.US,
				(ThemeDisplay)mockLiferayResourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY));

		Assert.assertEquals(
			getSpecificInformationJSONObject.toString(),
			jsonObject.getJSONObject(
				"specificFields"
			).toString());

		List<ContentDashboardItem.Version> versions =
			contentDashboardItem.getVersions(LocaleUtil.US);

		ContentDashboardItem.Version version = versions.get(0);

		JSONObject expectedVersionJSONObject = version.toJSONObject();

		JSONArray versionsJSONArray = jsonObject.getJSONArray("versions");

		JSONObject actualVersionJSONObject = versionsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			expectedVersionJSONObject.toString(),
			actualVersionJSONObject.toString());
	}

	private ContentDashboardItem _getContentDashboardItem(
		String assetCategoryTitle, String assetTagName, String className,
		long classPK) {

		return new ContentDashboardItem() {

			@Override
			public List<AssetCategory> getAssetCategories() {
				AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

				Mockito.when(
					assetCategory.getTitle(Mockito.any(Locale.class))
				).thenReturn(
					assetCategoryTitle
				);

				return Collections.singletonList(assetCategory);
			}

			@Override
			public List<AssetCategory> getAssetCategories(long vocabularyId) {
				return Collections.emptyList();
			}

			@Override
			public List<AssetTag> getAssetTags() {
				AssetTag assetCategory = Mockito.mock(AssetTag.class);

				Mockito.when(
					assetCategory.getName()
				).thenReturn(
					assetTagName
				);

				return Collections.singletonList(assetCategory);
			}

			@Override
			public List<Locale> getAvailableLocales() {
				return Collections.singletonList(LocaleUtil.US);
			}

			@Override
			public List<ContentDashboardItemAction>
				getContentDashboardItemActions(
					HttpServletRequest httpServletRequest,
					ContentDashboardItemAction.Type... types) {

				return Collections.emptyList();
			}

			@Override
			public ContentDashboardItemSubtype
				getContentDashboardItemSubtype() {

				ContentDashboardItemSubtype contentDashboardItemSubtype =
					Mockito.mock(ContentDashboardItemSubtype.class);

				Mockito.when(
					contentDashboardItemSubtype.getLabel(
						Mockito.any(Locale.class))
				).thenReturn(
					"subType"
				);

				return contentDashboardItemSubtype;
			}

			@Override
			public Date getCreateDate() {
				return new Date();
			}

			@Override
			public Map<String, Object> getData(Locale locale) {
				return Collections.emptyMap();
			}

			@Override
			public ContentDashboardItemAction
				getDefaultContentDashboardItemAction(
					HttpServletRequest httpServletRequest) {

				return null;
			}

			@Override
			public Locale getDefaultLocale() {
				return LocaleUtil.US;
			}

			@Override
			public String getDescription(Locale locale) {
				return "Web Content description";
			}

			@Override
			public Object getDisplayFieldValue(
				String fieldName, Locale locale) {

				if (Objects.equals(fieldName, "authorProfileImage")) {
					return new WebImage("url");
				}

				return null;
			}

			@Override
			public InfoItemReference getInfoItemReference() {
				return new InfoItemReference(className, classPK);
			}

			@Override
			public Date getModifiedDate() {
				return new Date();
			}

			@Override
			public String getScopeName(Locale locale) {
				return RandomTestUtil.randomString();
			}

			@Override
			public JSONObject getSpecificInformationJSONObject(
				String backURL, LiferayPortletResponse liferayPortletResponse,
				Locale locale, ThemeDisplay themeDisplay) {

				JSONObject jsonObject = new JSONObjectImpl();

				jsonObject.put(
					"description", "My very important description"
				).put(
					"downloadURL", "www.download.url.com/download"
				).put(
					"extension", ".pdf"
				).put(
					"fileName", "MyDocument"
				).put(
					"previewImageURL", "www.previewImage.url.com/previewImage"
				).put(
					"previewURL", "www.previewURL.url.com/previewURL"
				).put(
					"size", "5"
				).put(
					"viewURL", "www.viewURL.url.com/viewURL"
				);

				return jsonObject;
			}

			@Override
			public String getTitle(Locale locale) {
				return "title";
			}

			@Override
			public String getTypeLabel(Locale locale) {
				return "Web Content";
			}

			@Override
			public long getUserId() {
				return 0;
			}

			@Override
			public String getUserName() {
				return RandomTestUtil.randomString();
			}

			@Override
			public List<Version> getVersions(Locale locale) {
				return Collections.singletonList(
					new Version("version", "style", "0.1"));
			}

			@Override
			public boolean isViewable(HttpServletRequest httpServletRequest) {
				return true;
			}

		};
	}

	private void _initGetContentDashboardItemInfoMVCResourceCommand(
		ContentDashboardItem contentDashboardItem) {

		_getContentDashboardItemInfoMVCResourceCommand =
			new GetContentDashboardItemInfoMVCResourceCommand();

		ReflectionTestUtil.setFieldValue(
			_getContentDashboardItemInfoMVCResourceCommand,
			"_contentDashboardItemFactoryTracker",
			new ContentDashboardItemFactoryTracker() {

				public Optional<ContentDashboardItemFactory<?>>
					getContentDashboardItemFactoryOptional(String className) {

					return Optional.ofNullable(
						new ContentDashboardItemFactory() {

							@Override
							public ContentDashboardItem create(long classPK) {
								InfoItemReference infoItemReference =
									contentDashboardItem.getInfoItemReference();

								if (Objects.equals(
										className,
										infoItemReference.getClassName()) &&
									Objects.equals(
										classPK,
										infoItemReference.getClassPK())) {

									return contentDashboardItem;
								}

								return null;
							}

							@Override
							public Optional<ContentDashboardItemSubtypeFactory>
								getContentDashboardItemSubtypeFactoryOptional() {

								return Optional.empty();
							}

						});
				}

			});

		ReflectionTestUtil.setFieldValue(
			_getContentDashboardItemInfoMVCResourceCommand, "_http",
			new HttpImpl());

		ReflectionTestUtil.setFieldValue(
			_getContentDashboardItemInfoMVCResourceCommand, "_portal",
			new PortalImpl());

		ReflectionTestUtil.setFieldValue(
			_getContentDashboardItemInfoMVCResourceCommand, "_userLocalService",
			Mockito.mock(UserLocalService.class));
	}

	private GetContentDashboardItemInfoMVCResourceCommand
		_getContentDashboardItemInfoMVCResourceCommand;

}
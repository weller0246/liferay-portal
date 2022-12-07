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

package com.liferay.fragment.entry.processor.drop.zone;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.layout.constants.LayoutWebKeys;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.FragmentDropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.uuid.PortalUUIDImpl;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class DropZoneFragmentEntryProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpDropZoneFragmentEntryProcessor();

		_setUpPortalUUIDUtil();

		_setUpPropsUtil();
	}

	@Test
	public void testProcessFragmentEntryLinkHTMLInEditMode() throws Exception {
		FragmentEntryLink fragmentEntryLink = _getMockFragmentEntryLink();

		LayoutStructure layoutStructure = new LayoutStructure();

		FragmentDropZoneLayoutStructureItem
			fragmentDropZoneLayoutStructureItem =
				_addFragmentDropZoneLayoutStructureItem(
					fragmentEntryLink, layoutStructure);

		String expectedHTML = StringBundler.concat(
			"<div class=\"fragment_1\"><lfr-drop-zone uuid=\"",
			fragmentDropZoneLayoutStructureItem.getItemId(),
			"\"></lfr-drop-zone></div>");

		Assert.assertEquals(
			expectedHTML,
			_dropZoneFragmentEntryProcessor.processFragmentEntryLinkHTML(
				fragmentEntryLink, _getHTML(StringPool.BLANK),
				new DefaultFragmentEntryProcessorContext(
					_getMockHttpServletRequest(layoutStructure), null,
					FragmentEntryLinkConstants.EDIT,
					LocaleUtil.getMostRelevantLocale())));
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testValidateFragmentEntryHTMLDuplicatedId() throws Exception {
		_setFeatureFlag(true);

		String dropZoneId = RandomTestUtil.randomString();

		_dropZoneFragmentEntryProcessor.validateFragmentEntryHTML(
			_getHTML(dropZoneId, dropZoneId), null);
	}

	@Test(expected = FragmentEntryContentException.class)
	public void testValidateFragmentEntryHTMLMissingId() throws Exception {
		_setFeatureFlag(true);

		_dropZoneFragmentEntryProcessor.validateFragmentEntryHTML(
			_getHTML(StringPool.BLANK, RandomTestUtil.randomString()), null);
	}

	@Test
	public void testValidateFragmentEntryHTMLNoValidationWhenFFDisabled()
		throws Exception {

		_setFeatureFlag(false);

		String dropZoneId = RandomTestUtil.randomString();

		String duplicatedIdHTML = _getHTML(dropZoneId, dropZoneId);

		_dropZoneFragmentEntryProcessor.validateFragmentEntryHTML(
			duplicatedIdHTML, null);

		String missingIdHTML = _getHTML(StringPool.BLANK, dropZoneId);

		_dropZoneFragmentEntryProcessor.validateFragmentEntryHTML(
			missingIdHTML, null);
	}

	@Test
	public void testValidateFragmentEntryHTMLValidWithIds() throws Exception {
		_setFeatureFlag(true);

		_dropZoneFragmentEntryProcessor.validateFragmentEntryHTML(
			_getHTML(
				RandomTestUtil.randomString(), RandomTestUtil.randomString()),
			null);
	}

	@Test
	public void testValidateFragmentEntryHTMLValidWithoutIds()
		throws Exception {

		_setFeatureFlag(true);

		_dropZoneFragmentEntryProcessor.validateFragmentEntryHTML(
			_getHTML(StringPool.BLANK, StringPool.BLANK), null);
	}

	private static void _setUpDropZoneFragmentEntryProcessor() {
		_dropZoneFragmentEntryProcessor = new DropZoneFragmentEntryProcessor();

		_layoutPageTemplateStructureLocalService = Mockito.mock(
			LayoutPageTemplateStructureLocalService.class);

		ReflectionTestUtil.setFieldValue(
			_dropZoneFragmentEntryProcessor,
			"_layoutPageTemplateStructureLocalService",
			_layoutPageTemplateStructureLocalService);

		_language = Mockito.mock(Language.class);

		ReflectionTestUtil.setFieldValue(
			_dropZoneFragmentEntryProcessor, "_language", _language);

		_portal = Mockito.mock(Portal.class);

		ReflectionTestUtil.setFieldValue(
			_dropZoneFragmentEntryProcessor, "_portal", _portal);
	}

	private static void _setUpPortalUUIDUtil() {
		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());
	}

	private static void _setUpPropsUtil() {
		_props = Mockito.mock(Props.class);

		ReflectionTestUtil.setFieldValue(PropsUtil.class, "_props", _props);
	}

	private FragmentDropZoneLayoutStructureItem
		_addFragmentDropZoneLayoutStructureItem(
			FragmentEntryLink fragmentEntryLink,
			LayoutStructure layoutStructure) {

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem containerStyledLayoutStructureItem =
			layoutStructure.addContainerStyledLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

		layoutStructure.addLayoutStructureItem(
			containerStyledLayoutStructureItem);

		LayoutStructureItem fragmentStyledLayoutStructureItem =
			layoutStructure.addFragmentStyledLayoutStructureItem(
				fragmentEntryLink.getFragmentEntryLinkId(),
				containerStyledLayoutStructureItem.getItemId(), 0);

		layoutStructure.addLayoutStructureItem(
			fragmentStyledLayoutStructureItem);

		FragmentDropZoneLayoutStructureItem
			fragmentDropZoneLayoutStructureItem =
				(FragmentDropZoneLayoutStructureItem)
					layoutStructure.addFragmentDropZoneLayoutStructureItem(
						fragmentStyledLayoutStructureItem.getItemId(), 0);

		layoutStructure.addLayoutStructureItem(
			fragmentDropZoneLayoutStructureItem);

		return fragmentDropZoneLayoutStructureItem;
	}

	private String _getHTML(String... dropZoneIds) {
		StringBundler sb = new StringBundler("<div class=\"fragment_1\">");

		for (String dropZoneId : dropZoneIds) {
			sb.append("<lfr-drop-zone");

			if (!Validator.isBlank(dropZoneId)) {
				sb.append(" data-lfr-drop-zone-id=\"");
				sb.append(dropZoneId);
				sb.append(StringPool.QUOTE);
			}

			sb.append("></lfr-drop-zone>");
		}

		sb.append("</div>");

		return sb.toString();
	}

	private FragmentEntryLink _getMockFragmentEntryLink() {
		FragmentEntryLink fragmentEntryLink = Mockito.mock(
			FragmentEntryLink.class);

		Mockito.when(
			fragmentEntryLink.getFragmentEntryLinkId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return fragmentEntryLink;
	}

	private HttpServletRequest _getMockHttpServletRequest(
		LayoutStructure layoutStructure) {

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getAttribute(LayoutWebKeys.LAYOUT_STRUCTURE)
		).thenReturn(
			layoutStructure
		);

		return httpServletRequest;
	}

	private void _setFeatureFlag(boolean enabled) {
		Mockito.when(
			_props.get("feature.flag.LPS-167932")
		).thenReturn(
			Boolean.toString(enabled)
		);
	}

	private static DropZoneFragmentEntryProcessor
		_dropZoneFragmentEntryProcessor;
	private static Language _language;
	private static LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;
	private static Portal _portal;
	private static Props _props;

}
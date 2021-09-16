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

package com.liferay.dynamic.data.mapping.form.field.type.internal.document.library;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.constants.DDMFormConstants;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.form.item.selector.criterion.DDMUserPersonalFolderItemSelectorCriterion;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.HtmlImpl;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.PowerMockitoStubber;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Pedro Queiroz
 */
@PrepareForTest(
	{
		ModelResourcePermissionUtil.class,
		RequestBackedPortletURLFactoryUtil.class
	}
)
@RunWith(PowerMockRunner.class)
public class DocumentLibraryDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpCompanyLocalService();
		_setUpDLAppService();
		_setUpFileEntry();
		_setUpGroupLocalService();
		_setUpHtml();
		_setUpItemSelector();
		_setUpJSONFactory();
		_setUpJSONFactoryUtil();
		_setUpModelResourcePermissionUtil();
		_setUpParamUtil();
		_setUpPortal();
		_setUpPortletFileRepository();
		_setUpRequestBackedPortletURLFactoryUtil();
		_setUpUserLocalService();
	}

	@Test
	public void testDDMFormPortletItemSelector() {
		_mockDDMFormPortletItemSelector();

		ThemeDisplay themeDisplay = _mockThemeDisplay();

		when(
			themeDisplay.isSignedIn()
		).thenReturn(
			Boolean.TRUE
		);

		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				themeDisplay);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			_createDDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setPortletNamespace(
			_PORTLET_NAMESPACE_DDM_FORM);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				ddmFormFieldRenderingContext);

		Assert.assertTrue(parameters.containsKey("itemSelectorURL"));
	}

	@Test
	public void testGetParametersForAllowedGuestUser() {
		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				_mockThemeDisplay());

		DDMFormField ddmFormField = new DDMFormField(
			"field", "document_library");

		ddmFormField.setProperty("allowGuestUsers", true);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		Assert.assertEquals(true, parameters.get("allowGuestUsers"));
		Assert.assertEquals(_FORMS_FOLDER_ID, parameters.get("folderId"));

		String guestUploadURL = String.valueOf(
			parameters.get("guestUploadURL"));

		Assert.assertThat(
			guestUploadURL,
			CoreMatchers.containsString(
				"param_javax.portlet.action=/dynamic_data_mapping_form" +
					"/upload_file_entry"));
		Assert.assertThat(
			guestUploadURL,
			CoreMatchers.containsString(
				"param_formInstanceId=" + _FORM_INSTANCE_ID));
		Assert.assertThat(
			guestUploadURL,
			CoreMatchers.containsString("param_groupId=" + _GROUP_ID));
		Assert.assertThat(
			guestUploadURL,
			CoreMatchers.containsString("param_folderId=" + _FORMS_FOLDER_ID));
	}

	@Test
	public void testGetParametersForGuestUser() {
		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				_mockThemeDisplay());

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				_createDDMFormFieldRenderingContext());

		Assert.assertFalse(parameters.containsKey("folderId"));
		Assert.assertFalse(parameters.containsKey("guestUploadURL"));
	}

	@Test
	public void testGetParametersForSignedInUser() {
		ThemeDisplay themeDisplay = _mockThemeDisplay();

		when(
			themeDisplay.isSignedIn()
		).thenReturn(
			Boolean.TRUE
		);

		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				themeDisplay);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				_createDDMFormFieldRenderingContext());

		Assert.assertEquals(_PRIVATE_FOLDER_ID, parameters.get("folderId"));
		Assert.assertFalse(parameters.containsKey("guestUploadURL"));
		Assert.assertTrue(parameters.containsKey("itemSelectorURL"));
	}

	@Test
	public void testGetParametersForUserWithoutPermission() throws Exception {
		when(
			ModelResourcePermissionUtil.contains(
				Matchers.any(), Matchers.any(PermissionChecker.class),
				Matchers.eq(_GROUP_ID), Matchers.eq(_FORMS_FOLDER_ID),
				Matchers.eq(ActionKeys.ADD_FOLDER))
		).thenReturn(
			false
		);

		ThemeDisplay themeDisplay = _mockThemeDisplay();

		when(
			themeDisplay.isSignedIn()
		).thenReturn(
			Boolean.TRUE
		);

		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				themeDisplay);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				_createDDMFormFieldRenderingContext());

		Assert.assertFalse(parameters.containsKey("folderId"));
		Assert.assertFalse(parameters.containsKey("itemSelectorURL"));
		Assert.assertTrue(
			(boolean)parameters.get("showUploadPermissionMessage"));
	}

	@Test
	public void testGetParametersShouldContainFileEntryURL() {
		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				_mockThemeDisplay());

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				_createDDMFormFieldRenderingContext());

		Assert.assertTrue(parameters.containsKey("fileEntryURL"));
	}

	@Test
	public void testGetParametersShouldContainMaximumRepetitions() {
		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				_mockThemeDisplay());

		DDMFormField ddmFormField = new DDMFormField(
			"field", "document_library");

		ddmFormField.setProperty("maximumRepetitions", 8);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		Assert.assertEquals(8, parameters.get("maximumRepetitions"));
	}

	@Test
	public void testGetParametersShouldUseExistingGuestUploadURL() {
		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				_mockThemeDisplay());

		DDMFormField ddmFormField = new DDMFormField(
			"field", "document_library");

		ddmFormField.setProperty("allowGuestUsers", true);

		String expectedGuestUploadURL = RandomTestUtil.randomString();

		ddmFormField.setProperty("guestUploadURL", expectedGuestUploadURL);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		Assert.assertEquals(
			expectedGuestUploadURL,
			String.valueOf(parameters.get("guestUploadURL")));
	}

	@Test
	public void testGetParametersShouldUseFileEntryTitle() {
		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				_mockThemeDisplay());

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				_createDDMFormFieldRenderingContext());

		Assert.assertEquals("New Title", parameters.get("fileEntryTitle"));
	}

	@Test
	public void testGetParametersWithNullGroupShouldContainItemSelectorURL() {
		_mockGroupLocalServiceFetchGroup(null);

		ThemeDisplay themeDisplay = _mockThemeDisplay();

		when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			_scopeGroup
		);

		when(
			themeDisplay.isSignedIn()
		).thenReturn(
			Boolean.TRUE
		);

		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor = _createSpy(
				themeDisplay);

		Map<String, Object> parameters =
			documentLibraryDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "document_library"),
				_createDDMFormFieldRenderingContext());

		Assert.assertTrue(parameters.containsKey("itemSelectorURL"));
	}

	private DDMFormFieldRenderingContext _createDDMFormFieldRenderingContext() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setHttpServletRequest(
			_createHttpServletRequest());
		ddmFormFieldRenderingContext.setPortletNamespace(_PORTLET_NAMESPACE);
		ddmFormFieldRenderingContext.setProperty("groupId", _GROUP_ID);
		ddmFormFieldRenderingContext.setValue(
			JSONUtil.put(
				"groupId", _GROUP_ID
			).put(
				"title", "File Title"
			).put(
				"uuid", _FILE_ENTRY_UUID
			).toString());

		return ddmFormFieldRenderingContext;
	}

	private HttpServletRequest _createHttpServletRequest() {
		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setParameter(
			"formInstanceId", String.valueOf(_FORM_INSTANCE_ID));

		return httpServletRequest;
	}

	private DocumentLibraryDDMFormFieldTemplateContextContributor _createSpy(
		ThemeDisplay themeDisplay) {

		DocumentLibraryDDMFormFieldTemplateContextContributor
			documentLibraryDDMFormFieldTemplateContextContributor =
				PowerMockito.spy(
					_documentLibraryDDMFormFieldTemplateContextContributor);

		PowerMockitoStubber powerMockitoStubber = PowerMockito.doReturn(
			_resourceBundle);

		powerMockitoStubber.when(
			documentLibraryDDMFormFieldTemplateContextContributor
		).getResourceBundle(
			Matchers.any(Locale.class)
		);

		powerMockitoStubber = PowerMockito.doReturn(themeDisplay);

		powerMockitoStubber.when(
			documentLibraryDDMFormFieldTemplateContextContributor
		).getThemeDisplay(
			Matchers.any(HttpServletRequest.class)
		);

		return documentLibraryDDMFormFieldTemplateContextContributor;
	}

	private Company _mockCompany() {
		Company company = mock(Company.class);

		when(
			company.getMx()
		).thenReturn(
			"liferay.com"
		);

		return company;
	}

	private void _mockDDMFormPortletItemSelector() {
		when(
			_itemSelector.getItemSelectorURL(
				Mockito.eq(_requestBackedPortletURLFactory), Mockito.eq(_group),
				Mockito.eq(_GROUP_ID),
				Mockito.eq(
					_PORTLET_NAMESPACE_DDM_FORM + "selectDocumentLibrary"),
				Mockito.any(DDMUserPersonalFolderItemSelectorCriterion.class))
		).thenReturn(
			new MockLiferayPortletURL()
		);
	}

	private Folder _mockFolder(long folderId) {
		Folder folder = mock(Folder.class);

		when(
			folder.getFolderId()
		).thenReturn(
			folderId
		);

		return folder;
	}

	private void _mockGroupLocalServiceFetchGroup(Group group) {
		when(
			_groupLocalService.fetchGroup(_GROUP_ID)
		).thenReturn(
			group
		);
	}

	private Repository _mockRepository() {
		Repository repository = mock(Repository.class);

		when(
			repository.getRepositoryId()
		).thenReturn(
			_REPOSITORY_ID
		);

		return repository;
	}

	private RequestBackedPortletURLFactory
		_mockRequestBackedPortletURLFactory() {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory = mock(
			RequestBackedPortletURLFactory.class);

		when(
			requestBackedPortletURLFactory.createActionURL(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM)
		).thenReturn(
			new MockLiferayPortletURL()
		);

		return requestBackedPortletURLFactory;
	}

	private ThemeDisplay _mockThemeDisplay() {
		ThemeDisplay themeDisplay = mock(ThemeDisplay.class);

		when(
			themeDisplay.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		when(
			themeDisplay.getPathContext()
		).thenReturn(
			"/my/path/context/"
		);

		when(
			themeDisplay.getPathThemeImages()
		).thenReturn(
			"/my/theme/images/"
		);

		User user = _mockUser();

		when(
			themeDisplay.getUser()
		).thenReturn(
			user
		);

		return themeDisplay;
	}

	private User _mockUser() {
		User user = mock(User.class);

		when(
			user.getScreenName()
		).thenReturn(
			"Test"
		);

		when(
			user.getUserId()
		).thenReturn(
			0L
		);

		return user;
	}

	private void _setUpCompanyLocalService() throws Exception {
		CompanyLocalService companyLocalService = mock(
			CompanyLocalService.class);

		Company company = _mockCompany();

		when(
			companyLocalService.getCompany(_COMPANY_ID)
		).thenReturn(
			company
		);

		MemberMatcher.field(
			DocumentLibraryDDMFormFieldTemplateContextContributor.class,
			"_companyLocalService"
		).set(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			companyLocalService
		);
	}

	private void _setUpDLAppService() throws Exception {
		MemberMatcher.field(
			DocumentLibraryDDMFormFieldTemplateContextContributor.class,
			"_dlAppService"
		).set(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			_dlAppService
		);

		when(
			_dlAppService.getFileEntryByUuidAndGroupId(
				_FILE_ENTRY_UUID, _GROUP_ID)
		).thenReturn(
			_fileEntry
		);

		Folder folder = _mockFolder(_PRIVATE_FOLDER_ID);

		when(
			_dlAppService.getFolder(_REPOSITORY_ID, _FORMS_FOLDER_ID, "Test")
		).thenReturn(
			folder
		);
	}

	private void _setUpFileEntry() {
		_fileEntry.setUuid(_FILE_ENTRY_UUID);
		_fileEntry.setGroupId(_GROUP_ID);

		when(
			_fileEntry.getTitle()
		).thenReturn(
			"New Title"
		);
	}

	private void _setUpGroupLocalService() throws Exception {
		MemberMatcher.field(
			DocumentLibraryDDMFormFieldTemplateContextContributor.class,
			"_groupLocalService"
		).set(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			_groupLocalService
		);

		_mockGroupLocalServiceFetchGroup(_group);
	}

	private void _setUpHtml() throws Exception {
		MemberMatcher.field(
			DocumentLibraryDDMFormFieldTemplateContextContributor.class, "_html"
		).set(
			_documentLibraryDDMFormFieldTemplateContextContributor, _html
		);
	}

	private void _setUpItemSelector() throws Exception {
		MemberMatcher.field(
			DocumentLibraryDDMFormFieldTemplateContextContributor.class,
			"_itemSelector"
		).set(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			_itemSelector
		);

		when(
			_itemSelector.getItemSelectorURL(
				Mockito.eq(_requestBackedPortletURLFactory),
				Mockito.argThat(
					new ArgumentMatcher<Group>() {

						@Override
						public boolean matches(Object object) {
							Group group = (Group)object;

							if ((group == _group) || (group == _scopeGroup)) {
								return true;
							}

							return false;
						}

					}),
				Mockito.eq(_GROUP_ID),
				Mockito.eq(_PORTLET_NAMESPACE + "selectDocumentLibrary"),
				Mockito.any(DDMUserPersonalFolderItemSelectorCriterion.class),
				Mockito.any(FileItemSelectorCriterion.class))
		).thenReturn(
			new MockLiferayPortletURL()
		);
	}

	private void _setUpJSONFactory() throws Exception {
		MemberMatcher.field(
			DocumentLibraryDDMFormFieldTemplateContextContributor.class,
			"_jsonFactory"
		).set(
			_documentLibraryDDMFormFieldTemplateContextContributor, _jsonFactory
		);
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpModelResourcePermissionUtil() throws Exception {
		mockStatic(ModelResourcePermissionUtil.class);

		when(
			ModelResourcePermissionUtil.contains(
				Matchers.any(), Matchers.any(PermissionChecker.class),
				Matchers.eq(_GROUP_ID), Matchers.eq(_FORMS_FOLDER_ID),
				Matchers.eq(ActionKeys.ADD_FOLDER))
		).thenReturn(
			true
		);
	}

	private void _setUpParamUtil() {
		PropsUtil.setProps(Mockito.mock(Props.class));
	}

	private void _setUpPortal() throws Exception {
		MemberMatcher.field(
			DocumentLibraryDDMFormFieldTemplateContextContributor.class,
			"_portal"
		).set(
			_documentLibraryDDMFormFieldTemplateContextContributor, _portal
		);

		when(
			_portal.getPortletNamespace(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM)
		).thenReturn(
			_PORTLET_NAMESPACE_DDM_FORM
		);
	}

	private void _setUpPortletFileRepository() throws Exception {
		MemberMatcher.field(
			DocumentLibraryDDMFormFieldTemplateContextContributor.class,
			"_portletFileRepository"
		).set(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			_portletFileRepository
		);

		Folder folder = _mockFolder(_FORMS_FOLDER_ID);

		when(
			_portletFileRepository.getPortletFolder(
				_REPOSITORY_ID, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				DDMFormConstants.DDM_FORM_UPLOADED_FILES_FOLDER_NAME)
		).thenReturn(
			folder
		);

		Repository repository = _mockRepository();

		when(
			_portletFileRepository.fetchPortletRepository(
				_GROUP_ID, DDMFormConstants.SERVICE_NAME)
		).thenReturn(
			repository
		);
	}

	private void _setUpRequestBackedPortletURLFactoryUtil() {
		mockStatic(RequestBackedPortletURLFactoryUtil.class);

		when(
			RequestBackedPortletURLFactoryUtil.create(
				Matchers.any(HttpServletRequest.class))
		).thenReturn(
			_requestBackedPortletURLFactory
		);
	}

	private void _setUpUserLocalService() throws Exception {
		MemberMatcher.field(
			DocumentLibraryDDMFormFieldTemplateContextContributor.class,
			"_userLocalService"
		).set(
			_documentLibraryDDMFormFieldTemplateContextContributor,
			_userLocalService
		);

		User user = _mockUser();

		when(
			_userLocalService.getUserByEmailAddress(
				_COMPANY_ID,
				DDMFormConstants.DDM_FORM_DEFAULT_USER_SCREEN_NAME +
					"@liferay.com")
		).thenReturn(
			user
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _FILE_ENTRY_UUID =
		RandomTestUtil.randomString();

	private static final long _FORM_INSTANCE_ID = RandomTestUtil.randomLong();

	private static final long _FORMS_FOLDER_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final String _PORTLET_NAMESPACE =
		RandomTestUtil.randomString();

	private static final String _PORTLET_NAMESPACE_DDM_FORM =
		"_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormPortlet_";

	private static final long _PRIVATE_FOLDER_ID = RandomTestUtil.randomLong();

	private static final long _REPOSITORY_ID = RandomTestUtil.randomLong();

	@Mock
	private DLAppService _dlAppService;

	private final DocumentLibraryDDMFormFieldTemplateContextContributor
		_documentLibraryDDMFormFieldTemplateContextContributor =
			new DocumentLibraryDDMFormFieldTemplateContextContributor();

	@Mock
	private FileEntry _fileEntry;

	@Mock
	private Group _group;

	@Mock
	private GroupLocalService _groupLocalService;

	private final Html _html = new HtmlImpl();

	@Mock
	private ItemSelector _itemSelector;

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

	@Mock
	private Portal _portal;

	@Mock
	private PortletFileRepository _portletFileRepository;

	private final RequestBackedPortletURLFactory
		_requestBackedPortletURLFactory = _mockRequestBackedPortletURLFactory();

	@Mock
	private ResourceBundle _resourceBundle;

	@Mock
	private Group _scopeGroup;

	@Mock
	private UserLocalService _userLocalService;

}
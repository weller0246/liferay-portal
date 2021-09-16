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

package com.liferay.document.library.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslatorUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 */
@RunWith(Arquillian.class)
public class DLFileEntryMetadataLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_group = GroupTestUtil.addGroup();

		User user = TestPropsValues.getUser();

		ServiceContext serviceContext = _getServiceContext(_group, user);

		_dlFileEntryType = _dlFileEntryTypeLocalService.addFileEntryType(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), StringPool.BLANK, new long[0],
			serviceContext);

		List<com.liferay.dynamic.data.mapping.kernel.DDMStructure>
			ddmStructures = _dlFileEntryType.getDDMStructures();

		com.liferay.dynamic.data.mapping.kernel.DDMStructure ddmStructure =
			ddmStructures.get(0);

		_ddmStructure = _ddmStructureLocalService.getStructure(
			ddmStructure.getStructureId());

		Map<String, DDMFormValues> ddmFormValuesMap = setUpDDMFormValuesMap(
			_ddmStructure.getStructureKey(), user.getLocale());

		_dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			null, null, _dlFileEntryType.getFileEntryTypeId(), ddmFormValuesMap,
			null, new ByteArrayInputStream(TestDataConstants.TEST_BYTE_ARRAY),
			TestDataConstants.TEST_BYTE_ARRAY.length, null, null,
			serviceContext);
	}

	@Test
	public void testDeleteGroup() throws Exception {
		User user = TestPropsValues.getUser();

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = _getServiceContext(group, user);

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.addFileEntryType(
				user.getUserId(), group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK, new long[0],
				serviceContext);

		List<com.liferay.dynamic.data.mapping.kernel.DDMStructure>
			ddmStructures = dlFileEntryType.getDDMStructures();

		com.liferay.dynamic.data.mapping.kernel.DDMStructure
			kernelDDMStructure = ddmStructures.get(0);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			kernelDDMStructure.getStructureId());

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			null, null, dlFileEntryType.getFileEntryTypeId(),
			setUpDDMFormValuesMap(
				ddmStructure.getStructureKey(), user.getLocale()),
			null, new ByteArrayInputStream(TestDataConstants.TEST_BYTE_ARRAY),
			TestDataConstants.TEST_BYTE_ARRAY.length, null, null,
			serviceContext);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		DLFileEntryMetadata dlFileEntryMetadata =
			_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
				ddmStructure.getStructureId(),
				dlFileVersion.getFileVersionId());

		Assert.assertNotNull(dlFileEntryMetadata);

		_groupLocalService.deleteGroup(group);

		Assert.assertNull(
			_dlFileEntryTypeLocalService.fetchDLFileEntryType(
				dlFileEntryType.getFileEntryTypeId()));

		Assert.assertNull(
			_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
				ddmStructure.getStructureId(),
				dlFileVersion.getFileVersionId()));

		Assert.assertNull(
			_ddmStructureLocalService.fetchDDMStructure(
				ddmStructure.getStructureId()));
	}

	@Test
	public void testGetMismatchedCompanyIdFileEntryMetadatas()
		throws Exception {

		try {
			DLFileVersion dlFileVersion = _dlFileEntry.getFileVersion();

			DLFileEntryMetadata dlFileEntryMetadata =
				_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
					_ddmStructure.getStructureId(),
					dlFileVersion.getFileVersionId());

			_ddmStructure.setCompanyId(_company.getCompanyId());

			_ddmStructure = _ddmStructureLocalService.updateDDMStructure(
				_ddmStructure);

			List<DLFileEntryMetadata> dlFileEntryMetadatas =
				_dlFileEntryMetadataLocalService.
					getMismatchedCompanyIdFileEntryMetadatas();

			Assert.assertEquals(
				dlFileEntryMetadatas.toString(), 1,
				dlFileEntryMetadatas.size());
			Assert.assertEquals(
				dlFileEntryMetadata, dlFileEntryMetadatas.get(0));
		}
		finally {
			if (_ddmStructure != null) {
				_ddmStructure.setCompanyId(_dlFileEntry.getCompanyId());

				_ddmStructure = _ddmStructureLocalService.updateDDMStructure(
					_ddmStructure);
			}
		}
	}

	@Test
	public void testGetNoStructuresFileEntryMetadatas() throws Exception {
		try {
			DLFileVersion dlFileVersion = _dlFileEntry.getFileVersion();

			DLFileEntryMetadata dlFileEntryMetadata =
				_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
					_ddmStructure.getStructureId(),
					dlFileVersion.getFileVersionId());

			_ddmStructureLocalService.deleteDDMStructure(_ddmStructure);

			List<DLFileEntryMetadata> dlFileEntryMetadatas =
				_dlFileEntryMetadataLocalService.
					getNoStructuresFileEntryMetadatas();

			Assert.assertEquals(
				dlFileEntryMetadatas.toString(), 1,
				dlFileEntryMetadatas.size());
			Assert.assertEquals(
				dlFileEntryMetadata, dlFileEntryMetadatas.get(0));
		}
		finally {
			if (_ddmStructure != null) {
				_ddmStructureLocalService.addDDMStructure(_ddmStructure);
			}
		}
	}

	protected Map<String, DDMFormValues> setUpDDMFormValuesMap(
		String ddmStructureKey, Locale currentLocale) {

		return HashMapBuilder.<String, DDMFormValues>put(
			ddmStructureKey,
			() -> {
				Set<Locale> availableLocales =
					DDMFormTestUtil.createAvailableLocales(currentLocale);

				DDMForm ddmForm = new DDMForm();

				ddmForm.setAvailableLocales(availableLocales);
				ddmForm.setDefaultLocale(currentLocale);

				DDMFormField ddmFormField = new DDMFormField(
					"date_an", DDMFormFieldType.DATE);

				ddmFormField.setDataType("date");

				ddmForm.addDDMFormField(ddmFormField);

				DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

				ddmFormValues.setAvailableLocales(availableLocales);
				ddmFormValues.setDefaultLocale(currentLocale);

				DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

				ddmFormFieldValue.setName("date_an");
				ddmFormFieldValue.setValue(new UnlocalizedValue(""));

				ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

				return ddmFormValues;
			}
		).build();
	}

	private ServiceContext _getServiceContext(Group group, User user)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				new String(
					FileUtil.getBytes(
						getClass(), "dependencies/ddmstructure.xml")));

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(builder.build());

		serviceContext.setAttribute(
			"ddmForm",
			DDMBeanTranslatorUtil.translate(
				ddmFormDeserializerDeserializeResponse.getDDMForm()));

		serviceContext.setLanguageId(LocaleUtil.toLanguageId(user.getLocale()));

		return serviceContext;
	}

	@Inject
	private static DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private static DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private static DLFileEntryMetadataLocalService
		_dlFileEntryMetadataLocalService;

	@Inject
	private static DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Inject
	private static GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private Company _company;

	@Inject(filter = "ddm.form.deserializer.type=xsd")
	private DDMFormDeserializer _ddmFormDeserializer;

	private DDMStructure _ddmStructure;
	private DLFileEntry _dlFileEntry;
	private DLFileEntryType _dlFileEntryType;

	@DeleteAfterTestRun
	private Group _group;

}
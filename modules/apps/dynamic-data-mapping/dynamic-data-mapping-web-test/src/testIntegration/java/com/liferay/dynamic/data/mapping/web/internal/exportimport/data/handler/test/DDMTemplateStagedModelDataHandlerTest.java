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

package com.liferay.dynamic.data.mapping.web.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class DDMTemplateStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testPublishTemplateToLiveBeforeStructure() throws Exception {
		DDMTemplate template = DDMTemplateTestUtil.addTemplate(
			stagingGroup.getGroupId(), 0,
			PortalUtil.getClassNameId(_CLASS_NAME));

		DDMStructure structure = DDMStructureTestUtil.addStructure(
			stagingGroup.getGroupId(), _CLASS_NAME);

		exportImportTemplate(template);

		template.setClassPK(structure.getStructureId());

		DDMTemplateLocalServiceUtil.updateDDMTemplate(template);

		exportImportTemplateAndStructure(template, structure);

		DDMStructure importedStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				structure.getUuid(), liveGroup.getGroupId());

		DDMTemplate importedTemplate = (DDMTemplate)getStagedModel(
			template.getUuid(), liveGroup);

		Assert.assertNotNull(importedTemplate);

		Assert.assertNotNull(importedStructure);
		Assert.assertEquals(
			importedStructure.getStructureId(), importedTemplate.getClassPK());
	}

	@Test
	public void testPublishTemplateWithParentGroups() throws Exception {
		Group parentGroup = GroupTestUtil.addGroup();

		Group childGroup = GroupTestUtil.addGroup(parentGroup.getGroupId());

		DDMTemplate template = DDMTemplateTestUtil.addTemplate(
			parentGroup.getGroupId(), 0,
			PortalUtil.getClassNameId(_CLASS_NAME));

		DDMStructure structure = DDMStructureTestUtil.addStructure(
			parentGroup.getGroupId(), _CLASS_NAME_JOURNAL_ARTICLE);

		template.setClassPK(structure.getStructureId());

		DDMTemplateLocalServiceUtil.updateDDMTemplate(template);

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(childGroup.getGroupId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		JournalArticle journalArticle =
			JournalArticleLocalServiceUtil.addArticle(
				null, TestPropsValues.getUserId(), childGroup.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, titleMap,
				descriptionMap, content, structure.getStructureKey(),
				template.getTemplateKey(), serviceContext);

		exportTemplateAndStructure(parentGroup, template, structure);

		Group newParentGroup = GroupTestUtil.addGroup();

		importTemplateAndStructure(
			parentGroup, newParentGroup, template, structure);

		exportJournalArticle(childGroup, journalArticle);

		childGroup = GroupTestUtil.deleteGroup(childGroup);

		GroupTestUtil.deleteGroup(parentGroup);

		Group newChildGroup = GroupTestUtil.addGroup(
			newParentGroup.getGroupId());

		importJournalArticle(childGroup, newChildGroup, journalArticle);

		DDMStructure importedStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				structure.getUuid(), newParentGroup.getGroupId());

		DDMTemplate importedTemplate =
			DDMTemplateLocalServiceUtil.fetchDDMTemplateByUuidAndGroupId(
				template.getUuid(), newParentGroup.getGroupId());

		JournalArticle importedJournalArticle =
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				journalArticle.getUuid(), newChildGroup.getGroupId());

		Assert.assertNotNull(importedStructure);
		Assert.assertNotNull(importedTemplate);
		Assert.assertNotNull(importedJournalArticle);
		Assert.assertEquals(
			importedJournalArticle.getDDMStructureKey(),
			importedStructure.getStructureKey());
		Assert.assertEquals(
			importedJournalArticle.getDDMTemplateKey(),
			importedTemplate.getTemplateKey());
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		DDMStructure structure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), _CLASS_NAME);

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, structure);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			DDMStructure.class.getSimpleName());

		DDMStructure structure = (DDMStructure)dependentStagedModels.get(0);

		return DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), structure.getStructureId(),
			PortalUtil.getClassNameId(_CLASS_NAME));
	}

	protected void exportImportTemplate(DDMTemplate template) throws Exception {
		exportTemplateAndStructure(template, null);
		importTemplateAndStructure(template, null);
	}

	protected void exportImportTemplateAndStructure(
			DDMTemplate template, DDMStructure structure)
		throws Exception {

		exportTemplateAndStructure(template, structure);
		importTemplateAndStructure(template, structure);
	}

	protected void exportJournalArticle(
			Group exportGroup, JournalArticle journalArticle)
		throws Exception {

		initExport(exportGroup);

		if (Objects.nonNull(journalArticle)) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, journalArticle);
		}
	}

	protected void exportTemplateAndStructure(
			DDMTemplate template, DDMStructure structure)
		throws Exception {

		initExport();

		if (Objects.nonNull(structure)) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, structure);
		}

		if (Objects.nonNull(template)) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, template);
		}
	}

	protected void exportTemplateAndStructure(
			Group exportGroup, DDMTemplate template, DDMStructure structure)
		throws Exception {

		initExport(exportGroup);

		if (Objects.nonNull(structure)) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, structure);
		}

		if (Objects.nonNull(template)) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, template);
		}
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return DDMTemplateLocalServiceUtil.getDDMTemplateByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return DDMTemplate.class;
	}

	protected void importJournalArticle(
			Group exportGroup, Group importGroup, JournalArticle journalArticle)
		throws Exception {

		initImport(exportGroup, importGroup);

		if (Objects.nonNull(journalArticle)) {
			JournalArticle exportedJournalArticle =
				(JournalArticle)readExportedStagedModel(journalArticle);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedJournalArticle);
		}
	}

	protected void importTemplateAndStructure(
			DDMTemplate template, DDMStructure structure)
		throws Exception {

		initImport();

		if (Objects.nonNull(structure)) {
			DDMStructure exportedStructure =
				(DDMStructure)readExportedStagedModel(structure);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedStructure);
		}

		if (Objects.nonNull(template)) {
			DDMTemplate exportedTemplate = (DDMTemplate)readExportedStagedModel(
				template);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedTemplate);
		}
	}

	protected void importTemplateAndStructure(
			Group exportGroup, Group importGroup, DDMTemplate template,
			DDMStructure structure)
		throws Exception {

		initImport(exportGroup, importGroup);

		if (Objects.nonNull(structure)) {
			DDMStructure exportedStructure =
				(DDMStructure)readExportedStagedModel(structure);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedStructure);
		}

		if (Objects.nonNull(template)) {
			DDMTemplate exportedTemplate = (DDMTemplate)readExportedStagedModel(
				template);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedTemplate);
		}
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			DDMStructure.class.getSimpleName());

		Assert.assertEquals(
			dependentStagedModels.toString(), 1, dependentStagedModels.size());

		DDMStructure structure = (DDMStructure)dependentStagedModels.get(0);

		DDMStructureLocalServiceUtil.getDDMStructureByUuidAndGroupId(
			structure.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		DDMTemplate template = (DDMTemplate)stagedModel;
		DDMTemplate importedTemplate = (DDMTemplate)importedStagedModel;

		Assert.assertEquals(
			template.getTemplateKey(), importedTemplate.getTemplateKey());
		Assert.assertEquals(template.getName(), importedTemplate.getName());
		Assert.assertEquals(
			template.getDescription(), importedTemplate.getDescription());
		Assert.assertEquals(template.getMode(), importedTemplate.getMode());
		Assert.assertEquals(
			template.getLanguage(), importedTemplate.getLanguage());
		Assert.assertEquals(template.getScript(), importedTemplate.getScript());
		Assert.assertEquals(
			template.isCacheable(), importedTemplate.isCacheable());
		Assert.assertEquals(
			template.isSmallImage(), importedTemplate.isSmallImage());
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.lists.model.DDLRecordSet";

	private static final String _CLASS_NAME_JOURNAL_ARTICLE =
		"com.liferay.journal.model.JournalArticle";

}
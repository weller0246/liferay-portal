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

package com.liferay.knowledge.base.web.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.KBCommentLocalService;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.knowledge.base.service.KBTemplateLocalService;
import com.liferay.knowledge.base.util.comparator.KBArticleVersionComparator;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
	service = PortletDataHandler.class
)
public class AdminPortletDataHandler extends BasePortletDataHandler {

	public static final String[] CLASS_NAMES = {
		KBArticle.class.getName(), KBComment.class.getName(),
		KBTemplate.class.getName()
	};

	public static final String NAMESPACE = "knowledge_base";

	public static final String SCHEMA_VERSION = "4.0.0";

	public AdminPortletDataHandler() {
		setDataLevel(DataLevel.SITE);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(KBArticle.class),
			new StagedModelType(KBComment.class),
			new StagedModelType(KBTemplate.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "kb-folders", true, false, null,
				KBFolder.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "kb-articles", true, false,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "kb-comments", true, false, null,
						KBComment.class.getName())
				},
				KBArticle.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "kb-templates", true, false, null,
				KBTemplate.class.getName()));
		setStagingControls(getExportControls());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getResourceName() {
		return KBConstants.RESOURCE_NAME_ADMIN;
	}

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Override
	public String getServiceName() {
		return KBConstants.SERVICE_NAME;
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				AdminPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_kbArticleLocalService.deleteGroupKBArticles(
			portletDataContext.getScopeGroupId());
		_kbFolderLocalService.deleteKBFolders(
			portletDataContext.getScopeGroupId());
		_kbTemplateLocalService.deleteGroupKBTemplates(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(
			KBConstants.RESOURCE_NAME_ADMIN);

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (portletDataContext.getBooleanParameter(NAMESPACE, "kb-folders")) {
			ActionableDynamicQuery kbFoldersActionableDynamicQuery =
				_kbFolderLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			kbFoldersActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "kb-articles")) {
			ActionableDynamicQuery kbArticleActionableDynamicQuery =
				getKBArticleActionableDynamicQuery(portletDataContext);

			kbArticleActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "kb-templates")) {
			ActionableDynamicQuery kbTemplateActionableDynamicQuery =
				_kbTemplateLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			kbTemplateActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "kb-comments")) {
			ActionableDynamicQuery kbCommentActionableDynamicQuery =
				getKBCommentActionableDynamicQuery(portletDataContext);

			kbCommentActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(
			KBConstants.RESOURCE_NAME_ADMIN);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "kb-folders")) {
			Element kbFoldersElement =
				portletDataContext.getImportDataGroupElement(KBFolder.class);

			List<Element> kbFolderElements = kbFoldersElement.elements();

			for (Element kbFolderElement : kbFolderElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, kbFolderElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "kb-articles")) {
			Element kbArticlesElement =
				portletDataContext.getImportDataGroupElement(KBArticle.class);

			List<Element> kbArticleElements = kbArticlesElement.elements();

			for (Element kbArticleElement : kbArticleElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, kbArticleElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "kb-templates")) {
			Element kbTemplatesElement =
				portletDataContext.getImportDataGroupElement(KBTemplate.class);

			List<Element> kbTemplateElements = kbTemplatesElement.elements();

			for (Element kbTemplateElement : kbTemplateElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, kbTemplateElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "kb-comments")) {
			Element kbCommentsElement =
				portletDataContext.getImportDataGroupElement(KBComment.class);

			List<Element> kbCommentElements = kbCommentsElement.elements();

			for (Element kbCommentElement : kbCommentElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, kbCommentElement);
			}
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		if (ExportImportDateUtil.isRangeFromLastPublishDate(
				portletDataContext)) {

			_staging.populateLastPublishDateCounts(
				portletDataContext,
				new StagedModelType[] {
					new StagedModelType(KBArticle.class.getName()),
					new StagedModelType(KBComment.class.getName()),
					new StagedModelType(KBFolder.class.getName()),
					new StagedModelType(KBTemplate.class.getName())
				});

			return;
		}

		ActionableDynamicQuery kbFolderActionableDynamicQuery =
			_kbFolderLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		kbFolderActionableDynamicQuery.performCount();

		ActionableDynamicQuery kbArticleActionableDynamicQuery =
			_kbArticleLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		kbArticleActionableDynamicQuery.performCount();

		ActionableDynamicQuery kbTemplateActionableDynamicQuery =
			_kbTemplateLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		kbTemplateActionableDynamicQuery.performCount();

		ActionableDynamicQuery kbCommentActionableDynamicQuery =
			getKBCommentActionableDynamicQuery(portletDataContext);

		kbCommentActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getKBArticleActionableDynamicQuery(
			PortletDataContext portletDataContext)
		throws Exception {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_kbArticleLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddOrderCriteriaMethod
			addOrderCriteriaMethod =
				exportActionableDynamicQuery.getAddOrderCriteriaMethod();

		exportActionableDynamicQuery.setAddOrderCriteriaMethod(
			new ActionableDynamicQuery.AddOrderCriteriaMethod() {

				@Override
				public void addOrderCriteria(DynamicQuery dynamicQuery) {
					if (addOrderCriteriaMethod != null) {
						addOrderCriteriaMethod.addOrderCriteria(dynamicQuery);
					}

					OrderFactoryUtil.addOrderByComparator(
						dynamicQuery, new KBArticleVersionComparator(true));
				}

			});

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery getKBCommentActionableDynamicQuery(
			PortletDataContext portletDataContext)
		throws Exception {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_kbCommentLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				_portal.getClassNameId(KBComment.class),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	@Reference(unbind = "-")
	protected void setKBArticleLocalService(
		KBArticleLocalService kbArticleLocalService) {

		_kbArticleLocalService = kbArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setKBCommentLocalService(
		KBCommentLocalService kbCommentLocalService) {

		_kbCommentLocalService = kbCommentLocalService;
	}

	@Reference(unbind = "-")
	protected void setKBFolderLocalService(
		KBFolderLocalService kbFolderLocalService) {

		_kbFolderLocalService = kbFolderLocalService;
	}

	@Reference(unbind = "-")
	protected void setKBTemplateLocalService(
		KBTemplateLocalService kbTemplateLocalService) {

		_kbTemplateLocalService = kbTemplateLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	private KBArticleLocalService _kbArticleLocalService;
	private KBCommentLocalService _kbCommentLocalService;
	private KBFolderLocalService _kbFolderLocalService;
	private KBTemplateLocalService _kbTemplateLocalService;
	private Portal _portal;

	@Reference
	private Staging _staging;

}
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

package com.liferay.object.admin.rest.internal.resource.v1_0;

import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.Status;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldUtil;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectDefinitionResource.class
)
public class ObjectDefinitionResourceImpl
	extends BaseObjectDefinitionResourceImpl {

	@Override
	public void deleteObjectDefinition(Long objectDefinitionId)
		throws Exception {

		_objectDefinitionService.deleteObjectDefinition(objectDefinitionId);
	}

	@Override
	public ObjectDefinition getObjectDefinition(Long objectDefinitionId)
		throws Exception {

		return _toObjectDefinition(
			_objectDefinitionService.getObjectDefinition(objectDefinitionId));
	}

	@Override
	public Page<ObjectDefinition> getObjectDefinitionsPage(
			String search, Pagination pagination)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			null, com.liferay.object.model.ObjectDefinition.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			null,
			document -> _toObjectDefinition(
				_objectDefinitionService.getObjectDefinition(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ObjectDefinition postObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return _toObjectDefinition(
			_objectDefinitionService.addCustomObjectDefinition(
				LocalizedMapUtil.getLocalizedMap(objectDefinition.getLabel()),
				objectDefinition.getName(), objectDefinition.getPanelAppOrder(),
				objectDefinition.getPanelCategoryKey(),
				LocalizedMapUtil.getLocalizedMap(
					objectDefinition.getPluralLabel()),
				objectDefinition.getScope(),
				transformToList(
					objectDefinition.getObjectFields(),
					objectField -> ObjectFieldUtil.toObjectField(
						objectField, _objectFieldLocalService))));
	}

	@Override
	public void postObjectDefinitionPublish(Long objectDefinitionId)
		throws Exception {

		_objectDefinitionService.publishCustomObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public ObjectDefinition putObjectDefinition(
			Long objectDefinitionId, ObjectDefinition objectDefinition)
		throws Exception {

		return _toObjectDefinition(
			_objectDefinitionService.updateCustomObjectDefinition(
				objectDefinitionId,
				GetterUtil.getBoolean(objectDefinition.getActive(), true),
				LocalizedMapUtil.getLocalizedMap(objectDefinition.getLabel()),
				objectDefinition.getName(), objectDefinition.getPanelAppOrder(),
				objectDefinition.getPanelCategoryKey(),
				LocalizedMapUtil.getLocalizedMap(
					objectDefinition.getPluralLabel()),
				objectDefinition.getScope()));
	}

	private ObjectDefinition _toObjectDefinition(
		com.liferay.object.model.ObjectDefinition objectDefinition) {

		String permissionName =
			com.liferay.object.model.ObjectDefinition.class.getName();

		return new ObjectDefinition() {
			{
				actions = HashMapBuilder.put(
					"delete",
					() -> {
						if (objectDefinition.isApproved() ||
							objectDefinition.isSystem()) {

							return null;
						}

						return addAction(
							ActionKeys.DELETE, "deleteObjectDefinition",
							permissionName,
							objectDefinition.getObjectDefinitionId());
					}
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, "getObjectDefinition", permissionName,
						objectDefinition.getObjectDefinitionId())
				).put(
					"update",
					addAction(
						ActionKeys.UPDATE, "postObjectDefinition",
						permissionName,
						objectDefinition.getObjectDefinitionId())
				).build();
				dateCreated = objectDefinition.getCreateDate();
				dateModified = objectDefinition.getModifiedDate();
				id = objectDefinition.getObjectDefinitionId();
				name = objectDefinition.getShortName();
				objectFields = transformToArray(
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId()),
					objectField -> ObjectFieldUtil.toObjectField(
						null, objectField),
					ObjectField.class);
				scope = objectDefinition.getScope();
				status = new Status() {
					{
						code = objectDefinition.getStatus();
						label = WorkflowConstants.getStatusLabel(
							objectDefinition.getStatus());
						label_i18n = LanguageUtil.get(
							LanguageResources.getResourceBundle(
								contextAcceptLanguage.getPreferredLocale()),
							WorkflowConstants.getStatusLabel(
								objectDefinition.getStatus()));
					}
				};
				system = objectDefinition.isSystem();
			}
		};
	}

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}
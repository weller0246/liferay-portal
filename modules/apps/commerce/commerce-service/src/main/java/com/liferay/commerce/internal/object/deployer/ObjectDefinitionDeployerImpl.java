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

package com.liferay.commerce.internal.object.deployer;

import com.liferay.commerce.constants.CommerceDefinitionTermConstants;
import com.liferay.commerce.internal.notification.type.ObjectDefinitionCommerceNotificationType;
import com.liferay.commerce.internal.order.term.contributor.ObjectCommerceDefinitionTermContributor;
import com.liferay.commerce.internal.order.term.contributor.ObjectRecipientCommerceDefinitionTermContributor;
import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.commerce.order.CommerceDefinitionTermContributor;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Arrays;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true, service = ObjectDefinitionDeployer.class
)
public class ObjectDefinitionDeployerImpl implements ObjectDefinitionDeployer {

	@Override
	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		return Arrays.asList(
			_bundleContext.registerService(
				CommerceNotificationType.class,
				new ObjectDefinitionCommerceNotificationType(
					"create", objectDefinition.getClassName() + "#create",
					objectDefinition.getName()),
				HashMapDictionaryBuilder.put(
					"commerce.notification.type.key",
					objectDefinition.getClassName() + "#create"
				).build()),
			_bundleContext.registerService(
				CommerceNotificationType.class,
				new ObjectDefinitionCommerceNotificationType(
					"delete", objectDefinition.getClassName() + "#delete",
					objectDefinition.getName()),
				HashMapDictionaryBuilder.put(
					"commerce.notification.type.key",
					objectDefinition.getClassName() + "#delete"
				).build()),
			_bundleContext.registerService(
				CommerceNotificationType.class,
				new ObjectDefinitionCommerceNotificationType(
					"update", objectDefinition.getClassName() + "#update",
					objectDefinition.getName()),
				HashMapDictionaryBuilder.put(
					"commerce.notification.type.key",
					objectDefinition.getClassName() + "#update"
				).build()),
			_bundleContext.registerService(
				CommerceDefinitionTermContributor.class,
				new ObjectCommerceDefinitionTermContributor(
					objectDefinition.getObjectDefinitionId(),
					_objectFieldLocalService, _userLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"commerce.definition.term.contributor.key",
					CommerceDefinitionTermConstants.
						BODY_AND_SUBJECT_DEFINITION_TERMS_CONTRIBUTOR
				).put(
					"commerce.notification.type.key",
					new String[] {
						objectDefinition.getClassName() + "#create",
						objectDefinition.getClassName() + "#delete",
						objectDefinition.getClassName() + "#update"
					}
				).build()),
			_bundleContext.registerService(
				CommerceDefinitionTermContributor.class,
				new ObjectRecipientCommerceDefinitionTermContributor(
					_userGroupLocalService, _userLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"commerce.definition.term.contributor.key",
					CommerceDefinitionTermConstants.
						RECIPIENT_DEFINITION_TERMS_CONTRIBUTOR
				).put(
					"commerce.notification.type.key",
					new String[] {
						objectDefinition.getClassName() + "#create",
						objectDefinition.getClassName() + "#delete",
						objectDefinition.getClassName() + "#update"
					}
				).build()));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
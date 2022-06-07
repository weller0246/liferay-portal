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

package com.liferay.notification.service.impl;

import com.liferay.notification.constants.NotificationTermContributorConstants;
import com.liferay.notification.exception.NotificationTemplateFromException;
import com.liferay.notification.exception.NotificationTemplateNameException;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.base.NotificationTemplateLocalServiceBaseImpl;
import com.liferay.notification.service.persistence.NotificationQueueEntryPersistence;
import com.liferay.notification.term.contributor.NotificationTermContributor;
import com.liferay.notification.term.contributor.NotificationTermContributorRegistry;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.util.NotificationTypeRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.EmailAddressValidatorFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 * @author Gustavo Lima
 */
@Component(
	property = "model.class.name=com.liferay.notification.model.NotificationTemplate",
	service = AopService.class
)
public class NotificationTemplateLocalServiceImpl
	extends NotificationTemplateLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationTemplate addNotificationTemplate(
			long userId, String bcc, Map<Locale, String> bodyMap, String cc,
			String description, String from, Map<Locale, String> fromNameMap,
			String name, Map<Locale, String> subjectMap,
			Map<Locale, String> toMap)
		throws PortalException {

		_validate(name, from);

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		notificationTemplate.setCompanyId(user.getCompanyId());
		notificationTemplate.setUserId(user.getUserId());
		notificationTemplate.setUserName(user.getFullName());

		notificationTemplate.setBcc(bcc);
		notificationTemplate.setBodyMap(bodyMap);
		notificationTemplate.setCc(cc);
		notificationTemplate.setDescription(description);
		notificationTemplate.setFrom(from);
		notificationTemplate.setFromNameMap(fromNameMap);
		notificationTemplate.setName(name);
		notificationTemplate.setSubjectMap(subjectMap);
		notificationTemplate.setToMap(toMap);

		notificationTemplate = notificationTemplatePersistence.update(
			notificationTemplate);

		_resourceLocalService.addResources(
			notificationTemplate.getCompanyId(), 0,
			notificationTemplate.getUserId(),
			NotificationTemplate.class.getName(),
			notificationTemplate.getNotificationTemplateId(), false, true,
			true);

		return notificationTemplate;
	}

	@Override
	public NotificationTemplate deleteNotificationTemplate(
			long notificationTemplateId)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.findByPrimaryKey(
				notificationTemplateId);

		return notificationTemplateLocalService.deleteNotificationTemplate(
			notificationTemplate);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public NotificationTemplate deleteNotificationTemplate(
			NotificationTemplate notificationTemplate)
		throws PortalException {

		notificationTemplate = notificationTemplatePersistence.remove(
			notificationTemplate);

		_resourceLocalService.deleteResource(
			notificationTemplate, ResourceConstants.SCOPE_INDIVIDUAL);

		List<NotificationQueueEntry> notificationQueueEntries =
			_notificationQueueEntryPersistence.findByNotificationTemplateId(
				notificationTemplate.getNotificationTemplateId());

		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntries) {

			notificationQueueEntry.setNotificationTemplateId(0);

			_notificationQueueEntryPersistence.update(notificationQueueEntry);
		}

		return notificationTemplate;
	}

	@Override
	public void sendNotificationTemplate(
			long userId, long notificationTemplateId,
			String notificationTypeKey, Object object)
		throws PortalException {

		if (Validator.isBlank(notificationTypeKey)) {
			return;
		}

		NotificationType notificationType =
			_notificationTypeRegistry.getNotificationType(notificationTypeKey);

		if (notificationType == null) {
			return;
		}

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.findByPrimaryKey(
				notificationTemplateId);

		String body = _formatContent(
			notificationTemplate.getBody(user.getLocale()), user.getLocale(),
			Collections.emptyList(), notificationType, object);

		User user = _userLocalService.getUser(userId);

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(
			user.getGroupId());

		if (Validator.isNull(body)) {
			body = _formatContent(
				notificationTemplate.getBody(siteDefaultLocale),
				siteDefaultLocale, Collections.emptyList(), notificationType,
				object);
		}

		String fromName = notificationTemplate.getFromName(
			user.getLanguageId());

		if (Validator.isNull(fromName)) {
			fromName = notificationTemplate.getFromName(
				_portal.getSiteDefaultLocale(user.getGroupId()));
		}

		String subject = _formatContent(
			notificationTemplate.getSubject(user.getLocale()), user.getLocale(),
			Collections.emptyList(), notificationType, object);

		if (Validator.isNull(subject)) {
			subject = _formatContent(
				notificationTemplate.getSubject(siteDefaultLocale),
				siteDefaultLocale, Collections.emptyList(), notificationType,
				object);
		}

		String to = _formatContent(
			notificationTemplate.getTo(user.getLocale()), user.getLocale(),
			_notificationTermContributorRegistry.
				getNotificationTermContributorsByNotificationTermContributorKey(
					NotificationTermContributorConstants.RECIPIENT),
			notificationType, object);

		if (Validator.isNull(to)) {
			to = _formatContent(
				notificationTemplate.getTo(siteDefaultLocale),
				siteDefaultLocale,
				_notificationTermContributorRegistry.
					getNotificationTermContributorsByNotificationTermContributorKey(
						NotificationTermContributorConstants.RECIPIENT),
				notificationType, object);
		}

		EmailAddressValidator emailAddressValidator =
			EmailAddressValidatorFactory.getInstance();

		String[] toUserStrings = StringUtil.split(to);

		for (String toUserString : toUserStrings) {
			User toUser = _userLocalService.fetchUser(
				GetterUtil.getLong(toUserString));

			if ((toUser == null) &&
				emailAddressValidator.validate(
					user.getCompanyId(), toUserString)) {

				toUser = _userLocalService.fetchUserByEmailAddress(
					user.getCompanyId(), toUserString);

				if (toUser == null) {
					if (_log.isInfoEnabled()) {
						_log.info("No User found with key: " + toUserString);
					}

					User defaultUser = _userLocalService.getDefaultUser(
						CompanyThreadLocal.getCompanyId());

					_notificationQueueEntryLocalService.
						addNotificationQueueEntry(
							defaultUser.getUserId(),
							notificationTemplate.getNotificationTemplateId(),
							notificationTemplate.getBcc(), body,
							notificationTemplate.getCc(),
							notificationType.getClassName(object),
							notificationType.getClassPK(object),
							notificationTemplate.getFrom(), fromName, 0,
							subject, toUserString, toUserString);

					continue;
				}
			}

			_notificationQueueEntryLocalService.addNotificationQueueEntry(
				userId, notificationTemplate.getNotificationTemplateId(),
				notificationTemplate.getBcc(), body,
				notificationTemplate.getCc(),
				notificationType.getClassName(object),
				notificationType.getClassPK(object),
				notificationTemplate.getFrom(), fromName, 0, subject,
				toUser.getEmailAddress(), notificationTemplate.getName());
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationTemplate updateNotificationTemplate(
			long notificationTemplateId, String bcc,
			Map<Locale, String> bodyMap, String cc, String description,
			String from, Map<Locale, String> fromNameMap, String name,
			Map<Locale, String> subjectMap, Map<Locale, String> toMap)
		throws PortalException {

		_validate(name, from);

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.findByPrimaryKey(
				notificationTemplateId);

		notificationTemplate.setBcc(bcc);
		notificationTemplate.setBodyMap(bodyMap);
		notificationTemplate.setCc(cc);
		notificationTemplate.setDescription(description);
		notificationTemplate.setFrom(from);
		notificationTemplate.setFromNameMap(fromNameMap);
		notificationTemplate.setName(name);
		notificationTemplate.setSubjectMap(subjectMap);
		notificationTemplate.setToMap(toMap);

		return notificationTemplatePersistence.update(notificationTemplate);
	}

	private String _formatContent(
			String content, Locale locale,
			List<NotificationTermContributor> notificationTermContributors,
			NotificationType notificationType, Object object)
		throws PortalException {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		Set<String> placeholders = new HashSet<>();

		Matcher matcher = _placeholderPattern.matcher(content);

		while (matcher.find()) {
			placeholders.add(matcher.group());
		}

		for (NotificationTermContributor notificationTermContributor :
				notificationTermContributors) {

			for (String placeholder : placeholders) {
				content = StringUtil.replace(
					content, placeholder,
					notificationTermContributor.getTermValue(
						locale, object, placeholder));
			}
		}

		for (NotificationTermContributor notificationTermContributor :
				_notificationTermContributorRegistry.
					getNotificationTermContributorsByNotificationTypeKey(
						notificationType.getKey())) {

			for (String placeholder : placeholders) {
				content = StringUtil.replace(
					content, placeholder,
					notificationTermContributor.getTermValue(
						locale, object, placeholder));
			}
		}

		return content;
	}

	private void _validate(String name, String from) throws PortalException {
		if (Validator.isNull(name)) {
			throw new NotificationTemplateNameException("Name is null");
		}

		if (Validator.isNull(from)) {
			throw new NotificationTemplateFromException("From is null");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationTemplateLocalServiceImpl.class);

	private static final Pattern _placeholderPattern = Pattern.compile(
		"\\[%[^\\[%]+%\\]", Pattern.CASE_INSENSITIVE);

	@Reference
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Reference
	private NotificationQueueEntryPersistence
		_notificationQueueEntryPersistence;

	@Reference
	private NotificationTermContributorRegistry
		_notificationTermContributorRegistry;

	@Reference
	private NotificationTypeRegistry _notificationTypeRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
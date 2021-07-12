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

package com.liferay.translation.service.impl;

import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.translation.constants.TranslationActionKeys;
import com.liferay.translation.constants.TranslationConstants;
import com.liferay.translation.exception.XLIFFFileException;
import com.liferay.translation.internal.util.XLIFFLocaleIdUtil;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.security.permission.TranslationPermission;
import com.liferay.translation.service.base.TranslationEntryServiceBaseImpl;

import net.sf.okapi.common.LocaleId;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the translation entry remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * <code>com.liferay.translation.service.TranslationEntryService</code>
 * interface. <p> This is a remote service. Methods of this service are expected
 * to have security checks based on the propagated JAAS credentials because this
 * service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    TranslationEntryServiceBaseImpl
 */
@Component(
	property = {
		"json.web.service.context.name=translation",
		"json.web.service.context.path=TranslationEntry"
	},
	service = AopService.class
)
public class TranslationEntryServiceImpl
	extends TranslationEntryServiceBaseImpl {

	@Override
	public TranslationEntry addOrUpdateTranslationEntry(
			long groupId, InfoItemReference infoItemReference, String content,
			String contentType, ServiceContext serviceContext)
		throws PortalException {

		try {
			LocaleId targetLocaleId = XLIFFLocaleIdUtil.getTargetLocaleId(
				_saxReader.read(content));

			String languageId = _language.getLanguageId(
				LocaleUtil.fromLanguageId(targetLocaleId.toString()));

			_checkPermission(groupId, languageId, infoItemReference);

			return translationEntryLocalService.addOrUpdateTranslationEntry(
				groupId, infoItemReference.getClassName(),
				infoItemReference.getClassPK(), content, contentType,
				languageId, serviceContext);
		}
		catch (DocumentException documentException) {
			throw new XLIFFFileException.MustHaveCorrectEncoding(
				documentException);
		}
	}

	@Override
	public TranslationEntry addOrUpdateTranslationEntry(
			long groupId, String languageId,
			InfoItemReference infoItemReference,
			InfoItemFieldValues infoItemFieldValues,
			ServiceContext serviceContext)
		throws PortalException {

		_checkPermission(groupId, languageId, infoItemReference);

		return translationEntryLocalService.addOrUpdateTranslationEntry(
			groupId, languageId, infoItemReference, infoItemFieldValues,
			serviceContext);
	}

	@Override
	public TranslationEntry deleteTranslationEntry(long translationEntryId)
		throws PortalException {

		_modelResourcePermission.check(
			getPermissionChecker(), translationEntryId, ActionKeys.DELETE);

		return translationEntryLocalService.deleteTranslationEntry(
			translationEntryId);
	}

	private void _checkPermission(
			long groupId, String languageId,
			InfoItemReference infoItemReference)
		throws InfoItemPermissionException, PrincipalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		InfoItemPermissionProvider<JournalArticle> infoItemPermissionProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemPermissionProvider.class,
				infoItemReference.getClassName());

		if (!infoItemPermissionProvider.hasPermission(
				permissionChecker, infoItemReference, ActionKeys.UPDATE)) {

			String name = TranslationConstants.RESOURCE_NAME + "." + languageId;

			if (!_translationPermission.contains(
					permissionChecker, groupId, languageId,
					TranslationActionKeys.TRANSLATE)) {

				throw new PrincipalException.MustHavePermission(
					permissionChecker, name, name,
					TranslationActionKeys.TRANSLATE);
			}
		}
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

	@Reference(
		target = "(model.class.name=com.liferay.translation.model.TranslationEntry)"
	)
	private ModelResourcePermission<TranslationEntry> _modelResourcePermission;

	@Reference
	private SAXReader _saxReader;

	@Reference
	private TranslationPermission _translationPermission;

}
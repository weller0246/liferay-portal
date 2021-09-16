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

package com.liferay.style.book.service.impl;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.exception.DuplicateStyleBookEntryKeyException;
import com.liferay.style.book.exception.StyleBookEntryNameException;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.base.StyleBookEntryLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @see    StyleBookEntryLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.style.book.model.StyleBookEntry",
	service = AopService.class
)
public class StyleBookEntryLocalServiceImpl
	extends StyleBookEntryLocalServiceBaseImpl {

	@Override
	public StyleBookEntry addStyleBookEntry(
			long userId, long groupId, String name, String styleBookEntryKey,
			ServiceContext serviceContext)
		throws PortalException {

		return addStyleBookEntry(
			userId, groupId, StringPool.BLANK, name, styleBookEntryKey,
			serviceContext);
	}

	@Override
	public StyleBookEntry addStyleBookEntry(
			long userId, long groupId, String frontendTokensValues, String name,
			String styleBookEntryKey, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		if (serviceContext != null) {
			companyId = serviceContext.getCompanyId();
		}
		else {
			serviceContext = new ServiceContext();
		}

		_validate(name);

		if (Validator.isNull(styleBookEntryKey)) {
			styleBookEntryKey = generateStyleBookEntryKey(groupId, name);
		}
		else {
			styleBookEntryKey = _getStyleBookEntryKey(styleBookEntryKey);
		}

		_validateStyleBookEntryKey(groupId, styleBookEntryKey);

		StyleBookEntry styleBookEntry = create();

		String uuid = serviceContext.getUuid();

		if (Validator.isNotNull(uuid)) {
			styleBookEntry.setUuid(uuid);
		}

		styleBookEntry.setGroupId(groupId);
		styleBookEntry.setCompanyId(companyId);
		styleBookEntry.setUserId(user.getUserId());
		styleBookEntry.setUserName(user.getFullName());
		styleBookEntry.setCreateDate(serviceContext.getCreateDate(new Date()));
		styleBookEntry.setDefaultStyleBookEntry(false);
		styleBookEntry.setFrontendTokensValues(frontendTokensValues);
		styleBookEntry.setName(name);
		styleBookEntry.setStyleBookEntryKey(styleBookEntryKey);

		return publishDraft(styleBookEntry);
	}

	@Override
	public StyleBookEntry copyStyleBookEntry(
			long userId, long groupId, long styleBookEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		StyleBookEntry styleBookEntry = getStyleBookEntry(styleBookEntryId);

		String name = StringBundler.concat(
			styleBookEntry.getName(), StringPool.SPACE,
			StringPool.OPEN_PARENTHESIS,
			LanguageUtil.get(LocaleUtil.getMostRelevantLocale(), "copy"),
			StringPool.CLOSE_PARENTHESIS);

		StyleBookEntry copyStyleBookEntry = addStyleBookEntry(
			userId, groupId, styleBookEntry.getFrontendTokensValues(), name,
			StringPool.BLANK, serviceContext);

		long previewFileEntryId = _copyStyleBookEntryPreviewFileEntry(
			userId, groupId, styleBookEntry, copyStyleBookEntry);

		StyleBookEntry draftStyleBookEntry = fetchDraft(styleBookEntry);

		if (draftStyleBookEntry != null) {
			StyleBookEntry copyDraftStyleBookEntry = getDraft(
				copyStyleBookEntry);

			copyDraftStyleBookEntry.setFrontendTokensValues(
				draftStyleBookEntry.getFrontendTokensValues());

			updateDraft(copyDraftStyleBookEntry);
		}

		return updatePreviewFileEntryId(
			copyStyleBookEntry.getStyleBookEntryId(), previewFileEntryId);
	}

	@Override
	public StyleBookEntry deleteStyleBookEntry(long styleBookEntryId)
		throws PortalException {

		return deleteStyleBookEntry(getStyleBookEntry(styleBookEntryId));
	}

	@Override
	public StyleBookEntry deleteStyleBookEntry(StyleBookEntry styleBookEntry)
		throws PortalException {

		if (styleBookEntry.getPreviewFileEntryId() > 0) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				styleBookEntry.getPreviewFileEntryId());
		}

		return delete(styleBookEntry);
	}

	@Override
	public StyleBookEntry fetchDefaultStyleBookEntry(long groupId) {
		return styleBookEntryPersistence.fetchByG_D_Head_First(
			groupId, true, true, null);
	}

	@Override
	public StyleBookEntry fetchStyleBookEntry(
		long groupId, String styleBookEntryKey) {

		return styleBookEntryPersistence.fetchByG_SBEK_First(
			groupId, _getStyleBookEntryKey(styleBookEntryKey), null);
	}

	@Override
	public StyleBookEntry fetchStyleBookEntryByUuidAndGroupId(
		String uuid, long groupId) {

		StyleBookEntry styleBookEntry =
			styleBookEntryPersistence.fetchByUUID_G_Head(uuid, groupId, true);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		return styleBookEntryPersistence.fetchByUUID_G_Head(
			uuid, groupId, false);
	}

	@Override
	public String generateStyleBookEntryKey(long groupId, String name) {
		String styleBookEntryKey = _getStyleBookEntryKey(name);

		styleBookEntryKey = StringUtil.replace(
			styleBookEntryKey, CharPool.SPACE, CharPool.DASH);

		String curStyleBookEntryKey = styleBookEntryKey;

		int count = 0;

		while (true) {
			StyleBookEntry styleBookEntry = fetchStyleBookEntry(
				groupId, curStyleBookEntryKey);

			if (styleBookEntry == null) {
				return curStyleBookEntryKey;
			}

			curStyleBookEntryKey = styleBookEntryKey + CharPool.DASH + count++;
		}
	}

	@Override
	public List<StyleBookEntry> getStyleBookEntries(
		long groupId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return styleBookEntryPersistence.findByGroupId_Head(
			groupId, true, start, end, orderByComparator);
	}

	@Override
	public List<StyleBookEntry> getStyleBookEntries(
		long groupId, String name, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return styleBookEntryPersistence.findByG_LikeN_Head(
			groupId, _customSQL.keywords(name, false, WildcardMode.SURROUND)[0],
			true, start, end, orderByComparator);
	}

	@Override
	public List<StyleBookEntry> getStyleBookEntriesByUuidAndCompanyId(
		String uuid, long companyId) {

		return styleBookEntryPersistence.findByUuid_C(uuid, companyId);
	}

	@Override
	public int getStyleBookEntriesCount(long groupId) {
		return styleBookEntryPersistence.countByGroupId_Head(groupId, true);
	}

	@Override
	public int getStyleBookEntriesCount(long groupId, String name) {
		return styleBookEntryPersistence.countByG_LikeN_Head(
			groupId, _customSQL.keywords(name, false, WildcardMode.SURROUND)[0],
			true);
	}

	@Override
	public StyleBookEntry updateDefaultStyleBookEntry(
			long styleBookEntryId, boolean defaultStyleBookEntry)
		throws PortalException {

		StyleBookEntry styleBookEntry = fetchStyleBookEntry(styleBookEntryId);

		if (styleBookEntry == null) {
			return null;
		}

		StyleBookEntry oldDefaultStyleBookEntry =
			styleBookEntryPersistence.fetchByG_D_First(
				styleBookEntry.getGroupId(), true, null);

		if (defaultStyleBookEntry && (oldDefaultStyleBookEntry != null) &&
			(oldDefaultStyleBookEntry.getStyleBookEntryId() !=
				styleBookEntryId)) {

			oldDefaultStyleBookEntry.setDefaultStyleBookEntry(false);

			StyleBookEntry oldDefaultDraftStyleBookEntry = fetchDraft(
				oldDefaultStyleBookEntry);

			if (oldDefaultDraftStyleBookEntry != null) {
				oldDefaultDraftStyleBookEntry.setDefaultStyleBookEntry(
					defaultStyleBookEntry);

				updateDraft(oldDefaultDraftStyleBookEntry);
			}

			styleBookEntryPersistence.update(oldDefaultStyleBookEntry);
		}

		styleBookEntry.setModifiedDate(new Date());
		styleBookEntry.setDefaultStyleBookEntry(defaultStyleBookEntry);

		StyleBookEntry draftStyleBookEntry = fetchDraft(styleBookEntry);

		if (draftStyleBookEntry != null) {
			draftStyleBookEntry.setDefaultStyleBookEntry(defaultStyleBookEntry);

			updateDraft(draftStyleBookEntry);
		}

		return styleBookEntryPersistence.update(styleBookEntry);
	}

	@Override
	public StyleBookEntry updateFrontendTokensValues(
			long styleBookEntryId, String frontendTokensValues)
		throws PortalException {

		StyleBookEntry styleBookEntry =
			styleBookEntryPersistence.findByPrimaryKey(styleBookEntryId);

		styleBookEntry.setModifiedDate(new Date());
		styleBookEntry.setFrontendTokensValues(frontendTokensValues);

		StyleBookEntry draftStyleBookEntry = fetchDraft(styleBookEntry);

		if (draftStyleBookEntry != null) {
			draftStyleBookEntry.setModifiedDate(new Date());
			draftStyleBookEntry.setFrontendTokensValues(frontendTokensValues);

			updateDraft(draftStyleBookEntry);
		}

		return styleBookEntryPersistence.update(styleBookEntry);
	}

	@Override
	public StyleBookEntry updateName(long styleBookEntryId, String name)
		throws PortalException {

		StyleBookEntry styleBookEntry =
			styleBookEntryPersistence.findByPrimaryKey(styleBookEntryId);

		_validate(name);

		styleBookEntry.setModifiedDate(new Date());
		styleBookEntry.setName(name);

		StyleBookEntry draftStyleBookEntry = fetchDraft(styleBookEntry);

		if (draftStyleBookEntry != null) {
			draftStyleBookEntry.setModifiedDate(new Date());
			draftStyleBookEntry.setName(name);

			updateDraft(draftStyleBookEntry);
		}

		return styleBookEntryPersistence.update(styleBookEntry);
	}

	@Override
	public StyleBookEntry updatePreviewFileEntryId(
			long styleBookEntryId, long previewFileEntryId)
		throws PortalException {

		StyleBookEntry styleBookEntry =
			styleBookEntryPersistence.findByPrimaryKey(styleBookEntryId);

		styleBookEntry.setModifiedDate(new Date());
		styleBookEntry.setPreviewFileEntryId(previewFileEntryId);

		StyleBookEntry draftStyleBookEntry = fetchDraft(styleBookEntry);

		if (draftStyleBookEntry != null) {
			draftStyleBookEntry.setModifiedDate(new Date());
			draftStyleBookEntry.setPreviewFileEntryId(previewFileEntryId);

			updateDraft(draftStyleBookEntry);
		}

		return styleBookEntryPersistence.update(styleBookEntry);
	}

	@Override
	public StyleBookEntry updateStyleBookEntry(
			long userId, long styleBookEntryId, boolean defaultStylebookEntry,
			String frontendTokensValues, String name, String styleBookEntryKey,
			long previewFileEntryId)
		throws PortalException {

		StyleBookEntry styleBookEntry =
			styleBookEntryPersistence.findByPrimaryKey(styleBookEntryId);

		_validate(name);

		if (Validator.isNull(styleBookEntryKey)) {
			styleBookEntryKey = generateStyleBookEntryKey(
				styleBookEntry.getGroupId(), name);
		}
		else {
			styleBookEntryKey = _getStyleBookEntryKey(styleBookEntryKey);
		}

		if (!StringUtil.equals(
				_getStyleBookEntryKey(styleBookEntry.getStyleBookEntryKey()),
				styleBookEntryKey)) {

			_validateStyleBookEntryKey(
				styleBookEntry.getGroupId(), styleBookEntryKey);
		}

		styleBookEntry.setUserId(userId);
		styleBookEntry.setDefaultStyleBookEntry(defaultStylebookEntry);
		styleBookEntry.setFrontendTokensValues(frontendTokensValues);
		styleBookEntry.setName(name);
		styleBookEntry.setPreviewFileEntryId(previewFileEntryId);
		styleBookEntry.setStyleBookEntryKey(styleBookEntryKey);

		return styleBookEntryPersistence.update(styleBookEntry);
	}

	@Override
	public StyleBookEntry updateStyleBookEntry(
			long styleBookEntryId, String frontendTokensValues, String name)
		throws PortalException {

		StyleBookEntry styleBookEntry =
			styleBookEntryPersistence.findByPrimaryKey(styleBookEntryId);

		_validate(name);

		styleBookEntry.setModifiedDate(new Date());
		styleBookEntry.setFrontendTokensValues(frontendTokensValues);
		styleBookEntry.setName(name);

		StyleBookEntry draftStyleBookEntry = fetchDraft(styleBookEntry);

		if (draftStyleBookEntry != null) {
			draftStyleBookEntry.setModifiedDate(new Date());
			draftStyleBookEntry.setFrontendTokensValues(frontendTokensValues);
			draftStyleBookEntry.setName(name);

			updateDraft(draftStyleBookEntry);
		}

		return styleBookEntryPersistence.update(styleBookEntry);
	}

	private long _copyStyleBookEntryPreviewFileEntry(
			long userId, long groupId, StyleBookEntry styleBookEntry,
			StyleBookEntry copyStyleBookEntry)
		throws PortalException {

		if (styleBookEntry.getPreviewFileEntryId() == 0) {
			return 0;
		}

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			styleBookEntry.getPreviewFileEntryId());

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId, StyleBookPortletKeys.STYLE_BOOK);

		if (repository == null) {
			ServiceContext addPortletRepositoryServiceContext =
				new ServiceContext();

			addPortletRepositoryServiceContext.setAddGroupPermissions(true);
			addPortletRepositoryServiceContext.setAddGuestPermissions(true);

			repository = PortletFileRepositoryUtil.addPortletRepository(
				groupId, StyleBookPortletKeys.STYLE_BOOK,
				addPortletRepositoryServiceContext);
		}

		String fileName =
			copyStyleBookEntry.getStyleBookEntryId() + "_preview." +
				fileEntry.getExtension();

		fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, StyleBookEntry.class.getName(),
			copyStyleBookEntry.getStyleBookEntryId(),
			StyleBookPortletKeys.STYLE_BOOK, repository.getDlFolderId(),
			fileEntry.getContentStream(), fileName, fileEntry.getMimeType(),
			false);

		return fileEntry.getFileEntryId();
	}

	private String _getStyleBookEntryKey(String styleBookEntryKey) {
		if (styleBookEntryKey != null) {
			styleBookEntryKey = styleBookEntryKey.trim();

			return StringUtil.toLowerCase(styleBookEntryKey);
		}

		return StringPool.BLANK;
	}

	private void _validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new StyleBookEntryNameException("Name must not be null");
		}

		if (name.contains(StringPool.PERIOD) ||
			name.contains(StringPool.SLASH)) {

			throw new StyleBookEntryNameException(
				"Name contains invalid characters");
		}

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			StyleBookEntry.class.getName(), "name");

		if (name.length() > nameMaxLength) {
			throw new StyleBookEntryNameException(
				"Maximum length of name exceeded");
		}
	}

	private void _validateStyleBookEntryKey(
			long groupId, String styleBookEntryKey)
		throws PortalException {

		styleBookEntryKey = _getStyleBookEntryKey(styleBookEntryKey);

		StyleBookEntry styleBookEntry = fetchStyleBookEntry(
			groupId, styleBookEntryKey);

		if (styleBookEntry != null) {
			throw new DuplicateStyleBookEntryKeyException();
		}
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private DLAppLocalService _dlAppLocalService;

}
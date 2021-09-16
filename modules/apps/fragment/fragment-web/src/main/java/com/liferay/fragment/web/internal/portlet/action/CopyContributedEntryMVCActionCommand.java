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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCompositionService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.InputStream;

import java.net.URL;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/copy_contributed_entry"
	},
	service = MVCActionCommand.class
)
public class CopyContributedEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		sendRedirect(
			actionRequest, actionResponse,
			PortletURLBuilder.createRenderURL(
				_portal.getLiferayPortletResponse(actionResponse)
			).setParameter(
				"fragmentCollectionId",
				() -> {
					ServiceContext serviceContext =
						ServiceContextFactory.getInstance(actionRequest);

					ThemeDisplay themeDisplay =
						(ThemeDisplay)actionRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					String[] contributedEntryKeys = StringUtil.split(
						ParamUtil.getString(
							actionRequest, "contributedEntryKeys"));

					long fragmentCollectionId = ParamUtil.getLong(
						actionRequest, "fragmentCollectionId");

					for (String contributedEntryKey : contributedEntryKeys) {
						FragmentComposition fragmentComposition =
							_fragmentCollectionContributorTracker.
								getFragmentComposition(contributedEntryKey);

						FragmentEntry fragmentEntry =
							_fragmentCollectionContributorTracker.
								getFragmentEntry(contributedEntryKey);

						if (fragmentComposition != null) {
							_addFragmentComposition(
								fragmentCollectionId, fragmentComposition,
								serviceContext, themeDisplay);
						}
						else if (fragmentEntry != null) {
							_addFragmentEntry(
								fragmentCollectionId, fragmentEntry,
								serviceContext, themeDisplay);
						}
					}

					return fragmentCollectionId;
				}
			).buildString());
	}

	private void _addFragmentComposition(
			long fragmentCollectionId, FragmentComposition fragmentComposition,
			ServiceContext serviceContext, ThemeDisplay themeDisplay)
		throws PortalException {

		long previewFileEntryId = 0;

		String imagePreviewURL = fragmentComposition.getImagePreviewURL(
			themeDisplay);

		if (Validator.isNotNull(imagePreviewURL)) {
			previewFileEntryId = _getPreviewFileEntryId(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				fragmentCollectionId,
				themeDisplay.getPortalURL() + imagePreviewURL);
		}

		_fragmentCompositionService.addFragmentComposition(
			themeDisplay.getScopeGroupId(), fragmentCollectionId,
			StringPool.BLANK,
			StringBundler.concat(
				fragmentComposition.getName(), StringPool.SPACE,
				StringPool.OPEN_PARENTHESIS,
				LanguageUtil.get(LocaleUtil.getMostRelevantLocale(), "copy"),
				StringPool.CLOSE_PARENTHESIS),
			null, fragmentComposition.getData(), previewFileEntryId,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private void _addFragmentEntry(
			long fragmentCollectionId, FragmentEntry fragmentEntry,
			ServiceContext serviceContext, ThemeDisplay themeDisplay)
		throws PortalException {

		long previewFileEntryId = 0;

		String imagePreviewURL = fragmentEntry.getImagePreviewURL(themeDisplay);

		if (Validator.isNotNull(imagePreviewURL)) {
			previewFileEntryId = _getPreviewFileEntryId(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				fragmentCollectionId,
				themeDisplay.getPortalURL() + imagePreviewURL);
		}

		_fragmentEntryService.addFragmentEntry(
			themeDisplay.getScopeGroupId(), fragmentCollectionId,
			StringPool.BLANK,
			StringBundler.concat(
				fragmentEntry.getName(), StringPool.SPACE,
				StringPool.OPEN_PARENTHESIS,
				LanguageUtil.get(LocaleUtil.getMostRelevantLocale(), "copy"),
				StringPool.CLOSE_PARENTHESIS),
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
			previewFileEntryId, fragmentEntry.getType(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private long _getPreviewFileEntryId(
		long userId, long groupId, long fragmentCollectionId,
		String imagePreviewURL) {

		try {
			URL url = new URL(imagePreviewURL);

			byte[] bytes = null;

			try (InputStream inputStream = url.openStream()) {
				bytes = FileUtil.getBytes(inputStream);
			}

			String shortFileName = FileUtil.getShortFileName(imagePreviewURL);

			Repository repository =
				PortletFileRepositoryUtil.fetchPortletRepository(
					groupId, FragmentPortletKeys.FRAGMENT);

			if (repository == null) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);

				repository = PortletFileRepositoryUtil.addPortletRepository(
					groupId, FragmentPortletKeys.FRAGMENT, serviceContext);
			}

			FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
				groupId, userId, FragmentCollection.class.getName(),
				fragmentCollectionId, FragmentPortletKeys.FRAGMENT,
				repository.getDlFolderId(), bytes, shortFileName,
				MimeTypesUtil.getContentType(shortFileName), false);

			return fileEntry.getFileEntryId();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get preview entry image URL", exception);
			}
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopyContributedEntryMVCActionCommand.class);

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentCompositionService _fragmentCompositionService;

	@Reference
	private FragmentEntryService _fragmentEntryService;

	@Reference
	private Portal _portal;

}
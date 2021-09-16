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

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToMapConverter;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.journal.web.internal.security.permission.resource.JournalFolderPermission;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalEditArticleDisplayContext {

	public JournalEditArticleDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse, JournalArticle article) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_article = article;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_setViewAttributes();
	}

	public String getArticleId() {
		if (_article == null) {
			return null;
		}

		return _article.getArticleId();
	}

	public Set<Locale> getAvailableLocales() {
		if (_availableLocales != null) {
			return _availableLocales;
		}

		_availableLocales = LanguageUtil.getAvailableLocales(getGroupId());

		return _availableLocales;
	}

	public Map<String, Object> getChangeDefaultLanguageData() {
		return HashMapBuilder.<String, Object>put(
			"defaultLanguage", getDefaultArticleLanguageId()
		).put(
			"languages",
			() -> {
				List<Map<String, Object>> languages = new ArrayList<>();

				LinkedHashSet<String> uniqueLanguageIds = new LinkedHashSet<>();

				uniqueLanguageIds.add(getSelectedLanguageId());

				for (Locale availableLocale : getAvailableLocales()) {
					uniqueLanguageIds.add(
						LocaleUtil.toLanguageId(availableLocale));
				}

				for (String languageId : uniqueLanguageIds) {
					languages.add(
						HashMapBuilder.<String, Object>put(
							"icon",
							StringUtil.toLowerCase(
								StringUtil.replace(languageId, '_', '-'))
						).put(
							"label", languageId
						).build());
				}

				return languages;
			}
		).put(
			"strings",
			() -> {
				Map<String, Object> strings = new HashMap<>();

				Set<Locale> locales = new HashSet<>(getAvailableLocales());

				locales.add(
					LocaleUtil.fromLanguageId(getDefaultArticleLanguageId()));

				for (Locale locale : locales) {
					strings.put(
						LocaleUtil.toLanguageId(locale),
						StringBundler.concat(
							locale.getDisplayLanguage(), StringPool.SPACE,
							StringPool.OPEN_PARENTHESIS, locale.getCountry(),
							StringPool.CLOSE_PARENTHESIS));
				}

				return strings;
			}
		).build();
	}

	public long getClassNameId() {
		if (_classNameId != null) {
			return _classNameId;
		}

		_classNameId = BeanParamUtil.getLong(
			_article, _httpServletRequest, "classNameId");

		return _classNameId;
	}

	public long getClassPK() {
		if (_classPK != null) {
			return _classPK;
		}

		_classPK = BeanParamUtil.getLong(
			_article, _httpServletRequest, "classPK");

		return _classPK;
	}

	public Map<String, Object> getComponentContext() {
		return HashMapBuilder.<String, Object>put(
			"articleId", getArticleId()
		).put(
			"availableLocales", _getAvailableLanguageIds()
		).put(
			"classNameId", String.valueOf(getClassNameId())
		).put(
			"contentTitle", "titleMapAsXML"
		).put(
			"defaultLanguageId", getDefaultArticleLanguageId()
		).build();
	}

	public Map<String, Object> getDataEngineLayoutRendererComponentContext() {
		return HashMapBuilder.<String, Object>put(
			"currentLanguageId", getSelectedLanguageId()
		).build();
	}

	public DDMFormValues getDDMFormValues(DDMStructure ddmStructure)
		throws PortalException {

		if (_ddmFormValues != null) {
			return _ddmFormValues;
		}

		if (_article == null) {
			DDMFormValuesFactory ddmFormValuesFactory =
				_getDDMFormValuesFactory();

			return ddmFormValuesFactory.create(
				_httpServletRequest, ddmStructure.getDDMForm());
		}

		String content = _article.getContent();

		if (Validator.isNull(content) && _article.isNew() &&
			(getClassNameId() ==
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT)) {

			JournalArticle ddmStructureArticle =
				JournalArticleServiceUtil.getArticle(
					ddmStructure.getGroupId(), DDMStructure.class.getName(),
					ddmStructure.getStructureId());

			content = ddmStructureArticle.getContent();
		}

		if (Validator.isNull(content)) {
			return _ddmFormValues;
		}

		JournalConverter journalConverter = _getJournalConverter();

		Fields fields = journalConverter.getDDMFields(ddmStructure, content);

		if (fields == null) {
			return _ddmFormValues;
		}

		FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter =
			(FieldsToDDMFormValuesConverter)_httpServletRequest.getAttribute(
				FieldsToDDMFormValuesConverter.class.getName());

		_ddmFormValues = fieldsToDDMFormValuesConverter.convert(
			ddmStructure, fields);

		return _ddmFormValues;
	}

	public DDMStructure getDDMStructure() {
		if (_ddmStructure != null) {
			return _ddmStructure;
		}

		long ddmStructureId = ParamUtil.getLong(
			_httpServletRequest, "ddmStructureId");

		if (ddmStructureId > 0) {
			_ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
				ddmStructureId);
		}
		else if (Validator.isNotNull(getDDMStructureKey())) {
			long groupId = ParamUtil.getLong(
				_httpServletRequest, "groupId", _themeDisplay.getSiteGroupId());

			if (_article != null) {
				groupId = _article.getGroupId();
			}

			_ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
				groupId, PortalUtil.getClassNameId(JournalArticle.class),
				getDDMStructureKey(), true);
		}

		return _ddmStructure;
	}

	public long getDDMStructureId() {
		DDMStructure ddmStructure = getDDMStructure();

		return ddmStructure.getStructureId();
	}

	public String getDDMStructureKey() {
		if (_ddmStructureKey != null) {
			return _ddmStructureKey;
		}

		_ddmStructureKey = ParamUtil.getString(
			_httpServletRequest, "ddmStructureKey");

		if (Validator.isNull(_ddmStructureKey) && (_article != null)) {
			_ddmStructureKey = _article.getDDMStructureKey();
		}

		return _ddmStructureKey;
	}

	public DDMTemplate getDDMTemplate() throws PortalException {
		if (_ddmTemplate != null) {
			return _ddmTemplate;
		}

		long ddmTemplateId = ParamUtil.getLong(
			_httpServletRequest, "ddmTemplateId");

		if (ddmTemplateId == -1) {
			return null;
		}

		if (ddmTemplateId > 0) {
			_ddmTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(
				ddmTemplateId);

			return _ddmTemplate;
		}

		if (Validator.isNotNull(getDDMTemplateKey())) {
			long groupId = ParamUtil.getLong(
				_httpServletRequest, "groupId", _themeDisplay.getSiteGroupId());

			if (_article != null) {
				groupId = _article.getGroupId();
			}

			_ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
				groupId, PortalUtil.getClassNameId(DDMStructure.class),
				getDDMTemplateKey(), true);

			return _ddmTemplate;
		}

		if ((_article == null) && (_ddmTemplate == null)) {
			DDMStructure ddmStructure = getDDMStructure();

			List<DDMTemplate> ddmTemplates =
				DDMTemplateServiceUtil.getTemplates(
					_themeDisplay.getCompanyId(), ddmStructure.getGroupId(),
					PortalUtil.getClassNameId(DDMStructure.class),
					ddmStructure.getStructureId(),
					PortalUtil.getClassNameId(JournalArticle.class), true,
					WorkflowConstants.STATUS_APPROVED);

			if (!ddmTemplates.isEmpty()) {
				_ddmTemplate = ddmTemplates.get(0);

				return _ddmTemplate;
			}
		}

		return null;
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		_ddmTemplateKey = ParamUtil.getString(
			_httpServletRequest, "ddmTemplateKey");

		if (Validator.isNull(_ddmTemplateKey) && (_article != null) &&
			Objects.equals(
				_article.getDDMStructureKey(), getDDMStructureKey())) {

			_ddmTemplateKey = _article.getDDMTemplateKey();
		}

		return _ddmTemplateKey;
	}

	public String getDefaultArticleLanguageId() {
		if (_defaultArticleLanguageId != null) {
			return _defaultArticleLanguageId;
		}

		Locale siteDefaultLocale = null;

		try {
			siteDefaultLocale = PortalUtil.getSiteDefaultLocale(getGroupId());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			siteDefaultLocale = LocaleUtil.getSiteDefault();
		}

		if (Validator.isNull(getArticleId())) {
			_defaultArticleLanguageId = LocaleUtil.toLanguageId(
				siteDefaultLocale);
		}
		else {
			_defaultArticleLanguageId = LocalizationUtil.getDefaultLanguageId(
				_article.getContent(), siteDefaultLocale);
		}

		return _defaultArticleLanguageId;
	}

	public String getEditArticleURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/edit_article.jsp"
		).setRedirect(
			getRedirect()
		).setParameter(
			"articleId", getArticleId()
		).setParameter(
			"groupId", getGroupId()
		).setParameter(
			"version", getVersion()
		).buildString();
	}

	public long getFolderId() {
		if (_folderId != null) {
			return _folderId;
		}

		_folderId = BeanParamUtil.getLong(
			_article, _httpServletRequest, "folderId",
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _folderId;
	}

	public String getFolderName() {
		if (_folderName != null) {
			return _folderName;
		}

		if (JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID != getFolderId()) {
			JournalFolder folder =
				JournalFolderLocalServiceUtil.fetchJournalFolder(getFolderId());

			if (folder != null) {
				_folderName = folder.getName();

				return _folderName;
			}
		}

		_folderName = LanguageUtil.get(_httpServletRequest, "home");

		return _folderName;
	}

	public String getFriendlyURLBase() {
		StringBundler sb = new StringBundler(4);

		sb.append(_themeDisplay.getPortalURL());

		Group group = _themeDisplay.getScopeGroup();

		boolean privateLayout = false;

		if (_article != null) {
			Layout layout = _article.getLayout();

			if (layout != null) {
				privateLayout = layout.isPrivateLayout();
			}
		}

		if (privateLayout) {
			sb.append(PortalUtil.getPathFriendlyURLPrivateGroup());
		}
		else {
			sb.append(PortalUtil.getPathFriendlyURLPublic());
		}

		sb.append(group.getFriendlyURL());

		sb.append(JournalArticleConstants.CANONICAL_URL_SEPARATOR);

		return sb.toString();
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = BeanParamUtil.getLong(
			_article, _httpServletRequest, "groupId",
			_themeDisplay.getScopeGroupId());

		return _groupId;
	}

	public String getPortletResource() {
		if (_portletResource != null) {
			return _portletResource;
		}

		_portletResource = ParamUtil.getString(
			_httpServletRequest, "portletResource");

		return _portletResource;
	}

	public String getPublishButtonLabel() throws PortalException {
		if (getClassNameId() > JournalArticleConstants.CLASS_NAME_ID_DEFAULT) {
			return "save";
		}

		if (_isWorkflowEnabled()) {
			return "submit-for-publication";
		}

		return "publish";
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public long getRefererPlid() {
		if (_refererPlid != null) {
			return _refererPlid;
		}

		_refererPlid = ParamUtil.getLong(_httpServletRequest, "refererPlid");

		return _refererPlid;
	}

	public String getReferringPortletResource() {
		if (_referringPortletResource != null) {
			return _referringPortletResource;
		}

		_referringPortletResource = ParamUtil.getString(
			_httpServletRequest, "referringPortletResource");

		return _referringPortletResource;
	}

	public String getSaveButtonLabel() {
		if ((_article == null) || _article.isApproved() || _article.isDraft() ||
			_article.isExpired() || _article.isScheduled()) {

			return "save-as-draft";
		}

		return "save";
	}

	public String getSelectedLanguageId() {
		if (Validator.isNotNull(_defaultLanguageId)) {
			return _defaultLanguageId;
		}

		_defaultLanguageId = ParamUtil.getString(
			_httpServletRequest, "languageId");

		if (Validator.isNotNull(_defaultLanguageId)) {
			return _defaultLanguageId;
		}

		_defaultLanguageId = getDefaultArticleLanguageId();

		return _defaultLanguageId;
	}

	public String getSmallImageSource() {
		if (Validator.isNotNull(_smallImageSource)) {
			return _smallImageSource;
		}

		if (_article == null) {
			_smallImageSource = "none";

			return _smallImageSource;
		}

		_smallImageSource = ParamUtil.getString(
			_httpServletRequest, "smallImageSource");

		if (Validator.isNotNull(_smallImageSource)) {
			return _smallImageSource;
		}

		if (!_article.isSmallImage()) {
			_smallImageSource = "none";
		}
		else if (Validator.isNotNull(_article.getSmallImageURL())) {
			_smallImageSource = "url";
		}
		else if ((_article.getSmallImageId() > 0) &&
				 Validator.isNull(_article.getSmallImageURL())) {

			_smallImageSource = "file";
		}

		return _smallImageSource;
	}

	public Map<String, Object> getTemplateComponentContext() {
		return HashMapBuilder.<String, Object>put(
			"currentURL", _themeDisplay::getURLCurrent
		).put(
			"ddmTemplateId",
			() -> {
				DDMTemplate ddmTemplate = getDDMTemplate();

				if (ddmTemplate != null) {
					return String.valueOf(ddmTemplate.getTemplateId());
				}

				return "0";
			}
		).put(
			"editDDMTemplateURL",
			() -> PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCPath(
				"/edit_ddm_template.jsp"
			).setRedirect(
				_themeDisplay.getURLCurrent()
			).setParameter(
				"ddmTemplateId",
				() -> {
					DDMTemplate ddmTemplate = getDDMTemplate();

					if (ddmTemplate != null) {
						return ddmTemplate.getTemplateId();
					}

					return 0;
				}
			).buildString()
		).put(
			"previewArticleContentTemplateURL",
			() -> PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCPath(
				"/preview_article_content_template.jsp"
			).setParameter(
				"articleId", getArticleId()
			).setParameter(
				"groupId", getGroupId()
			).setParameter(
				"version", getVersion()
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).put(
			"selectDDMTemplateURL",
			() -> PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCPath(
				"/select_ddm_template.jsp"
			).setParameter(
				"ddmStructureId", _ddmStructure.getStructureId()
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).build();
	}

	public Map<String, Object> getValues(DDMStructure ddmStructure)
		throws PortalException {

		DDMFormValuesToMapConverter ddmFormValuesToMapConverter =
			(DDMFormValuesToMapConverter)_httpServletRequest.getAttribute(
				DDMFormValuesToMapConverter.class.getName());

		return ddmFormValuesToMapConverter.convert(
			getDDMFormValues(ddmStructure), ddmStructure);
	}

	public double getVersion() {
		if (_version != null) {
			return _version;
		}

		_version = BeanParamUtil.getDouble(
			_article, _httpServletRequest, "version",
			JournalArticleConstants.VERSION_DEFAULT);

		return _version;
	}

	public boolean hasSavePermission() throws PortalException {
		if ((_article != null) && !_article.isNew()) {
			return JournalArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), _article,
				ActionKeys.UPDATE);
		}

		return JournalFolderPermission.contains(
			_themeDisplay.getPermissionChecker(), getGroupId(), getFolderId(),
			ActionKeys.ADD_ARTICLE);
	}

	public boolean isApproved() {
		if ((_article != null) && (getVersion() > 0)) {
			return _article.isApproved();
		}

		return false;
	}

	public boolean isChangeStructure() {
		if (_changeStructure != null) {
			return _changeStructure;
		}

		_changeStructure = GetterUtil.getBoolean(
			ParamUtil.getString(_httpServletRequest, "changeStructure"));

		return _changeStructure;
	}

	public boolean isNeverExpire() {
		if (_neverExpire != null) {
			return _neverExpire;
		}

		_neverExpire = ParamUtil.getBoolean(
			_httpServletRequest, "neverExpire", true);

		if ((_article != null) && (_article.getExpirationDate() != null)) {
			_neverExpire = false;
		}

		return _neverExpire;
	}

	public boolean isNeverReview() {
		if (_neverReview != null) {
			return _neverReview;
		}

		_neverReview = ParamUtil.getBoolean(
			_httpServletRequest, "neverReview", true);

		if ((_article != null) && (_article.getReviewDate() != null)) {
			_neverReview = false;
		}

		return _neverReview;
	}

	public boolean isPending() throws PortalException {
		if ((_article != null) && (getVersion() > 0) && _isWorkflowEnabled()) {
			return _article.isPending();
		}

		return false;
	}

	public boolean isShowSelectFolder() {
		if (_showSelectFolder != null) {
			return _showSelectFolder;
		}

		_showSelectFolder = false;

		if (_article == null) {
			_showSelectFolder = ParamUtil.getBoolean(
				_httpServletRequest, "showSelectFolder", true);
		}

		return _showSelectFolder;
	}

	private String[] _getAvailableLanguageIds() {
		if (_article == null) {
			return new String[] {getDefaultArticleLanguageId()};
		}

		return _article.getAvailableLanguageIds();
	}

	private DDMFormValuesFactory _getDDMFormValuesFactory() {
		return (DDMFormValuesFactory)_httpServletRequest.getAttribute(
			DDMFormValuesFactory.class.getName());
	}

	private long _getInheritedWorkflowDDMStructuresFolderId()
		throws PortalException {

		if (_inheritedWorkflowDDMStructuresFolderId != null) {
			return _inheritedWorkflowDDMStructuresFolderId;
		}

		_inheritedWorkflowDDMStructuresFolderId =
			JournalFolderLocalServiceUtil.getInheritedWorkflowFolderId(
				getFolderId());

		return _inheritedWorkflowDDMStructuresFolderId;
	}

	private JournalConverter _getJournalConverter() {
		return (JournalConverter)_httpServletRequest.getAttribute(
			JournalWebKeys.JOURNAL_CONVERTER);
	}

	private String _getTitle() {
		if (getClassNameId() > JournalArticleConstants.CLASS_NAME_ID_DEFAULT) {
			return LanguageUtil.get(
				_httpServletRequest, "structure-default-values");
		}
		else if ((_article != null) && !_article.isNew()) {
			return _article.getTitle(_themeDisplay.getLocale());
		}

		return LanguageUtil.get(_httpServletRequest, "new-web-content");
	}

	private boolean _hasInheritedWorkflowDefinitionLink()
		throws PortalException {

		if (_getInheritedWorkflowDDMStructuresFolderId() <= 0) {
			return WorkflowDefinitionLinkLocalServiceUtil.
				hasWorkflowDefinitionLink(
					_themeDisplay.getCompanyId(), getGroupId(),
					JournalArticle.class.getName());
		}

		JournalFolder inheritedWorkflowDDMStructuresFolder =
			JournalFolderLocalServiceUtil.getFolder(
				_getInheritedWorkflowDDMStructuresFolderId());

		if (inheritedWorkflowDDMStructuresFolder.getRestrictionType() ==
				JournalFolderConstants.RESTRICTION_TYPE_INHERIT) {

			return true;
		}

		return false;
	}

	private boolean _isShowHeader() {
		if (_showHeader != null) {
			return _showHeader;
		}

		_showHeader = ParamUtil.getBoolean(
			_httpServletRequest, "showHeader", true);

		return _showHeader;
	}

	private boolean _isWorkflowEnabled() throws PortalException {
		if (getClassNameId() > JournalArticleConstants.CLASS_NAME_ID_DEFAULT) {
			return false;
		}

		if (_hasInheritedWorkflowDefinitionLink()) {
			return true;
		}

		DDMStructure ddmStructure = getDDMStructure();

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				_themeDisplay.getCompanyId(), getGroupId(),
				JournalFolder.class.getName(), getFolderId(),
				ddmStructure.getStructureId()) ||
			WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				_themeDisplay.getCompanyId(), getGroupId(),
				JournalFolder.class.getName(),
				_getInheritedWorkflowDDMStructuresFolderId(),
				ddmStructure.getStructureId()) ||
			WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				_themeDisplay.getCompanyId(), getGroupId(),
				JournalFolder.class.getName(),
				_getInheritedWorkflowDDMStructuresFolderId(),
				JournalArticleConstants.DDM_STRUCTURE_ID_ALL)) {

			return true;
		}

		return false;
	}

	private void _setViewAttributes() {
		if (!_isShowHeader()) {
			return;
		}

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);

		if (Validator.isNotNull(getRedirect())) {
			portletDisplay.setURLBack(getRedirect());
		}
		else if ((getClassNameId() ==
					JournalArticleConstants.CLASS_NAME_ID_DEFAULT) &&
				 (_article != null)) {

			portletDisplay.setURLBack(
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setParameter(
					"folderId", _article.getFolderId()
				).setParameter(
					"groupId", _article.getGroupId()
				).buildString());
		}

		if (_liferayPortletResponse instanceof RenderResponse) {
			RenderResponse renderResponse =
				(RenderResponse)_liferayPortletResponse;

			renderResponse.setTitle(_getTitle());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalEditArticleDisplayContext.class);

	private JournalArticle _article;
	private Set<Locale> _availableLocales;
	private Boolean _changeStructure;
	private Long _classNameId;
	private Long _classPK;
	private DDMFormValues _ddmFormValues;
	private DDMStructure _ddmStructure;
	private String _ddmStructureKey;
	private DDMTemplate _ddmTemplate;
	private String _ddmTemplateKey;
	private String _defaultArticleLanguageId;
	private String _defaultLanguageId;
	private Long _folderId;
	private String _folderName;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private Long _inheritedWorkflowDDMStructuresFolderId;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Boolean _neverExpire;
	private Boolean _neverReview;
	private String _portletResource;
	private String _redirect;
	private Long _refererPlid;
	private String _referringPortletResource;
	private Boolean _showHeader;
	private Boolean _showSelectFolder;
	private String _smallImageSource;
	private final ThemeDisplay _themeDisplay;
	private Double _version;

}
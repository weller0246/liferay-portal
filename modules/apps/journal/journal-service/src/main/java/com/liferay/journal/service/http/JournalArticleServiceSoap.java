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

package com.liferay.journal.service.http;

import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>JournalArticleServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.journal.model.JournalArticleSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.journal.model.JournalArticle</code>, that is translated to a
 * <code>com.liferay.journal.model.JournalArticleSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class JournalArticleServiceSoap {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addArticle(String, long, long, long, long, String, boolean,
	 Map, Map, Map, String, String, String, String, int, int, int,
	 int, int, int, int, int, int, int, boolean, int, int, int,
	 int, int, boolean, boolean, boolean, String, File, Map,
	 String, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.journal.model.JournalArticleSoap addArticle(
			long groupId, long folderId, long classNameId, long classPK,
			String articleId, boolean autoArticleId,
			String[] titleMapLanguageIds, String[] titleMapValues,
			String[] descriptionMapLanguageIds, String[] descriptionMapValues,
			String content, String ddmStructureKey, String ddmTemplateKey,
			String layoutUuid, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.addArticle(
					groupId, folderId, classNameId, classPK, articleId,
					autoArticleId, titleMap, descriptionMap, content,
					ddmStructureKey, ddmTemplateKey, layoutUuid,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, indexable, articleURL,
					serviceContext);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addArticle(String, long, long, Map, Map, String, String,
	 String, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.journal.model.JournalArticleSoap addArticle(
			long groupId, long folderId, String[] titleMapLanguageIds,
			String[] titleMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, String content,
			String ddmStructureKey, String ddmTemplateKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.addArticle(
					groupId, folderId, titleMap, descriptionMap, content,
					ddmStructureKey, ddmTemplateKey, serviceContext);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Adds a web content article.
	 *
	 * @param externalReferenceCode the external reference code of the web
	 content article
	 * @param groupId the primary key of the web content article's group
	 * @param folderId the primary key of the web content article folder
	 * @param titleMap the web content article's locales and localized titles
	 * @param descriptionMap the web content article's locales and localized
	 descriptions
	 * @param content the HTML content wrapped in XML. For more information,
	 see the content example in the {@link #updateArticle(long, long,
	 String, double, String, ServiceContext)} description.
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure, if the article is related to a DDM structure, or
	 <code>null</code> otherwise
	 * @param ddmTemplateKey the primary key of the web content article's DDM
	 template
	 * @param serviceContext the service context to be applied. Can set the
	 UUID, creation date, modification date, expando bridge
	 attributes, guest permissions, group permissions, asset category
	 IDs, asset tag names, asset link entry IDs, asset priority, URL
	 title, and workflow actions for the web content article. Can also
	 set whether to add the default guest and group permissions.
	 * @return the web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap addArticle(
			String externalReferenceCode, long groupId, long folderId,
			String[] titleMapLanguageIds, String[] titleMapValues,
			String[] descriptionMapLanguageIds, String[] descriptionMapValues,
			String content, String ddmStructureKey, String ddmTemplateKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.addArticle(
					externalReferenceCode, groupId, folderId, titleMap,
					descriptionMap, content, ddmStructureKey, ddmTemplateKey,
					serviceContext);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Copies the web content article matching the group, article ID, and
	 * version. This method creates a new article, extracting all the values
	 * from the old one and updating its article ID.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param oldArticleId the primary key of the old web content article
	 * @param newArticleId the primary key of the new web content article
	 * @param autoArticleId whether to auto-generate the web content article ID
	 * @param version the web content article's version
	 * @return the new web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap copyArticle(
			long groupId, String oldArticleId, String newArticleId,
			boolean autoArticleId, double version)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.copyArticle(
					groupId, oldArticleId, newArticleId, autoArticleId,
					version);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Deletes the web content article and its resources matching the group,
	 * article ID, and version, optionally sending email notifying denial of the
	 * web content article if it had not yet been approved.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @param version the web content article's version
	 * @param articleURL the web content article's accessible URL
	 * @param serviceContext the service context to be applied. Can set the
	 portlet preferences that include email information to notify
	 recipients of the unapproved web content article's denial.
	 * @throws PortalException if a portal exception occurred
	 */
	public static void deleteArticle(
			long groupId, String articleId, double version, String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			JournalArticleServiceUtil.deleteArticle(
				groupId, articleId, version, articleURL, serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Deletes all web content articles and their resources matching the group
	 * and article ID, optionally sending email notifying denial of article if
	 * it had not yet been approved.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @param articleURL the web content article's accessible URL
	 * @param serviceContext the service context to be applied. Can set the
	 portlet preferences that include email information to notify
	 recipients of the unapproved web content article's denial.
	 * @throws PortalException if a portal exception occurred
	 */
	public static void deleteArticle(
			long groupId, String articleId, String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			JournalArticleServiceUtil.deleteArticle(
				groupId, articleId, articleURL, serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteArticleDefaultValues(
			long groupId, String articleId, String ddmStructureKey)
		throws RemoteException {

		try {
			JournalArticleServiceUtil.deleteArticleDefaultValues(
				groupId, articleId, ddmStructureKey);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Expires the web content article matching the group, article ID, and
	 * version.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @param version the web content article's version
	 * @param articleURL the web content article's accessible URL
	 * @param serviceContext the service context to be applied. Can set the
	 modification date, status date, portlet preferences, and can set
	 whether to add the default command update for the web content
	 article. With respect to social activities, by setting the
	 service context's command to {@link
	 com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	 is considered a web content update activity; otherwise it is
	 considered a web content add activity.
	 * @return the web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap expireArticle(
			long groupId, String articleId, double version, String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.expireArticle(
					groupId, articleId, version, articleURL, serviceContext);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Expires the web content article matching the group and article ID,
	 * expiring all of its versions if the
	 * <code>journal.article.expire.all.versions</code> portal property is
	 * <code>true</code>, otherwise expiring only its latest approved version.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @param articleURL the web content article's accessible URL
	 * @param serviceContext the service context to be applied. Can set the
	 modification date, status date, portlet preferences, and can set
	 whether to add the default command update for the web content
	 article. With respect to social activities, by setting the
	 service context's command to {@link
	 com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	 is considered a web content update activity; otherwise it is
	 considered a web content add activity.
	 * @throws PortalException if a portal exception occurred
	 */
	public static void expireArticle(
			long groupId, String articleId, String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			JournalArticleServiceUtil.expireArticle(
				groupId, articleId, articleURL, serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalArticleSoap fetchArticle(
			long groupId, String articleId)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.fetchArticle(groupId, articleId);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the latest web content article matching the group and the
	 * external reference code.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param externalReferenceCode the external reference code of the web
	 content article
	 * @return the latest matching web content article, or <code>null</code> if
	 no matching web content article could be found
	 */
	public static com.liferay.journal.model.JournalArticleSoap
			fetchLatestArticleByExternalReferenceCode(
				long groupId, String externalReferenceCode)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.
					fetchLatestArticleByExternalReferenceCode(
						groupId, externalReferenceCode);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the web content article with the ID.
	 *
	 * @param id the primary key of the web content article
	 * @return the web content article with the ID
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap getArticle(
			long id)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.getArticle(id);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the latest approved web content article, or the latest unapproved
	 * article if none are approved. Both approved and unapproved articles must
	 * match the group and article ID.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @return the matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap getArticle(
			long groupId, String articleId)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.getArticle(groupId, articleId);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the web content article matching the group, article ID, and
	 * version.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @param version the web content article's version
	 * @return the matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap getArticle(
			long groupId, String articleId, double version)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.getArticle(
					groupId, articleId, version);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the web content article matching the group, class name, and class
	 * PK.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param className the DDMStructure class name if the web content article
	 is related to a DDM structure, the primary key of the class name
	 associated with the article, or
	 JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 module otherwise
	 * @param classPK the primary key of the DDM structure, if the DDMStructure
	 class name is given as the <code>className</code> parameter, the
	 primary key of the class associated with the web content article,
	 or <code>0</code> otherwise
	 * @return the matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap getArticle(
			long groupId, String className, long classPK)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.getArticle(
					groupId, className, classPK);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the latest web content article that is approved, or the latest
	 * unapproved article if none are approved. Both approved and unapproved
	 * articles must match the group and URL title.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param urlTitle the web content article's accessible URL title
	 * @return the matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap
			getArticleByUrlTitle(long groupId, String urlTitle)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.getArticleByUrlTitle(
					groupId, urlTitle);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalArticleSoap[] getArticles(
			long groupId, long folderId, String locale)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.getArticles(
					groupId, folderId, LocaleUtil.fromLanguageId(locale));

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalArticleSoap[] getArticles(
			long groupId, long folderId, String locale, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.getArticles(
					groupId, folderId, LocaleUtil.fromLanguageId(locale), start,
					end, orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group and article ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getArticlesByArticleId(
				long groupId, String articleId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.getArticlesByArticleId(
					groupId, articleId, start, end, orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns all the web content articles matching the group and layout UUID.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param layoutUuid the unique string identifying the web content
	 article's display page
	 * @return the matching web content articles
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getArticlesByLayoutUuid(long groupId, String layoutUuid)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.getArticlesByLayoutUuid(
					groupId, layoutUuid);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns all the web content articles that the user has permission to view
	 * matching the group and layout UUID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param layoutUuid the unique string identifying the web content
	 article's display page
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @return the range of matching web content articles
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getArticlesByLayoutUuid(
				long groupId, String layoutUuid, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.getArticlesByLayoutUuid(
					groupId, layoutUuid, start, end);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles that the user has permission
	 * to view matching the group and layout UUID.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param layoutUuid the unique string identifying the web content
	 article's display page
	 * @return the matching web content articles
	 */
	public static int getArticlesByLayoutUuidCount(
			long groupId, String layoutUuid)
		throws RemoteException {

		try {
			int returnValue =
				JournalArticleServiceUtil.getArticlesByLayoutUuidCount(
					groupId, layoutUuid);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group, class name ID, DDM structure key, and workflow status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param classNameId the primary key of the DDMStructure class if the web
	 content article is related to a DDM structure, the primary key of
	 the class name associated with the article, or
	 JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 module otherwise
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getArticlesByStructureId(
				long groupId, long classNameId, String ddmStructureKey,
				int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue =
					JournalArticleServiceUtil.getArticlesByStructureId(
						groupId, classNameId, ddmStructureKey, status, start,
						end, orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group, class name ID, DDM structure key, and workflow status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param classNameId the primary key of the DDMStructure class if the web
	 content article is related to a DDM structure, the primary key of
	 the class name associated with the article, or
	 JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 module otherwise
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getArticlesByStructureId(
				long groupId, long classNameId, String ddmStructureKey,
				String locale, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue =
					JournalArticleServiceUtil.getArticlesByStructureId(
						groupId, classNameId, ddmStructureKey,
						LocaleUtil.fromLanguageId(locale), status, start, end,
						orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group, default class name ID, and DDM structure key.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getArticlesByStructureId(
				long groupId, String ddmStructureKey, int status, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue =
					JournalArticleServiceUtil.getArticlesByStructureId(
						groupId, ddmStructureKey, status, start, end,
						orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group, default class name ID, and DDM structure key.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getArticlesByStructureId(
				long groupId, String ddmStructureKey, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue =
					JournalArticleServiceUtil.getArticlesByStructureId(
						groupId, ddmStructureKey, start, end,
						orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group, default class name ID, and DDM structure key.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure
	 * @param locale web content articles locale
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getArticlesByStructureId(
				long groupId, String ddmStructureKey, String locale, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue =
					JournalArticleServiceUtil.getArticlesByStructureId(
						groupId, ddmStructureKey,
						LocaleUtil.fromLanguageId(locale), status, start, end,
						orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles matching the group and folder.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param folderId the primary key of the web content article folder
	 * @return the number of matching web content articles
	 */
	public static int getArticlesCount(long groupId, long folderId)
		throws RemoteException {

		try {
			int returnValue = JournalArticleServiceUtil.getArticlesCount(
				groupId, folderId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles matching the group, folder,
	 * and status.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param folderId the primary key of the web content article's folder
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @return the number of matching web content articles
	 */
	public static int getArticlesCount(long groupId, long folderId, int status)
		throws RemoteException {

		try {
			int returnValue = JournalArticleServiceUtil.getArticlesCount(
				groupId, folderId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles matching the group and article
	 * ID.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @return the number of matching web content articles
	 */
	public static int getArticlesCountByArticleId(
			long groupId, String articleId)
		throws RemoteException {

		try {
			int returnValue =
				JournalArticleServiceUtil.getArticlesCountByArticleId(
					groupId, articleId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles matching the group, class name
	 * ID, DDM structure key, and workflow status.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param classNameId the primary key of the DDMStructure class if the web
	 content article is related to a DDM structure, the primary key of
	 the class name associated with the article, or
	 JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 module otherwise
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @return the number of matching web content articles
	 */
	public static int getArticlesCountByStructureId(
			long groupId, long classNameId, String ddmStructureKey, int status)
		throws RemoteException {

		try {
			int returnValue =
				JournalArticleServiceUtil.getArticlesCountByStructureId(
					groupId, classNameId, ddmStructureKey, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles matching the group, default
	 * class name ID, and DDM structure key.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure
	 * @return the number of matching web content articles
	 */
	public static int getArticlesCountByStructureId(
			long groupId, String ddmStructureKey)
		throws RemoteException {

		try {
			int returnValue =
				JournalArticleServiceUtil.getArticlesCountByStructureId(
					groupId, ddmStructureKey);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles matching the group, default
	 * class name ID, and DDM structure key.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @return the number of matching web content articles
	 */
	public static int getArticlesCountByStructureId(
			long groupId, String ddmStructureKey, int status)
		throws RemoteException {

		try {
			int returnValue =
				JournalArticleServiceUtil.getArticlesCountByStructureId(
					groupId, ddmStructureKey, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the web content article matching the URL title that is currently
	 * displayed or next to be displayed if no article is currently displayed.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param urlTitle the web content article's accessible URL title
	 * @return the web content article matching the URL title that is currently
	 displayed, or next one to be displayed if no version of the
	 article is currently displayed
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap
			getDisplayArticleByUrlTitle(long groupId, String urlTitle)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.getDisplayArticleByUrlTitle(
					groupId, urlTitle);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of folders containing web content articles belonging
	 * to the group.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param folderIds the primary keys of the web content article folders
	 (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @return the number of matching folders containing web content articles
	 */
	public static int getFoldersAndArticlesCount(long groupId, Long[] folderIds)
		throws RemoteException {

		try {
			int returnValue =
				JournalArticleServiceUtil.getFoldersAndArticlesCount(
					groupId, ListUtil.toList(folderIds));

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group, user, the root folder or any of its subfolders.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param userId the primary key of the user (optionally <code>0</code>)
	 * @param rootFolderId the primary key of the root folder to begin the
	 search
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param includeOwner whether to include the user's web content
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getGroupArticles(
				long groupId, long userId, long rootFolderId, int status,
				boolean includeOwner, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.getGroupArticles(
					groupId, userId, rootFolderId, status, includeOwner, start,
					end, orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group, user, the root folder or any of its subfolders.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param userId the primary key of the user (optionally <code>0</code>)
	 * @param rootFolderId the primary key of the root folder to begin the
	 search
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param includeOwner whether to include the user's web content
	 * @param locale web content articles locale
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getGroupArticles(
				long groupId, long userId, long rootFolderId, int status,
				boolean includeOwner, String locale, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.getGroupArticles(
					groupId, userId, rootFolderId, status, includeOwner,
					LocaleUtil.fromLanguageId(locale), start, end,
					orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group, user, the root folder or any of its subfolders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param userId the primary key of the user (optionally <code>0</code>)
	 * @param rootFolderId the primary key of the root folder to begin the
	 search
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getGroupArticles(
				long groupId, long userId, long rootFolderId, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.getGroupArticles(
					groupId, userId, rootFolderId, status, start, end,
					orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group, user, the root folder or any of its subfolders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param userId the primary key of the user (optionally <code>0</code>)
	 * @param rootFolderId the primary key of the root folder to begin the
	 search
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getGroupArticles(
				long groupId, long userId, long rootFolderId, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.getGroupArticles(
					groupId, userId, rootFolderId, start, end,
					orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles matching the group, user, and
	 * the root folder or any of its subfolders.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param userId the primary key of the user (optionally <code>0</code>)
	 * @param rootFolderId the primary key of the root folder to begin the
	 search
	 * @return the number of matching web content articles
	 * @throws PortalException if a portal exception occurred
	 */
	public static int getGroupArticlesCount(
			long groupId, long userId, long rootFolderId)
		throws RemoteException {

		try {
			int returnValue = JournalArticleServiceUtil.getGroupArticlesCount(
				groupId, userId, rootFolderId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles matching the group, user, and
	 * the root folder or any of its subfolders.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param userId the primary key of the user (optionally <code>0</code>)
	 * @param rootFolderId the primary key of the root folder to begin the
	 search
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @return the number of matching web content articles
	 * @throws PortalException if a portal exception occurred
	 */
	public static int getGroupArticlesCount(
			long groupId, long userId, long rootFolderId, int status)
		throws RemoteException {

		try {
			int returnValue = JournalArticleServiceUtil.getGroupArticlesCount(
				groupId, userId, rootFolderId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles matching the group, user, the
	 * root folder or any of its subfolders.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param userId the primary key of the user (optionally <code>0</code>)
	 * @param rootFolderId the primary key of the root folder to begin the
	 search
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param includeOwner whether to include the user's web content
	 * @return the range of matching web content articles ordered by the
	 comparator
	 * @throws PortalException if a portal exception occurred
	 */
	public static int getGroupArticlesCount(
			long groupId, long userId, long rootFolderId, int status,
			boolean includeOwner)
		throws RemoteException {

		try {
			int returnValue = JournalArticleServiceUtil.getGroupArticlesCount(
				groupId, userId, rootFolderId, status, includeOwner);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the latest web content article matching the resource primary key,
	 * preferring articles with approved workflow status.
	 *
	 * @param resourcePrimKey the primary key of the resource instance
	 * @return the latest web content article matching the resource primary key,
	 preferring articles with approved workflow status
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap getLatestArticle(
			long resourcePrimKey)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.getLatestArticle(resourcePrimKey);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the latest web content article matching the group, article ID,
	 * and workflow status.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @return the latest matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap getLatestArticle(
			long groupId, String articleId, int status)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.getLatestArticle(
					groupId, articleId, status);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the latest web content article matching the group, class name ID,
	 * and class PK.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param className the DDMStructure class name if the web content article
	 is related to a DDM structure, the class name associated with the
	 article, or JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the
	 journal-api module otherwise
	 * @param classPK the primary key of the DDM structure, if the DDMStructure
	 class name is given as the <code>className</code> parameter, the
	 primary key of the class associated with the web content article,
	 or <code>0</code> otherwise
	 * @return the latest matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap getLatestArticle(
			long groupId, String className, long classPK)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.getLatestArticle(
					groupId, className, classPK);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the latest web content article matching the group and the
	 * external reference code.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param externalReferenceCode the external reference code of the web
	 content article
	 * @return the latest matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap
			getLatestArticleByExternalReferenceCode(
				long groupId, String externalReferenceCode)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.
					getLatestArticleByExternalReferenceCode(
						groupId, externalReferenceCode);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalArticleSoap[]
			getLatestArticles(
				long groupId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.getLatestArticles(
					groupId, status, start, end, orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getLatestArticlesCount(long groupId, int status)
		throws RemoteException {

		try {
			int returnValue = JournalArticleServiceUtil.getLatestArticlesCount(
				groupId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns all the web content articles that the user has permission to view
	 * matching the group.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @return The matching web content articles
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getLayoutArticles(long groupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.getLayoutArticles(
					groupId);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns all the web content articles that the user has permission to view
	 * matching the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @return the range of matching web content articles
	 */
	public static com.liferay.journal.model.JournalArticleSoap[]
			getLayoutArticles(long groupId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.getLayoutArticles(
					groupId, start, end);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles that the user has permission
	 * to view matching the group.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @return the number of matching web content articles
	 */
	public static int getLayoutArticlesCount(long groupId)
		throws RemoteException {

		try {
			int returnValue = JournalArticleServiceUtil.getLayoutArticlesCount(
				groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Moves all versions of the web content article matching the group and
	 * article ID to the folder.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @param newFolderId the primary key of the web content article's new
	 folder
	 * @param serviceContext the service context to be applied. Can set the
	 user ID, language ID, portlet preferences, portlet request,
	 portlet response, theme display, and can set whether to add the
	 default command update for the web content article. With respect
	 to social activities, by setting the service context's command to
	 {@link com.liferay.portal.kernel.util.Constants#UPDATE}, the
	 invocation is considered a web content update activity; otherwise
	 it is considered a web content add activity.
	 * @throws PortalException if a portal exception occurred
	 */
	public static void moveArticle(
			long groupId, String articleId, long newFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			JournalArticleServiceUtil.moveArticle(
				groupId, articleId, newFolderId, serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Moves the web content article from the Recycle Bin to the folder.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param resourcePrimKey the primary key of the resource instance
	 * @param newFolderId the primary key of the web content article's new
	 folder
	 * @param serviceContext the service context to be applied. Can set the
	 modification date, portlet preferences, and can set whether to
	 add the default command update for the web content article. With
	 respect to social activities, by setting the service context's
	 command to {@link
	 com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	 is considered a web content update activity; otherwise it is
	 considered a web content add activity.
	 * @return the updated web content article, which was moved from the Recycle
	 Bin to the folder
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap
			moveArticleFromTrash(
				long groupId, long resourcePrimKey, long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.moveArticleFromTrash(
					groupId, resourcePrimKey, newFolderId, serviceContext);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Moves the web content article from the Recycle Bin to the folder.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @param newFolderId the primary key of the web content article's new
	 folder
	 * @param serviceContext the service context to be applied. Can set the
	 modification date, portlet preferences, and can set whether to
	 add the default command update for the web content article. With
	 respect to social activities, by setting the service context's
	 command to {@link
	 com.liferay.portal.kernel.util.Constants#UPDATE}, the invocation
	 is considered a web content update activity; otherwise it is
	 considered a web content add activity.
	 * @return the updated web content article, which was moved from the Recycle
	 Bin to the folder
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap
			moveArticleFromTrash(
				long groupId, String articleId, long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.moveArticleFromTrash(
					groupId, articleId, newFolderId, serviceContext);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Moves the latest version of the web content article matching the group
	 * and article ID to the recycle bin.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @return the moved web content article or <code>null</code> if no matching
	 article was found
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap
			moveArticleToTrash(long groupId, String articleId)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.moveArticleToTrash(
					groupId, articleId);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Removes the web content of all the company's web content articles
	 * matching the language.
	 *
	 * @param companyId the primary key of the web content article's company
	 * @param languageId the primary key of the language locale to remove
	 * @throws PortalException if a portal exception occurred
	 */
	public static void removeArticleLocale(long companyId, String languageId)
		throws RemoteException {

		try {
			JournalArticleServiceUtil.removeArticleLocale(
				companyId, languageId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Removes the web content of the web content article matching the group,
	 * article ID, and version, and language.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @param version the web content article's version
	 * @param languageId the primary key of the language locale to remove
	 * @return the updated web content article with the locale removed
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap
			removeArticleLocale(
				long groupId, String articleId, double version,
				String languageId)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.removeArticleLocale(
					groupId, articleId, version, languageId);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Restores the web content article associated with the resource primary key
	 * from the Recycle Bin.
	 *
	 * @param resourcePrimKey the primary key of the resource instance
	 * @throws PortalException if a portal exception occurred
	 */
	public static void restoreArticleFromTrash(long resourcePrimKey)
		throws RemoteException {

		try {
			JournalArticleServiceUtil.restoreArticleFromTrash(resourcePrimKey);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Restores the web content article from the Recycle Bin.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static void restoreArticleFromTrash(long groupId, String articleId)
		throws RemoteException {

		try {
			JournalArticleServiceUtil.restoreArticleFromTrash(
				groupId, articleId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * parameters, including a keywords parameter for matching with the
	 * article's ID, title, description, and content, a DDM structure key
	 * parameter, and a DDM template key parameter.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the web content article's company
	 * @param groupId the primary key of the group (optionally <code>0</code>)
	 * @param folderIds the primary keys of the web content article folders
	 (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @param classNameId the primary key of the DDMStructure class if the web
	 content article is related to a DDM structure, the primary key of
	 the class name associated with the article, or
	 JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 module otherwise
	 * @param keywords the keywords (space separated), which may occur in the
	 web content article ID, title, description, or content
	 (optionally <code>null</code>). If the keywords value is not
	 <code>null</code>, the search uses the OR operator in connecting
	 query criteria; otherwise it uses the AND operator.
	 * @param version the web content article's version (optionally
	 <code>null</code>)
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure, if the article is related to a DDM structure, or
	 <code>null</code> otherwise
	 * @param ddmTemplateKey the primary key of the web content article's DDM
	 template
	 * @param displayDateGT the date after which a matching web content
	 article's display date must be after (optionally
	 <code>null</code>)
	 * @param displayDateLT the date before which a matching web content
	 article's display date must be before (optionally
	 <code>null</code>)
	 * @param reviewDate the web content article's scheduled review date
	 (optionally <code>null</code>)
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 */
	public static com.liferay.journal.model.JournalArticleSoap[] search(
			long companyId, long groupId, Long[] folderIds, long classNameId,
			String keywords, Double version, String ddmStructureKey,
			String ddmTemplateKey, java.util.Date displayDateGT,
			java.util.Date displayDateLT, java.util.Date reviewDate, int status,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.search(
					companyId, groupId, ListUtil.toList(folderIds), classNameId,
					keywords, version, ddmStructureKey, ddmTemplateKey,
					displayDateGT, displayDateLT, reviewDate, status, start,
					end, orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * parameters, including keyword parameters for article ID, title,
	 * description, and content, a DDM structure key parameter, a DDM template
	 * key parameter, and an AND operator switch.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the web content article's company
	 * @param groupId the primary key of the group (optionally <code>0</code>)
	 * @param folderIds the primary keys of the web content article folders
	 (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @param classNameId the primary key of the DDMStructure class if the web
	 content article is related to a DDM structure, the primary key of
	 the class name associated with the article, or
	 JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 module otherwise
	 * @param articleId the article ID keywords (space separated, optionally
	 <code>null</code>)
	 * @param version the web content article's version (optionally
	 <code>null</code>)
	 * @param title the title keywords (space separated, optionally
	 <code>null</code>)
	 * @param description the description keywords (space separated, optionally
	 <code>null</code>)
	 * @param content the content keywords (space separated, optionally
	 <code>null</code>)
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure, if the article is related to a DDM structure, or
	 <code>null</code> otherwise
	 * @param ddmTemplateKey the primary key of the web content article's DDM
	 template
	 * @param displayDateGT the date after which a matching web content
	 article's display date must be after (optionally
	 <code>null</code>)
	 * @param displayDateLT the date before which a matching web content
	 article's display date must be before (optionally
	 <code>null</code>)
	 * @param reviewDate the web content article's scheduled review date
	 (optionally <code>null</code>)
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param andOperator whether every field must match its value or keywords,
	 or just one field must match. Company, group, folder IDs, class
	 name ID, and status must all match their values.
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 */
	public static com.liferay.journal.model.JournalArticleSoap[] search(
			long companyId, long groupId, Long[] folderIds, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String ddmStructureKey, String ddmTemplateKey,
			java.util.Date displayDateGT, java.util.Date displayDateLT,
			java.util.Date reviewDate, int status, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.search(
					companyId, groupId, ListUtil.toList(folderIds), classNameId,
					articleId, version, title, description, content,
					ddmStructureKey, ddmTemplateKey, displayDateGT,
					displayDateLT, reviewDate, status, andOperator, start, end,
					orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * parameters, including keyword parameters for article ID, title,
	 * description, and content, a DDM structure keys (plural) parameter, a DDM
	 * template keys (plural) parameter, and an AND operator switch.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the web content article's company
	 * @param groupId the primary key of the group (optionally <code>0</code>)
	 * @param folderIds the primary keys of the web content article folders
	 (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @param classNameId the primary key of the DDMStructure class if the web
	 content article is related to a DDM structure, the primary key of
	 the class name associated with the article, or
	 JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 module otherwise
	 * @param articleId the article ID keywords (space separated, optionally
	 <code>null</code>)
	 * @param version the web content article's version (optionally
	 <code>null</code>)
	 * @param title the title keywords (space separated, optionally
	 <code>null</code>)
	 * @param description the description keywords (space separated, optionally
	 <code>null</code>)
	 * @param content the content keywords (space separated, optionally
	 <code>null</code>)
	 * @param ddmStructureKeys the primary keys of the web content article's
	 DDM structures, if the article is related to a DDM structure, or
	 <code>null</code> otherwise
	 * @param ddmTemplateKeys the primary keys of the web content article's DDM
	 templates (originally <code>null</code>). If the articles are
	 related to a DDM structure, the template's structure must match
	 it.
	 * @param displayDateGT the date after which a matching web content
	 article's display date must be after (optionally
	 <code>null</code>)
	 * @param displayDateLT the date before which a matching web content
	 article's display date must be before (optionally
	 <code>null</code>)
	 * @param reviewDate the web content article's scheduled review date
	 (optionally <code>null</code>)
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param andOperator whether every field must match its value or keywords,
	 or just one field must match.  Company, group, folder IDs, class
	 name ID, and status must all match their values.
	 * @param start the lower bound of the range of web content articles to
	 return
	 * @param end the upper bound of the range of web content articles to
	 return (not inclusive)
	 * @param orderByComparator the comparator to order the web content
	 articles
	 * @return the range of matching web content articles ordered by the
	 comparator
	 */
	public static com.liferay.journal.model.JournalArticleSoap[] search(
			long companyId, long groupId, Long[] folderIds, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String[] ddmStructureKeys, String[] ddmTemplateKeys,
			java.util.Date displayDateGT, java.util.Date displayDateLT,
			java.util.Date reviewDate, int status, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalArticle>
				returnValue = JournalArticleServiceUtil.search(
					companyId, groupId, ListUtil.toList(folderIds), classNameId,
					articleId, version, title, description, content,
					ddmStructureKeys, ddmTemplateKeys, displayDateGT,
					displayDateLT, reviewDate, status, andOperator, start, end,
					orderByComparator);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles matching the parameters,
	 * including a keywords parameter for matching with the article's ID, title,
	 * description, and content, a DDM structure key parameter, and a DDM
	 * template key parameter.
	 *
	 * @param companyId the primary key of the web content article's company
	 * @param groupId the primary key of the group (optionally <code>0</code>)
	 * @param folderIds the primary keys of the web content article folders
	 (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @param classNameId the primary key of the DDMStructure class if the web
	 content article is related to a DDM structure, the primary key of
	 the class name associated with the article, or
	 JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 module otherwise
	 * @param keywords the keywords (space separated), which may occur in the
	 web content article ID, title, description, or content
	 (optionally <code>null</code>). If the keywords value is not
	 <code>null</code>, the search uses the OR operator in connecting
	 query criteria; otherwise it uses the AND operator.
	 * @param version the web content article's version (optionally
	 <code>null</code>)
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure, if the article is related to a DDM structure, or
	 <code>null</code> otherwise
	 * @param ddmTemplateKey the primary key of the web content article's DDM
	 template
	 * @param displayDateGT the date after which a matching web content
	 article's display date must be after (optionally
	 <code>null</code>)
	 * @param displayDateLT the date before which a matching web content
	 article's display date must be before (optionally
	 <code>null</code>)
	 * @param reviewDate the web content article's scheduled review date
	 (optionally <code>null</code>)
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @return the number of matching web content articles
	 */
	public static int searchCount(
			long companyId, long groupId, Long[] folderIds, long classNameId,
			String keywords, Double version, String ddmStructureKey,
			String ddmTemplateKey, java.util.Date displayDateGT,
			java.util.Date displayDateLT, java.util.Date reviewDate, int status)
		throws RemoteException {

		try {
			int returnValue = JournalArticleServiceUtil.searchCount(
				companyId, groupId, ListUtil.toList(folderIds), classNameId,
				keywords, version, ddmStructureKey, ddmTemplateKey,
				displayDateGT, displayDateLT, reviewDate, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles matching the parameters,
	 * including keyword parameters for article ID, title, description, and
	 * content, a DDM structure key parameter, a DDM template key parameter, and
	 * an AND operator switch.
	 *
	 * @param companyId the primary key of the web content article's company
	 * @param groupId the primary key of the group (optionally <code>0</code>)
	 * @param folderIds the primary keys of the web content article folders
	 (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @param classNameId the primary key of the DDMStructure class if the web
	 content article is related to a DDM structure, the primary key of
	 the class name associated with the article, or
	 JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 module otherwise
	 * @param articleId the article ID keywords (space separated, optionally
	 <code>null</code>)
	 * @param version the web content article's version (optionally
	 <code>null</code>)
	 * @param title the title keywords (space separated, optionally
	 <code>null</code>)
	 * @param description the description keywords (space separated, optionally
	 <code>null</code>)
	 * @param content the content keywords (space separated, optionally
	 <code>null</code>)
	 * @param ddmStructureKey the primary key of the web content article's DDM
	 structure, if the article is related to a DDM structure, or
	 <code>null</code> otherwise
	 * @param ddmTemplateKey the primary key of the web content article's DDM
	 template
	 * @param displayDateGT the date after which a matching web content
	 article's display date must be after (optionally
	 <code>null</code>)
	 * @param displayDateLT the date before which a matching web content
	 article's display date must be before (optionally
	 <code>null</code>)
	 * @param reviewDate the web content article's scheduled review date
	 (optionally <code>null</code>)
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param andOperator whether every field must match its value or keywords,
	 or just one field must match. Group, folder IDs, class name ID,
	 and status must all match their values.
	 * @return the number of matching web content articles
	 */
	public static int searchCount(
			long companyId, long groupId, Long[] folderIds, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String ddmStructureKey, String ddmTemplateKey,
			java.util.Date displayDateGT, java.util.Date displayDateLT,
			java.util.Date reviewDate, int status, boolean andOperator)
		throws RemoteException {

		try {
			int returnValue = JournalArticleServiceUtil.searchCount(
				companyId, groupId, ListUtil.toList(folderIds), classNameId,
				articleId, version, title, description, content,
				ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
				reviewDate, status, andOperator);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of web content articles matching the parameters,
	 * including keyword parameters for article ID, title, description, and
	 * content, a DDM structure keys (plural) parameter, a DDM template keys
	 * (plural) parameter, and an AND operator switch.
	 *
	 * @param companyId the primary key of the web content article's company
	 * @param groupId the primary key of the group (optionally <code>0</code>)
	 * @param folderIds the primary keys of the web content article folders
	 (optionally {@link java.util.Collections#EMPTY_LIST})
	 * @param classNameId the primary key of the DDMStructure class if the web
	 content article is related to a DDM structure, the primary key of
	 the class name associated with the article, or
	 JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 module otherwise
	 * @param articleId the article ID keywords (space separated, optionally
	 <code>null</code>)
	 * @param version the web content article's version (optionally
	 <code>null</code>)
	 * @param title the title keywords (space separated, optionally
	 <code>null</code>)
	 * @param description the description keywords (space separated, optionally
	 <code>null</code>)
	 * @param content the content keywords (space separated, optionally
	 <code>null</code>)
	 * @param ddmStructureKeys the primary keys of the web content article's
	 DDM structures, if the article is related to a DDM structure, or
	 <code>null</code> otherwise
	 * @param ddmTemplateKeys the primary keys of the web content article's DDM
	 templates (originally <code>null</code>). If the articles are
	 related to a DDM structure, the template's structure must match
	 it.
	 * @param displayDateGT the date after which a matching web content
	 article's display date must be after (optionally
	 <code>null</code>)
	 * @param displayDateLT the date before which a matching web content
	 article's display date must be before (optionally
	 <code>null</code>)
	 * @param reviewDate the web content article's scheduled review date
	 (optionally <code>null</code>)
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param andOperator whether every field must match its value or keywords,
	 or just one field must match.  Group, folder IDs, class name ID,
	 and status must all match their values.
	 * @return the number of matching web content articles
	 */
	public static int searchCount(
			long companyId, long groupId, Long[] folderIds, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String[] ddmStructureKeys, String[] ddmTemplateKeys,
			java.util.Date displayDateGT, java.util.Date displayDateLT,
			java.util.Date reviewDate, int status, boolean andOperator)
		throws RemoteException {

		try {
			int returnValue = JournalArticleServiceUtil.searchCount(
				companyId, groupId, ListUtil.toList(folderIds), classNameId,
				articleId, version, title, description, content,
				ddmStructureKeys, ddmTemplateKeys, displayDateGT, displayDateLT,
				reviewDate, status, andOperator);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void subscribe(long groupId, long articleId)
		throws RemoteException {

		try {
			JournalArticleServiceUtil.subscribe(groupId, articleId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Subscribes the user to changes in elements that belong to the web content
	 * article's DDM structure.
	 *
	 * @param groupId the primary key of the folder's group
	 * @param userId the primary key of the user to be subscribed
	 * @param ddmStructureId the primary key of the structure to subscribe to
	 * @throws PortalException if a portal exception occurred
	 */
	public static void subscribeStructure(
			long groupId, long userId, long ddmStructureId)
		throws RemoteException {

		try {
			JournalArticleServiceUtil.subscribeStructure(
				groupId, userId, ddmStructureId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void unsubscribe(long groupId, long articleId)
		throws RemoteException {

		try {
			JournalArticleServiceUtil.unsubscribe(groupId, articleId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Unsubscribes the user from changes in elements that belong to the web
	 * content article's DDM structure.
	 *
	 * @param groupId the primary key of the folder's group
	 * @param userId the primary key of the user to be subscribed
	 * @param ddmStructureId the primary key of the structure to subscribe to
	 * @throws PortalException if a portal exception occurred
	 */
	public static void unsubscribeStructure(
			long groupId, long userId, long ddmStructureId)
		throws RemoteException {

		try {
			JournalArticleServiceUtil.unsubscribeStructure(
				groupId, userId, ddmStructureId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Updates the web content article matching the version, replacing its
	 * folder, title, description, content, and layout UUID.
	 *
	 * @param userId the primary key of the user updating the web content
	 article
	 * @param groupId the primary key of the web content article's group
	 * @param folderId the primary key of the web content article folder
	 * @param articleId the primary key of the web content article
	 * @param version the web content article's version
	 * @param titleMap the web content article's locales and localized titles
	 * @param descriptionMap the web content article's locales and localized
	 descriptions
	 * @param content the HTML content wrapped in XML. For more information,
	 see the content example in the {@link #updateArticle(long, long,
	 String, double, String, ServiceContext)} description.
	 * @param layoutUuid the unique string identifying the web content
	 article's display page
	 * @param serviceContext the service context to be applied. Can set the
	 modification date, expando bridge attributes, asset category IDs,
	 asset tag names, asset link entry IDs, asset priority, workflow
	 actions, URL title, and can set whether to add the default
	 command update for the web content article. With respect to
	 social activities, by setting the service context's command to
	 {@link com.liferay.portal.kernel.util.Constants#UPDATE}, the
	 invocation is considered a web content update activity; otherwise
	 it is considered a web content add activity.
	 * @return the updated web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, String[] titleMapLanguageIds,
			String[] titleMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, String content, String layoutUuid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.updateArticle(
					userId, groupId, folderId, articleId, version, titleMap,
					descriptionMap, content, layoutUuid, serviceContext);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Updates the web content article matching the version, replacing its
	 * folder and content.
	 *
	 * <p>
	 * The web content articles hold HTML content wrapped in XML. The XML lets
	 * you specify the article's default locale and available locales. Here is a
	 * content example:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * &lt;?xml version='1.0' encoding='UTF-8'?&gt;
	 * &lt;root default-locale="en_US" available-locales="en_US"&gt;
	 * 	&lt;static-content language-id="en_US"&gt;
	 * 		&lt;![CDATA[&lt;p&gt;&lt;b&gt;&lt;i&gt;test&lt;i&gt; content&lt;b&gt;&lt;/p&gt;]]&gt;
	 * 	&lt;/static-content&gt;
	 * &lt;/root&gt;
	 * </code>
	 * </pre></p>
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param folderId the primary key of the web content article folder
	 * @param articleId the primary key of the web content article
	 * @param version the web content article's version
	 * @param content the HTML content wrapped in XML.
	 * @param serviceContext the service context to be applied. Can set the
	 modification date, expando bridge attributes, asset category IDs,
	 asset tag names, asset link entry IDs, asset priority, workflow
	 actions, URL title, and can set whether to add the default
	 command update for the web content article. With respect to
	 social activities, by setting the service context's command to
	 {@link com.liferay.portal.kernel.util.Constants#UPDATE}, the
	 invocation is considered a web content update activity; otherwise
	 it is considered a web content add activity.
	 * @return the updated web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap updateArticle(
			long groupId, long folderId, String articleId, double version,
			String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.updateArticle(
					groupId, folderId, articleId, version, content,
					serviceContext);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Updates the workflow status of the web content article matching the
	 * group, article ID, and version.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param articleId the primary key of the web content article
	 * @param version the web content article's version
	 * @param status the web content article's workflow status. For more
	 information see {@link WorkflowConstants} for constants starting
	 with the "STATUS_" prefix.
	 * @param articleURL the web content article's accessible URL
	 * @param serviceContext the service context to be applied. Can set the
	 modification date, portlet preferences, and can set whether to
	 add the default command update for the web content article.
	 * @return the updated web content article
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.journal.model.JournalArticleSoap updateStatus(
			long groupId, String articleId, double version, int status,
			String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalArticle returnValue =
				JournalArticleServiceUtil.updateStatus(
					groupId, articleId, version, status, articleURL,
					serviceContext);

			return com.liferay.journal.model.JournalArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalArticleServiceSoap.class);

}
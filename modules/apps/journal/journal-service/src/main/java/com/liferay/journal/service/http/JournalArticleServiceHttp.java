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
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>JournalArticleServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleServiceSoap
 * @generated
 */
public class JournalArticleServiceHttp {

	public static com.liferay.journal.model.JournalArticle addArticle(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			long classNameId, long classPK, String articleId,
			boolean autoArticleId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			String layoutUuid, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			boolean smallImage, String smallImageURL, java.io.File smallFile,
			java.util.Map<String, byte[]> images, String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "addArticle",
				_addArticleParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, classNameId, classPK, articleId,
				autoArticleId, titleMap, descriptionMap, friendlyURLMap,
				content, ddmStructureKey, ddmTemplateKey, layoutUuid,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, reviewDateMonth,
				reviewDateDay, reviewDateYear, reviewDateHour, reviewDateMinute,
				neverReview, indexable, smallImage, smallImageURL, smallFile,
				images, articleURL, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle addArticle(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			long classNameId, long classPK, String articleId,
			boolean autoArticleId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			String layoutUuid, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			boolean smallImage, String smallImageURL, java.io.File smallFile,
			java.util.Map<String, byte[]> images, String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "addArticle",
				_addArticleParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, classNameId, classPK, articleId,
				autoArticleId, titleMap, descriptionMap, content,
				ddmStructureKey, ddmTemplateKey, layoutUuid, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
				reviewDateHour, reviewDateMinute, neverReview, indexable,
				smallImage, smallImageURL, smallFile, images, articleURL,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle addArticle(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			long classNameId, long classPK, String articleId,
			boolean autoArticleId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
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
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "addArticle",
				_addArticleParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, classNameId, classPK, articleId,
				autoArticleId, titleMap, descriptionMap, content,
				ddmStructureKey, ddmTemplateKey, layoutUuid, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
				reviewDateHour, reviewDateMinute, neverReview, indexable,
				articleURL, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle addArticle(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "addArticle",
				_addArticleParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, titleMap, descriptionMap, content,
				ddmStructureKey, ddmTemplateKey, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle addArticle(
			HttpPrincipal httpPrincipal, String externalReferenceCode,
			long groupId, long folderId, long classNameId, long classPK,
			String articleId, boolean autoArticleId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			String layoutUuid, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			boolean smallImage, String smallImageURL, java.io.File smallFile,
			java.util.Map<String, byte[]> images, String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "addArticle",
				_addArticleParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, folderId,
				classNameId, classPK, articleId, autoArticleId, titleMap,
				descriptionMap, friendlyURLMap, content, ddmStructureKey,
				ddmTemplateKey, layoutUuid, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, neverReview, indexable, smallImage,
				smallImageURL, smallFile, images, articleURL, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle addArticle(
			HttpPrincipal httpPrincipal, String externalReferenceCode,
			long groupId, long folderId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "addArticle",
				_addArticleParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, folderId, titleMap,
				descriptionMap, content, ddmStructureKey, ddmTemplateKey,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle
			addArticleDefaultValues(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				long classPK, java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String content, String ddmStructureKey, String ddmTemplateKey,
				String layoutUuid, boolean indexable, boolean smallImage,
				String smallImageURL, java.io.File smallImageFile,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "addArticleDefaultValues",
				_addArticleDefaultValuesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, classPK, titleMap,
				descriptionMap, content, ddmStructureKey, ddmTemplateKey,
				layoutUuid, indexable, smallImage, smallImageURL,
				smallImageFile, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle copyArticle(
			HttpPrincipal httpPrincipal, long groupId, String oldArticleId,
			String newArticleId, boolean autoArticleId, double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "copyArticle",
				_copyArticleParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, oldArticleId, newArticleId, autoArticleId,
				version);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteArticle(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			double version, String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "deleteArticle",
				_deleteArticleParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, version, articleURL,
				serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteArticle(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "deleteArticle",
				_deleteArticleParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, articleURL, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteArticleDefaultValues(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			String ddmStructureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "deleteArticleDefaultValues",
				_deleteArticleDefaultValuesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, ddmStructureKey);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle expireArticle(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			double version, String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "expireArticle",
				_expireArticleParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, version, articleURL,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void expireArticle(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "expireArticle",
				_expireArticleParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, articleURL, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle fetchArticle(
			HttpPrincipal httpPrincipal, long groupId, String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "fetchArticle",
				_fetchArticleParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle
			fetchLatestArticleByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long groupId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class,
				"fetchLatestArticleByExternalReferenceCode",
				_fetchLatestArticleByExternalReferenceCodeParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, externalReferenceCode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle getArticle(
			HttpPrincipal httpPrincipal, long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticle",
				_getArticleParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey, id);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle getArticle(
			HttpPrincipal httpPrincipal, long groupId, String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticle",
				_getArticleParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle getArticle(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticle",
				_getArticleParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, version);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle getArticle(
			HttpPrincipal httpPrincipal, long groupId, String className,
			long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticle",
				_getArticleParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle getArticleByUrlTitle(
			HttpPrincipal httpPrincipal, long groupId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticleByUrlTitle",
				_getArticleByUrlTitleParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, urlTitle);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static String getArticleContent(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			double version, String languageId,
			com.liferay.portal.kernel.portlet.PortletRequestModel
				portletRequestModel,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticleContent",
				_getArticleContentParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, version, languageId,
				portletRequestModel, themeDisplay);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static String getArticleContent(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			String languageId,
			com.liferay.portal.kernel.portlet.PortletRequestModel
				portletRequestModel,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticleContent",
				_getArticleContentParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, languageId, portletRequestModel,
				themeDisplay);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getArticles(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			java.util.Locale locale) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticles",
				_getArticlesParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, locale);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getArticles(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			java.util.Locale locale, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticles",
				_getArticlesParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, locale, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getArticlesByArticleId(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticlesByArticleId",
				_getArticlesByArticleIdParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getArticlesByLayoutUuid(
			HttpPrincipal httpPrincipal, long groupId, String layoutUuid) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticlesByLayoutUuid",
				_getArticlesByLayoutUuidParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, layoutUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getArticlesByLayoutUuid(
			HttpPrincipal httpPrincipal, long groupId, String layoutUuid,
			int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticlesByLayoutUuid",
				_getArticlesByLayoutUuidParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, layoutUuid, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getArticlesByLayoutUuidCount(
		HttpPrincipal httpPrincipal, long groupId, String layoutUuid) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticlesByLayoutUuidCount",
				_getArticlesByLayoutUuidCountParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, layoutUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getArticlesByStructureId(
			HttpPrincipal httpPrincipal, long groupId, long classNameId,
			String ddmStructureKey, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticlesByStructureId",
				_getArticlesByStructureIdParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, ddmStructureKey, status, start,
				end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getArticlesByStructureId(
			HttpPrincipal httpPrincipal, long groupId, long classNameId,
			String ddmStructureKey, java.util.Locale locale, int status,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticlesByStructureId",
				_getArticlesByStructureIdParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, ddmStructureKey, locale,
				status, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getArticlesByStructureId(
			HttpPrincipal httpPrincipal, long groupId, String ddmStructureKey,
			int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticlesByStructureId",
				_getArticlesByStructureIdParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, ddmStructureKey, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getArticlesByStructureId(
			HttpPrincipal httpPrincipal, long groupId, String ddmStructureKey,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticlesByStructureId",
				_getArticlesByStructureIdParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, ddmStructureKey, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getArticlesByStructureId(
			HttpPrincipal httpPrincipal, long groupId, String ddmStructureKey,
			java.util.Locale locale, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticlesByStructureId",
				_getArticlesByStructureIdParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, ddmStructureKey, locale, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getArticlesCount(
		HttpPrincipal httpPrincipal, long groupId, long folderId) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticlesCount",
				_getArticlesCountParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getArticlesCount(
		HttpPrincipal httpPrincipal, long groupId, long folderId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticlesCount",
				_getArticlesCountParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getArticlesCountByArticleId(
		HttpPrincipal httpPrincipal, long groupId, String articleId) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getArticlesCountByArticleId",
				_getArticlesCountByArticleIdParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getArticlesCountByStructureId(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		String ddmStructureKey, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class,
				"getArticlesCountByStructureId",
				_getArticlesCountByStructureIdParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, ddmStructureKey, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getArticlesCountByStructureId(
		HttpPrincipal httpPrincipal, long groupId, String ddmStructureKey) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class,
				"getArticlesCountByStructureId",
				_getArticlesCountByStructureIdParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, ddmStructureKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getArticlesCountByStructureId(
		HttpPrincipal httpPrincipal, long groupId, String ddmStructureKey,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class,
				"getArticlesCountByStructureId",
				_getArticlesCountByStructureIdParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, ddmStructureKey, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle
			getDisplayArticleByUrlTitle(
				HttpPrincipal httpPrincipal, long groupId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getDisplayArticleByUrlTitle",
				_getDisplayArticleByUrlTitleParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, urlTitle);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getFoldersAndArticlesCount(
		HttpPrincipal httpPrincipal, long groupId,
		java.util.List<Long> folderIds) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getFoldersAndArticlesCount",
				_getFoldersAndArticlesCountParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
			getGroupArticles(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				long rootFolderId, int status, boolean includeOwner, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getGroupArticles",
				_getGroupArticlesParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, status, includeOwner,
				start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
			getGroupArticles(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				long rootFolderId, int status, boolean includeOwner,
				java.util.Locale locale, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getGroupArticles",
				_getGroupArticlesParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, status, includeOwner,
				locale, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
			getGroupArticles(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				long rootFolderId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getGroupArticles",
				_getGroupArticlesParameterTypes43);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
			getGroupArticles(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				long rootFolderId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.journal.model.JournalArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getGroupArticles",
				_getGroupArticlesParameterTypes44);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getGroupArticlesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId,
			long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getGroupArticlesCount",
				_getGroupArticlesCountParameterTypes45);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getGroupArticlesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId,
			long rootFolderId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getGroupArticlesCount",
				_getGroupArticlesCountParameterTypes46);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getGroupArticlesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId,
			long rootFolderId, int status, boolean includeOwner)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getGroupArticlesCount",
				_getGroupArticlesCountParameterTypes47);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, status, includeOwner);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle getLatestArticle(
			HttpPrincipal httpPrincipal, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getLatestArticle",
				_getLatestArticleParameterTypes48);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, resourcePrimKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle getLatestArticle(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getLatestArticle",
				_getLatestArticleParameterTypes49);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle getLatestArticle(
			HttpPrincipal httpPrincipal, long groupId, String className,
			long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getLatestArticle",
				_getLatestArticleParameterTypes50);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle
			getLatestArticleByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long groupId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class,
				"getLatestArticleByExternalReferenceCode",
				_getLatestArticleByExternalReferenceCodeParameterTypes51);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, externalReferenceCode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getLatestArticles(
			HttpPrincipal httpPrincipal, long groupId, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getLatestArticles",
				_getLatestArticlesParameterTypes52);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getLatestArticlesCount(
		HttpPrincipal httpPrincipal, long groupId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getLatestArticlesCount",
				_getLatestArticlesCountParameterTypes53);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getLayoutArticles(HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getLayoutArticles",
				_getLayoutArticlesParameterTypes54);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		getLayoutArticles(
			HttpPrincipal httpPrincipal, long groupId, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getLayoutArticles",
				_getLayoutArticlesParameterTypes55);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getLayoutArticlesCount(
		HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "getLayoutArticlesCount",
				_getLayoutArticlesCountParameterTypes56);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void moveArticle(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			long newFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "moveArticle",
				_moveArticleParameterTypes57);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, newFolderId, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle moveArticleFromTrash(
			HttpPrincipal httpPrincipal, long groupId, long resourcePrimKey,
			long newFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "moveArticleFromTrash",
				_moveArticleFromTrashParameterTypes58);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, resourcePrimKey, newFolderId,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle moveArticleFromTrash(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			long newFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "moveArticleFromTrash",
				_moveArticleFromTrashParameterTypes59);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, newFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle moveArticleToTrash(
			HttpPrincipal httpPrincipal, long groupId, String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "moveArticleToTrash",
				_moveArticleToTrashParameterTypes60);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void removeArticleLocale(
			HttpPrincipal httpPrincipal, long companyId, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "removeArticleLocale",
				_removeArticleLocaleParameterTypes61);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, languageId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle removeArticleLocale(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			double version, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "removeArticleLocale",
				_removeArticleLocaleParameterTypes62);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, version, languageId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void restoreArticleFromTrash(
			HttpPrincipal httpPrincipal, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "restoreArticleFromTrash",
				_restoreArticleFromTrashParameterTypes63);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, resourcePrimKey);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void restoreArticleFromTrash(
			HttpPrincipal httpPrincipal, long groupId, String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "restoreArticleFromTrash",
				_restoreArticleFromTrashParameterTypes64);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long groupId, long creatorUserId,
			int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "search",
				_searchParameterTypes65);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, creatorUserId, status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		search(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			java.util.List<Long> folderIds, long classNameId, String keywords,
			Double version, String ddmStructureKey, String ddmTemplateKey,
			java.util.Date displayDateGT, java.util.Date displayDateLT,
			java.util.Date reviewDate, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "search",
				_searchParameterTypes66);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, folderIds, classNameId, keywords,
				version, ddmStructureKey, ddmTemplateKey, displayDateGT,
				displayDateLT, reviewDate, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		search(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			java.util.List<Long> folderIds, long classNameId, String articleId,
			Double version, String title, String description, String content,
			String ddmStructureKey, String ddmTemplateKey,
			java.util.Date displayDateGT, java.util.Date displayDateLT,
			java.util.Date reviewDate, int status, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "search",
				_searchParameterTypes67);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, folderIds, classNameId,
				articleId, version, title, description, content,
				ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
				reviewDate, status, andOperator, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.journal.model.JournalArticle>
		search(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			java.util.List<Long> folderIds, long classNameId, String articleId,
			Double version, String title, String description, String content,
			String[] ddmStructureKeys, String[] ddmTemplateKeys,
			java.util.Date displayDateGT, java.util.Date displayDateLT,
			java.util.Date reviewDate, int status, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalArticle> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "search",
				_searchParameterTypes68);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, folderIds, classNameId,
				articleId, version, title, description, content,
				ddmStructureKeys, ddmTemplateKeys, displayDateGT, displayDateLT,
				reviewDate, status, andOperator, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.journal.model.JournalArticle>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.util.List<Long> folderIds, long classNameId, String keywords,
		Double version, String ddmStructureKey, String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT,
		java.util.Date reviewDate, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "searchCount",
				_searchCountParameterTypes69);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, folderIds, classNameId, keywords,
				version, ddmStructureKey, ddmTemplateKey, displayDateGT,
				displayDateLT, reviewDate, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.util.List<Long> folderIds, long classNameId, String articleId,
		Double version, String title, String description, String content,
		String ddmStructureKey, String ddmTemplateKey,
		java.util.Date displayDateGT, java.util.Date displayDateLT,
		java.util.Date reviewDate, int status, boolean andOperator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "searchCount",
				_searchCountParameterTypes70);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, folderIds, classNameId,
				articleId, version, title, description, content,
				ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
				reviewDate, status, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.util.List<Long> folderIds, long classNameId, String articleId,
		Double version, String title, String description, String content,
		String[] ddmStructureKeys, String[] ddmTemplateKeys,
		java.util.Date displayDateGT, java.util.Date displayDateLT,
		java.util.Date reviewDate, int status, boolean andOperator) {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "searchCount",
				_searchCountParameterTypes71);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, folderIds, classNameId,
				articleId, version, title, description, content,
				ddmStructureKeys, ddmTemplateKeys, displayDateGT, displayDateLT,
				reviewDate, status, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void subscribe(
			HttpPrincipal httpPrincipal, long groupId, long articleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "subscribe",
				_subscribeParameterTypes72);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void subscribeStructure(
			HttpPrincipal httpPrincipal, long groupId, long userId,
			long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "subscribeStructure",
				_subscribeStructureParameterTypes73);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, ddmStructureId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void unsubscribe(
			HttpPrincipal httpPrincipal, long groupId, long articleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "unsubscribe",
				_unsubscribeParameterTypes74);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void unsubscribeStructure(
			HttpPrincipal httpPrincipal, long groupId, long userId,
			long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "unsubscribeStructure",
				_unsubscribeStructureParameterTypes75);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, ddmStructureId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle updateArticle(
			HttpPrincipal httpPrincipal, long userId, long groupId,
			long folderId, String articleId, double version,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String content, String layoutUuid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "updateArticle",
				_updateArticleParameterTypes76);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groupId, folderId, articleId, version,
				titleMap, descriptionMap, content, layoutUuid, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle updateArticle(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			String articleId, double version,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<java.util.Locale, String> friendlyURLMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			String layoutUuid, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			boolean smallImage, String smallImageURL, java.io.File smallFile,
			java.util.Map<String, byte[]> images, String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "updateArticle",
				_updateArticleParameterTypes77);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, articleId, version, titleMap,
				descriptionMap, friendlyURLMap, content, ddmStructureKey,
				ddmTemplateKey, layoutUuid, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, neverReview, indexable, smallImage,
				smallImageURL, smallFile, images, articleURL, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle updateArticle(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			String articleId, double version,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			String layoutUuid, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			boolean smallImage, String smallImageURL, java.io.File smallFile,
			java.util.Map<String, byte[]> images, String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "updateArticle",
				_updateArticleParameterTypes78);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, articleId, version, titleMap,
				descriptionMap, content, ddmStructureKey, ddmTemplateKey,
				layoutUuid, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, reviewDateMonth,
				reviewDateDay, reviewDateYear, reviewDateHour, reviewDateMinute,
				neverReview, indexable, smallImage, smallImageURL, smallFile,
				images, articleURL, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle updateArticle(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			String articleId, double version, String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "updateArticle",
				_updateArticleParameterTypes79);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, articleId, version, content,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle
			updateArticleDefaultValues(
				HttpPrincipal httpPrincipal, long groupId, String articleId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String content, String ddmStructureKey, String ddmTemplateKey,
				String layoutUuid, boolean indexable, boolean smallImage,
				String smallImageURL, java.io.File smallImageFile,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "updateArticleDefaultValues",
				_updateArticleDefaultValuesParameterTypes80);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, titleMap, descriptionMap,
				content, ddmStructureKey, ddmTemplateKey, layoutUuid, indexable,
				smallImage, smallImageURL, smallImageFile, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle
			updateArticleTranslation(
				HttpPrincipal httpPrincipal, long groupId, String articleId,
				double version, java.util.Locale locale, String title,
				String description, String content,
				java.util.Map<String, byte[]> images,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "updateArticleTranslation",
				_updateArticleTranslationParameterTypes81);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, version, locale, title,
				description, content, images, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.journal.model.JournalArticle updateStatus(
			HttpPrincipal httpPrincipal, long groupId, String articleId,
			double version, int status, String articleURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalArticleServiceUtil.class, "updateStatus",
				_updateStatusParameterTypes82);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, articleId, version, status, articleURL,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.journal.model.JournalArticle)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalArticleServiceHttp.class);

	private static final Class<?>[] _addArticleParameterTypes0 = new Class[] {
		long.class, long.class, long.class, long.class, String.class,
		boolean.class, java.util.Map.class, java.util.Map.class,
		java.util.Map.class, String.class, String.class, String.class,
		String.class, int.class, int.class, int.class, int.class, int.class,
		int.class, int.class, int.class, int.class, int.class, boolean.class,
		int.class, int.class, int.class, int.class, int.class, boolean.class,
		boolean.class, boolean.class, String.class, java.io.File.class,
		java.util.Map.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addArticleParameterTypes1 = new Class[] {
		long.class, long.class, long.class, long.class, String.class,
		boolean.class, java.util.Map.class, java.util.Map.class, String.class,
		String.class, String.class, String.class, int.class, int.class,
		int.class, int.class, int.class, int.class, int.class, int.class,
		int.class, int.class, boolean.class, int.class, int.class, int.class,
		int.class, int.class, boolean.class, boolean.class, boolean.class,
		String.class, java.io.File.class, java.util.Map.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addArticleParameterTypes2 = new Class[] {
		long.class, long.class, long.class, long.class, String.class,
		boolean.class, java.util.Map.class, java.util.Map.class, String.class,
		String.class, String.class, String.class, int.class, int.class,
		int.class, int.class, int.class, int.class, int.class, int.class,
		int.class, int.class, boolean.class, int.class, int.class, int.class,
		int.class, int.class, boolean.class, boolean.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addArticleParameterTypes3 = new Class[] {
		long.class, long.class, java.util.Map.class, java.util.Map.class,
		String.class, String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addArticleParameterTypes4 = new Class[] {
		String.class, long.class, long.class, long.class, long.class,
		String.class, boolean.class, java.util.Map.class, java.util.Map.class,
		java.util.Map.class, String.class, String.class, String.class,
		String.class, int.class, int.class, int.class, int.class, int.class,
		int.class, int.class, int.class, int.class, int.class, boolean.class,
		int.class, int.class, int.class, int.class, int.class, boolean.class,
		boolean.class, boolean.class, String.class, java.io.File.class,
		java.util.Map.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addArticleParameterTypes5 = new Class[] {
		String.class, long.class, long.class, java.util.Map.class,
		java.util.Map.class, String.class, String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addArticleDefaultValuesParameterTypes6 =
		new Class[] {
			long.class, long.class, long.class, java.util.Map.class,
			java.util.Map.class, String.class, String.class, String.class,
			String.class, boolean.class, boolean.class, String.class,
			java.io.File.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _copyArticleParameterTypes7 = new Class[] {
		long.class, String.class, String.class, boolean.class, double.class
	};
	private static final Class<?>[] _deleteArticleParameterTypes8 =
		new Class[] {
			long.class, String.class, double.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteArticleParameterTypes9 =
		new Class[] {
			long.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteArticleDefaultValuesParameterTypes10 = new Class[] {
			long.class, String.class, String.class
		};
	private static final Class<?>[] _expireArticleParameterTypes11 =
		new Class[] {
			long.class, String.class, double.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _expireArticleParameterTypes12 =
		new Class[] {
			long.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _fetchArticleParameterTypes13 =
		new Class[] {long.class, String.class};
	private static final Class<?>[]
		_fetchLatestArticleByExternalReferenceCodeParameterTypes14 =
			new Class[] {long.class, String.class};
	private static final Class<?>[] _getArticleParameterTypes15 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getArticleParameterTypes16 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _getArticleParameterTypes17 = new Class[] {
		long.class, String.class, double.class
	};
	private static final Class<?>[] _getArticleParameterTypes18 = new Class[] {
		long.class, String.class, long.class
	};
	private static final Class<?>[] _getArticleByUrlTitleParameterTypes19 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getArticleContentParameterTypes20 =
		new Class[] {
			long.class, String.class, double.class, String.class,
			com.liferay.portal.kernel.portlet.PortletRequestModel.class,
			com.liferay.portal.kernel.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getArticleContentParameterTypes21 =
		new Class[] {
			long.class, String.class, String.class,
			com.liferay.portal.kernel.portlet.PortletRequestModel.class,
			com.liferay.portal.kernel.theme.ThemeDisplay.class
		};
	private static final Class<?>[] _getArticlesParameterTypes22 = new Class[] {
		long.class, long.class, java.util.Locale.class
	};
	private static final Class<?>[] _getArticlesParameterTypes23 = new Class[] {
		long.class, long.class, java.util.Locale.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getArticlesByArticleIdParameterTypes24 =
		new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getArticlesByLayoutUuidParameterTypes25 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getArticlesByLayoutUuidParameterTypes26 =
		new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getArticlesByLayoutUuidCountParameterTypes27 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getArticlesByStructureIdParameterTypes28 =
		new Class[] {
			long.class, long.class, String.class, int.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getArticlesByStructureIdParameterTypes29 =
		new Class[] {
			long.class, long.class, String.class, java.util.Locale.class,
			int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getArticlesByStructureIdParameterTypes30 =
		new Class[] {
			long.class, String.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getArticlesByStructureIdParameterTypes31 =
		new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getArticlesByStructureIdParameterTypes32 =
		new Class[] {
			long.class, String.class, java.util.Locale.class, int.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getArticlesCountParameterTypes33 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getArticlesCountParameterTypes34 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[]
		_getArticlesCountByArticleIdParameterTypes35 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getArticlesCountByStructureIdParameterTypes36 = new Class[] {
			long.class, long.class, String.class, int.class
		};
	private static final Class<?>[]
		_getArticlesCountByStructureIdParameterTypes37 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getArticlesCountByStructureIdParameterTypes38 = new Class[] {
			long.class, String.class, int.class
		};
	private static final Class<?>[]
		_getDisplayArticleByUrlTitleParameterTypes39 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getFoldersAndArticlesCountParameterTypes40 = new Class[] {
			long.class, java.util.List.class
		};
	private static final Class<?>[] _getGroupArticlesParameterTypes41 =
		new Class[] {
			long.class, long.class, long.class, int.class, boolean.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupArticlesParameterTypes42 =
		new Class[] {
			long.class, long.class, long.class, int.class, boolean.class,
			java.util.Locale.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupArticlesParameterTypes43 =
		new Class[] {
			long.class, long.class, long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupArticlesParameterTypes44 =
		new Class[] {
			long.class, long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupArticlesCountParameterTypes45 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _getGroupArticlesCountParameterTypes46 =
		new Class[] {long.class, long.class, long.class, int.class};
	private static final Class<?>[] _getGroupArticlesCountParameterTypes47 =
		new Class[] {
			long.class, long.class, long.class, int.class, boolean.class
		};
	private static final Class<?>[] _getLatestArticleParameterTypes48 =
		new Class[] {long.class};
	private static final Class<?>[] _getLatestArticleParameterTypes49 =
		new Class[] {long.class, String.class, int.class};
	private static final Class<?>[] _getLatestArticleParameterTypes50 =
		new Class[] {long.class, String.class, long.class};
	private static final Class<?>[]
		_getLatestArticleByExternalReferenceCodeParameterTypes51 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getLatestArticlesParameterTypes52 =
		new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getLatestArticlesCountParameterTypes53 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getLayoutArticlesParameterTypes54 =
		new Class[] {long.class};
	private static final Class<?>[] _getLayoutArticlesParameterTypes55 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getLayoutArticlesCountParameterTypes56 =
		new Class[] {long.class};
	private static final Class<?>[] _moveArticleParameterTypes57 = new Class[] {
		long.class, String.class, long.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _moveArticleFromTrashParameterTypes58 =
		new Class[] {
			long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _moveArticleFromTrashParameterTypes59 =
		new Class[] {
			long.class, String.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _moveArticleToTrashParameterTypes60 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _removeArticleLocaleParameterTypes61 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _removeArticleLocaleParameterTypes62 =
		new Class[] {long.class, String.class, double.class, String.class};
	private static final Class<?>[] _restoreArticleFromTrashParameterTypes63 =
		new Class[] {long.class};
	private static final Class<?>[] _restoreArticleFromTrashParameterTypes64 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _searchParameterTypes65 = new Class[] {
		long.class, long.class, int.class, int.class, int.class
	};
	private static final Class<?>[] _searchParameterTypes66 = new Class[] {
		long.class, long.class, java.util.List.class, long.class, String.class,
		Double.class, String.class, String.class, java.util.Date.class,
		java.util.Date.class, java.util.Date.class, int.class, int.class,
		int.class, com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes67 = new Class[] {
		long.class, long.class, java.util.List.class, long.class, String.class,
		Double.class, String.class, String.class, String.class, String.class,
		String.class, java.util.Date.class, java.util.Date.class,
		java.util.Date.class, int.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes68 = new Class[] {
		long.class, long.class, java.util.List.class, long.class, String.class,
		Double.class, String.class, String.class, String.class, String[].class,
		String[].class, java.util.Date.class, java.util.Date.class,
		java.util.Date.class, int.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes69 = new Class[] {
		long.class, long.class, java.util.List.class, long.class, String.class,
		Double.class, String.class, String.class, java.util.Date.class,
		java.util.Date.class, java.util.Date.class, int.class
	};
	private static final Class<?>[] _searchCountParameterTypes70 = new Class[] {
		long.class, long.class, java.util.List.class, long.class, String.class,
		Double.class, String.class, String.class, String.class, String.class,
		String.class, java.util.Date.class, java.util.Date.class,
		java.util.Date.class, int.class, boolean.class
	};
	private static final Class<?>[] _searchCountParameterTypes71 = new Class[] {
		long.class, long.class, java.util.List.class, long.class, String.class,
		Double.class, String.class, String.class, String.class, String[].class,
		String[].class, java.util.Date.class, java.util.Date.class,
		java.util.Date.class, int.class, boolean.class
	};
	private static final Class<?>[] _subscribeParameterTypes72 = new Class[] {
		long.class, long.class
	};
	private static final Class<?>[] _subscribeStructureParameterTypes73 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _unsubscribeParameterTypes74 = new Class[] {
		long.class, long.class
	};
	private static final Class<?>[] _unsubscribeStructureParameterTypes75 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _updateArticleParameterTypes76 =
		new Class[] {
			long.class, long.class, long.class, String.class, double.class,
			java.util.Map.class, java.util.Map.class, String.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateArticleParameterTypes77 =
		new Class[] {
			long.class, long.class, String.class, double.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			String.class, String.class, String.class, String.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class, int.class,
			int.class, int.class, int.class, int.class, boolean.class,
			boolean.class, boolean.class, String.class, java.io.File.class,
			java.util.Map.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateArticleParameterTypes78 =
		new Class[] {
			long.class, long.class, String.class, double.class,
			java.util.Map.class, java.util.Map.class, String.class,
			String.class, String.class, String.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, boolean.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class, boolean.class,
			boolean.class, String.class, java.io.File.class,
			java.util.Map.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateArticleParameterTypes79 =
		new Class[] {
			long.class, long.class, String.class, double.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateArticleDefaultValuesParameterTypes80 = new Class[] {
			long.class, String.class, java.util.Map.class, java.util.Map.class,
			String.class, String.class, String.class, String.class,
			boolean.class, boolean.class, String.class, java.io.File.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateArticleTranslationParameterTypes81 =
		new Class[] {
			long.class, String.class, double.class, java.util.Locale.class,
			String.class, String.class, String.class, java.util.Map.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateStatusParameterTypes82 =
		new Class[] {
			long.class, String.class, double.class, int.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
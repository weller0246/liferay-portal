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

package com.liferay.site.navigation.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuItemServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>SiteNavigationMenuItemServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.site.navigation.model.SiteNavigationMenuItemSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.site.navigation.model.SiteNavigationMenuItem</code>, that is translated to a
 * <code>com.liferay.site.navigation.model.SiteNavigationMenuItemSoap</code>. Methods that SOAP
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
 * @see SiteNavigationMenuItemServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SiteNavigationMenuItemServiceSoap {

	public static com.liferay.site.navigation.model.SiteNavigationMenuItemSoap
			addSiteNavigationMenuItem(
				long groupId, long siteNavigationMenuId,
				long parentSiteNavigationMenuItemId, String type,
				String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.site.navigation.model.SiteNavigationMenuItem
				returnValue =
					SiteNavigationMenuItemServiceUtil.addSiteNavigationMenuItem(
						groupId, siteNavigationMenuId,
						parentSiteNavigationMenuItemId, type, typeSettings,
						serviceContext);

			return com.liferay.site.navigation.model.SiteNavigationMenuItemSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItemSoap
			deleteSiteNavigationMenuItem(long siteNavigationMenuItemId)
		throws RemoteException {

		try {
			com.liferay.site.navigation.model.SiteNavigationMenuItem
				returnValue =
					SiteNavigationMenuItemServiceUtil.
						deleteSiteNavigationMenuItem(siteNavigationMenuItemId);

			return com.liferay.site.navigation.model.SiteNavigationMenuItemSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteSiteNavigationMenuItems(long siteNavigationMenuId)
		throws RemoteException {

		try {
			SiteNavigationMenuItemServiceUtil.deleteSiteNavigationMenuItems(
				siteNavigationMenuId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static Long[] getParentSiteNavigationMenuItemIds(
			long siteNavigationMenuId, String typeSettingsKeyword)
		throws RemoteException {

		try {
			java.util.List<Long> returnValue =
				SiteNavigationMenuItemServiceUtil.
					getParentSiteNavigationMenuItemIds(
						siteNavigationMenuId, typeSettingsKeyword);

			return returnValue.toArray(new Long[returnValue.size()]);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItemSoap[]
			getSiteNavigationMenuItems(long siteNavigationMenuId)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenuItem>
					returnValue =
						SiteNavigationMenuItemServiceUtil.
							getSiteNavigationMenuItems(siteNavigationMenuId);

			return com.liferay.site.navigation.model.SiteNavigationMenuItemSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItemSoap[]
			getSiteNavigationMenuItems(
				long siteNavigationMenuId, long parentSiteNavigationMenuItemId)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenuItem>
					returnValue =
						SiteNavigationMenuItemServiceUtil.
							getSiteNavigationMenuItems(
								siteNavigationMenuId,
								parentSiteNavigationMenuItemId);

			return com.liferay.site.navigation.model.SiteNavigationMenuItemSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItemSoap
			updateSiteNavigationMenuItem(
				long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
				int order)
		throws RemoteException {

		try {
			com.liferay.site.navigation.model.SiteNavigationMenuItem
				returnValue =
					SiteNavigationMenuItemServiceUtil.
						updateSiteNavigationMenuItem(
							siteNavigationMenuId,
							parentSiteNavigationMenuItemId, order);

			return com.liferay.site.navigation.model.SiteNavigationMenuItemSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItemSoap
			updateSiteNavigationMenuItem(
				long siteNavigationMenuId, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.site.navigation.model.SiteNavigationMenuItem
				returnValue =
					SiteNavigationMenuItemServiceUtil.
						updateSiteNavigationMenuItem(
							siteNavigationMenuId, typeSettings, serviceContext);

			return com.liferay.site.navigation.model.SiteNavigationMenuItemSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuItemServiceSoap.class);

}
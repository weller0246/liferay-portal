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

package com.liferay.account.service.http;

import com.liferay.account.service.AccountEntryUserRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>AccountEntryUserRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.account.model.AccountEntryUserRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.account.model.AccountEntryUserRel</code>, that is translated to a
 * <code>com.liferay.account.model.AccountEntryUserRelSoap</code>. Methods that SOAP
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
 * @see AccountEntryUserRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AccountEntryUserRelServiceSoap {

	public static com.liferay.account.model.AccountEntryUserRelSoap
			addAccountEntryUserRel(
				long accountEntryId, long creatorUserId, String screenName,
				String emailAddress, String locale, String firstName,
				String middleName, String lastName, long prefixId,
				long suffixId)
		throws RemoteException {

		try {
			com.liferay.account.model.AccountEntryUserRel returnValue =
				AccountEntryUserRelServiceUtil.addAccountEntryUserRel(
					accountEntryId, creatorUserId, screenName, emailAddress,
					LocaleUtil.fromLanguageId(locale), firstName, middleName,
					lastName, prefixId, suffixId);

			return com.liferay.account.model.AccountEntryUserRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.account.model.AccountEntryUserRelSoap
			addAccountEntryUserRelByEmailAddress(
				long accountEntryId, String emailAddress, long[] accountRoleIds,
				String userExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.account.model.AccountEntryUserRel returnValue =
				AccountEntryUserRelServiceUtil.
					addAccountEntryUserRelByEmailAddress(
						accountEntryId, emailAddress, accountRoleIds,
						userExternalReferenceCode, serviceContext);

			return com.liferay.account.model.AccountEntryUserRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteAccountEntryUserRelByEmailAddress(
			long accountEntryId, String emailAddress)
		throws RemoteException {

		try {
			AccountEntryUserRelServiceUtil.
				deleteAccountEntryUserRelByEmailAddress(
					accountEntryId, emailAddress);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AccountEntryUserRelServiceSoap.class);

}
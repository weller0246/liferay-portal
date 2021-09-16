/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service.http;

import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CPDefinitionDiagramSettingServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettingSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting</code>, that is translated to a
 * <code>com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettingSoap</code>. Methods that SOAP
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
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramSettingServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CPDefinitionDiagramSettingServiceSoap {

	public static
		com.liferay.commerce.shop.by.diagram.model.
			CPDefinitionDiagramSettingSoap addCPDefinitionDiagramSetting(
					long cpDefinitionId, long cpAttachmentFileEntryId,
					String color, double radius, String type)
				throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSetting returnValue =
					CPDefinitionDiagramSettingServiceUtil.
						addCPDefinitionDiagramSetting(
							cpDefinitionId, cpAttachmentFileEntryId, color,
							radius, type);

			return com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSettingSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.
			CPDefinitionDiagramSettingSoap
					fetchCPDefinitionDiagramSettingByCPDefinitionId(
						long cpDefinitionId)
				throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSetting returnValue =
					CPDefinitionDiagramSettingServiceUtil.
						fetchCPDefinitionDiagramSettingByCPDefinitionId(
							cpDefinitionId);

			return com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSettingSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.
			CPDefinitionDiagramSettingSoap getCPDefinitionDiagramSetting(
					long cpDefinitionDiagramSettingId)
				throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSetting returnValue =
					CPDefinitionDiagramSettingServiceUtil.
						getCPDefinitionDiagramSetting(
							cpDefinitionDiagramSettingId);

			return com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSettingSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.
			CPDefinitionDiagramSettingSoap
					getCPDefinitionDiagramSettingByCPDefinitionId(
						long cpDefinitionId)
				throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSetting returnValue =
					CPDefinitionDiagramSettingServiceUtil.
						getCPDefinitionDiagramSettingByCPDefinitionId(
							cpDefinitionId);

			return com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSettingSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.
			CPDefinitionDiagramSettingSoap updateCPDefinitionDiagramSetting(
					long cpDefinitionDiagramSettingId,
					long cpAttachmentFileEntryId, String color, double radius,
					String type)
				throws RemoteException {

		try {
			com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSetting returnValue =
					CPDefinitionDiagramSettingServiceUtil.
						updateCPDefinitionDiagramSetting(
							cpDefinitionDiagramSettingId,
							cpAttachmentFileEntryId, color, radius, type);

			return com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSettingSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionDiagramSettingServiceSoap.class);

}
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

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.internal.util.RemoteExceptionSharepointExceptionMapper;

import com.microsoft.schemas.sharepoint.soap.UndoCheckOutDocument;
import com.microsoft.schemas.sharepoint.soap.UndoCheckOutResponseDocument;

import java.rmi.RemoteException;

/**
 * @author Iván Zaera
 */
public final class CancelCheckOutFileOperation extends BaseOperation {

	public boolean execute(String filePath) throws SharepointException {
		try {
			UndoCheckOutResponseDocument undoCheckOutResponseDocument =
				listsSoap12Stub.undoCheckOut(
					_getUndoCheckOutDocument(filePath));

			return _isUndoCheckOut(undoCheckOutResponseDocument);
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(
				remoteException, sharepointConnectionInfo);
		}
	}

	private UndoCheckOutDocument _getUndoCheckOutDocument(String filePath) {
		UndoCheckOutDocument undoCheckOutDocument =
			UndoCheckOutDocument.Factory.newInstance();

		UndoCheckOutDocument.UndoCheckOut undoCheckOut =
			undoCheckOutDocument.addNewUndoCheckOut();

		undoCheckOut.setPageUrl(String.valueOf(toURL(filePath)));

		return undoCheckOutDocument;
	}

	private boolean _isUndoCheckOut(
		UndoCheckOutResponseDocument undoCheckOutResponseDocument) {

		UndoCheckOutResponseDocument.UndoCheckOutResponse undoCheckOutResponse =
			undoCheckOutResponseDocument.getUndoCheckOutResponse();

		return undoCheckOutResponse.getUndoCheckOutResult();
	}

}
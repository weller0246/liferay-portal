/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.documentlibrary.service.DLLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.util.ValueMapperFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * <a href="DLFileEntryIdUpgradeColumnImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLFileEntryIdUpgradeColumnImpl extends PKUpgradeColumnImpl {

	public DLFileEntryIdUpgradeColumnImpl(
		UpgradeColumn companyIdColumn, UpgradeColumn folderIdColumn,
		UpgradeColumn nameColumn) {

		super("fileEntryId", false);

		_companyIdColumn = companyIdColumn;
		_folderIdColumn = folderIdColumn;
		_nameColumn = nameColumn;
		_dlFileEntryIdMapper = ValueMapperFactory.getValueMapper();
		_movedFolderIds = new HashSet<Long>();
	}

	public Object getNewValue(Object oldValue) throws Exception {
		Object newValue = super.getNewValue(oldValue);

		String oldCompanyId = (String)_companyIdColumn.getOldValue();
		Long oldFolderId = (Long)_folderIdColumn.getOldValue();

		Long newCompanyId = (Long)_companyIdColumn.getNewValue();
		Long newFolderId = (Long)_folderIdColumn.getNewValue();

		String name = (String)_nameColumn.getOldValue();

		String oldPageIdValue =
			"{folderId=" + oldFolderId + ", name=" + name + "}";

		_dlFileEntryIdMapper.mapValue(oldPageIdValue, newValue);

		if (!_movedFolderIds.contains(oldFolderId)) {
			try {
				DLLocalServiceUtil.move(
					"/" + oldCompanyId + "/documentlibrary/" + oldFolderId,
					"/" + newCompanyId + "/documentlibrary/" + newFolderId);
			}
			catch (Exception e) {
				_log.error(e.getMessage());
			}

			_movedFolderIds.add(oldFolderId);
		}

		return newValue;
	}

	public ValueMapper getValueMapper() {
		return _dlFileEntryIdMapper;
	}

	private static Log _log =
		LogFactoryUtil.getLog(DLFileEntryIdUpgradeColumnImpl.class);

	private UpgradeColumn _companyIdColumn;
	private UpgradeColumn _folderIdColumn;
	private UpgradeColumn _nameColumn;
	private ValueMapper _dlFileEntryIdMapper;
	private Set<Long> _movedFolderIds;

}
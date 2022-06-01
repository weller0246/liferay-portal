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

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseCETImpl implements CET {

	public BaseCETImpl(ClientExtensionEntry clientExtensionEntry) {
		this(
			(clientExtensionEntry == null) ? null :
				clientExtensionEntry.getTypeSettings());

		if (clientExtensionEntry != null) {
			_id = clientExtensionEntry.getClientExtensionEntryId();
			_name = clientExtensionEntry.getName(
				LocaleUtil.getMostRelevantLocale());
			_status = clientExtensionEntry.getStatus();
		}
	}

	public BaseCETImpl(String typeSettings) {
		_unicodeProperties = UnicodePropertiesBuilder.load(
			GetterUtil.getString(typeSettings)
		).build();
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public String toString() {
		return _unicodeProperties.toString();
	}

	protected boolean getBoolean(String key) {
		return GetterUtil.getBoolean(_unicodeProperties.getProperty(key));
	}

	protected String getString(String key) {
		return GetterUtil.getString(_unicodeProperties.getProperty(key));
	}

	private long _id;
	private String _name = StringPool.BLANK;
	private int _status;
	private final UnicodeProperties _unicodeProperties;

}
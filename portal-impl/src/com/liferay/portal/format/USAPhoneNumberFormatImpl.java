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

package com.liferay.portal.format;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.format.PhoneNumberFormat;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
public class USAPhoneNumberFormatImpl implements PhoneNumberFormat {

	@Override
	public String format(String phoneNumber) {
		if (Validator.isNull(phoneNumber)) {
			return StringPool.BLANK;
		}

		if (phoneNumber.length() > 10) {
			return StringBundler.concat(
				StringPool.OPEN_PARENTHESIS, phoneNumber.substring(0, 3), ") ",
				phoneNumber.substring(3, 6), StringPool.DASH,
				phoneNumber.substring(6, 10), " x", phoneNumber.substring(10));
		}
		else if (phoneNumber.length() == 10) {
			return StringBundler.concat(
				StringPool.OPEN_PARENTHESIS, phoneNumber.substring(0, 3), ") ",
				phoneNumber.substring(3, 6), StringPool.DASH,
				phoneNumber.substring(6));
		}
		else if (phoneNumber.length() == 7) {
			return StringBundler.concat(
				phoneNumber.substring(0, 3), StringPool.DASH,
				phoneNumber.substring(3));
		}

		return phoneNumber;
	}

	@Override
	public String strip(String phoneNumber) {
		return StringUtil.extractDigits(phoneNumber);
	}

	@Override
	public boolean validate(String phoneNumber) {
		if (Validator.isNull(phoneNumber)) {
			return false;
		}

		return phoneNumber.matches(PropsValues.PHONE_NUMBER_FORMAT_USA_REGEXP);
	}

}
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

package com.liferay.commerce.product.type.grouped.test.util;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class GroupedCPTypeTestUtil {

	public static CPDefinitionGroupedEntry addCPDefinitionGroupedEntry(
			long groupId, long cpDefinitionId, long entryCPDefinitionId)
		throws Exception {

		CPDefinition entryCPDefinition =
			CPDefinitionLocalServiceUtil.getCPDefinition(entryCPDefinitionId);

		return CPDefinitionGroupedEntryLocalServiceUtil.
			addCPDefinitionGroupedEntry(
				cpDefinitionId, entryCPDefinition.getCProductId(),
				RandomTestUtil.randomDouble(), RandomTestUtil.randomInt(),
				ServiceContextTestUtil.getServiceContext(groupId));
	}

}
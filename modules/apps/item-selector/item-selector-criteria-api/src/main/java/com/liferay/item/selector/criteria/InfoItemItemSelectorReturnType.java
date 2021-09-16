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

package com.liferay.item.selector.criteria;

import com.liferay.item.selector.ItemSelectorReturnType;

/**
 * This return type should return the following information of a info item as a
 * JSON object:
 *
 * <ul>
 * <li>
 * <code>className</code>: The class name of the selected info item
 * </li>
 * <li>
 * <code>classNameId</code>: The class name ID of the selected info item
 * </li>
 * <li>
 * <code>classPK</code>: The class pk of the selected info item
 * </li>
 * <li>
 * <code>classTypeId</code>: The class type ID of the selected info item
 * </li>
 * <li>
 * <code>subtype</code>: The sub type of the selected info item
 * </li>
 * <li>
 * <code>title</code>: The title of the selected info item
 * </li>
 * </ul>
 *
 * @author Eudaldo Alonso
 * @review
 */
public class InfoItemItemSelectorReturnType implements ItemSelectorReturnType {
}
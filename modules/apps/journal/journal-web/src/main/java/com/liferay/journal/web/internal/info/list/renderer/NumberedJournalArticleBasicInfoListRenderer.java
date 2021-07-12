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

package com.liferay.journal.web.internal.info.list.renderer;

import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.taglib.list.renderer.NumberedBasicInfoListRenderer;
import com.liferay.journal.model.JournalArticle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = InfoListRenderer.class)
public class NumberedJournalArticleBasicInfoListRenderer
	extends JournalArticleBasicInfoListRenderer
	implements NumberedBasicInfoListRenderer<JournalArticle> {
}
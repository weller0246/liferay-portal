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

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;

/**
 * @author Adam Brandizzi
 */
public interface SynonymSetIndexWriter {

	public String create(
		SynonymSetIndexName synonymSetIndexName, SynonymSet synonymSet);

	public void remove(
		SynonymSetIndexName synonymSetIndexName, String synonymSetDocumentId);

	public void update(
		SynonymSetIndexName synonymSetIndexName, SynonymSet synonymSet);

}
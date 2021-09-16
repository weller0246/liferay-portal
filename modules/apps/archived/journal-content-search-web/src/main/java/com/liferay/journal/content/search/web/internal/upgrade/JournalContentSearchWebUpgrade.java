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

package com.liferay.journal.content.search.web.internal.upgrade;

import com.liferay.journal.content.search.web.internal.configuration.JournalContentSearchPortletInstanceConfiguration;
import com.liferay.journal.content.search.web.internal.constants.JournalContentSearchPortletKeys;
import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class JournalContentSearchWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.0", "1.0.2", new DummyUpgradeStep());

		registry.register(
			"0.0.1", "1.0.0",
			new BasePortletIdUpgradeProcess() {

				@Override
				protected String[][] getRenamePortletIdsArray() {
					return new String[][] {
						{
							"77",
							JournalContentSearchPortletKeys.
								JOURNAL_CONTENT_SEARCH
						}
					};
				}

			});

		registry.register("1.0.0", "1.0.1", new DummyUpgradeStep());

		registry.register(
			"1.0.1", "1.0.2",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.journal.content.search.web.configuration." +
					"JournalContentSearchPortletInstanceConfiguration",
				JournalContentSearchPortletInstanceConfiguration.class.
					getName()));
	}

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

}
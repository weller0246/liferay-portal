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

package com.liferay.webhook.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;WebhookEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see WebhookEntry
 * @generated
 */
public class WebhookEntryTable extends BaseTable<WebhookEntryTable> {

	public static final WebhookEntryTable INSTANCE = new WebhookEntryTable();

	public final Column<WebhookEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<WebhookEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WebhookEntryTable, Long> webhookEntryId = createColumn(
		"webhookEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<WebhookEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WebhookEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WebhookEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WebhookEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WebhookEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WebhookEntryTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<WebhookEntryTable, String> destinationName =
		createColumn(
			"destinationName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<WebhookEntryTable, String> destinationWebhookEventKeys =
		createColumn(
			"destinationWebhookEventKeys", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<WebhookEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WebhookEntryTable, String> secret = createColumn(
		"secret", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WebhookEntryTable, String> url = createColumn(
		"url", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private WebhookEntryTable() {
		super("WebhookEntry", WebhookEntryTable::new);
	}

}
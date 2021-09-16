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

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class AccountSerDes {

	public static Account toDTO(String json) {
		AccountJSONParser accountJSONParser = new AccountJSONParser();

		return accountJSONParser.parseToDTO(json);
	}

	public static Account[] toDTOs(String json) {
		AccountJSONParser accountJSONParser = new AccountJSONParser();

		return accountJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Account account) {
		if (account == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (account.getAccountUserAccounts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountUserAccounts\": ");

			sb.append("[");

			for (int i = 0; i < account.getAccountUserAccounts().length; i++) {
				sb.append(String.valueOf(account.getAccountUserAccounts()[i]));

				if ((i + 1) < account.getAccountUserAccounts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (account.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(account.getActions()));
		}

		if (account.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(account.getDescription()));

			sb.append("\"");
		}

		if (account.getDomains() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"domains\": ");

			sb.append("[");

			for (int i = 0; i < account.getDomains().length; i++) {
				sb.append("\"");

				sb.append(_escape(account.getDomains()[i]));

				sb.append("\"");

				if ((i + 1) < account.getDomains().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (account.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(account.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (account.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(account.getId());
		}

		if (account.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(account.getName()));

			sb.append("\"");
		}

		if (account.getNumberOfUsers() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfUsers\": ");

			sb.append(account.getNumberOfUsers());
		}

		if (account.getOrganizationIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"organizationIds\": ");

			sb.append("[");

			for (int i = 0; i < account.getOrganizationIds().length; i++) {
				sb.append(account.getOrganizationIds()[i]);

				if ((i + 1) < account.getOrganizationIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (account.getParentAccountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentAccountId\": ");

			sb.append(account.getParentAccountId());
		}

		if (account.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(account.getStatus());
		}

		if (account.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(account.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AccountJSONParser accountJSONParser = new AccountJSONParser();

		return accountJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Account account) {
		if (account == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (account.getAccountUserAccounts() == null) {
			map.put("accountUserAccounts", null);
		}
		else {
			map.put(
				"accountUserAccounts",
				String.valueOf(account.getAccountUserAccounts()));
		}

		if (account.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(account.getActions()));
		}

		if (account.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(account.getDescription()));
		}

		if (account.getDomains() == null) {
			map.put("domains", null);
		}
		else {
			map.put("domains", String.valueOf(account.getDomains()));
		}

		if (account.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(account.getExternalReferenceCode()));
		}

		if (account.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(account.getId()));
		}

		if (account.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(account.getName()));
		}

		if (account.getNumberOfUsers() == null) {
			map.put("numberOfUsers", null);
		}
		else {
			map.put(
				"numberOfUsers", String.valueOf(account.getNumberOfUsers()));
		}

		if (account.getOrganizationIds() == null) {
			map.put("organizationIds", null);
		}
		else {
			map.put(
				"organizationIds",
				String.valueOf(account.getOrganizationIds()));
		}

		if (account.getParentAccountId() == null) {
			map.put("parentAccountId", null);
		}
		else {
			map.put(
				"parentAccountId",
				String.valueOf(account.getParentAccountId()));
		}

		if (account.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(account.getStatus()));
		}

		if (account.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(account.getType()));
		}

		return map;
	}

	public static class AccountJSONParser extends BaseJSONParser<Account> {

		@Override
		protected Account createDTO() {
			return new Account();
		}

		@Override
		protected Account[] createDTOArray(int size) {
			return new Account[size];
		}

		@Override
		protected void setField(
			Account account, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "accountUserAccounts")) {
				if (jsonParserFieldValue != null) {
					account.setAccountUserAccounts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> UserAccountSerDes.toDTO((String)object)
						).toArray(
							size -> new UserAccount[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					account.setActions(
						(Map)AccountSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					account.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "domains")) {
				if (jsonParserFieldValue != null) {
					account.setDomains(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					account.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					account.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					account.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfUsers")) {
				if (jsonParserFieldValue != null) {
					account.setNumberOfUsers(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "organizationIds")) {
				if (jsonParserFieldValue != null) {
					account.setOrganizationIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "parentAccountId")) {
				if (jsonParserFieldValue != null) {
					account.setParentAccountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					account.setStatus(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					account.setType(
						Account.Type.create((String)jsonParserFieldValue));
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}
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

package com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountSku;
import com.liferay.headless.commerce.admin.pricing.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class DiscountSkuSerDes {

	public static DiscountSku toDTO(String json) {
		DiscountSkuJSONParser discountSkuJSONParser =
			new DiscountSkuJSONParser();

		return discountSkuJSONParser.parseToDTO(json);
	}

	public static DiscountSku[] toDTOs(String json) {
		DiscountSkuJSONParser discountSkuJSONParser =
			new DiscountSkuJSONParser();

		return discountSkuJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DiscountSku discountSku) {
		if (discountSku == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (discountSku.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(discountSku.getActions()));
		}

		if (discountSku.getDiscountExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(discountSku.getDiscountExternalReferenceCode()));

			sb.append("\"");
		}

		if (discountSku.getDiscountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountId\": ");

			sb.append(discountSku.getDiscountId());
		}

		if (discountSku.getDiscountSkuId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountSkuId\": ");

			sb.append(discountSku.getDiscountSkuId());
		}

		if (discountSku.getProductId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productId\": ");

			sb.append(discountSku.getProductId());
		}

		if (discountSku.getProductName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productName\": ");

			sb.append(_toJSON(discountSku.getProductName()));
		}

		if (discountSku.getSku() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sku\": ");

			sb.append(String.valueOf(discountSku.getSku()));
		}

		if (discountSku.getSkuExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(discountSku.getSkuExternalReferenceCode()));

			sb.append("\"");
		}

		if (discountSku.getSkuId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuId\": ");

			sb.append(discountSku.getSkuId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DiscountSkuJSONParser discountSkuJSONParser =
			new DiscountSkuJSONParser();

		return discountSkuJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DiscountSku discountSku) {
		if (discountSku == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (discountSku.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(discountSku.getActions()));
		}

		if (discountSku.getDiscountExternalReferenceCode() == null) {
			map.put("discountExternalReferenceCode", null);
		}
		else {
			map.put(
				"discountExternalReferenceCode",
				String.valueOf(discountSku.getDiscountExternalReferenceCode()));
		}

		if (discountSku.getDiscountId() == null) {
			map.put("discountId", null);
		}
		else {
			map.put("discountId", String.valueOf(discountSku.getDiscountId()));
		}

		if (discountSku.getDiscountSkuId() == null) {
			map.put("discountSkuId", null);
		}
		else {
			map.put(
				"discountSkuId",
				String.valueOf(discountSku.getDiscountSkuId()));
		}

		if (discountSku.getProductId() == null) {
			map.put("productId", null);
		}
		else {
			map.put("productId", String.valueOf(discountSku.getProductId()));
		}

		if (discountSku.getProductName() == null) {
			map.put("productName", null);
		}
		else {
			map.put(
				"productName", String.valueOf(discountSku.getProductName()));
		}

		if (discountSku.getSku() == null) {
			map.put("sku", null);
		}
		else {
			map.put("sku", String.valueOf(discountSku.getSku()));
		}

		if (discountSku.getSkuExternalReferenceCode() == null) {
			map.put("skuExternalReferenceCode", null);
		}
		else {
			map.put(
				"skuExternalReferenceCode",
				String.valueOf(discountSku.getSkuExternalReferenceCode()));
		}

		if (discountSku.getSkuId() == null) {
			map.put("skuId", null);
		}
		else {
			map.put("skuId", String.valueOf(discountSku.getSkuId()));
		}

		return map;
	}

	public static class DiscountSkuJSONParser
		extends BaseJSONParser<DiscountSku> {

		@Override
		protected DiscountSku createDTO() {
			return new DiscountSku();
		}

		@Override
		protected DiscountSku[] createDTOArray(int size) {
			return new DiscountSku[size];
		}

		@Override
		protected void setField(
			DiscountSku discountSku, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					discountSku.setActions(
						(Map)DiscountSkuSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "discountExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					discountSku.setDiscountExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountId")) {
				if (jsonParserFieldValue != null) {
					discountSku.setDiscountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountSkuId")) {
				if (jsonParserFieldValue != null) {
					discountSku.setDiscountSkuId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productId")) {
				if (jsonParserFieldValue != null) {
					discountSku.setProductId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productName")) {
				if (jsonParserFieldValue != null) {
					discountSku.setProductName(
						(Map)DiscountSkuSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sku")) {
				if (jsonParserFieldValue != null) {
					discountSku.setSku(
						SkuSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "skuExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					discountSku.setSkuExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuId")) {
				if (jsonParserFieldValue != null) {
					discountSku.setSkuId(
						Long.valueOf((String)jsonParserFieldValue));
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
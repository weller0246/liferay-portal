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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.client.dto.v1_0.CustomField;
import com.liferay.headless.delivery.client.dto.v1_0.RelatedContent;
import com.liferay.headless.delivery.client.dto.v1_0.RenderedContent;
import com.liferay.headless.delivery.client.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class BlogPostingSerDes {

	public static BlogPosting toDTO(String json) {
		BlogPostingJSONParser blogPostingJSONParser =
			new BlogPostingJSONParser();

		return blogPostingJSONParser.parseToDTO(json);
	}

	public static BlogPosting[] toDTOs(String json) {
		BlogPostingJSONParser blogPostingJSONParser =
			new BlogPostingJSONParser();

		return blogPostingJSONParser.parseToDTOs(json);
	}

	public static String toJSON(BlogPosting blogPosting) {
		if (blogPosting == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (blogPosting.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(blogPosting.getActions()));
		}

		if (blogPosting.getAggregateRating() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"aggregateRating\": ");

			sb.append(String.valueOf(blogPosting.getAggregateRating()));
		}

		if (blogPosting.getAlternativeHeadline() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"alternativeHeadline\": ");

			sb.append("\"");

			sb.append(_escape(blogPosting.getAlternativeHeadline()));

			sb.append("\"");
		}

		if (blogPosting.getArticleBody() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"articleBody\": ");

			sb.append("\"");

			sb.append(_escape(blogPosting.getArticleBody()));

			sb.append("\"");
		}

		if (blogPosting.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(blogPosting.getCreator()));
		}

		if (blogPosting.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append("[");

			for (int i = 0; i < blogPosting.getCustomFields().length; i++) {
				sb.append(String.valueOf(blogPosting.getCustomFields()[i]));

				if ((i + 1) < blogPosting.getCustomFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (blogPosting.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(blogPosting.getDateCreated()));

			sb.append("\"");
		}

		if (blogPosting.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(blogPosting.getDateModified()));

			sb.append("\"");
		}

		if (blogPosting.getDatePublished() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"datePublished\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(blogPosting.getDatePublished()));

			sb.append("\"");
		}

		if (blogPosting.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(blogPosting.getDescription()));

			sb.append("\"");
		}

		if (blogPosting.getEncodingFormat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"encodingFormat\": ");

			sb.append("\"");

			sb.append(_escape(blogPosting.getEncodingFormat()));

			sb.append("\"");
		}

		if (blogPosting.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(blogPosting.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (blogPosting.getFriendlyUrlPath() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"friendlyUrlPath\": ");

			sb.append("\"");

			sb.append(_escape(blogPosting.getFriendlyUrlPath()));

			sb.append("\"");
		}

		if (blogPosting.getHeadline() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"headline\": ");

			sb.append("\"");

			sb.append(_escape(blogPosting.getHeadline()));

			sb.append("\"");
		}

		if (blogPosting.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(blogPosting.getId());
		}

		if (blogPosting.getImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"image\": ");

			sb.append(String.valueOf(blogPosting.getImage()));
		}

		if (blogPosting.getKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywords\": ");

			sb.append("[");

			for (int i = 0; i < blogPosting.getKeywords().length; i++) {
				sb.append("\"");

				sb.append(_escape(blogPosting.getKeywords()[i]));

				sb.append("\"");

				if ((i + 1) < blogPosting.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (blogPosting.getNumberOfComments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfComments\": ");

			sb.append(blogPosting.getNumberOfComments());
		}

		if (blogPosting.getRelatedContents() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"relatedContents\": ");

			sb.append("[");

			for (int i = 0; i < blogPosting.getRelatedContents().length; i++) {
				sb.append(String.valueOf(blogPosting.getRelatedContents()[i]));

				if ((i + 1) < blogPosting.getRelatedContents().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (blogPosting.getRenderedContents() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"renderedContents\": ");

			sb.append("[");

			for (int i = 0; i < blogPosting.getRenderedContents().length; i++) {
				sb.append(String.valueOf(blogPosting.getRenderedContents()[i]));

				if ((i + 1) < blogPosting.getRenderedContents().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (blogPosting.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(blogPosting.getSiteId());
		}

		if (blogPosting.getTaxonomyCategoryBriefs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryBriefs\": ");

			sb.append("[");

			for (int i = 0; i < blogPosting.getTaxonomyCategoryBriefs().length;
				 i++) {

				sb.append(
					String.valueOf(blogPosting.getTaxonomyCategoryBriefs()[i]));

				if ((i + 1) < blogPosting.getTaxonomyCategoryBriefs().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (blogPosting.getTaxonomyCategoryIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryIds\": ");

			sb.append("[");

			for (int i = 0; i < blogPosting.getTaxonomyCategoryIds().length;
				 i++) {

				sb.append(blogPosting.getTaxonomyCategoryIds()[i]);

				if ((i + 1) < blogPosting.getTaxonomyCategoryIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (blogPosting.getViewableBy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewableBy\": ");

			sb.append("\"");

			sb.append(blogPosting.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		BlogPostingJSONParser blogPostingJSONParser =
			new BlogPostingJSONParser();

		return blogPostingJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(BlogPosting blogPosting) {
		if (blogPosting == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (blogPosting.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(blogPosting.getActions()));
		}

		if (blogPosting.getAggregateRating() == null) {
			map.put("aggregateRating", null);
		}
		else {
			map.put(
				"aggregateRating",
				String.valueOf(blogPosting.getAggregateRating()));
		}

		if (blogPosting.getAlternativeHeadline() == null) {
			map.put("alternativeHeadline", null);
		}
		else {
			map.put(
				"alternativeHeadline",
				String.valueOf(blogPosting.getAlternativeHeadline()));
		}

		if (blogPosting.getArticleBody() == null) {
			map.put("articleBody", null);
		}
		else {
			map.put(
				"articleBody", String.valueOf(blogPosting.getArticleBody()));
		}

		if (blogPosting.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(blogPosting.getCreator()));
		}

		if (blogPosting.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields", String.valueOf(blogPosting.getCustomFields()));
		}

		if (blogPosting.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(blogPosting.getDateCreated()));
		}

		if (blogPosting.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(blogPosting.getDateModified()));
		}

		if (blogPosting.getDatePublished() == null) {
			map.put("datePublished", null);
		}
		else {
			map.put(
				"datePublished",
				liferayToJSONDateFormat.format(blogPosting.getDatePublished()));
		}

		if (blogPosting.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(blogPosting.getDescription()));
		}

		if (blogPosting.getEncodingFormat() == null) {
			map.put("encodingFormat", null);
		}
		else {
			map.put(
				"encodingFormat",
				String.valueOf(blogPosting.getEncodingFormat()));
		}

		if (blogPosting.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(blogPosting.getExternalReferenceCode()));
		}

		if (blogPosting.getFriendlyUrlPath() == null) {
			map.put("friendlyUrlPath", null);
		}
		else {
			map.put(
				"friendlyUrlPath",
				String.valueOf(blogPosting.getFriendlyUrlPath()));
		}

		if (blogPosting.getHeadline() == null) {
			map.put("headline", null);
		}
		else {
			map.put("headline", String.valueOf(blogPosting.getHeadline()));
		}

		if (blogPosting.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(blogPosting.getId()));
		}

		if (blogPosting.getImage() == null) {
			map.put("image", null);
		}
		else {
			map.put("image", String.valueOf(blogPosting.getImage()));
		}

		if (blogPosting.getKeywords() == null) {
			map.put("keywords", null);
		}
		else {
			map.put("keywords", String.valueOf(blogPosting.getKeywords()));
		}

		if (blogPosting.getNumberOfComments() == null) {
			map.put("numberOfComments", null);
		}
		else {
			map.put(
				"numberOfComments",
				String.valueOf(blogPosting.getNumberOfComments()));
		}

		if (blogPosting.getRelatedContents() == null) {
			map.put("relatedContents", null);
		}
		else {
			map.put(
				"relatedContents",
				String.valueOf(blogPosting.getRelatedContents()));
		}

		if (blogPosting.getRenderedContents() == null) {
			map.put("renderedContents", null);
		}
		else {
			map.put(
				"renderedContents",
				String.valueOf(blogPosting.getRenderedContents()));
		}

		if (blogPosting.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(blogPosting.getSiteId()));
		}

		if (blogPosting.getTaxonomyCategoryBriefs() == null) {
			map.put("taxonomyCategoryBriefs", null);
		}
		else {
			map.put(
				"taxonomyCategoryBriefs",
				String.valueOf(blogPosting.getTaxonomyCategoryBriefs()));
		}

		if (blogPosting.getTaxonomyCategoryIds() == null) {
			map.put("taxonomyCategoryIds", null);
		}
		else {
			map.put(
				"taxonomyCategoryIds",
				String.valueOf(blogPosting.getTaxonomyCategoryIds()));
		}

		if (blogPosting.getViewableBy() == null) {
			map.put("viewableBy", null);
		}
		else {
			map.put("viewableBy", String.valueOf(blogPosting.getViewableBy()));
		}

		return map;
	}

	public static class BlogPostingJSONParser
		extends BaseJSONParser<BlogPosting> {

		@Override
		protected BlogPosting createDTO() {
			return new BlogPosting();
		}

		@Override
		protected BlogPosting[] createDTOArray(int size) {
			return new BlogPosting[size];
		}

		@Override
		protected void setField(
			BlogPosting blogPosting, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setActions(
						(Map)BlogPostingSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "aggregateRating")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setAggregateRating(
						AggregateRatingSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "alternativeHeadline")) {

				if (jsonParserFieldValue != null) {
					blogPosting.setAlternativeHeadline(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "articleBody")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setArticleBody((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setCustomFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CustomFieldSerDes.toDTO((String)object)
						).toArray(
							size -> new CustomField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "datePublished")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setDatePublished(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setEncodingFormat((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					blogPosting.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "friendlyUrlPath")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setFriendlyUrlPath(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setHeadline((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "image")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setImage(
						ImageSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfComments")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setNumberOfComments(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "relatedContents")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setRelatedContents(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RelatedContentSerDes.toDTO((String)object)
						).toArray(
							size -> new RelatedContent[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "renderedContents")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setRenderedContents(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RenderedContentSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new RenderedContent[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryBriefs")) {

				if (jsonParserFieldValue != null) {
					blogPosting.setTaxonomyCategoryBriefs(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> TaxonomyCategoryBriefSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new TaxonomyCategoryBrief[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryIds")) {

				if (jsonParserFieldValue != null) {
					blogPosting.setTaxonomyCategoryIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setViewableBy(
						BlogPosting.ViewableBy.create(
							(String)jsonParserFieldValue));
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
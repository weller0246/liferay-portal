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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class XMLServiceFinderNameCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException {

		if (!fileName.endsWith("/service.xml")) {
			return content;
		}

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		for (Element entityElement :
				(List<Element>)rootElement.elements("entity")) {

			String entityName = entityElement.attributeValue("name");

			for (Element finderElement :
					(List<Element>)entityElement.elements("finder")) {

				String finderName = finderElement.attributeValue("name");

				List<Map<String, String>> finderColumns = new ArrayList<>();

				for (Element finderColumnElement :
						(List<Element>)finderElement.elements(
							"finder-column")) {

					Map<String, String> attributesMap = new LinkedHashMap<>();

					String comparator = finderColumnElement.attributeValue(
						"comparator");

					if (Validator.isNotNull(comparator)) {
						attributesMap.put(
							"comparator",
							finderColumnElement.attributeValue("comparator"));
					}

					attributesMap.put(
						"name", finderColumnElement.attributeValue("name"));

					finderColumns.add(attributesMap);
				}

				_checkFinderName(
					fileName, entityName, finderName, finderColumns);
			}
		}

		return content;
	}

	private String _checkCaps(String name) {
		for (String[] array : _ALL_CAPS_STRINGS) {
			String s = array[1];

			int x = -1;

			while (true) {
				x = name.indexOf(s, x + 1);

				if (x == -1) {
					break;
				}

				int y = x + s.length();

				if ((y != name.length()) &&
					!Character.isUpperCase(name.charAt(y))) {

					continue;
				}

				return name.substring(0, x) + array[0] + name.substring(y);
			}
		}

		return name;
	}

	private void _checkFinderName(
		String fileName, String entityName, String finderName,
		List<Map<String, String>> finderColumns) {

		if (finderColumns.size() == 1) {
			Map<String, String> finderColumn = finderColumns.get(0);

			if (!finderColumn.containsKey("name")) {
				return;
			}

			String expectedFinderName = _checkCaps(
				TextFormatter.format(
					finderColumn.get("name"), TextFormatter.G));

			if (finderColumn.containsKey("comparator")) {
				expectedFinderName =
					_comparatorNamesMap.get(finderColumn.get("comparator")) +
						expectedFinderName;
			}

			if (!finderName.startsWith(expectedFinderName)) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Finder name '", entityName, "#", finderName,
						"' should starts with the combination of comparator ",
						"prefix and finder colume name"));
			}

			return;
		}

		List<String> splitFinderNames = ListUtil.fromString(
			finderName, StringPool.UNDERLINE);

		Collections.sort(splitFinderNames);

		outerLoop:
		for (Map<String, String> finderColumn : finderColumns) {
			if (!finderColumn.containsKey("name")) {
				continue;
			}

			String finderColumnName = finderColumn.get("name");

			finderColumnName = StringUtil.upperCase(
				finderColumnName.substring(0, 1));

			String expectedFinderName = StringPool.BLANK;

			if (finderColumn.containsKey("comparator")) {
				expectedFinderName += _comparatorNamesMap.get(
					finderColumn.get("comparator"));
			}

			expectedFinderName = expectedFinderName + finderColumnName;

			for (String splitFinderName : splitFinderNames) {
				if (splitFinderName.startsWith(expectedFinderName)) {
					splitFinderNames.remove(splitFinderName);

					continue outerLoop;
				}
			}

			addMessage(
				fileName,
				StringBundler.concat(
					"Finder name '", entityName, "#", finderName,
					"' should be combined by finder colume names(",
					"at least the first character) following by each ",
					"comparator prefix with delimiter '_'"));
		}
	}

	private static final String[][] _ALL_CAPS_STRINGS = {
		{"DDL", "Ddl"}, {"DDM", "Ddm"}, {"DL", "Dl"}, {"PK", "Pk"}
	};

	private static final Map<String, String> _comparatorNamesMap =
		HashMapBuilder.put(
			"!=", "Not"
		).put(
			"<", "Lt"
		).put(
			"<=", "Lte"
		).put(
			"=", StringPool.BLANK
		).put(
			">", "Gt"
		).put(
			">=", "Gte"
		).put(
			"is", "Is"
		).put(
			"LIKE", "Like"
		).build();

}
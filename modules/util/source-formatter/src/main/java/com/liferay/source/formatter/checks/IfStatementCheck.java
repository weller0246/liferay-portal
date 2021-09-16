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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.JSPSourceProcessor;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class IfStatementCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _ifStatementPattern.matcher(content);

		while (matcher.find()) {
			if (!isJavaSource(content, matcher.start())) {
				continue;
			}

			IfStatement ifStatement1 = _getIfStatement(
				content, matcher.start());

			if (ifStatement1 == null) {
				continue;
			}

			String followingCode = ifStatement1.getFollowingCode();

			if (!followingCode.startsWith("//") &&
				!followingCode.startsWith("else ") &&
				Validator.isNull(ifStatement1.getBody())) {

				addMessage(
					fileName, "If-statement with empty body",
					getLineNumber(content, matcher.start()));
			}

			if (!followingCode.startsWith("if (")) {
				continue;
			}

			IfStatement ifStatement2 = _getIfStatement(
				content, ifStatement1.getEnd());

			String newContent = _combineStatementsWithSameBodies(
				content, ifStatement1, ifStatement2);

			if (!content.equals(newContent)) {
				if (getSourceProcessor() instanceof JSPSourceProcessor) {
					addMessage(
						fileName,
						"Merge consecutive if-statements when executing " +
							"identical code",
						getLineNumber(content, matcher.start()));

					continue;
				}

				return newContent;
			}
		}

		return content;
	}

	private String _combineStatementsWithSameBodies(
		String content, IfStatement ifStatement1, IfStatement ifStatement2) {

		String body = ifStatement1.getBody();

		if (!body.equals(ifStatement2.getBody())) {
			return content;
		}

		Matcher matcher = _assignStatementPattern.matcher(body);

		if (matcher.find()) {
			String clause = ifStatement2.getClause();

			if (clause.matches("(?s).*\\W" + matcher.group(1) + "\\W.*")) {
				return content;
			}
		}
		else if (!body.matches(
					"(?s)(.+\t)?(break|continue|return|throw)(\\s|;).*")) {

			return content;
		}

		String followingCode = ifStatement2.getFollowingCode();

		if (followingCode.startsWith("//") ||
			followingCode.startsWith("else ")) {

			return content;
		}

		String combinedStatements = StringBundler.concat(
			"if (", ifStatement1.getClause(), " || ", ifStatement2.getClause(),
			") {\n", ifStatement1.getBody(), "\n}\n");

		if (combinedStatements.contains("&&")) {
			int count =
				StringUtil.count(combinedStatements, "||") +
					StringUtil.count(combinedStatements, "&&");

			if (count > 2) {
				return content;
			}
		}

		String consecutiveStatements = content.substring(
			ifStatement1.getStart(), ifStatement2.getEnd());

		return StringUtil.replace(
			content, consecutiveStatements, combinedStatements,
			ifStatement1.getStart() - 1);
	}

	private int _getClosePos(
		String content, String openChar, String closeChar, int start) {

		int closePos = start;

		while (true) {
			closePos = content.indexOf(closeChar, closePos + 1);

			if (closePos == -1) {
				return -1;
			}

			String s = content.substring(start, closePos + 1);

			int level = getLevel(s, openChar, closeChar);

			if (level == 0) {
				return closePos;
			}

			if (level == -1) {
				return -1;
			}
		}
	}

	private IfStatement _getIfStatement(String content, int pos) {
		int x = _getClosePos(content, "(", ")", pos);

		if ((x == -1) || !Objects.equals(content.substring(x, x + 3), ") {")) {
			return null;
		}

		int y = _getClosePos(content, "{", "}", x + 1);

		if (y == -1) {
			return null;
		}

		return new IfStatement(
			StringUtil.trim(content.substring(x + 3, y)),
			content.substring(content.indexOf("(", pos), x + 1),
			StringUtil.trim(content.substring(y + 1)), pos, y + 1);
	}

	private static final Pattern _assignStatementPattern = Pattern.compile(
		"^(\\w+) =[^;]+;$");
	private static final Pattern _ifStatementPattern = Pattern.compile(
		"[\n\t]if \\(");

	private class IfStatement {

		public IfStatement(
			String body, String clause, String followingCode, int start,
			int end) {

			_body = body;
			_clause = clause;
			_followingCode = followingCode;
			_start = start;
			_end = end;
		}

		public String getBody() {
			return _body;
		}

		public String getClause() {
			return _clause;
		}

		public int getEnd() {
			return _end;
		}

		public String getFollowingCode() {
			return _followingCode;
		}

		public int getStart() {
			return _start;
		}

		private final String _body;
		private final String _clause;
		private final int _end;
		private final String _followingCode;
		private final int _start;

	}

}
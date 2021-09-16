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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class MethodNamingCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (AnnotationUtil.containsAnnotation(detailAST, "Override")) {
			return;
		}

		String methodName = _getMethodName(detailAST);

		_checkMethodNamePrefix(detailAST, methodName);
		_checkTypeName(detailAST, methodName);
	}

	private void _checkMethodNamePrefix(
		DetailAST detailAST, String methodName) {

		String typeName = getTypeName(
			detailAST.findFirstToken(TokenTypes.TYPE), false);
		Matcher matcher = null;

		for (String[] array : _METHOD_NAME_PREFIXS) {
			if (array[0].equals("get") && !typeName.equals("boolean")) {
				continue;
			}

			Pattern pattern = Pattern.compile("^_" + array[0] + "([A-Z])(.*)$");

			matcher = pattern.matcher(methodName);

			if (!matcher.find()) {
				continue;
			}

			String newMethodName = StringPool.UNDERLINE + array[1];

			if (array[0].equals("get")) {
				newMethodName = newMethodName + matcher.group(1);
			}
			else {
				newMethodName =
					newMethodName + StringUtil.toLowerCase(matcher.group(1));
			}

			newMethodName = newMethodName + matcher.group(2);

			String noUnderscoreMethodName = StringPool.BLANK;

			if (array[1].equals(StringPool.BLANK)) {
				noUnderscoreMethodName = methodName.substring(1);
			}
			else {
				noUnderscoreMethodName = newMethodName.substring(1);
			}

			DetailAST parentDetailAST = detailAST.getParent();

			List<DetailAST> methodDefinitionDetailASTList = getAllChildTokens(
				parentDetailAST, false, TokenTypes.METHOD_DEF);

			for (DetailAST methodDefinitionDetailAST :
					methodDefinitionDetailASTList) {

				String curMethodName = _getMethodName(
					methodDefinitionDetailAST);

				if (curMethodName.equals(noUnderscoreMethodName) ||
					(curMethodName.equals(newMethodName) &&
					 Objects.equals(
						 getSignature(detailAST),
						 getSignature(methodDefinitionDetailAST)))) {

					return;
				}
			}

			log(detailAST, _MSG_RENAME_METHOD, methodName, newMethodName);
		}
	}

	private void _checkTypeName(DetailAST detailAST, String methodName) {
		String absolutePath = getAbsolutePath();

		if ((!methodName.matches("get[A-Z].*") ||
			 !absolutePath.contains("/internal/")) &&
			!methodName.matches("_get[A-Z].*")) {

			return;
		}

		String returnTypeName = getTypeName(detailAST, true);

		if (returnTypeName.contains("[]") ||
			methodName.matches(".*" + returnTypeName + "[0-9]*") ||
			methodName.matches("_?get" + returnTypeName + ".*")) {

			return;
		}

		List<String> enforceTypeNames = getAttributeValues(
			_ENFORCE_TYPE_NAMES_KEY);

		for (String enforceTypeName : enforceTypeNames) {
			if (returnTypeName.matches(enforceTypeName)) {
				log(detailAST, _MSG_INCORRECT_ENDING_METHOD, returnTypeName);

				return;
			}
		}
	}

	private String _getMethodName(DetailAST detailAST) {
		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		return nameDetailAST.getText();
	}

	private static final String _ENFORCE_TYPE_NAMES_KEY = "enforceTypeNames";

	private static final String[][] _METHOD_NAME_PREFIXS = {
		{"do", StringPool.BLANK}, {"get", "is"}
	};

	private static final String _MSG_INCORRECT_ENDING_METHOD =
		"method.incorrect.ending";

	private static final String _MSG_RENAME_METHOD = "method.rename";

}
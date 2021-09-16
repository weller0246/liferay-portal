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

import antlr.CommonASTWithHiddenTokens;
import antlr.CommonHiddenStreamToken;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.JSPImportsFormatter;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checkstyle.util.CheckstyleUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterCheckUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hugo Huijser
 */
public abstract class BaseCheck extends AbstractCheck {

	@Override
	public int[] getAcceptableTokens() {
		return getDefaultTokens();
	}

	@Override
	public int[] getRequiredTokens() {
		return getDefaultTokens();
	}

	public void setAttributes(String attributes) throws JSONException {
		_attributesJSONObject = new JSONObjectImpl(attributes);
	}

	public void setExcludes(String excludes) throws JSONException {
		_excludesJSONObject = new JSONObjectImpl(excludes);
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		if (!isAttributeValue(SourceFormatterCheckUtil.ENABLED_KEY, true)) {
			return;
		}

		if (!isAttributeValue(CheckstyleUtil.SHOW_DEBUG_INFORMATION_KEY)) {
			doVisitToken(detailAST);

			return;
		}

		long startTime = System.currentTimeMillis();

		doVisitToken(detailAST);

		long endTime = System.currentTimeMillis();

		Class<?> clazz = getClass();

		DebugUtil.increaseProcessingTime(
			clazz.getSimpleName(), endTime - startTime);
	}

	protected abstract void doVisitToken(DetailAST detailAST);

	protected boolean equals(DetailAST detailAST1, DetailAST detailAST2) {
		return Objects.equals(detailAST1.toString(), detailAST2.toString());
	}

	protected String getAbsolutePath() {
		FileContents fileContents = getFileContents();

		String fileName = StringUtil.replace(
			fileContents.getFileName(), CharPool.BACK_SLASH, CharPool.SLASH);

		return SourceUtil.getAbsolutePath(fileName);
	}

	protected List<DetailAST> getAllChildTokens(
		DetailAST detailAST, boolean recursive, int... tokenTypes) {

		return DetailASTUtil.getAllChildTokens(
			detailAST, recursive, tokenTypes);
	}

	protected DetailAST getAnnotationMemberValuePairDetailAST(
		DetailAST annotationDetailAST, String key) {

		List<DetailAST> annotationMemberValuePairDetailASTList =
			getAllChildTokens(
				annotationDetailAST, false,
				TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

		for (DetailAST annotationMemberValuePairDetailAST :
				annotationMemberValuePairDetailASTList) {

			DetailAST firstChildDetailAST =
				annotationMemberValuePairDetailAST.getFirstChild();

			if ((firstChildDetailAST.getType() == TokenTypes.IDENT) &&
				Objects.equals(firstChildDetailAST.getText(), key)) {

				return annotationMemberValuePairDetailAST;
			}
		}

		return null;
	}

	protected String getAttributeValue(String attributeKey) {
		return getAttributeValue(attributeKey, StringPool.BLANK);
	}

	protected String getAttributeValue(
		String attributeKey, String defaultValue) {

		return SourceFormatterCheckUtil.getJSONObjectValue(
			_attributesJSONObject, _attributeValueMap, attributeKey,
			defaultValue, getAbsolutePath(), getBaseDirName());
	}

	protected List<String> getAttributeValues(String attributeKey) {
		return SourceFormatterCheckUtil.getJSONObjectValues(
			_attributesJSONObject, attributeKey, _attributeValuesMap,
			getAbsolutePath(), getBaseDirName());
	}

	protected String getBaseDirName() {
		return SourceFormatterCheckUtil.getJSONObjectValue(
			_attributesJSONObject, _attributeValueMap,
			CheckstyleUtil.BASE_DIR_NAME_KEY, StringPool.BLANK, null, null,
			true);
	}

	protected List<String> getChainedMethodNames(
		DetailAST methodCallDetailAST) {

		List<String> chainedMethodNames = new ArrayList<>();

		chainedMethodNames.add(getMethodName(methodCallDetailAST));

		while (true) {
			DetailAST parentDetailAST = methodCallDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.DOT) {
				return chainedMethodNames;
			}

			DetailAST grandParentDetailAST = parentDetailAST.getParent();

			if (grandParentDetailAST.getType() != TokenTypes.METHOD_CALL) {
				DetailAST siblingDetailAST =
					methodCallDetailAST.getNextSibling();

				if (siblingDetailAST.getType() == TokenTypes.IDENT) {
					chainedMethodNames.add(siblingDetailAST.getText());
				}

				return chainedMethodNames;
			}

			methodCallDetailAST = grandParentDetailAST;

			chainedMethodNames.add(getMethodName(methodCallDetailAST));
		}
	}

	protected String getClassOrVariableName(DetailAST methodCallDetailAST) {
		DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.DOT);

		if (dotDetailAST == null) {
			return null;
		}

		DetailAST firstChildDetailAST = dotDetailAST.getFirstChild();

		FullIdent fullIdent = null;

		if (firstChildDetailAST.getType() == TokenTypes.LITERAL_NEW) {
			fullIdent = FullIdent.createFullIdent(
				firstChildDetailAST.getFirstChild());
		}
		else {
			fullIdent = FullIdent.createFullIdent(dotDetailAST);
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if ((firstChildDetailAST != null) &&
			(firstChildDetailAST.getType() == TokenTypes.DOT)) {

			return fullIdent.getText();
		}

		String s = fullIdent.getText();

		int x = s.lastIndexOf(CharPool.PERIOD);

		if (x == -1) {
			return s;
		}

		return s.substring(0, x);
	}

	protected List<DetailAST> getDependentIdentDetailASTList(
		DetailAST variableDefinitionDetailAST, int lineNumber) {

		return getDependentIdentDetailASTList(
			variableDefinitionDetailAST, lineNumber, false);
	}

	protected List<DetailAST> getDependentIdentDetailASTList(
		DetailAST variableDefinitionDetailAST, int lineNumber,
		boolean includeGetters) {

		List<Variable> variables = _getVariables(
			variableDefinitionDetailAST, includeGetters);

		List<DetailAST> dependentIdentDetailASTList = new ArrayList<>();

		return _addDependentIdentDetailASTList(
			dependentIdentDetailASTList,
			variableDefinitionDetailAST.getNextSibling(), variables, lineNumber,
			includeGetters);
	}

	protected int getEndLineNumber(DetailAST detailAST) {
		int endLineNumber = detailAST.getLineNo();

		for (DetailAST childDetailAST :
				getAllChildTokens(detailAST, true, ALL_TYPES)) {

			if (childDetailAST.getLineNo() > endLineNumber) {
				endLineNumber = childDetailAST.getLineNo();
			}
		}

		return endLineNumber;
	}

	protected String getFullyQualifiedTypeName(
		DetailAST typeDetailAST, boolean checkPackage) {

		return getFullyQualifiedTypeName(
			getTypeName(typeDetailAST, false), typeDetailAST, checkPackage);
	}

	protected String getFullyQualifiedTypeName(
		String typeName, DetailAST detailAST, boolean checkPackage) {

		if (typeName.contains(StringPool.PERIOD) &&
			Character.isLowerCase(typeName.charAt(0))) {

			return typeName;
		}

		List<String> importNames = getImportNames(detailAST);

		for (String importName : importNames) {
			int x = importName.lastIndexOf(CharPool.PERIOD);

			String className = importName.substring(x + 1);

			if (typeName.equals(className)) {
				return importName;
			}

			if (typeName.startsWith(className + ".")) {
				return StringUtil.replaceLast(importName, className, typeName);
			}
		}

		if (!checkPackage) {
			return null;
		}

		FileContents fileContents = getFileContents();

		FileText fileText = fileContents.getText();

		return JavaSourceUtil.getPackageName((String)fileText.getFullText()) +
			StringPool.PERIOD + typeName;
	}

	protected CommonHiddenStreamToken getHiddenAfter(DetailAST detailAST) {
		CommonASTWithHiddenTokens commonASTWithHiddenTokens =
			(CommonASTWithHiddenTokens)detailAST;

		return commonASTWithHiddenTokens.getHiddenAfter();
	}

	protected CommonHiddenStreamToken getHiddenBefore(DetailAST detailAST) {
		CommonASTWithHiddenTokens commonASTWithHiddenTokens =
			(CommonASTWithHiddenTokens)detailAST;

		CommonHiddenStreamToken commonHiddenStreamToken =
			commonASTWithHiddenTokens.getHiddenBefore();

		if (commonHiddenStreamToken != null) {
			return commonHiddenStreamToken;
		}

		DetailAST previousSiblingDetailAST = detailAST.getPreviousSibling();

		while (true) {
			if (previousSiblingDetailAST == null) {
				return null;
			}

			commonHiddenStreamToken = getHiddenAfter(previousSiblingDetailAST);

			if (commonHiddenStreamToken != null) {
				return commonHiddenStreamToken;
			}

			previousSiblingDetailAST = previousSiblingDetailAST.getLastChild();
		}
	}

	protected List<String> getImportNames(DetailAST detailAST) {
		if (isJSPFile()) {
			String absolutePath = getAbsolutePath();

			return _getJSPImportNames(
				absolutePath.substring(
					0, absolutePath.lastIndexOf(CharPool.SLASH)));
		}

		DetailAST rootDetailAST = detailAST;

		while (true) {
			if (rootDetailAST.getParent() != null) {
				rootDetailAST = rootDetailAST.getParent();
			}
			else if (rootDetailAST.getPreviousSibling() != null) {
				rootDetailAST = rootDetailAST.getPreviousSibling();
			}
			else {
				break;
			}
		}

		List<String> importNames = new ArrayList<>();

		DetailAST siblingDetailAST = rootDetailAST.getNextSibling();

		while (true) {
			if (siblingDetailAST == null) {
				return importNames;
			}

			if (siblingDetailAST.getType() == TokenTypes.IMPORT) {
				FullIdent importIdent = FullIdent.createFullIdentBelow(
					siblingDetailAST);

				importNames.add(importIdent.getText());
			}
			else if (siblingDetailAST.getType() != TokenTypes.STATIC_IMPORT) {
				return importNames;
			}

			siblingDetailAST = siblingDetailAST.getNextSibling();
		}
	}

	protected int getMaxDirLevel() {
		return GetterUtil.getInteger(
			getAttributeValue(CheckstyleUtil.MAX_DIR_LEVEL_KEY));
	}

	protected List<DetailAST> getMethodCalls(
		DetailAST detailAST, String methodName) {

		return getMethodCalls(detailAST, null, methodName);
	}

	protected List<DetailAST> getMethodCalls(
		DetailAST detailAST, String className, String methodName) {

		List<DetailAST> list = new ArrayList<>();

		List<DetailAST> methodCallDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.DOT);

			if (dotDetailAST == null) {
				continue;
			}

			List<DetailAST> nameDetailASTList = getAllChildTokens(
				dotDetailAST, false, TokenTypes.IDENT);

			if (nameDetailASTList.size() != 2) {
				continue;
			}

			DetailAST classNameDetailAST = nameDetailASTList.get(0);
			DetailAST methodNameDetailAST = nameDetailASTList.get(1);

			String methodCallClassName = classNameDetailAST.getText();
			String methodCallMethodName = methodNameDetailAST.getText();

			if (((className == null) ||
				 methodCallClassName.equals(className)) &&
				methodCallMethodName.equals(methodName)) {

				list.add(methodCallDetailAST);
			}
		}

		return list;
	}

	protected String getMethodName(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.METHOD_CALL) {
			return null;
		}

		DetailAST dotDetailAST = detailAST.findFirstToken(TokenTypes.DOT);

		if (dotDetailAST == null) {
			DetailAST nameDetailAST = detailAST.findFirstToken(
				TokenTypes.IDENT);

			return nameDetailAST.getText();
		}

		List<DetailAST> nameDetailASTList = getAllChildTokens(
			dotDetailAST, false, TokenTypes.IDENT);

		DetailAST methodNameDetailAST = nameDetailASTList.get(
			nameDetailASTList.size() - 1);

		return methodNameDetailAST.getText();
	}

	protected List<DetailAST> getParameterDefs(DetailAST detailAST) {
		if ((detailAST.getType() != TokenTypes.CTOR_DEF) &&
			(detailAST.getType() != TokenTypes.METHOD_DEF)) {

			return new ArrayList<>();
		}

		DetailAST parametersDetailAST = detailAST.findFirstToken(
			TokenTypes.PARAMETERS);

		return getAllChildTokens(
			parametersDetailAST, false, TokenTypes.PARAMETER_DEF);
	}

	protected DetailAST getParameterDetailAST(DetailAST methodCallDetailAST) {
		DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.ELIST);

		DetailAST exprDetailAST = elistDetailAST.findFirstToken(
			TokenTypes.EXPR);

		if (exprDetailAST == null) {
			return null;
		}

		return exprDetailAST.getFirstChild();
	}

	protected List<String> getParameterNames(DetailAST detailAST) {
		List<String> parameterNames = new ArrayList<>();

		for (DetailAST parameterDefinitionDetailAST :
				getParameterDefs(detailAST)) {

			DetailAST identDetailAST =
				parameterDefinitionDetailAST.findFirstToken(TokenTypes.IDENT);

			parameterNames.add(identDetailAST.getText());
		}

		return parameterNames;
	}

	protected DetailAST getParentWithTokenType(
		DetailAST detailAST, int... tokenTypes) {

		DetailAST parentDetailAST = detailAST.getParent();

		while (parentDetailAST != null) {
			if (ArrayUtil.contains(tokenTypes, parentDetailAST.getType())) {
				return parentDetailAST;
			}

			parentDetailAST = parentDetailAST.getParent();
		}

		return null;
	}

	protected String getSignature(DetailAST detailAST) {
		if ((detailAST.getType() != TokenTypes.CTOR_DEF) &&
			(detailAST.getType() != TokenTypes.METHOD_DEF)) {

			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		sb.append(CharPool.OPEN_PARENTHESIS);

		DetailAST parametersDetailAST = detailAST.findFirstToken(
			TokenTypes.PARAMETERS);

		List<DetailAST> parameterDefinitionDetailASTList = getAllChildTokens(
			parametersDetailAST, false, TokenTypes.PARAMETER_DEF);

		if (parameterDefinitionDetailASTList.isEmpty()) {
			sb.append(CharPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		for (DetailAST parameterDefinitionDetailAST :
				parameterDefinitionDetailASTList) {

			sb.append(getTypeName(parameterDefinitionDetailAST, true));
			sb.append(CharPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(CharPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected int getStartLineNumber(DetailAST detailAST) {
		int startLineNumber = detailAST.getLineNo();

		for (DetailAST childDetailAST :
				getAllChildTokens(detailAST, true, ALL_TYPES)) {

			if (childDetailAST.getLineNo() < startLineNumber) {
				startLineNumber = childDetailAST.getLineNo();
			}
		}

		return startLineNumber;
	}

	protected String getTypeName(
		DetailAST detailAST, boolean includeTypeArguments) {

		if (detailAST == null) {
			return StringPool.BLANK;
		}

		DetailAST typeDetailAST = detailAST;

		if ((detailAST.getType() != TokenTypes.TYPE) &&
			(detailAST.getType() != TokenTypes.TYPE_ARGUMENT)) {

			typeDetailAST = detailAST.findFirstToken(TokenTypes.TYPE);
		}

		DetailAST childDetailAST = typeDetailAST.getFirstChild();

		if (childDetailAST == null) {
			return StringPool.BLANK;
		}

		int arrayDimension = 0;

		while (childDetailAST.getType() == TokenTypes.ARRAY_DECLARATOR) {
			arrayDimension++;

			childDetailAST = childDetailAST.getFirstChild();
		}

		StringBundler sb = new StringBundler(1 + arrayDimension);

		FullIdent typeIdent = FullIdent.createFullIdent(childDetailAST);

		sb.append(typeIdent.getText());

		for (int i = 0; i < arrayDimension; i++) {
			sb.append("[]");
		}

		if (!includeTypeArguments) {
			return sb.toString();
		}

		DetailAST typeArgumentsDetailAST = typeDetailAST.findFirstToken(
			TokenTypes.TYPE_ARGUMENTS);

		if ((typeArgumentsDetailAST == null) &&
			(childDetailAST.getType() == TokenTypes.DOT)) {

			typeArgumentsDetailAST = childDetailAST.findFirstToken(
				TokenTypes.TYPE_ARGUMENTS);
		}

		if (typeArgumentsDetailAST == null) {
			return sb.toString();
		}

		sb.append(CharPool.LESS_THAN);

		List<DetailAST> typeArgumentDetailASTList = getAllChildTokens(
			typeArgumentsDetailAST, false, TokenTypes.TYPE_ARGUMENT);

		for (DetailAST typeArgumentDetailAST : typeArgumentDetailASTList) {
			sb.append(getTypeName(typeArgumentDetailAST, true));
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(CharPool.GREATER_THAN);

		return sb.toString();
	}

	protected Tuple getTypeNamesTuple(String fileName, String category) {
		File typeNamesFile = SourceFormatterUtil.getFile(
			getBaseDirName(),
			"modules/util/source-formatter/src/main/resources/dependencies/" +
				fileName,
			getMaxDirLevel());

		JSONObject jsonObject = null;

		try {
			String content = null;

			if (typeNamesFile != null) {
				content = FileUtil.read(typeNamesFile);
			}
			else {
				Class<?> clazz = getClass();

				ClassLoader classLoader = clazz.getClassLoader();

				content = StringUtil.read(
					classLoader.getResourceAsStream(
						"dependencies/" + fileName));
			}

			if (Validator.isNotNull(content)) {
				jsonObject = new JSONObjectImpl(content);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		if (jsonObject == null) {
			jsonObject = new JSONObjectImpl();

			jsonObject.put(category, new JSONArrayImpl());
		}

		return new Tuple(jsonObject, typeNamesFile);
	}

	protected List<DetailAST> getVariableCallerDetailASTList(
		DetailAST variableDefinitionDetailAST) {

		DetailAST identDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (identDetailAST == null) {
			return Collections.emptyList();
		}

		return getVariableCallerDetailASTList(
			variableDefinitionDetailAST, identDetailAST.getText());
	}

	protected List<DetailAST> getVariableCallerDetailASTList(
		DetailAST variableDefinitionDetailAST, String variableName) {

		List<DetailAST> variableCallerDetailASTList = new ArrayList<>();

		DetailAST parentDetailAST = variableDefinitionDetailAST.getParent();

		boolean globalVariable = false;
		DetailAST rangeDetailAST = null;

		if (parentDetailAST.getType() == TokenTypes.FOR_INIT) {
			rangeDetailAST = parentDetailAST.getParent();
		}
		else if (parentDetailAST.getType() == TokenTypes.OBJBLOCK) {
			rangeDetailAST = parentDetailAST;

			globalVariable = true;
		}
		else if (parentDetailAST.getType() == TokenTypes.SLIST) {
			rangeDetailAST = parentDetailAST;
		}
		else {
			if (parentDetailAST.getType() != TokenTypes.LITERAL_CATCH) {
				parentDetailAST = parentDetailAST.getParent();
			}

			rangeDetailAST = parentDetailAST.getLastChild();
		}

		if ((rangeDetailAST.getType() != TokenTypes.LITERAL_FOR) &&
			(rangeDetailAST.getType() != TokenTypes.OBJBLOCK) &&
			(rangeDetailAST.getType() != TokenTypes.SLIST)) {

			return variableCallerDetailASTList;
		}

		List<DetailAST> nameDetailASTList = getAllChildTokens(
			rangeDetailAST, true, TokenTypes.IDENT);

		for (DetailAST nameDetailAST : nameDetailASTList) {
			if (!variableName.equals(nameDetailAST.getText())) {
				continue;
			}

			parentDetailAST = nameDetailAST.getParent();

			if ((parentDetailAST.getType() == TokenTypes.METHOD_CALL) ||
				(parentDetailAST.getType() == TokenTypes.PARAMETER_DEF) ||
				(parentDetailAST.getType() == TokenTypes.VARIABLE_DEF)) {

				continue;
			}

			if (parentDetailAST.getType() == TokenTypes.DOT) {
				if (globalVariable ||
					equals(parentDetailAST.getFirstChild(), nameDetailAST)) {

					variableCallerDetailASTList.add(nameDetailAST);
				}
			}
			else {
				variableCallerDetailASTList.add(nameDetailAST);
			}
		}

		return variableCallerDetailASTList;
	}

	protected DetailAST getVariableDefinitionDetailAST(
		DetailAST detailAST, String variableName) {

		return getVariableDefinitionDetailAST(detailAST, variableName, true);
	}

	protected DetailAST getVariableDefinitionDetailAST(
		DetailAST detailAST, String variableName,
		boolean includeGlobalVariables) {

		DetailAST previousDetailAST = detailAST;

		while (true) {
			if (includeGlobalVariables &&
				((previousDetailAST.getType() == TokenTypes.CLASS_DEF) ||
				 (previousDetailAST.getType() == TokenTypes.ENUM_DEF) ||
				 (previousDetailAST.getType() == TokenTypes.INTERFACE_DEF))) {

				DetailAST objBlockDetailAST = previousDetailAST.findFirstToken(
					TokenTypes.OBJBLOCK);

				List<DetailAST> variableDefinitionDetailASTList =
					getAllChildTokens(
						objBlockDetailAST, false, TokenTypes.VARIABLE_DEF);

				for (DetailAST variableDefinitionDetailAST :
						variableDefinitionDetailASTList) {

					if (variableName.equals(
							_getVariableName(variableDefinitionDetailAST))) {

						return variableDefinitionDetailAST;
					}
				}
			}
			else if ((previousDetailAST.getType() ==
						TokenTypes.FOR_EACH_CLAUSE) ||
					 (previousDetailAST.getType() == TokenTypes.FOR_INIT)) {

				List<DetailAST> variableDefinitionDetailASTList =
					getAllChildTokens(
						previousDetailAST, false, TokenTypes.VARIABLE_DEF);

				for (DetailAST variableDefinitionDetailAST :
						variableDefinitionDetailASTList) {

					if (variableName.equals(
							_getVariableName(variableDefinitionDetailAST))) {

						return variableDefinitionDetailAST;
					}
				}
			}
			else if ((previousDetailAST.getType() ==
						TokenTypes.LITERAL_CATCH) ||
					 (previousDetailAST.getType() == TokenTypes.PARAMETERS)) {

				List<DetailAST> parameterDefinitionDetailASTList =
					getAllChildTokens(
						previousDetailAST, false, TokenTypes.PARAMETER_DEF);

				for (DetailAST parameterDefinitionDetailAST :
						parameterDefinitionDetailASTList) {

					if (variableName.equals(
							_getVariableName(parameterDefinitionDetailAST))) {

						return parameterDefinitionDetailAST;
					}
				}
			}
			else if (previousDetailAST.getType() ==
						TokenTypes.RESOURCE_SPECIFICATION) {

				DetailAST recourcesDetailAST = previousDetailAST.findFirstToken(
					TokenTypes.RESOURCES);

				List<DetailAST> resourceDetailASTList = getAllChildTokens(
					recourcesDetailAST, false, TokenTypes.RESOURCE);

				for (DetailAST resourceDetailAST : resourceDetailASTList) {
					if (variableName.equals(
							_getVariableName(resourceDetailAST))) {

						return resourceDetailAST;
					}
				}
			}
			else if ((previousDetailAST.getType() == TokenTypes.VARIABLE_DEF) &&
					 variableName.equals(_getVariableName(previousDetailAST))) {

				DetailAST parentDetailAST = previousDetailAST.getParent();

				if (includeGlobalVariables ||
					(parentDetailAST.getType() != TokenTypes.OBJBLOCK)) {

					return previousDetailAST;
				}
			}

			DetailAST previousSiblingDetailAST =
				previousDetailAST.getPreviousSibling();

			if (previousSiblingDetailAST != null) {
				previousDetailAST = previousSiblingDetailAST;

				continue;
			}

			DetailAST parentDetailAST = previousDetailAST.getParent();

			if (parentDetailAST != null) {
				previousDetailAST = parentDetailAST;

				continue;
			}

			break;
		}

		return null;
	}

	protected String getVariableName(DetailAST methodCallDetailAST) {
		DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.DOT);

		if (dotDetailAST == null) {
			return null;
		}

		DetailAST nameDetailAST = dotDetailAST.findFirstToken(TokenTypes.IDENT);

		if (nameDetailAST == null) {
			return null;
		}

		return nameDetailAST.getText();
	}

	protected DetailAST getVariableTypeDetailAST(
		DetailAST detailAST, String variableName) {

		return getVariableTypeDetailAST(detailAST, variableName, true);
	}

	protected DetailAST getVariableTypeDetailAST(
		DetailAST detailAST, String variableName,
		boolean includeGlobalVariables) {

		DetailAST variableDefinitionDetailAST = getVariableDefinitionDetailAST(
			detailAST, variableName, includeGlobalVariables);

		if (variableDefinitionDetailAST != null) {
			return variableDefinitionDetailAST.findFirstToken(TokenTypes.TYPE);
		}

		return null;
	}

	protected String getVariableTypeName(
		DetailAST detailAST, String variableName,
		boolean includeTypeArguments) {

		return getTypeName(
			getVariableTypeDetailAST(detailAST, variableName),
			includeTypeArguments);
	}

	protected boolean hasParentWithTokenType(
		DetailAST detailAST, int... tokenTypes) {

		DetailAST parentDetailAST = getParentWithTokenType(
			detailAST, tokenTypes);

		if (parentDetailAST != null) {
			return true;
		}

		return false;
	}

	protected boolean hasPrecedingPlaceholder(DetailAST detailAST) {
		CommonHiddenStreamToken commonHiddenStreamToken = getHiddenBefore(
			detailAST);

		if (commonHiddenStreamToken == null) {
			return false;
		}

		String text = commonHiddenStreamToken.getText();

		return text.contains("PLACEHOLDER");
	}

	protected boolean isArray(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.TYPE) {
			return false;
		}

		DetailAST arrayDeclaratorDetailAST = detailAST.findFirstToken(
			TokenTypes.ARRAY_DECLARATOR);

		if (arrayDeclaratorDetailAST != null) {
			return true;
		}

		return false;
	}

	protected boolean isAssignNewArrayList(DetailAST detailAST) {
		DetailAST assignDetailAST = detailAST.findFirstToken(TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return false;
		}

		DetailAST firstChildDetailAST = null;

		if (detailAST.getType() == TokenTypes.EXPR) {
			firstChildDetailAST = assignDetailAST.findFirstToken(
				TokenTypes.LITERAL_NEW);

			if (firstChildDetailAST == null) {
				return false;
			}
		}
		else {
			firstChildDetailAST = assignDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
				return false;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();
		}

		if (firstChildDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return false;
		}

		DetailAST identDetailAST = firstChildDetailAST.getFirstChild();

		if ((identDetailAST.getType() != TokenTypes.IDENT) ||
			!Objects.equals(identDetailAST.getText(), "ArrayList")) {

			return false;
		}

		DetailAST elistDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.ELIST);

		if (elistDetailAST == null) {
			return false;
		}

		firstChildDetailAST = elistDetailAST.getFirstChild();

		if (firstChildDetailAST == null) {
			return true;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.NUM_INT) {
			return true;
		}

		return false;
	}

	protected boolean isAtLineEnd(DetailAST detailAST, String line) {
		String text = detailAST.getText();

		if (line.endsWith(text) &&
			((detailAST.getColumnNo() + text.length()) == line.length())) {

			return true;
		}

		return false;
	}

	protected boolean isAtLineStart(DetailAST detailAST, String line) {
		for (int i = 0; i < detailAST.getColumnNo(); i++) {
			char c = line.charAt(i);

			if ((c != CharPool.SPACE) && (c != CharPool.TAB)) {
				return false;
			}
		}

		return true;
	}

	protected boolean isAttributeValue(String attributeKey) {
		return GetterUtil.getBoolean(getAttributeValue(attributeKey));
	}

	protected boolean isAttributeValue(
		String attributeKey, boolean defaultValue) {

		String attributeValue = getAttributeValue(attributeKey);

		if (Validator.isNull(attributeValue)) {
			return defaultValue;
		}

		return GetterUtil.getBoolean(attributeValue);
	}

	protected boolean isCollection(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.TYPE) {
			return false;
		}

		DetailAST typeArgumentsDetailAST = detailAST.findFirstToken(
			TokenTypes.TYPE_ARGUMENTS);

		if (typeArgumentsDetailAST == null) {
			return false;
		}

		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameDetailAST.getText();

		if (name.matches(".*(Collection|List|Map|Set)")) {
			return true;
		}

		return false;
	}

	protected boolean isExcludedPath(String key) {
		return SourceFormatterCheckUtil.isExcludedPath(
			_excludesJSONObject, _excludesValuesMap, key, getAbsolutePath(), -1,
			null, getBaseDirName());
	}

	protected boolean isJSPFile() {
		FileContents fileContents = getFileContents();

		if (StringUtil.endsWith(fileContents.getFileName(), ".jsp") ||
			StringUtil.endsWith(fileContents.getFileName(), ".jspf")) {

			return true;
		}

		return false;
	}

	protected boolean isMethodNameDetailAST(DetailAST identDetailAST) {
		DetailAST parentDetailAST = identDetailAST.getParent();

		if (parentDetailAST.getType() == TokenTypes.METHOD_CALL) {
			return true;
		}

		if (parentDetailAST.getType() != TokenTypes.DOT) {
			return false;
		}

		parentDetailAST = parentDetailAST.getParent();

		if ((parentDetailAST.getType() == TokenTypes.METHOD_CALL) &&
			(identDetailAST.getNextSibling() == null)) {

			return true;
		}

		return false;
	}

	protected static final int ALL_TYPES = DetailASTUtil.ALL_TYPES;

	protected static final int[] ARITHMETIC_OPERATOR_TOKEN_TYPES = {
		TokenTypes.DIV, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.PLUS,
		TokenTypes.STAR
	};

	protected static final int[] ASSIGNMENT_OPERATOR_TOKEN_TYPES = {
		TokenTypes.ASSIGN, TokenTypes.BAND_ASSIGN, TokenTypes.BOR_ASSIGN,
		TokenTypes.BSR_ASSIGN, TokenTypes.BXOR_ASSIGN, TokenTypes.DIV_ASSIGN,
		TokenTypes.MINUS_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.PLUS_ASSIGN,
		TokenTypes.SL_ASSIGN, TokenTypes.SR_ASSIGN, TokenTypes.STAR_ASSIGN
	};

	protected static final int[] CONDITIONAL_OPERATOR_TOKEN_TYPES = {
		TokenTypes.BAND, TokenTypes.BOR, TokenTypes.BXOR, TokenTypes.LAND,
		TokenTypes.LOR
	};

	protected static final int[] RELATIONAL_OPERATOR_TOKEN_TYPES = {
		TokenTypes.EQUAL, TokenTypes.GE, TokenTypes.GT, TokenTypes.LE,
		TokenTypes.LT, TokenTypes.NOT_EQUAL
	};

	protected static final String RUN_OUTSIDE_PORTAL_EXCLUDES =
		"run.outside.portal.excludes";

	protected static final int[] UNARY_OPERATOR_TOKEN_TYPES = {
		TokenTypes.DEC, TokenTypes.INC, TokenTypes.LNOT, TokenTypes.POST_DEC,
		TokenTypes.POST_INC, TokenTypes.UNARY_MINUS, TokenTypes.UNARY_PLUS
	};

	private List<DetailAST> _addDependentIdentDetailASTList(
		List<DetailAST> dependentIdentDetailASTList, DetailAST detailAST,
		List<Variable> variables, int lineNumber, boolean includeGetters) {

		if (detailAST == null) {
			return dependentIdentDetailASTList;
		}

		int count = dependentIdentDetailASTList.size();

		List<DetailAST> identDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST identDetailAST : identDetailASTList) {
			if (isMethodNameDetailAST(identDetailAST) ||
				dependentIdentDetailASTList.contains(identDetailAST)) {

				continue;
			}

			String name = identDetailAST.getText();

			for (Variable variable : variables) {
				if (!name.equals(variable.getName())) {
					continue;
				}

				if (_hasPossibleValueChangeOperation(identDetailAST, false) ||
					variable.hasPossibleValueChangeOperation()) {

					dependentIdentDetailASTList.add(identDetailAST);

					break;
				}
			}

			if ((detailAST.getLineNo() < lineNumber) &&
				(count != dependentIdentDetailASTList.size())) {

				if (getEndLineNumber(detailAST) < lineNumber) {
					variables.addAll(_getVariables(detailAST, false));
				}
				else {
					DetailAST elistDetailAST = getParentWithTokenType(
						identDetailAST, TokenTypes.ELIST);

					if (elistDetailAST == null) {
						variables.addAll(_getVariables(detailAST, false));
					}
					else {
						while (true) {
							if (elistDetailAST == null) {
								break;
							}

							DetailAST parentDetailAST =
								elistDetailAST.getParent();

							if (parentDetailAST.getLineNo() >= lineNumber) {
								variables.addAll(
									_getVariables(elistDetailAST, false));
							}
							else {
								break;
							}

							elistDetailAST = getParentWithTokenType(
								elistDetailAST, TokenTypes.ELIST);
						}
					}
				}
			}
		}

		return _addDependentIdentDetailASTList(
			dependentIdentDetailASTList, detailAST.getNextSibling(), variables,
			lineNumber, includeGetters);
	}

	private List<String> _getJSPImportNames(String directoryName) {
		if (_jspImportNamesMap.containsKey(directoryName)) {
			return _jspImportNamesMap.get(directoryName);
		}

		List<String> importNames = new ArrayList<>();

		String fileName = directoryName + "/init.jsp";

		File file = new File(fileName);

		if (file.exists()) {
			try {
				List<String> curImportNames =
					JSPImportsFormatter.getImportNames(FileUtil.read(file));

				importNames.addAll(curImportNames);
			}
			catch (IOException ioException) {
				if (_log.isDebugEnabled()) {
					_log.debug(ioException, ioException);
				}
			}
		}

		int x = directoryName.lastIndexOf(CharPool.SLASH);

		if ((x != -1) && !directoryName.endsWith("/resources") &&
			!directoryName.endsWith("/docroot")) {

			importNames.addAll(
				_getJSPImportNames(directoryName.substring(0, x)));
		}

		_jspImportNamesMap.put(directoryName, importNames);

		return importNames;
	}

	private String _getVariableName(DetailAST variableDefinitionDetailAST) {
		DetailAST nameDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		return nameDetailAST.getText();
	}

	private List<Variable> _getVariables(
		DetailAST detailAST, boolean includeGetters) {

		List<Variable> variables = new ArrayList<>();

		List<DetailAST> identDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST identDetailAST : identDetailASTList) {
			if (isMethodNameDetailAST(identDetailAST)) {
				continue;
			}

			String name = identDetailAST.getText();

			if (!Character.isUpperCase(name.charAt(0))) {
				variables.add(
					new Variable(
						name,
						_hasPossibleValueChangeOperation(
							identDetailAST, includeGetters)));
			}
		}

		return variables;
	}

	private boolean _hasPossibleValueChangeOperation(
		DetailAST identDetailAST, boolean includeGetters) {

		DetailAST parentDetailAST = identDetailAST.getParent();

		if (parentDetailAST.getType() == TokenTypes.DOT) {
			DetailAST grandParentDetailAST = parentDetailAST.getParent();

			if (grandParentDetailAST.getType() == TokenTypes.METHOD_CALL) {
				DetailAST nextSiblingDetailAST =
					identDetailAST.getNextSibling();

				if (nextSiblingDetailAST == null) {
					return false;
				}

				if (includeGetters) {
					return true;
				}

				String methodName = nextSiblingDetailAST.getText();

				if (methodName.matches("(get|is)[A-Z].*")) {
					return false;
				}

				return true;
			}
		}

		while (true) {
			if ((parentDetailAST.getType() != TokenTypes.DOT) &&
				(parentDetailAST.getType() != TokenTypes.EXPR)) {

				break;
			}

			parentDetailAST = parentDetailAST.getParent();
		}

		if (parentDetailAST.getType() == TokenTypes.ELIST) {
			String typeName = getVariableTypeName(
				identDetailAST, identDetailAST.getText(), false);

			if (typeName.equals("ActionRequest") ||
				typeName.equals("HttpServletRequest") ||
				typeName.equals("RenderRequest") ||
				typeName.equals("ResourceRequest") ||
				typeName.equals("ThemeDisplay") ||
				typeName.endsWith("PortletRequest")) {

				String methodName = getMethodName(parentDetailAST.getParent());

				// We can assume variable of these types are not modified when
				// passed as parameter, except when the name starts with 'set'
				// or equals 'getCompanyId' (value changes in
				// PortalInstances.getCompanyId)

				if ((methodName != null) &&
					(methodName.equals("getCompanyId") ||
					 methodName.startsWith("_set") ||
					 methodName.startsWith("set"))) {

					return true;
				}

				return false;
			}

			return true;
		}

		if (ArrayUtil.contains(
				ASSIGNMENT_OPERATOR_TOKEN_TYPES, parentDetailAST.getType()) ||
			(parentDetailAST.getType() == TokenTypes.DEC) ||
			(parentDetailAST.getType() == TokenTypes.ELIST) ||
			(parentDetailAST.getType() == TokenTypes.INC) ||
			(parentDetailAST.getType() == TokenTypes.POST_DEC) ||
			(parentDetailAST.getType() == TokenTypes.POST_INC) ||
			(parentDetailAST.getType() == TokenTypes.VARIABLE_DEF)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseCheck.class);

	private JSONObject _attributesJSONObject = new JSONObjectImpl();
	private final Map<String, String> _attributeValueMap =
		new ConcurrentHashMap<>();
	private final Map<String, List<String>> _attributeValuesMap =
		new ConcurrentHashMap<>();
	private JSONObject _excludesJSONObject = new JSONObjectImpl();
	private final Map<String, List<String>> _excludesValuesMap =
		new ConcurrentHashMap<>();
	private final Map<String, List<String>> _jspImportNamesMap =
		new HashMap<>();

	private class Variable {

		public Variable(String name, boolean hasPossibleValueChangeOperation) {
			_name = name;
			_hasPossibleValueChangeOperation = hasPossibleValueChangeOperation;
		}

		public String getName() {
			return _name;
		}

		public boolean hasPossibleValueChangeOperation() {
			return _hasPossibleValueChangeOperation;
		}

		private final boolean _hasPossibleValueChangeOperation;
		private final String _name;

	}

}
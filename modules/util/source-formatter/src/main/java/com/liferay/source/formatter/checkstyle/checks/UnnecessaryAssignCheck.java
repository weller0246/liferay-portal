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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class UnnecessaryAssignCheck extends BaseUnnecessaryStatementCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.ASSIGN};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		DetailAST slistDetailAST = parentDetailAST.getParent();

		if (slistDetailAST.getType() != TokenTypes.SLIST) {
			return;
		}

		DetailAST semiDetailAST = parentDetailAST.getNextSibling();

		if ((semiDetailAST == null) ||
			(semiDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return;
		}

		String variableName = firstChildDetailAST.getText();

		DetailAST variableTypeDetailAST = getVariableTypeDetailAST(
			detailAST, variableName, false);

		if (variableTypeDetailAST == null) {
			return;
		}

		if (!_hasPrecedingAssignStatement(parentDetailAST, variableName) &&
			!_isUsedInFinallyStatement(detailAST, variableName)) {

			if (!isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES)) {
				checkUnnecessaryListVariableBeforeReturn(
					detailAST, semiDetailAST, variableName,
					_MSG_UNNECESSARY_LIST_ASSIGN_BEFORE_RETURN);
			}

			checkUnnecessaryStatementBeforeReturn(
				detailAST, semiDetailAST, variableName,
				_MSG_UNNECESSARY_ASSIGN_BEFORE_RETURN);
		}

		DetailAST variableDefinitionDetailAST =
			variableTypeDetailAST.getParent();

		List<DetailAST> variableCallerDetailASTList =
			getVariableCallerDetailASTList(
				variableDefinitionDetailAST, variableName);

		if (variableCallerDetailASTList.isEmpty()) {
			return;
		}

		checkUnnecessaryToString(detailAST, _MSG_UNNECESSARY_TO_STRING);

		DetailAST firstNextVariableCallerDetailAST = null;
		DetailAST secondNextVariableCallerDetailAST = null;

		int endLineNumber = getEndLineNumber(detailAST);

		for (int i = 0; i < variableCallerDetailASTList.size(); i++) {
			DetailAST variableCallerDetailAST = variableCallerDetailASTList.get(
				i);

			if (variableCallerDetailAST.getLineNo() <= endLineNumber) {
				continue;
			}

			firstNextVariableCallerDetailAST = variableCallerDetailAST;

			if (i < (variableCallerDetailASTList.size() - 1)) {
				secondNextVariableCallerDetailAST =
					variableCallerDetailASTList.get(i + 1);
			}

			break;
		}

		if (firstNextVariableCallerDetailAST != null) {
			checkUnnecessaryStatementBeforeReassign(
				detailAST, firstNextVariableCallerDetailAST,
				secondNextVariableCallerDetailAST, slistDetailAST, variableName,
				_MSG_UNNECESSARY_ASSIGN_BEFORE_REASSIGN);

			return;
		}

		DetailAST lastChildDetailAST = detailAST.getLastChild();

		if (lastChildDetailAST.getType() == TokenTypes.LITERAL_NULL) {
			return;
		}

		parentDetailAST = variableDefinitionDetailAST.getParent();

		if ((parentDetailAST.getType() != TokenTypes.FOR_EACH_CLAUSE) &&
			(parentDetailAST.getType() != TokenTypes.SLIST)) {

			return;
		}

		if (firstNextVariableCallerDetailAST == null) {
			DetailAST ancestorDetailAST = getParentWithTokenType(
				detailAST, TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE,
				TokenTypes.OBJBLOCK);

			if (ancestorDetailAST.getLineNo() <=
					variableDefinitionDetailAST.getLineNo()) {

				log(detailAST, _MSG_UNNECESSARY_ASSIGN_UNUSED, variableName);
			}
		}
	}

	private boolean _hasPrecedingAssignStatement(
		DetailAST detailAST, String variableName) {

		DetailAST previousDetailAST = detailAST.getPreviousSibling();

		if ((previousDetailAST == null) ||
			(previousDetailAST.getType() != TokenTypes.SEMI)) {

			return false;
		}

		previousDetailAST = previousDetailAST.getPreviousSibling();

		if (previousDetailAST.getType() != TokenTypes.EXPR) {
			return false;
		}

		DetailAST firstChildDetailAST = previousDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.ASSIGN) {
			return false;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (variableName.equals(firstChildDetailAST.getText())) {
			return true;
		}

		return false;
	}

	private boolean _isUsedInFinallyStatement(
		DetailAST detailAST, String variableName) {

		DetailAST parentDetailAST = detailAST;

		while (true) {
			parentDetailAST = getParentWithTokenType(
				parentDetailAST, TokenTypes.LITERAL_TRY);

			if (parentDetailAST == null) {
				return false;
			}

			DetailAST literalFinallyDetailAST = parentDetailAST.findFirstToken(
				TokenTypes.LITERAL_FINALLY);

			if (literalFinallyDetailAST == null) {
				continue;
			}

			List<DetailAST> identDetailASTList = getAllChildTokens(
				literalFinallyDetailAST, true, TokenTypes.IDENT);

			for (DetailAST identDetailAST : identDetailASTList) {
				if (variableName.equals(identDetailAST.getText())) {
					return true;
				}
			}
		}
	}

	private static final String _MSG_UNNECESSARY_ASSIGN_BEFORE_REASSIGN =
		"assign.unnecessary.before.reassign";

	private static final String _MSG_UNNECESSARY_ASSIGN_BEFORE_RETURN =
		"assign.unnecessary.before.return";

	private static final String _MSG_UNNECESSARY_ASSIGN_UNUSED =
		"assign.unnecessary.unused";

	private static final String _MSG_UNNECESSARY_LIST_ASSIGN_BEFORE_RETURN =
		"list.assign.unnecessary.before.return";

	private static final String _MSG_UNNECESSARY_TO_STRING =
		"assign.unnecessary.to.string";

}
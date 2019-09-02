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

AUI.add(
	'liferay-staging-branch',
	function(A) {
		var Lang = A.Lang;

		var StagingBar = Liferay.StagingBar;

		var MAP_TEXT_REVISION = {
			redo: Liferay.Language.get(
				'are-you-sure-you-want-to-redo-your-last-changes'
			),
			undo: Liferay.Language.get(
				'are-you-sure-you-want-to-undo-your-last-changes'
			)
		};

		A.mix(StagingBar, {
			addBranch(dialogTitle) {
				var instance = this;

				var branchDialog = instance._getBranchDialog();

				if (Lang.isValue(dialogTitle)) {
					branchDialog.set('title', dialogTitle);
				}

				branchDialog.show();
			},

			_getBranchDialog() {
				var instance = this;

				var branchDialog = instance._branchDialog;

				if (!branchDialog) {
					var namespace = instance._namespace;

					branchDialog = Liferay.Util.Window.getWindow({
						dialog: {
							bodyContent: A.one(
								'#' + namespace + 'addBranch'
							).show()
						},
						title: Liferay.Language.get('branch')
					});

					instance._branchDialog = branchDialog;
				}

				return branchDialog;
			}
		});
	},
	'',
	{
		requires: ['liferay-staging']
	}
);

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
	'liferay-search-container',
	(A) => {
		var Lang = A.Lang;

		var CSS_TEMPLATE = 'd-none';

		var STR_BLANK = '';

		var STR_BOUNDING_BOX = 'boundingBox';

		var SearchContainer = A.Component.create({
			_cache: {},

			ATTRS: {
				id: {
					value: STR_BLANK,
				},
			},

			NAME: 'searchcontainer',

			// NOTE: Do not convert the constructor to an object concise method.
			//
			// See: https://stackoverflow.com/a/45119651/2103996

			constructor: function constructor(config) {
				var id = config.id;

				config.boundingBox = config.boundingBox || '#' + id;
				config.contentBox =
					config.contentBox || '#' + config.id + 'SearchContainer';

				SearchContainer.superclass.constructor.apply(this, arguments);
			},

			get(id) {
				var instance = this;

				var searchContainer = null;

				if (instance._cache[id]) {
					searchContainer = instance._cache[id];
				}
				else {
					searchContainer = new SearchContainer({
						id,
					}).render();
				}

				return searchContainer;
			},

			prototype: {
				_addRow() {
					var instance = this;

					instance._parentContainer.show();

					if (instance._emptyResultsMessage) {
						instance._emptyResultsMessage.hide();
					}
				},

				_deleteRow() {
					var instance = this;

					var action = 'show';

					if (instance._ids.length == 0) {
						action = 'hide';

						if (instance._emptyResultsMessage) {
							instance._emptyResultsMessage.show();
						}
					}

					instance._parentContainer[action]();
				},

				addRow(array, id, columnsCssClasses) {
					var instance = this;

					var row;

					if (id) {
						var template = instance._table.one('.' + CSS_TEMPLATE);

						if (template) {
							row = template.previous()
								? template.previous().clone()
								: template.clone();

							var cells = row.all('> td');

							cells.empty();

							array.forEach((item, index) => {
								var cell = cells.item(index);

								if (cell) {
									cell.html(item);
									if (
										columnsCssClasses &&
										columnsCssClasses[index]
									) {
										cell.addClass(columnsCssClasses[index]);
									}
								}
							});

							template.placeBefore(row);

							row.removeClass(CSS_TEMPLATE);

							row.attr('id', instance.get('id') + '_' + id);

							instance._ids.push(id);
						}

						instance.updateDataStore();

						instance.fire('addRow', {
							id,
							ids: instance._ids,
							row,
							rowData: array,
						});
					}

					return row;
				},

				bindUI() {
					var instance = this;

					instance.publish('addRow', {
						defaultFn: instance._addRow,
					});

					instance.publish('deleteRow', {
						defaultFn: instance._deleteRow,
					});
				},

				deleteRow(object, id) {
					var instance = this;

					if (Lang.isNumber(object) || Lang.isString(object)) {
						var row = null;

						instance._table.all('tr').some((item, index) => {
							if (
								!item.hasClass(CSS_TEMPLATE) &&
								index == object
							) {
								row = item;
							}

							return row;
						});

						object = row;
					}
					else {
						object = A.one(object);
					}

					if (id) {
						var index = instance._ids.indexOf(id.toString());

						if (index > -1) {
							instance._ids.splice(index, 1);

							instance.updateDataStore();
						}
					}

					instance.fire('deleteRow', {
						id,
						ids: instance._ids,
						row: object,
					});

					if (object) {
						if (object.get('nodeName').toLowerCase() !== 'tr') {
							object = object.ancestor('tr');
						}

						// LPS-83031

						setTimeout(() => {
							object.remove(true);
						}, 0);
					}
				},

				executeAction(name, params) {
					var instance = this;

					if (instance._actions[name]) {
						instance._actions[name](params);
					}
				},

				getData(toArray) {
					var instance = this;

					var ids = instance._ids;

					if (!toArray) {
						ids = ids.join(',');
					}

					return ids;
				},

				getForm() {
					var instance = this;

					return instance.get(STR_BOUNDING_BOX).ancestor('form');
				},

				getSize() {
					var instance = this;

					return instance._ids.length;
				},

				initializer() {
					var instance = this;

					instance._ids = [];

					instance._actions = {};

					SearchContainer.register(instance);
				},

				registerAction(name, fn) {
					var instance = this;

					instance._actions[name] = fn;
				},

				renderUI() {
					var instance = this;

					var id = instance.get('id');

					var boundingBox = instance.get(STR_BOUNDING_BOX);

					instance._dataStore = A.one('#' + id + 'PrimaryKeys');
					instance._emptyResultsMessage = A.one(
						'#' + id + 'EmptyResultsMessage'
					);

					if (instance._dataStore) {
						var dataStoreForm = instance._dataStore.attr('form');

						if (dataStoreForm) {
							var method = dataStoreForm
								.attr('method')
								.toLowerCase();

							if (method && method == 'get') {
								instance._dataStore = null;
							}
						}
					}

					instance._table = boundingBox.one('table');
					instance._parentContainer = boundingBox.ancestor(
						'.lfr-search-container-wrapper'
					);

					if (instance._table) {
						instance._table.setAttribute(
							'data-searchContainerId',
							id
						);
					}
				},

				syncUI() {
					var instance = this;

					var dataStore = instance._dataStore;

					var initialIds = dataStore && dataStore.val();

					if (initialIds) {
						initialIds = initialIds.split(',');

						instance.updateDataStore(initialIds);
					}
				},

				updateDataStore(ids) {
					var instance = this;

					if (ids) {
						if (typeof ids == 'string') {
							ids = ids.split(',');
						}

						instance._ids = ids;
					}

					var dataStore = instance._dataStore;

					if (dataStore) {
						dataStore.val(instance._ids.join(','));
					}
				},
			},

			register(object) {
				var instance = this;

				var id = object.get('id');

				instance._cache[id] = object;

				Liferay.component(id, object, {
					destroyOnNavigate: true,
				});

				Liferay.fire('search-container:registered', {
					searchContainer: object,
				});
			},
		});

		Liferay.SearchContainer = SearchContainer;
	},
	'',
	{
		requires: ['aui-base', 'aui-component'],
	}
);

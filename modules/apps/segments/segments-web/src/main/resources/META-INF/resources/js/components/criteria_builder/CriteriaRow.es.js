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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import {fetch} from 'frontend-js-web';
import {PropTypes} from 'prop-types';
import React, {Component} from 'react';
import {DragSource as dragSource, DropTarget as dropTarget} from 'react-dnd';

import ThemeContext from '../../ThemeContext.es';
import {PROPERTY_TYPES} from '../../utils/constants.es';
import {DragTypes} from '../../utils/drag-types.es';
import {
	createNewGroup,
	dateToInternationalHuman,
	getSupportedOperatorsFromType,
	objectToFormData,
} from '../../utils/utils.es';
import BooleanInput from '../inputs/BooleanInput.es';
import CollectionInput from '../inputs/CollectionInput.es';
import DateInput from '../inputs/DateInput.es';
import DateTimeInput from '../inputs/DateTimeInput.es';
import DecimalInput from '../inputs/DecimalInput.es';
import IntegerInput from '../inputs/IntegerInput.es';
import SelectEntityInput from '../inputs/SelectEntityInput.es';
import StringInput from '../inputs/StringInput.es';

const acceptedDragTypes = [DragTypes.CRITERIA_ROW, DragTypes.PROPERTY];

const DISPLAY_VALUE_NOT_FOUND_ERROR = 'displayValue not found';

/**
 * Prevents rows from dropping onto itself and adding properties to not matching
 * contributors.
 * This method must be called `canDrop`.
 * @param {Object} props Component's current props.
 * @param {DropTargetMonitor} monitor
 * @returns {boolean} True if the target should accept the item.
 */
function canDrop(props, monitor) {
	const {
		groupId: destGroupId,
		index: destIndex,
		propertyKey: contributorPropertyKey,
	} = props;

	const {
		groupId: startGroupId,
		index: startIndex,
		propertyKey: sidebarItemPropertyKey,
	} = monitor.getItem();

	return (
		(destGroupId !== startGroupId || destIndex !== startIndex) &&
		contributorPropertyKey === sidebarItemPropertyKey
	);
}

/**
 * Implements the behavior of what will occur when an item is dropped.
 * Items dropped on top of rows will create a new grouping.
 * This method must be called `drop`.
 * @param {Object} props Component's current props.
 * @param {DropTargetMonitor} monitor
 */
function drop(props, monitor) {
	const {
		criterion,
		groupId: destGroupId,
		index: destIndex,
		onChange,
		onMove,
		supportedOperators,
		supportedPropertyTypes,
	} = props;

	const {
		criterion: droppedCriterion,
		groupId: startGroupId,
		index: startIndex,
	} = monitor.getItem();

	const {
		defaultValue,
		displayValue,
		operatorName,
		propertyName,
		type,
		value,
	} = droppedCriterion;

	const droppedCriterionValue = value || defaultValue;

	const operators = getSupportedOperatorsFromType(
		supportedOperators,
		supportedPropertyTypes,
		type
	);

	const newCriterion = {
		displayValue,
		operatorName: operatorName ? operatorName : operators[0].name,
		propertyName,
		value: droppedCriterionValue,
	};

	const itemType = monitor.getItemType();

	const newGroup = createNewGroup([criterion, newCriterion]);

	if (itemType === DragTypes.PROPERTY) {
		onChange(newGroup);
	}
	else if (itemType === DragTypes.CRITERIA_ROW) {
		onMove(
			startGroupId,
			startIndex,
			destGroupId,
			destIndex,
			newGroup,
			true
		);
	}
}

/**
 * Passes the required values to the drop target.
 * This method must be called `beginDrag`.
 * @param {Object} props Component's current props
 * @returns {Object} The props to be passed to the drop target.
 */
function beginDrag({criterion, groupId, index, propertyKey}) {
	return {criterion, groupId, index, propertyKey};
}

class CriteriaRow extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		canDrop: PropTypes.bool,
		connectDragPreview: PropTypes.func,
		connectDragSource: PropTypes.func,
		connectDropTarget: PropTypes.func,
		criterion: PropTypes.object,
		dragging: PropTypes.bool,
		editing: PropTypes.bool,
		entityName: PropTypes.string,
		groupId: PropTypes.string.isRequired,
		hover: PropTypes.bool,
		index: PropTypes.number.isRequired,
		modelLabel: PropTypes.string,
		onAdd: PropTypes.func.isRequired,
		onChange: PropTypes.func.isRequired,
		onDelete: PropTypes.func.isRequired,
		onMove: PropTypes.func.isRequired,
		propertyKey: PropTypes.string.isRequired,
		renderEmptyValuesErrors: PropTypes.bool,
		supportedOperators: PropTypes.array,
		supportedProperties: PropTypes.array,
		supportedPropertyTypes: PropTypes.object,
	};

	static defaultProps = {
		criterion: {},
		editing: true,
		renderEmptyValuesErrors: false,
		supportedOperators: [],
		supportedProperties: [],
		supportedPropertyTypes: {},
	};

	componentDidMount() {
		const {
			criterion: {displayValue, propertyName, value},
			supportedProperties,
		} = this.props;

		this._selectedProperty = this._getSelectedItem(
			supportedProperties,
			propertyName
		);

		if (
			this._selectedProperty.type === PROPERTY_TYPES.ID &&
			value &&
			!displayValue
		) {
			this._fetchEntityName();
		}
	}

	_fetchEntityName = () => {
		const {criterion, entityName, onChange} = this.props;

		const {propertyName, value} = criterion;

		const data = Liferay.Util.ns(this.context.namespace, {
			entityName,
			fieldName: propertyName,
			fieldValue: value,
		});

		fetch(this.context.requestFieldValueNameURL, {
			body: objectToFormData(data),
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({fieldValueName: displayValue}) => {
				if (displayValue === undefined) {
					throw new Error(DISPLAY_VALUE_NOT_FOUND_ERROR);
				}

				onChange({...criterion, displayValue, unknownEntity: false});
			})
			.catch((error) => {
				if (error && error.message === DISPLAY_VALUE_NOT_FOUND_ERROR) {
					onChange({
						...criterion,
						displayValue: value,
						unknownEntity: true,
					});
				}
				else {
					onChange({...criterion, displayValue: value});
				}
			});
	};

	_getReadableCriteriaString = ({
		operatorLabel,
		propertyLabel,
		type,
		value,
	}) => {
		const parsedValue =
			type === PROPERTY_TYPES.DATE || type === PROPERTY_TYPES.DATE_TIME
				? dateToInternationalHuman(value)
				: value;

		return (
			<span>
				<b className="mr-1 text-dark">{propertyLabel}</b>
				<span className="mr-1 operator">{operatorLabel}</span>
				<b>{parsedValue}</b>
			</span>
		);
	};

	/**
	 * Gets the selected item object with a `name` and `label` property for a
	 * selection input. If one isn't found, a new object is returned using the
	 * idSelected for name and label.
	 * @param {Array} list The list of objects to search through.
	 * @param {string} idSelected The name to match in each object in the list.
	 * @return {object} An object with a `name`, `label` and `type` property.
	 */
	_getSelectedItem = (list, idSelected) => {
		const selectedItem = list.find((item) => item.name === idSelected);

		return selectedItem
			? selectedItem
			: {
					label: idSelected,
					name: idSelected,
					notFound: true,
					type: PROPERTY_TYPES.STRING,
			  };
	};

	_handleDelete = (event) => {
		event.preventDefault();

		const {index, onDelete} = this.props;

		onDelete(index);
	};

	_handleDuplicate = (event) => {
		event.preventDefault();

		const {criterion, index, onAdd} = this.props;

		onAdd(index + 1, criterion);
	};

	_handleInputChange = (propertyName) => (event) => {
		const {criterion, onChange} = this.props;

		onChange({
			...criterion,
			[propertyName]: event.target.value,
		});
	};

	/**
	 * Updates the criteria with a criterion value change. The param 'value'
	 * will only be an array when selecting multiple entities (see
	 * {@link SelectEntityInput.es.js}). And in the case of an array, a new
	 * group with multiple criterion rows will be created.
	 * @param {Array|object} value The properties or list of objects with
	 * properties to update.
	 */
	_handleTypedInputChange = (value) => {
		const {criterion, onChange} = this.props;

		if (Array.isArray(value)) {
			const items = value.map((item) => ({
				...criterion,
				...item,
			}));

			onChange(createNewGroup(items));
		}
		else {
			onChange({
				...criterion,
				...value,
			});
		}
	};

	_renderValueInput = (
		disabled,
		renderEmptyValuesErrors,
		selectedProperty,
		value
	) => {
		const inputComponentsMap = {
			[PROPERTY_TYPES.BOOLEAN]: BooleanInput,
			[PROPERTY_TYPES.COLLECTION]: CollectionInput,
			[PROPERTY_TYPES.DATE]: DateInput,
			[PROPERTY_TYPES.DATE_TIME]: DateTimeInput,
			[PROPERTY_TYPES.DOUBLE]: DecimalInput,
			[PROPERTY_TYPES.ID]: SelectEntityInput,
			[PROPERTY_TYPES.INTEGER]: IntegerInput,
			[PROPERTY_TYPES.STRING]: StringInput,
		};

		const InputComponent =
			inputComponentsMap[selectedProperty.type] ||
			inputComponentsMap[PROPERTY_TYPES.STRING];

		return (
			<InputComponent
				disabled={disabled}
				displayValue={this.props.criterion.displayValue || ''}
				onChange={this._handleTypedInputChange}
				options={selectedProperty.options}
				renderEmptyValueErrors={renderEmptyValuesErrors}
				selectEntity={selectedProperty.selectEntity}
				value={value}
			/>
		);
	};

	_renderErrorMessages({errorOnProperty, unknownEntityError}) {
		const {editing} = this.props;
		const errors = [];
		if (errorOnProperty) {
			const message = editing
				? Liferay.Language.get('criteria-error-message-edit')
				: Liferay.Language.get('criteria-error-message-view');

			errors.push({
				message,
			});
		}

		if (unknownEntityError) {
			const message = editing
				? Liferay.Language.get('unknown-element-message-edit')
				: Liferay.Language.get('unknown-element-message-view');

			errors.push({
				message,
			});
		}

		return errors.map((error, index) => {
			return (
				<ClayAlert
					className="bg-transparent border-0 mt-1 p-1"
					displayType="danger"
					key={index}
					title={Liferay.Language.get('error')}
				>
					{error.message}
				</ClayAlert>
			);
		});
	}

	_renderWarningMessages() {
		const {editing} = this.props;
		const warnings = [];
		const message = editing
			? Liferay.Language.get('criteria-warning-message-edit')
			: Liferay.Language.get('criteria-warning-message-view');

		warnings.push({
			message,
		});

		return warnings.map((warning, index) => {
			return (
				<ClayAlert
					className="bg-transparent border-0 mt-1 p-1"
					displayType="warning"
					key={index}
					title={Liferay.Language.get('warning')}
				>
					{warning.message}
				</ClayAlert>
			);
		});
	}

	_renderEditContainer({
		error,
		propertyLabel,
		selectedOperator,
		selectedProperty,
		value,
	}) {
		const {
			connectDragSource,
			renderEmptyValuesErrors,
			supportedOperators,
			supportedPropertyTypes,
		} = this.props;

		const propertyType = selectedProperty ? selectedProperty.type : '';

		const filteredSupportedOperators = getSupportedOperatorsFromType(
			supportedOperators,
			supportedPropertyTypes,
			propertyType
		);

		const disabledInput = !!error;

		return (
			<div className="edit-container">
				{connectDragSource(
					<div className="drag-icon">
						<ClayIcon symbol="drag" />
					</div>
				)}

				<span className="criterion-string">
					<b>{propertyLabel}</b>
				</span>

				<ClaySelectWithOption
					className="criterion-input form-control operator-input"
					disabled={disabledInput}
					onChange={this._handleInputChange('operatorName')}
					options={filteredSupportedOperators.map(
						({label, name}) => ({
							label,
							value: name,
						})
					)}
					value={selectedOperator && selectedOperator.name}
				/>

				{this._renderValueInput(
					disabledInput,
					renderEmptyValuesErrors,
					selectedProperty,
					value
				)}

				{error ? (
					<ClayButton
						className="btn-outline-danger btn-sm"
						displayType=""
						onClick={this._handleDelete}
					>
						{Liferay.Language.get('delete')}
					</ClayButton>
				) : (
					<>
						<ClayButton
							className="btn-outline-borderless btn-sm mr-1"
							displayType="secondary"
							monospaced
							onClick={this._handleDuplicate}
						>
							<ClayIcon symbol="paste" />
						</ClayButton>

						<ClayButton
							className="btn-outline-borderless btn-sm"
							displayType="secondary"
							monospaced
							onClick={this._handleDelete}
						>
							<ClayIcon symbol="times-circle" />
						</ClayButton>
					</>
				)}
			</div>
		);
	}

	render() {
		const {
			canDrop,
			connectDragPreview,
			connectDropTarget,
			criterion,
			dragging,
			editing,
			hover,
			renderEmptyValuesErrors,
			supportedOperators,
			supportedProperties,
		} = this.props;

		const {unknownEntity} = criterion;

		const selectedOperator = this._getSelectedItem(
			supportedOperators,
			criterion.operatorName
		);

		const selectedProperty = this._getSelectedItem(
			supportedProperties,
			criterion.propertyName
		);

		const value = criterion ? criterion.value : '';
		const errorOnProperty = selectedProperty.notFound;
		const error = errorOnProperty || unknownEntity;
		const warningOnProperty =
			selectedProperty.options === undefined
				? false
				: selectedProperty.options.length === 0
				? false
				: selectedProperty.options.find((option) => {
						return (
							option.value === value &&
							option.disabled === undefined
						);
				  });
		const warning =
			warningOnProperty || warningOnProperty === false ? false : true;

		if (
			selectedProperty.options !== undefined &&
			selectedProperty.options.length > 0 &&
			selectedProperty.options.find((option) => {
				return option.value === value;
			}) === undefined &&
			warning
		) {
			selectedProperty.options.unshift({
				disabled: true,
				label: value,
				value,
			});
		}

		const operatorLabel = selectedOperator ? selectedOperator.label : '';
		const propertyLabel = selectedProperty ? selectedProperty.label : '';

		const classes = getCN('criterion-row-root', {
			'criterion-row-root-error': error,
			'criterion-row-root-warning': warning,
			'dnd-drag': dragging,
			'dnd-hover': hover && canDrop,
		});

		return (
			<>
				{connectDropTarget(
					connectDragPreview(
						<div className={classes}>
							{editing ? (
								this._renderEditContainer({
									error,
									propertyLabel,
									renderEmptyValuesErrors,
									selectedOperator,
									selectedProperty,
									value,
								})
							) : (
								<span className="criterion-string">
									{this._getReadableCriteriaString({
										error,
										operatorLabel,
										propertyLabel,
										type: selectedProperty.type,
										value: criterion.displayValue || value,
									})}
								</span>
							)}
						</div>
					)
				)}
				{error &&
					this._renderErrorMessages({
						errorOnProperty,
						unknownEntityError: unknownEntity,
					})}
				{warning && this._renderWarningMessages()}
				{!value && renderEmptyValuesErrors && (
					<ClayAlert
						className="pr-6 text-right"
						displayType="danger"
						title={Liferay.Language.get(
							'a-value-needs-to-be-added-or-selected-in-the-blank-field'
						)}
						variant="feedback"
					/>
				)}
			</>
		);
	}
}

const CriteriaRowWithDrag = dragSource(
	DragTypes.CRITERIA_ROW,
	{
		beginDrag,
	},
	(connect, monitor) => ({
		connectDragPreview: connect.dragPreview(),
		connectDragSource: connect.dragSource(),
		dragging: monitor.isDragging(),
	})
)(CriteriaRow);

export default dropTarget(
	acceptedDragTypes,
	{
		canDrop,
		drop,
	},
	(connect, monitor) => ({
		canDrop: monitor.canDrop(),
		connectDropTarget: connect.dropTarget(),
		hover: monitor.isOver(),
	})
)(CriteriaRowWithDrag);

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

package com.liferay.portal.workflow.kaleo.definition.exception;

import com.liferay.portal.kernel.workflow.WorkflowException;

/**
 * @author Inácio Nery
 */
public class KaleoDefinitionValidationException extends WorkflowException {

	public KaleoDefinitionValidationException() {
	}

	public KaleoDefinitionValidationException(String msg) {
		super(msg);
	}

	public KaleoDefinitionValidationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public KaleoDefinitionValidationException(Throwable throwable) {
		super(throwable);
	}

	public static class DuplicateNode
		extends KaleoDefinitionValidationException {

		public DuplicateNode(String node) {
			super(String.format("Duplicate node %s", node));

			_node = node;
		}

		public String getNode() {
			return _node;
		}

		private final String _node;

	}

	public static class EmptyNotificationTemplate
		extends KaleoDefinitionValidationException {

		public EmptyNotificationTemplate(String node) {
			super(
				String.format(
					"The %s node has a empty notification template", node));

			_node = node;
		}

		public String getNode() {
			return _node;
		}

		private final String _node;

	}

	public static class InvalidDurationScale
		extends KaleoDefinitionValidationException {

		public InvalidDurationScale(String durationScale) {
			super(String.format("Invalid duration scale %s", durationScale));

			_durationScale = durationScale;
		}

		public String getDurationScale() {
			return _durationScale;
		}

		private final String _durationScale;

	}

	public static class InvalidExecutionType
		extends KaleoDefinitionValidationException {

		public InvalidExecutionType(String executionType) {
			super(String.format("Invalid execution type %s", executionType));

			_executionType = executionType;
		}

		public String getExecutionType() {
			return _executionType;
		}

		private final String _executionType;

	}

	public static class InvalidNodeType
		extends KaleoDefinitionValidationException {

		public InvalidNodeType(String nodeType) {
			super(String.format("Invalid node type %s", nodeType));

			_nodeType = nodeType;
		}

		public String getNodeType() {
			return _nodeType;
		}

		private final String _nodeType;

	}

	public static class InvalidNotificationType
		extends KaleoDefinitionValidationException {

		public InvalidNotificationType(String notificationType) {
			super(
				String.format(
					"Invalid notification type %s", notificationType));

			_notificationType = notificationType;
		}

		public String getNotificationType() {
			return _notificationType;
		}

		private final String _notificationType;

	}

	public static class InvalidRecipientType
		extends KaleoDefinitionValidationException {

		public InvalidRecipientType(String recipientType) {
			super(String.format("Invalid recipient type %s", recipientType));

			_recipientType = recipientType;
		}

		public String getRecipientType() {
			return _recipientType;
		}

		private final String _recipientType;

	}

	public static class InvalidScriptLanguage
		extends KaleoDefinitionValidationException {

		public InvalidScriptLanguage(String scriptLanguage) {
			super(String.format("Invalid script language %s", scriptLanguage));

			_scriptLanguage = scriptLanguage;
		}

		public String getScriptLanguage() {
			return _scriptLanguage;
		}

		private final String _scriptLanguage;

	}

	public static class InvalidTemplateLanguage
		extends KaleoDefinitionValidationException {

		public InvalidTemplateLanguage(String templateLanguage) {
			super(
				String.format(
					"Invalid template language %s", templateLanguage));

			_templateLanguage = templateLanguage;
		}

		public String getTemplateLanguage() {
			return _templateLanguage;
		}

		private final String _templateLanguage;

	}

	public static class MultipleInitialStateNodes
		extends KaleoDefinitionValidationException {

		public MultipleInitialStateNodes(String state1, String state2) {
			super(
				String.format(
					"The workflow has too many start nodes (state nodes %s " +
						"and %s)",
					state1, state2));

			_state1 = state1;
			_state2 = state2;
		}

		public String getState1() {
			return _state1;
		}

		public String getState2() {
			return _state2;
		}

		private final String _state1;
		private final String _state2;

	}

	public static class MustNotSetIncomingTransition
		extends KaleoDefinitionValidationException {

		public MustNotSetIncomingTransition(String node) {
			super(
				String.format(
					"The %s node cannot have an incoming transition", node));

			_node = node;
		}

		public String getNode() {
			return _node;
		}

		private final String _node;

	}

	public static class MustNotSetMoreThanOneDefaultTransition
		extends KaleoDefinitionValidationException {

		public MustNotSetMoreThanOneDefaultTransition(String node) {
			super(
				String.format(
					"The %s node cannot have more than one default transition",
					node));

			_node = node;
		}

		public String getNode() {
			return _node;
		}

		private final String _node;

	}

	public static class MustPairedForkAndJoinNodes
		extends KaleoDefinitionValidationException {

		public MustPairedForkAndJoinNodes(String fork, String node) {
			super(
				String.format(
					"Fork %s and join %s nodes must be paired", fork, node));

			_fork = fork;
			_node = node;
		}

		public String getFork() {
			return _fork;
		}

		public String getNode() {
			return _node;
		}

		private final String _fork;
		private final String _node;

	}

	public static class MustSetAssignments
		extends KaleoDefinitionValidationException {

		public MustSetAssignments(String task) {
			super(
				String.format(
					"Specify at least one assignment for the %s task node",
					task));

			_task = task;
		}

		public String getTask() {
			return _task;
		}

		private final String _task;

	}

	public static class MustSetIncomingTransition
		extends KaleoDefinitionValidationException {

		public MustSetIncomingTransition(String node) {
			super(
				String.format(
					"The %s node must have an incoming transition", node));

			_node = node;
		}

		public String getNode() {
			return _node;
		}

		private final String _node;

	}

	public static class MustSetInitialStateNode
		extends KaleoDefinitionValidationException {

		public MustSetInitialStateNode() {
			super("You must define a start node");
		}

	}

	public static class MustSetJoinNode
		extends KaleoDefinitionValidationException {

		public MustSetJoinNode(String fork) {
			super(
				String.format(
					"The %s fork node must have a matching join node", fork));

			_fork = fork;
		}

		public String getFork() {
			return _fork;
		}

		private final String _fork;

	}

	public static class MustSetMultipleOutgoingTransition
		extends KaleoDefinitionValidationException {

		public MustSetMultipleOutgoingTransition(String node) {
			super(
				String.format(
					"The %s node must have at least 2 outgoing transitions",
					node));

			_node = node;
		}

		public String getNode() {
			return _node;
		}

		private final String _node;

	}

	public static class MustSetOutgoingTransition
		extends KaleoDefinitionValidationException {

		public MustSetOutgoingTransition(String node) {
			super(
				String.format(
					"The %s node must have an outgoing transition", node));

			_node = node;
		}

		public String getNode() {
			return _node;
		}

		private final String _node;

	}

	public static class MustSetSourceNode
		extends KaleoDefinitionValidationException {

		public MustSetSourceNode(String node) {
			super(
				String.format(
					"The %s transition must have a source node", node));

			_node = node;
		}

		public String getNode() {
			return _node;
		}

		private final String _node;

	}

	public static class MustSetTargetNode
		extends KaleoDefinitionValidationException {

		public MustSetTargetNode(String node) {
			super(String.format("The %s transition must end at a node", node));

			_node = node;
		}

		public String getNode() {
			return _node;
		}

		private final String _node;

	}

	public static class MustSetTaskFormDefinitionOrReference
		extends KaleoDefinitionValidationException {

		public MustSetTaskFormDefinitionOrReference(
			String task, String taskForm) {

			super(
				String.format(
					"The task form %s for task %s must specify a form " +
						"reference or form definition",
					taskForm, task));

			_task = task;
			_taskForm = taskForm;
		}

		public String getTask() {
			return _task;
		}

		public String getTaskForm() {
			return _taskForm;
		}

		private final String _task;
		private final String _taskForm;

	}

	public static class MustSetTerminalStateNode
		extends KaleoDefinitionValidationException {

		public MustSetTerminalStateNode() {
			super("You must define an end node");
		}

	}

	public static class MustSetValidNodeNameLength
		extends KaleoDefinitionValidationException {

		public MustSetValidNodeNameLength(int length, String node) {
			super(
				String.format(
					"The %s node name exceeds the length limit of %s " +
						"characters",
					node, length));

			_length = length;
			_node = node;
		}

		public int getLength() {
			return _length;
		}

		public String getNode() {
			return _node;
		}

		private final int _length;
		private final String _node;

	}

	public static class UnbalancedForkAndJoinNode
		extends KaleoDefinitionValidationException {

		public UnbalancedForkAndJoinNode(String fork, String join) {
			super(
				String.format(
					"Fix the errors between the fork node %s and join node %s",
					fork, join));

			_fork = fork;
			_join = join;
		}

		public String getFork() {
			return _fork;
		}

		public String getJoin() {
			return _join;
		}

		private final String _fork;
		private final String _join;

	}

	public static class UnbalancedForkAndJoinNodes
		extends KaleoDefinitionValidationException {

		public UnbalancedForkAndJoinNodes() {
			super(
				"Each fork node requires a join node. Make sure all forks " +
					"and joins are properly paired");
		}

	}

}
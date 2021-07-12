/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.workflow.metrics.internal.search.index.util.WorkflowMetricsIndexerUtil;
import com.liferay.portal.workflow.metrics.search.index.TaskWorkflowMetricsIndexer;

import java.time.Duration;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = TaskWorkflowMetricsIndexer.class)
public class TaskWorkflowMetricsIndexerImpl
	extends BaseWorkflowMetricsIndexer implements TaskWorkflowMetricsIndexer {

	@Override
	public Document addTask(
		Map<Locale, String> assetTitleMap, Map<Locale, String> assetTypeMap,
		Long[] assigneeIds, String assigneeType, String className, long classPK,
		long companyId, boolean completed, Date completionDate,
		Long completionUserId, Date createDate, boolean instanceCompleted,
		Date instanceCompletionDate, long instanceId, Date modifiedDate,
		String name, long nodeId, long processId, String processVersion,
		long taskId, long userId) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		if (assigneeIds != null) {
			documentBuilder.setLongs("assigneeIds", assigneeIds);
			documentBuilder.setString("assigneeType", assigneeType);
		}

		documentBuilder.setString(
			"className", className
		).setLong(
			"classPK", classPK
		).setLong(
			"companyId", companyId
		).setValue(
			"completed", completed
		);

		if (completed) {
			documentBuilder.setDate(
				"completionDate", getDate(completionDate)
			).setLong(
				"completionUserId", completionUserId
			);
		}

		documentBuilder.setDate(
			"createDate", getDate(createDate)
		).setValue(
			Field.getSortableFieldName("createDate_Number"),
			createDate.getTime()
		).setValue(
			"deleted", false
		);

		if (completed) {
			documentBuilder.setLong(
				"duration", _getDuration(completionDate, createDate));
		}

		documentBuilder.setValue(
			"instanceCompleted", instanceCompleted
		).setDate(
			"instanceCompletionDate", getDate(instanceCompletionDate)
		).setLong(
			"instanceId", instanceId
		).setDate(
			"modifiedDate", getDate(modifiedDate)
		).setString(
			"name", name
		).setLong(
			"nodeId", nodeId
		).setLong(
			"processId", processId
		).setLong(
			"taskId", taskId
		).setString(
			"uid", digest(companyId, taskId)
		).setLong(
			"userId", userId
		).setString(
			"version", processVersion
		);

		setLocalizedField(documentBuilder, "assetTitle", assetTitleMap);
		setLocalizedField(documentBuilder, "assetType", assetTypeMap);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(
			() -> {
				addDocument(document);

				if (completed) {
					return;
				}

				ScriptBuilder scriptBuilder = scripts.builder();

				UpdateDocumentRequest updateDocumentRequest =
					new UpdateDocumentRequest(
						_instanceWorkflowMetricsIndex.getIndexName(companyId),
						WorkflowMetricsIndexerUtil.digest(
							_instanceWorkflowMetricsIndex.getIndexType(),
							companyId, instanceId),
						scriptBuilder.idOrCode(
							StringUtil.read(
								getClass(),
								"dependencies/workflow-metrics-add-task-" +
									"script.painless")
						).language(
							"painless"
						).putParameter(
							"task",
							HashMapBuilder.<String, Object>put(
								"assigneeIds", assigneeIds
							).put(
								"assigneeName",
								() -> {
									if (!Objects.equals(
											assigneeType,
											User.class.getName()) ||
										(assigneeIds == null)) {

										return null;
									}

									User user = _userLocalService.fetchUser(
										assigneeIds[0]);

									return user.getFullName();
								}
							).put(
								"assigneeType", assigneeType
							).put(
								"taskId", taskId
							).put(
								"taskName", name
							).build()
						).scriptType(
							ScriptType.INLINE
						).build());

				updateDocumentRequest.setScriptedUpsert(true);

				searchEngineAdapter.execute(updateDocumentRequest);
			});

		return document;
	}

	@Override
	public Document completeTask(
		long companyId, Date completionDate, long completionUserId,
		long duration, Date modifiedDate, long taskId, long userId) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setValue(
			"completed", true
		).setDate(
			"completionDate", getDate(completionDate)
		).setLong(
			"completionUserId", completionUserId
		).setLong(
			"duration", duration
		).setDate(
			"modifiedDate", getDate(modifiedDate)
		).setLong(
			"taskId", taskId
		).setString(
			"uid", digest(companyId, taskId)
		).setLong(
			"userId", userId
		);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(
			() -> {
				updateDocument(document);

				_deleteTask(companyId, taskId);

				BooleanQuery booleanQuery = queries.booleanQuery();

				booleanQuery.addMustQueryClauses(
					queries.term("companyId", companyId),
					queries.term("taskId", taskId));

				_slaTaskResultWorkflowMetricsIndexer.updateDocuments(
					companyId,
					HashMapBuilder.<String, Object>put(
						"completionDate", document.getDate("completionDate")
					).put(
						"completionUserId", document.getLong("completionUserId")
					).build(),
					booleanQuery);
			});

		return document;
	}

	@Override
	public void deleteTask(long companyId, long taskId) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setLong(
			"taskId", taskId
		).setString(
			"uid", digest(companyId, taskId)
		);

		workflowMetricsPortalExecutor.execute(
			() -> {
				deleteDocument(documentBuilder);

				_deleteTask(companyId, taskId);
			});
	}

	@Override
	public String getIndexName(long companyId) {
		return _taskWorkflowMetricsIndex.getIndexName(companyId);
	}

	@Override
	public String getIndexType() {
		return _taskWorkflowMetricsIndex.getIndexType();
	}

	@Override
	public Document updateTask(
		Map<Locale, String> assetTitleMap, Map<Locale, String> assetTypeMap,
		Long[] assigneeIds, String assigneeType, long companyId,
		Date modifiedDate, long taskId, long userId) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		if (assigneeIds != null) {
			documentBuilder.setLongs("assigneeIds", assigneeIds);
			documentBuilder.setString("assigneeType", assigneeType);
		}

		documentBuilder.setLong(
			"companyId", companyId
		).setDate(
			"modifiedDate", getDate(modifiedDate)
		).setLong(
			"taskId", taskId
		).setString(
			"uid", digest(companyId, taskId)
		).setLong(
			"userId", userId
		);

		setLocalizedField(documentBuilder, "assetTitle", assetTitleMap);
		setLocalizedField(documentBuilder, "assetType", assetTypeMap);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(
			() -> {
				updateDocument(document);

				if (Objects.isNull(document.getLongs("assigneeIds"))) {
					return;
				}

				BooleanQuery booleanQuery = queries.booleanQuery();

				booleanQuery.addMustQueryClauses(
					queries.term("companyId", document.getLong("companyId")),
					queries.term("taskId", document.getLong("taskId")));

				_slaTaskResultWorkflowMetricsIndexer.updateDocuments(
					companyId,
					HashMapBuilder.<String, Object>put(
						"assigneeIds", assigneeIds
					).put(
						"assigneeType", assigneeType
					).build(),
					booleanQuery);

				ScriptBuilder scriptBuilder = scripts.builder();

				scriptBuilder.idOrCode(
					StringUtil.read(
						getClass(),
						"dependencies/workflow-metrics-update-task-" +
							"script.painless")
				).language(
					"painless"
				).putParameter(
					"assigneeIds", assigneeIds
				);

				if (Objects.equals(assigneeType, User.class.getName()) &&
					(assigneeIds != null)) {

					User user = _userLocalService.fetchUser(assigneeIds[0]);

					scriptBuilder.putParameter(
						"assigneeName", user.getFullName());
				}

				scriptBuilder.putParameter(
					"assigneeType", assigneeType
				).putParameter(
					"taskId", taskId
				).scriptType(
					ScriptType.INLINE
				);

				UpdateByQueryDocumentRequest updateByQueryDocumentRequest =
					new UpdateByQueryDocumentRequest(
						queries.nested(
							"tasks", queries.term("tasks.taskId", taskId)),
						scriptBuilder.build(),
						_instanceWorkflowMetricsIndex.getIndexName(companyId));

				updateByQueryDocumentRequest.setRefresh(true);

				searchEngineAdapter.execute(updateByQueryDocumentRequest);
			});

		return document;
	}

	private void _deleteTask(long companyId, long taskId) {
		ScriptBuilder scriptBuilder = scripts.builder();

		searchEngineAdapter.execute(
			new UpdateByQueryDocumentRequest(
				queries.nested("tasks", queries.term("tasks.taskId", taskId)),
				scriptBuilder.idOrCode(
					StringUtil.read(
						getClass(),
						"dependencies/workflow-metrics-delete-task-" +
							"script.painless")
				).language(
					"painless"
				).putParameter(
					"taskId", taskId
				).scriptType(
					ScriptType.INLINE
				).build(),
				_instanceWorkflowMetricsIndex.getIndexName(companyId)));
	}

	private long _getDuration(Date completionDate, Date createDate) {
		Duration duration = Duration.between(
			createDate.toInstant(), completionDate.toInstant());

		return duration.toMillis();
	}

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndex _instanceWorkflowMetricsIndex;

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndex _taskWorkflowMetricsIndex;

	@Reference
	private UserLocalService _userLocalService;

}
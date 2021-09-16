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

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.bucket.Order;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregationResult;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketSelectorPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketSortPipelineAggregation;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.workflow.metrics.model.Assignment;
import com.liferay.portal.workflow.metrics.model.UserAssignment;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Assignee;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.TaskBulkSelection;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util.TaskUtil;
import com.liferay.portal.workflow.metrics.rest.internal.resource.exception.NoSuchTaskException;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.TaskResource;
import com.liferay.portal.workflow.metrics.search.index.TaskWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/task.properties",
	scope = ServiceScope.PROTOTYPE, service = TaskResource.class
)
public class TaskResourceImpl extends BaseTaskResourceImpl {

	@Override
	public void deleteProcessTask(Long processId, Long taskId)
		throws Exception {

		_taskWorkflowMetricsIndexer.deleteTask(
			contextCompany.getCompanyId(), taskId);
	}

	@Override
	public Task getProcessTask(Long processId, Long taskId) throws Exception {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));
		searchSearchRequest.setQuery(
			_createTasksBooleanQuery(
				processId, taskId,
				_resourceHelper.getLatestProcessVersion(
					contextCompany.getCompanyId(), processId)));

		searchSearchRequest.setSize(1);

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getSearchHits
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::parallelStream
		).map(
			SearchHit::getDocument
		).findFirst(
		).map(
			document -> TaskUtil.toTask(
				document, _language, contextAcceptLanguage.getPreferredLocale(),
				_portal,
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					contextAcceptLanguage.getPreferredLocale(),
					TaskResourceImpl.class),
				_userLocalService::fetchUser)
		).orElseThrow(
			() -> new NoSuchTaskException(
				"No task exists with the task ID " + taskId)
		);
	}

	@Override
	public Page<Task> getProcessTasksPage(Long processId) throws Exception {
		return Page.of(_getTasks(processId));
	}

	@Override
	public void patchProcessTask(Long processId, Long taskId, Task task)
		throws Exception {

		getProcessTask(processId, taskId);

		List<Assignment> assignments = new ArrayList<>();

		Assignee assignee = task.getAssignee();

		if ((assignee != null) && (assignee.getId() != null)) {
			User user = _userLocalService.fetchUser(assignee.getId());

			assignments.add(
				new UserAssignment(assignee.getId(), user.getFullName()));
		}

		_taskWorkflowMetricsIndexer.updateTask(
			LocalizedMapUtil.getLocalizedMap(task.getAssetTitle_i18n()),
			LocalizedMapUtil.getLocalizedMap(task.getAssetType_i18n()),
			assignments, contextCompany.getCompanyId(), task.getDateModified(),
			task.getId(), contextUser.getUserId());
	}

	@Override
	public void patchProcessTaskComplete(Long processId, Long taskId, Task task)
		throws Exception {

		getProcessTask(processId, taskId);

		_taskWorkflowMetricsIndexer.completeTask(
			contextCompany.getCompanyId(), task.getDateCompletion(),
			task.getCompletionUserId(), task.getDuration(),
			task.getDateModified(), taskId, contextUser.getUserId());
	}

	@Override
	public Task postProcessTask(Long processId, Task task) throws Exception {
		List<Assignment> assignments = new ArrayList<>();

		Assignee assignee = task.getAssignee();

		if ((assignee != null) && (assignee.getId() != null)) {
			User user = _userLocalService.fetchUser(assignee.getId());

			assignments.add(
				new UserAssignment(assignee.getId(), user.getFullName()));
		}

		return TaskUtil.toTask(
			_taskWorkflowMetricsIndexer.addTask(
				LocalizedMapUtil.getLocalizedMap(task.getAssetTitle_i18n()),
				LocalizedMapUtil.getLocalizedMap(task.getAssetType_i18n()),
				assignments, task.getClassName(), task.getClassPK(),
				contextCompany.getCompanyId(), false, null, null,
				task.getDateCreated(), false, null, task.getInstanceId(),
				task.getDateModified(), task.getName(), task.getNodeId(),
				processId, task.getProcessVersion(), task.getId(),
				contextUser.getUserId()),
			_language, contextAcceptLanguage.getPreferredLocale(), _portal,
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				contextAcceptLanguage.getPreferredLocale(),
				TaskResourceImpl.class),
			_userLocalService::fetchUser);
	}

	@Override
	public Page<Task> postTasksPage(
			Pagination pagination, TaskBulkSelection taskBulkSelection)
		throws Exception {

		if (ArrayUtil.isEmpty(taskBulkSelection.getAssigneeIds())) {
			taskBulkSelection.setAssigneeIds(
				new Long[] {-1L, contextUser.getUserId()});
		}

		SearchSearchResponse searchSearchResponse = _getSearchSearchResponse(
			taskBulkSelection.getAssigneeIds(),
			taskBulkSelection.getInstanceIds(),
			taskBulkSelection.getProcessId(),
			taskBulkSelection.getSlaStatuses(),
			taskBulkSelection.getTaskNames());

		int taskCount = _getTaskCount(searchSearchResponse);

		if (taskCount > 0) {
			if (pagination == null) {
				return Page.of(
					Stream.of(
						searchSearchResponse.getAggregationResultsMap()
					).map(
						aggregationResultsMap ->
							(TermsAggregationResult)aggregationResultsMap.get(
								"name")
					).map(
						TermsAggregationResult::getBuckets
					).flatMap(
						Collection::stream
					).map(
						bucket -> TaskUtil.toTask(
							_language, bucket.getKey(),
							ResourceBundleUtil.getModuleAndPortalResourceBundle(
								contextAcceptLanguage.getPreferredLocale(),
								TaskResourceImpl.class))
					).collect(
						Collectors.toList()
					));
			}

			return Page.of(
				_getTasks(
					taskBulkSelection.getAssigneeIds(),
					taskBulkSelection.getInstanceIds(), pagination,
					taskBulkSelection.getProcessId(),
					taskBulkSelection.getSlaStatuses(),
					searchSearchResponse.getCount(),
					taskBulkSelection.getTaskNames()),
				pagination, taskCount);
		}

		return Page.of(Collections.emptyList());
	}

	private BooleanQuery _createAssigneeIdsTermsBooleanQuery(
		Long[] assigneeIds) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (ArrayUtil.contains(assigneeIds, -1L)) {
			BooleanQuery shouldBooleanQuery = _queries.booleanQuery();

			TermsQuery termsQuery = _queries.terms("assigneeIds");

			termsQuery.addValues(
				Stream.of(
					ArrayUtil.toArray(contextUser.getRoleIds())
				).map(
					String::valueOf
				).toArray(
					Object[]::new
				));

			shouldBooleanQuery.addMustQueryClauses(
				termsQuery,
				_queries.term("assigneeType", Role.class.getName()));

			booleanQuery.addShouldQueryClauses(shouldBooleanQuery);
		}

		if (!ArrayUtil.contains(assigneeIds, -1L) || (assigneeIds.length > 1)) {
			BooleanQuery shouldBooleanQuery = _queries.booleanQuery();

			TermsQuery termsQuery = _queries.terms("assigneeIds");

			termsQuery.addValues(
				Stream.of(
					assigneeIds
				).filter(
					assigneeId -> assigneeId > 0
				).map(
					String::valueOf
				).toArray(
					Object[]::new
				));

			shouldBooleanQuery.addMustQueryClauses(
				termsQuery,
				_queries.term("assigneeType", User.class.getName()));

			booleanQuery.addShouldQueryClauses(shouldBooleanQuery);
		}

		return booleanQuery;
	}

	private BooleanQuery _createBooleanQuery(long processId) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("taskId", 0));

		return booleanQuery.addMustQueryClauses(
			_queries.term("completed", Boolean.FALSE),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId));
	}

	private BooleanQuery _createBooleanQuery(long processId, String version) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustQueryClauses(
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId),
			_queries.term("version", version));
	}

	private BooleanQuery _createBooleanQuery(
		Long[] assigneeIds, Long[] instanceIds, Long processId) {

		BooleanQuery filterBooleanQuery = _queries.booleanQuery();

		BooleanQuery booleanQuery = _queries.booleanQuery();

		BooleanQuery slaTaskResultsBooleanQuery = _queries.booleanQuery();

		slaTaskResultsBooleanQuery.addFilterQueryClauses(
			_queries.term(
				"_index",
				_slaTaskResultWorkflowMetricsIndexNameBuilder.getIndexName(
					contextCompany.getCompanyId())));
		slaTaskResultsBooleanQuery.addMustQueryClauses(
			_createSLATaskResultsBooleanQuery(instanceIds, processId));

		BooleanQuery tasksBooleanQuery = _queries.booleanQuery();

		tasksBooleanQuery.addFilterQueryClauses(
			_queries.term(
				"_index",
				_taskWorkflowMetricsIndexNameBuilder.getIndexName(
					contextCompany.getCompanyId())));
		tasksBooleanQuery.addMustQueryClauses(
			_createTasksBooleanQuery(assigneeIds, instanceIds, processId));

		return filterBooleanQuery.addFilterQueryClauses(
			booleanQuery.addShouldQueryClauses(
				slaTaskResultsBooleanQuery, tasksBooleanQuery));
	}

	private BucketSelectorPipelineAggregation
		_createBucketSelectorPipelineAggregation() {

		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation =
			_aggregations.bucketSelector(
				"bucketSelector", _scripts.script("params.taskCount > 0"));

		bucketSelectorPipelineAggregation.addBucketPath(
			"taskCount", "taskCount.value");

		return bucketSelectorPipelineAggregation;
	}

	private BucketSortPipelineAggregation _createBucketSortPipelineAggregation(
		Pagination pagination) {

		BucketSortPipelineAggregation bucketSortPipelineAggregation =
			_aggregations.bucketSort("bucketSort");

		FieldSort keyFieldSort = _sorts.field("_key");

		keyFieldSort.setSortOrder(SortOrder.ASC);

		bucketSortPipelineAggregation.addSortFields(keyFieldSort);

		bucketSortPipelineAggregation.setFrom(pagination.getStartPosition());
		bucketSortPipelineAggregation.setSize(pagination.getPageSize());

		return bucketSortPipelineAggregation;
	}

	private BooleanQuery _createFilterBooleanQuery(
		long processId, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addShouldQueryClauses(_createBooleanQuery(processId));

		return booleanQuery.addShouldQueryClauses(
			_createBooleanQuery(processId, version));
	}

	private BooleanQuery _createInstanceIdsTermsBooleanQuery(
		Long[] instanceIds) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		TermsQuery termsQuery = _queries.terms("instanceId");

		termsQuery.addValues(
			Stream.of(
				instanceIds
			).map(
				String::valueOf
			).toArray(
				Object[]::new
			));

		return booleanQuery.addMustQueryClauses(termsQuery);
	}

	private BooleanQuery _createSLATaskResultsBooleanQuery(
		Long[] instanceIds, Long processId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(
			_queries.term("slaDefinitionId", 0),
			_queries.term("status", WorkflowMetricsSLAStatus.NEW.name()));

		if (instanceIds != null) {
			booleanQuery.addMustQueryClauses(
				_createInstanceIdsTermsBooleanQuery(instanceIds));
		}

		if (processId != null) {
			booleanQuery.addMustQueryClauses(
				_queries.term("processId", processId));
		}

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("instanceCompleted", Boolean.FALSE));
	}

	private BooleanQuery _createTasksBooleanQuery(
		long processId, long taskId, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId),
			_queries.term("taskId", taskId), _queries.term("version", version));
	}

	private BooleanQuery _createTasksBooleanQuery(
		long processId, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			_createFilterBooleanQuery(processId, version));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE));
	}

	private BooleanQuery _createTasksBooleanQuery(
		Long[] assigneeIds, Long[] instanceIds, Long processId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("taskId", 0));

		if (instanceIds != null) {
			booleanQuery.addMustQueryClauses(
				_createInstanceIdsTermsBooleanQuery(instanceIds));
		}

		if (processId != null) {
			booleanQuery.addMustQueryClauses(
				_queries.term("processId", processId));
		}

		return booleanQuery.addMustQueryClauses(
			_createAssigneeIdsTermsBooleanQuery(assigneeIds),
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("completed", Boolean.FALSE),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("instanceCompleted", Boolean.FALSE));
	}

	private SearchSearchResponse _getSearchSearchResponse(
		Long[] assigneeIds, Long[] instanceIds, Long processId,
		String[] slaStatuses, String[] taskNames) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms("name", "name");

		termsAggregation.addChildrenAggregations(
			_resourceHelper.creatTaskCountScriptedMetricAggregation(
				ListUtil.fromArray(assigneeIds),
				ListUtil.fromArray(slaStatuses),
				ListUtil.fromArray(taskNames)));

		termsAggregation.addOrders(Order.key(true));
		termsAggregation.addPipelineAggregations(
			_createBucketSelectorPipelineAggregation());

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.addAggregation(
			_resourceHelper.creatTaskCountScriptedMetricAggregation(
				ListUtil.fromArray(assigneeIds),
				ListUtil.fromArray(slaStatuses),
				ListUtil.fromArray(taskNames)));
		searchSearchRequest.setIndexNames(
			_slaTaskResultWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()),
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));
		searchSearchRequest.setQuery(
			_createBooleanQuery(assigneeIds, instanceIds, processId));

		return _searchRequestExecutor.executeSearchRequest(searchSearchRequest);
	}

	private int _getTaskCount(SearchSearchResponse searchSearchResponse) {
		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		ScriptedMetricAggregationResult scriptedMetricAggregationResult =
			(ScriptedMetricAggregationResult)aggregationResultsMap.get(
				"taskCount");

		return GetterUtil.getInteger(
			scriptedMetricAggregationResult.getValue());
	}

	private List<Task> _getTasks(long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms("name", "name");

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));
		searchSearchRequest.setQuery(
			_createTasksBooleanQuery(
				processId,
				_resourceHelper.getLatestProcessVersion(
					contextCompany.getCompanyId(), processId)));

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getAggregationResultsMap
		).map(
			aggregationResultsMap ->
				(TermsAggregationResult)aggregationResultsMap.get("name")
		).map(
			TermsAggregationResult::getBuckets
		).flatMap(
			Collection::stream
		).map(
			bucket -> TaskUtil.toTask(
				_language, bucket.getKey(),
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					contextAcceptLanguage.getPreferredLocale(),
					TaskResourceImpl.class))
		).collect(
			Collectors.toList()
		);
	}

	private List<Task> _getTasks(
		Long[] assigneeIds, Long[] instanceIds, Pagination pagination,
		Long processId, String[] slaStatuses, long taskCount,
		String[] taskNames) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"instanceIdTaskId", null);

		termsAggregation.setScript(
			_scripts.script("doc['instanceId'].value + doc['taskId'].value"));

		FilterAggregation indexFilterAggregation = _aggregations.filter(
			"index",
			_queries.term(
				"_index",
				_taskWorkflowMetricsIndexNameBuilder.getIndexName(
					contextCompany.getCompanyId())));

		indexFilterAggregation.addChildAggregation(
			_aggregations.topHits("topHits"));

		termsAggregation.addChildrenAggregations(
			indexFilterAggregation,
			_resourceHelper.creatTaskCountScriptedMetricAggregation(
				ListUtil.fromArray(assigneeIds),
				ListUtil.fromArray(slaStatuses),
				ListUtil.fromArray(taskNames)));

		termsAggregation.addOrders(Order.key(true));
		termsAggregation.addPipelineAggregations(
			_createBucketSelectorPipelineAggregation(),
			_createBucketSortPipelineAggregation(pagination));

		termsAggregation.setSize(GetterUtil.getInteger(taskCount));

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_slaTaskResultWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()),
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));

		searchSearchRequest.setQuery(
			_createBooleanQuery(assigneeIds, instanceIds, processId));

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getAggregationResultsMap
		).map(
			aggregationResultsMap ->
				(TermsAggregationResult)aggregationResultsMap.get(
					"instanceIdTaskId")
		).map(
			TermsAggregationResult::getBuckets
		).flatMap(
			Collection::stream
		).map(
			bucket -> (FilterAggregationResult)bucket.getChildAggregationResult(
				"index")
		).map(
			filterAggregationResult ->
				(TopHitsAggregationResult)
					filterAggregationResult.getChildAggregationResult("topHits")
		).map(
			TopHitsAggregationResult::getSearchHits
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::stream
		).map(
			SearchHit::getSourcesMap
		).map(
			sourcesMap -> TaskUtil.toTask(
				_language, contextAcceptLanguage.getPreferredLocale(), _portal,
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					contextAcceptLanguage.getPreferredLocale(),
					TaskResourceImpl.class),
				sourcesMap, _userLocalService::fetchUser)
		).collect(
			Collectors.toCollection(LinkedList::new)
		);
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

	@Reference
	private ResourceHelper _resourceHelper;

	@Reference
	private Scripts _scripts;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference(target = "(workflow.metrics.index.entity.name=sla-task-result)")
	private WorkflowMetricsIndexNameBuilder
		_slaTaskResultWorkflowMetricsIndexNameBuilder;

	@Reference
	private Sorts _sorts;

	@Reference
	private TaskWorkflowMetricsIndexer _taskWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndexNameBuilder
		_taskWorkflowMetricsIndexNameBuilder;

	@Reference
	private UserLocalService _userLocalService;

}
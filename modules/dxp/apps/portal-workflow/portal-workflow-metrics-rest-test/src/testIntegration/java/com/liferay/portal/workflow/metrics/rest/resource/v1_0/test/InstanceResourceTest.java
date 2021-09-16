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

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.model.Assignment;
import com.liferay.portal.workflow.metrics.model.RoleAssignment;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Assignee;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Creator;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.SLAResult;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class InstanceResourceTest extends BaseInstanceResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_classPK = RandomTestUtil.nextLong();
		_process = _workflowMetricsRESTTestHelper.addProcess(
			testGroup.getCompanyId());
		_user = UserTestUtil.addUser();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_process != null) {
			_workflowMetricsRESTTestHelper.deleteProcess(
				testGroup.getCompanyId(), _process);
		}

		_deleteInstances();
	}

	@Override
	@Test
	public void testGetProcessInstance() throws Exception {
		Instance instance = randomInstance();

		instance.setSlaResults(
			Stream.of(
				_toSLAResult(true, SLAResult.Status.NEW),
				_toSLAResult(true, SLAResult.Status.NEW),
				_toSLAResult(true, SLAResult.Status.PAUSED),
				_toSLAResult(true, SLAResult.Status.PAUSED),
				_toSLAResult(true, SLAResult.Status.RUNNING),
				_toSLAResult(true, SLAResult.Status.RUNNING),
				_toSLAResult(true, SLAResult.Status.RUNNING),
				_toSLAResult(true, SLAResult.Status.STOPPED),
				_toSLAResult(true, SLAResult.Status.STOPPED),
				_toSLAResult(true, SLAResult.Status.STOPPED)
			).sorted(
				Comparator.comparing(SLAResult::getRemainingTime)
			).toArray(
				SLAResult[]::new
			));

		testGetProcessInstancesPage_addInstance(_process.getId(), instance);

		Instance getInstance = instanceResource.getProcessInstance(
			instance.getProcessId(), instance.getId());

		assertEquals(instance, getInstance);
		assertValid(getInstance);
	}

	@Override
	@Test
	public void testGetProcessInstancesPage() throws Exception {
		super.testGetProcessInstancesPage();

		_deleteInstances();

		Instance instance1 = randomInstance();

		instance1.setAssignees(
			new Assignee[] {
				new Assignee() {
					{
						id = _user.getUserId();
					}
				}
			});
		instance1.setClassPK(_classPK);
		instance1.setCompleted(true);
		instance1.setDateCompletion(
			DateUtils.truncate(new Date(), Calendar.SECOND));

		testGetProcessInstancesPage_addInstance(_process.getId(), instance1);

		_workflowMetricsRESTTestHelper.addSLAInstanceResults(
			testGroup.getCompanyId(), instance1,
			_toSLAResult(true, SLAResult.Status.STOPPED),
			_toSLAResult(true, SLAResult.Status.PAUSED));

		Instance instance2 = randomInstance();

		instance2.setAssignees(
			new Assignee[] {
				new Assignee() {
					{
						id = -1L;
					}
				}
			});

		Instance instance3 = randomInstance();

		instance3.setAssignees(
			new Assignee[] {
				new Assignee() {
					{
						id = -1L;
					}
				}
			});

		Role siteAdministrationRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		Role siteMemberRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_MEMBER);

		_addUserGroupRole(
			new long[] {TestPropsValues.getUserId()},
			TestPropsValues.getGroupId(), siteAdministrationRole.getRoleId());

		_addUserGroupRole(
			new long[] {TestPropsValues.getUserId()}, testGroup.getGroupId(),
			siteMemberRole.getRoleId());

		List<Assignment> assignments = new ArrayList<>();

		assignments.add(
			new RoleAssignment(
				siteAdministrationRole.getRoleId(),
				Collections.singletonList(TestPropsValues.getGroupId())));

		_testGetProcessInstancesPage_addInstance(
			assignments, instance2, _process.getId());

		assignments = new ArrayList<>();

		assignments.add(
			new RoleAssignment(
				siteMemberRole.getRoleId(),
				Collections.singletonList(TestPropsValues.getGroupId())));

		_testGetProcessInstancesPage_addInstance(
			assignments, instance3, _process.getId());

		_testGetProcessInstancesPage(
			null, null, null, null, new String[] {"Pending"},
			instances -> instances.forEach(
				instance -> {
					Assignee assignee = (Assignee)ArrayUtil.getValue(
						instance.getAssignees(), 0);

					if (Objects.equals(instance.getId(), instance2.getId())) {
						Assert.assertTrue(assignee.getReviewer());
					}
					else if (Objects.equals(
								instance.getId(), instance3.getId())) {

						Assert.assertFalse(assignee.getReviewer());
					}
				}));

		_testGetProcessInstancesPage(
			null, null, null, null, new String[] {"Completed"},
			instances -> assertEqualsIgnoringOrder(
				Collections.singletonList(instance1), instances));
		_testGetProcessInstancesPage(
			null, new Long[] {_classPK}, null, null, null,
			instances -> assertEqualsIgnoringOrder(
				Collections.singletonList(instance1), instances));
		_testGetProcessInstancesPage(
			null, null, null, null, new String[] {"Pending"},
			instances -> assertEqualsIgnoringOrder(
				Arrays.asList(instance2, instance3), instances));
		_testGetProcessInstancesPage(
			new Long[] {_user.getUserId()}, null, null, null, null,
			instances -> assertEqualsIgnoringOrder(
				Collections.singletonList(instance1), instances));
		_testGetProcessInstancesPage(
			null, null, null, null, new String[] {"Completed", "Pending"},
			instances -> assertEqualsIgnoringOrder(
				Arrays.asList(instance1, instance2, instance3), instances));
		_testGetProcessInstancesPage(
			null, null, null, null, null,
			instances -> assertEqualsIgnoringOrder(
				Arrays.asList(instance1, instance2, instance3), instances));

		Date dateEnd = DateUtils.addSeconds(instance1.getDateCompletion(), 1);
		Date dateStart = DateUtils.addSeconds(
			instance1.getDateCompletion(), -1);

		_testGetProcessInstancesPage(
			null, null, dateEnd, dateStart, new String[] {"Completed"},
			instances -> assertEqualsIgnoringOrder(
				Collections.singletonList(instance1), instances));
		_testGetProcessInstancesPage(
			null, null, dateEnd, dateStart,
			new String[] {"Completed", "Pending"},
			instances -> assertEqualsIgnoringOrder(
				Arrays.asList(instance1, instance2, instance3), instances));
	}

	@Override
	@Test
	public void testGetProcessInstancesPageWithSortDateTime() throws Exception {
		testGetProcessInstancesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, instance1, instance2) -> {
				if (Objects.equals(entityField.getName(), "dateOverdue")) {
					Stream.of(
						instance1.getSlaResults()
					).forEach(
						slaResult -> slaResult.setDateOverdue(
							DateUtils.addDays(slaResult.getDateOverdue(), -2))
					);

					Stream.of(
						instance2.getSlaResults()
					).forEach(
						slaResult -> slaResult.setDateOverdue(
							DateUtils.addDays(slaResult.getDateOverdue(), -1))
					);
				}
				else {
					BeanUtils.setProperty(
						instance1, entityField.getName(),
						DateUtils.addMinutes(
							DateUtils.truncate(new Date(), Calendar.SECOND),
							-2));
				}
			});
	}

	@Override
	@Test
	public void testGetProcessInstancesPageWithSortString() throws Exception {
		testGetProcessInstancesPageWithSort(
			EntityField.Type.STRING,
			(entityField, instance1, instance2) -> {
				String entityFieldName = entityField.getName();

				if (StringUtil.equals("assigneeName", entityFieldName)) {
					instance1.setAssignees(
						() -> {
							User user = _addUser("aaa");

							return new Assignee[] {
								new Assignee() {
									{
										id = user.getUserId();
										name = user.getFullName();
									}
								}
							};
						});

					instance2.setAssignees(
						() -> {
							User user = _addUser("bbb");

							return new Assignee[] {
								new Assignee() {
									{
										id = user.getUserId();
										name = user.getFullName();
									}
								}
							};
						});
				}
				else if (StringUtil.equals("userName", entityFieldName)) {
					instance1.setCreator(
						() -> {
							User user = _addUser("aaa");

							return new Creator() {
								{
									id = user.getUserId();
									name = user.getFullName();
								}
							};
						});

					instance2.setCreator(
						() -> {
							User user = _addUser("bbb");

							return new Creator() {
								{
									id = user.getUserId();
									name = user.getFullName();
								}
							};
						});
				}
				else {
					BeanUtils.setProperty(
						instance1, entityFieldName,
						"aaa".concat(
							StringUtil.toLowerCase(
								RandomTestUtil.randomString())));
					BeanUtils.setProperty(
						instance2, entityFieldName,
						"bbb".concat(
							StringUtil.toLowerCase(
								RandomTestUtil.randomString())));
				}
			});
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Override
	protected boolean equals(Instance instance1, Instance instance2) {
		if (super.equals(instance1, instance2)) {
			return Objects.deepEquals(
				instance1.getDateCreated(), instance2.getDateCreated());
		}

		return false;
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"assetTitle", "assetType", "classPK", "creator", "processId",
			"slaResults"
		};
	}

	@Override
	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = super.getGraphQLFields();

		graphQLFields.addAll(
			getGraphQLFields(
				ReflectionUtil.getDeclaredField(
					com.liferay.portal.workflow.metrics.rest.dto.v1_0.Instance.
						class,
					"dateCreated")));

		return graphQLFields;
	}

	@Override
	protected Instance randomInstance() throws Exception {
		Instance instance = super.randomInstance();

		instance.setAssetTitle_i18n(
			HashMapBuilder.put(
				LocaleUtil.US.toLanguageTag(), instance.getAssetTitle()
			).build());
		instance.setAssetType_i18n(
			HashMapBuilder.put(
				LocaleUtil.US.toLanguageTag(), instance.getAssetType()
			).build());

		instance.setAssignees(new Assignee[0]);

		User adminUser = UserTestUtil.getAdminUser(testGroup.getCompanyId());

		instance.setCreator(
			new Creator() {
				{
					id = adminUser.getUserId();
					name = adminUser.getFullName();
				}
			});

		instance.setCompleted(false);
		instance.setDateCompletion((Date)null);
		instance.setDateCreated(
			DateUtils.truncate(new Date(), Calendar.SECOND));
		instance.setProcessId(_process.getId());
		instance.setProcessVersion(_process.getVersion());
		instance.setSlaResults(
			new SLAResult[] {
				_toSLAResult(true, SLAResult.Status.RUNNING),
				_toSLAResult(false, SLAResult.Status.RUNNING)
			});

		return instance;
	}

	@Override
	protected Instance testDeleteProcessInstance_addInstance()
		throws Exception {

		return testGetProcessInstance_addInstance();
	}

	@Override
	protected Instance testGetProcessInstance_addInstance() throws Exception {
		return testGetProcessInstancesPage_addInstance(
			_process.getId(), randomInstance());
	}

	@Override
	protected Instance testGetProcessInstancesPage_addInstance(
			Long processId, Instance instance)
		throws Exception {

		return _testGetProcessInstancesPage_addInstance(
			Collections.emptyList(), instance, processId);
	}

	@Override
	protected Long testGetProcessInstancesPage_getProcessId() throws Exception {
		return _process.getId();
	}

	@Override
	protected Instance testGraphQLInstance_addInstance() throws Exception {
		return testGetProcessInstance_addInstance();
	}

	@Override
	protected Instance testPatchProcessInstance_addInstance() throws Exception {
		return testGetProcessInstance_addInstance();
	}

	@Override
	protected Instance testPatchProcessInstanceComplete_addInstance()
		throws Exception {

		Instance instance = testGetProcessInstance_addInstance();

		instance.setCompleted(true);
		instance.setDateCompletion(RandomTestUtil.nextDate());

		return instance;
	}

	protected Instance testPostProcessInstance_addInstance(Instance instance)
		throws Exception {

		return testGetProcessInstancesPage_addInstance(
			_process.getId(), instance);
	}

	private User _addUser(String firstName) throws Exception {
		return UserTestUtil.addUser(
			RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE),
			LocaleUtil.getDefault(),
			firstName.concat(
				StringUtil.toLowerCase(RandomTestUtil.randomString())),
			RandomTestUtil.randomString(),
			new long[] {TestPropsValues.getGroupId()});
	}

	private void _addUserGroupRole(long[] userIds, long groupId, long roleIds) {
		_userGroupRoleLocalService.addUserGroupRoles(userIds, groupId, roleIds);
	}

	private void _deleteInstances() throws Exception {
		for (Instance instance : _instances) {
			_workflowMetricsRESTTestHelper.deleteInstance(
				testGroup.getCompanyId(), instance);
		}

		_instances.clear();
	}

	private void _testGetProcessInstancesPage(
			Long[] assigneeIds, Long[] classPKs, Date dateEnd, Date dateStart,
			String[] statuses,
			UnsafeConsumer<List<Instance>, Exception> unsafeConsumer)
		throws Exception {

		Page<Instance> page = instanceResource.getProcessInstancesPage(
			_process.getId(), assigneeIds, classPKs, dateEnd, dateStart, null,
			statuses, null, Pagination.of(1, 3), null);

		unsafeConsumer.accept((List<Instance>)page.getItems());
	}

	private Instance _testGetProcessInstancesPage_addInstance(
			List<Assignment> assignments, Instance instance, Long processId)
		throws Exception {

		instance.setProcessId(processId);

		instance = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), instance);

		for (Assignee assignee : instance.getAssignees()) {
			if (assignee.getId() == -1L) {
				_workflowMetricsRESTTestHelper.addTask(
					assignee, assignments, testGroup.getCompanyId(), instance);
			}
			else {
				_workflowMetricsRESTTestHelper.addTask(
					assignee, testGroup.getCompanyId(), instance,
					TestPropsValues.getUser());
			}
		}

		if (instance.getCompleted()) {
			_workflowMetricsRESTTestHelper.completeInstance(
				testGroup.getCompanyId(), instance);
		}

		_workflowMetricsRESTTestHelper.addSLAInstanceResults(
			testGroup.getCompanyId(), instance, instance.getSlaResults());

		_instances.add(instance);

		return instance;
	}

	private SLAResult _toSLAResult(
		boolean overdue, SLAResult.Status slaResultStatus) {

		return new SLAResult() {
			{
				dateModified = DateUtils.truncate(
					RandomTestUtil.nextDate(), Calendar.SECOND);
				dateOverdue = DateUtils.truncate(new Date(), Calendar.SECOND);
				id = RandomTestUtil.randomLong();
				name = StringPool.BLANK;
				onTime = !overdue;
				remainingTime = overdue ? -RandomTestUtil.randomLong() :
					RandomTestUtil.randomLong();
				status = slaResultStatus;
			}
		};
	}

	private Long _classPK;
	private final List<Instance> _instances = new ArrayList<>();
	private Process _process;

	@Inject
	private RoleLocalService _roleLocalService;

	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

}
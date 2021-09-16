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

package com.liferay.portal.workflow.kaleo.internal.runtime.node.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.test.mail.MailMessage;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerLocalService;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Feliphe Marinho
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class TaskNodeExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(TaskNodeExecutorTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_workflowHandlerServiceRegistration = bundleContext.registerService(
			(Class<WorkflowHandler<?>>)(Class<?>)WorkflowHandler.class,
			(WorkflowHandler)ProxyUtil.newProxyInstance(
				WorkflowHandler.class.getClassLoader(),
				new Class<?>[] {WorkflowHandler.class},
				(proxy, method, args) -> {
					if (Objects.equals(method.getName(), "getClassName")) {
						return TaskNodeExecutorTest.class.getName();
					}

					if (Objects.equals(method.getName(), "getTitle")) {
						return StringPool.BLANK;
					}

					if (Objects.equals(method.getName(), "isScopeable")) {
						return false;
					}

					return null;
				}),
			HashMapDictionaryBuilder.put(
				"model.class.name=", TaskNodeExecutorTest.class.getName()
			).build());
	}

	@AfterClass
	public static void tearDownClass() {
		_workflowHandlerServiceRegistration.unregister();
	}

	@Before
	public void setUp() throws Exception {
		_serviceContext = ServiceContextTestUtil.getServiceContext();

		_workflowContext = HashMapBuilder.<String, Serializable>put(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME,
			TaskNodeExecutorTest.class.getName()
		).put(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_PK,
			String.valueOf(RandomTestUtil.randomLong())
		).put(
			WorkflowConstants.CONTEXT_NOTIFICATION_SENDER_ADDRESS,
			() -> {
				User user = TestPropsValues.getUser();

				return user.getEmailAddress();
			}
		).put(
			WorkflowConstants.CONTEXT_SERVICE_CONTEXT, _serviceContext
		).build();

		String content = StringUtil.read(
			getClass(), "dependencies/timer-tasks-workflow-definition.xml");

		_workflowDefinition =
			_workflowDefinitionManager.deployWorkflowDefinition(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				content.getBytes());

		_kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				_workflowDefinition.getCompanyId(),
				_workflowDefinition.getName());

		_kaleoInstance = _kaleoInstanceLocalService.addKaleoInstance(
			_kaleoDefinitionVersion.getKaleoDefinitionId(),
			_kaleoDefinitionVersion.getKaleoDefinitionVersionId(),
			RandomTestUtil.randomString(), _workflowDefinition.getVersion(),
			_workflowContext, _serviceContext);
	}

	@Test
	public void testExecuteTimerNotifications() throws Exception {
		KaleoTask kaleoTask = _getKaleoTask("Timer Notification");

		KaleoInstanceToken kaleoInstanceToken = _addKaleoInstanceToken(
			kaleoTask);

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_addKaleoTaskInstanceToken(kaleoInstanceToken, kaleoTask);

		KaleoTimerInstanceToken kaleoTimerInstanceToken =
			_kaleoTimerInstanceTokenLocalService.addKaleoTimerInstanceToken(
				kaleoInstanceToken.getKaleoInstanceTokenId(),
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
				_getKaleoTimerId(kaleoTask), RandomTestUtil.randomString(),
				_workflowContext, _serviceContext);

		_executeTimer(
			kaleoInstanceToken, kaleoTaskInstanceToken,
			kaleoTimerInstanceToken);

		MailMessage mailMessage = MailServiceTestUtil.getLastMailMessage();

		Assert.assertEquals(
			"Timer notification template", mailMessage.getBody());
	}

	@Test
	public void testExecuteTimerReassignments() throws Exception {
		KaleoTask kaleoTask = _getKaleoTask("Timer Reassignment");

		KaleoInstanceToken kaleoInstanceToken = _addKaleoInstanceToken(
			kaleoTask);

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_addKaleoTaskInstanceToken(kaleoInstanceToken, kaleoTask);

		KaleoTimerInstanceToken kaleoTimerInstanceToken =
			_kaleoTimerInstanceTokenLocalService.addKaleoTimerInstanceToken(
				kaleoInstanceToken.getKaleoInstanceTokenId(),
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
				_getKaleoTimerId(kaleoTask), RandomTestUtil.randomString(),
				_workflowContext, _serviceContext);

		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances =
			_kaleoTaskAssignmentInstanceLocalService.
				getKaleoTaskAssignmentInstances(
					kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());

		Assert.assertTrue(ListUtil.isEmpty(kaleoTaskAssignmentInstances));

		_executeTimer(
			kaleoInstanceToken, kaleoTaskInstanceToken,
			kaleoTimerInstanceToken);

		kaleoTaskAssignmentInstances =
			_kaleoTaskAssignmentInstanceLocalService.
				getKaleoTaskAssignmentInstances(
					kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());

		Assert.assertTrue(ListUtil.isNotEmpty(kaleoTaskAssignmentInstances));
		Assert.assertEquals(
			kaleoTaskAssignmentInstances.toString(), 1,
			kaleoTaskAssignmentInstances.size());

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			kaleoTaskAssignmentInstances.get(0);

		Assert.assertEquals(
			User.class.getName(),
			kaleoTaskAssignmentInstance.getAssigneeClassName());
		Assert.assertEquals(
			TestPropsValues.getUserId(),
			kaleoTaskAssignmentInstance.getAssigneeClassPK());
	}

	private KaleoInstanceToken _addKaleoInstanceToken(KaleoTask kaleoTask)
		throws Exception {

		return _kaleoInstanceTokenLocalService.addKaleoInstanceToken(
			kaleoTask.getKaleoNodeId(), _kaleoInstance.getKaleoDefinitionId(),
			_kaleoInstance.getKaleoDefinitionVersionId(),
			_kaleoInstance.getKaleoInstanceId(), 0, _workflowContext,
			_serviceContext);
	}

	private KaleoTaskInstanceToken _addKaleoTaskInstanceToken(
			KaleoInstanceToken kaleoInstanceToken, KaleoTask kaleoTask)
		throws Exception {

		return _kaleoTaskInstanceTokenLocalService.addKaleoTaskInstanceToken(
			kaleoInstanceToken.getKaleoInstanceTokenId(),
			kaleoTask.getKaleoTaskId(), kaleoTask.getName(),
			Collections.emptyList(), null, _workflowContext, _serviceContext);
	}

	private void _executeTimer(
			KaleoInstanceToken kaleoInstanceToken,
			KaleoTaskInstanceToken kaleoTaskInstanceToken,
			KaleoTimerInstanceToken kaleoTimerInstanceToken)
		throws Exception {

		Method executeTimerMethod = ReflectionUtil.getDeclaredMethod(
			_nodeExecutor.getClass(), "executeTimer", ExecutionContext.class);

		executeTimerMethod.invoke(
			_nodeExecutor,
			new ExecutionContext(
				kaleoInstanceToken, kaleoTimerInstanceToken, _workflowContext,
				_serviceContext) {

				{
					setKaleoTaskInstanceToken(kaleoTaskInstanceToken);
				}
			});
	}

	private KaleoTask _getKaleoTask(String taskName) throws Exception {
		return Stream.of(
			_kaleoNodeLocalService.getKaleoDefinitionVersionKaleoNodes(
				_kaleoDefinitionVersion.getKaleoDefinitionVersionId())
		).flatMap(
			List::stream
		).filter(
			kaleoNode -> Objects.equals(kaleoNode.getName(), taskName)
		).map(
			kaleoNode -> {
				try {
					return _kaleoTaskLocalService.getKaleoNodeKaleoTask(
						kaleoNode.getKaleoNodeId());
				}
				catch (PortalException portalException) {
				}

				return null;
			}
		).filter(
			Objects::nonNull
		).findFirst(
		).get();
	}

	private long _getKaleoTimerId(KaleoTask kaleoTask) {
		List<KaleoTimer> kaleoTimers = _kaleoTimerLocalService.getKaleoTimers(
			KaleoNode.class.getName(), kaleoTask.getKaleoNodeId());

		KaleoTimer kaleoTimer = kaleoTimers.get(0);

		return kaleoTimer.getKaleoTimerId();
	}

	private static ServiceRegistration<WorkflowHandler<?>>
		_workflowHandlerServiceRegistration;

	private KaleoDefinitionVersion _kaleoDefinitionVersion;

	@Inject
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	private KaleoInstance _kaleoInstance;

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Inject
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Inject
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Inject
	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

	@Inject
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Inject
	private KaleoTaskLocalService _kaleoTaskLocalService;

	@Inject
	private KaleoTimerInstanceTokenLocalService
		_kaleoTimerInstanceTokenLocalService;

	@Inject
	private KaleoTimerLocalService _kaleoTimerLocalService;

	@Inject(filter = "node.type=TASK")
	private NodeExecutor _nodeExecutor;

	private ServiceContext _serviceContext;
	private Map<String, Serializable> _workflowContext;
	private WorkflowDefinition _workflowDefinition;

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

}
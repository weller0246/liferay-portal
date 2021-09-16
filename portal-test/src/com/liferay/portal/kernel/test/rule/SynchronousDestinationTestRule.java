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

package com.liferay.portal.kernel.test.rule;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.increment.BufferedIncrementThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule.SyncHandler;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.junit.runner.Description;

/**
 * @author Miguel Pastor
 * @author Shuyang Zhou
 */
public class SynchronousDestinationTestRule
	extends AbstractTestRule<SyncHandler, SyncHandler> {

	public static final SynchronousDestinationTestRule INSTANCE =
		new SynchronousDestinationTestRule();

	@Override
	public void afterClass(Description description, SyncHandler syncHandler)
		throws Exception {

		if (syncHandler != null) {
			syncHandler.restorePreviousSync();
		}
	}

	@Override
	public void afterMethod(
		Description description, SyncHandler syncHandler, Object target) {

		if (syncHandler != null) {
			syncHandler.restorePreviousSync();
		}
	}

	@Override
	public SyncHandler beforeClass(Description description) throws Throwable {
		DependencyManagerSyncUtil.sync();

		Class<?> testClass = description.getTestClass();

		return _createSyncHandler(testClass.getAnnotation(Sync.class));
	}

	@Override
	public SyncHandler beforeMethod(Description description, Object target) {
		Class<?> testClass = description.getTestClass();

		Sync sync = testClass.getAnnotation(Sync.class);

		if (sync != null) {
			return null;
		}

		sync = description.getAnnotation(Sync.class);

		if (sync == null) {
			return null;
		}

		return _createSyncHandler(sync);
	}

	public static class SyncHandler {

		public BaseDestination createSynchronousDestination(
			String destinationName) {

			TestSynchronousDestination testSynchronousDestination = null;

			if ((_sync != null) && _sync.cleanTransaction()) {
				testSynchronousDestination =
					new CleanTransactionSynchronousDestination();
			}
			else {
				testSynchronousDestination = new TestSynchronousDestination();
			}

			testSynchronousDestination.setName(destinationName);

			return testSynchronousDestination;
		}

		public void enableSync() {
			Filter audioProcessorFilter = _registerDestinationFilter(
				DestinationNames.DOCUMENT_LIBRARY_AUDIO_PROCESSOR);
			Filter auditFilter = _registerDestinationFilter(
				DestinationNames.AUDIT);
			Filter asyncFilter = _registerDestinationFilter(
				DestinationNames.ASYNC_SERVICE);
			Filter backgroundTaskFilter = _registerDestinationFilter(
				DestinationNames.BACKGROUND_TASK);
			Filter backgroundTaskStatusFilter = _registerDestinationFilter(
				DestinationNames.BACKGROUND_TASK_STATUS);
			Filter commerceBasePriceListFilter = _registerDestinationFilter(
				DestinationNames.COMMERCE_BASE_PRICE_LIST);
			Filter commerceOrderFilter = _registerDestinationFilter(
				DestinationNames.COMMERCE_ORDER_STATUS);
			Filter commercePaymentFilter = _registerDestinationFilter(
				DestinationNames.COMMERCE_PAYMENT_STATUS);
			Filter commerceShipmentFilter = _registerDestinationFilter(
				DestinationNames.COMMERCE_SHIPMENT_STATUS);
			Filter commerceSubscriptionFilter = _registerDestinationFilter(
				DestinationNames.COMMERCE_SUBSCRIPTION_STATUS);
			Filter ddmStructureReindexFilter = _registerDestinationFilter(
				"liferay/ddm_structure_reindex");
			Filter mailFilter = _registerDestinationFilter(
				DestinationNames.MAIL);
			Filter pdfProcessorFilter = _registerDestinationFilter(
				DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR);
			Filter rawMetaDataProcessorFilter = _registerDestinationFilter(
				DestinationNames.DOCUMENT_LIBRARY_RAW_METADATA_PROCESSOR);
			Filter segmentsEntryReindexFilter = _registerDestinationFilter(
				"liferay/segments_entry_reindex");
			Filter subscrpitionSenderFilter = _registerDestinationFilter(
				DestinationNames.SUBSCRIPTION_SENDER);
			Filter tensorflowModelDownloadFilter = _registerDestinationFilter(
				"liferay/tensorflow_model_download");
			Filter videoProcessorFilter = _registerDestinationFilter(
				DestinationNames.DOCUMENT_LIBRARY_VIDEO_PROCESSOR);

			_waitForDependencies(
				audioProcessorFilter, auditFilter, asyncFilter,
				backgroundTaskFilter, backgroundTaskStatusFilter,
				commerceBasePriceListFilter, commerceOrderFilter,
				commercePaymentFilter, commerceShipmentFilter,
				commerceSubscriptionFilter, ddmStructureReindexFilter,
				mailFilter, pdfProcessorFilter, rawMetaDataProcessorFilter,
				segmentsEntryReindexFilter, subscrpitionSenderFilter,
				tensorflowModelDownloadFilter, videoProcessorFilter);

			_destinations = ReflectionTestUtil.getFieldValue(
				MessageBusUtil.getMessageBus(), "_destinations");

			_bufferedIncrementForceSyncSafeCloseable =
				BufferedIncrementThreadLocal.setWithSafeCloseable(true);
			_forceSyncSafeCloseable = ProxyModeThreadLocal.setWithSafeCloseable(
				true);

			replaceDestination(DestinationNames.AUDIT);
			replaceDestination(DestinationNames.ASYNC_SERVICE);
			replaceDestination(DestinationNames.BACKGROUND_TASK);
			replaceDestination(DestinationNames.BACKGROUND_TASK_STATUS);
			replaceDestination(DestinationNames.COMMERCE_BASE_PRICE_LIST);
			replaceDestination(DestinationNames.COMMERCE_ORDER_STATUS);
			replaceDestination(DestinationNames.COMMERCE_PAYMENT_STATUS);
			replaceDestination(DestinationNames.COMMERCE_SHIPMENT_STATUS);
			replaceDestination(DestinationNames.COMMERCE_SUBSCRIPTION_STATUS);
			replaceDestination(
				DestinationNames.DOCUMENT_LIBRARY_AUDIO_PROCESSOR);
			replaceDestination(DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR);
			replaceDestination(
				DestinationNames.DOCUMENT_LIBRARY_RAW_METADATA_PROCESSOR);
			replaceDestination(
				DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR);
			replaceDestination(
				DestinationNames.DOCUMENT_LIBRARY_VIDEO_PROCESSOR);
			replaceDestination(DestinationNames.MAIL);
			replaceDestination(DestinationNames.SCHEDULER_ENGINE);
			replaceDestination(DestinationNames.SUBSCRIPTION_SENDER);
			replaceDestination("liferay/adaptive_media_processor");
			replaceDestination("liferay/asset_auto_tagger");
			replaceDestination("liferay/ddm_structure_reindex");
			replaceDestination("liferay/report_request");
			replaceDestination("liferay/reports_admin");
			replaceDestination("liferay/segments_entry_reindex");
			replaceDestination("liferay/tensorflow_model_download");

			if (_sync != null) {
				for (String name : _sync.destinationNames()) {
					replaceDestination(name);
				}
			}

			for (String searchEngineId :
					SearchEngineHelperUtil.getSearchEngineIds()) {

				replaceDestination(
					SearchEngineHelperUtil.getSearchReaderDestinationName(
						searchEngineId));
				replaceDestination(
					SearchEngineHelperUtil.getSearchWriterDestinationName(
						searchEngineId));
			}

			Destination schedulerDestination = _destinations.get(
				DestinationNames.SCHEDULER_DISPATCH);

			if (schedulerDestination == null) {
				return;
			}

			for (MessageListener messageListener :
					schedulerDestination.getMessageListeners()) {

				InvokerMessageListener invokerMessageListener =
					(InvokerMessageListener)messageListener;

				MessageListener schedulerMessageListener =
					invokerMessageListener.getMessageListener();

				schedulerDestination.unregister(schedulerMessageListener);

				_schedulerInvokerMessageListeners.add(invokerMessageListener);
			}

			int workersMaxSize = ReflectionTestUtil.getFieldValue(
				schedulerDestination, "_workersMaxSize");

			CountDownLatch startCountDownLatch = new CountDownLatch(
				workersMaxSize);

			CountDownLatch endCountDownLatch = new CountDownLatch(1);

			Message countDownMessage = new Message();

			MessageListener messageListener = message -> {
				if (countDownMessage == message) {
					startCountDownLatch.countDown();

					try {
						endCountDownLatch.await();
					}
					catch (InterruptedException interruptedException) {
						ReflectionUtil.throwException(interruptedException);
					}
				}
			};

			schedulerDestination.register(messageListener);

			for (int i = 0; i < workersMaxSize; i++) {
				schedulerDestination.send(countDownMessage);
			}

			try {
				startCountDownLatch.await();
			}
			catch (InterruptedException interruptedException) {
				ReflectionUtil.throwException(interruptedException);
			}

			schedulerDestination.unregister(messageListener);

			endCountDownLatch.countDown();
		}

		public void replaceDestination(String destinationName) {
			Destination destination = _destinations.get(destinationName);

			boolean asyncDestination = false;

			if (destination != null) {
				try {
					ReflectionTestUtil.getField(
						destination.getClass(),
						"_noticeableThreadPoolExecutor");

					asyncDestination = true;
				}
				catch (Exception exception) {
				}
			}

			if (asyncDestination) {
				_asyncServiceDestinations.add(destination);

				Destination synchronousDestination =
					createSynchronousDestination(destinationName);

				destination.copyDestinationEventListeners(
					synchronousDestination);
				destination.copyMessageListeners(synchronousDestination);

				_destinations.put(destinationName, synchronousDestination);
			}

			if (destination == null) {
				_absentDestinationNames.add(destinationName);

				_destinations.put(
					destinationName,
					createSynchronousDestination(destinationName));
			}
		}

		public void restorePreviousSync() {
			if (_bufferedIncrementForceSyncSafeCloseable != null) {
				_bufferedIncrementForceSyncSafeCloseable.close();
			}

			if (_forceSyncSafeCloseable != null) {
				_forceSyncSafeCloseable.close();
			}

			for (Destination destination : _asyncServiceDestinations) {
				_destinations.put(destination.getName(), destination);
			}

			_asyncServiceDestinations.clear();

			for (String absentDestinationName : _absentDestinationNames) {
				_destinations.remove(absentDestinationName);
			}

			Destination destination = _destinations.get(
				DestinationNames.SCHEDULER_DISPATCH);

			if (destination == null) {
				return;
			}

			for (InvokerMessageListener invokerMessageListener :
					_schedulerInvokerMessageListeners) {

				destination.register(
					invokerMessageListener.getMessageListener(),
					invokerMessageListener.getClassLoader());
			}
		}

		/**
		 * @deprecated As of Mueller (7.2.x), with no direct replacement
		 */
		@Deprecated
		public void setForceSync(boolean forceSync) {
		}

		public void setSync(Sync sync) {
			_sync = sync;
		}

		private Filter _registerDestinationFilter(String destinationName) {
			Registry registry = RegistryUtil.getRegistry();

			return registry.getFilter(
				StringBundler.concat(
					"(&(destination.name=", destinationName, ")(objectClass=",
					Destination.class.getName(), "))"));
		}

		private void _waitForDependencies(Filter... filters) {
			Registry registry = RegistryUtil.getRegistry();

			for (Filter filter : filters) {
				ServiceTracker<Object, Object> serviceTracker =
					registry.trackServices(filter);

				serviceTracker.open();

				while (true) {
					try {
						Object service = serviceTracker.waitForService(2000);

						if (service != null) {
							serviceTracker.close();

							break;
						}

						System.out.println(
							"Waiting for destination " + filter.toString());
					}
					catch (InterruptedException interruptedException) {
						System.out.println(
							StringBundler.concat(
								"Stopped waiting for destination ",
								filter.toString(), " due to interruption"));

						return;
					}
				}
			}
		}

		private final List<String> _absentDestinationNames = new ArrayList<>();
		private final List<Destination> _asyncServiceDestinations =
			new ArrayList<>();
		private SafeCloseable _bufferedIncrementForceSyncSafeCloseable;
		private Map<String, Destination> _destinations;
		private SafeCloseable _forceSyncSafeCloseable;
		private final List<InvokerMessageListener>
			_schedulerInvokerMessageListeners = new ArrayList<>();
		private Sync _sync;

	}

	public static class TestSynchronousDestination extends BaseDestination {

		@Override
		public void send(Message message) {
			for (MessageListener messageListener : messageListeners) {
				try {
					messageListener.receive(message);
				}
				catch (MessageListenerException messageListenerException) {
					_log.error(
						"Unable to process message " + message,
						messageListenerException);
				}
			}
		}

		private static final Log _log = LogFactoryUtil.getLog(
			TestSynchronousDestination.class);

	}

	protected SynchronousDestinationTestRule() {
	}

	private SyncHandler _createSyncHandler(Sync sync) {
		SyncHandler syncHandler = new SyncHandler();

		syncHandler.setSync(sync);

		syncHandler.enableSync();

		return syncHandler;
	}

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.NOT_SUPPORTED);
		builder.setRollbackForClasses(
			PortalException.class, SystemException.class);

		_transactionConfig = builder.build();
	}

	private static class CleanTransactionSynchronousDestination
		extends TestSynchronousDestination {

		@Override
		public void send(final Message message) {
			try {
				TransactionInvokerUtil.invoke(
					_transactionConfig,
					new Callable<Void>() {

						@Override
						public Void call() throws Exception {
							CleanTransactionSynchronousDestination.super.send(
								message);

							return null;
						}

					});
			}
			catch (Throwable throwable) {
				throw new RuntimeException(throwable);
			}
		}

	}

}
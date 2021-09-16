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

package com.liferay.portal.kernel.messaging;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public abstract class BaseDestination implements Destination {

	@Override
	public boolean addDestinationEventListener(
		DestinationEventListener destinationEventListener) {

		return _destinationEventListeners.add(destinationEventListener);
	}

	public void afterPropertiesSet() {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException("Name is null");
		}
	}

	@Override
	public void close() {
		close(false);
	}

	@Override
	public void close(boolean force) {
	}

	@Override
	public void copyDestinationEventListeners(Destination destination) {
		for (DestinationEventListener destinationEventListener :
				_destinationEventListeners) {

			destination.addDestinationEventListener(destinationEventListener);
		}
	}

	@Override
	public void copyMessageListeners(Destination destination) {
		for (MessageListener messageListener : messageListeners) {
			InvokerMessageListener invokerMessageListener =
				(InvokerMessageListener)messageListener;

			destination.register(
				invokerMessageListener.getMessageListener(),
				invokerMessageListener.getClassLoader());
		}
	}

	@Override
	public void destroy() {
		close(true);

		removeDestinationEventListeners();

		unregisterMessageListeners();
	}

	@Override
	public DestinationStatistics getDestinationStatistics() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDestinationType() {
		return _destinationType;
	}

	@Override
	public int getMessageListenerCount() {
		return messageListeners.size();
	}

	@Override
	public Set<MessageListener> getMessageListeners() {
		return Collections.unmodifiableSet(messageListeners);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Set<WebhookEvent> getWebhookEvents() {
		return _webhookEvents;
	}

	@Override
	public boolean isRegistered() {
		if (getMessageListenerCount() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isWebhookCapable(long companyId) {
		if ((_webhookRequiredCompanyId == 0) ||
			(_webhookRequiredCompanyId == companyId)) {

			return true;
		}

		return false;
	}

	@Override
	public void open() {
	}

	@Override
	public boolean register(MessageListener messageListener) {
		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener);

		return registerMessageListener(invokerMessageListener);
	}

	@Override
	public boolean register(
		MessageListener messageListener, ClassLoader classLoader) {

		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener, classLoader);

		return registerMessageListener(invokerMessageListener);
	}

	@Override
	public boolean removeDestinationEventListener(
		DestinationEventListener destinationEventListener) {

		return _destinationEventListeners.remove(destinationEventListener);
	}

	@Override
	public void removeDestinationEventListeners() {
		_destinationEventListeners.clear();
	}

	@Override
	public void send(Message message) {
		throw new UnsupportedOperationException();
	}

	public void setDestinationType(String destinationType) {
		_destinationType = destinationType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProperties(Map<String, Object> properties) {
		long webhookRequiredCompanyId = MapUtil.getLong(
			properties, "destination.webhook.required.company.id", -1);

		if (webhookRequiredCompanyId < 0) {
			return;
		}

		_webhookRequiredCompanyId = webhookRequiredCompanyId;

		String[] keys = StringUtil.split(
			MapUtil.getString(properties, "destination.webhook.event.keys"));

		for (String key : keys) {
			_webhookEvents.add(
				new WebhookEvent(
					MapUtil.getString(
						properties,
						"destination.webhook.event.description[" + key + "]"),
					key,
					MapUtil.getString(
						properties,
						"destination.webhook.event.name[" + key + "]")));
		}
	}

	@Override
	public boolean unregister(MessageListener messageListener) {
		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener);

		return unregisterMessageListener(invokerMessageListener);
	}

	public boolean unregister(
		MessageListener messageListener, ClassLoader classLoader) {

		InvokerMessageListener invokerMessageListener =
			new InvokerMessageListener(messageListener, classLoader);

		return unregisterMessageListener(invokerMessageListener);
	}

	@Override
	public void unregisterMessageListeners() {
		for (MessageListener messageListener : messageListeners) {
			unregisterMessageListener((InvokerMessageListener)messageListener);
		}
	}

	protected void fireMessageListenerRegisteredEvent(
		MessageListener messageListener) {

		for (DestinationEventListener destinationEventListener :
				_destinationEventListeners) {

			destinationEventListener.messageListenerRegistered(
				getName(), messageListener);
		}
	}

	protected void fireMessageListenerUnregisteredEvent(
		MessageListener messageListener) {

		for (DestinationEventListener listener : _destinationEventListeners) {
			listener.messageListenerUnregistered(getName(), messageListener);
		}
	}

	protected boolean registerMessageListener(
		InvokerMessageListener invokerMessageListener) {

		boolean registered = messageListeners.add(invokerMessageListener);

		if (registered) {
			fireMessageListenerRegisteredEvent(
				invokerMessageListener.getMessageListener());
		}

		return registered;
	}

	protected boolean unregisterMessageListener(
		InvokerMessageListener invokerMessageListener) {

		boolean unregistered = messageListeners.remove(invokerMessageListener);

		if (unregistered) {
			fireMessageListenerUnregisteredEvent(
				invokerMessageListener.getMessageListener());
		}

		return unregistered;
	}

	protected Set<MessageListener> messageListeners = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	protected String name = StringPool.BLANK;

	private final Set<DestinationEventListener> _destinationEventListeners =
		Collections.newSetFromMap(new ConcurrentHashMap<>());
	private String _destinationType;

	private final Set<WebhookEvent> _webhookEvents = new TreeSet<>(
		new Comparator<WebhookEvent>() {

			public int compare(
				WebhookEvent webhookEvent1, WebhookEvent webhookEvent2) {

				String key1 = webhookEvent1.getKey();
				String key2 = webhookEvent2.getKey();

				return key1.compareTo(key2);
			}

		});

	private long _webhookRequiredCompanyId = -1;

}
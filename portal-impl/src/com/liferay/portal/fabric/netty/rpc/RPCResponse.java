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

package com.liferay.portal.fabric.netty.rpc;

import com.liferay.petra.concurrent.AsyncBroker;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.fabric.netty.handlers.NettyChannelAttributes;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class RPCResponse<T extends Serializable> extends RPCSerializable {

	public RPCResponse(
		long id, boolean cancelled, T result, Throwable throwable) {

		super(id);

		_cancelled = cancelled;
		_result = result;
		_throwable = throwable;
	}

	@Override
	public void execute(Channel channel) {
		AsyncBroker<Long, Serializable> asyncBroker =
			NettyChannelAttributes.getAsyncBroker(channel);

		if (_cancelled) {
			NoticeableFuture<?> noticeableFuture = asyncBroker.take(id);

			if (noticeableFuture == null) {
				_log.error(
					"Unable to place cancellation because no future exists " +
						"with ID " + id);
			}
			else if (noticeableFuture.cancel(true)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Cancelled future with ID " + id);
				}
			}
			else if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to cancel future with ID " + id +
						" because it is already completed");
			}
		}
		else if (_throwable != null) {
			if (!asyncBroker.takeWithException(id, _throwable)) {
				_log.error(
					"Unable to place exception because no future exists with " +
						"ID " + id,
					_throwable);
			}
		}
		else {
			if (!asyncBroker.takeWithResult(id, _result)) {
				_log.error(
					StringBundler.concat(
						"Unable to place result ", _result,
						" because no future exists with ID ", id));
			}
		}
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{cancelled=", _cancelled, ", id=", id, ", result=", _result,
			", throwable=", _throwable, "}");
	}

	private static final Log _log = LogFactoryUtil.getLog(RPCResponse.class);

	private static final long serialVersionUID = 1L;

	private final boolean _cancelled;
	private final T _result;
	private final Throwable _throwable;

}
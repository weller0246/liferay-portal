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

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.query.PrefixQuery;
import com.liferay.portal.search.query.QueryVisitor;

/**
 * @author Michael C. Han
 */
public class PrefixQueryImpl extends BaseQueryImpl implements PrefixQuery {

	public PrefixQueryImpl(String field, String prefix) {
		_field = field;
		_prefix = prefix;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public String getPrefix() {
		return _prefix;
	}

	@Override
	public String getRewrite() {
		return _rewrite;
	}

	@Override
	public void setRewrite(String rewrite) {
		_rewrite = rewrite;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{(", _field, "=", _prefix, ", _rewrite=", _rewrite, "), ",
			super.toString(), "}");
	}

	private static final long serialVersionUID = 1L;

	private final String _field;
	private final String _prefix;
	private String _rewrite;

}
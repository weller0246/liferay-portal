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

package com.liferay.segments.asah.connector.internal.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.string.StringBundler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Kong
 * @author David Arques
 */
public class IndividualSegment {

	public Author getAuthor() {
		return _author;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	@JsonProperty("_embedded")
	public Map<String, Object> getEmbeddedResources() {
		return _embeddedResources;
	}

	public String getFilter() {
		return _filter;
	}

	public String getFilterMetadata() {
		return _filterMetadata;
	}

	public String getId() {
		return _id;
	}

	public long getIndividualCount() {
		return _individualCount;
	}

	public String getName() {
		return _name;
	}

	public String getScope() {
		return _scope;
	}

	public String getSegmentType() {
		return _segmentType;
	}

	public String getState() {
		return _state;
	}

	public String getStatus() {
		return _status;
	}

	public void setAuthor(Author author) {
		_author = author;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setEmbeddedResources(Map<String, Object> embeddedResources) {
		_embeddedResources = embeddedResources;
	}

	public void setFilter(String filter) {
		_filter = filter;
	}

	public void setFilterMetadata(String filterMetadata) {
		_filterMetadata = filterMetadata;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIndividualCount(long individualCount) {
		_individualCount = individualCount;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setScope(String scope) {
		_scope = scope;
	}

	public void setSegmentType(String segmentType) {
		_segmentType = segmentType;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setStatus(String status) {
		_status = status;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{author=", _author, ", dateCreated=", _dateCreated,
			", dateModified=", _dateModified, ", embeddedResources=",
			_embeddedResources, ", filter=", _filter, ", filterMetadata=",
			_filterMetadata, ", id=", _id, ", individualCount=",
			_individualCount, ", name=", _name, ", scope=", _scope,
			", segmentType=", _segmentType, ", state=", _state, ", status=",
			_status, "}");
	}

	public enum Scope {

		PROJECT, USER

	}

	public enum State {

		IN_PROGRESS, READY

	}

	public enum Status {

		ACTIVE, INACTIVE

	}

	public enum Type {

		DYNAMIC, STATIC

	}

	private Author _author;
	private Date _dateCreated;
	private Date _dateModified;
	private Map<String, Object> _embeddedResources = new HashMap<>();
	private String _filter;
	private String _filterMetadata;
	private String _id;
	private long _individualCount;
	private String _name;
	private String _scope = Scope.PROJECT.name();
	private String _segmentType = Type.STATIC.name();
	private String _state = State.READY.name();
	private String _status = Status.ACTIVE.name();

}
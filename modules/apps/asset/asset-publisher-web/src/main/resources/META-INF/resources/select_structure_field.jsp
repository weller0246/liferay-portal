<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");
String className = ParamUtil.getString(request, "className");
long classTypeId = ParamUtil.getLong(request, "classTypeId");
String ddmStructureFieldName = ParamUtil.getString(request, "ddmStructureFieldName");
Serializable ddmStructureFieldValue = ParamUtil.getString(request, "ddmStructureFieldValue");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectDDMStructureField");

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

ClassTypeReader classTypeReader = assetRendererFactory.getClassTypeReader();

ClassType classType = classTypeReader.getClassType(classTypeId, locale);
%>

<div class="alert alert-danger hide" id="<portlet:namespace />message">
	<span class="error-message"><liferay-ui:message key="the-field-value-is-invalid" /></span>
</div>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "selectDDMStructureFieldForm" %>'
>
	<liferay-ui:search-container
		iteratorURL='<%=
			PortletURLBuilder.createRenderURL(
				renderResponse
			).setMVCPath(
				"/select_structure_field.jsp"
			).setPortletResource(
				portletResource
			).setParameter(
				"className", className
			).setParameter(
				"classTypeId", classTypeId
			).setParameter(
				"eventName", eventName
			).buildPortletURL()
		%>'
		total="<%= classType.getClassTypeFieldsCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= classType.getClassTypeFields(searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.ClassTypeField"
			modelVar="field"
		>

			<%
			String label = field.getLabel();
			String name = field.getName();
			long ddmStructureId = field.getClassTypeId();
			%>

			<liferay-ui:search-container-column-text>
				<input data-button-id="<%= liferayPortletResponse.getNamespace() + "applyButton" + name %>" data-form-id="<%= liferayPortletResponse.getNamespace() + name + "fieldForm" %>" name="<portlet:namespace />selectStructureFieldSubtype" type="radio" <%= name.equals(ddmStructureFieldName) ? "checked" : StringPool.BLANK %> />
			</liferay-ui:search-container-column-text>

			<%
			String fieldsNamespace = StringUtil.randomId();
			%>

			<liferay-ui:search-container-column-text
				name="field"
			>
				<liferay-portlet:resourceURL id="getFieldValue" portletConfiguration="<%= true %>" var="structureFieldURL">
					<portlet:param name="portletResource" value="<%= portletResource %>" />
					<portlet:param name="structureId" value="<%= String.valueOf(ddmStructureId) %>" />
					<portlet:param name="name" value="<%= name %>" />
					<portlet:param name="fieldsNamespace" value="<%= fieldsNamespace %>" />
				</liferay-portlet:resourceURL>

				<aui:form action="<%= structureFieldURL %>" disabled="<%= !name.equals(ddmStructureFieldName) %>" name='<%= name + "fieldForm" %>' onSubmit="event.preventDefault()">
					<aui:input disabled="<%= true %>" name="buttonId" type="hidden" value='<%= liferayPortletResponse.getNamespace() + "applyButton" + name %>' />

					<%
					com.liferay.dynamic.data.mapping.storage.Field ddmField = new com.liferay.dynamic.data.mapping.storage.Field();

					ddmField.setDefaultLocale(themeDisplay.getLocale());
					ddmField.setDDMStructureId(ddmStructureId);
					ddmField.setName(name);

					if (name.equals(ddmStructureFieldName)) {
						ddmField.setValue(themeDisplay.getLocale(), ddmStructureFieldValue);
					}
					%>

					<liferay-ddm:html-field
						classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
						classPK="<%= ddmStructureId %>"
						field="<%= ddmField %>"
						fieldsNamespace="<%= fieldsNamespace %>"
					/>
				</aui:form>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text>
				<aui:button
					cssClass="selector-button"
					data='<%=
						HashMapBuilder.<String, Object>put(
							"fieldsnamespace", fieldsNamespace
						).put(
							"form", liferayPortletResponse.getNamespace() + name + "fieldForm"
						).put(
							"label", label
						).put(
							"name", name
						).put(
							"value",
							JSONUtil.put(
								"ddmStructureFieldName", ddmStructureFieldName
							).put(
								"ddmStructureFieldValue", ddmStructureFieldValue
							)
						).build()
					%>'
					disabled="<%= name.equals(ddmStructureFieldName) ? false : true %>"
					id='<%= "applyButton" + name %>'
					value="apply"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "selectStructureField" %>'
	context='<%=
		HashMapBuilder.<String, Object>put(
			"assetClassName", assetPublisherWebHelper.getClassName(assetRendererFactory)
		).put(
			"eventName", HtmlUtil.escapeJS(eventName)
		).build()
	%>'
	module="js/SelectStructureField"
/>
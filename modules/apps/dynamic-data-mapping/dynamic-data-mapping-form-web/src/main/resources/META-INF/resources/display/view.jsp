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

<%@ include file="/display/init.jsp" %>

<%
long formInstanceId = ddmFormDisplayContext.getFormInstanceId();
%>

<c:choose>
	<c:when test="<%= formInstanceId == 0 %>">
		<div class="alert alert-info">
			<liferay-ui:message key="select-an-existing-form-or-add-a-form-to-be-displayed-in-this-application" />
		</div>
	</c:when>
	<c:when test="<%= !ddmFormDisplayContext.hasAddFormInstanceRecordPermission() && !ddmFormDisplayContext.hasViewPermission() %>">
		<div class="ddm-form-basic-info">
			<clay:container-fluid>
				<clay:alert
					displayType="warning"
					message="you-do-not-have-the-permission-to-view-this-form"
				/>
			</clay:container-fluid>
		</div>
	</c:when>
	<c:when test="<%= ddmFormDisplayContext.isRequireAuthentication() %>">
		<div class="ddm-form-basic-info">
			<clay:container-fluid>
				<clay:alert
					displayType="warning"
					message="you-need-to-be-signed-in-to-view-this-form"
				/>
			</clay:container-fluid>
		</div>
	</c:when>
	<c:otherwise>

		<%
		String languageId = ddmFormDisplayContext.getDefaultLanguageId();

		Locale displayLocale = LocaleUtil.fromLanguageId(languageId);
		%>

		<c:choose>
			<c:when test="<%= ddmFormDisplayContext.isShowSuccessPage() %>">

				<%
				DDMFormSuccessPageSettings ddmFormSuccessPageSettings = ddmFormDisplayContext.getDDMFormSuccessPageSettings();

				LocalizedValue title = ddmFormSuccessPageSettings.getTitle();
				LocalizedValue body = ddmFormSuccessPageSettings.getBody();
				%>

				<div class="portlet-forms">
					<div class="ddm-form-basic-info ddm-form-success-page">
						<clay:container-fluid>
							<h1 class="ddm-form-name"><%= HtmlUtil.escape(GetterUtil.getString(title.getString(displayLocale), title.getString(title.getDefaultLocale()))) %></h1>

							<p class="ddm-form-description"><%= HtmlUtil.escape(GetterUtil.getString(body.getString(displayLocale), body.getString(body.getDefaultLocale()))) %></p>
						</clay:container-fluid>
					</div>
				</div>
			</c:when>
			<c:when test="<%= ddmFormDisplayContext.isFormAvailable() %>">
				<portlet:actionURL name="/dynamic_data_mapping_form/add_form_instance_record" var="addFormInstanceRecordActionURL" />

				<%
				DDMFormInstance formInstance = ddmFormDisplayContext.getFormInstance();
				%>

				<div class="portlet-forms">
					<aui:form action="<%= addFormInstanceRecordActionURL %>" data-DDMFormInstanceId="<%= formInstanceId %>" data-senna-off="true" method="post" name="fm">
						<aui:input name="currentURL" type="hidden" value="<%= currentURL %>" />

						<%
						String redirectURL = ddmFormDisplayContext.getRedirectURL();
						%>

						<c:if test="<%= Validator.isNull(redirectURL) %>">
							<aui:input name="redirect" type="hidden" value='<%= ParamUtil.getString(request, "redirect", currentURL) %>' />
						</c:if>

						<aui:input name="groupId" type="hidden" value="<%= formInstance.getGroupId() %>" />
						<aui:input name="formInstanceId" type="hidden" value="<%= formInstance.getFormInstanceId() %>" />
						<aui:input name="languageId" type="hidden" value="<%= languageId %>" />
						<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

						<liferay-ui:error exception="<%= CaptchaException.class %>" message="captcha-verification-failed" />
						<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
						<liferay-ui:error exception="<%= DDMFormRenderingException.class %>" message="unable-to-render-the-selected-form" />
						<liferay-ui:error exception="<%= DDMFormValuesValidationException.class %>" message="field-validation-failed" />

						<liferay-ui:error exception="<%= DDMFormValuesValidationException.MustSetValidValue.class %>">

							<%
							DDMFormValuesValidationException.MustSetValidValue msvv = (DDMFormValuesValidationException.MustSetValidValue)errorException;
							%>

							<liferay-ui:message arguments="<%= HtmlUtil.escape(msvv.getFieldName()) %>" key="validation-failed-for-field-x" translateArguments="<%= false %>" />
						</liferay-ui:error>

						<liferay-ui:error exception="<%= DDMFormValuesValidationException.RequiredValue.class %>">

							<%
							DDMFormValuesValidationException.RequiredValue rv = (DDMFormValuesValidationException.RequiredValue)errorException;
							%>

							<liferay-ui:message arguments="<%= HtmlUtil.escape(rv.getFieldName()) %>" key="no-value-is-defined-for-field-x" translateArguments="<%= false %>" />
						</liferay-ui:error>

						<liferay-ui:error exception="<%= NoSuchFormInstanceException.class %>" message="the-selected-form-no-longer-exists" />
						<liferay-ui:error exception="<%= NoSuchStructureException.class %>" message="unable-to-retrieve-the-definition-of-the-selected-form" />
						<liferay-ui:error exception="<%= NoSuchStructureLayoutException.class %>" message="unable-to-retrieve-the-layout-of-the-selected-form" />
						<liferay-ui:error exception="<%= StorageException.class %>" message="there-was-an-error-when-accessing-the-data-storage" />

						<liferay-ui:error-principal />

						<c:if test="<%= ddmFormDisplayContext.isFormShared() || ddmFormDisplayContext.isPreview() %>">
							<clay:container-fluid>
								<div class="locale-actions">
									<liferay-ui:language
										formAction="<%= currentURL %>"
										languageId="<%= languageId %>"
										languageIds="<%= ddmFormDisplayContext.getAvailableLanguageIds() %>"
									/>
								</div>
							</clay:container-fluid>
						</c:if>

						<c:if test="<%= !ddmFormDisplayContext.hasAddFormInstanceRecordPermission() %>">
							<div class="ddm-form-basic-info">
								<clay:container-fluid>
									<clay:alert
										displayType="warning"
										message="you-do-not-have-the-permission-to-submit-this-form"
									/>
								</clay:container-fluid>
							</div>
						</c:if>

						<c:if test="<%= !ddmFormDisplayContext.hasValidStorageType(formInstance) %>">
							<div class="ddm-form-basic-info">
								<clay:container-fluid>
									<clay:alert
										displayType="danger"
										message='<%= LanguageUtil.format(request, "this-form-was-created-using-a-storage-type-x-that-is-not-available-for-this-liferay-dxp-installation.-install-x-to-make-it-available-for-editing", formInstance.getStorageType()) %>'
									/>
								</clay:container-fluid>
							</div>
						</c:if>

						<div class="ddm-form-upload-permission-message hide mt-4">
							<clay:container-fluid>
								<clay:alert
									displayType="warning"
									message="you-do-not-have-the-permission-to-access-the-upload-fields-on-this-form"
								/>
							</clay:container-fluid>
						</div>

						<div class="ddm-form-basic-info">
							<clay:container-fluid>
								<h1 class="ddm-form-name"><%= HtmlUtil.escape(formInstance.getName(displayLocale)) %></h1>

								<%
								String description = StringUtil.trim(HtmlUtil.escape(formInstance.getDescription(displayLocale)));
								%>

								<c:if test="<%= Validator.isNotNull(description) %>">
									<p class="ddm-form-description"><%= HtmlUtil.replaceNewLine(description) %></p>
								</c:if>
							</clay:container-fluid>
						</div>

						<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/dynamic_data_mapping_form/validate_csrf_token" var="validateCSRFTokenURL" />

						<clay:container-fluid
							cssClass="ddm-form-builder-app ddm-form-builder-app-not-ready"
							id="<%= ddmFormDisplayContext.getContainerId() %>"
						>
							<react:component
								module="admin/js/FormView.link.es"
								props='<%=
									HashMapBuilder.<String, Object>put(
										"validateCSRFTokenURL", validateCSRFTokenURL.toString()
									).putAll(
										ddmFormDisplayContext.getDDMFormContext()
									).build()
								%>'
							/>
						</clay:container-fluid>

						<aui:input name="empty" type="hidden" value="" />
					</aui:form>
				</div>

				<aui:script use="aui-base">
					function <portlet:namespace />clearInterval(intervalId) {
						if (intervalId) {
							clearInterval(intervalId);
						}
					}

					var <portlet:namespace />intervalId;

					function <portlet:namespace />clearPortletHandlers(event) {
						<portlet:namespace />clearInterval(<portlet:namespace />intervalId);

						Liferay.detach('destroyPortlet', <portlet:namespace />clearPortletHandlers);
					}

					Liferay.on('destroyPortlet', <portlet:namespace />clearPortletHandlers);

					<c:if test="<%= ddmFormDisplayContext.isFormShared() %>">
						document.title =
							'<%= HtmlUtil.escapeJS(formInstance.getName(displayLocale)) %>';
					</c:if>

					function <portlet:namespace />fireFormView() {
						Liferay.fire('ddmFormView', {
							formId: '<%= formInstanceId %>',
							title: '<%= HtmlUtil.escape(formInstance.getName(displayLocale)) %>',
						});
					}

					<c:choose>
						<c:when test="<%= ddmFormDisplayContext.isAutosaveEnabled() %>">
							var <portlet:namespace />form;

							<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/dynamic_data_mapping_form/add_form_instance_record" var="autoSaveFormInstanceRecordURL">
								<portlet:param name="autoSave" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="languageId" value="<%= languageId %>" />
								<portlet:param name="preview" value="<%= String.valueOf(ddmFormDisplayContext.isPreview()) %>" />
							</liferay-portlet:resourceURL>

							Liferay.on('sessionExpired', (event) => {
								<portlet:namespace />clearInterval(<portlet:namespace />intervalId);
							});

							function <portlet:namespace />autoSave() {
								var form = <portlet:namespace />form;
								var isRendered = form.reactComponentRef && form.reactComponentRef.current;
								var data = new URLSearchParams({
									<portlet:namespace />formInstanceId: <%= formInstanceId %>,
									<portlet:namespace />serializedDDMFormValues: JSON.stringify(
										isRendered ? form.reactComponentRef.current.toJSON() : {}
									),
								});

								Liferay.Util.fetch('<%= autoSaveFormInstanceRecordURL.toString() %>', {
									body: data,
									method: 'POST',
								});
							}

							function <portlet:namespace />startAutoSave() {
								<portlet:namespace />clearInterval(<portlet:namespace />intervalId);

								<portlet:namespace />intervalId = setInterval(
									<portlet:namespace />autoSave,
									<%= ddmFormDisplayContext.getAutosaveInterval() %>
								);
							}
						</c:when>
						<c:otherwise>
							function <portlet:namespace />startAutoExtendSession() {
								<portlet:namespace />clearInterval(<portlet:namespace />intervalId);

								var tenSeconds = 10000;

								var time = Liferay.Session.get('sessionLength') || tenSeconds;

								<portlet:namespace />intervalId = setInterval(
									<portlet:namespace />extendSession,
									time / 2
								);
							}

							function <portlet:namespace />extendSession() {
								Liferay.Session.extend();
							}
						</c:otherwise>
					</c:choose>

					function <portlet:namespace />enableForm() {
						var container = document.querySelector(
							'#<%= ddmFormDisplayContext.getContainerId() %>'
						);

						container.classList.remove('ddm-form-builder-app-not-ready');
					}

					function <portlet:namespace />initForm() {
						<portlet:namespace />enableForm();
						<portlet:namespace />fireFormView();

						<c:choose>
							<c:when test="<%= ddmFormDisplayContext.isAutosaveEnabled() %>">
								var container = document.querySelector(
									'#<%= ddmFormDisplayContext.getContainerId() %>'
								);

								container.onclick = function (event) {
									<portlet:namespace />startAutoSave();

									container.onclick = null;
									container.onkeypress = null;
								};

								container.onkeypress = function (event) {
									<portlet:namespace />startAutoSave();

									container.onclick = null;
									container.onkeypress = null;
								};
							</c:when>
							<c:otherwise>
								<portlet:namespace />startAutoExtendSession();
							</c:otherwise>
						</c:choose>
					}

					<c:if test="<%= ddmFormDisplayContext.isRememberMe() %>">
						var rememberMe = true;
					</c:if>

					<portlet:namespace />sessionIntervalId = setInterval(() => {
						if (Liferay.Session || rememberMe) {
							clearInterval(<portlet:namespace />sessionIntervalId);

							<portlet:namespace />form = Liferay.component(
								'<%= ddmFormDisplayContext.getContainerId() %>'
							);

							if (<portlet:namespace />form) {
								<portlet:namespace />initForm();
							}
							else {
								Liferay.componentReady(
									'<%= ddmFormDisplayContext.getContainerId() %>'
								).then((component) => {
									<portlet:namespace />form = component;

									if (component) {
										<portlet:namespace />initForm();
									}
								});
							}
						}
					}, 1000);
				</aui:script>
			</c:when>
			<c:otherwise>
				<div class="alert alert-warning">
					<liferay-ui:message key="this-form-not-available-or-it-was-not-published" />
				</div>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<c:if test="<%= ddmFormDisplayContext.isShowConfigurationIcon() %>">
	<div class="c-mt-3 icons-container">
		<div class="btn-group lfr-icon-actions">
			<liferay-ui:icon
				icon="cog"
				label="<%= true %>"
				markupView="lexicon"
				message="select-form"
				method="get"
				onClick="<%= portletDisplay.getURLConfigurationJS() %>"
				url="<%= portletDisplay.getURLConfiguration() %>"
			/>
		</div>
	</div>
</c:if>
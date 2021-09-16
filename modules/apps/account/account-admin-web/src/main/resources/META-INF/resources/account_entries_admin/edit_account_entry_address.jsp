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
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

long accountEntryAddressId = ParamUtil.getLong(renderRequest, "accountEntryAddressId");

Address address = AddressLocalServiceUtil.fetchAddress(accountEntryAddressId);

String defaultType = ParamUtil.getString(request, "defaultType");

AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((accountEntryAddressId == 0) ? LanguageUtil.get(request, "add-address") : LanguageUtil.get(request, "edit-address"));
%>

<portlet:actionURL name="/account_admin/edit_account_entry_address" var="editAccountEntryAddressURL" />

<liferay-frontend:edit-form
	action="<%= editAccountEntryAddressURL %>"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (accountEntryAddressId == 0) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= backURL %>" />
	<aui:input name="accountEntryAddressId" type="hidden" value="<%= accountEntryAddressId %>" />
	<aui:input name="accountEntryId" type="hidden" value="<%= accountEntryDisplay.getAccountEntryId() %>" />
	<aui:input name="defaultType" type="hidden" value="<%= defaultType %>" />

	<liferay-frontend:edit-form-body>
		<aui:model-context bean="<%= address %>" model="<%= Address.class %>" />

		<aui:input name="name" />

		<aui:input name="description" type="textarea" />

		<aui:select label="type" name="addressTypeId">

			<%
			String[] types = null;

			if (Objects.equals("billing", defaultType) || Objects.equals("shipping", defaultType)) {
				types = new String[] {defaultType, AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING_AND_SHIPPING};
			}
			else {
				types = new String[] {AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING_AND_SHIPPING, AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING, AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_SHIPPING};
			}

			for (String type : types) {
				ListType listType = ListTypeLocalServiceUtil.getListType(type, AccountEntry.class.getName() + ListTypeConstants.ADDRESS);
			%>

				<aui:option label="<%= LanguageUtil.get(request, type) %>" value="<%= listType.getListTypeId() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input name="street1" required="<%= true %>" />

		<aui:input name="street2" />

		<aui:input name="street3" />

		<div class="form-group-autofit">
			<div class="form-group-item">
				<aui:input name="city" required="<%= true %>" />
			</div>

			<div class="form-group-item">
				<aui:select label="region" name="addressRegionId" />
			</div>
		</div>

		<div class="form-group-autofit">
			<div class="form-group-item">
				<aui:input label="postal-code" name="zip" required="<%= true %>" />
			</div>

			<div class="form-group-item">
				<aui:select label="country" name="addressCountryId" required="<%= true %>" />
			</div>
		</div>

		<aui:input maxlength='<%= ModelHintsUtil.getMaxLength(Phone.class.getName(), "number") %>' name="phoneNumber" type="text" />
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<script>
	new Liferay.DynamicSelect([
		{
			select: '<portlet:namespace />addressCountryId',
			selectData: Liferay.Address.getCountries,
			selectDesc: 'nameCurrentValue',
			selectId: 'countryId',
			selectSort: '<%= true %>',
			selectVal: '<%= (address == null) ? 0L : address.getCountryId() %>',
		},
		{
			select: '<portlet:namespace />addressRegionId',
			selectData: Liferay.Address.getRegions,
			selectDesc: 'name',
			selectId: 'regionId',
			selectVal: '<%= (address == null) ? 0L : address.getRegionId() %>',
		},
	]);
</script>
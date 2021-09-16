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

<%@ include file="/account_selector/init.jsp" %>

<div class="account-selector-root" id="<%= accountSelectorId %>"></div>

<aui:script require="commerce-frontend-js/components/account_selector/entry as accountSelector">
	accountSelector.default(
		'<%= accountSelectorId %>',
		'<%= accountSelectorId %>',
		{
			commerceChannelId: '<%= commerceChannelId %>',
			createNewOrderURL: '<%= createNewOrderURL %>',
			currentCommerceAccount: <%= Validator.isNotNull(currentCommerceAccount) ? jsonSerializer.serializeDeep(currentCommerceAccount) : null %>,
			currentCommerceOrder: <%= Validator.isNotNull(currentCommerceOrder) ? jsonSerializer.serializeDeep(currentCommerceOrder) : null %>,
			refreshPageOnAccountSelected: true,
			selectOrderURL: '<%= selectOrderURL %>',
			setCurrentAccountURL: '<%= setCurrentAccountURL %>',
			showOrderTypeModal: <%= showOrderTypeModal %>,
			spritemap: '<%= spritemap %>',
		}
	);
</aui:script>
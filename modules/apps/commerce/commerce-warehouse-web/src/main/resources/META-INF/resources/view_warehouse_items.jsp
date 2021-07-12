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
CommerceInventoryWarehouseItemsDisplayContext commerceInventoryWarehouseItemsDisplayContext = (CommerceInventoryWarehouseItemsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String backURL = commerceInventoryWarehouseItemsDisplayContext.getBackURL();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}
%>

<c:if test="<%= commerceInventoryWarehouseItemsDisplayContext.hasManageCommerceInventoryWarehousePermission() %>">

	<%
	List<CommerceInventoryWarehouse> commerceInventoryWarehouses = commerceInventoryWarehouseItemsDisplayContext.getCommerceInventoryWarehouses();
	CPInstance cpInstance = commerceInventoryWarehouseItemsDisplayContext.getCPInstance();
	%>

	<c:choose>
		<c:when test="<%= commerceInventoryWarehouses.isEmpty() %>">
			<div class="alert alert-info">
				<liferay-ui:message key="there-are-no-active-warehouses" />
			</div>
		</c:when>
		<c:otherwise>
			<portlet:actionURL name="/cp_definitions/edit_commerce_inventory_warehouse_item" var="updateCommerceInventoryWarehouseItemURL" />

			<aui:form action="<%= updateCommerceInventoryWarehouseItemURL %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="commerceInventoryWarehouseId" type="hidden" />
				<aui:input name="commerceInventoryWarehouseItemId" type="hidden" />
				<aui:input name="sku" type="hidden" value="<%= cpInstance.getSku() %>" />
				<aui:input name="quantity" type="hidden" />
				<aui:input name="mvccVersion" type="hidden" />

				<table class="show-quick-actions-on-hover table table-autofit table-list table-responsive-lg">
					<thead>
						<tr>
							<th class="table-cell-expand"><liferay-ui:message key="warehouse" /></th>
							<th><liferay-ui:message key="quantity" /></th>
							<th></th>
						</tr>
					</thead>

					<tbody>

						<%
						for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceInventoryWarehouses) {
							CommerceInventoryWarehouseItem commerceInventoryWarehouseItem = commerceInventoryWarehouseItemsDisplayContext.getCommerceInventoryWarehouseItem(commerceInventoryWarehouse);

							long commerceInventoryWarehouseItemId = 0;
							int quantity = 0;
							long mvccVersion = 0;

							if (commerceInventoryWarehouseItem != null) {
								commerceInventoryWarehouseItemId = commerceInventoryWarehouseItem.getCommerceInventoryWarehouseItemId();
								quantity = commerceInventoryWarehouseItem.getQuantity();
								mvccVersion = commerceInventoryWarehouseItem.getMvccVersion();
							}

							int curIndex = commerceInventoryWarehouses.indexOf(commerceInventoryWarehouse);
						%>

							<tr data-commerce-inventory-warehouse-id="<%= commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" data-commerce-inventory-warehouse-item-id="<%= commerceInventoryWarehouseItemId %>" data-index="<%= curIndex %>" data-mvcc-version="<%= mvccVersion %>">
								<td>
									<%= HtmlUtil.escape(commerceInventoryWarehouse.getName()) %>
								</td>
								<td>
									<aui:input id='<%= "commerceInventoryWarehouseItemQuantity" + curIndex %>' label="" name="commerceInventoryWarehouseItemQuantity" value="<%= quantity %>" wrapperCssClass="m-0" />
								</td>
								<td class="text-center">
									<aui:button cssClass="btn-primary warehouse-save-btn" name='<%= "saveButton" + curIndex %>' primary="<%= true %>" value="save" />
								</td>
							</tr>

						<%
						}
						%>

					</tbody>
				</table>
			</aui:form>
		</c:otherwise>
	</c:choose>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"ADD", Constants.ADD
			).put(
				"CMD", Constants.CMD
			).put(
				"UPDATE", Constants.UPDATE
			).build()
		%>'
		module="js/viewWarehouseItems"
	/>
</c:if>
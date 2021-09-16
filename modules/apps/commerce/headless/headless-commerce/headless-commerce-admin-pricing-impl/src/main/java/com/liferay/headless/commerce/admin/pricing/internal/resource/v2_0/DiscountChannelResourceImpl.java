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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Discount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountChannel;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.DiscountChannelDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.DiscountChannelUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountChannelResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/discount-channel.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {DiscountChannelResource.class, NestedFieldSupport.class}
)
public class DiscountChannelResourceImpl
	extends BaseDiscountChannelResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteDiscountChannel(Long id) throws Exception {
		_commerceChannelRelService.deleteCommerceChannelRel(id);
	}

	@Override
	public Page<DiscountChannel>
			getDiscountByExternalReferenceCodeDiscountChannelsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find discount with external reference code " +
					externalReferenceCode);
		}

		List<CommerceChannelRel> commerceChannelRels =
			_commerceChannelRelService.getCommerceChannelRels(
				CommerceDiscount.class.getName(),
				commerceDiscount.getCommerceDiscountId(), null,
				pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems = _commerceChannelRelService.getCommerceChannelRelsCount(
			CommerceDiscount.class.getName(),
			commerceDiscount.getCommerceDiscountId());

		return Page.of(
			_toDiscountChannels(commerceChannelRels), pagination, totalItems);
	}

	@NestedField(parentClass = Discount.class, value = "discountChannels")
	@Override
	public Page<DiscountChannel> getDiscountIdDiscountChannelsPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchCommerceDiscount(id);

		if (commerceDiscount == null) {
			return Page.of(Collections.emptyList());
		}

		List<CommerceChannelRel> commerceChannelRel =
			_commerceChannelRelService.getCommerceChannelRels(
				CommerceDiscount.class.getName(), id, search,
				pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems = _commerceChannelRelService.getCommerceChannelRelsCount(
			CommerceDiscount.class.getName(), id, search);

		return Page.of(
			_toDiscountChannels(commerceChannelRel), pagination, totalItems);
	}

	@Override
	public DiscountChannel postDiscountByExternalReferenceCodeDiscountChannel(
			String externalReferenceCode, DiscountChannel discountChannel)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find discount with external reference code " +
					externalReferenceCode);
		}

		CommerceChannelRel commerceChannelRel =
			DiscountChannelUtil.addCommerceDiscountChannelRel(
				_commerceChannelService, _commerceChannelRelService,
				discountChannel, commerceDiscount, _serviceContextHelper);

		return _toDiscountChannel(commerceChannelRel.getCommerceChannelRelId());
	}

	@Override
	public DiscountChannel postDiscountIdDiscountChannel(
			Long id, DiscountChannel discountChannel)
		throws Exception {

		CommerceChannelRel commerceChannelRel =
			DiscountChannelUtil.addCommerceDiscountChannelRel(
				_commerceChannelService, _commerceChannelRelService,
				discountChannel,
				_commerceDiscountService.getCommerceDiscount(id),
				_serviceContextHelper);

		return _toDiscountChannel(commerceChannelRel.getCommerceChannelRelId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommerceChannelRel commerceChannelRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", commerceChannelRel.getCommerceChannelRelId(),
				"deleteDiscountChannel",
				_commerceChannelRelModelResourcePermission)
		).build();
	}

	private DiscountChannel _toDiscountChannel(Long commerceChannelRelId)
		throws Exception {

		CommerceChannelRel commerceChannelRel =
			_commerceChannelRelService.getCommerceChannelRel(
				commerceChannelRelId);

		return _discountChannelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceChannelRel), _dtoConverterRegistry,
				commerceChannelRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<DiscountChannel> _toDiscountChannels(
			List<CommerceChannelRel> commerceChannelRels)
		throws Exception {

		List<DiscountChannel> discountChannels = new ArrayList<>();

		for (CommerceChannelRel commerceChannelRel : commerceChannelRels) {
			discountChannels.add(
				_toDiscountChannel(
					commerceChannelRel.getCommerceChannelRelId()));
		}

		return discountChannels;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannelRel)"
	)
	private ModelResourcePermission<CommerceChannelRel>
		_commerceChannelRelModelResourcePermission;

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private DiscountChannelDTOConverter _discountChannelDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
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

package com.liferay.commerce.account.service.base;

import com.liferay.account.service.persistence.AccountGroupRelPersistence;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.CommerceAccountGroupRelLocalService;
import com.liferay.commerce.account.service.CommerceAccountGroupRelLocalServiceUtil;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.lang.reflect.Field;

/**
 * Provides the base implementation for the commerce account group rel local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.account.service.impl.CommerceAccountGroupRelLocalServiceImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.account.service.impl.CommerceAccountGroupRelLocalServiceImpl
 * @generated
 */
public abstract class CommerceAccountGroupRelLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements CommerceAccountGroupRelLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CommerceAccountGroupRelLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CommerceAccountGroupRelLocalServiceUtil</code>.
	 */

	/**
	 * Returns the commerce account local service.
	 *
	 * @return the commerce account local service
	 */
	public com.liferay.commerce.account.service.CommerceAccountLocalService
		getCommerceAccountLocalService() {

		return commerceAccountLocalService;
	}

	/**
	 * Sets the commerce account local service.
	 *
	 * @param commerceAccountLocalService the commerce account local service
	 */
	public void setCommerceAccountLocalService(
		com.liferay.commerce.account.service.CommerceAccountLocalService
			commerceAccountLocalService) {

		this.commerceAccountLocalService = commerceAccountLocalService;
	}

	/**
	 * Returns the commerce account group local service.
	 *
	 * @return the commerce account group local service
	 */
	public com.liferay.commerce.account.service.CommerceAccountGroupLocalService
		getCommerceAccountGroupLocalService() {

		return commerceAccountGroupLocalService;
	}

	/**
	 * Sets the commerce account group local service.
	 *
	 * @param commerceAccountGroupLocalService the commerce account group local service
	 */
	public void setCommerceAccountGroupLocalService(
		com.liferay.commerce.account.service.CommerceAccountGroupLocalService
			commerceAccountGroupLocalService) {

		this.commerceAccountGroupLocalService =
			commerceAccountGroupLocalService;
	}

	/**
	 * Returns the commerce account group commerce account rel local service.
	 *
	 * @return the commerce account group commerce account rel local service
	 */
	public com.liferay.commerce.account.service.
		CommerceAccountGroupCommerceAccountRelLocalService
			getCommerceAccountGroupCommerceAccountRelLocalService() {

		return commerceAccountGroupCommerceAccountRelLocalService;
	}

	/**
	 * Sets the commerce account group commerce account rel local service.
	 *
	 * @param commerceAccountGroupCommerceAccountRelLocalService the commerce account group commerce account rel local service
	 */
	public void setCommerceAccountGroupCommerceAccountRelLocalService(
		com.liferay.commerce.account.service.
			CommerceAccountGroupCommerceAccountRelLocalService
				commerceAccountGroupCommerceAccountRelLocalService) {

		this.commerceAccountGroupCommerceAccountRelLocalService =
			commerceAccountGroupCommerceAccountRelLocalService;
	}

	/**
	 * Returns the commerce account group rel local service.
	 *
	 * @return the commerce account group rel local service
	 */
	public CommerceAccountGroupRelLocalService
		getCommerceAccountGroupRelLocalService() {

		return commerceAccountGroupRelLocalService;
	}

	/**
	 * Sets the commerce account group rel local service.
	 *
	 * @param commerceAccountGroupRelLocalService the commerce account group rel local service
	 */
	public void setCommerceAccountGroupRelLocalService(
		CommerceAccountGroupRelLocalService
			commerceAccountGroupRelLocalService) {

		this.commerceAccountGroupRelLocalService =
			commerceAccountGroupRelLocalService;
	}

	/**
	 * Returns the commerce account organization rel local service.
	 *
	 * @return the commerce account organization rel local service
	 */
	public com.liferay.commerce.account.service.
		CommerceAccountOrganizationRelLocalService
			getCommerceAccountOrganizationRelLocalService() {

		return commerceAccountOrganizationRelLocalService;
	}

	/**
	 * Sets the commerce account organization rel local service.
	 *
	 * @param commerceAccountOrganizationRelLocalService the commerce account organization rel local service
	 */
	public void setCommerceAccountOrganizationRelLocalService(
		com.liferay.commerce.account.service.
			CommerceAccountOrganizationRelLocalService
				commerceAccountOrganizationRelLocalService) {

		this.commerceAccountOrganizationRelLocalService =
			commerceAccountOrganizationRelLocalService;
	}

	/**
	 * Returns the commerce account user rel local service.
	 *
	 * @return the commerce account user rel local service
	 */
	public
		com.liferay.commerce.account.service.CommerceAccountUserRelLocalService
			getCommerceAccountUserRelLocalService() {

		return commerceAccountUserRelLocalService;
	}

	/**
	 * Sets the commerce account user rel local service.
	 *
	 * @param commerceAccountUserRelLocalService the commerce account user rel local service
	 */
	public void setCommerceAccountUserRelLocalService(
		com.liferay.commerce.account.service.CommerceAccountUserRelLocalService
			commerceAccountUserRelLocalService) {

		this.commerceAccountUserRelLocalService =
			commerceAccountUserRelLocalService;
	}

	/**
	 * Returns the account group rel local service.
	 *
	 * @return the account group rel local service
	 */
	public com.liferay.account.service.AccountGroupRelLocalService
		getAccountGroupRelLocalService() {

		return accountGroupRelLocalService;
	}

	/**
	 * Sets the account group rel local service.
	 *
	 * @param accountGroupRelLocalService the account group rel local service
	 */
	public void setAccountGroupRelLocalService(
		com.liferay.account.service.AccountGroupRelLocalService
			accountGroupRelLocalService) {

		this.accountGroupRelLocalService = accountGroupRelLocalService;
	}

	/**
	 * Returns the account group rel persistence.
	 *
	 * @return the account group rel persistence
	 */
	public AccountGroupRelPersistence getAccountGroupRelPersistence() {
		return accountGroupRelPersistence;
	}

	/**
	 * Sets the account group rel persistence.
	 *
	 * @param accountGroupRelPersistence the account group rel persistence
	 */
	public void setAccountGroupRelPersistence(
		AccountGroupRelPersistence accountGroupRelPersistence) {

		this.accountGroupRelPersistence = accountGroupRelPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService
		getClassNameLocalService() {

		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService
			classNameLocalService) {

		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {

		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService
		getResourceLocalService() {

		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService
			resourceLocalService) {

		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService
		getUserLocalService() {

		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {

		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		_setLocalServiceUtilService(commerceAccountGroupRelLocalService);
	}

	public void destroy() {
		_setLocalServiceUtilService(null);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommerceAccountGroupRelLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceAccountGroupRel.class;
	}

	protected String getModelClassName() {
		return CommerceAccountGroupRel.class.getName();
	}

	private void _setLocalServiceUtilService(
		CommerceAccountGroupRelLocalService
			commerceAccountGroupRelLocalService) {

		try {
			Field field =
				CommerceAccountGroupRelLocalServiceUtil.class.getDeclaredField(
					"_service");

			field.setAccessible(true);

			field.set(null, commerceAccountGroupRelLocalService);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountLocalService.class
	)
	protected com.liferay.commerce.account.service.CommerceAccountLocalService
		commerceAccountLocalService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountGroupLocalService.class
	)
	protected
		com.liferay.commerce.account.service.CommerceAccountGroupLocalService
			commerceAccountGroupLocalService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelLocalService.class
	)
	protected com.liferay.commerce.account.service.
		CommerceAccountGroupCommerceAccountRelLocalService
			commerceAccountGroupCommerceAccountRelLocalService;

	@BeanReference(type = CommerceAccountGroupRelLocalService.class)
	protected CommerceAccountGroupRelLocalService
		commerceAccountGroupRelLocalService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountOrganizationRelLocalService.class
	)
	protected com.liferay.commerce.account.service.
		CommerceAccountOrganizationRelLocalService
			commerceAccountOrganizationRelLocalService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountUserRelLocalService.class
	)
	protected
		com.liferay.commerce.account.service.CommerceAccountUserRelLocalService
			commerceAccountUserRelLocalService;

	@ServiceReference(
		type = com.liferay.account.service.AccountGroupRelLocalService.class
	)
	protected com.liferay.account.service.AccountGroupRelLocalService
		accountGroupRelLocalService;

	@ServiceReference(type = AccountGroupRelPersistence.class)
	protected AccountGroupRelPersistence accountGroupRelPersistence;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ClassNameLocalService.class
	)
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ResourceLocalService.class
	)
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.UserLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

}
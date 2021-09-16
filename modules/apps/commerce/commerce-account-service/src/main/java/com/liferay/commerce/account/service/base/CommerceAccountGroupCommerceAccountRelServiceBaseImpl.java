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
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelService;
import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelServiceUtil;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.lang.reflect.Field;

/**
 * Provides the base implementation for the commerce account group commerce account rel remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.account.service.impl.CommerceAccountGroupCommerceAccountRelServiceImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.account.service.impl.CommerceAccountGroupCommerceAccountRelServiceImpl
 * @generated
 */
public abstract class CommerceAccountGroupCommerceAccountRelServiceBaseImpl
	extends BaseServiceImpl
	implements CommerceAccountGroupCommerceAccountRelService,
			   IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CommerceAccountGroupCommerceAccountRelService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CommerceAccountGroupCommerceAccountRelServiceUtil</code>.
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
	 * Returns the commerce account remote service.
	 *
	 * @return the commerce account remote service
	 */
	public com.liferay.commerce.account.service.CommerceAccountService
		getCommerceAccountService() {

		return commerceAccountService;
	}

	/**
	 * Sets the commerce account remote service.
	 *
	 * @param commerceAccountService the commerce account remote service
	 */
	public void setCommerceAccountService(
		com.liferay.commerce.account.service.CommerceAccountService
			commerceAccountService) {

		this.commerceAccountService = commerceAccountService;
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
	 * Returns the commerce account group remote service.
	 *
	 * @return the commerce account group remote service
	 */
	public com.liferay.commerce.account.service.CommerceAccountGroupService
		getCommerceAccountGroupService() {

		return commerceAccountGroupService;
	}

	/**
	 * Sets the commerce account group remote service.
	 *
	 * @param commerceAccountGroupService the commerce account group remote service
	 */
	public void setCommerceAccountGroupService(
		com.liferay.commerce.account.service.CommerceAccountGroupService
			commerceAccountGroupService) {

		this.commerceAccountGroupService = commerceAccountGroupService;
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
	 * Returns the commerce account group commerce account rel remote service.
	 *
	 * @return the commerce account group commerce account rel remote service
	 */
	public CommerceAccountGroupCommerceAccountRelService
		getCommerceAccountGroupCommerceAccountRelService() {

		return commerceAccountGroupCommerceAccountRelService;
	}

	/**
	 * Sets the commerce account group commerce account rel remote service.
	 *
	 * @param commerceAccountGroupCommerceAccountRelService the commerce account group commerce account rel remote service
	 */
	public void setCommerceAccountGroupCommerceAccountRelService(
		CommerceAccountGroupCommerceAccountRelService
			commerceAccountGroupCommerceAccountRelService) {

		this.commerceAccountGroupCommerceAccountRelService =
			commerceAccountGroupCommerceAccountRelService;
	}

	/**
	 * Returns the commerce account group rel local service.
	 *
	 * @return the commerce account group rel local service
	 */
	public
		com.liferay.commerce.account.service.CommerceAccountGroupRelLocalService
			getCommerceAccountGroupRelLocalService() {

		return commerceAccountGroupRelLocalService;
	}

	/**
	 * Sets the commerce account group rel local service.
	 *
	 * @param commerceAccountGroupRelLocalService the commerce account group rel local service
	 */
	public void setCommerceAccountGroupRelLocalService(
		com.liferay.commerce.account.service.CommerceAccountGroupRelLocalService
			commerceAccountGroupRelLocalService) {

		this.commerceAccountGroupRelLocalService =
			commerceAccountGroupRelLocalService;
	}

	/**
	 * Returns the commerce account group rel remote service.
	 *
	 * @return the commerce account group rel remote service
	 */
	public com.liferay.commerce.account.service.CommerceAccountGroupRelService
		getCommerceAccountGroupRelService() {

		return commerceAccountGroupRelService;
	}

	/**
	 * Sets the commerce account group rel remote service.
	 *
	 * @param commerceAccountGroupRelService the commerce account group rel remote service
	 */
	public void setCommerceAccountGroupRelService(
		com.liferay.commerce.account.service.CommerceAccountGroupRelService
			commerceAccountGroupRelService) {

		this.commerceAccountGroupRelService = commerceAccountGroupRelService;
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
	 * Returns the commerce account organization rel remote service.
	 *
	 * @return the commerce account organization rel remote service
	 */
	public
		com.liferay.commerce.account.service.
			CommerceAccountOrganizationRelService
				getCommerceAccountOrganizationRelService() {

		return commerceAccountOrganizationRelService;
	}

	/**
	 * Sets the commerce account organization rel remote service.
	 *
	 * @param commerceAccountOrganizationRelService the commerce account organization rel remote service
	 */
	public void setCommerceAccountOrganizationRelService(
		com.liferay.commerce.account.service.
			CommerceAccountOrganizationRelService
				commerceAccountOrganizationRelService) {

		this.commerceAccountOrganizationRelService =
			commerceAccountOrganizationRelService;
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
	 * Returns the commerce account user rel remote service.
	 *
	 * @return the commerce account user rel remote service
	 */
	public com.liferay.commerce.account.service.CommerceAccountUserRelService
		getCommerceAccountUserRelService() {

		return commerceAccountUserRelService;
	}

	/**
	 * Sets the commerce account user rel remote service.
	 *
	 * @param commerceAccountUserRelService the commerce account user rel remote service
	 */
	public void setCommerceAccountUserRelService(
		com.liferay.commerce.account.service.CommerceAccountUserRelService
			commerceAccountUserRelService) {

		this.commerceAccountUserRelService = commerceAccountUserRelService;
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
	 * Returns the account group rel remote service.
	 *
	 * @return the account group rel remote service
	 */
	public com.liferay.account.service.AccountGroupRelService
		getAccountGroupRelService() {

		return accountGroupRelService;
	}

	/**
	 * Sets the account group rel remote service.
	 *
	 * @param accountGroupRelService the account group rel remote service
	 */
	public void setAccountGroupRelService(
		com.liferay.account.service.AccountGroupRelService
			accountGroupRelService) {

		this.accountGroupRelService = accountGroupRelService;
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
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.kernel.service.ClassNameService
		getClassNameService() {

		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.kernel.service.ClassNameService classNameService) {

		this.classNameService = classNameService;
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
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.kernel.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.kernel.service.UserService userService) {

		this.userService = userService;
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
		_setServiceUtilService(commerceAccountGroupCommerceAccountRelService);
	}

	public void destroy() {
		_setServiceUtilService(null);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommerceAccountGroupCommerceAccountRelService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceAccountGroupCommerceAccountRel.class;
	}

	protected String getModelClassName() {
		return CommerceAccountGroupCommerceAccountRel.class.getName();
	}

	private void _setServiceUtilService(
		CommerceAccountGroupCommerceAccountRelService
			commerceAccountGroupCommerceAccountRelService) {

		try {
			Field field =
				CommerceAccountGroupCommerceAccountRelServiceUtil.class.
					getDeclaredField("_service");

			field.setAccessible(true);

			field.set(null, commerceAccountGroupCommerceAccountRelService);
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
		type = com.liferay.commerce.account.service.CommerceAccountService.class
	)
	protected com.liferay.commerce.account.service.CommerceAccountService
		commerceAccountService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountGroupLocalService.class
	)
	protected
		com.liferay.commerce.account.service.CommerceAccountGroupLocalService
			commerceAccountGroupLocalService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountGroupService.class
	)
	protected com.liferay.commerce.account.service.CommerceAccountGroupService
		commerceAccountGroupService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelLocalService.class
	)
	protected com.liferay.commerce.account.service.
		CommerceAccountGroupCommerceAccountRelLocalService
			commerceAccountGroupCommerceAccountRelLocalService;

	@BeanReference(type = CommerceAccountGroupCommerceAccountRelService.class)
	protected CommerceAccountGroupCommerceAccountRelService
		commerceAccountGroupCommerceAccountRelService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountGroupRelLocalService.class
	)
	protected
		com.liferay.commerce.account.service.CommerceAccountGroupRelLocalService
			commerceAccountGroupRelLocalService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountGroupRelService.class
	)
	protected
		com.liferay.commerce.account.service.CommerceAccountGroupRelService
			commerceAccountGroupRelService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountOrganizationRelLocalService.class
	)
	protected com.liferay.commerce.account.service.
		CommerceAccountOrganizationRelLocalService
			commerceAccountOrganizationRelLocalService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountOrganizationRelService.class
	)
	protected
		com.liferay.commerce.account.service.
			CommerceAccountOrganizationRelService
				commerceAccountOrganizationRelService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountUserRelLocalService.class
	)
	protected
		com.liferay.commerce.account.service.CommerceAccountUserRelLocalService
			commerceAccountUserRelLocalService;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountUserRelService.class
	)
	protected com.liferay.commerce.account.service.CommerceAccountUserRelService
		commerceAccountUserRelService;

	@ServiceReference(
		type = com.liferay.account.service.AccountGroupRelLocalService.class
	)
	protected com.liferay.account.service.AccountGroupRelLocalService
		accountGroupRelLocalService;

	@ServiceReference(
		type = com.liferay.account.service.AccountGroupRelService.class
	)
	protected com.liferay.account.service.AccountGroupRelService
		accountGroupRelService;

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

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ClassNameService.class
	)
	protected com.liferay.portal.kernel.service.ClassNameService
		classNameService;

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

	@ServiceReference(
		type = com.liferay.portal.kernel.service.UserService.class
	)
	protected com.liferay.portal.kernel.service.UserService userService;

	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

}
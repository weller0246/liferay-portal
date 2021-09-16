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

import com.liferay.account.service.persistence.AccountEntryUserRelPersistence;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.CommerceAccountUserRelService;
import com.liferay.commerce.account.service.CommerceAccountUserRelServiceUtil;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.RolePersistence;
import com.liferay.portal.kernel.service.persistence.UserGroupRolePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.lang.reflect.Field;

/**
 * Provides the base implementation for the commerce account user rel remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.account.service.impl.CommerceAccountUserRelServiceImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.account.service.impl.CommerceAccountUserRelServiceImpl
 * @generated
 */
public abstract class CommerceAccountUserRelServiceBaseImpl
	extends BaseServiceImpl
	implements CommerceAccountUserRelService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CommerceAccountUserRelService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CommerceAccountUserRelServiceUtil</code>.
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
	public com.liferay.commerce.account.service.
		CommerceAccountGroupCommerceAccountRelService
			getCommerceAccountGroupCommerceAccountRelService() {

		return commerceAccountGroupCommerceAccountRelService;
	}

	/**
	 * Sets the commerce account group commerce account rel remote service.
	 *
	 * @param commerceAccountGroupCommerceAccountRelService the commerce account group commerce account rel remote service
	 */
	public void setCommerceAccountGroupCommerceAccountRelService(
		com.liferay.commerce.account.service.
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
	public CommerceAccountUserRelService getCommerceAccountUserRelService() {
		return commerceAccountUserRelService;
	}

	/**
	 * Sets the commerce account user rel remote service.
	 *
	 * @param commerceAccountUserRelService the commerce account user rel remote service
	 */
	public void setCommerceAccountUserRelService(
		CommerceAccountUserRelService commerceAccountUserRelService) {

		this.commerceAccountUserRelService = commerceAccountUserRelService;
	}

	/**
	 * Returns the account entry user rel local service.
	 *
	 * @return the account entry user rel local service
	 */
	public com.liferay.account.service.AccountEntryUserRelLocalService
		getAccountEntryUserRelLocalService() {

		return accountEntryUserRelLocalService;
	}

	/**
	 * Sets the account entry user rel local service.
	 *
	 * @param accountEntryUserRelLocalService the account entry user rel local service
	 */
	public void setAccountEntryUserRelLocalService(
		com.liferay.account.service.AccountEntryUserRelLocalService
			accountEntryUserRelLocalService) {

		this.accountEntryUserRelLocalService = accountEntryUserRelLocalService;
	}

	/**
	 * Returns the account entry user rel remote service.
	 *
	 * @return the account entry user rel remote service
	 */
	public com.liferay.account.service.AccountEntryUserRelService
		getAccountEntryUserRelService() {

		return accountEntryUserRelService;
	}

	/**
	 * Sets the account entry user rel remote service.
	 *
	 * @param accountEntryUserRelService the account entry user rel remote service
	 */
	public void setAccountEntryUserRelService(
		com.liferay.account.service.AccountEntryUserRelService
			accountEntryUserRelService) {

		this.accountEntryUserRelService = accountEntryUserRelService;
	}

	/**
	 * Returns the account entry user rel persistence.
	 *
	 * @return the account entry user rel persistence
	 */
	public AccountEntryUserRelPersistence getAccountEntryUserRelPersistence() {
		return accountEntryUserRelPersistence;
	}

	/**
	 * Sets the account entry user rel persistence.
	 *
	 * @param accountEntryUserRelPersistence the account entry user rel persistence
	 */
	public void setAccountEntryUserRelPersistence(
		AccountEntryUserRelPersistence accountEntryUserRelPersistence) {

		this.accountEntryUserRelPersistence = accountEntryUserRelPersistence;
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
	 * Returns the role local service.
	 *
	 * @return the role local service
	 */
	public com.liferay.portal.kernel.service.RoleLocalService
		getRoleLocalService() {

		return roleLocalService;
	}

	/**
	 * Sets the role local service.
	 *
	 * @param roleLocalService the role local service
	 */
	public void setRoleLocalService(
		com.liferay.portal.kernel.service.RoleLocalService roleLocalService) {

		this.roleLocalService = roleLocalService;
	}

	/**
	 * Returns the role remote service.
	 *
	 * @return the role remote service
	 */
	public com.liferay.portal.kernel.service.RoleService getRoleService() {
		return roleService;
	}

	/**
	 * Sets the role remote service.
	 *
	 * @param roleService the role remote service
	 */
	public void setRoleService(
		com.liferay.portal.kernel.service.RoleService roleService) {

		this.roleService = roleService;
	}

	/**
	 * Returns the role persistence.
	 *
	 * @return the role persistence
	 */
	public RolePersistence getRolePersistence() {
		return rolePersistence;
	}

	/**
	 * Sets the role persistence.
	 *
	 * @param rolePersistence the role persistence
	 */
	public void setRolePersistence(RolePersistence rolePersistence) {
		this.rolePersistence = rolePersistence;
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

	/**
	 * Returns the user group role local service.
	 *
	 * @return the user group role local service
	 */
	public com.liferay.portal.kernel.service.UserGroupRoleLocalService
		getUserGroupRoleLocalService() {

		return userGroupRoleLocalService;
	}

	/**
	 * Sets the user group role local service.
	 *
	 * @param userGroupRoleLocalService the user group role local service
	 */
	public void setUserGroupRoleLocalService(
		com.liferay.portal.kernel.service.UserGroupRoleLocalService
			userGroupRoleLocalService) {

		this.userGroupRoleLocalService = userGroupRoleLocalService;
	}

	/**
	 * Returns the user group role remote service.
	 *
	 * @return the user group role remote service
	 */
	public com.liferay.portal.kernel.service.UserGroupRoleService
		getUserGroupRoleService() {

		return userGroupRoleService;
	}

	/**
	 * Sets the user group role remote service.
	 *
	 * @param userGroupRoleService the user group role remote service
	 */
	public void setUserGroupRoleService(
		com.liferay.portal.kernel.service.UserGroupRoleService
			userGroupRoleService) {

		this.userGroupRoleService = userGroupRoleService;
	}

	/**
	 * Returns the user group role persistence.
	 *
	 * @return the user group role persistence
	 */
	public UserGroupRolePersistence getUserGroupRolePersistence() {
		return userGroupRolePersistence;
	}

	/**
	 * Sets the user group role persistence.
	 *
	 * @param userGroupRolePersistence the user group role persistence
	 */
	public void setUserGroupRolePersistence(
		UserGroupRolePersistence userGroupRolePersistence) {

		this.userGroupRolePersistence = userGroupRolePersistence;
	}

	public void afterPropertiesSet() {
		_setServiceUtilService(commerceAccountUserRelService);
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
		return CommerceAccountUserRelService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceAccountUserRel.class;
	}

	protected String getModelClassName() {
		return CommerceAccountUserRel.class.getName();
	}

	private void _setServiceUtilService(
		CommerceAccountUserRelService commerceAccountUserRelService) {

		try {
			Field field =
				CommerceAccountUserRelServiceUtil.class.getDeclaredField(
					"_service");

			field.setAccessible(true);

			field.set(null, commerceAccountUserRelService);
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

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelService.class
	)
	protected com.liferay.commerce.account.service.
		CommerceAccountGroupCommerceAccountRelService
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

	@BeanReference(type = CommerceAccountUserRelService.class)
	protected CommerceAccountUserRelService commerceAccountUserRelService;

	@ServiceReference(
		type = com.liferay.account.service.AccountEntryUserRelLocalService.class
	)
	protected com.liferay.account.service.AccountEntryUserRelLocalService
		accountEntryUserRelLocalService;

	@ServiceReference(
		type = com.liferay.account.service.AccountEntryUserRelService.class
	)
	protected com.liferay.account.service.AccountEntryUserRelService
		accountEntryUserRelService;

	@ServiceReference(type = AccountEntryUserRelPersistence.class)
	protected AccountEntryUserRelPersistence accountEntryUserRelPersistence;

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
		type = com.liferay.portal.kernel.service.RoleLocalService.class
	)
	protected com.liferay.portal.kernel.service.RoleLocalService
		roleLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.RoleService.class
	)
	protected com.liferay.portal.kernel.service.RoleService roleService;

	@ServiceReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;

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

	@ServiceReference(
		type = com.liferay.portal.kernel.service.UserGroupRoleLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserGroupRoleLocalService
		userGroupRoleLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.UserGroupRoleService.class
	)
	protected com.liferay.portal.kernel.service.UserGroupRoleService
		userGroupRoleService;

	@ServiceReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;

}
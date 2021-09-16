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

package com.liferay.account.service.impl;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.AccountEntryDomainsException;
import com.liferay.account.exception.AccountEntryEmailAddressException;
import com.liferay.account.exception.AccountEntryNameException;
import com.liferay.account.exception.AccountEntryTypeException;
import com.liferay.account.exception.DuplicateAccountEntryExternalReferenceCodeException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRelTable;
import com.liferay.account.model.AccountEntryTable;
import com.liferay.account.model.AccountEntryUserRelTable;
import com.liferay.account.model.impl.AccountEntryImpl;
import com.liferay.account.service.base.AccountEntryLocalServiceBaseImpl;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortFieldBuilder;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.users.admin.kernel.file.uploads.UserFileUploadsSettings;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.EmailValidator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountEntry",
	service = AopService.class
)
public class AccountEntryLocalServiceImpl
	extends AccountEntryLocalServiceBaseImpl {

	@Override
	public void activateAccountEntries(long[] accountEntryIds)
		throws PortalException {

		_performActions(accountEntryIds, this::activateAccountEntry);
	}

	@Override
	public AccountEntry activateAccountEntry(AccountEntry accountEntry) {
		return updateStatus(accountEntry, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public AccountEntry activateAccountEntry(long accountEntryId)
		throws Exception {

		return activateAccountEntry(getAccountEntry(accountEntryId));
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addAccountEntry(long, long, String, String, String[],
	 *             byte[], String, String, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, byte[] logoBytes, int status)
		throws PortalException {

		return addAccountEntry(
			userId, parentAccountEntryId, name, description, domains, logoBytes,
			null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS, status, null);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addAccountEntry(long, long, String, String, String[],
	 *             byte[], String, String, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, byte[] logoBytes, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addAccountEntry(
			userId, parentAccountEntryId, name, description, domains, logoBytes,
			null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS, status,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, byte[] logoBytes,
			String taxIdNumber, String type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addAccountEntry(
			userId, parentAccountEntryId, name, description, domains, null,
			logoBytes, taxIdNumber, type, status, serviceContext);
	}

	@Override
	public AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, String emailAddress,
			byte[] logoBytes, String taxIdNumber, String type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		// Account entry

		long accountEntryId = counterLocalService.increment();

		AccountEntry accountEntry = accountEntryPersistence.create(
			accountEntryId);

		User user = userLocalService.getUser(userId);

		accountEntry.setCompanyId(user.getCompanyId());
		accountEntry.setUserId(user.getUserId());
		accountEntry.setUserName(user.getFullName());

		accountEntry.setParentAccountEntryId(parentAccountEntryId);

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			AccountEntry.class.getName(), "name");

		name = StringUtil.shorten(name, nameMaxLength);

		_validateName(name);

		accountEntry.setName(name);

		accountEntry.setDescription(description);

		domains = _validateDomains(domains);

		accountEntry.setDomains(StringUtil.merge(domains, StringPool.COMMA));

		_validateEmailAddress(emailAddress);

		accountEntry.setEmailAddress(emailAddress);

		_portal.updateImageId(
			accountEntry, true, logoBytes, "logoId",
			_userFileUploadsSettings.getImageMaxSize(),
			_userFileUploadsSettings.getImageMaxHeight(),
			_userFileUploadsSettings.getImageMaxWidth());

		accountEntry.setTaxIdNumber(taxIdNumber);

		_validateType(type);

		accountEntry.setType(type);

		accountEntry.setStatus(status);

		accountEntry = accountEntryPersistence.update(accountEntry);

		// Group

		groupLocalService.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			AccountEntry.class.getName(), accountEntryId,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, getLocalizationMap(name),
			null, GroupConstants.TYPE_SITE_PRIVATE, false,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), 0, user.getUserId(),
			AccountEntry.class.getName(), accountEntryId, false, false, false);

		if (serviceContext != null) {

			// Asset

			_updateAsset(accountEntry, serviceContext);

			// Expando

			accountEntry.setExpandoBridgeAttributes(serviceContext);
		}

		return accountEntry;
	}

	@Override
	public AccountEntry addOrUpdateAccountEntry(
			String externalReferenceCode, long userId,
			long parentAccountEntryId, String name, String description,
			String[] domains, String emailAddress, byte[] logoBytes,
			String taxIdNumber, String type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		AccountEntry accountEntry = fetchAccountEntryByReferenceCode(
			user.getCompanyId(), externalReferenceCode);

		if (accountEntry != null) {
			return updateAccountEntry(
				accountEntry.getAccountEntryId(), parentAccountEntryId, name,
				description, false, domains, emailAddress, logoBytes,
				taxIdNumber, status, serviceContext);
		}

		accountEntry = addAccountEntry(
			userId, parentAccountEntryId, name, description, domains,
			emailAddress, logoBytes, taxIdNumber, type, status, serviceContext);

		accountEntry.setExternalReferenceCode(externalReferenceCode);

		return accountEntryPersistence.update(accountEntry);
	}

	@Override
	public void deactivateAccountEntries(long[] accountEntryIds)
		throws PortalException {

		_performActions(accountEntryIds, this::deactivateAccountEntry);
	}

	@Override
	public AccountEntry deactivateAccountEntry(AccountEntry accountEntry) {
		return updateStatus(accountEntry, WorkflowConstants.STATUS_INACTIVE);
	}

	@Override
	public AccountEntry deactivateAccountEntry(long accountEntryId)
		throws Exception {

		return deactivateAccountEntry(getAccountEntry(accountEntryId));
	}

	@Override
	public void deleteAccountEntries(long[] accountEntryIds)
		throws PortalException {

		_performActions(
			accountEntryIds, accountEntryLocalService::deleteAccountEntry);
	}

	@Override
	public void deleteAccountEntriesByCompanyId(long companyId) {
		if (!CompanyThreadLocal.isDeleteInProcess()) {
			throw new UnsupportedOperationException(
				"Deleting account entries by company must be called when " +
					"deleting a company");
		}

		for (AccountEntry accountRole :
				accountEntryPersistence.findByCompanyId(companyId)) {

			accountEntryPersistence.remove(accountRole);
		}
	}

	@Override
	public AccountEntry deleteAccountEntry(AccountEntry accountEntry)
		throws PortalException {

		// Account entry

		accountEntry = super.deleteAccountEntry(accountEntry);

		// Group

		groupLocalService.deleteGroup(accountEntry.getAccountEntryGroup());

		// Resources

		resourceLocalService.deleteResource(
			accountEntry.getCompanyId(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			accountEntry.getAccountEntryId());

		// Asset

		assetEntryLocalService.deleteEntry(
			AccountEntry.class.getName(), accountEntry.getAccountEntryId());

		// Expando

		expandoRowLocalService.deleteRows(accountEntry.getAccountEntryId());

		return accountEntry;
	}

	@Override
	public AccountEntry deleteAccountEntry(long accountEntryId)
		throws PortalException {

		return deleteAccountEntry(getAccountEntry(accountEntryId));
	}

	@Override
	public AccountEntry fetchPersonAccountEntry(long userId) {
		return accountEntryPersistence.fetchByU_T_First(
			userId, AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON, null);
	}

	@Override
	public AccountEntry fetchUserAccountEntry(
		long userId, long accountEntryId) {

		JoinStep joinStep = DSLQueryFactoryUtil.selectDistinct(
			AccountEntryTable.INSTANCE
		).from(
			UserTable.INSTANCE
		).leftJoinOn(
			AccountEntryUserRelTable.INSTANCE,
			AccountEntryUserRelTable.INSTANCE.accountUserId.eq(
				UserTable.INSTANCE.userId)
		);

		Predicate accountEntryTablePredicate =
			AccountEntryTable.INSTANCE.accountEntryId.eq(
				AccountEntryUserRelTable.INSTANCE.accountEntryId
			).or(
				AccountEntryTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			);

		Long[] organizationIds = _getOrganizationIds(userId);

		if (ArrayUtil.isNotEmpty(organizationIds)) {
			joinStep = joinStep.leftJoinOn(
				AccountEntryOrganizationRelTable.INSTANCE,
				AccountEntryOrganizationRelTable.INSTANCE.organizationId.in(
					organizationIds));

			accountEntryTablePredicate = accountEntryTablePredicate.or(
				AccountEntryTable.INSTANCE.accountEntryId.eq(
					AccountEntryOrganizationRelTable.INSTANCE.accountEntryId));
		}

		joinStep = joinStep.leftJoinOn(
			AccountEntryTable.INSTANCE, accountEntryTablePredicate);

		DSLQuery dslQuery = joinStep.where(
			UserTable.INSTANCE.userId.eq(
				userId
			).and(
				AccountEntryTable.INSTANCE.type.neq(
					AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST)
			).and(
				AccountEntryTable.INSTANCE.accountEntryId.eq(accountEntryId)
			)
		).limit(
			0, 1
		);

		List<AccountEntry> accountEntries = dslQuery(dslQuery);

		if (accountEntries.isEmpty()) {
			return null;
		}

		return accountEntries.get(0);
	}

	@Override
	public List<AccountEntry> getAccountEntries(
		long companyId, int status, int start, int end,
		OrderByComparator<AccountEntry> orderByComparator) {

		return accountEntryPersistence.findByC_S(
			companyId, status, start, end, orderByComparator);
	}

	@Override
	public int getAccountEntriesCount(long companyId, int status) {
		return accountEntryPersistence.countByC_S(companyId, status);
	}

	@Override
	public AccountEntry getGuestAccountEntry(long companyId)
		throws PortalException {

		User defaultUser = userLocalService.getDefaultUser(companyId);

		AccountEntryImpl accountEntryImpl = new AccountEntryImpl();

		accountEntryImpl.setAccountEntryId(
			AccountConstants.ACCOUNT_ENTRY_ID_GUEST);
		accountEntryImpl.setCompanyId(defaultUser.getCompanyId());
		accountEntryImpl.setUserId(defaultUser.getUserId());
		accountEntryImpl.setUserName(defaultUser.getFullName());
		accountEntryImpl.setParentAccountEntryId(
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT);
		accountEntryImpl.setEmailAddress(defaultUser.getEmailAddress());
		accountEntryImpl.setName(defaultUser.getFullName());
		accountEntryImpl.setType(AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST);
		accountEntryImpl.setStatus(WorkflowConstants.STATUS_APPROVED);

		return accountEntryImpl;
	}

	@Override
	public List<AccountEntry> getUserAccountEntries(
			long userId, Long parentAccountEntryId, String keywords,
			String[] types, int start, int end)
		throws PortalException {

		return getUserAccountEntries(
			userId, parentAccountEntryId, keywords, types,
			WorkflowConstants.STATUS_ANY, start, end);
	}

	@Override
	public List<AccountEntry> getUserAccountEntries(
			long userId, Long parentAccountEntryId, String keywords,
			String[] types, Integer status, int start, int end)
		throws PortalException {

		return dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(AccountEntryTable.INSTANCE),
				userId, parentAccountEntryId, keywords, types, status
			).limit(
				start, end
			));
	}

	@Override
	public int getUserAccountEntriesCount(
			long userId, Long parentAccountEntryId, String keywords,
			String[] types)
		throws PortalException {

		return getUserAccountEntriesCount(
			userId, parentAccountEntryId, keywords, types,
			WorkflowConstants.STATUS_ANY);
	}

	@Override
	public int getUserAccountEntriesCount(
			long userId, Long parentAccountEntryId, String keywords,
			String[] types, Integer status)
		throws PortalException {

		return accountEntryPersistence.dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					AccountEntryTable.INSTANCE.accountEntryId),
				userId, parentAccountEntryId, keywords, types, status));
	}

	@Override
	public BaseModelSearchResult<AccountEntry> searchAccountEntries(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int cur, int delta, String orderByField, boolean reverse) {

		SearchResponse searchResponse = _searcher.search(
			_getSearchRequest(
				companyId, keywords, params, cur, delta, orderByField,
				reverse));

		SearchHits searchHits = searchResponse.getSearchHits();

		List<AccountEntry> accountEntries = TransformUtil.transform(
			searchHits.getSearchHits(),
			searchHit -> {
				Document document = searchHit.getDocument();

				long accountEntryId = document.getLong(Field.ENTRY_CLASS_PK);

				AccountEntry accountEntry = fetchAccountEntry(accountEntryId);

				if (accountEntry == null) {
					Indexer<AccountEntry> indexer =
						IndexerRegistryUtil.getIndexer(AccountEntry.class);

					indexer.delete(
						document.getLong(Field.COMPANY_ID),
						document.getString(Field.UID));
				}

				return accountEntry;
			});

		return new BaseModelSearchResult<>(
			accountEntries, searchResponse.getTotalHits());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #updateAccountEntry(Long, long, String, String, boolean,
	 *             String[], byte[], String, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public AccountEntry updateAccountEntry(
			Long accountEntryId, long parentAccountEntryId, String name,
			String description, boolean deleteLogo, String[] domains,
			byte[] logoBytes, int status)
		throws PortalException {

		return updateAccountEntry(
			accountEntryId, parentAccountEntryId, name, description, deleteLogo,
			domains, logoBytes, null, status, null);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #updateAccountEntry(Long, long, String, String, boolean,
	 *             String[], byte[], String, int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public AccountEntry updateAccountEntry(
			Long accountEntryId, long parentAccountEntryId, String name,
			String description, boolean deleteLogo, String[] domains,
			byte[] logoBytes, int status, ServiceContext serviceContext)
		throws PortalException {

		return updateAccountEntry(
			accountEntryId, parentAccountEntryId, name, description, deleteLogo,
			domains, logoBytes, null, status, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public AccountEntry updateAccountEntry(
			Long accountEntryId, long parentAccountEntryId, String name,
			String description, boolean deleteLogo, String[] domains,
			byte[] logoBytes, String taxIdNumber, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return updateAccountEntry(
			accountEntryId, parentAccountEntryId, name, description, deleteLogo,
			domains, null, logoBytes, taxIdNumber, status, serviceContext);
	}

	@Override
	public AccountEntry updateAccountEntry(
			Long accountEntryId, long parentAccountEntryId, String name,
			String description, boolean deleteLogo, String[] domains,
			String emailAddress, byte[] logoBytes, String taxIdNumber,
			int status, ServiceContext serviceContext)
		throws PortalException {

		AccountEntry accountEntry = accountEntryPersistence.fetchByPrimaryKey(
			accountEntryId);

		accountEntry.setParentAccountEntryId(parentAccountEntryId);

		_validateName(name);

		accountEntry.setName(name);

		accountEntry.setDescription(description);

		domains = _validateDomains(domains);

		accountEntry.setDomains(StringUtil.merge(domains, StringPool.COMMA));

		_validateEmailAddress(emailAddress);

		accountEntry.setEmailAddress(emailAddress);

		_portal.updateImageId(
			accountEntry, !deleteLogo, logoBytes, "logoId",
			_userFileUploadsSettings.getImageMaxSize(),
			_userFileUploadsSettings.getImageMaxHeight(),
			_userFileUploadsSettings.getImageMaxWidth());

		accountEntry.setTaxIdNumber(taxIdNumber);
		accountEntry.setStatus(status);

		if (serviceContext != null) {

			// Asset

			_updateAsset(accountEntry, serviceContext);

			// Expando

			accountEntry.setExpandoBridgeAttributes(serviceContext);
		}

		return accountEntryPersistence.update(accountEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AccountEntry updateDefaultBillingAddressId(
			long accountEntryId, long addressId)
		throws PortalException {

		AccountEntry accountEntry = getAccountEntry(accountEntryId);

		accountEntry.setDefaultBillingAddressId(addressId);

		return updateAccountEntry(accountEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AccountEntry updateDefaultShippingAddressId(
			long accountEntryId, long addressId)
		throws PortalException {

		AccountEntry accountEntry = getAccountEntry(accountEntryId);

		accountEntry.setDefaultShippingAddressId(addressId);

		return updateAccountEntry(accountEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AccountEntry updateExternalReferenceCode(
			AccountEntry accountEntry, String externalReferenceCode)
		throws PortalException {

		if (Objects.equals(
				accountEntry.getExternalReferenceCode(),
				externalReferenceCode)) {

			return accountEntry;
		}

		_validateExternalReferenceCode(
			accountEntry.getAccountEntryId(), externalReferenceCode);

		accountEntry.setExternalReferenceCode(externalReferenceCode);

		return updateAccountEntry(accountEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AccountEntry updateExternalReferenceCode(
			long accountEntryId, String externalReferenceCode)
		throws PortalException {

		return updateExternalReferenceCode(
			getAccountEntry(accountEntryId), externalReferenceCode);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AccountEntry updateStatus(AccountEntry accountEntry, int status) {
		accountEntry.setStatus(status);

		return updateAccountEntry(accountEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AccountEntry updateStatus(long accountEntryId, int status)
		throws PortalException {

		return updateStatus(getAccountEntry(accountEntryId), status);
	}

	private GroupByStep _getGroupByStep(
			FromStep fromStep, long userId, Long parentAccountId,
			String keywords, String[] types, Integer status)
		throws PortalException {

		JoinStep joinStep = fromStep.from(
			UserTable.INSTANCE
		).leftJoinOn(
			AccountEntryUserRelTable.INSTANCE,
			AccountEntryUserRelTable.INSTANCE.accountUserId.eq(
				UserTable.INSTANCE.userId)
		);

		Long[] organizationIds = _getOrganizationIds(userId);

		if (ArrayUtil.isNotEmpty(organizationIds)) {
			joinStep = joinStep.leftJoinOn(
				AccountEntryOrganizationRelTable.INSTANCE,
				AccountEntryOrganizationRelTable.INSTANCE.organizationId.in(
					organizationIds));
		}

		Predicate accountEntryPredicate =
			AccountEntryTable.INSTANCE.accountEntryId.eq(
				AccountEntryUserRelTable.INSTANCE.accountEntryId
			).or(
				AccountEntryTable.INSTANCE.userId.eq(userId)
			);

		if (ArrayUtil.isNotEmpty(organizationIds)) {
			accountEntryPredicate = accountEntryPredicate.or(
				AccountEntryTable.INSTANCE.accountEntryId.eq(
					AccountEntryOrganizationRelTable.INSTANCE.accountEntryId));
		}

		joinStep = joinStep.leftJoinOn(
			AccountEntryTable.INSTANCE, accountEntryPredicate);

		return joinStep.where(
			() -> {
				Predicate predicate = UserTable.INSTANCE.userId.eq(userId);

				if (parentAccountId != null) {
					predicate = predicate.and(
						AccountEntryTable.INSTANCE.parentAccountEntryId.eq(
							parentAccountId));
				}

				if (Validator.isNotNull(keywords)) {
					Predicate keywordsPredicate =
						_customSQL.getKeywordsPredicate(
							DSLFunctionFactoryUtil.lower(
								AccountEntryTable.INSTANCE.name),
							_customSQL.keywords(keywords, true));

					if (Validator.isDigit(keywords)) {
						keywordsPredicate = Predicate.or(
							AccountEntryTable.INSTANCE.accountEntryId.eq(
								Long.valueOf(keywords)),
							keywordsPredicate);
					}

					keywordsPredicate = Predicate.or(
						AccountEntryTable.INSTANCE.externalReferenceCode.eq(
							keywords),
						keywordsPredicate);

					predicate = predicate.and(
						Predicate.withParentheses(keywordsPredicate));
				}

				if (types != null) {
					predicate = predicate.and(
						AccountEntryTable.INSTANCE.type.in(types));
				}

				if ((status != null) &&
					(status != WorkflowConstants.STATUS_ANY)) {

					predicate = predicate.and(
						AccountEntryTable.INSTANCE.status.eq(status));
				}

				return predicate;
			});
	}

	private Long[] _getOrganizationIds(long userId) {
		List<Organization> organizations =
			organizationLocalService.getUserOrganizations(userId);

		ListIterator<Organization> listIterator = organizations.listIterator();

		while (listIterator.hasNext()) {
			Organization organization = listIterator.next();

			for (Organization curOrganization :
					organizationLocalService.getOrganizations(
						organization.getCompanyId(),
						organization.getTreePath() + "%")) {

				listIterator.add(curOrganization);
			}
		}

		Stream<Organization> stream = organizations.stream();

		return stream.map(
			Organization::getOrganizationId
		).distinct(
		).toArray(
			Long[]::new
		);
	}

	private SearchRequest _getSearchRequest(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int cur, int delta, String orderByField, boolean reverse) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.entryClassNames(
			AccountEntry.class.getName()
		).emptySearchEnabled(
			true
		).highlightEnabled(
			false
		).withSearchContext(
			searchContext -> _populateSearchContext(
				searchContext, companyId, keywords, params)
		);

		if (cur != QueryUtil.ALL_POS) {
			searchRequestBuilder.from(cur);
			searchRequestBuilder.size(delta);
		}

		if (Validator.isNotNull(orderByField)) {
			SortOrder sortOrder = SortOrder.ASC;

			if (reverse) {
				sortOrder = SortOrder.DESC;
			}

			FieldSort fieldSort = _sorts.field(
				_sortFieldBuilder.getSortField(
					AccountEntry.class.getName(), orderByField),
				sortOrder);

			searchRequestBuilder.sorts(fieldSort);
		}

		return searchRequestBuilder.build();
	}

	private void _performActions(
			long[] accountEntryIds,
			ActionableDynamicQuery.PerformActionMethod<AccountEntry>
				performActionMethod)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.in(
					"accountEntryId", ArrayUtil.toArray(accountEntryIds))));
		actionableDynamicQuery.setPerformActionMethod(performActionMethod);

		actionableDynamicQuery.performActions();
	}

	private void _populateSearchContext(
		SearchContext searchContext, long companyId, String keywords,
		LinkedHashMap<String, Object> params) {

		searchContext.setCompanyId(companyId);

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (MapUtil.isEmpty(params)) {
			return;
		}

		long[] accountGroupIds = (long[])params.get("accountGroupIds");

		if (ArrayUtil.isNotEmpty(accountGroupIds)) {
			searchContext.setAttribute("accountGroupIds", accountGroupIds);
		}

		long[] accountUserIds = (long[])params.get("accountUserIds");

		if (ArrayUtil.isNotEmpty(accountUserIds)) {
			searchContext.setAttribute("accountUserIds", accountUserIds);
		}

		String[] domains = (String[])params.get("domains");

		if (ArrayUtil.isNotEmpty(domains)) {
			searchContext.setAttribute("domains", domains);
		}

		long[] organizationIds = (long[])params.get("organizationIds");

		if (ArrayUtil.isNotEmpty(organizationIds)) {
			searchContext.setAttribute("organizationIds", organizationIds);
		}

		long parentAccountEntryId = GetterUtil.getLong(
			params.get("parentAccountEntryId"),
			AccountConstants.ACCOUNT_ENTRY_ID_ANY);

		if (parentAccountEntryId != AccountConstants.ACCOUNT_ENTRY_ID_ANY) {
			searchContext.setAttribute(
				"parentAccountEntryId", parentAccountEntryId);
		}

		int status = GetterUtil.getInteger(
			params.get("status"), WorkflowConstants.STATUS_APPROVED);

		searchContext.setAttribute(Field.STATUS, status);

		String type = (String)params.get("type");

		if (Validator.isNotNull(type)) {
			searchContext.setAttribute(Field.TYPE, type);
		}
	}

	private void _updateAsset(
			AccountEntry accountEntry, ServiceContext serviceContext)
		throws PortalException {

		Company company = _companyLocalService.getCompany(
			serviceContext.getCompanyId());

		assetEntryLocalService.updateEntry(
			serviceContext.getUserId(), company.getGroupId(),
			accountEntry.getCreateDate(), accountEntry.getModifiedDate(),
			AccountEntry.class.getName(), accountEntry.getAccountEntryId(),
			null, 0, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), true, true, null, null, null,
			null, null, accountEntry.getName(), accountEntry.getDescription(),
			null, null, null, 0, 0, null);
	}

	private String[] _validateDomains(String[] domains) throws PortalException {
		if (ArrayUtil.isEmpty(domains)) {
			return domains;
		}

		DomainValidator domainValidator = DomainValidator.getInstance();

		for (String domain : domains) {
			if (!domainValidator.isValid(domain)) {
				throw new AccountEntryDomainsException();
			}
		}

		return ArrayUtil.distinct(domains);
	}

	private void _validateEmailAddress(String emailAddress)
		throws AccountEntryEmailAddressException {

		if (Validator.isNotNull(emailAddress)) {
			EmailValidator emailValidator = EmailValidator.getInstance();

			if (!emailValidator.isValid(emailAddress)) {
				throw new AccountEntryEmailAddressException();
			}
		}
	}

	private void _validateExternalReferenceCode(
			long accountEntryId, String externalReferenceCode)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		AccountEntry accountEntry = getAccountEntry(accountEntryId);

		accountEntry = fetchAccountEntryByExternalReferenceCode(
			accountEntry.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			return;
		}

		if (accountEntry.getAccountEntryId() != accountEntryId) {
			throw new DuplicateAccountEntryExternalReferenceCodeException();
		}
	}

	private void _validateName(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new AccountEntryNameException();
		}
	}

	private void _validateType(String type) throws PortalException {
		if (!ArrayUtil.contains(AccountConstants.ACCOUNT_ENTRY_TYPES, type)) {
			throw new AccountEntryTypeException();
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private Portal _portal;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SortFieldBuilder _sortFieldBuilder;

	@Reference
	private Sorts _sorts;

	@Reference
	private UserFileUploadsSettings _userFileUploadsSettings;

}
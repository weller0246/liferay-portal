/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service.persistence;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the cp definition diagram entry service. This utility wraps <code>com.liferay.commerce.shop.by.diagram.service.persistence.impl.CPDefinitionDiagramEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramEntryPersistence
 * @generated
 */
public class CPDefinitionDiagramEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		getPersistence().clearCache(cpDefinitionDiagramEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CPDefinitionDiagramEntry>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPDefinitionDiagramEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionDiagramEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionDiagramEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionDiagramEntry update(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		return getPersistence().update(cpDefinitionDiagramEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionDiagramEntry update(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(
			cpDefinitionDiagramEntry, serviceContext);
	}

	/**
	 * Returns all the cp definition diagram entries where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram entries
	 */
	public static List<CPDefinitionDiagramEntry> findByCPDefinitionId(
		long CPDefinitionId) {

		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns a range of all the cp definition diagram entries where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @return the range of matching cp definition diagram entries
	 */
	public static List<CPDefinitionDiagramEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {

		return getPersistence().findByCPDefinitionId(
			CPDefinitionId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram entries where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram entries
	 */
	public static List<CPDefinitionDiagramEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator) {

		return getPersistence().findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram entries where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram entries
	 */
	public static List<CPDefinitionDiagramEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition diagram entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a matching cp definition diagram entry could not be found
	 */
	public static CPDefinitionDiagramEntry findByCPDefinitionId_First(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionDiagramEntry> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramEntryException {

		return getPersistence().findByCPDefinitionId_First(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the first cp definition diagram entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram entry, or <code>null</code> if a matching cp definition diagram entry could not be found
	 */
	public static CPDefinitionDiagramEntry fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator) {

		return getPersistence().fetchByCPDefinitionId_First(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last cp definition diagram entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a matching cp definition diagram entry could not be found
	 */
	public static CPDefinitionDiagramEntry findByCPDefinitionId_Last(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionDiagramEntry> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramEntryException {

		return getPersistence().findByCPDefinitionId_Last(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last cp definition diagram entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram entry, or <code>null</code> if a matching cp definition diagram entry could not be found
	 */
	public static CPDefinitionDiagramEntry fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator) {

		return getPersistence().fetchByCPDefinitionId_Last(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the cp definition diagram entries before and after the current cp definition diagram entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the current cp definition diagram entry
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a cp definition diagram entry with the primary key could not be found
	 */
	public static CPDefinitionDiagramEntry[] findByCPDefinitionId_PrevAndNext(
			long CPDefinitionDiagramEntryId, long CPDefinitionId,
			OrderByComparator<CPDefinitionDiagramEntry> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramEntryException {

		return getPersistence().findByCPDefinitionId_PrevAndNext(
			CPDefinitionDiagramEntryId, CPDefinitionId, orderByComparator);
	}

	/**
	 * Removes all the cp definition diagram entries where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	public static void removeByCPDefinitionId(long CPDefinitionId) {
		getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the number of cp definition diagram entries where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition diagram entries
	 */
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the cp definition diagram entry where CPDefinitionId = &#63; and sequence = &#63; or throws a <code>NoSuchCPDefinitionDiagramEntryException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sequence the sequence
	 * @return the matching cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a matching cp definition diagram entry could not be found
	 */
	public static CPDefinitionDiagramEntry findByCPDI_S(
			long CPDefinitionId, String sequence)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramEntryException {

		return getPersistence().findByCPDI_S(CPDefinitionId, sequence);
	}

	/**
	 * Returns the cp definition diagram entry where CPDefinitionId = &#63; and sequence = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sequence the sequence
	 * @return the matching cp definition diagram entry, or <code>null</code> if a matching cp definition diagram entry could not be found
	 */
	public static CPDefinitionDiagramEntry fetchByCPDI_S(
		long CPDefinitionId, String sequence) {

		return getPersistence().fetchByCPDI_S(CPDefinitionId, sequence);
	}

	/**
	 * Returns the cp definition diagram entry where CPDefinitionId = &#63; and sequence = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sequence the sequence
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition diagram entry, or <code>null</code> if a matching cp definition diagram entry could not be found
	 */
	public static CPDefinitionDiagramEntry fetchByCPDI_S(
		long CPDefinitionId, String sequence, boolean useFinderCache) {

		return getPersistence().fetchByCPDI_S(
			CPDefinitionId, sequence, useFinderCache);
	}

	/**
	 * Removes the cp definition diagram entry where CPDefinitionId = &#63; and sequence = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sequence the sequence
	 * @return the cp definition diagram entry that was removed
	 */
	public static CPDefinitionDiagramEntry removeByCPDI_S(
			long CPDefinitionId, String sequence)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramEntryException {

		return getPersistence().removeByCPDI_S(CPDefinitionId, sequence);
	}

	/**
	 * Returns the number of cp definition diagram entries where CPDefinitionId = &#63; and sequence = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param sequence the sequence
	 * @return the number of matching cp definition diagram entries
	 */
	public static int countByCPDI_S(long CPDefinitionId, String sequence) {
		return getPersistence().countByCPDI_S(CPDefinitionId, sequence);
	}

	/**
	 * Caches the cp definition diagram entry in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramEntry the cp definition diagram entry
	 */
	public static void cacheResult(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		getPersistence().cacheResult(cpDefinitionDiagramEntry);
	}

	/**
	 * Caches the cp definition diagram entries in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramEntries the cp definition diagram entries
	 */
	public static void cacheResult(
		List<CPDefinitionDiagramEntry> cpDefinitionDiagramEntries) {

		getPersistence().cacheResult(cpDefinitionDiagramEntries);
	}

	/**
	 * Creates a new cp definition diagram entry with the primary key. Does not add the cp definition diagram entry to the database.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key for the new cp definition diagram entry
	 * @return the new cp definition diagram entry
	 */
	public static CPDefinitionDiagramEntry create(
		long CPDefinitionDiagramEntryId) {

		return getPersistence().create(CPDefinitionDiagramEntryId);
	}

	/**
	 * Removes the cp definition diagram entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry that was removed
	 * @throws NoSuchCPDefinitionDiagramEntryException if a cp definition diagram entry with the primary key could not be found
	 */
	public static CPDefinitionDiagramEntry remove(
			long CPDefinitionDiagramEntryId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramEntryException {

		return getPersistence().remove(CPDefinitionDiagramEntryId);
	}

	public static CPDefinitionDiagramEntry updateImpl(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		return getPersistence().updateImpl(cpDefinitionDiagramEntry);
	}

	/**
	 * Returns the cp definition diagram entry with the primary key or throws a <code>NoSuchCPDefinitionDiagramEntryException</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a cp definition diagram entry with the primary key could not be found
	 */
	public static CPDefinitionDiagramEntry findByPrimaryKey(
			long CPDefinitionDiagramEntryId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramEntryException {

		return getPersistence().findByPrimaryKey(CPDefinitionDiagramEntryId);
	}

	/**
	 * Returns the cp definition diagram entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry, or <code>null</code> if a cp definition diagram entry with the primary key could not be found
	 */
	public static CPDefinitionDiagramEntry fetchByPrimaryKey(
		long CPDefinitionDiagramEntryId) {

		return getPersistence().fetchByPrimaryKey(CPDefinitionDiagramEntryId);
	}

	/**
	 * Returns all the cp definition diagram entries.
	 *
	 * @return the cp definition diagram entries
	 */
	public static List<CPDefinitionDiagramEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cp definition diagram entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @return the range of cp definition diagram entries
	 */
	public static List<CPDefinitionDiagramEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition diagram entries
	 */
	public static List<CPDefinitionDiagramEntry> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition diagram entries
	 */
	public static List<CPDefinitionDiagramEntry> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp definition diagram entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp definition diagram entries.
	 *
	 * @return the number of cp definition diagram entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPDefinitionDiagramEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CPDefinitionDiagramEntryPersistence,
		 CPDefinitionDiagramEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CPDefinitionDiagramEntryPersistence.class);

		ServiceTracker
			<CPDefinitionDiagramEntryPersistence,
			 CPDefinitionDiagramEntryPersistence> serviceTracker =
				new ServiceTracker
					<CPDefinitionDiagramEntryPersistence,
					 CPDefinitionDiagramEntryPersistence>(
						 bundle.getBundleContext(),
						 CPDefinitionDiagramEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
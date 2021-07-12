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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchCacheMissEntryException;
import com.liferay.portal.tools.service.builder.test.model.CacheMissEntry;
import com.liferay.portal.tools.service.builder.test.model.CacheMissEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.CacheMissEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.CacheMissEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.CacheMissEntryPersistence;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the cache miss entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CacheMissEntryPersistenceImpl
	extends BasePersistenceImpl<CacheMissEntry>
	implements CacheMissEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CacheMissEntryUtil</code> to access the cache miss entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CacheMissEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public CacheMissEntryPersistenceImpl() {
		setModelClass(CacheMissEntry.class);

		setModelImplClass(CacheMissEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CacheMissEntryTable.INSTANCE);
	}

	/**
	 * Caches the cache miss entry in the entity cache if it is enabled.
	 *
	 * @param cacheMissEntry the cache miss entry
	 */
	@Override
	public void cacheResult(CacheMissEntry cacheMissEntry) {
		dummyEntityCache.putResult(
			CacheMissEntryImpl.class, cacheMissEntry.getPrimaryKey(),
			cacheMissEntry);
	}

	/**
	 * Caches the cache miss entries in the entity cache if it is enabled.
	 *
	 * @param cacheMissEntries the cache miss entries
	 */
	@Override
	public void cacheResult(List<CacheMissEntry> cacheMissEntries) {
		for (CacheMissEntry cacheMissEntry : cacheMissEntries) {
			if (dummyEntityCache.getResult(
					CacheMissEntryImpl.class, cacheMissEntry.getPrimaryKey()) ==
						null) {

				cacheResult(cacheMissEntry);
			}
		}
	}

	/**
	 * Clears the cache for all cache miss entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		dummyEntityCache.clearCache(CacheMissEntryImpl.class);

		dummyFinderCache.clearCache(CacheMissEntryImpl.class);
	}

	/**
	 * Clears the cache for the cache miss entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CacheMissEntry cacheMissEntry) {
		dummyEntityCache.removeResult(CacheMissEntryImpl.class, cacheMissEntry);
	}

	@Override
	public void clearCache(List<CacheMissEntry> cacheMissEntries) {
		for (CacheMissEntry cacheMissEntry : cacheMissEntries) {
			dummyEntityCache.removeResult(
				CacheMissEntryImpl.class, cacheMissEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		dummyFinderCache.clearCache(CacheMissEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			dummyEntityCache.removeResult(CacheMissEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new cache miss entry with the primary key. Does not add the cache miss entry to the database.
	 *
	 * @param cacheMissEntryId the primary key for the new cache miss entry
	 * @return the new cache miss entry
	 */
	@Override
	public CacheMissEntry create(long cacheMissEntryId) {
		CacheMissEntry cacheMissEntry = new CacheMissEntryImpl();

		cacheMissEntry.setNew(true);
		cacheMissEntry.setPrimaryKey(cacheMissEntryId);

		return cacheMissEntry;
	}

	/**
	 * Removes the cache miss entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry that was removed
	 * @throws NoSuchCacheMissEntryException if a cache miss entry with the primary key could not be found
	 */
	@Override
	public CacheMissEntry remove(long cacheMissEntryId)
		throws NoSuchCacheMissEntryException {

		return remove((Serializable)cacheMissEntryId);
	}

	/**
	 * Removes the cache miss entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cache miss entry
	 * @return the cache miss entry that was removed
	 * @throws NoSuchCacheMissEntryException if a cache miss entry with the primary key could not be found
	 */
	@Override
	public CacheMissEntry remove(Serializable primaryKey)
		throws NoSuchCacheMissEntryException {

		Session session = null;

		try {
			session = openSession();

			CacheMissEntry cacheMissEntry = (CacheMissEntry)session.get(
				CacheMissEntryImpl.class, primaryKey);

			if (cacheMissEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCacheMissEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cacheMissEntry);
		}
		catch (NoSuchCacheMissEntryException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CacheMissEntry removeImpl(CacheMissEntry cacheMissEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cacheMissEntry)) {
				cacheMissEntry = (CacheMissEntry)session.get(
					CacheMissEntryImpl.class,
					cacheMissEntry.getPrimaryKeyObj());
			}

			if (cacheMissEntry != null) {
				session.delete(cacheMissEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cacheMissEntry != null) {
			clearCache(cacheMissEntry);
		}

		return cacheMissEntry;
	}

	@Override
	public CacheMissEntry updateImpl(CacheMissEntry cacheMissEntry) {
		boolean isNew = cacheMissEntry.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cacheMissEntry);
			}
			else {
				cacheMissEntry = (CacheMissEntry)session.merge(cacheMissEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		dummyEntityCache.putResult(
			CacheMissEntryImpl.class, cacheMissEntry, false, true);

		if (isNew) {
			cacheMissEntry.setNew(false);
		}

		cacheMissEntry.resetOriginalValues();

		return cacheMissEntry;
	}

	/**
	 * Returns the cache miss entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cache miss entry
	 * @return the cache miss entry
	 * @throws NoSuchCacheMissEntryException if a cache miss entry with the primary key could not be found
	 */
	@Override
	public CacheMissEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCacheMissEntryException {

		CacheMissEntry cacheMissEntry = fetchByPrimaryKey(primaryKey);

		if (cacheMissEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCacheMissEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cacheMissEntry;
	}

	/**
	 * Returns the cache miss entry with the primary key or throws a <code>NoSuchCacheMissEntryException</code> if it could not be found.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry
	 * @throws NoSuchCacheMissEntryException if a cache miss entry with the primary key could not be found
	 */
	@Override
	public CacheMissEntry findByPrimaryKey(long cacheMissEntryId)
		throws NoSuchCacheMissEntryException {

		return findByPrimaryKey((Serializable)cacheMissEntryId);
	}

	/**
	 * Returns the cache miss entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry, or <code>null</code> if a cache miss entry with the primary key could not be found
	 */
	@Override
	public CacheMissEntry fetchByPrimaryKey(long cacheMissEntryId) {
		return fetchByPrimaryKey((Serializable)cacheMissEntryId);
	}

	/**
	 * Returns all the cache miss entries.
	 *
	 * @return the cache miss entries
	 */
	@Override
	public List<CacheMissEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cache miss entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheMissEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache miss entries
	 * @param end the upper bound of the range of cache miss entries (not inclusive)
	 * @return the range of cache miss entries
	 */
	@Override
	public List<CacheMissEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cache miss entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheMissEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache miss entries
	 * @param end the upper bound of the range of cache miss entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cache miss entries
	 */
	@Override
	public List<CacheMissEntry> findAll(
		int start, int end,
		OrderByComparator<CacheMissEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cache miss entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheMissEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache miss entries
	 * @param end the upper bound of the range of cache miss entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cache miss entries
	 */
	@Override
	public List<CacheMissEntry> findAll(
		int start, int end, OrderByComparator<CacheMissEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CacheMissEntry> list = null;

		if (useFinderCache) {
			list = (List<CacheMissEntry>)dummyFinderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CACHEMISSENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CACHEMISSENTRY;

				sql = sql.concat(CacheMissEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CacheMissEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					dummyFinderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the cache miss entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CacheMissEntry cacheMissEntry : findAll()) {
			remove(cacheMissEntry);
		}
	}

	/**
	 * Returns the number of cache miss entries.
	 *
	 * @return the number of cache miss entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)dummyFinderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CACHEMISSENTRY);

				count = (Long)query.uniqueResult();

				dummyFinderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return dummyEntityCache;
	}

	@Override
	protected String getPKDBName() {
		return "cacheMissEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CACHEMISSENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CacheMissEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cache miss entry persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CacheMissEntryPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class, new CacheMissEntryModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);
	}

	public void destroy() {
		dummyEntityCache.removeCache(CacheMissEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	private static final String _SQL_SELECT_CACHEMISSENTRY =
		"SELECT cacheMissEntry FROM CacheMissEntry cacheMissEntry";

	private static final String _SQL_COUNT_CACHEMISSENTRY =
		"SELECT COUNT(cacheMissEntry) FROM CacheMissEntry cacheMissEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "cacheMissEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CacheMissEntry exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		CacheMissEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return dummyFinderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CacheMissEntryModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CacheMissEntryModelImpl cacheMissEntryModelImpl =
				(CacheMissEntryModelImpl)baseModel;

			long columnBitmask = cacheMissEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					cacheMissEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						cacheMissEntryModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					cacheMissEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CacheMissEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CacheMissEntryTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			CacheMissEntryModelImpl cacheMissEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						cacheMissEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = cacheMissEntryModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

	}

}
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

package com.liferay.search.experiences.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for SXPBlueprint. This utility wraps
 * <code>com.liferay.search.experiences.service.impl.SXPBlueprintLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SXPBlueprintLocalService
 * @generated
 */
public class SXPBlueprintLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.search.experiences.service.impl.SXPBlueprintLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the sxp blueprint to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SXPBlueprintLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param sxpBlueprint the sxp blueprint
	 * @return the sxp blueprint that was added
	 */
	public static SXPBlueprint addSXPBlueprint(SXPBlueprint sxpBlueprint) {
		return getService().addSXPBlueprint(sxpBlueprint);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new sxp blueprint with the primary key. Does not add the sxp blueprint to the database.
	 *
	 * @param sxpBlueprintId the primary key for the new sxp blueprint
	 * @return the new sxp blueprint
	 */
	public static SXPBlueprint createSXPBlueprint(long sxpBlueprintId) {
		return getService().createSXPBlueprint(sxpBlueprintId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the sxp blueprint with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SXPBlueprintLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param sxpBlueprintId the primary key of the sxp blueprint
	 * @return the sxp blueprint that was removed
	 * @throws PortalException if a sxp blueprint with the primary key could not be found
	 */
	public static SXPBlueprint deleteSXPBlueprint(long sxpBlueprintId)
		throws PortalException {

		return getService().deleteSXPBlueprint(sxpBlueprintId);
	}

	/**
	 * Deletes the sxp blueprint from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SXPBlueprintLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param sxpBlueprint the sxp blueprint
	 * @return the sxp blueprint that was removed
	 */
	public static SXPBlueprint deleteSXPBlueprint(SXPBlueprint sxpBlueprint) {
		return getService().deleteSXPBlueprint(sxpBlueprint);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.search.experiences.model.impl.SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.search.experiences.model.impl.SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static SXPBlueprint fetchSXPBlueprint(long sxpBlueprintId) {
		return getService().fetchSXPBlueprint(sxpBlueprintId);
	}

	/**
	 * Returns the sxp blueprint matching the UUID and group.
	 *
	 * @param uuid the sxp blueprint's UUID
	 * @param groupId the primary key of the group
	 * @return the matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchSXPBlueprintByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchSXPBlueprintByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the sxp blueprint with the primary key.
	 *
	 * @param sxpBlueprintId the primary key of the sxp blueprint
	 * @return the sxp blueprint
	 * @throws PortalException if a sxp blueprint with the primary key could not be found
	 */
	public static SXPBlueprint getSXPBlueprint(long sxpBlueprintId)
		throws PortalException {

		return getService().getSXPBlueprint(sxpBlueprintId);
	}

	/**
	 * Returns the sxp blueprint matching the UUID and group.
	 *
	 * @param uuid the sxp blueprint's UUID
	 * @param groupId the primary key of the group
	 * @return the matching sxp blueprint
	 * @throws PortalException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint getSXPBlueprintByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getSXPBlueprintByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the sxp blueprints.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.search.experiences.model.impl.SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of sxp blueprints
	 */
	public static List<SXPBlueprint> getSXPBlueprints(int start, int end) {
		return getService().getSXPBlueprints(start, end);
	}

	/**
	 * Returns all the sxp blueprints matching the UUID and company.
	 *
	 * @param uuid the UUID of the sxp blueprints
	 * @param companyId the primary key of the company
	 * @return the matching sxp blueprints, or an empty list if no matches were found
	 */
	public static List<SXPBlueprint> getSXPBlueprintsByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getSXPBlueprintsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of sxp blueprints matching the UUID and company.
	 *
	 * @param uuid the UUID of the sxp blueprints
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching sxp blueprints, or an empty list if no matches were found
	 */
	public static List<SXPBlueprint> getSXPBlueprintsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getService().getSXPBlueprintsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of sxp blueprints.
	 *
	 * @return the number of sxp blueprints
	 */
	public static int getSXPBlueprintsCount() {
		return getService().getSXPBlueprintsCount();
	}

	/**
	 * Updates the sxp blueprint in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SXPBlueprintLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param sxpBlueprint the sxp blueprint
	 * @return the sxp blueprint that was updated
	 */
	public static SXPBlueprint updateSXPBlueprint(SXPBlueprint sxpBlueprint) {
		return getService().updateSXPBlueprint(sxpBlueprint);
	}

	public static SXPBlueprintLocalService getService() {
		return _service;
	}

	private static volatile SXPBlueprintLocalService _service;

}
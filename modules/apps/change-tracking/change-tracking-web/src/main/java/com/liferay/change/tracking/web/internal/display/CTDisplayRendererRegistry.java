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

package com.liferay.change.tracking.web.internal.display;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRendererRegistry.class)
public class CTDisplayRendererRegistry {

	public <T extends BaseModel<T>> T fetchCTModel(
		long ctCollectionId, CTSQLModeThreadLocal.CTSQLMode ctSQLMode,
		long modelClassNameId, long modelClassPK) {

		CTService<?> ctService = _ctServiceServiceTrackerMap.getService(
			modelClassNameId);

		if (ctService == null) {
			return null;
		}

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId);
			SafeCloseable safeCloseable2 =
				CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(ctSQLMode)) {

			return (T)ctService.updateWithUnsafeFunction(
				ctPersistence -> ctPersistence.fetchByPrimaryKey(modelClassPK));
		}
	}

	public <T extends BaseModel<T>> T fetchCTModel(
		long modelClassNameId, long modelClassPK) {

		return fetchCTModel(
			CTConstants.CT_COLLECTION_ID_PRODUCTION,
			CTSQLModeThreadLocal.CTSQLMode.DEFAULT, modelClassNameId,
			modelClassPK);
	}

	public <T extends BaseModel<T>> Map<Serializable, T> fetchCTModelMap(
		long ctCollectionId, CTSQLModeThreadLocal.CTSQLMode ctSQLMode,
		long modelClassNameId, Set<Long> primaryKeys) {

		CTService<?> ctService = _ctServiceServiceTrackerMap.getService(
			modelClassNameId);

		if (ctService == null) {
			return null;
		}

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId);
			SafeCloseable safeCloseable2 =
				CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(ctSQLMode)) {

			return (Map<Serializable, T>)ctService.updateWithUnsafeFunction(
				ctPersistence -> ctPersistence.fetchByPrimaryKeys(
					(Set)primaryKeys));
		}
	}

	public <T extends BaseModel<T>> String[] getAvailableLanguageIds(
		long ctCollectionId, CTSQLModeThreadLocal.CTSQLMode ctSQLMode, T model,
		long modelClassNameId) {

		CTDisplayRenderer<T> ctDisplayRenderer =
			(CTDisplayRenderer<T>)_ctDisplayServiceTrackerMap.getService(
				modelClassNameId);

		if (ctDisplayRenderer == null) {
			return null;
		}

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId);
			SafeCloseable safeCloseable2 =
				CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(ctSQLMode)) {

			return ctDisplayRenderer.getAvailableLanguageIds(model);
		}
	}

	public long getCtCollectionId(CTCollection ctCollection, CTEntry ctEntry)
		throws PortalException {

		if (ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return _ctEntryLocalService.getCTRowCTCollectionId(ctEntry);
		}
		else if (ctEntry.getChangeType() ==
					CTConstants.CT_CHANGE_TYPE_DELETION) {

			return CTConstants.CT_COLLECTION_ID_PRODUCTION;
		}

		return ctCollection.getCtCollectionId();
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseModel<T>> CTDisplayRenderer<T> getCTDisplayRenderer(
		long modelClassNameId) {

		CTDisplayRenderer<T> ctDisplayRenderer =
			(CTDisplayRenderer<T>)_ctDisplayServiceTrackerMap.getService(
				modelClassNameId);

		if (ctDisplayRenderer == null) {
			ctDisplayRenderer = getDefaultRenderer();
		}

		return ctDisplayRenderer;
	}

	public CTService<?> getCTService(CTModel<?> ctModel) {
		return _ctServiceServiceTrackerMap.getService(
			_classNameLocalService.getClassNameId(ctModel.getModelClass()));
	}

	public CTSQLModeThreadLocal.CTSQLMode getCTSQLMode(
		long ctCollectionId, CTEntry ctEntry) {

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return CTSQLModeThreadLocal.CTSQLMode.DEFAULT;
		}

		if (ctCollectionId != ctEntry.getCtCollectionId()) {
			ctEntry = _ctEntryLocalService.fetchCTEntry(
				ctCollectionId, ctEntry.getModelClassNameId(),
				ctEntry.getModelClassPK());

			if (ctEntry == null) {
				return CTSQLModeThreadLocal.CTSQLMode.DEFAULT;
			}
		}

		if (ctEntry.getChangeType() == CTConstants.CT_CHANGE_TYPE_DELETION) {
			return CTSQLModeThreadLocal.CTSQLMode.CT_ONLY;
		}

		return CTSQLModeThreadLocal.CTSQLMode.DEFAULT;
	}

	public <T extends BaseModel<T>> String getDefaultLanguageId(
		T model, long modelClassNameId) {

		CTDisplayRenderer<T> ctDisplayRenderer =
			(CTDisplayRenderer<T>)_ctDisplayServiceTrackerMap.getService(
				modelClassNameId);

		if (ctDisplayRenderer == null) {
			return null;
		}

		return ctDisplayRenderer.getDefaultLanguageId(model);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseModel<T>> CTDisplayRenderer<T> getDefaultRenderer() {
		return (CTDisplayRenderer<T>)_defaultCTDisplayRenderer;
	}

	public <T extends BaseModel<T>> String getEditURL(
		HttpServletRequest httpServletRequest, CTEntry ctEntry) {

		T model = fetchCTModel(
			ctEntry.getCtCollectionId(), CTSQLModeThreadLocal.CTSQLMode.DEFAULT,
			ctEntry.getModelClassNameId(), ctEntry.getModelClassPK());

		if (model == null) {
			return null;
		}

		return getEditURL(
			httpServletRequest, model, ctEntry.getModelClassNameId());
	}

	public <T extends BaseModel<T>> String getEditURL(
		HttpServletRequest httpServletRequest, T model, long modelClassNameId) {

		CTDisplayRenderer<T> ctDisplayRenderer =
			(CTDisplayRenderer<T>)_ctDisplayServiceTrackerMap.getService(
				modelClassNameId);

		if (ctDisplayRenderer == null) {
			return null;
		}

		try {
			return ctDisplayRenderer.getEditURL(httpServletRequest, model);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return null;
		}
	}

	public String getEntryDescription(
		HttpServletRequest httpServletRequest, CTEntry ctEntry) {

		String languageKey = "x-modified-a-x-x-ago";

		if (ctEntry.getChangeType() == CTConstants.CT_CHANGE_TYPE_ADDITION) {
			languageKey = "x-added-a-x-x-ago";
		}
		else if (ctEntry.getChangeType() ==
					CTConstants.CT_CHANGE_TYPE_DELETION) {

			languageKey = "x-deleted-a-x-x-ago";
		}

		Locale locale = _portal.getLocale(httpServletRequest);
		Date modifiedDate = ctEntry.getModifiedDate();

		return _language.format(
			httpServletRequest, languageKey,
			new Object[] {
				ctEntry.getUserName(),
				getTypeName(locale, ctEntry.getModelClassNameId()),
				_language.getTimeDescription(
					locale, System.currentTimeMillis() - modifiedDate.getTime(),
					true)
			},
			false);
	}

	public <T extends BaseModel<T>> String getTitle(
		long ctCollectionId, CTEntry ctEntry, Locale locale) {

		CTSQLModeThreadLocal.CTSQLMode ctSQLMode = getCTSQLMode(
			ctCollectionId, ctEntry);

		T model = fetchCTModel(
			ctCollectionId, ctSQLMode, ctEntry.getModelClassNameId(),
			ctEntry.getModelClassPK());

		if (model == null) {
			return StringBundler.concat(
				getTypeName(locale, ctEntry.getModelClassNameId()),
				StringPool.SPACE, ctEntry.getModelClassPK());
		}

		return getTitle(
			ctCollectionId, ctSQLMode, locale, model,
			ctEntry.getModelClassNameId());
	}

	public <T extends BaseModel<T>> String getTitle(
		long ctCollectionId, CTSQLModeThreadLocal.CTSQLMode ctSQLMode,
		Locale locale, T model, long modelClassNameId) {

		CTDisplayRenderer<T> ctDisplayRenderer =
			(CTDisplayRenderer<T>)_ctDisplayServiceTrackerMap.getService(
				modelClassNameId);

		String name = null;

		if (ctDisplayRenderer != null) {
			try (SafeCloseable safeCloseable1 =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctCollectionId);
				SafeCloseable safeCloseable2 =
					CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(
						ctSQLMode)) {

				name = ctDisplayRenderer.getTitle(locale, model);
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException, portalException);
				}

				String typeName = ctDisplayRenderer.getTypeName(locale);

				if (Validator.isNotNull(typeName)) {
					return StringBundler.concat(
						typeName, StringPool.SPACE, model.getPrimaryKeyObj());
				}
			}
		}

		if (Validator.isNotNull(name)) {
			return name;
		}

		return StringBundler.concat(
			getTypeName(locale, modelClassNameId), StringPool.SPACE,
			model.getPrimaryKeyObj());
	}

	public <T extends BaseModel<T>> String getTypeName(
		Locale locale, long modelClassNameId) {

		CTDisplayRenderer<T> ctDisplayRenderer =
			(CTDisplayRenderer<T>)_ctDisplayServiceTrackerMap.getService(
				modelClassNameId);

		String name = null;

		if (ctDisplayRenderer != null) {
			name = ctDisplayRenderer.getTypeName(locale);
		}

		if (Validator.isNull(name)) {
			ClassName className = _classNameLocalService.fetchClassName(
				modelClassNameId);

			if (className != null) {
				name = _resourceActions.getModelResource(
					locale, className.getClassName());

				if (name.startsWith(
						_resourceActions.getModelResourceNamePrefix())) {

					name = className.getClassName();
				}
			}
		}

		return name;
	}

	public <T extends BaseModel<T>> boolean isHideable(
		T model, long modelClassNameId) {

		CTDisplayRenderer<T> ctDisplayRenderer = getCTDisplayRenderer(
			modelClassNameId);

		return ctDisplayRenderer.isHideable(model);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_ctDisplayServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<CTDisplayRenderer<?>>)(Class<?>)CTDisplayRenderer.class,
				null,
				(serviceReference, emitter) -> {
					CTDisplayRenderer<?> ctDisplayRenderer =
						bundleContext.getService(serviceReference);

					try {
						emitter.emit(
							_classNameLocalService.getClassNameId(
								ctDisplayRenderer.getModelClass()));
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				});
		_ctServiceServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, (Class<CTService<?>>)(Class<?>)CTService.class,
				null,
				(serviceReference, emitter) -> {
					CTService<?> ctService = bundleContext.getService(
						serviceReference);

					emitter.emit(
						_classNameLocalService.getClassNameId(
							ctService.getModelClass()));
				});

		_defaultCTDisplayRenderer = new CTModelDisplayRendererAdapter<>(this);
	}

	@Deactivate
	protected void deactivate() {
		_ctDisplayServiceTrackerMap.close();
		_ctServiceServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTDisplayRendererRegistry.class);

	@Reference
	private BasePersistenceRegistry _basePersistenceRegistry;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private ServiceTrackerMap<Long, CTDisplayRenderer<?>>
		_ctDisplayServiceTrackerMap;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	private ServiceTrackerMap<Long, CTService<?>> _ctServiceServiceTrackerMap;
	private CTDisplayRenderer<?> _defaultCTDisplayRenderer;

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceActions _resourceActions;

}
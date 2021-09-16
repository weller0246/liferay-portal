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

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.WebImage;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.info.item.ObjectEntryInfoItemFields;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class ObjectEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<ObjectEntry> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(ObjectEntry objectEntry) {
		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getObjectEntryInfoFieldValues(objectEntry)
		).infoFieldValues(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				ObjectEntry.class.getName(), objectEntry)
		).infoItemReference(
			new InfoItemReference(
				ObjectEntry.class.getName(), objectEntry.getObjectEntryId())
		).build();
	}

	private String _getDisplayPageURL(
			ObjectEntry objectEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			ObjectEntry.class.getName(), objectEntry.getObjectEntryId(),
			themeDisplay);
	}

	private InfoFieldType _getInfoFieldType(ObjectField objectField) {
		if (Objects.equals(objectField.getType(), "Boolean")) {
			return BooleanInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(objectField.getType(), "BigDecimal") ||
				 Objects.equals(objectField.getType(), "Double") ||
				 Objects.equals(objectField.getType(), "Integer") ||
				 Objects.equals(objectField.getType(), "Long")) {

			return NumberInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(objectField.getType(), "Blob")) {
			return ImageInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(objectField.getType(), "Date")) {
			return DateInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(objectField.getType(), "String")) {
			return TextInfoFieldType.INSTANCE;
		}

		return TextInfoFieldType.INSTANCE;
	}

	private List<InfoFieldValue<Object>> _getObjectEntryInfoFieldValues(
		ObjectEntry objectEntry) {

		try {
			List<InfoFieldValue<Object>> objectEntryFieldValues =
				new ArrayList<>();

			objectEntryFieldValues.add(
				new InfoFieldValue<>(
					ObjectEntryInfoItemFields.createDateInfoField,
					objectEntry.getCreateDate()));
			objectEntryFieldValues.add(
				new InfoFieldValue<>(
					ObjectEntryInfoItemFields.modifiedDateInfoField,
					objectEntry.getModifiedDate()));
			objectEntryFieldValues.add(
				new InfoFieldValue<>(
					ObjectEntryInfoItemFields.publishDateInfoField,
					objectEntry.getLastPublishDate()));
			objectEntryFieldValues.add(
				new InfoFieldValue<>(
					ObjectEntryInfoItemFields.userNameInfoField,
					objectEntry.getUserName()));
			objectEntryFieldValues.add(
				new InfoFieldValue<>(
					ObjectEntryInfoItemFields.userProfileImage,
					_getWebImage(objectEntry.getUserId())));

			ThemeDisplay themeDisplay = _getThemeDisplay();

			if (themeDisplay != null) {
				objectEntryFieldValues.add(
					new InfoFieldValue<>(
						ObjectEntryInfoItemFields.displayPageURLInfoField,
						_getDisplayPageURL(objectEntry, themeDisplay)));
			}

			Map<String, Serializable> values = objectEntry.getValues();

			objectEntryFieldValues.addAll(
				TransformUtil.transform(
					_objectFieldLocalService.getObjectFields(
						objectEntry.getObjectDefinitionId()),
					objectField -> new InfoFieldValue<>(
						InfoField.builder(
						).infoFieldType(
							_getInfoFieldType(objectField)
						).name(
							objectField.getName()
						).labelInfoLocalizedValue(
							InfoLocalizedValue.<String>builder(
							).values(
								objectField.getLabelMap()
							).build()
						).build(),
						_getValue(objectField, values))));

			return objectEntryFieldValues;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	private Object _getValue(
			ObjectField objectField, Map<String, Serializable> values)
		throws PortalException {

		if (Objects.equals(
				_getInfoFieldType(objectField), ImageInfoFieldType.INSTANCE)) {

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				new String((byte[])values.get(objectField.getName())));

			WebImage webImage = new WebImage(jsonObject.getString("url"));

			webImage.setAlt(jsonObject.getString("alt"));

			return webImage;
		}

		return values.get(objectField.getName());
	}

	private WebImage _getWebImage(long userId) throws PortalException {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			return null;
		}

		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay != null) {
			WebImage webImage = new WebImage(user.getPortraitURL(themeDisplay));

			webImage.setAlt(user.getFullName());

			return webImage;
		}

		return null;
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
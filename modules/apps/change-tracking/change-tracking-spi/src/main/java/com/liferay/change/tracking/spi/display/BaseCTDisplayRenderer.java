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

package com.liferay.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

import java.sql.Blob;

import java.text.Format;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Preston Crary
 */
public abstract class BaseCTDisplayRenderer<T extends BaseModel<T>>
	implements CTDisplayRenderer<T> {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getEditURL(HttpServletRequest, BaseModel)}
	 */
	@Deprecated
	public String getEditURL(
			HttpServletRequest httpServletRequest, CTModel<?> ctModel)
		throws Exception {

		return getEditURL(httpServletRequest, (T)ctModel);
	}

	@Override
	public String getEditURL(HttpServletRequest httpServletRequest, T model)
		throws Exception {

		return null;
	}

	@Override
	public abstract Class<T> getModelClass();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getTitle(Locale, BaseModel)}
	 */
	@Deprecated
	public String getTitle(Locale locale, CTModel<?> ctModel)
		throws PortalException {

		return getTitle(locale, (T)ctModel);
	}

	@Override
	public abstract String getTitle(Locale locale, T model)
		throws PortalException;

	@Override
	public String getTypeName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		Class<T> modelClass = getModelClass();

		return LanguageUtil.get(
			resourceBundle, "model.resource." + modelClass.getName(),
			modelClass.getName());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #isHideable(BaseModel)}
	 */
	@Deprecated
	public boolean isHideable(CTModel<?> ctModel) {
		return isHideable((T)ctModel);
	}

	@Override
	public boolean isHideable(T model) {
		return false;
	}

	@Override
	public void render(DisplayContext<T> displayContext) throws Exception {
		HttpServletResponse httpServletResponse =
			displayContext.getHttpServletResponse();

		Writer writer = httpServletResponse.getWriter();

		writer.write("<div class=\"table-responsive\"><table class=\"");
		writer.write("publications-render-table table table-autofit ");
		writer.write("table-nowrap\">");

		HttpServletRequest httpServletRequest =
			displayContext.getHttpServletRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			displayContext.getLocale(), getClass());

		buildDisplay(
			new DisplayBuilderImpl<>(
				displayContext, resourceBundle, themeDisplay));

		writer.write("</table></div>");
	}

	protected void buildDisplay(DisplayBuilder<T> displayBuilder)
		throws PortalException {

		T model = displayBuilder.getModel();

		Map<String, Function<T, Object>> attributeGetterFunctions =
			model.getAttributeGetterFunctions();

		for (Map.Entry<String, Function<T, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			Function<T, Object> function = entry.getValue();

			displayBuilder.display(
				CamelCaseUtil.fromCamelCase(entry.getKey()),
				function.apply(model));
		}
	}

	protected interface DisplayBuilder<T> {

		public DisplayBuilder<T> display(String languageKey, Object value);

		public DisplayBuilder<T> display(
			String languageKey, String value, boolean escape);

		public DisplayBuilder<T> display(
			String languageKey,
			UnsafeSupplier<Object, Exception> unsafeSupplier);

		public DisplayContext<T> getDisplayContext();

		public Locale getLocale();

		public T getModel();

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCTDisplayRenderer.class);

	private static class DisplayBuilderImpl<T> implements DisplayBuilder<T> {

		@Override
		public DisplayBuilder<T> display(String languageKey, Object value) {
			HttpServletResponse httpServletResponse =
				_displayContext.getHttpServletResponse();

			try {
				Writer writer = httpServletResponse.getWriter();

				writer.write("<tr><td class=\"publications-key-td ");
				writer.write("table-cell-expand-small\">");
				writer.write(LanguageUtil.get(_resourceBundle, languageKey));
				writer.write("</td><td class=\"table-cell-expand\">");

				if (value instanceof Blob) {
					String downloadURL = _displayContext.getDownloadURL(
						languageKey, 0, null);

					if (downloadURL == null) {
						writer.write(
							LanguageUtil.get(_resourceBundle, "no-download"));
					}
					else {
						writer.write("<a href=\"");
						writer.write(downloadURL);
						writer.write("\" >");
						writer.write(
							LanguageUtil.get(_resourceBundle, "download"));
						writer.write("</a>");
					}
				}
				else if (value instanceof Date) {
					Format format = FastDateFormatFactoryUtil.getDateTime(
						_resourceBundle.getLocale(),
						_themeDisplay.getTimeZone());

					writer.write(format.format(value));
				}
				else {
					writer.write(HtmlUtil.escape(String.valueOf(value)));
				}

				writer.write("</td></tr>");
			}
			catch (IOException ioException) {
				throw new UncheckedIOException(ioException);
			}

			return this;
		}

		@Override
		public DisplayBuilder<T> display(
			String languageKey, String value, boolean escape) {

			HttpServletResponse httpServletResponse =
				_displayContext.getHttpServletResponse();

			try {
				Writer writer = httpServletResponse.getWriter();

				writer.write("<tr><td class=\"publications-key-td ");
				writer.write("table-cell-expand-small\">");
				writer.write(LanguageUtil.get(_resourceBundle, languageKey));
				writer.write("</td><td class=\"table-cell-expand\">");

				if (escape) {
					value = HtmlUtil.escape(value);
				}

				writer.write(value);

				writer.write("</td></tr>");
			}
			catch (IOException ioException) {
				throw new UncheckedIOException(ioException);
			}

			return this;
		}

		@Override
		public DisplayBuilder<T> display(
			String languageKey,
			UnsafeSupplier<Object, Exception> unsafeSupplier) {

			try {
				Object value = unsafeSupplier.get();

				if (value != null) {
					display(languageKey, value);
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception, exception);
				}
			}

			return this;
		}

		@Override
		public DisplayContext<T> getDisplayContext() {
			return _displayContext;
		}

		@Override
		public Locale getLocale() {
			return _resourceBundle.getLocale();
		}

		@Override
		public T getModel() {
			return _displayContext.getModel();
		}

		private DisplayBuilderImpl(
			DisplayContext<T> displayContext, ResourceBundle resourceBundle,
			ThemeDisplay themeDisplay) {

			_displayContext = displayContext;
			_resourceBundle = resourceBundle;
			_themeDisplay = themeDisplay;
		}

		private final DisplayContext<T> _displayContext;
		private final ResourceBundle _resourceBundle;
		private final ThemeDisplay _themeDisplay;

	}

}
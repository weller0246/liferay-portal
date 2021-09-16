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

package com.liferay.translation.web.internal.servlet;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.translation.exception.TranslatorException;
import com.liferay.translation.translator.JSONTranslatorPacket;
import com.liferay.translation.translator.Translator;
import com.liferay.translation.translator.TranslatorPacket;

import java.io.IOException;

import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.translation.web.internal.servlet.AutoTranslateServlet",
		"osgi.http.whiteboard.servlet.pattern=/translation/auto_translate",
		"servlet.init.httpMethods=POST"
	},
	service = Servlet.class
)
public class AutoTranslateServlet extends HttpServlet {

	@Override
	protected void doPost(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			String content = StreamUtil.toString(
				httpServletRequest.getInputStream());

			TranslatorPacket translatedTranslatorPacket = _translator.translate(
				new JSONTranslatorPacket(
					JSONFactoryUtil.createJSONObject(content)));

			_writeJSON(
				httpServletResponse, _toJSON(translatedTranslatorPacket));
		}
		catch (TranslatorException translatorException) {
			_log.error(translatorException, translatorException);

			_writeErrorJSON(
				httpServletResponse,
				StringUtil.replace(
					translatorException.getMessage(), CharPool.QUOTE, "\\\""));
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			_writeErrorJSON(
				httpServletResponse,
				_language.get(
					ResourceBundleUtil.getBundle(
						_portal.getLocale(httpServletRequest), getClass()),
					"there-is-a-problem-with-the-translation-service.-please-" +
						"contact-your-administrator"));
		}
	}

	private JSONObject _getFieldsJSONObject(Map<String, String> fieldsMap) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject;
	}

	private String _toJSON(TranslatorPacket translatorPacket) {
		return JSONUtil.put(
			"fields", _getFieldsJSONObject(translatorPacket.getFieldsMap())
		).put(
			"sourceLanguageId", translatorPacket.getSourceLanguageId()
		).put(
			"targetLanguageId", translatorPacket.getTargetLanguageId()
		).toString();
	}

	private void _writeErrorJSON(
			HttpServletResponse httpServletResponse, String message)
		throws IOException {

		_writeJSON(
			httpServletResponse,
			StringBundler.concat(
				"{\"error\": {\"message\": \"", message, "\"}}"));
	}

	private void _writeJSON(
			HttpServletResponse httpServletResponse, String json)
		throws IOException {

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, json);

		httpServletResponse.flushBuffer();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AutoTranslateServlet.class);

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private Translator _translator;

}
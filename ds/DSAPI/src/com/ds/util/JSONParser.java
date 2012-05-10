package com.ds.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ds.beans.Envelope;
import com.ds.beans.EnvelopeStatus;
import com.ds.beans.EnvelopeTemplate;
import com.ds.beans.Status;
import com.ds.data.Data;

/**
 * This class used connect to web service, send request and parse the output of
 * the response sent as JSON.
 * 
 */
public class JSONParser {

	/**
	 * Read all.
	 * 
	 * @param rd
	 *            the rd
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * Parses the url connection.
	 * 
	 * @param url
	 *            the url
	 * @param apiKey
	 *            the api key
	 * @param requestMethod
	 *            the request method
	 * @param data
	 *            the data
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws JSONException
	 *             the jSON exception
	 */
	public static String parseUrlConnection(String url, String requestMethod,
			String data) throws IOException, JSONException {

		URL u = new URL(url);
		HttpsURLConnection uc = (HttpsURLConnection) u.openConnection();
		uc.setRequestMethod(requestMethod);
		String authentication = "<DocuSignCredentials><Username>"
				+ Data.USERNAME + "</Username><Password>" + Data.PASSWORD
				+ "</Password><IntegratorKey>" + Data.INTEGRATOR_KEY
				+ "</IntegratorKey></DocuSignCredentials>";
		uc.setRequestProperty("X-DocuSign-Authentication", authentication);
		uc.setRequestProperty("Content-type", "application/json");
		uc.setRequestProperty("Accept", "application/json");
		if (data != null) {
			uc.setDoOutput(true);
			OutputStreamWriter postData = new java.io.OutputStreamWriter(
					uc.getOutputStream());
			postData.write(data);
			postData.flush();
			postData.close();
		}
		InputStream is = uc.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			return jsonText;
		} finally {
			is.close();
		}
	}

	public static void parseLoginInfo(JSONObject jsonObject)
			throws JSONException {
		if (!jsonObject.has("errorCode")) {
			JSONArray jsonArray = jsonObject.getJSONArray("loginAccounts");
			if (jsonArray.length() > 0) {
				JSONObject jlogin = jsonArray.getJSONObject(0);
				Data.BASE_URL = jlogin.getString("baseUrl");
				Data.ACCOUNT_ID = jlogin.getString("accountId");
			}
		} else
			parseErrorCode(jsonObject);
	}

	private static void parseErrorCode(JSONObject jsonObject)
			throws JSONException {

		String errorCode = jsonObject.getString("errorCode");
		String message = jsonObject.getString("message");
		String errorString = "Error Code: " + errorCode + "\n" + "Message: "
				+ message;
		System.out.println(errorString);
		Data.ERROR_FLAG = true;
		Data.ERROR_MESSAGE = errorString;
	}

	public static ArrayList<EnvelopeTemplate> parseEnvelopeTemplatesList(
			JSONObject jsonObject) throws JSONException {
		ArrayList<EnvelopeTemplate> envelopeTemplatesList = new ArrayList<EnvelopeTemplate>();
		if (!jsonObject.has("errorCode")) {
			JSONArray jenvelopeTemplatesList = jsonObject
					.getJSONArray("envelopeTemplates");
			for (int i = 0; i < jenvelopeTemplatesList.length(); i++) {
				JSONObject jenvelopeTemplate = jenvelopeTemplatesList
						.getJSONObject(i);
				EnvelopeTemplate tempEnvelopeTemplate = parseEnvelopeTemplate(jenvelopeTemplate);
				if (tempEnvelopeTemplate != null)
					envelopeTemplatesList.add(tempEnvelopeTemplate);
			}
		} else
			parseErrorCode(jsonObject);
		return envelopeTemplatesList;
	}

	public static EnvelopeTemplate parseEnvelopeTemplate(JSONObject jsonObject)
			throws JSONException {
		EnvelopeTemplate envelopeTemplate = new EnvelopeTemplate();
		envelopeTemplate.setDescription(jsonObject.getString("description"));
		envelopeTemplate.setLastModified(jsonObject.getString("lastModified"));
		envelopeTemplate.setName(jsonObject.getString("name"));
		envelopeTemplate.setPageCount(jsonObject.getInt("pageCount"));
		envelopeTemplate.setPassword(jsonObject.getString("password"));
		envelopeTemplate.setShared(jsonObject.getBoolean("shared"));
		envelopeTemplate.setTemplateId(jsonObject.getString("templateId"));
		envelopeTemplate.setUri(jsonObject.getString("uri"));
		return envelopeTemplate;
	}

	public static Status parseStatus(JSONObject jsonObject)
			throws JSONException {
		Status status = new Status();
		if (!jsonObject.has("errorCode")) {
			if (jsonObject.has("envelopeStatusChanges"))
				status.setEnvelopeStatusList(parseEnvelopeStatusesList(jsonObject));
			if (jsonObject.has("resultSetSize"))
				status.setResultSetSize(jsonObject.getInt("resultSetSize"));
		} else
			parseErrorCode(jsonObject);
		return status;
	}

	public static ArrayList<EnvelopeStatus> parseEnvelopeStatusesList(
			JSONObject jsonObject) throws JSONException {
		ArrayList<EnvelopeStatus> envelopeStatusList = new ArrayList<EnvelopeStatus>();
		JSONArray jenvelopeStatusesList = jsonObject
				.getJSONArray("envelopeStatusChanges");
		for (int i = 0; i < jenvelopeStatusesList.length(); i++) {
			JSONObject jenvelopeStatus = jenvelopeStatusesList.getJSONObject(i);
			EnvelopeStatus envelopeStatus = parseEnvelopeStatus(jenvelopeStatus);
			if (envelopeStatus != null)
				envelopeStatusList.add(envelopeStatus);
		}
		return envelopeStatusList;
	}

	public static EnvelopeStatus parseEnvelopeStatus(JSONObject jsonObject)
			throws JSONException {
		EnvelopeStatus envelopeStatus = new EnvelopeStatus();
		envelopeStatus.setEnvelopeId(jsonObject.getString("envelopeId"));
		envelopeStatus.setStatus(jsonObject.getString("status"));
		envelopeStatus.setStatusChangedDateTime(jsonObject
				.getString("statusChangedDateTime"));
		envelopeStatus.setUri(jsonObject.getString("uri"));
		return envelopeStatus;
	}

	public static Envelope parseEnvelope(JSONObject jsonObject) throws JSONException {
		Envelope envelope = new Envelope();
		if (!jsonObject.has("errorCode")) {
			
		} else
			parseErrorCode(jsonObject);
		return envelope;
	}

}
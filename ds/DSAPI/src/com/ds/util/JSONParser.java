package com.ds.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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

import com.ds.beans.Document;
import com.ds.beans.Envelope;
import com.ds.beans.EnvelopeDocument;
import com.ds.beans.EnvelopeRecipientStatus;
import com.ds.beans.EnvelopeStatus;
import com.ds.beans.EnvelopeTemplate;
import com.ds.beans.EventDateTime;
import com.ds.beans.Expirations;
import com.ds.beans.Notification;
import com.ds.beans.RecipientStatus;
import com.ds.beans.Recipients;
import com.ds.beans.Reminders;
import com.ds.beans.SignHereTabs;
import com.ds.beans.Signer;
import com.ds.beans.Status;
import com.ds.beans.Tabs;
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

	public static void downloadPDF(String url, String downloadPath)
			throws IOException, JSONException {

		URL u = new URL(url);
		HttpsURLConnection uc = (HttpsURLConnection) u.openConnection();
		uc.setRequestMethod("GET");
		String authentication = "<DocuSignCredentials><Username>"
				+ Data.USERNAME + "</Username><Password>" + Data.PASSWORD
				+ "</Password><IntegratorKey>" + Data.INTEGRATOR_KEY
				+ "</IntegratorKey></DocuSignCredentials>";
		uc.setRequestProperty("X-DocuSign-Authentication", authentication);
		uc.setRequestProperty("Content-type", "application/pdf");
		uc.setRequestProperty("Accept", "application/pdf");
		File file = new File(downloadPath);
		FileOutputStream fileOutput = new FileOutputStream(file);
		InputStream is = uc.getInputStream();
		try {
			byte[] buffer = new byte[1024];
			int bufferLength = 0; // used to store a temporary size of the
									// buffer
			while ((bufferLength = is.read(buffer)) > 0) {
				fileOutput.write(buffer, 0, bufferLength);
			}
			fileOutput.close();
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
				status.setEnvelopeStatusList(parseEnvelopeStatusChangesList(jsonObject));
			if (jsonObject.has("resultSetSize"))
				status.setResultSetSize(jsonObject.getInt("resultSetSize"));
		} else
			parseErrorCode(jsonObject);
		return status;
	}

	public static ArrayList<EnvelopeStatus> parseEnvelopeStatusChangesList(
			JSONObject jsonObject) throws JSONException {
		ArrayList<EnvelopeStatus> envelopeStatusList = new ArrayList<EnvelopeStatus>();
		JSONArray jenvelopeStatusesList = jsonObject
				.getJSONArray("envelopeStatusChanges");
		for (int i = 0; i < jenvelopeStatusesList.length(); i++) {
			JSONObject jenvelopeStatus = jenvelopeStatusesList.getJSONObject(i);
			EnvelopeStatus envelopeStatus = parseEnvelopeStatusChange(jenvelopeStatus);
			if (envelopeStatus != null)
				envelopeStatusList.add(envelopeStatus);
		}
		return envelopeStatusList;
	}

	public static EnvelopeStatus parseEnvelopeStatusChange(JSONObject jsonObject)
			throws JSONException {
		EnvelopeStatus envelopeStatus = new EnvelopeStatus();
		envelopeStatus.setEnvelopeId(jsonObject.getString("envelopeId"));
		envelopeStatus.setStatus(jsonObject.getString("status"));
		envelopeStatus.setStatusChangedDateTime(jsonObject
				.getString("statusChangedDateTime"));
		envelopeStatus.setUri(jsonObject.getString("uri"));
		return envelopeStatus;
	}

	public static Envelope parseEnvelope(JSONObject jsonObject)
			throws JSONException {
		Envelope envelope = new Envelope();
		if (!jsonObject.has("errorCode")) {
			envelope.setAllowReassign(jsonObject.getBoolean("allowReassign"));
			envelope.setAsynchronous(jsonObject.getString("asynchronous"));
			envelope.setAuthoritativeCopy(jsonObject
					.getBoolean("authoritativeCopy"));
			envelope.setCustomFields(jsonObject.getString("customFields"));
			envelope.setDocumentsList(parseDocumentsList(jsonObject));
			envelope.setEmailBlurb(jsonObject.getString("emailBlurb"));
			envelope.setEmailSubject(jsonObject.getString("emailSubject"));
			envelope.setEnableWetSign(jsonObject.getBoolean("enableWetSign"));
			envelope.setEnforceSignerVisibility(jsonObject
					.getBoolean("enforceSignerVisibility"));
			envelope.setEventNotification(jsonObject
					.getString("eventNotification"));
			envelope.setNotification(parseNotification(jsonObject
					.getJSONObject("notification")));
			envelope.setRecipients(parseRecipients(jsonObject
					.getJSONObject("recipients")));
			envelope.setSigningLocation(jsonObject.getString("signingLocation"));
		} else
			parseErrorCode(jsonObject);
		return envelope;
	}

	private static ArrayList<Document> parseDocumentsList(JSONObject jsonObject)
			throws JSONException {
		ArrayList<Document> documentsList = new ArrayList<Document>();
		JSONArray jdocumentsList = jsonObject.getJSONArray("documents");
		for (int i = 0; i < jdocumentsList.length(); i++) {
			JSONObject jdocument = jdocumentsList.getJSONObject(i);
			Document document = parseDocument(jdocument);
			if (document != null)
				documentsList.add(document);
		}
		return documentsList;
	}

	public static Document parseDocument(JSONObject jsonObject)
			throws JSONException {
		Document document = new Document();
		document.setDocumentId(jsonObject.getString("documentId"));
		document.setFileExtension(jsonObject.getString("fileExtension"));
		document.setMatchBoxes(jsonObject.getString("matchBoxes"));
		document.setName(jsonObject.getString("name"));
		document.setPassword(jsonObject.getString("password"));
		document.setRemoteUrl(jsonObject.getString("remoteUrl"));
		document.setUri(jsonObject.getString("uri"));
		return document;
	}

	private static Notification parseNotification(JSONObject jsonObject)
			throws JSONException {
		Notification notification = new Notification();
		notification.setExpirations(parseExpirations(jsonObject
				.getJSONObject("expirations")));
		notification.setReminders(parseReminders(jsonObject
				.getJSONObject("reminders")));
		notification.setUserAccountDefaults(jsonObject
				.getString("userAccountDefaults"));
		return notification;
	}

	private static Expirations parseExpirations(JSONObject jsonObject)
			throws JSONException {
		Expirations expirations = new Expirations();
		expirations.setExpireAfter(jsonObject.getString("expireAfter"));
		expirations.setExpireEnabled(jsonObject.getBoolean("expireEnabled"));
		expirations.setExpireWarn(jsonObject.getString("expireWarn"));

		return expirations;
	}

	private static Reminders parseReminders(JSONObject jsonObject)
			throws JSONException {
		Reminders reminders = new Reminders();
		reminders.setReminderDelay(jsonObject.getString("reminderDelay"));
		reminders.setReminderEnabled(jsonObject.getBoolean("reminderEnabled"));
		reminders.setReminderFrequency(jsonObject
				.getString("reminderFrequency"));
		return reminders;
	}

	private static Recipients parseRecipients(JSONObject jsonObject)
			throws JSONException {
		Recipients recipients = new Recipients();
		recipients.setAgents(jsonObject.getString("agents"));
		recipients.setCarbonCopies(jsonObject.getString("carbonCopies"));
		recipients.setCertifiedDeliveries(jsonObject
				.getString("certifiedDeliveries"));
		recipients.setEditors(jsonObject.getString("editors"));
		recipients.setInPersonSigners(jsonObject.getString("inPersonSigners"));
		recipients.setIntermediaries(jsonObject.getString("intermediaries"));
		recipients.setSignersList(parseSignersList(jsonObject));
		return recipients;
	}

	public static ArrayList<Signer> parseSignersList(JSONObject jsonObject)
			throws JSONException {
		ArrayList<Signer> signersList = new ArrayList<Signer>();
		JSONArray jsignersList = jsonObject.getJSONArray("signers");
		for (int i = 0; i < jsignersList.length(); i++) {
			JSONObject jsigner = jsignersList.getJSONObject(i);
			Signer signer = parseSigner(jsigner);
			if (signer != null)
				signersList.add(signer);
		}
		return signersList;
	}

	public static Signer parseSigner(JSONObject jsonObject)
			throws JSONException {
		Signer signer = new Signer();
		signer.setAccessCode(jsonObject.getString("accessCode"));
		signer.setAddAccessCodeToEmail(jsonObject
				.getString("addAccessCodeToEmail"));
		signer.setClientUserId(jsonObject.getString("clientUserId"));
		signer.setCustomFields(jsonObject.getString("customFields"));
		signer.setDefaultRecipient(jsonObject.getString("defaultRecipient"));
		signer.setEmail(jsonObject.getString("email"));
		signer.setEmailNotification(jsonObject.getString("emailNotification"));
		signer.setIdCheckConfigurationName(jsonObject
				.getString("idCheckConfigurationName"));
		signer.setIdCheckInformationInput(jsonObject
				.getString("idCheckInformationInput"));
		signer.setInheritEmailNotificationConfiguration(jsonObject
				.getString("inheritEmailNotificationConfiguration"));
		signer.setName(jsonObject.getString("name"));
		signer.setNote(jsonObject.getString("note"));
		signer.setPhoneAuthentication(jsonObject
				.getString("phoneAuthentication"));
		signer.setRecipientAttachments(jsonObject
				.getString("recipientAttachments"));
		signer.setRecipientId(jsonObject.getString("recipientId"));
		signer.setRequireIdLookup(jsonObject.getString("recipientId"));
		signer.setRoleName(jsonObject.getString("roleName"));
		signer.setRoutingOrder(jsonObject.getString("routingOrder"));
		signer.setSignatureInfo(jsonObject.getString("signatureInfo"));
		signer.setSocialAuthentications(jsonObject
				.getString("socialAuthentications"));
		signer.setTabs(parseTabs(jsonObject.getJSONObject("tabs")));
		return signer;
	}

	public static Tabs parseTabs(JSONObject jsonObject) throws JSONException {
		Tabs tabs = new Tabs();
		tabs.setApproveTabs(jsonObject.getString("approveTabs"));
		tabs.setCheckboxTabs(jsonObject.getString("checkboxTabs"));
		tabs.setCompanyTabs(jsonObject.getString("companyTabs"));
		tabs.setDateSignedTabs(jsonObject.getString("dateSignedTabs"));
		tabs.setDateTabs(jsonObject.getString("dateTabs"));
		tabs.setDeclineTabs(jsonObject.getString("declineTabs"));
		tabs.setEmailTabs(jsonObject.getString("emailTabs"));
		tabs.setEnvelopeIdTabs(jsonObject.getString("envelopeIdTabs"));
		tabs.setFullNameTabs(jsonObject.getString("fullNameTabs"));
		tabs.setInitialHereTabs(jsonObject.getString("initialHereTabs"));
		tabs.setListTabs(jsonObject.getString("listTabs"));
		tabs.setNoteTabs(jsonObject.getString("noteTabs"));
		tabs.setNumberTabs(jsonObject.getString("numberTabs"));
		tabs.setRadioGroupTabs(jsonObject.getString("radioGroupTabs"));
		tabs.setSignerAttachmentTabs(jsonObject
				.getString("signerAttachmentTabs"));
		tabs.setSignHereTabsList(parseSignHereTabsList(jsonObject));
		tabs.setSsnTabs(jsonObject.getString("ssnTabs"));
		tabs.setTextTabs(jsonObject.getString("textTabs"));
		tabs.setTitleTabs(jsonObject.getString("titleTabs"));
		tabs.setZipTabs(jsonObject.getString("zipTabs"));
		return tabs;
	}

	public static ArrayList<SignHereTabs> parseSignHereTabsList(
			JSONObject jsonObject) throws JSONException {
		ArrayList<SignHereTabs> signHereTabsList = new ArrayList<SignHereTabs>();
		JSONArray jsignHereTabsList = jsonObject.getJSONArray("signHereTabs");
		for (int i = 0; i < jsignHereTabsList.length(); i++) {
			JSONObject jsignHereTab = jsignHereTabsList.getJSONObject(i);
			SignHereTabs signHereTabs = parseSignHereTabs(jsignHereTab);
			if (signHereTabs != null)
				signHereTabsList.add(signHereTabs);
		}
		return signHereTabsList;
	}

	private static SignHereTabs parseSignHereTabs(JSONObject jsonObject)
			throws JSONException {
		SignHereTabs signHereTabs = new SignHereTabs();
		signHereTabs.setAnchorString(jsonObject.getString("anchorString"));
		signHereTabs.setConditionalParentLabel(jsonObject
				.getString("conditionalParentLabel"));
		signHereTabs.setConditionalParentValue(jsonObject
				.getString("conditionalParentValue"));
		signHereTabs.setDocumentId(jsonObject.getString("documentId"));
		signHereTabs.setName(jsonObject.getString("name"));
		signHereTabs.setOptional(jsonObject.getBoolean("optional"));
		signHereTabs.setPageNumber(jsonObject.getInt("pageNumber"));
		signHereTabs.setRecipientId(jsonObject.getString("recipientId"));
		signHereTabs.setScaleValue(jsonObject.getString("scaleValue"));
		signHereTabs.setTabLabel(jsonObject.getString("tabLabel"));
		signHereTabs.setTabLabel(jsonObject.getString("tabLabel"));
		signHereTabs.setxPosition(jsonObject.getInt("xPosition"));
		signHereTabs.setyPosition(jsonObject.getInt("yPosition"));
		return signHereTabs;
	}

	public static EnvelopeRecipientStatus parseEnvelopeRecipientStatus(
			JSONObject jsonObject) throws JSONException {
		EnvelopeRecipientStatus envelopeRecipientStatus = new EnvelopeRecipientStatus();
		if (!jsonObject.has("errorCode")) {
			envelopeRecipientStatus.setEnvelopeId(jsonObject
					.getString("envelopeId"));
			envelopeRecipientStatus.setEnvelopeUri(jsonObject
					.getString("envelopeUri"));
			envelopeRecipientStatus
					.setRecipientStatusesList(parseRecipientStatusesList(jsonObject));
		} else
			parseErrorCode(jsonObject);
		return envelopeRecipientStatus;
	}

	private static ArrayList<RecipientStatus> parseRecipientStatusesList(
			JSONObject jsonObject) throws JSONException {
		ArrayList<RecipientStatus> recipientStatusList = new ArrayList<RecipientStatus>();
		JSONArray jrecipientStatusList = jsonObject
				.getJSONArray("recipientStatuses");
		for (int i = 0; i < jrecipientStatusList.length(); i++) {
			JSONObject jrecipientStatus = jrecipientStatusList.getJSONObject(i);
			RecipientStatus recipientStatus = parseRecipientStatus(jrecipientStatus);
			if (recipientStatus != null)
				recipientStatusList.add(recipientStatus);
		}
		return recipientStatusList;
	}

	public static RecipientStatus parseRecipientStatus(JSONObject jsonObject)
			throws JSONException {
		RecipientStatus recipientStatus = new RecipientStatus();
		recipientStatus.setDeclinedReason(jsonObject
				.getString("declinedReason"));
		recipientStatus.setEmail(jsonObject.getString("email"));
		recipientStatus
				.setEventDateTimesList(parseEventDateTimesList(jsonObject));
		recipientStatus.setName(jsonObject.getString("name"));
		recipientStatus.setRecipientId(jsonObject.getString("recipientId"));
		recipientStatus.setRecipientType(jsonObject.getString("recipientType"));
		recipientStatus.setRoutingOrder(jsonObject.getInt("routingOrder"));
		recipientStatus.setStatus(jsonObject.getString("status"));
		return recipientStatus;
	}

	private static ArrayList<EventDateTime> parseEventDateTimesList(
			JSONObject jsonObject) throws JSONException {
		ArrayList<EventDateTime> eventDateTimesList = new ArrayList<EventDateTime>();
		JSONArray jeventDateTimesList = jsonObject
				.getJSONArray("eventDateTimes");
		for (int i = 0; i < jeventDateTimesList.length(); i++) {
			JSONObject jeventDateTime = jeventDateTimesList.getJSONObject(i);
			EventDateTime eventDateTime = parseEventDateTime(jeventDateTime);
			if (eventDateTime != null)
				eventDateTimesList.add(eventDateTime);
		}
		return eventDateTimesList;
	}

	private static EventDateTime parseEventDateTime(JSONObject jsonObject)
			throws JSONException {
		EventDateTime eventDateTime = new EventDateTime();
		eventDateTime.setDateTime(jsonObject.getString("dateTime"));
		eventDateTime.setEventName(jsonObject.getString("eventName"));
		return eventDateTime;
	}

	public static String parseEnvelopeStatus(JSONObject jsonObject)
			throws JSONException {
		String envelopeStatus = null;
		if (!jsonObject.has("errorCode")) {
			envelopeStatus = jsonObject.getString("status");
		} else
			parseErrorCode(jsonObject);
		return envelopeStatus;
	}

	public static ArrayList<EnvelopeDocument> parseEnvelopeDocumentsList(
			JSONObject jsonObject) throws JSONException {
		ArrayList<EnvelopeDocument> envelopeDocumentsList = null;
		if (!jsonObject.has("errorCode")) {
			envelopeDocumentsList = new ArrayList<EnvelopeDocument>();
			JSONArray jenvelopeDocumentsList = jsonObject
					.getJSONArray("envelopeDocuments");
			for (int i = 0; i < jenvelopeDocumentsList.length(); i++) {
				JSONObject jenvelopeDocumnent = jenvelopeDocumentsList
						.getJSONObject(i);
				EnvelopeDocument envelopeDocument = parseEnvelopeDocument(jenvelopeDocumnent);
				if (envelopeDocument != null)
					envelopeDocumentsList.add(envelopeDocument);
			}
		} else
			parseErrorCode(jsonObject);
		return envelopeDocumentsList;
	}

	private static EnvelopeDocument parseEnvelopeDocument(JSONObject jsonObject)
			throws JSONException {
		EnvelopeDocument envelopeDocument = new EnvelopeDocument();
		envelopeDocument.setDocumentId(jsonObject.getString("documentId"));
		envelopeDocument.setName(jsonObject.getString("name"));
		envelopeDocument.setType(jsonObject.getString("type"));
		envelopeDocument.setUri(jsonObject.getString("uri"));
		return envelopeDocument;
	}

}
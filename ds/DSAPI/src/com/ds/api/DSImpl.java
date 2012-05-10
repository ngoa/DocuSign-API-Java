package com.ds.api;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.ds.beans.Envelope;
import com.ds.beans.EnvelopeDocument;
import com.ds.beans.EnvelopeRecipientStatus;
import com.ds.beans.EnvelopeTemplate;
import com.ds.beans.Status;
import com.ds.data.Data;
import com.ds.util.JSONParser;

public class DSImpl implements DS {

	public DSImpl() throws IOException {
		Data.initialize();
	}

	@Override
	public void getLoginInfo() throws JSONException, IOException {
		String url = "https://demo.docusign.net/restapi/v1/login_information";
		JSONObject jsonObject = new JSONObject(JSONParser.parseUrlConnection(
				url, "GET", null));
		JSONParser.parseLoginInfo(jsonObject);
	}

	@Override
	public ArrayList<EnvelopeTemplate> getEnvelopeTemplatesList()
			throws JSONException, IOException {
		String url = Data.BASE_URL + "/templates";
		JSONObject jsonObject = new JSONObject(JSONParser.parseUrlConnection(
				url, "GET", null));
		return JSONParser.parseEnvelopeTemplatesList(jsonObject);
	}

	@Override
	public Status getStatus() throws JSONException, IOException {
		Status status = new Status();
		String url = Data.BASE_URL + "/envelopes/status";
		JSONObject jsonObject = new JSONObject(JSONParser.parseUrlConnection(
				url, "GET", null));
		status = JSONParser.parseStatus(jsonObject);
		return status;
	}

	@Override
	public Status getStatusCreatedByMe(String username, String email,
			String statuses) throws JSONException, IOException {
		Status status = new Status();
		String url = Data.BASE_URL + "/envelopes/status?username=" + username
				+ "&email=" + email + "&statuses=" + statuses;
		JSONObject jsonObject = new JSONObject(JSONParser.parseUrlConnection(
				url, "GET", null));
		status = JSONParser.parseStatus(jsonObject);
		return status;
	}

	@Override
	public Status getStatusSinceDate(int month, int day, int year,
			String statuses) throws JSONException, IOException {
		Status status = new Status();
		String url = Data.BASE_URL + "/envelopes/status?since="
				+ Integer.toString(month) + "%2F" + Integer.toString(day)
				+ "%2F" + Integer.toString(year) + "&statuses=" + statuses;
		JSONObject jsonObject = new JSONObject(JSONParser.parseUrlConnection(
				url, "GET", null));
		status = JSONParser.parseStatus(jsonObject);
		return status;
	}

	@Override
	public Status getStatusSinceDate(String mm_dd_yyyy, String statuses)
			throws JSONException, IOException {
		Status status = new Status();
		mm_dd_yyyy = mm_dd_yyyy.replaceAll("_", "/");
		mm_dd_yyyy = mm_dd_yyyy.replaceAll("/", "%2F");
		String url = Data.BASE_URL + "/envelopes/status?since=" + mm_dd_yyyy
				+ "&statuses=" + statuses;
		JSONObject jsonObject = new JSONObject(JSONParser.parseUrlConnection(
				url, "GET", null));
		status = JSONParser.parseStatus(jsonObject);
		return status;
	}

	@Override
	public Envelope getEnvelope(String envelopeId) throws JSONException,
			IOException {
		Envelope envelope = new Envelope();
		String url = Data.BASE_URL + "/envelopes/" + envelopeId;
		JSONObject jsonObject = new JSONObject(JSONParser.parseUrlConnection(
				url, "GET", null));
		envelope = JSONParser.parseEnvelope(jsonObject);
		return envelope;
	}

	@Override
	public EnvelopeRecipientStatus getEnvelopeRecipientStatus(String envelopeId)
			throws JSONException, IOException {
		EnvelopeRecipientStatus envelopeRecipientStatus = new EnvelopeRecipientStatus();
		String url = Data.BASE_URL + "/envelopes/" + envelopeId
				+ "/recipients/status";
		JSONObject jsonObject = new JSONObject(JSONParser.parseUrlConnection(
				url, "GET", null));
		envelopeRecipientStatus = JSONParser
				.parseEnvelopeRecipientStatus(jsonObject);
		return envelopeRecipientStatus;
	}

	@Override
	public String getEnvelopeStatus(String envelopeId) throws JSONException,
			IOException {
		String url = Data.BASE_URL + "/envelopes/" + envelopeId + "/status";
		JSONObject jsonObject = new JSONObject(JSONParser.parseUrlConnection(
				url, "GET", null));
		String envelopeStatus = JSONParser.parseEnvelopeStatus(jsonObject);
		return envelopeStatus;
	}

	@Override
	public ArrayList<EnvelopeDocument> getEnvelopeDocumentsList(
			String envelopeId) throws JSONException, IOException {
		String url = Data.BASE_URL + "/envelopes/" + envelopeId + "/documents";
		JSONObject jsonObject = new JSONObject(JSONParser.parseUrlConnection(
				url, "GET", null));
		ArrayList<EnvelopeDocument> envelopeDocumentsList = JSONParser
				.parseEnvelopeDocumentsList(jsonObject);
		return envelopeDocumentsList;
	}

	@Override
	public void downloadEnvelopeDocument(String envelopeId, String documentId,
			String downloadPath) throws JSONException, IOException {
		String url = Data.BASE_URL + "/envelopes/" + envelopeId + "/documents/"
				+ documentId;
		JSONParser.downloadPDF(url, downloadPath);
	}
}

package com.ds.api;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.ds.beans.EnvelopeTemplate;
import com.ds.data.Data;
import com.ds.util.JSONParser;

public class DSImpl implements DS {

	public DSImpl() throws IOException{
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

}

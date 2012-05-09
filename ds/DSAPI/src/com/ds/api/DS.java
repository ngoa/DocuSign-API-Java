package com.ds.api;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import com.ds.beans.EnvelopeTemplate;

public interface DS {

	public void getLoginInfo() throws JSONException, IOException;

	public ArrayList<EnvelopeTemplate> getEnvelopeTemplatesList()
			throws JSONException, IOException;

}

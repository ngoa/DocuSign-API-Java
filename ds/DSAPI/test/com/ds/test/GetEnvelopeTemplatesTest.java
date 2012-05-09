package com.ds.test;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import com.ds.api.DS;
import com.ds.api.DSFactory;
import com.ds.beans.EnvelopeTemplate;

public class GetEnvelopeTemplatesTest {

	public static void main(String args[]) throws IOException, JSONException {

		DS ds = new DSFactory().createDSImpl();
		ds.getLoginInfo();
		ArrayList<EnvelopeTemplate> envelopeTemplatesList = ds
				.getEnvelopeTemplatesList();
		System.out.println(envelopeTemplatesList);
	}
}

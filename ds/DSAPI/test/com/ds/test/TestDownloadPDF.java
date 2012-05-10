package com.ds.test;

import java.io.IOException;

import org.json.JSONException;

import com.ds.api.DS;
import com.ds.api.DSFactory;

public class TestDownloadPDF {

	public static void main(String args[]) throws IOException, JSONException {

		DS ds = new DSFactory().createDSImpl();
		ds.getLoginInfo();
		ds.downloadEnvelopeDocument("BF6E06175A414AE0AB55CEFAE55ACE08", "1",
				"resources\\PDF.pdf");
	}
}

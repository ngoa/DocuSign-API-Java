package com.ds.test;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ds.data.Data;
import com.ds.util.JSONParser;

public class LoginTest {

	public static void main(String args[]) throws IOException, JSONException {
		Data.initialize();
		System.out.println(Data.USERNAME);
		System.out.println(Data.PASSWORD);
		System.out.println(Data.ACCOUNT_ID);
		String url = "https://demo.docusign.net/restapi/v1/login_information";
		JSONObject jsonObject = new JSONObject(JSONParser.parseUrlConnection(
				url, "GET", null));
		System.out.println(jsonObject.toString());
	}
}

package com.ds.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LoginInfo {

	public static String USERNAME;
	public static String PASSWORD;
	public static String ACCOUNT_ID;
	public static String INTEGRATOR_KEY;
	public static String BASE_URL;

	public static void initialize() throws IOException {
		File file = new File("resources\\data.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		USERNAME = br.readLine();
		PASSWORD = br.readLine();
		ACCOUNT_ID = br.readLine();
		INTEGRATOR_KEY = br.readLine();
	}
}

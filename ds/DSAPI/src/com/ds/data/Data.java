package com.ds.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Data {

	public static String USERNAME = null;
	public static String PASSWORD = null;
	public static String ACCOUNT_ID = null;
	public static String INTEGRATOR_KEY = null;
	public static String BASE_URL = null;

	public static void initialize() throws IOException {
		File file = new File("resources\\data.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		USERNAME = br.readLine();
		PASSWORD = br.readLine();
		INTEGRATOR_KEY = br.readLine();
	}
}

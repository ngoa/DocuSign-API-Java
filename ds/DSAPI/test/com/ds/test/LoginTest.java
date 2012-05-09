package com.ds.test;

import java.io.IOException;

import com.ds.util.LoginInfo;

public class LoginTest {

	public static void main(String args[]) throws IOException {
		LoginInfo.initialize();
		System.out.println(LoginInfo.USERNAME);
		System.out.println(LoginInfo.PASSWORD);
		System.out.println(LoginInfo.ACCOUNT_ID);
	}

}

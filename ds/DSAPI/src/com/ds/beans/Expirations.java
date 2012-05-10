package com.ds.beans;

public class Expirations {

	private String expireAfter;
	private boolean expireEnabled;
	private String expireWarn;

	public String getExpireAfter() {
		return expireAfter;
	}

	public void setExpireAfter(String expireAfter) {
		this.expireAfter = expireAfter;
	}

	public boolean isExpireEnabled() {
		return expireEnabled;
	}

	public void setExpireEnabled(boolean expireEnabled) {
		this.expireEnabled = expireEnabled;
	}

	public String getExpireWarn() {
		return expireWarn;
	}

	public void setExpireWarn(String expireWarn) {
		this.expireWarn = expireWarn;
	}

}

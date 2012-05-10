package com.ds.beans;

public class Notification {

	private Expirations expirations;
	private Reminders reminders;
	private String UserAccountDefaults;

	public Expirations getExpirations() {
		return expirations;
	}

	public void setExpirations(Expirations expirations) {
		this.expirations = expirations;
	}

	public Reminders getReminders() {
		return reminders;
	}

	public void setReminders(Reminders reminders) {
		this.reminders = reminders;
	}

	public String getUserAccountDefaults() {
		return UserAccountDefaults;
	}

	public void setUserAccountDefaults(String userAccountDefaults) {
		UserAccountDefaults = userAccountDefaults;
	}

}

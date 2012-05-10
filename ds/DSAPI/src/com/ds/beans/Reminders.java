package com.ds.beans;

public class Reminders {

	private String reminderDelay;
	private boolean reminderEnabled;
	private String reminderFrequency;

	public String getReminderDelay() {
		return reminderDelay;
	}

	public void setReminderDelay(String reminderDelay) {
		this.reminderDelay = reminderDelay;
	}

	public boolean isReminderEnabled() {
		return reminderEnabled;
	}

	public void setReminderEnabled(boolean reminderEnabled) {
		this.reminderEnabled = reminderEnabled;
	}

	public String getReminderFrequency() {
		return reminderFrequency;
	}

	public void setReminderFrequency(String reminderFrequency) {
		this.reminderFrequency = reminderFrequency;
	}

}

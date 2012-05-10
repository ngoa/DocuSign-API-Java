package com.ds.beans;

import java.util.ArrayList;

public class Envelope {

	private boolean allowReassign;
	private String asynchronous;
	private boolean authoritativeCopy;
	private String customFields;
	private ArrayList<Document> documentsList;
	private String emailBlurb;
	private String emailSubject;
	private String enableWetSign;
	private String enforceSignerVisibility;
	private String eventNotification;
	private Notification notification;
	private Recipients recipients;
	private String signingLocation;

	public boolean isAllowReassign() {
		return allowReassign;
	}

	public void setAllowReassign(boolean allowReassign) {
		this.allowReassign = allowReassign;
	}

	public String getAsynchronous() {
		return asynchronous;
	}

	public void setAsynchronous(String asynchronous) {
		this.asynchronous = asynchronous;
	}

	public boolean isAuthoritativeCopy() {
		return authoritativeCopy;
	}

	public void setAuthoritativeCopy(boolean authoritativeCopy) {
		this.authoritativeCopy = authoritativeCopy;
	}

	public String getCustomFields() {
		return customFields;
	}

	public void setCustomFields(String customFields) {
		this.customFields = customFields;
	}

	public ArrayList<Document> getDocumentsList() {
		return documentsList;
	}

	public void setDocumentsList(ArrayList<Document> documentsList) {
		this.documentsList = documentsList;
	}

	public String getEmailBlurb() {
		return emailBlurb;
	}

	public void setEmailBlurb(String emailBlurb) {
		this.emailBlurb = emailBlurb;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEnableWetSign() {
		return enableWetSign;
	}

	public void setEnableWetSign(String enableWetSign) {
		this.enableWetSign = enableWetSign;
	}

	public String getEnforceSignerVisibility() {
		return enforceSignerVisibility;
	}

	public void setEnforceSignerVisibility(String enforceSignerVisibility) {
		this.enforceSignerVisibility = enforceSignerVisibility;
	}

	public String getEventNotification() {
		return eventNotification;
	}

	public void setEventNotification(String eventNotification) {
		this.eventNotification = eventNotification;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public Recipients getRecipients() {
		return recipients;
	}

	public void setRecipients(Recipients recipients) {
		this.recipients = recipients;
	}

	public String getSigningLocation() {
		return signingLocation;
	}

	public void setSigningLocation(String signingLocation) {
		this.signingLocation = signingLocation;
	}

}

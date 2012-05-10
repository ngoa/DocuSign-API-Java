package com.ds.beans;

import java.util.ArrayList;

public class EnvelopeRecipientStatus {

	private String envelopeId;
	private String envelopeUri;
	private ArrayList<RecipientStatus> recipientStatusesList;

	public String getEnvelopeId() {
		return envelopeId;
	}

	public void setEnvelopeId(String envelopeId) {
		this.envelopeId = envelopeId;
	}

	public String getEnvelopeUri() {
		return envelopeUri;
	}

	public void setEnvelopeUri(String envelopeUri) {
		this.envelopeUri = envelopeUri;
	}

	public ArrayList<RecipientStatus> getRecipientStatusesList() {
		return recipientStatusesList;
	}

	public void setRecipientStatusesList(
			ArrayList<RecipientStatus> recipientStatusesList) {
		this.recipientStatusesList = recipientStatusesList;
	}

}

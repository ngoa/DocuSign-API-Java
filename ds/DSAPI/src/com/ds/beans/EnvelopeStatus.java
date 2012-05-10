package com.ds.beans;

public class EnvelopeStatus {

	private String envelopeId;
	private String status;
	private String statusChangedDateTime;
	private String uri;

	public String getEnvelopeId() {
		return envelopeId;
	}

	public void setEnvelopeId(String envelopeId) {
		this.envelopeId = envelopeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusChangedDateTime() {
		return statusChangedDateTime;
	}

	public void setStatusChangedDateTime(String statusChangedDateTime) {
		this.statusChangedDateTime = statusChangedDateTime;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}

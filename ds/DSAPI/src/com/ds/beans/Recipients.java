package com.ds.beans;

import java.util.ArrayList;

public class Recipients {

	private String agents;
	private String carbonCopies;
	private String certifiedDeliveries;
	private String editors;
	private String inPersonSigners;
	private String intermediaries;
	private ArrayList<Signer> signersList;

	public String getAgents() {
		return agents;
	}

	public void setAgents(String agents) {
		this.agents = agents;
	}

	public String getCarbonCopies() {
		return carbonCopies;
	}

	public void setCarbonCopies(String carbonCopies) {
		this.carbonCopies = carbonCopies;
	}

	public String getCertifiedDeliveries() {
		return certifiedDeliveries;
	}

	public void setCertifiedDeliveries(String certifiedDeliveries) {
		this.certifiedDeliveries = certifiedDeliveries;
	}

	public String getEditors() {
		return editors;
	}

	public void setEditors(String editors) {
		this.editors = editors;
	}

	public String getInPersonSigners() {
		return inPersonSigners;
	}

	public void setInPersonSigners(String inPersonSigners) {
		this.inPersonSigners = inPersonSigners;
	}

	public String getIntermediaries() {
		return intermediaries;
	}

	public void setIntermediaries(String intermediaries) {
		this.intermediaries = intermediaries;
	}

	public ArrayList<Signer> getSignersList() {
		return signersList;
	}

	public void setSignersList(ArrayList<Signer> signersList) {
		this.signersList = signersList;
	}

}

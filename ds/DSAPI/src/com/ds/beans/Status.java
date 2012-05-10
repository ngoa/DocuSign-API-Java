package com.ds.beans;

import java.util.ArrayList;

public class Status {

	private ArrayList<EnvelopeStatus> envelopeStatusList;
	private int resultSetSize;

	public ArrayList<EnvelopeStatus> getEnvelopeStatusList() {
		return envelopeStatusList;
	}

	public void setEnvelopeStatusList(
			ArrayList<EnvelopeStatus> envelopeStatusList) {
		this.envelopeStatusList = envelopeStatusList;
	}

	public int getResultSetSize() {
		return resultSetSize;
	}

	public void setResultSetSize(int resultSetSize) {
		this.resultSetSize = resultSetSize;
	}

}

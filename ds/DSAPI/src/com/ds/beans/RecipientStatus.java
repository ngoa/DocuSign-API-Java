package com.ds.beans;

import java.util.ArrayList;

public class RecipientStatus {

	private String declinedReason;
	private String email;
	private ArrayList<EventDateTime> eventDateTimesList;
	private String name;
	private String recipientId;
	private String recipientType;
	private int routingOrder;
	private String status;

	public String getDeclinedReason() {
		return declinedReason;
	}

	public void setDeclinedReason(String declinedReason) {
		this.declinedReason = declinedReason;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<EventDateTime> getEventDateTimesList() {
		return eventDateTimesList;
	}

	public void setEventDateTimesList(
			ArrayList<EventDateTime> eventDateTimesList) {
		this.eventDateTimesList = eventDateTimesList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public String getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}

	public int getRoutingOrder() {
		return routingOrder;
	}

	public void setRoutingOrder(int routingOrder) {
		this.routingOrder = routingOrder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

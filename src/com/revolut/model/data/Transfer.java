package com.revolut.model.data;

import com.revolut.restful.json.JsonElement;

public class Transfer {

	
	@JsonElement(key = "Sender")
	Recipient sender;
	
	@JsonElement(key = "Recipient")
	Recipient recipient;
	
	@JsonElement
	private Double Amount;
	
	@JsonElement
	private String Currency;

	// Getter Methods

	public Recipient getSender() {
		return sender;
	}

	public void setSender(Recipient sender) {
		this.sender = sender;
	}


	public Recipient getRecipient() {
		return recipient;
	}

	public Double getAmount() {
		return Amount;
	}

	public String getCurrency() {
		return Currency;
	}

	// Setter Methods


	public void setRecipient(Recipient RecipientObject) {
		this.recipient = RecipientObject;
	}

	public void setAmount(Double Amount) {
		this.Amount = Amount;
	}

	public void setCurrency(String Currency) {
		this.Currency = Currency;
	}
	
}

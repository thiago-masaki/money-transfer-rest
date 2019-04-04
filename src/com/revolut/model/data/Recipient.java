package com.revolut.model.data;

import com.revolut.restful.json.JsonElement;
import com.revolut.restful.json.JsonSerializable;

@JsonSerializable
public class Recipient {
	@JsonElement
	private String Name;
	
	@JsonElement
	private String SocialID;
	
	@JsonElement(key = "Bank")
	private Bank bank;
	
	// Getter Methods
	
	public Bank getBank() {
		return bank;
	}

	public String getName() {
		return Name;
	}

	public String getSocialID() {
		return SocialID;
	}

	// Setter Methods

	public void setName(String Name) {
		this.Name = Name;
	}

	public void setSocialID(String SocialID) {
		this.SocialID = SocialID;
	}
	
	public void setBank(Bank BankObject) {
		this.bank = BankObject;
	}

}

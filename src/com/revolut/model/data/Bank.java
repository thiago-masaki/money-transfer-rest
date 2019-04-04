package com.revolut.model.data;

import com.revolut.restful.json.JsonElement;
import com.revolut.restful.json.JsonSerializable;

@JsonSerializable
public class Bank {
	
	@JsonElement
	private String Name;
	
	@JsonElement
	private String AccountNumber;

	@JsonElement
	private String Branch;

	// Getter Methods

	public String getName() {
		return Name;
	}

	public String getAccountNumber() {
		return AccountNumber;
	}

	// Setter Methods

	public void setName(String Name) {
		this.Name = Name;
	}

	public void setAccountNumber(String AccountNumber) {
		this.AccountNumber = AccountNumber;
	}

	public String getBranch() {
		return Branch;
	}
	
	public void setBranch(String branch) {
		Branch = branch;
	}
}

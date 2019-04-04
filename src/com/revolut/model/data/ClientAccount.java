package com.revolut.model.data;

public class ClientAccount {
	private String Name;
	private String BirthDate;
	private String Address;
	private String Branch;
	private String AccountNumber;
	private Double Balance = 0.0;
	private String SocialID;

	public ClientAccount(String socialid, String name, String birthDate, String address, String branch,
			String accountNumber, Double balance) {
		Name = name;
		BirthDate = birthDate;
		Address = address;
		Branch = branch;
		AccountNumber = accountNumber;
		Balance = balance;
		SocialID = socialid;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getBirthDate() {
		return BirthDate;
	}

	public void setBirthDate(String birthDay) {
		BirthDate = birthDay;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getBranch() {
		return Branch;
	}

	public void setBranch(String branch) {
		Branch = branch;
	}

	public String getAccountNumber() {
		return AccountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}

	public Double getBalance() {
		return Balance;
	}

	public void withdrawal(Double balance) {
		Balance -= balance;
	}

	public void deposit(Double balance) {
		Balance += balance;
	}

	public String getSocialID() {
		return SocialID;
	}

	public void setSocialID(String socialID) {
		SocialID = socialID;
	}
}

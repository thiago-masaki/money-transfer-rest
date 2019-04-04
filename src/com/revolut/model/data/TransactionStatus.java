package com.revolut.model.data;

import com.revolut.restful.json.JsonElement;
import com.revolut.restful.json.JsonSerializable;

@JsonSerializable
public class TransactionStatus {
	
	public TransactionStatus(String stat, String desc, Integer errorcode) {
		this.status = stat;
		this.description = desc;
		this.errorcode = errorcode;
	}
	
	@JsonElement
	private String status;
	@JsonElement
	private String description;
	@JsonElement
	private Integer errorcode;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(Integer errorcode) {
		this.errorcode = errorcode;
	}
}

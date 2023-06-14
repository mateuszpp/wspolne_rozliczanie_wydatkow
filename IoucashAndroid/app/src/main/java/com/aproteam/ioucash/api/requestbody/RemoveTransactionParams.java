package com.aproteam.ioucash.api.requestbody;

public class RemoveTransactionParams {

	public String sender;
	public String receiver;

	public RemoveTransactionParams(String sender, String receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}

}
package com.aproteam.ioucash.api.requestbody;

/**
 * Represents the parameters for removing a transaction between two users.
 */
public class RemoveTransactionParams {
	public String sender;
	public String receiver;

	public RemoveTransactionParams(String sender, String receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}

}
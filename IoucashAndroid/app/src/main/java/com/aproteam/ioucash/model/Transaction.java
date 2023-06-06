package com.aproteam.ioucash.model;

public class Transaction {

	public int id;
	public User sender;
	public User receiver;
	public double amount;

	public String getSenderName() {
		if (sender == null)
			return null;
		return sender.username;
	}

	public String getReceiverName() {
		if (receiver == null)
			return null;
		return receiver.username;
	}

}
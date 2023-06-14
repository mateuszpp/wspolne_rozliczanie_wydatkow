package com.aproteam.ioucash.model;

import com.aproteam.ioucash.App;
import com.aproteam.ioucash.SessionManager;

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

	public String getOtherName(){
		SessionManager sessionManager = SessionManager.getInstance(App.get());
		User current = sessionManager.readUserData();
		if(current.username.equals(receiver.username)) return sender.username;
		return receiver.username;
	}

}
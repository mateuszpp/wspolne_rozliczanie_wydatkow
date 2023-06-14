package com.aproteam.ioucash.model;

import com.aproteam.ioucash.App;
import com.aproteam.ioucash.SessionManager;

/**
 * A class representing a transaction between two users.
 */
public class Transaction {

	public int id;
	public User sender;
	public User receiver;
	public double amount;

	/**
	 * Gets the username of the sender.
	 *
	 * @return username of the sender, or null if the sender is null
	 */
	public String getSenderName() {
		if (sender == null)
			return null;
		return sender.username;
	}

	/**
	 * Gets the username of the receiver.
	 *
	 * @return username of the receiver, or null if the receiver is null
	 */
	public String getReceiverName() {
		if (receiver == null)
			return null;
		return receiver.username;
	}

	/**
	 * Gets username of the other party involved in the transaction (opposite of the current user).
	 *
	 * @return The username of the other party, or null if the current user or the other party is null.
	 */
	public String getOtherName(){
		SessionManager sessionManager = SessionManager.getInstance(App.get());
		User current = sessionManager.readUserData();
		if(current.username.equals(receiver.username)) return sender.username;
		return receiver.username;
	}

	/**
	 * Checks current user balance and returns if the balance value is positive or negative.
	 *
	 * @return symbol representing the effect on the current user's balance ("+" or "-")
	 */
	public String onPlus() {
		SessionManager sessionManager = SessionManager.getInstance(App.get());
		User current = sessionManager.readUserData();
		if(current.username.equals(receiver.username)) return "+";
		return "-";
	}

}
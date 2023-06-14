package com.aproteam.ioucash.model;

import androidx.annotation.NonNull;

/**
 * A class representing the user in the system.
 */
public class User extends ResponseErrorInfo {

	public String username;
	public String hashedPasswd;
	public String token;
	public double initialBalance;
	public double balance;
	public int id;

	@NonNull
	@Override
	public String toString() {
		return username;
	}
}
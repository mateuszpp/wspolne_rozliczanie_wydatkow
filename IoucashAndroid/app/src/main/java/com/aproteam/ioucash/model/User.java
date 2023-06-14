package com.aproteam.ioucash.model;

import androidx.annotation.NonNull;

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
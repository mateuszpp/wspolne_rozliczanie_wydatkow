package com.aproteam.ioucash.api.requestbody;

public class ChangeUserPasswordParams {

	public String username;
	public String currentPassword;
	public String newPassword;

	public ChangeUserPasswordParams(String username, String currentPassword, String newPassword) {
		this.username = username;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}

}
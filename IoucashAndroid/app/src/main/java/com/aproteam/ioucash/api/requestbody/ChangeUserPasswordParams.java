package com.aproteam.ioucash.api.requestbody;

public class ChangeUserPasswordParams {

	public String username;
	public String currentPassword;
	public String newPassword;
	public String token;

	public ChangeUserPasswordParams(String username, String currentPassword, String newPassword, String token) {
		this.username = username;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.token = token;
	}

}
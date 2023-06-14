package com.aproteam.ioucash.api.requestbody;

/**
 * Represents the parameters for changing user's password.
 */
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
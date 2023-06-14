package com.aproteam.ioucash.api.requestbody;

/**
 * Represents the parameters for user authorization.
 */
public class UserAuthorizationParams {

	public String username;
	public String password;

	public UserAuthorizationParams(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
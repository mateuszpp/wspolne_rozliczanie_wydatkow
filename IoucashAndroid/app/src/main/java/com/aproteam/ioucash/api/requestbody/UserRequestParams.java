package com.aproteam.ioucash.api.requestbody;

import com.aproteam.ioucash.model.User;

/**
 * Represents the parameters for a user request.
 */
public class UserRequestParams {
	public String username;

	public UserRequestParams(User user) {
		this.username = user.username;
	}
}

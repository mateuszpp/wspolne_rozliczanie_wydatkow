package com.aproteam.ioucash.model;

import com.google.gson.annotations.SerializedName;

/**
 * A class representing an error response returned by the API.
 */
public class ResponseErrorInfo {

	@SerializedName("error")
	public String error;

	@SerializedName("errorMessage")
	public String errorMessage;

}
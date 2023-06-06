package com.aproteam.ioucash.model;

import com.google.gson.annotations.SerializedName;

public class ResponseErrorInfo {

	@SerializedName("error")
	public String error;

	@SerializedName("errorMessage")
	public String errorMessage;

}
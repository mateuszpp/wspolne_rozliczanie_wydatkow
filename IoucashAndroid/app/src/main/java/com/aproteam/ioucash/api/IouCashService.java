package com.aproteam.ioucash.api;

import com.aproteam.ioucash.api.requestbody.ChangeUserPasswordParams;
import com.aproteam.ioucash.api.requestbody.UserAuthorizationParams;
import com.aproteam.ioucash.model.Transaction;
import com.aproteam.ioucash.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IouCashService {

	@POST("/users/remove/{name}")
	Call<Object> removeUser(@Path("name") String username);

	@POST("/users/register")
	Call<User> register(@Body UserAuthorizationParams userAuthorizationParams);

	@POST("/users/login")
	Call<User> login(@Body UserAuthorizationParams userAuthorizationParams);

	@POST("/users/changePassword")
	Call<User> changePassword(@Body ChangeUserPasswordParams changeUserPasswordParams);

	@GET("/Transaction")
	Call<List<Transaction>> getTransactions();

}
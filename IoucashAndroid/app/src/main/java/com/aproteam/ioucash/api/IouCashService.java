package com.aproteam.ioucash.api;

import com.aproteam.ioucash.api.requestbody.ChangeUserPasswordParams;
import com.aproteam.ioucash.api.requestbody.RemoveTransactionParams;
import com.aproteam.ioucash.api.requestbody.UserAuthorizationParams;
import com.aproteam.ioucash.api.requestbody.UserRequestParams;
import com.aproteam.ioucash.api.requestbody.UserTransactionRequestParams;
import com.aproteam.ioucash.model.Result;
import com.aproteam.ioucash.model.Transaction;
import com.aproteam.ioucash.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface defining the API endpoints for the IouCash service.
 */
public interface IouCashService {

	@GET("/users")
	Call<List<User>> getUsers();

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

	@DELETE("/removeTransaction/{sender}/{receiver}")
	Call<Transaction> removeTransaction(@Path ("sender") String sender, @Path ("receiver") String receiver);

	@POST("/addTransaction")
	Call<Result> addTransaction(@Body UserTransactionRequestParams userTransactionRequestParams);

	@GET("Transaction/bySender/{sender}")
	Call<List<Transaction>> getTransactionsBySender(@Path ("sender") String senderName);

	@GET("Transaction/byReceiver/{receiver}")
	Call<List<Transaction>> getTransactionsByReceiver(@Path ("receiver") String receiverName);
}
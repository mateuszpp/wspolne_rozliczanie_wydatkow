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
 * An interface defining the API endpoints for the IouCash service.
 * Each of its methods returns concretely typed Call object.
 */
public interface IouCashService {

	/**
	 * Sends a GET request and retrieves a list of users.
	 *
	 * @return a Call object representing the API call, expecting to receive a list of users
	 */
	@GET("/users")
	Call<List<User>> getUsers();

	/**
	 * Sends a POST request and removes a user.
	 *
	 * @param username username of the user to remove
	 * @return A Call object representing the API call
	 */
	@POST("/users/remove/{name}")
	Call<Object> removeUser(@Path("name") String username);

	/**
	 * Sends a POST request and registers a new user.
	 *
	 * @param userAuthorizationParams parameters for user authorization
	 * @return a Call object representing the API call, expecting a response representing a user
	 */
	@POST("/users/register")
	Call<User> register(@Body UserAuthorizationParams userAuthorizationParams);

	/**
	 * Sends a POST request and logs user in.
	 *
	 * @param userAuthorizationParams parameters for user authorization
	 * @return a Call object representing the API call, expecting a response representing a user
	 */
	@POST("/users/login")
	Call<User> login(@Body UserAuthorizationParams userAuthorizationParams);

	/**
	 * Sends a POST request and changes his password.
	 *
	 * @param changeUserPasswordParams parameters for changing the user's password
	 * @return a Call object representing the API call, expecting a response representing a user
	 */
	@POST("/users/changePassword")
	Call<User> changePassword(@Body ChangeUserPasswordParams changeUserPasswordParams);

	/**
	 * Sends a GET request and retrieves a list of transactions.
	 *
	 * @return a Call object representing the API call, expecting a response representing a list of transactions
	 *
	 */
	@GET("/Transaction")
	Call<List<Transaction>> getTransactions();

	/**
	 * Sends a DELETE request and removes a transaction.
	 *
	 * @param sender username of the sender
	 * @param receiver username of the receiver
	 * @return a Call object representing the API call, expecting a response representing a transaction
	 */
	@DELETE("/removeTransaction/{sender}/{receiver}")
	Call<Transaction> removeTransaction(@Path ("sender") String sender, @Path ("receiver") String receiver);

	/**
	 * Sends a POST request and adds a new transaction.
	 *
	 * @param userTransactionRequestParams parameters for the user transaction
	 * @return a Call object representing the API call, expecting a response representing a result
	 */
	@POST("/addTransaction")
	Call<Result> addTransaction(@Body UserTransactionRequestParams userTransactionRequestParams);

	/**
	 * Sends a GET request and retrieves a list of transactions by the sender.
	 *
	 * @param senderName username of the sender
	 * @return a Call object representing the API call, expecting a response representing a list of transactions
	 *
	 */
	@GET("Transaction/bySender/{sender}")
	Call<List<Transaction>> getTransactionsBySender(@Path ("sender") String senderName);

	/**
	 * Sends a GET request and retrieves a list of transactions by the receiver.
	 *
	 * @param receiverName username of the receiver
	 * @return a Call object representing the API call, expecting a response representing a list of transactions
	 */
	@GET("Transaction/byReceiver/{receiver}")
	Call<List<Transaction>> getTransactionsByReceiver(@Path ("receiver") String receiverName);
}
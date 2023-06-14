package com.aproteam.ioucash.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aproteam.ioucash.App;
import com.aproteam.ioucash.Constants;
import com.aproteam.ioucash.SessionManager;
import com.aproteam.ioucash.api.requestbody.ChangeUserPasswordParams;
import com.aproteam.ioucash.api.requestbody.UserAuthorizationParams;
import com.aproteam.ioucash.api.requestbody.UserRequestParams;
import com.aproteam.ioucash.api.requestbody.UserTransactionRequestParams;
import com.aproteam.ioucash.model.Result;
import com.aproteam.ioucash.model.Transaction;
import com.aproteam.ioucash.model.User;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A class responsible for handling API requests and responses.
 * It uses Retrofit to communicate with the API endpoints and LiveData to provide not synchronous data updates.
 */
public class ApiRepository {

    private final IouCashService apiService;

    /**
     * Constructor of the ApiRepository class.
     * It initializes the Retrofit API service with the base URL and sets up the http client with headers and logging.
     */
    public ApiRepository() {
        apiService = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(chain -> {
                    Request.Builder builder = chain.request().newBuilder();
                    SessionManager sessionManager = SessionManager.getInstance(App.get());
                    User user = sessionManager.readUserData();
                    if (user != null) {
                        builder.addHeader("token", user.token);
                    }
                    Request request = builder.build();
                    return chain.proceed(request);
                }).addInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                ).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IouCashService.class);
    }

    /**
     * Sends a login request to API and returns the response as LiveData.
     *
     * @param username user's username
     * @param password user's password
     * @return LiveData object containing the user data
     */
    public LiveData<User> login(String username, String password) {
        MutableLiveData<User> data = new MutableLiveData<>();
        apiService.login(new UserAuthorizationParams(username, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    /**
     * Sends a registration request to API and returns the response as LiveData.
     *
     * @param username user's username
     * @param password user's password
     * @return LiveData object containing the user data
     */
    public LiveData<User> register(String username, String password) {
        MutableLiveData<User> data = new MutableLiveData<>();
        apiService.register(new UserAuthorizationParams(username, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    /**
     * Gets the list of transactions from API and returns it as LiveData.
     *
     * @return LiveData object containing the list of transactions
     */
    public LiveData<List<Transaction>> getTransactions() {
        MutableLiveData<List<Transaction>> data = new MutableLiveData<>();
        apiService.getTransactions().enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(@NonNull Call<List<Transaction>> call, @NonNull Response<List<Transaction>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Transaction>> call, @NonNull Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    /**
     * Sends a request to remove a user from API and returns the response as LiveData.
     *
     * @param username username of the user to be removed
     * @return LiveData object containing the response
     */
    public LiveData<Object> removeUser(String username) {
        MutableLiveData<Object> data = new MutableLiveData<>();
        apiService.removeUser(username).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    /**
     * Sends a request to change user's password and returns updated user data as LiveData.
     *
     * @param changeUserPasswordParams parameters for changing the user's password
     * @return LiveData object containing the updated user data
     */
    public LiveData<User> changePassword(ChangeUserPasswordParams changeUserPasswordParams) {
        MutableLiveData<User> data = new MutableLiveData<>();
        apiService.changePassword(changeUserPasswordParams).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                data.postValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    /**
     * Sends a request to remove a transaction and returns the removed transaction as LiveData.
     *
     * @param transaction transaction to be removed
     * @return LiveData object containing the removed transaction
     */
    public LiveData<Transaction> removeTransaction(Transaction transaction) {
        MutableLiveData<Transaction> data = new MutableLiveData<>();
        apiService.removeTransaction(transaction.sender.username, transaction.receiver.username).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(@NonNull Call<Transaction> call, @NonNull Response<Transaction> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Transaction> call, @NonNull Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    /**
     * Sends a request to add a transaction and returns the result as LiveData.
     *
     * @param params parameters for adding the transaction
     * @return LiveData object containing the result of adding the transaction
     */
    public LiveData<Result> addTransaction(UserTransactionRequestParams params) {
        MutableLiveData<Result> data = new MutableLiveData<>();
        apiService.addTransaction(params).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    /**
     * Retrieves the list of transactions by sender username and returns it as LiveData.
     *
     * @param userRequestParams parameters for retrieving the transactions by sender
     * @return LiveData object containing the list of transactions
     */
    public LiveData<List<Transaction>> getTransactionsBySender(UserRequestParams userRequestParams) {
        MutableLiveData<List<Transaction>> data = new MutableLiveData<>();
        apiService.getTransactionsBySender(userRequestParams.username).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(@NonNull Call<List<Transaction>> call, @NonNull Response<List<Transaction>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Transaction>> call, @NonNull Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    /**
     * Retrieves the list of transactions by receiver username and returns it as LiveData.
     *
     * @param userRequestParams parameters for retrieving the transactions by receiver
     * @return LiveData object containing the list of transactions
     */
    public LiveData<List<Transaction>> getTransactionsByReceiver(UserRequestParams userRequestParams) {
        MutableLiveData<List<Transaction>> data = new MutableLiveData<>();
        apiService.getTransactionsByReceiver(userRequestParams.username).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(@NonNull Call<List<Transaction>> call, @NonNull Response<List<Transaction>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Transaction>> call, @NonNull Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    /**
     * Gets the list of users from API and returns it as LiveData.
     *
     * @return LiveData object containing the list of users
     */
    public LiveData<List<User>> getUsers() {
        MutableLiveData<List<User>> data = new MutableLiveData<>();
        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

}
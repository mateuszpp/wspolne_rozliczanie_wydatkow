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

public class ApiRepository {

    private final IouCashService apiService;

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
package com.aproteam.ioucash.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aproteam.ioucash.App;
import com.aproteam.ioucash.Constants;
import com.aproteam.ioucash.SessionManager;
import com.aproteam.ioucash.api.requestbody.ChangeUserPasswordParams;
import com.aproteam.ioucash.api.requestbody.UserAuthorizationParams;
import com.aproteam.ioucash.api.requestbody.UserTransactionRequestParams;
import com.aproteam.ioucash.model.Transaction;
import com.aproteam.ioucash.model.User;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
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
            public void onResponse(Call<Object> call, Response<Object> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    public LiveData<User> changePassword(ChangeUserPasswordParams changeUserPasswordParams) {
        MutableLiveData<User> data = new MutableLiveData<>();
        ChangeUserPasswordParams params = changeUserPasswordParams;
        apiService.changePassword(params).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                data.postValue(response.body());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
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
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    public LiveData<Object> addTransaction(UserTransactionRequestParams params) {
        MutableLiveData<Object> data = new MutableLiveData<>();
        apiService.addTransaction(params).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    public LiveData<List<Transaction>> getTransactionsBySender(String senderName) {
        MutableLiveData<List<Transaction>> data = new MutableLiveData<>();
        apiService.getTransactionsBySender(senderName).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

    public LiveData<List<Transaction>> getTransactionsByReceiver(String receiverName) {
        MutableLiveData<List<Transaction>> data = new MutableLiveData<>();
        apiService.getTransactionsByReceiver(receiverName).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });
        return data;
    }

}
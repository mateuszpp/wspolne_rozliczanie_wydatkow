package com.aproteam.ioucash.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aproteam.ioucash.Constants;
import com.aproteam.ioucash.api.requestbody.UserAuthorizationParams;
import com.aproteam.ioucash.model.Transaction;
import com.aproteam.ioucash.model.User;

import java.util.List;

import okhttp3.OkHttpClient;
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
				.client(new OkHttpClient.Builder().addInterceptor(
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

}
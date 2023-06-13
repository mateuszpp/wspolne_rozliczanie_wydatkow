package com.aproteam.ioucash.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aproteam.ioucash.SessionManager;
import com.aproteam.ioucash.activity.BaseActivity;
import com.aproteam.ioucash.api.ApiRepository;
import com.aproteam.ioucash.api.requestbody.UserRequestParams;
import com.aproteam.ioucash.model.Transaction;
import com.aproteam.ioucash.model.User;

import java.util.List;

@SuppressLint("StaticFieldLeak")
public class MainViewModel extends ViewModel {

	private final ApiRepository repository;

	private BaseActivity activity;
	private MainModelCallback callback;

	public MutableLiveData<List<Transaction>> transactionsData;
	public MutableLiveData<Boolean> busy = new MutableLiveData<>(false);

	public MainViewModel() {
		repository = new ApiRepository();
		transactionsData = new MutableLiveData<>();
	}

	public void setActivity(BaseActivity activity) {
		this.activity = activity;
	}

	public void onRefresh() {
		busy.setValue(true);
		UserRequestParams params = new UserRequestParams(activity.getSessionManager().readUserData());
		repository.getTransactionsByReceiver(params).observe(activity, transactions -> {
			busy.setValue(false);
			transactionsData.postValue(transactions);
		});
		repository.getTransactionsBySender(params).observe(activity, transactions -> {
			busy.setValue(false);
			transactionsData.postValue(transactions);
		});
	}

	public MutableLiveData<List<Transaction>> getTransations() {
		return transactionsData;
	}

	public MutableLiveData<Boolean> getBusy() {
		return busy;
	}

	public void setMainModelCallback(MainModelCallback callback) {
		this.callback = callback;
	}

	public interface MainModelCallback {
		void onLogout();
	}

	public MainModelCallback getCallback() {
		return callback;
	}

}
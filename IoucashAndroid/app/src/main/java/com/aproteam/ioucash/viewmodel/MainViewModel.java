package com.aproteam.ioucash.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aproteam.ioucash.activity.BaseActivity;
import com.aproteam.ioucash.api.ApiRepository;
import com.aproteam.ioucash.api.requestbody.UserRequestParams;
import com.aproteam.ioucash.model.Transaction;

import java.util.List;

@SuppressLint("StaticFieldLeak")
public class MainViewModel extends ViewModel {

	private final ApiRepository repository;

	private BaseActivity activity;
	private MainModelCallback callback;

	public MutableLiveData<List<Transaction>> transactionsDataBySender = new MutableLiveData<>();
	public MutableLiveData<List<Transaction>> transactionsDataByReceiver = new MutableLiveData<>();
	public MutableLiveData<Boolean> busy = new MutableLiveData<>(false);

	public MainViewModel() {
		repository = new ApiRepository();
	}

	public void setActivity(BaseActivity activity) {
		this.activity = activity;
	}

	public void onRefresh() {
		busy.setValue(true);
		UserRequestParams params = new UserRequestParams(activity.getSessionManager().readUserData());
		repository.getTransactionsByReceiver(params).observe(activity, transactions -> {
			if (transactions != null)
				transactionsDataByReceiver.postValue(transactions);
			busy.setValue(false);
		});
		repository.getTransactionsBySender(params).observe(activity, transactions -> {
			if (transactions != null)
				transactionsDataBySender.postValue(transactions);
			busy.setValue(false);
		});
	}

	public MutableLiveData<List<Transaction>> getTransactionsBySender() {
		return transactionsDataBySender;
	}

	public void removeTransaction(Transaction transaction) {
		busy.setValue(true);
		repository.removeTransaction(transaction).observe(activity, removedTransaction -> {
			busy.setValue(false);
			onRefresh();
		});
	}

	public MutableLiveData<List<Transaction>> getTransations() {
		return transactionsDataBySender;
	}

	public MutableLiveData<List<Transaction>> getTransactionsByReceiver() {
		return transactionsDataByReceiver;
	}

	public MutableLiveData<Boolean> getBusy() {
		return busy;
	}

	public void setMainModelCallback(MainModelCallback callback) {
		this.callback = callback;
	}

	public interface MainModelCallback {
		void onLogout();

		void onAddTransaction();
	}

	public MainModelCallback getCallback() {
		return callback;
	}

}
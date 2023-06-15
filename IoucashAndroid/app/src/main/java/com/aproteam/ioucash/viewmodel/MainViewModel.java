package com.aproteam.ioucash.viewmodel;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aproteam.ioucash.R;
import com.aproteam.ioucash.activity.BaseActivity;
import com.aproteam.ioucash.api.ApiRepository;
import com.aproteam.ioucash.api.requestbody.UserRequestParams;
import com.aproteam.ioucash.api.requestbody.UserTransactionRequestParams;
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

	/**
	 * Refreshes the list of transactions by making API calls to retrieve transactions by sender and receiver
	 */
	public void onRefresh() {
		busy.setValue(true);
		UserRequestParams params = new UserRequestParams(activity.getUser());
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

	/**
	 * Removes a transaction by making an API call and updates the list of transactions.
	 * @param transaction the transaction to be removed.
	 */
	public void removeTransaction(Transaction transaction) {
		busy.setValue(true);
		Transaction payBackTransaction = new Transaction();
		payBackTransaction.amount=transaction.amount;
		payBackTransaction.sender=transaction.receiver;
		payBackTransaction.receiver=transaction.sender;
		repository.addTransaction(new UserTransactionRequestParams(transaction)).observe(activity, removedTransaction -> {
			if (callback != null) {
				if (removedTransaction != null)
					callback.onTransactionRemoved();
				else
					callback.onTransactionNotRemoved();
			}
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

		void onTransactionRemoved();

		void onTransactionNotRemoved();
	}

	public MainModelCallback getCallback() {
		return callback;
	}

}
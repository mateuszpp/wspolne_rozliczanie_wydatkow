package com.aproteam.ioucash.viewmodel;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.aproteam.ioucash.R;
import com.aproteam.ioucash.activity.BaseActivity;
import com.aproteam.ioucash.api.ApiRepository;
import com.aproteam.ioucash.api.requestbody.UserTransactionRequestParams;
import com.aproteam.ioucash.model.Transaction;
import com.aproteam.ioucash.model.User;

import java.util.List;

/**
 * ViewModel for adding a transaction.
 */
@SuppressLint("StaticFieldLeak")
public class AddTransactionViewModel extends ViewModel {

	public MutableLiveData<String> senderError = new MutableLiveData<>();
	public MutableLiveData<String> receiverError = new MutableLiveData<>();
	public MutableLiveData<String> amountError = new MutableLiveData<>();

	public MutableLiveData<String> senderName = new MutableLiveData<>();
	public MutableLiveData<String> receiverName = new MutableLiveData<>();
	public MutableLiveData<String> amount = new MutableLiveData<>();
	public MutableLiveData<Boolean> directionFromMe = new MutableLiveData<>(true);
	public MutableLiveData<List<User>> users = new MutableLiveData<>();
	public MutableLiveData<Integer> busy = new MutableLiveData<>(View.GONE);
	private final ApiRepository repository;

	private BaseActivity activity;
	private AddTransactionListener addTransactionListener;

	public AddTransactionViewModel() {
		repository = new ApiRepository();
	}

	public void setActivity(BaseActivity activity) {
		this.activity = activity;
		senderName.setValue(activity.getUser().username);
	}

	public void setListener(AddTransactionListener addTransactionListener) {
		this.addTransactionListener = addTransactionListener;
	}

	/**
	 * Loads the list of users.
	 */
	public void loadUsers() {
		busy.setValue(View.VISIBLE);
		repository.getUsers().observe(activity, users -> {
			if (users != null)
				this.users.postValue(users);
			busy.setValue(View.GONE);
		});
	}

	/** @noinspection DataFlowIssue*/
	/**
	 * Called when the reverse order button is clicked.
	 * Reverses the sender and receiver names and updates the direction.
	 */
	public void onReverseOrderClicked() {
		String sender = senderName.getValue();
		String receiver = receiverName.getValue();
		senderName.postValue(receiver);
		receiverName.postValue(sender);
		directionFromMe.postValue(!directionFromMe.getValue());
	}

	/** @noinspection DataFlowIssue*/
	/**
	 * Called when the reverse order button is clicked.
	 * Reverses the sender and receiver names and updates the direction.
	 */
	public void onAddTransactionClicked() {
		if (checkIfFieldsEmpty())
			return;
		busy.setValue(View.VISIBLE);
		repository.addTransaction(new UserTransactionRequestParams(senderName.getValue(), receiverName.getValue(), Double.parseDouble(amount.getValue()))).observe(activity, result -> {
			busy.setValue(View.GONE);
			if (result != null && result.success) {
				if (addTransactionListener != null)
					addTransactionListener.onAddSuccess();
				receiverError.setValue(null);
				senderError.setValue(null);
			} else {
				if (directionFromMe.getValue())
					receiverError.setValue(activity.getString(R.string.errorUsernameIncorrect));
				else
					senderError.setValue(activity.getString(R.string.errorUsernameIncorrect));
			}
		});
	}

	/**
	 * Checks if the sender and receiver fields are empty.
	 *
	 * @return true if any of the fields is empty, false if not
	 */
	public boolean checkIfFieldsEmpty() {
		boolean empty = false;
		if (senderName.getValue() == null || senderName.getValue().isEmpty()) {
			empty = true;
			receiverError.setValue(activity.getString(R.string.errorPleaseFillAllFields));
		} else {
			receiverError.setValue(null);
		}
		if (receiverName.getValue() == null || receiverName.getValue().isEmpty()) {
			empty = true;
			senderError.setValue(activity.getString(R.string.errorPleaseFillAllFields));
		} else {
			senderError.setValue(null);
		}
		if (amount.getValue() == null || amount.getValue().isEmpty()) {
			empty = true;
			amountError.setValue(activity.getString(R.string.errorPleaseFillAllFields));
		} else {
			amountError.setValue(null);
		}
		return empty;
	}

	public MutableLiveData<Integer> getBusy() {
		return busy;
	}

	public interface AddTransactionListener {
		void onAddSuccess();
	}

}
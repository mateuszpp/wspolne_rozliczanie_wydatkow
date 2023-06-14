package com.aproteam.ioucash.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.aproteam.ioucash.R;
import com.aproteam.ioucash.activity.BaseActivity;
import com.aproteam.ioucash.api.ApiRepository;
import com.aproteam.ioucash.model.Transaction;
import com.aproteam.ioucash.model.User;

import java.util.List;

@SuppressLint("StaticFieldLeak")
public class AddTransactionViewModel extends ViewModel {

	public MutableLiveData<String> senderError = new MutableLiveData<>();
	public MutableLiveData<String> receiverError = new MutableLiveData<>();

	public MutableLiveData<String> senderName = new MutableLiveData<>();
	public MutableLiveData<String> receiverName = new MutableLiveData<>();
	public MutableLiveData<String> amount = new MutableLiveData<>();
	public MutableLiveData<Boolean> directionFromMe = new MutableLiveData<>(true);
	public MutableLiveData<List<User>> users = new MutableLiveData<>();
	public MutableLiveData<Integer> busy = new MutableLiveData<>(View.GONE);
	private final ApiRepository repository;

	private BaseActivity activity;
	private AddTransactionListener addTransactionListener;
	private final Observer<User> userObserver = userResponse -> {
		busy.setValue(View.GONE);
		if (userResponse != null) {
			//if (addTransactionListener != null)
			//	addTransactionListener.onAddSuccess(userResponse);
			receiverError.setValue(null);
			senderError.setValue(null);
		} else {
			receiverError.setValue(activity.getString(R.string.errorUserPasswordIncorrect));
			senderError.setValue(activity.getString(R.string.errorUserPasswordIncorrect));
		}
	};

	public AddTransactionViewModel() {
		repository = new ApiRepository();
	}

	public void setActivity(BaseActivity activity) {
		this.activity = activity;
		senderName.setValue(activity.getSessionManager().readUserData().username);
	}

	public void setLoginListener(AddTransactionListener addTransactionListener) {
		this.addTransactionListener = addTransactionListener;
	}

	public void loadUsers() {
		busy.setValue(View.VISIBLE);
		repository.getUsers().observe(activity, users -> {
			if (users != null)
				this.users.postValue(users);
			busy.setValue(View.GONE);
		});
	}

	/** @noinspection DataFlowIssue*/
	public void onReverseOrderClicked() {
		String sender = senderName.getValue();
		String receiver = receiverName.getValue();
		senderName.postValue(receiver);
		receiverName.postValue(sender);
		directionFromMe.postValue(!directionFromMe.getValue());
	}

	public void onAddTransactionClicked() {
		if (checkIfFieldsEmpty())
			return;
		busy.setValue(View.VISIBLE);
		repository.login(senderName.getValue(), receiverName.getValue()).observe(activity, userObserver);
	}

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
		return empty;
	}

	public MutableLiveData<Integer> getBusy() {
		return busy;
	}

	public interface AddTransactionListener {
		void onAddSuccess(Transaction transaction);
	}

}
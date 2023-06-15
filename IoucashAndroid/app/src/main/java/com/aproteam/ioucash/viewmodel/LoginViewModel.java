package com.aproteam.ioucash.viewmodel;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.aproteam.ioucash.R;
import com.aproteam.ioucash.activity.BaseActivity;
import com.aproteam.ioucash.api.ApiRepository;
import com.aproteam.ioucash.model.User;

@SuppressLint("StaticFieldLeak")
public class LoginViewModel extends ViewModel {

	public MutableLiveData<String> errorPassword = new MutableLiveData<>();
	public MutableLiveData<String> errorEmail = new MutableLiveData<>();

	public MutableLiveData<String> email = new MutableLiveData<>();
	public MutableLiveData<String> password = new MutableLiveData<>();
	public MutableLiveData<Integer> busy;
	private final ApiRepository repository;

	private BaseActivity loginActivity;
	private LoginListener loginListener;
	private final Observer<User> userObserver = userResponse -> {
		busy.setValue(View.GONE);
		if (userResponse != null) {
			if (loginListener != null)
				loginListener.onLoginSuccess(userResponse);
			errorEmail.setValue(null);
			errorPassword.setValue(null);
		} else {
			errorEmail.setValue(loginActivity.getString(R.string.errorUserPasswordIncorrect));
			errorPassword.setValue(loginActivity.getString(R.string.errorUserPasswordIncorrect));
		}
	};

	public LoginViewModel() {
		repository = new ApiRepository();
	}

	public void setActivity(BaseActivity loginActivity) {
		this.loginActivity = loginActivity;
	}

	public void setLoginListener(LoginListener loginListener) {
		this.loginListener = loginListener;
	}

	/**
	 * Handles the click event when the login button is clicked.
	 * If the fields are not empty, it initiates the login process.
	 */
	public void onLoginClicked() {
		if (checkIfFieldsEmpty())
			return;
		busy.setValue(View.VISIBLE);
		repository.login(email.getValue(), password.getValue()).observe(loginActivity, userObserver);
	}

	/**
	 * Handles the click event when the register button is clicked.
	 * If the fields are not empty, it initiates the registration process.
	 */
	public void onRegisterClicked() {
		if (checkIfFieldsEmpty())
			return;
		busy.setValue(View.VISIBLE);
		repository.register(email.getValue(), password.getValue()).observe(loginActivity, userObserver);
	}

	/**
	 * Checks if the email and password fields are not empty.
	 * If any of the fields are empty, it sets the corresponding error message.
	 * @return true if any of the fields are empty, false if not
	 */
	public boolean checkIfFieldsEmpty() {
		boolean empty = false;
		if (email.getValue() == null || email.getValue().isEmpty()) {
			empty = true;
			errorEmail.setValue(loginActivity.getString(R.string.errorPleaseFillAllFields));
		} else {
			errorEmail.setValue(null);
		}
		if (password.getValue() == null || password.getValue().isEmpty()) {
			empty = true;
			errorPassword.setValue(loginActivity.getString(R.string.errorPleaseFillAllFields));
		} else {
			errorPassword.setValue(null);
		}
		return empty;
	}

	/**
	 * Gets the MutableLiveData object in the busy state.
	 * If the object is not initialized, it initializes it with the initial value of View.GONE.
	 * @return the MutableLiveData object for the busy state
	 */
	public MutableLiveData<Integer> getBusy() {
		if (busy == null) {
			busy = new MutableLiveData<>();
			busy.setValue(View.GONE);
		}
		return busy;
	}

	public interface LoginListener {
		void onLoginSuccess(User user);
	}

}
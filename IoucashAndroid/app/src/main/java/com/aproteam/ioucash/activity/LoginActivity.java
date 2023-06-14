package com.aproteam.ioucash.activity;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.aproteam.ioucash.App;
import com.aproteam.ioucash.R;
import com.aproteam.ioucash.databinding.ActivityLoginBinding;
import com.aproteam.ioucash.model.User;
import com.aproteam.ioucash.viewmodel.LoginViewModel;

/**
 * A class responsible for user login functionality, it allows user to log in.
 * After the successful login redirects the user to the main activity.
 */
public class LoginActivity extends BaseActivity implements LoginViewModel.LoginListener {

	ActivityLoginBinding binding;

	/**
	 * Creates the user interface of the activity.
	 * If the user is already logged in, redirects him to the main activity.
	 * If not, it sets up data binding, view model and event listeners.
	 */
	@Override
	public void createUI() {
		if (getSessionManager().isLoggedIn()) {
			startActivity(new Intent(this, MainActivity.class));
			finish();
			return;
		}
		binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
		setTitle(R.string.loginTitle);
		LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
		loginViewModel.setActivity(this);
		binding.setViewModel(loginViewModel);
		binding.setLifecycleOwner(this);
		loginViewModel.setLoginListener(this);
		openIME(binding.usernameEditText);
	}

	@Override
	public boolean shouldShowBackButton() {
		return false;
	}

	@Override
	public void onLoginSuccess(User user) {
		App.toast(R.string.loginSuccess);
		getSessionManager().saveUserData(user);
		setResult(RESULT_OK);
		finish();
		startActivity(new Intent(this, MainActivity.class));
	}

}
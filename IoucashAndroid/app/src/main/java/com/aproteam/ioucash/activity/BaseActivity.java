package com.aproteam.ioucash.activity;

import android.animation.LayoutTransition;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import com.aproteam.ioucash.App;
import com.aproteam.ioucash.Prefs;
import com.aproteam.ioucash.R;
import com.aproteam.ioucash.SessionManager;
import com.aproteam.ioucash.model.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public abstract class BaseActivity extends AppCompatActivity {

	public ComponentName parentActivity;
	ActionBar actionBar;
	boolean resumed;
	public SharedPreferences sp;
	public InputMethodManager imm;
	public ClipboardManager clipboard;
	int lastAppTheme;
	SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = (sharedPreferences, key) -> {
		if (Prefs.APP_THEME.equals(key) && (lastAppTheme != Prefs.getThemePref(this)))
			recreate();
	};
	RequestManager glide;
	private SessionManager sessionManager;
	private User user;

	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sessionManager = SessionManager.getInstance(this);
		user = getSessionManager().readUserData();
		parentActivity = getCallingActivity();
		lastAppTheme = Prefs.getThemePref(this);
		sp = Prefs.get();
		sp.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		glide = Glide.with(this);
		createUI();
		actionBar = getSupportActionBar();
		if (shouldShowBackButton())
			actionBar.setDisplayHomeAsUpEnabled(true);
		animateLayoutChanges(findViewById(R.id.root));
	}

	public abstract void createUI();

	public abstract boolean shouldShowBackButton();

	public void animateLayoutChanges(ViewGroup viewGroup) {
		if (viewGroup != null) {
			LayoutTransition transition = new LayoutTransition();
			transition.disableTransitionType(LayoutTransition.DISAPPEARING);
			transition.enableTransitionType(LayoutTransition.CHANGING);
			viewGroup.setLayoutTransition(transition);
		}
	}

	@Override
	public void onBackPressed() {
		if (parentActivity != null)
			NavUtils.navigateUpTo(this, new Intent().setComponent(parentActivity));
		else
			super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home)
			onBackPressed();
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(Prefs.setupTheme(newBase));
		getDelegate().applyDayNight();
	}

	@Override
	protected void onResume() {
		super.onResume();
		resumed = true;
	}

	@Override
	protected void onPause() {
		resumed = false;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		sp.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
	}

	private Runnable afterPermissionRequestRunnable;

	public void checkAppPermissionAndRun(String permission, Runnable runnable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
			afterPermissionRequestRunnable = runnable;
			requestPermissions(new String[]{permission}, 0);
		} else
			runnable.run();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			if (afterPermissionRequestRunnable != null)
				afterPermissionRequestRunnable.run();
		} else
			App.toast(R.string.errorPermission);
		afterPermissionRequestRunnable = null;
	}


	public void openIME(View view) {
		view.post(() -> {
			view.requestFocus();
			imm.showSoftInput(view, 0);
		});
	}

	public void closeIME(View view) {
		view.post(() -> {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			view.clearFocus();
		});
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public User getUser() {
		return user;
	}
}
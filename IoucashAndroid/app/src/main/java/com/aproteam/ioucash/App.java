package com.aproteam.ioucash;

import android.content.Context;
import android.widget.Toast;

import androidx.multidex.MultiDexApplication;

public class App extends MultiDexApplication {

	static App mThis;

	public static App get() {
		return mThis;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mThis = this;
	}

	@Override
	protected void attachBaseContext(Context base) {
		mThis = this;
		super.attachBaseContext(Prefs.setupTheme(base));
	}

	public static void toast(int resId) {
		Toast.makeText(get(), resId, Toast.LENGTH_SHORT).show();
	}

	public static void toast(CharSequence text) {
		Toast.makeText(get(), text, Toast.LENGTH_SHORT).show();
	}

}
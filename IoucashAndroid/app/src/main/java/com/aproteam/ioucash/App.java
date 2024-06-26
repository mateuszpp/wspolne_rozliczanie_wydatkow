package com.aproteam.ioucash;

import android.content.Context;
import android.widget.Toast;

import androidx.multidex.MultiDexApplication;

/**
 * An application class
 */
public class App extends MultiDexApplication {

	static App mThis;

	/**
	 * Retrieves the singleton instance of the App class.
	 * @return the singleton instance of the App class
	 */
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

	/**
	 * Displays a short toast message with the provided string resource ID.
	 * @param resId the string resource ID
	 */
	public static void toast(int resId) {
		Toast.makeText(get(), resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Displays a short toast message with the provided text.
	 * @param text the text to be displayed
	 */
	public static void toast(CharSequence text) {
		Toast.makeText(get(), text, Toast.LENGTH_SHORT).show();
	}

}
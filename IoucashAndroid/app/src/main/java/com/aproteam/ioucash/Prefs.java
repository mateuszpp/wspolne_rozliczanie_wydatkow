package com.aproteam.ioucash;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class Prefs {

	public static final String APP_THEME = "APP_THEME";

	/**
	 * Retrieves the default SharedPreferences instance.
	 *
	 * @return default SharedPreferences instance
	 */
	public static SharedPreferences get() {
		return PreferenceManager.getDefaultSharedPreferences(App.get());
	}

	/**
	 * Retrieves the theme preference value.
	 *
	 * @param context the context
	 * @return theme preference value
	 */
	public static int getThemePref(Context context) {
		return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString(APP_THEME, "0"));
	}

	public static boolean isDark(Context context) {
		int app_theme = getThemePref(context);
		if (app_theme == 0)
			return isDarkSystem(context);
		else
			return app_theme == 2 || app_theme == 3;
	}

	/**
	 * Retrieves the theme value based on the app theme preference.
	 *
	 * @param context the context
	 * @return theme value
	 */
	static int getTheme(Context context) {
		int app_theme = getThemePref(context);
		if (app_theme == 0)
			return isDarkSystem(context) ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;//powinno być po prostu AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
		else if (app_theme == 1)
			return AppCompatDelegate.MODE_NIGHT_NO;
		else// if(app_theme == 2)
			return AppCompatDelegate.MODE_NIGHT_YES;
	}

	/**
	 * Checks if the system is in dark mode.
	 *
	 * @param context the context
	 * @return true if the system is in dark mode, false if not
	 */
	static boolean isDarkSystem(Context context) {
		return (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
	}

	/**
	 * Sets up theme for the app based on the theme preference.
	 *
	 * @param context actual context
	 * @return updated context
	 */
	public static Context setupTheme(Context context) {
		Resources res = context.getResources();
		Configuration config = new Configuration(res.getConfiguration());
		int mode = getTheme(context);
		config.uiMode = mode;
		AppCompatDelegate.setDefaultNightMode(mode);

		context = context.createConfigurationContext(config);
		res.updateConfiguration(config, res.getDisplayMetrics());
		return context;
	}

}
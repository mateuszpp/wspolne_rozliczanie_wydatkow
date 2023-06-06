package com.aproteam.ioucash;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class Prefs {

	public static final String APP_THEME = "APP_THEME";

	public static SharedPreferences get() {
		return PreferenceManager.getDefaultSharedPreferences(App.get());
	}

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

	static int getTheme(Context context) {
		int app_theme = getThemePref(context);
		if (app_theme == 0)
			return isDarkSystem(context) ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;//powinno być po prostu AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
		else if (app_theme == 1)
			return AppCompatDelegate.MODE_NIGHT_NO;
		else// if(app_theme == 2)
			return AppCompatDelegate.MODE_NIGHT_YES;
	}

	static boolean isDarkSystem(Context context) {
		return (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
	}

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
package com.aproteam.ioucash.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.aproteam.ioucash.BuildConfig;
import com.aproteam.ioucash.R;

/**
 * A class representing the settings screen activity.
 * It allows the user to change settings and view of the app.
 */
public class SettingsActivity extends BaseActivity {

	/**
	 * Creates the user interface of the activity.
	 * It sets the content view to the settings activity layout.
	 */
	@Override
	public void createUI() {
		setContentView(R.layout.activity_settings);
	}

	@Override
	public boolean shouldShowBackButton() {
		return true;
	}

	/**
	 * A static nested class representing the preference fragment of the settings screen.
	 */
	public static class PrefsFragment extends PreferenceFragmentCompat {

		BaseActivity activity;

		@Override
		public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
			addPreferencesFromResource(R.xml.settings);
			activity = (BaseActivity) getActivity();
			Preference version = findPreference("version");
			if (version != null)
				version.setSummary(BuildConfig.VERSION_NAME);
		}

		@Override
		public boolean onPreferenceTreeClick(@NonNull Preference preference) {
			return super.onPreferenceTreeClick(preference);
		}

	}

}
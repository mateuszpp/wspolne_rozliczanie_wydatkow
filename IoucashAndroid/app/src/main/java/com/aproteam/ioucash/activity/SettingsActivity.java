package com.aproteam.ioucash.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.aproteam.ioucash.BuildConfig;
import com.aproteam.ioucash.R;

public class SettingsActivity extends BaseActivity {

	@Override
	public void createUI() {
		setContentView(R.layout.activity_settings);
	}

	@Override
	public boolean shouldShowBackButton() {
		return true;
	}

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
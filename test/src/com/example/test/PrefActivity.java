package com.example.test;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.DisplayMetrics;
import me.noip.tele2kz.R;

public class PrefActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref);
	}

	public static String getStringInDefaultLocale(int resId, Activity act) {
		Resources currentResources = act.getResources();
		AssetManager assets = currentResources.getAssets();
		DisplayMetrics metrics = currentResources.getDisplayMetrics();
		Configuration config = new Configuration(
				currentResources.getConfiguration());
		config.locale = act.getResources().getConfiguration().locale;

		Resources defaultLocaleResources = new Resources(assets, metrics,
				config);
		String string = defaultLocaleResources.getString(resId);
		// Restore device-specific locale
		new Resources(assets, metrics, currentResources.getConfiguration());
		return string;
	}
}
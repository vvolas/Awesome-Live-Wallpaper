package com.wolas.awesomewallpaper;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by wolas on 16.8.12.
 */
public class SettingsActivity extends Activity {

    public static final String AA_AND_DITHER = "AA_and_Dither";
    public static final String B_FILTER = "BFilter";

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        //addPreferencesFromResource(R.xml.preferences);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment())
                .commit();

    }
}


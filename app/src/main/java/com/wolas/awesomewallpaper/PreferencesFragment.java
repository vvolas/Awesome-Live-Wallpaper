package com.wolas.awesomewallpaper;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by wolas on 16.8.12.
 */
public class PreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        addPreferencesFromResource(R.xml.preferences);
    }
}


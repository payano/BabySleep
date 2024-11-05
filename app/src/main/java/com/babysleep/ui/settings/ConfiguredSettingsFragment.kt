package com.babysleep.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat;
import com.babysleep.R

class ConfiguredSettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        super.setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
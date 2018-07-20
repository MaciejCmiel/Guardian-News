package com.example.macx.guardiannews;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity
         {

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        }


    public static class NewsPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener{

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference date = findPreference(getString(R.string.settings_date_key));
            bindPreferenceSummaryToValue(date);

            // find the CheckBox
            CheckBoxPreference debateCheckBox = (CheckBoxPreference) findPreference(getString(R.string.settings_debate_key));
            bindPreferenceSummaryToValueBoolean(debateCheckBox);

            CheckBoxPreference economyCheckBox = (CheckBoxPreference) findPreference(getString(R.string.settings_debate_key));
            bindPreferenceSummaryToValueBoolean(economyCheckBox);

        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            // The code in this method takes care of updating the displayed preference summary after it has been changed
            String stringValue = newValue.toString();

            if(preference instanceof ListPreference) {
                ListPreference listPref = (ListPreference) preference;
                int prefIndex = listPref.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPref.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            }else {
                preference.setSummary(stringValue);
            }

            return true;
        }

        private void bindPreferenceSummaryToValue(Preference pref) {
            pref.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(pref.getContext());
            String preferenceString = preferences.getString(pref.getKey(), "");
            onPreferenceChange(pref, preferenceString);
        }

        private void bindPreferenceSummaryToValueBoolean(Preference boolPref) {
            boolPref.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(boolPref.getContext());
            boolean preferenceBoolean = preferences.getBoolean(boolPref.getKey(), true);
            onPreferenceChange(boolPref, preferenceBoolean);
        }
    }
}

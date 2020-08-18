package mz.co.scn.hinario_lite.activity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import mz.co.scn.hinario_lite.R;

/**
 * Created by Sidónio Goenha on 6/21/2019.
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     *
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValue = (preference, newValue) -> {
        String stringValue = newValue.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list.
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            // Set the summary to reflect the new value.
            //preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
        } else {
            // For all other preferences, set the summary to the value's
            // simple string representation.
            preference.setSummary(stringValue);
        }
        return true;
    };

    /**
     *
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * @param preference
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValue);
// Trigger the listener immediately with the preference's current value.
        if (preference.getKey().equals("dark_mode"))
            sBindPreferenceSummaryToValue.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getBoolean(preference.getKey(), false));
        else
            sBindPreferenceSummaryToValue.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));

    }

    /**
     *
     */
    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.root_preferences);

            //setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference("language"));
            bindPreferenceSummaryToValue(findPreference("lyric_style"));
            bindPreferenceSummaryToValue(findPreference("lyric_size"));
            bindPreferenceSummaryToValue(findPreference("book"));

            ListPreference language = findPreference("language");

            int indexL = language.findIndexOfValue(android.preference.PreferenceManager.getDefaultSharedPreferences(language.getContext()).getString("language", "en"));

            // Set the summary to reflect the new value.
            ///language.setSummary(indexL >= 0 ? language.getEntries()[indexL] : null);
            language.setOnPreferenceChangeListener((preference, o) -> {
                //String stringValue = o.toString();

                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                //ListPreference listPreference = (ListPreference) preference;
                //int index = listPreference.findIndexOfValue(stringValue);

                //Set the summary to reflect the new value.
                //preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);

                // Use to refresh view
                getActivity().recreate();
                return true;
            });
        }

        /**
         * @param savedInstanceState
         * @param rootKey
         */
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        }

       /* @Override
        public void onPrepareOptionsMenu(@NonNull Menu menu) {
            super.onPrepareOptionsMenu(menu);
            menu.findItem(R.id.search).setVisible(false);
            menu.findItem(R.id.total_songs).setVisible(false);
        }*/
    }


}

package ua.rDev.myEng.view;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.Locale;

import ua.rDev.myEng.R;
import ua.rDev.myEng.Utill;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by pk on 03.07.2017.
 */

public class SettingsFragment extends PreferenceFragment {
    ListPreference languagePref;
    Preference dialogPreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_layout);
        languagePref = (ListPreference) findPreference("language");
        final SharedPreferences preferences = getDefaultSharedPreferences(getActivity());
        final ListPreference listPreference = (ListPreference) findPreference("color");
        String lang = preferences.getString("language", Locale.getDefault().getLanguage());
        if (lang.equals("uk")) {
            languagePref.setSummary(getString(R.string.ukr_lang));
        } else {
            languagePref.setSummary(getString(R.string.rus_lang));
        }
        String color = preferences.getString("color", "1");
        if (color.equals("1")) {
            listPreference.setSummary(getString(R.string.brown));
        } else {
            listPreference.setSummary(getString(R.string.blue));
        }
        languagePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                updateLangState(o.toString());
                return true;
            }
        });
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preferenc, Object o) {
                updateState(o.toString(), listPreference);
                return true;
            }
        });
        dialogPreference =getPreferenceScreen().findPreference("dialog_preference");
        dialogPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();

                builder.setView(inflater.inflate(R.layout.about_layout, null));
                builder.create().show();
                return true;
            }
        });

    }

    private void updateLangState(String lang) {
        if (lang.equals("uk")) {
            Utill.changelang("uk", getActivity());
            languagePref.setSummary(getString(R.string.ukr_lang));
        } else {
            Utill.changelang("ru", getActivity());
            languagePref.setSummary(getString(R.string.rus_lang));
        }
    }

    private void updateState(String color, ListPreference listPreference) {
        if (color.equals("1")) {
            listPreference.setSummary(getString(R.string.brown));
        } else {
            listPreference.setSummary(getString(R.string.blue));
        }
    }
}

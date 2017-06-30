package ua.rDev.myEng.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import ua.rDev.myEng.R;

/**
 * Created by pk on 26.06.2017.
 */

public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_layout);
    }

    @Override
    public void onBackPressed() {
        close();
    }

    private void close() {
        Intent intent = new Intent(this, MainActivity.class);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim_enter_to_main, R.anim.anim_leave_to_main);
        startActivity(intent, options.toBundle());
        finish();
    }
}

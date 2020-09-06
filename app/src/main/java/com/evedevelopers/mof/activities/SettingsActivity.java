package com.evedevelopers.mof.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.evedevelopers.mof.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String
            KEY_PREF_VIBRATION_SWITCH = "vibration";
    public static final String
            KEY_PREF_SOUND_SWITCH = "sound";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        findViewById(R.id.rate).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.feedback).setOnClickListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
    }

    @Override
    public void onClick(View view) {
        Intent browserIntent;
        switch (view.getId()){
            case R.id.rate:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.evedevelopers.mof"));
                startActivity(browserIntent);
                break;
            case R.id.share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "M Overflow");
                    String shareMessage= "\nHey, I just found an awesome memory exercise !!!\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.evedevelopers.mof";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Share Game"));
                } catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.feedback:
                browserIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","philip@evedevelopers.com", null));
                browserIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback on M Overflow Game");
                browserIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(browserIntent, "Choose an Email client :"));
                break;
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

        }
    }
}
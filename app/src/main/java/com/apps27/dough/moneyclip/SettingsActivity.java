package com.apps27.dough.moneyclip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ivilcu on 03/18/2016.
 * Activity for Settings
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}

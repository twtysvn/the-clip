package com.apps27.dough.moneyclip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RecordsDBAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String user = prefs.getString(getString(R.string.pref_user_key),
                getString(R.string.pref_user_default));

        TextView userTextView = new TextView(this);
        userTextView = (TextView)findViewById(R.id.summary_name);
        userTextView.setText(user);

        dbHelper = new RecordsDBAdapter(this);
        dbHelper.open();

        dbHelper.deleteAllRecords();
        dbHelper.insertSomeRecords();

        displayListView();
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String user = prefs.getString(getString(R.string.pref_user_key),
                getString(R.string.pref_user_default));

        TextView userTextView = new TextView(this);
        userTextView = (TextView)findViewById(R.id.summary_name);
        userTextView.setText(user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayListView() {

        Cursor cursor = dbHelper.readAllRecords();

        String[] columnsFrom = new String[] {
                RecordsDBAdapter.COLUMN_DATE,
                RecordsDBAdapter.COLUMN_AMOUNT,
                RecordsDBAdapter.COLUMN_TYPE
        };

        int[] viewsTo = new int[] {
                R.id.text_plus_secondary,
                R.id.text_plus_amount,
                R.id.text_plus_primary
        };

        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.list_last_activity,cursor, columnsFrom, viewsTo, 0);

        final ListView listView = (ListView) findViewById(R.id.listview_last_activity);
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                String recordType = cursor.getString(cursor.getColumnIndexOrThrow("type"));

                Toast.makeText(getApplicationContext(), recordType, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
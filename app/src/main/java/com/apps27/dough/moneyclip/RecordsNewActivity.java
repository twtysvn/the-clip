package com.apps27.dough.moneyclip;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class RecordsNewActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_new);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);



    }
/*
    //selecting an item from dropdown
   // @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        //
    }
    */

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_records_new, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int screenNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            String layoutName = "";

            Resources res = getResources();
            String[] string_array = res.getStringArray(R.array.records_type_array);

            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
            String formatedDate = sdf.format(cal.getTime());


            switch (screenNumber) {
                //expense screen
                case 1:
                    final View rootView1 =
                            inflater.inflate(R.layout.fragment_records_new, container, false);
                    /*EditText yourEditText= (EditText) rootView1.findViewById(R.id.expense_amount);
                    InputMethodManager imm = (InputMethodManager) getContext().
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(yourEditText, InputMethodManager.SHOW_IMPLICIT);*/

                    //Spinner
                    final Spinner spinner = (Spinner) rootView1.findViewById(R.id.new_record_type);
                    //spinner.setOnItemSelectedListener(this);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, string_array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);

                    //display current date and time
                    final TextView dateTextView = (TextView) rootView1.findViewById(R.id.new_date);
                    dateTextView.setText(formatedDate);

                    //action on button
                    Button addButton = (Button) rootView1.findViewById(R.id.add_button);
                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //get text from amount
                            TextView amountTextView = (TextView)
                                    rootView1.findViewById(R.id.expense_amount);
                            String amount = amountTextView.getText().toString();
                            //compare if value is inserted or blank
                            if (amount.equals("")) {
                                Toast.makeText(getActivity(),
                                        "Spent nothing? Wallet says otherwise, liar!",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                            //get date and time
                            String dateTimeString = dateTextView.getText().toString();
                            //get dropdown list item
                            String spinnerText = spinner.getSelectedItem().toString();
                            //open db and write values
                            RecordsDBAdapter mRecords = new RecordsDBAdapter(getActivity());
                            mRecords.open();
                            mRecords.createRecord(dateTimeString, amount, spinnerText);
                            //show toast
                            Toast.makeText(getActivity(),
                                    "Just spent " + amount + " on " + spinnerText + ", stupid!",
                                    Toast.LENGTH_LONG).show();
                            //close screen
                            getActivity().finish();
                        }
                    });

                    return rootView1;


                case 2:
                    View rootView2 =
                            inflater.inflate(R.layout.fragment_records_new_2, container, false);
                    TextView textView2 = (TextView) rootView2.findViewById(R.id.section_label);
                    textView2.setText(getString(R.string.section_format,
                            getArguments().getInt(ARG_SECTION_NUMBER)));
                    return rootView2;
            }
            return null;

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Expense";
                case 1:
                    return "Income";
            }
            return null;
        }
    }
}

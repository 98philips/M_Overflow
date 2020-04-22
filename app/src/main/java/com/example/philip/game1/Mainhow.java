package com.example.philip.game1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.evedevelopers.mof.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class Mainhow extends AppCompatActivity {

    /**
     * The {@link androidx.viewpager.widget.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link androidx.fragment.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhow);
        SharedPreferences first = getSharedPreferences("first", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor edit = first.edit();
        edit.putBoolean("one",false);
        edit.apply();
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        final Button play = findViewById(R.id.play);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(mViewPager.getCurrentItem()==5){
                    fab.setVisibility(View.GONE);
                    play.setVisibility(View.VISIBLE);
                }
                else {
                    fab.setVisibility(View.VISIBLE);
                    play.setVisibility(View.GONE);
                }
            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        play.setVisibility(View.GONE);
        fab =  findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mViewPager.getCurrentItem()==4){
                    fab.setVisibility(View.GONE);
                    play.setVisibility(View.VISIBLE);
                }
                mViewPager.setCurrentItem((mViewPager.getCurrentItem()+1)%6);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mainhow.this,Home.class));
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainhow, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_mainhow, container, false);
            TextView tex = (TextView) rootView.findViewById(R.id.text);
            GridLayout grid = rootView.findViewById(R.id.gridLayout);
            Button b0,b1,b2,b3,b4,b5,b6,b7,b8;
            b0 = rootView.findViewById(R.id.button);
            b1 = rootView.findViewById(R.id.button1);
            b2 = rootView.findViewById(R.id.button2);
            b3 = rootView.findViewById(R.id.button3);
            b4 = rootView.findViewById(R.id.button4);
            b5 = rootView.findViewById(R.id.button5);
            b6 = rootView.findViewById(R.id.button6);
            b7 = rootView.findViewById(R.id.button7);
            b8 = rootView.findViewById(R.id.button8);
            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    tex.setText(getResources().getString(R.string.ins_1));
                    b0.setVisibility(View.VISIBLE);
                    b0.setText("");
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.INVISIBLE);
                    grid.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    tex.setText(getResources().getString(R.string.ins_2));
                    b0.setVisibility(View.VISIBLE);
                    b0.setText("1");
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.INVISIBLE);
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.VISIBLE);
                    b8.setText("2");
                    grid.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    tex.setText(getResources().getString(R.string.ins_3));
                    b0.setVisibility(View.VISIBLE);
                    b0.setText("1");
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.INVISIBLE);
                    b6.setVisibility(View.VISIBLE);
                    b6.setText("3");
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.VISIBLE);
                    b8.setText("2");
                    grid.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    tex.setText(getResources().getString(R.string.ins_4));
                    b0.setVisibility(View.VISIBLE);
                    b0.setText("1");
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b5.setVisibility(View.VISIBLE);
                    b5.setText("4");
                    b6.setVisibility(View.VISIBLE);
                    b6.setText("3");
                    b7.setVisibility(View.INVISIBLE);
                    b8.setVisibility(View.VISIBLE);
                    b8.setText("2");
                    grid.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    tex.setText(getResources().getString(R.string.ins_5));
                    b0.setVisibility(View.VISIBLE);
                    b0.setText("");
                    b1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.VISIBLE);
                    b4.setVisibility(View.VISIBLE);
                    b5.setVisibility(View.VISIBLE);
                    b5.setText("");
                    b6.setVisibility(View.VISIBLE);
                    b6.setText("");
                    b7.setVisibility(View.VISIBLE);
                    b8.setVisibility(View.VISIBLE);
                    b8.setText("");
                    grid.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    tex.setText(getResources().getString(R.string.ins_6));
                    grid.setVisibility(View.GONE);
                    break;
            }
            return rootView;
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
            return 6;
        }
    }
}

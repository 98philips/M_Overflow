package com.evedevelopers.mof;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;


public class Home extends AppCompatActivity {

    /**
     * The {@link androidx.viewpager.widget.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link androidx.fragment.app.FragmentManager}  derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link androidx.fragment.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static final int RC_SIGN_IN = 9001;
    GoogleSignInAccount signedInAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        SharedPreferences first = getSharedPreferences("first", AppCompatActivity.MODE_PRIVATE);
        if(first.getBoolean("one",true)){
            Intent i = new Intent(Home.this,Mainhow.class);
            startActivity(i);
            finish();
        }else{
            if(!isSignedIn()) {
                signInSilently();
            }
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
  //      setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
      //  ConstraintLayout cc = (ConstraintLayout) findViewById(R.id.constraintLayout);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout =findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        /*mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(mViewPager.getCurrentItem()==0){
                    next.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.GONE);
                }
                else if(mViewPager.getCurrentItem()==1) {
                    next.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.VISIBLE);
                }
                else {
                    next.setVisibility(View.GONE);
                    prev.setVisibility(View.VISIBLE);
                }
            }
        });*/
        final SharedPreferences sound = getSharedPreferences("sound", AppCompatActivity.MODE_PRIVATE);
        final SharedPreferences.Editor edit = sound.edit();
        final ImageView ss =  findViewById(R.id.sound);
        switch (sound.getInt("s",1)){
            case 1:ss.setImageResource(R.drawable.vib);
                break;
            case 0:ss.setImageResource(R.drawable.novib);
                break;
        }
        ImageView fab = findViewById(R.id.rate);
        TextView how = findViewById(R.id.ques);
        ImageView test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, com.evedevelopers.mof.activities.Home.class));
            }
        });
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (sound.getInt("s",1)){
                    case 0:ss.setImageResource(R.drawable.vib);
                            break;
                    case 1:ss.setImageResource(R.drawable.novib);
                            break;
                }
                edit.putInt("s",(sound.getInt("s",1)+1)%2);
                edit.commit();
            }
        });
        how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,Mainhow.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.evedevelopers.mof");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        ImageView hhh = findViewById(R.id.high);
        hhh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,HighScore.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
            }
        });
        /*mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("mail");
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{"98philips@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT,"");
                i.putExtra(Intent.EXTRA_TEXT,"");
                startActivity(Intent.createChooser(i,"Send mail....."));
            }
        });*/

    }

    private void signInSilently() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        //Intent signInIntent = signInClient.getSignInIntent();
        //startActivityForResult(signInIntent, RC_SIGN_IN);

        signInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            // The signed in account is stored in the task's result.
                            signedInAccount = task.getResult();
                            //Toast.makeText(getApplicationContext(),"SignedIn",Toast.LENGTH_SHORT).show();

                        } else {
                            startSignInIntent();
                        }
                    }
                });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                signedInAccount = result.getSignInAccount();
            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);
                }
//                new AlertDialog.Builder(this).setMessage(message)
//                        .setNeutralButton(android.R.string.ok, null).show();
            }
        }
    }
    private void startSignInIntent() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }
    private boolean isSignedIn() {
        signedInAccount = GoogleSignIn.getLastSignedInAccount(this);
        return signedInAccount != null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main5, menu);
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
            View rootView = inflater.inflate(R.layout.home_fragment, container, false);
            final Context t=getContext();
            ConstraintLayout three =  (ConstraintLayout) rootView.findViewById(R.id.three);
            ConstraintLayout four =  (ConstraintLayout) rootView.findViewById(R.id.four);
            ConstraintLayout five =  (ConstraintLayout) rootView.findViewById(R.id.five);
            //ConstraintLayout cc = (ConstraintLayout) rootView.findViewById(R.id.constraintLayout);
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                three.setVisibility(View.VISIBLE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);
                //cc.setBackground(getResources().getDrawable(R.drawable.bg));
            }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                three.setVisibility(View.GONE);
                four.setVisibility(View.VISIBLE);
                five.setVisibility(View.GONE);
                //cc.setBackground(getResources().getDrawable(R.drawable.bgg));
            }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                three.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.VISIBLE);
               // cc.setBackground(getResources().getDrawable(R.drawable.bggg));

            }
            three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(t, GameLevel3.class);
                    startActivity(i);
                   // overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
                }
            });
            four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(t, GameLevel4.class);
                    startActivity(i);
                    //overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
                }
            });
            five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(t, GameLevel5.class);
                    startActivity(i);
                    //overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
                }
            });
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
            return 3;
        }
    }
}

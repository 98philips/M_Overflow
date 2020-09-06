package com.evedevelopers.mof.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.evedevelopers.mof.HowTo;
import com.evedevelopers.mof.R;
import com.evedevelopers.mof.adapters.ViewPagerAdapter;
import com.evedevelopers.mof.models.OverflowMain;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static com.evedevelopers.mof.models.OverflowMain.primary_colors;

public class Home extends AppCompatActivity implements View.OnClickListener {

    Button play;
    EditText level;
    ViewPager viewPager;
    List<Integer> level_list;
    DrawableContainer container;
    RelativeLayout relativeLayout;
    ImageView imageView,settings;
    int w,h,bgc;
    private static final int RC_SIGN_IN = 9001;
    GoogleSignInAccount signedInAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);
        findViewById(R.id.play).setOnClickListener(this);
        imageView = findViewById(R.id.bg);
        relativeLayout = findViewById(R.id.relative_bg);
        viewPager = findViewById(R.id.view_pager);
        settings = findViewById(R.id.settings);
        settings.setOnClickListener(this);
        level = findViewById(R.id.level);
        findViewById(R.id.stats).setOnClickListener(this);
        findViewById(R.id.how_to).setOnClickListener(this);
        level_list = new ArrayList<>();
        level_list.add(3);
        level_list.add(4);
        level_list.add(5);
        level_list.add(6);
        bgc = 3;
        container = new DrawableContainer();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        w = displayMetrics.widthPixels;
        h = displayMetrics.heightPixels;
        viewPager.setAdapter(new ViewPagerAdapter(level_list,Home.this));
        imageView.post(new Runnable() {
            @Override
            public void run() {
                //create your anim here
                animation(0);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        //create your anim here
                        animation(position);
                        bgc = position;
                    }
                });

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //handleGoogleLogin();
    }

    private void handleGoogleLogin() {
        if(!isSignedIn()) {
            signInSilently();
        }else{
            submitScore();
        }
    }

    private void submitScore() {
        if(signedInAccount!=null) {
            SharedPreferences h_score = getSharedPreferences("score", AppCompatActivity.MODE_PRIVATE);
            int high_score = h_score.getInt("score",0);
            Games.getLeaderboardsClient(Home.this, signedInAccount);
            Games.getLeaderboardsClient(getApplicationContext(), signedInAccount)
                    .submitScore(getString(R.string.leaderboard_high_score), high_score);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:
                Intent i = new Intent(this,GamePlay.class);
                i.putExtra("level",Integer.parseInt(level.getText().toString()));
                startActivity(i);
                break;
            case R.id.settings:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
            case R.id.stats:
                startActivity(new Intent(this,Stats.class));
                break;
            case R.id.how_to:
                startActivity(new Intent(this, HowTo.class));
                break;
        }
    }

    void animation(final int pos){
        relativeLayout.setBackgroundColor(getResources().getColor(primary_colors[bgc]));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(primary_colors[pos]));
        getWindow().setNavigationBarColor(getResources().getColor(primary_colors[pos]));
        if (imageView.getVisibility() == View.VISIBLE) {
            imageView.setVisibility(View.GONE);
        }
        Log.d("Here","");
        imageView.setBackgroundColor(getResources().getColor(primary_colors[pos]));
        double finalRadius = Math.hypot(w,h);
        Animator anim = ViewAnimationUtils.createCircularReveal(imageView,w/2,h/2,(float)w/5, (float) finalRadius);
        imageView.setVisibility(View.VISIBLE);
        anim.setDuration(500);
        anim.start();
    }

    private void signInSilently() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            // The signed in account is stored in the task's result.
                            signedInAccount = task.getResult();
                            submitScore();

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
            assert result != null;
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                signedInAccount = result.getSignInAccount();
                submitScore();
            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);
                    Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
                }
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
}

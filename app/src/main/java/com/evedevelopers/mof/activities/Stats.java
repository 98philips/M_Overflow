package com.evedevelopers.mof.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.evedevelopers.mof.R;
import com.evedevelopers.mof.models.OverflowMain;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Stats extends AppCompatActivity implements View.OnClickListener {


    LinearLayout root;
    List<Map<String, Integer>> scoreList;

    private static final int RC_SIGN_IN = 9001;
    private static final int RC_LEADER_BOARD_UI = 9004;
    GoogleSignInAccount signedInAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        root = findViewById(R.id.root_layout);
        findViewById(R.id.leader_board_button).setOnClickListener(this);
        scoreList = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences("lev", AppCompatActivity.MODE_PRIVATE);
        scoreList.add(getScoreMap(3,preferences));
        scoreList.add(getScoreMap(4,preferences));
        scoreList.add(getScoreMap(5,preferences));
        scoreList.add(getScoreMap(6,preferences));
        for (Map<String, Integer> integerMap: scoreList) {
            root.addView(buildStatItem(integerMap.get("score"),integerMap.get("level")));
        }
        handleGoogleLogin();
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
            Games.getLeaderboardsClient(Stats.this, signedInAccount);
            Games.getLeaderboardsClient(getApplicationContext(), signedInAccount)
                    .submitScore(getString(R.string.leaderboard_high_score), high_score);
        }

    }

    Map<String, Integer> getScoreMap(int level,SharedPreferences preferences){
        Map<String,Integer> map;
        map = new HashMap<>();
        map.put("score",preferences.getInt("l".concat(String.valueOf(level)),0));
        map.put("level",level);
        map.put("color", OverflowMain.primary_colors[level-3]);
        return map;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    LinearLayout buildStatItem(int score, int level){
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linear_params.setMargins(0,48,0,0);
        linearLayout.setLayoutParams(linear_params);
        TextView level_text = new TextView(this);
        LinearLayout.LayoutParams text_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        text_params.setMargins(32,16,48,16);
        text_params.gravity = Gravity.CENTER_VERTICAL;
        level_text.setLayoutParams(text_params);
        level_text.setText(String.valueOf(level).concat(" x ").concat(String.valueOf(level)));
        level_text.setTextSize(16);
        level_text.setTypeface(level_text.getTypeface(), Typeface.BOLD);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        LinearLayout.LayoutParams rel_params = new LinearLayout.LayoutParams(
                0,
                80,
                1
        );
        rel_params.gravity = Gravity.CENTER_VERTICAL;
        relativeLayout.setLayoutParams(rel_params);
        ProgressBar progressBar = new ProgressBar(this,null,
                android.R.attr.progressBarStyleHorizontal);
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bg));
        RelativeLayout.LayoutParams progress_params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        progressBar.setLayoutParams(progress_params);
        progressBar.setProgress((int) ((score/(double)(level*level))*100));
        TextView score_text = new TextView(this);
        RelativeLayout.LayoutParams score_text_params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        score_text.setGravity(Gravity.CENTER);
        score_text_params.addRule(RelativeLayout.ALIGN_PARENT_END);
        score_text_params.setMarginEnd(32);
        score_text.setTypeface(level_text.getTypeface(), Typeface.BOLD);
        score_text.setText(String.valueOf(score));
        relativeLayout.addView(progressBar,progress_params);
        relativeLayout.addView(score_text,score_text_params);
        linearLayout.addView(level_text);
        linearLayout.addView(relativeLayout);
        return  linearLayout;
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

    private void showLeaderBoard() {
        Games.getLeaderboardsClient(this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this)))
                .getLeaderboardIntent(getString(R.string.leaderboard_high_score))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADER_BOARD_UI);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.leader_board_button:
                if(isSignedIn()) {
                    submitScore();
                    showLeaderBoard();
                }
                else
                    signInSilently();
                break;
        }
    }
}
package com.evedevelopers.mof.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.evedevelopers.mof.HighScore;
import com.evedevelopers.mof.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GameOver extends AppCompatActivity implements View.OnClickListener {

    TextView level,score,level_best,badge,message,time;
    ImageView home,tryAgain,nextLevel;
    int level_number = 3,high_score = 0;

    private static final int RC_SIGN_IN = 9001;
    GoogleSignInAccount signedInAccount;

    SharedPreferences h_score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_new);
        level = findViewById(R.id.level_number);
        score = findViewById(R.id.score_number);
        level_best = findViewById(R.id.level_best_number);
        badge = findViewById(R.id.new_level_best);
        message = findViewById(R.id.message);
        home = findViewById(R.id.home);
        tryAgain = findViewById(R.id.tryAgain);
        nextLevel = findViewById(R.id.nextLevel);
        time = findViewById(R.id.time_number);
        home.setOnClickListener(this);
        tryAgain.setOnClickListener(this);
        nextLevel.setOnClickListener(this);
        h_score = getSharedPreferences("score", AppCompatActivity.MODE_PRIVATE);
        init_highScore();
        Intent incoming = getIntent();
        SharedPreferences preferences = getSharedPreferences("lev", AppCompatActivity.MODE_PRIVATE);
        level_number = incoming.getIntExtra("level",3);
        int level_best_number = getLevelBest(level_number,preferences);
        int score_number = incoming.getIntExtra("score",0);
        String message_string = incoming.getStringExtra("message");
        message.setText(message_string);
        level.setText(String.valueOf(level_number));
        score.setText(String.valueOf(score_number));
        time.setText(String.valueOf(incoming.getLongExtra("time",0)).concat("s"));
        level_best.setText(String.valueOf(level_best_number));
        configBadge(score_number,level_best_number,preferences);
        decideButtonVisibility(score_number,level_number);
        handleGoogleLogin();
    }

    private void init_highScore() {
        high_score = h_score.getInt("score",0);
        submitScore();
    }

    private void handleGoogleLogin() {
        if(!isSignedIn()) {
            signInSilently();
        }
    }

    private void submitScore() {
        if(signedInAccount!=null) {
            Games.getLeaderboardsClient(GameOver.this, signedInAccount);
            Games.getLeaderboardsClient(getApplicationContext(), signedInAccount)
                    .submitScore(getString(R.string.leaderboard_high_score), high_score);
        }

    }

    private void configBadge(int score_number,int level_best_number,SharedPreferences preferences) {
        badge.setVisibility((score_number > level_best_number)?View.VISIBLE: View.INVISIBLE);
        if(score_number > level_best_number){
            setLevelBest(score_number,level_number,preferences);
        }
        int hsc = high_score;
        if(score_number > hsc){
            badge.setText("New High Score");
            SharedPreferences.Editor editor = h_score.edit();
            editor.putInt("score",score_number);
            high_score = score_number;
            submitScore();
            editor.apply();
        }
    }

    private void decideButtonVisibility(int score_number,int level_number) {
        if(score_number == level_number*level_number) {
            tryAgain.setVisibility(View.GONE);
            if(level_number == 6){
                nextLevel.setVisibility(View.GONE);
            }
        }
        else{
            nextLevel.setVisibility(View.GONE);
        }

    }

    private int getLevelBest(int level_number, SharedPreferences preferences) {
        return preferences.getInt("l".concat(String.valueOf(level_number)),0);
    }

    private void setLevelBest(int score,int level_number, SharedPreferences preferences){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("l".concat(String.valueOf(level_number)),score);
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home:
                finish();
                break;
            case R.id.tryAgain:
                Intent i = new Intent(this,GamePlay.class);
                i.putExtra("level",level_number);
                i.putExtra("color",level_number-3);
                startActivity(i);
                finish();
                break;
            case R.id.nextLevel:
                Intent intent = new Intent(this,GamePlay.class);
                intent.putExtra("level",level_number+1);
                intent.putExtra("color",level_number-2);
                startActivity(intent);
                finish();
                break;
        }
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
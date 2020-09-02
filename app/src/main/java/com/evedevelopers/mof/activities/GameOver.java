package com.evedevelopers.mof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.evedevelopers.mof.R;

public class GameOver extends AppCompatActivity implements View.OnClickListener {

    TextView level,score,level_best,badge,message;
    ImageView home,tryAgain,nextLevel;
    int level_number = 3;

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
        home.setOnClickListener(this);
        tryAgain.setOnClickListener(this);
        nextLevel.setOnClickListener(this);
        Intent incoming = getIntent();
        SharedPreferences preferences = getSharedPreferences("lev", AppCompatActivity.MODE_PRIVATE);
        level_number = incoming.getIntExtra("level",3);
        int level_best_number = getLevelBest(level_number,preferences);
        int score_number = incoming.getIntExtra("score",0);
        String message_string = incoming.getStringExtra("message");
        message.setText(message_string);
        level.setText(String.valueOf(level_number));
        score.setText(String.valueOf(score_number));
        level_best.setText(String.valueOf(level_best_number));
        configBadge(score_number,level_best_number,preferences);
        decideButtonVisibility(score_number,level_number);
    }

    private void configBadge(int score_number,int level_best_number,SharedPreferences preferences) {
        badge.setVisibility((score_number > level_best_number)?View.VISIBLE: View.INVISIBLE);
        if(score_number > level_best_number){
            setLevelBest(score_number,level_number,preferences);
        }
        SharedPreferences h_score = getSharedPreferences("score", AppCompatActivity.MODE_PRIVATE);
        int hsc = h_score.getInt("score",0);
        if(score_number > hsc){
            badge.setText("New High Score");
            SharedPreferences.Editor editor = h_score.edit();
            editor.putInt("score",score_number);
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
}
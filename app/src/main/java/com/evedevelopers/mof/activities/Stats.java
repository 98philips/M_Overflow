package com.evedevelopers.mof.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evedevelopers.mof.R;
import com.evedevelopers.mof.models.OverflowMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats extends AppCompatActivity {


    LinearLayout root;
    List<Map<String, Integer>> scoreList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        root = findViewById(R.id.root_layout);
        scoreList = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences("lev", AppCompatActivity.MODE_PRIVATE);
        scoreList.add(getScoreMap(3,preferences));
        scoreList.add(getScoreMap(4,preferences));
        scoreList.add(getScoreMap(5,preferences));
        scoreList.add(getScoreMap(6,preferences));
        for (Map<String, Integer> integerMap: scoreList) {
            root.addView(buildStatItem(integerMap.get("score"),integerMap.get("level")));
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
}
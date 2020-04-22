package com.example.philip.game1;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.evedevelopers.mof.R;




public class GameOver extends AppCompatActivity {
    TextView ss,game,hs,highscore;
    ImageView tryagain,home;
    int ll,s;
    SharedPreferences lv;
    Bundle ex;
    /*private InterstitialAd mInterstitialAd;
    public void ad(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }*/

    public void levelbest(){
        lv = getSharedPreferences("lev", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor ed = lv.edit();
        switch (ll){
            case 3:
                if(lv.getInt("l3",0)<s){
                    ed.putInt("l3",s);
                    ed.commit();
                    hs.setVisibility(View.VISIBLE);
                    hs.setText("New level best");
                }
                break;
            case 4:
                if(lv.getInt("l4",0)<s){
                    ed.putInt("l4",s);
                    ed.commit();
                    hs.setVisibility(View.VISIBLE);
                    hs.setText("New level best");
                }
                break;
            case 5:
                if(lv.getInt("l5",0)<s){
                    ed.putInt("l5",s);
                    ed.commit();
                    hs.setVisibility(View.VISIBLE);
                    hs.setText("New level best");
                }
                break;
        }



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game_over);
        ex = getIntent().getExtras();
        s = ex.getInt("score");
        ll = ex.getInt("level");
        ss  = findViewById(R.id.textView1);
        game = findViewById(R.id.textView);
        hs = findViewById(R.id.hs);
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Bold.ttf");
        game.setTypeface(custom_font1);
        ss.setTypeface(custom_font1);
        hs.setTypeface(custom_font1);
        home = findViewById(R.id.home);
        tryagain = findViewById(R.id.tryAgain);
       /* mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8540538329551129/7405828211");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());*/
        SharedPreferences level = getSharedPreferences("level", AppCompatActivity.MODE_PRIVATE);
        int lev = level.getInt("level",25);
        //Toast.makeText(Main2Activity.this,String.valueOf(lev),Toast.LENGTH_SHORT).show();
        SharedPreferences res = getSharedPreferences("resume", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor edit = res.edit();
        edit.putInt("res",1);
        edit.commit();
        if(s==lev){
            game.setText("You Won");
            tryagain.setVisibility(View.GONE);
        }
        ss.setText(String.valueOf(s));
        SharedPreferences hom = getSharedPreferences("home", AppCompatActivity.MODE_PRIVATE);
        final SharedPreferences.Editor hh = hom.edit();
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ad();
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ad();
                hh.putInt("home",1);
                hh.commit();
                finish();
            }
        });
        SharedPreferences hscore = getSharedPreferences("score", AppCompatActivity.MODE_PRIVATE);
        int hsc = hscore.getInt("score",0);
        hs.setVisibility(View.GONE);
        levelbest();
        if(s>hsc){
            hs.setVisibility(View.VISIBLE);
            hs.setText("New High Score");
            SharedPreferences.Editor hsedit = hscore.edit();
            hsedit.putInt("score",s);
            hsedit.commit();
        }


    }

    @Override
    public void onBackPressed() {
        //ad();
        finish();

    }
}

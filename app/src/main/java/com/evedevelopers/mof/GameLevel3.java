package com.evedevelopers.mof;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;



public class GameLevel3 extends AppCompatActivity {
    Button b0,b1,b2,b3,b4,b5,b6,b7,b8;
    int n,i,c,sel,temp,ar[],d;
    long millis,millip;

    CountDownTimer tt,te;
    TextView score,time;
    Vibrator vibe;
    Vector ars ;
    ConstraintLayout base;
    public void gameover(){
        Intent i = new Intent(GameLevel3.this, GameOver.class);
        i.putExtra("level",3);
        i.putExtra("score",c);
        overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
        startActivity(i);
    }
    public  void check(){
        //MediaPlayer mp = MediaPlayer.create(Main3Activity.this, R.raw.touch);
        SharedPreferences sound = getSharedPreferences("sound", AppCompatActivity.MODE_PRIVATE);
        if(sound.getInt("s",1) == 1) {
            vibe.vibrate(15);
        }
       // mp.start();
        if(sel!=ar[i]) {
            gameover();

        }else if(i==n){
            c++;
            //String nums = String.format("%d",c);
            score.setText("SCORE: "+String.valueOf(c));
           // Toast.makeText(Main3Activity.this,String.valueOf(n),Toast.LENGTH_SHORT).show();
            if(n==8){
                gameover();
            }
            new_button();
            i=0;


        }
        else {
            i++;
        }
    }
    public void new_button(){
        n++;
        int cc;
        Random rand = new Random();
        Random color = new Random();
        int co =  color.nextInt(3);
        switch (co){
            case 0:cc = R.drawable.color4;
                        if(Build.VERSION.SDK_INT>=21) {
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            getWindow().setStatusBarColor(getResources().getColor(R.color.yellow));
                            getWindow().setNavigationBarColor(getResources().getColor(R.color.yellow));
                            base.setBackground(getResources().getDrawable(R.color.yellow));
                        }break;
            case 1:cc = R.drawable.color1;
                if(Build.VERSION.SDK_INT>=21) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    getWindow().setStatusBarColor(getResources().getColor(R.color.red));
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.red));
                    base.setBackground(getResources().getDrawable(R.color.red));
                }break;
            case 2:cc = R.drawable.color2;
                if(Build.VERSION.SDK_INT>=21) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    getWindow().setStatusBarColor(getResources().getColor(R.color.green));
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.green));
                    base.setBackground(getResources().getDrawable(R.color.green));
                }break;
            //case 3:cc = R.drawable.color3;break;
            // case 4:cc = R.drawable.color5;break;
            default:cc = R.drawable.bgroup;break;
        }
        temp=0;
        if(ars.size()!=0) {
            temp = (int) ars.get(rand.nextInt(ars.size()));
            ar[n]=temp;
        }
        boolean k = ars.removeElement(temp);


        switch (temp){
            case 1: b1.setVisibility(View.VISIBLE);b1.setBackgroundResource(cc);break;
            case 2: b2.setVisibility(View.VISIBLE);b2.setBackgroundResource(cc);break;
            case 3: b3.setVisibility(View.VISIBLE);b3.setBackgroundResource(cc);break;
            case 4: b4.setVisibility(View.VISIBLE);b4.setBackgroundResource(cc);break;
            case 5: b5.setVisibility(View.VISIBLE);b5.setBackgroundResource(cc);break;
            case 6: b6.setVisibility(View.VISIBLE);b6.setBackgroundResource(cc);break;
            case 7: b7.setVisibility(View.VISIBLE);b7.setBackgroundResource(cc);break;
            case 8: b8.setVisibility(View.VISIBLE);b8.setBackgroundResource(cc);break;


        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_level_3);
        base = findViewById(R.id.base);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);
        i=0;n=0;c=0;millip=60000;millis=60000;
        SharedPreferences level = getSharedPreferences("level", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editl = level.edit();
        editl.putInt("level",9);
        editl.apply();
        final SharedPreferences res = getSharedPreferences("resume", AppCompatActivity.MODE_PRIVATE);
        final SharedPreferences.Editor edit = res.edit();
        ar = new int[9];
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ars =new Vector(9,1);
        for(int j=0;j<8;j++){
            ars.addElement(j+1);
        }
        Arrays.fill(ar,0);
        score = findViewById(R.id.score);
        b0 = findViewById(R.id.button);
        b1 = findViewById(R.id.button1);
        b1.setVisibility(View.INVISIBLE);
        b2 = findViewById(R.id.button2);
        b2.setVisibility(View.INVISIBLE);
        b3 = findViewById(R.id.button3);
        b3.setVisibility(View.INVISIBLE);
        b4 = findViewById(R.id.button4);
        b4.setVisibility(View.INVISIBLE);
        b5 = findViewById(R.id.button5);
        b5.setVisibility(View.INVISIBLE);
        b6 = findViewById(R.id.button6);
        b6.setVisibility(View.INVISIBLE);
        b7 = findViewById(R.id.button7);
        b7.setVisibility(View.INVISIBLE);
        b8 = findViewById(R.id.button8);
        b8.setVisibility(View.INVISIBLE);

        time  = findViewById(R.id.time);
        d=1;
        tt = new CountDownTimer(millip,1000){
            public void onTick(long milli){
                millis-=1000;
                String sec;
                if((milli/1000)%60<10){
                    sec = "0"+String.valueOf((milli/1000)%60);
                }else {
                    sec = String.valueOf((milli/1000)%60);
                }
                time.setText("0"+String.valueOf(milli/60000)+":"+sec);
            }
            public void onFinish() {
                gameover();
            }
        };
        te = new CountDownTimer(millip,1000){
            public void onTick(long milli){
                millis-=1000;
                String sec;
                if((milli/1000)%60<10){
                    sec = "0"+String.valueOf((milli/1000)%60);
                }else {
                    sec = String.valueOf((milli/1000)%60);
                }
                time.setText("0"+String.valueOf(milli/60000)+":"+sec);
            }
            public void onFinish() {
                gameover();
            }
        };
        //Toast.makeText(MainActivity.this, n + "," + nex, Toast.LENGTH_SHORT).show();
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel = 0;
                if(n==0){
                    tt.start();
                    edit.putInt("res",0);
                    edit.commit();
                }
                check();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel = 1;
                check();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel = 2;
                check();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel = 3;
                check();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel = 3;
                check();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel = 4;
                check();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel = 5;
                check();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel = 6;
                check();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel = 7;
                check();
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sel = 8;
                check();
            }
        });


    }
    @Override
    public void onPause(){
        super.onPause();
        tt.cancel();
        te.cancel();
        // k=0;
        millip=millis;
        //Toast.makeText(MainActivity.this,String.valueOf(millis),Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences home = getSharedPreferences("home", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor hh = home.edit();
        if(home.getInt("home",0)==1){
            hh.putInt("home",0);
            hh.commit();
            finish();
        }
        if(Build.VERSION.SDK_INT>=21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
            base.setBackground(getResources().getDrawable(R.color.black));
        }
        SharedPreferences res = getSharedPreferences("resume", AppCompatActivity.MODE_PRIVATE);
        int re = res.getInt("res",0);
        if(re == 1) {
            b1.setVisibility(View.INVISIBLE);
            b2.setVisibility(View.INVISIBLE);
            b3.setVisibility(View.INVISIBLE);
            b4.setVisibility(View.INVISIBLE);
            b5.setVisibility(View.INVISIBLE);
            b6.setVisibility(View.INVISIBLE);
            b7.setVisibility(View.INVISIBLE);
            b8.setVisibility(View.INVISIBLE);


            i = 0;
            n = 0;
            c = 0;
            ar = new int[16];
            score.setText("SCORE: " + String.valueOf(c));
            Arrays.fill(ar, 0);
            vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            ars = new Vector(9, 1);
            for (int j = 0; j < 8; j++) {
                ars.addElement(j + 1);
                //for(int j=0;j<24;j++){
                //    ars.addElement(j+1);
            }
            tt.cancel();
            time.setText("01:00");
            d=0;
            millip=60000;
            millis=60000;
           /* new CountDownTimer(120000,1000){
                public void onTick(long mill){
                    time.setText(String.valueOf(mill/60000)+":"+String.valueOf((mill/1000)));
                }
                public void onFinish() {
                    Intent over = new Intent(MainActivity.this, Main2Activity.class);
                    over.putExtra("score",c);
                    startActivity(over);
                }
            }.start();*/
        }else {
            if(n!=0){
                te = new CountDownTimer(millip,1000){
                    public void onTick(long milli){
                        millis-=1000;
                        String sec;
                        if((milli/1000)%60<10){
                            sec = "0"+String.valueOf((milli/1000)%60);
                        }else {
                            sec = String.valueOf((milli/1000)%60);
                        }
                        time.setText("0"+String.valueOf(milli/60000)+":"+sec);
                    }
                    public void onFinish() {
                        gameover();
                    }
                }.start();
            }
        }

    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(GameLevel3.this);
        builder.setTitle("Quit Game!");
        builder.setMessage("All your current progress will be lost. Are you sure!!!");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}


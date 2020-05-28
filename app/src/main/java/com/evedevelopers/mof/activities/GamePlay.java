package com.evedevelopers.mof.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evedevelopers.mof.R;
import com.evedevelopers.mof.models.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.evedevelopers.mof.models.OverflowMain.buttons;
import static com.evedevelopers.mof.models.OverflowMain.primary_colors;

public class GamePlay extends AppCompatActivity {

    GridLayout grid;
    RelativeLayout relative_bg,relativeLayout;
    int level,color;
    int seq_no,w,h,size;
    int max,bgc;
    int padding;
    ImageView imageView;
    RelativeLayout bg_card;
    List<Cell> cellList, availableCellList;
    TextView score,time;
    long millis,millip;
    boolean game;
    CountDownTimer te;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play_new);
        grid = findViewById(R.id.grid);
        relativeLayout = findViewById(R.id.relativeLayout);
        relative_bg = findViewById(R.id.relative_bg);
        bg_card = findViewById(R.id.bg_re);
        score = findViewById(R.id.score);
        time = findViewById(R.id.time);
        imageView = findViewById(R.id.bg);
        grid.setPadding(8,8,8,8);
        cellList = new ArrayList<>();
        availableCellList = new ArrayList<>();
        seq_no = 0;
        game = false;
        max = -1;
        bgc = R.color.colorPrimaryDark;
        padding = (7-level)*2;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        w = displayMetrics.widthPixels;
        h = displayMetrics.heightPixels;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.dark));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.dark));
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.dark));
        level = getIntent().getIntExtra("level",3);
        size = getSize(level);
        color = primary_colors[getIntent().getIntExtra("color",0)];
        setUpGrid(level);
    }

    private int getSize(int level){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (displayMetrics.widthPixels-((1/level)*1000+padding*2*(level+1)+100))/level;

    }

    private Button getButton(){
        int r = ThreadLocalRandom.current().nextInt(0, 4);
        int color_d = buttons[r];
        int color = primary_colors[r];
        Button button = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                size,
                size
        );

        params.setMargins(padding,padding,padding,padding);
        button.setLayoutParams(params);
        button.setBackground(getResources().getDrawable(color_d));
        button.setVisibility(View.INVISIBLE);
        final Cell c = new Cell(button,color);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!game){
                    game = true;
                    setup_timer();
                }

                if(seq_no == c.getSeq_no() && max == seq_no){
                    pickRandom();
                }else if(seq_no == c.getSeq_no()){
                    seq_no++;
                }else{
                    new AlertDialog.Builder(GamePlay.this).setMessage("Game Over").show();
                }
            }
        });
        cellList.add(c);
        availableCellList.add(c);
        return button;
    }

    private void pickRandom(){
        max++;
        if(max != level*level) {
            seq_no = 0;
            score.setText(String.valueOf(max));
            int random = ThreadLocalRandom.current().nextInt(0, availableCellList.size());
            final Cell cell = availableCellList.get(random);
            cell.getButton().setVisibility(View.VISIBLE);
            cell.setSeq_no(max);
            availableCellList.remove(random);
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    //create your anim here
                    int c = cell.getColor();
                    animation(c);

                }
            });
        }else{
            new AlertDialog.Builder(this).setMessage("You Won").show();
        }



    }

    private void setUpGrid(int level) {
        grid.setColumnCount(level);
        for(int i = 0;i < level*level; i++ ){
            Button b = getButton();
            grid.addView(b);
        }
        imageView.setLayoutParams(new FrameLayout.LayoutParams(
                size*level+(level*32)+20,
                size*level+(level*32)+20
        ));
        pickRandom();
//        imageView.post(new Runnable() {
//            @Override
//            public void run() {
//                //create your anim here
//                animation(color);
//            }
//        });
    }

    void animation(int color){

        if (imageView.getVisibility() == View.VISIBLE) {
            imageView.setVisibility(View.GONE);
        }
        Log.d("Here","");
        imageView.setBackgroundColor(getResources().getColor(color));
        double finalRadius = Math.hypot(w,h);
        Animator anim = ViewAnimationUtils.createCircularReveal(imageView,w/2,0,0, (float) finalRadius);
        imageView.setVisibility(View.VISIBLE);
        anim.setDuration(500);
        anim.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(game){
            setup_timer();
        }else{
            millis = (level-2)*30000;
            millip = millis;
            time.setText(String.valueOf(millis/1000));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(te!=null){
            te.cancel();
            millip = millis;
        }
    }

    void setup_timer(){
        te = new CountDownTimer(millip,1000){
            public void onTick(long milli){
                millis-=1000;
                String sec;
                sec = String.valueOf((milli/1000));
                time.setText(sec);
            }
            public void onFinish() {}
        };
        te.start();
    }
}

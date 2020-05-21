package com.evedevelopers.mof.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.content.Intent;
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

import com.evedevelopers.mof.R;
import com.evedevelopers.mof.adapters.ViewPagerAdapter;
import com.evedevelopers.mof.models.OverflowMain;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements View.OnClickListener {

    Button play;
    EditText level;
    ViewPager viewPager;
    List<Integer> level_list;
    DrawableContainer container;
    RelativeLayout relativeLayout;
    ImageView imageView;
    int w,h,bgc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);
        findViewById(R.id.play).setOnClickListener(this);
        imageView = findViewById(R.id.bg);
        relativeLayout = findViewById(R.id.relative_bg);
        viewPager = findViewById(R.id.view_pager);
        level = findViewById(R.id.level);
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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:
                Intent i = new Intent(this,GamePlay.class);
                i.putExtra("level",Integer.parseInt(level.getText().toString()));
                startActivity(i);
                break;
        }
    }

    void animation(final int pos){
        relativeLayout.setBackgroundColor(getResources().getColor(OverflowMain.colors[bgc]));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(OverflowMain.colors[pos]));
        getWindow().setNavigationBarColor(getResources().getColor(OverflowMain.colors[pos]));
        if (imageView.getVisibility() == View.VISIBLE) {
            imageView.setVisibility(View.GONE);
        }
        Log.d("Here","");
        imageView.setBackgroundColor(getResources().getColor(OverflowMain.colors[pos]));
        double finalRadius = Math.hypot(w,h);
        Animator anim = ViewAnimationUtils.createCircularReveal(imageView,w/2,h/2,(float)w/5, (float) finalRadius);
        imageView.setVisibility(View.VISIBLE);
        anim.setDuration(500);
        anim.start();
    }
}

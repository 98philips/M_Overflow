package com.evedevelopers.mof;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HowTo extends AppCompatActivity {
    Button b0,b1,b2,b3,b4,b5,b6,b7,b8;
    TextView ins;
    int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        n=0;
        ins = findViewById(R.id.ins);
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
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(),"fonts/NewsCycle-Regular.ttf");
        ins.setTypeface(custom_font1);
        final GridLayout grid = findViewById(R.id.gridLayout);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n++;
                String mess="";
                switch (n) {
                    case 1:
                        mess = "Clicking the buttons in the anti-Chronological order completes the cycle,on completing a cycle a new button is created and a new cycle is started";
                           b1.setVisibility(View.VISIBLE);
                           b1.setBackgroundResource(R.drawable.color1);
                           break;
                    case 2:
                        mess = "You Lose the game when you click the wrong button";
                           b2.setVisibility(View.VISIBLE);
                           b2.setBackgroundResource(R.drawable.color2);
                           break;
                    case 3:
                       mess = "Score is based on cycles completed";
                           b3.setVisibility(View.VISIBLE);
                           b3.setBackgroundResource(R.drawable.color4);
                           break;
                    case 4:
                        mess = "You Win a game when you complete n x n cycles before the timer ends!!!\n" +
                                "Good Luck,You are ready to play";
                            grid.setVisibility(View.GONE);
                            break;
                    case 5:
                            finish();
                            break;

                   }
                   ins.setText(mess);
            }
        });
    }

}

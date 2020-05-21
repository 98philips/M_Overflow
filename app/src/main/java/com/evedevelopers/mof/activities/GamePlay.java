package com.evedevelopers.mof.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.evedevelopers.mof.R;
import com.evedevelopers.mof.models.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GamePlay extends AppCompatActivity {

    GridLayout grid;
    int level;
    int seq_no;
    int max;
    List<Cell> cellList, availableCellList;
    int[] colors = {
            R.drawable.color1,
            R.drawable.color2,
            R.drawable.color3,
            R.drawable.color4,
            R.drawable.color5,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play_new);
        grid = findViewById(R.id.grid);
        grid.setPadding(8,8,8,8);
        cellList = new ArrayList<>();
        availableCellList = new ArrayList<>();
        seq_no = 0;
        max = -1;
        level = getIntent().getIntExtra("level",3);
        setUpGrid(level);
    }

    private int getSize(int level){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (displayMetrics.widthPixels-((1/level)*1000+16*(level+1)+100))/level;
    }

    private Button getButton(){
        int color = colors[ThreadLocalRandom.current().nextInt(0, 5)];
        Button button = new Button(this);
        int size = getSize(level);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                size,
                size
        );
        params.setMargins(8,8,8,8);
        button.setLayoutParams(params);
        button.setBackground(getResources().getDrawable(color));
        button.setVisibility(View.INVISIBLE);
        final Cell c = new Cell(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            int random = ThreadLocalRandom.current().nextInt(0, availableCellList.size());
            Cell cell = availableCellList.get(random);
            cell.getButton().setVisibility(View.VISIBLE);
            cell.setSeq_no(max);
            availableCellList.remove(random);
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
        pickRandom();
    }
}

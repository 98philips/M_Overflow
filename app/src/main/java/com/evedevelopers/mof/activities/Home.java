package com.evedevelopers.mof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.evedevelopers.mof.R;

public class Home extends AppCompatActivity implements View.OnClickListener {

    Button play;
    EditText level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);
        findViewById(R.id.play).setOnClickListener(this);
        level = findViewById(R.id.level);
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
}

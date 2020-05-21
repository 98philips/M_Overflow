package com.evedevelopers.mof.models;

import android.widget.Button;

public class Cell {

    int seq_no,color;
    Button button;

    public Cell(Button button,int color) {
        this.seq_no = -1;
        this.color = color;
        this.button = button;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(int seq_no) {
        this.seq_no = seq_no;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}

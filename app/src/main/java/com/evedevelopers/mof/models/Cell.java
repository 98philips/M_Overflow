package com.evedevelopers.mof.models;

import android.widget.Button;

public class Cell {

    int seq_no;
    Button button;

    public Cell(Button button) {
        this.seq_no = -1;
        this.button = button;
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

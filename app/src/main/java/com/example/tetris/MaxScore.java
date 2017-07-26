package com.example.tetris;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2017/7/25.
 */

public class MaxScore extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maxscore);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        TextView record = (TextView) findViewById(R.id.record);
        String easy = FileControl.getInstance().onlyReadFile("easy",MaxScore.this);
        String hard = FileControl.getInstance().onlyReadFile("hard",MaxScore.this);
        record.setText(easy + hard);
    }

}

package com.example.tetris;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class MaxScore extends BaseActivity implements View.OnClickListener{

    //显示最高纪录，通过点击纪录选择是否重置纪录
    private TextView recordEasy;
    private TextView recordHard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maxscore);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        recordEasy = (TextView) findViewById(R.id.record_easy);
        recordHard = (TextView) findViewById(R.id.record_hard);
        String easy = FileControl.getInstance().onlyReadFile("easy",MaxScore.this);
        String hard = FileControl.getInstance().onlyReadFile("hard",MaxScore.this);
        recordEasy.setText("简单 "+easy);
        recordHard.setText("困难 "+hard);
        recordEasy.setOnClickListener(this);
        recordHard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.record_easy:
                AlertDialog.Builder dialog_easy = new AlertDialog.Builder(MaxScore.this);
                dialog_easy.setMessage("是否确定要重置纪录？");
                dialog_easy.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog_easy.setPositiveButton("重置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileControl.getInstance().resetFile("easy",MaxScore.this);
                        String easy = FileControl.getInstance().onlyReadFile("easy",MaxScore.this);
                        recordEasy.setText("简单 "+easy);
                    }
                });
                dialog_easy.show();
                break;
            case R.id.record_hard:
                AlertDialog.Builder dialog_hard = new AlertDialog.Builder(MaxScore.this);
                dialog_hard.setMessage("是否确定要重置纪录？");
                dialog_hard.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog_hard.setPositiveButton("重置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileControl.getInstance().resetFile("hard",MaxScore.this);
                        String hard = FileControl.getInstance().onlyReadFile("hard",MaxScore.this);
                        recordHard.setText("困难 "+hard);
                    }
                });
                dialog_hard.show();
                break;
            default:
                break;
        }
    }
}

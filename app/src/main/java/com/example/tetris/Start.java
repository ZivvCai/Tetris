package com.example.tetris;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Start extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        Button start = (Button) findViewById(R.id.start);
        Button difficulty = (Button) findViewById(R.id.btn_exit);
        Button max_score = (Button) findViewById(R.id.max_score);
        Button help = (Button) findViewById(R.id.help);
        start.setOnClickListener(this);
        difficulty.setOnClickListener(this);
        max_score.setOnClickListener(this);
        help.setOnClickListener(this);

        File file = new File("/data/data/com.example.tetris/files/record");
        if(!file.exists()){
            FileOutputStream out = null;
            BufferedWriter writer = null;
            try {
                out = openFileOutput("record", Context.MODE_PRIVATE);
                writer = new BufferedWriter(new OutputStreamWriter(out));
                writer.write("最高分："+0);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                Intent startIntent = new Intent(Start.this, DifficultyChoose.class);
                startActivity(startIntent);
                break;
            case R.id.btn_exit:
                AlertDialog.Builder dialog = new AlertDialog.Builder(Start.this);
                dialog.setTitle("确定要退出吗？");
                dialog.setIcon(R.drawable.ic_launcher);
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();
                    }
                });
                dialog.show();
                break;
            case R.id.max_score:
                Intent recordIntent = new Intent(Start.this,MaxScore.class);
                startActivity(recordIntent);
                break;
            case R.id.help:
                Intent helpIntent = new Intent(Start.this, Help.class);
                startActivity(helpIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Start.this);
        dialog.setTitle("确定要退出吗？");
        dialog.setIcon(R.drawable.ic_launcher);
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();
            }
        });
        dialog.show();
    }
}

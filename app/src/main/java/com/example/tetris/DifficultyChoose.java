package com.example.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tetris.view.TetrisView;

/**
 * Created by 博凯 on 2017.7.26.
 */

public class DifficultyChoose extends BaseActivity implements View.OnClickListener {

    private Button btn_easymode, btn_hardmode;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        btn_easymode = (Button) findViewById(R.id.btn_easy_mode);
        btn_hardmode = (Button) findViewById(R.id.btn_hard_mode);
        btn_easymode.setOnClickListener(this);
        btn_hardmode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_easy_mode:
                TetrisView.difficultyType = 1;
                intent = new Intent(DifficultyChoose.this,Main.class);
                break;
            case R.id.btn_hard_mode:
                TetrisView.difficultyType = 2;
                intent = new Intent(DifficultyChoose.this,Main.class);
                break;
            default:
        }
        startActivity(intent);
    }
}

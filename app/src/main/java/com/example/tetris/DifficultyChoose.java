package com.example.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.tetris.view.TetrisView;

/**
 * Created by 博凯 on 2017.7.26.
 */

public class DifficultyChoose extends AppCompatActivity implements View.OnClickListener{

    private Button btn_easymode, btn_hardmode;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);
        btn_easymode = (Button) findViewById(R.id.btn_easy_mode);
        btn_hardmode = (Button) findViewById(R.id.btn_hard_mode);
        btn_easymode.setOnClickListener(this);
        btn_hardmode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_easy_mode:
                intent = new Intent(DifficultyChoose.this,Main.class);
                TetrisView.difficultyType = 1;
                break;
            case R.id.btn_hard_mode:
                intent = new Intent(DifficultyChoose.this,Main.class);
                TetrisView.difficultyType = 2;
                break;
            default:
        }
        startActivity(intent);
    }
}

package com.example.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/7/22.
 */

public class Help extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        Button help_back = (Button)findViewById(R.id.help_back);
        TextView help_text = (TextView)findViewById(R.id.help_text);
        help_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this,Start.class);
                startActivity(intent);
            }
        });
        String text = "俄罗斯方块是一个休闲游戏：" +
                "\n"+"左右移动、旋转以及加速下落";
        help_text.setText(text);
    }
}

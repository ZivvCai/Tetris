package com.example.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.tetris.service.BGMService;

import java.util.Set;

/**
 * Created by Administrator on 2017/7/28.
 */

public class Setting extends BaseActivity {
    private CheckBox bgm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        bgm = (CheckBox)findViewById(R.id.bgm_checkbox);
        if(Start.BGM_flag){
            bgm.setChecked(true);
        }else{
            bgm.setChecked(false);
        }
        bgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Start.BGM_flag){
                    bgm.setChecked(false);
                    stopService(new Intent(Setting.this,BGMService.class));
                    Start.BGM_flag = false;
                }else{
                    bgm.setChecked(true);
                    startService(new Intent(Setting.this,BGMService.class));
                    Start.BGM_flag = true;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,Start.class);
        startActivity(intent);
    }
}

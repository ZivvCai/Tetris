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
    private CheckBox night_mode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        bgm = (CheckBox)findViewById(R.id.bgm_checkbox);
        night_mode = (CheckBox)findViewById(R.id.mode_checkbox);
        //如果开启背景音乐，则将checkbox选中
        if(Start.BGM_flag){
            bgm.setChecked(true);
        }else{
            bgm.setChecked(false);
        }
        //如果开启夜间模式，则将checkbox选中
        if(Start.NIGHT_flag){
            night_mode.setChecked(true);
        }else{
            night_mode.setChecked(false);
        }
        //背景音乐checkbox点击事件，如未选中，点击后改为已选中，并开启音乐，反之改为未选中并关闭音乐
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
        //夜间模式checkbox点击事件，如未选中，点击后改为已选中，并开启夜间模式，反之改为未选中并关闭夜间模式
        night_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Start.NIGHT_flag){
                    night_mode.setChecked(false);
                    Start.NIGHT_flag = false;
                }else{
                    night_mode.setChecked(true);
                    Start.NIGHT_flag = true;
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

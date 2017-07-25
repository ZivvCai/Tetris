package com.example.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Help extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
        Button help_back = (Button)findViewById(R.id.help_back);
        help_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this,Start.class);
                startActivity(intent);
            }
        });
    }
}

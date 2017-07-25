package com.example.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tetris.model.BlockUnit;
import com.example.tetris.model.TetrisBlock;
import com.example.tetris.view.ShowNextBlockView;
import com.example.tetris.view.TetrisView;

/**
 * Created by Administrator on 2017/7/20.
 */

public class Main extends BaseActivity {
    public Button left, right, rotate, speedUp, pause;   //按钮

    public TextView score, maxScore, level, speed;       //标签

    public int scoreValue, maxScoreValue, levelValue, speedValue;     //标签值

    public String scoreString = "分数：", maxScoreString = "最高分：", levelString = "等级：", speedString = "速度：";

    public TetrisView view;

    public ShowNextBlockView nextBlockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        // 获取各组件和标签值
        view = (TetrisView) findViewById(R.id.tetrisView);
        left = (Button) findViewById(R.id.left);                    //左移
        right = (Button) findViewById(R.id.right);                //右移
        rotate = (Button) findViewById(R.id.rotate);                //旋转
        speedUp = (Button) findViewById(R.id.speedUp);            //加速
        pause = (Button) findViewById(R.id.pause);
        nextBlockView = (ShowNextBlockView) findViewById(R.id.nextBlockView);
        nextBlockView.invalidate();
        score = (TextView) findViewById(R.id.score);
        maxScore = (TextView) findViewById(R.id.maxScore);
        level = (TextView) findViewById(R.id.level);
        speed = (TextView) findViewById(R.id.speed);
        scoreValue = maxScoreValue = 0;
        levelValue = speedValue = 1;
        score.setText(scoreString + scoreValue);
        level.setText(levelString + levelValue);
        speed.setText(speedString + speedValue);
        maxScore.setText(maxScoreString + maxScoreValue);
        view.init();

        //设置各按钮的监听器
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.canMove)
                    view.getFallingBlock().move(-1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();
                    }
                });
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.canMove)
                    view.getFallingBlock().move(1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();
                    }
                });
            }
        });
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!view.canMove)
                    return;
                try {
                    TetrisBlock copyOfFallingBlock = view.getFallingBlock().clone();
                    copyOfFallingBlock.rotate();
                    if (copyOfFallingBlock.canRotate()) {
                        TetrisBlock fallingBlock = view.getFallingBlock();
                        fallingBlock.rotate();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();
                    }
                });
            }
        });

        speedUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.canMove) {
                    view.getFallingBlock().setY(view.getFallingBlock().getY() + BlockUnit.UNITSIZE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.invalidate();
                        }
                    });
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    view.dropThread.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        view.setFather(this);
        view.invalidate();

    }
}

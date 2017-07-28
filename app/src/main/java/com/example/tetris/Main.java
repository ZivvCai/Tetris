package com.example.tetris;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tetris.model.BlockUnit;
import com.example.tetris.model.TetrisBlock;
import com.example.tetris.view.ShowNextBlockView;
import com.example.tetris.view.TetrisView;

/**
 * Created by Administrator on 2017/7/20.
 */

public class Main extends BaseActivity {

    public static boolean isPause = false;//判断是否暂停标识

    public Button left, right, rotate, speedUp, pause;   //按钮

    public TextView score, maxScore, level, speed;       //标签

    public int scoreValue, maxScoreValue, levelValue, speedValue;     //标签值

    public String scoreString = "分数：", maxScoreString = "最高分：", levelString = "等级：", speedString = "速度：";

    private LinearLayout viewLayout,btnLayout;//布局背景设置

    public TetrisView view;

    public ShowNextBlockView nextBlockView;

    //通过异步消息处理机制在父进程更新UI
    public android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TetrisView.CLEAR:
                    score.setText(scoreString + scoreValue);
                    speed.setText(speedString + speedValue);
                    level.setText(levelString + levelValue);
                    view.invalidate();
                    break;
                case 2:
                    nextBlockView.invalidate();
                    break;
                case 3:
                    nextBlockView.invalidate();
                    break;
                case 4:
                    view.invalidate();
                    break;
                case 5:
                    score.setText(scoreString + scoreValue);
                    level.setText(levelString + levelValue);
                    speed.setText(speedString + speedValue);
                    maxScore.setText(maxScoreString + maxScoreValue);
                    view.invalidate();
                default:
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        isPause = true;
        AlertDialog.Builder dialog = new AlertDialog.Builder(Main.this);
        dialog.setCancelable(false);
        dialog.setMessage("是否退出？");
        dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Main.this, Start.class);
                startActivity(intent);
            }
        });
        dialog.setPositiveButton("继续游戏", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isPause = false;
            }
        });
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        isPause = false;//暂停初始为false
        // 获取各组件和标签值
        view = (TetrisView) findViewById(R.id.tetrisView);
        left = (Button) findViewById(R.id.left);                    //左移
        right = (Button) findViewById(R.id.right);                //右移
        rotate = (Button) findViewById(R.id.rotate);                //旋转
        speedUp = (Button) findViewById(R.id.speedUp);            //加速
        pause = (Button) findViewById(R.id.pause);                //暂停
        viewLayout = (LinearLayout)findViewById(R.id.view_layout);
        btnLayout = (LinearLayout)findViewById(R.id.btn_layout);
        nextBlockView = (ShowNextBlockView) findViewById(R.id.nextBlockView);
        nextBlockView.invalidate();
        score = (TextView) findViewById(R.id.score);
        maxScore = (TextView) findViewById(R.id.maxScore);
        level = (TextView) findViewById(R.id.level);
        speed = (TextView) findViewById(R.id.speed);
        scoreValue = 0;
        levelValue = speedValue = (TetrisView.difficultyType - 1) * 10;
        if(levelValue == 0){
            levelValue++;
            speedValue++;
        }
        score.setText(scoreString + scoreValue);
        level.setText(levelString + levelValue);
        speed.setText(speedString + speedValue);
        view.setFather(this);
        if (TetrisView.difficultyType == 1)
            maxScoreValue = FileControl.getInstance().loadFile("easy", Main.this);
        else if (TetrisView.difficultyType == 2)
            maxScoreValue = FileControl.getInstance().loadFile("hard", Main.this);
        maxScore.setText(maxScoreString + maxScoreValue);
        view.init();
        //判断是否为夜间模式，修改相应布局的设置
        if(Start.NIGHT_flag){
            viewLayout.setBackgroundColor(Color.BLACK);
            btnLayout.setBackgroundColor(Color.BLACK);
            score.setTextColor(Color.BLUE);
            level.setTextColor(Color.BLUE);
            speed.setTextColor(Color.BLUE);
            maxScore.setTextColor(Color.BLUE);
        }else{
            viewLayout.setBackgroundColor(Color.WHITE);
            btnLayout.setBackgroundColor(Color.WHITE);
            score.setTextColor(Color.BLACK);
            level.setTextColor(Color.BLACK);
            speed.setTextColor(Color.BLACK);
            maxScore.setTextColor(Color.BLACK);
        }

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
                isPause = true;
                AlertDialog.Builder dialog = new AlertDialog.Builder(Main.this);
                dialog.setCancelable(false);
                dialog.setMessage("游戏暂停中...");
                dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Main.this, Start.class);
                        startActivity(intent);
                    }
                });
                dialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isPause = false;
                    }
                });
                dialog.show();
            }
        });

        view.invalidate();
    }
}

package com.example.tetris;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2017/7/26.
 */

public class FileControl {

    private static FileControl fileControl = new FileControl();

    private FileControl() {
    }

    public static FileControl getInstance() {
        return fileControl;
    }

    //判断文件是否存在，如果不存在，则初始化一个文件。
    public void fileExist(String filepath, String filename, Context context) {
        File file = new File(filepath);
        if (!file.exists()) {
            FileOutputStream out = null;
            BufferedWriter writer = null;
            try {
                out = context.openFileOutput(filename, Context.MODE_PRIVATE);
                writer = new BufferedWriter(new OutputStreamWriter(out));
                writer.write("最高分：" + 0);
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

    //根据输入的内容和文件名将内容写入指定文件中
    public void saveFile(String inputText, String filename, Context context) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
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

    //根据文件名读取文件里的内容，并截取内容的分数部分作为返回值。
    public int loadFile(String filename, Context context) {
        FileInputStream in = null;
        BufferedReader reader = null;
        int score = 0;
        try {
            in = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            String scoreString = reader.readLine();
            score = Integer.parseInt(scoreString.substring(4));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return score;
    }

    //根据文件名将文件的内容直接返回。
    public String onlyReadFile(String filename, Context context) {
        FileInputStream in = null;
        BufferedReader reader = null;
        String scoreString = "";
        try {
            in = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            scoreString = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return scoreString;
    }

    //根据文件名重置文件里的内容
    public void resetFile(String filename, Context context) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write("最高分：" + 0);
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

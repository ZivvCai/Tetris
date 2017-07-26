package com.example.tetris;

import android.content.Context;
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

public class FileControl extends AppCompatActivity{

    private static FileControl fileControl = new FileControl();

    private FileControl(){}

    public static FileControl getInstance(){
        return fileControl;
    }

    public boolean fileExist(String filepath){
        File file = new File(filepath);
        if(!file.exists()){
            FileOutputStream out = null;
            BufferedWriter writer = null;
            try {
                //out = openFileOutput("record", Context.MODE_PRIVATE);
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
            return false;
        }else {
            return true;
        }
    }

    public void saveFile(String inputText,String filename){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput(filename, Context.MODE_PRIVATE);
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

    public int loadFile(String filename) {
        FileInputStream in = null;
        BufferedReader reader = null;
        int score = 0;
        try {
            in = openFileInput(filename);
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

}

package com.example.randhawanumerical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//order to read comments:
//really doesn't matter but i ignore previous commented things
//game, instructions, settings, main.
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean changed = false;
        //declaring files and making them (only way it worked on my end)
        File theme = new File(getFilesDir(), "theme.txt");
        File ai = new File(getFilesDir(), "ai.txt");
        File replace = new File(getFilesDir(), "replace.txt");
        File end = new File(getFilesDir(), "end.txt");
        File mode = new File(getFilesDir(), "mode.txt");
        try {
            theme.createNewFile();
            ai.createNewFile();
            replace.createNewFile();
            end.createNewFile();
            mode.createNewFile();
            FileInputStream in = openFileInput("theme.txt");
            if (in.read() == 3) {
                setContentView(R.layout.activity_main3);
                changed = true;
            }
            in = openFileInput("theme.txt");
            if (in.read() == 2) {
                setContentView(R.layout.activity_main2);
            } else if (!changed) {
                setContentView(R.layout.activity_main);
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

    //goes to game screen, depending on theme choice it runs completely different java files because of the drawable differences
    public void gamescreen(View view) {
        try {
            FileInputStream in = openFileInput("theme.txt");
            Intent game = new Intent(this, Game.class);
            if (in.read() == 3)
                game = new Intent(this, Gameend.class);
            in = openFileInput("theme.txt");
            if (in.read() == 2) {
                game = new Intent(this, Gamenether.class);
            }
            startActivity(game);
        } catch (IOException e) {

        }
    }

    // goes to instruct screen
    public void instructscreen(View view) {
        Intent instruct = new Intent(this, Instructions.class);
        startActivity(instruct);
    }

    //settings screen
    public void settingscreen(View view) {
        Intent settings = new Intent(this, Settings.class);
        startActivity(settings);
    }

}
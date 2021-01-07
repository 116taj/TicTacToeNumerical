package com.example.randhawanumerical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//instructions
public class Instructions extends AppCompatActivity {
    boolean changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        try {
            FileInputStream in = openFileInput("end.txt");
            FileOutputStream out = openFileOutput("end.txt", MODE_PRIVATE);
            int x = in.read();
            x++;
            out.write(x);
            out.flush();
            out.close();
            in.close();
            in = openFileInput("theme.txt");
            if (in.read() == 3) {
                setContentView(R.layout.activity_instructions3);
                changed = true;
            }
            // reinit is required because file is 1 line long
            in = openFileInput("theme.txt");
            if (in.read() == 2) {
                setContentView(R.layout.activity_instructions2);
            } else if (!changed)
                setContentView(R.layout.activity_instructions);
        } catch (IOException e) {

        }
    }

    // goes to previous screen (either game screen or title)
    public void back(View view) {
        finish();
    }
}
package com.example.randhawanumerical;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Settings extends AppCompatActivity {
    // for string function skill test, remembers the random value so it can ask question
    int z = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean changed = false;
        //changes theme based on theme value
        try {
            FileInputStream in = openFileInput("theme.txt");
            if (in.read() == 3) {
                setContentView(R.layout.activity_settings3);
                changed = true;
            }
            in = openFileInput("theme.txt");
            if (in.read() == 2) {
                setContentView(R.layout.activity_settings2);
            } else if (!changed) {
                setContentView(R.layout.activity_settings);
            }
            //if options were already checked, leaves them checked
            in = openFileInput("ai.txt");
            if (in.read() == 1) {
                CheckBox check = findViewById(R.id.aicheck);
                check.setChecked(true);
            }
            in = openFileInput("replace.txt");
            if (in.read() == 1) {
                CheckBox check2 = findViewById(R.id.replacecheck);
                check2.setChecked(true);
            }
            //handles if options are unlocked or locked
            in = openFileInput("end.txt");
            if (in.read() < 0) {
                RadioButton endbutton = (RadioButton) findViewById(R.id.end);
                RadioButton lockbutton = (RadioButton) findViewById(R.id.locked);
                endbutton.setVisibility(View.GONE);
                lockbutton.setVisibility(View.VISIBLE);
            }
            in = openFileInput("mode.txt");
            if (in.read() < 0) {
                CheckBox check = (CheckBox) findViewById(R.id.replacecheck);
                CheckBox lock = (CheckBox) findViewById(R.id.lockcheck);
                check.setVisibility(View.GONE);
                lock.setVisibility(View.VISIBLE);
            }
            in.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

    //changes theme to nether by updating file
    public void nether(View view) {
        try {
            FileOutputStream out = openFileOutput("theme.txt", Activity.MODE_PRIVATE);
            out.write(2);
            out.flush();
            out.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

    //changes theme to end by updating file
    public void end(View view) {
        try {
            FileOutputStream out = openFileOutput("theme.txt", Activity.MODE_PRIVATE);
            out.write(3);
            out.flush();
            out.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

    //changes theme to overworld by updating file
    public void normal(View view) {
        try {
            FileOutputStream out = openFileOutput("theme.txt", Activity.MODE_PRIVATE);

            out.write(1);
            out.flush();
            out.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }

    }

    //begins toggle of ai
    public void aiclick(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        CheckBox check = (CheckBox) findViewById(R.id.aicheck);
        if (checked) {
            check.setChecked(true);
            aiON();
        } else {
            aiOFF();
            check.setChecked(false);
        }
    }

    //begins toggle of replace
    public void replaceclick(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        CheckBox check = (CheckBox) findViewById(R.id.replacecheck);
        if (checked) {
            check.setChecked(true);
            replaceON();
        } else {
            replaceOFF();
            check.setChecked(false);
        }
    }

    //turns ai on
    public void aiON() {
        try {
            FileOutputStream out = openFileOutput("ai.txt", Activity.MODE_PRIVATE);
            FileInputStream in = openFileInput("ai.txt");
            out.write(1);
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

    //turns ai off
    public void aiOFF() {
        try {
            FileOutputStream out = openFileOutput("ai.txt", Activity.MODE_PRIVATE);
            FileInputStream in = openFileInput("ai.txt");
            out.write(0);
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

    //turns replace on
    public void replaceON() {
        try {
            FileOutputStream out = openFileOutput("replace.txt", Activity.MODE_PRIVATE);
            FileInputStream in = openFileInput("replace.txt");
            out.write(1);
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

    //turns replace off
    public void replaceOFF() {
        try {
            FileOutputStream out = openFileOutput("replace.txt", Activity.MODE_PRIVATE);
            FileInputStream in = openFileInput("replace.txt");
            out.write(0);
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

    //goes back to title, but prevents the choice of both modes because doesn't function
    public void back(View view) {
        boolean checkedai = ((CheckBox) findViewById(R.id.replacecheck)).isChecked();
        boolean checkedreplace = ((CheckBox) findViewById(R.id.aicheck)).isChecked();
        if (checkedreplace && checkedai) {
            Toast.makeText(getApplicationContext(), "You cannot choose both modes!", Toast.LENGTH_SHORT).show();
        } else {
            Intent title = new Intent(this, MainActivity.class);
            startActivity(title);
        }
    }

    //creates help box
    public void help(View view) {
        helpbox();
    }

    // helpbox describes what settings do
    public void helpbox() {
        new AlertDialog.Builder(this)
                .setTitle("What do these do?")
                .setMessage("Themes change how everything look. Based off the 3 Minecraft dimensions, they are purely cosmetic." +
                        "\nModes are different ways to play numerical tic-tac-toe." +
                        "\nThe \"LOCKED\" options will be unlocked by changing settings and then playing the game OR attempting skill-testing questions.")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    //launches skill box
    public void skill(View view) {
        skillbox();
    }

    //skillbox asks one of 3 questions, checks if you're right, if yes you unlock everything, if no you do it again.
    public void skillbox() {
        String x = getquestion();
        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle("THE QUESTION")
                .setMessage(x);
        //creating an edittext in java so it can be in the dialog box.
        EditText ans = new EditText(Settings.this);
        LinearLayout.LayoutParams linear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ans.setLayoutParams(linear);
        alert.setView(ans);
        alert.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            //onclick it checks if you are correct
            public void onClick(DialogInterface dialog, int which) {
                String y = ans.getText().toString();
                if (y.equals(getanswer(y))) {
                    try {
                        OutputStream out = openFileOutput("mode.txt", MODE_PRIVATE);
                        int d = 1;
                        out.write(d);
                        out.flush();
                        out = openFileOutput("end.txt", MODE_PRIVATE);
                        out.write(d);
                        out.close();
                    } catch (IOException e) {

                    }
                    Toast.makeText(getApplicationContext(), "Correct answer. Leave the screen and come back to unlock all settings.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong answer. Try again.", Toast.LENGTH_SHORT).show();
                    skillbox();
                }
            }
        }).show();
    }

    //gets question and returns it so it can be in the dialog box
    public String getquestion() {
        int x = (int) (Math.random() * 3);
        z = x;
        switch (x) {
            case 0:
                return "Type a palindrome.";
            case 1:
                return "Type a word where there is an o in the 2nd position.";
            default:
                return "Type a word where if every second letter is deleted, the word would be mine.";
        }
    }

    //returns answer (same as user answer) but it checks whether the answer fulfills the requirements
    public String getanswer(String y) {
        String ans = "";
        if (z == 0) {
            String check = "";
            int c = y.length() - 1;
            for (int i = 0; i < y.length(); i++) {
                ans += y.charAt(i);
                check += y.charAt(c);
                c--;
            }
            if (check.equals(ans))
                ans = check;
        } else if (z == 1) {
            if (y.charAt(1) == 'o')
                ans = y;
        } else {
            for (int i = 0; i < y.length(); i += 2) {
                ans += y.charAt(i);
            }
            if (ans.equals("mine"))
                ans = y;
        }
        return ans;
    }

}
package com.example.randhawanumerical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.*;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

//THIS JAVA FILE IS NEAR IDENTICAL TO NORMAL GAME EXCEPT FOR IMAGE CHANGES (turnpic.setImageResource(R.drawable.xturne);)
//NO COMMENTS FOR THIS REASON, READ Game.java INSTEAD :)\
// THIS JAVA FILE IS NEAR IDENTICAL TO NORMAL GAME EXCEPT FOR IMAGE CHANGES (turnpic.setImageResource(R.drawable.xturne);)
//NO COMMENTS FOR THIS REASON, READ Game.java INSTEAD :)
//THIS JAVA FILE IS NEAR IDENTICAL TO NORMAL GAME EXCEPT FOR IMAGE CHANGES (turnpic.setImageResource(R.drawable.xturne);)
//NO COMMENTS FOR THIS REASON, READ Game.java INSTEAD :)
public class Gamenether extends AppCompatActivity {
    int board[][] = new int[3][3];
    int oddavail[] = {1, 3, 5, 7, 9};
    int p1select = 1;
    int p2select = 2;
    int evenavail[] = {2, 4, 6, 8};
    int turn = 1;
    int oddcount = 0;
    int evencount = 0;
    int p1numwins = 0;
    int p2numwins = 0;
    boolean gameover = false;
    boolean aienable = true;
    boolean replaceenable = false;
    boolean p1hasreplaced = false;
    boolean p2hasreplaced = false;
    boolean p1isreplacing = false;
    boolean p2isreplacing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        try {
            FileInputStream in = openFileInput("ai.txt");
            if (in.read() == 1) {
                aienable = true;
            } else {
                aienable = false;
            }
            in = openFileInput("replace.txt");
            if (in.read() == 1) {
                replaceenable = true;
                showreplace();
                hidewins();
            } else {
                replaceenable = false;
                hidereplace();
                showwins();
            }
            in.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

    public void flip(ImageView i) {
        ImageView turnpic = (ImageView) findViewById(R.id.turn);
        ImageView num = (ImageView) findViewById(R.id.number);
        ImageView a = (ImageView) findViewById(R.id.a);
        ImageView b = (ImageView) findViewById(R.id.b);
        ImageView c = (ImageView) findViewById(R.id.c);
        ImageView d = (ImageView) findViewById(R.id.d);
        ImageView e = (ImageView) findViewById(R.id.e);
        ImageView f = (ImageView) findViewById(R.id.f);
        ImageView g = (ImageView) findViewById(R.id.g);
        ImageView h = (ImageView) findViewById(R.id.h);
        ImageView p = (ImageView) findViewById(R.id.i);
        if (turn == 1) {
            changeodd(i);
            oddavail[oddcount] = -1;
            turn = 2;
            p2select = -1;
            turnpic.setImageResource(R.drawable.oturnn);
            win();
            if (aienable && gameover == false) {
                aiturn(a, b, c, d, e, f, g, h, p);
                turnpic.setImageResource(R.drawable.xturnn);
            }
        } else {
            changeeven(i);
            evenavail[evencount] = -1;
            turn = 1;
            p1select = -1;
            turnpic.setImageResource(R.drawable.xturnn);
            win();
        }
        num.setImageResource(R.drawable.blank);
    }

    public void cycle(View view) {
        ImageView num = (ImageView) findViewById(R.id.number);
        if (turn == 1) {
            do {
                if (oddcount < 4)
                    oddcount++;
                else
                    oddcount = 0;
            } while (oddavail[oddcount] == -1);
            p1select = oddavail[oddcount];
            changeodd(num);
        } else {
            do {
                if (evencount < 3)
                    evencount++;
                else
                    evencount = 0;
            } while (evenavail[evencount] == -1);
            p2select = evenavail[evencount];
            changeeven(num);
        }
    }

    public void changeodd(ImageView e) {
        switch (p1select) {
            case 1:
                e.setImageResource(R.drawable.xpicn);
                break;
            case 3:
                e.setImageResource(R.drawable.xpic3n);
                break;
            case 5:
                e.setImageResource(R.drawable.xpic5n);
                break;
            case 7:
                e.setImageResource(R.drawable.xpic7n);
                break;
            case 9:
                e.setImageResource(R.drawable.xpic9n);
                break;
        }
    }

    public void changeeven(ImageView e) {
        switch (p2select) {
            case 2:
                e.setImageResource(R.drawable.opicn);
                break;
            case 4:
                e.setImageResource(R.drawable.opic4n);
                break;
            case 6:
                e.setImageResource(R.drawable.opic6n);
                break;
            case 8:
                e.setImageResource(R.drawable.opic8n);
                break;
        }
    }

    public void win() {
        int winner = 0;
        if (board[0][0] + board[0][1] + board[0][2] == 15 && board[0][0] != 0 && board[0][1] != 0 && board[0][2] != 0)
            winner = turn;
        else if (board[1][0] + board[1][1] + board[1][2] == 15 && board[1][0] != 0 && board[1][1] != 0 && board[1][2] != 0)
            winner = turn;
        else if (board[2][0] + board[2][1] + board[2][2] == 15 && board[2][0] != 0 && board[2][1] != 0 && board[2][2] != 0)
            winner = turn;
        else if (board[0][0] + board[1][0] + board[2][0] == 15 && board[0][0] != 0 && board[1][0] != 0 && board[2][0] != 0)
            winner = turn;
        else if (board[0][1] + board[1][1] + board[2][1] == 15 && board[0][1] != 0 && board[1][1] != 0 && board[2][1] != 0)
            winner = turn;
        else if (board[0][2] + board[1][2] + board[2][2] == 15 && board[0][2] != 0 && board[1][2] != 0 && board[2][2] != 0)
            winner = turn;
        else if (board[0][0] + board[1][1] + board[2][2] == 15 && board[0][0] != 0 && board[1][1] != 0 && board[2][2] != 0)
            winner = turn;
        else if (board[0][2] + board[1][1] + board[2][0] == 15 && board[0][2] != 0 && board[1][1] != 0 && board[2][0] != 0)
            winner = turn;
        else if (board[0][0] != 0 && board[0][1] != 0 && board[0][2] != 0 &&
                board[1][0] != 0 && board[1][1] != 0 && board[1][2] != 0 &&
                board[2][0] != 0 && board[2][1] != 0 && board[2][2] != 0)
            winner = 3;
        if (winner == 2) {
            Toast.makeText(getApplicationContext(), "Crimson Nylium Wins", Toast.LENGTH_SHORT).show();
            gameover = true;
            p1numwins++;
            try {
                FileInputStream in = openFileInput("mode.txt");
                FileOutputStream out = openFileOutput("mode.txt", MODE_PRIVATE);
                int x = in.read();
                x++;
                out.write(x);
                out.flush();
                out.close();
            } catch (IOException e) {
            }
        } else if (winner == 1) {
            Toast.makeText(getApplicationContext(), "Warped Nylium Wins", Toast.LENGTH_SHORT).show();
            gameover = true;
            p2numwins++;
        } else if (winner == 3) {
            Toast.makeText(getApplicationContext(), "No one Wins", Toast.LENGTH_SHORT).show();
            gameover = true;
        }
    }

    public void aClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.a);
        if ((p1isreplacing || p2isreplacing) && board[0][0] != 0) {
            replace(i, 0, 0);
        } else if (p1isreplacing || p2isreplacing) {
            Toast.makeText(getApplicationContext(), "Place is not taken, cannot replace.", Toast.LENGTH_SHORT).show();
        } else if (p1select != -1 && p2select != -1 && gameover == false) {
            if (board[0][0] == 0) {
                if (turn == 2)
                    board[0][0] = p2select;
                else
                    board[0][0] = p1select;
                flip(i);
            } else {
                Toast.makeText(getApplicationContext(), "Place already taken", Toast.LENGTH_SHORT).show();
            }
        } else if (gameover)
            Toast.makeText(getApplicationContext(), "The game is over! Press reset.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Change the number!", Toast.LENGTH_SHORT).show();
    }

    public void bClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.b);
        if ((p1isreplacing || p2isreplacing) && board[0][1] != 0) {
            replace(i, 0, 1);
        } else if (p1isreplacing || p2isreplacing) {
            Toast.makeText(getApplicationContext(), "Place is not taken, cannot replace.", Toast.LENGTH_SHORT).show();
        } else if (p1select != -1 && p2select != -1 && gameover == false) {
            if (board[0][1] == 0) {
                if (turn == 2)
                    board[0][1] = p2select;
                else
                    board[0][1] = p1select;
                flip(i);
            } else {
                Toast.makeText(getApplicationContext(), "Place already taken", Toast.LENGTH_SHORT).show();
            }
        } else if (gameover)
            Toast.makeText(getApplicationContext(), "The game is over! Press reset.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Change the number!", Toast.LENGTH_SHORT).show();
    }

    public void cClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.c);
        if ((p1isreplacing || p2isreplacing) && board[0][2] != 0) {
            replace(i, 0, 2);
        } else if (p1isreplacing || p2isreplacing) {
            Toast.makeText(getApplicationContext(), "Place is not taken, cannot replace.", Toast.LENGTH_SHORT).show();
        } else if (p1select != -1 && p2select != -1 && gameover == false) {
            if (board[0][2] == 0) {
                if (turn == 2)
                    board[0][2] = p2select;
                else
                    board[0][2] = p1select;
                flip(i);
            } else {
                Toast.makeText(getApplicationContext(), "Place already taken", Toast.LENGTH_SHORT).show();
            }
        } else if (gameover)
            Toast.makeText(getApplicationContext(), "The game is over! Press reset.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Change the number!", Toast.LENGTH_SHORT).show();
    }

    public void dClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.d);
        if ((p1isreplacing || p2isreplacing) && board[1][0] != 0) {
            replace(i, 1, 0);
        } else if (p1isreplacing || p2isreplacing) {
            Toast.makeText(getApplicationContext(), "Place is not taken, cannot replace.", Toast.LENGTH_SHORT).show();
        } else if (p1select != -1 && p2select != -1 && gameover == false) {
            if (board[1][0] == 0) {
                if (turn == 2)
                    board[1][0] = p2select;
                else
                    board[1][0] = p1select;
                flip(i);
            } else {
                Toast.makeText(getApplicationContext(), "Place already taken", Toast.LENGTH_SHORT).show();
            }
        } else if (gameover)
            Toast.makeText(getApplicationContext(), "The game is over! Press reset.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Change the number!", Toast.LENGTH_SHORT).show();
    }

    public void eClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.e);
        if ((p1isreplacing || p2isreplacing) && board[1][1] != 0) {
            replace(i, 1, 1);
        } else if (p1isreplacing || p2isreplacing) {
            Toast.makeText(getApplicationContext(), "Place is not taken, cannot replace.", Toast.LENGTH_SHORT).show();
        } else if (p1select != -1 && p2select != -1 && gameover == false) {
            if (board[1][1] == 0) {
                if (turn == 2)
                    board[1][1] = p2select;
                else
                    board[1][1] = p1select;
                flip(i);
            } else {
                Toast.makeText(getApplicationContext(), "Place already taken", Toast.LENGTH_SHORT).show();
            }
        } else if (gameover)
            Toast.makeText(getApplicationContext(), "The game is over! Press reset.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Change the number!", Toast.LENGTH_SHORT).show();
    }

    public void fClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.f);
        if ((p1isreplacing || p2isreplacing) && board[1][2] != 0) {
            replace(i, 1, 2);
        } else if (p1isreplacing || p2isreplacing) {
            Toast.makeText(getApplicationContext(), "Place is not taken, cannot replace.", Toast.LENGTH_SHORT).show();
        } else if (p1select != -1 && p2select != -1 && gameover == false) {
            if (board[1][2] == 0) {
                if (turn == 2)
                    board[1][2] = p2select;
                else
                    board[1][2] = p1select;
                flip(i);
            } else {
                Toast.makeText(getApplicationContext(), "Place already taken", Toast.LENGTH_SHORT).show();
            }
        } else if (gameover)
            Toast.makeText(getApplicationContext(), "The game is over! Press reset.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Change the number!", Toast.LENGTH_SHORT).show();
    }

    public void gClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.g);
        if ((p1isreplacing || p2isreplacing) && board[2][0] != 0) {
            replace(i, 2, 0);
        } else if (p1isreplacing || p2isreplacing) {
            Toast.makeText(getApplicationContext(), "Place is not taken, cannot replace.", Toast.LENGTH_SHORT).show();
        } else if (p1select != -1 && p2select != -1 && gameover == false) {
            if (board[2][0] == 0) {
                if (turn == 2)
                    board[2][0] = p2select;
                else
                    board[2][0] = p1select;
                flip(i);
            } else {
                Toast.makeText(getApplicationContext(), "Place already taken", Toast.LENGTH_SHORT).show();
            }
        } else if (gameover)
            Toast.makeText(getApplicationContext(), "The game is over! Press reset.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Change the number!", Toast.LENGTH_SHORT).show();
    }

    public void hClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.h);
        if ((p1isreplacing || p2isreplacing) && board[2][1] != 0) {
            replace(i, 2, 1);
        } else if (p1isreplacing || p2isreplacing) {
            Toast.makeText(getApplicationContext(), "Place is not taken, cannot replace.", Toast.LENGTH_SHORT).show();
        } else if (p1select != -1 && p2select != -1 && gameover == false) {
            if (board[2][1] == 0) {
                if (turn == 2)
                    board[2][1] = p2select;
                else
                    board[2][1] = p1select;
                flip(i);
            } else {
                Toast.makeText(getApplicationContext(), "Place already taken", Toast.LENGTH_SHORT).show();
            }
        } else if (gameover)
            Toast.makeText(getApplicationContext(), "The game is over! Press reset.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Change the number!", Toast.LENGTH_SHORT).show();
    }

    public void iClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.i);
        if ((p1isreplacing || p2isreplacing) && board[2][2] != 0) {
            replace(i, 2, 2);
        } else if (p1isreplacing || p2isreplacing) {
            Toast.makeText(getApplicationContext(), "Place is not taken, cannot replace.", Toast.LENGTH_SHORT).show();
        } else if (p1select != -1 && p2select != -1 && gameover == false) {
            if (board[2][2] == 0) {
                if (turn == 2)
                    board[2][2] = p2select;
                else
                    board[2][2] = p1select;
                flip(i);
            } else {
                Toast.makeText(getApplicationContext(), "Place already taken", Toast.LENGTH_SHORT).show();
            }

        } else if (gameover)
            Toast.makeText(getApplicationContext(), "The game is over! Press reset.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Change the number!", Toast.LENGTH_SHORT).show();
    }

    public void reset(View view) {
        ImageView a = (ImageView) findViewById(R.id.a);
        a.setImageResource(R.drawable.blank);
        ImageView b = (ImageView) findViewById(R.id.b);
        b.setImageResource(R.drawable.blank);
        ImageView c = (ImageView) findViewById(R.id.c);
        c.setImageResource(R.drawable.blank);
        ImageView d = (ImageView) findViewById(R.id.d);
        d.setImageResource(R.drawable.blank);
        ImageView e = (ImageView) findViewById(R.id.e);
        e.setImageResource(R.drawable.blank);
        ImageView f = (ImageView) findViewById(R.id.f);
        f.setImageResource(R.drawable.blank);
        ImageView g = (ImageView) findViewById(R.id.g);
        g.setImageResource(R.drawable.blank);
        ImageView h = (ImageView) findViewById(R.id.h);
        h.setImageResource(R.drawable.blank);
        ImageView i = (ImageView) findViewById(R.id.i);
        i.setImageResource(R.drawable.blank);
        ImageView num = (ImageView) findViewById(R.id.number);
        num.setImageResource(R.drawable.xpicn);
        ImageView turnpic = (ImageView) findViewById(R.id.turn);
        turnpic.setImageResource(R.drawable.xturnn);
        ImageView xreplace = (ImageView) findViewById(R.id.replacex);
        ImageView oreplace = (ImageView) findViewById(R.id.replaceo);
        for (int k = 0; k < 3; k++) {
            for (int j = 0; j < 3; j++) {
                board[k][j] = 0;
            }
        }
        int n = 1;
        for (int l = 0; l < 5; l++) {
            oddavail[l] = l + n;
            n++;
            if (l < 4)
                evenavail[l] = l + n;
        }
        p1select = 1;
        p2select = 2;
        turn = 1;
        oddcount = 0;
        evencount = 0;
        gameover = false;
        TextView p1 = (TextView) findViewById(R.id.p1wins);
        TextView p2 = (TextView) findViewById(R.id.p2wins);
        p1.setText(p1numwins + "");
        p2.setText(p2numwins + "");
        p1hasreplaced = false;
        p2hasreplaced = false;
        p1isreplacing = false;
        p2isreplacing = false;
        xreplace.setImageResource(R.drawable.notreplacingxn);
        oreplace.setImageResource(R.drawable.notreplacingon);
    }

    public void back(View view) {
        Intent title = new Intent(this, MainActivity.class);
        startActivity(title);
    }

    public void aiturn(ImageView a, ImageView b, ImageView c, ImageView d, ImageView e, ImageView f, ImageView g, ImageView h, ImageView p) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0)
                    count++;
            }
        }
        int choice = (int) (Math.random() * count) + 1;
        do {
            evencount = (int) (Math.random() * 4);
        }
        while (evenavail[evencount] == -1);
        p2select = evenavail[evencount];
        ImageView pic = a;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    choice--;
                    if (choice == 0) {
                        board[i][j] = p2select;
                        pic = getimage(a, b, c, d, e, f, g, h, p, i, j);
                    }
                }
            }
        }
        changeeven(pic);
        evenavail[evencount] = -1;
        turn = 1;
        p1select = -1;
        win();
    }

    public ImageView getimage(ImageView a, ImageView b, ImageView c, ImageView d, ImageView e, ImageView f, ImageView g, ImageView h, ImageView p, int i, int j) {
        if (i == 0 && j == 0) {
            return a;
        } else if (i == 0 && j == 1) {
            return b;
        } else if (i == 0 && j == 2) {
            return c;
        } else if (i == 1 && j == 0) {
            return d;
        } else if (i == 1 && j == 1) {
            return e;
        } else if (i == 1 && j == 2) {
            return f;
        } else if (i == 2 && j == 0) {
            return g;
        } else if (i == 2 && j == 1) {
            return h;
        } else {
            return p;
        }
    }

    public void replacetogglex(View view) {
        if (turn == 1) {
            if (!p1hasreplaced) {
                ImageView xreplace = (ImageView) findViewById(R.id.replacex);
                if (!p1isreplacing) {
                    xreplace.setImageResource(R.drawable.xturnn);
                    p1isreplacing = true;
                } else {
                    xreplace.setImageResource(R.drawable.notreplacingxn);
                    p1isreplacing = false;
                }
            } else {
                Toast.makeText(getApplicationContext(), "You have already replaced!", Toast.LENGTH_SHORT).show();
            }
        } else
            Toast.makeText(getApplicationContext(), "It's not your turn! Try the other replace button.", Toast.LENGTH_SHORT).show();
    }

    public void replacetoggleo(View view) {
        if (turn == 2) {
            if (!p2hasreplaced) {
                ImageView oreplace = (ImageView) findViewById(R.id.replaceo);
                if (!p2isreplacing) {
                    oreplace.setImageResource(R.drawable.oturnn);
                    p2isreplacing = true;
                } else {
                    oreplace.setImageResource(R.drawable.notreplacingon);
                    p2isreplacing = false;
                }
            } else {
                Toast.makeText(getApplicationContext(), "You have already replaced!", Toast.LENGTH_SHORT).show();
            }
        } else
            Toast.makeText(getApplicationContext(), "It's not your turn! Try the other replace button.", Toast.LENGTH_SHORT).show();
    }

    public void showreplace() {
        ImageView xreplace = (ImageView) findViewById(R.id.replacex);
        ImageView oreplace = (ImageView) findViewById(R.id.replaceo);
        ImageView replace = (ImageView) findViewById(R.id.replace);
        replace.setVisibility(View.VISIBLE);
        oreplace.setVisibility(View.VISIBLE);
        xreplace.setVisibility(View.VISIBLE);
    }

    public void hidereplace() {
        ImageView xreplace = (ImageView) findViewById(R.id.replacex);
        ImageView oreplace = (ImageView) findViewById(R.id.replaceo);
        ImageView replace = (ImageView) findViewById(R.id.replace);
        replace.setVisibility(View.GONE);
        oreplace.setVisibility(View.GONE);
        xreplace.setVisibility(View.GONE);
    }

    public void hidewins() {
        ImageView xwins = (ImageView) findViewById(R.id.xwins);
        ImageView owins = (ImageView) findViewById(R.id.owins);
        ImageView wins1 = (ImageView) findViewById(R.id.wins1);
        ImageView wins2 = (ImageView) findViewById(R.id.wins2);
        TextView p1wins = (TextView) findViewById(R.id.p1wins);
        TextView p2wins = (TextView) findViewById(R.id.p2wins);
        xwins.setVisibility(View.GONE);
        owins.setVisibility(View.GONE);
        wins1.setVisibility(View.GONE);
        wins2.setVisibility(View.GONE);
        p1wins.setVisibility(View.GONE);
        p2wins.setVisibility(View.GONE);
    }

    public void showwins() {
        ImageView xwins = (ImageView) findViewById(R.id.xwins);
        ImageView owins = (ImageView) findViewById(R.id.owins);
        ImageView wins1 = (ImageView) findViewById(R.id.wins1);
        ImageView wins2 = (ImageView) findViewById(R.id.wins2);
        TextView p1wins = (TextView) findViewById(R.id.p1wins);
        TextView p2wins = (TextView) findViewById(R.id.p2wins);
        xwins.setVisibility(View.VISIBLE);
        owins.setVisibility(View.VISIBLE);
        wins1.setVisibility(View.VISIBLE);
        wins2.setVisibility(View.VISIBLE);
        p1wins.setVisibility(View.VISIBLE);
        p2wins.setVisibility(View.VISIBLE);
    }

    public void replace(ImageView pic, int i, int j) {
        int restore = board[i][j];
        if (turn == 1) {
            switch (restore) {
                case 2:
                    evenavail[0] = 2;
                    break;
                case 4:
                    evenavail[1] = 4;
                    break;
                case 6:
                    evenavail[2] = 6;
                    break;
                case 8:
                    evenavail[3] = 8;
                    break;
            }
            p1hasreplaced = true;
            p1isreplacing = false;
            ImageView xreplace = (ImageView) findViewById(R.id.replacex);
            xreplace.setImageResource(R.drawable.usedxn);
        } else {
            switch (restore) {
                case 1:
                    oddavail[0] = 1;
                    break;
                case 3:
                    oddavail[1] = 3;
                    break;
                case 5:
                    oddavail[2] = 5;
                    break;
                case 7:
                    oddavail[3] = 7;
                    break;
                case 9:
                    oddavail[4] = 9;
                    break;
            }
            p2hasreplaced = true;
            p2isreplacing = false;
            ImageView oreplace = (ImageView) findViewById(R.id.replaceo);
            oreplace.setImageResource(R.drawable.usedon);
        }
        pic.setImageResource(R.drawable.blank);
        board[i][j] = 0;
    }

    public void help(View view) {
        Intent ins = new Intent(this, Instructions.class);
        startActivity(ins);
    }
}
package io.github.jjang3530.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_newGame:
                newBoard();
                return true;

            case R.id.menu_reset:
                resetBoard();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    Button aButtons[][] = new Button[3][3];

    private boolean playerXTurn = true;
    private int roundCount;
    private int playerXPoints;
    private int playerOPoints;
    private TextView textViewPlayerX;
    private TextView textViewPlayerO;
    private TextView textViewMsg;
    private Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerX = findViewById(R.id.text_view_x);
        textViewPlayerO = findViewById(R.id.text_view_o);
        textViewMsg = findViewById(R.id.text_view_msg);

        textViewMsg.setText("Player X's turn");

        aButtons[0][0] = findViewById(R.id.button00);
        aButtons[0][1] = findViewById(R.id.button01);
        aButtons[0][2] = findViewById(R.id.button02);
        aButtons[1][0] = findViewById(R.id.button10);
        aButtons[1][1] = findViewById(R.id.button11);
        aButtons[1][2] = findViewById(R.id.button12);
        aButtons[2][0] = findViewById(R.id.button20);
        aButtons[2][1] = findViewById(R.id.button21);
        aButtons[2][2] = findViewById(R.id.button22);

        buttonReset = findViewById(R.id.Button_reset);

        // link delegate to the layout

        for(int j = 0; j < 3; j++){
            for(int i =  0; i < 3; i++){
                aButtons[i][j].setOnClickListener(this);
            }
        }

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBoard();
            }
        });

    }

    @Override

    public void onClick(View v){
        if (!((Button)v).getText().toString().equals("")){
            return;
        }
        if (playerXTurn) {
            ((Button) v).setText("X");
            textViewMsg.setText("Player O's turn");
        } else {
            ((Button) v).setText("O");
            textViewMsg.setText("Player X's turn");
        }
        roundCount++;

        if (checkForWin()) {
            if (playerXTurn) {
                playerXWins();
                freezeButton();
                textViewMsg.setText("X wins!");
                blinkButton();
            } else {
                playerOWins();
                freezeButton();
                textViewMsg.setText("O wins!");
                blinkButton();
            }
        } else if (roundCount == 9) {
            freezeButton();
            textViewMsg.setText("Draw!");
            blinkButton();
        } else {
            playerXTurn = !playerXTurn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = aButtons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void playerXWins() {
        playerXPoints++;
        updatePointsText();
    }

    private void playerOWins() {
        playerOPoints++;
        updatePointsText();
    }

    private void updatePointsText() {
        textViewPlayerX.setText("X WINS: " + playerXPoints);
        textViewPlayerO.setText("O WINS: " + playerOPoints);
    }

    private void newBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                aButtons[i][j].setText("");
            }
        }
        textViewMsg.setText("Player X's turn");
        roundCount = 0;
        playerXTurn = true;
        ableButton();
        buttonReset.clearAnimation();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                aButtons[i][j].setText("");
            }
        }
        textViewMsg.setText("Player X's turn");
        roundCount = 0;
        playerXTurn = true;
        ableButton();
        buttonReset.clearAnimation();
        playerXPoints = 0;
        playerOPoints = 0;
        updatePointsText();
    }

    private void freezeButton() {
        for(int j = 0; j < 3; j++){
            for(int i =  0; i < 3; i++){
                aButtons[i][j].setClickable(false);
            }
        }
    }

    private void ableButton() {
        for(int j = 0; j < 3; j++){
            for(int i =  0; i < 3; i++){
                aButtons[i][j].setClickable(true);
            }
        }
    }

    private void blinkButton() {
        Animation anim = new AlphaAnimation(0.0f, 3.0f);
        anim.setDuration(400); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        buttonReset.startAnimation(anim);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerXPoints", playerXPoints);
        outState.putInt("playerOPoints", playerOPoints);
        outState.putBoolean("playerXTurn", playerXTurn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        playerXPoints = savedInstanceState.getInt("playerXPoints");
        playerOPoints = savedInstanceState.getInt("playerOPoints");
        playerXTurn = savedInstanceState.getBoolean("playerXTurn");

    }
}
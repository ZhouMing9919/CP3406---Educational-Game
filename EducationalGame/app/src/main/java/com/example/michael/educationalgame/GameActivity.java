package com.example.michael.educationalgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    Button leftButton;
    Button rightButton;
    TextView question;

    Random random;
    int firstNumber;
    int secondNumber;
    int answer;
    String stringAnswer;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        leftButton = (Button) findViewById(R.id.leftButton);
        rightButton = (Button) findViewById(R.id.rightButton);
        question = (TextView) findViewById(R.id.question);
        generateQuestion();
        setButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void leftButtonPressed(View v) {
        if(leftButton.getText().equals(stringAnswer)) {
            System.out.println("NICE LEFT BUTTON CORRECT");
            score += 1;
            generateQuestion();
            setButtons();
        }

    }

    void rightButtonPressed(View v) {
        if(rightButton.getText().equals(stringAnswer)) {
            System.out.println("NICE RIGHT BUTTON CORRECT");
            score += 1;
            generateQuestion();
            setButtons();
        }
    }

    void generateQuestion() {
        random = new Random();
        firstNumber = random.nextInt(100);
        secondNumber = random.nextInt(100);
        answer = firstNumber + secondNumber;
        stringAnswer = Integer.toString(answer);
        String questionText = firstNumber + " + " + secondNumber + " =";
        question.setText(questionText);
    }

    void setButtons() {

        int leftOrRight = random.nextInt(1);
        if (leftOrRight == 0) {
            leftButton.setText(stringAnswer);
            rightButton.setText(stringAnswer);
        } else {
            rightButton.setText(stringAnswer);
            leftButton.setText(stringAnswer);
        }
    }
}

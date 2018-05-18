package com.example.michael.educationalgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import database.DatabaseHelper;
import database.HighScores;
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.support.v4.view.GestureDetectorCompat;

public class HighScoresActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    TextView highScoresText;
    DatabaseHelper highScores;
    List<HighScores> allScores;
    int scoreList[] = {0, 0, 0, 0, 0};

    private GestureDetectorCompat gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_high_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        highScoresText = (TextView) findViewById(R.id.highScores);
        highScores = new DatabaseHelper(this);
        allScores = highScores.getAllScores();

        gestureDetector = new GestureDetectorCompat(this, this);

        int count = 0;
        for (HighScores score : allScores) {

            if (count < 5) {
                if(score.getScore() > scoreList[count]) {
                    scoreList[count] = score.getScore();
                }
            }
            count = count + 1;
        }
        count = 0;

        highScoresText.setText("\n" + "1: " + scoreList[0] + "\n" + "2: " + scoreList[1] + "\n" + "3: " + scoreList[2] + "\n" + "4: " + scoreList[3] + "\n" + "5: " + scoreList[4]);

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}

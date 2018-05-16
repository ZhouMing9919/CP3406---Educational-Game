package com.example.michael.educationalgame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import database.DatabaseHelper;
import database.HighScores;

public class HighScoresActivity extends AppCompatActivity {

    TextView highScoresText;
    DatabaseHelper highScores;
    List<HighScores> allScores;
    int scoreList[] = {0, 0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        highScoresText = (TextView) findViewById(R.id.highScores);
        highScores = new DatabaseHelper(this);
        allScores = highScores.getAllScores();

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

        highScoresText.setText("HighScores: " + "\n" + "1: " + scoreList[0] + "\n" + "2: " + scoreList[1] + "\n" + "3: " + scoreList[2] + "\n" + "4: " + scoreList[3] + "\n" + "5: " + scoreList[4]);

    }
    //protected void onResume() {
    //    super.onResume();
//
    //    highScores = new DatabaseHelper(this);
    //    allScores = highScores.getAllScores();
//
    //    for (HighScores score : allScores) {
    //        int count = 0;
    //        if (count < 5) {
    //            if(score.getScore() > scoreList[count]) {
    //                scoreList[count] = score.getScore();
    //            }
    //        }
    //        count = count + 1;
    //    }
//
    //    highScoresText.setText("HighScores: " + "\n" + "1: " + scoreList[0] + "\n" + "2: " + scoreList[1] + "\n" + "3: " + scoreList[2] + "\n" + "4: " + scoreList[3] + "\n" + "5: " + scoreList[4]);

    //}
}

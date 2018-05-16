package com.example.michael.educationalgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import database.DatabaseHelper;
import database.HighScores;

public class GameOverActivity extends AppCompatActivity {

    SharedPreferences pref;

    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = getSharedPreferences("preferences", MODE_PRIVATE);

        score = (TextView) findViewById(R.id.scoreText);

        System.out.println(pref.getInt("score", 0));
        String scoreAsText = Integer.toString(pref.getInt("score", 0));
        score.setText("Score: " + scoreAsText);

        DatabaseHelper highscores = new DatabaseHelper(this);
        //long id = highscores.saveScore("Test", 9);
        //System.out.println(Long.toString(id));
        HighScores score = highscores.getScore(1);
        System.out.println(score.getName() + Integer.toString(score.getScore()));
    }

    void mainMenuButtonPress(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void replayButtonPress(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }


}

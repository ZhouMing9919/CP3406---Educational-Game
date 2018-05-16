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
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import database.DatabaseHelper;
import database.HighScores;

public class GameOverActivity extends AppCompatActivity {

    SharedPreferences pref;

    DatabaseHelper highScores;
    List<HighScores> allScores;
    TextView score;
    TextView highScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = getSharedPreferences("preferences", MODE_PRIVATE);

        highScoreText = (TextView) findViewById(R.id.highScoreText);
        score = (TextView) findViewById(R.id.scoreText);

        highScores = new DatabaseHelper(this);
        allScores = highScores.getAllScores();

        for (HighScores localScore : allScores) {
            if (pref.getInt("score", 0) > localScore.getScore()) {
                highScoreText.setVisibility(View.VISIBLE);
            } else {
                highScoreText.setVisibility(View.INVISIBLE);
            }
            break;
        }

        String scoreAsText = Integer.toString(pref.getInt("score", 0));
        score.setText("Score: " + scoreAsText);

        long id = highScores.saveScore("Test", pref.getInt("score", 0));
        
        //System.out.println(Long.toString(id));
        //HighScores score = highscores.getScore(1);
        //System.out.println(score.getName() + Integer.toString(score.getScore()));
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

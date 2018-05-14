package com.example.michael.educationalgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
        score.setText(scoreAsText);
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

package com.example.michael.educationalgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    SharedPreferences.Editor prefEditor;
    Button settingsButton;
    Button highScoresButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settingsButton = (Button) findViewById(R.id.settingsButton);
        highScoresButton = (Button) findViewById(R.id.highScoreButton);

        prefEditor = getSharedPreferences("preferences", MODE_PRIVATE).edit();
        prefEditor.putInt("firstValueMax", 100);
        prefEditor.putInt("secondValueMax", 100);
        prefEditor.apply();

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettingsActvity();
            }
        });
        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHighScoresActivity();
            }
        });
    }

    void startGameActivity(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    void startSettingsActvity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    void startHighScoresActivity() {
        Intent intent = new Intent(this, HighScoresActivity.class);
        startActivity(intent);
    }
}

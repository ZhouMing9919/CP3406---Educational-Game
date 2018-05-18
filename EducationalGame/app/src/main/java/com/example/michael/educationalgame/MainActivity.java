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
        //prefEditor.putInt("firstValueMin", 0);
        prefEditor.putInt("firstValueMax", 100);
        //prefEditor.putInt("secondValueMin", 0);
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

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
    //    // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.menu_main, menu);
    //    return true;
    //}
//
    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
    //    // Handle action bar item clicks here. The action bar will
    //    // automatically handle clicks on the Home/Up button, so long
    //    // as you specify a parent activity in AndroidManifest.xml.
    //    int id = item.getItemId();
//
    //    //noinspection SimplifiableIfStatement
    //    if (id == R.id.action_settings) {
    //        startSettingsActvity();
    //        return true;
    //    }
    //    if (id == R.id.action_highscores) {
    //        startHighScoresActivity();
    //        return true;
    //    }
//
    //    return super.onOptionsItemSelected(item);
    //}

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

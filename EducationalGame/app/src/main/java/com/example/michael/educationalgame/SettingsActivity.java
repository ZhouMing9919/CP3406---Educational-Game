package com.example.michael.educationalgame;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import database.DatabaseHelper;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences.Editor prefEditor;
    SharedPreferences pref;

    Button clearHighScores;
    SeekBar firstValueMaxSlider;
    SeekBar secondValueMaxSlider;
    TextView firstValueMaxText;
    TextView secondValueMaxText;
    DatabaseHelper highScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = getSharedPreferences("preferences", MODE_PRIVATE);

        clearHighScores = (Button) findViewById(R.id.clearHighscores);
        highScores = new DatabaseHelper(this);

        firstValueMaxSlider = (SeekBar) findViewById(R.id.firstValueMaxSlider);
        secondValueMaxSlider = (SeekBar) findViewById(R.id.secondValueMaxSlider);
        firstValueMaxText = (TextView) findViewById(R.id.firstValueMaxText);
        secondValueMaxText = (TextView) findViewById(R.id.secondValueMaxText);

        firstValueMaxText.setText(Integer.toString(pref.getInt("firstValueMax", 0)));
        secondValueMaxText.setText(Integer.toString(pref.getInt("secondValueMax", 0)));

        firstValueMaxSlider.setProgress(pref.getInt("firstValueMax", 0));
        secondValueMaxSlider.setProgress(pref.getInt("secondValueMax", 0));

        firstValueMaxSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prefEditor = getSharedPreferences("preferences", MODE_PRIVATE).edit();
                prefEditor.putInt("firstValueMax", firstValueMaxSlider.getProgress());
                prefEditor.apply();
                firstValueMaxText.setText(Integer.toString(pref.getInt("firstValueMax", 0)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        secondValueMaxSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prefEditor = getSharedPreferences("preferences", MODE_PRIVATE).edit();
                prefEditor.putInt("secondValueMax", secondValueMaxSlider.getProgress());
                prefEditor.apply();
                secondValueMaxText.setText(Integer.toString(pref.getInt("secondValueMax", 0)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    void clearHighscores(View v) {
        highScores.clearDatabase();
    }

}

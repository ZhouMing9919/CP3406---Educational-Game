package com.example.michael.educationalgame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.SensorListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


import java.util.Random;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor sensor;
    private SensorManager sensorManager;


    Button leftButton;
    Button rightButton;
    TextView question;
    TextView scoreText;
    ProgressBar timerBar;

    SharedPreferences.Editor prefEditor;
    SharedPreferences pref;

    Random random;
    int firstNumber;
    int secondNumber;
    int answer;
    String stringAnswer;
    int score;
    String stringScore;
    ValueAnimator animator;

    MediaPlayer dingSound;
    MediaPlayer clockSound;
    MediaPlayer wrongSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#7A7D7D"));
        setSupportActionBar(toolbar);



        pref = getSharedPreferences("preferences", MODE_PRIVATE);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);

        leftButton = (Button) findViewById(R.id.leftButton);
        rightButton = (Button) findViewById(R.id.rightButton);
        question = (TextView) findViewById(R.id.question);
        scoreText = (TextView) findViewById(R.id.score);
        timerBar = (ProgressBar) findViewById(R.id.timerBar);
        dingSound = MediaPlayer.create(this, R.raw.ding);
        wrongSound = MediaPlayer.create(this, R.raw.wrong);
        clockSound = MediaPlayer.create(this, R.raw.clock);
        animator = ValueAnimator.ofInt(0, timerBar.getMax());

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftButtonPressed();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightButtonPressed();
            }
        });
        generateAdditionQuestion();
        setButtons();
        runTimer();
        clockSound.start();
        clockSound.setLooping(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        animator.pause();
        clockSound.stop();
    }

    protected void onResume() {
        super.onResume();
        animator.resume();
        clockSound.start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] > 4) {
            leftButtonPressed();
        } else if (event.values[0] < -4) {
            rightButtonPressed();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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
        if (id == R.id.action_highscores) {
            Intent intent = new Intent(this, HighScoresActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void leftButtonPressed() {
        if(leftButton.getText().equals(stringAnswer)) {
            dingSound.start();
            System.out.println("NICE LEFT BUTTON CORRECT");
            score += 1;
            stringScore = Integer.toString(score);
            scoreText.setText(stringScore);
            generateAdditionQuestion();
            setButtons();
            runTimer();
        } else {
            gameOver();
        }

    }

    void rightButtonPressed() {
        if(rightButton.getText().equals(stringAnswer)) {
            dingSound.start();
            System.out.println("NICE RIGHT BUTTON CORRECT");
            score += 1;
            stringScore = Integer.toString(score);
            scoreText.setText(stringScore);
            generateAdditionQuestion();
            setButtons();
            runTimer();
        } else {
            gameOver();
        }
    }

    void generateAdditionQuestion() {
        random = new Random();
        firstNumber = random.nextInt(pref.getInt("firstValueMax", 0));
        secondNumber = random.nextInt(pref.getInt("secondValueMax", 0));
        answer = firstNumber + secondNumber;
        stringAnswer = Integer.toString(answer);
        String questionText = firstNumber + " + " + secondNumber + " =";
        question.setText(questionText);
    }

    void setButtons() {
        int fakeAnswer = (answer - 10) - random.nextInt(answer + 10);
        String fakeAnswerString = Integer.toString(fakeAnswer);
        int leftOrRight = random.nextInt(2);
        if (leftOrRight == 0) {
            leftButton.setText(stringAnswer);
            rightButton.setText(fakeAnswerString + ".");
        } else {
            rightButton.setText(stringAnswer);
            leftButton.setText(fakeAnswerString + ".");
        }
    }

    void gameOver() {
        wrongSound.start();
        prefEditor = getSharedPreferences("preferences", MODE_PRIVATE).edit();
        prefEditor.putInt("score", score);
        prefEditor.apply();
        System.out.println(pref.getInt("score", 0));

        animator.pause();
        animator.cancel();
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);
        stringScore = Integer.toString(score);
        scoreText.setText(stringScore);
    }

    void runTimer() {
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                timerBar.setProgress((Integer)animation.getAnimatedValue());

            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                gameOver();
            }
        });
        animator.start();
    }



}

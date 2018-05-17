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
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import database.DatabaseHelper;
import database.HighScores;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class GameOverActivity extends AppCompatActivity {

    private static final int AUTHENTICATE = 1;
    Twitter twitter = TwitterFactory.getSingleton();
    SharedPreferences pref;

    DatabaseHelper highScores;
    List<HighScores> allScores;
    TextView score;
    TextView highScoreText;
    Button shareToTwitter;

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
        shareToTwitter = (Button) findViewById(R.id.shareToTwitter);

        highScores = new DatabaseHelper(this);
        allScores = highScores.getAllScores();

        for (HighScores localScore : allScores) {
            if (pref.getInt("score", 0) > localScore.getScore()) {
                highScoreText.setVisibility(View.VISIBLE);
                shareToTwitter.setVisibility(View.VISIBLE);
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

    void authorise(View v) {
        Intent intent = new Intent(this, Authenticate.class);
        startActivityForResult(intent, AUTHENTICATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == AUTHENTICATE && resultCode == RESULT_OK) {
            Background.run(new Runnable() {
                @Override
                public void run() {
                    String token = data.getStringExtra("access token");
                    String secret = data.getStringExtra("access token secret");
                    AccessToken accessToken = new AccessToken(token, secret);
                    twitter.setOAuthAccessToken(accessToken);

                    Query query = new Query("@twitterapi");
                    QueryResult result;
                    try {
                        twitter.updateStatus("I scored a new high score of: " + Integer.toString(pref.getInt("score", 0)) + " on the Addition Educational Game");
                        result = twitter.search(query);
                    } catch (final Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //textView.setText(e.toString());
                            }
                        });
                        return;
                    }

                    // convert tweets into text
                    final StringBuilder builder = new StringBuilder();
                    for (Status status : result.getTweets()) {
                        builder.append(status.getUser().getScreenName())
                                .append(" said ")
                                .append(status.getText())
                                .append("\n");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //textView.setText(builder.toString().trim());
                        }
                    });
                }
            });
        }
    }
}

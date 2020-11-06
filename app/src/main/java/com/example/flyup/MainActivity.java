package com.example.flyup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mPlayer;
    private SharedPreferences mSharedPreferences;
    private FlyingBirdView mFlyingBirdView;
    private static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    TextView textViewScore;
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton settingsButton = findViewById(R.id.settings);
        Button exit = findViewById(R.id.Exit);
        Button play = findViewById(R.id.textButtonPlay);

        PreferenceManager.setDefaultValues(this,R.xml.settings_game,false);
       // mFlyingBirdView = new FlyingBirdView(this);
        textViewScore = findViewById(R.id.result);
        mSharedPreferences = getPreferences(MODE_PRIVATE);

        mPlayer = MediaPlayer.create(this, R.raw.bloom);
        // We play a music on Background
       // mPlayer.start();
        //User click on Play Button
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_fragment,new PlayFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });

        //User has intention to exit the game
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 message();
            }
        });

        //User want to access to game settings
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_fragment, new Settings())
                        .addToBackStack(null)
                        .commit();


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void message()
    {
        AlertDialog.Builder ExitMessage = new AlertDialog.Builder(this) ;
        ExitMessage.setTitle("Are you sure to exit?")
                .setIcon(R.drawable.exit)
                .setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();


                    }
                })
                .setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //mPlayer = MediaPlayer.create(this, R.raw.bloom);
        mPlayer.stop();
        mPlayer.release();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode)
        {
            result = data.getIntExtra("Score",0);
            mSharedPreferences.edit().putInt(PREF_KEY_SCORE,result).apply();
            show_score();
        }
    }

    @Override
    protected void onResume() {
        mPlayer = MediaPlayer.create(this, R.raw.bloom);
        mPlayer.start();
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlayer = MediaPlayer.create(this, R.raw.bloom);
        mPlayer.stop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer = MediaPlayer.create(this, R.raw.bloom);
        mPlayer.stop();
    }


    public void show_score()
    {
        int value_score = mSharedPreferences.getInt(PREF_KEY_SCORE, 0);
        String message =  "Your Last Score was : "+value_score;
        if(value_score >0)
           textViewScore.setText(message);
           textViewScore.setTextColor(Color.BLACK);
    }
}

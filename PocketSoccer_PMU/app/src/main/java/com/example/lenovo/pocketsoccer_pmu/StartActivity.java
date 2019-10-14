package com.example.lenovo.pocketsoccer_pmu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.HashMap;

public class StartActivity extends AppCompatActivity {

    private ImageButton buttonPlay;
    private ImageButton continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //getting the button
        buttonPlay = (ImageButton) findViewById(R.id.imagePlay);
        continueButton = findViewById(R.id.imageContinue);
        AppConstants.init(this);

        if(AppConstants.getSelectedValues().isCanContinue()) {
            continueButton.setImageResource(R.drawable.button_continue);
        } else  {
            continueButton.setImageResource(R.drawable.button_continuefalse);
        }





    }




    public void playGame(View v) {

        startActivity(new Intent(this, SingleOrMultiplayerActivity.class));
    }

    public void showStats(View view) {
        startActivity(new Intent(this, StatsActivity.class));

    }

    public void continueGame(View view) {
        if(AppConstants.getSelectedValues().isCanContinue()) {
            AppConstants.getSelectedValues().setContinueGame(true);
            startActivity(new Intent(this, ContinueActivity.class));
        }
    }

    public void settings(View v) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppConstants.getSelectedValues().isCanContinue()) {
            continueButton.setImageResource(R.drawable.button_continue);
        } else  {
            continueButton.setImageResource(R.drawable.button_continuefalse);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(AppConstants.getSelectedValues().isCanContinue()) {
            continueButton.setImageResource(R.drawable.button_continue);
        } else  {
            continueButton.setImageResource(R.drawable.button_continuefalse);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

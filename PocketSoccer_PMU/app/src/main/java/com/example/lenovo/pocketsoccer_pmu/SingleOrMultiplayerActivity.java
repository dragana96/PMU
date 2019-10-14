package com.example.lenovo.pocketsoccer_pmu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SingleOrMultiplayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_or_multiplayer);
        // setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    public void multiplayer(View view) {
        AppConstants.getSelectedValues().setMultiplayer(true);
        AppConstants.getSelectedValues().setSinglePlayer(false);
        startActivity(new Intent(this, SelectPlayersActivity.class));
    }

    public void singlePlayer(View view){
        AppConstants.getSelectedValues().setMultiplayer(false);
        AppConstants.getSelectedValues().setSinglePlayer(true);
        startActivity(new Intent(this, SelectPlayersActivity.class));
    }
}

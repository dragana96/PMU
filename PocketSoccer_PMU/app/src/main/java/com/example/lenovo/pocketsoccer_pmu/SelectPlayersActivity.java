package com.example.lenovo.pocketsoccer_pmu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;

public class SelectPlayersActivity extends AppCompatActivity {

    int flagCounterPlayer1;
    int flagCounterPlayer2;
    HashMap<Integer, Bitmap> allFlags;
    ImageView player1view;
    ImageView player2view;

    EditText player1Name;
    EditText player2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_players);
        // setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        flagCounterPlayer1 = 0;
        flagCounterPlayer2 = 0;
        player1view = findViewById(R.id.player1);
        player2view = findViewById(R.id.player2);

        player1Name = findViewById(R.id.player1Name);
        player2Name = findViewById(R.id.player2Name);
        allFlags = AppConstants.getAllFlags();
        player1view.setImageBitmap(AppConstants.getSelectedValues().getSelectedPlayer1());
        player2view.setImageBitmap(AppConstants.getSelectedValues().getSelectedPlayer2());

    }

    public void leftPlayer1(View view) {
        if(flagCounterPlayer1 == 0){
            flagCounterPlayer1 = allFlags.size() - 1;
        } else flagCounterPlayer1--;
        player1view.setImageBitmap(allFlags.get(flagCounterPlayer1));


        AppConstants.getSelectedValues().setSelectedPlayer1(allFlags.get(flagCounterPlayer1));

    }

    public void rightPlayer1(View view) {
        if(flagCounterPlayer1 == allFlags.size() - 1){
            flagCounterPlayer1 = 0;
        } else flagCounterPlayer1++;
        player1view.setImageBitmap(allFlags.get(flagCounterPlayer1));


        AppConstants.getSelectedValues().setSelectedPlayer1(allFlags.get(flagCounterPlayer1));

    }

    public void leftPlayer2(View view) {
        if(flagCounterPlayer2 == 0){
            flagCounterPlayer2 = allFlags.size() - 1;
        } else flagCounterPlayer2--;
        player2view.setImageBitmap(allFlags.get(flagCounterPlayer2));

        AppConstants.getSelectedValues().setSelectedPlayer2(allFlags.get(flagCounterPlayer2));

    }

    public void rightPlayer2(View view) {
        if(flagCounterPlayer2 == allFlags.size() - 1){
            flagCounterPlayer2 = 0;
        } else flagCounterPlayer2++;
        player2view.setImageBitmap(allFlags.get(flagCounterPlayer2));
        AppConstants.getSelectedValues().setSelectedPlayer2(allFlags.get(flagCounterPlayer2));

    }

    public void play(View v) {
        AppConstants.getSelectedValues().setPlayer1Name(player1Name.getText().toString());
        AppConstants.getSelectedValues().setPlayer2Name(player2Name.getText().toString());

        // postavljena su imena igraca
         startActivity(new Intent(this, GameActivity.class));
    }
}

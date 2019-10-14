package com.example.lenovo.pocketsoccer_pmu;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class GameTypeActivity extends AppCompatActivity {

    private ImageButton  timeButton;
    private ImageButton goalsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_type);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



    }

     public void setTimeGameType(View view) {
            AppConstants.getSelectedValues().setGoalsGame(false);
            AppConstants.getSelectedValues().setTimeGame(true);
            Toast.makeText(this, "Selected game type: time", Toast.LENGTH_SHORT).show();
            finish();
     }

    public void setGoalsGameType(View view) {
        AppConstants.getSelectedValues().setGoalsGame(true);
        AppConstants.getSelectedValues().setTimeGame(false);
        Toast.makeText(this, "Selected game type: number of goals", Toast.LENGTH_SHORT).show();
        finish();
    }






}

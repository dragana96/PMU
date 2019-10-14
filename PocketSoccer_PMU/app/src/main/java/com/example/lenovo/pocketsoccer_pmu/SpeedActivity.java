package com.example.lenovo.pocketsoccer_pmu;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class SpeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void fast(View view) {
            AppConstants.getSelectedValues().setGameSpeed(9);
            AppConstants.getSelectedValues().setComputerSpeed(10);
            Toast.makeText(SpeedActivity.this, "Selected speed: FAST", Toast.LENGTH_SHORT).show();
    }

    public void medium(View view) {
             AppConstants.getSelectedValues().setGameSpeed(25);
             AppConstants.getSelectedValues().setComputerSpeed(15);
             Toast.makeText(SpeedActivity.this, "Selected speed: MEDIUM", Toast.LENGTH_SHORT).show();
    }

    public void slow(View view){
            AppConstants.getSelectedValues().setGameSpeed(40);
            AppConstants.getSelectedValues().setComputerSpeed(20);
            Toast.makeText(SpeedActivity.this, "Selected speed: SLOW", Toast.LENGTH_SHORT).show();
    }
}

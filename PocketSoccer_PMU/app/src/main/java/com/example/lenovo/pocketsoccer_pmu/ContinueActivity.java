package com.example.lenovo.pocketsoccer_pmu;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class ContinueActivity extends AppCompatActivity {

    private static final String TAG = GameActivity.class.getSimpleName();
    private int field;
    private MainGamePanel mainGamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View

        mainGamePanel = new MainGamePanel(this);
        setContentView(mainGamePanel);

        // setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Log.d(TAG, "View added");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainGamePanel.saveGame();
        mainGamePanel.stopThreadFromRunning();
        AppConstants.getSelectedValues().setContinueGame(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainGamePanel.initAudio();
    }
}

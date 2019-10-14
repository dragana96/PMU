package com.example.lenovo.pocketsoccer_pmu;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = GameActivity.class.getSimpleName();
    private int field;
    private MainGamePanel mainGamePanel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    protected void onStop() {
        mainGamePanel.stopThreadFromRunning();
        finish();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainGamePanel.initAudio();
    }

    @Override
    protected void onPause() {
        // sacuvati igru
        super.onPause();
        long time = System.currentTimeMillis();
        mainGamePanel.saveGame();
        mainGamePanel.stopThreadFromRunning();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}

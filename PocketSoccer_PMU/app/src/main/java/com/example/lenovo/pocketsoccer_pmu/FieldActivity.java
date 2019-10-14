package com.example.lenovo.pocketsoccer_pmu;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;



import java.util.HashMap;

public class FieldActivity extends AppCompatActivity {

    private int fieldCounter;
    private ImageView fieldView;
    HashMap<Integer, Bitmap> allFields;


    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int width;
    private int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);
        // setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        fieldView = findViewById(R.id.fieldView);

        allFields = AppConstants.getAllFields();

        Bitmap picture = AppConstants.getSelectedValues().getSelectedField();
        width = screenWidth/10 * 6;
        height= screenHeight/5 *3;
        Bitmap resizePicture = Bitmap.createScaledBitmap(picture, width, height, true);
        fieldView.setImageBitmap(resizePicture);
        fieldCounter = 0;
    }



    public void left(View view) {

        if(fieldCounter == 0){
            fieldCounter = allFields.size() - 1;
        } else fieldCounter--;
        Bitmap picture = allFields.get(fieldCounter);
        Bitmap resizePicture = Bitmap.createScaledBitmap(picture, width, height, true);
        fieldView.setImageBitmap(resizePicture);

        AppConstants.getSelectedValues().setSelectedField(picture);
    }

   public void right(View view) {
        if(fieldCounter == allFields.size() - 1){
            fieldCounter = 0;
        } else fieldCounter++;
        Bitmap picture = allFields.get(fieldCounter);
        Bitmap resizePicture = Bitmap.createScaledBitmap(picture, width, height, true);
        fieldView.setImageBitmap(resizePicture);

       AppConstants.getSelectedValues().setSelectedField(picture);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
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

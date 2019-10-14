package com.example.lenovo.pocketsoccer_pmu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.HashMap;

public class AppConstants {

    private static HashMap<Integer, Bitmap> allFields;
    private static HashMap<Integer, Bitmap> allFlags;
    private static SelectedValues selectedValues;
    private static float screenHeight; // naknadno uneto, lopte drugacije postavljene
    private static float screenWidth;
    private static float goalWidth;
    private static float goalHeight;

    public static final int GOAL = 1;
    public static final int BALL = 2;
    public static final int TIMEOUT = 3;

    public static void init(Context context) {

        initFields(context.getResources());
        initFlags(context.getResources());
        selectedValues = new SelectedValues();
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        goalWidth = BitmapFactory.decodeResource(context.getResources(), R.drawable.goal).getWidth();
        goalHeight = BitmapFactory.decodeResource(context.getResources(), R.drawable.goal).getHeight();

    }


    public static float getGoalHeight() {
        return goalHeight;
    }

    public static float getGoalWidth() {
        return goalWidth;
    }

    public static int getGOAL() {
        return GOAL;
    }

    public static int getBALL() {
        return BALL;
    }

    public static int getTIMEOUT(){
        return TIMEOUT;
    }
    private static void initFields(Resources resources) {
        allFields = new HashMap<>();
        allFields.put(0, BitmapFactory.decodeResource(resources, R.drawable.field1));
        allFields.put(1, BitmapFactory.decodeResource(resources, R.drawable.field2));
        allFields.put(2, BitmapFactory.decodeResource(resources, R.drawable.field3));
        allFields.put(3, BitmapFactory.decodeResource(resources, R.drawable.field4));
        allFields.put(4, BitmapFactory.decodeResource(resources, R.drawable.field5));
    }

    private static void initFlags(Resources res) {
        allFlags = new HashMap<>();
        allFlags.put(0, BitmapFactory.decodeResource(res, R.drawable.button_serbia));
        allFlags.put(1, BitmapFactory.decodeResource(res, R.drawable.button_new_zealand));
        allFlags.put(2, BitmapFactory.decodeResource(res, R.drawable.button_australia));
        allFlags.put(3, BitmapFactory.decodeResource(res, R.drawable.button_croatia));
        allFlags.put(4, BitmapFactory.decodeResource(res, R.drawable.button_germany));
        allFlags.put(5, BitmapFactory.decodeResource(res, R.drawable.button_hungary));
        allFlags.put(6, BitmapFactory.decodeResource(res, R.drawable.button_italy));
        allFlags.put(7, BitmapFactory.decodeResource(res, R.drawable.button_macedonia));
        allFlags.put(8, BitmapFactory.decodeResource(res, R.drawable.button_netherlands));
        allFlags.put(9, BitmapFactory.decodeResource(res, R.drawable.button_norway));
        allFlags.put(10, BitmapFactory.decodeResource(res, R.drawable.button_portugal));
        allFlags.put(11, BitmapFactory.decodeResource(res, R.drawable.button_russia));
        allFlags.put(12, BitmapFactory.decodeResource(res, R.drawable.button_slovakia));
        allFlags.put(13, BitmapFactory.decodeResource(res, R.drawable.button_slovenia));
        allFlags.put(14, BitmapFactory.decodeResource(res, R.drawable.button_spain));
        allFlags.put(15, BitmapFactory.decodeResource(res, R.drawable.button_sweden));

    }

    public static HashMap<Integer, Bitmap> getAllFields() {
        return allFields;
    }

    public static SelectedValues getSelectedValues() {
        return selectedValues;
    }

    public static HashMap<Integer, Bitmap> getAllFlags() {
        return allFlags;
    }

    public static float getScreenHeight() {
        return screenHeight;
    }

    public static float getScreenWidth() {
        return screenWidth;
    }
}

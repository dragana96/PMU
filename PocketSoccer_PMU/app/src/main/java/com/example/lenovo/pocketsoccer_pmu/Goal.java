package com.example.lenovo.pocketsoccer_pmu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class Goal {

    private float x;   // the X coordinate
    private float y;   // the Y coordinate
    private float widthOfGoal;
    private Bitmap goalBitmap;

    public Goal(Bitmap bmap, float x, float y) {
        this.goalBitmap = bmap;
        this.widthOfGoal = bmap.getHeight();
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(goalBitmap, (float)(x-goalBitmap.getWidth()), (float)((y - goalBitmap.getHeight())/2), null);
    }




    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidthOfGoal() {
        return widthOfGoal;
    }

    public void setWidthOfGoal(float widthOfGoal) {
        this.widthOfGoal = widthOfGoal;
    }

    public Bitmap getGoalBitmap() {
        return goalBitmap;
    }

    public void setGoalBitmap(Bitmap goalBitmap) {
        this.goalBitmap = goalBitmap;
    }


}

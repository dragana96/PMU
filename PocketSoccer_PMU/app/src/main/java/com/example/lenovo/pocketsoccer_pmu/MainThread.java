package com.example.lenovo.pocketsoccer_pmu;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {


    private boolean running;

    private SurfaceHolder surfaceHolder;
    private MainGamePanel gamePanel;

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;
     //   Log.d(TAG, "Starting game loop");
        while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {

                    this.gamePanel.update();
                    this.gamePanel.render(canvas);
                }
            } finally {

                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

}

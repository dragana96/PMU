package com.example.lenovo.pocketsoccer_pmu;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private Bitmap currentField;
    private Bitmap currentPlayer1;
    private Bitmap currentPlayer2;
    private Goal goal1;
    private Goal goal2;
    private boolean firstTurn;
    private Object lock = new Object();
    Timer timer;

    private Context context;

    private PostGoal postGoal11;
    private PostGoal postGoal12;
    private PostGoal postGoal13;
    private PostGoal postGoal14;

    private PostGoal postGoal21;
    private PostGoal postGoal22;
    private PostGoal postGoal23;
    private PostGoal postGoal24;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private boolean turnPlayer1;
    private boolean turnPlayer2;
    private boolean computerTurn;
    private long expectedTime;
    private long period = 2000l;
    private long lastTime;

    private long timeStart;
    private Audio audioplayer;

    private Boolean gameOver;

    private int scorePlayer1;
    private int scorePlayer2;

    private boolean player1scored;
    private boolean player2scored;
    Paint paint;
    private long timeElapsed;


    private double timeEnd;
    private Paint transparentPaint;
    private Paint opaquePaint;
    private ArrayList<Ball> listOfObjectsOnTheField = null;
    private ArrayList<Ball> teamComputer = null;
    private HashMap<Integer, Bitmap> allFields;

    static final Object _sync = new Object();


    public MainGamePanel(Context context) {
        super(context);

        this.context = context;

        // We start thread when the screen loads
        // We are passing the current holder and the panel to its new constructor so the thread can access them.
        thread = new MainThread(getHolder(), this);
        // This line sets the current class (MainGamePanel) as the handler for the events happening on the actual surface.
        getHolder().addCallback(this);






        gameOver = false;

        timer = new Timer(true);
        initAudio();

        paint = new Paint();
        paint.setTextSize(60);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.WHITE);



        currentPlayer1 = AppConstants.getSelectedValues().getSelectedPlayer1();

        currentPlayer2 = AppConstants.getSelectedValues().getSelectedPlayer2();

        if(AppConstants.getSelectedValues().getSinglePlayer()) computerTurn = turnPlayer2;

        transparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        transparentPaint.setAlpha(170);

        opaquePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        opaquePaint.setAlpha(255);

        if(AppConstants.getSelectedValues().isCanContinue() && AppConstants.getSelectedValues().isContinueGame()) {
            continueInit();
            timeStart= System.currentTimeMillis() - AppConstants.getSelectedValues().getGameDuration();
            timeElapsed = AppConstants.getSelectedValues().getElapsedTime();
            scorePlayer1 = AppConstants.getSelectedValues().getScorePlayer1();
            scorePlayer2 = AppConstants.getSelectedValues().getScorePlayer2();
            currentField = AppConstants.getSelectedValues().getContinueField();

            WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            currentField = Bitmap.createScaledBitmap(currentField, displayMetrics.widthPixels, displayMetrics.heightPixels, true);
        }

        else  {
            currentField = AppConstants.getSelectedValues().getSelectedField();
            WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            currentField = Bitmap.createScaledBitmap(currentField, displayMetrics.widthPixels, displayMetrics.heightPixels, true);

            initField();
            timeStart= System.currentTimeMillis();
            timeElapsed = timeStart;
            scorePlayer1 = 0;
            scorePlayer2 = 0;
        }
        // The above line makes our Game Panel focusable, which means it can receive focus so it can handle events.
        setFocusable(true);
    }


    public void initAudio() {
        audioplayer = new Audio(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // We set running flag to true and we start up the thread.
        // By the time the this method is called the surface is already created and the game loop can be safely started.

        thread.setRunning(true);
        thread.start();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // delegating event handling to the droid


            for (Ball b : listOfObjectsOnTheField) {
                if (!((b instanceof SoccerBall) || (b instanceof PostGoal))) {
                    if(!((turnPlayer1 && b.getPlayer()==2)||((turnPlayer2 && b.getPlayer()==1)))) {
                        if(AppConstants.getSelectedValues().getMultiplayer()) {
                              if (b.handleActionDown((int) event.getX(), (int) event.getY())) break;
                        } else    if((AppConstants.getSelectedValues().getSinglePlayer() && turnPlayer1))
                                if (b.handleActionDown((int) event.getX(), (int) event.getY())) break;


                    }
                }
            }


        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released

            for (Ball ball : listOfObjectsOnTheField) {
                if (!((ball instanceof SoccerBall) || (ball instanceof PostGoal))) {
                    if(!((turnPlayer1 && ball.getPlayer()==2)||((turnPlayer2 && ball.getPlayer()==1)))) {
                        if(AppConstants.getSelectedValues().getMultiplayer() ) {
                            if (ball.isTouched()) {
                                ball.getSpeed().setXv((event.getX() - ball.getX()) / AppConstants.getSelectedValues().getGameSpeed());
                                ball.getSpeed().setYv((event.getY() - ball.getY()) / AppConstants.getSelectedValues().getGameSpeed());
                                ball.setTouched(false);
                                // gotov potez sledeci igrac moze da igra
                                changeTurns();
                            }
                        } else if((AppConstants.getSelectedValues().getSinglePlayer() && turnPlayer1)){
                            if (ball.isTouched()) {
                                ball.getSpeed().setXv((event.getX() - ball.getX()) / AppConstants.getSelectedValues().getGameSpeed());
                                ball.getSpeed().setYv((event.getY() - ball.getY()) / AppConstants.getSelectedValues().getGameSpeed());
                                ball.setTouched(false);
                                // gotov potez sledeci igrac moze da igra
                                 changeTurns();
                            }

                        }
                    }
                }
            }
        }
        return true;
    }


    protected void render(Canvas canvas) {
        // fills the canvas with black
        synchronized (_sync) {
            if (canvas != null) {

                canvas.drawBitmap(currentField, 0, 0, new Paint());
                goal1.draw(canvas);
                goal2.draw(canvas);
                String score = scorePlayer1 + "   " + scorePlayer2;
                canvas.drawText(score, AppConstants.getScreenWidth() / 2 - 53 , AppConstants.getScreenHeight() / 11,paint);

                String time;
                double current = System.currentTimeMillis() - timeStart;
                int min = (int) (current / 1000 / 60);
                int sec = (int) (current / 1000 % 60);
                time = min + ":" + sec;
                canvas.drawText(time, AppConstants.getScreenWidth() / 8 * 5, AppConstants.getScreenHeight() / 11, paint);
                for (Ball b : listOfObjectsOnTheField) {
                    b.draw(canvas);
                }
            }
        }
    }

    public void initField() {


        turnPlayer1 = AppConstants.getSelectedValues().isTurnPlayer1();
        turnPlayer2 = AppConstants.getSelectedValues().isTurnPlayer2();

        if(AppConstants.getSelectedValues().getSinglePlayer()){
            computerTurn = turnPlayer2;
            AppConstants.getSelectedValues().setComputerTurn(turnPlayer2);
        }

        player1scored = false;
        player2scored = false;



        listOfObjectsOnTheField = new ArrayList<>();


        listOfObjectsOnTheField.add(new Ball(currentPlayer1, AppConstants.getScreenWidth()/3, AppConstants.getScreenHeight()/2, 10,opaquePaint));
        listOfObjectsOnTheField.get(0).setPlayer(1);
        listOfObjectsOnTheField.add(new Ball(currentPlayer1, AppConstants.getScreenWidth()/4, AppConstants.getScreenHeight()/3, 10,opaquePaint));
        listOfObjectsOnTheField.get(1).setPlayer(1);
        listOfObjectsOnTheField.add(new Ball(currentPlayer1, AppConstants.getScreenWidth()/4, AppConstants.getScreenHeight() /3*2, 10,opaquePaint));
        listOfObjectsOnTheField.get(2).setPlayer(1);

        listOfObjectsOnTheField.add(new Ball(currentPlayer2, AppConstants.getScreenWidth() / 3 * 2, AppConstants.getScreenHeight() / 2, 10,transparentPaint));
        listOfObjectsOnTheField.get(3).setPlayer(2);
        listOfObjectsOnTheField.add(new Ball(currentPlayer2, AppConstants.getScreenWidth() / 4 * 3, AppConstants.getScreenHeight() /3, 10,transparentPaint));
        listOfObjectsOnTheField.get(4).setPlayer(2);
        listOfObjectsOnTheField.add(new Ball(currentPlayer2, AppConstants.getScreenWidth() / 4 * 3, AppConstants.getScreenHeight() /3*2, 10,transparentPaint));
        listOfObjectsOnTheField.get(5).setPlayer(2);

        if(AppConstants.getSelectedValues().getSinglePlayer()){
            teamComputer = new ArrayList<>();
            teamComputer.add(listOfObjectsOnTheField.get(3));
            teamComputer.add(listOfObjectsOnTheField.get(4));
            teamComputer.add(listOfObjectsOnTheField.get(5));

        }

        listOfObjectsOnTheField.add(new SoccerBall(BitmapFactory.decodeResource(getResources(), R.drawable.ball6), screenWidth / 2, screenHeight / 2, 6,opaquePaint));

        goal1 = new Goal(BitmapFactory.decodeResource(getResources(), R.drawable.goal), BitmapFactory.decodeResource(getResources(), R.drawable.goal).getWidth(), AppConstants.getScreenHeight());
        goal2 = new Goal(BitmapFactory.decodeResource(getResources(), R.drawable.goal2), AppConstants.getScreenWidth(), AppConstants.getScreenHeight());

        postGoal11 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getGoalWidth(), (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2, 20,transparentPaint);
        postGoal12 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getGoalWidth(), (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2 + AppConstants.getGoalHeight(), 20,transparentPaint);
        postGoal13 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getGoalWidth() / 2, (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2, 20,transparentPaint);
        postGoal14 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getGoalWidth() / 2, (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2 + AppConstants.getGoalHeight(), 20,transparentPaint);
        postGoal21 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getScreenWidth() - AppConstants.getGoalWidth(), (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2, 20,transparentPaint);
        postGoal22 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getScreenWidth() - AppConstants.getGoalWidth(), (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2 + AppConstants.getGoalHeight(), 20,transparentPaint);
        postGoal23 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getScreenWidth() - AppConstants.getGoalWidth() / 2 -  AppConstants.getGoalWidth() / 6, (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2, 20,transparentPaint);
        postGoal24 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getScreenWidth() - AppConstants.getGoalWidth() / 2, (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2 + AppConstants.getGoalHeight(), 20,transparentPaint);


        listOfObjectsOnTheField.add(postGoal11);
        listOfObjectsOnTheField.add(postGoal12);
        listOfObjectsOnTheField.add(postGoal13);
        listOfObjectsOnTheField.add(postGoal14);

        listOfObjectsOnTheField.add(postGoal21);
        listOfObjectsOnTheField.add(postGoal22);
        listOfObjectsOnTheField.add(postGoal23);
        listOfObjectsOnTheField.add(postGoal24);


        AppConstants.getSelectedValues().setCanContinue(true);
    }



    public void saveGame() {


        AppConstants.getSelectedValues().setTurnPlayer1(turnPlayer1);
        AppConstants.getSelectedValues().setTurnPlayer2(turnPlayer2);

        AppConstants.getSelectedValues().setScorePlayer1(scorePlayer1);
        AppConstants.getSelectedValues().setScorePlayer2(scorePlayer2);

        AppConstants.getSelectedValues().setPlayer1x(listOfObjectsOnTheField.get(0).getX());
        AppConstants.getSelectedValues().setPlayer1y(listOfObjectsOnTheField.get(0).getY());

        AppConstants.getSelectedValues().setPlayer2x(listOfObjectsOnTheField.get(1).getX());
        AppConstants.getSelectedValues().setPlayer2y(listOfObjectsOnTheField.get(1).getY());

        AppConstants.getSelectedValues().setPlayer3x(listOfObjectsOnTheField.get(2).getX());
        AppConstants.getSelectedValues().setPlayer3y(listOfObjectsOnTheField.get(2).getY());

        AppConstants.getSelectedValues().setPlayer4x(listOfObjectsOnTheField.get(3).getX());
        AppConstants.getSelectedValues().setPlayer4y(listOfObjectsOnTheField.get(3).getY());

        AppConstants.getSelectedValues().setPlayer5x(listOfObjectsOnTheField.get(4).getX());
        AppConstants.getSelectedValues().setPlayer5y(listOfObjectsOnTheField.get(4).getY());

        AppConstants.getSelectedValues().setPlayer6x(listOfObjectsOnTheField.get(5).getX());
        AppConstants.getSelectedValues().setPlayer6y(listOfObjectsOnTheField.get(5).getY());

        AppConstants.getSelectedValues().setSoccerBallx(listOfObjectsOnTheField.get(6).getX());
        AppConstants.getSelectedValues().setSoccerBally(listOfObjectsOnTheField.get(6).getY());

        AppConstants.getSelectedValues().setElapsedTime(timeElapsed);

        AppConstants.getSelectedValues().setContinueField(currentField);

        AppConstants.getSelectedValues().setGameDuration(System.currentTimeMillis() - timeStart);

    }


    public void continueInit() {

        turnPlayer1 = AppConstants.getSelectedValues().isTurnPlayer1();
        turnPlayer2 = AppConstants.getSelectedValues().isTurnPlayer2();

        if(AppConstants.getSelectedValues().getSinglePlayer()){
            computerTurn = turnPlayer2;
            AppConstants.getSelectedValues().setComputerTurn(turnPlayer2);
        }

        player1scored = false;
        player2scored = false;



        listOfObjectsOnTheField = new ArrayList<>();


        listOfObjectsOnTheField.add(new Ball(currentPlayer1, AppConstants.getSelectedValues().getPlayer1x(), AppConstants.getSelectedValues().getPlayer1y(), 10,opaquePaint));
        listOfObjectsOnTheField.get(0).setPlayer(1);
        listOfObjectsOnTheField.add(new Ball(currentPlayer1, AppConstants.getSelectedValues().getPlayer2x(), AppConstants.getSelectedValues().getPlayer2y(), 10,opaquePaint));
        listOfObjectsOnTheField.get(1).setPlayer(1);
        listOfObjectsOnTheField.add(new Ball(currentPlayer1, AppConstants.getSelectedValues().getPlayer3x(), AppConstants.getSelectedValues().getPlayer3y(), 10,opaquePaint));
        listOfObjectsOnTheField.get(2).setPlayer(1);

        listOfObjectsOnTheField.add(new Ball(currentPlayer2, AppConstants.getSelectedValues().getPlayer4x(), AppConstants.getSelectedValues().getPlayer4y(), 10,transparentPaint));
        listOfObjectsOnTheField.get(3).setPlayer(2);
        listOfObjectsOnTheField.add(new Ball(currentPlayer2, AppConstants.getSelectedValues().getPlayer5x(), AppConstants.getSelectedValues().getPlayer5y(), 10,transparentPaint));
        listOfObjectsOnTheField.get(4).setPlayer(2);
        listOfObjectsOnTheField.add(new Ball(currentPlayer2, AppConstants.getSelectedValues().getPlayer6x(), AppConstants.getSelectedValues().getPlayer6y(), 10,transparentPaint));
        listOfObjectsOnTheField.get(5).setPlayer(2);

        if(AppConstants.getSelectedValues().getSinglePlayer()){
            teamComputer = new ArrayList<>();
            teamComputer.add(listOfObjectsOnTheField.get(3));
            teamComputer.add(listOfObjectsOnTheField.get(4));
            teamComputer.add(listOfObjectsOnTheField.get(5));

        }

        listOfObjectsOnTheField.add(new SoccerBall(BitmapFactory.decodeResource(getResources(), R.drawable.ball6), AppConstants.getSelectedValues().getSoccerBallx(), AppConstants.getSelectedValues().getSoccerBally(), 6,opaquePaint));

        goal1 = new Goal(BitmapFactory.decodeResource(getResources(), R.drawable.goal), BitmapFactory.decodeResource(getResources(), R.drawable.goal).getWidth(), AppConstants.getScreenHeight());
        goal2 = new Goal(BitmapFactory.decodeResource(getResources(), R.drawable.goal2), AppConstants.getScreenWidth(), AppConstants.getScreenHeight());

        postGoal11 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getGoalWidth(), (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2, 20,transparentPaint);
        postGoal12 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getGoalWidth(), (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2 + AppConstants.getGoalHeight(), 20,transparentPaint);
        postGoal13 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getGoalWidth() / 2, (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2, 20,transparentPaint);
        postGoal14 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getGoalWidth() / 2, (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2 + AppConstants.getGoalHeight(), 20,transparentPaint);
        postGoal21 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getScreenWidth() - AppConstants.getGoalWidth(), (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2, 20,transparentPaint);
        postGoal22 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getScreenWidth() - AppConstants.getGoalWidth(), (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2 + AppConstants.getGoalHeight(), 20,transparentPaint);
        postGoal23 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getScreenWidth() - AppConstants.getGoalWidth() / 2 -  AppConstants.getGoalWidth() / 6, (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2, 20,transparentPaint);
        postGoal24 = new PostGoal(BitmapFactory.decodeResource(getResources(), R.drawable.postfinal), AppConstants.getScreenWidth() - AppConstants.getGoalWidth() / 2, (AppConstants.getScreenHeight() - AppConstants.getGoalHeight()) / 2 + AppConstants.getGoalHeight(), 20,transparentPaint);


        listOfObjectsOnTheField.add(postGoal11);
        listOfObjectsOnTheField.add(postGoal12);
        listOfObjectsOnTheField.add(postGoal13);
        listOfObjectsOnTheField.add(postGoal14);

        listOfObjectsOnTheField.add(postGoal21);
        listOfObjectsOnTheField.add(postGoal22);
        listOfObjectsOnTheField.add(postGoal23);
        listOfObjectsOnTheField.add(postGoal24);


    }

    public void update() {

        if(checkIfGameOver()) {
            gameOver = true;
            AppConstants.getSelectedValues().setCanContinue(false);
            stopThreadFromRunning();


            context.startActivity(new Intent(context, GameOverActivity.class)); // u game over activity prikaz svih igara izmedju ova dva igraca koji su igrali
            // srediti u bazi
            return;
        }
        synchronized (_sync) {
            for (Ball b : listOfObjectsOnTheField) {
                b.update();
                if (!(b instanceof PostGoal)) {
                    handleCollision(b);
                }


                if (b instanceof SoccerBall) {
                    if(checkIfPlayer1Scored(b, goal2)) {
                        //  povecaj skor prvom igracu
                        audioplayer.playSound(AppConstants.getGOAL());
                        scorePlayer1++;
                       player1scored = true;


                        // kad udje u change turns turnplayer1 treba da se postavi na true, turnplayer2 na false
                        turnPlayer2 = true;
                        turnPlayer1 = false;
                        computerTurn = turnPlayer2;
                        changeTurns();
                        resetField();


                    }
                    if(checkIfPlayer2Scored(b,goal1)) {
                        // povecaj skor drugom igracu
                        audioplayer.playSound(AppConstants.getGOAL());
                        scorePlayer2++;
                        player2scored = true;

                        turnPlayer1 = true;
                        turnPlayer2 = false;
                        computerTurn = turnPlayer2;
                        changeTurns();
                        resetField();

                    }
                }
            }

            if(System.currentTimeMillis() - timeElapsed >= 5000){
                audioplayer.playSound(AppConstants.getTIMEOUT());
                changeTurns();
            }

            if(AppConstants.getSelectedValues().getSinglePlayer()) {
                if (computerTurn && AppConstants.getSelectedValues().getComputerMadeMove()==false) {

                    makeComputerMove();
                }
            }
        }
    }


    public void changeTurns() {

        player1scored = false;
        player2scored = false;
        if(turnPlayer1) {
            turnPlayer1 = false;
            turnPlayer2 = true;
            computerTurn = turnPlayer2;
            listOfObjectsOnTheField.get(0).setPaint(transparentPaint);
            listOfObjectsOnTheField.get(1).setPaint(transparentPaint);
            listOfObjectsOnTheField.get(2).setPaint(transparentPaint);

            listOfObjectsOnTheField.get(3).setPaint(opaquePaint);
            listOfObjectsOnTheField.get(4).setPaint(opaquePaint);
            listOfObjectsOnTheField.get(5).setPaint(opaquePaint);


        } else if(turnPlayer2){
            turnPlayer1= true;
            turnPlayer2 = false;
            computerTurn = turnPlayer2;

            listOfObjectsOnTheField.get(0).setPaint(opaquePaint);
            listOfObjectsOnTheField.get(1).setPaint(opaquePaint);
            listOfObjectsOnTheField.get(2).setPaint(opaquePaint);

            listOfObjectsOnTheField.get(3).setPaint(transparentPaint);
            listOfObjectsOnTheField.get(4).setPaint(transparentPaint);
            listOfObjectsOnTheField.get(5).setPaint(transparentPaint);
        }



        timeElapsed = System.currentTimeMillis();
        AppConstants.getSelectedValues().setMadeMove(false);
        AppConstants.getSelectedValues().setComputerMadeMove(false);

    }




    public void resetField() {

        listOfObjectsOnTheField.get(0).setX(AppConstants.getScreenWidth()/3);
        listOfObjectsOnTheField.get(0).setY(AppConstants.getScreenHeight()/2);

        listOfObjectsOnTheField.get(1).setX(AppConstants.getScreenWidth()/4);
        listOfObjectsOnTheField.get(1).setY(AppConstants.getScreenHeight()/3);

        listOfObjectsOnTheField.get(2).setX(AppConstants.getScreenWidth()/4);
        listOfObjectsOnTheField.get(2).setY(AppConstants.getScreenHeight()/3 *2);

        listOfObjectsOnTheField.get(3).setX(AppConstants.getScreenWidth() / 3 * 2);
        listOfObjectsOnTheField.get(3).setY(AppConstants.getScreenHeight()/2);

        listOfObjectsOnTheField.get(4).setX(AppConstants.getScreenWidth() / 4 * 3);
        listOfObjectsOnTheField.get(4).setY(AppConstants.getScreenHeight()/3);

        listOfObjectsOnTheField.get(5).setX(AppConstants.getScreenWidth() / 4 * 3);
        listOfObjectsOnTheField.get(5).setY(AppConstants.getScreenHeight()/3 * 2);

        listOfObjectsOnTheField.get(6).setX(screenWidth/2);
        listOfObjectsOnTheField.get(6).setY(screenHeight/2);

        for(Ball b: listOfObjectsOnTheField) {
            b.setSpeed(new Speed());
        }


    }

    public void makeComputerMove() {


         TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (AppConstants.getSelectedValues().getComputerMadeMove() == false && computerTurn) {

                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(2) + 1;


                float soccerBallX = listOfObjectsOnTheField.get(6).getX();
                float soccerBallY = listOfObjectsOnTheField.get(6).getY();

                teamComputer.get(randomInt).getSpeed().setXv((soccerBallX - teamComputer.get(randomInt).getX()) / AppConstants.getSelectedValues().getComputerSpeed());
                teamComputer.get(randomInt).getSpeed().setYv((soccerBallY - teamComputer.get(randomInt).getY()) / AppConstants.getSelectedValues().getComputerSpeed());
                AppConstants.getSelectedValues().setComputerMadeMove(true);
                if(!player2scored)changeTurns();


            }
        }
    };

    timer.schedule(timerTask, 2000l);






    }

    public void handleCollision(Ball myBall) {
        synchronized (_sync) {
            for (Ball b : listOfObjectsOnTheField) {

                if (!(myBall.getX() == b.getX() && myBall.getY() == b.getY())) {
                    // getting distance from our ball and other ball
                    float distance = myBall.getDistance(b.getX(), b.getY());
                    if (distance <= myBall.getRadius() + b.getRadius()) {
                        audioplayer.playSound(AppConstants.getBALL());
                        myBall.resolveCollision(b);
                    }
                }
            }
        }
    }

    public boolean checkIfPlayer1Scored(Ball b, Goal goal) {

        if (b.getX() < AppConstants.getScreenWidth() && b.getX() > AppConstants.getScreenWidth() - AppConstants.getGoalWidth() + b.getRadius()/2
                && b.getY() > ((AppConstants.getScreenHeight() - goal.getGoalBitmap().getHeight()) / 2)
                && b.getY() < (((AppConstants.getScreenHeight() - goal.getGoalBitmap().getHeight()) / 2) + goal.getGoalBitmap().getHeight())) {

            return true;

        }
        return false;
    }

    public boolean checkIfPlayer2Scored(Ball b, Goal goal) {

        if (b.getX() > 0 && b.getX()  < AppConstants.getGoalWidth() -b.getRadius()/2
                && b.getY() > ((AppConstants.getScreenHeight() - goal.getGoalBitmap().getHeight()) / 2)
                && b.getY() < (((AppConstants.getScreenHeight() - goal.getGoalBitmap().getHeight()) / 2) + goal.getGoalBitmap().getHeight())) {
            return true;

        }
        return false;
    }







    public Boolean checkIfGameOver() {

        Boolean ret = false;
        if(AppConstants.getSelectedValues().getTimeGame()){

            double duration = System.currentTimeMillis() - timeStart;

            if (duration/1000/60 >= AppConstants.getSelectedValues().getSecondsToPlay()) {
                timeEnd = System.currentTimeMillis();
                AppConstants.getSelectedValues().setScorePlayer1(scorePlayer1);
                AppConstants.getSelectedValues().setScorePlayer2(scorePlayer2);
                AppConstants.getSelectedValues().setDuration(duration);
                ret = true;
            }


        } else if (AppConstants.getSelectedValues().getGoalsGame()) {
            double duration = System.currentTimeMillis() - timeStart;
            if (scorePlayer1 + scorePlayer2 == AppConstants.getSelectedValues().getNumOfGoals()) {
                timeEnd = System.currentTimeMillis();
                //// cuvamo na kraju vrednosti skora za stavljanje u bazu
                AppConstants.getSelectedValues().setScorePlayer1(scorePlayer1);
                AppConstants.getSelectedValues().setScorePlayer2(scorePlayer2);
                AppConstants.getSelectedValues().setDuration(duration);
                ret = true;
            }

        }

        return ret;

    }




    public void stopThreadFromRunning() {
        thread.setRunning(false);

    }



    public void killThread() {
        thread.interrupt();
    }

}

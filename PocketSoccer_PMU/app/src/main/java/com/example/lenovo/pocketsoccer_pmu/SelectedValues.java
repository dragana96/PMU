package com.example.lenovo.pocketsoccer_pmu;

import android.graphics.Bitmap;

public class SelectedValues {

    private Bitmap selectedField; // selektovan teren na kom se igra
    private Bitmap selectedPlayer1; // selektovani timovi koji igraju
    private Bitmap selectedPlayer2;
    private Ball selectedForPlaying; // nije korisceno...

    private Boolean madeMove; // vrednosti koje omogucavaju da u jednom potezu moze samo jednom da se odigra
    private Boolean computerMadeMove;

    private String player1Name; // imena igraca koji trenutno igraju
    private String player2Name;

    private int scorePlayer1;
    private int scorePlayer2;
    private double endOfGame;

    private Boolean multiplayer;
    private Boolean singlePlayer;

    private Boolean timeGame;
    private Boolean goalsGame;

    private double duration;


    private float player1x;
    private float player1y;

    private float player2x;
    private float player2y;

    private float player3x;
    private float player3y;

    private float player4x;
    private float player4y;

    private float player5x;
    private float player5y;

    private float player6x;
    private float player6y;

    private float soccerBallx;
    private float soccerBally;

    private boolean turnPlayer1;
    private boolean turnPlayer2;
    private boolean computerTurn;

    private boolean continueGame;

    private boolean canContinue;

    private long startTime;
    private long elapsedTime;

    private long gameDuration;

    private Bitmap continueField;

    private int numOfGoals;
    private int secondsToPlay;

    private int gameSpeed;
    private int computerSpeed;

    public int getComputerSpeed() {
        return computerSpeed;
    }

    public void setComputerSpeed(int computerSpeed) {
        this.computerSpeed = computerSpeed;
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public int getNumOfGoals() {
        return numOfGoals;
    }

    public void setNumOfGoals(int numOfGoals) {
        this.numOfGoals = numOfGoals;
    }

    public int getSecondsToPlay() {
        return secondsToPlay;
    }

    public void setSecondsToPlay(int secondsToPlay) {
        this.secondsToPlay = secondsToPlay;
    }

    public Bitmap getContinueField() {
        return continueField;
    }

    public void setContinueField(Bitmap continueField) {
        this.continueField = continueField;
    }

    public long getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(long gameDuration) {
        this.gameDuration = gameDuration;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isCanContinue() {
        return canContinue;
    }

    public void setCanContinue(boolean canContinue) {
        this.canContinue = canContinue;
    }

    public Boolean getComputerMadeMove() {
        return computerMadeMove;
    }

    public void setComputerMadeMove(Boolean computerMadeMove) {
        this.computerMadeMove = computerMadeMove;
    }

    public Boolean getMultiplayer() {
        return multiplayer;
    }

    public Boolean getSinglePlayer() {
        return singlePlayer;
    }

    public void setMultiplayer(Boolean multiplayer) {
        this.multiplayer = multiplayer;
    }

    public void setSinglePlayer(Boolean singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public float getPlayer1x() {
        return player1x;
    }

    public void setPlayer1x(float player1x) {
        this.player1x = player1x;
    }

    public float getPlayer1y() {
        return player1y;
    }

    public void setPlayer1y(float player1y) {
        this.player1y = player1y;
    }

    public float getPlayer2x() {
        return player2x;
    }

    public void setPlayer2x(float player2x) {
        this.player2x = player2x;
    }

    public float getPlayer2y() {
        return player2y;
    }

    public void setPlayer2y(float player2y) {
        this.player2y = player2y;
    }

    public float getPlayer3x() {
        return player3x;
    }

    public void setPlayer3x(float player3x) {
        this.player3x = player3x;
    }

    public float getPlayer3y() {
        return player3y;
    }

    public void setPlayer3y(float player3y) {
        this.player3y = player3y;
    }

    public float getPlayer4x() {
        return player4x;
    }

    public void setPlayer4x(float player4x) {
        this.player4x = player4x;
    }

    public float getPlayer4y() {
        return player4y;
    }

    public void setPlayer4y(float player4y) {
        this.player4y = player4y;
    }

    public float getPlayer5x() {
        return player5x;
    }

    public void setPlayer5x(float player5x) {
        this.player5x = player5x;
    }

    public float getPlayer5y() {
        return player5y;
    }

    public void setPlayer5y(float player5y) {
        this.player5y = player5y;
    }

    public float getPlayer6x() {
        return player6x;
    }

    public void setPlayer6x(float player6x) {
        this.player6x = player6x;
    }

    public float getPlayer6y() {
        return player6y;
    }

    public void setPlayer6y(float player6y) {
        this.player6y = player6y;
    }

    public float getSoccerBallx() {
        return soccerBallx;
    }

    public void setSoccerBallx(float soccerBallx) {
        this.soccerBallx = soccerBallx;
    }

    public float getSoccerBally() {
        return soccerBally;
    }

    public void setSoccerBally(float soccerBally) {
        this.soccerBally = soccerBally;
    }

    public boolean isTurnPlayer1() {
        return turnPlayer1;
    }

    public void setTurnPlayer1(boolean turnPlayer1) {
        this.turnPlayer1 = turnPlayer1;
    }

    public boolean isTurnPlayer2() {
        return turnPlayer2;
    }

    public void setTurnPlayer2(boolean turnPlayer2) {
        this.turnPlayer2 = turnPlayer2;
    }

    public boolean isComputerTurn() {
        return computerTurn;
    }

    public void setComputerTurn(boolean computerTurn) {
        this.computerTurn = computerTurn;
    }

    public SelectedValues() {
        selectedField = AppConstants.getAllFields().get(0);
        selectedPlayer1= AppConstants.getAllFlags().get(0);
        selectedPlayer2= AppConstants.getAllFlags().get(0);
        madeMove = false;
        computerMadeMove = false;
        goalsGame = true;
        timeGame = false;

        scorePlayer1 = 0; // potrebno za cuvanje u bazi
        scorePlayer2 = 0;
        endOfGame = 0;
        duration = 0;

        turnPlayer1 = true;
        turnPlayer2 = false;
        continueGame = false;
        canContinue = false;

        secondsToPlay = 2;
        numOfGoals = 3;

        gameSpeed = 25;
        computerSpeed = 10;
    }

    public boolean isContinueGame() {
        return continueGame;
    }

    public void setContinueGame(boolean continueGame) {
        this.continueGame = continueGame;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setEndOfGame(double endOfGame) {
        this.endOfGame = endOfGame;
    }

    public double getEndOfGame() {
        return endOfGame;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public Boolean getTimeGame() {
        return timeGame;
    }

    public void setTimeGame(Boolean timeGame) {
        this.timeGame = timeGame;
    }

    public void setGoalsGame(Boolean goalsGame) {
        this.goalsGame = goalsGame;
    }

    public Boolean getGoalsGame() {
        return goalsGame;
    }

    public Boolean getMadeMove() {
        return madeMove;
    }

    public void setMadeMove(Boolean madeMove) {
        this.madeMove = madeMove;
    }

    public Ball getSelectedForPlaying() {
        return selectedForPlaying;
    }

    public void setSelectedForPlaying(Ball selectedForPlaying) {
        this.selectedForPlaying = selectedForPlaying;
    }

    public Bitmap getSelectedField() {
        return selectedField;
    }

    public void setSelectedField(Bitmap selectedField) {
        this.selectedField = selectedField;
    }

    public Bitmap getSelectedPlayer1() {
        return selectedPlayer1;
    }

    public void setSelectedPlayer1(Bitmap selectedPlayer1) {
        this.selectedPlayer1 = selectedPlayer1;
    }

    public Bitmap getSelectedPlayer2() {
        return selectedPlayer2;
    }

    public void setSelectedPlayer2(Bitmap selectedPlayer2) {
        this.selectedPlayer2 = selectedPlayer2;
    }
}


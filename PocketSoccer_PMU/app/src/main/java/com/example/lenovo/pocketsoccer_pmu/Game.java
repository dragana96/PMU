package com.example.lenovo.pocketsoccer_pmu;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "game_table")
public class Game {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private double time;
    private String player1;
    private String player2;
    private int score1;
    private int score2;


    public Game( String player1, String player2, int score1, int score2, double time) {
        this.player1= player1;
        this.player2= player2;
        this.score1 = score1;
        this.score2 = score2;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getTime() {
        return time;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

}

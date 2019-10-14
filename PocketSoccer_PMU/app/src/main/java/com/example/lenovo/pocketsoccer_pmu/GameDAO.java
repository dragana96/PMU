package com.example.lenovo.pocketsoccer_pmu;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GameDAO {

    @Insert
    void insert(Game game);



    @Query("SELECT  *, (SELECT COUNT(*) FROM game_table WHERE (player1 = A.player1 AND player2 = A.player2 AND score1 > score2)" +
            " OR (player1 = A.player2 AND player2 = A.player1 AND score2 > score1)) as score1, " +
            "(SELECT COUNT(*) FROM game_table WHERE (player1 = A.player1 AND player2 = A.player2 AND score2 > score1) " +
            "OR (player1 = A.player2 AND player2 = A.player1 AND score1 > score2)) AS score2 " +
            "FROM (SELECT DISTINCT CASE WHEN player1 > player2 THEN player1 ELSE player2 END as player1, CASE WHEN player1 > player2 THEN player2 ELSE player1 END AS player2 FROM game_table) AS A")
    LiveData<List<AfterGameInfo>> getAllGamesInfo();


    // dohvata sve igre sa specificirana dva igraca
    @Query("SELECT * FROM game_table WHERE (player1 = :player1Name AND player2 = :player2Name) OR (player1 = :player2Name AND player2 = :player1Name) ")
    LiveData<List<Game>> getAllGamesForTwoPlayers(String player1Name, String player2Name);

    @Query("DELETE FROM game_table")
    void deleteAllGames();

    // broj pobeda
    @Query("SELECT COUNT(*) FROM game_table WHERE ((player1 = :player1 AND player2 = :player2 AND score2 > score2) OR (player1 = :player2 AND player2 = :player1 AND score2 > score1))")
    LiveData<Integer> getWins(String player1, String player2);
}

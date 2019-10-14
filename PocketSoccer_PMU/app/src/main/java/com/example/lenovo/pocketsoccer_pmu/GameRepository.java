package com.example.lenovo.pocketsoccer_pmu;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class GameRepository {

    private GameDAO gameDAO;
    private LiveData<List<AfterGameInfo>> allGames;


    public GameRepository(Application application){

        GameDatabase instance = GameDatabase.getInstance(application);
        gameDAO = instance.gameDAO();
        allGames = gameDAO.getAllGamesInfo();

    }




    public void insert(Game game) {
        new InsertAsyncTask(gameDAO).execute(game);
    }


    // async pri umetanju, brisanju, azuriranju
    private static class InsertAsyncTask extends AsyncTask<Game, Void, Void>{

        private GameDAO gameDAO;

        private InsertAsyncTask(GameDAO gameDAO){
            this.gameDAO = gameDAO;
        }
        @Override
        protected Void doInBackground(Game... games) {
            gameDAO.insert(games[0]);
            return null;
        }
    }


    public void deleteAllGames() {
        new deleteAllGamesAsyncTask(gameDAO).execute();
    }

    private static class deleteAllGamesAsyncTask extends AsyncTask<Void, Void, Void>{
        private GameDAO gameDAO;

        private deleteAllGamesAsyncTask(GameDAO gameDAO){
            this.gameDAO = gameDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            gameDAO.deleteAllGames();
            return null;
        }
    }





    public LiveData<Integer> getWins(String player, String opponent) {
        return gameDAO.getWins(player, opponent);
    }

    public LiveData<List<AfterGameInfo>> getAllGames() {
        return allGames;
    }

    public LiveData<List<Game>> getAllGamesForTwoPlayers(String player1, String player2) {
        return gameDAO.getAllGamesForTwoPlayers(player1, player2);
    }
}

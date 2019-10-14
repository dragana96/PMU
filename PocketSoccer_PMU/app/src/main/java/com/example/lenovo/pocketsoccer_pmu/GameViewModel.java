package com.example.lenovo.pocketsoccer_pmu;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class GameViewModel extends AndroidViewModel {
    private GameRepository gameRepository;
    private LiveData<List<AfterGameInfo>> allGames;

    public GameViewModel(@NonNull Application application) {
        super(application);
        gameRepository = new GameRepository(application);
        allGames = gameRepository.getAllGames();
    }

    public void insert(Game game) {
        gameRepository.insert(game);
    }

    public void deleteAllGames() {
        gameRepository.deleteAllGames();
    }

    public LiveData<List<AfterGameInfo>> getAllGames() {
        return allGames;
    }

    public LiveData<Integer> getWins(String player, String opponent) {
        return gameRepository.getWins(player, opponent);
    }

    public LiveData<List<Game>> getAllGamesForTwoPlayers(String player1, String player2) {
        return gameRepository.getAllGamesForTwoPlayers(player1, player2);
    }
}

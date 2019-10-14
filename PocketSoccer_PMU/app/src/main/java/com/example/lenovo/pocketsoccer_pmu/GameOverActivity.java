package com.example.lenovo.pocketsoccer_pmu;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class GameOverActivity extends AppCompatActivity {

    private GameViewModel gameViewModel;
    private String player1, player2;
    private int score1, score2;
    private double time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final GameAdapter adapter = new GameAdapter();
        recyclerView.setAdapter(adapter);

        player1 = AppConstants.getSelectedValues().getPlayer1Name();
        player2 = AppConstants.getSelectedValues().getPlayer2Name();
        score1 = AppConstants.getSelectedValues().getScorePlayer1();
        score2 = AppConstants.getSelectedValues().getScorePlayer2();
        time = AppConstants.getSelectedValues().getDuration();

        Game game = new Game(player1,player2, score1,score2, time);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.insert(game);
        gameViewModel.getAllGamesForTwoPlayers(player1, player2).observe(this, new Observer<List<Game>>() {

            @Override
            public void onChanged(@Nullable List<Game> games) {
                adapter.setGames(games);
                Toast.makeText(GameOverActivity.this, "onChanged" + "p:" + player1 + "p1: + " + player2, Toast.LENGTH_SHORT ).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.finish();
        startActivity(new Intent(this, StartActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }
}

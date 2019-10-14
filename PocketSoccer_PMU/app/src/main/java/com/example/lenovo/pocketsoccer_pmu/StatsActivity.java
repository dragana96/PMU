package com.example.lenovo.pocketsoccer_pmu;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private GameViewModel gameViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        RecyclerView recyclerView = findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StatisticsAdapter adapter = new StatisticsAdapter();
        recyclerView.setAdapter(adapter);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.getAllGames().observe(this, new Observer<List<AfterGameInfo>>() {

            @Override
            public void onChanged(@Nullable List<AfterGameInfo> games) {
                // adapter.setNotes(notes);
                adapter.setGames(games);
                Toast.makeText(StatsActivity.this, "onChanged", Toast.LENGTH_SHORT ).show();
            }
        });
    }
}

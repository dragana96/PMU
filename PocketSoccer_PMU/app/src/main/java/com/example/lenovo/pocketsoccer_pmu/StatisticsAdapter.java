package com.example.lenovo.pocketsoccer_pmu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsHolder> {

    List<AfterGameInfo>games = new ArrayList<>();

    @NonNull
    @Override
    public StatisticsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.statistics_item, viewGroup, false);

        return new StatisticsAdapter.StatisticsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsHolder statisticsHolder, int i) {

        AfterGameInfo currentGame = games.get(i);
        statisticsHolder.player1.setText(currentGame.getPlayer1());
        statisticsHolder.player2.setText(currentGame.getPlayer2());
        statisticsHolder.wins1.setText(String.valueOf(currentGame.getScore1()));
        statisticsHolder.wins2.setText(String.valueOf(currentGame.getScore2()));
        //statisticsHolder.time.setText(currentTimeAsString(currentGame.getTime()));

    }


    @Override
    public int getItemCount() {
        return games.size();
    }

    public void setGames(List<AfterGameInfo> games){
        this.games = games;
        notifyDataSetChanged();
    }

    class StatisticsHolder extends RecyclerView.ViewHolder {
        private TextView player1;
        private TextView player2;
        private TextView wins1;
        private TextView wins2;

        public StatisticsHolder(@NonNull View itemView) {
            super(itemView);

            player1 = itemView.findViewById(R.id.player1);
            player2 = itemView.findViewById(R.id.player2);
            wins1 = itemView.findViewById(R.id.score1);
            wins2 = itemView.findViewById(R.id.score2);
        }
    }
}

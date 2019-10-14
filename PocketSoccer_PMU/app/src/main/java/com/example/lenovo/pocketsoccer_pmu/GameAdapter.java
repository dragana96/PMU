package com.example.lenovo.pocketsoccer_pmu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameHolder> {

    private List<Game> games = new ArrayList<>();


    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_item, viewGroup, false);

        return new GameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder gameHolder, int i) {

        Game currentGame = games.get(i);
        gameHolder.player1.setText(currentGame.getPlayer1());
        gameHolder.player2.setText(currentGame.getPlayer2());
        gameHolder.score1.setText(String.valueOf(currentGame.getScore1()));
        gameHolder.score2.setText(String.valueOf(currentGame.getScore2()));
        gameHolder.time.setText(currentTimeAsString(currentGame.getTime()));



    }

    public String currentTimeAsString(double time) {
        int min = (int) (time / 1000 / 60);
        int sec = (int) (time / 1000 % 60);
        String minString;
        String secString;
        if (min < 10)
            minString = "0" + min;
        else
            minString = min + "";

        if (sec < 10)
            secString = "0" + sec;
        else
            secString = sec + "";

        return minString + " : " + secString;
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void setGames(List<Game> games){
        this.games = games;
        notifyDataSetChanged();
    }

    class GameHolder extends RecyclerView.ViewHolder{
        private TextView player1;
        private TextView player2;
        private TextView score1;
        private TextView score2;
        private TextView time;


        public GameHolder(@NonNull View itemView) {
            super(itemView);
            player1 = itemView.findViewById(R.id.player1);
            player2 = itemView.findViewById(R.id.player2);
            score1 = itemView.findViewById(R.id.score1);
            score2 = itemView.findViewById(R.id.score2);
            time = itemView.findViewById(R.id.time1);

        }
    }


}

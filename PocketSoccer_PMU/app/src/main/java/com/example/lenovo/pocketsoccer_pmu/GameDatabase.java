package com.example.lenovo.pocketsoccer_pmu;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Game.class}, version = 1)
public abstract class GameDatabase extends RoomDatabase {

    private static GameDatabase instance;

    public abstract GameDAO gameDAO();

    public static synchronized GameDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), GameDatabase.class, "game_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDBAsyncTask(instance).execute();
        }
    };

    private static class populateDBAsyncTask extends AsyncTask<Void, Void, Void>{
        private GameDAO gameDAO;

        private populateDBAsyncTask(GameDatabase db){
            this.gameDAO = db.gameDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            gameDAO.insert(new Game("Dragana", "Milica", 3, 1, 60000));
            gameDAO.insert(new Game("Dragana", "Milica", 3, 2, 61000));
            gameDAO.insert(new Game("Dragana", "Milica", 2, 4, 100));
            gameDAO.insert(new Game("Dragana", "Lara", 3, 2, 100));
            gameDAO.insert(new Game("Milica", "Lara", 1, 2, 100));
            return null;
        }
    }
}

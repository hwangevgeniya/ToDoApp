package com.geektech.taskapplication;

import android.app.Application;

import androidx.room.Room;

import com.geektech.taskapplication.ui.room.AppDatabase;

public class App extends Application {

    private AppDatabase database;
    private static App instance;
    private Prefs prefs;

    public Prefs getPrefs() {
        return prefs;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = new Prefs(this);
        instance = this;
        database = Room
                .databaseBuilder(this, AppDatabase.class,"database")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase(){
        return database;
    }
}


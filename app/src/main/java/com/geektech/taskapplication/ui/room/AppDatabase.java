package com.geektech.taskapplication.ui.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.geektech.taskapplication.ui.models.News;

@Database(entities = {News.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();
}

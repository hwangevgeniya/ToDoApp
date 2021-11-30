package com.geektech.taskapplication.ui.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.geektech.taskapplication.ui.models.News;

import java.util.List;


@Dao
public interface NewsDao {

    @Query("SELECT * FROM news order by time DESC")
    List<News> getAll();

    @Query("SELECT * FROM news order by title ASC")
    List<News> getAllSortedByTitle();

    @Insert
    void insert(News news);

    @Update
    void update(News news);

    @Delete
    void delete(News news);
}

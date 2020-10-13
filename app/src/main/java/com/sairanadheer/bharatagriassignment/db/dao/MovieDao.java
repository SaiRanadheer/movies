package com.sairanadheer.bharatagriassignment.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sairanadheer.bharatagriassignment.vo.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Movie> movies);

    @Query("SELECT * FROM movies LIMIT :limit OFFSET :offset")
    LiveData<List<Movie>> getMovies(int offset, int limit);

    @Query("SELECT * FROM movies LIMIT 1")
    Movie hasMovies();
}

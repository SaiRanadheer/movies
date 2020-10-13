package com.sairanadheer.bharatagriassignment.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.sairanadheer.bharatagriassignment.db.dao.MovieDao;
import com.sairanadheer.bharatagriassignment.vo.Genre;
import com.sairanadheer.bharatagriassignment.vo.Movie;

@Database(entities = {Movie.class, Genre.class}, version = 1, exportSchema = false)
@TypeConverters(GenreConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "app_database";
    private static AppDatabase instance;

    public static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract MovieDao movieDao();
}

package com.sairanadheer.bharatagriassignment.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GenreConverter {
    @TypeConverter
    public static String fromList(List<Integer> genreIds) {
        Gson gson = new Gson();
        return gson.toJson(genreIds);
    }

    @TypeConverter
    public static List<Integer> fromString(String value) {
        Type listType = new TypeToken<List<Integer>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }
}

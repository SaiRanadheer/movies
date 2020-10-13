package com.sairanadheer.bharatagriassignment.api;

import com.sairanadheer.bharatagriassignment.vo.LatestMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("now_playing")
    Call<LatestMovies> getMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);
}

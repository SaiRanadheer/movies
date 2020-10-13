package com.sairanadheer.bharatagriassignment.repo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sairanadheer.bharatagriassignment.api.Service;
import com.sairanadheer.bharatagriassignment.api.ServiceGenerator;
import com.sairanadheer.bharatagriassignment.db.AppDatabase;
import com.sairanadheer.bharatagriassignment.db.dao.MovieDao;
import com.sairanadheer.bharatagriassignment.utils.Constants;
import com.sairanadheer.bharatagriassignment.vo.LatestMovies;
import com.sairanadheer.bharatagriassignment.vo.Movie;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieRepository {

    private final MovieDao movieDao;
    private MutableLiveData<NetworkState> networkState;

    public MovieRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        movieDao = database.movieDao();
        networkState = new MutableLiveData<>();
   }

   public LiveData<List<Movie>> getMovies(Runnable successHandler, Runnable failureHandler, int page) {
       int offset = (page-1) * 20;
        checkIfMoviesCached(successHandler, failureHandler, page, offset);
        return movieDao.getMovies(offset, 20);
   }

    private void checkIfMoviesCached(Runnable successHandler, Runnable failureHandler, int page, int offset) {
        try {
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                if(movieDao.hasMovies() == null) {
                    fetchFromRemote(successHandler, failureHandler, page);
                } else {
                    successHandler.run();
                }
            });
        } catch(Exception e) {

        }
    }

    private void fetchFromRemote(Runnable successHandler, Runnable failureHandler, int page) {
        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            ServiceGenerator.createService(Service.class).getMovies(Constants.API_KEY, Constants.LANGUAGE, page).enqueue(new Callback<LatestMovies>() {
                @Override
                public void onResponse(@NonNull Call<LatestMovies> call, @NonNull Response<LatestMovies> response) {
                    if(response.isSuccessful() && response.code() == 200 && response.body() != null) {
                        networkState.postValue(NetworkState.LOADED);
                        executorService.execute(() -> {
                            movieDao.insert(response.body().getResults());
                            successHandler.run();
                        });

                        executorService.shutdown();
                    } else {
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        failureHandler.run();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LatestMovies> call, @NonNull Throwable t) {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, "API call failure"));
                }
            });
        } catch (Exception e) {

        }
    }
}

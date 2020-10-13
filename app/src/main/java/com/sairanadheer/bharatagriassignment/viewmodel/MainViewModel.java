package com.sairanadheer.bharatagriassignment.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sairanadheer.bharatagriassignment.repo.MovieRepository;
import com.sairanadheer.bharatagriassignment.vo.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;
    private MutableLiveData<Boolean> isLoading;
    private int page = 0;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.isLoading = new MutableLiveData<>();
        MovieRepository repository = new MovieRepository(application);
        this.page++;
        movies = repository.getMovies(this::defaultSuccessHandler, this::defaultFailureHandler, page);
        Log.e("MyTAG", "Movies List");
    }

    public LiveData<List<Movie>> getMovies() {
        isLoading.postValue(true);
        return movies;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return this.isLoading;
    }

    private void defaultSuccessHandler() {
        this.isLoading.postValue(false);
    }

    private void defaultFailureHandler() {
        this.isLoading.postValue(false);
    }
}
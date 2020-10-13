package com.sairanadheer.bharatagriassignment.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sairanadheer.bharatagriassignment.vo.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private List<Movie> mMovies;
    private Context mContext;

    public MoviesAdapter(Context context) {
        this.mContext = context;
    }

    public void setMovies(List<Movie> movies) {
        this.mMovies = movies;
    }

    @NonNull
    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder {
        public MoviesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

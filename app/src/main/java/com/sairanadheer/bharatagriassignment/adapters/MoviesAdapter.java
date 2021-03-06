package com.sairanadheer.bharatagriassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textview.MaterialTextView;
import com.sairanadheer.bharatagriassignment.R;
import com.sairanadheer.bharatagriassignment.databinding.MainFragmentBinding;
import com.sairanadheer.bharatagriassignment.util.Common;
import com.sairanadheer.bharatagriassignment.util.Constants;
import com.sairanadheer.bharatagriassignment.vo.Movie;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private final MainFragmentBinding mMainFragmentBinding;
    private List<Movie> mMovies;
    private Context mContext;
    private static final DateFormat mApiDateFormat = new SimpleDateFormat(Constants.API_DATE_FORMAT, Locale.ROOT);
    private static final DateFormat mDisplayDateFormat = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT, Locale.ROOT);

    public MoviesAdapter(Context context, MainFragmentBinding mainFragmentBinding) {
        this.mContext = context;
        this.mMainFragmentBinding = mainFragmentBinding;
    }

    public void setMovies(List<Movie> movies) {
        this.mMovies = movies;
    }

    @NonNull
    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_card, parent, false);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapterViewHolder holder, int position) {
        try {
            Glide.with(mContext).load(Constants.IMAGE_BASE_URL.concat(mMovies.get(position).getPosterPath())).placeholder(android.R.drawable.progress_indeterminate_horizontal).
                    into(holder.mMoviePoster);


            Date date = mApiDateFormat.parse(mMovies.get(position).getReleaseDate());
            String displayDate;
            if (date != null) {
                displayDate = mDisplayDateFormat.format(date);
                holder.mReleaseDate.setText(displayDate);
            } else {
                holder.mReleaseDate.setText(Constants.COMING_SOON);
            }
            holder.mMovieName.setText(mMovies.get(position).getTitle());
            holder.mMovieCardLayout.setOnClickListener(v -> {
                mMainFragmentBinding.back.setVisibility(View.VISIBLE);
                mMainFragmentBinding.detailsLayout.setVisibility(View.VISIBLE);
                RequestOptions options = new RequestOptions().transform(new FitCenter(), new RoundedCorners(24));
                Glide.with(mContext).load(Constants.ORIGINAL_IMAGE_BASE_URL.concat(mMovies.get(position).getBackdropPath())).apply(options).placeholder(android.R.drawable.progress_indeterminate_horizontal).
                        into(mMainFragmentBinding.backdrop);
                mMainFragmentBinding.nowShowingMovies.setVisibility(View.GONE);
                mMainFragmentBinding.sortIcon.setVisibility(View.GONE);
                mMainFragmentBinding.headerTitle.setText(mMovies.get(position).getTitle());
                mMainFragmentBinding.votingAverage.setText(String.valueOf(mMovies.get(position).getVoteAverage()));
                mMainFragmentBinding.votingCount.setText(String.valueOf(mMovies.get(position).getVoteCount()));
                mMainFragmentBinding.overview.setText(String.valueOf(mMovies.get(position).getOverview()));
                mMainFragmentBinding.releaseDateDetails.setText(String.valueOf(mMovies.get(position).getReleaseDate()));
            });
        } catch (Exception e) {
            Common.showAlert(mContext);
        }
    }

    @Override
    public int getItemCount() {
        return (mMovies == null) ? 0 : mMovies.size();
    }

    static class MoviesAdapterViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView mMoviePoster;
        private MaterialTextView mReleaseDate;
        private MaterialTextView mMovieName;
        private LinearLayoutCompat mMovieCardLayout;
        public MoviesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mMoviePoster = itemView.findViewById(R.id.movie_poster);
            mReleaseDate = itemView.findViewById(R.id.release_date);
            mMovieName = itemView.findViewById(R.id.movie_name);
            mMovieCardLayout = itemView.findViewById(R.id.movie_card_layout);
        }
    }
}

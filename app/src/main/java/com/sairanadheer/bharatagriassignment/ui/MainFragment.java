package com.sairanadheer.bharatagriassignment.ui;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.sairanadheer.bharatagriassignment.R;
import com.sairanadheer.bharatagriassignment.adapters.MoviesAdapter;
import com.sairanadheer.bharatagriassignment.databinding.MainFragmentBinding;
import com.sairanadheer.bharatagriassignment.util.Common;
import com.sairanadheer.bharatagriassignment.util.Constants;
import com.sairanadheer.bharatagriassignment.viewmodel.MainViewModel;
import com.sairanadheer.bharatagriassignment.vo.Movie;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainFragment extends Fragment implements View.OnClickListener {

    private MainViewModel mViewModel;
    private MainFragmentBinding mBinding;
    private MoviesAdapter mAdapter;
    private RecyclerView mNowShowingMovies;
    private FloatingActionButton mSortIcon;
    private List<Movie> mMovies = new ArrayList<>();
    private boolean mSortingFlag = false;
    private MaterialTextView mHeaderTitle;
    private AppCompatImageView mBack;
    private LinearLayoutCompat mDetailsLayout;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = MainFragmentBinding.inflate(inflater, container, false);
        configureElements();
        configureViewListeners();
        return mBinding.getRoot();
    }

    private void configureViewListeners() {
        mSortIcon.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    private void configureElements() {
        mNowShowingMovies = mBinding.nowShowingMovies;
        mSortIcon = mBinding.sortIcon;
        mHeaderTitle = mBinding.headerTitle;
        mBack = mBinding.back;
        mDetailsLayout = mBinding.detailsLayout;
        mNowShowingMovies.setHasFixedSize(true);
        mNowShowingMovies.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        mAdapter = new MoviesAdapter(requireContext(), mBinding);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        observeDatabase();
    }

    private void observeDatabase() {
        mViewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            if(movies != null) mMovies.addAll(movies);
            mAdapter.setMovies(movies);
            mNowShowingMovies.setAdapter(mAdapter);
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mSortIcon.getId()) {

            Dialog sortOptionsDialog = new Dialog(requireContext());
            sortOptionsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            sortOptionsDialog.setContentView(R.layout.sorting_options_dialog);
            sortOptionsDialog.setCancelable(true);
            Objects.requireNonNull(sortOptionsDialog.getWindow()).setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            sortOptionsDialog.getWindow().setGravity(Gravity.BOTTOM);
            sortOptionsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            sortOptionsDialog.setOnShowListener(dialog -> {
                View view = sortOptionsDialog.getWindow().getDecorView();
                ObjectAnimator.ofFloat(view, "translationY", view.getHeight(), 0.0f).start();
            });

            MaterialTextView ascendingReleaseDate = sortOptionsDialog.findViewById(R.id.ascending_release_date);
            MaterialTextView descendingReleaseDate = sortOptionsDialog.findViewById(R.id.descending_release_date );
            MaterialTextView ratingDescending = sortOptionsDialog.findViewById(R.id.rating_descending);
            MaterialTextView ratingAscending = sortOptionsDialog.findViewById(R.id.rating_ascending);
            MaterialTextView reset = sortOptionsDialog.findViewById(R.id.reset);

            descendingReleaseDate.setOnClickListener(v1 -> {
                List<Movie> movies = new ArrayList<>(mMovies);
                Collections.sort(movies, (movie1, movie2) -> {
                    DateFormat dateFormat = new SimpleDateFormat(Constants.API_DATE_FORMAT, Locale.ROOT);
                    Date movie1Date = null;
                    Date movie2Date = null;
                    try {
                        movie1Date = dateFormat.parse(movie1.getReleaseDate());
                        movie2Date = dateFormat.parse(movie2.getReleaseDate());
                    } catch (Exception e) {
                        Common.showAlert(requireContext());
                    }
                    if(movie1Date != null && movie2Date != null && movie1Date.after(movie2Date)) {
                        return -1;
                    }
                    else {
                        return 1;
                    }
                });
                mAdapter.setMovies(movies);
                mNowShowingMovies.setAdapter(mAdapter);
                mSortingFlag = true;
                sortOptionsDialog.dismiss();
            });

            ascendingReleaseDate.setOnClickListener(v2 -> {
                List<Movie> movies = new ArrayList<>(mMovies);
                Collections.sort(movies, (movie1, movie2) -> {
                    DateFormat dateFormat = new SimpleDateFormat(Constants.API_DATE_FORMAT, Locale.ROOT);
                    Date movie1Date = null;
                    Date movie2Date = null;
                    try {
                        movie1Date = dateFormat.parse(movie1.getReleaseDate());
                        movie2Date = dateFormat.parse(movie2.getReleaseDate());
                    } catch (Exception e) {
                        Common.showAlert(requireContext());
                    }
                    if(movie1Date != null && movie2Date != null && movie1Date.after(movie2Date)) {
                        return 1;
                    }
                    else {
                        return -1;
                    }
                });
                mAdapter.setMovies(movies);
                mNowShowingMovies.setAdapter(mAdapter);
                mSortingFlag = true;
                sortOptionsDialog.dismiss();
            });

            ratingAscending.setOnClickListener(v3 -> {
                List<Movie> movies = new ArrayList<>(mMovies);
                Collections.sort(movies, (movie1, movie2) -> Double.compare(movie1.getVoteAverage(), movie2.getVoteAverage()));
                mAdapter.setMovies(movies);
                mNowShowingMovies.setAdapter(mAdapter);
                mSortingFlag = true;
                sortOptionsDialog.dismiss();
            });

            ratingDescending.setOnClickListener(v4 -> {
                List<Movie> movies = new ArrayList<>(mMovies);
                Collections.sort(movies, (movie1, movie2) -> Double.compare(movie2.getVoteAverage(), movie1.getVoteAverage()));
                mAdapter.setMovies(movies);
                mNowShowingMovies.setAdapter(mAdapter);
                mSortingFlag = true;
                sortOptionsDialog.dismiss();
            });

            reset.setOnClickListener(v5 -> {
                if(mSortingFlag) {
                    mAdapter.setMovies(mMovies);
                    mNowShowingMovies.setAdapter(mAdapter);
                    sortOptionsDialog.dismiss();
                }
            });

            sortOptionsDialog.show();
        } else if(v.getId() == mBack.getId()) {
            mHeaderTitle.setText(R.string.header_title);
            mNowShowingMovies.setVisibility(View.VISIBLE);
            mSortIcon.setVisibility(View.VISIBLE);
            mBack.setVisibility(View.GONE);
            mDetailsLayout.setVisibility(View.GONE);
        }
    }
}
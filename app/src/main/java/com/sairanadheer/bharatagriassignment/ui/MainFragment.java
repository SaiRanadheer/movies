package com.sairanadheer.bharatagriassignment.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sairanadheer.bharatagriassignment.adapters.MoviesAdapter;
import com.sairanadheer.bharatagriassignment.databinding.MainFragmentBinding;
import com.sairanadheer.bharatagriassignment.viewmodel.MainViewModel;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private MainFragmentBinding mBinding;
    private MoviesAdapter mAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = MainFragmentBinding.inflate(inflater, container, false);
        configureElements();
        return mBinding.getRoot();
    }

    private void configureElements() {
        mAdapter = new MoviesAdapter(requireContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        observeDatabase();
    }

    private void observeDatabase() {
        mViewModel.getMovies().observe(getViewLifecycleOwner(), movies -> mAdapter.setMovies(movies));
        mViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading) {
                Log.e("MyTAG", "loading");
            } else {
                Log.e("MyTAG", "Loaded");
            }
        });
    }

}
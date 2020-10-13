package com.sairanadheer.bharatagriassignment.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LatestMovies implements Serializable {

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }
}

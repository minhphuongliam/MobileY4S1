package com.example.retrofit2_tmdb.response;

import com.example.retrofit2_tmdb.model.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// This class is for getting Movie list - now playing movies
public class MovieListResponse {
    @SerializedName("total_results")
    @Expose
    private int totalCount;

    @SerializedName("results")
    @Expose
    private List<Movie> movies;

    public int getTotalCount() {
        return totalCount;
    }

    public List<Movie> getMovies(){
        return movies;
    }

    @Override
    public String toString() {
        return "MovieListResponse{" +
                "totalCount=" + totalCount +
                ", movies=" + movies +
                '}';
    }
}

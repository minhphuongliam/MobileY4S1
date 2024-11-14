package com.example.firebasedemo;
import com.example.firebasedemo.Model.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import retrofit2.http.Path;

public interface MovieApi  {
    // Search for Movies by Query
    // https://api.themoviedb.org/3/search/movie?query=Jack+Reacher&api_key=d0dcad5c634786c89fc9db3502f7bfa4
    @GET("3/search/movie")
    Call<MovieListResponse> searchMovie(
            @Query("api_key")String apiKey,
            @Query("query") String query,
            @Query("page") String page
    );

    // Get Movie by Id
    // https://api.themoviedb.org/3/movie/75780?language=en-US&api_key=d0dcad5c634786c89fc9db3502f7bfa4
    @GET("3/movie/{movie_id}?")
    Call<Movie> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

}
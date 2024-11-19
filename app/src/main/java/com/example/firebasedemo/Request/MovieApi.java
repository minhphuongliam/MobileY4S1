package com.example.firebasedemo.Request;
import com.example.firebasedemo.DTO.CreditDTO;
import com.example.firebasedemo.DTO.MovieDTO;
import com.example.firebasedemo.DTO.VideoDTO;
import com.example.firebasedemo.MovieListResponse;

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

    // Get MovieDTO by movie id
    // https://api.themoviedb.org/3/movie/75780?language=en-US&api_key=d0dcad5c634786c89fc9db3502f7bfa4
    @GET("3/movie/{movie_id}?")
    Call<MovieDTO> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    // Get CreditDTO by movie id
    // https://api.themoviedb.org/3/movie/75780/credits?language=en-US&api_key=d0dcad5c634786c89fc9db3502f7bfa4
    @GET("3/movie/{movie_id}/credits?")
    Call<CreditDTO> getCredit(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    // Get VideoDTO by movie id
    // https://api.themoviedb.org/3/movie/1354039/videos?language=en-US&api_key=d0dcad5c634786c89fc9db3502f7bfa4
    @GET("3/movie/{movie_id}/videos?")
    Call<VideoDTO> getVideo(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

}
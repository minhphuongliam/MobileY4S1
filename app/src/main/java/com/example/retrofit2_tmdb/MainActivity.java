package com.example.retrofit2_tmdb;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit2_tmdb.dto.CreditDTO;
import com.example.retrofit2_tmdb.dto.CrewDTO;
import com.example.retrofit2_tmdb.dto.MovieDTO;
import com.example.retrofit2_tmdb.request.MovieApi;
import com.example.retrofit2_tmdb.request.Service;
import com.example.retrofit2_tmdb.response.MovieListResponse;
import com.example.retrofit2_tmdb.utils.TmdbApiConstants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int[] nowShowing = {1376716, 1372737, 1358033, 1354039, 1314450, 1263992, 1369768, 1312078, 1184918, 912649, 698687, 889737, 1079091, 1244492, 947938};
    private static final int[] upComing = {1376716, 1372737, 1358033, 1354039, 1314450, 1263992, 1369768, 1312078, 1184918, 912649, 698687, 889737, 1079091, 1244492, 947938};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log all the now showing movies
                for(int movieId : nowShowing){
                    getMovieDetailsById(movieId);
                    getCreditById(movieId);
                }
            }
        });

    }

    private void getMovieDetailsById(int movie_id){
        MovieApi movieApi = Service.getMovieApi();
        Call<MovieDTO> responsecall = movieApi.getMovie(movie_id, TmdbApiConstants.API_KEY);

        responsecall.enqueue(new Callback<MovieDTO>() {
            @Override
            public void onResponse(Call<MovieDTO> call, Response<MovieDTO> response) {
                if(response.code() == 200){
                    MovieDTO movie = response.body();
                    Log.v("Tag" , "The Response: " + movie);
                }
                else {
                    Log.e("Tag", "Error: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MovieDTO> call, Throwable throwable) {
                Log.e("Tag", "Network failure: " + throwable.getMessage());
            }
        });
    }

    private void getCreditById(int movie_id){
        MovieApi movieApi = Service.getMovieApi();
        Call<CreditDTO> responsecall = movieApi.getCredit(movie_id, TmdbApiConstants.API_KEY);

        responsecall.enqueue(new Callback<CreditDTO>() {
            @Override
            public void onResponse(Call<CreditDTO> call, Response<CreditDTO> response) {
                if(response.code() == 200){
                    CreditDTO credit = response.body();
                    Log.v("Tag" , "The Response: " + credit);
                }
                else {
                    Log.e("Tag", "Error: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CreditDTO> call, Throwable throwable) {
                Log.e("Tag", "Network failure: " + throwable.getMessage());
            }
        });
    }

    private void getRetrofitResponse() {
        MovieApi movieApi = Service.getMovieApi();
        Call<MovieListResponse> responseCall = movieApi.searchMovie(
                TmdbApiConstants.API_KEY,
                "Jack Reacher",
                "1");
        responseCall.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if(response.code() == 200){
                    Log.v("Tag", "the response" + response.body().toString());
                    List<MovieDTO> movies = new ArrayList<>(response.body().getMovies());
                    for(MovieDTO movie : movies){
                        String posterPath = TmdbApiConstants.IMG_URL + movie.getPosterPath();
                        Log.v("Tag", "Poster path: " + posterPath);
                    }
                }
                else {
                    Log.e("Tag", "Error: " + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable throwable) {
                Log.e("Tag", "Network failure: " + throwable.getMessage());
            }
        });
    }

}
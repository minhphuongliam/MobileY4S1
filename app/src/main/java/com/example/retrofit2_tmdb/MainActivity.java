package com.example.retrofit2_tmdb;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit2_tmdb.dto.CreditDTO;
import com.example.retrofit2_tmdb.dto.MovieDTO;
import com.example.retrofit2_tmdb.dto.VideoDTO;
import com.example.retrofit2_tmdb.mapper.MovieMapper;
import com.example.retrofit2_tmdb.model.Movie;
import com.example.retrofit2_tmdb.request.MovieApi;
import com.example.retrofit2_tmdb.request.Service;
import com.example.retrofit2_tmdb.utils.TmdbApiConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int[] nowShowing = {1376716, 1372737, 1358033, 1354039, 1314450, 1263992, 1369768, 1312078, 1184918, 912649, 698687, 889737, 1079091, 1244492, 947938};
    private static final int[] upComming = {1376716, 1372737, 1358033, 1354039, 1314450, 1263992, 1369768, 1312078, 1184918, 912649, 698687, 889737, 1079091, 1244492, 947938};
    private List<Movie> nowShowingMovies = new ArrayList<>();
    private List<Movie> upCommingMoives = new ArrayList<>();

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
                }
            }
        });

    }

    private void getMovieDetailsById(int movieId) {
        MovieApi movieApi = Service.getMovieApi();

        // API calls
        Call<MovieDTO> movieCall = movieApi.getMovie(movieId, TmdbApiConstants.API_KEY);
        Call<CreditDTO> creditCall = movieApi.getCredit(movieId, TmdbApiConstants.API_KEY);
        Call<VideoDTO> videoCall = movieApi.getVideo(movieId, TmdbApiConstants.API_KEY);

        // Step 1: Fetch the main movie details first
        movieCall.enqueue(new Callback<MovieDTO>() {
            @Override
            public void onResponse(Call<MovieDTO> call, Response<MovieDTO> movieResponse) {
                if (movieResponse.code() == 200) {
                    MovieDTO movieDTO = movieResponse.body();

                    // Step 2: Make the creditCall and videoCall in parallel
                    AtomicReference<CreditDTO> creditData = new AtomicReference<>();
                    AtomicReference<VideoDTO> videoData = new AtomicReference<>();

                    CountDownLatch latch = new CountDownLatch(2); // Wait for 2 parallel calls

                    // Call credits API
                    creditCall.enqueue(new Callback<CreditDTO>() {
                        @Override
                        public void onResponse(Call<CreditDTO> call, Response<CreditDTO> response) {
                            if (response.code() == 200) {
                                creditData.set(response.body());
                            } else {
                                Log.e("Tag", "Credit API Error: " + response.errorBody().toString());
                            }
                            latch.countDown();
                        }

                        @Override
                        public void onFailure(Call<CreditDTO> call, Throwable t) {
                            Log.e("Tag", "Credit API Failure: " + t.getMessage());
                            latch.countDown();
                        }
                    });

                    // Call videos API
                    videoCall.enqueue(new Callback<VideoDTO>() {
                        @Override
                        public void onResponse(Call<VideoDTO> call, Response<VideoDTO> response) {
                            if (response.code() == 200) {
                                videoData.set(response.body());
                            } else {
                                Log.e("Tag", "Video API Error: " + response.errorBody().toString());
                            }
                            latch.countDown();
                        }

                        @Override
                        public void onFailure(Call<VideoDTO> call, Throwable t) {
                            Log.e("Tag", "Video API Failure: " + t.getMessage());
                            latch.countDown();
                        }
                    });

                    // Step 3: Combine results once both calls are completed
                    new Thread(() -> {
                        try {
                            latch.await(); // Wait for all API calls to finish

                            CreditDTO creditDTO = creditData.get();
                            VideoDTO videoDTO = videoData.get();

                            // Combine data and map it to the Movie object
                            Movie movie = MovieMapper.mapToMovie(movieDTO, creditDTO, videoDTO);
                            nowShowingMovies.add(movie);

                            Log.v("Tag", "Added movie: " + movie);
                        } catch (InterruptedException e) {
                            Log.e("Tag", "Error waiting for API responses: " + e.getMessage());
                        }
                    }).start();

                } else {
                    Log.e("Tag", "Movie API Error: " + movieResponse.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MovieDTO> call, Throwable t) {
                Log.e("Tag", "Movie API Failure: " + t.getMessage());
            }
        });
    }

}
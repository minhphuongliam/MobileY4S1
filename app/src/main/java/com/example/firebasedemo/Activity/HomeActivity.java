package com.example.firebasedemo.Activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedemo.Adapter.MovieAdapter;
import com.example.firebasedemo.Model.Movie;
import com.example.firebasedemo.MovieApi;
import com.example.firebasedemo.R;
import com.example.firebasedemo.TmdbApiConstants;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private static final int[] nowShowing = {1376716, 1372737, 1358033, 1354039, 1314450, 1263992, 1369768, 1312078, 1184918, 912649, 698687, 889737, 1079091, 1244492, 947938};
    private static final int[] upComing = {1376716, 1372737, 1358033, 1354039, 1314450, 1263992, 1369768, 1312078, 1184918, 912649, 698687, 889737, 1079091, 1244492, 947938};
    private ArrayList<Movie> nowShowingMovies = new ArrayList<>();
    private ArrayList<Movie> upComingMovies = new ArrayList<>();
    private RecyclerView recyclerViewTopMovies, recyclerViewUpcoming;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerViewTopMovies = findViewById(R.id.recyclerViewTopMovies);
        recyclerViewUpcoming = findViewById(R.id.recyclerViewUpcomming);

        recyclerViewTopMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        fetchMovieDetails(nowShowingMovies, "Now Showing");
        fetchMovieDetails(upComingMovies, "Up Coming");
    }

    // Fetch movie details from TMDB API
    private void fetchMovieDetails(ArrayList<Movie> movieList, String category) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TmdbApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);

        for (int movieId : getMovieIds(category)) {
            movieApi.getMovie(movieId, TmdbApiConstants.API_KEY).enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Movie movie = response.body();
                        movieList.add(movie);
                        updateRecyclerView(category, movieList);
                    } else {
                        Toast.makeText(HomeActivity.this, "Failed to fetch movie details", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Error fetching movie details", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Update RecyclerView based on category
    private void updateRecyclerView(String category, ArrayList<Movie> movieList) {
        if (category.equals("Now Showing")) {
            recyclerViewTopMovies.setAdapter(new MovieAdapter(this, movieList));
        } else {
            recyclerViewUpcoming.setAdapter(new MovieAdapter(this, movieList));
        }
    }

    // Fetch movie IDs based on category
    private int[] getMovieIds(String category) {
        if (category.equals("Now Showing")) {
            return nowShowing; // Add more IDs
        } else {
            return upComing; // Add more IDs
        }
    }
}

package com.example.firebasedemo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import androidx.viewpager2.widget.ViewPager2;

import com.example.firebasedemo.Adapter.AdAdapter;
import com.example.firebasedemo.Adapter.MovieAdapter;
import com.example.firebasedemo.DTO.CreditDTO;
import com.example.firebasedemo.DTO.MovieDTO;
import com.example.firebasedemo.DTO.VideoDTO;
import com.example.firebasedemo.Mapper.MovieMapper;
import com.example.firebasedemo.Model.Movie;
import com.example.firebasedemo.Request.MovieApi;
import com.example.firebasedemo.R;
import com.example.firebasedemo.Request.Service;
import com.example.firebasedemo.Utils.TmdbApiConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private static final int[] nowShowing = {1376716, 1372737, 1358033, 1354039, 1314450, 1263992, 1369768, 1312078, 1184918, 912649, 698687, 889737, 1079091, 1244492, 947938};
    private static final int[] upComing = {1376716, 1372737, 1358033, 1354039, 1314450, 1263992, 1369768, 1312078, 1184918, 912649, 698687, 889737, 1079091, 1244492, 947938};
    private ArrayList<Movie> nowShowingMovies = new ArrayList<>();
    private ArrayList<Movie> upComingMovies = new ArrayList<>();
    private List<String> adUrls = Arrays.asList(
            "https://i.ibb.co/8djsyhf/advertisement1.jpg",
            "https://i.ibb.co/3zCqh5x/advertisement2.jpg",
            "https://i.ibb.co/ys7Ykpc/advertisement3.jpg"
    );
    private RecyclerView recyclerViewNowshowing, recyclerViewUpcoming;
    private TextView textEmail;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize ProgressBars
        ProgressBar progressBarNowshowing = findViewById(R.id.progressBarNowshowing);
        ProgressBar progressBarSlider = findViewById(R.id.progressBarSlider);
        ProgressBar progressBarupcomming = findViewById(R.id.progressBarupcomming);

        // Show ProgressBars initially
        progressBarNowshowing.setVisibility(View.VISIBLE);
        progressBarSlider.setVisibility(View.VISIBLE);
        progressBarupcomming.setVisibility(View.VISIBLE);

        // Hide ProgressBars after 3 seconds (3000 milliseconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarNowshowing.setVisibility(View.GONE);
                progressBarSlider.setVisibility(View.GONE);
                progressBarupcomming.setVisibility(View.GONE);
            }
        }, 3000);
        recyclerViewNowshowing = findViewById(R.id.recyclerViewNowshowing);
        recyclerViewUpcoming = findViewById(R.id.recyclerViewUpcomming);
        viewPager2 = findViewById(R.id.viewPager2);

        // thông tin email
        textEmail = findViewById(R.id.textEmail);
        //lấy từ sharedPref rồi lưu
        setEmail();

        //nút đăng xuất
        ImageView logoutIcon = findViewById(R.id.logoutIcon);
        logoutIcon.setOnClickListener(v -> logoutUser());

        setupAdViewPager();

        recyclerViewNowshowing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        fetchMovieDetails(nowShowingMovies, "Now Showing");
        fetchMovieDetails(upComingMovies, "Up Coming");

    }

    private void setupAdViewPager() {
        AdAdapter adAdapter = new AdAdapter(this, adUrls);
        viewPager2.setAdapter(adAdapter);

        // Automatically swipe pages every 5 seconds
        final Handler handler = new Handler();
        Runnable autoSwipe = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager2.getCurrentItem();
                int nextItem = (currentItem + 1) % adAdapter.getItemCount();
                viewPager2.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(autoSwipe, 5000);
    }


    // Fetch movie details from TMDB API
    private void fetchMovieDetails(ArrayList<Movie> movieList, String category) {
        MovieApi movieApi = Service.getMovieApi();
        for (int movieId : getMovieIds(category)) {
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

                                // Add to list and update RecyclerView
                                movieList.add(movie);

                                // Update RecyclerView on the main thread
                                runOnUiThread(() -> updateRecyclerView(category, movieList));

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

    // Update RecyclerView based on category
    // Update RecyclerView based on category
    private void updateRecyclerView(String category, ArrayList<Movie> movieList) {
        // Tạo listener xử lý sự kiện click
        MovieAdapter.OnMovieClickListener listener = (movie, isNowShowing) -> {
            if (isNowShowing) {
                // Xử lý sự kiện khi phim thuộc danh mục "Now Showing"
                // Log thông tin phim được chọn
                Log.v("Tag", "Selected movie: " + movie +" isNowShowing: " + isNowShowing);

                //Toast.makeText(HomeActivity.this, "Now Showing: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
            } else {
                // Xử lý sự kiện khi phim thuộc danh mục "Up Coming"
                Log.v("Tag", "Selected movie: " + movie +" isNowShowing: " + isNowShowing);
                //Toast.makeText(HomeActivity.this, "Up Coming: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
            }
            // Gọi sang movieDetail

            Intent intent = new Intent(HomeActivity.this, MovieDetailActivity.class);
            intent.putExtra("movieId", movie.getMovieId()); // Truyền đối tượng movieId
            Log.v("Tag", "Movie ID: " + movie.getMovieId());
            intent.putExtra("isNowShowing", isNowShowing); // Truyền thông tin danh mục
            startActivity(intent);
        };

        // Truyền listener vào adapter
        if (category.equals("Now Showing")) {
            recyclerViewNowshowing.setAdapter(new MovieAdapter(this, movieList, true, listener));
        } else {
            recyclerViewUpcoming.setAdapter(new MovieAdapter(this, movieList, false, listener));
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
    //phương thức set Email
    private void setEmail()
    {
        try{
            // Tạo key Master nếu chưa có
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            // Đọc EncryptedSharedPreferences
            SharedPreferences encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    "UserPrefs",
                    masterKeyAlias,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            // lấy email từ shared rồi lưu
            String email = encryptedSharedPreferences.getString("fullName",null);

            if(email != null)
            {
                textEmail.setText(email);
            }else
            {
                textEmail.setText("Email");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            textEmail.setText("Email Error");
        }
    }
    // Phương thức logout
    private void logoutUser() {
        try {
            // Tạo MasterKey để mã hóa dữ liệu
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // Lấy EncryptedSharedPreferences
            SharedPreferences encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    "UserPrefs", // Tên file sharedPreferences
                    masterKeyAlias, // Master key
                    this, // Context
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // Mã hóa key
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // Mã hóa value
            );

            // Xóa thông tin người dùng
            SharedPreferences.Editor editor = encryptedSharedPreferences.edit();
            editor.clear(); // Xóa tất cả thông tin người dùng
            editor.apply(); // Lưu thay đổi

            // Thông báo cho người dùng và chuyển hướng về trang login
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            navigateToLogin();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error during logout", Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức chuyển hướng về LoginActivity
    private void navigateToLogin() {
        Intent intent = new Intent(HomeActivity.this, LoginDemoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Xóa lịch sử các Activity phía sau LoginActivity
        startActivity(intent);
        finish();
    }
}

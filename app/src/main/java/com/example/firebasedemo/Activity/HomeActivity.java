package com.example.firebasedemo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

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
    private TextView textEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerViewTopMovies = findViewById(R.id.recyclerViewTopMovies);
        recyclerViewUpcoming = findViewById(R.id.recyclerViewUpcomming);

        // thông tin email
        textEmail = findViewById(R.id.textEmail);
        //lấy từ sharedPref rồi lưu
        setEmail();

        //nút đăng xuất
        ImageView logoutIcon = findViewById(R.id.logoutIcon);
        logoutIcon.setOnClickListener(v -> logoutUser());

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

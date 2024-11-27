package com.example.firebasedemo.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.firebasedemo.DTO.CreditDTO;
import com.example.firebasedemo.DTO.MovieDTO;
import com.example.firebasedemo.DTO.VideoDTO;
import com.example.firebasedemo.Mapper.MovieMapper;
import com.example.firebasedemo.Model.Movie;
import com.example.firebasedemo.R;
import com.example.firebasedemo.Request.MovieApi;
import com.example.firebasedemo.Request.Service;
import com.example.firebasedemo.Utils.TmdbApiConstants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView backgroundPoster, smallPoster, playButton;
    private TextView movieTitle, movieAge, movieRating, directorValue, castValue, genreValue, runtimeValue, releaseDateValue, movieDescription;
    private Button bookNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Find Views
        backgroundPoster = findViewById(R.id.background_poster);
        smallPoster = findViewById(R.id.small_poster);
        movieTitle = findViewById(R.id.movie_title);
        movieAge = findViewById(R.id.movie_age);
        movieRating = findViewById(R.id.movie_rating);
        directorValue = findViewById(R.id.director_value);
        castValue = findViewById(R.id.cast_value);
        genreValue = findViewById(R.id.genre_value);
        runtimeValue = findViewById(R.id.runtime_value);
        releaseDateValue = findViewById(R.id.release_date_value);
        movieDescription = findViewById(R.id.movie_description);
        bookNowButton = findViewById(R.id.book_button);
        playButton = findViewById(R.id.play_button);

        // Trong MovieDetailActivity, khi nhận Intent:
        int movieId = getIntent().getIntExtra("movieId", -1);
        boolean isNowShowing = getIntent().getBooleanExtra("isNowShowing", false);

        if (movieId == -1) {
            Log.e("MovieDetailActivity", "movieId is null!");
            Toast.makeText(this, "Invalid movie ID", Toast.LENGTH_SHORT).show();
            finish(); // Đóng Activity nếu movieId không hợp lệ
            return;
        }
        else
        {
            Log.v("Tag", "movieId  received is " + movieId);
        }


        // Lấy movieId rồi fetch vào
        new Thread(() -> {
            Movie movie = fetchAndMapMovie(movieId);

            if (movie != null) {
                runOnUiThread(() -> {
                    // Cập nhật UI trên luồng chính
                    Log.v("Tag", "Received Movie: " + movie.toString() + " isShowing : " + isNowShowing);  // Log lại thông tin Movie để kiểm tra
                    Log.v("Tag", "Actor " + movie.getActors());
// Kiểm tra lại các trường dữ liệu
                    if (movie != null) {
                        Glide.with(this).load(TmdbApiConstants.IMG_URL + movie.getPosterPath()).into(backgroundPoster);
                        Glide.with(this).load(TmdbApiConstants.SMALL_IMG_URL + movie.getPosterPath()).into(smallPoster);
                        movieTitle.setText(movie.getTitle());

                        // Kiểm tra và cập nhật các trường dữ liệu khác
                        if (movie.isAdult()) {
                            movieAge.setText("Adult only");
                        } else {
                            movieAge.setText("Any ages");
                        }

                        // Kiểm tra nếu giá trị null, sử dụng fallback để tránh null pointer
                        movieRating.setText("Rating: " + (movie.getVoteAvarage() != null ? movie.getVoteAvarage() : 0.0f));

                        // Kiểm tra null và xử lý trường directors, actors, genres
                        directorValue.setText(movie.getDirectors() != null && !movie.getDirectors().isEmpty() ?
                                String.join(", ", movie.getDirectors()) : "N/A");
                        castValue.setText(movie.getActors() != null && !movie.getActors().isEmpty() ?
                                String.join(", ", movie.getActors()) : "N/A");

                        // Đảm bảo genres không phải là null trước khi chuyển thành chuỗi
                        genreValue.setText(movie.getGenres() != null && !movie.getGenres().isEmpty() ?
                                String.join(", ", movie.getGenres()) : "N/A");

                        runtimeValue.setText(movie.getRuntime() != null ? movie.getRuntime() + " mins" : "N/A");
                        releaseDateValue.setText(movie.getReleaseDate() != null ? movie.getReleaseDate() : "N/A");
                        movieDescription.setText(movie.getMovieOverview() != null ? movie.getMovieOverview() : "No description available");

                        if (isNowShowing) {
                            bookNowButton.setVisibility(View.VISIBLE);
                        } else {
                            bookNowButton.setVisibility(View.GONE);
                        }

                        // Xử lý sự kiện click "Book Now"
                        bookNowButton.setOnClickListener(v -> {
                            Toast.makeText(MovieDetailActivity.this, "Booking " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                        });
                    }
                    // ấn nút play, truyền url để chạy movie
                    playButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MovieDetailActivity.this, TrailerActivity.class);
                            intent.putExtra("VIDEO_ID", movie.getTrailerPath()); // Truyền trailer url
                            startActivity(intent);
                        }
                    });
                });
            } else {
                runOnUiThread(() -> {
                    // Hiển thị lỗi nếu không lấy được dữ liệu
                    Toast.makeText(this, "Failed to fetch movie details", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();



    }

    public Movie fetchAndMapMovie(int movieId) {
        MovieApi movieApi = Service.getMovieApi();
        Movie movie = null; // Biến để lưu kết quả trả về

        try {
            // Gọi Movie API đồng bộ
            Response<MovieDTO> movieResponse = movieApi.getMovie(movieId, TmdbApiConstants.API_KEY).execute();
            if (movieResponse.isSuccessful() && movieResponse.body() != null) {
                MovieDTO movieDTO = movieResponse.body();

                // Gọi Credit API đồng bộ
                Response<CreditDTO> creditResponse = movieApi.getCredit(movieId, TmdbApiConstants.API_KEY).execute();
                CreditDTO creditDTO = creditResponse.isSuccessful() ? creditResponse.body() : null;

                // Gọi Video API đồng bộ
                Response<VideoDTO> videoResponse = movieApi.getVideo(movieId, TmdbApiConstants.API_KEY).execute();
                VideoDTO videoDTO = videoResponse.isSuccessful() ? videoResponse.body() : null;

                // Map dữ liệu thành Movie object
                movie = MovieMapper.mapToMovie(movieDTO, creditDTO, videoDTO);
            } else {
                Log.e("fetchAndMapMovie", "Movie API Error: " + (movieResponse.errorBody() != null ? movieResponse.errorBody().string() : "Unknown error"));
            }
        } catch (Exception e) {
            Log.e("fetchAndMapMovie", "Error during API calls: " + e.getMessage(), e);
        }

        return movie; // Trả về đối tượng Movie (có thể null nếu xảy ra lỗi)
    }

}

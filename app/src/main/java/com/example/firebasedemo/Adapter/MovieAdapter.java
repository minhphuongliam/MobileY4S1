package com.example.firebasedemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasedemo.Model.Movie;
import com.example.firebasedemo.R;
import com.example.firebasedemo.Utils.TmdbApiConstants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;
    private OnMovieClickListener listener; // Listener cho sự kiện click
    private boolean isNowShowing; // đánh dấu là isNowShowing hay là upComming

    // Constructor
    // thêm mới listener và isNowShowing?
    public MovieAdapter(Context context, List<Movie> movieList, boolean isNowShowing, OnMovieClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
        this.isNowShowing = isNowShowing;
    }
    // Setter cho listener
    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view, listener, movieList, isNowShowing); // Truyền listener vào ViewHolder
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        // Bind movie title
        holder.movieTitle.setText(movie.getTitle());

        // Load movie poster using Glide
        String imageUrl = TmdbApiConstants.SMALL_IMG_URL + movie.getPosterPath();
        Glide.with(context)
                .load(imageUrl)
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;  // thêm để tránh null Exception
    }

    //interface để xử lý sự kiện click
    // truyền thông tin movie, có phải là now Showing hay không để hiện nút Booknow
    public interface OnMovieClickListener {
        void onMovieClick(Movie movie, boolean isNowShowing);
    }
    // ViewHolder class
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        ImageView moviePoster;

        public MovieViewHolder(View itemView, OnMovieClickListener listener, List<Movie> movieList, boolean isNowShowing) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            moviePoster = itemView.findViewById(R.id.moviePoster);

            // Xử lý sự kiện click
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Movie clickedMovie = movieList.get(position); // Lấy Movie tại vị trí
                        listener.onMovieClick(clickedMovie, isNowShowing); // Truyền thêm dữ liệu
                    }
                }
            });
        }

    }
}

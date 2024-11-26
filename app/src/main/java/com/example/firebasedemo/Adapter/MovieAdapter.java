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

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;

    // Constructor
    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
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
        return movieList.size();
    }

    // ViewHolder class
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        ImageView moviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            moviePoster = itemView.findViewById(R.id.moviePoster);
        }
    }
}

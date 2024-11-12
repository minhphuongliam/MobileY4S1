package com.example.firebasedemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable {
    @SerializedName("title")
    private String title;

    @SerializedName("id")
    private Integer movieId;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("vote_average")
    private Float voteAvarage;

    @SerializedName("overview")
    private String movieOverview;

    public Movie(String title, Integer movieId, boolean adult, String originalTitle, String posterPath, String releaseDate, List<Genre> genres, Float voteAvarage, String movieOverview) {
        this.title = title;
        this.movieId = movieId;
        this.adult = adult;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.voteAvarage = voteAvarage;
        this.movieOverview = movieOverview;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        if (in.readByte() == 0) {
            movieId = null;
        } else {
            movieId = in.readInt();
        }
        adult = in.readByte() != 0;
        originalTitle = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        if (in.readByte() == 0) {
            voteAvarage = null;
        } else {
            voteAvarage = in.readFloat();
        }
        movieOverview = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        if (movieId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(movieId);
        }
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(originalTitle);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        if (voteAvarage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(voteAvarage);
        }
        dest.writeString(movieOverview);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Float getVoteAvarage() {
        return voteAvarage;
    }

    public void setVoteAvarage(Float voteAvarage) {
        this.voteAvarage = voteAvarage;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", movieId=" + movieId +
                ", adult=" + adult +
                ", originalTitle='" + originalTitle + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", genres=" + genres +
                ", voteAvarage=" + voteAvarage +
                ", movieOverview='" + movieOverview + '\'' +
                '}';
    }
}
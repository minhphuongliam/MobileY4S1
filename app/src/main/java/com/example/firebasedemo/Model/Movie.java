package com.example.firebasedemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Movie implements Parcelable {
    private String title;
    private Integer movieId;
    private boolean adult;
    private String originalTitle;
    private String posterPath;
    private String backdropPath;
    private String trailerPath;
    private String releaseDate;
    private Integer runtime;
    private List<String> genres;
    private Float voteAvarage;
    private String movieOverview;
    private List<String> directors;
    private List<String> actors;

    // Constructor, các getter và setter

    public Movie() {
    }

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

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getTrailerPath() {
        return trailerPath;
    }

    public void setTrailerPath(String trailerPath) {
        this.trailerPath = trailerPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
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

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(movieId != null ? movieId : -1); // Null check for Integer
        dest.writeByte((byte) (adult ? 1 : 0)); // Boolean to byte
        dest.writeString(originalTitle);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(trailerPath);
        dest.writeString(releaseDate);
        dest.writeInt(runtime != null ? runtime : -1); // Null check for Integer
        dest.writeStringList(genres);
        dest.writeFloat(voteAvarage != null ? voteAvarage : -1.0f); // Null check for Float
        dest.writeString(movieOverview);
        dest.writeStringList(directors);
        dest.writeStringList(actors);
    }

    protected Movie(Parcel in) {
        title = in.readString();
        movieId = in.readInt() != -1 ? in.readInt() : null; // Null check for Integer
        adult = in.readByte() != 0;
        originalTitle = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        trailerPath = in.readString();
        releaseDate = in.readString();
        runtime = in.readInt(); // Null check for Integer
        genres = in.createStringArrayList();
        voteAvarage = in.readFloat(); // Null check for Float
        movieOverview = in.readString();
        directors = in.createStringArrayList();
        actors = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", movieId=" + movieId +
                ", adult=" + adult +
                ", originalTitle='" + originalTitle + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", trailerPath='" + trailerPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", runtime=" + runtime +
                ", genres=" + genres +
                ", voteAvarage=" + voteAvarage +
                ", movieOverview='" + movieOverview + '\'' +
                ", directors=" + directors +
                ", actors=" + actors +
                '}';
    }
}

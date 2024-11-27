package com.example.firebasedemo.DTO;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.firebasedemo.Model.Genre;
import com.example.firebasedemo.Model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieDTO implements Parcelable {
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

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("runtime")
    private Integer runtime;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("genreDTOS")
    private List<GenreDTO> genreDTOS;

    @SerializedName("vote_average")
    private Float voteAvarage;

    @SerializedName("overview")
    private String movieOverview;


    public MovieDTO(String title, Integer movieId, boolean adult, String originalTitle, String posterPath, String backdropPath, Integer runtime, String releaseDate, List<GenreDTO> genreDTOS, Float voteAvarage, String movieOverview) {
        this.title = title;
        this.movieId = movieId;
        this.adult = adult;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.genreDTOS = genreDTOS;
        this.voteAvarage = voteAvarage;
        this.movieOverview = movieOverview;
    }
    /* Phương thức chuyển đổi từ đối tượng Movie sang MovieDTO.
            */
    public static MovieDTO fromMovie(Movie movie) {
        // Chuyển đổi List<String> (danh sách tên thể loại) sang List<GenreDTO>
        List<GenreDTO> genreDTOList = new ArrayList<>();
        for (String genreName : movie.getGenres()) {
            GenreDTO genreDTO = new GenreDTO();
            genreDTO.setName(genreName);  // Gán tên thể loại từ List<String> vào GenreDTO
            genreDTOList.add(genreDTO);
        }
        // Trả về đối tượng MovieDTO với List<GenreDTO>
        return new MovieDTO(
                movie.getTitle(),
                movie.getMovieId(),
                movie.isAdult(),
                movie.getOriginalTitle(),
                movie.getPosterPath(),
                movie.getBackdropPath(),
                movie.getRuntime(),
                movie.getReleaseDate(),
                genreDTOList,  // Truyền List<GenreDTO> đã chuyển đổi
                movie.getVoteAvarage(),
                movie.getMovieOverview()
        );
    }

    protected MovieDTO(Parcel in) {
        title = in.readString();
        if (in.readByte() == 0) {
            movieId = null;
        } else {
            movieId = in.readInt();
        }
        adult = in.readByte() != 0;
        originalTitle = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        if (in.readByte() == 0) {
            runtime = null;
        } else {
            runtime = in.readInt();
        }
        releaseDate = in.readString();
        if (in.readByte() == 0) {
            voteAvarage = null;
        } else {
            voteAvarage = in.readFloat();
        }
        movieOverview = in.readString();
    }

    public static final Creator<MovieDTO> CREATOR = new Creator<MovieDTO>() {
        @Override
        public MovieDTO createFromParcel(Parcel in) {
            return new MovieDTO(in);
        }

        @Override
        public MovieDTO[] newArray(int size) {
            return new MovieDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        if (movieId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(movieId);
        }
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(originalTitle);
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        if (runtime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(runtime);
        }
        parcel.writeString(releaseDate);
        if (voteAvarage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(voteAvarage);
        }
        parcel.writeString(movieOverview);
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "title='" + title + '\'' +
                ", movieId=" + movieId +
                ", adult=" + adult +
                ", originalTitle='" + originalTitle + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", runtime=" + runtime +
                ", releaseDate='" + releaseDate + '\'' +
                ", genreDTOS=" + genreDTOS +
                ", voteAvarage=" + voteAvarage +
                ", movieOverview='" + movieOverview + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<GenreDTO> getGenreDTOS() {
        return genreDTOS;
    }

    public Float getVoteAvarage() {
        return voteAvarage;
    }

    public String getMovieOverview() {
        return movieOverview;
    }
}


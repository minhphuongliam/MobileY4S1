package com.example.firebasedemo.Mapper;

import com.example.firebasedemo.DTO.CastDTO;
import com.example.firebasedemo.DTO.CreditDTO;
import com.example.firebasedemo.DTO.CrewDTO;
import com.example.firebasedemo.DTO.GenreDTO;
import com.example.firebasedemo.DTO.MovieDTO;
import com.example.firebasedemo.DTO.VideoDTO;
import com.example.firebasedemo.DTO.VideoResultDTO;
import com.example.firebasedemo.Model.Movie;

import java.util.ArrayList;
import java.util.List;


public class MovieMapper {
    private static final int MAX_ACTORS = 5;
    private static final int MAX_DIRECTORS = 1;

    public static Movie mapToMovie(MovieDTO movieDTO, CreditDTO creditDTO, VideoDTO videoDTO) {
        Movie movie = new Movie();

        // Map fields from MovieDTO
        movie.setTitle(movieDTO.getTitle());
        movie.setMovieId(movieDTO.getMovieId());
        movie.setAdult(movieDTO.isAdult());
        movie.setOriginalTitle(movieDTO.getOriginalTitle());
        movie.setPosterPath(movieDTO.getPosterPath());
        movie.setBackdropPath(movieDTO.getBackdropPath());
        movie.setRuntime(movieDTO.getRuntime());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setVoteAvarage(movieDTO.getVoteAvarage());
        movie.setMovieOverview(movieDTO.getMovieOverview());


        // Filter first 5 actors
        if (creditDTO != null && creditDTO.getCastDTOS() != null) {
            List<String> topActors = new ArrayList<>();
            for (CastDTO cast : creditDTO.getCastDTOS()) {
                topActors.add(cast.getName());
                if (topActors.size() >= MAX_ACTORS) { // Stop after collecting MAX_ACTORS actors
                    break;
                }
            }
            movie.setActors(topActors);
        }

        // Set the genres
        if(movieDTO.getGenreDTOS() != null){
            List<String> genres = new ArrayList<>();
            for(GenreDTO genre : movieDTO.getGenreDTOS()){
                genres.add(genre.getName());
            }
            movie.setGenres(genres);
        }

        // Find the first director
        if (creditDTO != null && creditDTO.getCrewDTOS() != null) {
            List<String> directors = new ArrayList<>();
            for (CrewDTO crew : creditDTO.getCrewDTOS()) {
                if ("Director".equalsIgnoreCase(crew.getJob())) {
                    directors.add(crew.getName());
                    if (directors.size() >= MAX_DIRECTORS) { // Stop after finding MAX_DIRECTORS director
                        break;
                    }
                }
            }
            movie.setDirectors(directors);
        }

        // Find the first trailer
        if (videoDTO != null && videoDTO.getResults() != null) {
            List<String> result = new ArrayList<>();
            for (VideoResultDTO videoResult : videoDTO.getResults()) {
                if ("Trailer".equalsIgnoreCase(videoResult.getType())) {
                    movie.setTrailerPath(videoResult.getKey());
                    break;
                }
            }
        }

        return movie;
    }
}

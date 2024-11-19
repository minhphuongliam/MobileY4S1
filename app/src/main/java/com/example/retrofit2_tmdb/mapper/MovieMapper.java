package com.example.retrofit2_tmdb.mapper;

import com.example.retrofit2_tmdb.dto.CreditDTO;
import com.example.retrofit2_tmdb.dto.MovieDTO;
import com.example.retrofit2_tmdb.dto.VideoDTO;
import com.example.retrofit2_tmdb.dto.VideoResultDTO;
import com.example.retrofit2_tmdb.model.Movie;
import com.example.retrofit2_tmdb.dto.CastDTO;
import com.example.retrofit2_tmdb.dto.CrewDTO;

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

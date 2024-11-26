package com.example.firebasedemo;

import com.example.firebasedemo.Model.Movie;

import java.util.ArrayList;

/*This class defines a new data type which called Movies.
 * This Movies data type used for calling the GET methods of the created api interface, and for the responses.*/
public class Movies {

    private String stat;
    private String total;
    private ArrayList<Movie> results;

    public Movies(String stat, String total, ArrayList<Movie> results) {
        this.stat = stat;
        this.total = total;
        this.results = results;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Movie> getMovies() {
        return results;
    }

    public void setMovies(ArrayList<Movie> results) {
        this.results = results;
    }
}


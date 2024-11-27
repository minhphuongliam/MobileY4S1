package com.example.firebasedemo.Model;

import java.io.Serializable;
import java.util.List;

public class MovieRoom implements Serializable {
    private Screening screening;
    private List<Seat> seats;

    public MovieRoom() {
    }

    public MovieRoom(Screening screening, List<Seat> seats) {
        this.screening = screening;
        this.seats = seats;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
package com.example.firebasetest;

import java.util.Date;

public class ScreeningDTO {
    private String movieID;
    private String roomID;
    private Date time;

    public ScreeningDTO() {
    }

    public ScreeningDTO(String movieID, String roomID, Date time) {
        this.movieID = movieID;
        this.roomID = roomID;
        this.time = time;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

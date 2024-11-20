package com.example.firebasetest.Model;

import com.google.type.DateTime;

import java.io.Serializable;
import java.util.Date;

public class Screening implements Serializable {
    private String id;
    private String movieID;
    private String roomID;
    private Date time;

    public Screening() {
    }

    public Screening(String id, String movieID, String roomID, Date time) {
        this.id = id;
        this.movieID = movieID;
        this.roomID = roomID;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

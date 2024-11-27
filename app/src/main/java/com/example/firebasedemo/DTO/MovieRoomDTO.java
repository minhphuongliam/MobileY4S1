package com.example.firebasedemo.DTO;

import java.util.Map;

public class MovieRoomDTO {
    private String screeningID;
    private Map<String,String> seats;

    public MovieRoomDTO() {
    }

    public MovieRoomDTO(String screeningID, Map<String, String> seats) {
        this.screeningID = screeningID;
        this.seats = seats;
    }

    public String getScreeningID() {
        return screeningID;
    }

    public void setScreeningID(String screeningID) {
        this.screeningID = screeningID;
    }

    public Map<String, String> getSeats() {
        return seats;
    }

    public void setSeats(Map<String, String> seats) {
        this.seats = seats;
    }
}
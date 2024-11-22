package com.example.firebasetest.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Booking implements Serializable {
    private String id;
    private String userID;
    private Screening screening;
    private List<Seat> seatList;
    private Date holdingTime;

    public Booking() {
    }

    public Booking(String id, String userID, Screening screening, List<Seat> seatList, Date holdingTime) {
        this.id = id;
        this.userID = userID;
        this.screening = screening;
        this.seatList = seatList;
        this.holdingTime = holdingTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public Date getHoldingTime() {
        return holdingTime;
    }

    public void setHoldingTime(Date holdingTime) {
        this.holdingTime = holdingTime;
    }
}

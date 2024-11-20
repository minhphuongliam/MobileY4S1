package com.example.firebasetest.Model;

import java.io.Serializable;

public class Seat implements Serializable {
    private String seatNum;
    private String status;

    public enum Status{
        AVAILABLE,UNAVAILABLE,HOLDING,BOOKED
    }

    public Seat() {
    }

    public Seat(String seatNum, String status) {
        this.seatNum = seatNum;
        this.status = status;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public String getStatus() {
        return status;
    }
    public Status getStat() {
        switch (this.status){
            case "unavailable":
                return Status.UNAVAILABLE;
            case "holding":
                return Status.HOLDING;
            case "booked":
                return Status.BOOKED;
        }
        return Status.AVAILABLE;
    }

    public void setStatus(Status status) {
        String stat = "available";
        switch (status){
            case UNAVAILABLE:
                stat = "unavailable";
                break;
            case HOLDING:
                stat = "holding";
                break;
            case BOOKED:
                stat = "booked";
                break;
        }
        this.status = stat;
    }
}

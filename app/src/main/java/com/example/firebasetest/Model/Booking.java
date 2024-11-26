package com.example.firebasetest.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Booking implements Serializable {
    private String id;
    private String userID;
    private Screening screening;
    private List<Seat> seatList;
    private Date bookTime;
    private Float price;
    private List<Voucher> vouchers;
    private Boolean payed;

    public Booking() {
    }

    public Booking(String id, String userID, Screening screening, List<Seat> seatList, Date bookTime) {
        this.id = id;
        this.userID = userID;
        this.screening = screening;
        this.seatList = seatList;
        this.bookTime = bookTime;
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

    public Date getBookTime() {
        return bookTime;
    }

    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }
}

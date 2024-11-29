package com.example.firebasedemo.DTO;

import com.example.firebasedemo.Model.Combo;
import com.example.firebasedemo.Model.Screening;
import com.example.firebasedemo.Model.Seat;
import com.example.firebasedemo.Model.Voucher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDTO {
    private String userID;
    private Screening screening;
    private List<String> seatList = new ArrayList<>();
    private List<ComboDTO> comboList = new ArrayList<>();
    private Date bookTime;
    private Float price;
    private List<String> vouchers = new ArrayList<>();
    private Boolean payed;

    public BookingDTO() {
    }

    public BookingDTO(String userID, Screening screening, List<String> seatList, List<ComboDTO> comboList, Date bookTime, Float price, List<String> vouchers, Boolean payed) {
        this.userID = userID;
        this.screening = screening;
        this.seatList = seatList;
        this.comboList = comboList;
        this.bookTime = bookTime;
        this.price = price;
        this.vouchers = vouchers;
        this.payed = payed;
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

    public List<String> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<String> seatList) {
        this.seatList = seatList;
    }

    public List<ComboDTO> getComboList() {
        return comboList;
    }

    public void setComboList(List<ComboDTO> comboList) {
        this.comboList = comboList;
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

    public List<String> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<String> vouchers) {
        this.vouchers = vouchers;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }
}

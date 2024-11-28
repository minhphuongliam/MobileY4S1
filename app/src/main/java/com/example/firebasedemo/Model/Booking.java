package com.example.firebasedemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Booking implements Serializable, Parcelable {
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

    public Booking(String id, String userID, Screening screening, List<Seat> seatList, Date bookTime, Float price, List<Voucher> vouchers, Boolean payed) {
        this.id = id;
        this.userID = userID;
        this.screening = screening;
        this.seatList = seatList;
        this.bookTime = bookTime;
        this.price = price;
        this.vouchers = vouchers;
        this.payed = payed;
    }

    protected Booking(Parcel in) {
        id = in.readString();
        userID = in.readString();
        screening = in.readParcelable(Screening.class.getClassLoader());
        seatList = in.createTypedArrayList(Seat.CREATOR);
        vouchers = in.createTypedArrayList(Voucher.CREATOR);
        long bookTimeMillis = in.readLong();
        bookTime = bookTimeMillis == -1 ? null : new Date(bookTimeMillis);
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readFloat();
        }
        byte tmpPayed = in.readByte();
        payed = tmpPayed == 0 ? null : tmpPayed == 1;
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

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

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(userID);
        parcel.writeParcelable(screening, i);
        parcel.writeTypedList(seatList);
        parcel.writeTypedList(vouchers);
        parcel.writeLong(bookTime != null ? bookTime.getTime() : -1);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(price);
        }
        parcel.writeByte((byte) (payed == null ? 0 : payed ? 1 : 2));
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", userID='" + userID + '\'' +
                ", screening=" + screening +
                ", seatList=" + seatList +
                ", bookTime=" + bookTime +
                ", price=" + price +
                ", vouchers=" + vouchers +
                ", payed=" + payed +
                '}';
    }
}

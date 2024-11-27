package com.example.firebasetest.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Seat implements Serializable, Parcelable {
    private String seatNum;
    private Status status;

    protected Seat(Parcel in) {
        seatNum = in.readString();
        String statusString = in.readString();
        status = Status.fromString(statusString);
    }

    public static final Creator<Seat> CREATOR = new Creator<Seat>() {
        @Override
        public Seat createFromParcel(Parcel in) {
            return new Seat(in);
        }

        @Override
        public Seat[] newArray(int size) {
            return new Seat[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(seatNum);
        parcel.writeString(status != null ? status.toString() : null);
    }

    public enum Status{
        AVAILABLE, TAPPING, UNAVAILABLE, BOOKED, HOLDING;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

        public static Status fromString(String status) {
            try {
                return Status.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                return UNAVAILABLE; // Giá trị mặc định
            }
        }
    }

    public Seat() {
        this.status = Status.UNAVAILABLE; // Trạng thái mặc định
    }

    public Seat(String seatNum, Status status) {
        this.seatNum = seatNum;
        this.status = status;
    }

    public Seat(String seatNum, String status) {
        this.seatNum = seatNum;
        this.status = Status.fromString(status);
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}

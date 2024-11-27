package com.example.firebasetest.Model;

import java.io.Serializable;

public class Seat implements Serializable {
    private String seatNum;
    private Status status;

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

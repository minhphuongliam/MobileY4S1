package com.example.firebasetest.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Screening implements Serializable, Parcelable {
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

    protected Screening(Parcel in) {
        id = in.readString();
        movieID = in.readString();
        roomID = in.readString();
        long timeMillis = in.readLong();
        time = timeMillis == -1 ? null : new Date(timeMillis);
    }

    public static final Creator<Screening> CREATOR = new Creator<Screening>() {
        @Override
        public Screening createFromParcel(Parcel in) {
            return new Screening(in);
        }

        @Override
        public Screening[] newArray(int size) {
            return new Screening[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(movieID);
        parcel.writeString(roomID);
        parcel.writeLong(time != null ? time.getTime() : -1);
    }
}

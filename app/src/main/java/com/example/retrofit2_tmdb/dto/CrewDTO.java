package com.example.retrofit2_tmdb.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CrewDTO implements Parcelable {
    @SerializedName("id")
    private Integer castId;

    @SerializedName("job")
    private String job;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private Integer gender;

    @SerializedName("known_for_department")
    private String knowForDepartment;

    public Integer getCastId() {
        return castId;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public Integer getGender() {
        return gender;
    }

    public String getKnowForDepartment() {
        return knowForDepartment;
    }

    protected CrewDTO(Parcel in) {
        if (in.readByte() == 0) {
            castId = null;
        } else {
            castId = in.readInt();
        }
        job = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            gender = null;
        } else {
            gender = in.readInt();
        }
        knowForDepartment = in.readString();
    }

    public static final Creator<CrewDTO> CREATOR = new Creator<CrewDTO>() {
        @Override
        public CrewDTO createFromParcel(Parcel in) {
            return new CrewDTO(in);
        }

        @Override
        public CrewDTO[] newArray(int size) {
            return new CrewDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (castId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(castId);
        }
        parcel.writeString(job);
        parcel.writeString(name);
        if (gender == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(gender);
        }
        parcel.writeString(knowForDepartment);
    }

    @Override
    public String toString() {
        return "CrewDTO{" +
                "castId=" + castId +
                ", job='" + job + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", knowForDepartment='" + knowForDepartment + '\'' +
                '}';
    }
}

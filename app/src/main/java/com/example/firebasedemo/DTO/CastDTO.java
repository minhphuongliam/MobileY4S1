package com.example.firebasedemo.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CastDTO implements Parcelable {
    @SerializedName("cast_id")
    private Integer castId;

    @SerializedName("character")
    private String character;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private Integer gender;

    @SerializedName("known_for_department")
    private String knowForDepartment;

    public Integer getCastId() {
        return castId;
    }

    public String getCharacter() {
        return character;
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

    protected CastDTO(Parcel in) {
        if (in.readByte() == 0) {
            castId = null;
        } else {
            castId = in.readInt();
        }
        character = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            gender = null;
        } else {
            gender = in.readInt();
        }
        knowForDepartment = in.readString();
    }

    public static final Creator<CastDTO> CREATOR = new Creator<CastDTO>() {
        @Override
        public CastDTO createFromParcel(Parcel in) {
            return new CastDTO(in);
        }

        @Override
        public CastDTO[] newArray(int size) {
            return new CastDTO[size];
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
        parcel.writeString(character);
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
        return "CastDTO{" +
                "castId=" + castId +
                ", character='" + character + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", knowForDepartment='" + knowForDepartment + '\'' +
                '}';
    }
}
